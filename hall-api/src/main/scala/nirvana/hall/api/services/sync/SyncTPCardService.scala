package nirvana.hall.api.services.sync

import nirvana.hall.protocol.api.SyncDataProto.SyncTPCardResponse

/**
 * Created by songpeng on 16/6/18.
 */
trait SyncTPCardService {

  /**
   * 同步TPCard数据
   * @param responseBuilder
   * @param timestamp
   * @param size
   * @return
   */
  def syncTPCard(responseBuilder: SyncTPCardResponse.Builder, timestamp: Long, size: Int)
}
