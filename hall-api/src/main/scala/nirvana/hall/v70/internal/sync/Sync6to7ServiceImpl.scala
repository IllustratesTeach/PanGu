package nirvana.hall.v70.internal.sync

import javax.persistence.EntityManager

import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.api.services.{CaseInfoService, LPCardService, TPCardService}
import nirvana.hall.protocol.api.SyncDataProto._
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v70.config.HallV70Config
import nirvana.hall.v70.jpa.SyncGafis6Config
import nirvana.hall.v70.services.sync.Sync6to7Service
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/6/18.
 */
class Sync6to7ServiceImpl(entityManager: EntityManager,v70Config: HallV70Config,rpcHttpClient: RpcHttpClient, tpCardService: TPCardService, lpCardService: LPCardService, caseInfoService: CaseInfoService) extends Sync6to7Service{
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
  @Transactional
  override def doWork(): Unit = {
    //TODO 手动提交事务
    val syncGafis6 = SyncGafis6Config.find("1")
    syncTPCard(syncGafis6)
    syncCaseInfo(syncGafis6)
    syncLPCard(syncGafis6)
  }

  @Transactional
  def syncTPCard(syncGafis6Config: SyncGafis6Config): Unit ={
    val url = "http://"+syncGafis6Config.ip+":"+syncGafis6Config.port
    val request = SyncTPCardRequest.newBuilder()
    request.setSize(1)
    request.setTimestamp(syncGafis6Config.tpcardTimestamp)
    val baseResponse = rpcHttpClient.call(url, SyncTPCardRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      val syncGafis6 = SyncGafis6Config.find("1")
      syncTPCard(syncGafis6)
      val response = baseResponse.getExtension(SyncTPCardResponse.cmd)
      val iter = response.getSyncTPCardList.iterator()
      while (iter.hasNext){
        val syncTPCard = iter.next()
        val tpCard = syncTPCard.getTpCard
        val cardId = tpCard.getStrCardID
        if(!tpCardService.isExist(cardId)){
          tpCardService.addTPCard(tpCard)
        }
        syncGafis6Config.tpcardTimestamp = syncTPCard.getTimestamp
        syncGafis6Config.save()
      }
//      syncTPCard(syncGafis6Config)
    }
  }
  @Transactional
  def syncLPCard(syncGafis6Config: SyncGafis6Config): Unit ={
    val url = "http://"+syncGafis6Config.ip+":"+syncGafis6Config.port
    val request = SyncLPCardRequest.newBuilder()
    request.setSize(1)
    request.setTimestamp(syncGafis6Config.lpcardTimestamp)
    val baseResponse = rpcHttpClient.call(url, SyncLPCardRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      val response = baseResponse.getExtension(SyncLPCardResponse.cmd)
      val iter = response.getSyncLPCardList.iterator()
      while (iter.hasNext){
        val syncLPCard = iter.next()
        val lpCard = syncLPCard.getLpCard
        val cardId = lpCard.getStrCardID
        if(!lpCardService.isExist(cardId)){
          lpCardService.addLPCard(lpCard)
        }
        syncGafis6Config.lpcardTimestamp = syncLPCard.getTimestamp
        syncGafis6Config.save()
      }
//      syncLPCard(syncGafis6Config)
    }
  }

  @Transactional
  def syncCaseInfo(syncGafis6Config: SyncGafis6Config): Unit ={
    val url = "http://"+syncGafis6Config.ip+":"+syncGafis6Config.port
    val request = SyncCaseRequest.newBuilder()
    request.setSize(1)
    request.setTimestamp(syncGafis6Config.caseTimestamp)
    val baseResponse = rpcHttpClient.call(url, SyncCaseRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      val response = baseResponse.getExtension(SyncCaseResponse.cmd)
      val iter = response.getSyncCaseList.iterator()
      while (iter.hasNext){
        val syncCaseInfo = iter.next()
        val caseInfo = syncCaseInfo.getCaseInfo
        val cardId = caseInfo.getStrCaseID
        if(!caseInfoService.isExist(cardId)){
          caseInfoService.addCaseInfo(caseInfo)
        }
        syncGafis6Config.caseTimestamp = syncCaseInfo.getTimestamp
        syncGafis6Config.save()
      }
//      syncCaseInfo(syncGafis6Config)
    }
  }
}
