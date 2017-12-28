package nirvana.hall.v70.internal


import nirvana.hall.api.internal.DateConverter
import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.c.services.gfpt4lib.FPT4File.{Logic04Rec, Logic05Rec, Logic06Rec}
import nirvana.hall.c.services.gfpt5lib.{LlHitResultPackage, LtHitResultPackage, TtHitResultPackage}
import nirvana.hall.protocol.api.FPTProto.{FingerFgp, MatchRelationInfo}
import nirvana.hall.protocol.api.HallMatchRelationProto.{MatchRelationGetRequest, MatchRelationGetResponse, MatchStatus}
import nirvana.hall.protocol.fpt.MatchRelationProto._
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v70.internal.query.QueryConstants
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.GafisCheckinInfo


/**
 * Created by songpeng on 16/9/21.
 */
class MatchRelationServiceImpl(fptService: FPTService) extends MatchRelationService{
  /**
   * 获取比对关系
   * 查询比中关系表获取比中关系信息
    *
    * @param request
   * @return
   */
  override def getMatchRelation(request: MatchRelationGetRequest): MatchRelationGetResponse = {
    val matchType = request.getMatchType
    val cardId = request.getCardId
    val response = MatchRelationGetResponse.newBuilder()
    response.setMatchType(matchType)
    response.setMatchStatus(MatchStatus.UN_KNOWN)//默认状态未知
    matchType match {
      case MatchType.FINGER_TT =>
        val checkinInfoList = GafisCheckinInfo.find_by_querytype_and_confirmStatus_and_code(QueryConstants.QUERY_TYPE_TT.toShort, QueryConstants.CONFIRM_STATUS_YES, cardId)
        checkinInfoList.foreach{checkinInfo =>
          val tt = MatchRelationTT.newBuilder()
          tt.setPersonId1(checkinInfo.code)
          tt.setPersonId2(checkinInfo.tcode)
          val matchRelation = MatchRelation.newBuilder()
          //比中登记系统信息
          val matchSysInfo = MatchSysInfo.newBuilder()
          matchSysInfo.setMatchUnitCode(checkinInfo.registerOrg)
          matchSysInfo.setMatchUnitName("")
          matchSysInfo.setMatcher(checkinInfo.registerUser)
          matchSysInfo.setMatchDate(DateConverter.convertDate2String(checkinInfo.registerTime))

          matchRelation.setMatchSysInfo(matchSysInfo)
          matchRelation.setExtension(MatchRelationTT.data, tt.build())

          response.setMatchStatus(MatchStatus.CHECKED)//TT没有复核
          response.addMatchRelation(matchRelation.build())
        }
      case MatchType.FINGER_TL =>
        val checkinInfoList = GafisCheckinInfo.find_by_querytype_and_reviewStatus_and_tcode(QueryConstants.QUERY_TYPE_TL.toShort, QueryConstants.REVIEW_STATUS_YES, cardId)
        checkinInfoList.foreach{checkinInfo =>
          val code = checkinInfo.code
          val tl = MatchRelationTLAndLT.newBuilder()
          tl.setCaseId(code.substring(0, code.length-2))
          tl.setPersonId(checkinInfo.tcode)
          tl.setSeqNo(checkinInfo.code.substring(code.length-2))
          val fgpInt = checkinInfo.fgp.toInt
          tl.setFpg(FingerFgp.valueOf(if(fgpInt > 10) fgpInt - 10 else fgpInt))
          tl.setCapture(if("1".equals(checkinInfo.personContrDeltag)) true else false)//???

          val matchRelation = MatchRelation.newBuilder()
          //比中登记系统信息
          val matchSysInfo = MatchSysInfo.newBuilder()
          matchSysInfo.setMatchUnitCode(checkinInfo.registerOrg)
          matchSysInfo.setMatchUnitName("")
          matchSysInfo.setMatcher(checkinInfo.registerUser)
          matchSysInfo.setMatchDate(DateConverter.convertDate2String(checkinInfo.registerTime))

          matchRelation.setMatchSysInfo(matchSysInfo)
          matchRelation.setExtension(MatchRelationTLAndLT.data, tl.build())

          response.setMatchStatus(MatchStatus.RECHECKED)
          response.addMatchRelation(matchRelation.build())
        }

      case MatchType.FINGER_LT =>
        val checkinInfoList = GafisCheckinInfo.find_by_querytype_and_reviewStatus_and_code(QueryConstants.QUERY_TYPE_LT.toShort, QueryConstants.REVIEW_STATUS_YES, cardId).headOption
        checkinInfoList.foreach{checkinInfo =>
          val code = checkinInfo.code
          val tl = MatchRelationTLAndLT.newBuilder()
          tl.setCaseId(code.substring(0, code.length-2))
          tl.setPersonId(checkinInfo.tcode)
          tl.setSeqNo(checkinInfo.code.substring(code.length-2))
          val fgpInt = checkinInfo.fgp.toInt
          tl.setFpg(FingerFgp.valueOf(if(fgpInt > 10) fgpInt - 10 else fgpInt))
          tl.setCapture(if("1".equals(checkinInfo.personContrDeltag)) true else false)//???

          val matchRelation = MatchRelation.newBuilder()
          //比中登记系统信息
          val matchSysInfo = MatchSysInfo.newBuilder()
          matchSysInfo.setMatchUnitCode(checkinInfo.registerOrg)
          matchSysInfo.setMatchUnitName("")
          matchSysInfo.setMatcher(checkinInfo.registerUser)
          matchSysInfo.setMatchDate(DateConverter.convertDate2String(checkinInfo.registerTime))

          matchRelation.setMatchSysInfo(matchSysInfo)
          matchRelation.setExtension(MatchRelationTLAndLT.data, tl.build())

          response.setMatchStatus(MatchStatus.RECHECKED)
          response.addMatchRelation(matchRelation.build())
        }
      case MatchType.FINGER_LL =>
        throw new UnsupportedOperationException
    }

    response.build()
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
    ProtobufConverter.convertGafisCheckInfo2MatchSysInfo(gafisCheckinInfo)
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
  override def getLtHitResultPackageByOraSid(oraSid: String): Seq[LtHitResultPackage] = ???

  /**
    * 获取串查比中关系
    *
    * @param cardId
    * @return
    */
  override def getLlHitResultPackage(cardId: String): Seq[LlHitResultPackage] = ???

  /**
    * 获取串查比中关系
    *
    * @param oraSid 任务号
    * @return
    */
  override def getLlHitResultPackageByOraSid(oraSid: String): Seq[LlHitResultPackage] = ???

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

  /**
    * 获取重卡比中关系
    *
    * @param oraSid 任务号
    * @return
    */
  override def getTtHitResultPackageByOraSid(oraSid: String): Seq[TtHitResultPackage] = ???
}
