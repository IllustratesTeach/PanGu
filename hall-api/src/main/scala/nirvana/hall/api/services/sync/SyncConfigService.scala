package nirvana.hall.api.services.sync

import nirvana.hall.v70.jpa.SyncConfig
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/6/20.
 */
trait SyncConfigService {

  /**
   * 获取同步配置信息
   * @return
   */
  def getSyncConfig(): SyncConfig

  /**
   * 更新同步配置
   * @param syncConfig
   */
  @Transactional
  def updateSyncConfig(syncConfig: SyncConfig)
}
