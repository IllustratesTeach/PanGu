package nirvana.hall.v70.internal

import java.text.SimpleDateFormat
import javax.activation.DataHandler

import nirvana.hall.api.internal.DateConverter
import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.c.services.gfpt4lib.FPT4File.{FPT4File, Logic02Rec, Logic03Rec}
import nirvana.hall.protocol.api.FPTProto.FingerFgp
import nirvana.hall.protocol.api.HallMatchRelationProto.{MatchRelationGetRequest, MatchRelationGetResponse, MatchStatus}
import nirvana.hall.protocol.fpt.MatchRelationProto.{MatchRelation, MatchRelationTLAndLT, MatchRelationTT, MatchSysInfo}
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v70.internal.query.QueryConstants
import nirvana.hall.v70.jpa.{GafisCaseFinger, GafisCheckinInfo, GafisGatherFinger, SysDepart}
import nirvana.hall.v70.services.service.GetPKIDService
import org.apache.axiom.attachments.ByteArrayDataSource

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/9/21.
 */
class MatchRelationServiceImpl(fptService: FPTService,getPKIDService: GetPKIDService) extends MatchRelationService{
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

  /**
    * 导出比对关系
    *
    * @param queryid
    * @param ora_sid
    * @return
    */
  override def exportMatchRelation(queryid:String,ora_sid:String): DataHandler = {
    val fPT4File = new FPT4File
    var dataHandler:DataHandler = null
    val pkidlist = getPKIDService.getDataInfo(queryid,ora_sid)
    for (i <- 0 to pkidlist.size - 1)
    {
      val gafisCheckinInfo = GafisCheckinInfo.find_by_pkId(pkidlist(i).get("pk_id").get.asInstanceOf[String])
      if(gafisCheckinInfo.nonEmpty){
        val gafisCaseFingerSource = GafisCaseFinger.find_by_fingerId(gafisCheckinInfo.headOption.get.code)
        val gafisCaseFingerDest = GafisCaseFinger.find_by_fingerId(gafisCheckinInfo.headOption.get.tcode)
        val gafisGatherFingerSource = GafisGatherFinger.find_by_personId(gafisCheckinInfo.headOption.get.code)
        val gafisGatherFingerDest = GafisGatherFinger.find_by_personId(gafisCheckinInfo.headOption.get.tcode)
        if(gafisCaseFingerSource.nonEmpty || gafisCaseFingerDest.nonEmpty || gafisGatherFingerSource.nonEmpty || gafisGatherFingerDest.nonEmpty){
          gafisCheckinInfo.headOption.get.querytype.toString match {
            case MatchRelationService.querytypeTT =>
              val logic02RecSource = fptService.getLogic02Rec(gafisGatherFingerSource.head.personId)
              val logic02RecDest = fptService.getLogic02Rec(gafisGatherFingerDest.head.personId)
              val logic05Rec = fptService.getLogic05Rec(pkidlist(i).get("pk_id").get.asInstanceOf[String])
              val logic02List = new ArrayBuffer[Logic02Rec]()
              logic02List += logic02RecSource
              logic02List += logic02RecDest
              fPT4File.logic02Recs = logic02List.toArray
              fPT4File.logic05Recs = Array(logic05Rec)
            case MatchRelationService.querytypeTL=>
              val logic03Rec = fptService.getLogic03Rec(gafisCaseFingerSource.head.caseId)
              val logic02Rec = fptService.getLogic02Rec(gafisGatherFingerDest.head.personId)
              val logic04Rec = fptService.getLogic04Rec(pkidlist(i).get("pk_id").get.asInstanceOf[String])
              fPT4File.logic03Recs = Array(logic03Rec)
              fPT4File.logic02Recs = Array(logic02Rec)
              fPT4File.logic04Recs = Array(logic04Rec)
            case MatchRelationService.querytypeLT=>
              val logic02Rec = fptService.getLogic02Rec(gafisGatherFingerSource.head.personId)
              val logic03Rec = fptService.getLogic03Rec(gafisCaseFingerDest.head.caseId)
              val logic04Rec = fptService.getLogic04Rec(pkidlist(i).get("pk_id").get.asInstanceOf[String])
              fPT4File.logic02Recs = Array(logic02Rec)
              fPT4File.logic03Recs = Array(logic03Rec)
              fPT4File.logic04Recs = Array(logic04Rec)
            case MatchRelationService.querytypeLL =>
              val logic03RecSource = fptService.getLogic03Rec(gafisCaseFingerSource.head.caseId)
              val logic03RecDest = fptService.getLogic03Rec(gafisCaseFingerDest.head.caseId)
              val logic06Rec = fptService.getLogic06Rec(pkidlist(i).get("pk_id").get.asInstanceOf[String])
              val logic03List = new ArrayBuffer[Logic03Rec]()
              logic03List += logic03RecSource
              logic03List += logic03RecDest
              fPT4File.logic03Recs = logic03List.toArray
              fPT4File.logic06Recs = Array(logic06Rec)
            case _ =>
              throw new Exception("queryType error:" + gafisCheckinInfo.headOption.get.querytype)
          }
        }
    }
      dataHandler = new DataHandler(new ByteArrayDataSource(fPT4File.build().toByteArray()))
    }
    dataHandler
  }

  /**
    * 获取查询的比对关系
    *
    * @param pkid
    * @return
    */
  override def getSearchMatchRelation(pkid: String): GafisMatchInfo = {
    val gafisCheckinInfo = GafisCheckinInfo.find_by_pkId(pkid)
    val gafisMatchInfo = new GafisMatchInfo
    val df = new SimpleDateFormat("yyyyMMdd")
    gafisMatchInfo.code = gafisCheckinInfo.headOption.get.code
    gafisMatchInfo.tcode = gafisCheckinInfo.headOption.get.tcode
    gafisMatchInfo.registerOrg = gafisCheckinInfo.headOption.get.registerOrg
    gafisMatchInfo.registerUser = gafisCheckinInfo.headOption.get.registerUser
    gafisMatchInfo.registerTime = df.format(gafisCheckinInfo.headOption.get.registerTime)
    gafisMatchInfo.querytype = gafisCheckinInfo.headOption.get.querytype.toString
    gafisMatchInfo.fgp = gafisCheckinInfo.headOption.get.fgp
    gafisMatchInfo.matchName = SysDepart.find_by_code(gafisMatchInfo.registerOrg).headOption.getOrElse(gafisMatchInfo.registerOrg).toString
    gafisMatchInfo
  }
}
