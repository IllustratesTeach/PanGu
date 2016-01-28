package nirvana.hall.v70.internal.sync

import com.google.protobuf.ByteString
import nirvana.hall.orm.services.Relation
import nirvana.hall.protocol.v62.FPTProto.{FingerFgp, ImageType, TPCard}
import nirvana.hall.protocol.v62.tp.TPCardProto._
import nirvana.hall.v70.jpa.{CodeGj, GafisGatherFinger, GafisPerson, SyncQueue}

/**
 * Created by songpeng on 15/12/7.
 */
trait Sync7to6TPCardService {
  /**
   * 同步捺印卡到6.2
   * @param syncQueue
   * @return
   */
  def syncTPCard(syncQueue: SyncQueue): Unit = {
    syncQueue.opration match {
      case "insert" =>
        addTPCard(syncQueue)
      case "update" =>
        updateTPCard(syncQueue)
      case "delete" =>
        deleteTPCard(syncQueue)
    }
  }

  private def addTPCard(syncQueue: SyncQueue): Unit = {
    val tpCard = getTPCard(syncQueue.uploadKeyid)
    val request = TPCardAddRequest.newBuilder().setCard(tpCard).build()

    httpCall(syncQueue.targetIp, syncQueue.targetPort,TPCardAddRequest.cmd, request, TPCardAddResponse.newBuilder())
  }

  private def updateTPCard(syncQueue: SyncQueue): Unit = {
    val tpCard = getTPCard(syncQueue.uploadKeyid)
    val request = TPCardUpdateRequest.newBuilder().setCard(tpCard).build()

    httpCall(syncQueue.targetIp, syncQueue.targetPort, TPCardUpdateRequest.cmd, request, TPCardUpdateResponse.newBuilder())
  }

  private def deleteTPCard(syncQueue: SyncQueue): Unit = {
    val request = TPCardDelRequest.newBuilder().setCardId(syncQueue.uploadKeyid).build()

    httpCall(syncQueue.targetIp, syncQueue.targetPort, TPCardDelRequest.cmd, request,TPCardDelResponse.newBuilder())
  }

  private def getTPCard(personId: String): TPCard = {
    val person = GafisPerson.find(personId)
    val tpCard = TPCard.newBuilder()
    tpCard.setStrCardID(personId)

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
    magicSet(person.nationCode, textBuilder.setStrRace)
    if(isNonBlank(person.nativeplaceCode))
      textBuilder.setStrNation(CodeGj.find(person.nativeplaceCode).name)
    magicSet(person.caseClasses, textBuilder.setStrCaseType1)
    magicSet(person.caseClasses2, textBuilder.setStrCaseType2)
    magicSet(person.caseClasses3, textBuilder.setStrCaseType3)
    magicSet(person.address, textBuilder.setStrAddrCode)
    magicSet(person.addressdetail, textBuilder.setStrAddr)
    magicSet(person.personCategory, textBuilder.setStrPersonType)

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
    val fingers = findGafisGatherFingerListByPersonId(personId)
    val mntMap = fingers.filter(_.groupId == 0).map(f => ((f.fgpCase, f.fgp), f.gatherData)).toMap[(String, Short), Array[Byte]]
    fingers.filter(_.groupId == 1).foreach { finger =>
      val blobBuilder = tpCard.addBlobBuilder()
      val mnt = mntMap.get((finger.fgpCase, finger.fgp))
      mnt.foreach { blob =>
        blobBuilder.setStMntBytes(ByteString.copyFrom(blob))
      }
      blobBuilder.setStImageBytes(ByteString.copyFrom(finger.gatherData))
      blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
      blobBuilder.setBPlain("1".equals(finger.fgpCase))
      blobBuilder.setFgp(FingerFgp.valueOf(finger.fgp))
    }
    tpCard.build()
  }

  private def findGafisGatherFingerListByPersonId(personId: String): Relation[GafisGatherFinger] = {
    GafisGatherFinger.find_by_personId(personId)
    /*
    val ggf = GafisGatherFinger.syntax("ggf")
    withSQL {
      select.from(GafisGatherFinger as ggf).where.eq(ggf.personId, personId)
    }.map(GafisGatherFinger(ggf.resultName)).list().apply().toSeq
    */
  }


}
