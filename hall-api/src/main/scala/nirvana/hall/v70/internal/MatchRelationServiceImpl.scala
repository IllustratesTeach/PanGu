package nirvana.hall.v70.internal

import nirvana.hall.api.internal.DateConverter
import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.protocol.api.FPTProto.FingerFgp
import nirvana.hall.protocol.api.HallMatchRelationProto.{MatchRelationGetRequest, MatchRelationGetResponse, MatchStatus}
import nirvana.hall.protocol.fpt.MatchRelationProto.{MatchRelation, MatchRelationTLAndLT, MatchRelationTT, MatchSysInfo}
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v70.internal.query.QueryConstants
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
}
