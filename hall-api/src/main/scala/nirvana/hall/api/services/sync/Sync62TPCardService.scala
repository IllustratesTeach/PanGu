package nirvana.hall.api.services.sync

import nirvana.hall.api.entities.SyncQueue
import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc.DBSession

/**
 * Created by songpeng on 15/12/7.
 */
trait Sync62TPCardService {
  /**
   * 同步捺印卡到6.2
   * @param syncQueue
   * @param session
   * @return
   */
  def syncTPCard(syncQueue: SyncQueue)(implicit session: DBSession = AutoSpringDataSourceSession.apply())
}
