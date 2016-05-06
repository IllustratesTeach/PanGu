package nirvana.hall.matcher.service

import nirvana.protocol.SyncDataProto.{SyncDataRequest, SyncDataResponse}

/**
 * Created by songpeng on 16/3/20.
 */
trait SyncDataService {

  /**
   * 同步数据
   * @param syncDataRequest
   * @return
   */
  def syncData( syncDataRequest:SyncDataRequest):SyncDataResponse
}
