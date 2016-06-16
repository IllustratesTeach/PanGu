package nirvana.hall.api.internal.remote

import monad.rpc.protocol.CommandProto.CommandStatus
import monad.support.services.LoggerSupport
import nirvana.hall.api.services.remote.LPCardRemoteService
import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.protocol.api.LPCardProto.{LPCardAddRequest, LPCardGetRequest, LPCardGetResponse}
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
  override def getLPCard(cardId: String, url: String): Option[LPCard] = {
    info("remote get lpcard [cardId:{},url:{}]", cardId, url)
    val request = LPCardGetRequest.newBuilder().setCardId(cardId)
    val response = rpcHttpClient.call(url, LPCardGetRequest.cmd, request.build())
    if(response.getStatus == CommandStatus.OK){
      Option(response.getExtension(LPCardGetResponse.cmd).getCard)
    }else{
      error("remote get lpcard message:{}", response.getMsg)
      None
    }
  }

  /**
   * 添加现场卡
   * @param lPCard
   * @param url
   * @return
   */
  override def addLPCard(lPCard: LPCard, url: String): Boolean = {
    info("remote add lpcard [cardId:{},url:{}]", lPCard.getStrCardID, url)
    val request = LPCardAddRequest.newBuilder().setCard(lPCard)
    val response = rpcHttpClient.call(url, LPCardAddRequest.cmd, request.build())
    if(response.getStatus == CommandStatus.OK){
      true
    }else{
      error("remote add lpcard message:{}", response.getMsg)
      false
    }
  }
}
