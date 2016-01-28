package nirvana.hall.c.services.gfpt4lib

import java.nio.charset.Charset

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.AncientData.{StreamReader, StreamWriter}

import scala.language.reflectiveCalls

/**
 * fpt file
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-27
 */
object FPTFile {
  val FS:Byte= 28
  val GS:Byte= 29

  val V3_LOGIC_DATA_TYPE_2="2"
  val V3_LOGIC_DATA_TYPE_3="3"
  class FPTHead extends AncientData{
    @Length(3)
    var flag:String = _
    @Length(2)
    var majorVersion:String = _
    @Length(2)
    var minorVersion:String = _
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

