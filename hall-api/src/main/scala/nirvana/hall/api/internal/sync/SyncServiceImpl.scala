package nirvana.hall.api.internal.sync

import javax.persistence.EntityManager

import monad.rpc.protocol.CommandProto.CommandStatus
import monad.support.services.LoggerSupport
import nirvana.hall.api.HallApiConstants
import nirvana.hall.api.config.{DBConfig, HallApiConfig}
import nirvana.hall.api.services.remote.CaseInfoRemoteService
import nirvana.hall.api.services.sync.{SyncConfigService, SyncService}
import nirvana.hall.api.services.{CaseInfoService, LPCardService, TPCardService}
import nirvana.hall.protocol.api.SyncDataProto._
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v70.jpa.GafisSyncConfig
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.apache.tapestry5.json.JSONObject

/**
 * Created by songpeng on 16/6/18.
 */
class SyncServiceImpl(entityManager: EntityManager, apiConfig: HallApiConfig,rpcHttpClient: RpcHttpClient,syncConfigService: SyncConfigService,
                      tpCardService: TPCardService,
                      lpCardService: LPCardService,
                      caseInfoService: CaseInfoService,
                      caseInfoRemoteService: CaseInfoRemoteService) extends SyncService with LoggerSupport{
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
          info("begin sync-cron")
          syncService.doWork
          info("end sync-cron")
        }
      })
    }
  }
  /**
   * 定时任务调用方法
   */
  override def doWork(): Unit = {
    //查询同步配置，依次执行同步任务
    GafisSyncConfig.find_by_deletag("1").foreach{syncConfig=>
      doWork(syncConfig)
    }
  }

  def doWork(syncConfig: GafisSyncConfig): Unit ={
    val config = new JSONObject(syncConfig.config)
    //是否覆盖更新, 默认更新
    val isUpdate = if(config.has("update")){
      config.getBoolean("update")
    }else true
    config.get("type") match {
      case "TPCard" =>
        syncTPCard(syncConfig, isUpdate)
      case "Case" =>
        syncCaseInfo(syncConfig, isUpdate)
      case "LPCard" =>
        syncLPCard(syncConfig, isUpdate)
      case other =>
    }
  }

  /**
   * 同步捺印信息
   * @param syncConfig
   */
  def syncTPCard(syncConfig: GafisSyncConfig, isUpdate: Boolean): Unit ={
    info("syncTPCard name:{} timestamp:{}", syncConfig.name, syncConfig.timestamp)
    val request = SyncTPCardRequest.newBuilder()
    request.setSize(SYNC_BATCH_SIZE)
    request.setTimestamp(syncConfig.timestamp)
    val headerMap = getHeaderMap(syncConfig)
    val baseResponse = rpcHttpClient.call(syncConfig.url, SyncTPCardRequest.cmd, request.build(), headerMap)
    if(baseResponse.getStatus == CommandStatus.OK){
      val destDBConfig = getDestDBConfig(syncConfig)
      var timestamp = syncConfig.timestamp
      val response = baseResponse.getExtension(SyncTPCardResponse.cmd)
      val iter = response.getSyncTPCardList.iterator()
      while (iter.hasNext){
        val syncTPCard = iter.next()
        val tpCard = syncTPCard.getTpCard
        val cardId = tpCard.getStrCardID
        if(tpCardService.isExist(cardId,destDBConfig)){
          if(isUpdate)//更新
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
        syncTPCard(syncConfig, isUpdate)
      }
    }
  }

  /**
   * 同步现场指纹, 同时获取案件信息
   * @param syncConfig
   */
  def syncLPCard(syncConfig: GafisSyncConfig, isUpdate: Boolean): Unit ={
    info("syncLPCard name:{} timestamp:{}", syncConfig.name, syncConfig.timestamp)
    val request = SyncLPCardRequest.newBuilder()
    request.setSize(SYNC_BATCH_SIZE)
    request.setTimestamp(syncConfig.timestamp)
    val headerMap = getHeaderMap(syncConfig)
    val baseResponse = rpcHttpClient.call(syncConfig.url, SyncLPCardRequest.cmd, request.build(), headerMap)
    if(baseResponse.getStatus == CommandStatus.OK){
      val destDBConfig = getDestDBConfig(syncConfig)
      var timestamp = syncConfig.timestamp
      val response = baseResponse.getExtension(SyncLPCardResponse.cmd)
      val iter = response.getSyncLPCardList.iterator()
      while (iter.hasNext){
        val syncLPCard = iter.next()
        val lpCard = syncLPCard.getLpCard
        val cardId = lpCard.getStrCardID
        if(lpCardService.isExist(cardId, destDBConfig)){
          if(isUpdate)
            lpCardService.updateLPCard(lpCard, destDBConfig)
        }else{
          var caseId = lpCard.getText.getStrCaseId
          //如果没有案件编号，截掉指纹编号后两位作为案件编号同步案件信息
          if(caseId.trim.length == 0){
            caseId = cardId.substring(0, cardId.length - 2)
          }
          //如果没有案件信息获取案件
          if(!caseInfoService.isExist(caseId, destDBConfig)){
            syncCaseInfo(caseId, syncConfig)
          }
          lpCardService.addLPCard(lpCard, destDBConfig)
        }
        timestamp = syncLPCard.getTimestamp
      }
      if(response.getSyncLPCardCount > 0){
        syncConfig.timestamp = timestamp
        syncConfigService.updateSyncConfig(syncConfig)
        info("success syncLPCard count {} timestamp {}", response.getSyncLPCardCount, timestamp)
        syncLPCard(syncConfig, isUpdate)
      }
    }
  }

  /**
   * 根据案件编号同步案件信息
   * 由于只有案件编号没有物理配置信息，多物理库同步的dbid使用现场的dbid，tableid=4
   * 如果没有案件信息不同步案件信息，6.2存在只有现场没有案件的情况
   * @param caseId
   * @param syncConfig
   */
  def syncCaseInfo(caseId: String, syncConfig: GafisSyncConfig): Unit ={
    info("syncCaseInfo caseId:{}", caseId)
    if(caseIdIsExist(caseId, syncConfig)){
      val json = new JSONObject(syncConfig.config)
      val dbId = json.getString("src_db_id")
      val tableId = V62Facade.TID_CASE.toString
      val headerMap = Map(HallApiConstants.HTTP_HEADER_DBID -> dbId, HallApiConstants.HTTP_HEADER_TABLEID -> tableId)
      val caseInfo = caseInfoRemoteService.getCaseInfo(caseId, syncConfig.url, headerMap)
      caseInfoService.addCaseInfo(caseInfo)
    }else{
      warn("remote caseId:{} is not exist ", caseId)
    }
  }

  /**
   * 验证是否有案件信息
   * @param caseId
   * @param syncConfig
   * @return
   */
  private def caseIdIsExist(caseId: String, syncConfig: GafisSyncConfig): Boolean = {
    val json = new JSONObject(syncConfig.config)
    val dbId = json.getString("src_db_id")
    val tableId = V62Facade.TID_CASE.toString
    val headerMap = Map(HallApiConstants.HTTP_HEADER_DBID -> dbId, HallApiConstants.HTTP_HEADER_TABLEID -> tableId)
    caseInfoRemoteService.isExist(caseId, syncConfig.url, headerMap)
  }

  def syncCaseInfo(syncConfig: GafisSyncConfig, isUpdate: Boolean): Unit ={
    info("syncCaseInfo name:{} timestamp:{}", syncConfig.name, syncConfig.timestamp)
    val request = SyncCaseRequest.newBuilder()
    request.setSize(SYNC_BATCH_SIZE)
    request.setTimestamp(syncConfig.timestamp)
    val headerMap = getHeaderMap(syncConfig)
    val baseResponse = rpcHttpClient.call(syncConfig.url, SyncCaseRequest.cmd, request.build(), headerMap)
    if(baseResponse.getStatus == CommandStatus.OK){
      val destDBConfig = getDestDBConfig(syncConfig)
      var timestamp = syncConfig.timestamp
      val response = baseResponse.getExtension(SyncCaseResponse.cmd)
      val iter = response.getSyncCaseList.iterator()
      while (iter.hasNext){
        val syncCaseInfo = iter.next()
        val caseInfo = syncCaseInfo.getCaseInfo
        val cardId = caseInfo.getStrCaseID
        if(caseInfoService.isExist(cardId, destDBConfig)){
          if(isUpdate)
            caseInfoService.updateCaseInfo(caseInfo, destDBConfig)
        }else{
          caseInfoService.addCaseInfo(caseInfo, destDBConfig)
        }
        timestamp = syncCaseInfo.getTimestamp
      }
      if(response.getSyncCaseCount > 0){
        syncConfig.timestamp = timestamp
        syncConfigService.updateSyncConfig(syncConfig)
        info("success syncCaseInfo count {} timestamp {}", response.getSyncCaseCount, timestamp)
        syncCaseInfo(syncConfig, isUpdate)
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
    Map(HallApiConstants.HTTP_HEADER_DBID -> dbId, HallApiConstants.HTTP_HEADER_TABLEID -> tableId)
  }
  private def getDestDBConfig(syncConfig: GafisSyncConfig): DBConfig={
    val json = new JSONObject(syncConfig.config)
    DBConfig(Right(json.getString("dest_db_id")), if(json.has("dest_table_id")) Option(json.getString("dest_table_id").toShort) else None)
  }
}
