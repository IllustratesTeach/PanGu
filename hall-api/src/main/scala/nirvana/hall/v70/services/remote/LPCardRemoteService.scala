package nirvana.hall.v70.services.remote

import nirvana.hall.protocol.api.FPTProto.LPCard

/**
 * Created by songpeng on 16/3/7.
 */
trait LPCardRemoteService {

  /**
   * 获取现场卡片信息
   * @param cardId
   * @param ip
   * @param port
   * @return
   */
  def getLPCard(cardId: String, ip: String, port: String): LPCard
}
