package nirvana.hall.v70.internal.adapter.gz.services.versionfpt5


import java.util.{Date, UUID}
import javax.persistence.EntityManager

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import nirvana.hall.api.services.TPCardService
import nirvana.hall.c.services.gfpt4lib.FPT4File.Logic02Rec
import nirvana.hall.c.services.gfpt5lib.fpt5util
import nirvana.hall.protocol.api.FPTProto._
import nirvana.hall.v70.common.jpa.SysUser
import nirvana.hall.v70.internal.adapter.gz.jpa._
import nirvana.hall.v70.internal.adapter.gz.sync._
import nirvana.hall.v70.internal.{CommonUtils, Gafis70Constants}
import nirvana.hall.v70.services.sys.UserService

/**
  * Created by songpeng on 2017/5/26.
  */
class TPCardServiceImpl(entityManager: EntityManager, userService: UserService) extends TPCardService with LoggerSupport {
  /**
    * 新增捺印卡片
    *
    * @param tpCard
    * @return
    */
  override def addTPCard(tpCard: TPCard, dbId: Option[String]): Unit = {
    info("addTPCard cardId:{}", tpCard.getStrCardID)

    if(isExist(tpCard.getStrCardID)){
      throw new RuntimeException("记录已存在")
    }else{
      //保存人员基本信息
      val person = ProtobufConverterForFPT5.convertTPCard2GafisPerson(tpCard)
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
      person.dataSources = "3"
      person.gatherTypeId = Gafis70Constants.GATHER_TYPE_ID_DEFAULT
      person.save

      //保存人员证件信息
      if(tpCard.getText.getStrCertifType.nonEmpty && tpCard.getText.getStrCertifType != fpt5util.DEFAULT_CERTIFICATE_TYPE){
        val personCertificate = new GafisGatherCertificate()
        personCertificate.personId = tpCard.getStrCardID
        personCertificate.certificateType = tpCard.getText.getStrCertifType
        personCertificate.certificateId = tpCard.getText.getStrCertifID
        personCertificate.inputpsn = user.get.pkId
        personCertificate.inputtime = new Date()
        personCertificate.deletag = Gafis70Constants.DELETAG_USE
        personCertificate.pkId = UUID.randomUUID().toString.replace("-","")
        personCertificate.save()
      }

      //保存指纹--包括平指、滚指、指节纹
      val fingerList = ProtobufConverterForFPT5.convertTPCard2GafisGatherFinger(tpCard)
      GafisGatherFinger.find_by_personId(person.personid).foreach(f=> f.delete())
      fingerList.foreach{finger =>
        finger.pkId = CommonUtils.getUUID()
        finger.inputtime = new Date()
        finger.inputpsn = Gafis70Constants.INPUTPSN
        finger.save()
      }


      //掌纹--包括掌心、四联指、全掌
      val palmList = ProtobufConverterForFPT5.convertTPCard2GafisGatherPalm(tpCard)
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
    val palmList = GafisGatherPalm.find_by_personId(personId).toSeq

    convertGafisPerson2TPCard(person, photoList, fingerList, palmList)
  }

  def convertGafisPerson2TPCard(person: GafisPerson, photoList: Seq[GafisGatherPortrait], fingerList: Seq[GafisGatherFinger], palmList: Seq[GafisGatherPalm]): TPCard = {
    val tpCard = TPCard.newBuilder()
    tpCard.setStrCardID(person.personid)
    tpCard.setStrMisPersonID(person.personid)
    tpCard.setStrJingZongPersonId("")
    tpCard.setStrCasePersonID("")
    tpCard.setCaptureInfoReasonCode("")

    //文本信息
    val textBuilder = tpCard.getTextBuilder
    magicSet(person.name, textBuilder.setStrName)
    magicSet(person.aliasname, textBuilder.setStrAliasName)
    if (isNonBlank(person.sexCode))
      textBuilder.setNSex(Integer.parseInt(person.sexCode))
    magicSet(person.idcardno, textBuilder.setStrIdentityNum)
    textBuilder.setStrBirthDate(person.birthdayst)
    magicSet(person.birthCode, textBuilder.setStrBirthAddrCode)
    magicSet(person.birthdetail, textBuilder.setStrBirthAddr)
    magicSet(person.door, textBuilder.setStrHuKouPlaceCode)
    magicSet(person.doordetail, textBuilder.setStrHuKouPlaceTail)
    magicSet(person.nationCode, textBuilder.setStrRace)
    magicSet(person.nativeplaceCode, textBuilder.setStrNation)
    magicSet(person.caseClasses, textBuilder.setStrCaseType1)
    //    magicSet(person.caseClasses2, textBuilder.setStrCaseType2)
    //    magicSet(person.caseClasses3, textBuilder.setStrCaseType3)
    magicSet(person.address, textBuilder.setStrAddrCode)
    magicSet(person.addressdetail, textBuilder.setStrAddr)
    //    magicSet(person.personType, textBuilder.setStrPersonType)

    magicSet(person.gatherdepartcode, textBuilder.setStrPrintUnitCode)
    magicSet(person.gatherdepartname, textBuilder.setStrPrintUnitName)
    magicSet(person.gatherusername, textBuilder.setStrPrinter)
    textBuilder.setStrPrintDate("")
    magicSet(person.remark, textBuilder.setStrComment)
    magicSet(person.idcardno,textBuilder.setStrCertifID)
    magicSet(person.chopPersonIdCard,textBuilder.setStrPrinterIdCardNo)
    magicSet(person.chopPersonTel,textBuilder.setStrPrinterPhone)
    //协查信息
    textBuilder.setNXieChaFlag(person.assistSign)
    textBuilder.setNXieChaLevel(person.assistLevel)
    magicSet(person.assistBonus, textBuilder.setStrPremium)
    magicSet(person.assistDate, textBuilder.setStrXieChaDate)
    magicSet(person.assistDeptCode, textBuilder.setStrXieChaRequestUnitCode)
    magicSet(person.assistDeptName, textBuilder.setStrXieChaRequestUnitName)
    magicSet(person.assistPurpose, textBuilder.setStrXieChaForWhat)
    magicSet(person.assistRefPerson, textBuilder.setStrRelPersonNo)
    magicSet(person.assistRefCase, textBuilder.setStrRelCaseNo)
    magicSet(person.assistValidDate, textBuilder.setStrXieChaTimeLimit)
    magicSet(person.assistContacts, textBuilder.setStrXieChaContacter)
    magicSet(person.assistNumber, textBuilder.setStrXieChaTelNo)
    magicSet(person.assistApproval, textBuilder.setStrShenPiBy)

    //指纹数据
    val mntMap = fingerList.filter(_.groupId == 0).map(f => ((f.fgpCase, f.fgp), f.gatherData)).toMap[(String, Short), Array[Byte]]
    //纹线特征数据
    val binMap = fingerList.filter(_.groupId == 4).map(f => ((f.fgpCase, f.fgp), f.gatherData)).toMap[(String, Short), Array[Byte]]
    fingerList.filter(_.groupId == 1).foreach { finger =>
      val blobBuilder = tpCard.addBlobBuilder()
      val mnt = mntMap.get((finger.fgpCase, finger.fgp))
      val bin = binMap.get((finger.fgpCase, finger.fgp))
      mnt.foreach { blob =>
        blobBuilder.setStMntBytes(ByteString.copyFrom(blob))
      }
      bin.foreach {
        blob => blobBuilder.setStBinBytes(ByteString.copyFrom(blob))
      }
      blobBuilder.setStImageBytes(ByteString.copyFrom(finger.gatherData))
      if(finger.fgpCase == Gafis70Constants.FGP_CASE_KNUCKLE_PRINTS){
        blobBuilder.setType(ImageType.IMAGETYPE_KNUCKLEPRINTS)
      }else{
        blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
        blobBuilder.setBPlain("1".equals(finger.fgpCase))
      }
      blobBuilder.setFgp(FingerFgp.valueOf(finger.fgp))
    }

    //人像数据
    photoList.foreach { photo =>
      val blobBuilder = tpCard.addBlobBuilder()
      blobBuilder.setStImageBytes(ByteString.copyFrom(photo.gatherData))
      blobBuilder.setType(ImageType.IMAGETYPE_FACE)
      photo.fgp match {
        case Gafis70Constants.FACE_FRONT => blobBuilder.setFacefgp(FaceFgp.FACE_FRONT)
        case Gafis70Constants.FACE_RIGHT => blobBuilder.setFacefgp(FaceFgp.FACE_RIGHT)
        case Gafis70Constants.FACE_LEFT => blobBuilder.setFacefgp(FaceFgp.FACE_LEFT)
      }
    }

    //掌纹数据
    if (palmList != null) {
      //指纹数据
      val mntMap = palmList.filter(_.groupId == 0).map(f => (f.fgp, f.gatherData)).toMap[(Short), Array[Byte]]
      palmList.filter(_.groupId == 1)foreach { palm =>
        val blobBuilder = tpCard.addBlobBuilder()
        blobBuilder.setStImageBytes(ByteString.copyFrom(palm.gatherData))
        val mnt = mntMap.get(palm.fgp)
        mnt.foreach { blob =>
          blobBuilder.setStMntBytes(ByteString.copyFrom(blob))
        }
        palm.fgp match {
          case 11 =>
            blobBuilder.setType(ImageType.IMAGETYPE_PALM)
            blobBuilder.setPalmfgp(PalmFgp.PALM_RIGHT)
          case 12 =>
            blobBuilder.setType(ImageType.IMAGETYPE_PALM)
            blobBuilder.setPalmfgp(PalmFgp.PALM_LEFT)
          case 13 =>
            blobBuilder.setType(ImageType.IMAGETYPE_FOURPRINT)
            blobBuilder.setPalmfgp(PalmFgp.PALM_FOUR_PRINT_RIGHT)
          case 14 =>
            blobBuilder.setType(ImageType.IMAGETYPE_FOURPRINT)
            blobBuilder.setPalmfgp(PalmFgp.PALM_FOUR_PRINT_LEFT)
          case 15 =>
            blobBuilder.setType(ImageType.IMAGETYPE_FULLPALM)
            blobBuilder.setPalmfgp(PalmFgp.PALM_FULL_PALM_RIGHT)
          case 16 =>
            blobBuilder.setType(ImageType.IMAGETYPE_FULLPALM)
            blobBuilder.setPalmfgp(PalmFgp.PALM_FULL_PALM_LEFT)
          case _ =>
            val blobBuilder = tpCard.addBlobBuilder()
            blobBuilder.setPalmfgp(PalmFgp.PALM_UNKNOWN)
        }
      }
    }

    tpCard.build()
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