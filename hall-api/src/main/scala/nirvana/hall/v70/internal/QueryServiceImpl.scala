package nirvana.hall.v70.internal

import java.util.Date

import nirvana.hall.api.services.QueryService
import nirvana.hall.protocol.api.QueryProto.{QueryGetRequest, QueryGetResponse, QuerySendRequest, QuerySendResponse}
import nirvana.hall.v70.internal.sync.ProtobufConverter

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
    val matchTask = querySendRequest.getMatchTask
    val gafisQuery = ProtobufConverter.convertMatchTask2GafisNormalqueryQueryque(matchTask)
    gafisQuery.oraSid = 1L//TODO 查询序列
    gafisQuery.pkId = CommonUtils.getUUID()
    gafisQuery.createtime = new Date()
    gafisQuery.deletag = Gafis70Constants.DELETAG_USE

    gafisQuery.save()

    QuerySendResponse.newBuilder().setOraSid(gafisQuery.oraSid).build()

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
