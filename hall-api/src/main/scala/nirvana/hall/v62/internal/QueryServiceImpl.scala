package nirvana.hall.v62.internal

import java.nio.ByteBuffer

import nirvana.hall.api.config.QueryDBConfig
import nirvana.hall.api.services.QueryService
import nirvana.hall.c.services.ganumia.gadbdef.GADB_KEYARRAY
import nirvana.hall.c.services.gloclib.gaqryque.{GAQUERYSIMPSTRUCT, GAQUERYSTRUCT}
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
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
  override def findFirstQueryResultByCardId(cardId: String, dbId: Option[String]): Option[MatchResult] = {
    val queryResult = findSimpleQuery(getDBID(dbId), Some("(KeyID='%s')".format(cardId)), 1)
    if(queryResult != Nil){
      queryResult.headOption.map(query => gaqryqueConverter.convertSixByteArrayToLong(query.nQueryID)).map(id => getMatchResult(id, dbId)).get
    }else{
      None
    }
  }

  /**
   * 获取查询信息，只有文本
    *
    * @param dbId
   * @param statement 查询条件
   * @param limit 限制个数
   * @return
   */
  def findSimpleQuery(dbId:Short, statement:Option[String], limit: Int): List[GAQUERYSIMPSTRUCT]= {
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

    facade.queryV62Table[GAQUERYSIMPSTRUCT](dbId, V62Facade.TID_QUERYQUE, mapper, statement, limit)
  }

  /**
   * 发送查询任务
    *
    * @param matchTask
   * @return 查询任务号
   */
  override def addMatchTask(matchTask: MatchTask, queryDBConfig: QueryDBConfig): Long= {
    val dbId = if(queryDBConfig.dbId == None){
      config.queryTable.dbId.toShort
    }else{
      queryDBConfig.dbId.get
    }
    val queryStruct = gaqryqueConverter.convertProtoBuf2GAQUERYSTRUCT(matchTask, queryDBConfig)(config)

    facade.NET_GAFIS_QUERY_Add(dbId, V62Facade.TID_QUERYQUE, queryStruct)

  }

  /**
   * 获取查询信息
    *
    * @param oraSid
   * @return
   */
  override def getMatchResult(oraSid: Long, dbId: Option[String]): Option[MatchResult] = {
    val pstQry = gaqryqueConverter.convertQueryId2GAQUERYSTRUCT(oraSid)
    val gaQueryStruct = facade.NET_GAFIS_QUERY_Get(getDBID(dbId), V62Facade.TID_QUERYQUE, pstQry)
    if(gaQueryStruct.stSimpQry.nStatus >= 2){//比对完成
      return Option(gaqryqueConverter.convertGAQUERYSTRUCT2MatchResult(gaQueryStruct))
    }
    None
  }

  /**
   * 根据卡号查找第一个比对任务的状态, 没有比对任务返回UN_KNOWN
    *
    * @param cardId
   * @return
   */
  override def findFirstQueryStatusByCardIdAndMatchType(cardId: String, matchType: MatchType, dbId: Option[String]): MatchStatus = {
    val simpleQuery = findSimpleQuery(getDBID(dbId), Some("(KeyID='%s' AND QueryType=%d)".format(cardId, matchType.getNumber-1)), 1)
    if(simpleQuery.nonEmpty){
       return gaqryqueConverter.convertStatusAsMatchStatus(simpleQuery.head.nStatus)
    }

    MatchStatus.UN_KNOWN
  }

  /**
    * 根据卡号信息发送查询, 不需要特征信息
    *
    * @param matchTask 只有查询信息不需要特征信息
    * @param queryDBConfig
    * @return 任务号
    */
  override def sendQuery(matchTask: MatchTask, queryDBConfig: QueryDBConfig): Long= {
    //key长度32，用来获取特征数据
    val key = ByteBuffer.allocate(32).put(matchTask.getMatchId.getBytes()).array()
    val pstKey = new GADB_KEYARRAY
    pstKey.nKeyCount = 1
    pstKey.nKeySize = key.size.asInstanceOf[Short]
    pstKey.pKey_Data = key

    val idx = 1 to 10 map (x => x.asInstanceOf[Byte]) toArray
    val queryStruct = gaqryqueConverter.convertProtoBuf2GAQUERYSTRUCT(matchTask, queryDBConfig)(config)
    val retvals = facade.NET_GAFIS_QUERY_Submit(config.queryTable.dbId.toShort, config.queryTable.tableId.toShort, pstKey, queryStruct, idx)
    retvals.foreach{retval =>
      val queryId = gaqryqueConverter.convertSixByteArrayToLong(retval.nSID)
      return queryId
    }
    0
  }

  /**
    * 根据编号和查询类型发送查询
    * 最大候选50，优先级2，最小分数60
    *
    * @param cardId
    * @param matchType
    * @return
    */
  override def sendQueryByCardIdAndMatchType(cardId: String, matchType: MatchType, queryDBConfig: QueryDBConfig = new QueryDBConfig(None, None, None)): Long={
    val matchTask = MatchTask.newBuilder
    matchType match {
      case MatchType.FINGER_TT |
           MatchType.FINGER_TL |
           MatchType.FINGER_LL |
           MatchType.FINGER_LT =>
        matchTask.setMatchType(matchType)
      case other =>
        throw new IllegalArgumentException("unsupport MatchType:" + matchType)
    }
    matchTask.setObjectId(0)//这里设置为0也不会比中自己
    matchTask.setMatchId(cardId)
    matchTask.setTopN(50)
    matchTask.setObjectId(0)
    matchTask.setPriority(5)
    matchTask.setScoreThreshold(60)
    matchTask.setCommitUser(config.appServer.user)

    sendQuery(matchTask.build())
  }

  /**
    * 根据任务号sid获取比对状态
    *
    * @param oraSid
    * @param dbId
    * @return
    */
  override def getStatusBySid(oraSid: Long, dbId: Option[String]): Int = {
    var status = 0
    val querySimpleStruts = findSimpleQuery(getDBID(dbId), Some("(ORA_SID='%s')".format(oraSid)),1)
    if(querySimpleStruts.nonEmpty){
      status = querySimpleStruts.headOption.get.nStatus
    }
    status
  }

  /**
    * 获取查询信息GAQUERYSTRUCT
    *
    * @param oraSid
    * @param dbId
    * @return
    */
  override def getGAQUERYSTRUCT(oraSid: Long, dbId: Option[String]): GAQUERYSTRUCT = {
    val pstQry = gaqryqueConverter.convertQueryId2GAQUERYSTRUCT(oraSid)
    facade.NET_GAFIS_QUERY_Get(getDBID(dbId), V62Facade.TID_QUERYQUE, pstQry)
  }

  /**
   * 获取DBID
    *
    * @param dbId
   */
  private def getDBID(dbId: Option[String]): Short ={
    if(dbId == None){
      config.queryTable.dbId.toShort
    }else{
      dbId.get.toShort
    }
  }

  override def updateCandListFromQueryQue(gaQuery:GAQUERYSTRUCT,dbId: Option[String] = None): Unit = {
    facade.NET_GAFIS_QUERY_Update(getDBID(dbId), V62Facade.TID_QUERYQUE,gaQuery)
  }
}
