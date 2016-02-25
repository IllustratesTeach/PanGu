package nirvana.hall.v70.internal.sync

import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.protocol.api.LPCardProto._
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v70.jpa.{GafisCaseFinger, GafisCaseFingerMnt, SyncQueue}

/**
 * Created by songpeng on 15/12/7.
 */
trait Sync7to6LPCardService {

  /**
   * 同步现场卡片信息到62
   * @param syncQueue
   * @return
   */
  def syncLPCard(syncQueue: SyncQueue, rpcHttpClient: RpcHttpClient): Unit = {
    syncQueue.opration match {
      case "insert" =>
        addLPCard(syncQueue, rpcHttpClient)
      case "update" =>
        updateLPCard(syncQueue, rpcHttpClient)
      case "delete" =>
        deleteLPCard(syncQueue, rpcHttpClient)
    }
  }

  private def addLPCard(syncQueue: SyncQueue, rpcHttpClient: RpcHttpClient): Unit = {
    val lpCard = getLPCard(syncQueue.uploadKeyid)
    val request = LPCardAddRequest.newBuilder().setCard(lpCard).build()

    val baseResponse = rpcHttpClient.call("http://"+syncQueue.targetIp+":"+syncQueue.targetPort, LPCardAddRequest.cmd, request)
    if(baseResponse.getStatus == CommandStatus.FAIL){
      println(baseResponse.getMsg)
    }
  }

  private def updateLPCard(syncQueue: SyncQueue, rpcHttpClient: RpcHttpClient): Unit = {
    val lpCard = getLPCard(syncQueue.uploadKeyid)
    val request = LPCardUpdateRequest.newBuilder().setCard(lpCard).build()

    val baseResponse = rpcHttpClient.call("http://"+syncQueue.targetIp+":"+syncQueue.targetPort, LPCardUpdateRequest.cmd, request)
    if(baseResponse.getStatus == CommandStatus.FAIL){
      println(baseResponse.getMsg)
    }
  }

  private def deleteLPCard(syncQueue: SyncQueue, rpcHttpClient: RpcHttpClient): Unit = {
    val request = LPCardDelRequest.newBuilder().setCardId(syncQueue.uploadKeyid).build()

    val baseResponse = rpcHttpClient.call("http://"+syncQueue.targetIp+":"+syncQueue.targetPort, LPCardDelRequest.cmd, request)
    if(baseResponse.getStatus == CommandStatus.FAIL){
      println(baseResponse.getMsg)
    }
  }

  private def getLPCard(fingerId: String): LPCard = {
    val caseFinger = GafisCaseFinger.find(fingerId)
    val caseFingerMnt = GafisCaseFingerMnt.find_by_fingerId_and_isMainMnt(fingerId, "1").headOption

    ProtobufConverter.convertGafisCaseFinger2LPCard(caseFinger, caseFingerMnt.get)
  }
}
