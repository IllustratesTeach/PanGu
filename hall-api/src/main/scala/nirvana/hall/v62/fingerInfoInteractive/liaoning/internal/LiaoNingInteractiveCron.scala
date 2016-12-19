package nirvana.hall.v62.fingerInfoInteractive.liaoning.internal



import nirvana.hall.v62.fingerInfoInteractive.liaoning.services.{DataService, FingerInfoService}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

/**
  * Created by yuchen on 2016/12/16.
  */
class LiaoNingInteractiveCron(dataService:DataService
                              ,fingerInfoService:FingerInfoService) {

  /**
    * 定时器
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    periodicExecutor.addJob(new CronSchedule("0/10 * * * * ?"), "sync-cron", new Runnable {
      override def run(): Unit = {

        doWork
      }
    })
  }

  /**
    * 定时任务调用方法
    */
  def doWork(): Unit = {

  }

}
