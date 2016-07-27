package nirvana.hall.api.services.sync

import nirvana.hall.protocol.api.SyncDataProto.SyncLPPalmResponse

/**
 * 同步现场掌纹
 */
trait SyncLPPalmService {
  /**
   * 同步LPCard数据
   * @param timestamp
   * @param size
   * @return
   */
  def syncLPCard(responseBuilder: SyncLPPalmResponse.Builder, timestamp: Long, size: Int, dbId: Option[String] = None)

}
