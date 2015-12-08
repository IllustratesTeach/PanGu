package nirvana.hall.api.services.sync

import nirvana.hall.api.entities.SyncQueue
import nirvana.hall.api.services.AutoSpringDataSourceSession
import org.springframework.transaction.annotation.Transactional
import scalikejdbc.DBSession

/**
 * 70上报62接口服务
 * Created by songpeng on 15/12/1.
 */
trait Sync62Service {

  /**
   * 处理上报队列任务
   * @param syncQueue 上报队列
   * @param session
   * @return
   */
  @Transactional
  def doWork(syncQueue: SyncQueue)(implicit session: DBSession = AutoSpringDataSourceSession.apply())
}
