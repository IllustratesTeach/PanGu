package nirvana.hall.api.internal.sync

import javax.inject.Inject

import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.entities._
import nirvana.hall.api.services.DictService
import nirvana.hall.api.services.sync.Sync62Service
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.services.GafisException
import org.apache.tapestry5.ioc.annotations.{EagerLoad, PostInjection}
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import scalikejdbc._

/**
 * Created by songpeng on 15/12/1.
 */
@EagerLoad
class Sync62ServiceImpl(facade:V62Facade, v62Config:HallV62Config, apiConfig: HallApiConfig)
  extends Sync62Service
  with Sync62CaseService
  with Sync62LPCardService
  with Sync62TPCardService{
  @Inject
  var sync62TPCardService: Sync62TPCardService = _
  @Inject
  var sync62CaseService: Sync62CaseService = _
  @Inject
  var sync62LPCardService: Sync62LPCardService = _
  @Inject
  var dictService: DictService = _

  val UPLOAD_FLAG_TPCARD = "1"
  val UPLOAD_FLAG_CASE = "2"
  val UPLOAD_FLAG_LPCARD = "3"
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    periodicExecutor.addJob(new CronSchedule(apiConfig.sync62Cron), "sync-70to62", new Runnable {
      override def run(): Unit = {
        findSyncQueue(SyncQueue.autoSession).foreach(doWork)
      }
    })
  }

  def findSyncQueue(implicit session: DBSession): Option[SyncQueue] ={
    val sq = SyncQueue.sq
    withSQL{
      selectFrom(SyncQueue as sq).where.eq(sq.uploadStatus, "0")
    }.map(SyncQueue(sq.resultName)).single.apply()
  }

  /**
   * 处理上报队列任务
   * @param syncQueue 上报队列
   * @param session
   * @return
   */
  override def doWork(syncQueue: SyncQueue)(implicit session: DBSession): Unit = {
    val uploadFlag = syncQueue.uploadFlag.get

    try {
      uploadFlag match {
        case UPLOAD_FLAG_TPCARD =>
          syncTPCard(facade, v62Config, syncQueue)
        case UPLOAD_FLAG_CASE =>
          syncCase(facade, v62Config, syncQueue)
        case UPLOAD_FLAG_LPCARD =>
          syncLPCard(facade, v62Config, syncQueue)
        case other =>
          //TODO 定义异常
          println(uploadFlag)
      }
    }
    catch {
      case e: GafisException =>
      case other =>
        //TODO 其他异常处理
    }
    updateSyncQueueFlagFinish(syncQueue)
  }

  /**
   * 更新队列任务状态
   * @param syncQueue
   */
  private def updateSyncQueueFlagFinish(syncQueue: SyncQueue): Unit ={

  }

}
