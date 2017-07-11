package nirvana.hall.webservice.internal.xingzhuan


import javax.sql.DataSource
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.xingzhuan.{FetchFPTService, SyncSyCronService}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

/**
  * Created by Administrator on 2017/5/9.
  */
class SyncSyCronServiceImpl(implicit val database: DataSource,
                            fetchFPTService: FetchFPTService,
                            hallWebserviceConfig: HallWebserviceConfig) extends SyncSyCronService with LoggerSupport{

  /**
    * 定时器，同步数据
    *
    * @param periodicExecutor
    * @param syncSyCronService
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, syncSyCronService: SyncSyCronService): Unit = {
    if(hallWebserviceConfig.union4pfmip.cron!= null){
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.XingZhuanSetting.cron), "sync-cron", new Runnable {
        override def run(): Unit = {
          info("begin sync-cron assistCheck_query")
          try{
            syncSyCronService.doWork()
          }catch{
            case e:Exception =>
              error(ExceptionUtil.getStackTraceInfo(e))
          }
          info("end sync-cron assistCheck_query")
        }
      })
    }
  }

  /**
    * 定时任务调用方法
    */
  override def doWork(): Unit = {
    fetchFPTService.fetchFPT
  }


}
