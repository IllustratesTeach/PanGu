package nirvana.hall.c.services

import java.lang
import java.nio.charset.Charset

import nirvana.hall.c.services.AncientData._
import nirvana.hall.c.services.TermValueProcessor._

import scala.language.experimental.macros
import scala.language.reflectiveCalls
import scala.reflect.runtime.universe._

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-03-01
 */
object TermValueProcessor{
  //println("process term "+term+" length:"+length)
  def returnLengthOrThrowException(term:TermSymbol,length:Option[Int]):Int={
    if(length.isEmpty)
      throw new AncientDataException("@Length not defined at "+term)
    length.get
  }
  def throwExceptionIfLengthGTZeroOrGet[T](term:TermSymbol,length:Option[Int],value:T): T={
    if(length.isDefined)
      throw new AncientDataException("@Length wrong defined at "+term)
    value
  }
  def writeBytes(dataSink:StreamWriter,bytes:Array[Byte],length:Int): Unit ={
    //println(length)
    val bytesLength = if(bytes == null) 0 else bytes.length
    val zeroLength = length - bytesLength
    if(bytes != null) {
      dataSink.writeBytes(bytes)
    }
    dataSink.writeZero(zeroLength)
  }
}

  case class ValueManipulate(instanceMirror: InstanceMirror,term:TermSymbol){
    private lazy val field = instanceMirror.reflectField(term)
    def get:Any = field.get
    def set(value:Any) = field.set(value)
  }
  sealed trait TermValueProcessor{
    val term:TermSymbol
    def calLength(valueManipulate: ValueManipulate,length:Option[Int]):Int
    def writeToStreamWriter(stream:StreamWriter,valueManipulate: ValueManipulate,length: Option[Int],encoding:Charset):Unit
    def fromStreamReader(dataSource: StreamReader, valueManipulate: ValueManipulate, length:Option[Int],encoding: Charset):Unit
  }
  class ByteProcessor(override val term:TermSymbol) extends TermValueProcessor{
    override def calLength(value: ValueManipulate, length: Option[Int]): Int= throwExceptionIfLengthGTZeroOrGet(term,length,1)
    override def writeToStreamWriter(stream: StreamWriter, valueManipulate: ValueManipulate, length: Option[Int],encoding:Charset): Unit = {
      stream.writeByte(valueManipulate.get.asInstanceOf[Byte])
    }
    override def fromStreamReader(dataSource: StreamReader, valueManipulate: ValueManipulate, length:Option[Int],encoding: Charset): Unit = {
      valueManipulate.set(dataSource.readByte())
    }
  }
  class ShortProcessor(val term:TermSymbol) extends TermValueProcessor{
    override def calLength(value: ValueManipulate, length: Option[Int]): Int= throwExceptionIfLengthGTZeroOrGet(term,length,2)
    override def writeToStreamWriter(stream: StreamWriter, valueManipulate: ValueManipulate, length: Option[Int],encoding:Charset): Unit = {
      stream.writeShort(valueManipulate.get.asInstanceOf[Short])
    }
    override def fromStreamReader(dataSource: StreamReader, valueManipulate: ValueManipulate, length:Option[Int],encoding: Charset): Unit = {
      valueManipulate.set(dataSource.readShort())
    }
  }
  class IntProcessor(val term:TermSymbol) extends TermValueProcessor{
    override def calLength(value: ValueManipulate, length: Option[Int]): Int= throwExceptionIfLengthGTZeroOrGet(term,length,4)
    override def writeToStreamWriter(stream: StreamWriter, valueManipulate: ValueManipulate, length: Option[Int],encoding:Charset): Unit = {
      stream.writeInt(valueManipulate.get.asInstanceOf[Int])
    }
    override def fromStreamReader(dataSource: StreamReader, valueManipulate: ValueManipulate, length:Option[Int],encoding: Charset): Unit = {
      valueManipulate.set(dataSource.readInt())
    }
  }
  class LongProcessor(val term:TermSymbol) extends TermValueProcessor{
    override def calLength(value: ValueManipulate, length: Option[Int]): Int= throwExceptionIfLengthGTZeroOrGet(term,length,8)
    override def writeToStreamWriter(stream: StreamWriter, valueManipulate: ValueManipulate, length: Option[Int],encoding:Charset): Unit = {
      stream.writeLong(valueManipulate.get.asInstanceOf[Long])
    }
    override def fromStreamReader(dataSource: StreamReader, valueManipulate: ValueManipulate, length:Option[Int],encoding: Charset): Unit = {
      valueManipulate.set(dataSource.readLong())
    }
  }
  class StringProcessor(val term:TermSymbol) extends TermValueProcessor{
    override def calLength(value: ValueManipulate, length: Option[Int]): Int= returnLengthOrThrowException(term,length)
    override def writeToStreamWriter(stream: StreamWriter, valueManipulate: ValueManipulate, length: Option[Int],encoding:Charset): Unit = {
      val valueLength = calLength(valueManipulate,length)
      val value = valueManipulate.get
      if(value == null) writeBytes(stream,null,valueLength) else writeBytes(stream,value.asInstanceOf[String].getBytes(encoding.name()),valueLength)
    }
    override def fromStreamReader(dataSource: StreamReader, valueManipulate: ValueManipulate, length:Option[Int],encoding: Charset): Unit = {
      val bytes = dataSource.readByteArray(calLength(valueManipulate,length))
      valueManipulate.set(new String(bytes,encoding).trim)
    }
  }
  class ByteArrayProcessor(val term:TermSymbol) extends TermValueProcessor{
    override def calLength(value: ValueManipulate, length: Option[Int]): Int= returnLengthOrThrowException(term,length)

    override def writeToStreamWriter(stream: StreamWriter, valueManipulate: ValueManipulate, length: Option[Int], encoding: Charset): Unit = {
      writeBytes(stream,valueManipulate.get.asInstanceOf[Array[Byte]],calLength(valueManipulate,length))
    }
    override def fromStreamReader(dataSource: StreamReader, valueManipulate: ValueManipulate, length:Option[Int],encoding: Charset): Unit = {
      val bytes = dataSource.readByteArray(calLength(valueManipulate,length))
      valueManipulate.set(bytes)
    }
  }
  class AncientDataProcessor(val term:TermSymbol,tpe:Type) extends TermValueProcessor{
    override def calLength(valueManipulate: ValueManipulate, length: Option[Int]): Int = {
      val value = valueManipulate.get
      val ancientDataSize =
        if(value == null){
          createAncientDataByType(tpe).getDataSize
        }else{
          value.asInstanceOf[AncientData].getDataSize
        }
      throwExceptionIfLengthGTZeroOrGet(term,length,ancientDataSize)
    }

    override def writeToStreamWriter(stream: StreamWriter, valueManipulate: ValueManipulate, length: Option[Int], encoding: Charset): Unit = {
      val value  = valueManipulate.get
      if(value == null) {
        val len = createAncientDataByType(tpe).getDataSize
        stream.writeZero(len)
      }else{
        value.asInstanceOf[AncientData].writeToStreamWriter(stream)
      }
    }

    override def fromStreamReader(dataSource: StreamReader, valueManipulate: ValueManipulate, length: Option[Int], encoding: Charset): Unit = {
      val ancientData = createAncientDataByType(tpe)
      ancientData.fromStreamReader(dataSource,encoding)
      valueManipulate.set(ancientData)
    }
  }
  class AncientDataArrayProcessor(val term:TermSymbol,tpe:Type) extends TermValueProcessor{
    override def calLength(valueManipulate: ValueManipulate, length: Option[Int]): Int = {
      val value = valueManipulate.get
      val len = returnLengthOrThrowException(term,length)
      val type_len = createAncientDataByType(tpe).getDataSize
      if(value==null){
        len * type_len
      }else {
        var finalLength = 0
        var zeroLen = type_len * len
        val ancientDataArray = value.asInstanceOf[Array[AncientData]].filterNot(_ == null)
        ancientDataArray.foreach { x =>
          finalLength += x.getDataSize
          zeroLen -= type_len
        }
        finalLength += zeroLen

        finalLength
      }
    }

    override def writeToStreamWriter(stream: StreamWriter, valueManipulate: ValueManipulate, length: Option[Int], encoding: Charset): Unit = {
      val len = returnLengthOrThrowException(term,length)
      val type_len = createAncientDataByType(tpe).getDataSize
      var zeroLen = type_len * len
      val value = valueManipulate.get
      if(value != null){
        value.asInstanceOf[Array[AncientData]].foreach{x=>
          if(x == null)
            stream.writeZero(type_len)
          else
            x.writeToStreamWriter(stream,encoding)
          zeroLen -= type_len
        }
      }
      stream.writeZero(zeroLen)
    }

    override def fromStreamReader(dataSource: StreamReader, valueManipulate: ValueManipulate, length: Option[Int], encoding: Charset): Unit = {
      val len = returnLengthOrThrowException(term,length)
      val sampleClass = createAncientDataByType(tpe).getClass
      val ancientDataArray = lang.reflect.Array.newInstance(sampleClass,len)
      0 until len foreach {i=>
        lang.reflect.Array.set(ancientDataArray,i,sampleClass.newInstance().fromStreamReader(dataSource,encoding))
      }
      valueManipulate.set(ancientDataArray)
    }
  }


