package nirvana.hall.v70.internal.adapter.nj.service

import javax.persistence.EntityManager

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.TPCardService
import nirvana.hall.c.services.gfpt4lib.FPT4File.Logic02Rec
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.v70.internal.{CommonUtils, Gafis70Constants}
import nirvana.hall.v70.internal.adapter.nj.sync.ProtobufConverter
import nirvana.hall.v70.internal.adapter.nj.jpa._
import nirvana.hall.v70.services.sys.UserService
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
class TPCardServiceImpl(entityManager: EntityManager, userService: UserService) extends TPCardService with LoggerSupport{
  /**
   * 获取捺印卡信息
   * @param personId
   * @return
   */
  override def getTPCard(personId: String, dbid: Option[String]): TPCard = {
    val person = GafisPerson.find(personId)
    val photoList = GafisGatherPortrait.find_by_personid(personId).toSeq
    val fingerList = GafisGatherFinger.find_by_personId(personId).toSeq
    val palmList = GafisGatherPalm.find_by_personId(personId).toSeq
    val otherList = GafisPersonOther.find_by_personId(personId).toSeq

    ProtobufConverter.convertGafisPerson2TPCard(person, photoList, fingerList, palmList, otherList)
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
      person.deletag = Gafis70Constants.DELETAG_USE
      person.dataSources =  Gafis70Constants.DATA_SOURCE_HALLSYNC.toLong
      person.gatherTypeId = Gafis70Constants.NJ_GATHER_TYPE_ID_DEFAULT
      person.status = Gafis70Constants.NJ_Status
      person.schedule = Gafis70Constants.NJ_Schedule
      person.approval = Gafis70Constants.NJ_Approval
      person.gatherFingerMode = Gafis70Constants.NJ_GatherFingerMode
      person.gatherFingerNum = Gafis70Constants.NJ_GatherFingerNum.toLong
      person.cityCode = if(person.gatherdepartcode.length > 6)
        person.gatherdepartcode.substring(0,5) else person.gatherdepartcode

      person.save()
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
      //保存指纹
      val fingerList = ProtobufConverter.convertTPCard2GafisGatherFinger(tpCard)
      GafisGatherFinger.delete.where(GafisGatherFinger.personId === person.personid)
      fingerList.foreach{finger =>
        finger.pkId = CommonUtils.getUUID()
        finger.inputtime = person.inputtime
        finger.inputpsn = person.inputUsername
        finger.save()
      }
      //掌纹
      val palmList = ProtobufConverter.convertTPCard2GafisGatherPalm(tpCard)
      GafisGatherPalm.delete.where(GafisGatherPalm.personId === person.personid)
      palmList.foreach{palm=>
        palm.pkId = CommonUtils.getUUID()
        palm.inputtime = person.inputtime
        palm.inputpsn = person.inputUsername
        palm.save()
      }
      //保存人像
      val portraitList = ProtobufConverter.convertTPCard2GafisGatherPortrait(tpCard)
      GafisGatherPortrait.delete.where(GafisGatherPortrait.personId === person.personid)
      portraitList.foreach{ portrait =>
        portrait.pkId = CommonUtils.getUUID()
        portrait.inputpsn = person.inputUsername
        portrait.inputtime = person.inputtime
        portrait.deletag = Gafis70Constants.DELETAG_USE
        portrait.save()
      }
      //其他BLOB
      val otherList = ProtobufConverter.convertTPCard2GafisPersonOther(tpCard)
      GafisPersonOther.delete.where(GafisPersonOther.personId === person.personid)
      otherList.foreach{other=>
        other.uuid = CommonUtils.getUUID()
        other.inputtime = person.inputtime
        other.inputpsn = person.inputUsername
        other.inputtime = person.modifiedtime
        other.inputpsn = person.modifyUsername
        other.save()
      }
    }
  }

  /**
   * 删除捺印卡片, 更新删除状态
   * @param cardId
   * @return
   */
  override def delTPCard(cardId: String, dbId: Option[String]): Unit = {
    val gafisPerson = GafisPerson.find(cardId)
    gafisPerson.deletag = Gafis70Constants.DELETAG_DEL
    gafisPerson.save()
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
    val sid = java.lang.Long.parseLong(entityManager.createNativeQuery("select gafis_person_sid_seq.nextval from dual").getResultList.get(0).toString)
    person.sid = sid
    person.deletag = Gafis70Constants.DELETAG_USE
    person.dataSources =  Gafis70Constants.DATA_SOURCE_HALLSYNC.toLong
    person.gatherTypeId = Gafis70Constants.NJ_GATHER_TYPE_ID_DEFAULT
    person.status = Gafis70Constants.NJ_Status
    person.schedule = Gafis70Constants.NJ_Schedule
    person.approval = Gafis70Constants.NJ_Approval
    person.gatherFingerMode = Gafis70Constants.NJ_GatherFingerMode
    person.gatherFingerNum = Gafis70Constants.NJ_GatherFingerNum.toLong
    person.cityCode = if(person.gatherdepartcode.length > 6)
      person.gatherdepartcode.substring(0,5) else person.gatherdepartcode

    GafisPerson.delete.where(GafisPerson.personid === tpCard.getStrPersonID)
    person.save()
    //删除原来的逻辑库
    GafisLogicDbFingerprint.delete.where(GafisLogicDbFingerprint.fingerprintPkid === person.personid)
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
    //指纹
    val fingerList = ProtobufConverter.convertTPCard2GafisGatherFinger(tpCard)
    GafisGatherFinger.delete.where(GafisGatherFinger.personId === person.personid)
    fingerList.foreach{finger =>
      finger.pkId = CommonUtils.getUUID()
      finger.inputtime = person.inputtime
      finger.inputpsn = person.inputUsername
      finger.save()
    }
    //掌纹
    val palmList = ProtobufConverter.convertTPCard2GafisGatherPalm(tpCard)
    GafisGatherPalm.delete.where(GafisGatherPalm.personId === person.personid)
    palmList.foreach{palm=>
      palm.pkId = CommonUtils.getUUID()
      palm.inputtime = person.inputtime
      palm.inputpsn = person.inputUsername
      palm.save()
    }

    //人像
    val portraitList = ProtobufConverter.convertTPCard2GafisGatherPortrait(tpCard)
    GafisGatherPortrait.delete.where(GafisGatherPortrait.personId === person.personid)
    portraitList.foreach{portrait=>
      portrait.pkId = CommonUtils.getUUID()
      portrait.inputpsn = person.inputUsername
      portrait.inputtime = person.inputtime
      portrait.deletag = Gafis70Constants.DELETAG_USE
      portrait.save()
    }

    //其他BLOB
    val otherList = ProtobufConverter.convertTPCard2GafisPersonOther(tpCard)
    GafisPersonOther.delete.where(GafisPersonOther.personId === person.personid)
    otherList.foreach{other=>
      other.uuid = CommonUtils.getUUID()
      other.inputtime = person.inputtime
      other.inputpsn = person.inputUsername
      other.inputtime = person.modifiedtime
      other.inputpsn = person.modifyUsername
      other.save()
    }
}

  /**
   * 验证卡号是否已存在
   * @param cardId
   * @return
   */
  override def isExist(cardId: String, dbId: Option[String]): Boolean = {
    GafisPerson.findOption(cardId).nonEmpty
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
    * 针对海鑫综采对接使用
    * 7.0实现该方法
    * @param tpCard
    * @param dbId
    */
  override def addTPCardHXZC(tpCard: TPCard, dbId: Option[String]): Unit = ???
}
