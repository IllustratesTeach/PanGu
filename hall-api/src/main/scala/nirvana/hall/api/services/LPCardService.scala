package nirvana.hall.api.services

import nirvana.hall.protocol.api.LPCardProto._
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
trait LPCardService {

  /**
   * 新增现场卡片
   * @param lPCardAddRequest
   * @return
   */
  @Transactional
  def addLPCard(lPCardAddRequest: LPCardAddRequest): LPCardAddResponse

  /**
   * 删除现场卡片
   * @param lPCardDelRequest
   * @return
   */
  @Transactional
  def delLPCard(lPCardDelRequest: LPCardDelRequest): LPCardDelResponse

  /**
   * 更新现场卡片
   * @param lPCardUpdateRequest
   * @return
   */
  @Transactional
  def updateLPCard(lPCardUpdateRequest: LPCardUpdateRequest): LPCardUpdateResponse

  /**
   * 获取现场卡片
   * @param lPCardGetRequest
   * @return
   */
  def getLPCard(lPCardGetRequest: LPCardGetRequest): LPCardGetResponse

  /**
   * 验证现场卡片是否存在
   * @param cardId
   * @return
   */
  def isExist(cardId: String): Boolean
}
