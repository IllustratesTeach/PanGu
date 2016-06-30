package nirvana.hall.api.internal.sync

import javax.persistence.EntityManager

import monad.rpc.protocol.CommandProto.CommandStatus
import monad.support.services.LoggerSupport
import nirvana.hall.api.HallApiConstants
import nirvana.hall.api.config.{DBConfig, HallApiConfig}
import nirvana.hall.api.services.sync.{SyncConfigService, SyncService}
import nirvana.hall.api.services.{CaseInfoService, LPCardService, TPCardService}
import nirvana.hall.protocol.api.SyncDataProto._
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v70.jpa.GafisSyncConfig
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
    GafisSyncConfig.all.foreach{syncConfig=>
      doWork(syncConfig)
    }
  }

  def doWork(syncConfig: GafisSyncConfig): Unit ={
    val config = new JSONObject(syncConfig.config)
    config.get("type") match {
      case "TPCard" =>
        syncTPCard(syncConfig)
      case "Case" =>
      case "LPCard" =>
      case other =>
    }
  }

  def syncTPCard(syncConfig: GafisSyncConfig): Unit ={
    val request = SyncTPCardRequest.newBuilder()
    request.setSize(SYNC_BATCH_SIZE)
    request.setTimestamp(syncConfig.timestamp)
    val headerMap = getHeaderMap(syncConfig)
    val destDBConfig = getDestDBConfig(syncConfig)
    val baseResponse = rpcHttpClient.call(syncConfig.url, SyncTPCardRequest.cmd, request.build(), headerMap)
    if(baseResponse.getStatus == CommandStatus.OK){
      var timestamp = syncConfig.timestamp
      val response = baseResponse.getExtension(SyncTPCardResponse.cmd)
      val iter = response.getSyncTPCardList.iterator()
      while (iter.hasNext){
        val syncTPCard = iter.next()
        val tpCard = syncTPCard.getTpCard
        val cardId = tpCard.getStrCardID
        if(tpCardService.isExist(cardId,destDBConfig)){
          tpCardService.updateTPCard(tpCard, destDBConfig)
        }else{
          tpCardService.addTPCard(tpCard, destDBConfig)
        }
        timestamp = syncTPCard.getTimestamp
      }
      if(response.getSyncTPCardCount > 0){
        syncConfig.timestamp = timestamp
        syncConfigService.updateSyncConfig(syncConfig)
        info("success syncTPCard count {} timestamp {}", response.getSyncTPCardCount, timestamp)
        //递归
        syncTPCard(syncConfig)
      }
    }
  }

  def syncLPCard(syncConfig: GafisSyncConfig): Unit ={
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
        syncLPCard(syncConfig)
      }
    }
  }

  def syncCaseInfo(syncConfig: GafisSyncConfig): Unit ={
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
        syncCaseInfo(syncConfig)
      }
    }
  }

  /**
   * 获取request请求的头部信息
   * @param syncConfig
   * @return
   */
  private def getHeaderMap(syncConfig: GafisSyncConfig): Map[String, String] ={
    val json = new JSONObject(syncConfig.config)
    val dbId = if(json.has("src_db_id")) json.getString("src_db_id") else ""
    val tableId = if(json.has("src_table_id")) json.getString("src_table_id") else ""
    Map(HallApiConstants.HALL_HTTP_HEADER_DBID -> dbId, HallApiConstants.HALL_HTTP_HEADER_TABLEID -> tableId)
  }
  private def getDestDBConfig(syncConfig: GafisSyncConfig): DBConfig={
    val json = new JSONObject(syncConfig.config)
    DBConfig(Right(json.getString("dest_db_id")), if(json.has("dest_table_id")) Option(json.getString("dest_table_id").toShort) else None)
  }
}
