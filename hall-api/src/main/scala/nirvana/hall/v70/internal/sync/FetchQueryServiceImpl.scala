package nirvana.hall.v70.internal.sync

import java.io.ByteArrayOutputStream
import javax.sql.DataSource

import nirvana.hall.api.config.QueryQue
import nirvana.hall.api.jpa.HallFetchConfig
import nirvana.hall.api.services.sync.FetchQueryService
import nirvana.hall.c.services.ghpcbase.ghpcdef.AFISDateTime
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYCANDSTRUCT
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult.MatcherStatus
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.v62.internal.c.gloclib.gaqryqueConverter
import nirvana.hall.v70.internal.query.QueryConstants
import nirvana.hall.v70.jpa.GafisNormalqueryQueryque

import scala.collection.mutable.ArrayBuffer

/**
  * Created by songpeng on 16/8/26.
  */
class FetchQueryServiceImpl(implicit datasource: DataSource) extends FetchQueryService{
  /**
    * 获取查询任务
 *
    * @param size
    * @return
    */
  override def fetchMatchTask(size: Int, dbId: Option[String]): Seq[MatchTask] = {
    GafisNormalqueryQueryque.find_by_status(0.toShort).limit(size).map{gafisQuery=>
      ProtobufConverter.convertGafisNormalqueryQueryque2MatchTask(gafisQuery)
    }.toSeq
  }

  /**
    * 保存候选信息
 *
    * @param matchResult
    */
  override def saveMatchResult(matchResult: MatchResult, fetchConfig: HallFetchConfig, candDBIDMap: Map[String, Short] = Map(), configMap : scala.collection.mutable.HashMap[String, String]) = {
    val sql = "update NORMALQUERY_QUERYQUE t set t.status=2, t.curcandnum=?, t.candlist=?, t.hitpossibility=?, t.verifyresult=?, t.handleresult=?, t.time_elapsed=?, t.record_num_matched=?, t.match_progress=100, t.FINISHTIME=sysdate where t.ora_sid =?"
    val oraSid = matchResult.getMatchId
    val candNum = matchResult.getCandidateNum
    val maxScore = matchResult.getMaxScore
    val queryQue = getQueryQue(oraSid)
    val candHead = getCandHead(matchResult, queryQue)
    var candList:Array[Byte] = null
    if(candNum > 0){
      candList = convertMatchResult2CandList(matchResult, queryQue.queryType)
    }

    JdbcDatabase.update(sql) { ps =>
      ps.setInt(1, candNum)
      ps.setBytes(2, candHead)
      ps.setBytes(3, candList)
      ps.setInt(4, maxScore)
      ps.setString(5, oraSid)
    }
  }

  /**
    * 根据远程查询queryid获取查询结果信息
    * @param queryId
    * @param pkId
    * @param typ
    * @param dbId
    * @return
    */
  override def getMatchResultByQueryid(queryId: Long, pkId: String, typ: Short, dbId: Option[String]): Option[MatchResult] = {
    val matchResult = MatchResult.newBuilder()
    matchResult.setMatchId(queryId.toString)
    matchResult.setStatus(MatcherStatus.newBuilder().setCode(0))
    //val queryQueOpt = GafisNormalqueryQueryque.find_by_queryid(queryid).headOption
    val queryQueOpt = GafisNormalqueryQueryque.find_by_queryid_and_keyid_and_querytype(queryId, pkId, typ).headOption
    if(queryQueOpt.nonEmpty){
      val queryQue = queryQueOpt.get
      if(queryQue.status >= 2){
        if(queryQue.candlist != null){
          val matchResultObj = gaqryqueConverter.convertCandList2GAQUERYCANDSTRUCT(queryQue.candlist)
          matchResultObj.foreach{cand=>
            matchResult.addCandidateResult(cand)
          }
        }
        matchResult.setCandidateNum(queryQue.curcandnum)
        matchResult.setTimeElapsed(queryQue.timeElapsed)
        matchResult.setRecordNumMatched(queryQue.recordNumMatched)
        matchResult.setMaxScore(queryQue.hitpossibility.toInt)
        matchResult.setMaxcandnum(queryQue.maxcandnum.toLong) //最大候选数量
        return Option(matchResult.build())
      }
    }
    None
  }

  /**
    * 候选列表转换
 *
    * @param matchResult
    * @param queryType
    * @return
    */
  def convertMatchResult2CandList(matchResult: MatchResult, queryType: Int): Array[Byte] ={
    val result = new ByteArrayOutputStream()
    val candIter = matchResult.getCandidateResultList.iterator()
    var index = 0 //比对排名
    while (candIter.hasNext) {
      index += 1
      val cand = candIter.next()
      val gCand = new GAQUERYCANDSTRUCT
      gCand.nScore = cand.getScore
      gCand.szKey = cand.getObjectId
      gCand.nDBID = if (queryType == 0 || queryType == 1) 1 else 2
      gCand.nTableID = 2
      gCand.nIndex = cand.getPos.toByte
      gCand.tFinishTime = new AFISDateTime
      gCand.nStepOneRank = index
      result.write(gCand.toByteArray())
    }
    result.toByteArray
  }
  override def getQueryQue(oraSid: Int): QueryQue = {
    val sql = "select t.keyid, t.querytype, t.flag from GAFIS_NORMALQUERY_QUERYQUE t where t.ora_sid =?"
    JdbcDatabase.queryFirst(sql) { ps =>
      ps.setInt(1, oraSid)
    } { rs =>
      val keyId = rs.getString("keyid")
      val queryType = rs.getInt("querytype")
      val flag = rs.getInt("flag")
      new QueryQue(keyId, oraSid, queryType, if(flag == 2 || flag == 22) true else false)
    }.get
  }

  /**
    * 根据queryid获取比对状态
    * @param queryId
    * @param pkId
    * @param typ
    * @return
    */
  override def getMatchStatusByQueryid(queryId: Long, pkId: String, typ: Short): MatchStatus = {
    val sql = "select t.status from GAFIS_NORMALQUERY_QUERYQUE t where t.queryid=? and t.keyid=? and t.querytype=?"
    JdbcDatabase.queryFirst(sql){ps=>
      ps.setLong(1, queryId)
      ps.setString(2, pkId)     //新增两个判断条件
      ps.setShort(3, typ)
    }{rs=>
    val status = java.lang.Short.valueOf(rs.getShort("status"))
      return status match {
        case QueryConstants.STATUS_WAIT=>
          MatchStatus.WAITING_MATCH
        case QueryConstants.STATUS_MATCHING=>
          MatchStatus.MATCHING
        case QueryConstants.STATUS_SUCCESS=>
          MatchStatus.FINISHED
        case QueryConstants.STATUS_FAIL=>
          MatchStatus.FAILED
        case other => MatchStatus.UN_KNOWN
      }
    }

    MatchStatus.UN_KNOWN
  }

  /**
    * 获取比对状态正在比对任务SID
 *
    * @param size
    * @return
    */
  override def getSidByStatusMatching(size: Int, dbId: Option[String]): Seq[Long] = {
    val sidArr = ArrayBuffer[Long]()
    val sql = "select t.ora_sid from GAFIS_NORMALQUERY_QUERYQUE t where t.status=1 and rownum <=?"
    JdbcDatabase.queryWithPsSetter(sql){ps=>
      ps.setInt(1, size)
    }{rs=>
      sidArr += rs.getLong("ora_sid")
    }
    sidArr.toSeq
  }

  /**
    * 更新Status 不实现
    *
    * @param oraSid
    * @param status
    */
  override def updateMatchStatus(oraSid: Long, status: Int): Unit = {
  }

  /**
    * 根据orasid获取对应任务的捺印卡号 keyId
    *
    * @param oraSid
    */
  override def getKeyIdArrByOraSid(oraSid: Long): Seq[String] = ???

  /**
    * 根据orasid获取对应任务的查询类型 queryType
    *
    * @param sid
    */
  override def getQueryTypeArrByOraSid(sid: Long): Seq[String] = ???
  
    /**
    * 获得配置信息
    */
  override def getAfisinitConfig() : scala.collection.mutable.HashMap[String, String]  = ???
}
