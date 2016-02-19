package nirvana.hall.v62.internal

import nirvana.hall.api.services.QueryService
import nirvana.hall.c.services.ganumia.gadbdef.GADB_KEYARRAY
import nirvana.hall.protocol.api.QueryProto.{QueryGetRequest, QueryGetResponse, QuerySendRequest, QuerySendResponse}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gloclib.gaqryqueConverter

/**
 * Created by songpeng on 16/1/26.
 */
class QueryServiceImpl(facade:V62Facade, config:HallV62Config) extends QueryService{
  /**
   * 发送查询任务
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
   * @param queryGetRequest
   * @return
   */
  override def getQuery(queryGetRequest: QueryGetRequest): QueryGetResponse = {
    val response = QueryGetResponse.newBuilder()
    val pstQry = gaqryqueConverter.convertQueryId2GAQUERYSTRUCT(queryGetRequest.getOraSid)
    val gaQueryStruct = facade.NET_GAFIS_QUERY_Get(config.queryTable.dbId.toShort, config.queryTable.tableId.toShort, pstQry)

    val matchResult = gaqryqueConverter.convertGAQUERYSTRUCT2ProtoBuf(gaQueryStruct)
    response.setMatchResult(matchResult)

    response.build()
  }
}
