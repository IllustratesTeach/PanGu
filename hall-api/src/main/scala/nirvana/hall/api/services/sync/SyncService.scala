package nirvana.hall.api.services.sync

/**
 * Created by songpeng on 16/6/12.
 */
trait SyncService {

  /**
   * 定时任务调用方法
   */
  def doWork()

  /**
   * 同步捺印数据
   */
  def syncTPCard()

  /**
   * 同步现场数据
   */
  def syncLPCard()

  /**
   * 同步案件数据
   */
  def syncCaseInfo()



}
