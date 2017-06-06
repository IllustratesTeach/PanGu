package nirvana.hall.v70.internal

import javax.persistence.EntityManager

import monad.support.services.LoggerSupport
import nirvana.hall.api.HallDatasource
import nirvana.hall.api.services.{HallDatasourceService, LPCardService}
import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa._
import nirvana.hall.v70.services.sys.UserService
import org.springframework.beans.BeanUtils
import org.springframework.transaction.annotation.Transactional

/**
  * Created by songpeng on 16/1/26.
  */
class LPCardServiceImpl(entityManager: EntityManager, userService: UserService, hallDatasourceService: HallDatasourceService) extends LPCardService with LoggerSupport{
  var ip_source=""

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

    val caseFinger_HallDatasource=new HallDatasource(caseFinger.fingerId,"",ip_source,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD)
    hallDatasourceService.save(caseFinger_HallDatasource,HallDatasource.TABLE_V70_CASE_FINGER)
    caseFinger.save()


    caseFingerMnt.pkId = CommonUtils.getUUID()
    caseFingerMnt.inputpsn = user.get.pkId
    caseFingerMnt.deletag = Gafis70Constants.DELETAG_USE
    val caseFingerMnt_HallDatasource=new HallDatasource(caseFinger.inputpsn,"",ip_source,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_ADD)
    hallDatasourceService.save(caseFingerMnt_HallDatasource,HallDatasource.TABLE_V70_CASE_FINGER_MNT)
    caseFingerMnt.save()

    val caseFinger_bak = new GafisCaseFingerBak(caseFinger)
    caseFinger_bak.save()
    val caseFingerMnt_bak = new GafisCaseFingerMntBak(caseFingerMnt)
    caseFingerMnt_bak.save()
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
    val caseFinger_HallDatasource=new HallDatasource(caseFinger.fingerId,"",ip_source,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_MODIFY,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_MODIFY)
    hallDatasourceService.save(caseFinger_HallDatasource,HallDatasource.TABLE_V70_CASE_FINGER)
    caseFinger.save()

    val caseFingerMnt = ProtobufConverter.convertLPCard2GafisCaseFingerMnt(lpCard)
    //先删除，后插入
    GafisCaseFingerMnt.delete.where(GafisCaseFingerMnt.fingerId === caseFinger.fingerId).execute
    caseFingerMnt.pkId = CommonUtils.getUUID()
    caseFingerMnt.inputpsn = user.get.pkId
    caseFingerMnt.deletag = Gafis70Constants.DELETAG_USE
    val caseFingerMnt_HallDatasource=new HallDatasource(caseFingerMnt.pkId,"",ip_source,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_ADD)
    hallDatasourceService.save(caseFingerMnt_HallDatasource,HallDatasource.TABLE_V70_CASE_FINGER_MNT)
    caseFingerMnt.save()
    //数据备份：数据更新
    GafisCaseFingerBak.delete.where(GafisCaseFingerBak.fingerId === caseFinger.fingerId).execute
    val caseFinger_bak = new GafisCaseFingerBak(caseFinger)
    caseFinger_bak.save()
    GafisCaseFingerMntBak.delete.where(GafisCaseFingerMntBak.fingerId === caseFinger.fingerId).execute
    val caseFingerMnt_bak = new GafisCaseFingerMntBak(caseFingerMnt)
    caseFingerMnt_bak.save()
    info("updateLPCard cardId:{}", lpCard.getStrCardID)
  }

  /**
    * 删除现场卡片
    * @param cardId
    * @return
    */
  @Transactional
  override def delLPCard(cardId: String, dbId: Option[String]): Unit = {
    //    GafisCaseFingerMnt.delete.where(GafisCaseFingerMnt.fingerId === cardId).execute
    //    GafisCaseFinger.find(cardId).delete
    val gafisCaseFingerMnt = GafisCaseFingerMnt.where(GafisCaseFingerMnt.fingerId === cardId).headOption.get
    gafisCaseFingerMnt.deletag = Gafis70Constants.DELETAG_DEL
    val caseFingerMnt_HallDatasource=new HallDatasource(cardId,"",ip_source,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_DEL,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_DEL)
    hallDatasourceService.save(caseFingerMnt_HallDatasource,HallDatasource.TABLE_V70_CASE_FINGER_MNT)
    gafisCaseFingerMnt.save()
    GafisCaseFinger.find(cardId).deletag = Gafis70Constants.DELETAG_DEL
    val caseFinger_HallDatasource=new HallDatasource(cardId,"",ip_source,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_DEL,HallDatasource.SERVICE_TYPE_SYNC,HallDatasource.OPERATION_TYPE_DEL)
    hallDatasourceService.save(caseFinger_HallDatasource,HallDatasource.TABLE_V70_CASE_FINGER)
    GafisCaseFinger.update

    //数据备份：标记删除
    var gafisCaseFingerMnt_bak = GafisCaseFingerMntBak.where(GafisCaseFingerMntBak.fingerId === cardId).headOption.get
    if (gafisCaseFingerMnt_bak != null) {
      gafisCaseFingerMnt_bak.deletag = Gafis70Constants.DELETAG_DEL
      gafisCaseFingerMnt_bak.save()
    } else {
      gafisCaseFingerMnt_bak = new GafisCaseFingerMntBak(gafisCaseFingerMnt)
      gafisCaseFingerMnt_bak.save()
    }
    if (GafisCaseFingerBak.find(cardId) != null) {
      GafisCaseFingerBak.find(cardId).deletag = Gafis70Constants.DELETAG_DEL
      GafisCaseFingerBak.update
    } else {
      val gafisCaseFinger_bak = new GafisCaseFingerBak(GafisCaseFinger.find(cardId))
      gafisCaseFinger_bak.save()
    }
  }

  override def isExist(cardId: String, dbId: Option[String]): Boolean = {
    GafisCaseFinger.findOption(cardId).nonEmpty
  }

  /**
    * 赋值来源url
    * @param url
    */
  override def cutIP(url: String): Unit ={
    ip_source=url.split(":", -1)(1).substring(2)
  }
}
