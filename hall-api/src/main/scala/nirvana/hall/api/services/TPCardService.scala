package nirvana.hall.api.services

import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.protocol.api.TPCardProto._
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
trait TPCardService {

  /**
   * 新增捺印卡片
   * @param tPCardAddRequest
   * @return
   */
  @Transactional
  def addTPCard(tPCardAddRequest: TPCardAddRequest): TPCardAddResponse

  /**
   * 删除捺印卡片
   * @param tPCardDelRequest
   * @return
   */
  @Transactional
  def delTPCard(tPCardDelRequest: TPCardDelRequest): TPCardDelResponse

  /**
   * 更新捺印卡片
   * @param tPCardUpdateRequest
   * @return
   */
  @Transactional
  def updateTPCard(tPCardUpdateRequest: TPCardUpdateRequest): TPCardUpdateResponse

  /**
   * 获取捺印卡片
   * @param tPCardGetRequest
   * @return
   */
  def getTPCard(tPCardGetRequest: TPCardGetRequest): TPCardGetResponse

  /**
   * 验证卡号是否已存在
   * @param cardId
   * @return
   */
  def isExist(cardId: String): Boolean

  /**
   * 获取捺印卡信息
   * @param cardId
   * @return
   */
  def getTPCard(cardId: String): TPCard
}
