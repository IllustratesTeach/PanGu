package nirvana.hall.api.internal.remote

import nirvana.hall.api.services.remote.QueryRemoteService
import nirvana.hall.protocol.api.QueryProto.{QueryGetRequest, QueryGetResponse}
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.support.services.RpcHttpClient

/**
 * Created by songpeng on 16/3/4.
 */
class QueryRemoteServiceImpl(rpcHttpClient: RpcHttpClient) extends QueryRemoteService{
  /**
   * 获取查询结果,如果比对没有完成返回null
   * @param oraSid
   * @return
   */
  override def getQuery(oraSid: Long, url: String, headerMap: Map[String, String], uuid: String): MatchResult = {
    val request = QueryGetRequest.newBuilder().setOraSid(oraSid).setUuid(uuid)
    val baseResponse = rpcHttpClient.call(url, QueryGetRequest.cmd, request.build(), headerMap)
    val queryGetResponse = baseResponse.getExtension(QueryGetResponse.cmd)
    if(queryGetResponse.getIsComplete){
      queryGetResponse.getMatchResult
    }else{
      null
    }
  }

}
