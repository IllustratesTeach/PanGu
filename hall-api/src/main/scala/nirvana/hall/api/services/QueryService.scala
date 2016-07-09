package nirvana.hall.api.services

import nirvana.hall.api.config.DBConfig
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.api.QueryProto.{QuerySendRequest, QuerySendResponse}
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult

/**
  * Created by songpeng on 16/1/26.
 */
trait QueryService {

  /**
   * 发送查询任务
   * @param querySendRequest
   * @return
   */
  def sendQuery(querySendRequest: QuerySendRequest, dBConfig: DBConfig = null): QuerySendResponse

  /**
   * 获取查询结果信息
   * @param oraSid
   * @return
   */
  def getMatchResult(oraSid: Long, dBConfig: DBConfig = null): Option[MatchResult]

  /**
    * 通过卡号查找第一个的比中结果
    * @param cardId 卡号
    * @return 比对结果
    */
  def findFirstQueryResultByCardId(cardId:String, dBConfig: DBConfig = null):Option[MatchResult]

  /**
   * 根据卡号查找第一个比对任务的状态, 如果没有获取到返回UN_KNOWN
   * @param cardId
   * @param dBConfig
   * @return
   */
  def findFirstQueryStatusByCardId(cardId:String, dBConfig: DBConfig = null):MatchStatus
}
