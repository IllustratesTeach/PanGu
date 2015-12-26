package nirvana.hall.api.internal.sync

import com.google.protobuf.ByteString
import nirvana.hall.api.entities._
import nirvana.hall.protocol.v62.FPTProto.{FingerFgp, ImageType, TPCard}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.galoctpConverter
import scalikejdbc._

/**
 * Created by songpeng on 15/12/7.
 */
trait Sync62TPCardService {
  /**
   * 同步捺印卡到6.2
   * @param syncQueue
   * @param session
   * @return
   */
  def syncTPCard(facade: V62Facade, v62Config: HallV62Config, syncQueue: SyncQueue)(implicit session: DBSession): Unit = {
    val personId = syncQueue.uploadKeyid.get
    syncQueue.opration.get match {
      case "insert" =>
        addTPCard(facade, v62Config, personId)
      case "update" =>
        updateTPCard(facade, v62Config, personId)
      case "delete" =>
        deleteTPCard(facade, v62Config, personId)
    }
  }

  private def addTPCard(facade: V62Facade, v62Config: HallV62Config, personId: String)(implicit session: DBSession): Unit = {
    val tpCard = getTPCard(personId)
    //数据转换为C的结构
    val gTPCard = galoctpConverter.convertProtoBuf2GTPCARDINFOSTRUCT(tpCard)
    //上报
    facade.NET_GAFIS_FLIB_Add(v62Config.templateTable.dbId.toShort,
      v62Config.templateTable.tableId.toShort,
      personId,gTPCard)
  }

  private def updateTPCard(facade: V62Facade, v62Config: HallV62Config, personId: String)(implicit session: DBSession): Unit = {
    val tpCard = getTPCard(personId)
    val gTPCard = galoctpConverter.convertProtoBuf2GTPCARDINFOSTRUCT(tpCard)
    facade.NET_GAFIS_FLIB_Update(v62Config.templateTable.dbId.toShort,
      v62Config.templateTable.tableId.toShort, personId, gTPCard)
  }

  private def deleteTPCard(facade: V62Facade, v62Config: HallV62Config, personId: String)(implicit session: DBSession): Unit ={
    facade.NET_GAFIS_FLIB_Del(v62Config.templateTable.dbId.toShort,
      v62Config.templateTable.tableId.toShort, personId)
  }

  private def getTPCard(personId: String)(implicit session: DBSession): TPCard ={
    val person = GafisPerson.find(personId).get
    val tpCard = TPCard.newBuilder()
    tpCard.setStrCardID(personId)

    //文本信息
    val textBuilder = tpCard.getTextBuilder
    person.name.foreach(textBuilder.setStrName)
    person.aliasname.foreach(textBuilder.setStrAliasName)
    person.sexCode.foreach(f => textBuilder.setNSex(Integer.parseInt(f)))
    person.idcardno.foreach(textBuilder.setStrIdentityNum)
    person.birthdayst.foreach{ f =>
      textBuilder.setStrBirthDate(f)
    }
    person.birthCode.foreach(textBuilder.setStrBirthAddrCode)
    person.birthdetail.foreach(textBuilder.setStrBirthAddr)
    person.nationCode.foreach{f =>
      CodeMz.find(f).foreach(_.name.foreach(textBuilder.setStrRace))
    }
    person.nativeplaceCode.foreach{f =>
      CodeGj.find(f).foreach(_.name.foreach(textBuilder.setStrNation))
    }
    person.caseClasses.foreach(textBuilder.setStrCaseType1)
    person.caseClasses2.foreach(textBuilder.setStrCaseType2)
    person.caseClasses3.foreach(textBuilder.setStrCaseType3)
    person.address.foreach(textBuilder.setStrAddrCode)
    person.addressdetail.foreach(textBuilder.setStrAddr)
    person.personCategory.foreach(textBuilder.setStrPersonType)

    person.gatherdepartcode.foreach(textBuilder.setStrPrintUnitCode)
    person.gatherdepartname.foreach(textBuilder.setStrPrintUnitName)
    person.gatherusername.foreach(textBuilder.setStrPrinter)
    person.gatherDate.foreach(f => textBuilder.setStrPrintDate(f))
    person.remark.foreach(textBuilder.setStrComment)
    //协查信息
    person.assistSign.foreach(f => textBuilder.setNXieChaFlag(Integer.parseInt(f)))
    person.assistLevel.foreach(f => textBuilder.setNXieChaLevel(Integer.parseInt(f)))
    person.assistBonus.foreach(textBuilder.setStrPremium)
    person.assistDate.foreach(f => textBuilder.setStrXieChaDate(f))
    person.assistDeptCode.foreach(textBuilder.setStrXieChaRequestUnitCode)
    person.assistDeptName.foreach(textBuilder.setStrXieChaRequestUnitName)
    person.assistPurpose.foreach(textBuilder.setStrXieChaForWhat)
    person.assistRefPerson.foreach(textBuilder.setStrRelPersonNo)
    person.assistRefCase.foreach(textBuilder.setStrRelCaseNo)
    person.assistValidDate.foreach(textBuilder.setStrXieChaTimeLimit)
    person.assistContacts.foreach(textBuilder.setStrXieChaContacter)
    person.assistNumber.foreach(textBuilder.setStrXieChaTelNo)
    person.assistApproval.foreach(textBuilder.setStrShenPiBy)

    //指纹数据
    val fingers = findGafisGatherFingerListByPersonId(personId)
    val mntMap = fingers.filter(_.groupId.get ==0 ).map(f => ((f.fgpCase,f.fgp), f.gatherData)).toMap[(Option[String], Short), java.sql.Blob]
    fingers.filter(_.groupId.get == 1).foreach{ finger =>
      val blobBuilder = tpCard.addBlobBuilder()
      val mnt = mntMap.get((finger.fgpCase, finger.fgp))
      mnt.foreach{ blob =>
        blobBuilder.setStMntBytes(ByteString.readFrom(blob.getBinaryStream))
      }
      blobBuilder.setStImageBytes(ByteString.readFrom(finger.gatherData.getBinaryStream()))
      blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
      blobBuilder.setBPlain("1".equals(finger.fgpCase.get))
      blobBuilder.setFgp(FingerFgp.valueOf(finger.fgp))
    }
    tpCard.build()
  }

  private def findGafisGatherFingerListByPersonId(personId: String)(implicit session: DBSession): Seq[GafisGatherFinger] ={
    val ggf = GafisGatherFinger.syntax("ggf")
    withSQL {
      select.from(GafisGatherFinger as ggf).where.eq(ggf.personId, personId)
    }.map(GafisGatherFinger(ggf.resultName)).list().apply().toSeq
  }


}
