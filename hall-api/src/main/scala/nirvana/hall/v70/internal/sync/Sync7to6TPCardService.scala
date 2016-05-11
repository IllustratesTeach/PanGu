package nirvana.hall.v70.internal.sync

import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.protocol.api.TPCardProto._
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v70.jpa._

/**
 * Created by songpeng on 15/12/7.
 */
trait Sync7to6TPCardService {
  /**
   * 同步捺印卡到6.2
   * @param syncQueue
   * @return
   */
  def syncTPCard(syncQueue: SyncQueue, rpcHttpClient: RpcHttpClient): Unit = {
    syncQueue.opration match {
      case "insert" =>
        addTPCard(syncQueue, rpcHttpClient)
      case "update" =>
        updateTPCard(syncQueue, rpcHttpClient)
      case "delete" =>
        deleteTPCard(syncQueue, rpcHttpClient)
    }
  }

  private def addTPCard(syncQueue: SyncQueue, rpcHttpClient: RpcHttpClient): Unit = {
    val tpCard = getTPCard(syncQueue.uploadKeyid, "1".equals(syncQueue.hasPalm))
    val request = TPCardAddRequest.newBuilder().setCard(tpCard).build()

    val baseResponse = rpcHttpClient.call("http://"+syncQueue.targetIp+":"+syncQueue.targetPort, TPCardAddRequest.cmd, request)
    if(baseResponse.getStatus == CommandStatus.FAIL){
      println(baseResponse.getMsg)
    }
  }

  private def updateTPCard(syncQueue: SyncQueue, rpcHttpClient: RpcHttpClient): Unit = {
    val tpCard = getTPCard(syncQueue.uploadKeyid, "1".equals(syncQueue.hasPalm))
    val request = TPCardUpdateRequest.newBuilder().setCard(tpCard).build()

    val baseResponse = rpcHttpClient.call("http://"+syncQueue.targetIp+":"+syncQueue.targetPort, TPCardUpdateRequest.cmd, request)
    if(baseResponse.getStatus == CommandStatus.FAIL){
      println(baseResponse.getMsg)
    }
  }

  private def deleteTPCard(syncQueue: SyncQueue, rpcHttpClient: RpcHttpClient): Unit = {
    val request = TPCardDelRequest.newBuilder().setCardId(syncQueue.uploadKeyid).build()

    val baseResponse = rpcHttpClient.call("http://"+syncQueue.targetIp+":"+syncQueue.targetPort, TPCardDelRequest.cmd, request)
    if(baseResponse.getStatus == CommandStatus.FAIL){
      println(baseResponse.getMsg)
    }
  }

  /**
   * 查询捺印卡信息
   * @param personId
   * @return
   */
  //TODO 改用TPCardService
  private def getTPCard(personId: String, hasPalm: Boolean): TPCard = {
    val person = GafisPerson.find(personId)
    val photos = GafisGatherPortrait.find_by_personid(personId).toSeq
    val fingers = GafisGatherFinger.find_by_personId(personId).toSeq

    ProtobufConverter.convertGafisPerson2TPCard(person, photos, fingers, null)
  }

}
