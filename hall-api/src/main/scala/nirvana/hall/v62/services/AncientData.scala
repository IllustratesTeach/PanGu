package nirvana.hall.v62.services

import java._

import nirvana.hall.v62.annotations.{IgnoreTransfer, Length}
import nirvana.hall.v62.services.AncientData._
import org.jboss.netty.buffer.ChannelBuffer
import org.xsocket.{IDataSink, IDataSource}

import scala.reflect.ClassTag
import scala.reflect.runtime.universe
import scala.reflect.runtime.universe._
import scala.reflect.runtime.universe.definitions._


/**
 * support to serialize/deserialize data
 * TODO using byte transformation to convert data
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-29
 */
object AncientData {
  //global scala reflect mirror
  val mirror = universe.runtimeMirror(getClass.getClassLoader)
  val STRING_CLASS = typeOf[String]

  /** stream reader type ,it can suit netty's ChannelBuffer and xSocket's IDataSource **/
  type StreamReader = {
    def readByte(): Byte
    def readShort(): Short
    def readInt(): Int
    def readLong(): Long
  }
  type StreamWriter ={
    def writeByte(byte:Byte)
    def writeInt(i:Int)
    def writeShort(i:Short)
    def writeLong(i:Long)
    def writeBytes(src:Array[Byte])
    def writeBytes(src: Array[Byte], srcIndex: Int, length: Int)
  }
}
trait ScalaReflect{
  private val instanceMirror = AncientData.mirror.reflect(this)
  private val clazzSymbol = instanceMirror.symbol
  private val clazzType = clazzSymbol.asType.toType

  //self type data size by member data type
  private var dataSize:Int = 0

  /**
   * find primitive data type length
   */
  private def findPrimitiveTypeLength(tpe:Type,length:Int):Int={
    def returnLengthOrThrowException:Int={
      if(length == 0)
        throw new IllegalArgumentException("@Length not defined @"+tpe)
      length
    }
    def throwExceptionIfLengthGTZeroOrGet[T](value:T): T={
      if(length !=0)
        throw new IllegalArgumentException("@Length wrong defined @"+tpe)
      value
    }
    tpe match {
      case ByteTpe => throwExceptionIfLengthGTZeroOrGet(1)
      case ShortTpe | CharTpe => throwExceptionIfLengthGTZeroOrGet(2)
      case IntTpe => throwExceptionIfLengthGTZeroOrGet(4)
      case LongTpe => throwExceptionIfLengthGTZeroOrGet(8)
      case AncientData.STRING_CLASS =>
        returnLengthOrThrowException
      case t if t <:< typeOf[ScalaReflect] =>
        throwExceptionIfLengthGTZeroOrGet(createAncientDataByType(t).getDataSize)
      case TypeRef(pre,sym,args) if sym == typeOf[Array[_]].typeSymbol =>
        if(args.length != 1)
          throw new IllegalArgumentException("only support one type parameter in Array.")
        //using recursive call to find length
        returnLengthOrThrowException * findPrimitiveTypeLength(args.head,0)
      case other =>
        throw new IllegalArgumentException("type is not supported "+other)
    }
  }

  /**
   * calculate data size and return.
   * @return data size
   */
  def getDataSize:Int={
    if(dataSize == 0) {
      dataSize = internalProcessField((symbol,length)=>findPrimitiveTypeLength(symbol.info,length)).sum
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
      //find @Length annotation and get value
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
  def writeToStreamWriter[T <: StreamWriter](dataSink:T): T= {
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
        0 until length foreach(x=>dataSink.writeByte(0.asInstanceOf[Byte]))
      }
      def writeBytes(bytes:Array[Byte],length:Int): Unit ={
        val bytesLength = if(bytes == null) 0 else bytes.length
        val zeroLength = length - bytesLength
        if(bytes != null)
          dataSink.writeBytes(bytes)
        if(zeroLength > 0)
          skip(zeroLength)
      }

      val termSymbol = clazzType.decl(symbol.name.toTermName).asTerm
      val value = instanceMirror.reflectField(termSymbol).get
      tpe match {
        case ByteTpe => dataSink.writeByte(value.asInstanceOf[Byte])
        case ShortTpe | CharTpe => dataSink.writeShort(value.asInstanceOf[Short])
        case IntTpe => dataSink.writeInt(value.asInstanceOf[Int])
        case LongTpe => dataSink.writeLong(value.asInstanceOf[Long])
        case AncientData.STRING_CLASS =>
          writeString(value.asInstanceOf[String],returnLengthOrThrowException)
        case t if t <:< typeOf[ScalaReflect] => //inherit ScalaReflect
          if(value == null) {
            val len: Int = createAncientDataByType(t).getDataSize
            skip(len)
          }else{
            value.asInstanceOf[ScalaReflect].writeToStreamWriter(dataSink)
          }
        case t  if t =:= typeOf[Array[Byte]] =>
          writeBytes(value.asInstanceOf[Array[Byte]],returnLengthOrThrowException)
        case TypeRef(pre,sym,args) if sym == typeOf[Array[ScalaReflect]].typeSymbol => //Array of ScalaReflect
          val len = returnLengthOrThrowException
          val type_len = createAncientDataByType(args.head).getDataSize
          var zeroLen = type_len * len
          if(value != null){
            val ancientDataArray = value.asInstanceOf[Array[ScalaReflect]].filterNot( _ == null)
            ancientDataArray.foreach(_.writeToStreamWriter(dataSink))
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
        case t if t <:< typeOf[ScalaReflect] => //inherit ScalaReflect
          if(value == null) {
            val len: Int = createAncientDataByType(t).getDataSize
            skip(len)
          }else{
            value.asInstanceOf[ScalaReflect].writeToDataSink(dataSink)
          }
        case t  if t =:= typeOf[Array[Byte]] =>
          writeBytes(value.asInstanceOf[Array[Byte]],returnLengthOrThrowException)
        case TypeRef(pre,sym,args) if sym == typeOf[Array[ScalaReflect]].typeSymbol => //Array of ScalaReflect
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
        //TODO encoding ?
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
  def fromStreamReader(dataSource: StreamReader): this.type ={
    internalProcessField{(symbol,length)=>
      val tpe = symbol.info
      def returnLengthOrThrowException:Int={
        if(length == 0)
          throw new IllegalArgumentException("@Lenght not defined "+tpe)
        length
      }
      def readByteArray(len:Int): Array[Byte]={
        dataSource match{
          case ds:IDataSource =>
            ds.readBytesByLength(len)
          case channel:ChannelBuffer =>
            channel.readBytes(len).array()
        }
        //dataSource.readBytesByLength(len)
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
          ancientData.fromStreamReader(dataSource)
          field.set(ancientData)
        case t  if t =:= typeOf[Array[Byte]] =>
          val bytes = readByteArray(returnLengthOrThrowException)
          field.set(bytes)
        case TypeRef(pre,sym,args) if sym == typeOf[Array[ScalaReflect]].typeSymbol =>
          val len = returnLengthOrThrowException
          val sampleClass = createAncientDataByType(args.head).getClass
          val ancientDataArray = lang.reflect.Array.newInstance(sampleClass,len)

          0 until len foreach {i=>
            lang.reflect.Array.set(ancientDataArray,i,createAncientDataByType(args.head).fromStreamReader(dataSource))
          }
          field.set(ancientDataArray)
        case other =>
          throw new IllegalArgumentException("type is not supported "+other)
      }
    }

    this
  }
}
class AncientData extends ScalaReflect{
}

