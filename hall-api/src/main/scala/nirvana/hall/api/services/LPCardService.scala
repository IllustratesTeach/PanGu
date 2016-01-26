package nirvana.hall.api.services

import nirvana.hall.protocol.v62.lp.LPCardProto._

/**
 * Created by songpeng on 16/1/26.
 */
trait LPCardService {

  /**
   * 新增现场卡片
   * @param lPCardAddRequest
   * @return
   */
  def addLPCard(lPCardAddRequest: LPCardAddRequest): LPCardAddResponse

  /**
   * 删除现场卡片
   * @param lPCardDelRequest
   * @return
   */
  def delLPCard(lPCardDelRequest: LPCardDelRequest): LPCardDelResponse

  /**
   * 更新现场卡片
   * @param lPCardUpdateRequest
   * @return
   */
  def updateLPCard(lPCardUpdateRequest: LPCardUpdateRequest): LPCardUpdateResponse

  /**
   * 获取现场卡片
   * @param lPCardGetRequest
   * @return
   */
  def getLPCard(lPCardGetRequest: LPCardGetRequest): LPCardGetResponse
}
