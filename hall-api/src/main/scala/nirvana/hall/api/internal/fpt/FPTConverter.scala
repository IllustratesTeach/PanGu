package nirvana.hall.api.internal.fpt

import com.google.protobuf.ByteString
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.{Logic02Rec, Logic03Rec}
import nirvana.hall.extractor.internal.FPTMntConverter
import nirvana.hall.image.internal.FPTImageConverter
import nirvana.hall.protocol.api.FPTProto._

import scala.collection.mutable.ArrayBuffer

/**
  * Created by songpeng on 2017/1/26.
  * 将fpt对象转换为proto
  */
object FPTConverter {

  /**
    * 将Logic02Rec转换为TPCard，不做图像解压和特征转换，如果保存需要解压图像并提取特征
    * @param logic02Rec
    * @return
    */
  def convertLogic02Rec2TPCard(logic02Rec: Logic02Rec): TPCard = {
    val tpCard = TPCard.newBuilder()
    val textBuilder = tpCard.getTextBuilder
    tpCard.setStrCardID(logic02Rec.personId)
    textBuilder.setStrName(logic02Rec.personName)
    textBuilder.setStrAliasName(logic02Rec.alias)
    if (logic02Rec.gender != null && logic02Rec.gender.length > 0) {
      textBuilder.setNSex(logic02Rec.gender.toInt)
    }
    textBuilder.setStrBirthDate(logic02Rec.birthday)
    textBuilder.setStrIdentityNum(logic02Rec.idCardNo)
    textBuilder.setStrBirthAddrCode("") //logic02Rec.address
    textBuilder.setStrBirthAddr("") //logic02Rec.addressDetail
    textBuilder.setStrAddrCode(logic02Rec.address)
    textBuilder.setStrAddr(logic02Rec.addressDetail)
    textBuilder.setStrPersonType(logic02Rec.category)
    textBuilder.setStrCaseType1(logic02Rec.caseClass1Code)
    textBuilder.setStrCaseType2(logic02Rec.caseClass2Code)
    textBuilder.setStrCaseType3(logic02Rec.caseClass3Code)
    textBuilder.setStrPrintUnitCode(logic02Rec.gatherUnitCode)
    textBuilder.setStrPrintUnitName(logic02Rec.gatherName)
    textBuilder.setStrPrinter(logic02Rec.gatherName)
    textBuilder.setStrPrintDate(logic02Rec.gatherDate)
    textBuilder.setStrComment(logic02Rec.remark)
    textBuilder.setStrNation(logic02Rec.nation)
    textBuilder.setStrRace(logic02Rec.nativeplace)
    textBuilder.setStrCertifType(logic02Rec.certificateType)
    textBuilder.setStrCertifID(logic02Rec.certificateNo)
    textBuilder.setBHasCriminalRecord("1".equals(logic02Rec.isCriminal))
    textBuilder.setStrCriminalRecordDesc(logic02Rec.criminalInfo)
    textBuilder.setStrPremium(logic02Rec.assistUnitName)
    if (logic02Rec.isAssist != null && logic02Rec.isAssist.length > 0) {
      textBuilder.setNXieChaFlag(logic02Rec.isAssist.toInt)
    }
    textBuilder.setStrXieChaRequestUnitName(logic02Rec.assistUnitName)
    textBuilder.setStrXieChaRequestUnitCode(logic02Rec.assistUnitCode)
    if (logic02Rec.assistLevel != null && logic02Rec.assistLevel.length > 0) {
      textBuilder.setNXieChaFlag(logic02Rec.isAssist.toInt)
    }
    textBuilder.setStrXieChaForWhat(logic02Rec.assistPurpose)
    textBuilder.setStrRelPersonNo(logic02Rec.relatedPersonId)
    textBuilder.setStrRelCaseNo(logic02Rec.relatedCaseId)
    textBuilder.setStrXieChaTimeLimit(logic02Rec.assistTimeLimit)
    textBuilder.setStrXieChaDate(logic02Rec.assistDate)
    textBuilder.setStrXieChaRequestComment(logic02Rec.assistAskingInfo)
    textBuilder.setStrXieChaContacter(logic02Rec.contact)
    textBuilder.setStrXieChaTelNo(logic02Rec.contactPhone)
    textBuilder.setStrShenPiBy(logic02Rec.approver)
    textBuilder.setStrHuKouPlaceCode(logic02Rec.door)
    textBuilder.setStrHuKouPlaceTail(logic02Rec.doorDetail)


    logic02Rec.fingers.foreach { finger =>
      if (finger.imgData != null && finger.imgData.length > 0) {
        val blobBuilder = tpCard.addBlobBuilder()
        blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
        val fgp = finger.fgp.toInt
        if (fgp > 10) {
          blobBuilder.setFgp(FingerFgp.valueOf(fgp - 10))
          blobBuilder.setBPlain(true)
        } else {
          blobBuilder.setFgp(FingerFgp.valueOf(fgp))
          blobBuilder.setBPlain(false)
        }
        //这里只转换为gafisImage，保存的时候需要解压并提取特征
        val gafisImage = FPTImageConverter.convert2GafisImage(finger)
        blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray(AncientConstants.GBK_ENCODING)))
      }
    }
    //人像
    if (logic02Rec.portraitData != null && logic02Rec.portraitData.length > 0) {
      val blobBuilder = tpCard.addBlobBuilder()
      blobBuilder.setType(ImageType.IMAGETYPE_FACE)
      blobBuilder.setStImageBytes(ByteString.copyFrom(logic02Rec.portraitData))
    }

    tpCard.build()
  }

  def convertLogic03Res2Case(logic03Rec: Logic03Rec): Case = {
    val caseInfo = Case.newBuilder()
    val textBuilder = caseInfo.getTextBuilder
    caseInfo.setStrCaseID(logic03Rec.caseId)
    caseInfo.setNCaseFingerCount(logic03Rec.fingers.length)
    logic03Rec.fingers.foreach(f => caseInfo.addStrFingerID(f.fingerId))
    textBuilder.setStrCaseType1(logic03Rec.caseClass1Code) //案件类别
    textBuilder.setStrCaseType2(logic03Rec.caseClass2Code)
    textBuilder.setStrCaseType3(logic03Rec.caseClass3Code)
    textBuilder.setStrSuspArea1Code(logic03Rec.suspiciousArea1Code) //可疑地区行政区划
    textBuilder.setStrSuspArea2Code(logic03Rec.suspiciousArea2Code)
    textBuilder.setStrSuspArea3Code(logic03Rec.suspiciousArea3Code)
    textBuilder.setStrCaseOccurDate(logic03Rec.occurDate) //案发日期
    textBuilder.setStrCaseOccurPlaceCode(logic03Rec.occurPlaceCode) //案发地点代码
    textBuilder.setStrCaseOccurPlace(logic03Rec.occurPlace) //案发地址详情

    textBuilder.setStrExtractUnitCode(logic03Rec.extractUnitCode) //提取单位代码
    textBuilder.setStrExtractUnitName(logic03Rec.extractUnitName) //提取单位
    textBuilder.setStrExtractor(logic03Rec.extractor) //提取人
    textBuilder.setStrExtractDate(logic03Rec.extractDate) //提取时间
    textBuilder.setStrMoneyLost(logic03Rec.amount) //涉案金额
    textBuilder.setStrPremium(logic03Rec.bonus) //协查奖金
    textBuilder.setBPersonKilled("1".equals(logic03Rec.isMurder)) //命案标识
    textBuilder.setStrComment(logic03Rec.caseBriefDetail) //备注,简要案情
    textBuilder.setStrXieChaDate(logic03Rec.assistDate) //协查日期
    textBuilder.setStrXieChaRequestUnitName(logic03Rec.assistUnitName) //协查单位名称
    textBuilder.setStrXieChaRequestUnitCode(logic03Rec.assistUnitCode) //协查单位代码

    //隐式转换，字符串转数字
    implicit def string2Int(str: String): Int = {
      if (str != null && str.matches("[0-9]+"))
        Integer.parseInt(str)
      else 0
    }
    textBuilder.setNSuperviseLevel(logic03Rec.assistLevel) //协查级别
    textBuilder.setNCaseState(logic03Rec.caseStatus) //案件状态
    textBuilder.setNCaseState(logic03Rec.isCaseAssist) //协查状态
    textBuilder.setNCancelFlag(logic03Rec.isRevoke) //撤销标识

    caseInfo.build()
  }

  def convertLogic03Res2LPCard(logic03Rec: Logic03Rec): Seq[LPCard] = {
    val lpCardList = new ArrayBuffer[LPCard]()
    logic03Rec.fingers.foreach { finger =>
      val lpCard = LPCard.newBuilder()
      lpCard.setStrCardID(finger.fingerId)
      val textBuilder = lpCard.getTextBuilder
      textBuilder.setStrCaseId(logic03Rec.caseId)
      textBuilder.setStrSeq(finger.fingerNo)
      textBuilder.setStrRemainPlace(finger.remainPlace) //遗留部位
      textBuilder.setStrRidgeColor(finger.ridgeColor) //乳突线颜色
      textBuilder.setBDeadBody("1".equals(finger.ridgeColor)) //未知名尸体标识
      textBuilder.setStrDeadPersonNo(finger.corpseNo) //未知名尸体编号
      textBuilder.setStrStart(finger.mittensBegNo) //联指开始序号
      textBuilder.setStrEnd(finger.mittensEndNo) //联指结束序号
      textBuilder.setStrCaptureMethod(finger.extractMethod) //提取方式

      if (finger.imgData != null && finger.imgData.length > 0) {
        val blobBuilder = lpCard.getBlobBuilder
        blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
        if (finger.fgp != null && finger.fgp.length > 0) {
          0.until(finger.fgp.length)
            .filter("1" == finger.fgp.charAt(_))
            .foreach(i => blobBuilder.addFgp(FingerFgp.valueOf(i + 1)))
        }
        val gafisImage = FPTImageConverter.convert2GafisImage(finger, true)
        val gafisMnt = FPTMntConverter.convertFingerLData2GafisMnt(finger)
        blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray()))
        blobBuilder.setStMntBytes(ByteString.copyFrom(gafisMnt.toByteArray()))
      }
      //隐式转换，字符串转数字
      implicit def string2Int(str: String): Int = {
        if (str != null && str.matches("[0-9]+"))
          Integer.parseInt(str)
        else 0
      }
      textBuilder.setNXieChaState(finger.isFingerAssist)
      textBuilder.setNBiDuiState(finger.matchStatus)

      lpCardList += lpCard.build()
    }

    lpCardList
  }

}
