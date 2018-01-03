package nirvana.hall.v70.gz.services.versionfpt5

import java.text.SimpleDateFormat
import java.util.Date

import nirvana.hall.api.internal.DateConverter
import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.c.services.gfpt4lib.FPT4File.Logic03Rec
import nirvana.hall.protocol.api.FPTProto.Case
import nirvana.hall.v70.gz.jpa.{GafisCase, GafisCaseFinger, GafisCasePalm, SysUser}
import nirvana.hall.v70.gz.sys.UserService
import nirvana.hall.v70.internal.Gafis70Constants

/**
  * Created by songpeng on 2017/6/29.
  */
class CaseInfoServiceImpl(userService: UserService) extends CaseInfoService{

  /**
    * 新增案件信息
    *
    * @param caseInfo
    * @return
    */
  override def addCaseInfo(caseInfo: Case, dbId: Option[String]): Unit = {
    val gafisCase = convertCase2GafisCase(caseInfo)
    var user = userService.findSysUserByLoginName(gafisCase.inputpsn)
    if (user.isEmpty){//找不到对应的用户，使用管理员用户
      user = Option(SysUser.find(Gafis70Constants.INPUTPSN))
    }
    gafisCase.inputpsn = user.get.pkId
    gafisCase.createUnitCode = user.get.departCode
    val modUser = userService.findSysUserByLoginName(gafisCase.modifiedpsn)
    if(modUser.nonEmpty){
      gafisCase.modifiedpsn = modUser.get.pkId
    }

    gafisCase.deletag = Gafis70Constants.DELETAG_USE
    gafisCase.caseSource = Gafis70Constants.DATA_SOURCE_SURVEY.toString
    gafisCase.save()
  }

  /**
    * 删除案件信息
    *
    * @param caseId
    * @return
    */
override def delCaseInfo(caseId: String, dbId: Option[String]): Unit = ???

  /**
    * 更新案件信息
    *
    * @param caseInfo
    * @return
    */
  override def updateCaseInfo(caseInfo: Case, dbId: Option[String]): Unit = {
    val gafisCase = GafisCase.find(caseInfo.getStrCaseID)
    convertCase2GafisCase(caseInfo, gafisCase)

    var user = userService.findSysUserByLoginName(gafisCase.inputpsn)
    if (user.isEmpty){//找不到对应的用户，使用管理员用户
      user = Option(SysUser.find(Gafis70Constants.INPUTPSN))
    }
    gafisCase.inputpsn = user.get.pkId
    gafisCase.createUnitCode = user.get.departCode
    val modUser = userService.findSysUserByLoginName(gafisCase.modifiedpsn)
    if(modUser.nonEmpty){
      gafisCase.modifiedpsn = modUser.get.pkId
    }

    gafisCase.deletag = Gafis70Constants.DELETAG_USE
    gafisCase.caseSource = caseInfo.getStrDataSource
    gafisCase.save()
  }

  /**
    * 获取案件信息
    *
    * @param caseId
    * @return
    */
  override def getCaseInfo(caseId: String, dbId: Option[String]): Case = {
    val gafisCase = GafisCase.findOption(caseId)
    if(gafisCase.isEmpty){
      throw new RuntimeException("记录不存在!");
    }
    val fingers = GafisCaseFinger.select(GafisCaseFinger.fingerId).where(GafisCaseFinger.caseId === caseId).toList.asInstanceOf[List[String]]
    val palms = GafisCasePalm.select(GafisCasePalm.palmId).where(GafisCasePalm.caseId === caseId).toList.asInstanceOf[List[String]]
    convertGafisCase2Case(gafisCase.get,fingers,palms)
  }

  /**
    * 验证案件编号是否已存在
    *
    * @param caseId
    * @return
    */
  override def isExist(caseId: String, dbId: Option[String]): Boolean = {
    GafisCase.findOption(caseId).nonEmpty
  }

  /**
    * 查询案件编号列表
    *
    * @param ajno        案件编号
    * @param ajlb        案件类别
    * @param fadddm      发案地代码
    * @param mabs        命案标识
    * @param xcjb        协查级别
    * @param xcdwdm      查询单位代码
    * @param startfadate 开始时间（检索发案时间，时间格式YYYYMMDD）
    * @param endfadate   结束时间（检索发案时间，时间格式YYYYMMDD）
    * @return
    */
  override def getFPT4Logic03RecList(ajno: String, ajlb: String, fadddm: String, mabs: String, xcjb: String, xcdwdm: String, startfadate: String, endfadate: String): Seq[Logic03Rec] = ???

  implicit def string2Int(string: String): Int ={
    if(isNonBlank(string))
      Integer.parseInt(string)
    else
      0
  }
  implicit def date2String(date: Date): String = {
    if (date != null)
      new SimpleDateFormat("yyyyMMdd").format(date)
    else ""
  }
  def isNonBlank(string: String):Boolean = string != null && string.length >0
  def magicSet(value:String,fun:String=>Any){
    if(isNonBlank(value)){ fun(value)}
  }
  def convertGafisCase2Case(caseInfo: GafisCase, fingerIds: Seq[String],palms: Seq[String]): Case = {
    val caseBuilder = Case.newBuilder()
    caseBuilder.setStrCaseID(caseInfo.caseId)
    caseBuilder.setStrDataSource(caseInfo.caseSource)
    //FPT5.0 新加
    caseBuilder.setStrJingZongCaseId(caseInfo.caseSystemId)
    caseBuilder.setStrSurveyId(caseInfo.sceneSurveyId)

    val textBuilder = caseBuilder.getTextBuilder
    magicSet(caseInfo.caseClassCode, textBuilder.setStrCaseType1)
    magicSet(caseInfo.caseOccurDate, textBuilder.setStrCaseOccurDate)
    magicSet(caseInfo.caseOccurPlaceCode, textBuilder.setStrCaseOccurPlaceCode)
    magicSet(caseInfo.caseOccurPlaceDetail, textBuilder.setStrCaseOccurPlace)

    magicSet(caseInfo.remark, textBuilder.setStrComment)
    if("1".equals(caseInfo.isMurder))
      textBuilder.setBPersonKilled(true)
    magicSet(caseInfo.amount, textBuilder.setStrMoneyLost)
    magicSet(caseInfo.extractUnitCode, textBuilder.setStrExtractUnitCode)
    magicSet(caseInfo.extractUnitName, textBuilder.setStrExtractUnitName)
    magicSet(caseInfo.extractDate, textBuilder.setStrExtractDate)
    magicSet(caseInfo.extractor, textBuilder.setStrExtractor)
    magicSet(caseInfo.suspiciousAreaCode, textBuilder.setStrSuspArea1Code)
    textBuilder.setNSuperviseLevel(caseInfo.assistLevel)
    magicSet(caseInfo.assistDeptCode, textBuilder.setStrXieChaRequestUnitCode)
    magicSet(caseInfo.assistDeptName, textBuilder.setStrXieChaRequestUnitName)
    magicSet(caseInfo.assistDate, textBuilder.setStrXieChaDate)
    magicSet(caseInfo.assistBonus, textBuilder.setStrPremium)
    //FPT5.0 新加
    magicSet(caseInfo.caseBriefDetail, textBuilder.setStrBriefCase)
    magicSet(caseInfo.extractorIdcardNo, textBuilder.setStrExtractorIdCard)
    magicSet(caseInfo.extractorPhone, textBuilder.setStrExtractorTel)
    magicSet(caseInfo.caseClassCode2, textBuilder.setStrCaseType2)
    magicSet(caseInfo.caseClassCode3, textBuilder.setStrCaseType3)


    textBuilder.setNCaseState(caseInfo.assistSign)
    textBuilder.setNCancelFlag(caseInfo.assistRevokeSign)
    //案件状态字典不符fpt标准，暂时忽略该项信息
//    textBuilder.setNCaseState(caseInfo.caseState)

    if(fingerIds != null)
      fingerIds.foreach(f => caseBuilder.addStrFingerID(f))
    caseBuilder.setNCaseFingerCount(caseBuilder.getStrFingerIDCount)

    if(palms != null)
      palms.foreach(p => caseBuilder.addStrPalmID(p))

    caseBuilder.build()
  }

  /**
    * 案件信息转换
    *
    * @param caseInfo
    * @return
    */
  def convertCase2GafisCase(caseInfo: Case, gafisCase: GafisCase = new GafisCase()): GafisCase = {
    gafisCase.caseId = caseInfo.getStrCaseID
    gafisCase.cardId = caseInfo.getStrCaseID
    val text = caseInfo.getText
    gafisCase.caseClassCode = text.getStrCaseType1
    gafisCase.suspiciousAreaCode = text.getStrSuspArea1Code
    gafisCase.caseOccurDate = DateConverter.convertString2Date(text.getStrCaseOccurDate, "yyyyMMddHHmmss")
    gafisCase.caseOccurPlaceCode = text.getStrCaseOccurPlaceCode
    gafisCase.caseOccurPlaceDetail = text.getStrCaseOccurPlace
    //gafisCase.assistLevel = DictCode6Map7.assistLevel.get(text.getNSuperviseLevel)
    gafisCase.extractUnitCode = text.getStrExtractUnitCode
    gafisCase.extractUnitName = text.getStrExtractUnitName
    gafisCase.extractor = text.getStrExtractor
    gafisCase.extractDate = DateConverter.convertString2Date( text.getStrExtractDate, "yyyyMMddHHmmss")
    gafisCase.amount = text.getStrMoneyLost
    gafisCase.isMurder = if(text.getBPersonKilled) "1" else "0"
    gafisCase.remark = text.getStrComment
    gafisCase.caseState = text.getNCaseState.toString
    gafisCase.assistBonus = text.getStrPremium
    gafisCase.assistSign = text.getNXieChaState.toString
    gafisCase.assistRevokeSign = text.getNCancelFlag.toString// TODO值太大
    gafisCase.assistDeptCode = text.getStrXieChaRequestUnitCode
    gafisCase.assistDeptName = text.getStrXieChaRequestUnitName
    //新增
    gafisCase.caseClassCode2 = text.getStrCaseType2
    gafisCase.caseClassCode3 = text.getStrCaseType3
    gafisCase.suspiciousAreaCode2 = text.getStrSuspArea2Code
    gafisCase.suspiciousAreaCode3 = text.getStrSuspArea3Code
    //gafisCase.bonus = text.getStrPremium
    gafisCase.assistStatus = text.getNXieChaState.toString
    gafisCase.receptionNo = ""

    //fpt5.0新增
    gafisCase.caseBriefDetail = text.getStrBriefCase
    gafisCase.caseSystemId = caseInfo.getStrJingZongCaseId
    gafisCase.sceneSurveyId = caseInfo.getStrSurveyId
    gafisCase.extractorIdcardNo = text.getStrExtractorIdCard
    gafisCase.extractorPhone = text.getStrExtractorTel

    //操作信息
    val admData = caseInfo.getAdmData
    if(admData != null){
      gafisCase.inputpsn = admData.getCreator
      gafisCase.modifiedpsn = admData.getUpdator
      gafisCase.createUnitCode = admData.getCreateUnitCode
      if(admData.getCreateDatetime != null && admData.getCreateDatetime.length == 14){
        gafisCase.inputtime = DateConverter.convertString2Date(admData.getCreateDatetime, "yyyyMMddHHmmss")
      }else{
        gafisCase.inputtime = new Date
      }
      if(admData.getUpdateDatetime != null && admData.getUpdateDatetime.length == 14){
        gafisCase.modifiedtime = DateConverter.convertString2Date(admData.getUpdateDatetime, "yyyyMMddHHmmss")
      }
    }
    gafisCase
  }

}
