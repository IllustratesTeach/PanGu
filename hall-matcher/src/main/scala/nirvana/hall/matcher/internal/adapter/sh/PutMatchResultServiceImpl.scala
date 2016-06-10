package nirvana.hall.matcher.internal.adapter.sh

import javax.sql.DataSource

import nirvana.hall.matcher.HallMatcherConstants
import nirvana.hall.matcher.internal.GafisConverter
import nirvana.hall.matcher.service.PutMatchResultService
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.MatchResult.MatchResultRequest.MatcherStatus
import nirvana.protocol.MatchResult.MatchResultResponse.MatchResultResponseStatus
import nirvana.protocol.MatchResult.{MatchResultRequest, MatchResultResponse}

import scala.collection.JavaConversions._

/**
 * Created by songpeng on 16/4/9.
 */
class PutMatchResultServiceImpl(implicit dataSource: DataSource) extends PutMatchResultService {
  val UPDATE_MATCH_RESULT_SQL = "update GAFIS_NORMALQUERY_QUERYQUE t set t.status="+HallMatcherConstants.QUERY_STATUS_SUCCESS+", t.curcandnum=?, t.candlist=?, t.hitpossibility=?, t.verifyresult=?, t.handleresult=?, t.time_elapsed=?, t.record_num_matched=?, t.match_progress=100, t.FINISHTIME=sysdate where t.ora_sid=?"
  val GET_QUERY_QUE_SQL = "select t.keyid, t.querytype, t.flag from GAFIS_NORMALQUERY_QUERYQUE t where t.ora_sid=?"

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
    val queryQue = getQueryQue(oraSid.toInt)

    var candList:Array[Byte] = null
    if(candNum > 0){
      val sidKeyidMap = getCardIdSidMap(matchResultRequest, queryQue)
      candList = GafisConverter.convertMatchResult2CandList(matchResultRequest, queryQue.queryType, sidKeyidMap, queryQue.isPalm)
    }
    if (queryQue.queryType != 0) {
      maxScore = maxScore / 10
    }
    JdbcDatabase.update(UPDATE_MATCH_RESULT_SQL) { ps =>
      ps.setInt(1, candNum)
      ps.setBytes(2, candList)
      ps.setInt(3, maxScore)
      if (candNum > 0) {
        //如果有候选队列，处理状态为待处理0,比中状态0;否则已处理1,没有比中1
        ps.setInt(4, 0)
        ps.setInt(5, 0)
      } else {
        ps.setInt(4, 99)
        ps.setInt(5, 1)
      }
      ps.setLong(6, matchResultRequest.getTimeElapsed)
      ps.setLong(7, matchResultRequest.getRecordNumMatched)
      ps.setLong(8, oraSid.toLong)
    }
  }

  private def updateMatchStatusFail(match_id: String, status: MatcherStatus) {
    val sql: String = "UPDATE GAFIS_NORMALQUERY_QUERYQUE t SET t.status="+HallMatcherConstants.QUERY_STATUS_FAIL+", t.ORACOMMENT=? WHERE t.ora_sid=?"
    JdbcDatabase.update(sql) { ps =>
      ps.setString(1, status.getMsg)
      ps.setString(2, match_id)
    }
  }

  /**
   * 根据候选列表的sid获取编号, 生成对应关系map
   * @param matchResultRequest
   * @param queryQue
   * @param dataSource
   * @return
   */
  private def getCardIdSidMap(matchResultRequest: MatchResultRequest, queryQue: QueryQue)(implicit dataSource: DataSource): Map[Long, String] = {
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
      sql = "select p.sid as sid, p.personid as cardid from gafis_person p where p.sid in (" + sids + ")"
    } else {
      if(queryQue.isPalm){
        sql = "select t.sid as sid, t.palm_id as cardid from gafis_case_palm t where t.sid in (" + sids + ")"
      }else{
        sql = "select t.sid as sid, t.finger_id as cardid from gafis_case_finger t where t.sid in (" + sids + ")"
      }
    }
    JdbcDatabase.queryWithPsSetter(sql) { ps =>
    } { rs =>
      map +=(rs.getLong("sid") -> rs.getString("cardid"))
    }
    map
  }

  private def getQueryQue(oraSid: Int)(implicit dataSource: DataSource): QueryQue = {
    JdbcDatabase.queryFirst(GET_QUERY_QUE_SQL) { ps =>
      ps.setInt(1, oraSid)
    } { rs =>
      val keyId = rs.getString("keyid")
      val queryType = rs.getInt("querytype")
      val flag = rs.getInt("flag")
      new QueryQue(keyId, oraSid, queryType, if(flag == 2 || flag == 22) true else false)
    }.get
  }
}

class QueryQue(val keyId: String, val oraSid: Int, val queryType: Int, val isPalm: Boolean)
