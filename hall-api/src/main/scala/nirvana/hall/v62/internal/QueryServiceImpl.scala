package nirvana.hall.v62.internal

import nirvana.hall.api.config.{DBConfig, QueryDBConfig}
import nirvana.hall.api.services.QueryService
import nirvana.hall.c.services.ganumia.gadbdef.GADB_KEYARRAY
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYSIMPSTRUCT
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.api.QueryProto.{QuerySendRequest, QuerySendResponse}
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gloclib.{gaqryqueConverter, gcolnames}

/**
 * Created by songpeng on 16/1/26.
 */
class QueryServiceImpl(facade:V62Facade, config:HallV62Config) extends QueryService{


  /**
    * 通过卡号查找第一个的比中结果
    *
    * @param cardId 卡号
    * @return 比对结果
    */
  override def findFirstQueryResultByCardId(cardId: String, dBConfig: DBConfig = DBConfig(Left(config.queryTable.dbId.toShort), Option(config.queryTable.tableId.toShort))): Option[MatchResult] = {
    val dbId = dBConfig.dbId.left.get
    val tableId = dBConfig.tableId.get
    val queryResult = queryMatchResultByCardId(dbId, tableId, Some("(KeyID=%s)".format(cardId)), 1)
    queryResult.headOption.map(query => gaqryqueConverter.convertSixByteArrayToLong(query.nQueryID)).map(id => getMatchResult(id, dBConfig)).get
  }

  /**
   *
   * @param dbId
   * @param tableId
   * @param statement 查询条件
   * @param limit 限制个数
   * @return
   */
  def queryMatchResultByCardId( dbId:Short,
                                tableId:Short,
                                statement:Option[String],
                                limit: Int): List[GAQUERYSIMPSTRUCT]= {
    val pn = gcolnames.g_stCN.stQn
    val mapper = Map(
      gcolnames.g_stCN.stNuminaCol.pszSID->"nQueryID",
      pn.pszKeyID->"szKeyID",
      pn.pszUserName->"szUserName",
      pn.pszQueryType->"nQueryType",
      pn.pszPriority->"nPriority",
      pn.pszHitPoss->"nHitPossibility",
      pn.pszStatus->"nStatus",
      pn.pszFlag->"nFlag",
      pn.pszRmtFlag->"nRmtFlag",
      pn.pszStage->"nStage",
      pn.pszSourceDB->"stSrcDB",
      pn.pszDestDB->"stDestDB",
      pn.pszTimeUsed->"nTimeUsed",
      pn.pszMaxCandNum->"nMaxCandidateNum",
      pn.pszCurCandNum->"nCurCandidateNum",
      pn.pszSubmitTime->"tSubmitTime",
      pn.pszFinishTime->"tFinishTime",
      pn.pszCheckTime->"tCheckTime",
      pn.pszCheckUserName->"szCheckUserName",
      pn.pszVerifyResult->"nVerifyResult",
      pn.pszRmtState->"nRmtState",
      pn.pszMISState->"nMISState",
      pn.pszDestDBCount->"nDestDBCount",
      pn.pszGroupID->"nGroupID",
      pn.pszVerifyPri->"nVerifyPri",
      pn.pszFlag3->"nFlag3",
      pn.pszFlag4->"nFlag4",
      pn.pszMinScore->"nMinScore",
      pn.pszSchCandCnt->"nSchCandCnt",
      pn.pszReCheckUserName->"szReCheckUserName",
      pn.pszReCheckDate->"tReCheckDate",
      pn.pszFlag5->"nFlag5",
      pn.pszFlag8->"nFlag8"
    )

    facade.queryV62Table[GAQUERYSIMPSTRUCT](dbId, tableId, mapper, statement, limit)
  }

  /**
   * 发送查询任务
   * @param querySendRequest
   * @return
   */
  override def sendQuery(querySendRequest: QuerySendRequest, queryDBConfig: QueryDBConfig): QuerySendResponse = {
    val response = QuerySendResponse.newBuilder()
    val matchTask = querySendRequest.getMatchTask
    val key = matchTask.getMatchId.getBytes()
    val pstKey = new GADB_KEYARRAY
    pstKey.nKeyCount = 1
    pstKey.nKeySize = key.size.asInstanceOf[Short]
    pstKey.pKey_Data = key

//    val idx = 1 to 10 map (x => x.asInstanceOf[Byte]) toArray
//    val queryStruct = gaqryqueConverter.convertProtoBuf2GAQUERYSTRUCT(matchTask)(config)
//    val retvals = facade.NET_GAFIS_QUERY_Submit(config.queryTable.dbId.toShort, config.queryTable.tableId.toShort, pstKey, queryStruct, idx)
//    retvals.foreach{retval =>
//      val queryId = gaqryqueConverter.convertSixByteArrayToLong(retval.nSID)
//      response.setOraSid(queryId)
//    }

    val dbId = if(queryDBConfig.dbId == None){
      config.queryTable.dbId.toShort
    }else{
      queryDBConfig.dbId.get
    }
    val queryStruct = gaqryqueConverter.convertProtoBuf2GAQUERYSTRUCT(matchTask, queryDBConfig)(config)
    val oraSid = facade.NET_GAFIS_QUERY_Add(dbId, V62Facade.TID_QUERYQUE, queryStruct)
    response.setOraSid(oraSid)

    response.build()
  }

  /**
   * 获取查询信息
   * @param oraSid
   * @return
   */
  override def getMatchResult(oraSid: Long, dBConfig: DBConfig = DBConfig(Left(config.queryTable.dbId.toShort), Option(config.queryTable.tableId.toShort))): Option[MatchResult] = {
    val dbId = dBConfig.dbId.left.get
    val tableId = dBConfig.tableId.get
    val pstQry = gaqryqueConverter.convertQueryId2GAQUERYSTRUCT(oraSid)
    val gaQueryStruct = facade.NET_GAFIS_QUERY_Get(dbId, tableId, pstQry)
    if(gaQueryStruct.stSimpQry.nStatus >= 2){//比对完成
      return Option(gaqryqueConverter.convertGAQUERYSTRUCT2ProtoBuf(gaQueryStruct))
    }
    None
  }

  /**
   * 根据卡号查找第一个比对任务的状态
   * @param cardId
   * @param dBConfig
   * @return
   */
  override def findFirstQueryStatusByCardId( cardId: String, dBConfig: DBConfig = DBConfig(Left(config.queryTable.dbId.toShort), Option(config.queryTable.tableId.toShort))): MatchStatus = {
    val dbId = dBConfig.dbId.left.get
    val tableId = dBConfig.tableId.get
    val simpleQuery = queryMatchResultByCardId(dbId, tableId, Some("(KeyID=%s)".format(cardId)), 1)
    if(simpleQuery.nonEmpty){
       return gaqryqueConverter.convertStatusAsMatchStatus(simpleQuery.head.nStatus)
    }

    MatchStatus.UN_KNOWN
  }
}
