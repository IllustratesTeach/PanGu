package nirvana.hall.v70.internal.sync

import java.util.Date

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.remote.{CaseInfoRemoteService, LPCardRemoteService, TPCardRemoteService}
import nirvana.hall.api.services.{CaseInfoService, LPCardService, TPCardService}
import nirvana.hall.v62.services.GafisException
import nirvana.hall.v70.config.HallV70Config
import nirvana.hall.v70.jpa.SyncQueue
import nirvana.hall.v70.services.sync.Sync7to6Service
import org.apache.tapestry5.ioc.annotations.{EagerLoad, PostInjection}
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 15/12/1.
 */
@EagerLoad
class Sync7to6ServiceImpl(v70Config: HallV70Config,
                          tPCardService: TPCardService,
                          caseInfoService: CaseInfoService,
                          lPCardService: LPCardService,
                          tPCardRemoteService: TPCardRemoteService,
                          caseInfoRemoteService: CaseInfoRemoteService,
                          lPCardRemoteService: LPCardRemoteService)
  extends Sync7to6Service
  with LoggerSupport{

  //上报标记(1:捺印;2:案件;3:现场)
  val UPLOAD_FLAG_TPCARD = "1"
  val UPLOAD_FLAG_CASE = "2"
  val UPLOAD_FLAG_LPCARD = "3"

  //上报成功标记(0:等待;1:成功;2:失败)
  val UPLOAD_STATUS_WAIT = "0"
  val UPLOAD_STATUS_SUCCESS = "1"
  val UPLOAD_STATUS_FAIL = "2"

  /**
   * 上报任务定时器，向6.2上报数据
   * @param periodicExecutor
   * @param sync7to6Service
   */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, sync7to6Service: Sync7to6Service): Unit = {
    if(v70Config.cron.sync7to6Cron != null){
      periodicExecutor.addJob(new CronSchedule(v70Config.cron.sync7to6Cron), "sync-70to62", new Runnable {
        override def run(): Unit = {
          sync7to6Service.doWork
        }
      })
    }
  }

  //TODO 允许3次失败
  /**
   * 查询一条上报任务
   */
  def findSyncQueue: Option[SyncQueue] ={
    SyncQueue.where(SyncQueue.uploadStatus === UPLOAD_STATUS_WAIT).orderBy(SyncQueue.createdate).headOption
  }

  @Transactional
  override def doWork: Unit ={
    findSyncQueue.foreach{ syncQueue =>
      info("sync-70to62 sync_queue info[keyid:{} type:{} targetIp:{} port:{} userName:{}]", syncQueue.uploadKeyid, syncQueue.uploadType,
        syncQueue.targetIp, syncQueue.targetPort, syncQueue.targetUsername)
      doTaskOfSyncQueue(syncQueue)
      doWork
    }
  }

  /**
   * 处理上报队列任务
   * @param syncQueue 上报队列
   * @return
   */
  @Transactional
  override def doTaskOfSyncQueue(syncQueue: SyncQueue): Unit = {
    val uploadFlag = syncQueue.uploadFlag
    val url = "http://%s:%s".format(syncQueue.targetIp, syncQueue.targetPort)
    try {
      uploadFlag match {
        case UPLOAD_FLAG_TPCARD =>
          syncQueue.opration match {
            case "insert" =>
              val tpCard = tPCardService.getTPCard(syncQueue.uploadKeyid)
              tPCardRemoteService.addTPCard(tpCard, url)
            case "update" =>
              val tpCard = tPCardService.getTPCard(syncQueue.uploadKeyid)
              tPCardRemoteService.updateTPCard(tpCard, url)
            case "delete" =>
              tPCardRemoteService.deleteTPCard(syncQueue.uploadKeyid, url)
          }
        case UPLOAD_FLAG_CASE =>
          syncQueue.opration match {
            case "insert" =>
              val caseInfo = caseInfoService.getCaseInfo(syncQueue.uploadKeyid)
              caseInfoRemoteService.addCaseInfo(caseInfo, url)
            case "update" =>
              val caseInfo = caseInfoService.getCaseInfo(syncQueue.uploadKeyid)
              caseInfoRemoteService.updateCaseInfo(caseInfo, url)
            case "delete" =>
              caseInfoRemoteService.deleteCaseInfo(syncQueue.uploadKeyid, url)
          }
        case UPLOAD_FLAG_LPCARD =>
          syncQueue.opration match {
            case "insert" =>
              val lpCard = lPCardService.getLPCard(syncQueue.uploadKeyid)
              lPCardRemoteService.addLPCard(lpCard, url)
            case "update" =>
              val lpCard = lPCardService.getLPCard(syncQueue.uploadKeyid)
              lPCardRemoteService.updateLPCard(lpCard, url)
            case "delete" =>
              lPCardRemoteService.deleteLPCard(syncQueue.uploadKeyid, url)
          }
        case other =>
          throw new UnsupportedOperationException("unknown uploadFlag " + other)
      }
      updateSyncQueueSuccess(syncQueue)
    }
    catch {
      case e: Exception =>
        e.printStackTrace()
        updateSyncQueueFail(syncQueue, e)
    }
  }

  /**
   * 更新队列任务状态
   * @param syncQueue
   */
  private def updateSyncQueueSuccess(syncQueue: SyncQueue): Unit ={
    syncQueue.uploadStatus = UPLOAD_STATUS_SUCCESS
    syncQueue.remark="success"
    syncQueue.finishdate=new Date()
    syncQueue.save()

  }

  @Transactional
  private def updateSyncQueueFail(syncQueue: SyncQueue, exception: Exception): Unit ={
    val message = exception match {
      case e: GafisException =>
        e.getSimpleMessage
      case other =>
        other.getMessage
    }
    syncQueue.uploadStatus = UPLOAD_STATUS_FAIL
    syncQueue.remark=message
    syncQueue.finishdate=new Date()

    syncQueue.save()
  }
}
