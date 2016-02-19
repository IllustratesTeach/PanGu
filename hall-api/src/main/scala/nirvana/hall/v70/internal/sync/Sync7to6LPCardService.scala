package nirvana.hall.v70.internal.sync

import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.protocol.api.LPCardProto._
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
  def syncLPCard(syncQueue: SyncQueue): Unit = {
    syncQueue.opration match {
      case "insert" =>
        addLPCard(syncQueue)
      case "update" =>
        updateLPCard(syncQueue)
      case "delete" =>
        deleteLPCard(syncQueue)
    }
  }

  private def addLPCard(syncQueue: SyncQueue): Unit = {
    val lpCard = getLPCard(syncQueue.uploadKeyid)
    val request = LPCardAddRequest.newBuilder().setCard(lpCard).build()

    httpCall(syncQueue.targetIp, syncQueue.targetPort, LPCardAddRequest.cmd, request, LPCardAddResponse.newBuilder())
  }

  private def updateLPCard(syncQueue: SyncQueue): Unit = {
    val lpCard = getLPCard(syncQueue.uploadKeyid)
    val request = LPCardUpdateRequest.newBuilder().setCard(lpCard).build()

    httpCall(syncQueue.targetIp, syncQueue.targetPort, LPCardUpdateRequest.cmd, request, LPCardUpdateResponse.newBuilder())
  }

  private def deleteLPCard(syncQueue: SyncQueue): Unit = {
    val request = LPCardDelRequest.newBuilder().setCardId(syncQueue.uploadKeyid).build()

    httpCall(syncQueue.targetIp, syncQueue.targetPort, LPCardDelRequest.cmd, request, LPCardDelResponse.newBuilder())
  }

  private def getLPCard(fingerId: String): LPCard = {
    val caseFinger = GafisCaseFinger.find(fingerId)
    val caseFingerMnt = GafisCaseFingerMnt.find_by_fingerId_and_isMainMnt(fingerId, "1").headOption

    ProtobufConverter.convertGafisCaseFinger2LPCard(caseFinger, caseFingerMnt.get)
  }
}
