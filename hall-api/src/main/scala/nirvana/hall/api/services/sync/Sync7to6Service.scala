package nirvana.hall.api.services.sync

import nirvana.hall.api.jpa.SyncQueue
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
}
