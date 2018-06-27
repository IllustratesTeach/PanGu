package nirvana.hall.c.services

import java.io.{EOFException, InputStream}
import java.nio.{ByteOrder, ByteBuffer}
import java.nio.charset.Charset
import java.util.concurrent.ConcurrentHashMap
import javax.imageio.stream.ImageInputStream

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.annotations.{IgnoreTransfer, Length, LengthRef}
import nirvana.hall.c.services.AncientData._
import org.jboss.netty.buffer.{ChannelBuffer, ChannelBuffers}
import org.slf4j.LoggerFactory
import org.xsocket.IDataSource

import scala.annotation.tailrec
import scala.language.experimental.macros
import scala.language.reflectiveCalls
import scala.reflect.runtime.universe
import scala.reflect.runtime.universe._
import scala.reflect.runtime.universe.definitions._
import scala.util.control.NonFatal


/**
 * support to serialize/deserialize data
 * TODO using byte transformation to convert data
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-29
 */
object AncientData extends AncientDataStreamWrapper{
  val logger = LoggerFactory getLogger getClass
  //global scala reflect mirror
  lazy val mirror = universe.runtimeMirror(Thread.currentThread().getContextClassLoader)
  lazy val STRING_CLASS = typeOf[String]
  lazy val reflectCaches = new ConcurrentHashMap[Class[_],Seq[(AncientDataTermValueProcessor,Option[Either[Int,TermSymbol]])]]()
  type FieldDataType = (AncientDataTermValueProcessor,Option[Either[Int,TermSymbol]])


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
    if(n < 0 )
      throw new EOFException("No more data in stream")
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
  class AncientDataException(message:String) extends RuntimeException(message){
    def this(message:String,cause:Throwable){
      this(message)
      super.initCause(cause)
    }
  }
}

trait AncientData{
  private lazy val instanceMirror = AncientData.mirror.reflect(this)
//  private val clazzSymbol = typeOf[this.type].typeSymbol
  private lazy val clazzSymbol = instanceMirror.symbol
  private lazy val clazzType = clazzSymbol.asType.toType

  /**
   * calculate data size and return.
 *
   * @return data size
   */
  def getDataSize:Int= {
    internalProcessField
      .map{case (processor,lengthDef) =>
      tryIgnoreAncientDataException[Int](processor.term){
        processor.computeLength(ValueManipulation(instanceMirror, processor.term), findLength(lengthDef))
      }
    }.sum
  }

  /**
    * 通过给定的field的名称来得到对应字段在类中数据的 偏移量 和 本字段的占用字节数
 *
    * @param fieldName 待查询的字段名称
    * @return 偏移量和字节数
    */
  def findFieldOffsetAndLength(fieldName:String):(Int,Int)={
    @tailrec
    def loopFields(list:List[FieldDataType],
                   offset:Int):(Int,Int)= list match {
      case Nil =>
        throw new AncientDataException("field [%s] not found".format(fieldName))
      case (processor, lengthDef) :: tail =>
        val termLength = tryIgnoreAncientDataException[Int](processor.term) {
          processor.computeLength(ValueManipulation(instanceMirror, processor.term), findLength(lengthDef))
        }
        if (processor.term.name.decodedName.toString.trim == fieldName) {
          (offset, termLength)
        } else {
          loopFields(tail, offset + termLength)
        }
    }
    val fields = internalProcessField.toList
    loopFields(fields,0)
  }

  /**
   * serialize to channel buffer
 *
   * @param stream netty channel buffer
   */
  def writeToStreamWriter[T](stream:T,encoding:Charset=AncientConstants.UTF8_ENCODING)(implicit converter:T=> StreamWriter): T= {
    val dataSink = converter(stream)
    internalProcessField.foreach{
      case (processor,lengthDef) =>
        tryIgnoreAncientDataException(processor.term) {
          processor.writeToStreamWriter(dataSink, ValueManipulation(instanceMirror, processor.term), findLength(lengthDef), encoding)
        }
    }
    stream
  }
  protected def readBytesFromStreamReader(dataSource:StreamReader,len:Int): Array[Byte]= dataSource.readByteArray(len)
  /**
   * convert channel buffer data as object
 *
   * @param dataSource netty channel buffer
   */
  def fromStreamReader(dataSource: StreamReader,encoding:Charset=AncientConstants.UTF8_ENCODING): this.type ={
    internalProcessField.foreach{
      case (processor,lengthDef) =>
        tryIgnoreAncientDataException(processor.term) {
          processor.fromStreamReader(dataSource, ValueManipulation(instanceMirror, processor.term), findLength(lengthDef), encoding)
        }
    }
    this
  }
  def fromByteArray(data: Array[Byte],encoding:Charset=AncientConstants.UTF8_ENCODING, byteOrder:ByteOrder=ByteOrder.BIG_ENDIAN):this.type = {
    fromStreamReader(ChannelBuffers.wrappedBuffer(byteOrder, data),encoding)
  }
  def toByteArray(encoding:Charset=AncientConstants.UTF8_ENCODING,byteOrder:ByteOrder=ByteOrder.BIG_ENDIAN):Array[Byte]={
    val data = ChannelBuffers.buffer(byteOrder,getDataSize)
    writeToStreamWriter(data,encoding).array()
  }
  /**
   * create term processor
   */
  private def createTermProcessorByType(term:TermSymbol,tpe:Type):AncientDataTermValueProcessor={
    tpe match {
      case t if t<:< ByteTpe =>  new ByteProcessor(term)
      case t if t<:< ShortTpe | t <:< CharTpe => new ShortProcessor(term)
      case t if t <:< IntTpe => new IntProcessor(term)
      case t if t <:< LongTpe => new LongProcessor(term)
      case AncientData.STRING_CLASS => new StringProcessor(term)
      case t if t <:< typeOf[AncientData] => new AncientDataProcessor(term,t)
      case t  if t =:= typeOf[Array[Byte]] => new ByteArrayProcessor(term)
      case TypeRef(pre,sym,args) if sym == typeOf[Array[AncientData]].typeSymbol => //Array of ScalaReflect
        new AncientDataArrayProcessor(term,args.head)
      case other =>
        throw new AncientDataException("type is not supported "+other+" for:"+term)
    }
  }

  private def findReferenceLength(termSymbol:TermSymbol): Int={
    val value = termSymbol match{
        case m:MethodSymbol =>
          instanceMirror.reflectMethod(m).apply()
        case other =>
          instanceMirror.reflectField(termSymbol).get
      }
    if (value == null) {
      0
    } else {
      val stringValue = value.toString.trim()
      if (stringValue.isEmpty) 0 else  {
        try{
          stringValue.toInt
        } catch{
          case e:NumberFormatException =>
            logger.warn("%s invalid number".format(termSymbol.name),e)
            0
        }
      }
    }
  }
  private def findLength(lengthDef:Option[Either[Int,TermSymbol]]):Option[Int]={
    lengthDef match {
      case Some(Right(refTerm))=>
        Some(findReferenceLength(refTerm))
      case Some(Left(length))=>
        Some(length)
      case None=>
        None
    }
  }
  private def tryIgnoreAncientDataException[T](term:TermSymbol)(fun: =>T): T={
    try {
      fun
    }catch {
      case e: AncientDataException =>
        throw new AncientDataException(term + "->" + e.getMessage, e)
      case NonFatal(e) =>
        throw new AncientDataException(term.toString + "," + e.toString, e)
    }
  }
  private def internalProcessField:Seq[FieldDataType]={
    var members = reflectCaches.get(getClass)
    if(members == null) {
      members = clazzType.members.toStream
        .filter(_.isTerm)
        .filter(_.asTerm.isVar)
        .filterNot(_.annotations.exists(typeOf[IgnoreTransfer] =:= _.tree.tpe))
        .toSeq.reverse // <----- must be reversed
        .map { m =>
        //find @Length annotation and get value
        val lengthAnnotation = m.annotations.find(typeOf[Length] =:= _.tree.tpe)
        val length:Option[Either[Int,TermSymbol]] = lengthAnnotation.map(_.tree).map {
          case Apply(fun, AssignOrNamedArg(name, Literal(Constant(value))) :: Nil) =>
            Left(value.asInstanceOf[Int])
        }

        try {
          //find @LengthRef annotation and get value
          val lengthRefAnnotation = m.annotations.find(typeOf[LengthRef] =:= _.tree.tpe)
          val lengthRef:Option[Either[Int,TermSymbol]] = lengthRefAnnotation.map(_.tree).map {
            case Apply(fun, AssignOrNamedArg(name, Literal(Constant(refFieldName))) :: Nil) =>

              val fieldName = refFieldName.asInstanceOf[String]
              val termSymbol = clazzType.decl(TermName(fieldName)).asTerm
              Right(termSymbol)
          }
          if (length.isDefined && lengthRef.isDefined)
            throw new AncientDataException("@Length and @LengthRef both defined at " + m)
          //val length = lengthAnnotation.map(_.tree.children.tail.head.children(1).asInstanceOf[Literal].value.value.asInstanceOf[Int]).sum

          (createTermProcessorByType(m.asTerm,m.asTerm.typeSignature), if (length.isDefined) length else lengthRef)
        } catch {
          case e: AncientDataException =>
            throw new AncientDataException(m + "->" + e.getMessage, e)
          case NonFatal(e) =>
            throw new AncientDataException(m.toString + "," + e.toString, e)
        }
      }
      reflectCaches.putIfAbsent(getClass,members)
    }

    members
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


