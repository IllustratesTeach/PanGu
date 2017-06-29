package nirvana.hall.api.internal.remote

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.remote.LPPalmRemoteService
import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.protocol.api.LPPalmProto._
import nirvana.hall.support.services.RpcHttpClient

/**
 * Created by songpeng on 16/3/7.
 */
class LPPalmRemoteServiceImpl(rpcHttpClient: RpcHttpClient) extends LPPalmRemoteService with LoggerSupport{
  /**
   * 获取现场卡片信息
   * @param cardId
   * @param url
   * @return
   */
  override def getLPPalm(cardId: String, url: String, dbId: String, headerMap: Map[String, String]): Option[LPCard] = {
    info("remote get lppalm [cardId:{},url:{}]", cardId, url)
    val request = LPPalmGetRequest.newBuilder().setCardId(cardId)
    request.setDbid(dbId)
    val response = rpcHttpClient.call(url, LPPalmGetRequest.cmd, request.build(),headerMap)
    val card = response.getExtension(LPPalmGetResponse.cmd).getCard

    if(card.getStrCardID.nonEmpty){
      Option(card)
    }else{
      None
    }
  }

  /**
   * 添加现场卡
   * @param lPCard
   * @param url
   * @return
   */
  override def addLPPalm(lPCard: LPCard, url: String, dbId: String, headerMap: Map[String, String]) = {
    info("remote add lppalm [cardId:{},url:{}]", lPCard.getStrCardID, url)
    val request = LPPalmAddRequest.newBuilder().setCard(lPCard)
    request.setDbid(dbId)
    rpcHttpClient.call(url, LPPalmAddRequest.cmd, request.build())
  }

  /**
   * 更新现场卡片
   * @param lPCard
   * @param url
   */
  override def updateLPPalm(lPCard: LPCard, url: String, dbId: String, headerMap: Map[String, String]): Unit = {
    info("remote update lppalm [cardId:{},url:{}]", lPCard.getStrCardID, url)
    val request = LPPalmUpdateRequest.newBuilder().setCard(lPCard)
    request.setDbid(dbId)
    rpcHttpClient.call(url, LPPalmUpdateRequest.cmd, request.build())
  }

  /**
   * 删除现场卡信息
   * @param cardId
   * @param url
   */
  override def deleteLPPalm(cardId: String, url: String, dbId: String, headerMap: Map[String, String]): Unit = {
    info("remote add lppalm [cardId:{},url:{}]", cardId, url)
    val request = LPPalmDelRequest.newBuilder().setCardId(cardId)
    request.setDbid(dbId)
    rpcHttpClient.call(url, LPPalmDelRequest.cmd, request.build())
  }
}
