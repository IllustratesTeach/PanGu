package nirvana.hall.v70.services.sync

import nirvana.hall.v70.jpa.SyncGafis6Config
import org.springframework.transaction.annotation.{Propagation, Transactional}

/**
 * Created by songpeng on 16/6/12.
 */
trait Sync6to7Service {

  /**
   * 定时任务调用方法
   */
  def doWork()

  /**
   * 同步捺印数据
   * @param syncGafis6Config
   */
  @Transactional(propagation=Propagation.REQUIRES_NEW)
  def syncTPCard(syncGafis6Config: SyncGafis6Config)

  /**
   * 同步现场数据
   * @param syncGafis6Config
   */
  @Transactional(propagation=Propagation.REQUIRES_NEW)
  def syncLPCard(syncGafis6Config: SyncGafis6Config)

  /**
   * 同步案件数据
   * @param syncGafis6Config
   */
  @Transactional(propagation=Propagation.REQUIRES_NEW)
  def syncCaseInfo(syncGafis6Config: SyncGafis6Config)
}
