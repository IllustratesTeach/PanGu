package nirvana.hall.c.services.gfpt4lib

import java.nio.charset.Charset

import nirvana.hall.c.annotations.{Length, LengthRef}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.AncientData.StreamReader
import nirvana.hall.c.services.gfpt4lib.FPTFile.{DynamicFingerData, FPTHead, FPTParseException}

import scala.language.reflectiveCalls
import scala.util.control.NonFatal

/**
 * fpt3 file
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-27
 */
object FPT3File {
  class LogicHeadV3 extends AncientData{
    @Length(8)
    var fileLength: String = _
    @Length(1)
    var dataType:String = _
  }
  class FPT3File extends AncientData {
    var head: FPTHead = new FPTHead
    @Length(12)
    var fileLength: String = _
    @Length(1)
    var dataType: String = "1"
    @Length(6)
    var lpCount: String = _
    @Length(6)
    var tpCount: String = _
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

    var fs: Byte = FPTFile.FS
    @LengthRef("lpCount")
    var logic2Recs: Array[Logic2Rec] = _
    @LengthRef("tpCount")
    var logic3Recs: Array[Logic3Rec] = _

    private def tryReadLogic(dataSource:StreamReader,encoding:Charset): Unit ={
      dataSource.markReaderIndex()
      val head = new LogicHeadV3()
      head.fromStreamReader(dataSource, encoding)
      dataSource.resetReaderIndex()
      head.dataType.trim match {
        case "3" =>
          if(logic3Recs == null)
            logic3Recs = Array[Logic3Rec](new Logic3Rec)
          else{
            val tmp = logic3Recs
            logic3Recs = new Array[Logic3Rec](logic3Recs.length+1)
            System.arraycopy(tmp,0,logic3Recs,0,tmp.length)
            logic3Recs(tmp.length) = new Logic3Rec
          }
          tpCount = logic3Recs.length.toString
          logic3Recs.last.fromStreamReader(dataSource)
        case "2" =>
          if(logic2Recs == null)
            logic2Recs = Array[Logic2Rec](new Logic2Rec)
          else{
            val tmp = logic2Recs
            logic2Recs = new Array[Logic2Rec](logic2Recs.length+1)
            System.arraycopy(tmp,0,logic2Recs,0,tmp.length)
            logic2Recs(tmp.length) = new Logic2Rec
          }
          logic2Recs.last.fromStreamReader(dataSource)
          lpCount = logic2Recs.length.toString
        case other=>
          throw new FPTParseException("wrong data type %s for fpt3".format(other))
      }
    }
    /**
     * convert channel buffer data as object
     * @param dataSource netty channel buffer
     */
    override def fromStreamReader(dataSource: StreamReader, encoding: Charset): FPT3File.this.type = {
      super.fromStreamReader(dataSource, encoding)

      //for BigData test,retry read logic head from stream
      if(tpCount == null || tpCount.isEmpty){
        try {
          0 until 100 foreach{i=>
            tryReadLogic(dataSource,encoding)
          }
        }catch{
          case NonFatal(e)=>
            //do nothing
        }
      }
      this
    }
  }

  class Logic2Rec extends DynamicFingerData{
    var head = new LogicHeadV3()
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
    var fingers: Array[FingerLData] = _

    // GS
    override protected def getFingerDataCount: Int = {
      if(sendFingerCount != null && sendFingerCount.nonEmpty) sendFingerCount.toInt else 0
    }
  }

  class FingerTData extends AncientData {
    @Length(6)
    var dataLength: Array[Byte] = _
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
    var customInfoLength: String = "0"
    @LengthRef("customInfoLength")
    var customInfo: Array[Byte] = _
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
    var fingerDataEnd: Byte = FPTFile.GS // GS
  }

  class FingerLData extends AncientData {
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
    var customInfoLength: String = "0"
    @LengthRef("customInfoLength")
    var customInfo: Array[Byte] = _
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
    var fingerDataEnd: Byte = FPTFile.GS // GS
  }

  class Logic3Rec extends DynamicFingerData{
    var head = new LogicHeadV3()
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
    var fingers: Array[FingerTData] = _

    // GS
    override protected def getFingerDataCount: Int = {
      if(sendFingerCount != null && sendFingerCount.nonEmpty) sendFingerCount.toInt else 0
    }
  }
}
