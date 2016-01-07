package nirvana.hall.api.internal.sync

import java.util.Date

import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.jpa._
import nirvana.hall.api.services.AutoSpringDataSourceSession
import nirvana.hall.api.services.sync.Sync7to6Service
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.services.GafisException
import org.apache.tapestry5.ioc.annotations.{EagerLoad, PostInjection}
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.joda.time.DateTime
import org.springframework.transaction.annotation.Transactional
import scalikejdbc._

/**
 * Created by songpeng on 15/12/1.
 */
@EagerLoad
class Sync7to6ServiceImpl(facade:V62Facade, v62Config:HallV62Config, apiConfig: HallApiConfig)
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
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    periodicExecutor.addJob(new CronSchedule(apiConfig.sync62Cron), "sync-70to62", new Runnable {
      override def run(): Unit = {
        doWork
      }
    })
  }

  //TODO 允许3次失败
  def findSyncQueue(implicit session: DBSession): Option[SyncQueue] ={
    SyncQueue.find_by_uploadStatus(UPLOAD_STATUS_WAIT).asc("createdate").takeOption
  }
  def doWork: Unit ={
    //这里手动提交事务
    implicit val session = AutoSpringDataSourceSession.apply()
    findSyncQueue.foreach{ syncQueue =>
      session.connection.setAutoCommit(false)
      doWork(syncQueue)
      session.connection.commit()
      doWork
    }
  }

  /**
   * 处理上报队列任务
   * @param syncQueue 上报队列
   * @param session
   * @return
   */
  @Transactional
  override def doWork(syncQueue: SyncQueue)(implicit session: DBSession): Unit = {
    val uploadFlag = syncQueue.uploadFlag
    try {
      uploadFlag match {
        case UPLOAD_FLAG_TPCARD =>
          syncTPCard(facade, v62Config, syncQueue)
        case UPLOAD_FLAG_CASE =>
          syncCase(facade, v62Config, syncQueue)
        case UPLOAD_FLAG_LPCARD =>
          syncLPCard(facade, v62Config, syncQueue)
        case other =>
          throw new RuntimeException("unknown uploadFlag " + other)
      }
      updateSyncQueueSucess(syncQueue)
    }
    catch {
      case e: Exception =>
        updateSyncQueueFail(syncQueue, e)
    }
  }

  /**
   * 更新队列任务状态
   * @param syncQueue
   */
  private def updateSyncQueueSucess(syncQueue: SyncQueue)(implicit session: DBSession): Unit ={
    syncQueue.uploadStatus = UPLOAD_STATUS_SUCCESS
    syncQueue.remark="success"
    syncQueue.save()

    //SyncQueue.where(pkId=syncQueue.pkId).update_set(uploadStatus=UPLOAD_STATUS_SUCCESS,remark="success").update()
    /*
    withSQL{
      val column = SyncQueue.column
      update(SyncQueue).set(column.uploadStatus -> UPLOAD_STATUS_SUCCESS, column.remark -> "success").where.eq(column.pkId, syncQueue.pkId)
    }.update().apply()
    */
  }

  private def updateSyncQueueFail(syncQueue: SyncQueue, exception: Exception)(implicit session: DBSession): Unit ={
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
