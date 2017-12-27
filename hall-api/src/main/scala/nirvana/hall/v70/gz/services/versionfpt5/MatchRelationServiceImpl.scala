package nirvana.hall.v70.gz.services.versionfpt5

import java.text.SimpleDateFormat

import nirvana.hall.api.internal.DateConverter
import nirvana.hall.api.internal.fpt.CodeConverterV70New
import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gfpt4lib.FPT4File.{Logic04Rec, Logic05Rec, Logic06Rec}
import nirvana.hall.c.services.gfpt5lib.{LlHitResultPackage, LtHitResultPackage, TtHitResultPackage, fpt5util}
import nirvana.hall.protocol.api.FPTProto.{FingerFgp, MatchRelationInfo}
import nirvana.hall.protocol.api.HallMatchRelationProto.{MatchRelationGetRequest, MatchRelationGetResponse, MatchStatus}
import nirvana.hall.protocol.fpt.MatchRelationProto._
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v70.gz.jpa._
import nirvana.hall.v70.internal.query.QueryConstants
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.gz.jpa.GafisCheckinInfo

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
  override def getTtHitResultPackage(cardId: String): Seq[TtHitResultPackage] = ???

  /**
    * 获取正查或倒查比中关系
    * @param cardId 现场指纹编号
    * @param isLatent
    * @return
    */
  override def getLtHitResultPackage(cardId: String, isLatent: Boolean): Seq[LtHitResultPackage] = ???


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
    ltHitResultPackage.comparisonSystemTypeDescript = fpt4code.GAIMG_CPRMETHOD_EGFS_CODE
    ltHitResultPackage.latentFingerCaseId = gafisCase.caseSystemId
    ltHitResultPackage.latentFingerOriginalSystemCaseId = gafisCase.caseId
    ltHitResultPackage.latentFingerLatentSurveyId = gafisCase.sceneSurveyId
    ltHitResultPackage.latentFingerOriginalSystemFingerId = gafisCheckinInfo.code
    ltHitResultPackage.latentFingerLatentPhysicalId = GafisCaseFinger.where(GafisCaseFinger.fingerId === gafisCheckinInfo.code).headOption.get.physicalEvidenceNo
    ltHitResultPackage.latentFingerCardId = gafisCase.caseId
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
  override def getLlHitResultPackage(cardId: String): Seq[LlHitResultPackage] = ???

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
