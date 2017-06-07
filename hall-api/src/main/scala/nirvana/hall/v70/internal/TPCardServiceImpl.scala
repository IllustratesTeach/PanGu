package nirvana.hall.v70.internal

import java.util.Date
import javax.persistence.EntityManager

import monad.support.services.LoggerSupport
import nirvana.hall.api.HallDatasource
import nirvana.hall.api.services.{HallDatasourceService, TPCardService}
import nirvana.hall.c.services.gfpt4lib.FPT4File.Logic02Rec
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.{GafisGatherPalm, _}
import nirvana.hall.v70.services.sys.UserService
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
class TPCardServiceImpl(entityManager: EntityManager, userService: UserService, hallDatasourceService:HallDatasourceService) extends TPCardService with LoggerSupport{
  var ip_source=""  /**
   * 获取捺印卡信息
   * @param personId
   * @return
   */
  override def getTPCard(personId: String, dbid: Option[String]): TPCard = {
    val person = GafisPerson.find(personId)
    val photoList = GafisGatherPortrait.find_by_personid(personId).toSeq
    val fingerList = GafisGatherFinger.find_by_personId(personId).toSeq
    val palmList = GafisGatherPalm.find_by_personId(personId).toSeq

    ProtobufConverter.convertGafisPerson2TPCard(person, photoList, fingerList, palmList)
  }

  /**
   * 新增捺印卡片
   * @param tpCard
   * @return
   */
  @Transactional
  override def addTPCard(tpCard: TPCard, dbId: Option[String]): Unit = {
    info("addTPCard cardId:{}", tpCard.getStrCardID)
    //验证卡号是否已经存在
    if(isExist(tpCard.getStrCardID)){
      throw new RuntimeException("记录已存在")
    }else{
      //保存人员基本信息
      val person = ProtobufConverter.convertTPCard2GafisPerson(tpCard)
      val sid = java.lang.Long.parseLong(entityManager.createNativeQuery("select gafis_person_sid_seq.nextval from dual").getResultList.get(0).toString)
      person.sid = sid
      //用户名获取用户ID
      var user = userService.findSysUserByLoginName(person.inputpsn)
      if (user.isEmpty){
        user = Option(SysUser.find(Gafis70Constants.INPUTPSN))
      }
      person.inputpsn = user.get.pkId
      person.gatherOrgCode = user.get.departCode
      val modUser = userService.findSysUserByLoginName(person.modifiedpsn)
      if(modUser.nonEmpty){
        person.modifiedpsn = modUser.get.pkId
      }else{
        person.modifiedpsn = ""
      }

      person.deletag = Gafis70Constants.DELETAG_USE
      person.fingershowStatus = 1.toShort
      person.isfingerrepeat = "0"
      person.dataSources = Gafis70Constants.DATA_SOURCE_GAFIS6
      person.gatherTypeId = Gafis70Constants.GATHER_TYPE_ID_DEFAULT
      val person_HallDatasource=new HallDatasource(tpCard.getStrCardID,tpCard.getStrCardID,ip_source,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD)
      hallDatasourceService.save(person_HallDatasource,HallDatasource.TABLE_V70_PERSON)
      person.save()
      val person_bak = new GafisPersonBak(person)
      person_bak.save()
      //保存逻辑库
      val logicDb: GafisLogicDb = if(dbId == None || dbId.get.length <= 0){
        //如果没有指定逻辑库，使用默认库
        GafisLogicDb.where(GafisLogicDb.logicCategory === "0").and(GafisLogicDb.logicIsdefaulttag === "1").headOption.get
      }else{
        GafisLogicDb.find(dbId.get)
      }
      val logicDbFingerprint = new GafisLogicDbFingerprint()
      logicDbFingerprint.pkId = CommonUtils.getUUID()
      logicDbFingerprint.fingerprintPkid = person.personid
      logicDbFingerprint.logicDbPkid = logicDb.pkId
      logicDbFingerprint.save()
      val logicDbFingerprint_bak = new GafisLogicDbFingerprintBak(logicDbFingerprint)
      logicDbFingerprint_bak.save()
      //保存指纹
      val fingerList = ProtobufConverter.convertTPCard2GafisGatherFinger(tpCard)
      GafisGatherFinger.find_by_personId(person.personid).foreach(f=> f.delete())
      fingerList.foreach{finger =>
        finger.pkId = CommonUtils.getUUID()
        finger.inputtime = new Date()
        finger.inputpsn = Gafis70Constants.INPUTPSN
        val finger_HallDatasource=new HallDatasource(finger.pkId,finger.personId,ip_source,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD)
        hallDatasourceService.save(finger_HallDatasource,HallDatasource.TABLE_V70_PERSON_FINGER)
        finger.save()
        val finger_bak = new GafisGatherFingerBak(finger)
        finger_bak.save()
      }
      //掌纹
      val palmList = ProtobufConverter.convertTPCard2GafisGatherPalm(tpCard)
      GafisGatherPalm.find_by_personId(person.personid).foreach(f=> f.delete())
      palmList.foreach{palm=>
        palm.pkId = CommonUtils.getUUID()
        palm.inputtime = new Date()
        palm.inputpsn = Gafis70Constants.INPUTPSN
        val palm_HallDatasource=new HallDatasource(palm.pkId,palm.personId,ip_source,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD)
        hallDatasourceService.save(palm_HallDatasource,HallDatasource.TABLE_V70_PERSON_PALM)
        palm.save()
        val palm_bak = new GafisGatherPalmBak(palm)
        palm_bak.save()
      }
      //保存人像
      val portraitList = ProtobufConverter.convertTPCard2GafisGatherPortrait(tpCard)
      portraitList.foreach{ portrait =>
        portrait.pkId = CommonUtils.getUUID()
        portrait.inputpsn = Gafis70Constants.INPUTPSN
        portrait.inputtime = new Date()
        portrait.deletag = Gafis70Constants.DELETAG_USE
        val portrait_HallDatasource=new HallDatasource(portrait.pkId,portrait.personid,ip_source,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD)
        hallDatasourceService.save(portrait_HallDatasource,HallDatasource.TABLE_V70_PERSON_PORTRAIT)
        portrait.save()
        val portrait_bak = new GafisGatherPortraitBak(portrait)
        portrait_bak.save()
      }
    }
  }

  /**
   * 删除捺印卡片, 更新删除状态
   * @param cardId
   * @return
   */
  @Transactional
  override def delTPCard(cardId: String, dbId: Option[String]): Unit = {
    val gafisPerson = GafisPerson.find(cardId)
    gafisPerson.deletag = Gafis70Constants.DELETAG_DEL
    val person_HallDatasource=new HallDatasource(cardId,cardId,ip_source,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_DEL,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_DEL)
    hallDatasourceService.save(person_HallDatasource,HallDatasource.TABLE_V70_PERSON)
    gafisPerson.save()
    //数据备份：数据删除
    var gafisPerson_bak = GafisPersonBak.find(cardId)
    if (gafisPerson_bak != null) {
      gafisPerson_bak.deletag = Gafis70Constants.DELETAG_DEL
      gafisPerson_bak.save()
    } else {
      gafisPerson_bak = new GafisPersonBak(gafisPerson)
      gafisPerson_bak.save()
    }
    /*//删除指纹
    GafisGatherFinger.find_by_personId(cardId).foreach(f=> f.delete())
    //删除掌纹
    GafisGatherPalm.find_by_personId(cardId).foreach(f=> f.delete())
    //删除人像
    GafisGatherPortrait.find_by_personid(cardId).foreach(f=> f.delete())
    //删除逻辑库
    GafisLogicDbFingerprint.find_by_fingerprintPkid(cardId).foreach(_.delete())
    //删除人员信息
    GafisPerson.find(cardId).delete()*/
  }

  /**
   * 更新捺印卡片
   * @param tpCard
   * @return
   */
  @Transactional
  override def updateTPCard(tpCard: TPCard, dbId: Option[String]): Unit ={
    info("updateTPCard cardId:{}", tpCard.getStrCardID)
    val person = GafisPerson.find(tpCard.getStrCardID)
    ProtobufConverter.convertTPCard2GafisPerson(tpCard, person)

    //用户名获取用户ID
    var user = userService.findSysUserByLoginName(person.inputpsn)
    if (user.isEmpty){//找不到对应的用户，使用管理员用户
      user = Option(SysUser.find(Gafis70Constants.INPUTPSN))
    }
    person.inputpsn = user.get.pkId
    person.gatherOrgCode = user.get.departCode
    val modUser = userService.findSysUserByLoginName(person.modifiedpsn)
    if(modUser.nonEmpty){
      person.modifiedpsn = modUser.get.pkId
    }else{
      person.modifiedpsn = ""
    }

    person.deletag = Gafis70Constants.DELETAG_USE
    val person_HallDatasource=new HallDatasource(tpCard.getStrCardID,tpCard.getStrCardID,ip_source,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_MODIFY,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_MODIFY)
    hallDatasourceService.save(person_HallDatasource,HallDatasource.TABLE_V70_PERSON)
    person.save()
    //GafisPersonBak.find(GafisPersonBak.personid === person.personid).delete()
    GafisPersonBak.find_by_personid(person.personid).foreach(_.delete())
    val person_bak = new GafisPersonBak(person)
    person_bak.save()
    //删除原来的逻辑库
    GafisLogicDbFingerprint.find_by_fingerprintPkid(person.personid).foreach(_.delete())
    //保存逻辑库
    val logicDb: GafisLogicDb = if(dbId == None || dbId.get.length <= 0){
      //如果没有指定逻辑库，使用默认库
      GafisLogicDb.where(GafisLogicDb.logicCategory === "0").and(GafisLogicDb.logicIsdefaulttag === "1").headOption.get
    }else{
      GafisLogicDb.find(dbId.get)
    }
    val logicDbFingerprint = new GafisLogicDbFingerprint()
    logicDbFingerprint.pkId = CommonUtils.getUUID()
    logicDbFingerprint.fingerprintPkid = person.personid
    logicDbFingerprint.logicDbPkid = logicDb.pkId
    logicDbFingerprint.save()
    GafisLogicDbFingerprintBak.find_by_fingerprintPkid(person.personid).foreach(_.delete())
    val logicDbFingerprint_bak = new GafisLogicDbFingerprintBak(logicDbFingerprint)
    logicDbFingerprint_bak.save()
    //指纹
    val fingerList = ProtobufConverter.convertTPCard2GafisGatherFinger(tpCard)
    GafisGatherFinger.find_by_personId(person.personid).foreach(f=> f.delete())
    GafisGatherFingerBak.find_by_personId(person.personid).foreach(f=>f.delete())
    fingerList.foreach{finger =>
      finger.pkId = CommonUtils.getUUID()
      finger.inputtime = new Date()
      finger.inputpsn = Gafis70Constants.INPUTPSN
      val finger_HallDatasource=new HallDatasource(finger.pkId,finger.personId,ip_source,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD)
      hallDatasourceService.save(finger_HallDatasource,HallDatasource.TABLE_V70_PERSON_FINGER)
      finger.save()
      val finger_bak = new GafisGatherFingerBak(finger)
      finger_bak.save()
    }
    //掌纹
    val palmList = ProtobufConverter.convertTPCard2GafisGatherPalm(tpCard)
    GafisGatherPalm.find_by_personId(person.personid).foreach(f=> f.delete())
    GafisGatherPalmBak.find_by_personId(person.personid).foreach(f=>f.delete())
    palmList.foreach{palm=>
      palm.pkId = CommonUtils.getUUID()
      palm.inputtime = new Date()
      palm.inputpsn = Gafis70Constants.INPUTPSN
      val palm_HallDatasource=new HallDatasource(palm.pkId,palm.personId,ip_source,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD)
      hallDatasourceService.save(palm_HallDatasource,HallDatasource.TABLE_V70_PERSON_PALM)
      palm.save()
      val palm_bak = new GafisGatherPalmBak(palm)
      palm_bak.save()
    }

    //人像
    val portraitList = ProtobufConverter.convertTPCard2GafisGatherPortrait(tpCard)
    GafisGatherPortrait.find_by_personid(person.personid).foreach(f=> f.delete())
    GafisGatherPortraitBak.find_by_personid(person.personid).foreach(f=>f.delete())
    portraitList.foreach{portrait=>
      portrait.pkId = CommonUtils.getUUID()
      portrait.inputtime = new Date()
      portrait.inputpsn = Gafis70Constants.INPUTPSN
      portrait.deletag = Gafis70Constants.DELETAG_USE
      val portrait_HallDatasource=new HallDatasource(portrait.pkId,portrait.personid,ip_source,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.OPERATION_TYPE_ADD)
      hallDatasourceService.save(portrait_HallDatasource,HallDatasource.TABLE_V70_PERSON_PORTRAIT)
      portrait.save()
      val portrait_bak = new GafisGatherPortraitBak(portrait)
      portrait_bak.save()
    }
  }

  /**
   * 验证卡号是否已存在
   * @param cardId
   * @return
   */
  override def isExist(cardId: String, dbId: Option[String]): Boolean = {
    GafisPerson.findOption(cardId).nonEmpty
    var result=false
    if(GafisPerson.findOption(cardId).nonEmpty){
      val person=GafisPerson.findOption(cardId).get
      if(person.deletag.equals("1"))
        result=true
    }
    result
  }

  /**
    * 查询捺印文本信息
    * @param ryno        人员编号
    * @param xm          姓名
    * @param xb          性别
    * @param idno        身份证号码
    * @param zjlb        证件类别
    * @param zjhm        证件号码
    * @param hjddm       户籍地代码
    * @param xzzdm       现住址代码
    * @param rylb        人员类别
    * @param ajlb        案件类别
    * @param qkbs        前科标识
    * @param xcjb        协查级别
    * @param nydwdm      捺印单位代码
    * @param startnydate 开始时间（检索捺印时间，时间格式YYYYMMDDHHMM）
    * @param endnydate   结束时间（检索捺印时间，时间格式YYYYMMDDHHMM）
    * @return Logic02Rec(fpt4捺印文本信息)
    */
  override def getFPT4Logic02RecList(ryno: String, xm: String, xb: String, idno: String, zjlb: String, zjhm: String, hjddm: String, xzzdm: String, rylb: String, ajlb: String, qkbs: String, xcjb: String, nydwdm: String, startnydate: String, endnydate: String): Seq[Logic02Rec] = ???

  /**
    * 赋值来源url
    * @param url
    */

  override def cutIP(url: String): Unit = {
    ip_source=url.split(":", -1)(1).substring(2)
  }
}
