package nirvana.hall.api.internal.sync

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.services.sync.SyncCronService
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

/**
  * @author <a href="yuchen_1997_200486@126.com">yuchen</a>.
  * @since 2019/3/4
  */
class CronSyncResult(apiConfig: HallApiConfig) extends SyncCronService with LoggerSupport{


  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, syncCronService: SyncCronService): Unit = {
    if(apiConfig.sync.syncCron != null){
      periodicExecutor.addJob(new CronScheduleWithStartModel(null, StartAtDelay), "sync-cron", new Runnable {
        override def run(): Unit = {
          info("begin sync-cron")
          try{
            syncCronService.doWork
          }catch{
            case e:Exception =>
              error("CronSyncResult error:{}",e.getMessage,e)
          }
          info("end sync-cron")
        }
      })
    }
  }


  /**
    * 定时任务调用方法
    */
  override def doWork(): Unit = ???
}
