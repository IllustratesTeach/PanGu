package nirvana.hall.c.services.gfpt4lib

import nirvana.hall.c.annotations.{NotTrim, Length, LengthRef}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gfpt4lib.FPTFile.{DynamicFingerData, FPTHead}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-27
 */
object FPT4File {
  private lazy val headSize = 10
  class LogicHeadV4 extends AncientData{
    @Length(8)
    var fileLength: String = _
    @Length(2)
    var dataType:String = _

    /**
     * calculate data size and return.
 *
     * @return data size
     */
    override def getDataSize: Int = headSize
  }
  class FPT4File extends AncientData {
    var head: FPTHead = new FPTHead
    //var logic01Rec = new Logic01Rec
    @Length(12)
    var fileLength: String = _
    @Length(2)
    var dataType: String = "1"
    @Length(6)
    var tpCount: String = _
    @Length(6)
    var lpCount: String = _

    @Length(6)
    var tl_ltCount: String = _
    @Length(6)
    var ttCount: String = _
    @Length(6)
    var llCount: String = _
    @Length(6)
    var lpRequestCount: String = _
    @Length(6)
    var tpRequestCount: String = _
    @Length(6)
    var ltCandidateCount: String = _
    @Length(6)
    var tlCandidateCount: String = _
    @Length(6)
    var ttCandidateCount: String = _
    @Length(6)
    var llCandidateCount: String = _
    @Length(6)
    var customCandidateCount: String = _


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
    var fs: Byte = FPTFile.FS //FS

    @LengthRef("tpCount")
    var logic02Recs: Array[Logic02Rec] = _
    @LengthRef("lpCount")
    var logic03Recs: Array[Logic03Rec] = _

    @LengthRef("tl_ltCount")
    var logic04Recs: Array[Logic04Rec] = _
    @LengthRef("ttCount")
    var logic05Recs: Array[Logic05Rec] = _
    @LengthRef("llCount")
    var logic06Recs: Array[Logic06Rec] = _
    @LengthRef("lpRequestCount")
    var logic07Recs: Array[Logic07Rec] = _
    @LengthRef("tpRequestCount")
    var logic08Recs: Array[Logic08Rec] = _
    @LengthRef("ltCandidateCount")
    var logic09Recs: Array[Logic09Rec] = _
    @LengthRef("tlCandidateCount")
    var logic10Recs: Array[Logic10Rec] = _
    @LengthRef("ttCandidateCount")
    var logic11Recs: Array[Logic11Rec] = _
    @LengthRef("llCandidateCount")
    var logic12Recs: Array[Logic12Rec] = _
    @LengthRef("customCandidateCount")
    var logic99Recs: Array[Logic99Rec] = _

  }

  class Logic02Rec extends DynamicFingerData {
    var head = new LogicHeadV4
    @Length(6)
    var index: String = _
    @Length(4)
    var systemType: String = _
    @Length(23)
    var personId: String = _
    @Length(23)
    var cardId: String = _
    @Length(30)
    var personName: String = _
    @Length(30)
    var alias: String = _
    @Length(1)
    var gender: String = _
    @Length(8)
    var birthday: String = _
    @Length(3)
    var nativeplace: String = _
    @Length(2)
    var nation: String = _
    @Length(18)
    var idCardNo: String = _
    @Length(3)
    var certificateType: String = _
    @Length(20)
    var certificateNo: String = _
    @Length(6)
    var door: String = _
    @Length(70)
    var doorDetail: String = _
    @Length(6)
    var address: String = _
    @Length(70)
    var addressDetail: String = _
    @Length(2)
    var category: String = _
    @Length(6)
    var caseClass1Code: String = _
    @Length(6)
    var caseClass2Code: String = _
    @Length(6)
    var caseClass3Code: String = _
    @Length(1)
    var isCriminal: String = _
    @Length(1024)
    var criminalInfo: String = _
    @Length(12)
    var gatherUnitCode: String = _
    @Length(70)
    var gatherUnitName: String = _
    @Length(30)
    var gatherName: String = _
    @Length(8)
    var gatherDate: String = _
    @Length(1)
    var assistLevel: String = _
    @Length(6)
    var bonus: String = _
    @Length(5)
    var assistPurpose: String = _
    @Length(23)
    var relatedPersonId: String = _
    @Length(23)
    var relatedCaseId: String = _
    @Length(1)
    var assistTimeLimit: String = _
    @Length(512)
    var assistAskingInfo: String = _
    @Length(12)
    var assistUnitCode: String = _
    @Length(70)
    var assistUnitName: String = _
    @Length(8)
    var assistDate: String = _
    @Length(30)
    var contact: String = _
    @Length(40)
    var contactPhone: String = _
    @Length(30)
    var approver: String = _
    @Length(512)
    var remark: String = _
    @Length(1)
    var isAssist: String = _
    @Length(6)
    var portraitDataLength: String = _
    @LengthRef("portraitDataLength")
    var portraitData: Array[Byte] = _
    @Length(2)
    var sendFingerCount: String = _
    @LengthRef("sendFingerCount")
    var fingers: Array[FingerTData] = _

    // GS
    override protected def getFingerDataCount: Int = {
      if (sendFingerCount != null && sendFingerCount.nonEmpty) sendFingerCount.toInt else 0
    }
  }

  class FingerTData extends AncientData {
    @Length(7)
    var dataLength: String = _
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
    var feature: String = _
    @Length(6)
    var customInfoLength: String = _
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
    var imgDataLength: String = _
    @LengthRef("imgDataLength")
    var imgData: Array[Byte]= _
    var end: Byte = FPTFile.GS
  }

  class Logic03Rec extends DynamicFingerData {
    var head = new LogicHeadV4
    @Length(6)
    var index: String = _
    @Length(4)
    var systemType: String = _
    @Length(23)
    var caseId: String = _
    @Length(23)
    var cardId: String = _
    @Length(6)
    var caseClass1Code: String = _
    @Length(6)
    var caseClass2Code: String = _
    @Length(6)
    var caseClass3Code: String = _
    @Length(8)
    var occurDate: String = _
    @Length(6)
    var occurPlaceCode: String = _
    @Length(70)
    var occurPlace: String = _
    @Length(512)
    var caseBriefDetail: String = _
    @Length(1)
    var isMurder: String = _
    @Length(10)
    var amount: String = _
    @Length(12)
    var extractUnitCode: String = _
    @Length(70)
    var extractUnitName: String = _
    @Length(8)
    var extractDate: String = _
    @Length(30)
    var extractor: String = _
    @Length(6)
    var suspiciousArea1Code: String = _
    @Length(6)
    var suspiciousArea2Code: String = _
    @Length(6)
    var suspiciousArea3Code: String = _
    @Length(1)
    var assistLevel: String = _
    @Length(6)
    var bonus: String = _
    @Length(12)
    var assistUnitCode: String = _
    @Length(70)
    var assistUnitName: String = _
    @Length(8)
    var assistDate: String = _
    @Length(1)
    var isCaseAssist: String = _
    @Length(1)
    var isRevoke: String = _
    @Length(1)
    var caseStatus: String = _
    @Length(2)
    var fingerCount: String = _
    @Length(2)
    var sendFingerCount: String = _
    @LengthRef("sendFingerCount")
    var fingers: Array[FingerLData] = _

    // GS
    override protected def getFingerDataCount: Int = {
      if (sendFingerCount != null && sendFingerCount.nonEmpty) sendFingerCount.toInt else 0
    }
  }

  class FingerLData extends AncientData{
    @Length(7)
    var dataLength: String = _
    @Length(2)
    var sendNo: String = _
    @Length(2)
    var fingerNo: String = _
    @Length(25)
    var fingerId: String = _
    @Length(1)
    var isCorpse: String = _
    @Length(23)
    var corpseNo: String = _
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
    var isFingerAssist: String = _
    @Length(1)
    var matchStatus: String = _
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
    @NotTrim
    @Length(1800)
    var feature: String = _
    @Length(6)
    var customInfoLength: String = _
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
    var imgDataLength: String = _
    @LengthRef("imgDataLength")
    var imgData: Array[Byte] = _
    var end: Byte = FPTFile.GS
  }

  class Logic04Rec extends AncientData {
    var head = new LogicHeadV4
    @Length(6) //序号
    var index: String = _
    @Length(4) //系统类型
    var systemType: String = _
    @Length(23) //人员编号1
    var personId1: String = _
    @Length(23) //人员编号2
    var personId2: String = _
    @Length(12) //比对单位代码
    var matchUnitCode: String = _
    @Length(70) //比对单位名称
    var matchName: String = _
    @Length(30) //比对人姓名
    var matcher: String = _
    @Length(8) //比对日期
    var matchDate: String = _
    @Length(100) //备注
    var remark: String = _
    @Length(30) //填报人
    var inputer: String = _
    @Length(8) //填报日期
    var inputDate: String = _
    @Length(30) //审批人
    var approver: String = _
    @Length(8) //审批日期
    var approveDate: String = _
    @Length(12) //填报单位代码
    var inputUnitCode: String = _
    @Length(70) //填报单位名称
    var inputUnitName: String = _
    @Length(30) //复核人
    var rechecker: String = _
    @Length(12) //复核单位代码
    var recheckUnitCode: String = _
    @Length(8) //复核日期
    var recheckDate: String = _
    var fs: Byte = FPTFile.FS
  }

  class Logic05Rec extends AncientData {
    var head = new LogicHeadV4
    @Length(6) //序号
    var index: String = _
    @Length(4) //系统类型
    var systemType: String = _
    @Length(23) //人员编号1
    var personId1: String = _
    @Length(23) //人员编号2
    var personId2: String = _
    @Length(12) //比对单位代码
    var matchUnitCode: String = _
    @Length(70) //比对单位名称
    var matchName: String = _
    @Length(30) //比对人姓名
    var matcher: String = _
    @Length(8) //比对日期
    var matchDate: String = _
    @Length(100) //备注
    var remark: String = _
    @Length(30) //填报人
    var inputer: String = _
    @Length(8) //填报日期
    var inputDate: String = _
    @Length(30) //审批人
    var approver: String = _
    @Length(8) //审批日期
    var approveDate: String = _
    @Length(12) //填报单位代码
    var inputUnitCode: String = _
    @Length(70) //填报单位名称
    var inputUnitName: String = _
    @Length(30) //复核人
    var rechecker: String = _
    @Length(12) //复核单位代码
    var recheckUnitCode: String = _
    @Length(8) //复核日期
    var recheckDate: String = _
    var fs: Byte = FPTFile.FS
  }

  class Logic06Rec extends AncientData {
    var head = new LogicHeadV4
    @Length(6) //序号
    var index: String = _
    @Length(4) //系统类型
    var systemType: String = _
    @Length(23) //案件编号1
    var caseId1: String = _
    @Length(2) //指纹序号1
    var seqNo1: String = _
    @Length(23) //案件编号2
    var caseId2: String = _
    @Length(2) //指纹序号2
    var seqNo2: String = _
    @Length(12) //比对单位代码
    var matchUnitCode: String = _
    @Length(70) //比对单位名称
    var matchName: String = _
    @Length(30) //比对人姓名
    var matcher: String = _
    @Length(8) //比对日期
    var matchDate: String = _
    @Length(100) //备注
    var remark: String = _
    @Length(30) //填报人
    var inputer: String = _
    @Length(8) //填报日期
    var inputDate: String = _
    @Length(30) //审批人
    var approver: String = _
    @Length(8) //审批日期
    var approveDate: String = _
    @Length(12) //填报单位代码
    var inputUnitCode: String = _
    @Length(70) //填报单位名称
    var inputUnitName: String = _
    @Length(30) //复核人
    var rechecker: String = _
    @Length(12) //复核单位代码
    var recheckUnitCode: String = _
    @Length(8) //复核日期
    var recheckDate: String = _
    var fs: Byte = FPTFile.FS
  }

  class Logic07Rec extends AncientData {
    var head = new LogicHeadV4
    @Length(6) //序号
    var index: String = _
    @Length(4) //系统类型
    var systemType: String = _
    @Length(23) //案件编号
    var caseId: String = _
    @Length(2) //指纹序号
    var seqNo: String = _
    @Length(1) //指纹查询目的
    var matchPurpose: String = _
    var fs: Byte = FPTFile.FS
  }

  class Logic08Rec extends AncientData {
    var head = new LogicHeadV4
    @Length(6) //序号
    var index: String = _
    @Length(4) //系统类型
    var systemType: String = _
    @Length(23) //人员编号
    var personId: String = _
    @Length(1) //指纹查询目的
    var matchPurpose: String = _
    var fs: Byte = FPTFile.FS
  }

  class Logic09Rec extends AncientData {
    var head = new LogicHeadV4
    @Length(6) //序号
    var index: String = _
    @Length(4)
    var matchMethod: String = _
    @Length(12)
    var matchUnitCode: String = _
    @Length(8)
    var matchFinshDate: String = _
    @Length(30)
    var authenticator: String = _
    @Length(23)
    var caseId: String = _
    @Length(2)
    var seqNo: String = _
    @Length(3)
    var tpFingerCount: String = _
    @Length(3)
    var candidatePlace: String = _
    @Length(8)
    var candidateScore: String = _
    @Length(23)
    var personId: String = _
    @Length(2)
    var fgp: String = _
    var fs: Byte = FPTFile.FS
  }

  class Logic10Rec extends AncientData {
    var head = new LogicHeadV4
    @Length(6) //序号
    var index: String = _
    @Length(4)
    var matchMethod: String = _
    @Length(12)
    var matchUnitCode: String = _
    @Length(8)
    var matchFinshDate: String = _
    @Length(23)
    var personId: String = _
    @Length(3)
    var lpFingerCount: String = _
    @Length(3)
    var candidatePlace: String = _
    @Length(8)
    var candidateScore: String = _
    @Length(2)
    var fgp: String = _
    @Length(23)
    var caseId: String = _
    @Length(2)
    var seqNo: String = _
    var fs: Byte = FPTFile.FS
  }

  class Logic11Rec extends AncientData {
    var head = new LogicHeadV4
    @Length(6) //序号
    var index: String = _
    @Length(4)
    var matchMethod: String = _
    @Length(12)
    var matchUnitCode: String = _
    @Length(8)
    var matchFinshDate: String = _
    @Length(23)
    var submitPersonId: String = _
    @Length(3)
    var personCount: String = _
    @Length(3)
    var candidatePlace: String = _
    @Length(8)
    var candidateScore: String = _
    @Length(23)
    var personId: String = _
    var fs: Byte = FPTFile.FS
  }

  class Logic12Rec extends AncientData {
    var head = new LogicHeadV4
    @Length(6) //序号
    var index: String = _
    @Length(4)
    var matchMethod: String = _
    @Length(12)
    var matchUnitCode: String = _
    @Length(8)
    var matchFinshDate: String = _
    @Length(23)
    var submitCaseId: String = _
    @Length(2)
    var submitSeqNo: String = _
    @Length(3)
    var llFingerCount: String = _
    @Length(3)
    var candidatePlace: String = _
    @Length(8)
    var candidateScore: String = _
    @Length(23)
    var caseId: String = _
    @Length(2)
    var seqNo: String = _
    var fs: Byte = FPTFile.FS
  }

  class Logic99Rec extends AncientData {
    var head = new LogicHeadV4
    @Length(6) //序号
    var index: String = _
    @Length(4) //系统类型
    var systemType: String = _
    var fs: Byte = FPTFile.FS
  }

}
