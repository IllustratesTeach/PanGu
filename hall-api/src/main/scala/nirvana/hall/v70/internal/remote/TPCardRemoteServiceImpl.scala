package nirvana.hall.v70.internal.remote

import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.protocol.api.TPCardProto.{TPCardGetRequest, TPCardGetResponse}
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v70.services.remote.TPCardRemoteService

/**
 * Created by songpeng on 16/3/4.
 */
class TPCardRemoteServiceImpl(rpcHttpClient: RpcHttpClient) extends TPCardRemoteService{
  /**
   * 获取捺印卡片
   * @return
   */
  override def getTPCard(personId: String, ip: String, port: String): TPCard= {
    val request = TPCardGetRequest.newBuilder().setCardId(personId).build()
    val baseResponse = rpcHttpClient.call("http://"+ip+":"+port, TPCardGetRequest.cmd, request)
    if(baseResponse.getStatus == CommandStatus.OK){
      baseResponse.getExtension(TPCardGetResponse.cmd).getCard
    }else{
      throw new RuntimeException(baseResponse.getMsg)
    }
  }
}
