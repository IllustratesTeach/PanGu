package nirvana.hall.v70.internal

import java.util.Date
import javax.persistence.EntityManager

import nirvana.hall.api.config.DBConfig
import nirvana.hall.api.services.TPCardService
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa._
import org.springframework.beans.BeanUtils
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
class TPCardServiceImpl(entityManager: EntityManager) extends TPCardService{
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
      person.inputpsn = getSysUserPkIdByLoginName(person.inputpsn)
      person.modifiedpsn = getSysUserPkIdByLoginName(person.modifiedpsn)
      //根据用户信息获取单位信息
      if(person.inputpsn != null){
        val sysUser = SysUser.find(person.inputpsn)
        person.gatherOrgCode = sysUser.departCode
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
      //保存人像
      val portraitList = ProtobufConverter.convertTPCard2GafisGatherPortrait(tpCard)
      portraitList.foreach{ portrait =>
        portrait.pkId = CommonUtils.getUUID()
        portrait.inputpsn = Gafis70Constants.INPUTPSN
        portrait.inputtime = new Date()
        portrait.save()
      }
    }
  }

  /**
   * 根据用户名获取用户id，如果没有找到返回默认用户
   * @param loginName
   * @return
   */
  private def getSysUserPkIdByLoginName(loginName: String): String ={
    if(loginName != null && loginName.nonEmpty){
      val user = SysUser.find_by_loginName(loginName).headOption
      if(user.nonEmpty){
         return user.get.pkId
      }
    }
//    Gafis70Constants.INPUTPSN
    //TODO 目前对应不上的用户设置为空,等以后处理
    null
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
    val personNew  = ProtobufConverter.convertTPCard2GafisPerson(tpCard)
    val fingerList = ProtobufConverter.convertTPCard2GafisGatherFinger(tpCard)


    val person = GafisPerson.find(tpCard.getStrCardID)
    BeanUtils.copyProperties(personNew, person)
    //用户名获取用户ID
    person.inputpsn = getSysUserPkIdByLoginName(personNew.inputpsn)
    //根据用户信息获取单位信息
    if(person.inputpsn != null){
      val sysUser = SysUser.find(person.inputpsn)
      person.gatherOrgCode = sysUser.departCode
    }
    person.modifiedpsn = getSysUserPkIdByLoginName(personNew.modifiedpsn)
    person.inputtime = personNew.inputtime
    person.modifiedtime = personNew.modifiedtime
    person.deletag = Gafis70Constants.DELETAG_USE
    person.save()

    //删除指纹
    GafisGatherFinger.find_by_personId(person.personid).foreach(f=> f.delete())

    fingerList.foreach{finger =>
      finger.pkId = CommonUtils.getUUID()
      finger.inputtime = new Date()
      finger.inputpsn = Gafis70Constants.INPUTPSN
      finger.save()
    }

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
