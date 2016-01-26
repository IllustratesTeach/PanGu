package nirvana.hall.v70.internal.sync

import java.util.Date
import javax.persistence.EntityManagerFactory

import nirvana.hall.v62.services.GafisException
import nirvana.hall.v70.config.HallV70Config
import nirvana.hall.v70.jpa.SyncQueue
import nirvana.hall.v70.services.sync.Sync7to6Service
import org.apache.tapestry5.ioc.annotations.{EagerLoad, PostInjection}
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.springframework.orm.jpa.{EntityManagerFactoryUtils, EntityManagerHolder}
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

/**
 * Created by songpeng on 15/12/1.
 */
@EagerLoad
class Sync7to6ServiceImpl(v70Config: HallV70Config)
  extends Sync7to6Service
  with Sync7to6CaseService
  with Sync7to6LPCardService
  with Sync7to6TPCardService{

  //上报标记(1:捺印;2:案件;3:现场)
  val UPLOAD_FLAG_TPCARD = "1"
  val UPLOAD_FLAG_CASE = "2"
  val UPLOAD_FLAG_LPCARD = "3"

  //上报成功标记(0:等待;1:成功;2:失败)
  val UPLOAD_STATUS_WAIT = "0"
  val UPLOAD_STATUS_SUCCESS = "1"
  val UPLOAD_STATUS_FAIL = "2"


  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, entityManagerFactory: EntityManagerFactory): Unit = {
    periodicExecutor.addJob(new CronSchedule(v70Config.sync62Cron), "sync-70to62", new Runnable {
      override def run(): Unit = {
        val emHolder= new EntityManagerHolder(entityManagerFactory.createEntityManager())
        TransactionSynchronizationManager.bindResource(entityManagerFactory,emHolder)
        doWork
        TransactionSynchronizationManager.unbindResource(entityManagerFactory)
        EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager)
      }
    })
  }

  //TODO 允许3次失败
  def findSyncQueue: Option[SyncQueue] ={
    SyncQueue.find_by_uploadStatus(UPLOAD_STATUS_WAIT).asc("createdate").takeOption
  }
  def doWork: Unit ={
    findSyncQueue.foreach{ syncQueue =>
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
    try {
      uploadFlag match {
        case UPLOAD_FLAG_TPCARD =>
          syncTPCard(syncQueue)
        case UPLOAD_FLAG_CASE =>
          syncCase(syncQueue)
        case UPLOAD_FLAG_LPCARD =>
          syncLPCard(syncQueue)
        case other =>
          throw new RuntimeException("unknown uploadFlag " + other)
      }
      updateSyncQueueSucess(syncQueue)
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
  private def updateSyncQueueSucess(syncQueue: SyncQueue): Unit ={
    syncQueue.uploadStatus = UPLOAD_STATUS_SUCCESS
    syncQueue.remark="success"
    syncQueue.finishdate=new Date()
    syncQueue.save()

  }

  @Transactional
  private def updateSyncQueueFail(syncQueue: SyncQueue, exception: Exception): Unit ={
    //TODO open session in thread
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
