package nirvana.hall.c.services

import java.lang
import java.nio.charset.Charset

import nirvana.hall.c.services.AncientData._
import nirvana.hall.c.services.AncientDataTermValueProcessor._

import scala.language.experimental.macros
import scala.language.reflectiveCalls
import scala.reflect.runtime._
import scala.reflect.runtime.universe._

/**
 * Ancient data term value processor
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-03-01
 */
object AncientDataTermValueProcessor{
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
    if(zeroLength<0)
      throw new IllegalStateException("length define is %s ,but data length is %s".format(length,bytesLength))
    if(bytes != null) {
      dataSink.writeBytes(bytes)
    }
    dataSink.writeZero(zeroLength)
  }
  def createAncientDataByType(t: universe.Type): AncientData = {
    val classType = t.typeSymbol.asClass
    val constructor = classType.primaryConstructor.asMethod
    AncientData.mirror
      .reflectClass(classType)
      .reflectConstructor(constructor)()
      .asInstanceOf[AncientData]
  }
}

/**
 * value manipulation
 * @param instanceMirror instance mirror
 * @param term term symbol
 */
case class ValueManipulation(instanceMirror: InstanceMirror,term:TermSymbol){
  //field
  private lazy val field = instanceMirror.reflectField(term)
  def get:Any = field.get
  def set(value:Any) = field.set(value)
}

/**
 * process term value.
 * provide calculate length
 */
sealed trait AncientDataTermValueProcessor{
  val term:TermSymbol
  def computeLength(valueManipulation: ValueManipulation,length:Option[Int]):Int
  def writeToStreamWriter(stream:StreamWriter,valueManipulation: ValueManipulation,length: Option[Int],encoding:Charset):Unit
  def fromStreamReader(dataSource: StreamReader, valueManipulation: ValueManipulation, length:Option[Int],encoding: Charset):Unit
}

/**
 * byte processor ancient data
 * @param term field term
 */
class ByteProcessor(override val term:TermSymbol) extends AncientDataTermValueProcessor{
  override def computeLength(value: ValueManipulation, length: Option[Int]): Int= throwExceptionIfLengthGTZeroOrGet(term,length,1)
  override def writeToStreamWriter(stream: StreamWriter, valueManipulation: ValueManipulation, length: Option[Int],encoding:Charset): Unit = {
    stream.writeByte(valueManipulation.get.asInstanceOf[Byte])
  }
  override def fromStreamReader(dataSource: StreamReader, valueManipulation: ValueManipulation, length:Option[Int],encoding: Charset): Unit = {
    valueManipulation.set(dataSource.readByte())
  }
}
class ShortProcessor(val term:TermSymbol) extends AncientDataTermValueProcessor{
  override def computeLength(value: ValueManipulation, length: Option[Int]): Int= throwExceptionIfLengthGTZeroOrGet(term,length,2)
  override def writeToStreamWriter(stream: StreamWriter, valueManipulation: ValueManipulation, length: Option[Int],encoding:Charset): Unit = {
    stream.writeShort(valueManipulation.get.asInstanceOf[Short])
  }
  override def fromStreamReader(dataSource: StreamReader, valueManipulation: ValueManipulation, length:Option[Int],encoding: Charset): Unit = {
    valueManipulation.set(dataSource.readShort())
  }
}
class IntProcessor(val term:TermSymbol) extends AncientDataTermValueProcessor{
  override def computeLength(value: ValueManipulation, length: Option[Int]): Int= throwExceptionIfLengthGTZeroOrGet(term,length,4)
  override def writeToStreamWriter(stream: StreamWriter, valueManipulation: ValueManipulation, length: Option[Int],encoding:Charset): Unit = {
    stream.writeInt(valueManipulation.get.asInstanceOf[Int])
  }
  override def fromStreamReader(dataSource: StreamReader, valueManipulation: ValueManipulation, length:Option[Int],encoding: Charset): Unit = {
    valueManipulation.set(dataSource.readInt())
  }
}
class LongProcessor(val term:TermSymbol) extends AncientDataTermValueProcessor{
  override def computeLength(value: ValueManipulation, length: Option[Int]): Int= throwExceptionIfLengthGTZeroOrGet(term,length,8)
  override def writeToStreamWriter(stream: StreamWriter, valueManipulation: ValueManipulation, length: Option[Int],encoding:Charset): Unit = {
    stream.writeLong(valueManipulation.get.asInstanceOf[Long])
  }
  override def fromStreamReader(dataSource: StreamReader, valueManipulation: ValueManipulation, length:Option[Int],encoding: Charset): Unit = {
    valueManipulation.set(dataSource.readLong())
  }
}
class StringProcessor(val term:TermSymbol) extends AncientDataTermValueProcessor{
  override def computeLength(value: ValueManipulation, length: Option[Int]): Int= returnLengthOrThrowException(term,length)
  override def writeToStreamWriter(stream: StreamWriter, valueManipulation: ValueManipulation, length: Option[Int],encoding:Charset): Unit = {
    val valueLength = computeLength(valueManipulation,length)
    val value = valueManipulation.get
    if(value == null) writeBytes(stream,null,valueLength) else writeBytes(stream,value.asInstanceOf[String].getBytes(encoding.name()),valueLength)
  }
  override def fromStreamReader(dataSource: StreamReader, valueManipulation: ValueManipulation, length:Option[Int],encoding: Charset): Unit = {
    val bytes = dataSource.readByteArray(computeLength(valueManipulation,length))
    valueManipulation.set(new String(bytes,encoding).trim)
  }
}
class ByteArrayProcessor(val term:TermSymbol) extends AncientDataTermValueProcessor{
  override def computeLength(value: ValueManipulation, length: Option[Int]): Int= returnLengthOrThrowException(term,length)

  override def writeToStreamWriter(stream: StreamWriter, valueManipulation: ValueManipulation, length: Option[Int], encoding: Charset): Unit = {
    writeBytes(stream,valueManipulation.get.asInstanceOf[Array[Byte]],computeLength(valueManipulation,length))
  }
  override def fromStreamReader(dataSource: StreamReader, valueManipulation: ValueManipulation, length:Option[Int],encoding: Charset): Unit = {
    val bytes = dataSource.readByteArray(computeLength(valueManipulation,length))
    valueManipulation.set(bytes)
  }
}
class AncientDataProcessor(val term:TermSymbol,tpe:Type) extends AncientDataTermValueProcessor{
  private lazy val typeInstance = createAncientDataByType(tpe)
  private lazy val typeClass = typeInstance.getClass
  private lazy val typeLength= typeInstance.getDataSize
  override def computeLength(valueManipulation: ValueManipulation, length: Option[Int]): Int = {
    val value = valueManipulation.get
    val ancientDataSize =
      if(value == null){
        typeLength
      }else{
        value.asInstanceOf[AncientData].getDataSize
      }
    throwExceptionIfLengthGTZeroOrGet(term,length,ancientDataSize)
  }

  override def writeToStreamWriter(stream: StreamWriter, valueManipulation: ValueManipulation, length: Option[Int], encoding: Charset): Unit = {
    val value  = valueManipulation.get
    if(value == null) {
      stream.writeZero(typeLength)
    }else{
      value.asInstanceOf[AncientData].writeToStreamWriter(stream,encoding)
    }
  }

  override def fromStreamReader(dataSource: StreamReader, valueManipulation: ValueManipulation, length: Option[Int], encoding: Charset): Unit = {
    val ancientData = typeClass.newInstance()
    ancientData.fromStreamReader(dataSource,encoding)
    valueManipulation.set(ancientData)
  }
}
class AncientDataArrayProcessor(val term:TermSymbol,tpe:Type) extends AncientDataTermValueProcessor{
  private lazy val typeInstance = createAncientDataByType(tpe)
  private lazy val typeClass = typeInstance.getClass
  private lazy val typeLength = typeInstance.getDataSize
  override def computeLength(valueManipulation: ValueManipulation, length: Option[Int]): Int = {
    val value = valueManipulation.get
    val len = returnLengthOrThrowException(term,length)
    if(value==null){
      len * typeLength
    }else {
      var finalLength = 0
      var zeroLen = typeLength * len
      val ancientDataArray = value.asInstanceOf[Array[AncientData]].filterNot(_ == null)
      ancientDataArray.foreach { x =>
        finalLength += x.getDataSize
        zeroLen -= typeLength
      }
      finalLength += zeroLen

      finalLength
    }
  }

  override def writeToStreamWriter(stream: StreamWriter, valueManipulation: ValueManipulation, length: Option[Int], encoding: Charset): Unit = {
    val len = returnLengthOrThrowException(term,length)
    var zeroLen = typeLength * len
    val value = valueManipulation.get
    if(value != null){
      value.asInstanceOf[Array[AncientData]].foreach{x=>
        if(x == null)
          stream.writeZero(typeLength)
        else
          x.writeToStreamWriter(stream,encoding)
        zeroLen -= typeLength
      }
    }
    stream.writeZero(zeroLen)
  }

  override def fromStreamReader(dataSource: StreamReader, valueManipulation: ValueManipulation, length: Option[Int], encoding: Charset): Unit = {
    val len = returnLengthOrThrowException(term,length)
    val ancientDataArray = lang.reflect.Array.newInstance(typeClass,len)
    0 until len foreach {i=>
      lang.reflect.Array.set(ancientDataArray,i,typeClass.newInstance().fromStreamReader(dataSource,encoding))
    }
    valueManipulation.set(ancientDataArray)
  }
}


