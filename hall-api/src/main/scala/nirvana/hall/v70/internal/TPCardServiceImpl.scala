package nirvana.hall.v70.internal

import nirvana.hall.api.services.TPCardService
import nirvana.hall.protocol.v62.tp.TPCardProto._

/**
 * Created by songpeng on 16/1/26.
 */
class TPCardServiceImpl extends TPCardService{
  /**
   * 新增捺印卡片
   * @param tPCardAddRequest
   * @return
   */
  override def addTPCard(tPCardAddRequest: TPCardAddRequest): TPCardAddResponse = {
    throw new UnsupportedOperationException
  }

  /**
   * 删除捺印卡片
   * @param tPCardDelRequest
   * @return
   */
  override def delTPCard(tPCardDelRequest: TPCardDelRequest): TPCardDelResponse = {
    throw new UnsupportedOperationException
  }

  /**
   * 获取捺印卡片
   * @param tPCardGetRequest
   * @return
   */
  override def getTPCard(tPCardGetRequest: TPCardGetRequest): TPCardGetResponse = {
    throw new UnsupportedOperationException
  }

  /**
   * 更新捺印卡片
   * @param tPCardUpdateRequest
   * @return
   */
  override def updateTPCard(tPCardUpdateRequest: TPCardUpdateRequest): TPCardUpdateResponse = {
    throw new UnsupportedOperationException
  }
}
