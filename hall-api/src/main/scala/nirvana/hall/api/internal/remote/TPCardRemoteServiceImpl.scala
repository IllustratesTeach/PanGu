package nirvana.hall.api.internal.remote

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.remote.TPCardRemoteService
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.protocol.api.TPCardProto._
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
    Option(baseResponse.getExtension(TPCardGetResponse.cmd).getCard)
  }

  /**
   * 添加捺印卡片
   * @param tpCard
   * @param url
   */
  override def addTPCard(tpCard: TPCard, url: String) = {
    info("remote add tpcard [personId:{},url:{}]", tpCard.getStrCardID, url)
    val request = TPCardAddRequest.newBuilder().setCard(tpCard).build()
    rpcHttpClient.call(url, TPCardAddRequest.cmd, request)
  }

  /**
   * 更新捺印卡片
   * @param tPCard
   * @param url
   */
  override def updateTPCard(tPCard: TPCard, url: String): Unit = {
    info("remote update tpcard [personId:{},url:{}]", tPCard.getStrCardID, url)
    val request = TPCardUpdateRequest.newBuilder().setCard(tPCard).build()
    rpcHttpClient.call(url, TPCardUpdateRequest.cmd, request)
  }

  /**
   * 验证编号是否已存在
   * @param personId
   * @param url
   * @return
   */
  override def isExist(personId: String, url: String): Boolean = {
    info("remote isExist tpcard [personId:{},url:{}]", personId, url)
    val request = TPCardIsExistRequest.newBuilder().setCardId(personId).build()
    val baseResponse = rpcHttpClient.call(url, TPCardIsExistRequest.cmd, request)
    baseResponse.getExtension(TPCardIsExistResponse.cmd).getIsExist
  }

  /**
   * 删除捺印卡片
   * @param cardId
   */
  override def deleteTPCard(cardId: String, url: String): Unit = {
    info("remote delete tpcard [personId:{},url:{}]", cardId, url)
    val request = TPCardDelRequest.newBuilder().setCardId(cardId).build()
    rpcHttpClient.call(url, TPCardDelRequest.cmd, request)
  }
}
