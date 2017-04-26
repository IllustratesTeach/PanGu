package nirvana.hall.v62.internal

import javax.activation.DataHandler

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.DateConverter
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.{LPCardService, MatchRelationService, QueryService}
import nirvana.hall.c.services.gfpt4lib.FPT4File.{FPT4File, Logic02Rec, Logic03Rec}
import nirvana.hall.c.services.gloclib.galoclog.GAFIS_VERIFYLOGSTRUCT
import nirvana.hall.c.services.gloclib.galoctp._
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYCANDSTRUCT
import nirvana.hall.protocol.api.HallMatchRelationProto.{MatchRelationGetRequest, MatchRelationGetResponse, MatchStatus}
import nirvana.hall.protocol.fpt.MatchRelationProto.{MatchRelation, MatchRelationTLAndLT, MatchRelationTT, MatchSysInfo}
import nirvana.hall.protocol.fpt.TypeDefinitionProto.{FingerFgp, MatchType}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gloclib.gcolnames._
import nirvana.hall.v62.services.service.GetPKIDService
import org.apache.tapestry5.ioc.internal.util.InternalUtils
import org.apache.axiom.attachments.ByteArrayDataSource
import org.jboss.netty.buffer.ChannelBuffers

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/6/21.
 */
class MatchRelationServiceImpl(v62Config: HallV62Config, facade: V62Facade, lPCardService: LPCardService, queryService: QueryService,getPKIDService: GetPKIDService,fptService: FPTService) extends MatchRelationService with LoggerSupport{
  /**
   * 获取比对关系
    *
    * @param request
   * @return
   */
  override def getMatchRelation(request: MatchRelationGetRequest): MatchRelationGetResponse = {
    val matchType = request.getMatchType
    val response = MatchRelationGetResponse.newBuilder()
    response.setMatchType(matchType)
    val cardId = request.getCardId
    //获取比对状态
    val status = queryService.findFirstQueryStatusByCardIdAndMatchType(cardId, matchType)
    response.setMatchStatus(status)
    matchType match {
      case MatchType.FINGER_TL =>
        if(status == MatchStatus.RECHECKED){//倒查复核后生成比对关系
        //倒查直接查询比中关系表
        val statement = Option("(SrcKey='%s')".format(cardId))
          val matchInfo = queryMatchInfo(statement, 1)
          matchInfo.foreach{ verifyLog: GAFIS_VERIFYLOGSTRUCT=>
            val matchRelationTL = MatchRelationTLAndLT.newBuilder()
            matchRelationTL.setPersonId(verifyLog.szSrcKey)
            matchRelationTL.setFpg(FingerFgp.valueOf(verifyLog.nFg))
            matchRelationTL.setCapture(verifyLog.bIsCrimeCaptured > 0)

            //根据现场卡号查询案件卡号，seqno信息
            val fingerId = verifyLog.szDestKey
            val lpCard = lPCardService.getLPCard(fingerId)
            val caseId = lpCard.getText.getStrCaseId
            matchRelationTL.setCaseId(caseId)
            matchRelationTL.setSeqNo(lpCard.getText.getStrSeq)

            val matchSysInfo = MatchSysInfo.newBuilder()
            matchSysInfo.setMatchUnitCode(verifyLog.szSubmitUserUnitCode)
            matchSysInfo.setMatchUnitName("")
            matchSysInfo.setMatcher(verifyLog.szSubmitUserName)
            matchSysInfo.setMatchDate(DateConverter.convertAFISDateTime2String(verifyLog.tSubmitDateTime).substring(0, 8))
            matchSysInfo.setInputer(verifyLog.szBreakUserName)
            matchSysInfo.setInputUnitCode(verifyLog.szBreakUserUnitCode)
            matchSysInfo.setInputDate(DateConverter.convertAFISDateTime2String(verifyLog.tBreakDateTime).substring(0, 8))
            matchSysInfo.setApprover(verifyLog.szReCheckUserName)
            matchSysInfo.setApproveDate(DateConverter.convertAFISDateTime2String(verifyLog.tReCheckDateTime).substring(0, 8))

            val matchRelation = MatchRelation.newBuilder()
            matchRelation.setMatchSysInfo(matchSysInfo)
            matchRelation.setExtension(MatchRelationTLAndLT.data, matchRelationTL.build())

            response.addMatchRelation(matchRelation.build())
          }
        }
      case MatchType.FINGER_TT =>
        //查重核查完毕后生成比对关系
        if(status.getNumber == MatchStatus.CHECKED.getNumber){
          //重卡信息先从捺印表查到重卡组号，然后根据重卡组号查询重卡信息
          val tp = new GTPCARDINFOSTRUCT
          facade.NET_GAFIS_FLIB_Get(v62Config.templateTable.dbId.toShort, v62Config.templateTable.tableId.toShort, cardId, tp, null, 3)
          val personId = tp.stAdmData.szPersonID.trim //重卡组号
          //如果没有重卡组号不获取重卡信息
          if (InternalUtils.isNonBlank(personId)){
            val m_stPersonInfo = new GPERSONINFOSTRUCT
            m_stPersonInfo.szPersonID = personId
            m_stPersonInfo.nItemFlag = (GPIS_ITEMFLAG_CARDCOUNT | GPIS_ITEMFLAG_CARDID | GPIS_ITEMFLAG_TEXT).toByte
            m_stPersonInfo.nItemFlag2 = (GPIS_ITEMFLAG2_LPGROUPDBID | GPIS_ITEMFLAG2_LPGROUPTID | GPIS_ITEMFLAG2_FLAG).toByte
            val dbId: Short = 1
            val tableId: Short = 3
            facade.NET_GAFIS_PERSON_Get(dbId, tableId, m_stPersonInfo)
            m_stPersonInfo.pstID_Data.foreach{personInfo =>
              val tt = MatchRelationTT.newBuilder()
              tt.setPersonId1(cardId)
              tt.setPersonId2(personInfo.szCardID)
              //TT没有单位信息
              val matchSysInfo = MatchSysInfo.newBuilder()
              matchSysInfo.setMatchUnitCode("")
              matchSysInfo.setMatchUnitName("")
              matchSysInfo.setMatcher(personInfo.szUserName)
              matchSysInfo.setMatchDate(DateConverter.convertAFISDateTime2String(personInfo.tCheckTime).substring(0, 8))
              val matchRelation = MatchRelation.newBuilder()
              matchRelation.setMatchSysInfo(matchSysInfo)
              matchRelation.setExtension(MatchRelationTT.data, tt.build())

              response.addMatchRelation(matchRelation.build())
            }
          }
        }
      case other =>
        warn("unsupport matchType:{}", matchType)
    }

    response.build()
  }
  /**
   * 查询比中关系
   *
   * @param statementOpt 查询语句
   * @param limit 抓取多少条
   * @return 查询结构
   */
  private def queryMatchInfo(statementOpt:Option[String],limit:Int): List[GAFIS_VERIFYLOGSTRUCT]={
    val mapper = Map(
      g_stCN.stBc.pszBreakID->"szBreakID",
      g_stCN.stBc.pszSrcKey->"szSrcKey",
      g_stCN.stBc.pszDestKey->"szDestKey",
      g_stCN.stBc.pszScore->"nScore",
      g_stCN.stBc.pszFirstRankScore->"nFirstRankScore",
      g_stCN.stBc.pszRank->"nRank",
      g_stCN.stBc.pszFg->"nFg",
      g_stCN.stBc.pszHitPoss->"nHitPoss",
      g_stCN.stBc.pszIsRemoteSearched->"bIsRmtSearched",
      g_stCN.stBc.pszIsCrimeCaptured->"bIsCrimeCaptured",
      g_stCN.stBc.pszTotoalMatchedCnt->"nTotalMatchedCnt",
      g_stCN.stBc.pszQueryType->"nQueryType",
      g_stCN.stBc.pszSrcDBID->"nSrcDBID",
      g_stCN.stBc.pszDestDBID->"nDestDBID",
      g_stCN.stBc.pszSrcPersonCaseID->"szSrcPersonCaseID",
      g_stCN.stBc.pszDestPersonCaseID->"szDestPersonCaseID",
      g_stCN.stBc.pszCaseClassCode1->"szCaseClassCode1",
      g_stCN.stBc.pszCaseClassCode2->"szCaseClassCode2",
      g_stCN.stBc.pszCaseClassCode3->"szCaseClassCode3",
      g_stCN.stBc.pszSubmitUserName->"szSubmitUserName",
      g_stCN.stBc.pszSubmitUserUnitCode->"szSubmitUserUnitCode",
      g_stCN.stBc.pszSubmitDateTime->"tSubmitDateTime",
      g_stCN.stBc.pszBreakUserName->"szBreakUserName",
      g_stCN.stBc.pszBreakUserUnitCode->"szBreakUserUnitCode",
      g_stCN.stBc.pszBreakDateTime->"tBreakDateTime",
      g_stCN.stBc.pszReCheckUserName->"szReCheckUserName",
      g_stCN.stBc.pszReCheckerUnitCode->"szReCheckerUnitCode",
      g_stCN.stBc.pszReCheckDate->"tReCheckDateTime",
      g_stCN.stNuminaCol.pszSID->"nSID"
    )

    val dbDef = facade.NET_GAFIS_MISC_GetDefDBID()
    facade.queryV62Table[GAFIS_VERIFYLOGSTRUCT](
      dbDef.nAdminDefDBID,
      g_stCN.stSysAdm.nTIDBreakCaseTable,
      mapper,
      statementOpt,
      limit)
  }

  /**
    * 导出比对关系
    *
    * @param code
    * @return
    */
  override def exportMatchRelation(queryid:String,ora_sid:String): DataHandler = {
    val fPT4File = new FPT4File
    var dataHandler:DataHandler = null
    val pkidlist = getPKIDService.getDataInfo(queryid,ora_sid)
    for (i <- 0 to pkidlist.size - 1)
    {
      if(pkidlist(i).get("candlist").size>0){
        val byteVal = pkidlist(i).get("candlist").get.asInstanceOf[Array[Byte]]
        val buffer = ChannelBuffers.wrappedBuffer(byteVal)
        while(buffer.readableBytes() >= 96) {
          val gaCand = new GAQUERYCANDSTRUCT
          gaCand.fromStreamReader(buffer)
          exportImplMRELATION(fPT4File,pkidlist(i).get("keyid").get.asInstanceOf[String],pkidlist(i).get("querytype").get.asInstanceOf[String],gaCand.szKey,pkidlist(i).get("ora_uuid").get.asInstanceOf[String])
        }
      }
    dataHandler = new DataHandler(new ByteArrayDataSource(fPT4File.build().toByteArray()))
    }
    dataHandler
  }

  /**
    * 实现导出比对关系
    * @param fPT4File
    * @param code
    * @param querytype
    * @param tcode
    * @param uuid
    */
  def exportImplMRELATION(fPT4File:FPT4File,code:String,querytype:String,tcode:String,uuid:String):Unit={

    querytype match{
      case MatchRelationService.querytypeTT =>
        val logic02RecSource = fptService.getLogic02Rec(code)
        val logic02RecDest = fptService.getLogic02Rec(tcode)
        val logic05Rec = fptService.getLogic05Rec(uuid)
        val logic02List = new ArrayBuffer[Logic02Rec]()
        logic02List += logic02RecSource
        logic02List += logic02RecDest
        fPT4File.logic02Recs = logic02List.toArray
        fPT4File.logic05Recs = Array(logic05Rec)
      case MatchRelationService.querytypeTL=>
        val logic03Rec = fptService.getLogic03Rec(code)
        val logic02Rec = fptService.getLogic02Rec(tcode)
        val logic04Rec = fptService.getLogic04Rec(uuid)
        fPT4File.logic03Recs = Array(logic03Rec)
        fPT4File.logic02Recs = Array(logic02Rec)
        fPT4File.logic04Recs = Array(logic04Rec)
      case MatchRelationService.querytypeLT=>
        val logic02Rec = fptService.getLogic02Rec(code)
        val logic03Rec = fptService.getLogic03Rec(tcode)
        val logic04Rec = fptService.getLogic04Rec(uuid)
        fPT4File.logic02Recs = Array(logic02Rec)
        fPT4File.logic03Recs = Array(logic03Rec)
        fPT4File.logic04Recs = Array(logic04Rec)
      case MatchRelationService.querytypeLL =>
        val logic03RecSource = fptService.getLogic03Rec(code)
        val logic03RecDest = fptService.getLogic03Rec(tcode)
        val logic06Rec = fptService.getLogic06Rec(uuid)
        val logic03List = new ArrayBuffer[Logic03Rec]()
        logic03List += logic03RecSource
        logic03List += logic03RecDest
        fPT4File.logic03Recs = logic03List.toArray
        fPT4File.logic06Recs = Array(logic06Rec)
      case _ =>
        throw new Exception("queryType error:" + querytype)
    }
  }

  /**
    * 获取查询的比对关系
    *
    * @param pkid
    * @return
    */
  override def getSearchMatchRelation(pkid: String): GafisMatchInfo = {
    val gafisMatchInfo = new GafisMatchInfo
    val pkidlist = getPKIDService.getDatabyPKIDInfo(pkid)
    for (i <- 0 to pkidlist.size - 1)
    {
      gafisMatchInfo.code = pkidlist(0).get("keyid").get.asInstanceOf[String]
      gafisMatchInfo.registerOrg = pkidlist(0).get("checkerunitcode").get.asInstanceOf[String]
      gafisMatchInfo.registerUser = pkidlist(0).get("checkusername").get.asInstanceOf[String]
      gafisMatchInfo.registerTime = pkidlist(0).get("checktime").get.toString
      gafisMatchInfo.querytype = pkidlist(0).get("querytype").get.asInstanceOf[String]

      val byteVal = pkidlist(0).get("candlist").get.asInstanceOf[Array[Byte]]
      val buffer = ChannelBuffers.wrappedBuffer(byteVal)
      while(buffer.readableBytes() >= 96) {
        val gaCand = new GAQUERYCANDSTRUCT
        gaCand.fromStreamReader(buffer)
        gafisMatchInfo.tcode = gaCand.szKey
        gafisMatchInfo.fgp = gaCand.nIndex.toString
      }
    }
    gafisMatchInfo
  }
}
