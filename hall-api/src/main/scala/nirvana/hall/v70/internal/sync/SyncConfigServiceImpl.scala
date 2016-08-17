package nirvana.hall.v70.internal.sync

import nirvana.hall.api.services.sync.SyncConfigService
import nirvana.hall.v70.jpa.GafisSyncConfig
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/6/20.
 */
class SyncConfigServiceImpl extends SyncConfigService{
  /**
   * 获取同步配置信息
   * @return
   */
  override def getSyncConfigList(): Seq[GafisSyncConfig] = {
    GafisSyncConfig.find_by_deletag("1").toSeq
  }

  /**
   * 更新同步配置
   * @param syncConfig
   */
  @Transactional
  override def updateSyncConfig(syncConfig: GafisSyncConfig): Unit = {
    val config = GafisSyncConfig.find(syncConfig.pkId)
    config.timestamp = syncConfig.timestamp
    config.save()
  }
}
