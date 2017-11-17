package nirvana.hall.v70.gz.gzcapture

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
    var lpcard:LPCard = null
    val hallHitinfoRecord = new HallHitinfoRecord()
    hallHitinfoRecord.uuid = UUID.randomUUID().toString.replace("-",Constant.EMPTY)
    hallHitinfoRecord.codetype = Constant.GETLP
    hallHitinfoRecord.code = fingerId
    try{
      if(isExist(fingerId, dbId)){
        val caseFinger = GafisCaseFinger.find(fingerId)
        //处理特征不存在的情况
        if(caseFinger.fingerImg != null){
          val caseFingerMntOp = GafisCaseFingerMnt.where(GafisCaseFingerMnt.fingerId === fingerId).and(GafisCaseFingerMnt.isMainMnt === "1").headOption
          lpcard = ProtobufConverter.convertGafisCaseFinger2LPCard(caseFinger, caseFingerMntOp.getOrElse(new GafisCaseFingerMnt()))
        }
        hallHitinfoRecord.status = Constant.SUCCESS
        hallHitinfoRecord.insertdate = new Date()
        hallHitinfoRecord.save()
      }
    }catch {
      case ex:Exception =>
        hallHitinfoRecord.status = Constant.FAIL
        hallHitinfoRecord.insertdate = new Date()
        hallHitinfoRecord.save()
        val hallCaptureException = new HallCaptureException()
        hallCaptureException.uuid = UUID.randomUUID().toString.replace("-",Constant.EMPTY)
        hallCaptureException.puuid = hallHitinfoRecord.uuid
        hallCaptureException.msg = ex.getMessage
        hallCaptureException.errtype = Constant.GetLPCardFilter
        hallCaptureException.save()
    }
    lpcard
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
