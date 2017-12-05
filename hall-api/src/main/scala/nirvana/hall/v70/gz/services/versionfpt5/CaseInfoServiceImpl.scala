package nirvana.hall.v70.gz.services.versionfpt5

import java.text.SimpleDateFormat
import java.util.Date

import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.c.services.gfpt4lib.FPT4File.Logic03Rec
import nirvana.hall.protocol.api.FPTProto.Case
import nirvana.hall.v70.gz.jpa.{GafisCase, GafisCaseFinger, GafisCasePalm, SysUser}
import nirvana.hall.v70.gz.sync.ProtobufConverter
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
    val gafisCase = ProtobufConverter.convertCase2GafisCase(caseInfo)
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
    ProtobufConverter.convertCase2GafisCase(caseInfo, gafisCase)

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
    magicSet(caseInfo.caseBriefDetail, textBuilder.setStrBriefCase)
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

}
