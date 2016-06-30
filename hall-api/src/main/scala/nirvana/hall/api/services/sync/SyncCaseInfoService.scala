package nirvana.hall.api.services.sync

import nirvana.hall.api.config.DBConfig
import nirvana.hall.protocol.api.SyncDataProto.SyncCaseResponse

/**
  * Created by songpeng on 16/6/18.
  */
trait SyncCaseInfoService {
  /**
   * 同步CaseInfo数据
   * @param timestamp
   * @param size
   * @return
   */
  def syncCaseInfo(responseBuilder: SyncCaseResponse.Builder, timestamp: Long, size: Int, dBConfig: DBConfig = null)
}
