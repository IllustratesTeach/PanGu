package nirvana.hall.v70.internal

import nirvana.hall.api.services.QueryService
import nirvana.hall.protocol.v62.qry.QueryProto.{QuerySendResponse, QuerySendRequest, QueryGetResponse, QueryGetRequest}

/**
 * Created by songpeng on 16/1/26.
 */
class QueryServiceImpl extends QueryService{
  /**
   * 发送查询任务
   * @param querySendRequest
   * @return
   */
  override def sendQuery(querySendRequest: QuerySendRequest): QuerySendResponse = {
    throw new UnsupportedOperationException
  }

  /**
   * 获取查询信息
   * @param queryGetRequest
   * @return
   */
  override def getQuery(queryGetRequest: QueryGetRequest): QueryGetResponse = {
    throw new UnsupportedOperationException
  }
}
