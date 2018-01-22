package nirvana.hall.v70.internal.adapter.gz.services.versionfpt5

import java.text.SimpleDateFormat

import nirvana.hall.api.internal.fpt.CodeConverterV70New
import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.c.services.gfpt4lib.FPT4File.{Logic04Rec, Logic05Rec, Logic06Rec}
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gfpt5lib.{LlHitResultPackage, LtHitResultPackage, TtHitResultPackage, fpt5util}
import nirvana.hall.protocol.api.FPTProto.MatchRelationInfo
import nirvana.hall.protocol.api.HallMatchRelationProto.{MatchRelationGetRequest, MatchRelationGetResponse}
import nirvana.hall.v70.common.jpa.SysUser
import nirvana.hall.v70.internal.adapter.gz.jpa._
import nirvana.hall.v70.internal.adapter.gz.jpa.GafisCheckinInfo

import scala.collection.mutable.ArrayBuffer



class MatchRelationServiceImpl extends MatchRelationService{
  /**
   * 获取比对关系
   * 查询比中关系表获取比中关系信息
    *
    * @param request
   * @return
   */
  override def getMatchRelation(request: MatchRelationGetRequest): MatchRelationGetResponse = {
    null
  }

  override def isExist(szBreakID: String, dbId: Option[String]): Boolean = {
    var result=false
    if(GafisCheckinInfo.findOption(szBreakID).nonEmpty){
      result=true
    }
    result
  }

  /**
    * 增加比中关系
    *
    * @param matchRelationInfo
    * @param dbId
    */
  override def addMatchRelation(matchRelationInfo: MatchRelationInfo, dbId: Option[String]): Unit = ???

  /**
    * 更新比中关系
    *
    * @param matchRelationInfo
    * @param dbId
    */
  override def updateMatchRelation(matchRelationInfo: MatchRelationInfo, dbId: Option[String]): Unit = ???

  /**
    * 获取比对关系
    *
    * @param breakId
    * @return
    */
  override def getMatchRelation(breakId: String): MatchRelationInfo = {
    val gafisCheckinInfo = GafisCheckinInfo.find(breakId)
    //ProtobufConverter.convertGafisCheckInfo2MatchSysInfo(gafisCheckinInfo)
    null
  }

  /**
    * 获取重卡比中关系
    *
    * @param cardId
    * @return
    */
  override def getTtHitResultPackage(cardId: String): Seq[TtHitResultPackage] = {
    val ttHitResultPackageArray = new ArrayBuffer[TtHitResultPackage]
    GafisNormalqueryQueryque.where(GafisNormalqueryQueryque.keyid === cardId).foreach{
      t =>
        ttHitResultPackageArray += getTtHitResultPackageByOraSid(t.oraSid.toString).head
    }
    ttHitResultPackageArray
  }


  /**
    * 获取重卡比中关系
    *
    * @param oraSid 任务号
    * @return
    */
  override def getTtHitResultPackageByOraSid(oraSid: String): Seq[TtHitResultPackage] = {
    val gafisNormalQueryQueryque = GafisNormalqueryQueryque.where(GafisNormalqueryQueryque.oraSid === oraSid).headOption.get
    val gafisCheckInInfo = GafisCheckinInfo.where(GafisCheckinInfo.queryUUID === gafisNormalQueryQueryque.pkId).headOption.get
    val gafisPersonSource = GafisPerson.where(GafisPerson.personid === gafisCheckInInfo.code).headOption.get
    val gafisPersonDest = GafisPerson.where(GafisPerson.personid === gafisCheckInInfo.tcode).headOption.get

    val ttHitResultPackage = new TtHitResultPackage
    ttHitResultPackage.taskId = oraSid
    ttHitResultPackage.comparisonSystemTypeDescript = fpt4code.GAIMG_CPRMETHOD_EGFS_CODE
    ttHitResultPackage.ttHitTypeCode = fpt5util.TT_MATCHED_NORMAL
    ttHitResultPackage.originalPersonId = gafisPersonSource.personid
    ttHitResultPackage.jingZongPersonId = gafisPersonSource.jingZongPersonId
    ttHitResultPackage.personId = gafisPersonSource.casePersonid
    ttHitResultPackage.cardId = gafisPersonSource.personid
    ttHitResultPackage.whetherFingerJudgmentMark = "1" //是否指纹 1--是 2--否
    ttHitResultPackage.resultOriginalSystemPersonId = gafisPersonDest.personid
    ttHitResultPackage.resultjingZongPersonId = gafisPersonDest.jingZongPersonId
    ttHitResultPackage.resultPersonId = gafisPersonDest.casePersonid
    ttHitResultPackage.resultCardId = gafisPersonDest.personid

    ttHitResultPackage.hitUnitCode = gafisCheckInInfo.registerOrg
    ttHitResultPackage.hitUnitName = SysDepart.find_by_code(gafisCheckInInfo.registerOrg).headOption.get.name
    ttHitResultPackage.hitPersonName = SysUser.find_by_pkId(gafisCheckInInfo.registerUser).headOption.get.trueName
    ttHitResultPackage.hitPersonIdCard = SysUser.find_by_pkId(gafisCheckInInfo.registerUser).headOption.get.idcard
    ttHitResultPackage.hitPersonTel = SysUser.find_by_pkId(gafisCheckInInfo.registerUser).headOption.get.phone
    ttHitResultPackage.hitDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(gafisCheckInInfo.registerTime)

    ttHitResultPackage.checkUnitCode = gafisCheckInInfo.registerOrg
    ttHitResultPackage.checkUnitName = SysDepart.find_by_code(gafisCheckInInfo.registerOrg).headOption.get.name
    ttHitResultPackage.checkPersonName = SysUser.find_by_pkId(gafisCheckInInfo.registerUser).headOption.get.trueName
    ttHitResultPackage.checkPersonIdCard = SysUser.find_by_pkId(gafisCheckInInfo.registerUser).headOption.get.idcard
    ttHitResultPackage.checkPersonTel = SysUser.find_by_pkId(gafisCheckInInfo.registerUser).headOption.get.phone
    ttHitResultPackage.checkDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(gafisCheckInInfo.registerTime)
    ttHitResultPackage.memo = ""

    val ttHitResultPackageSeq = new ArrayBuffer[TtHitResultPackage]
    ttHitResultPackageSeq += ttHitResultPackage
    ttHitResultPackageSeq
  }

  /**
    * 获取正查或倒查比中关系
    * @param cardId 现场指纹编号
    * @param isLatent
    * @return
    */
  override def getLtHitResultPackage(cardId: String, isLatent: Boolean): Seq[LtHitResultPackage] = {

    val ltHitResultPackageSeq = new ArrayBuffer[LtHitResultPackage]
    GafisNormalqueryQueryque.where(GafisNormalqueryQueryque.keyid === cardId).foreach{
      t =>
        ltHitResultPackageSeq += getLtHitResultPackageByOraSid(t.oraSid.toString).head
    }
    ltHitResultPackageSeq
  }


  /**
    * 获取正查或倒查比中关系
    * @param oraSid 任务号
    * @return
    */
  override def getLtHitResultPackageByOraSid(oraSid: String): Seq[LtHitResultPackage] = {

    val gafisNormalqueryQueryque = GafisNormalqueryQueryque.where(GafisNormalqueryQueryque.oraSid === oraSid).headOption.get
    val gafisCheckinInfo = GafisCheckinInfo.where(GafisCheckinInfo.queryUUID === gafisNormalqueryQueryque.pkId).headOption.get

    val gafisCaseFinger = GafisCaseFinger.where(GafisCaseFinger.fingerId === gafisCheckinInfo.code).headOption.get
    val gafisCase = GafisCase.where(GafisCase.caseId === gafisCaseFinger.caseId).headOption.get
    val gafisPerson = GafisPerson.where(GafisPerson.personid === gafisCheckinInfo.tcode).headOption.get

    val ltHitResultPackage = new LtHitResultPackage
    var ora_sid = oraSid
    while (ora_sid.length<23){
      ora_sid = "0" + ora_sid
    }
    ltHitResultPackage.taskId = ora_sid
    ltHitResultPackage.comparisonSystemTypeDescript = fpt4code.GAIMG_CPRMETHOD_EGFS_CODE
    ltHitResultPackage.latentFingerCaseId = gafisCase.caseSystemId
    ltHitResultPackage.latentFingerOriginalSystemCaseId = gafisCase.caseId
    ltHitResultPackage.latentFingerLatentSurveyId = gafisCase.sceneSurveyId
    ltHitResultPackage.latentFingerOriginalSystemFingerId = gafisCheckinInfo.code
    ltHitResultPackage.latentFingerLatentPhysicalId = GafisCaseFinger.where(GafisCaseFinger.fingerId === gafisCheckinInfo.code).headOption.get.physicalEvidenceNo
    ltHitResultPackage.latentFingerCardId = gafisCaseFinger.caseId
    ltHitResultPackage.fingerPrintOriginalSystemPersonId  = gafisPerson.personid
    ltHitResultPackage.fingerPrintJingZongPersonId = gafisPerson.jingZongPersonId
    ltHitResultPackage.fingerPrintPersonId = gafisPerson.casePersonid
    ltHitResultPackage.fingerPrintCardId = gafisPerson.personid
    var fptFgp = ""
    if(gafisCheckinInfo.fgp.toInt > 10){
      fptFgp = CodeConverterV70New.converFingerFgp(CodeConverterV70New.PLANE_FINGER,(gafisCheckinInfo.fgp.toInt -10).toString)
    }else{
      fptFgp = CodeConverterV70New.converFingerFgp(CodeConverterV70New.PLANE_FINGER,gafisCheckinInfo.fgp)
    }
    ltHitResultPackage.fingerPrintPostionCode = fptFgp
    gafisCheckinInfo.querytype.toString match {
      case fpt5util.QUERY_TYPE_TL => //捺印倒查(TL)
        ltHitResultPackage.fingerPrintComparisonMethodCode = fpt5util.QUERY_TYPE_TL
      case fpt5util.QUERY_TYPE_LT => //现场查案(LT)
        ltHitResultPackage.fingerPrintComparisonMethodCode = fpt5util.QUERY_TYPE_LT
      case _ =>
    }
    ltHitResultPackage.hitUnitCode = gafisCheckinInfo.registerOrg
    ltHitResultPackage.hitUnitName = SysDepart.find_by_code(gafisCheckinInfo.registerOrg).headOption.get.name
    ltHitResultPackage.hitPersonName = SysUser.find_by_pkId(gafisCheckinInfo.registerUser).headOption.get.trueName
    ltHitResultPackage.hitPersonIdCard = SysUser.find_by_pkId(gafisCheckinInfo.registerUser).headOption.get.idcard
    ltHitResultPackage.hitPersonTel = SysUser.find_by_pkId(gafisCheckinInfo.registerUser).headOption.get.phone
    ltHitResultPackage.hitDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(gafisCheckinInfo.registerTime)

    ltHitResultPackage.checkUnitCode = gafisCheckinInfo.reviewOrg
    ltHitResultPackage.checkUnitName = SysDepart.find_by_code(gafisCheckinInfo.reviewOrg).headOption.get.name
    ltHitResultPackage.checkPersonName = SysUser.find_by_pkId(GafisCheckinReview.where(GafisCheckinReview.checkInId === gafisCheckinInfo.pkId).headOption.get.reviewUser).headOption.get.trueName
    ltHitResultPackage.checkPersonIdCard = SysUser.find_by_pkId(GafisCheckinReview.where(GafisCheckinReview.checkInId === gafisCheckinInfo.pkId).headOption.get.reviewUser).headOption.get.idcard
    ltHitResultPackage.checkPersonTel = SysUser.find_by_pkId(GafisCheckinReview.where(GafisCheckinReview.checkInId === gafisCheckinInfo.pkId).headOption.get.reviewUser).headOption.get.phone
    ltHitResultPackage.checkDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(GafisCheckinReview.where(GafisCheckinReview.checkInId === gafisCheckinInfo.pkId).headOption.get.reviewTime)
    ltHitResultPackage.memo = ""

    val ltHitResultPackageSeq = new ArrayBuffer[LtHitResultPackage]
    ltHitResultPackageSeq += ltHitResultPackage
    ltHitResultPackageSeq
  }

  /**
    * 获取串查比中关系
    *
    * @param cardId
    * @return
    */
  override def getLlHitResultPackage(cardId: String): Seq[LlHitResultPackage] = {

    val llHitResultPackageSeq = new ArrayBuffer[LlHitResultPackage]
    GafisNormalqueryQueryque.where(GafisNormalqueryQueryque.keyid === cardId).foreach{
      t =>
        llHitResultPackageSeq += getLlHitResultPackageByOraSid(t.oraSid.toString).head
    }
    llHitResultPackageSeq
  }


  /**
    * 获取串查比中关系
    *
    * @param oraSid 任务号
    * @return
    */
  override def getLlHitResultPackageByOraSid(oraSid: String): Seq[LlHitResultPackage] = {
    val gafisNormalqueryQueryque = GafisNormalqueryQueryque.where(GafisNormalqueryQueryque.oraSid === oraSid).headOption.get
    val gafisCheckinInfo = GafisCheckinInfo.where(GafisCheckinInfo.queryUUID === gafisNormalqueryQueryque.pkId).headOption.get

    val gafisCaseFingerSource = GafisCaseFinger.where(GafisCaseFinger.fingerId === gafisCheckinInfo.code).headOption.get
    val gafisCaseFingerDest = GafisCaseFinger.where(GafisCaseFinger.fingerId === gafisCheckinInfo.tcode).headOption.get
    val gafisCaseSource = GafisCase.where(GafisCase.caseId === gafisCaseFingerSource.caseId).headOption.get
    val gafisCaseDest = GafisCase.where(GafisCase.caseId === gafisCaseFingerDest.caseId).headOption.get



    val llHitResultPackage = new LlHitResultPackage
    var ora_sid = oraSid
    while (ora_sid.length<23){
      ora_sid = "0" + ora_sid
    }
    llHitResultPackage.taskId = ora_sid
    llHitResultPackage.comparisonSystemTypeDescript = fpt4code.GAIMG_CPRMETHOD_EGFS_CODE
    llHitResultPackage.originalSystemCaseId = gafisCaseFingerSource.caseId
    llHitResultPackage.caseId = gafisCaseSource.caseSystemId
    llHitResultPackage.latentSurveyId = gafisCaseSource.sceneSurveyId
    llHitResultPackage.originalSystemLatentFingerId = gafisCaseFingerSource.fingerId
    llHitResultPackage.latentPhysicalId = gafisCaseFingerSource.physicalEvidenceNo
    llHitResultPackage.cardId = gafisCaseSource.caseId

    llHitResultPackage.resultOriginalSystemCaseId = gafisCaseFingerDest.caseId
    llHitResultPackage.resultCaseId = gafisCaseDest.caseSystemId
    llHitResultPackage.resultLatentSurveyId = gafisCaseDest.sceneSurveyId
    llHitResultPackage.resultOriginalSystemLatentPersonId = gafisCaseFingerDest.fingerId
    llHitResultPackage.resultLatentPhysicalId = gafisCaseFingerDest.physicalEvidenceNo
    llHitResultPackage.resultCardId = gafisCaseDest.caseId

    llHitResultPackage.hitUnitCode = gafisCheckinInfo.registerOrg
    llHitResultPackage.hitUnitName = SysDepart.find_by_code(gafisCheckinInfo.registerOrg).headOption.get.name
    llHitResultPackage.hitPersonName = SysUser.find_by_pkId(gafisCheckinInfo.registerUser).headOption.get.trueName
    llHitResultPackage.hitPersonIdCard = SysUser.find_by_pkId(gafisCheckinInfo.registerUser).headOption.get.idcard
    llHitResultPackage.hitPersonTel = SysUser.find_by_pkId(gafisCheckinInfo.registerUser).headOption.get.phone
    llHitResultPackage.hitDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(gafisCheckinInfo.registerTime)

    llHitResultPackage.checkUnitCode = gafisCheckinInfo.reviewOrg
    llHitResultPackage.checkUnitName = SysDepart.find_by_code(gafisCheckinInfo.reviewOrg).headOption.get.name
    llHitResultPackage.checkPersonName = SysUser.find_by_pkId(GafisCheckinReview.where(GafisCheckinReview.checkInId === gafisCheckinInfo.pkId).headOption.get.reviewUser).headOption.get.trueName
    llHitResultPackage.checkPersonIdCard = SysUser.find_by_pkId(GafisCheckinReview.where(GafisCheckinReview.checkInId === gafisCheckinInfo.pkId).headOption.get.reviewUser).headOption.get.idcard
    llHitResultPackage.checkPersonTel = SysUser.find_by_pkId(GafisCheckinReview.where(GafisCheckinReview.checkInId === gafisCheckinInfo.pkId).headOption.get.reviewUser).headOption.get.phone
    llHitResultPackage.checkDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(GafisCheckinReview.where(GafisCheckinReview.checkInId === gafisCheckinInfo.pkId).headOption.get.reviewTime)
    llHitResultPackage.memo = ""

    val llHitResultPackageSeq = new ArrayBuffer[LlHitResultPackage]
    llHitResultPackageSeq += llHitResultPackage
    llHitResultPackageSeq
  }

  /**
    * 获取正查或倒查比中关系
    *
    * @param cardId   现场指纹编号
    * @param isLatent 是否现场
    * @return
    */
  override def getLogic04Rec(cardId: String, isLatent: Boolean): Seq[Logic04Rec] = ???

  /**
    * 获取重卡比中关系
    *
    * @param cardId
    * @return
    */
override def getLogic05Rec(cardId: String): Seq[Logic05Rec] = ???

  /**
    * 获取串查比中关系
    *
    * @param cardId
    * @return
    */
  override def getLogic06Rec(cardId: String): Seq[Logic06Rec] = ???
}
