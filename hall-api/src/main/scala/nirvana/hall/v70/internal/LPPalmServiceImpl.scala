package nirvana.hall.v70.internal

import javax.persistence.EntityManager

import monad.support.services.LoggerSupport
import nirvana.hall.api.HallDatasource
import nirvana.hall.api.services.{HallDatasourceService, LPPalmService}
import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa._
import nirvana.hall.v70.services.sys.UserService
import org.springframework.transaction.annotation.Transactional

/**
  * 现场掌纹service实现
  */
class LPPalmServiceImpl(entityManager: EntityManager, userService: UserService,hallDatasourceService: HallDatasourceService) extends LPPalmService with LoggerSupport{
  var ip_source=""

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
    val casePalm_HallDatasource=new HallDatasource(casePalm.palmId,casePalm.palmId,ip_source,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_ADD)
    hallDatasourceService.save(casePalm_HallDatasource,HallDatasource.TABLE_V70_CASE_PALM)
    casePalm.save()
    val casePalm_bak = new GafisCasePalmBak(casePalm)
    casePalm_bak.save()

    casePalmMnt.pkId = CommonUtils.getUUID()
    casePalmMnt.inputpsn = user.get.pkId
    casePalmMnt.isMainMnt = Gafis70Constants.IS_MAIN_MNT
    casePalmMnt.deletag = Gafis70Constants.DELETAG_USE
    val casePalmMnt_HallDatasource=new HallDatasource(casePalmMnt.pkId,casePalm.palmId,ip_source,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_ADD)
    hallDatasourceService.save(casePalmMnt_HallDatasource,HallDatasource.TABLE_V70_CASE_PALM_MNT)
    casePalmMnt.save()
    val casePalmMnt_bak = new GafisCasePalmMntBak(casePalmMnt)
    casePalmMnt_bak.save()
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
    val casePalm_HallDatasource=new HallDatasource(casePalm.palmId,casePalm.palmId,ip_source,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_MODIFY,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_MODIFY)
    hallDatasourceService.save(casePalm_HallDatasource,HallDatasource.TABLE_V70_CASE_PALM)
    casePalm.save()

    val casePalmMnt = ProtobufConverter.convertLPCard2GafisCasePalmMnt(lpCard)
    //先删除，后插入
    GafisCasePalmMnt.delete.where(GafisCasePalmMnt.palmId === casePalm.palmId).execute
    casePalmMnt.pkId = CommonUtils.getUUID()
    casePalmMnt.inputpsn = user.get.pkId
    casePalmMnt.deletag = Gafis70Constants.DELETAG_USE
    val casePalmMnt_HallDatasource=new HallDatasource(casePalmMnt.pkId,casePalm.palmId,ip_source,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_MODIFY,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_MODIFY)
    hallDatasourceService.save(casePalmMnt_HallDatasource,HallDatasource.TABLE_V70_CASE_PALM_MNT)
    casePalmMnt.save()

    //数据备份：数据更新
    GafisCasePalmBak.delete.where(GafisCasePalmBak.palmId === casePalm.palmId).execute
    val casePalm_bak = new GafisCasePalmBak(casePalm)
    casePalm_bak.save()
    GafisCasePalmMntBak.delete.where(GafisCasePalmMntBak.palmId === casePalm.palmId).execute
    val casePalmMnt_bak = new GafisCasePalmMntBak(casePalmMnt)
    casePalmMnt_bak.save()
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
    val casePalmMnt_HallDatasource=new HallDatasource(gafisCasePalmMnt.pkId,cardId,ip_source,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_DEL,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_DEL)
    hallDatasourceService.save(casePalmMnt_HallDatasource,HallDatasource.TABLE_V70_CASE_PALM_MNT)
    gafisCasePalmMnt.save()
    GafisCasePalm.find(cardId).deletag = Gafis70Constants.DELETAG_DEL
    val casePalm_HallDatasource=new HallDatasource(cardId,cardId,ip_source,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_DEL,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_DEL)
    hallDatasourceService.save(casePalm_HallDatasource,HallDatasource.TABLE_V70_CASE_PALM)
    GafisCasePalm.update
    //数据备份：标记删除
    var gafisCasePalmMnt_bak = GafisCasePalmMntBak.where(GafisCasePalmMntBak.palmId === cardId).headOption.get
    if (gafisCasePalmMnt_bak != null) {
      gafisCasePalmMnt_bak.deletag = Gafis70Constants.DELETAG_DEL
      gafisCasePalmMnt_bak.save()
    } else {
      gafisCasePalmMnt_bak = new GafisCasePalmMntBak(gafisCasePalmMnt)
      gafisCasePalmMnt_bak.save()
    }
    if (GafisCasePalmBak.find(cardId) != null) {
      GafisCasePalmBak.find(cardId).deletag = Gafis70Constants.DELETAG_DEL
      GafisCasePalmBak.update
    } else {
      val gafisCasePalm_bak = new GafisCasePalmBak(GafisCasePalm.find(cardId))
      gafisCasePalm_bak.save()
    }
  }

  override def isExist(cardId: String, dbId: Option[String]): Boolean = {
    GafisCasePalm.findOption(cardId).nonEmpty
    var result=false
    if(GafisCasePalm.findOption(cardId).nonEmpty){
      val palm=GafisCasePalm.findOption(cardId).get
      if(palm.deletag.equals("1"))
        result=true
    }
    result
  }

  /**
    * 赋值来源url
    *
    * @param url
    */
  override def cutIP(url: String): Unit = {
    ip_source=url.split(":", -1)(1).substring(2)
  }
}
