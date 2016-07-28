package nirvana.hall.api.services

import nirvana.hall.api.config.{QueryDBConfig}
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.api.QueryProto.{QuerySendRequest, QuerySendResponse}
import nirvana.hall.protocol.fpt.TypeDefinitionProto.MatchType
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
  def sendQuery(querySendRequest: QuerySendRequest, queryDBConfig: QueryDBConfig = null): QuerySendResponse

  /**
   * 获取查询结果信息
   * @param oraSid
   * @return
   */
  def getMatchResult(oraSid: Long, dbId: Option[String] = None): Option[MatchResult]

  /**
    * 通过卡号查找第一个的比中结果
    * @param cardId 卡号
    * @return 比对结果
    */
  def findFirstQueryResultByCardId(cardId:String, dbId: Option[String] = None):Option[MatchResult]

  /**
   * 根据卡号查找第一个比对任务的状态, 如果没有获取到返回UN_KNOWN
   * @param cardId
   * @return
   */
  def findFirstQueryStatusByCardIdAndMatchType(cardId:String, matchType: MatchType, dbId: Option[String] = None):MatchStatus
}
