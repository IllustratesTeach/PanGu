package nirvana.hall.matcher.internal.adapter.gafis6

import java.io.ByteArrayOutputStream
import javax.sql.DataSource

import nirvana.hall.c.services.ghpcbase.ghpcdef.AFISDateTime
import nirvana.hall.c.services.gloclib.gaqryque.{GAQUERYCANDSTRUCT, GAQUERYCANDHEADSTRUCT}
import nirvana.hall.matcher.internal.DataConverter
import nirvana.hall.matcher.service.PutMatchResultService
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.MatchResult.MatchResultRequest.MatcherStatus
import nirvana.protocol.MatchResult.MatchResultResponse.MatchResultResponseStatus
import nirvana.protocol.MatchResult.{MatchResultRequest, MatchResultResponse}

import scala.collection.JavaConversions._

/**
 * 保存比对结果service
 */
class PutMatchResultServiceImpl(implicit dataSource: DataSource) extends PutMatchResultService {
  val UPDATE_MATCH_RESULT_SQL = "update NORMALQUERY_QUERYQUE t set t.status=2, t.curcandnum=?, t.candhead=?, t.candlist=?, t.hitpossibility=?,  t.FINISHTIME=sysdate where t.ora_sid=?"
  val GET_QUERY_QUE_SQL = "select t.keyid, t.querytype, t.flag from NORMALQUERY_QUERYQUE t where t.ora_sid=?"

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
      candList = getCandList(matchResultRequest, queryQue, sidKeyidMap)
    }
    val candHead = getCandHead(matchResultRequest, queryQue)

    if (queryQue.queryType != 0) {
      maxScore = maxScore / 10
    }
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
    val sql: String = "UPDATE NORMALQUERY_QUERYQUE t SET t.status=2, t.ORACOMMENT=? WHERE t.ora_sid=?"
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
  private def getCandHead(matchResultRequest: MatchResultRequest, queryQue: QueryQue): Array[Byte] ={
    val queryType = queryQue.queryType
    val candHead = new GAQUERYCANDHEADSTRUCT
    candHead.szKey = queryQue.keyId
    candHead.bIsPalm = if (queryQue.isPalm) 1 else 0
    candHead.nQueryType = queryType.toByte
    candHead.nSrcDBID = if (queryType == 0 || queryType == 1) 1 else 2
    candHead.nTableID = 2
    candHead.nQueryID = queryQue.oraSid.toString
    candHead.nCandidateNum = matchResultRequest.getCandidateNum
    candHead.tFinishTime = new AFISDateTime
    return candHead.toByteArray()
  }
  /**
   * 获取候选列表
   * @param matchResultRequest
   * @param queryQue
   * @param sidKeyidMap
   * @return
   */
  private def getCandList(matchResultRequest: MatchResultRequest, queryQue: QueryQue, sidKeyidMap: Map[Long, String]): Array[Byte] = {
    val queryType = queryQue.queryType
    val result = new ByteArrayOutputStream()
    val candIter = matchResultRequest.getCandidateResultList.iterator()
    var index = 0 //比对排名
    while (candIter.hasNext) {
      index += 1
      val cand = candIter.next()
      val keyId = sidKeyidMap.get(cand.getObjectId)
      if (keyId.nonEmpty) {
        //指位转换
        var fgp = 0
        if(queryQue.isPalm){
          fgp = DataConverter.palmPos8to6(cand.getPos)
        }else{
          fgp = DataConverter.fingerPos8to6(cand.getPos)
        }
        val gCand = new GAQUERYCANDSTRUCT
        gCand.nScore = cand.getScore
        gCand.szKey = keyId.get
        gCand.nDBID = if (queryType == 0 || queryType == 2) 1 else 2
        gCand.nTableID = 2
        gCand.nIndex = fgp.toByte
        gCand.tFinishTime = new AFISDateTime
        gCand.nStepOneRank = index
        result.write(gCand.toByteArray())
/*        result.write(new Array[Byte](4))
        result.write(DataConverter.int2Bytes(cand.getScore))
        result.write(keyId.get.getBytes)
        result.write(new Array[Byte](32 - keyId.get.getBytes().length + 2))
        val dbId = if (queryType == 0 || queryType == 2) 1 else 2
        result.write(ByteBuffer.allocate(2).putShort(dbId.toShort).array())
        result.write(ByteBuffer.allocate(2).putShort(2.toShort).array())
        result.write(new Array[Byte](2 + 1 + 3 + 1 + 1 + 1 + 1))
        result.write(fgp.toByte)
        result.write(new Array[Byte](1 + 2 + 1 + 1 + 1 + 1))
        result.write(DataConverter.getAFISDateTime(new Date()))
        result.write(new Array[Byte](2 + 2 + 2 + 2))
        result.write(new Array[Byte](8 + 2))
        result.write(DataConverter.int2Bytes(index))
        result.write(new Array[Byte](2))*/
      }
    }
    result.toByteArray
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
    matchResultRequest.getCandidateResultList.foreach {
      sids += _.getObjectId + ","
    }
    if (sids.lastIndexOf(",") > 0) {
      sids = sids.substring(0, sids.length - 1)
    }
    if (queryType == 0 || queryType == 1) {
      sql = "select t.ora_sid,t.cardid from normaltp_tpcardinfo t where t.ora_sid in ("+sids+")"
    }
    else {
      sql = "select t.ora_sid,t.fingerid as cardid from normallp_latfinger t where t.ora_sid in ("+sids+")"
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
