package nirvana.hall.api.internal.remote

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.remote.LPCardRemoteService
import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.protocol.api.LPCardProto._
import nirvana.hall.support.services.RpcHttpClient

/**
 * Created by songpeng on 16/3/7.
 */
class LPCardRemoteServiceImpl(rpcHttpClient: RpcHttpClient) extends LPCardRemoteService with LoggerSupport{
  /**
   * 获取现场卡片信息
   * @param cardId
   * @param url
   * @return
   */
  override def getLPCard(cardId: String, url: String, headerMap: Map[String, String]): Option[LPCard] = {
    info("remote get lpcard [cardId:{},url:{}]", cardId, url)
    val request = LPCardGetRequest.newBuilder().setCardId(cardId)
    val response = rpcHttpClient.call(url, LPCardGetRequest.cmd, request.build(), headerMap)
    Option(response.getExtension(LPCardGetResponse.cmd).getCard)
  }

  /**
   * 添加现场卡
   * @param lPCard
   * @param url
   * @return
   */
  override def addLPCard(lPCard: LPCard, url: String) = {
    info("remote add lpcard [cardId:{},url:{}]", lPCard.getStrCardID, url)
    val request = LPCardAddRequest.newBuilder().setCard(lPCard)
    rpcHttpClient.call(url, LPCardAddRequest.cmd, request.build())
  }

  /**
   * 更新现场卡片
   * @param lPCard
   * @param url
   */
  override def updateLPCard(lPCard: LPCard, url: String): Unit = {
    info("remote update lpcard [cardId:{},url:{}]", lPCard.getStrCardID, url)
    val request = LPCardUpdateRequest.newBuilder().setCard(lPCard)
    rpcHttpClient.call(url, LPCardUpdateRequest.cmd, request.build())
  }

  /**
   * 删除现场卡信息
   * @param cardId
   * @param url
   */
  override def deleteLPCard(cardId: String, url: String): Unit = {
    info("remote add lpcard [cardId:{},url:{}]", cardId, url)
    val request = LPCardDelRequest.newBuilder().setCardId(cardId)
    rpcHttpClient.call(url, LPCardDelRequest.cmd, request.build())
  }
}
