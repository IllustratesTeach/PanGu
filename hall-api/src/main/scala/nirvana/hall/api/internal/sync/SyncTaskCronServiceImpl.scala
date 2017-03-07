package nirvana.hall.api.internal.sync

import java.util.UUID

import monad.rpc.protocol.CommandProto.CommandStatus
import monad.support.services.LoggerSupport
import nirvana.hall.api.{HallApiConstants, HallApiErrorConstants}
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.jpa.HallFetchConfig
import nirvana.hall.api.services._
import nirvana.hall.api.services.remote.{CaseInfoRemoteService, LPCardRemoteService, TPCardRemoteService}
import nirvana.hall.api.services.sync.{FetchQueryService, SyncCronService, SyncTaskCronService}
import nirvana.hall.protocol.api.FPTProto.Case
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.api.SyncDataProto._
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v70.internal.query.QueryConstants
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.apache.tapestry5.json.JSONObject

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * Created by songpeng on 16/8/18.
  */
class SyncTaskCronServiceImpl(apiConfig: HallApiConfig,
                              rpcHttpClient: RpcHttpClient,
                              tpCardService: TPCardService,
                              lPCardService: LPCardService,
                              caseInfoService: CaseInfoService,
                              queryService: QueryService,
                              fetchQueryService: FetchQueryService,
                              tPCardRemoteService: TPCardRemoteService,
                              lPCardRemoteService: LPCardRemoteService,
                              syncInfoLogManageService: SyncInfoLogManageService,
                              caseInfoRemoteService: CaseInfoRemoteService) extends SyncTaskCronService with LoggerSupport{

  final val SYNC_BATCH_SIZE = 1
  final val SYNC_MATCH_TASK_BATCH_SIZE = 5          //一批抓取的比对任务数


  /**
    * 定时器，同步数据
    *
    * @param periodicExecutor
    * @param syncTaskCronService
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, syncTaskCronService: SyncTaskCronService): Unit = {
    if(apiConfig.sync.syncCron2 != null){
      periodicExecutor.addJob(new CronSchedule(apiConfig.sync.syncCron2), "sync-cron", new Runnable {
        override def run(): Unit = {
          info("begin sync-cron")
          try{
            syncTaskCronService.doWork
          }catch{
            case e:Exception =>
              error(ExceptionUtil.getStackTraceInfo(e))
          }
          info("end sync-cron")
        }
      })
    }
  }

  /**
    * 定时任务调用方法
    */
  override def doWork(): Unit = {
    HallFetchConfig.find_by_deletag("1").toSeq.foreach{ fetchConfig=>
      val strategy = new JSONObject(fetchConfig.writeStrategy)
      val update = if (strategy.has("update")) strategy.getBoolean("update") else true
      fetchConfig.typ match {
        case HallApiConstants.SYNC_TYPE_MATCH_TASK =>
          fetchMatchTask(fetchConfig, update)
        case HallApiConstants.SYNC_TYPE_MATCH_RESULT =>
          fetchMatchResult(fetchConfig, update)
        case other =>
          warn("unsupport fetch type:{}", other)
      }
    }
  }

  /**
    * 根据案件编号同步案件信息
    * 由于只有案件编号没有物理配置信息，多物理库同步的dbid使用现场的dbid，tableid=4
    * 如果没有案件信息不同步案件信息，6.2存在只有现场没有案件的情况
    *
    * @param caseId
    */
  def fetchCaseInfo(caseId: String, url: String,isExist:Boolean,dbId: Option[String] = None, destDbId: Option[String] = None): Unit ={
    info("syncCaseInfo caseId:{}", caseId)
    if(caseInfoRemoteService.isExist(caseId, url, dbId.get)){
      val caseInfoOpt = caseInfoRemoteService.getCaseInfo(caseId, url, dbId.get)
      if(isExist){
        caseInfoOpt.foreach(caseInfoService.updateCaseInfo(_, destDbId))
      }else{
        caseInfoOpt.foreach(caseInfoService.addCaseInfo(_, destDbId))
      }
    }else{
      //如果远程没有案件信息，系统自动新建一个案件，保证在7.0系统能够查询到数据
      warn("remote caseId:{} is not exist, system auto create", caseId)
      if(!isExist){
        val caseInfo = Case.newBuilder()
        caseInfo.setStrCaseID(caseId)
        caseInfoService.addCaseInfo(caseInfo.build(), destDbId)
      }
    }
  }

  /**
    * 抓取比对任务
    *
    * @param fetchConfig
    * @param update
    */
  def fetchMatchTask(fetchConfig: HallFetchConfig, update: Boolean): Unit ={
    //info("fetchMatchTask name:{} seq:{}", fetchConfig.name, fetchConfig.seq)
    info("fetchMatchTask name:{}", fetchConfig.name)
    val uuid = UUID.randomUUID().toString
    var taskId = ""
    try {
      val request = SyncMatchTaskRequest.newBuilder()
      request.setSize(SYNC_MATCH_TASK_BATCH_SIZE)
      request.setDbid(fetchConfig.dbid)
      //request.setSeq(fetchConfig.seq)
      request.setUuid(uuid)
      val baseResponse = rpcHttpClient.call(fetchConfig.url, SyncMatchTaskRequest.cmd, request.build())
      if(baseResponse.getStatus == CommandStatus.OK) {
        val response = baseResponse.getExtension(SyncMatchTaskResponse.cmd)
        //var seq = fetchConfig.seq
        val iter = response.getMatchTaskList.iterator()
        while (iter.hasNext) {
          val matchTask = iter.next()
          taskId = matchTask.getMatchId
          if (validateMatchTaskByWriteStrategy(matchTask, fetchConfig.writeStrategy)) {
            //TODO queryDBConfig 添加是否更新校验
            queryService.addMatchTask(matchTask)
            info("add MatchTask:{} type:{}", matchTask.getMatchId, matchTask.getMatchType)
            //seq = matchTask.getObjectId
            syncInfoLogManageService.recordSyncDataIdentifyLog(uuid, taskId, fetchConfig.typ, fetchConfig.url.substring(7,fetchConfig.url.length-5) ,"0","1")
          }
        }
        info("MatchTask-RequestData success,taskId:{};BatchSyncCompleted",taskId)
      } else {
        syncInfoLogManageService.recordSyncDataLog(uuid, taskId, null, null, 2, HallApiErrorConstants.SYNC_RETURN_FAIL + HallApiConstants.SYNC_TYPE_MATCH_TASK)
      }
    } catch {
      case e: nirvana.hall.support.internal.CallRpcException =>
        val eInfo = ExceptionUtil.getStackTraceInfo(e)
        error("MatchTask-RequestData fail,uuid:{};taskId:{};错误堆栈信息:{};错误信息:{}",uuid,taskId,eInfo,e.getMessage)
        syncInfoLogManageService.recordSyncDataLog(uuid, taskId, null, eInfo, 2, HallApiErrorConstants.SYNC_FETCH + HallApiConstants.SYNC_TYPE_MATCH_TASK)
      case e: Exception =>
        val eInfo = ExceptionUtil.getStackTraceInfo(e)
        error("MatchTask-RequestData fail,uuid:{};taskId:{};错误堆栈信息:{};错误信息:{}",uuid,taskId,eInfo,e.getMessage)
        syncInfoLogManageService.recordSyncDataLog(uuid, taskId, null, eInfo, 2, HallApiErrorConstants.SYNC_REQUEST_UNKNOWN + HallApiConstants.SYNC_TYPE_MATCH_TASK)
    }
    //如果获取到数据递归获取
    /*     if(response.getMatchTaskCount  > 0 && fetchConfig.seq != seq){
           //更新配置seq
           fetchConfig.seq = seq
           updateSeq(fetchConfig)
           fetchMatchTask(fetchConfig, update)
         }*/
  }

  /**
    * 抓取比对结果候选列表
    * TODO 先查询比对状态是正在比对的任务sid，然后再根据sid获取比对结果
    *
    * @param fetchConfig
    */
  def  fetchMatchResult(fetchConfig: HallFetchConfig, update: Boolean): Unit ={


    val  sidIter = fetchQueryService.getSidByStatusMatching(SYNC_BATCH_SIZE).iterator
    try{
      while(sidIter.hasNext){
        val tmp=sidIter.next
        //info("fetchMatchTask name:{} seq:{}", fetchConfig.name, sidIter.next)
        info("fetchMatchTask name:{} ", fetchConfig.name, tmp)
        val request = SyncMatchResultRequest.newBuilder()
        request.setSid(tmp)
        request.setDbid(fetchConfig.dbid)

        request.setPkid(fetchQueryService.getKeyIdArrByOraSid(tmp).headOption.get) //6.2比对任务的捺印卡号
        request.setTyp(fetchQueryService.getQueryTypeArrByOraSid(tmp).headOption.get) //6.2比对任务的查询类型

        val baseResponse = rpcHttpClient.call(fetchConfig.url, SyncMatchResultRequest.cmd, request.build())
        if(baseResponse.getStatus == CommandStatus.OK){

          val response = baseResponse.getExtension(SyncMatchResultResponse.cmd)
          val matchStatus = response.getMatchStatus
          if(matchStatus.getNumber > 2 && matchStatus != MatchStatus.UN_KNOWN){//大于2有候选信息
          val matchResult = response.getMatchResult
            if(validateMatchResultByWriteStrategy(matchResult, fetchConfig.writeStrategy)){
              //获取候选信息
              val candDBDIMap = fetchCandListDataByMatchResult(matchResult, fetchConfig)
              val configMap = fetchQueryService.getAfisinitConfig()
              fetchQueryService.saveMatchResult(matchResult, fetchConfig: HallFetchConfig, candDBDIMap, configMap)
              info("add MatchResult:{} candNum:{}", matchResult.getMatchId, matchResult.getCandidateNum)
            }
          }
        }
      }
    } catch {
      case e: Exception => error("抓取比对结果时异常:" + e.getMessage)
    }
  }


  /**
    * 循环候选列表，如果本地没有，远程获取候选数据保存到默认库
    * TODO 1,候选应该只存对应指位的信息，不存文本，存到远程库
    * 解决方法需要候选信息增加dbid信息
    *
    * @param matchResult
    */
  private def fetchCandListDataByMatchResult(matchResult: MatchResult,fetchConfig: HallFetchConfig): Map[String, Short]={
    val candDBIDMap = mutable.HashMap[String, Short]()
    val queryQue = fetchQueryService.getQueryQue(matchResult.getMatchId.toInt)
    val dbidList = getDBIDList(fetchConfig, queryQue.queryType)
    val candIter = matchResult.getCandidateResultList.iterator()
    while (candIter.hasNext){
      val cand = candIter.next()
      val cardId = cand.getObjectId
      val candDbId = cand.getDbid
      if(queryQue.queryType == QueryConstants.QUERY_TYPE_TT || queryQue.queryType == QueryConstants.QUERY_TYPE_LT){//候选是捺印
      val dbId = getTPDBIDByCardId(cardId, dbidList)
        if(dbId.nonEmpty){
          candDBIDMap.+=(cardId -> dbId.get.toShort)
        } else {
          val tpCardOpt = tPCardRemoteService.getTPCard(cardId, fetchConfig.url, candDbId)
          /*
            捺印卡在6.2保存时先进行判断 若已存在执行更新捺印卡信息 不存在添加捺印卡信息  2016/12/7
             */
          if(tpCardService.isExist(cardId)){
            tpCardOpt.foreach(tpCardService.updateTPCard(_))
            candDBIDMap.+=(cardId -> V62Facade.DBID_TP_DEFAULT)
          }else{
            tpCardOpt.foreach(tpCardService.addTPCard(_))
            candDBIDMap.+=(cardId -> V62Facade.DBID_TP_DEFAULT)

          }

        }
      }else{//候选是现场
      val dbId = getLPDBIDByCardId(cardId, dbidList)
        if(dbId.nonEmpty){
          candDBIDMap.+=(cardId -> dbId.get.toShort)
        }else{
          val lPCardOpt = lPCardRemoteService.getLPCard(cardId, fetchConfig.url, candDbId)
          lPCardOpt.foreach{ lpCard =>
            lPCardService.addLPCard(lpCard)
            val caseId = lpCard.getText.getStrCaseId
            if(!caseInfoService.isExist(caseId, Option(candDbId))){//获取案件
              fetchCaseInfo(caseId, fetchConfig.url, false,Option(fetchConfig.dbid))
            }
            candDBIDMap.+=(cardId -> V62Facade.DBID_LP_DEFAULT)
          }
        }
      }

    }

    candDBIDMap.toMap
  }

  /**
    * 根据捺印卡号查找所在DBID
    *
    * @param cardId
    * @param dbidList
    * @return
    */
  private def getTPDBIDByCardId(cardId: String, dbidList: Seq[String]): Option[String]={
    dbidList.foreach{dbid =>
      if(tpCardService.isExist(cardId, Option(dbid))){
        return Option(dbid)
      }
    }
    None
  }

  /**
    * 根据现场卡号查找所在DBID
    *
    * @param cardId
    * @param dbidList
    * @return
    */
  private def getLPDBIDByCardId(cardId: String, dbidList: Seq[String]): Option[String]={
    dbidList.foreach{dbid =>
      if(lPCardService.isExist(cardId, Option(dbid))){
        return Option(dbid)
      }
    }
    None
  }

  /**
    * 获取DBID列表
    *
    * @param fetchConfig
    * @param queryType
    * @return
    */
  private def getDBIDList(fetchConfig: HallFetchConfig, queryType: Int): Seq[String]={
    val dbidList = new ArrayBuffer[String]()
    if(fetchConfig.config != null){
      val jsonObj= new JSONObject(fetchConfig.config)
      val key = queryType match {
        case QueryConstants.QUERY_TYPE_TT =>
          QueryConstants.FETCH_CONFIG_TPDB
        case QueryConstants.QUERY_TYPE_TL =>
          QueryConstants.FETCH_CONFIG_LPDB
        case QueryConstants.QUERY_TYPE_LT =>
          QueryConstants.FETCH_CONFIG_TPDB
        case QueryConstants.QUERY_TYPE_LL =>
          QueryConstants.FETCH_CONFIG_LPDB
        case other =>
          throw new RuntimeException("unsupport queryType:"+ queryType)
      }
      if(jsonObj.has(key)){
        val iter = jsonObj.getJSONArray(key).iterator()
        while (iter.hasNext){
          val db = iter.next()
          dbidList += db.toString
        }
      }
    }

    dbidList
  }

  /**
    * 更新同步HallFetchConfig.seq 的值, 因为直接调用save方法，session问题,程序调用insert而不是update
    *
    * @param fetchConfig
    */
  private def updateSeq(fetchConfig: HallFetchConfig): Unit ={
    HallFetchConfig.update.set(seq = fetchConfig.seq).where(HallFetchConfig.pkId === fetchConfig.pkId).execute
  }
}
