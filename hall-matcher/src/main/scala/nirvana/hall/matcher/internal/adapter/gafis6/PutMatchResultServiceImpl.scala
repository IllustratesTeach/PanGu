package nirvana.hall.matcher.internal.adapter.gafis6

import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.ghpcbase.ghpcdef.AFISDateTime
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYCANDHEADSTRUCT
import nirvana.hall.matcher.HallMatcherConstants
import nirvana.hall.matcher.internal.adapter.common.vo.QueryQueVo
import nirvana.hall.matcher.internal.{DataConverter, GafisConverter}
import nirvana.hall.matcher.service.PutMatchResultService
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.MatchResult.MatchResultRequest.MatcherStatus
import nirvana.protocol.MatchResult.MatchResultResponse.MatchResultResponseStatus
import nirvana.protocol.MatchResult.{MatchResultRequest, MatchResultResponse}

import scala.collection.JavaConversions._

/**
 * 保存比对结果service
 */
class PutMatchResultServiceImpl(implicit dataSource: DataSource) extends PutMatchResultService with LoggerSupport{
  val UPDATE_MATCH_RESULT_SQL = "update NORMALQUERY_QUERYQUE t set t.status="+HallMatcherConstants.QUERY_STATUS_SUCCESS+", t.curcandnum=?, t.candhead=?, t.candlist=?, t.hitpossibility=?,  t.FINISHTIME=sysdate where t.ora_sid=?"
  val GET_QUERY_QUE_SQL = "select t.keyid, t.querytype, t.flag, t.maxcandnum from NORMALQUERY_QUERYQUE t where t.status="+HallMatcherConstants.QUERY_STATUS_MATCHING+" and t.ora_sid=?"

  /**
   * 推送比对结果
   * @param matchResultRequest
   * @return
   */
  override def putMatchResult(matchResultRequest: MatchResultRequest): MatchResultResponse = {
    val matchResultResponse = MatchResultResponse.newBuilder()
    matchResultResponse.setStatus(MatchResultResponseStatus.OK)
    if (matchResultRequest.getStatus.getCode == 0) {
      addMatchResult(matchResultRequest)
    } else {
      updateMatchStatusFail(matchResultRequest.getMatchId, matchResultRequest.getStatus)
    }
    matchResultResponse.build()
  }

  private def addMatchResult(matchResultRequest: MatchResultRequest): Unit = {
    val oraSid = matchResultRequest.getMatchId
    val candNum = matchResultRequest.getCandidateNum
    var maxScore = matchResultRequest.getMaxScore
    val queryQue = getQueryQueVo(oraSid.toInt)
    if (queryQue.queryType != HallMatcherConstants.QUERY_TYPE_TT) {
      maxScore = maxScore / 10
    }else if(maxScore > 100){//如果查重候选分数大于100，比对任务不对应
      error("error addMatchResult sid {} queryType {} maxScore{}", oraSid, queryQue.queryType, maxScore)
      return
    }

    var candList:Array[Byte] = null
    if(candNum > 0){
      val sidKeyidMap = getCardIdSidMap(matchResultRequest, queryQue)
      candList = GafisConverter.convertMatchResultObjectList2CandList(matchResultRequest.getCandidateResultList, queryQue.queryType, sidKeyidMap, queryQue.isPalm, true)
    }
    val candHead = getCandHead(matchResultRequest, queryQue)

    JdbcDatabase.update(UPDATE_MATCH_RESULT_SQL) { ps =>
      ps.setInt(1, candNum)
      ps.setBytes(2, candHead)
      ps.setBytes(3, candList)
      ps.setInt(4, maxScore)
      /*
      if (candNum > 0) {
        //如果有候选队列，处理状态为待处理0,比中状态0;否则已处理1,没有比中1
        ps.setInt(5, 0)
        ps.setInt(6, 0)
      } else {
        ps.setInt(5, 99)
        ps.setInt(6, 1)
      }
      */
      ps.setLong(5, oraSid.toLong)
    }
  }

  /**
   * 更新比对状态失败
   * @param match_id
   * @param status
   */
  private def updateMatchStatusFail(match_id: String, status: MatcherStatus) {
    val sql: String = "UPDATE NORMALQUERY_QUERYQUE t SET t.status="+HallMatcherConstants.QUERY_STATUS_FAIL+", t.ORACOMMENT=? WHERE t.ora_sid=?"
    JdbcDatabase.update(sql) { ps =>
      ps.setString(1, status.getMsg)
      ps.setString(2, match_id)
    }
  }

  /**
   * 获取候选头结构信息
   * @param matchResultRequest
   * @param queryQue
   */
  private def getCandHead(matchResultRequest: MatchResultRequest, queryQue: QueryQueVo): Array[Byte] ={
    val queryType = queryQue.queryType
    val candHead = new GAQUERYCANDHEADSTRUCT
    candHead.szKey = queryQue.keyId
    candHead.bIsPalm = if (queryQue.isPalm) 1 else 0
    candHead.nQueryType = queryType.toByte
    candHead.nSrcDBID = if (queryType == HallMatcherConstants.QUERY_TYPE_TT || queryType == HallMatcherConstants.QUERY_TYPE_TL) 1 else 2
    candHead.nTableID = 2
    candHead.nQueryID = DataConverter.long2SidArray(queryQue.oraSid)
    candHead.nCandidateNum = matchResultRequest.getCandidateNum
    candHead.tFinishTime = new AFISDateTime
    return candHead.toByteArray()
  }

  /**
   * 根据候选列表的sid获取编号, 生成对应关系map
   * @param matchResultRequest
   * @param queryQue
   * @param dataSource
   * @return
   */
  private def getCardIdSidMap(matchResultRequest: MatchResultRequest, queryQue: QueryQueVo)(implicit dataSource: DataSource): Map[Long, String] = {
    val queryType = queryQue.queryType
    var sids = ""
    var sql = ""
    var map: Map[Long, String] = Map()
    matchResultRequest.getCandidateResultList.foreach {
      sids += _.getObjectId + ","
    }
    if (sids.lastIndexOf(",") > 0) {
      sids = sids.substring(0, sids.length - 1)
    }
    if (queryType == HallMatcherConstants.QUERY_TYPE_TT || queryType == HallMatcherConstants.QUERY_TYPE_LT) {
      sql = "select t.ora_sid,t.cardid from normaltp_tpcardinfo t where t.ora_sid in ("+sids+")"
    }
    else {
      if(queryQue.isPalm){
        sql = "select t.ora_sid, t.palmid as cardid from normallp_latpalm t where t.ora_sid in (" + sids + ")"
      }else{
        sql = "select t.ora_sid,t.fingerid as cardid from normallp_latfinger t where t.ora_sid in ("+sids+")"
      }
    }
    JdbcDatabase.queryWithPsSetter(sql) { ps =>
    } { rs =>
      map +=(rs.getLong("ora_sid") -> rs.getString("cardid"))
    }
    map
  }

  /**
   * 获取查询信息
   * @param oraSid
   * @param dataSource
   * @return
   */
  private def getQueryQueVo(oraSid: Int)(implicit dataSource: DataSource): QueryQueVo = {
    JdbcDatabase.queryFirst(GET_QUERY_QUE_SQL) { ps =>
      ps.setInt(1, oraSid)
    } { rs =>
      val keyId = rs.getString("keyid")
      val queryType = rs.getInt("querytype")
      val flag = rs.getInt("flag")
      val maxcandnum = rs.getInt("maxcandnum")
      new QueryQueVo(keyId, oraSid, queryType, if(flag == 2 || flag == 22) true else false, maxcandnum)
    }.get
  }
}

