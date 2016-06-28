package nirvana.hall.api.internal.sync

import javax.persistence.EntityManager

import monad.rpc.protocol.CommandProto.CommandStatus
import monad.support.services.LoggerSupport
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.services.sync.{SyncConfigService, SyncService}
import nirvana.hall.api.services.{CaseInfoService, LPCardService, TPCardService}
import nirvana.hall.protocol.api.SyncDataProto._
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v70.jpa.SyncConfig
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.apache.tapestry5.json.JSONObject

/**
 * Created by songpeng on 16/6/18.
 */
class SyncServiceImpl(entityManager: EntityManager, apiConfig: HallApiConfig,rpcHttpClient: RpcHttpClient,syncConfigService: SyncConfigService, tpCardService: TPCardService, lpCardService: LPCardService, caseInfoService: CaseInfoService) extends SyncService with LoggerSupport{
  val SYNC_BATCH_SIZE = apiConfig.sync.batchSize
  /**
   * 定时器，向6.2同步数据
   * @param periodicExecutor
   * @param syncService
   */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, syncService: SyncService): Unit = {
    if(apiConfig.sync.syncCron != null){
      periodicExecutor.addJob(new CronSchedule(apiConfig.sync.syncCron), "sync-cron", new Runnable {
        override def run(): Unit = {
          syncService.doWork
        }
      })
    }
  }
  /**
   * 定时任务调用方法
   */
  override def doWork(): Unit = {
    SyncConfig.all.foreach{syncConfig=>
      doWork(syncConfig)
    }
  }

  def doWork(syncConfig: SyncConfig): Unit ={
    val config = new JSONObject(syncConfig.config)
    config.get("type") match {
      case "TPCard" =>
        syncTPCard(syncConfig)
      case "Case" =>
      case "LPCard" =>
      case other =>
    }
  }

  def syncTPCard(syncConfig: SyncConfig): Unit ={
    val request = SyncTPCardRequest.newBuilder()
    request.setSize(SYNC_BATCH_SIZE)
    request.setTimestamp(syncConfig.timestamp)
    val baseResponse = rpcHttpClient.call(syncConfig.url, SyncTPCardRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      var timestamp = syncConfig.timestamp
      val response = baseResponse.getExtension(SyncTPCardResponse.cmd)
      val iter = response.getSyncTPCardList.iterator()
      while (iter.hasNext){
        val syncTPCard = iter.next()
        val tpCard = syncTPCard.getTpCard
        val cardId = tpCard.getStrCardID
        if(!tpCardService.isExist(cardId)){
          tpCardService.addTPCard(tpCard)
        }
        timestamp = syncTPCard.getTimestamp
      }
      if(response.getSyncTPCardCount > 0){
        syncConfig.timestamp = timestamp
        syncConfigService.updateSyncConfig(syncConfig)
        info("success syncTPCard count {} timestamp {}", response.getSyncTPCardCount, timestamp)
        syncTPCard(syncConfig)
      }
    }
  }

  /*def syncLPCard(): Unit ={
    val syncConfig = syncConfigService.getSyncConfig()
    val request = SyncLPCardRequest.newBuilder()
    request.setSize(SYNC_BATCH_SIZE)
    request.setTimestamp(syncConfig.timestamp)
    val baseResponse = rpcHttpClient.call(syncConfig.url, SyncLPCardRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      var timestamp = syncConfig.timestamp
      val response = baseResponse.getExtension(SyncLPCardResponse.cmd)
      val iter = response.getSyncLPCardList.iterator()
      while (iter.hasNext){
        val syncLPCard = iter.next()
        val lpCard = syncLPCard.getLpCard
        val cardId = lpCard.getStrCardID
        if(!lpCardService.isExist(cardId)){
          lpCardService.addLPCard(lpCard)
        }
        timestamp = syncLPCard.getTimestamp
      }
      if(response.getSyncLPCardCount > 0){
        syncConfig.timestamp = timestamp
        syncConfigService.updateSyncConfig(syncConfig)
        info("success syncLPCard count {} timestamp {}", response.getSyncLPCardCount, timestamp)
        syncLPCard()
      }
    }
  }

  def syncCaseInfo(): Unit ={
    val syncConfig = syncConfigService.getSyncConfig()
    val request = SyncCaseRequest.newBuilder()
    request.setSize(SYNC_BATCH_SIZE)
    request.setTimestamp(syncConfig.timestamp)
    val baseResponse = rpcHttpClient.call(syncConfig.url, SyncCaseRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      var timestamp = syncConfig.timestamp
      val response = baseResponse.getExtension(SyncCaseResponse.cmd)
      val iter = response.getSyncCaseList.iterator()
      while (iter.hasNext){
        val syncCaseInfo = iter.next()
        val caseInfo = syncCaseInfo.getCaseInfo
        val cardId = caseInfo.getStrCaseID
        if(!caseInfoService.isExist(cardId)){
          caseInfoService.addCaseInfo(caseInfo)
        }
        timestamp = syncCaseInfo.getTimestamp
      }
      if(response.getSyncCaseCount > 0){
        syncConfig.timestamp = timestamp
        syncConfigService.updateSyncConfig(syncConfig)
        info("success syncCaseInfo count {} timestamp {}", response.getSyncCaseCount, timestamp)
        syncCaseInfo()
      }
    }
  }*/
}
