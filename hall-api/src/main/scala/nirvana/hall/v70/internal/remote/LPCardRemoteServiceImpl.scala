package nirvana.hall.v70.internal.remote

import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.protocol.api.LPCardProto.{LPCardGetRequest, LPCardGetResponse}
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v70.services.remote.LPCardRemoteService

/**
 * Created by songpeng on 16/3/7.
 */
class LPCardRemoteServiceImpl(rpcHttpClient: RpcHttpClient) extends LPCardRemoteService{
  /**
   * 获取现场卡片信息
   * @param cardId
   * @param ip
   * @param port
   * @return
   */
  override def getLPCard(cardId: String, ip: String, port: String): LPCard = {
    val request = LPCardGetRequest.newBuilder().setCardId(cardId)
    val response = rpcHttpClient.call("http://"+ip+":"+port, LPCardGetRequest.cmd, request.build())
    if(response.getStatus == CommandStatus.OK){
      response.getExtension(LPCardGetResponse.cmd).getCard
    }else{
      throw new RuntimeException(response.getMsg)
    }
  }
}
