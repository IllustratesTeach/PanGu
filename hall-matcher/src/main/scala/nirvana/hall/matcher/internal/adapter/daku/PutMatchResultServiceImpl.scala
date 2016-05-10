package nirvana.hall.matcher.internal.adapter.daku

import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.Date
import javax.sql.DataSource

import nirvana.hall.matcher.internal.DataConverter
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
  val UPDATE_MATCH_RESULT_SQL = "update GAFIS_NORMALQUERY_QUERYQUE t set t.status=2, t.curcandnum=?, t.candlist=?, t.hitpossibility=?, t.verifyresult=?, t.handleresult=?, t.time_elapsed=?, t.record_num_matched=?, t.match_progress=100, t.FINISHTIME=sysdate where t.ora_sid=?"
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
      val sidKeyidMap = getCardIdSidMap(matchResultRequest, queryQue.queryType)
      candList = getCandList(matchResultRequest, queryQue.queryType, sidKeyidMap)
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
    val sql: String = "UPDATE GAFIS_NORMALQUERY_QUERYQUE t SET t.status=3, t.ORACOMMENT=? WHERE t.ora_sid=?"
    JdbcDatabase.update(sql) { ps =>
      ps.setString(1, status.getMsg)
      ps.setString(2, match_id)
    }
  }

  private def getCandList(matchResultRequest: MatchResultRequest, queryType: Int,sidKeyidMap: Map[Long, String]): Array[Byte] = {
    val result = new ByteArrayOutputStream()
    val candIter = matchResultRequest.getCandidateResultList.iterator()
    var index = 0 //比对排名
    while (candIter.hasNext) {
      index += 1
      val cand = candIter.next()
      val keyId = sidKeyidMap.get(cand.getObjectId)
      if (keyId.nonEmpty) {
        result.write(new Array[Byte](4))
        result.write(DataConverter.int2Bytes(cand.getScore))
        result.write(keyId.get.getBytes)
        result.write(new Array[Byte](32 - keyId.get.getBytes().length + 2))
        val dbId = if (queryType == 0 || queryType == 2) 1 else 2
        result.write(ByteBuffer.allocate(2).putShort(dbId.toShort).array())
        result.write(ByteBuffer.allocate(2).putShort(2.toShort).array())
        result.write(new Array[Byte](2 + 1 + 3 + 1 + 1 + 1 + 1))
        result.write(cand.getPos.toByte)
        result.write(new Array[Byte](1 + 2 + 1 + 1 + 1 + 1))
        result.write(DataConverter.getAFISDateTime(new Date()))
        result.write(new Array[Byte](2 + 2 + 2 + 2))
        result.write(new Array[Byte](8 + 2))
        result.write(DataConverter.int2Bytes(index))
        result.write(new Array[Byte](2))
      }
    }
    result.toByteArray
  }

  private def getCardIdSidMap(matchResultRequest: MatchResultRequest, queryType: Int)(implicit dataSource: DataSource): Map[Long, String] = {
    var sids = ""
    var sql = ""
    var map: Map[Long, String] = Map()
    matchResultRequest.getCandidateResultList.foreach {
      sids += _.getObjectId + ","
    }
    if (sids.lastIndexOf(",") > 0) {
      sids = sids.substring(0, sids.length - 1)
    }
    if (queryType == 0 || queryType == 2) {
      sql = "select p.sid as sid, p.personid as cardid from gafis_person p where p.sid in (" + sids + ")"
    } else {
      sql = "select t.sid as sid, t.finger_id as cardid from gafis_case_finger t where t.sid in (" + sids + ")"
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
      //      val flag = rs.getInt("flag")
      new QueryQue(keyId, oraSid, queryType)
    }.get
  }
}
class QueryQue(val keyId: String, val oraSid: Int, val queryType: Int)
