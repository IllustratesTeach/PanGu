package nirvana.hall.api.services.sync

/**
  * Created by mengxin 2019/3/20
  */
trait CronSyncQueryOrQualityService {
  /**
    * 定时任务调用方法
    */
  def doWork()
}
