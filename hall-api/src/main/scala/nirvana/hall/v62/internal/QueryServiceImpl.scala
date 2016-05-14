package nirvana.hall.v62.internal

import nirvana.hall.api.services.QueryService
import nirvana.hall.c.services.ganumia.gadbdef.GADB_KEYARRAY
import nirvana.hall.c.services.ganumia.gadbrec.{GADB_SELRESITEM, GADB_SELRESULT, _}
import nirvana.hall.c.services.gloclib.galoclog.GAFIS_VERIFYLOGSTRUCT
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYSIMPSTRUCT
import nirvana.hall.protocol.api.QueryProto.{QueryGetRequest, QueryGetResponse, QuerySendRequest, QuerySendResponse}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gloclib.{gaqryqueConverter, gcolnames}
import nirvana.hall.v62.internal.c.gloclib.gcolnames.g_stCN
import org.jboss.netty.buffer.ChannelBuffers

import scala.collection.mutable

/**
 * Created by songpeng on 16/1/26.
 */
class QueryServiceImpl(facade:V62Facade, config:HallV62Config) extends QueryService{

  /**
    * 查询比中关系
    *
    * @param statementOpt 查询语句
    * @param limit 抓取多少条
    * @return 查询结构
    */
  def queryMatchInfo(statementOpt:Option[String],limit:Int): List[GAFIS_VERIFYLOGSTRUCT]={
    val stSelStatement = new GADB_SELSTATEMENT    
    val stItems = new mutable.ListBuffer[GADB_SELRESITEM]();
    val stSelRes = new GADB_SELRESULT

    val destStruct = new GAFIS_VERIFYLOGSTRUCT
    stSelRes.nSegSize = destStruct.getDataSize

      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszBreakID,"szBreakID")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszSrcKey,"szSrcKey")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszDestKey,"szDestKey")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszScore,"nScore")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszFirstRankScore,"nFirstRankScore")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszRank,"nRank")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszFg,"nFg")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszHitPoss,"nHitPoss")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszIsRemoteSearched,"bIsRmtSearched")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszIsCrimeCaptured,"bIsCrimeCaptured")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszTotoalMatchedCnt,"nTotalMatchedCnt")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszQueryType,"nQueryType")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszSrcDBID,"nSrcDBID")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszDestDBID,"nDestDBID")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszSrcPersonCaseID,"szSrcPersonCaseID")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszDestPersonCaseID,"szDestPersonCaseID")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszCaseClassCode1,"szCaseClassCode1")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszCaseClassCode2,"szCaseClassCode2")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszCaseClassCode3,"szCaseClassCode3")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszSubmitUserName,"szSubmitUserName")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszSubmitUserUnitCode,"szSubmitUserUnitCode")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszSubmitDateTime,"tSubmitDateTime")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszBreakUserName,"szBreakUserName")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszBreakUserUnitCode,"szBreakUserUnitCode")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszBreakDateTime,"tBreakDateTime")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszReCheckUserName,"szReCheckUserName")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszReCheckerUnitCode,"szReCheckerUnitCode")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stBc.pszReCheckDate,"tReCheckDateTime")
      SETSELRESITEM_FIXED(stItems, destStruct, g_stCN.stNuminaCol.pszSID,"nSID")

   stSelRes.pstItem_Data = stItems.toArray
    stSelRes.nResItemCount = stItems.size

    stSelStatement.nMaxToGet = limit
    statementOpt.foreach(x=>stSelStatement.szStatement=x)

    val dbDef = facade.NET_GAFIS_MISC_GetDefDBID()
    val nret = facade.NET_GAFIS_TABLE_Select(dbDef.nAdminDefDBID,g_stCN.stSysAdm.nTIDBreakCaseTable, stSelRes, stSelStatement);
    if(nret >= 0){
      val count = stSelRes.nRecGot
      if(count > 0) {
        val buffer = ChannelBuffers.wrappedBuffer(stSelRes.pDataBuf_Data)
        return Range(0, count).map(x => new GAFIS_VERIFYLOGSTRUCT().fromStreamReader(buffer)).toList
      }
    }
    Nil
  }
  def queryMatchResultByCardId( dbId:Short,
                                tableId:Short,
                                statement:Option[String], //查询条件
                                limit: Int): List[GAQUERYSIMPSTRUCT]= {
    
    val schema = facade.NET_GAFIS_SYS_GetTableSchema(dbId, tableId);
    val stSelRes = new GADB_SELRESULT
    val pn = gcolnames.g_stCN.stQn

    val querySimpleStruct = new GAQUERYSIMPSTRUCT()
    stSelRes.nSegSize = new GAQUERYSIMPSTRUCT().getDataSize
    val stItems = new mutable.ListBuffer[GADB_SELRESITEM]()
    //用下列正则进行代码替换
    // stItem\s+\+\s+i  => stItems
    //(SETSELRESITEM_FIXED(_EXIST)?[^\)]+)\);    => $1,schema)
    //,\s*(\w+),schema => ,."$1",schema
    //. => .
    
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszKeyID,"szKeyID",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszUserName,"szUserName",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszQueryType,"nQueryType",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszPriority,"nPriority",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszHitPoss,"nHitPossibility",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszStatus,"nStatus",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszFlag,"nFlag",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszRmtFlag,"nRmtFlag",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszStage,"nStage",schema)
    SETSELRESITEM_FIXED(stItems, querySimpleStruct, gcolnames.g_stCN.stNuminaCol.pszSID, "nQueryID")
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszSourceDB,"stSrcDB",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszDestDB,"stDestDB",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszTimeUsed,"nTimeUsed",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszMaxCandNum,"nMaxCandidateNum",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszCurCandNum,"nCurCandidateNum",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszSubmitTime,"tSubmitTime",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszFinishTime,"tFinishTime",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszCheckTime,"tCheckTime",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszCheckUserName,"szCheckUserName",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszVerifyResult,"nVerifyResult",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszRmtState,"nRmtState",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszMISState,"nMISState",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszDestDBCount,"nDestDBCount",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszGroupID,"nGroupID",schema)
    //	SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszisf)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszVerifyPri,"nVerifyPri",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszFlag3,"nFlag3",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszFlag4,"nFlag4",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszMinScore,"nMinScore",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszSchCandCnt,"nSchCandCnt",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszReCheckUserName,"szReCheckUserName",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszReCheckDate,"tReCheckDate",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszFlag5,"nFlag5",schema)
    SETSELRESITEM_FIXED_EXIST(stItems, querySimpleStruct, pn.pszFlag8,"nFlag8",schema)



    stSelRes.pstItem_Data = stItems.toArray
    stSelRes.nResItemCount = stItems.size

    val stSelStatement = new 	GADB_SELSTATEMENT
    stSelStatement.nMaxToGet = limit
    statement.foreach(x=>stSelStatement.szStatement=x)


    /*
    if(m_bUseSID)
    {
      uint8ToChar6(m_nStartSID, stSelStatement.nStartSID);
      uint8ToChar6(m_nEndedSID, stSelStatement.nEndSID);
    }
    */

    val nret = facade.NET_GAFIS_TABLE_Select(dbId, tableId, stSelRes, stSelStatement)
    if(nret >= 0) {
      val nCount = stSelRes.nRecGot
      if (nCount > 0) {
        val buffer = ChannelBuffers.wrappedBuffer(stSelRes.pDataBuf_Data)
        val result = Range(0,nCount).map(x=>new GAQUERYSIMPSTRUCT().fromStreamReader(buffer))
        return result.toList
      }
    }

    Nil
  }

  /**
   * 发送查询任务
 *
   * @param querySendRequest
   * @return
   */
  override def sendQuery(querySendRequest: QuerySendRequest): QuerySendResponse = {
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
    val queryStruct = gaqryqueConverter.convertProtoBuf2GAQUERYSTRUCT(matchTask)(config)
    val oraSid = facade.NET_GAFIS_QUERY_Add(config.queryTable.dbId.toShort, config.queryTable.tableId.toShort, queryStruct)
    response.setOraSid(oraSid)

    response.build()
  }

  /**
   * 获取查询信息
 *
   * @param queryGetRequest
   * @return
   */
  override def getQuery(queryGetRequest: QueryGetRequest): QueryGetResponse = {
    val response = QueryGetResponse.newBuilder()
    val pstQry = gaqryqueConverter.convertQueryId2GAQUERYSTRUCT(queryGetRequest.getOraSid)
    val gaQueryStruct = facade.NET_GAFIS_QUERY_Get(config.queryTable.dbId.toShort, config.queryTable.tableId.toShort, pstQry)
    if(gaQueryStruct.stSimpQry.nStatus >= 2){//比对完成
      val matchResult = gaqryqueConverter.convertGAQUERYSTRUCT2ProtoBuf(gaQueryStruct)
      response.setMatchResult(matchResult)
      response.setIsComplete(true)
    }else{
      response.setIsComplete(false)
    }

    response.build()
  }
}
