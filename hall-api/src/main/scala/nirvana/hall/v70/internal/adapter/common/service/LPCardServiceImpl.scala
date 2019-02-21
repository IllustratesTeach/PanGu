package nirvana.hall.v70.internal.adapter.common.service

import javax.persistence.EntityManager

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.LPCardService
import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.v70.common.jpa.SysUser
import nirvana.hall.v70.internal.{CommonUtils, Gafis70Constants}
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.internal.adapter.common.jpa._
import nirvana.hall.v70.services.sys.UserService
import org.springframework.transaction.annotation.Transactional

/**
  * Created by songpeng on 16/1/26.
  */
class LPCardServiceImpl(entityManager: EntityManager, userService: UserService) extends LPCardService with LoggerSupport{

  /**
    * 新增现场卡片
    * @param lpCard
    * @return
    */
  @Transactional
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
    caseFinger.creatorUnitCode = user.get.departCode
    val modUser = userService.findSysUserByLoginName(caseFinger.modifiedpsn)
    if(modUser.nonEmpty){
      caseFinger.modifiedpsn = modUser.get.pkId
    }else{
      caseFinger.modifiedpsn = ""
    }
    caseFinger.deletag = Gafis70Constants.DELETAG_USE
    caseFinger.save()

    caseFingerMnt.pkId = CommonUtils.getUUID()
    caseFingerMnt.inputpsn = user.get.pkId
    caseFingerMnt.deletag = Gafis70Constants.DELETAG_USE
    caseFingerMnt.save()
    info("addLPCard cardId:{}", lpCard.getStrCardID)
  }

  /**
   * 获取现场卡片
   * @param fingerId
   * @return
   */
  override def getLPCard(fingerId: String, dbId: Option[String]): LPCard = {
    if(isExist(fingerId, dbId)){
      val caseFinger = GafisCaseFinger.find(fingerId)
      val caseFingerMnt = GafisCaseFingerMnt.where(GafisCaseFingerMnt.fingerId === fingerId).and(GafisCaseFingerMnt.isMainMnt === "1").headOption.get
      ProtobufConverter.convertGafisCaseFinger2LPCard(caseFinger, caseFingerMnt)
    }else{
      null
    }
  }

  /**
   * 更新现场卡片
   * @param lpCard
   * @return
   */
  @Transactional
  override def updateLPCard(lpCard: LPCard, dbId: Option[String]): Unit = {
    val caseFinger = GafisCaseFinger.find(lpCard.getStrCardID)
    ProtobufConverter.convertLPCard2GafisCaseFinger(lpCard, caseFinger)
    //将用户名转为用户id
    var user = userService.findSysUserByLoginName(caseFinger.inputpsn)
    if (user.isEmpty){//找不到对应的用户，使用管理员用户
      user = Option(SysUser.find(Gafis70Constants.INPUTPSN))
    }
    caseFinger.inputpsn = user.get.pkId
    caseFinger.creatorUnitCode= user.get.departCode
    val modUser = userService.findSysUserByLoginName(caseFinger.modifiedpsn)
    if(modUser.nonEmpty){
      caseFinger.modifiedpsn = modUser.get.pkId
    }else{
      caseFinger.modifiedpsn = ""
    }
    caseFinger.deletag = Gafis70Constants.DELETAG_USE
    caseFinger.save()

    val caseFingerMnt = ProtobufConverter.convertLPCard2GafisCaseFingerMnt(lpCard)
    //先删除，后插入
    GafisCaseFingerMnt.delete.where(GafisCaseFingerMnt.fingerId === caseFinger.fingerId).execute
    caseFingerMnt.pkId = CommonUtils.getUUID()
    caseFingerMnt.inputpsn = user.get.pkId
    caseFingerMnt.deletag = Gafis70Constants.DELETAG_USE
    caseFingerMnt.save()
    info("updateLPCard cardId:{}", lpCard.getStrCardID)
  }

  /**
    * 删除现场卡片
    * @param cardId
    * @return
    */
  @Transactional
  override def delLPCard(cardId: String, dbId: Option[String]): Unit = {
    val gafisCaseFingerMnt = GafisCaseFingerMnt.where(GafisCaseFingerMnt.fingerId === cardId).headOption.get
    gafisCaseFingerMnt.deletag = Gafis70Constants.DELETAG_DEL
    gafisCaseFingerMnt.save()
    GafisCaseFinger.find(cardId).deletag = Gafis70Constants.DELETAG_DEL
    GafisCaseFinger.update
  }

  override def isExist(cardId: String, dbId: Option[String]): Boolean = {
    GafisCaseFinger.findOption(cardId).nonEmpty
  }

}
