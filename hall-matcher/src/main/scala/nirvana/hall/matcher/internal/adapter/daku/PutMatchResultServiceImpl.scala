package nirvana.hall.matcher.internal.adapter.daku

import java.io.ByteArrayOutputStream
import javax.sql.DataSource

import nirvana.hall.c.services.gbaselib.gbasedef.AFISDateTime
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYCANDSTRUCT
import nirvana.hall.matcher.HallMatcherConstants
import nirvana.hall.matcher.internal.DataConverter
import nirvana.hall.matcher.internal.adapter.common.vo.QueryQueVo
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
  val GET_QUERY_QUE_SQL = "select t.keyid, t.querytype, t.flag, t.maxcandnum from GAFIS_NORMALQUERY_QUERYQUE t where t.ora_sid=?"

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

    var candList:Array[Byte] = null
    if(candNum > 0){
      val sidKeyidMap = getCardIdSidMap(matchResultRequest, queryQue.queryType)
      candList = convertMatchResult2CandList(matchResultRequest, queryQue.queryType, sidKeyidMap)
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
    val sql: String = "UPDATE GAFIS_NORMALQUERY_QUERYQUE t SET t.status="+HallMatcherConstants.QUERY_STATUS_FAIL +", t.ORACOMMENT=? WHERE t.ora_sid=?"
    JdbcDatabase.update(sql) { ps =>
      ps.setString(1, status.getMsg)
      ps.setString(2, match_id)
    }
  }

  /**
   * 根据候选列表的sid获取编号, 生成对应关系map
   * @param matchResultRequest
   * @param queryType
   * @param dataSource
   * @return
   */
  private def getCardIdSidMap(matchResultRequest: MatchResultRequest, queryType: Int)(implicit dataSource: DataSource): Map[Long, String] = {
    var sids = ""
    var sql = ""
    var map: Map[Long, String] = Map()
    var i = 0
    val batchSize = 100 //每100条执行一次sql
    matchResultRequest.getCandidateResultList.foreach {cand=>
      sids += cand.getObjectId + ","
      i += 1
      if(i % batchSize == 0 || i == matchResultRequest.getCandidateNum){
        if (sids.lastIndexOf(",") > 0) {
          sids = sids.substring(0, sids.length - 1)
        }
        if (queryType == HallMatcherConstants.QUERY_TYPE_TT || queryType == HallMatcherConstants.QUERY_TYPE_LT) {
          sql = "select p.sid as sid, p.personid as cardid from gafis_person p where p.sid in (" + sids + ")"
        } else {
          sql = "select t.sid as sid, t.finger_id as cardid from gafis_case_finger t where t.sid in (" + sids + ")"
        }
        JdbcDatabase.queryWithPsSetter(sql) { ps =>
        } { rs =>
          map +=(rs.getLong("sid") -> rs.getString("cardid"))
        }
        //清空sids
        sids = ""
      }
    }
    map
  }

  private def getQueryQueVo(oraSid: Int)(implicit dataSource: DataSource): QueryQueVo = {
    JdbcDatabase.queryFirst(GET_QUERY_QUE_SQL) { ps =>
      ps.setInt(1, oraSid)
    } { rs =>
      val keyId = rs.getString("keyid")
      val queryType = rs.getInt("querytype")
      val maxcandnum = rs.getInt("maxcandnum")
      new QueryQueVo(keyId, oraSid, queryType, false, maxcandnum)
    }.get
  }

  /**
    * 候选列表转换，
    * @param matchResultRequest
    * @param queryType
    * @param sidKeyidMap
    * @param isPalm
    * @param isGafis6
    * @return
    */
  def convertMatchResult2CandList(matchResultRequest: MatchResultRequest, queryType: Int,sidKeyidMap: Map[Long, String], isPalm: Boolean = false, isGafis6: Boolean = false): Array[Byte] ={
    val result = new ByteArrayOutputStream()
    val candIter = matchResultRequest.getCandidateResultList.iterator()
    var index = 0 //比对排名
    while (candIter.hasNext) {
      index += 1
      val cand = candIter.next()
      val keyId = sidKeyidMap.get(cand.getObjectId)
      if (keyId.nonEmpty) {
        var fgp = cand.getPos
        //指位转换
        if(!isPalm){
          fgp = DataConverter.fingerPos8to6(cand.getPos)
          if(isGafis6){
            if(fgp > 10){//gafis6.2中平指指位[21,30]
              fgp += 10
            }
          }
        }
        val gCand = new GAQUERYCANDSTRUCT
        gCand.nScore = cand.getScore
        gCand.szKey = keyId.get
        //dbid, tableid 分别截取desc的前两位字节和三四位字节, 分别表示比对类型和指位
        //val desc = ByteBuffer.allocate(8).putLong(cand.getDesc)
        //gCand.nDBID = desc.getShort(4)
        //gCand.nTableID = desc.getShort(6)
//        gCand.nDBID = if (queryType == HallMatcherConstants.QUERY_TYPE_TT || queryType == HallMatcherConstants.QUERY_TYPE_LT) 1 else 2
//        gCand.nTableID = 2
        gCand.nIndex = fgp.toByte
        gCand.tFinishTime = new AFISDateTime
        gCand.nStepOneRank = index
        result.write(gCand.toByteArray())
      }
    }
    result.toByteArray
  }
}
