package nirvana.hall.v70.internal.sync

import nirvana.hall.protocol.v62.FPTProto.TPCard
import nirvana.hall.protocol.v62.tp.TPCardProto._
import nirvana.hall.v70.jpa.{GafisGatherFinger, GafisPerson, SyncQueue}

/**
 * Created by songpeng on 15/12/7.
 */
trait Sync7to6TPCardService {
  /**
   * 同步捺印卡到6.2
   * @param syncQueue
   * @return
   */
  def syncTPCard(syncQueue: SyncQueue): Unit = {
    syncQueue.opration match {
      case "insert" =>
        addTPCard(syncQueue)
      case "update" =>
        updateTPCard(syncQueue)
      case "delete" =>
        deleteTPCard(syncQueue)
    }
  }

  private def addTPCard(syncQueue: SyncQueue): Unit = {
    val tpCard = getTPCard(syncQueue.uploadKeyid)
    val request = TPCardAddRequest.newBuilder().setCard(tpCard).build()

    httpCall(syncQueue.targetIp, syncQueue.targetPort,TPCardAddRequest.cmd, request, TPCardAddResponse.newBuilder())
  }

  private def updateTPCard(syncQueue: SyncQueue): Unit = {
    val tpCard = getTPCard(syncQueue.uploadKeyid)
    val request = TPCardUpdateRequest.newBuilder().setCard(tpCard).build()

    httpCall(syncQueue.targetIp, syncQueue.targetPort, TPCardUpdateRequest.cmd, request, TPCardUpdateResponse.newBuilder())
  }

  private def deleteTPCard(syncQueue: SyncQueue): Unit = {
    val request = TPCardDelRequest.newBuilder().setCardId(syncQueue.uploadKeyid).build()

    httpCall(syncQueue.targetIp, syncQueue.targetPort, TPCardDelRequest.cmd, request,TPCardDelResponse.newBuilder())
  }

  private def getTPCard(personId: String): TPCard = {
    val person = GafisPerson.find(personId)
    val fingers = GafisGatherFinger.find_by_personId(personId)

    ProtobufConverter.convertGafisPerson2TPCard(person, fingers)
  }

}
