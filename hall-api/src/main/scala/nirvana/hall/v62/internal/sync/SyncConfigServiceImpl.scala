package nirvana.hall.v62.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.sync.SyncConfigService
import nirvana.hall.v70.jpa.GafisSyncConfig

/**
 * Created by songpeng on 16/8/5.
 */
class SyncConfigServiceImpl(implicit val dataSource: DataSource) extends SyncConfigService{
  /**
   * 获取同步配置信息
   * @return
   */
  override def getSyncConfigList(): Seq[GafisSyncConfig] = {
    Nil
  }

  /**
   * 更新同步配置
   * @param syncConfig
   */
  override def updateSyncConfig(syncConfig: GafisSyncConfig): Unit = {

  }
}
