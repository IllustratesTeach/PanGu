package nirvana.hall.v70.gz.services


import java.util.{Date, UUID}
import javax.persistence.EntityManager
import monad.support.services.LoggerSupport
import nirvana.hall.api.services.LPCardService
import nirvana.hall.protocol.api.FPTProto.{FingerFgp, ImageType, LPCard, PatternType}
import nirvana.hall.v70.gz.Constant
import nirvana.hall.v70.gz.jpa.{GafisCaseFinger, GafisCaseFingerMnt, SysUser}
import nirvana.hall.v70.gz.sync.ProtobufConverter
import nirvana.hall.v70.gz.sys.UserService
import nirvana.hall.v70.internal.Gafis70Constants

/**
  * Created by songpeng on 2017/6/29.
  */
class LPCardServiceImpl(entityManager: EntityManager, userService: UserService) extends LPCardService with LoggerSupport{
  /**
    * 新增现场卡片
    *
    * @param lpCard
    * @return
    */
  override def addLPCard(lpCard: LPCard, dbId: Option[String]): Unit = {
    val caseFinger = ProtobufConverter.convertLPCard2GafisCaseFinger(lpCard)
    val caseFingerMnt = ProtobufConverter.convertLPCard2GafisCaseFingerMnt(lpCard)
    val nativeQuery = entityManager.createNativeQuery("select gafis_case_sid_seq.nextval from dual")
    val sid = java.lang.Long.parseLong(nativeQuery.getResultList.get(0).toString)
    caseFinger.sid = sid
    //将用户名转为用户id
    var user = userService.findSysUserByLoginName(caseFinger.inputpsn)
    if (user.isEmpty){//找不到对应的用户，使用管理员用户
      user = Option(SysUser.find(Gafis70Constants.INPUTPSN))
    }
    caseFinger.inputpsn = user.get.pkId
    caseFinger.inputtime = new Date
    caseFinger.creatorUnitCode = user.get.departCode
    val modUser = userService.findSysUserByLoginName(caseFinger.modifiedpsn)
    if(modUser.nonEmpty){
      caseFinger.modifiedpsn = modUser.get.pkId
    }else{
      caseFinger.modifiedpsn = ""
    }
    caseFinger.deletag = Gafis70Constants.DELETAG_USE
    caseFinger.save()

    caseFingerMnt.pkId = UUID.randomUUID().toString.replace("-",Constant.EMPTY)
    caseFingerMnt.inputpsn = user.get.pkId
    caseFingerMnt.deletag = Gafis70Constants.DELETAG_USE
    caseFingerMnt.save()
    info("addLPCard cardId:{}", lpCard.getStrCardID)
  }

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
    if(isExist(fingerId, dbId)){
      val caseFinger = GafisCaseFinger.find(fingerId)
      //处理特征不存在的情况
      if(caseFinger.fingerImg != null){
        val caseFingerMntOp = GafisCaseFingerMnt.where(GafisCaseFingerMnt.fingerId === fingerId).and(GafisCaseFingerMnt.isMainMnt === "1").headOption
        ProtobufConverter.convertGafisCaseFinger2LPCard(caseFinger, caseFingerMntOp.getOrElse(new GafisCaseFingerMnt()))
      }else{
        null
      }
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
