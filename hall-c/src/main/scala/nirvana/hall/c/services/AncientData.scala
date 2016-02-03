package nirvana.hall.c.services

import java._
import java.io.{BufferedInputStream, InputStream, OutputStream}
import java.nio.ByteBuffer
import java.nio.charset.Charset
import javax.imageio.stream.ImageInputStream

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.annotations.{IgnoreTransfer, Length, LengthRef}
import nirvana.hall.c.services.AncientData._
import org.jboss.netty.buffer.{ChannelBuffer, ChannelBuffers}
import org.xsocket.{IDataSink, IDataSource}

import scala.annotation.tailrec
import scala.language.experimental.macros
import scala.language.reflectiveCalls
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
object AncientData extends WrapAsStreamWriter{
  //global scala reflect mirror
  val mirror = universe.runtimeMirror(Thread.currentThread().getContextClassLoader)
  val STRING_CLASS = typeOf[String]

  /** stream reader type ,it can suit netty's ChannelBuffer and xSocket's IDataSource **/
  trait StreamReader {
    def readByte(): Byte
    def readShort(): Short
    def readInt(): Int
    def readLong(): Long
    def readByteArray(len: Int): Array[Byte]
    def markReaderIndex():Unit
    def resetReaderIndex():Unit
  }
  type StreamWriter = {
    def writeByte(byte:Int)
    def writeInt(i:Int)
    def writeShort(i:Int)
    def writeLong(i:Long)
    def writeBytes(src:Array[Byte])
    def writeZero(length:Int)
  }
  @tailrec
  def readByteArray(is:InputStream,data:Array[Byte],size:Int,offset:Int=0): Unit ={
    val n = is.read(data,offset,size)
    if(n != size){
      readByteArray(is,data,size-n,n)
    }
  }
  class InputStreamReader(is:InputStream) extends StreamReader{
    def readByteArray(len: Int): Array[Byte] = {
      val data = new Array[Byte](len)
      AncientData.readByteArray(is,data,len)
      data
    }

    private val tmp = new Array[Byte](8)
    def readByte(): Byte = {
      AncientData.readByteArray(is,tmp,1)
      tmp(0)
    }
    def readShort(): Short ={
      AncientData.readByteArray(is,tmp,2)
      ByteBuffer.wrap(tmp).getShort
    }
    def readInt(): Int ={
      AncientData.readByteArray(is,tmp,4)
      ByteBuffer.wrap(tmp).getInt
    }
    def readLong(): Long ={
      AncientData.readByteArray(is,tmp,8)
      ByteBuffer.wrap(tmp).getLong
    }
    def markReaderIndex():Unit={is.mark(1000)}
    def resetReaderIndex():Unit={is.reset()}
  }
  class ImageInputStreamReader(is:ImageInputStream) extends StreamReader{
    def readByte(): Byte = is.readByte()
    def readShort(): Short = is.readShort()
    def readInt(): Int = is.readInt()
    def readLong(): Long = is.readLong()
    def markReaderIndex():Unit={is.mark()}
    def resetReaderIndex():Unit={is.reset()}
    def readByteArray(len: Int): Array[Byte] = {
      val r = new Array[Byte](len)
      is.readFully(r)
      r
    }
  }
  class DataSourceReader(is:IDataSource) extends StreamReader{
    def readByte(): Byte = is.readByte()
    def readShort(): Short = is.readShort()
    def readInt(): Int = is.readInt()
    def readLong(): Long = is.readLong()
    def markReaderIndex():Unit={throw new UnsupportedOperationException}
    def resetReaderIndex():Unit={throw new UnsupportedOperationException}
    def readByteArray(len: Int): Array[Byte] =  is.readBytesByLength(len)
  }
  class ChannelBufferReader(is:ChannelBuffer) extends StreamReader{
    def readByte(): Byte = is.readByte()
    def readShort(): Short = is.readShort()
    def readInt(): Int = is.readInt()
    def readLong(): Long = is.readLong()
    def markReaderIndex():Unit={is.markReaderIndex()}
    def resetReaderIndex():Unit={is.resetReaderIndex()}
    def readByteArray(len: Int): Array[Byte] =  {val r=new Array[Byte](len);is.readBytes(r);r}
  }
}

/**
 * wrap netty's ChannelBuffer and xSocket's IDataSink
 */
trait WrapAsStreamWriter {
  implicit def asStreamReader(is:IDataSource):StreamReader = new DataSourceReader(is)
  implicit def asStreamReader(is:ChannelBuffer):StreamReader = new ChannelBufferReader(is)
  implicit def asStreamReader(is:InputStream):StreamReader = {
    if(is.markSupported()) new InputStreamReader(is) else new InputStreamReader(new BufferedInputStream(is))
  }
  implicit def asStreamReader(is:ImageInputStream):StreamReader = new ImageInputStreamReader(is)

  implicit def asStreamWriter(output: OutputStream): StreamWriter = new {
    private val arr = new Array[Byte](8)
    private val tmp = ByteBuffer.wrap(arr)
    def writeShort(i: Int): Unit = {
      tmp.position(0)
      tmp.putShort(i.toShort)
      output.write(arr,0,2)
    }
    def writeInt(i: Int): Unit = {
      tmp.position(0)
      tmp.putInt(i)
      output.write(arr,0,4)
    }
    def writeBytes(src: Array[Byte]): Unit = {
      output.write(src)
    }
    def writeLong(i: Long): Unit = {
      tmp.position(0)
      tmp.putLong(i)
      output.write(arr,0,8)
    }
    def writeByte(byte: Int): Unit = output.write(byte)
    def writeZero(length: Int): Unit = {
      if (length == 0) {
        return
      }
      if (length < 0) {
        throw new IllegalArgumentException("length must be 0 or greater than 0.")
      }
      val nLong = length >>> 3
      val nBytes = length & 7
      0 until nLong foreach (x => writeLong(0))
      if (nBytes == 4) {
        writeInt(0)
      } else if (nBytes < 4) {
        0 until nBytes foreach (x => writeByte(0))
      } else {
        writeInt(0)
        0 until (nBytes - 4) foreach (x => writeByte(0))
      }
    }
  }
  implicit def asStreamWriter(dataSink: IDataSink): StreamWriter = new {
    def writeShort(i: Int): Unit = dataSink.write(i.toShort)
    def writeInt(i: Int): Unit = dataSink.write(i)
    def writeBytes(src: Array[Byte]): Unit = dataSink.write(src, 0, src.length)
    def writeLong(i: Long): Unit = dataSink.write(i)
    def writeByte(byte: Int): Unit = dataSink.write(byte.toByte)
    def writeZero(length: Int): Unit = {
      if (length == 0) {
        return
      }
      if (length < 0) {
        throw new IllegalArgumentException("length must be 0 or greater than 0.")
      }
      val nLong = length >>> 3
      val nBytes = length & 7
      0 until nLong foreach (x => writeLong(0))
      if (nBytes == 4) {
        writeInt(0)
      } else if (nBytes < 4) {
        0 until nBytes foreach (x => writeByte(0))
      } else {
        writeInt(0)
        0 until (nBytes - 4) foreach (x => writeByte(0))
      }
    }
  }
}


trait ScalaReflect{
  private val instanceMirror = AncientData.mirror.reflect(this)
//  private val clazzSymbol = typeOf[this.type].typeSymbol
  private val clazzSymbol = instanceMirror.symbol
  private val clazzType = clazzSymbol.asType.toType

  //self type data size by member data type
  private var dataSize:Int = 0

  /**
   * find primitive data type length
   */
  private def findPrimitiveTypeLength(term:Symbol,tpe:Type,length:Option[Int]):Int={
    //println("process term "+term+" length:"+length)
    def returnLengthOrThrowException:Int={
      if(length.isEmpty)
        throw new IllegalArgumentException("@Length not defined at "+term)
      length.get
    }
    def throwExceptionIfLengthGTZeroOrGet[T](value:T): T={
      if(length.isDefined)
        throw new IllegalArgumentException("@Length wrong defined at "+term)
      value
    }
    tpe match {
      case t if t<:< ByteTpe => throwExceptionIfLengthGTZeroOrGet(1)
      case t if t<:< ShortTpe | t <:< CharTpe => throwExceptionIfLengthGTZeroOrGet(2)
      case t if t <:< IntTpe => throwExceptionIfLengthGTZeroOrGet(4)
      case t if t <:< LongTpe => throwExceptionIfLengthGTZeroOrGet(8)
      case AncientData.STRING_CLASS =>
        returnLengthOrThrowException
      case t if t <:< typeOf[ScalaReflect] =>
        throwExceptionIfLengthGTZeroOrGet(createAncientDataByType(t).getDataSize)
      case t  if t =:= typeOf[Array[Byte]] =>
        returnLengthOrThrowException
      case TypeRef(pre,sym,args) if sym == typeOf[Array[ScalaReflect]].typeSymbol => //Array of ScalaReflect
        val len = returnLengthOrThrowException
        val value = instanceMirror.reflectField(term.asInstanceOf[TermSymbol]).get
        if(value==null){
          len * findPrimitiveTypeLength(term,args.head,None)
        }else {
          var finalLength = 0
          val type_len = createAncientDataByType(args.head).getDataSize
          var zeroLen = type_len * len
          val ancientDataArray = value.asInstanceOf[Array[AncientData]].filterNot(_ == null)
          ancientDataArray.foreach { x =>
            finalLength += x.getDataSize
            zeroLen -= type_len
          }
          finalLength += zeroLen

          finalLength
        }
      case other =>
        throw new IllegalArgumentException("type is not supported "+other+" for:"+term)
    }
  }

  /**
   * calculate data size and return.
   * @return data size
   */
  def getDataSize:Int={
    if(dataSize == 0) {
      dataSize = internalProcessField((symbol,length)=>findPrimitiveTypeLength(symbol,symbol.asTerm.typeSignature,length)).sum
    }
    dataSize
  }
  private def internalProcessField[T :ClassTag](processor:(Symbol,Option[Int])=>T):Array[T]={
    clazzType.members
      .filter(_.isTerm)
      .filter(_.asTerm.isVar)
      .filterNot(_.annotations.exists (typeOf[IgnoreTransfer] =:= _.tree.tpe))
      .toSeq.reverse // <----- must be reversed
      .map{ m =>
      //println("process ",m)
      //find @Length annotation and get value
      val lengthAnnotation = m.annotations.find (typeOf[Length] =:= _.tree.tpe)
      val length = lengthAnnotation.map(_.tree).map{
        case Apply(fun, AssignOrNamedArg(name,Literal(Constant(value)))::Nil)=>
          value.asInstanceOf[Int]
      }

      //find @LengthRef annotation and get value
      val lengthRefAnnotation = m.annotations.find (typeOf[LengthRef] =:= _.tree.tpe)
      val lengthRef = lengthRefAnnotation.map(_.tree).map{
        case Apply(fun, AssignOrNamedArg(name,Literal(Constant(refFieldName)))::Nil)=>

          val fieldName = refFieldName.asInstanceOf[String]
          val termSymbol = clazzType.decl(TermName(fieldName)).asTerm
          val value = instanceMirror.reflectField(termSymbol).get
          if(value == null) {
            0
          } else {
            val stringValue = value.toString.trim()
            if (stringValue.isEmpty) 0 else {
              //println(stringValue,stringValue.toInt)
              stringValue.toInt
            }
          }
      }
      if(length.isDefined  && lengthRef.isDefined )
        throw new IllegalStateException("@Length and @LengthRef both defined at "+m)
      //val length = lengthAnnotation.map(_.tree.children.tail.head.children(1).asInstanceOf[Literal].value.value.asInstanceOf[Int]).sum
      processor(m,if(length.isDefined) length else lengthRef)
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
   * @param stream netty channel buffer
   */
  def writeToStreamWriter[T](stream:T)(implicit converter:T=> StreamWriter): T= {
    val dataSink = converter(stream)
    internalProcessField{(symbol,length)=>
      //println("process "+symbol)
      val tpe = symbol.info
      def returnLengthOrThrowException:Int={
        if(length.isEmpty)
          throw new IllegalArgumentException("@Lenght not defined "+tpe)
        length.get
      }
      def writeString(str:String,length:Int): Unit ={
        if(str == null) writeBytes(null,length) else writeBytes(str.getBytes,length)
      }
      def writeBytes(bytes:Array[Byte],length:Int): Unit ={
        //println(length)
        val bytesLength = if(bytes == null) 0 else bytes.length
        val zeroLength = length - bytesLength
        if(bytes != null) {
          dataSink.writeBytes(bytes)
        }
        dataSink.writeZero(zeroLength)
      }

      val termSymbol = clazzType.decl(symbol.name.toTermName).asTerm
      val value = instanceMirror.reflectField(termSymbol).get
      tpe match {
        case t if t <:< ByteTpe => dataSink.writeByte(value.asInstanceOf[Byte])
        case t if t <:< ShortTpe | t <:< CharTpe => dataSink.writeShort(value.asInstanceOf[Short])
        case t if t <:< IntTpe => dataSink.writeInt(value.asInstanceOf[Int])
        case t if t <:< LongTpe => dataSink.writeLong(value.asInstanceOf[Long])
        case AncientData.STRING_CLASS =>
          writeString(value.asInstanceOf[String],returnLengthOrThrowException)
        case t if t <:< typeOf[ScalaReflect] => //inherit ScalaReflect
          if(value == null) {
            val len = createAncientDataByType(t).getDataSize
            dataSink.writeZero(len)
          }else{
            value.asInstanceOf[ScalaReflect].writeToStreamWriter(stream)
          }
        case t  if t =:= typeOf[Array[Byte]] =>
          writeBytes(value.asInstanceOf[Array[Byte]],returnLengthOrThrowException)
        case TypeRef(pre,sym,args) if sym == typeOf[Array[ScalaReflect]].typeSymbol => //Array of ScalaReflect
          val len = returnLengthOrThrowException
          val type_len = createAncientDataByType(args.head).getDataSize
          var zeroLen = type_len * len
          if(value != null){
            val ancientDataArray = value.asInstanceOf[Array[ScalaReflect]].filterNot( _ == null)
            ancientDataArray.foreach{x=>
              x.writeToStreamWriter(stream)
              zeroLen -= type_len
            }
          }
          dataSink.writeZero(zeroLen)
        case other =>
          throw new IllegalArgumentException("type is not supported "+other)
      }
    }
    stream
  }
  protected def readBytesFromStreamReader(dataSource:StreamReader,len:Int): Array[Byte]= dataSource.readByteArray(len)
  /**
   * convert channel buffer data as object
   * @param dataSource netty channel buffer
   */
  def fromStreamReader(dataSource: StreamReader,encoding:Charset=AncientConstants.UTF8_ENCODING): this.type ={
    internalProcessField{(symbol,length)=>
      ////println("read "+symbol+" ..." )

      val tpe = symbol.info
      def returnLengthOrThrowException:Int={
        if(length.isEmpty)
          throw new IllegalArgumentException("@Lenght not defined "+tpe)
        length.get
      }
      def readByteArray(len:Int): Array[Byte]= readBytesFromStreamReader(dataSource,len)

      val termSymbol = clazzType.decl(symbol.name.toTermName).asTerm
      val field = instanceMirror.reflectField(termSymbol)
      tpe match {
        case t if t <:< ByteTpe => field.set(dataSource.readByte())
        case t if t <:< ShortTpe | t<:< CharTpe => field.set(dataSource.readShort())
        case t if t<:< IntTpe => field.set(dataSource.readInt())
        case t if t<:< LongTpe => field.set(dataSource.readLong())
        case AncientData.STRING_CLASS =>
          val bytes = readByteArray(returnLengthOrThrowException)
          field.set(new String(bytes,encoding).trim)
        case t if t <:< typeOf[ScalaReflect] =>
          val ancientData = createAncientDataByType(t)
          ancientData.fromStreamReader(dataSource,encoding)
          field.set(ancientData)
        case t  if t =:= typeOf[Array[Byte]] =>
          val bytes = readByteArray(returnLengthOrThrowException)
          field.set(bytes)
        case TypeRef(pre,sym,args) if sym == typeOf[Array[ScalaReflect]].typeSymbol =>
          val len = returnLengthOrThrowException
          val sampleClass = createAncientDataByType(args.head).getClass
          val ancientDataArray = lang.reflect.Array.newInstance(sampleClass,len)

          0 until len foreach {i=>
            lang.reflect.Array.set(ancientDataArray,i,createAncientDataByType(args.head).fromStreamReader(dataSource,encoding))
          }
          field.set(ancientDataArray)
        case other =>
          throw new IllegalArgumentException("type is not supported "+symbol)
      }
    }

    this
  }
  def fromByteArray(data: Array[Byte],encoding:Charset=AncientConstants.UTF8_ENCODING):this.type = {
    fromStreamReader(ChannelBuffers.wrappedBuffer(data),encoding)
  }
  def toByteArray:Array[Byte]={
    val data = ChannelBuffers.buffer(getDataSize)
    writeToStreamWriter(data).array()
  }
}


/*
trait Model
object Model {
  implicit class StreamSupport[M <: Model](val model: M) extends AnyVal {
    def getDataSize: Int = macro AncientDataMacroDefine.getDataSizeImpl[M, Model, IgnoreTransfer, Length]

    def writeToStreamWriter[T <: StreamWriter](dataSink: T): T = macro AncientDataMacroDefine.writeStream[M, T, Model, IgnoreTransfer, Length]

    def fromStreamReader[T <: StreamReader](dataSource: T): M = macro AncientDataMacroDefine.readStream[M, T, Model, IgnoreTransfer, Length]

    def readBytesFromStreamReader(dataSource: StreamReader, len: Int): Array[Byte] = dataSource.readByteArray(len)
  }
}
*/

trait AncientData extends ScalaReflect{}
//trait AncientData extends Model

