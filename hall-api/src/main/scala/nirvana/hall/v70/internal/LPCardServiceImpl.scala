package nirvana.hall.v70.internal

import nirvana.hall.api.services.LPCardService
import nirvana.hall.protocol.v62.lp.LPCardProto._

/**
 * Created by songpeng on 16/1/26.
 */
class LPCardServiceImpl extends LPCardService{
  /**
   * 新增现场卡片
   * @param lPCardAddRequest
   * @return
   */
  override def addLPCard(lPCardAddRequest: LPCardAddRequest): LPCardAddResponse = {
    throw new UnsupportedOperationException
  }

  /**
   * 获取现场卡片
   * @param lPCardGetRequest
   * @return
   */
  override def getLPCard(lPCardGetRequest: LPCardGetRequest): LPCardGetResponse = {
    throw new UnsupportedOperationException
  }

  /**
   * 更新现场卡片
   * @param lPCardUpdateRequest
   * @return
   */
  override def updateLPCard(lPCardUpdateRequest: LPCardUpdateRequest): LPCardUpdateResponse = {
    throw new UnsupportedOperationException
  }

  /**
   * 删除现场卡片
   * @param lPCardDelRequest
   * @return
   */
  override def delLPCard(lPCardDelRequest: LPCardDelRequest): LPCardDelResponse = {
    throw new UnsupportedOperationException
  }
}
