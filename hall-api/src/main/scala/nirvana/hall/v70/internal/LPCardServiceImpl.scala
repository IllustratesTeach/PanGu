package nirvana.hall.v70.internal

import java.util.Date
import javax.persistence.EntityManager

import nirvana.hall.api.config.DBConfig
import nirvana.hall.api.services.LPCardService
import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.{GafisCaseFinger, GafisCaseFingerMnt}
import org.springframework.beans.BeanUtils
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
class LPCardServiceImpl(entityManager: EntityManager) extends LPCardService{
  /**
   * 新增现场卡片
   * @param lpCard
   * @return
   */
  @Transactional
  override def addLPCard(lpCard: LPCard, dBConfig: DBConfig): Unit = {
    val caseFinger = ProtobufConverter.convertLPCard2GafisCaseFinger(lpCard)
    val caseFingerMnt = ProtobufConverter.convertLPCard2GafisCaseFingerMnt(lpCard)
    val nativeQuery = entityManager.createNativeQuery("select gafis_case_sid_seq.nextval from dual")
    val sid = java.lang.Long.parseLong(nativeQuery.getResultList.get(0).toString)
    caseFinger.sid = sid
    caseFinger.inputpsn = Gafis70Constants.INPUTPSN
    caseFinger.inputtime = new Date()
    caseFinger.deletag = Gafis70Constants.DELETAG_USE
    caseFinger.save()

    caseFingerMnt.pkId = CommonUtils.getUUID()
    caseFingerMnt.inputpsn = Gafis70Constants.INPUTPSN
    caseFingerMnt.inputtime = new Date()
    caseFingerMnt.isMainMnt = Gafis70Constants.IS_MAIN_MNT
    caseFingerMnt.save()
  }

  /**
   * 获取现场卡片
   * @param fingerId
   * @return
   */
  override def getLPCard(fingerId: String, dBConfig: DBConfig): LPCard = {
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
  override def updateLPCard(lpCard: LPCard, dBConfig: DBConfig): Unit = {
    val caseFingerNew = ProtobufConverter.convertLPCard2GafisCaseFinger(lpCard)
    val caseFingerMnt = ProtobufConverter.convertLPCard2GafisCaseFingerMnt(lpCard)

    val caseFinger = GafisCaseFinger.find(caseFingerNew.fingerId)
    BeanUtils.copyProperties(caseFingerNew, caseFinger)
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

  override def isExist(cardId: String, dBConfig: DBConfig): Boolean = {
    GafisCaseFinger.findOption(cardId).nonEmpty
  }
}
