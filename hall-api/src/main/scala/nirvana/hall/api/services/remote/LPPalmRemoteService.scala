package nirvana.hall.api.services.remote

import nirvana.hall.protocol.api.FPTProto.LPCard

/**
 * Created by songpeng on 16/3/7.
 */
trait LPPalmRemoteService {

  /**
   * 获取现场卡片信息
   * @param cardId
   * @param url
   * @return
   */
  def getLPPalm(cardId: String, url: String, dbId: String = "", headerMap: Map[String, String] = Map()): Option[LPCard]

  /**
   * 添加现场卡
   * @param lPCard
   * @param url
   * @return
   */
  def addLPPalm(lPCard: LPCard, url: String, dbId: String = "", headerMap: Map[String, String] = Map())

  /**
   * 更新现场卡片
   * @param lPCard
   * @param url
   */
  def updateLPPalm(lPCard: LPCard, url: String, dbId: String = "", headerMap: Map[String, String] = Map())

  /**
   * 删除现场卡信息
   * @param cardId
   * @param url
   */
  def deleteLPPalm(cardId: String, url: String, dbId: String = "", headerMap: Map[String, String] = Map())
}
