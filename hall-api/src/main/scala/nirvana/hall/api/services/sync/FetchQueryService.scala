package nirvana.hall.api.services.sync

import nirvana.hall.api.config.QueryQue
import nirvana.hall.api.internal.DataConverter
import nirvana.hall.api.jpa.HallFetchConfig
import nirvana.hall.c.services.ghpcbase.ghpcdef.AFISDateTime
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYCANDHEADSTRUCT
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v70.internal.query.QueryConstants

/**
  * Created by songpeng on 16/8/31.
  */
trait FetchQueryService {

  /**
    * 获取比对任务
    * @param size
    * @param dbId
    * @return
    */
  def fetchMatchTask(seq: Long, size: Int, dbId: Option[String] = None): Seq[MatchTask]

  /**
    * 保存候选信息
    * @param matchResult 候选信息
    * @param fetchConfig 同步配置
    * @param candDBIDMap 候选卡号->dbid
    */
  def saveMatchResult(matchResult: MatchResult, fetchConfig: HallFetchConfig, candDBIDMap: Map[String, Short] = Map())

  /**
    * 根据远程查询queryid获取查询结果信息
    * @param queryid
    * @return
    */
  def getMatchResultByQueryid(queryid: Long, dbId: Option[String] = None): Option[MatchResult]

  /**
    * 获取比对状态正在比对任务SID
    * @param size
    * @return
    */
  def getSidByStatusMatching(size: Int, dbId: Option[String] = None): Seq[Long]

  /**
    * 根据queryid获取比对状态
    * @param queryId
    */
  def getMatchStatusByQueryid(queryId: Long): MatchStatus

  /**
    * 获取候选头结构信息
    * @param matchResult
    * @param queryQue
    */
  protected def getCandHead(matchResult: MatchResult, queryQue: QueryQue, cardIdDBIDMap: Map[String, Short] = Map()): Array[Byte] ={
    val queryType = queryQue.queryType
    val candHead = new GAQUERYCANDHEADSTRUCT
    candHead.szKey = queryQue.keyId
    candHead.bIsPalm = if (queryQue.isPalm) 1 else 0
    candHead.nQueryType = queryType.toByte
    candHead.nSrcDBID = if (queryType == QueryConstants.QUERY_TYPE_TT || queryType == QueryConstants.QUERY_TYPE_TL) V62Facade.DBID_TP_DEFAULT else V62Facade.DBID_LP_DEFAULT
    candHead.nTableID = 2
    candHead.nQueryID = DataConverter.longToSidArray(queryQue.oraSid)
    candHead.nCandidateNum = matchResult.getCandidateNum
    candHead.tFinishTime = new AFISDateTime
    return candHead.toByteArray()
  }

  /**
    * 获取查询信息
    * @param oraSid
    * @return
    */
  def getQueryQue(oraSid: Int): QueryQue
  /**
    * 更新status
    * sjr 2016/11/28
    * @param oraSid
    * @param status
    */
  def updateMatchStatus(oraSid: Long, status: Int)


  /**
    * 根据orasid获取对应任务的捺印卡号 keyId
    * @param oraSid
    */
  def getKeyIdArrByOraSid(oraSid: Long):Seq[String]

  /**
    * 根据orasid获取对应任务的查询类型 queryType
    * @param oraSid
    */
  def getQueryTypeArrByOraSid(oraSid: Long): Seq[String]
}


