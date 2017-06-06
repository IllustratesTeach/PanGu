package nirvana.hall.api.internal.sync

import monad.support.services.LoggerSupport
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.services.sync.SyncCronFilterFPTService
import nirvana.hall.api.services.FPTFilterService
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

/**
  * Created by win-20161010 on 2017/5/13.
  * 定时调用Filter过滤已生成的FPT文件，使其符合刑专标准
  */
class SyncCronFilterFPTServiceImpl(apiConfig: HallApiConfig
                                   ,fPTFilterService: FPTFilterService
                                  )
  extends SyncCronFilterFPTService with LoggerSupport{

  val BATCH_SIZE = 10
  /**
    * 定时器，同步数据
    * @param periodicExecutor
    * @param syncCronFilterFPTService
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, syncCronFilterFPTService: SyncCronFilterFPTService): Unit = {
    if(apiConfig.sync.syncCron != null){
      periodicExecutor.addJob(new CronSchedule(apiConfig.sync.syncCron), "sync-cron", new Runnable {
        override def run(): Unit = {
          info("begin Filter sync-cron")
          try{
            syncCronFilterFPTService.doWork
          }catch{
            case e:Exception =>
              error("filter cron error:" + ExceptionUtil.getStackTraceInfo(e))
          }
          info("end Filter sync-cron")
        }
      })
    }
  }


  override def doWork(): Unit = {
    fPTFilterService.handler(BATCH_SIZE)
  }



}
