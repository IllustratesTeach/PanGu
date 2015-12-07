package nirvana.hall.api.services.sync

import nirvana.hall.api.entities.SyncQueue
import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc.DBSession

/**
 * Created by songpeng on 15/12/7.
 */
trait Sync62LPCardService {
  /**
   * 同步现场卡片信息到62
   * @param syncQueue
   * @param session
   * @return
   */
  def syncLPCard(syncQueue: SyncQueue)(implicit session: DBSession = AutoSpringDataSourceSession.apply())
}
