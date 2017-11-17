package nirvana.hall.v70.internal

import javax.persistence.EntityManager

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.{LPPalmService}
import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa._
import nirvana.hall.v70.services.sys.UserService
import org.springframework.transaction.annotation.Transactional

/**
  * 现场掌纹service实现
  */
class LPPalmServiceImpl(entityManager: EntityManager, userService: UserService) extends LPPalmService with LoggerSupport{

  /**
    * 新增现场卡片
    *
    * @param lpCard
    * @return
    */
  @Transactional
  override def addLPCard(lpCard: LPCard, dbId: Option[String]): Unit = {
    val casePalm= ProtobufConverter.convertLPCard2GafisCasePalm(lpCard)
    val casePalmMnt = ProtobufConverter.convertLPCard2GafisCasePalmMnt(lpCard)
    val nativeQuery = entityManager.createNativeQuery("select gafis_case_sid_seq.nextval from dual")
    val sid = java.lang.Long.parseLong(nativeQuery.getResultList.get(0).toString)
    casePalm.sid = sid
    //将用户名转为用户id
    var user = userService.findSysUserByLoginName(casePalm.inputpsn)
    if (user.isEmpty){//找不到对应的用户，使用管理员用户
      user = Option(SysUser.find(Gafis70Constants.INPUTPSN))
    }
    casePalm.inputpsn = user.get.pkId
    casePalm.creatorUnitCode = user.get.departCode
    val modUser = userService.findSysUserByLoginName(casePalm.modifiedpsn)
    if(modUser.nonEmpty){
      casePalm.modifiedpsn = modUser.get.pkId
    }else{
      casePalm.modifiedpsn = ""
    }
    casePalm.deletag = Gafis70Constants.DELETAG_USE
    casePalm.save()

    casePalmMnt.pkId = CommonUtils.getUUID()
    casePalmMnt.inputpsn = user.get.pkId
    casePalmMnt.isMainMnt = Gafis70Constants.IS_MAIN_MNT
    casePalmMnt.deletag = Gafis70Constants.DELETAG_USE
    casePalmMnt.save()
    info("addLPPalm cardId:{}", lpCard.getStrCardID)
  }

  /**
    * 获取现场卡片
    *
    * @param palmId
    * @return
    */
  override def getLPCard(palmId: String, dbId: Option[String]): LPCard = {
    val casePalm = GafisCasePalm.find(palmId)
    val casePalmMnt = GafisCasePalmMnt.where(GafisCasePalmMnt.palmId === palmId).and(GafisCasePalmMnt.isMainMnt === "1").headOption.get
    ProtobufConverter.convertGafisCasePalm2LPCard(casePalm, casePalmMnt)
  }

  /**
    * 更新现场卡片
    *
    * @param lpCard
    * @return
    */
  @Transactional
  override def updateLPCard(lpCard: LPCard, dbId: Option[String]): Unit = {
    val casePalm = GafisCasePalm.find(lpCard.getStrCardID)
    ProtobufConverter.convertLPCard2GafisCasePalm(lpCard, casePalm)
    //将用户名转为用户id
    var user = userService.findSysUserByLoginName(casePalm.inputpsn)
    if (user.isEmpty){//找不到对应的用户，使用管理员用户
      user = Option(SysUser.find(Gafis70Constants.INPUTPSN))
    }
    casePalm.inputpsn = user.get.pkId
    casePalm.creatorUnitCode= user.get.departCode
    val modUser = userService.findSysUserByLoginName(casePalm.modifiedpsn)
    if(modUser.nonEmpty){
      casePalm.modifiedpsn = modUser.get.pkId
    }else{
      casePalm.modifiedpsn = ""
    }
    casePalm.deletag = Gafis70Constants.DELETAG_USE
    casePalm.save()

    val casePalmMnt = ProtobufConverter.convertLPCard2GafisCasePalmMnt(lpCard)
    //先删除，后插入
    GafisCasePalmMnt.delete.where(GafisCasePalmMnt.palmId === casePalm.palmId).execute
    casePalmMnt.pkId = CommonUtils.getUUID()
    casePalmMnt.inputpsn = user.get.pkId
    casePalmMnt.deletag = Gafis70Constants.DELETAG_USE
    casePalmMnt.save()

    info("addLPPalm cardId:{}", lpCard.getStrCardID)
  }

  /**
    * 删除现场卡片
    *
    * @param cardId
    * @return
    */
  @Transactional
  override def delLPCard(cardId: String, dbId: Option[String]): Unit = {

    val gafisCasePalmMnt = GafisCasePalmMnt.where(GafisCasePalmMnt.palmId === cardId).headOption.get
    gafisCasePalmMnt.deletag = Gafis70Constants.DELETAG_DEL
    gafisCasePalmMnt.save()
    GafisCasePalm.find(cardId).deletag = Gafis70Constants.DELETAG_DEL
    GafisCasePalm.update
  }

  override def isExist(cardId: String, dbId: Option[String]): Boolean = {
    GafisCasePalm.findOption(cardId).nonEmpty
  }

}
