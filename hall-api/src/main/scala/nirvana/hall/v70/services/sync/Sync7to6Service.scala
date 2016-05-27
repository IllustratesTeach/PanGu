package nirvana.hall.v70.services.sync

import nirvana.hall.v70.jpa.SyncQueue
import org.springframework.transaction.annotation.Transactional

/**
 * 70上报62接口服务
 * Created by songpeng on 15/12/1.
 */
trait Sync7to6Service {

  /**
   * 处理上报队列任务
   * @param syncQueue 上报队列
   * @return
   */
  @Transactional
  def doTaskOfSyncQueue(syncQueue: SyncQueue)

  /**
   * 读取上报任务队列, 处理上报任务
   */
  @Transactional
  def doWork
}
