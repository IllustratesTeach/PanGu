package nirvana.hall.v70.internal.sync

import com.google.protobuf.ByteString
import nirvana.hall.protocol.v62.FPTProto.{FingerFgp, ImageType, LPCard, PatternType}
import nirvana.hall.protocol.v62.lp.LPCardProto._
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
    val lpCard = LPCard.newBuilder()
    lpCard.setStrCardID(fingerId)
    val card = GafisCaseFinger.find(fingerId)

    val textBuilder = lpCard.getTextBuilder
    magicSet(card.seqNo, textBuilder.setStrSeq)
    if ("1".equals(card.isCorpse))
      textBuilder.setBDeadBody(true)
    magicSet(card.corpseNo, textBuilder.setStrDeadPersonNo)
    magicSet(card.remainPlace, textBuilder.setStrRemainPlace)
    magicSet(card.ridgeColor, textBuilder.setStrRidgeColor)
    magicSet(card.mittensBegNo, textBuilder.setStrStart)
    magicSet(card.mittensEndNo, textBuilder.setStrEnd)
    textBuilder.setNXieChaState(card.isAssist)
    textBuilder.setNBiDuiState(card.matchStatus)

    val blobBuilder = lpCard.getBlobBuilder
    blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
    magicSet(card.developMethod, blobBuilder.setStrMntExtractMethod)
    blobBuilder.setStImageBytes(ByteString.copyFrom(card.fingerImg))
    //特征
    GafisCaseFingerMnt.find_by_fingerId_and_isMainMnt(card.fingerId, "1").foreach { mnt =>
      blobBuilder.setStMntBytes(ByteString.copyFrom(mnt.fingerMnt))
    }
    //指位
    if (isNonBlank(card.fgp))
      0.until(card.fgp.length)
        .filter("1" == card.fgp.charAt(_))
        .foreach(i => blobBuilder.addFgp(FingerFgp.valueOf(i + 1)))
    //纹型
    if (isNonBlank(card.pattern))
      card.pattern.split(",").foreach(f => blobBuilder.addRp(PatternType.valueOf(f)))

    lpCard.build()
  }
}
