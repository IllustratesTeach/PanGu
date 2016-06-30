package nirvana.hall.api.services

import nirvana.hall.api.config.DBConfig
import nirvana.hall.protocol.api.FPTProto.TPCard
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
trait TPCardService {

  /**
   * 新增捺印卡片
   * @param tPCard
   * @param dBConfig
   * @return
   */
  @Transactional
  def addTPCard(tPCard: TPCard, dBConfig: DBConfig = null): Unit

  /**
   * 删除捺印卡片
   * @param cardId
   * @return
   */
  @Transactional
  def delTPCard(cardId: String, dBConfig: DBConfig = null): Unit

  /**
   * 更新捺印卡片
   * @param tpCard
   * @return
   */
  @Transactional
  def updateTPCard(tpCard: TPCard, dBConfig: DBConfig = null): Unit

  /**
   * 验证卡号是否已存在
   * @param cardId
   * @return
   */
  def isExist(cardId: String, dBConfig: DBConfig = null): Boolean

  /**
   * 获取捺印卡信息
   * @param cardId
   * @param dBConfig
   * @return
   */
  def getTPCard(cardId: String, dBConfig: DBConfig = null): TPCard
}
