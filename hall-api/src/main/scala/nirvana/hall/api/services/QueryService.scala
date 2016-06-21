package nirvana.hall.api.services

import nirvana.hall.protocol.api.QueryProto.{QueryGetResponse, QueryGetRequest, QuerySendResponse, QuerySendRequest}
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult

/**
  * Created by songpeng on 16/1/26.
 */
trait QueryService {

  /**
   * 发送查询任务
    *
    * @param querySendRequest
   * @return
   */
  def sendQuery(querySendRequest: QuerySendRequest): QuerySendResponse

  /**
   * 获取查询信息
    *
    * @param queryGetRequest
   * @return
   */
  def getQuery(queryGetRequest: QueryGetRequest): QueryGetResponse

  /**
    * 通过卡号查找第一个的比中结果
    * @param cardId 卡号
    * @return 比对结果
    */
  def findFirstQueryResultByCardId(dbId:Short,tableId:Short,cardId:String):Option[MatchResult]
}
