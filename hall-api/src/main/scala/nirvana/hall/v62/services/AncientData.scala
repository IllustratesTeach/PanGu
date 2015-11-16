package nirvana.hall.v62.services

import java._

import nirvana.hall.v62.annotations.{IgnoreTransfer, Length}
import org.jboss.netty.buffer.{ChannelBuffer, ChannelBuffers}
import org.xsocket.{IDataSource, IDataSink}

import scala.reflect.ClassTag
import scala.reflect.runtime.universe
import scala.reflect.runtime.universe._
import scala.reflect.runtime.universe.definitions._


/**
 * support to serialize/unserialize data
 * TODO using byte transformation to convert data
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-29
 */
object AncientData {
  val mirror = universe.runtimeMirror(getClass.getClassLoader)
  val STRING_CLASS = typeOf[String]
}
trait ScalaReflect{
  private val instanceMirror = AncientData.mirror.reflect(this)
  private val clazzSymbol = instanceMirror.symbol
  private val clazzType = clazzSymbol.asType.toType

  private var dataSize:Int = 0
  private def findBaseLength(tpe:Type,length:Int):Int={
    def returnLengthOrThrowException:Int={
      if(length == 0)
        throw new IllegalArgumentException("@Lenght not defined "+tpe)
      length
    }
    tpe match {
      case ByteTpe => 1
      case ShortTpe | CharTpe => 2
      case IntTpe => 4
      case LongTpe => 8
      case AncientData.STRING_CLASS =>
        returnLengthOrThrowException
      case t if t <:< typeOf[ScalaReflect] =>
        createAncientDataByType(t).getDataSize
      case TypeRef(pre,sym,args) if sym == typeOf[Array[_]].typeSymbol =>
        if(args.length != 1)
          throw new IllegalArgumentException("only support one type parameter in Array.")
        returnLengthOrThrowException * findBaseLength(args.head,0)
      case other =>
        throw new IllegalArgumentException("type is not supported "+other)
    }
  }
  def getDataSize:Int={
    if(dataSize == 0) {
      dataSize = internalProcessField((symbol,length)=>findBaseLength(symbol.info,length)).sum
    }
    dataSize
  }
  private def internalProcessField[T :ClassTag](processor:(Symbol,Int)=>T):Array[T]={
    clazzType.members
      .filter(_.isTerm)
      .filter(_.asTerm.isVar)
      .filterNot(_.annotations.exists (typeOf[IgnoreTransfer] =:= _.tree.tpe))
      .toSeq.reverse // <----- must be reversed
      .map{ m =>
      //find @Length annotation
      val lengthAnnotation = m.annotations.find (typeOf[Length] =:= _.tree.tpe)
      val length = lengthAnnotation.map(_.tree).map{
        case Apply(fun, AssignOrNamedArg(name,Literal(Constant(value)))::Nil)=>
          value.asInstanceOf[Int]
      }.sum
      //val length = lengthAnnotation.map(_.tree.children.tail.head.children(1).asInstanceOf[Literal].value.value.asInstanceOf[Int]).sum
      processor(m,length)
    }.toArray
  }
  private def createAncientDataByType(t: universe.Type): ScalaReflect = {
    val classType = t.typeSymbol.asClass
    val constructor = classType.primaryConstructor.asMethod
    AncientData.mirror
      .reflectClass(classType)
      .reflectConstructor(constructor)()
      .asInstanceOf[ScalaReflect]
  }
  /**
   * serialize to channel buffer
   * @param dataSink netty channel buffer
   */
  def writeToDataSink(dataSink:IDataSink): IDataSink= {
    internalProcessField{(symbol,length)=>
      val tpe = symbol.info
      def returnLengthOrThrowException:Int={
        if(length == 0)
          throw new IllegalArgumentException("@Lenght not defined "+tpe)
        length
      }
      def writeString(str:String,length:Int): Unit ={
        if(str == null) writeBytes(null,length) else writeBytes(str.getBytes,length)
      }
      def skip(length:Int): Unit ={
        0 until length foreach(x=>dataSink.write(0.asInstanceOf[Byte]))
      }
      def writeBytes(bytes:Array[Byte],length:Int): Unit ={
        val bytesLength = if(bytes == null) 0 else bytes.length
        val zeroLength = length - bytesLength
        if(bytes != null)
          dataSink.write(bytes,0,bytes.length)
        if(zeroLength > 0)
          skip(zeroLength)
      }

      val termSymbol = clazzType.decl(symbol.name.toTermName).asTerm
      val value = instanceMirror.reflectField(termSymbol).get
      tpe match {
        case ByteTpe => dataSink.write(value.asInstanceOf[Byte])
        case ShortTpe | CharTpe => dataSink.write(value.asInstanceOf[Short])
        case IntTpe => dataSink.write(value.asInstanceOf[Int])
        case LongTpe => dataSink.write(value.asInstanceOf[Long])
        case AncientData.STRING_CLASS =>
          writeString(value.asInstanceOf[String],returnLengthOrThrowException)
        case t if t <:< typeOf[ScalaReflect] =>
          if(value == null) {
            val len: Int = createAncientDataByType(t).getDataSize
            skip(len)
          }else{
            value.asInstanceOf[ScalaReflect].writeToDataSink(dataSink)
          }
        case t  if t =:= typeOf[Array[Byte]] =>
          writeBytes(value.asInstanceOf[Array[Byte]],returnLengthOrThrowException)
        case TypeRef(pre,sym,args) if sym == typeOf[Array[ScalaReflect]].typeSymbol =>
          val len = returnLengthOrThrowException
          val type_len = createAncientDataByType(args.head).getDataSize
          var zeroLen = type_len * len
          if(value != null){
            val ancientDataArray = value.asInstanceOf[Array[ScalaReflect]].filterNot( _ == null)
            ancientDataArray.foreach(_.writeToDataSink(dataSink))
            zeroLen = (len-ancientDataArray.length) * type_len
          }
          if(zeroLen >0)
            skip(zeroLen)
        case other =>
          throw new IllegalArgumentException("type is not supported "+other)
      }
    }
    dataSink
  }
  /**
   * serialize to channel buffer
   * @param channelBuffer netty channel buffer
   */
  @deprecated(message = "use writeToDataSink instead of")
  def writeToChannelBuffer(channelBuffer:ChannelBuffer): ChannelBuffer = {
    internalProcessField{(symbol,length)=>
      val tpe = symbol.info
      def returnLengthOrThrowException:Int={
        if(length == 0)
          throw new IllegalArgumentException("@Lenght not defined "+tpe)
        length
      }
      def writeString(str:String,length:Int): Unit ={
        if(str == null) writeBytes(null,length) else writeBytes(str.getBytes,length)
      }
      def writeBytes(bytes:Array[Byte],length:Int): Unit ={
        val bytesLength = if(bytes == null) 0 else bytes.length
        val zeroLength = length - bytesLength
        if(bytes != null)
          channelBuffer.writeBytes(bytes)
        if(zeroLength > 0)
          channelBuffer.writerIndex(channelBuffer.writerIndex() + zeroLength)
      }

      val termSymbol = clazzType.decl(symbol.name.toTermName).asTerm
      val value = instanceMirror.reflectField(termSymbol).get
      tpe match {
        case ByteTpe => channelBuffer.writeByte(value.asInstanceOf[Byte])
        case ShortTpe | CharTpe  => channelBuffer.writeShort(value.asInstanceOf[Short])
        case IntTpe => channelBuffer.writeInt(value.asInstanceOf[Int])
        case LongTpe => channelBuffer.writeLong(value.asInstanceOf[Long])
        case AncientData.STRING_CLASS =>
          writeString(value.asInstanceOf[String],returnLengthOrThrowException)
        case t if t <:< typeOf[ScalaReflect] =>
          if(value == null) {
            val len: Int = createAncientDataByType(t).getDataSize
            channelBuffer.writerIndex(channelBuffer.writerIndex()+ len)
          }else{
            value.asInstanceOf[ScalaReflect].writeToChannelBuffer(channelBuffer)
          }
        case t  if t =:= typeOf[Array[Byte]] =>
          writeBytes(value.asInstanceOf[Array[Byte]],returnLengthOrThrowException)
        case TypeRef(pre,sym,args) if sym == typeOf[Array[ScalaReflect]].typeSymbol =>
          val len = returnLengthOrThrowException
          val type_len = createAncientDataByType(args.head).getDataSize
          var zeroLen = type_len * len
          if(value != null){
            val ancientDataArray = value.asInstanceOf[Array[ScalaReflect]].filterNot( _ == null)
            ancientDataArray.foreach(_.writeToChannelBuffer(channelBuffer))
            zeroLen = (len-ancientDataArray.length) * type_len
          }
          if(zeroLen >0)
            channelBuffer.writerIndex(channelBuffer.writerIndex() + zeroLen)
        case other =>
          throw new IllegalArgumentException("type is not supported "+other)
      }
    }
    channelBuffer
  }
  /**
   * convert channel buffer data as object
   * @param dataSource netty channel buffer
   */
  def fromDataSource(dataSource: IDataSource): this.type ={
    internalProcessField{(symbol,length)=>
      val tpe = symbol.info
      def returnLengthOrThrowException:Int={
        if(length == 0)
          throw new IllegalArgumentException("@Lenght not defined "+tpe)
        length
      }
      def readByteArray(len:Int): Array[Byte]={
        dataSource.readBytesByLength(len)
      }

      val termSymbol = clazzType.decl(symbol.name.toTermName).asTerm
      val field = instanceMirror.reflectField(termSymbol)
      tpe match {
        case ByteTpe => field.set(dataSource.readByte())
        case ShortTpe | CharTpe => field.set(dataSource.readShort())
        case IntTpe => field.set(dataSource.readInt())
        case LongTpe => field.set(dataSource.readLong())
        case AncientData.STRING_CLASS =>
          val bytes = readByteArray(returnLengthOrThrowException)
          field.set(new String(bytes).trim)
        case t if t <:< typeOf[ScalaReflect] =>
          val ancientData = createAncientDataByType(t)
          ancientData.fromDataSource(dataSource)
          field.set(ancientData)
        case t  if t =:= typeOf[Array[Byte]] =>
          val bytes = readByteArray(returnLengthOrThrowException)
          field.set(bytes)
        case TypeRef(pre,sym,args) if sym == typeOf[Array[ScalaReflect]].typeSymbol =>
          val len = returnLengthOrThrowException
          val sampleClass = createAncientDataByType(args.head).getClass
          val ancientDataArray = lang.reflect.Array.newInstance(sampleClass,len)

          0 until len foreach {i=>
            lang.reflect.Array.set(ancientDataArray,i,createAncientDataByType(args.head).fromDataSource(dataSource))
          }
          field.set(ancientDataArray)
        case other =>
          throw new IllegalArgumentException("type is not supported "+other)
      }
    }

    this
  }
  /**
   * convert channel buffer data as object
   * @param channelBuffer netty channel buffer
   */
  @deprecated(message = "use fromDataSource instead of")
  def fromChannelBuffer(channelBuffer: ChannelBuffer): this.type ={
    internalProcessField{(symbol,length)=>
      val tpe = symbol.info
      def returnLengthOrThrowException:Int={
        if(length == 0)
          throw new IllegalArgumentException("@Lenght not defined "+tpe)
        length
      }

      val termSymbol = clazzType.decl(symbol.name.toTermName).asTerm
      val field = instanceMirror.reflectField(termSymbol)
      tpe match {
        case ByteTpe => field.set(channelBuffer.readByte())
        case ShortTpe | CharTpe => field.set(channelBuffer.readShort())
        case IntTpe => field.set(channelBuffer.readInt())
        case LongTpe => field.set(channelBuffer.readLong())
        case AncientData.STRING_CLASS =>
          val bytes = new Array[Byte](returnLengthOrThrowException)
          channelBuffer.readBytes(bytes)
          field.set(new String(bytes).trim)
        case t if t <:< typeOf[ScalaReflect] =>
          val ancientData = createAncientDataByType(t)
          ancientData.fromChannelBuffer(channelBuffer)
          field.set(ancientData)
        case t  if t =:= typeOf[Array[Byte]] =>
          val bytes = new Array[Byte](returnLengthOrThrowException)
          channelBuffer.readBytes(bytes)
          field.set(bytes)
        case TypeRef(pre,sym,args) if sym == typeOf[Array[ScalaReflect]].typeSymbol =>
          val len = returnLengthOrThrowException
          val sampleClass = createAncientDataByType(args.head).getClass
          val ancientDataArray = lang.reflect.Array.newInstance(sampleClass,len)

          0 until len foreach {i=>
            lang.reflect.Array.set(ancientDataArray,i,createAncientDataByType(args.head).fromChannelBuffer(channelBuffer))
          }
          field.set(ancientDataArray)
        case other =>
          throw new IllegalArgumentException("type is not supported "+other)
      }
    }

    this
  }
}
sealed trait JavaReflect{
  private var dataSize:Int = 0
  /**
   * get data size
   */
  def getDataSize:Int={
    if(dataSize == 0) {
      val BYTE_CLASS = classOf[Byte]
      val CHAR_CLASS = classOf[Char]
      val SHORT_CLASS = classOf[Short]
      val INT_CLASS = classOf[Int]
      val LONG_CLASS = classOf[Long]
      val STRING_CLASS = classOf[String]
      val BYTE_ARRAY_CLASS = classOf[Array[Byte]]
      val ANCIENT_CLASS = classOf[AncientData]
      getClass.getDeclaredFields.filterNot(_.isAnnotationPresent(classOf[IgnoreTransfer])).foreach { f =>
        val annotation = f.getAnnotation(classOf[Length])
        var dataLength = 0
        if (annotation != null) {
          dataLength = annotation.value()
        }
        f.getType match {
          case `BYTE_CLASS` =>
            dataSize += 1
          case `CHAR_CLASS` =>
            dataSize += 1
          case `SHORT_CLASS` =>
            dataSize += 2
          case `INT_CLASS` =>
            dataSize += 4
          case `LONG_CLASS` =>
            dataSize += 8
          case `STRING_CLASS` =>
            dataSize += dataLength
          case `BYTE_ARRAY_CLASS` =>
            dataSize += dataLength
          case other =>
            if(ANCIENT_CLASS.isAssignableFrom(other)) {
              dataSize += other.newInstance().asInstanceOf[AncientData].getDataSize
            }else if(other.isArray){
              val componentType = other.getComponentType
              if(ANCIENT_CLASS.isAssignableFrom(componentType)){
                dataSize += componentType.newInstance().asInstanceOf[AncientData].getDataSize * dataLength
              }else{
                throw new IllegalAccessException("unknown type:" + componentType)
              }
            }else {
              throw new IllegalAccessException("unknown type:" + other)
            }
        }
      }
    }
    dataSize
  }
  def toByteArray: Array[Byte]= {
    val buffer = ChannelBuffers.buffer(getDataSize)
    writeToChannelBuffer(buffer).array()
  }
  /**
   * serialize to channel buffer
   * @param channelBuffer netty channel buffer
   */
  def writeToChannelBuffer(channelBuffer:ChannelBuffer): ChannelBuffer = {
    getClass.getDeclaredFields.filterNot(_.isAnnotationPresent(classOf[IgnoreTransfer])).foreach { f =>
      val annotation = f.getAnnotation(classOf[Length])
      val fieldValue = getClass.getMethod(f.getName).invoke(this)
      var dataLength = 0
      if(annotation != null){
        dataLength = annotation.value()
      }
      val BYTE_CLASS         = classOf[Byte]
      val CHAR_CLASS         = classOf[Char]
      val SHORT_CLASS        = classOf[Short]
      val INT_CLASS          = classOf[Int]
      val LONG_CLASS         = classOf[Long]
      val STRING_CLASS       = classOf[String]
      val BYTE_ARRAY_CLASS   = classOf[Array[Byte]]
      val ANCIENT_CLASS      = classOf[AncientData]
      f.getType match{
        case `BYTE_CLASS` =>
          channelBuffer.writeByte(fieldValue.asInstanceOf[Byte])
        case `CHAR_CLASS` =>
          channelBuffer.writeChar(fieldValue.asInstanceOf[Char])
        case `SHORT_CLASS` =>
          channelBuffer.writeShort(fieldValue.asInstanceOf[Short])
        case `INT_CLASS` =>
          channelBuffer.writeInt(fieldValue.asInstanceOf[Int])
        case `LONG_CLASS` =>
          channelBuffer.writeLong(fieldValue.asInstanceOf[Long])
        case `STRING_CLASS` =>
          val x = fieldValue.asInstanceOf[String]
          var length = 0
          if(x!= null) {
            val bytes = x.getBytes
            length = bytes.length
            if (length > dataLength) {
              throw new IllegalAccessException("value length[" ++ "] > length defined [" + dataLength + "]")
            }
            channelBuffer.writeBytes(bytes)
          }
          channelBuffer.writerIndex(channelBuffer.writerIndex() + dataLength - length)
        case `BYTE_ARRAY_CLASS` =>
          var length = 0
          val x = fieldValue.asInstanceOf[Array[Byte]]
          if(x!= null) {
            length = x.length
            if (length > dataLength) {
              throw new IllegalAccessException("value length[" ++ "] > length defined [" + dataLength + "]")
            }
            channelBuffer.writeBytes(x)
          }
          channelBuffer.writerIndex(channelBuffer.writerIndex() + dataLength - length)

        case `ANCIENT_CLASS` =>
          fieldValue.asInstanceOf[AncientData].writeToChannelBuffer(channelBuffer)
        case other=>
          if(ANCIENT_CLASS.isAssignableFrom(other)) {
            if(fieldValue == null){
              val size = other.newInstance().asInstanceOf[AncientData].getDataSize
              channelBuffer.writerIndex(channelBuffer.writerIndex() + size)
            }else{
              fieldValue.asInstanceOf[AncientData].writeToChannelBuffer(channelBuffer)
            }
          }else if(other.isArray){
            val componentType = other.getComponentType
            if(ANCIENT_CLASS.isAssignableFrom(componentType)){
              val componentSize = componentType.newInstance().asInstanceOf[AncientData].getDataSize
              if(fieldValue == null){//fill empty data
                channelBuffer.writerIndex(channelBuffer.writerIndex() + componentSize * dataLength)
              }else{
                val arr = fieldValue.asInstanceOf[Array[_]]
                arr.foreach { x =>
                  if(x == null)//increase writer index
                    channelBuffer.writerIndex(channelBuffer.writerIndex() + componentSize)
                  else
                    x.asInstanceOf[AncientData].writeToChannelBuffer(channelBuffer)
                }

                val remain = dataLength - arr.length
                if(remain > 0)
                  channelBuffer.writerIndex(channelBuffer.writerIndex() + componentSize * remain)
              }
            }else{
              throw new IllegalAccessException("unknown type:" + componentType)
            }
          }else {
            throw new IllegalAccessException("unknown type:" + other)
          }
      }
    }

    channelBuffer
  }
  def fromBytes(bytes: Array[Byte]): this.type ={
    fromChannelBuffer(ChannelBuffers.wrappedBuffer(bytes))
  }

  /**
   * convert channel buffer data as object
   * @param channelBuffer netty channel buffer
   */
  def fromChannelBuffer(channelBuffer: ChannelBuffer): this.type ={
    getClass.getDeclaredFields.filterNot(_.isAnnotationPresent(classOf[IgnoreTransfer])).foreach { f =>
      val annotation = f.getAnnotation(classOf[Length])
      var dataLength = 0
      if(annotation != null){
        dataLength = annotation.value()
      }
      val BYTE_CLASS         = classOf[Byte]
      val CHAR_CLASS         = classOf[Char]
      val SHORT_CLASS        = classOf[Short]
      val INT_CLASS          = classOf[Int]
      val LONG_CLASS         = classOf[Long]
      val STRING_CLASS       = classOf[String]
      val BYTE_ARRAY_CLASS   = classOf[Array[Byte]]
      val ANCIENT_CLASS      = classOf[AncientData]
      f.getType match{
        case `BYTE_CLASS` =>
          getClass.getMethod(f.getName+"_$eq",BYTE_CLASS).invoke(this,channelBuffer.readByte().asInstanceOf[AnyRef])
        case `CHAR_CLASS` =>
          getClass.getMethod(f.getName+"_$eq",CHAR_CLASS).invoke(this,channelBuffer.readChar().asInstanceOf[AnyRef])
        case `SHORT_CLASS` =>
          getClass.getMethod(f.getName+"_$eq",SHORT_CLASS).invoke(this,channelBuffer.readShort().asInstanceOf[AnyRef])
        case `INT_CLASS` =>
          getClass.getMethod(f.getName+"_$eq",INT_CLASS).invoke(this,channelBuffer.readInt().asInstanceOf[AnyRef])
        case `LONG_CLASS` =>
          getClass.getMethod(f.getName+"_$eq",LONG_CLASS).invoke(this,channelBuffer.readLong().asInstanceOf[AnyRef])
        case `STRING_CLASS` =>
          val bytes = new Array[Byte](dataLength)
          channelBuffer.readBytes(bytes)
          getClass.getMethod(f.getName+"_$eq",STRING_CLASS).invoke(this,new String(bytes).trim)
        case `BYTE_ARRAY_CLASS` =>
          val bytes = new Array[Byte](dataLength)
          channelBuffer.readBytes(bytes)
          getClass.getMethod(f.getName+"_$eq",BYTE_ARRAY_CLASS).invoke(this,bytes)
        case `ANCIENT_CLASS` =>
          val ancientData = f.getType.newInstance().asInstanceOf[AncientData]
          ancientData.fromChannelBuffer(channelBuffer)
          getClass.getMethod(f.getName+"_$eq",f.getType).invoke(this,ancientData)
        case other=>
          if(ANCIENT_CLASS.isAssignableFrom(other)) {
            val ancientData = other.newInstance().asInstanceOf[AncientData]
            ancientData.fromChannelBuffer(channelBuffer)
            getClass.getMethod(f.getName+"_$eq",other).invoke(this,ancientData)
          }else if(other.isArray){
            val componentType = other.getComponentType
            if(ANCIENT_CLASS.isAssignableFrom(componentType)){
              val dataArray = lang.reflect.Array.newInstance(componentType,dataLength)

              0 until dataLength foreach { x=>
                val value = componentType.newInstance().asInstanceOf[AncientData]
                value.fromChannelBuffer(channelBuffer)
                lang.reflect.Array.set(dataArray,x,value)
              }
              getClass.getMethod(f.getName+"_$eq",other).invoke(this,dataArray)
            }else{
              throw new IllegalAccessException("unknown type:" + componentType)
            }
          }else {
            throw new IllegalAccessException("unknown type:" + other)
          }
      }
    }

    this
  }
}
class AncientData extends ScalaReflect{
}

