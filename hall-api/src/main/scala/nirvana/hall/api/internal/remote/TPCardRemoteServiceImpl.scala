package nirvana.hall.api.internal.remote

import monad.rpc.protocol.CommandProto.CommandStatus
import monad.support.services.LoggerSupport
import nirvana.hall.api.services.remote.TPCardRemoteService
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.protocol.api.TPCardProto.{TPCardAddRequest, TPCardGetRequest, TPCardGetResponse}
import nirvana.hall.support.services.RpcHttpClient

/**
 * Created by songpeng on 16/3/4.
 */
class TPCardRemoteServiceImpl(rpcHttpClient: RpcHttpClient) extends TPCardRemoteService with LoggerSupport{
  /**
   * 获取捺印卡片
   * @return
   */
  override def getTPCard(personId: String, url: String): Option[TPCard]= {
    info("remote get tpcard [personId:{},url:{}]", personId, url)
    val request = TPCardGetRequest.newBuilder().setCardId(personId).build()
    val baseResponse = rpcHttpClient.call(url, TPCardGetRequest.cmd, request)
    if(baseResponse.getStatus == CommandStatus.OK){
      Option(baseResponse.getExtension(TPCardGetResponse.cmd).getCard)
    }else{
      error("remote get tpcard message:{}", baseResponse.getMsg)
      None
    }
  }

  /**
   * 添加捺印卡片
   * @param tpCard
   * @param url
   */
  override def addTPCard(tpCard: TPCard, url: String): Boolean= {
    info("remote add tpcard [personId:{},url:{}]", tpCard.getStrCardID, url)
    val request = TPCardAddRequest.newBuilder().setCard(tpCard).build()
    val baseResponse = rpcHttpClient.call(url, TPCardAddRequest.cmd, request)
    if(baseResponse.getStatus == CommandStatus.OK){
      true
    }else{
      error("remote get tpcard message:{}", baseResponse.getMsg)
      false
    }
  }
}
