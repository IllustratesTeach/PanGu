package nirvana.hall.c.services.gfpt4lib

import nirvana.hall.c.annotations.{LengthRef, Length, IgnoreTransfer}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.AncientData._
import nirvana.hall.c.services.gfpt4lib.FPTFile.{FPTHead, LogicHeadV3}

import scala.collection.mutable
import scala.language.reflectiveCalls

/**
 * fpt3 file
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-27
 */
class FPT3File extends AncientData{
  var head:FPTHead = new FPTHead
  var logic1Rec = new Logic1Rec
  @IgnoreTransfer
  var logic2Recs:Array[Logic2Rec] =  _
  @IgnoreTransfer
  var logic3Recs:Array[Logic3Rec] =  _

  /**
   * calculate data size and return.
   * @return data size
   */
  override def getDataSize: Int = {
    var count = super.getDataSize
    if(logic2Recs != null)
      logic2Recs.foreach(count += _.getDataSize)
    if(logic3Recs != null)
      logic3Recs.foreach(count += _.getDataSize)

    count
  }

  /**
   * serialize to channel buffer
   * @param stream netty channel buffer
   */
  override def writeToStreamWriter[T](stream: T)(implicit converter: (T) => StreamWriter): T = {
    super.writeToStreamWriter(stream)
    if(logic2Recs!=null)
      logic2Recs.foreach(_.writeToStreamWriter(stream))
    if(logic3Recs!=null)
      logic3Recs.foreach(_.writeToStreamWriter(stream))

    stream
  }

  /**
   * convert channel buffer data as object
   * @param dataSource netty channel buffer
   */
  override def fromStreamReader(dataSource: StreamReader): FPT3File.this.type = {
    super.fromStreamReader(dataSource)
    dataSource.markReaderIndex()
    val head = new LogicHeadV3
    head.fromStreamReader(dataSource)
    dataSource.resetReaderIndex()

    val logic2Buffer = mutable.Buffer[Logic2Rec]()
    val logic3Buffer = mutable.Buffer[Logic3Rec]()
    head.dataType match{
      case FPTFile.V3_LOGIC_DATA_TYPE_2 =>
        logic2Buffer += new Logic2Rec().fromStreamReader(dataSource)
      case FPTFile.V3_LOGIC_DATA_TYPE_3 =>
        logic3Buffer += new Logic3Rec().fromStreamReader(dataSource)
    }

    logic2Recs = logic2Buffer.toArray
    logic3Recs = logic3Buffer.toArray

    this
  }
}

class Logic1Rec extends AncientData{
  @Length(12)
  var fileLength: String = _
  @Length(1)
  var dataType:String = "1"
  @Length(6)
  var tpCount: String =  _
  @Length(6)
  var lpCount: String = _
  @Length(14)
  var sendTime: String = _
  @Length(12)
  var receiveUnitCode: String = _
  @Length(12)
  var sendUnitCode: String = _
  @Length(70)
  var sendUnitName: String = _
  @Length(30)
  var sender: String = _
  /**
   * 发送单位系统类型
   * 1900 东方金指
   * 1300 北大高科
   * 1700 北京海鑫
   * 1800 小日本NEC
   * 1200 北京邮电大学
   * 1100 北京刑科所
   */
  @Length(4)
  var sendUnitSystemType: String = _
  @Length(10)
  var sid: String = _
  @Length(512)
  var remark: String = _
  var fs:Byte = FPTFile.FS //FS
}
class Logic2Rec extends AncientData{
  var head  = new LogicHeadV3()
  //head.dataType = "2"

  @Length(6)
  var index: String = _
  @Length(4)
  var systemType: String = _
  @Length(23)
  var caseId: String = _
  @Length(20)
  var cardId: String = _
  @Length(6)
  var caseClass1Code: String = _
  @Length(6)
  var caseClass2Code: String = _
  @Length(6)
  var caseClass3Code: String = _

  @Length(8)
  var occurDate: String = _
  @Length(1)
  var assistLevel: String = _
  @Length(70)
  var occurPlace: String = _
  @Length(12)
  var extractUnitCode: String = _
  @Length(70)
  var extractUnitName: String = _
  @Length(30)
  var extractor: String = _
  @Length(6)
  var suspiciousArea1Code: String = _
  @Length(6)
  var suspiciousArea2Code: String = _
  @Length(6)
  var suspiciousArea3Code: String = _
  @Length(10)
  var amount: String = _
  @Length(512)
  var remark: String = _
  @Length(2)
  var fingerCount: String = _
  @Length(2)
  var sendFingerCount: String = _
  @LengthRef("sendFingerCount")
  var fingers:Array[FingerLData] = _
  @IgnoreTransfer
  var logicEnd:Byte= FPTFile.FS // GS

  override def getDataSize: Int = {
    var count = super.getDataSize
    if(fingers == null)
      count += 1

    count
  }

  override def writeToStreamWriter[T](stream: T)(implicit converter: (T) => StreamWriter): T = {
    super.writeToStreamWriter(stream)
    if(fingers == null){
      val dataSink = converter(stream)
      dataSink.writeByte(logicEnd)
    }
    stream
  }

  override def fromStreamReader(dataSource: StreamReader): this.type = {
    super.fromStreamReader(dataSource)

    if(sendFingerCount.isEmpty || sendFingerCount.toInt ==0){
      logicEnd = dataSource.readByte()
    }

    this
  }
}

class FingerTData extends AncientData{
  @Length(6)
  var dataLength: Array[Byte]= _
  @Length(2)
  var sendNo: String = _
  @Length(2)
  var fgp: String = _
  @Length(1)
  var extractMethod: String = _

  @Length(1)
  var pattern1: String = _
  @Length(1)
  var pattern2: String = _

  @Length(5)
  var fingerDirection: String = _
  @Length(14)
  var centerPoint: String = _
  @Length(14)
  var subCenterPoint: String = _
  @Length(14)
  var leftTriangle: String = _
  @Length(14)
  var rightTriangle: String = _
  @Length(3)
  var featureCount: String = _
  @Length(1800)
  var feature: Array[Byte] = _
  @Length(4)
  var customInfoLength:String = "0"
  @LengthRef("customInfoLength")
  var customInfo : Array[Byte] = _
  @Length(3)
  var imgHorizontalLength: String = _
  @Length(3)
  var imgVerticalLength: String = _
  @Length(3)
  var dpi: String = _
  @Length(4)
  var imgCompressMethod: String = _
  @Length(6)
  var imgDataLength: String = "0"
  @LengthRef("imgDataLength")
  var imgData: Array[Byte] = _
  var fingerDataEnd:Byte= FPTFile.GS // GS
}
class FingerLData extends AncientData{
  @Length(6)
  var dataLength: String = _
  @Length(2)
  var sendNo: String = _
  @Length(2)
  var fingerNo: String = _
  @Length(20)
  var fingerId: String = _
  @Length(30)
  var remainPlace: String = _
  @Length(10)
  var fgp: String = _
  @Length(1)
  var ridgeColor: String = _
  @Length(2)
  var mittensBegNo: String = _
  @Length(2)
  var mittensEndNo: String = _
  @Length(1)
  var extractMethod: String = _
  @Length(7)
  var pattern: String = _
  @Length(5)
  var fingerDirection: String = _
  @Length(14)
  var centerPoint: String = _
  @Length(14)
  var subCenterPoint: String = _
  @Length(14)
  var leftTriangle: String = _
  @Length(14)
  var rightTriangle: String = _
  @Length(3)
  var featureCount: String = _
  @Length(1800)
  var feature: Array[Byte] = _
  @Length(4)
  var customInfoLength:String = "0"
  @LengthRef("customInfoLength")
  var customInfo : Array[Byte] = _
  @Length(3)
  var imgHorizontalLength: String = _
  @Length(3)
  var imgVerticalLength: String = _
  @Length(3)
  var dpi: String = _
  @Length(4)
  var imgCompressMethod: String = _
  @Length(6)
  var imgDataLength: String = "0"
  @LengthRef("imgDataLength")
  var imgData: Array[Byte] = _
  var fingerDataEnd:Byte= FPTFile.GS // GS
}

class Logic3Rec extends AncientData{
  var head  = new LogicHeadV3()
  head.dataType = "3"
  @Length(6)
  var index: String = _
  @Length(4)
  var systemType: String = null
  @Length(23)
  var personId: String = null
  @Length(20)
  var cardId: String = null
  @Length(30)
  var personName: String = null
  @Length(30)
  var alias: String = null
  @Length(1)
  var gender: String = null
  @Length(8)
  var birthday: String = null
  @Length(18)
  var idCardNo: String = null
  @Length(6)
  var door: String = null
  @Length(70)
  var doorDetail: String = null
  @Length(6)
  var address: String = null
  @Length(70)
  var addressDetail: String = null
  @Length(20)
  var category: String = null
  @Length(6)
  var caseClass1Code: String = null
  @Length(6)
  var caseClass2Code: String = null
  @Length(6)
  var caseClass3Code: String = null
  @Length(12)
  var gatherUnitCode: String = null
  @Length(70)
  var gatherUnitName: String = null
  @Length(30)
  var gatherName: String = null
  @Length(8)
  var gatherDate: String = null
  @Length(512)
  var remark: String = null
  @Length(2)
  var sendFingerCount: String = _
  @LengthRef("sendFingerCount")
  var fingers:Array[FingerTData] = _
  @IgnoreTransfer
  var logicEnd:Byte= FPTFile.FS // GS

  override def getDataSize: Int = {
    var count = super.getDataSize
    if(fingers == null)
      count += 1
    count
  }

  override def writeToStreamWriter[T](stream: T)(implicit converter: (T) => StreamWriter): T = {
    super.writeToStreamWriter(stream)
    if(fingers == null){
      val dataSink = converter(stream)
      dataSink.writeByte(logicEnd)
    }

    stream
  }

  override def fromStreamReader(dataSource: StreamReader): this.type = {
    super.fromStreamReader(dataSource)

    if(sendFingerCount.isEmpty || sendFingerCount.toInt == 0){
      logicEnd = dataSource.readByte()
    }

    this
  }
}
