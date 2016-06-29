package nirvana.hall.api.services.sync

import nirvana.hall.v70.jpa.GafisSyncConfig
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/6/20.
 */
trait SyncConfigService {

  /**
   * 获取同步配置信息
   * @return
   */
  def getSyncConfigList(): Seq[GafisSyncConfig]

  /**
   * 更新同步配置
   * @param syncConfig
   */
  @Transactional
  def updateSyncConfig(syncConfig: GafisSyncConfig)
}
