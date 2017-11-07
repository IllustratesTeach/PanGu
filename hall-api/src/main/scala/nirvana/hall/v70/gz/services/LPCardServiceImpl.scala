package nirvana.hall.v70.gz.services

import java.util.{Date, UUID}

import nirvana.hall.api.services.LPCardService
import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.v70.gz.Constant
import nirvana.hall.v70.gz.jpa.{GafisCaseFinger, GafisCaseFingerMnt, HallCaptureException, HallHitinfoRecord}
import nirvana.hall.v70.gz.sync.ProtobufConverter

/**
  * Created by songpeng on 2017/6/29.
  */
class LPCardServiceImpl extends LPCardService{
  /**
    * 新增现场卡片
    *
    * @param lpCard
    * @return
    */
  override def addLPCard(lpCard: LPCard, dbId: Option[String]): Unit = ???

  /**
    * 删除现场卡片
    *
    * @param cardId
    * @return
    */
override def delLPCard(cardId: String, dbId: Option[String]): Unit = ???

  /**
    * 更新现场卡片
    *
    * @param lpCard
    * @return
    */
  override def updateLPCard(lpCard: LPCard, dbId: Option[String]): Unit = ???

  /**
    * 获取现场卡片
    *
    * @param fingerId
    * @return
    */
  override def getLPCard(fingerId: String, dbId: Option[String]): LPCard = {
    if(isExist(fingerId, dbId)) {
      val caseFinger = GafisCaseFinger.find(fingerId)
      val caseFingerMntOp = GafisCaseFingerMnt.where(GafisCaseFingerMnt.fingerId === fingerId).and(GafisCaseFingerMnt.isMainMnt === "1").headOption
      ProtobufConverter.convertGafisCaseFinger2LPCard(caseFinger, caseFingerMntOp.getOrElse(new GafisCaseFingerMnt()))
    }else{
      null
    }
  }

  /**
    * 验证现场卡片是否存在
    *
    * @param cardId
    * @return
    */
  override def isExist(cardId: String, dbId: Option[String]): Boolean = {
    GafisCaseFinger.findOption(cardId).nonEmpty
  }
}
