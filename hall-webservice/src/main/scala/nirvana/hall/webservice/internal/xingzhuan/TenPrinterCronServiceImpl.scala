package nirvana.hall.webservice.internal.xingzhuan



import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.TenPrinterExportService
import nirvana.hall.webservice.services.xingzhuan.TenPrinterCronService
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
/**
  * Created by yuchen on 2017/5/10.
  */
class TenPrinterCronServiceImpl(hallWebserviceConfig: HallWebserviceConfig
                                ,tenPrinterExportService:TenPrinterExportService) extends TenPrinterCronService with LoggerSupport{

  /**
    * 定时器，获取比对任务
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallWebserviceConfig.union4pfmip.cron != null){
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.union4pfmip.cron), "sync-cron", new Runnable {
        override def run(): Unit = {
          info("sync-cron-export tenPrinter started")
          try{
            tenPrinterExportService.exportTenPrinterFPT
          }catch{
            case e:Exception =>
              error(ExceptionUtil.getStackTraceInfo(e))
          }
          info("sync-cron-tenPrinter end")
        }
      })
    }
  }
}
