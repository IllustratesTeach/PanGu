package nirvana.hall.v70.internal

import nirvana.hall.api.services.LPCardService
import nirvana.hall.protocol.v62.lp.LPCardProto._
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.{GafisCaseFinger, GafisCaseFingerMnt}

/**
 * Created by songpeng on 16/1/26.
 */
class LPCardServiceImpl extends LPCardService{
  /**
   * 新增现场卡片
   * @param lPCardAddRequest
   * @return
   */
  override def addLPCard(lPCardAddRequest: LPCardAddRequest): LPCardAddResponse = {
    val lpCard = lPCardAddRequest.getCard
    val caseFinger = ProtobufConverter.convertLPCard2GafisCaseFinger(lpCard)
    val caseFingerMnt = ProtobufConverter.convertLPCard2GafisCaseFingerMnt(lpCard)

    //TODO 记录操作信息，创建人等
    caseFinger.save()
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
  override def updateLPCard(lPCardUpdateRequest: LPCardUpdateRequest): LPCardUpdateResponse = {
    val lpCard = lPCardUpdateRequest.getCard
    val caseFinger = ProtobufConverter.convertLPCard2GafisCaseFinger(lpCard)
    val caseFingerMnt = ProtobufConverter.convertLPCard2GafisCaseFingerMnt(lpCard)

    //TODO 记录操作信息，创建人等
//    caseFinger.save()
//    caseFingerMnt.save()

    LPCardUpdateResponse.newBuilder().build()
    throw new UnsupportedOperationException
  }

  /**
   * 删除现场卡片
   * @param lPCardDelRequest
   * @return
   */
  override def delLPCard(lPCardDelRequest: LPCardDelRequest): LPCardDelResponse = {
    val cardId = lPCardDelRequest.getCardId
    GafisCaseFingerMnt.where("fingerId=?1", cardId).delete
    GafisCaseFinger.find(cardId).delete()

    LPCardDelResponse.newBuilder().build()
  }
}
