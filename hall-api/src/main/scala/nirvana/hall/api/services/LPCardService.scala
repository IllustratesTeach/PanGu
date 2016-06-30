package nirvana.hall.api.services

import nirvana.hall.api.config.DBConfig
import nirvana.hall.protocol.api.FPTProto.LPCard
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
trait LPCardService {

  /**
   * 新增现场卡片
   * @param lpCard
   * @return
   */
  @Transactional
  def addLPCard(lpCard: LPCard, dBConfig: DBConfig = null): Unit

  /**
   * 删除现场卡片
   * @param cardId
   * @return
   */
  @Transactional
  def delLPCard(cardId: String): Unit

  /**
   * 更新现场卡片
   * @param lpCard
   * @return
   */
  @Transactional
  def updateLPCard(lpCard: LPCard, dBConfig: DBConfig = null): Unit

  /**
   * 获取现场卡片
   * @param cardId
   * @return
   */
  def getLPCard(cardId: String, dBConfig: DBConfig = null): LPCard

  /**
   * 验证现场卡片是否存在
   * @param cardId
   * @return
   */
  def isExist(cardId: String, dBConfig: DBConfig = null): Boolean
}
