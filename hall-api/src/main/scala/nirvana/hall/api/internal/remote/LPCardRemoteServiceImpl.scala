package nirvana.hall.api.internal.remote

import monad.rpc.protocol.CommandProto.CommandStatus
import monad.support.services.LoggerSupport
import nirvana.hall.api.services.remote.LPCardRemoteService
import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.protocol.api.LPCardProto.{LPCardGetRequest, LPCardGetResponse}
import nirvana.hall.support.services.RpcHttpClient

/**
 * Created by songpeng on 16/3/7.
 */
class LPCardRemoteServiceImpl(rpcHttpClient: RpcHttpClient) extends LPCardRemoteService with LoggerSupport{
  /**
   * 获取现场卡片信息
   * @param cardId
   * @param ip
   * @param port
   * @return
   */
  override def getLPCard(cardId: String, ip: String, port: String): Option[LPCard] = {
    info("remote get lpcard [cardId:{},ip:{},port:{}]", cardId, ip, port)
    val request = LPCardGetRequest.newBuilder().setCardId(cardId)
    val response = rpcHttpClient.call("http://"+ip+":"+port, LPCardGetRequest.cmd, request.build())
    if(response.getStatus == CommandStatus.OK){
      Option(response.getExtension(LPCardGetResponse.cmd).getCard)
    }else{
      error("remote get lpcard message:{}", response.getMsg)
      None
    }
  }
}
