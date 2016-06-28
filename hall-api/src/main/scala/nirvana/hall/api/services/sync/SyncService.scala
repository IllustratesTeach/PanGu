package nirvana.hall.api.services.sync

/**
 * Created by songpeng on 16/6/12.
 */
trait SyncService {

  /**
   * 定时任务调用方法
   */
  def doWork()

}
