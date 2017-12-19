package nirvana.hall.v70.gz.services

import java.util.Date
import javax.persistence.EntityManager
import monad.support.services.LoggerSupport
import nirvana.hall.api.services.TPCardService
import nirvana.hall.c.services.gfpt4lib.FPT4File.Logic02Rec
import nirvana.hall.protocol.api.FPTProto._
import nirvana.hall.v70.gz.jpa._
import nirvana.hall.v70.gz.sync.ProtobufConverter
import nirvana.hall.v70.internal.{CommonUtils, Gafis70Constants}
import nirvana.hall.v70.gz.sys.UserService

/**
  * Created by songpeng on 2017/5/26.
  */
class TPCardServiceImpl(entityManager: EntityManager, userService: UserService) extends TPCardService with LoggerSupport {
  /**
    * 新增捺印卡片
    *
    * @param tPCard
    * @return
    */
  override def addTPCard(tPCard: TPCard, dbId: Option[String]): Unit = {
    info("addTPCard cardId:{}", tPCard.getStrCardID)
    //验证卡号是否已经存在
    if(isExist(tPCard.getStrCardID)){
      throw new RuntimeException("记录已存在")
    }else{
      //保存人员基本信息
      val person = ProtobufConverter.convertTPCard2GafisPerson(tPCard)
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
      //person.dataSources = Gafis70Constants.DATA_SOURCE_GAFIS6
      person.dataSources = Gafis70Constants.DATA_SOURCE_FPT
      person.gatherTypeId = Gafis70Constants.GATHER_TYPE_ID_DEFAULT
      person.save()
      //保存指纹
      val fingerList = ProtobufConverter.convertTPCard2GafisGatherFinger(tPCard)
      GafisGatherFinger.find_by_personId(person.personid).foreach(f=> f.delete())
      fingerList.foreach{finger =>
        finger.pkId = CommonUtils.getUUID()
        finger.inputtime = new Date()
        finger.inputpsn = Gafis70Constants.INPUTPSN
        finger.save()
      }
      //掌纹
      val palmList = ProtobufConverter.convertTPCard2GafisGatherPalm(tPCard)
      GafisGatherPalm.find_by_personId(person.personid).foreach(f=> f.delete())
      palmList.foreach{palm=>
        palm.pkId = CommonUtils.getUUID()
        palm.inputtime = new Date()
        palm.inputpsn = Gafis70Constants.INPUTPSN
        palm.save()
      }
      //保存人像
      val portraitList = ProtobufConverter.convertTPCard2GafisGatherPortrait(tPCard)
      portraitList.foreach{ portrait =>
        portrait.pkId = CommonUtils.getUUID()
        portrait.inputpsn = Gafis70Constants.INPUTPSN
        portrait.inputtime = new Date()
        portrait.deletag = Gafis70Constants.DELETAG_USE
        portrait.save()
      }
    }
  }

  /**
    * 删除捺印卡片
    *
    * @param cardId
    * @return
    */
  override def delTPCard(cardId: String, dbId: Option[String]): Unit = ???

  /**
    * 更新捺印卡片
    *
    * @param tpCard
    * @return
    */
  override def updateTPCard(tpCard: TPCard, dbId: Option[String]): Unit = ???

  /**
    * 验证卡号是否已存在
    *
    * @param cardId
    * @return
    */
  override def isExist(cardId: String, dbId: Option[String]): Boolean = {
    GafisPerson.findOption(cardId).nonEmpty
  }

  /**
    * 获取捺印卡信息
    *
    * @param personId
    * @param dbid
    * @return
    */
  override def getTPCard(personId: String, dbid: Option[String]): TPCard = {
    val person = GafisPerson.find(personId)
    val photoList = GafisGatherPortrait.find_by_personid(personId).toSeq
    val fingerList = GafisGatherFinger.find_by_personId(personId).toSeq
    //    val palmList = GafisGatherPalm.find_by_personId(personId).toSeq

    ProtobufConverter.convertGafisPerson2TPCard(person, photoList, fingerList, null)
  }

  /**
    * 查询捺印文本信息
    *
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
    *
    * @param tpCard
    * @param dbId
    */
  override def addTPCardHXZC(tpCard: TPCard, dbId: Option[String]): Unit = ???


}