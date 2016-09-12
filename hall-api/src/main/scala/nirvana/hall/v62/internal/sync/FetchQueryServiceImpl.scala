package nirvana.hall.v62.internal.sync

import java.io.ByteArrayOutputStream
import javax.sql.DataSource

import nirvana.hall.api.config.QueryQue
import nirvana.hall.api.jpa.HallFetchConfig
import nirvana.hall.api.services.TPCardService
import nirvana.hall.api.services.sync.FetchQueryService
import nirvana.hall.c.services.ghpcbase.ghpcdef.AFISDateTime
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYCANDSTRUCT
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.GafisNormalqueryQueryque

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/8/31.
 */
class FetchQueryServiceImpl(facade: V62Facade, config:HallV62Config, tPCardService: TPCardService, implicit val dataSource: DataSource) extends FetchQueryService{
  /**
   * 获取比对任务
   * @param size
   * @param dbId
   * @return
   */
  override def fetchMatchTask(seq: Long, size: Int, dbId: Option[String]): Seq[MatchTask] = {
    val matchTaskList = new ArrayBuffer[MatchTask]
    val sql = "select t.ora_sid, t.keyid, t.querytype, t.maxcandnum, t.minscore, t.priority, t.mic, t.qrycondition, t.textsql, t.flag from NORMALQUERY_QUERYQUE t where t.ora_sid >? and t.ora_sid <=?"
    JdbcDatabase.queryWithPsSetter(sql){ps=>
      ps.setLong(1, seq)
      ps.setLong(2, seq + size)
    }{rs=>
      val gaQuery = new GafisNormalqueryQueryque()
      gaQuery.oraSid = rs.getLong("ora_sid")
      gaQuery.keyid = rs.getString("keyid")
      gaQuery.minscore = rs.getInt("minscore")
      gaQuery.querytype = rs.getShort("querytype")
      gaQuery.priority = rs.getShort("priority")
      gaQuery.maxcandnum = rs.getInt("maxcandnum")
      gaQuery.mic = rs.getBytes("mic")
      matchTaskList += ProtobufConverter.convertGafisNormalqueryQueryque2MatchTask(gaQuery)
    }

    matchTaskList.toSeq
  }

  /**
   * 更新比对状态
   * @param oraSid
   * @param status
   */
  def updateMatchStatus(oraSid: Long, status: Int): Unit ={
    val sql = "update NORMALQUERY_QUERYQUE t set t.status =? where t.ora_sid =?"
    JdbcDatabase.update(sql){ps=>
      ps.setInt(1, status)
      ps.setLong(2, oraSid)
    }
  }

  /**
   * 根据远程查询queryid获取查询结果信息
   * @param queryid
   * @return
   */
  override def getMatchResultByQueryid(queryid: Long, dbId: Option[String]): Option[MatchResult] = {
    throw new UnsupportedOperationException
  }
  /**
   * 保存候选信息
   * @param matchResult
   */
  override def saveMatchResult(matchResult: MatchResult, fetchConfig: HallFetchConfig) = {
    val sql = "update NORMALQUERY_QUERYQUE t set t.status=2, t.curcandnum=?, t.candhead=?, t.candlist=?, t.hitpossibility=?, t.FINISHTIME=sysdate where t.ora_sid =?"
    val oraSid = matchResult.getMatchId
    val candNum = matchResult.getCandidateNum
    val maxScore = matchResult.getMaxScore
    val queryQue = getQueryQue(oraSid.toInt)
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
      ps.setLong(5, oraSid.toLong)
    }
  }
  //TODO dbid要遍历数据库查找
  private def convertMatchResult2CandList(matchResult: MatchResult, queryType: Int): Array[Byte] ={
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


  /**
   * 获取查询信息
   * @param oraSid
   * @return
   */
  override def getQueryQue(oraSid: Int): QueryQue = {
    val sql = "select t.keyid, t.querytype, t.flag from NORMALQUERY_QUERYQUE t where t.ora_sid =?"
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
   * 根据卡号查找
   * @param matchResult
   * @param queryType
   */
  private def getCardIdDbidMap(matchResult: MatchResult, queryType: Int): Unit ={

  }

}
