package nirvana.hall.api.services

import nirvana.hall.protocol.v62.qry.QueryProto.{QueryGetResponse, QueryGetRequest, QuerySendResponse, QuerySendRequest}

/**
 * Created by songpeng on 16/1/26.
 */
trait QueryService {

  /**
   * 发送查询任务
   * @param querySendRequest
   * @return
   */
  def sendQuery(querySendRequest: QuerySendRequest): QuerySendResponse

  /**
   * 获取查询信息
   * @param queryGetRequest
   * @return
   */
  def getQuery(queryGetRequest: QueryGetRequest): QueryGetResponse
}
