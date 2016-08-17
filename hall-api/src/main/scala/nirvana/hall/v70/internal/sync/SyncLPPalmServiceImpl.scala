package nirvana.hall.v70.internal.sync

import nirvana.hall.api.services.sync.SyncLPPalmService
import nirvana.hall.protocol.api.SyncDataProto.SyncLPPalmResponse

/**
 * Created by songpeng on 16/8/8.
 */
class SyncLPPalmServiceImpl extends SyncLPPalmService{
  /**
   * 同步LPCard数据
   * @param timestamp
   * @param size
   * @return
   */
  override def syncLPCard(responseBuilder: SyncLPPalmResponse.Builder, timestamp: Long, size: Int, dbId: Option[String]): Unit = {
    throw new UnsupportedOperationException
  }
}
