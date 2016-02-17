package nirvana.hall.v70.internal.sync

import com.google.protobuf.ByteString
import nirvana.hall.orm.services.Relation
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask.LatentMatchData
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.protocol.v62.FPTProto._
import nirvana.hall.v62.internal.c.gloclib.galoctp
import nirvana.hall.v70.jpa._
import org.jboss.netty.buffer.ChannelBuffers

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/1/28.
 */
object ProtobufConverter {

  /**
   * 案件信息转换
   * @param caseInfo
   * @return
   */
  def convertCase2GafisCase(caseInfo: Case): GafisCase = {
    val gafisCase = new GafisCase()
    gafisCase.caseId = caseInfo.getStrCaseID
    gafisCase.cardId = caseInfo.getStrCaseID
    val text = caseInfo.getText
    gafisCase.caseClassCode = text.getStrCaseType1
    gafisCase.suspiciousAreaCode = text.getStrSuspArea1Code
    gafisCase.caseOccurDate = text.getStrCaseOccurDate
    gafisCase.caseOccurPlaceCode = text.getStrCaseOccurPlaceCode
    gafisCase.caseOccurPlaceDetail = text.getStrCaseOccurPlace
    gafisCase.assistLevel = text.getNSuperviseLevel.toString
    gafisCase.extractUnitCode = text.getStrExtractUnitCode
    gafisCase.extractUnitName = text.getStrExtractUnitName
    gafisCase.extractor = text.getStrExtractor
    gafisCase.extractDate = text.getStrExtractDate
    gafisCase.amount = text.getStrMoneyLost
    gafisCase.isMurder = if(text.getBPersonKilled) "1" else "0"
    gafisCase.remark = text.getStrComment
    gafisCase.caseState = text.getNCaseState.toString
    gafisCase.assistBonus = text.getStrPremium
    gafisCase.assistSign = text.getNXieChaState.toString
    gafisCase.assistRevokeSign = text.getNCancelFlag.toString
    gafisCase.assistDeptCode = text.getStrXieChaRequestUnitCode
    gafisCase.assistDeptName = text.getStrXieChaRequestUnitName

    gafisCase
  }
  def convertGafisCase2Case(caseInfo: GafisCase, fingerIds: Seq[String] = null): Case = {
    val caseBuilder = Case.newBuilder()
    caseBuilder.setStrCaseID(caseInfo.caseId)

    val textBuilder = caseBuilder.getTextBuilder
    magicSet(caseInfo.caseClassCode, textBuilder.setStrCaseType1)
    magicSet(caseInfo.caseOccurDate, textBuilder.setStrCaseOccurDate)
    magicSet(caseInfo.caseOccurPlaceCode, textBuilder.setStrCaseOccurPlaceCode)
    magicSet(caseInfo.caseOccurPlaceDetail, textBuilder.setStrCaseOccurPlace)

    magicSet(caseInfo.remark, textBuilder.setStrComment)
    if("1".equals(caseInfo.isMurder))
      textBuilder.setBPersonKilled(true)
    magicSet(caseInfo.amount, textBuilder.setStrMoneyLost)
    magicSet(caseInfo.extractUnitCode, textBuilder.setStrExtractUnitCode)
    magicSet(caseInfo.extractUnitName, textBuilder.setStrExtractUnitName)
    magicSet(caseInfo.extractDate, textBuilder.setStrExtractDate)
    magicSet(caseInfo.extractor, textBuilder.setStrExtractor)
    magicSet(caseInfo.suspiciousAreaCode, textBuilder.setStrSuspArea1Code)
    textBuilder.setNSuperviseLevel(caseInfo.assistLevel)
    magicSet(caseInfo.assistDeptCode, textBuilder.setStrXieChaRequestUnitCode)
    magicSet(caseInfo.assistDeptName, textBuilder.setStrXieChaRequestUnitName)
    magicSet(caseInfo.assistDate, textBuilder.setStrXieChaDate)
    magicSet(caseInfo.assistBonus, textBuilder.setStrPremium)
    textBuilder.setNCaseState(caseInfo.assistSign)
    textBuilder.setNCancelFlag(caseInfo.assistRevokeSign)
    textBuilder.setNCaseState(caseInfo.caseState)

    if(fingerIds != null)
      fingerIds.foreach(f => caseBuilder.addStrFingerID(f))
    caseBuilder.setNCaseFingerCount(caseBuilder.getStrFingerIDCount)

    caseBuilder.build()
  }

  def convertLPCard2GafisCaseFinger(lpCard: LPCard): GafisCaseFinger = {
    val caseFinger = new GafisCaseFinger()
    caseFinger.fingerId = lpCard.getStrCardID
    val text = lpCard.getText
    caseFinger.seqNo = text.getStrSeq
    caseFinger.remainPlace = text.getStrRemainPlace
    caseFinger.ridgeColor = text.getStrRidgeColor
    caseFinger.isCorpse = if(text.getBDeadBody) "1" else "0"
    caseFinger.corpseNo = text.getStrDeadPersonNo
    caseFinger.isAssist = text.getNXieChaState.toString
    caseFinger.matchStatus = text.getNBiDuiState.toString
    caseFinger.mittensBegNo = text.getStrStart
    caseFinger.mittensEndNo = text.getStrEnd

    val blob = lpCard.getBlob
    caseFinger.fingerImg = blob.getStImageBytes.toByteArray
    caseFinger
  }
  def convertLPCard2GafisCaseFingerMnt(lpCard: LPCard): GafisCaseFingerMnt = {
    val caseFingerMnt = new GafisCaseFingerMnt()
    caseFingerMnt.fingerId = lpCard.getStrCardID
    val blob = lpCard.getBlob
    caseFingerMnt.fingerMnt = blob.getStMntBytes.toByteArray
    caseFingerMnt.captureMethod = blob.getStrMntExtractMethod

    caseFingerMnt
  }
  def convertGafisCaseFinger2LPCard(caseFinger: GafisCaseFinger, caseFingerMnt: GafisCaseFingerMnt): LPCard = {
    val lpCard = LPCard.newBuilder()
    lpCard.setStrCardID(caseFinger.fingerId)
    val textBuilder = lpCard.getTextBuilder
    magicSet(caseFinger.seqNo, textBuilder.setStrSeq)
    if ("1".equals(caseFinger.isCorpse))
      textBuilder.setBDeadBody(true)
    magicSet(caseFinger.corpseNo, textBuilder.setStrDeadPersonNo)
    magicSet(caseFinger.remainPlace, textBuilder.setStrRemainPlace)
    magicSet(caseFinger.ridgeColor, textBuilder.setStrRidgeColor)
    magicSet(caseFinger.mittensBegNo, textBuilder.setStrStart)
    magicSet(caseFinger.mittensEndNo, textBuilder.setStrEnd)
    textBuilder.setNXieChaState(caseFinger.isAssist)
    textBuilder.setNBiDuiState(caseFinger.matchStatus)

    val blobBuilder = lpCard.getBlobBuilder
    blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
//    magicSet(caseFinger.developMethod, blobBuilder.setStrMntExtractMethod)
    blobBuilder.setStImageBytes(ByteString.copyFrom(caseFinger.fingerImg))
    //特征
    blobBuilder.setStMntBytes(ByteString.copyFrom(caseFingerMnt.fingerMnt))
    magicSet(caseFingerMnt.captureMethod, blobBuilder.setStrMntExtractMethod)
    //指位
    if (isNonBlank(caseFinger.fgp))
      0.until(caseFinger.fgp.length)
        .filter("1" == caseFinger.fgp.charAt(_))
        .foreach(i => blobBuilder.addFgp(FingerFgp.valueOf(i + 1)))
    //纹型
    if (isNonBlank(caseFinger.pattern))
      caseFinger.pattern.split(",").foreach(f => blobBuilder.addRp(PatternType.valueOf(f)))

    lpCard.build()
  }

  def convertGafisPerson2TPCard(person: GafisPerson, fingerList: Relation[GafisGatherFinger] = null): TPCard={
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
    magicSet(person.nationCode, textBuilder.setStrRace)
    magicSet(person.nativeplaceCode, textBuilder.setStrNation)
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
    val mntMap = fingerList.filter(_.groupId == 0).map(f => ((f.fgpCase, f.fgp), f.gatherData)).toMap[(String, Short), Array[Byte]]
    fingerList.filter(_.groupId == 1).foreach { finger =>
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

  def convertTPCard2GafisPerson(tpCard: TPCard): GafisPerson={
    val person = new GafisPerson()
    person.personid = tpCard.getStrCardID
    val text = tpCard.getText
    person.name = text.getStrName
    person.aliasname = text.getStrAliasName
    person.sexCode = text.getNSex.toString
    person.birthdayed = text.getStrBirthDate
    person.idcardno = text.getStrIdentityNum
    person.birthCode = text.getStrBirthAddrCode
    person.birthdetail = text.getStrBirthAddr
    person.address = text.getStrAddrCode
    person.addressdetail = text.getStrAddr
    person.personCategory = text.getStrPersonType
    person.caseClasses = text.getStrCaseType1
    person.caseClasses2 = text.getStrCaseType2
    person.caseClasses3 = text.getStrCaseType3
    person.gatherdepartcode = text.getStrPrintUnitCode
    person.gatherdepartname = text.getStrPrintUnitName
    person.gatherusername = text.getStrPrinter
    person.gatherDate = text.getStrPrintDate
    person.remark = text.getStrComment

    person.nativeplaceCode = text.getStrNation
    person.nationCode = text.getStrRace
    person.certificatetype = text.getStrCertifType
    person.certificateid = text.getStrCertifID

    person.assistSign = text.getNXieChaFlag.toString
    person.assistLevel = text.getNXieChaLevel.toString
    person.assistBonus = text.getStrPremium
    person.assistDate = text.getStrXieChaDate
    person.assistDeptCode = text.getStrXieChaRequestUnitCode
    person.assistDeptName = text.getStrXieChaRequestUnitName
    person.assistPurpose = text.getStrXieChaForWhat
    person.assistRefPerson = text.getStrRelPersonNo
    person.assistRefCase = text.getStrRelCaseNo
    person.validDate = text.getStrXieChaTimeLimit
    person.assistContacts = text.getStrXieChaContacter
    person.assistNumber = text.getStrXieChaTelNo
    person.assistApproval = text.getStrShenPiBy

    person
  }

  def convertTPCard2GafisGatherFinger(tpCard: TPCard): Seq[GafisGatherFinger] = {
    val fingerList = new ArrayBuffer[GafisGatherFinger]()
    val personId = tpCard.getStrCardID
    val iter = tpCard.getBlobList.iterator()
    while (iter.hasNext){
      val blob = iter.next()
      //指纹
      val finger = new GafisGatherFinger()
      finger.personId = personId
      finger.gatherData = blob.getStImageBytes.toByteArray
      finger.fgp = blob.getFgp.getNumber.toShort
      finger.fgpCase = if(blob.getBPlain) "1" else "0"
      finger.groupId = 1: Short
      finger.lobtype = 1: Short
      fingerList += finger
      //特征
      val mnt = new GafisGatherFinger()
      mnt.personId = personId
      mnt.gatherData = blob.getStMntBytes.toByteArray
      mnt.fgp = blob.getFgp.getNumber.toShort
      mnt.fgpCase = if(blob.getBPlain) "1" else "0"
      mnt.groupId = 0: Short
      mnt.lobtype = 0: Short
      fingerList += mnt
    }

    fingerList.toSeq
  }

  def convertMatchTask2GafisNormalqueryQueryque(matchTask: MatchTask): GafisNormalqueryQueryque={

    throw new UnsupportedOperationException
  }

  def convertGafisNormalqueryQueryque2MatchTask(gafisQuery: GafisNormalqueryQueryque): MatchTask = {

    val matchTask = MatchTask.newBuilder()
    matchTask.setMatchId(gafisQuery.keyid)
    matchTask.setMatchType(MatchType.valueOf(gafisQuery.querytype+1))
    matchTask.setObjectId(gafisQuery.oraSid)//必填项，现在用于存放oraSid
    matchTask.setPriority(gafisQuery.priority.toInt)
    matchTask.setScoreThreshold(gafisQuery.minscore)

    val mics = new galoctp{}.GAFIS_MIC_GetDataFromStream(ChannelBuffers.wrappedBuffer(gafisQuery.mic))
    mics.foreach{mic =>
      if(mic.bIsLatent == 1){
        val ldata = LatentMatchData.newBuilder()
        ldata.setMinutia(ByteString.copyFrom(mic.pstMnt_Data))
        matchTask.setLData(ldata)
      }else{
        mic.nItemType match {
          case 1 =>
            matchTask.getTDataBuilder.addMinutiaDataBuilder().setMinutia(ByteString.copyFrom(mic.pstMnt_Data)).setPos(mic.nItemData)
          case 8 =>
            matchTask.getTDataBuilder.addMinutiaDataBuilder().setMinutia(ByteString.copyFrom(mic.pstMnt_Data)).setPos(mic.nItemData+10)
          case 2 =>
            //TODO 掌纹
        }
      }
    }
    matchTask.build()

    throw new UnsupportedOperationException
  }
}
