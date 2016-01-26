package nirvana.hall.v70.internal.sync

import com.google.protobuf.ByteString
import nirvana.hall.protocol.v62.FPTProto.{FingerFgp, ImageType, LPCard, PatternType}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter
import nirvana.hall.v70.jpa.{SyncQueue, GafisCaseFingerMnt, GafisCaseFinger}

/**
 * Created by songpeng on 15/12/7.
 */
trait Sync7to6LPCardService {

  /**
   * 同步现场卡片信息到62
   * @param syncQueue
   * @return
   */
  def syncLPCard(facade: V62Facade, v62Config: HallV62Config, syncQueue: SyncQueue): Unit = {
    val fingerId = syncQueue.uploadKeyid
    syncQueue.opration match {
      case "insert" =>
        addLPCard(facade, v62Config, fingerId)
      case "update" =>
        updateLPCard(facade, v62Config, fingerId)
      case "delete" =>
        deleteLPCard(facade, v62Config, fingerId)
    }
  }

  private def addLPCard(facade: V62Facade, v62Config: HallV62Config, fingerId: String): Unit = {
    val lpCard = getLPCard(fingerId)
    val gLPCard = galoclpConverter.convertProtoBuf2GLPCARDINFOSTRUCT(lpCard)
    //调用实现方法
    facade.NET_GAFIS_FLIB_Add(v62Config.latentTable.dbId.toShort,
      v62Config.latentTable.tableId.toShort,
      fingerId, gLPCard)
  }

  private def updateLPCard(facade: V62Facade, v62Config: HallV62Config, fingerId: String): Unit = {
    val lpCard = getLPCard(fingerId)
    val gLPCard = galoclpConverter.convertProtoBuf2GLPCARDINFOSTRUCT(lpCard)
    //调用实现方法
    facade.NET_GAFIS_FLIB_Update(v62Config.latentTable.dbId.toShort,
      v62Config.latentTable.tableId.toShort,
      fingerId, gLPCard)
  }

  private def deleteLPCard(facade: V62Facade, v62Config: HallV62Config, fingerId: String): Unit = {
    facade.NET_GAFIS_FLIB_Del(v62Config.latentTable.dbId.toShort,
      v62Config.latentTable.tableId.toShort,
      fingerId)
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
