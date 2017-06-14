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

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

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
  def fetchMatchTask(size: Int, dbId: Option[String] = None): Seq[MatchTask]

  /**
    * 保存候选信息
    * @param matchResult 候选信息
    * @param fetchConfig 同步配置
    * @param candDBIDMap 候选卡号->dbid
    */
  def saveMatchResult(matchResult: MatchResult, fetchConfig: HallFetchConfig, candDBIDMap: Map[String, Short] = Map(),configMap : scala.collection.mutable.HashMap[String, String])

  /**
    * 根据远程查询queryid获取查询结果信息
    * @param queryId
    * @param pkId
    * @param typ
    * @param dbId
    * @return
    */
  //def getMatchResultByQueryid(queryid: Long, dbId: Option[String] = None): Option[MatchResult]
  def getMatchResultByQueryid(queryId: Long, pkId: String, typ: Short, dbId: Option[String] = None): Option[MatchResult]

  /**
    * 获取比对状态正在比对任务SID
    * @param size
    * @return
    */
  def getSidByStatusMatching(size: Int, dbId: Option[String] = None): Seq[Long]
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
    candHead.nQueryID = DataConverter.convertLongAsSixByteArray(queryQue.oraSid)
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
    * 获得配置信息
    */
  def getAfisinitConfig() : scala.collection.mutable.HashMap[String, String]

  /**
    * 保存抓取记录
    * @param oraSid
    */
  def saveFetchRecord(oraSid:String)

  /**
    * 获得没有同步候选的比对任务的任务号
    * @param size 单次请求数量
    * @author yuchen
    */
  def getTaskNumWithNotSyncCandList(size:Int):ListBuffer[mutable.HashMap[String,Any]]


  def updateStatusWithGafis_Task62Record(status:String,uuid:String):Unit
}


