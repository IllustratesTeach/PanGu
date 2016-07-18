package nirvana.hall.v70.internal

import java.util.Date
import javax.persistence.EntityManager

import monad.support.services.LoggerSupport
import nirvana.hall.api.config.DBConfig
import nirvana.hall.api.services.TPCardService
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa._
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
  override def getTPCard(personId: String, dBConfig: DBConfig): TPCard = {
    val person = GafisPerson.find(personId)
    val photoList = GafisGatherPortrait.find_by_personid(personId).toSeq
    val fingerList = GafisGatherFinger.find_by_personId(personId).toSeq

    ProtobufConverter.convertGafisPerson2TPCard(person, photoList, fingerList, null)
  }

  /**
   * 新增捺印卡片
   * @param tpCard
   * @return
   */
  @Transactional
  override def addTPCard(tpCard: TPCard, dbConfig: DBConfig = null): Unit = {
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
      person.save()
      //保存逻辑库
      val logicDb: GafisLogicDb = if(dbConfig != null){
        GafisLogicDb.find(dbConfig.dbId.right.get)
      }else{
        //如果没有指定逻辑库，使用默认库
        GafisLogicDb.where(GafisLogicDb.logicCategory === "0").and(GafisLogicDb.logicIsdefaulttag === "1").headOption.get
      }
      val logicDbFingerprint = new GafisLogicDbFingerprint()
      logicDbFingerprint.pkId = CommonUtils.getUUID()
      logicDbFingerprint.fingerprintPkid = person.personid
      logicDbFingerprint.logicDbPkid = logicDb.pkId
      logicDbFingerprint.save()
      //保存指纹
      val fingerList = ProtobufConverter.convertTPCard2GafisGatherFinger(tpCard)
      fingerList.foreach{finger =>
        finger.pkId = CommonUtils.getUUID()
        finger.inputtime = new Date()
        finger.inputpsn = Gafis70Constants.INPUTPSN
        finger.save()
      }
      //掌纹
      val palmList = ProtobufConverter.convertTPCard2GafisGatherPalm(tpCard)
      GafisGatherPalm.find_by_personId(person.personid).foreach(f=> f.delete())
      palmList.foreach{palm=>
        palm.pkId = CommonUtils.getUUID()
        palm.inputtime = new Date()
        palm.inputpsn = Gafis70Constants.INPUTPSN
        palm.save()
      }
      //保存人像
      val portraitList = ProtobufConverter.convertTPCard2GafisGatherPortrait(tpCard)
      portraitList.foreach{ portrait =>
        portrait.pkId = CommonUtils.getUUID()
        portrait.inputpsn = Gafis70Constants.INPUTPSN
        portrait.inputtime = new Date()
        portrait.deletag = Gafis70Constants.DELETAG_USE
        portrait.save()
      }
      info("addTPCard cardId:{}", tpCard.getStrCardID)
    }
  }

  /**
   * 删除捺印卡片
   * @param cardId
   * @return
   */
  @Transactional
  override def delTPCard(cardId: String, dbConfig: DBConfig = null): Unit = {
    //删除指纹
    GafisGatherFinger.find_by_personId(cardId).foreach(f=> f.delete())
    //删除掌纹
    GafisGatherPalm.find_by_personId(cardId).foreach(f=> f.delete())
    //删除人像
    GafisGatherPortrait.find_by_personid(cardId).foreach(f=> f.delete())
    //删除逻辑库
    GafisLogicDbFingerprint.find_by_fingerprintPkid(cardId).foreach(_.delete())
    //删除人员信息
    GafisPerson.find(cardId).delete()
  }

  /**
   * 更新捺印卡片
   * @param tpCard
   * @return
   */
  @Transactional
  override def updateTPCard(tpCard: TPCard, dBConfig: DBConfig): Unit ={
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
    person.save()

    //指纹
    val fingerList = ProtobufConverter.convertTPCard2GafisGatherFinger(tpCard)
    GafisGatherFinger.find_by_personId(person.personid).foreach(f=> f.delete())
    fingerList.foreach{finger =>
      finger.pkId = CommonUtils.getUUID()
      finger.inputtime = new Date()
      finger.inputpsn = Gafis70Constants.INPUTPSN
      finger.save()
    }
    //掌纹
    val palmList = ProtobufConverter.convertTPCard2GafisGatherPalm(tpCard)
    GafisGatherPalm.find_by_personId(person.personid).foreach(f=> f.delete())
    palmList.foreach{palm=>
      palm.pkId = CommonUtils.getUUID()
      palm.inputtime = new Date()
      palm.inputpsn = Gafis70Constants.INPUTPSN
      palm.save()
    }

    //人像
    val portraitList = ProtobufConverter.convertTPCard2GafisGatherPortrait(tpCard)
    GafisGatherPortrait.find_by_personid(person.personid).foreach(f=> f.delete())
    portraitList.foreach{portrait=>
      portrait.pkId = CommonUtils.getUUID()
      portrait.inputtime = new Date()
      portrait.inputpsn = Gafis70Constants.INPUTPSN
      portrait.deletag = Gafis70Constants.DELETAG_USE
      portrait.save()
    }
    info("updateTPCard cardId:{}", tpCard.getStrCardID)
  }

  /**
   * 验证卡号是否已存在
   * @param cardId
   * @return
   */
  override def isExist(cardId: String, dbConfig: DBConfig = null): Boolean = {
    GafisPerson.findOption(cardId).nonEmpty
  }
}
