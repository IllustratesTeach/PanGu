package nirvana.hall.api.internal.fpt

import com.google.protobuf.ByteString
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.{Logic02Rec, Logic03Rec}
import nirvana.hall.image.internal.FPTImageConverter
import nirvana.hall.protocol.api.FPTProto._

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
  def convertLogic02Rec2TPCard(logic02Rec: Logic02Rec): TPCard ={
    val tpCard = TPCard.newBuilder()
    val textBuilder = tpCard.getTextBuilder
    tpCard.setStrCardID(logic02Rec.personId)
    textBuilder.setStrName(logic02Rec.personName)
    textBuilder.setStrAliasName(logic02Rec.alias)
    if(logic02Rec.gender != null && logic02Rec.gender.length >0){
      textBuilder.setNSex(logic02Rec.gender.toInt)
    }
    textBuilder.setStrBirthDate(logic02Rec.birthday)
    textBuilder.setStrIdentityNum(logic02Rec.idCardNo)
    textBuilder.setStrBirthAddrCode(logic02Rec.addressDetail)
    textBuilder.setStrBirthAddr(logic02Rec.address)
    textBuilder.setStrAddrCode(logic02Rec.doorDetail)
    textBuilder.setStrAddr(logic02Rec.door)
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
    textBuilder.setBHasCriminalRecord(logic02Rec.isCriminal.equals("1"))
    textBuilder.setStrCriminalRecordDesc(logic02Rec.criminalInfo)
    textBuilder.setStrPremium(logic02Rec.assistUnitName)
    if(logic02Rec.isAssist != null && logic02Rec.isAssist.length > 0){
      textBuilder.setNXieChaFlag(logic02Rec.isAssist.toInt)
    }
    textBuilder.setStrXieChaRequestUnitName(logic02Rec.assistUnitName)
    textBuilder.setStrXieChaRequestUnitCode(logic02Rec.assistUnitCode)
    if(logic02Rec.assistLevel != null && logic02Rec.assistLevel.length > 0){
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


    logic02Rec.fingers.foreach { finger=>
      if (finger.imgData != null && finger.imgData.length > 0) {
        val blobBuilder = tpCard.addBlobBuilder()
        blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
        val fgp = finger.fgp.toInt
        if(fgp > 10){
          blobBuilder.setFgp(FingerFgp.valueOf(fgp - 10))
          blobBuilder.setBPlain(true)
        }else{
          blobBuilder.setFgp(FingerFgp.valueOf(fgp))
          blobBuilder.setBPlain(false)
        }
        //这里只转换为gafisImage，保存的时候需要解压并提取特征
        val gafisImage = FPTImageConverter.convert2GafisImage(finger)
        blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray(AncientConstants.GBK_ENCODING)))
      }
    }
    //人像
    if(logic02Rec.portraitData != null && logic02Rec.portraitData.length > 0){
      val blobBuilder = tpCard.addBlobBuilder()
      blobBuilder.setType(ImageType.IMAGETYPE_FACE)
      blobBuilder.setStImageBytes(ByteString.copyFrom(logic02Rec.portraitData))
    }

    tpCard.build()
  }

  def convertLogic03Res2Case(logic03Rec: Logic03Rec):Case ={

    null
  }
  def convertLogic03Res2LPCard(logic03Rec: Logic03Rec):Seq[LPCard] ={

    null
  }

}
