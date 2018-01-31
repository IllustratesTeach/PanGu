package nirvana.hall.api.internal.sync

import java.util.UUID
import javax.sql.DataSource

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.rpc.protocol.CommandProto.CommandStatus
import monad.support.services.LoggerSupport
import nirvana.hall.api.HallApiConstants
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.jpa.HallFetchConfig
import nirvana.hall.api.services._
import nirvana.hall.api.services.remote.{CaseInfoRemoteService, LPCardRemoteService, LPPalmRemoteService, TPCardRemoteService}
import nirvana.hall.api.services.sync.{FetchQueryService, LogicDBJudgeService, SyncCronService}
import nirvana.hall.c.services.gloclib.gaqryque
import nirvana.hall.protocol.api.FPTProto.{Case, MatchRelationInfo}
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.api.SyncDataProto._
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v70.internal.query.QueryConstants
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor
import org.apache.tapestry5.json.JSONObject

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * Created by songpeng on 16/8/18.
  */
class SyncCronServiceImpl(apiConfig: HallApiConfig,
                          rpcHttpClient: RpcHttpClient,
                          tpCardService: TPCardService,
                          lPCardService: LPCardService,
                          lPPalmService: LPPalmService,
                          caseInfoService: CaseInfoService,
                          queryService: QueryService,
                          fetchQueryService: FetchQueryService,
                          tPCardRemoteService: TPCardRemoteService,
                          lPCardRemoteService: LPCardRemoteService,
                          lPPalmRemoteService: LPPalmRemoteService,
                          syncInfoLogManageService: SyncInfoLogManageService,
                          logicDBJudgeService: LogicDBJudgeService,
                          caseInfoRemoteService: CaseInfoRemoteService,
                          matchRelationService:MatchRelationService, implicit val dataSource: DataSource) extends SyncCronService with LoggerSupport{

  final val SYNC_BATCH_SIZE = 1
  final val SYNC_MATCH_TASK_BATCH_SIZE = 5
  final val SYNC_MATCH_RELATION_BATCH_SIZE = 5
  /**
    * 定时器，同步数据
    *
    * @param periodicExecutor
    * @param syncCronService
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, syncCronService: SyncCronService): Unit = {
    if(apiConfig.sync.syncCron != null){
      periodicExecutor.addJob(new CronScheduleWithStartModel(apiConfig.sync.syncCron, StartAtDelay), "sync-cron", new Runnable {
        override def run(): Unit = {
          info("begin sync-cron")
          try{
            syncCronService.doWork
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
        case HallApiConstants.SYNC_TYPE_TPCARD =>
          fetchTPCard(fetchConfig, update)
        case HallApiConstants.SYNC_TYPE_LPCARD =>
          fetchLPCard(fetchConfig, update)
        case HallApiConstants.SYNC_TYPE_CASEINFO =>
        case HallApiConstants.SYNC_TYPE_LPPALM =>
          fetchLPPalm(fetchConfig, update)
        case HallApiConstants.SYNC_TYPE_MATCH_TASK =>
          fetchMatchTask(fetchConfig, update)
        case HallApiConstants.SYNC_TYPE_MATCH_RESULT =>
          fetchMatchResult(fetchConfig, update)
        case HallApiConstants.SYNC_TYPE_MATCH_RELATION =>
          fetchMatchRelation(fetchConfig, update)
        case other =>
          warn("unsupport fetch type:{}", other)
      }
    }
  }

  /**
    * 执行抓取数据
    *
    * @param fetchConfig
    */
  def fetchTPCard(fetchConfig: HallFetchConfig, update: Boolean): Unit ={
    info("syncTPCard name:{} current-seq:{}", fetchConfig.name, fetchConfig.seq)
    val uuid = UUID.randomUUID().toString
    var cardId = ""
    var seq = fetchConfig.seq
    var typ_add=""
    try {
      val request = SyncTPCardRequest.newBuilder()
      request.setSize(SYNC_BATCH_SIZE)
      request.setSeq(seq)
      request.setDbid(fetchConfig.dbid)
      request.setUuid(uuid)
      val baseResponse = rpcHttpClient.call(fetchConfig.url, SyncTPCardRequest.cmd, request.build())
      if(baseResponse.getStatus == CommandStatus.OK){
        val response = baseResponse.getExtension(SyncTPCardResponse.cmd)
        var destDBID = Option(fetchConfig.destDbid)
        val iterator = response.getSyncTPCardList.iterator()
        while (iterator.hasNext) {
          val syncTPCard = iterator.next()
          var tpCard = syncTPCard.getTpCard
          cardId = tpCard.getStrCardID
          if (syncTPCard.getOperationType == OperationType.PUT &&
            validateTPCardByWriteStrategy(tpCard, fetchConfig.writeStrategy)) {
            //读取策略信息,设置DataSource
            val strategy = new JSONObject(fetchConfig.writeStrategy)
            if(strategy.has("setdatasource")){
              tpCard = tpCard.toBuilder.setStrDataSource(strategy.getString("setdatasource")).build()
            }

            //逻辑分库处理
            destDBID = logicDBJudgeService.logicJudge(cardId,Option(fetchConfig.destDbid),HallApiConstants.SYNC_TYPE_TPCARD)
            //验证本地是否存在
            if (tpCardService.isExist(cardId, destDBID)) {
              if (update) {
                tpCardService.updateTPCard(tpCard, destDBID)
                info("update TPCard:{}", cardId)
                typ_add = HallApiConstants.UPDATE
              }
            } else {
              tpCardService.addTPCard(tpCard, destDBID)
              info("add TPCard:{}", cardId)
              typ_add = HallApiConstants.PUT
            }

          } else if(syncTPCard.getOperationType == OperationType.DEL) {
            if(tpCardService.isExist(cardId, destDBID)){
              tpCardService.delTPCard(cardId, destDBID)
              info("delete TPCard:{}", cardId)
              typ_add = HallApiConstants.DELETE
            }
          }
          seq = syncTPCard.getSeq
          info("TP-RequestData success,cardId:{}",cardId)

            syncInfoLogManageService.recordSyncDataIdentifyLog(uuid, cardId, fetchConfig.typ + typ_add, fetchConfig.url.substring(7,
              fetchConfig.url.length - 5)
              , HallApiConstants.MESSAGE_SEND, HallApiConstants.MESSAGE_RECEIVE_OR_SEND_SUCCESS)
        }
        seq = response.getSeq
        if(seq > 0 && fetchConfig.seq != seq){
          //更新配置seq
          fetchConfig.seq = seq
          updateSeq(fetchConfig)
          fetchTPCard(fetchConfig, update)
        }
      } else {
        syncInfoLogManageService.recordSyncDataLog(uuid, cardId, seq.toString, baseResponse.getMsg, HallApiConstants.LOG_ERROR_TYPE, SyncErrorConstants.SYNC_RETURN_FAIL +
          HallApiConstants.SYNC_TYPE_TPCARD)
      }
    } catch {
      case e: nirvana.hall.support.internal.CallRpcException =>
        val eInfo = ExceptionUtil.getStackTraceInfo(e)
        error("TP-RequestData fail,uuid:{};cardId:{};错误堆栈信息:{};错误信息:{}",uuid,cardId,eInfo,e.getMessage)
        syncInfoLogManageService.recordSyncDataLog(uuid, cardId, seq.toString, eInfo, HallApiConstants.LOG_ERROR_TYPE, SyncErrorConstants.SYNC_FETCH +
          HallApiConstants.SYNC_TYPE_TPCARD)
      case e: Exception =>
        val eInfo = ExceptionUtil.getStackTraceInfo(e)
        error("TP-RequestData fail,uuid:{};cardId:{};错误堆栈信息:{};错误信息:{}",uuid,cardId,eInfo,e.getMessage)
        syncInfoLogManageService.recordSyncDataLog(uuid, cardId, seq.toString, eInfo, HallApiConstants.LOG_ERROR_TYPE, SyncErrorConstants.SYNC_REQUEST_UNKNOWN +
          HallApiConstants.SYNC_TYPE_TPCARD)
    }
  }

  /**
    * 抓取现场指纹
    *
    * @param fetchConfig
    * @param update
    */
  def fetchLPCard(fetchConfig: HallFetchConfig, update: Boolean): Unit ={
    info("syncLPCard name:{} current-seq:{}", fetchConfig.name, fetchConfig.seq)
    var cardId = ""
    val uuid = UUID.randomUUID().toString
    var seq = fetchConfig.seq
    var typ_add=""
    try{
      val request = SyncLPCardRequest.newBuilder()
      request.setSize(SYNC_BATCH_SIZE)
      request.setSeq(fetchConfig.seq)
      request.setDbid(fetchConfig.dbid)
      request.setUuid(uuid)
      val baseResponse = rpcHttpClient.call(fetchConfig.url, SyncLPCardRequest.cmd, request.build())
      if(baseResponse.getStatus == CommandStatus.OK){
        info("LP-RequestData start")
        val response = baseResponse.getExtension(SyncLPCardResponse.cmd)
        var destDBID = Option(fetchConfig.destDbid)
        val iterator = response.getSyncLPCardList.iterator()
        while (iterator.hasNext) {
          val syncLPCard = iterator.next()
          var lpCard = syncLPCard.getLpCard
          cardId = lpCard.getStrCardID
          if (syncLPCard.getOperationType == OperationType.PUT &&
            validateLPCardByWriteStrategy(lpCard, fetchConfig.writeStrategy)) {

            //读取策略信息,设置DataSource
            val strategy = new JSONObject(fetchConfig.writeStrategy)
            if(strategy.has("setdatasource")){
              lpCard = lpCard.toBuilder.setStrDataSource(strategy.getString("setdatasource")).build()
            }

            //如果没有案件编号，截掉指纹编号后两位作为案件编号
            var caseId = lpCard.getText.getStrCaseId
            if(caseId.trim.length == 0){
              caseId = cardId.substring(0, cardId.length - 2)
              val lPCardBuilder = lpCard.toBuilder
              lPCardBuilder.getTextBuilder.setStrCaseId(caseId)
              lpCard = lPCardBuilder.build()
            }
            //逻辑分库处理
            //此处的destDBID采用新标准，上面有个从数据库取出的默认值，实际并没有作用，只是为防止语法错
            //逻辑分库处理
            destDBID = logicDBJudgeService.logicJudge(cardId,Option(fetchConfig.destDbid),HallApiConstants.SYNC_TYPE_LPCARD)
            //验证本地是否存在
            if (lPCardService.isExist(cardId, destDBID)) {
              if (update) {//更新
                lPCardService.updateLPCard(lpCard, destDBID)
                info("update LPCard:{}", cardId)
                typ_add= HallApiConstants.UPDATE
              }
              if(caseInfoService.isExist(caseId,destDBID)){
                fetchCaseInfo(caseId, fetchConfig.url, true,Option(fetchConfig.dbid), destDBID)
              }
            } else {
              //如果没有案件信息获取案件
              if(!caseInfoService.isExist(caseId, destDBID)){
                fetchCaseInfo(caseId, fetchConfig.url, false,Option(fetchConfig.dbid), destDBID)
              }else{
                fetchCaseInfo(caseId, fetchConfig.url, true,Option(fetchConfig.dbid), destDBID)
              }
              lPCardService.addLPCard(lpCard, destDBID)
              info("add LPCard:{}", cardId)
              typ_add = HallApiConstants.PUT
            }

          } else if (syncLPCard.getOperationType == OperationType.DEL){
            if(lPCardService.isExist(cardId, destDBID)){
              lPCardService.delLPCard(cardId, destDBID)
              info("delete LPCard:{}", cardId)
              typ_add = HallApiConstants.DELETE
            }
          }
          seq = syncLPCard.getSeq
          info("LP-RequestData success,cardId:{}",cardId)
            syncInfoLogManageService.recordSyncDataIdentifyLog(uuid, cardId, fetchConfig.typ + typ_add, fetchConfig.url.substring(7,
              fetchConfig.url.length - 5)
              ,HallApiConstants.MESSAGE_SEND, HallApiConstants.MESSAGE_RECEIVE_OR_SEND_SUCCESS)

        }
        seq = response.getSeq
        if(seq > 0 && fetchConfig.seq != seq){
          //更新配置seq
          fetchConfig.seq = seq
          updateSeq(fetchConfig)
          fetchLPCard(fetchConfig, update)
        }
        info("LP-RequestData success,seq:{};BatchSyncCompleted",seq)
      } else {
        syncInfoLogManageService.recordSyncDataLog(uuid, cardId, seq.toString, baseResponse.getMsg, HallApiConstants.LOG_ERROR_TYPE, SyncErrorConstants.SYNC_RETURN_FAIL +
          HallApiConstants.SYNC_TYPE_LPCARD)
      }
    }catch{
      case e: nirvana.hall.support.internal.CallRpcException =>
        val eInfo = ExceptionUtil.getStackTraceInfo(e)
        error("LP-RequestData fail,uuid:{};cardId:{};错误堆栈信息:{};错误信息:{}",uuid,cardId,eInfo,e.getMessage)
        syncInfoLogManageService.recordSyncDataLog(uuid, cardId,seq+"", eInfo, HallApiConstants.LOG_ERROR_TYPE, SyncErrorConstants.SYNC_FETCH +
          HallApiConstants.SYNC_TYPE_LPCARD)
      case e:Exception =>
        val eInfo = ExceptionUtil.getStackTraceInfo(e)
        error("LP-RequestData fail,uuid{};cardId:{};错误堆栈信息:{};错误信息:{}",uuid,cardId,eInfo,e.getMessage)
        syncInfoLogManageService.recordSyncDataLog(uuid, cardId,seq+"", eInfo, HallApiConstants.LOG_ERROR_TYPE, SyncErrorConstants.SYNC_REQUEST_UNKNOWN +
          HallApiConstants.SYNC_TYPE_LPCARD)
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
    * 抓取现场掌纹
    *
    * @param fetchConfig
    * @param update
    */
  def fetchLPPalm(fetchConfig: HallFetchConfig, update: Boolean): Unit ={
    info("syncLPPalm name:{} timestamp:{}", fetchConfig.name, fetchConfig.seq)
    var cardId = ""
    val uuid = UUID.randomUUID().toString
    var seq = fetchConfig.seq
    var typ_add="" //日志类型后缀
    try {
      val request = SyncLPPalmRequest.newBuilder()
      request.setSize(SYNC_BATCH_SIZE)
      request.setSeq(seq)
      request.setDbid(fetchConfig.dbid)
      request.setUuid(uuid)
      val baseResponse = rpcHttpClient.call(fetchConfig.url, SyncLPPalmRequest.cmd, request.build())
      if(baseResponse.getStatus == CommandStatus.OK) {
        info("LP-Plam-RequestData start")
        val response = baseResponse.getExtension(SyncLPPalmResponse.cmd)
        val destDBID = Option(fetchConfig.destDbid)
        val iter = response.getSyncLPCardList.iterator()
        while (iter.hasNext) {
          val syncLPCard = iter.next()
          var lpCard = syncLPCard.getLpCard
          cardId = lpCard.getStrCardID
          if (syncLPCard.getOperationType == OperationType.PUT &&
            validateLPCardByWriteStrategy(lpCard, fetchConfig.writeStrategy)) {
            //如果没有案件编号，截掉指纹编号后两位作为案件编号
            var caseId = lpCard.getText.getStrCaseId
            if (caseId.trim.length == 0) {
              caseId = cardId.substring(0, cardId.length - 2)
              val lPCardBuilder = lpCard.toBuilder
              lPCardBuilder.getTextBuilder.setStrCaseId(caseId)
              lpCard = lPCardBuilder.build()
            }
            //验证本地是否存在
            if (lPPalmService.isExist(cardId, destDBID)) {
              if (update) {//更新
                lPPalmService.updateLPCard(lpCard, destDBID)
                info("update LPPalm:{}", cardId)
                typ_add="-UPDATE"
              }
              if(caseInfoService.isExist(caseId, destDBID)){
                fetchCaseInfo(caseId, fetchConfig.url, true,Option(fetchConfig.dbid), destDBID)
              }
            } else {
              //如果没有案件信息获取案件
              if(!caseInfoService.isExist(caseId, destDBID)){
                fetchCaseInfo(caseId, fetchConfig.url, false,Option(fetchConfig.dbid), destDBID)
              }else{
                fetchCaseInfo(caseId, fetchConfig.url, true,Option(fetchConfig.dbid), destDBID)
              }
              lPPalmService.addLPCard(lpCard, destDBID)
              info("add LPPalm:{}", cardId)
              typ_add="-PUT"
            }

          } else if (syncLPCard.getOperationType == OperationType.DEL) {
            if (lPPalmService.isExist(cardId, destDBID)) {
              lPPalmService.delLPCard(cardId, destDBID)
              info("delete LPPalm:{}", cardId)
              typ_add="-DEL"
            }
          }
          seq = syncLPCard.getSeq
          info("LP-Plam-RequestData success,cardId:{}", cardId)
          if (!typ_add.equals("")) {
            syncInfoLogManageService.recordSyncDataIdentifyLog(uuid, cardId, fetchConfig.typ + typ_add, fetchConfig.url.substring(7,
              fetchConfig.url.length - 5)
              , "0", "1")
          }
        }
        seq = response.getSeq
        //如果获取到数据递归获取
        if (response.getSyncLPCardCount > 0 && fetchConfig.seq != seq) {
          //更新配置seq
          fetchConfig.seq = seq
          updateSeq(fetchConfig)
          fetchLPPalm(fetchConfig, update)
        }
        info("LP-Plam-RequestData success,seq:{};BatchSyncCompleted", seq)
      } else {
        syncInfoLogManageService.recordSyncDataLog(uuid, cardId, seq+"", null, 2, SyncErrorConstants.SYNC_RETURN_FAIL +
          HallApiConstants.SYNC_TYPE_LPPALM)
      }
    } catch {
      case e: nirvana.hall.support.internal.CallRpcException =>
        val eInfo = ExceptionUtil.getStackTraceInfo(e)
        error("LP-Plam-RequestData fail,uuid:{};cardId:{};错误堆栈信息:{};错误信息:{}",uuid,cardId,eInfo,e.getMessage)
        syncInfoLogManageService.recordSyncDataLog(uuid, cardId, seq+"",eInfo, 2, SyncErrorConstants.SYNC_FETCH +
          HallApiConstants.SYNC_TYPE_LPPALM)
      case e: Exception =>
        val eInfo = ExceptionUtil.getStackTraceInfo(e)
        error("LP-Plam-RequestData fail,uuid{};cardId:{};错误堆栈信息:{};错误信息:{}",uuid,cardId,eInfo,e.getMessage)
        syncInfoLogManageService.recordSyncDataLog(uuid, cardId, seq+"",eInfo, 2, SyncErrorConstants.SYNC_REQUEST_UNKNOWN +
          HallApiConstants.SYNC_TYPE_LPPALM)
    }
  }

  /**
    * 抓取比对任务
    *
    * @param fetchConfig
    * @param update
    */
  def fetchMatchTask(fetchConfig: HallFetchConfig, update: Boolean): Unit ={

    info("fetchMatchTask name:{}", fetchConfig.name)
    val uuid = UUID.randomUUID().toString
    var cardId = ""
    try {
      val request = SyncMatchTaskRequest.newBuilder()
      request.setSize(SYNC_MATCH_TASK_BATCH_SIZE)
      request.setDbid(fetchConfig.dbid)
      request.setUuid(uuid)
      val baseResponse = rpcHttpClient.call(fetchConfig.url, SyncMatchTaskRequest.cmd, request.build())
      if(baseResponse.getStatus == CommandStatus.OK) {
        val response = baseResponse.getExtension(SyncMatchTaskResponse.cmd)
        val iterator = response.getMatchTaskList.iterator()
        while (iterator.hasNext) {
          val matchTask = iterator.next()
          cardId = matchTask.getMatchId
          if (validateMatchTaskByWriteStrategy(matchTask, fetchConfig.writeStrategy)){
            val queryId = queryService.addMatchTask(matchTask)
            fetchQueryService.recordGafisTask(matchTask.getObjectId.toString,queryId.toString
              ,"0",matchTask.getMatchType.getNumber.toString,cardId,fetchConfig.pkId)
            info("add MatchTask:{} type:{}", matchTask.getMatchId, matchTask.getMatchType.toString)
            syncInfoLogManageService.recordSyncDataIdentifyLog(uuid, cardId, fetchConfig.typ, fetchConfig.url.substring(7,fetchConfig.url.length-5) ,HallApiConstants.MESSAGE_SEND,HallApiConstants.MESSAGE_RECEIVE_OR_SEND_SUCCESS)
          }
        }
        info("MatchTask-RequestData success,taskId:{};BatchSyncCompleted",cardId)
      } else {
        syncInfoLogManageService.recordSyncDataLog(uuid, cardId,baseResponse.getMsg , baseResponse.getMsg, HallApiConstants.LOG_MESSAGE_TYPE, SyncErrorConstants.SYNC_RETURN_FAIL + HallApiConstants.SYNC_TYPE_MATCH_TASK)
      }
    } catch {
      case e: nirvana.hall.support.internal.CallRpcException =>
        val eInfo = ExceptionUtil.getStackTraceInfo(e)
        error("MatchTask-RequestData fail,uuid:{};taskId:{};错误堆栈信息:{};错误信息:{}",uuid,cardId,eInfo,e.getMessage)
        syncInfoLogManageService.recordSyncDataLog(uuid, cardId, null, eInfo, HallApiConstants.LOG_ERROR_TYPE, SyncErrorConstants.SYNC_FETCH + HallApiConstants.SYNC_TYPE_MATCH_TASK)
      case e: Exception =>
        val eInfo = ExceptionUtil.getStackTraceInfo(e)
        error("MatchTask-RequestData fail,uuid:{};taskId:{};错误堆栈信息:{};错误信息:{}",uuid,cardId,eInfo,e.getMessage)
        syncInfoLogManageService.recordSyncDataLog(uuid, cardId, null, eInfo, HallApiConstants.LOG_ERROR_TYPE, SyncErrorConstants.SYNC_REQUEST_UNKNOWN + HallApiConstants.SYNC_TYPE_MATCH_TASK)
    }
  }

  /**
    * 抓取比对结果候选列表
    * TODO 先查询比对状态是正在比对的任务sid，然后再根据sid获取比对结果
    *
    * @param fetchConfig
    */
  def  fetchMatchResult(fetchConfig: HallFetchConfig, update: Boolean): Unit ={
    val  taskNumList = fetchQueryService.getTaskNumWithNotSyncCandList(SYNC_BATCH_SIZE)
    try{
        taskNumList.foreach{
           t => syncCandList(fetchConfig,t)
        }
    } catch {
      case e: Exception => error("抓取比对结果时异常:" + e.getMessage)
    }
  }

  /**
    * 抓取比中关系
    * @param fetchConfig
    */
  def  fetchMatchRelation(fetchConfig: HallFetchConfig, update: Boolean): Unit ={
    info("fetchMatchRelation name:{}", fetchConfig.name)
    val uuid = UUID.randomUUID().toString
    var seq = fetchConfig.seq
    try {
      val request = SyncMatchRelationRequest.newBuilder()
      request.setSize(SYNC_MATCH_RELATION_BATCH_SIZE)
      request.setDbid(fetchConfig.dbid)
      request.setSeq(seq)
      request.setUuid(uuid)
      val baseResponse = rpcHttpClient.call(fetchConfig.url, SyncMatchRelationRequest.cmd, request.build())
      if(baseResponse.getStatus == CommandStatus.OK){
        val response = baseResponse.getExtension(SyncMatchRelationResponse.cmd)
        val iterator = response.getSyncMatchRelationList.iterator()
        while (iterator.hasNext) {
          val syncMatchRelation = iterator.next()
          val matchRelationInfo = syncMatchRelation.getMatchRelationInfo
          if(matchRelationService.isExist(matchRelationInfo.getBreakid)){
            matchRelationService.updateMatchRelation(matchRelationInfo)
          }else{
            matchRelationService.addMatchRelation(matchRelationInfo)
          }
          val option = getKeyIdAndTcodeByQueryType(matchRelationInfo)
          updateCandListStatus(matchRelationInfo.getQueryTaskId
                                            ,matchRelationInfo.getQuerytype
                                            ,option.headOption.get._1
                                            ,option.headOption.get._2
                                            ,matchRelationInfo.getFg.getNumber)
        }
        seq = response.getSeq
        if(seq > 0 && fetchConfig.seq != seq){
          //更新配置seq
          fetchConfig.seq = seq
          updateSeq(fetchConfig)
          fetchMatchRelation(fetchConfig, update)
        }
      }
    } catch {
      case e: nirvana.hall.support.internal.CallRpcException =>
        val eInfo = ExceptionUtil.getStackTraceInfo(e)
        error("fetchMatchRelation-RequestData fail,uuid:{};seq:{};错误堆栈信息:{};错误信息:{}",uuid,seq,eInfo,e.getMessage)
        syncInfoLogManageService.recordSyncDataLog(uuid, seq.toString, null, eInfo, HallApiConstants.LOG_ERROR_TYPE, SyncErrorConstants.SYNC_FETCH + HallApiConstants.SYNC_TYPE_MATCH_RELATION)
      case e: Exception =>
        val eInfo = ExceptionUtil.getStackTraceInfo(e)
        error("fetchMatchRelation-RequestData fail,uuid:{};seq:{};错误堆栈信息:{};错误信息:{}",uuid,seq,eInfo,e.getMessage)
        syncInfoLogManageService.recordSyncDataLog(uuid, seq.toString, null, eInfo, HallApiConstants.LOG_ERROR_TYPE, SyncErrorConstants.SYNC_FETCH + HallApiConstants.SYNC_TYPE_MATCH_RELATION)
    }
  }


  private def syncCandList(fetchConfig: HallFetchConfig,hashMap:mutable.HashMap[String,Any]): Unit ={

    val uuid = UUID.randomUUID.toString
    val uniqueId = hashMap.get("uuid").get.toString
    val taskId = hashMap.get("queryid").get.toString

    try{
      info("fetchMatchTask name:{};taskNo:{} ", fetchConfig.name,hashMap.get("orasid").headOption.get)
      val request = SyncMatchResultRequest.newBuilder()
      request.setSid(taskId.toLong)
      request.setDbid(fetchConfig.dbid)
      request.setUuid(uuid)
      request.setPkid(hashMap.get("keyid").headOption.get.toString) //6.2比对任务的捺印卡号
      request.setTyp(hashMap.get("querytype").headOption.get.toString) //6.2比对任务的查询类型

      val baseResponse = rpcHttpClient.call(fetchConfig.url, SyncMatchResultRequest.cmd, request.build())
      if(baseResponse.getStatus == CommandStatus.OK){

        val response = baseResponse.getExtension(SyncMatchResultResponse.cmd)
        val matchStatus = response.getMatchStatus
        if(matchStatus.getNumber > 2 && matchStatus != MatchStatus.UN_KNOWN){//大于2有候选信息
          val matchResult = response.getMatchResult
          if(validateMatchResultByWriteStrategy(matchResult, fetchConfig.writeStrategy)){
            val candDBDIMap = fetchCandListDataByMatchResult(hashMap.get("orasid").headOption.get.toString,matchResult, fetchConfig)
            fetchQueryService.saveMatchResult(matchResult, fetchConfig: HallFetchConfig,candDBDIMap)
            fetchQueryService.updateStatusWithGafis_Task62Record(matchStatus.getNumber.toString,uniqueId)
            info("add MatchResult:{} candNum:{}", matchResult.getMatchId, matchResult.getCandidateNum)
          }
        }
      }
    } catch {
      case e: nirvana.hall.support.internal.CallRpcException =>
        val eInfo = ExceptionUtil.getStackTraceInfo(e)
        error("MatchResult-RequestData fail,uuid:{};taskId:{};错误堆栈信息:{};错误信息:{}",uuid,taskId,eInfo,e.getMessage)
        syncInfoLogManageService.recordSyncDataLog(uuid, taskId, null, eInfo,HallApiConstants.LOG_ERROR_TYPE, SyncErrorConstants.SYNC_FETCH + HallApiConstants.SYNC_TYPE_MATCH_RESULT)
        fetchQueryService.updateStatusWithGafis_Task62Record(HallApiConstants.CANDLIST_SYNC_FAIL.toString,uniqueId)
      case e: Exception =>
        val eInfo = ExceptionUtil.getStackTraceInfo(e)
        error("MatchResult-RequestData fail,uuid:{};taskId:{};错误堆栈信息:{};错误信息:{}",uuid,taskId,eInfo,e.getMessage)
        syncInfoLogManageService.recordSyncDataLog(uuid, taskId, null, eInfo,HallApiConstants.LOG_ERROR_TYPE, SyncErrorConstants.SYNC_REQUEST_UNKNOWN + HallApiConstants.SYNC_TYPE_MATCH_RESULT)
        fetchQueryService.updateStatusWithGafis_Task62Record(HallApiConstants.CANDLIST_SYNC_FAIL.toString,uniqueId)
    }
  }

  /**
    * 循环候选列表，如果本地没有，远程获取候选数据保存到默认库
    * TODO 1,候选应该只存对应指位的信息，不存文本，存到远程库
    * 解决方法需要候选信息增加dbid信息
    *
    * @param matchResult
    */
  private def fetchCandListDataByMatchResult(oraSid:String,matchResult: MatchResult,fetchConfig: HallFetchConfig): Map[String, Short]={
    val candDBIDMap = mutable.HashMap[String, Short]()
    val queryQue = fetchQueryService.getQueryQue(oraSid.toInt)
    val isPalm = queryQue.isPalm //指掌纹标识
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
            /*tpCardOpt.foreach{
              t =>
                var tpCard = t
                val strategy = new JSONObject(fetchConfig.writeStrategy)
                if(strategy.has("setdatasource")){
                  tpCard = tpCard.toBuilder.setStrDataSource(strategy.getString("setdatasource")).build()
                }
                if(strategy.has("update")){
                  if(strategy.getBoolean("update")){
                    tpCardService.updateTPCard(tpCard)
                  }
                }
            }*/
            candDBIDMap.+=(cardId -> V62Facade.DBID_TP_DEFAULT)
          }else{
            tpCardOpt.foreach{
              t =>
                var tpCard = t
                val strategy = new JSONObject(fetchConfig.writeStrategy)
                if(strategy.has("setdatasource")){
                  tpCard = tpCard.toBuilder.setStrDataSource(strategy.getString("setdatasource")).build()
                }
              //tpCardService.addTPCard(tpCard)
            }
            candDBIDMap.+=(cardId -> V62Facade.DBID_TP_DEFAULT)
          }
        }
      }else{//候选是现场
            val dbId = getLPDBIDByCardId(cardId, dbidList,isPalm)
            if(dbId.nonEmpty){
              candDBIDMap.+=(cardId -> dbId.get.toShort)
            }else {
              if(!isPalm){
                val lPCardOpt = lPCardRemoteService.getLPCard(cardId, fetchConfig.url, candDbId)
                if(!lPCardOpt.isEmpty){
                  lPCardOpt.filter(_.getStrCardID.nonEmpty).foreach { lpCard =>
                    if(!lPCardService.isExist(lpCard.getStrCardID)){
                      lPCardService.addLPCard(lpCard)
                    }
                    val caseId = lpCard.getText.getStrCaseId
                    if (!caseInfoService.isExist(caseId, Option(candDbId))) {
                      //获取案件
                      //fetchCaseInfo(caseId, fetchConfig.url, false, Option(candDbId))
                    }
                    candDBIDMap.+=(cardId -> V62Facade.DBID_LP_DEFAULT)
                  }
                }
              }
              else{
                val lPPalmOpt = lPPalmRemoteService.getLPPalm(cardId, fetchConfig.url, candDbId)
                lPPalmOpt.filter(_.getStrCardID.nonEmpty).foreach { lpCard =>
                  lPPalmService.addLPCard(lpCard)
                  val caseId = lpCard.getText.getStrCaseId
                  if (!caseInfoService.isExist(caseId, Option(candDbId))) {
                    //获取案件
                    //fetchCaseInfo(caseId, fetchConfig.url, false, Option(candDbId))
                  }
                  candDBIDMap.+=(cardId -> V62Facade.DBID_LP_DEFAULT)
                }
              }
            }
      }

    }

    candDBIDMap.toMap
  }

  /**
   * 根据捺印卡号查找所在DBID
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
  private def getLPDBIDByCardId(cardId: String, dbidList: Seq[String],isPalm:Boolean): Option[String]={
    dbidList.foreach{dbid =>
      if(!isPalm) {
        if (lPCardService.isExist(cardId, Option(dbid))) {
          return Option(dbid)
        }
      }
      else{
        if (lPPalmService.isExist(cardId, Option(dbid))) {
          return Option(dbid)
        }
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
    * 通过查询类型获得keyid和Tcode的值
    * 在数据库中比中关系表中由于根据不同的查询类型，存的列不固定，所以需要判断一下
    * @param matchRelationInfo
    * @return
    */
  private def getKeyIdAndTcodeByQueryType(matchRelationInfo:MatchRelationInfo): Seq[(String, String)] ={
    val arrayBuf = ArrayBuffer[(String, String)]()
    if(matchRelationInfo.getQuerytype == MatchType.FINGER_TT.getNumber||matchRelationInfo.getQuerytype == MatchType.FINGER_LL.getNumber){
      arrayBuf += (matchRelationInfo.getSrckey -> matchRelationInfo.getDestkey)
    }else if(matchRelationInfo.getQuerytype==MatchType.FINGER_TL.getNumber){
      arrayBuf += (matchRelationInfo.getDestkey -> matchRelationInfo.getSrckey)
    }else if(matchRelationInfo.getQuerytype==MatchType.FINGER_LT.getNumber){
      arrayBuf += (matchRelationInfo.getSrckey -> matchRelationInfo.getDestkey)
    }
    arrayBuf.toSeq
  }



  /**
    * 更新同步HallFetchConfig.seq 的值, 因为直接调用save方法，session问题,程序调用insert而不是update
    *
    * @param fetchConfig
    */
  private def updateSeq(fetchConfig: HallFetchConfig): Unit ={
    HallFetchConfig.update.set(seq = fetchConfig.seq).where(HallFetchConfig.pkId === fetchConfig.pkId).execute
  }

  /**
    * 更新6.2任务表中对应这条认定的候选信息的候选状态,暂时先写到这里
    * @param oraSid
    * @param taskType
    * @param keyId
    * @param fgp
    * @return
    */
  def updateCandListStatus(oraSid:String,taskType:Int,keyId:String,tCode:String,fgp:Int):Unit = {
    val queryStruts = queryService.getGAQUERYSTRUCT(oraSid.toLong)
    queryStruts.pstCand_Data.foreach{
      candList =>
        if(tCode.equals(candList.szKey) && fgp==candList.nIndex.toInt){
          candList.nCheckState = HallApiConstants.GAQRYCAND_CHKSTATE_MATCH.toByte
          //gaCand.nStatus = gaqryque.GAQRYCAND_STATUS_FINISHED.toByte -- not must
          if(taskType == QueryConstants.QUERY_TYPE_TT || taskType == QueryConstants.QUERY_TYPE_LL){
            candList.nFlag = (gaqryque.GAQRYCAND_FLAG_RECHECKED.toByte | gaqryque.GAQRYCAND_FLAG_BROKEN.toByte).toByte
          }
          if(taskType == QueryConstants.QUERY_TYPE_TL || taskType == QueryConstants.QUERY_TYPE_LT){
            candList.nFlag = (gaqryque.GAQRYCAND_FLAG_NEEDRECHECK.toByte | gaqryque.GAQRYCAND_FLAG_RECHECKED.toByte | gaqryque.GAQRYCAND_FLAG_BROKEN.toByte).toByte
          }
        }
    }
    queryService.updateCandListFromQueryQue(queryStruts)
  }

}
