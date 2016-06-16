package nirvana.hall.v70.internal

import java.util.Date

import nirvana.hall.api.services.LPCardService
import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.{GafisCaseFinger, GafisCaseFingerMnt}
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
class LPCardServiceImpl extends LPCardService{
  /**
   * 新增现场卡片
   * @param lpCard
   * @return
   */
  @Transactional
  override def addLPCard(lpCard: LPCard): Unit = {
    val caseFinger = ProtobufConverter.convertLPCard2GafisCaseFinger(lpCard)
    val caseFingerMnt = ProtobufConverter.convertLPCard2GafisCaseFingerMnt(lpCard)

    caseFinger.inputpsn = Gafis70Constants.INPUTPSN
    caseFinger.inputtime = new Date()
    caseFinger.deletag = Gafis70Constants.DELETAG_USE
    caseFinger.save()

    caseFingerMnt.pkId = CommonUtils.getUUID()
    caseFingerMnt.inputpsn = Gafis70Constants.INPUTPSN
    caseFingerMnt.inputtime = new Date()
    caseFingerMnt.isMainMnt = "1"
    caseFingerMnt.save()
  }

  /**
   * 获取现场卡片
   * @param fingerId
   * @return
   */
  override def getLPCard(fingerId: String): LPCard = {
    val caseFinger = GafisCaseFinger.find(fingerId)
    val caseFingerMnt = GafisCaseFingerMnt.where(GafisCaseFingerMnt.fingerId === fingerId).and(GafisCaseFingerMnt.isMainMnt === "1").headOption.get
    ProtobufConverter.convertGafisCaseFinger2LPCard(caseFinger, caseFingerMnt)
  }

  /**
   * 更新现场卡片
   * @param lpCard
   * @return
   */
  @Transactional
  override def updateLPCard(lpCard: LPCard): Unit = {
    val caseFinger = ProtobufConverter.convertLPCard2GafisCaseFinger(lpCard)
    val caseFingerMnt = ProtobufConverter.convertLPCard2GafisCaseFingerMnt(lpCard)

    caseFinger.modifiedpsn = Gafis70Constants.INPUTPSN
    caseFinger.modifiedtime = new Date()
    caseFinger.deletag = Gafis70Constants.DELETAG_USE
    caseFinger.save()

    //先删除，后插入
    GafisCaseFingerMnt.delete.where(GafisCaseFingerMnt.fingerId === caseFinger.fingerId).execute
    caseFingerMnt.pkId = CommonUtils.getUUID()
    caseFingerMnt.inputpsn = Gafis70Constants.INPUTPSN
    caseFingerMnt.inputtime = new Date()
    caseFingerMnt.save()
  }

  /**
   * 删除现场卡片
   * @param cardId
   * @return
   */
  @Transactional
  override def delLPCard(cardId: String): Unit = {
    GafisCaseFingerMnt.delete.where(GafisCaseFingerMnt.fingerId === cardId).execute
    GafisCaseFinger.find(cardId).delete
  }

  override def isExist(cardId: String): Boolean = {
    GafisCaseFinger.findOption(cardId).nonEmpty
  }
}
