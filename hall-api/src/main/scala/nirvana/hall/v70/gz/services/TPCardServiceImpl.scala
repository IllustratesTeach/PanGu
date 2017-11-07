package nirvana.hall.v70.gz.services

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import nirvana.hall.api.services.TPCardService
import nirvana.hall.c.services.gfpt4lib.FPT4File.Logic02Rec
import nirvana.hall.protocol.api.FPTProto._
import nirvana.hall.v70.gz.jpa.GafisPerson
import nirvana.hall.v70.internal.Gafis70Constants
import nirvana.hall.v70.internal.sync._
import nirvana.hall.v70.gz.jpa.{GafisGatherFinger, GafisGatherPalm, GafisGatherPortrait}

/**
  * Created by songpeng on 2017/5/26.
  */
class TPCardServiceImpl extends TPCardService with LoggerSupport {
  /**
    * 新增捺印卡片
    *
    * @param tPCard
    * @return
    */
  override def addTPCard(tPCard: TPCard, dbId: Option[String]): Unit = ???

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

    convertGafisPerson2TPCard(person, photoList, fingerList, null)
  }

  def convertGafisPerson2TPCard(person: GafisPerson, photoList: Seq[GafisGatherPortrait], fingerList: Seq[GafisGatherFinger], palmList: Seq[GafisGatherPalm]): TPCard = {
    val tpCard = TPCard.newBuilder()
    tpCard.setStrCardID(person.personid)
    tpCard.setStrPersonID(person.personid)

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
    textBuilder.setStrPrintDate(person.gatherDate)
    magicSet(person.remark, textBuilder.setStrComment)

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
      blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
      blobBuilder.setBPlain("1".equals(finger.fgpCase))
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
      palmList.foreach { palm =>
        val blobBuilder = tpCard.addBlobBuilder()
        blobBuilder.setStImageBytes(ByteString.copyFrom(palm.gatherData))
        blobBuilder.setType(ImageType.IMAGETYPE_PALM)
        palm.fgp match {
          case 11 => blobBuilder.setPalmfgp(PalmFgp.PALM_RIGHT)
          case 12 => blobBuilder.setPalmfgp(PalmFgp.PALM_LEFT)
          case other => blobBuilder.setPalmfgp(PalmFgp.PALM_UNKNOWN)
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