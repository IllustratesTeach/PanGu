package nirvana.hall.v70.ln.services

import javax.persistence.EntityManager

import nirvana.hall.api.config.QueryDBConfig
import nirvana.hall.api.services.QueryService
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYSTRUCT
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v70.ln.sys

/**
 * Created by songpeng on 16/1/26.
 */
class QueryServiceImpl(entityManager: EntityManager, userService:sys.UserService) extends QueryService{
  /**
    * 发送查询任务
    *
    * @param matchTask
    * @return 任务号
    */
  override def addMatchTask(matchTask: MatchTask, queryDBConfig: QueryDBConfig): Long = ???

  /**
    * 根据编号和查询类型发送查询
    * 最大候选50，优先级2，最小分数60
    *
    * @param cardId
    * @param matchType
    * @return
    */
override def sendQueryByCardIdAndMatchType(cardId: String, matchType: MatchType, queryDBConfig: QueryDBConfig): Long = ???

  /**
    * 根据任务号sid获取比对状态
    *
    * @param oraSid
    * @param dbId
    * @return
    */
  override def getStatusBySid(oraSid: Long, dbId: Option[String]): Int = ???

  /**
    * 根据卡号信息发送查询, 不需要特征信息
    *
    * @param matchTask 只有查询信息不需要特征信息
    * @param queryDBConfig
    * @return 任务号
    */
  override def sendQuery(matchTask: MatchTask, queryDBConfig: QueryDBConfig): Long = ???

  /**
    * 根据卡号查找第一个比对任务的状态, 如果没有获取到返回UN_KNOWN
    *
    * @param cardId
    * @return
    */
  override def findFirstQueryStatusByCardIdAndMatchType(cardId: String, matchType: MatchType, dbId: Option[String]): MatchStatus = ???

  /**
    * 获取查询信息GAQUERYSTRUCT
    *
    * @param oraSid
    * @param dbId
    * @return
    */
  override def getGAQUERYSTRUCT(oraSid: Long, dbId: Option[String]): GAQUERYSTRUCT = ???

  /**
    * 通过卡号查找第一个的比中结果
    *
    * @param cardId 卡号
    * @return 比对结果
    */
  override def findFirstQueryResultByCardId(cardId: String, dbId: Option[String]): Option[MatchResult] = ???

  /**
    * 获取查询结果信息
    *
    * @param oraSid
    * @return
    */
  override def getMatchResult(oraSid: Long, dbId: Option[String]): Option[MatchResult] = ???
}
