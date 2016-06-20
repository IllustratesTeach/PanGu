package nirvana.hall.v70.internal.sync

import nirvana.hall.api.services.sync.SyncConfigService
import nirvana.hall.v70.jpa.SyncConfig
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/6/20.
 */
class SyncConfigServiceImpl extends SyncConfigService{
  /**
   * 获取同步配置信息
   * @return
   */
  override def getSyncConfig(): SyncConfig = {
    SyncConfig.find("1")
  }

  /**
   * 更新同步配置
   * @param syncConfig
   */
  @Transactional
  override def updateSyncConfig(syncConfig: SyncConfig): Unit = {
    val config = getSyncConfig()
    config.tpcardTimestamp = syncConfig.tpcardTimestamp
    config.caseTimestamp = syncConfig.caseTimestamp
    config.lpcardTimestamp = syncConfig.lpcardTimestamp
    config.save()
  }
}
