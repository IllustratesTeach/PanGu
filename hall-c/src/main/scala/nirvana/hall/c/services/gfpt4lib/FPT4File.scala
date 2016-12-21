package nirvana.hall.c.services.gfpt4lib

import nirvana.hall.c.annotations.{Length, LengthRef, NotTrim}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gfpt4lib.FPTFile.{DynamicFingerData, FPTHead}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-27
 */
object FPT4File {
  //逻辑记录类型
  final val LOGIC01REC_DATATYPE = "01"
  final val LOGIC02REC_DATATYPE = "02"
  final val LOGIC03REC_DATATYPE = "03"
  final val LOGIC04REC_DATATYPE = "04"
  final val LOGIC05REC_DATATYPE = "05"
  final val LOGIC06REC_DATATYPE = "06"
  final val LOGIC07REC_DATATYPE = "07"
  final val LOGIC08REC_DATATYPE = "08"
  final val LOGIC09REC_DATATYPE = "09"
  final val LOGIC10REC_DATATYPE = "10"
  final val LOGIC11REC_DATATYPE = "11"
  final val LOGIC12REC_DATATYPE = "12"
  final val LOGIC99REC_DATATYPE = "99"

  private lazy val headSize = 10
  class LogicHeadV4 extends AncientData{
    @Length(8)
    var fileLength: String = _
    @Length(2)
    var dataType:String = _
    def this(dataType: String){
      this()
      this.dataType = dataType
    }

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
    var dataType: String = LOGIC01REC_DATATYPE
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

  /**
   * 十指指纹信息记录结构
   */
  class Logic02Rec extends DynamicFingerData {
    var head = new LogicHeadV4(LOGIC02REC_DATATYPE)
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
    override protected def getFingerDataCountString: String = sendFingerCount
  }

  /**
   * 十指指纹信息
   */
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

  /**
   * 现场指纹信息记录结构
   */
  class Logic03Rec extends DynamicFingerData {
    var head = new LogicHeadV4(LOGIC03REC_DATATYPE)
    @Length(6)
    var index: String = _   //序号
    @Length(4)
    var systemType: String = _    //系统类型
    @Length(23)
    var caseId: String = _    //案件编号
    @Length(23)
    var cardId: String = _     //卡号
    @Length(6)
    var caseClass1Code: String = _    //案件类别1
    @Length(6)
    var caseClass2Code: String = _    //案件类别2
    @Length(6)
    var caseClass3Code: String = _    //案件类别3
    @Length(8)
    var occurDate: String = _   //发案日期
    @Length(6)
    var occurPlaceCode: String = _    //发案地点代码
    @Length(70)
    var occurPlace: String = _    //发案地点
    @Length(512)
    var caseBriefDetail: String = _   //简要案情
    @Length(1)
    var isMurder: String = _    //命案标识
    @Length(10)
    var amount: String = _    //涉案金额
    @Length(12)
    var extractUnitCode: String = _   //提取单位代码
    @Length(70)
    var extractUnitName: String = _   //提取单位名称
    @Length(8)
    var extractDate: String = _   //提取日期
    @Length(30)
    var extractor: String = _   //提取人
    @Length(6)
    var suspiciousArea1Code: String = _   //可疑地区线索1
    @Length(6)
    var suspiciousArea2Code: String = _   //可疑地区线索2
    @Length(6)
    var suspiciousArea3Code: String = _   //可疑地区线索3
    @Length(1)
    var assistLevel: String = _   //协查级别
    @Length(6)
    var bonus: String = _   //奖金
    @Length(12)
    var assistUnitCode: String = _    //协查单位代码
    @Length(70)
    var assistUnitName: String = _    //协查单位名称
    @Length(8)
    var assistDate: String = _    //协查日期
    @Length(1)
    var isCaseAssist: String = _    //案件协查标识
    @Length(1)
    var isRevoke: String = _    //部撤销标识
    @Length(1)
    var caseStatus: String = _    //案件状态
    @Length(2)
    var fingerCount: String = _   //本案现场指纹个数
    @Length(2)
    var sendFingerCount: String = _   //发送现场指纹个数
    @LengthRef("sendFingerCount")
    var fingers: Array[FingerLData] = _

    // GS
    override protected def getFingerDataCountString: String = sendFingerCount
  }

  /**
   * 现场指纹信息
   */
  class FingerLData extends AncientData{
    @Length(7)
    var dataLength: String = _    //指纹信息长度
    @Length(2)
    var sendNo: String = _    //发送序号
    @Length(2)
    var fingerNo: String = _    //指纹序号
    @Length(25)
    var fingerId: String = _    //指纹编号
    @Length(1)
    var isCorpse: String = _    //是否为尸体指纹
    @Length(23)
    var corpseNo: String = _    //未知名尸体编号
    @Length(30)
    var remainPlace: String = _   //遗留部位
    @Length(10)
    var fgp: String = _   //分析指位
    @Length(1)
    var ridgeColor: String = _    //乳突线颜色
    @Length(2)
    var mittensBegNo: String = _    //连指开始序号
    @Length(2)
    var mittensEndNo: String = _    //连指结束序号
    @Length(1)
    var isFingerAssist: String = _    //指纹协查标识
    @Length(1)
    var matchStatus: String = _   //指纹比对状态
    @Length(1)
    var extractMethod: String = _   //特征提取方式
    @Length(7)
    var pattern: String = _   //指纹纹型分类
    @Length(5)
    var fingerDirection: String = _   //指纹方向
    @Length(14)
    var centerPoint: String = _   //中心点
    @Length(14)
    var subCenterPoint: String = _    //副中心
    @Length(14)
    var leftTriangle: String = _    //左三角
    @Length(14)
    var rightTriangle: String = _   //右三角
    @Length(3)
    var featureCount: String = _    //特征点个数
    @NotTrim
    @Length(1800)
    var feature: String = _   //特征点
    @Length(6)
    var customInfoLength: String = _    //自定义信息长度
    @LengthRef("customInfoLength")
    var customInfo: Array[Byte] = _   //自定义信息
    @Length(3)
    var imgHorizontalLength: String = _   //图像水平方向长度
    @Length(3)
    var imgVerticalLength: String = _   //图像垂直方向长度
    @Length(3)
    var dpi: String = _   //图像分辨率
    @Length(4)
    var imgCompressMethod: String = _   //图像压缩方法代码
    @Length(6)
    var imgDataLength: String = _   //图像长度
    @LengthRef("imgDataLength")
    var imgData: Array[Byte] = _    //图像数据
    var end: Byte = FPTFile.GS    //分隔符或结束符
  }

  /**
   * 指纹正查和倒查比中信息记录结构
   */
  class Logic04Rec extends AncientData {
    var head = new LogicHeadV4(LOGIC04REC_DATATYPE)
    @Length(6) //序号
    var index: String = _
    @Length(4) //系统类型
    var systemType: String = _
    @Length(23) //案件编号
    var caseId: String = _
    @Length(2) //现场指纹序号
    var seqNo: String = _
    @Length(23) //人员编号
    var personId: String = _
    @Length(2) //指纹指位
    var fgp: String = _
    @Length(1) //直接抓获1：是；0：否
    var capture: String = _
    @Length(1) //比对方法
    var matchMethod: String = _
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

  /**
   * 指纹查重比中信息记录结构
   */
  class Logic05Rec extends AncientData {
    var head = new LogicHeadV4(LOGIC05REC_DATATYPE)
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

  /**
   * 指纹串查比中信息记录结构
   */
  class Logic06Rec extends AncientData {
    var head = new LogicHeadV4(LOGIC06REC_DATATYPE)
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

  /**
   * 现场指纹查询请求信息记录结构
   */
  class Logic07Rec extends AncientData {
    var head = new LogicHeadV4(LOGIC07REC_DATATYPE)
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

  /**
   * 十指指纹查询请求信息记录结构
   */
  class Logic08Rec extends AncientData {
    var head = new LogicHeadV4(LOGIC08REC_DATATYPE)
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

  /**
   * 正查比对结果候选信息记录结构
   */
  class Logic09Rec extends AncientData {
    var head = new LogicHeadV4(LOGIC09REC_DATATYPE)
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

  /**
   * 倒查比对结果候选信息记录结构
   */
  class Logic10Rec extends AncientData {
    var head = new LogicHeadV4(LOGIC10REC_DATATYPE)
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

  /**
   * 查重比对结果候选信息记录结构
   */
  class Logic11Rec extends AncientData {
    var head = new LogicHeadV4(LOGIC11REC_DATATYPE)
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

  /**
   * 串查比对结果候选信息记录结构
   */
  class Logic12Rec extends AncientData {
    var head = new LogicHeadV4(LOGIC12REC_DATATYPE)
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

  /**
   * 自定义逻辑记录结构
   */
  class Logic99Rec extends AncientData {
    var head = new LogicHeadV4(LOGIC99REC_DATATYPE)
    @Length(6) //序号
    var index: String = _
    @Length(4) //系统类型
    var systemType: String = _
    var fs: Byte = FPTFile.FS
  }

}
