package nirvana.hall.api.webservice.util

import com.google.protobuf.ByteString
import nirvana.hall.c.services.gfpt4lib.FPT4File.{FPT4File, Logic02Rec}
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.kernel.FPTLDataToMNTDISP
import nirvana.hall.protocol.api.FPTProto._
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition

import scala.util.Success

/**
  * Created by yuchen on 2016/12/20.
  */
object FPTConvertToProtoBuffer {
  /**
    * 定义一个常量
    * 如果传入的int值为空，则给出9
    */
  val INTDEX = 9

  /**
    * 根据接口获得的FPT文件,构建TPCard的ProtoBuffer的对象
    * 在构建过程中,需要解压图片获得原图，提取特征
    *
    * @param fpt4
    * @return
    */
  def TPFPT2ProtoBuffer(logic02Rec:Logic02Rec,fpt4:FPT4File,imageDecompressUrl:String):TPCard ={
    val tpCard = TPCard.newBuilder()
    val textBuilder = tpCard.getTextBuilder
    tpCard.setStrCardID(logic02Rec.personId)
    textBuilder.setStrName(logic02Rec.personName)
    textBuilder.setStrAliasName(logic02Rec.alias)
    textBuilder.setNSex(if (logic02Rec.gender != null) {
      logic02Rec.gender.toInt
    } else {
      INTDEX
    })
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
    textBuilder.setBHasCriminalRecord(if (logic02Rec.isCriminal == 0) {
      logic02Rec.isCriminal == false
    } else {
      logic02Rec.isCriminal == true
    })
    textBuilder.setStrCriminalRecordDesc(logic02Rec.criminalInfo)
    textBuilder.setStrPremium(logic02Rec.assistUnitName)
    textBuilder.setNXieChaFlag(if (logic02Rec.isAssist != null) {
      logic02Rec.isAssist.toInt
    } else {
      INTDEX
    })
    textBuilder.setStrXieChaRequestUnitName(logic02Rec.assistUnitName)
    textBuilder.setStrXieChaRequestUnitCode(logic02Rec.assistUnitCode)
    textBuilder.setNXieChaLevel(if (logic02Rec.assistLevel != null) {
      logic02Rec.assistLevel.toInt
    } else {
      INTDEX
    })
    textBuilder.setStrXieChaForWhat(logic02Rec.assistPurpose)
    textBuilder.setStrRelPersonNo(logic02Rec.relatedPersonId)
    textBuilder.setStrRelCaseNo(logic02Rec.relatedCaseId)
    textBuilder.setStrXieChaTimeLimit(logic02Rec.assistTimeLimit)
    textBuilder.setStrXieChaDate(logic02Rec.assistDate)
    textBuilder.setStrXieChaRequestComment(logic02Rec.assistAskingInfo)
    textBuilder.setStrXieChaContacter(logic02Rec.contact)
    textBuilder.setStrXieChaTelNo(logic02Rec.contactPhone)
    textBuilder.setStrShenPiBy(logic02Rec.approver)


    logic02Rec.fingers.foreach { tData =>
      if (tData.imgData != null && tData.imgData.length > 0) {
        val tBuffer = FPTFileHandler.fingerDataToGafisImage(tData)
        //图像解压
        val s = FPTFileHandler.callHallImageDecompressionImage(imageDecompressUrl,tBuffer)
        //提取特征
        val mntAndBin = FPTFileHandler.extractorFeature(s, fgpParesEnum(tData.fgp), ParseFeatureTypeEnum(fpt4.tpCount.toInt, fpt4.lpCount.toInt))
        val blobBuilder = tpCard.addBlobBuilder()
        blobBuilder.setStMntBytes(ByteString.copyFrom(mntAndBin.get._1.toByteArray()))
        blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
        blobBuilder.setFgp(fgpParesProtoBuffer(tData.fgp))
        blobBuilder.setStImageBytes(ByteString.copyFrom(s.toByteArray()))
        blobBuilder.setStBinBytes(ByteString.copyFrom(mntAndBin.get._2.toByteArray()))
      }
    }
    tpCard.build()
  }

  /**
    * 将解析出的指位翻译成系统中的枚举类型
    */
  def fgpParesEnum(fgp:String): FingerPosition ={
    fgp match {
      case "01" =>
        FingerPosition.FINGER_R_THUMB
      case "02" =>
        FingerPosition.FINGER_R_INDEX
      case "03" =>
        FingerPosition.FINGER_R_MIDDLE
      case "04" =>
        FingerPosition.FINGER_R_RING
      case "05" =>
        FingerPosition.FINGER_R_LITTLE
      case "06" =>
        FingerPosition.FINGER_L_THUMB
      case "07" =>
        FingerPosition.FINGER_L_INDEX
      case "08" =>
        FingerPosition.FINGER_L_MIDDLE
      case "09" =>
        FingerPosition.FINGER_L_RING
      case "10" =>
        FingerPosition.FINGER_L_LITTLE
      case other =>
        FingerPosition.FINGER_UNDET
    }
  }


  /**
    * 将解析出的指位翻译成系统中的枚举类型,ProtoBuffer
    */
  def fgpParesProtoBuffer(fgp:String): FingerFgp ={
    fgp match {
      case "01" =>
        FingerFgp.FINGER_R_THUMB
      case "02" =>
        FingerFgp.FINGER_R_INDEX
      case "03" =>
        FingerFgp.FINGER_R_MIDDLE
      case "04" =>
        FingerFgp.FINGER_R_RING
      case "05" =>
        FingerFgp.FINGER_R_LITTLE
      case "06" =>
        FingerFgp.FINGER_L_THUMB
      case "07" =>
        FingerFgp.FINGER_L_INDEX
      case "08" =>
        FingerFgp.FINGER_L_MIDDLE
      case "09" =>
        FingerFgp.FINGER_L_RING
      case "10" =>
        FingerFgp.FINGER_L_LITTLE
      case other =>
        FingerFgp.FINGER_UNDET
    }
  }

  /**
    *
    * @param tpCount
    * @param lpCount
    * @return
    */
  def ParseFeatureTypeEnum(tpCount:Integer,lpCount:Integer): FeatureType ={
    var featureType:FeatureType = null
    if(tpCount>0){
      featureType = FeatureType.FingerTemplate
    }else if(lpCount>0){
      featureType = FeatureType.FingerLatent
    }
    featureType
  }

  /**
    * 根据接口获得的FPT文件,构建CaseInfo的ProtoBuffer的对象
    * 在构建过程中,需要解压图片获得原图，提取特征
    *
    * @param fpt4
    * @return
    */
  def FPT2LPProtoBuffer(fpt4:FPT4File):LPCard = {

    val lpCard = LPCard.newBuilder()
    val textBuilder = lpCard.getTextBuilder

    fpt4.logic03Recs.foreach { lp =>
      lpCard.setStrCardID(lp.cardId)

      lp.fingers.foreach { tData =>
        textBuilder.setStrSeq(tData.fingerNo)
        textBuilder.setStrRemainPlace(tData.remainPlace)     //遗留部位
        textBuilder.setStrRidgeColor(tData.ridgeColor)      //乳突线颜色
        textBuilder.setBDeadBody(if("1"==tData.ridgeColor){true}else{false})          //未知名尸体标识
        textBuilder.setStrDeadPersonNo(tData.corpseNo)    //未知名尸体编号
        textBuilder.setNXieChaState(if(null == tData.isFingerAssist){tData.isFingerAssist.toInt}else{9})       //协查状态
        scala.util.Try(tData.matchStatus.toInt) match {     //比对状态
          case Success(_) =>  textBuilder.setNBiDuiState(tData.matchStatus.toInt)
          case _ =>   }
        textBuilder.setStrStart(tData.mittensBegNo)           //联指开始序号
        textBuilder.setStrEnd(tData.mittensEndNo)             //联指结束序号
        //        textBuilder.setStrCaseId(tData.fingerId.substring(0,22))          //案件编号
        textBuilder.setStrCaptureMethod(tData.extractMethod)  //提取方式
        textBuilder.setStrComment("")         //备注

        if (tData.imgData != null && tData.imgData.length > 0) {
          val blobBuilder = lpCard.getBlobBuilder
          blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
          val buffer = FPTFileHandler.fingerDataToGafisImage(tData)//原图
          blobBuilder.setStImageBytes(ByteString.copyFrom(buffer.toByteArray()))

          val mntbuffer = fpt4code.FPTFingerLDataToGafisImage(tData)//特征
          val disp = FPTLDataToMNTDISP.convertFPT03ToMNTDISP(tData)
          val feature = FPTFileHandler.createImageLatentEvent(disp)
          mntbuffer.bnData = feature
          mntbuffer.stHead.nImgSize = feature.length
          blobBuilder.setStMntBytes(ByteString.copyFrom(mntbuffer.toByteArray()))

        }
      }
      textBuilder.build()
    }
    lpCard.build()
  }

  /**
    * 根据接口获得的FPT文件转换为CaseInfo proto对象
    * 在构建过程中,需要解压图片获得原图，提取特征
    *
    * @param fpt4
    * @return
    */
  def FPT2CaseProtoBuffer(fpt4:FPT4File):Case = {
    val caseData = Case.newBuilder()

    val textBuilder = caseData.getTextBuilder

    fpt4.logic03Recs.foreach { lp =>
      caseData.setStrCaseID(lp.caseId)
      caseData.setNCaseFingerCount(1)
      textBuilder.setStrCaseType1(lp.caseClass1Code) //案件类别
      textBuilder.setStrCaseType2(lp.caseClass2Code)
      textBuilder.setStrCaseType3(lp.caseClass3Code)
      textBuilder.setStrSuspArea1Code(lp.suspiciousArea1Code) //可疑地区行政区划
      textBuilder.setStrSuspArea2Code(lp.suspiciousArea2Code)
      textBuilder.setStrSuspArea3Code(lp.suspiciousArea3Code)
      textBuilder.setStrCaseOccurDate(lp.occurDate) //案发日期
      textBuilder.setStrCaseOccurPlaceCode(lp.occurPlaceCode) //案发地点代码
      textBuilder.setStrCaseOccurPlace(lp.occurPlace) //案发地址详情
      scala.util.Try(lp.assistLevel.toInt) match {     //协查级别
        case Success(_) =>  textBuilder.setNSuperviseLevel(lp.assistLevel.toInt)
        case _ =>  }
      textBuilder.setStrExtractUnitCode(lp.extractUnitCode) //提取单位代码
      textBuilder.setStrExtractUnitName(lp.extractUnitName) //提取单位
      textBuilder.setStrExtractor(lp.extractor) //提取人
      textBuilder.setStrExtractDate(lp.extractDate) //提取时间
      textBuilder.setStrMoneyLost(lp.amount) //涉案金额
      textBuilder.setStrPremium(lp.bonus) //协查奖金
      scala.util.Try(lp.isMurder.toBoolean) match {     //命案标识
        case Success(_) =>  textBuilder.setBPersonKilled(if("1"==lp.isMurder){true}else{false})
        case _ =>   }
      textBuilder.setStrComment("") //备注
      scala.util.Try(lp.caseStatus.toInt) match {
        case Success(_) =>  textBuilder.setNCaseState(lp.caseStatus.toInt) //案件状态
        case _ =>   }
      scala.util.Try(lp.isCaseAssist.toInt) match {
        case Success(_) =>  textBuilder.setNCaseState(lp.isCaseAssist.toInt) //协查状态
        case _ =>   }
      scala.util.Try(lp.isRevoke.toInt) match {
        case Success(_) =>  textBuilder.setNCancelFlag(lp.isRevoke.toInt) //撤销标识
        case _ =>   }
      textBuilder.setStrXieChaDate(lp.assistDate) //协查日期
      textBuilder.setStrXieChaRequestUnitName(lp.assistUnitName) //协查单位名称
      textBuilder.setStrXieChaRequestUnitCode(lp.assistUnitCode) //协查单位代码

      textBuilder.build()
    }
    caseData.build()
  }

  /**
    * 根据接口获得的FPT文件构建matchtask protobuffer对象
    * 在构建过程中,需要解压图片获得原图，提取特征
    *
    * @param fpt4
    * @return
    */
  def FPT2MatchTaskProtoBuffer(fpt4:FPT4File):MatchTask = {

    val matchTask = MatchTask.newBuilder()
    fpt4.logic02Recs.foreach { tp =>
      matchTask.setMatchId(tp.personId)
      matchTask.setMatchType(NirvanaTypeDefinition.MatchType.FINGER_TT)
      matchTask.setPriority(1)
      matchTask.setObjectId(1)
      matchTask.setScoreThreshold(50)
      matchTask.setTopN(50)
      matchTask.setQueryid(fpt4.sid)
    }
    matchTask.build()
  }

  /**
    * 根据接口获得的FPT文件构建matchtask Case protobuffer对象
    * 在构建过程中,需要解压图片获得原图，提取特征
    *
    * @param fpt4
    * @return
    */
  def FPT2MatchTaskCaseProtoBuffer(fpt4:FPT4File):MatchTask = {

    val matchTask = MatchTask.newBuilder()
    fpt4.logic03Recs.foreach { lp =>
      matchTask.setMatchId(lp.cardId)
      matchTask.setMatchType(NirvanaTypeDefinition.MatchType.FINGER_LT)
      matchTask.setPriority(1)
      matchTask.setObjectId(1)
      matchTask.setScoreThreshold(50)
      matchTask.setTopN(50)
    }
    matchTask.build()
  }
}
