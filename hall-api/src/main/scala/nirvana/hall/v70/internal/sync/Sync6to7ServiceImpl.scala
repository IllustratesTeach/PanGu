package nirvana.hall.v70.internal.sync

import javax.persistence.EntityManager

import monad.rpc.protocol.CommandProto.CommandStatus
import monad.support.services.LoggerSupport
import nirvana.hall.api.services.{CaseInfoService, LPCardService, TPCardService}
import nirvana.hall.protocol.api.SyncDataProto._
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v70.config.HallV70Config
import nirvana.hall.v70.jpa.SyncGafis6Config
import nirvana.hall.v70.services.sync.Sync6to7Service
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.springframework.transaction.annotation.{Propagation, Transactional}

/**
 * Created by songpeng on 16/6/18.
 */
class Sync6to7ServiceImpl(entityManager: EntityManager,v70Config: HallV70Config,rpcHttpClient: RpcHttpClient, tpCardService: TPCardService, lpCardService: LPCardService, caseInfoService: CaseInfoService) extends Sync6to7Service with LoggerSupport{
  val SYNC_BATCH_SIZE = 1
  /**
   * 上报任务定时器，向6.2上报数据
   * @param periodicExecutor
   * @param sync6to7Service
   */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, sync6to7Service: Sync6to7Service): Unit = {
    if(v70Config.cron.sync6to7Cron != null){
      periodicExecutor.addJob(new CronSchedule(v70Config.cron.sync6to7Cron), "sync-6to7", new Runnable {
        override def run(): Unit = {
          sync6to7Service.doWork
        }
      })
    }
  }
  /**
   * 定时任务调用方法
   */
  override def doWork(): Unit = {
    //从数据库读取配置
    val syncGafis6 = SyncGafis6Config.find("1")
    //同步捺印
    syncTPCard(syncGafis6)
    //同步案件
    syncCaseInfo(syncGafis6)
    //同步现场
    syncLPCard(syncGafis6)
  }

  @Transactional(propagation=Propagation.REQUIRES_NEW)
  override def syncTPCard(syncGafis6Config: SyncGafis6Config): Unit ={
    val url = "http://"+syncGafis6Config.ip+":"+syncGafis6Config.port
    val request = SyncTPCardRequest.newBuilder()
    request.setSize(SYNC_BATCH_SIZE)
    request.setTimestamp(syncGafis6Config.tpcardTimestamp)
    val baseResponse = rpcHttpClient.call(url, SyncTPCardRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      var timestamp = syncGafis6Config.tpcardTimestamp
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
        syncGafis6Config.tpcardTimestamp = timestamp
        syncGafis6Config.save()
        info("sucess syncTPCard count {} timestamp {}", response.getSyncTPCardCount, timestamp)
        syncTPCard(syncGafis6Config)
      }
    }
  }
  @Transactional(propagation=Propagation.REQUIRES_NEW)
  def syncLPCard(syncGafis6Config: SyncGafis6Config): Unit ={
    val url = "http://"+syncGafis6Config.ip+":"+syncGafis6Config.port
    val request = SyncLPCardRequest.newBuilder()
    request.setSize(SYNC_BATCH_SIZE)
    request.setTimestamp(syncGafis6Config.lpcardTimestamp)
    val baseResponse = rpcHttpClient.call(url, SyncLPCardRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      var timestamp = syncGafis6Config.lpcardTimestamp
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
        syncGafis6Config.lpcardTimestamp = timestamp
        syncGafis6Config.save()
        info("sucess syncLPCard count {} timestamp {}", response.getSyncLPCardCount, timestamp)
        syncLPCard(syncGafis6Config)
      }
    }
  }

  @Transactional(propagation=Propagation.REQUIRES_NEW)
  def syncCaseInfo(syncGafis6Config: SyncGafis6Config): Unit ={
    val url = "http://"+syncGafis6Config.ip+":"+syncGafis6Config.port
    val request = SyncCaseRequest.newBuilder()
    request.setSize(SYNC_BATCH_SIZE)
    request.setTimestamp(syncGafis6Config.caseTimestamp)
    val baseResponse = rpcHttpClient.call(url, SyncCaseRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      var timestamp = syncGafis6Config.caseTimestamp
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
        syncGafis6Config.caseTimestamp = timestamp
        syncGafis6Config.save()
        info("sucess syncCaseInfo count {} timestamp {}", response.getSyncCaseCount, timestamp)
        syncCaseInfo(syncGafis6Config)
      }
    }
  }
}
