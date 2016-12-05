package nirvana.hall.api.webservice.util


import java.io.{FileOutputStream, ObjectOutputStream}

import nirvana.hall.c.services.gfpt4lib.FPT4File.{FPT4File, FingerTData, Logic02Rec}
import nirvana.hall.protocol.api.FPTProto.TPCard

/**
  * Created by yuchen on 2016/12/2.
  */
object CommonUtil {

  def convertProtoBuf2TPFPT4File(card: TPCard,logic02RecsNum: Int = 1): FPT4File = {
    val fpt4File = new FPT4File
    fpt4File.head.flag = "FPT"
    fpt4File.head.majorVersion = "04"
    fpt4File.head.minorVersion = "00"
    fpt4File.fileLength = ""
    fpt4File.tpCount = card.getBlobCount.toString
    fpt4File.lpCount = "0"
    fpt4File.tl_ltCount = "0"
    fpt4File.ttCount = "0"
    fpt4File.llCount = "0"
    fpt4File.lpRequestCount = "0"
    fpt4File.tpRequestCount = "0"
    fpt4File.ltCandidateCount =  "0"
    fpt4File.tlCandidateCount = "0"
    fpt4File.ttCandidateCount = "0"
    fpt4File.llCandidateCount = "0"
    fpt4File.customCandidateCount = "0"
    fpt4File.sendTime = ""
    fpt4File.receiveUnitCode = ""
    fpt4File.sendUnitCode = ""
    fpt4File.sendUnitName = ""
    fpt4File.sender = ""
    fpt4File.sendUnitSystemType =""
    fpt4File.sid = ""
    fpt4File.remark = card.getText.getStrComment


    val arrLogic02Rec = new Array[Logic02Rec](logic02RecsNum)
    for( i <- 0 to  logic02RecsNum-1){

      val logic02Rec = new Logic02Rec
      logic02Rec.head.fileLength = "0"
      logic02Rec.head.dataType = "0"
      logic02Rec.index = ""
      logic02Rec.systemType = ""
      logic02Rec.personId = card.getStrPersonID
      logic02Rec.cardId = card.getStrCardID
      logic02Rec.personName = card.getText.getStrName
      logic02Rec.alias = card.getText.getStrAliasName
      logic02Rec.gender = card.getText.getNSex.toString
      logic02Rec.birthday = card.getText.getStrBirthDate
      logic02Rec.nativeplace = card.getText.getStrNation
      logic02Rec.nation = card.getText.getStrRace
      logic02Rec.idCardNo = card.getText.getStrIdentityNum
      logic02Rec.certificateType = card.getText.getStrCertifType
      logic02Rec.certificateNo = card.getText.getStrCertifID
      logic02Rec.door = card.getText.getStrHuKouPlaceCode
      logic02Rec.doorDetail = card.getText.getStrHuKouPlaceTail
      logic02Rec.address = card.getText.getStrAddr
      logic02Rec.addressDetail = card.getText.getStrAddrCode
      logic02Rec.category = card.getText.getStrPersonType
      logic02Rec.caseClass1Code = card.getText.getStrCaseType1
      logic02Rec.caseClass2Code = card.getText.getStrCaseType2
      logic02Rec.caseClass3Code = card.getText.getStrCaseType3
      logic02Rec.isCriminal = if(card.getText.getBHasCriminalRecord) "1" else "0"
      logic02Rec.criminalInfo = card.getText.getStrCriminalRecordDesc
      logic02Rec.gatherUnitCode = card.getText.getStrPrintUnitCode
      logic02Rec.gatherUnitName = card.getText.getStrPrintUnitName
      logic02Rec.gatherName = card.getText.getStrPrinter
      logic02Rec.gatherDate = card.getText.getStrPrintDate
      logic02Rec.assistLevel = card.getText.getNXieChaLevel.toString
      logic02Rec.bonus = card.getText.getStrPremium
      logic02Rec.assistPurpose = card.getText.getStrXieChaForWhat
      logic02Rec.relatedPersonId = card.getText.getStrRelPersonNo
      logic02Rec.relatedCaseId = card.getText.getStrRelCaseNo
      logic02Rec.assistTimeLimit = card.getText.getStrXieChaTimeLimit
      logic02Rec.assistAskingInfo = card.getText.getStrXieChaRequestComment
      logic02Rec.assistUnitCode = card.getText.getStrXieChaRequestUnitCode
      logic02Rec.assistUnitName = card.getText.getStrXieChaRequestUnitName
      logic02Rec.assistDate = card.getText.getStrXieChaDate
      logic02Rec.contact = card.getText.getStrXieChaContacter
      logic02Rec.contactPhone  = card.getText.getStrXieChaTelNo
      logic02Rec.approver = card.getText.getStrShenPiBy
      logic02Rec.remark = card.getText.getStrComment
      logic02Rec.isAssist = card.getText.getNXieChaFlag.toString
      logic02Rec.portraitDataLength = ""
      logic02Rec.portraitData = new Array[Byte](0)
        val fingerCount = card.getBlobList.size()
      logic02Rec.sendFingerCount = fingerCount.toString
      arrLogic02Rec.update(i,logic02Rec)
      fpt4File.logic02Recs = arrLogic02Rec


      val arrFingers = new Array[FingerTData](fingerCount)
      for(j <- 0 to fingerCount-1) {

        val fingerTData = new FingerTData
        fingerTData.dataLength = card.getBlobList.get(j).toByteArray.length.toString
        fingerTData.sendNo = "" //发送编号如何定义???
        fingerTData.fgp = card.getBlobList.get(j).getFgp.getNumber.toString
        fingerTData.extractMethod = card.getBlobList.get(j).getStrMntExtractMethod
        fingerTData.pattern1 = card.getBlobList.get(j).getRp.getNumber.toString
        fingerTData.pattern2 = card.getBlobList.get(j).getVrp.getNumber.toString
        fingerTData.fingerDirection = ""
        fingerTData.centerPoint = ""
        fingerTData.subCenterPoint = ""
        fingerTData.leftTriangle = ""
        fingerTData.rightTriangle = ""
        fingerTData.featureCount = card.getBlobList.get(j).getStMntBytes.size.toString //特征点个数
        fingerTData.feature = card.getBlobList.get(j).getStMnt
        fingerTData.customInfoLength = ""
        fingerTData.customInfo = new Array[Byte](0)
        fingerTData.imgHorizontalLength = "640"
        fingerTData.imgVerticalLength = "640"
        fingerTData.dpi = "500"
        fingerTData.imgCompressMethod = "1900"
        fingerTData.imgDataLength = card.getBlobList.get(j).getStImageBytes.size.toString
        fingerTData.imgData = card.getBlobList.get(j).getStImageBytes.toByteArray
        arrFingers.update(j,fingerTData)
      }
      fpt4File.logic02Recs(i).fingers = arrFingers
    }
    fpt4File

  }
}
