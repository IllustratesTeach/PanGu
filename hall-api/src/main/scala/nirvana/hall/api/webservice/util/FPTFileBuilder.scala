package nirvana.hall.api.webservice.util

import javax.activation.DataHandler

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.{FPT4File, FingerLData, FingerTData, Logic02Rec, Logic03Rec}
import nirvana.hall.protocol.api.FPTProto.{Case, LPCard, TPCard}
import org.apache.axiom.attachments.ByteArrayDataSource

import scala.collection.mutable

/**
  * Created by yuchen on 2016/12/2.
  */
object FPTFileBuilder {


  /**
    * 空FPT
    */
  val emptyFPT = new DataHandler(new ByteArrayDataSource(new Array[Byte](0)))

  /**
    * protobuffer转换为十指指纹FPT
    *
    * @param card 人员卡号
    * @param logic02RecsNum 逻辑记录类的数量
    * @return FPT4File对象
    */
  def convertProtoBuf2TPFPT4File(card: TPCard,logic02RecsNum: Int = 1): FPT4File = {
    val fpt4File = new FPT4File
    fpt4File.head.flag = "FPT"
    fpt4File.head.majorVersion = "04"
    fpt4File.head.minorVersion = "00"
    fpt4File.fileLength = ""
//    fpt4File.tpCount = card.getBlobCount.toString
    fpt4File.tpCount = "1"                          //十指指纹信息记录数量
    fpt4File.lpCount = "0"                          //现场指纹信息记录数量
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
    fpt4File.sendUnitSystemType ="1900" //1900 东方金指
    fpt4File.sid = ""
    fpt4File.remark = card.getText.getStrComment

    val arrLogic02Rec = new Array[Logic02Rec](logic02RecsNum)
    for( i <- 0 to  logic02RecsNum-1){

      val logic02Rec = new Logic02Rec
      logic02Rec.head.fileLength = "0"
      logic02Rec.head.dataType = "02"
      logic02Rec.index = ""
      logic02Rec.systemType = "1900" //1900 东方金指
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
      logic02Rec.head.fileLength = logic02Rec.toByteArray(AncientConstants.GBK_ENCODING).length + ""
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
    fpt4File.fileLength = fpt4File.toByteArray(AncientConstants.GBK_ENCODING).length + ""
    fpt4File
  }

  /**
    * protobuffer转换为案件指纹FPT
    *
    * @param card 案件卡号
    * @param logic03RecsNum 逻辑记录类的数量
    * @author ssj
    * @return FPT4File对象
    */
  def convertProtoBuf2LPFPT4File(card: LPCard,caseInfo: Case, logic03RecsNum: Int = 1): FPT4File = {
    val fpt4File = new FPT4File
    fpt4File.head.flag = "FPT"
    fpt4File.head.majorVersion = "04"
    fpt4File.head.minorVersion = "00"
    fpt4File.fileLength = ""
    fpt4File.tpCount = "0"
    fpt4File.lpCount = "1"
    fpt4File.tl_ltCount = "0"
    fpt4File.ttCount = "0"
    fpt4File.llCount = "0"
    fpt4File.lpRequestCount = "0"
    fpt4File.tpRequestCount = "0"
    fpt4File.ltCandidateCount = "0"
    fpt4File.tlCandidateCount = "0"
    fpt4File.ttCandidateCount = "0"
    fpt4File.llCandidateCount = "0"
    fpt4File.customCandidateCount = "0"
    fpt4File.sendTime = ""
    fpt4File.receiveUnitCode = ""
    fpt4File.sendUnitCode = ""
    fpt4File.sendUnitName = ""
    fpt4File.sender = ""
    fpt4File.sendUnitSystemType = "1900"
    fpt4File.sid = ""
    fpt4File.remark = ""


    val arrLogic03Rec = new Array[Logic03Rec](logic03RecsNum)
    for (i <- 0 to logic03RecsNum - 1) {

      val logic03Rec = new Logic03Rec
      logic03Rec.head.fileLength = "0"
      logic03Rec.head.dataType = "03"
      logic03Rec.index = ""
      logic03Rec.systemType = "1900"
      logic03Rec.caseId = if (22 == caseInfo.getStrCaseID.length) {
        "A".concat(caseInfo.getStrCaseID)
      } else {
        caseInfo.getStrCaseID
      }
      logic03Rec.cardId = caseInfo.getStrCaseID
      logic03Rec.caseClass1Code = caseInfo.getText.getStrCaseType1
      logic03Rec.caseClass2Code = caseInfo.getText.getStrCaseType2
      logic03Rec.caseClass3Code = caseInfo.getText.getStrCaseType3
      logic03Rec.occurDate = caseInfo.getText.getStrCaseOccurDate
      logic03Rec.occurPlaceCode = caseInfo.getText.getStrCaseOccurPlaceCode
      logic03Rec.occurPlace = caseInfo.getText.getStrCaseOccurPlace
      logic03Rec.caseBriefDetail = caseInfo.getText.getStrComment
      logic03Rec.isMurder = if (caseInfo.getText.getBPersonKilled) "1" else "0"
      logic03Rec.amount = caseInfo.getText.getStrMoneyLost
      logic03Rec.extractUnitCode = caseInfo.getText.getStrExtractUnitCode
      logic03Rec.extractUnitName = caseInfo.getText.getStrExtractUnitName
      logic03Rec.extractDate = caseInfo.getText.getStrExtractDate
      logic03Rec.extractor = caseInfo.getText.getStrExtractor
      logic03Rec.suspiciousArea1Code = caseInfo.getText.getStrSuspArea1Code
      logic03Rec.suspiciousArea2Code = caseInfo.getText.getStrSuspArea2Code
      logic03Rec.suspiciousArea3Code = caseInfo.getText.getStrSuspArea3Code
      logic03Rec.assistLevel = caseInfo.getText.getNSuperviseLevel.toString
      logic03Rec.bonus = caseInfo.getText.getStrPremium
      logic03Rec.assistUnitCode = caseInfo.getText.getStrXieChaRequestUnitName
      logic03Rec.assistUnitName = caseInfo.getText.getStrXieChaRequestUnitCode
      logic03Rec.assistDate = caseInfo.getText.getStrXieChaDate
      logic03Rec.isCaseAssist = caseInfo.getText.getNXieChaState.toString
      logic03Rec.isRevoke = "" ///获取到 是 31 caseInfo.getText.getNCancelFlag.toString
      logic03Rec.caseStatus = caseInfo.getText.getNCaseState.toString
      logic03Rec.fingerCount = caseInfo.getStrFingerIDCount.toString
      val fingerCount = caseInfo.getStrFingerIDCount
      logic03Rec.sendFingerCount = fingerCount.toString
      logic03Rec.head.fileLength = logic03Rec.toByteArray(AncientConstants.GBK_ENCODING).length + ""
      arrLogic03Rec.update(i, logic03Rec)
      fpt4File.logic03Recs = arrLogic03Rec



      val arrFingers = new Array[FingerLData](fingerCount)
      for (j <- 0 to fingerCount - 1) {

        val fingerLData = new FingerLData
        fingerLData.dataLength = ""
        fingerLData.sendNo = "" //发送编号如何定义???
        fingerLData.fingerNo = card.getText.getStrSeq
        fingerLData.fingerId = card.getStrCardID
        fingerLData.isCorpse = if (card.getText.getBDeadBody) "1" else "0"
        fingerLData.corpseNo = card.getText.getStrDeadPersonNo
        fingerLData.remainPlace = card.getText.getStrRemainPlace
        fingerLData.fgp = ""
        fingerLData.ridgeColor = card.getText.getStrRidgeColor
        fingerLData.mittensBegNo = card.getText.getStrStart
        fingerLData.mittensEndNo = card.getText.getStrEnd
        fingerLData.isFingerAssist = card.getText.getNXieChaState.toString
        fingerLData.matchStatus = card.getText.getNBiDuiState.toString
        fingerLData.extractMethod = card.getText.getStrCaptureMethod
        fingerLData.pattern = ""
        fingerLData.fingerDirection = ""
        fingerLData.centerPoint = ""
        fingerLData.subCenterPoint = ""
        fingerLData.leftTriangle = ""
        fingerLData.rightTriangle = ""
        fingerLData.featureCount = card.getBlob.getStMntBytes.size.toString //特征点个数
        fingerLData.feature = card.getBlob.getStMnt
        fingerLData.customInfoLength = ""
        fingerLData.customInfo = new Array[Byte](0)
        fingerLData.imgHorizontalLength = "512"
        fingerLData.imgVerticalLength = "512"
        fingerLData.dpi = "500"
        fingerLData.imgCompressMethod = ""
        fingerLData.imgDataLength = card.getBlob.getStImageBytes.size.toString
        fingerLData.imgData = card.getBlob.getStImageBytes.toByteArray
        arrFingers.update(j, fingerLData)
      }
      fpt4File.logic03Recs(i).fingers = arrFingers
    }
    fpt4File.fileLength = fpt4File.toByteArray(AncientConstants.GBK_ENCODING).length + ""
    fpt4File
  }

  /**
    * 建造十指FPT文件
    * @param  logic02RecList 十指数据集合
    * @return FPT4File对象
    */
  def buildTenprintRecordFpt(logic02RecList :Seq[Logic02Rec]): FPT4File = {
    val fpt4File = new FPT4File
    fpt4File.head.flag = "FPT"
    fpt4File.head.majorVersion = "04"
    fpt4File.head.minorVersion = "00"
    fpt4File.fileLength = ""
    fpt4File.tpCount = logic02RecList.size + ""
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
    fpt4File.remark = ""
    val arrLogic02Rec:Array[Logic02Rec] = logic02RecList.toArray
    fpt4File.logic02Recs = arrLogic02Rec
    fpt4File
  }

  /**
    * 建造案件FPT文件
    * @param  logic03RecList 案件数据集合
    * @return FPT4File对象
    */
  def buildLatentFpt(logic03RecList :Seq[Logic03Rec]): FPT4File = {
    val fpt4File = new FPT4File
    fpt4File.head.flag = "FPT"
    fpt4File.head.majorVersion = "04"
    fpt4File.head.minorVersion = "00"
    fpt4File.fileLength = ""
    fpt4File.tpCount = logic03RecList.size + ""
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
    fpt4File.remark = ""
    val arrLogic03Rec:Array[Logic03Rec] = logic03RecList.toArray
    fpt4File.logic03Recs = arrLogic03Rec
    fpt4File
  }


  /**
    * 获取数据
    *
    * @param map 数据集合
    * @param key 数据属性
    * @return data 数据
    */
  private def getData(map: mutable.HashMap[String,Any],key :String): String = {
    var data = ""
    if(map != null && map(key) != null){
      data = map(key).toString()
    }
    data
  }

}
