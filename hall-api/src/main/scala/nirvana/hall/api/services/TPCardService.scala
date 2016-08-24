package nirvana.hall.api.services

import nirvana.hall.protocol.api.FPTProto.TPCard
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
trait TPCardService {

  /**
   * 新增捺印卡片
   * @param tPCard
   * @return
   */
  @Transactional
  def addTPCard(tPCard: TPCard, dbId: Option[String] = None): Unit

  /**
   * 删除捺印卡片
   * @param cardId
   * @return
   */
  @Transactional
  def delTPCard(cardId: String, dbId: Option[String] = None): Unit

  /**
   * 更新捺印卡片
   * @param tpCard
   * @return
   */
  @Transactional
  def updateTPCard(tpCard: TPCard, dbId: Option[String] = None): Unit

  /**
   * 验证卡号是否已存在
   * @param cardId
   * @return
   */
  def isExist(cardId: String, dbId: Option[String] = None): Boolean

  /**
   * 获取捺印卡信息
   * @param cardId
   * @param dbid
   * @return
   */
  def getTPCard(cardId: String, dbid: Option[String] = None): TPCard

}
