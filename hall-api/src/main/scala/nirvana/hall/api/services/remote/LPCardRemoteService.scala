package nirvana.hall.api.services.remote

import nirvana.hall.protocol.api.FPTProto.LPCard

/**
 * Created by songpeng on 16/3/7.
 */
trait LPCardRemoteService {

  /**
   * 获取现场卡片信息
   * @param cardId
   * @param url
   * @return
   */
  def getLPCard(cardId: String, url: String): Option[LPCard]

  /**
   * 添加现场卡
   * @param lPCard
   * @param url
   * @return
   */
  def addLPCard(lPCard: LPCard, url: String): Boolean
}
