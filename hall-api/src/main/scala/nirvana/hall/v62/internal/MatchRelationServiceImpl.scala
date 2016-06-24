package nirvana.hall.v62.internal

import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.c.services.gfpmanager.GfpManagerConst.Gf_AssociateGroupInfo
import nirvana.hall.c.services.gloclib.galoclog.GAFIS_VERIFYLOGSTRUCT
import nirvana.hall.c.services.gloclib.galoctp.GTPCARDINFOSTRUCT
import nirvana.hall.protocol.api.HallMatchRelationProto.{MatchRelationGetRequest, MatchRelationGetResponse}
import nirvana.hall.protocol.fpt.MatchRelationProto.{MatchRelationTLAndLT, MatchRelation}
import nirvana.hall.protocol.fpt.TypeDefinitionProto.{FingerFgp, MatchType}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gloclib.gcolnames._

/**
 * Created by songpeng on 16/6/21.
 */
class MatchRelationServiceImpl(v62Config: HallV62Config, facade: V62Facade) extends MatchRelationService{
  /**
   * 获取比对关系
   * @param request
   * @return
   */
  override def getMatchRelation(request: MatchRelationGetRequest): MatchRelationGetResponse = {
    val reponse = MatchRelationGetResponse.newBuilder()
    val baseRelation = MatchRelation.newBuilder()
    //TODO 查询相关信息
    val matchSysInfo = baseRelation.getMatchSysInfoBuilder()
    matchSysInfo.setMatchUnitCode("100000000000")
    matchSysInfo.setMatchUnitName("上海市公安局")
    matchSysInfo.setMatcher("match")
    matchSysInfo.setMatchDate("20160501")
    matchSysInfo.setRemark("remark")
    matchSysInfo.setInputUnitCode("123456789012")
    matchSysInfo.setInputUnitName("东方金指")
    matchSysInfo.setInputer("sp")
    matchSysInfo.setInputDate("20160501")
    matchSysInfo.setApprover("jcai")
    matchSysInfo.setApproveDate("20160501")
    matchSysInfo.setRecheckUnitCode("20000000000")
    matchSysInfo.setRechecker("复核")
    matchSysInfo.setRecheckDate("20160501")
    val cardId = request.getCardId
    request.getMatchType match {
      case MatchType.FINGER_TL =>
        //倒查直接查询比中关系表
        //TODO 如何根据现场指纹获取案件编号和指纹序号
        val matchInfo = queryMatchInfo(None, 1)
        matchInfo.foreach{ verifyLog: GAFIS_VERIFYLOGSTRUCT=>
          val matchRelationTL = MatchRelationTLAndLT.newBuilder()
          matchRelationTL.setPersonId(verifyLog.szSrcKey)
          matchRelationTL.setCaseId(verifyLog.szDestKey)
          matchRelationTL.setFpg(FingerFgp.valueOf(verifyLog.nFg))
          matchRelationTL.setSeqNo("01")
          matchRelationTL.setCapture(verifyLog.bIsCrimeCaptured > 0)
          baseRelation.setExtension(MatchRelationTLAndLT.data, matchRelationTL.build())
        }
      case MatchType.FINGER_TT =>
        //重卡信息先从捺印表查到重卡组号，然后根据重卡组号查询重卡信息
        //TODO 人员编号对应的Key
        val tp = new GTPCARDINFOSTRUCT
        facade.NET_GAFIS_FLIB_Get(v62Config.templateTable.dbId.toShort, v62Config.templateTable.tableId.toShort, cardId, tp, null, 3)
        if(tp.stAdmData.szPersonID.nonEmpty){
          val mapper = Map(
            g_stCN.stTPnID.pszName -> "szGroupID",
            g_stCN.stTCardCount.pszName -> "nTprCardCnt"
          )
          val gfGroupInfo = facade.queryV62Table[Gf_AssociateGroupInfo](1, 3, mapper, None, 3)

          println(gfGroupInfo)
        }
    }
    reponse.addMatchRelation(baseRelation.build())
    reponse.setMatchType(request.getMatchType)

    reponse.build()
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
}
