package nirvana.hall.api.services.sync

import nirvana.hall.protocol.api.SyncDataProto.SyncLPCardResponse

/**
  * Created by songpeng on 16/6/18.
  */
trait SyncLPCardService {
  /**
   * 同步LPCard数据
   * @param timestamp
   * @param size
   * @return
   */
  def syncLPCard(responseBuilder: SyncLPCardResponse.Builder, timestamp: Long, size: Int, dbId: Option[String] = None)

}
