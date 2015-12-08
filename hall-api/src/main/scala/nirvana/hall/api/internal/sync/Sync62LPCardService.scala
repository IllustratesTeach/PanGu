package nirvana.hall.api.internal.sync

import com.google.protobuf.ByteString
import nirvana.hall.api.entities.{GafisCaseFinger, GafisCaseFingerMnt, SyncQueue}
import nirvana.hall.protocol.v62.FPTProto.{FingerFgp, ImageType, LPCard, PatternType}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter
import scalikejdbc._
/**
 * Created by songpeng on 15/12/7.
 */
trait Sync62LPCardService{

  /**
   * 同步现场卡片信息到62
   * @param syncQueue
   * @param session
   * @return
   */
  def syncLPCard(facade: V62Facade, v62Config: HallV62Config, syncQueue: SyncQueue)(implicit session: DBSession): Unit = {
    //TODO 添加修改和删除
    val fingerId = syncQueue.uploadKeyid.get
    val lpCard = LPCard.newBuilder()
    lpCard.setStrCardID(fingerId)

    val card = GafisCaseFinger.find(fingerId).get
    val textBuilder = lpCard.getTextBuilder
    card.seqNo.foreach(f => textBuilder.setStrSeq(f.toString))
    card.isCorpse.foreach(f => textBuilder.setBDeadBody("1".equals(f)))
    card.corpseNo.foreach(textBuilder.setStrDeadPersonNo)
    card.remainPlace.foreach(textBuilder.setStrRemainPlace)
    card.ridgeColor.foreach(textBuilder.setStrRidgeColor)
    card.mittensBegNo.foreach(textBuilder.setStrStart)
    card.mittensEndNo.foreach(textBuilder.setStrEnd)
    card.isAssist.foreach(f => textBuilder.setNXieChaState(f))
    card.matchStatus.foreach(f => textBuilder.setNBiDuiState(f))

    val blobBuilder = lpCard.getBlobBuilder
    blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
    card.developMethod.foreach(blobBuilder.setStrMntExtractMethod)
    card.fingerImg.foreach { img =>
      blobBuilder.setStImageBytes(ByteString.readFrom(img.getBinaryStream))
    }
    //特征
    findCaseFingerMntByFingerId(fingerId).foreach { mnt =>
      mnt.fingerMnt.foreach(f => blobBuilder.setStMntBytes(ByteString.readFrom(mnt.fingerMnt.get.getBinaryStream)))
    }
    //指位
    card.fgp.foreach { fgp =>
      0.until(fgp.length)
        .filter("1" == fgp.charAt(_))
        .foreach (i => blobBuilder.addFgp(FingerFgp.valueOf(i + 1)))
    }
    //纹型
    card.pattern.foreach { pattern =>
      pattern.split(",").foreach(f => blobBuilder.addRp(PatternType.valueOf(f)))
    }

    val gLPCard = galoclpConverter.convertProtoBuf2GLPCARDINFOSTRUCT(lpCard.build())
    //调用实现方法
    facade.NET_GAFIS_FLIB_Add(v62Config.latentTable.dbId.toShort,
      v62Config.latentTable.tableId.toShort,
      fingerId, gLPCard)
  }
  def findCaseFingerMntByFingerId(fingerId: String)(implicit session: DBSession): Option[GafisCaseFingerMnt] = {
    val gcfm = GafisCaseFingerMnt.syntax("gcfm")
    withSQL{
      selectFrom(GafisCaseFingerMnt as gcfm).where.eq(gcfm.fingerId, fingerId)
    }.map(GafisCaseFingerMnt(gcfm.resultName)).single().apply()
  }
}
