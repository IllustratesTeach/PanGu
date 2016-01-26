package nirvana.hall.api.services

import nirvana.hall.protocol.v62.tp.TPCardProto._

/**
 * Created by songpeng on 16/1/26.
 */
trait TPCardService {

  /**
   * 新增捺印卡片
   * @param tPCardAddRequest
   * @return
   */
  def addTPCard(tPCardAddRequest: TPCardAddRequest): TPCardAddResponse

  /**
   * 删除捺印卡片
   * @param tPCardDelRequest
   * @return
   */
  def delTPCard(tPCardDelRequest: TPCardDelRequest): TPCardDelResponse

  /**
   * 更新捺印卡片
   * @param tPCardUpdateRequest
   * @return
   */
  def updateTPCard(tPCardUpdateRequest: TPCardUpdateRequest): TPCardUpdateResponse

  /**
   * 获取捺印卡片
   * @param tPCardGetRequest
   * @return
   */
  def getTPCard(tPCardGetRequest: TPCardGetRequest): TPCardGetResponse
}
