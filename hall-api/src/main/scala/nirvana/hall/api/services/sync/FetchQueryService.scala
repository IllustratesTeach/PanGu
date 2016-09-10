package nirvana.hall.api.services.sync

import nirvana.hall.api.config.QueryQue
import nirvana.hall.api.internal.DataConverter
import nirvana.hall.api.jpa.HallFetchConfig
import nirvana.hall.c.services.ghpcbase.ghpcdef.AFISDateTime
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYCANDHEADSTRUCT
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask

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
   * @param matchResult
   */
  def saveMatchResult(matchResult: MatchResult, fetchConfig: HallFetchConfig)

  /**
   * 根据远程查询queryid获取查询结果信息
   * @param queryid
   * @return
   */
  def getMatchResultByQueryid(queryid: Long, dbId: Option[String] = None): Option[MatchResult]

  /**
   * 获取候选头结构信息
   * @param matchResult
   * @param queryQue
   */
  protected def getCandHead(matchResult: MatchResult, queryQue: QueryQue): Array[Byte] ={
    val queryType = queryQue.queryType
    val candHead = new GAQUERYCANDHEADSTRUCT
    candHead.szKey = queryQue.keyId
    candHead.bIsPalm = if (queryQue.isPalm) 1 else 0
    candHead.nQueryType = queryType.toByte
    candHead.nSrcDBID = if (queryType == 0 || queryType == 1) 1 else 2
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
}
