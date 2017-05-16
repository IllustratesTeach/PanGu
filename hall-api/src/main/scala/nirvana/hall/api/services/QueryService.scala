package nirvana.hall.api.services

import nirvana.hall.api.config.QueryDBConfig
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYSTRUCT
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType

/**
  * Created by songpeng on 16/1/26.
 */
trait QueryService {

  /**
   * 发送查询任务
   * @param matchTask
   * @return 任务号
   */
  def addMatchTask(matchTask: MatchTask, queryDBConfig: QueryDBConfig = QueryDBConfig(None, None, None)): Long

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

  /**
    * 根据卡号信息发送查询, 不需要特征信息
    * @param matchTask 只有查询信息不需要特征信息
    * @param queryDBConfig
    * @return 任务号
    */
  def sendQuery(matchTask: MatchTask, queryDBConfig: QueryDBConfig = QueryDBConfig(None, None, None)): Long

  /**
    * 根据任务号sid获取比对状态
    * @param oraSid
    * @param dbId
    * @return
    */
  def getStatusBySid(oraSid: Long, dbId: Option[String] = None): Int

  /**
    * 获取查询信息GAQUERYSTRUCT
    * @param oraSid
    * @param dbId
    * @return
    */
  def getGAQUERYSTRUCT(oraSid: Long, dbId: Option[String] = None): GAQUERYSTRUCT
}
