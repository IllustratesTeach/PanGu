package nirvana.hall.v70.internal.remote

import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.protocol.api.QueryProto.{QueryGetResponse, QueryGetRequest}
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v70.services.remote.QueryRemoteService

/**
 * Created by songpeng on 16/3/4.
 */
class QueryRemoteServiceImpl(rpcHttpClient: RpcHttpClient) extends QueryRemoteService{
  /**
   * 获取查询结果,如果比对没有完成返回null
   * @param oraSid
   * @return
   */
  override def getQuery(oraSid: Long, ip: String, port: String): MatchResult = {
    val request = QueryGetRequest.newBuilder().setOraSid(oraSid)
    val baseResponse = rpcHttpClient.call("http://"+ ip +":"+ port, QueryGetRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      val queryGetResponse = baseResponse.getExtension(QueryGetResponse.cmd)
      if(queryGetResponse.getIsComplete){
        queryGetResponse.getMatchResult
      }else{
        null
      }
    }else{
      throw new RuntimeException(baseResponse.getMsg)
    }
  }
}
