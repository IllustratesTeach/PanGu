package nirvana.hall.v70.internal

import java.util.Date

import nirvana.hall.api.services.LPCardService
import nirvana.hall.protocol.api.LPCardProto._
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.{GafisCaseFinger, GafisCaseFingerMnt}
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
class LPCardServiceImpl extends LPCardService{
  /**
   * 新增现场卡片
   * @param lPCardAddRequest
   * @return
   */
  @Transactional
  override def addLPCard(lPCardAddRequest: LPCardAddRequest): LPCardAddResponse = {
    val lpCard = lPCardAddRequest.getCard
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

    LPCardAddResponse.newBuilder().build()
  }

  /**
   * 获取现场卡片
   * @param lPCardGetRequest
   * @return
   */
  override def getLPCard(lPCardGetRequest: LPCardGetRequest): LPCardGetResponse = {
    val fingerId = lPCardGetRequest.getCardId
    val caseFinger = GafisCaseFinger.find(fingerId)
    val caseFingerMnt = GafisCaseFingerMnt.find_by_fingerId(fingerId).firstOption.get
    val lpCard = ProtobufConverter.convertGafisCaseFinger2LPCard(caseFinger, caseFingerMnt)

    LPCardGetResponse.newBuilder().setCard(lpCard).build()
  }

  /**
   * 更新现场卡片
   * @param lPCardUpdateRequest
   * @return
   */
  @Transactional
  override def updateLPCard(lPCardUpdateRequest: LPCardUpdateRequest): LPCardUpdateResponse = {
    val lpCard = lPCardUpdateRequest.getCard
    val caseFinger = ProtobufConverter.convertLPCard2GafisCaseFinger(lpCard)
    val caseFingerMnt = ProtobufConverter.convertLPCard2GafisCaseFingerMnt(lpCard)

    caseFinger.modifiedpsn = Gafis70Constants.INPUTPSN
    caseFinger.modifiedtime = new Date()
    caseFinger.deletag = Gafis70Constants.DELETAG_USE
    caseFinger.save()

    //先删除，后插入
    GafisCaseFingerMnt.where("fingerId=?1", caseFinger.fingerId).delete
    caseFingerMnt.pkId = CommonUtils.getUUID()
    caseFingerMnt.inputpsn = Gafis70Constants.INPUTPSN
    caseFingerMnt.inputtime = new Date()
    caseFingerMnt.save()

    LPCardUpdateResponse.newBuilder().build()
  }

  /**
   * 删除现场卡片
   * @param lPCardDelRequest
   * @return
   */
  @Transactional
  override def delLPCard(lPCardDelRequest: LPCardDelRequest): LPCardDelResponse = {
    val cardId = lPCardDelRequest.getCardId
    GafisCaseFingerMnt.where("fingerId=?1", cardId).delete
    GafisCaseFinger.where("fingerId=?1",cardId).delete

    LPCardDelResponse.newBuilder().build()
  }
}
