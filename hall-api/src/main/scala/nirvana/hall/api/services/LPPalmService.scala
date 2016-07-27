package nirvana.hall.api.services

import nirvana.hall.protocol.api.FPTProto.LPCard
import org.springframework.transaction.annotation.Transactional

/**
 * 现场掌纹service
 */
trait LPPalmService {

  /**
   * 新增现场卡片
   * @param lpCard
   * @return
   */
  @Transactional
  def addLPCard(lpCard: LPCard, dbId: Option[String] = None): Unit

  /**
   * 删除现场卡片
   * @param cardId
   * @return
   */
  @Transactional
  def delLPCard(cardId: String, dbId: Option[String] = None): Unit

  /**
   * 更新现场卡片
   * @param lpCard
   * @return
   */
  @Transactional
  def updateLPCard(lpCard: LPCard, dbId: Option[String] = None): Unit

  /**
   * 获取现场卡片
   * @param cardId
   * @return
   */
  def getLPCard(cardId: String, dbId: Option[String] = None): LPCard

  /**
   * 验证现场卡片是否存在
   * @param cardId
   * @return
   */
  def isExist(cardId: String, dbId: Option[String] = None): Boolean
}
