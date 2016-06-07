package nirvana.hall.v70.services.remote

import nirvana.hall.protocol.api.FPTProto.TPCard

/**
 * Created by songpeng on 16/3/4.
 */
trait TPCardRemoteService {

  /**
   * 获取捺印卡片
   * @return
   */
  def getTPCard(personId: String, ip: String, port: String): Option[TPCard]
}
