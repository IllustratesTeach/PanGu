package nirvana.hall.api.internal.sync

import monad.rpc.protocol.CommandProto.CommandStatus
import monad.support.services.LoggerSupport
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.jpa.HallFetchConfig
import nirvana.hall.api.services.remote.{CaseInfoRemoteService, TPCardRemoteService}
import nirvana.hall.api.services.sync.SyncCronService
import nirvana.hall.api.services.{LPPalmService, CaseInfoService, LPCardService, TPCardService}
import nirvana.hall.protocol.api.FPTProto.Case
import nirvana.hall.protocol.api.SyncDataProto._
import nirvana.hall.support.services.RpcHttpClient
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.apache.tapestry5.json.JSONObject

/**
 * Created by songpeng on 16/8/18.
 */
class SyncCronServiceImpl(apiConfig: HallApiConfig,
                          rpcHttpClient: RpcHttpClient,
                          tpCardService: TPCardService,
                          lPCardService: LPCardService,
                          lPPalmService: LPPalmService,
                          caseInfoService: CaseInfoService,
                          tPCardRemoteService: TPCardRemoteService,
                          caseInfoRemoteService: CaseInfoRemoteService) extends SyncCronService with LoggerSupport{

  final val SYNC_BATCH_SIZE = 10
  final val FETCH_TYPE_TPCARD = "TPCard"
  final val FETCH_TYPE_LPCARD = "LPCard"
  final val FETCH_TYPE_LPPALM= "LPPalm"
  final val FETCH_TYPE_CASEINFO = "CaseInfo"
  /**
   * 定时器，向6.2同步数据
   * @param periodicExecutor
   * @param syncCronService
   */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, syncCronService: SyncCronService): Unit = {
    if(apiConfig.sync.syncCron != null){
      periodicExecutor.addJob(new CronSchedule(apiConfig.sync.syncCron), "sync-cron", new Runnable {
        override def run(): Unit = {
          info("begin sync-cron")
          syncCronService.doWork
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
        case FETCH_TYPE_TPCARD =>
          fetchTPCard(fetchConfig, update)
        case FETCH_TYPE_LPCARD =>
          fetchLPCard(fetchConfig, update)
        case FETCH_TYPE_CASEINFO =>
        case FETCH_TYPE_LPPALM =>
          fetchLPPalm(fetchConfig, update)
        case other =>
          warn("unsupport fetch type:{}", other)
      }
    }
  }

  /**
   * 执行抓取数据
   * @param fetchConfig
   */
  def fetchTPCard(fetchConfig: HallFetchConfig, update: Boolean): Unit ={
    info("syncTPCard name:{} timestamp:{}", fetchConfig.name, fetchConfig.seq)
    val request = SyncTPCardRequest.newBuilder()
    request.setSize(SYNC_BATCH_SIZE)
    request.setSeq(fetchConfig.seq)
    request.setDbid(fetchConfig.dbid)
    val baseResponse = rpcHttpClient.call(fetchConfig.url, SyncTPCardRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      val response = baseResponse.getExtension(SyncTPCardResponse.cmd)
      var seq = fetchConfig.seq
      val destDBID = Option(fetchConfig.destDbid)
      val iter = response.getSyncTPCardList.iterator()
      try {
        while (iter.hasNext) {
          val syncTPCard = iter.next()
          val tpCard = syncTPCard.getTpCard
          val cardId = tpCard.getStrCardID
          if (syncTPCard.getOperationType == OperationType.PUT &&
            validateTPCardByWriteStrategy(tpCard, fetchConfig.writeStrategy)) {
            //验证本地是否存在
            if (tpCardService.isExist(cardId, destDBID)) {
              if (update) {//更新
                tpCardService.updateTPCard(tpCard, destDBID)
              }
            } else {
              tpCardService.addTPCard(tpCard, destDBID)
            }
          } else {
            tpCardService.delTPCard(cardId, destDBID)
          }
          seq = syncTPCard.getSeq
        }
        fetchConfig.seq = response.getSeq
      }
      catch {
        case e: Exception =>
          fetchConfig.seq = seq
      }
      //更新配置
      fetchConfig.save()
    }
  }

  /**
   * 抓取现场指纹
   * @param fetchConfig
   * @param update
   */
  def fetchLPCard(fetchConfig: HallFetchConfig, update: Boolean): Unit ={
    info("syncLPCard name:{} timestamp:{}", fetchConfig.name, fetchConfig.seq)
    val request = SyncLPCardRequest.newBuilder()
    request.setSize(SYNC_BATCH_SIZE)
    request.setSeq(fetchConfig.seq)
    request.setDbid(fetchConfig.dbid)
    val baseResponse = rpcHttpClient.call(fetchConfig.url, SyncLPCardRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      val response = baseResponse.getExtension(SyncLPCardResponse.cmd)
      var seq = fetchConfig.seq
      val destDBID = Option(fetchConfig.destDbid)
      val iter = response.getSyncLPCardList.iterator()
      try {
        while (iter.hasNext) {
          val syncLPCard = iter.next()
          val lpCard = syncLPCard.getLpCard
          val cardId = lpCard.getStrCardID
          if (syncLPCard.getOperationType == OperationType.PUT &&
            validateLPCardByWriteStrategy(lpCard, fetchConfig.writeStrategy)) {
            //验证本地是否存在
            if (lPCardService.isExist(cardId, destDBID)) {
              if (update) {//更新
                lPCardService.updateLPCard(lpCard, destDBID)
              }
            } else {
              var caseId = lpCard.getText.getStrCaseId
              //如果没有案件编号，截掉指纹编号后两位作为案件编号同步案件信息
              if(caseId.trim.length == 0){
                caseId = cardId.substring(0, cardId.length - 2)
              }
              //如果没有案件信息获取案件
              if(!caseInfoService.isExist(caseId, destDBID)){
                fetchCaseInfo(caseId, fetchConfig)
              }
              lPCardService.addLPCard(lpCard, destDBID)
            }
          } else {
            lPCardService.delLPCard(cardId, destDBID)
          }
          seq = syncLPCard.getSeq
        }
        fetchConfig.seq = response.getSeq
      }
      catch {
        case e: Exception =>
          fetchConfig.seq = seq
      }
      //更新配置
      fetchConfig.save()
    }
  }

  /**
   * 根据案件编号同步案件信息
   * 由于只有案件编号没有物理配置信息，多物理库同步的dbid使用现场的dbid，tableid=4
   * 如果没有案件信息不同步案件信息，6.2存在只有现场没有案件的情况
   * @param caseId
   * @param fetchConfig
   */
  def fetchCaseInfo(caseId: String, fetchConfig: HallFetchConfig): Unit ={
    info("syncCaseInfo caseId:{}", caseId)
    if(caseInfoRemoteService.isExist(caseId, fetchConfig.url, Option(fetchConfig.dbid))){
      val caseInfo = caseInfoRemoteService.getCaseInfo(caseId, fetchConfig.url, Option(fetchConfig.dbid))
      caseInfoService.addCaseInfo(caseInfo)
    }else{
      //如果远程没有案件信息，系统自动新建一个案件，保证在7.0系统能够查询到数据
      warn("remote caseId:{} is not exist, system auto create", caseId)
      val caseInfo = Case.newBuilder()
      caseInfo.setStrCaseID(caseId)
      caseInfoService.addCaseInfo(caseInfo.build(), Option(fetchConfig.destDbid))
    }
  }

  /**
   * 抓取现场掌纹
   * @param fetchConfig
   * @param update
   */
  def fetchLPPalm(fetchConfig: HallFetchConfig, update: Boolean): Unit ={
    info("syncLPPalm name:{} timestamp:{}", fetchConfig.name, fetchConfig.seq)
    val request = SyncLPPalmRequest.newBuilder()
    request.setSize(SYNC_BATCH_SIZE)
    request.setSeq(fetchConfig.seq)
    request.setDbid(fetchConfig.dbid)
    val baseResponse = rpcHttpClient.call(fetchConfig.url, SyncLPPalmRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      val response = baseResponse.getExtension(SyncLPPalmResponse.cmd)
      var seq = fetchConfig.seq
      val destDBID = Option(fetchConfig.destDbid)
      val iter = response.getSyncLPCardList.iterator()
      try {
        while (iter.hasNext) {
          val syncLPCard = iter.next()
          val lpCard = syncLPCard.getLpCard
          val cardId = lpCard.getStrCardID
          if (syncLPCard.getOperationType == OperationType.PUT &&
            validateLPCardByWriteStrategy(lpCard, fetchConfig.writeStrategy)) {
            //验证本地是否存在
            if (lPPalmService.isExist(cardId, destDBID)) {
              if (update) {//更新
                lPPalmService.updateLPCard(lpCard, destDBID)
              }
            } else {
              var caseId = lpCard.getText.getStrCaseId
              //如果没有案件编号，截掉指纹编号后两位作为案件编号同步案件信息
              if(caseId.trim.length == 0){
                caseId = cardId.substring(0, cardId.length - 2)
              }
              //如果没有案件信息获取案件
              if(!caseInfoService.isExist(caseId, destDBID)){
                fetchCaseInfo(caseId, fetchConfig)
              }
              lPPalmService.addLPCard(lpCard, destDBID)
            }
          } else {
            lPPalmService.delLPCard(cardId, destDBID)
          }
          seq = syncLPCard.getSeq
        }
        fetchConfig.seq = response.getSeq
      }
      catch {
        case e: Exception =>
          fetchConfig.seq = seq
      }
      //更新配置
      fetchConfig.save()
    }
  }

}
