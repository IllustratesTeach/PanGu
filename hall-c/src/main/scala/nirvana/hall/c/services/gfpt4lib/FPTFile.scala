package nirvana.hall.c.services.gfpt4lib

import java.io.InputStream
import java.nio.charset.Charset

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.AncientData.{StreamReader, StreamWriter}
import nirvana.hall.c.services.gfpt4lib.FPT3File.FPT3File

import scala.language.reflectiveCalls
import scala.reflect._
import nirvana.hall.c.services.AncientData._

/**
 * fpt file
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-27
 */
object FPTFile {
  val FS:Byte= 28
  val GS:Byte= 29
  val FPT_FLAG="FPT"
  val FPT_V3="03"
  val FPT_V4="04"
  def parseFromInputStream(stream:InputStream, encoding: Charset = AncientConstants.UTF8_ENCODING): Either[FPT3File,FPT4File]= {
    val streamReader:StreamReader = stream
    val head = new FPTHead
    streamReader.markReaderIndex()
    head.fromStreamReader(stream,encoding)
    streamReader.resetReaderIndex()

    if(head.flag != FPT_FLAG)
      throw new IllegalArgumentException("FPT expected.")
    head.majorVersion match{
      case FPT_V3=>
        Left(new FPT3File().fromStreamReader(streamReader))
      case FPT_V4 =>
        Right(new FPT4File().fromStreamReader(streamReader))
      case other =>
        throw new UnsupportedOperationException(other+" unsupported,only 03 or 04.")
    }
  }
  def readLogic[T <:AncientData : ClassTag](strNum:String,dataSource:StreamReader,encoding:Charset): Array[T]={
    val num = if(strNum == null || strNum.isEmpty) 0 else strNum.toInt
    val seq = 0 until num map {i =>
      val value = classTag[T].runtimeClass.newInstance().asInstanceOf[T].fromStreamReader(dataSource, encoding)
      value
    }
    seq.toArray
  }
  class FPTHead extends AncientData{
    @Length(3)
    var flag:String = _
    @Length(2)
    var majorVersion:String = _
    @Length(2)
    var minorVersion:String = _
  }
  class LogicHeadV4 extends AncientData{
    @Length(8)
    var fileLength: String = _
    @Length(2)
    var dataType:String = _
  }
  class LogicHeadV3 extends AncientData{
    @Length(8)
    var fileLength: String = _
    @Length(1)
    var dataType:String = _
  }
  trait DynamicFingerData extends AncientData{
    @IgnoreTransfer
    private var logicEnd: Byte = FPTFile.FS // GS
    protected def getFingerDataCount:Int
    override def getDataSize: Int = {
      var count = super.getDataSize
      if (getFingerDataCount == 0)
        count += 1
      count
    }

    override def writeToStreamWriter[T](stream: T)(implicit converter: (T) => StreamWriter): T = {
      super.writeToStreamWriter(stream)
      if (getFingerDataCount == 0){
        val dataSink = converter(stream)
        dataSink.writeByte(logicEnd)
      }
      stream
    }

    override def fromStreamReader(dataSource: StreamReader, encoding: Charset = AncientConstants.UTF8_ENCODING): this.type = {
      super.fromStreamReader(dataSource, encoding)
      if (getFingerDataCount == 0) {
        logicEnd = dataSource.readByte()
      }
      this
    }
  }
}

