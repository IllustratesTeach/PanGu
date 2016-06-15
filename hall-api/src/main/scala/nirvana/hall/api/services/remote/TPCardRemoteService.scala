package nirvana.hall.api.services.remote

import nirvana.hall.protocol.api.FPTProto.TPCard

/**
 * 发送远程捺印卡信息
 * Created by songpeng on 16/3/4.
 */
trait TPCardRemoteService {

  /**
   * 获取捺印卡片
   * @return
   */
  def getTPCard(personId: String, url: String): Option[TPCard]

  /**
   * 添加捺印卡片
   * @param tpCard
   * @param url
   */
  def addTPCard(tpCard: TPCard, url: String): Boolean
}
