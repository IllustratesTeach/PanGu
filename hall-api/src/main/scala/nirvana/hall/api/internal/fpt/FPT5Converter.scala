package nirvana.hall.api.internal.fpt

import com.google.protobuf.ByteString
import nirvana.hall.api.HallApiConstants
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gfpt5lib.{FaceImage, LatentPalmImageMsg, _}
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FPT5MntConverter
import nirvana.hall.image.internal.FPT5ImageConverter
import nirvana.hall.protocol.api.FPTProto
import nirvana.hall.protocol.api.FPTProto.TPCard.TPCardBlob
import nirvana.hall.protocol.api.FPTProto.{LPCard, _}
import nirvana.hall.v70.internal.Gafis70Constants
import org.apache.commons.lang.StringUtils

import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.collection.mutable._


/**
  * FPT5数据转换类
  * Created by songpeng on 2017/11/3.
  */
object FPT5Converter {

  /**
    * 将捺印proto信息转为FingerprintPackage
    * //@param tpCard 捺印信息
    * @return
    */
  def convertTPCard2FingerprintPackage(tpCard: TPCard): FingerprintPackage ={
    val fingerprintPackage = new FingerprintPackage

    fingerprintPackage.descriptiveMsg = new DescriptMsg
    fingerprintPackage.descriptiveMsg.originalSystemCasePersonId = tpCard.getStrMisPersonID  //原始系统_案事件相关人员编号
    fingerprintPackage.descriptiveMsg.jingZongPersonId = tpCard.getStrJingZongPersonId  //由警综平台标准化采集系统发号
    fingerprintPackage.descriptiveMsg.casePersonid = tpCard.getStrCasePersonID  //案事件相关人员编号 由刑专系统发号，新数据必须提供
    fingerprintPackage.descriptiveMsg.fingerPalmCardId = tpCard.getStrCardID   //指掌纹卡编号 系统自用
    val captureInfoReasonCode = new mutable.ArrayBuffer[String]//采集信息原因代码集合
    tpCard.getCaptureInfoReasonCode.split(",").foreach{code=>
      captureInfoReasonCode += code
    }
    fingerprintPackage.descriptiveMsg.collectingReasonSet.captureInfoReasonCode = captureInfoReasonCode.toArray // 采集信息原因代码
    fingerprintPackage.descriptiveMsg.name = tpCard.getText.getStrName   //姓名
    fingerprintPackage.descriptiveMsg.alias = tpCard.getText.getStrAliasName    //别名/绰号
    fingerprintPackage.descriptiveMsg.sex = CodeConverterV70New.converGender(tpCard.getText.getNSex.toString)  //性别代码
    fingerprintPackage.descriptiveMsg.birthday = tpCard.getText.getStrBirthDate //出生日期
    fingerprintPackage.descriptiveMsg.nationality =  CodeConverterV70New.converNativeplace(tpCard.getText.getStrNation)  //国籍代码
    fingerprintPackage.descriptiveMsg.nation =  CodeConverterV70New.converNation(tpCard.getText.getStrRace) //民族代码
    fingerprintPackage.descriptiveMsg.credentialsCode = fpt5util.DEFAULT_CERTIFICATE_TYPE //常用证件代码
    fingerprintPackage.descriptiveMsg.credentialsNo = tpCard.getText.getStrCertifID //证件号码
    fingerprintPackage.descriptiveMsg.houkouAdministrativeDivisionCode = tpCard.getText.getStrHuKouPlaceCode //户籍地址行政区划代码
    fingerprintPackage.descriptiveMsg.houkouAddress = tpCard.getText.getStrHuKouPlaceTail //户籍地址地址名称
    fingerprintPackage.descriptiveMsg.houseAdministrativeDivisionCode = tpCard.getText.getStrAddrCode //现住址行政区划代码
    fingerprintPackage.descriptiveMsg.houseAddress = tpCard.getText.getStrAddr //现住址地址名称
    fingerprintPackage.descriptiveMsg.memo = tpCard.getText.getStrComment //备注
    //捺印采集信息节点
    fingerprintPackage.collectInfoMsg = new CollectInfoMsg
    fingerprintPackage.collectInfoMsg.fingerprintComparisonSysTypeDescript = fpt4code.GAIMG_CPRMETHOD_EGFS_CODE  //指纹比对系统描述
    fingerprintPackage.collectInfoMsg.chopUnitCode = tpCard.getText.getStrPrintUnitCode //捺印单位公安机关机构代码
    fingerprintPackage.collectInfoMsg.chopUnitName =  tpCard.getText.getStrPrintUnitName //捺印单位公安机关机构名称
    fingerprintPackage.collectInfoMsg.chopPersonName = tpCard.getText.getStrPrinter //捺印人员_姓名
    fingerprintPackage.collectInfoMsg.chopPersonIdCard = tpCard.getText.getStrPrinterIdCardNo //捺印人员_公民身份号码
    fingerprintPackage.collectInfoMsg.chopPersonTel = tpCard.getText.getStrPrinterPhone  //捺印人员_联系电话
    fingerprintPackage.collectInfoMsg.chopDateTime =  tpCard.getText.getStrPrintDate //捺印时间
    //先初始化捺印指纹包信息
    val fingerMsgs = new mutable.ArrayBuffer[FingerMsg]
    val knuckleprintMsgs = new mutable.ArrayBuffer[KnuckleprintMsg]
    val palmMsgs = new mutable.ArrayBuffer[PalmMsg]
    val fourprintMsgs = new mutable.ArrayBuffer[FourprintMsg]
    val fullpalmMsgs = new mutable.ArrayBuffer[FullpalmMsg]
    val faceImageMsgs = new mutable.ArrayBuffer[FaceImage]
    tpCard.getBlobList.foreach{
      blob => blob.getType match {
        //指纹
        case ImageType.IMAGETYPE_FINGER =>
          fingerMsgs += getFingerMsg(blob)
        //掌纹
        case ImageType.IMAGETYPE_PALM =>
          palmMsgs += getPalmMsg(blob)
        //指节纹
        case ImageType.IMAGETYPE_KNUCKLEPRINTS =>
          knuckleprintMsgs += getKnuckleprintMsg(blob)
        //四联指
        case ImageType.IMAGETYPE_FOURPRINT =>
          fourprintMsgs += getPalmFourPrintMsg(blob)
        //全掌
        case ImageType.IMAGETYPE_FULLPALM =>
          fullpalmMsgs += getFullPalmMsg(blob)
        //人像
        case ImageType.IMAGETYPE_FACE =>
          faceImageMsgs += getFaceImage(blob)
        case _ =>
      }
    }
    //判断捺印指纹包大小,如果有指纹信息再进行赋值
    if( fingerMsgs.nonEmpty) {
      val fingers = new Fingers()
      fingers.fingerMsg = fingerMsgs.toArray
      fingerprintPackage.fingers = fingers
    }
    if(knuckleprintMsgs.nonEmpty){
      val knuckleprints = new Knuckleprints()
      knuckleprints.knuckleprintMsg = knuckleprintMsgs.toArray
      fingerprintPackage.knuckleprints = knuckleprints
    }
    if(palmMsgs.nonEmpty){
      val palms = new Palms()
      palms.palmMsg = palmMsgs.toArray
      fingerprintPackage.palms = palms
    }
    if(fourprintMsgs.nonEmpty){
      val fourprints = new Fourprints()
      fourprints.fourprintMsg = fourprintMsgs.toArray
      fingerprintPackage.fourprints = fourprints
    }
    if(fullpalmMsgs.nonEmpty) {
      val fullpalms = new Fullpalms()
      fullpalms.fullpalmMsg = fullpalmMsgs.toArray
      fingerprintPackage.fullpalms = fullpalms
    }
    if(faceImageMsgs.nonEmpty){
      val faceImages = new FaceImages()
      faceImages.faceImage = faceImageMsgs.toArray
      fingerprintPackage.faceImages = faceImages
    }
    fingerprintPackage
  }

  /**
    * 将指纹包转换为tpcard protobuf对象
    * @param fingerprintPackage
    * @return
    */
  def convertFingerprintPackage2TPCard(fingerprintPackage:FingerprintPackage):TPCard = {
    val tpCard = TPCard.newBuilder()
    val textBuilder = tpCard.getTextBuilder
    tpCard.setStrCardID(dropHeadLetter(fingerprintPackage.descriptiveMsg.originalSystemCasePersonId,HallApiConstants.TPCARDNO_HEAD_LETTER))
    tpCard.setStrMisPersonID(fingerprintPackage.descriptiveMsg.originalSystemCasePersonId)
    if(null != fingerprintPackage.descriptiveMsg.jingZongPersonId) tpCard.setStrJingZongPersonId(fingerprintPackage.descriptiveMsg.jingZongPersonId)
    tpCard.setStrCasePersonID(fingerprintPackage.descriptiveMsg.casePersonid)
    tpCard.setStrDataSource(Gafis70Constants.DATA_SOURCE_FPT)
    textBuilder.setStrName(fingerprintPackage.descriptiveMsg.name)
    if(StringUtils.isNotEmpty(fingerprintPackage.descriptiveMsg.alias)
      && StringUtils.isNotBlank(fingerprintPackage.descriptiveMsg.alias)){
      textBuilder.setStrAliasName(fingerprintPackage.descriptiveMsg.alias)
    }
    textBuilder.setNSex(CodeConverterV70Old.converGender(fingerprintPackage.descriptiveMsg.sex).toInt)
    textBuilder.setStrNation(CodeConverterV70Old.converNativeplace(fingerprintPackage.descriptiveMsg.nationality))
    textBuilder.setStrRace(CodeConverterV70Old.converNation(fingerprintPackage.descriptiveMsg.nation))
    textBuilder.setStrCertifType(fingerprintPackage.descriptiveMsg.credentialsCode)
    tpCard.setCaptureInfoReasonCode(fingerprintPackage.descriptiveMsg.collectingReasonSet.captureInfoReasonCode.mkString(","))
    //处理常用证件类型
    if(fingerprintPackage.descriptiveMsg.credentialsCode != fpt5util.DEFAULT_CERTIFICATE_TYPE){
      textBuilder.setStrCertifID(fingerprintPackage.descriptiveMsg.credentialsNo)
    }else{
      textBuilder.setStrIdentityNum(fingerprintPackage.descriptiveMsg.credentialsNo)
    }
    textBuilder.setStrBirthDate(fingerprintPackage.descriptiveMsg.birthday)
    textBuilder.setStrIdentityNum(fingerprintPackage.descriptiveMsg.credentialsNo)
    textBuilder.setStrHuKouPlaceCode(fingerprintPackage.descriptiveMsg.houkouAdministrativeDivisionCode)
    textBuilder.setStrHuKouPlaceTail(fingerprintPackage.descriptiveMsg.houkouAddress)
    textBuilder.setStrAddrCode(fingerprintPackage.descriptiveMsg.houseAdministrativeDivisionCode)
    textBuilder.setStrAddr(fingerprintPackage.descriptiveMsg.houseAddress)
    textBuilder.setStrPrintUnitCode(fingerprintPackage.collectInfoMsg.chopUnitCode)
    textBuilder.setStrPrintUnitName(fingerprintPackage.collectInfoMsg.chopUnitName)
    textBuilder.setStrPrinter(fingerprintPackage.collectInfoMsg.chopPersonName)
    textBuilder.setStrPrintDate(fingerprintPackage.collectInfoMsg.chopDateTime)
    textBuilder.setStrPrinterIdCardNo(fingerprintPackage.collectInfoMsg.chopPersonIdCard)
    if(null != fingerprintPackage.collectInfoMsg.chopPersonTel)textBuilder.setStrPrinterPhone(fingerprintPackage.collectInfoMsg.chopPersonTel)
    if(StringUtils.isNotEmpty(fingerprintPackage.descriptiveMsg.memo)
      && StringUtils.isNotBlank(fingerprintPackage.descriptiveMsg.memo)){
      textBuilder.setStrComment(fingerprintPackage.descriptiveMsg.memo)
    }
    if(fingerprintPackage.fingers != null){
      fingerprintPackage.fingers.fingerMsg.foreach{
        t =>
          if(t.fingerImageData !=null && t.fingerImageData.length > 0){
            val blobBuilder = tpCard.addBlobBuilder
            blobBuilder.setType(ImageType.IMAGETYPE_FINGER)

            if(t.fingerPositionCode.toInt >10){
              blobBuilder.setBPlain(true)
              blobBuilder.setFgp(FingerFgp.valueOf(t.fingerPositionCode.toInt -10))
            }else{
              blobBuilder.setBPlain(false)
              blobBuilder.setFgp(FingerFgp.valueOf(t.fingerPositionCode.toInt))
            }
            blobBuilder.setStImageBytes(ByteString.copyFrom(FPT5ImageConverter.convertTPFingerImageData2GafisImage(t).toByteArray(AncientConstants.GBK_ENCODING)))
          }
      }
    }
    if(fingerprintPackage.palms != null){
      fingerprintPackage.palms.palmMsg.foreach{
        t =>
          if(t.palmImageData !=null && t.palmImageData.length > 0){
            val blobBuilder = tpCard.addBlobBuilder
            blobBuilder.setType(ImageType.IMAGETYPE_PALM)
            blobBuilder.setPalmfgp(string2PalmFgpPares(t.palmPostionCode)) //掌纹掌位代码
            blobBuilder.setStImageBytes(ByteString.copyFrom(FPT5ImageConverter.convertTPPalmImageData2GafisImage(t).toByteArray(AncientConstants.GBK_ENCODING)))
          }
      }
    }
    if(fingerprintPackage.fourprints != null){
      fingerprintPackage.fourprints.fourprintMsg.foreach{
        t =>
          if(t.fourPrintImageData != null && t.fourPrintImageData.length > 0){
            val blobBuilder = tpCard.addBlobBuilder()
            blobBuilder.setType(ImageType.IMAGETYPE_FOURPRINT)
            t.fourPrintPostionCode match {
              case "21" => blobBuilder.setPalmfgp(PalmFgp.PALM_FOUR_PRINT_RIGHT)
              case "22" => blobBuilder.setPalmfgp(PalmFgp.PALM_FOUR_PRINT_LEFT)
            }
            blobBuilder.setStImageBytes(ByteString.copyFrom(FPT5ImageConverter.convertTPFourPrintImageData2GafisImage(t).toByteArray(AncientConstants.GBK_ENCODING)))
          }
      }
    }
    if(fingerprintPackage.knuckleprints != null){
      fingerprintPackage.knuckleprints.knuckleprintMsg.foreach{
        t =>
          if(t.knucklePrintImageData!=null && t.knucklePrintImageData.length > 0){
            val blobBuilder = tpCard.addBlobBuilder()
            blobBuilder.setType(ImageType.IMAGETYPE_KNUCKLEPRINTS)
            blobBuilder.setFgp(FingerFgp.valueOf(t.knucklePrintPostionCode.toInt))//指节纹的位置 待定？？？
            blobBuilder.setStImageBytes(ByteString.copyFrom(FPT5ImageConverter.convertTPKnuckleprintMsgImageData2GafisImage(t).toByteArray(AncientConstants.GBK_ENCODING)))
          }
      }
    }
    if(fingerprintPackage.fullpalms != null){
      fingerprintPackage.fullpalms.fullpalmMsg.foreach{
        t =>
          if(t.fullPalmImageData !=null && t.fullPalmImageData.length > 0){
            val blobBuilder = tpCard.addBlobBuilder()
            blobBuilder.setType(ImageType.IMAGETYPE_FULLPALM)
            blobBuilder.setPalmfgp(PalmFgp.valueOf(t.fullPalmPostionCode.toInt))//全掌的掌位，待定？？？
            blobBuilder.setStImageBytes(ByteString.copyFrom(FPT5ImageConverter.convertTPFullPalmMsgImageData2GafisImage(t).toByteArray(AncientConstants.GBK_ENCODING)))
          }
      }
    }
    if(fingerprintPackage.faceImages != null){
      fingerprintPackage.faceImages.faceImage.foreach{
        t =>
          if(t.personPictureImageData !=null && t.personPictureImageData.length > 0){
            val blobBuilder = tpCard.addBlobBuilder()
            blobBuilder.setType(ImageType.IMAGETYPE_FACE)
            blobBuilder.setFacefgp(FaceFgp.valueOf(t.personPictureTypeCode.toInt))
            blobBuilder.setStImageBytes(ByteString.copyFrom(t.personPictureImageData))
          }
      }
    }
    tpCard.build()
  }

  /**
    * 获取LatentPackage
    * @param caseInfo 案件信息
    * @param fingerCards 现场卡片信息
    * @param palmCards 现场卡片信息
    * @return
    */
  def convertCaseInfoAndLPCard2LatentPackage(caseInfo: Case, fingerCards: Seq[LPCard], palmCards: Seq[LPCard]): LatentPackage = {
    val latentPackage = new LatentPackage
    val caseMsg = new CaseMsg
    val latentCollectInfoMsg = new LatentCollectInfoMsg
    var latentFingers = new ArrayBuffer[LatentFingers]
    var latentPalms = new ArrayBuffer[LatentPalms]

    caseMsg.originalSystemCaseId = appendHeadLetter(caseInfo.getStrCaseID,HallApiConstants.LPCARDNO_HEAD_LETTER) //原始系统_案事件编号
    caseMsg.caseId = caseInfo.getStrJingZongCaseId //案事件编号 --警综案事件编号，新数据必须提供
    caseMsg.latentSurveyId = caseInfo.getStrSurveyId //现场勘验编号
    caseMsg.latentCardId = caseInfo.getStrCaseID //现场指掌纹卡编号，系统自用。这里暂时使用案件编号
    //案件类别集合
    val caseTypeCodeList = new ArrayBuffer[String]
    if(caseInfo.getText.getStrCaseType1.nonEmpty){
      caseTypeCodeList += caseInfo.getText.getStrCaseType1
    }
    if(caseInfo.getText.getStrCaseType2.nonEmpty){
      caseTypeCodeList += caseInfo.getText.getStrCaseType2
    }
    if(caseInfo.getText.getStrCaseType3.nonEmpty){
      caseTypeCodeList += caseInfo.getText.getStrCaseType3
    }
    if(caseTypeCodeList.nonEmpty){
      caseMsg.caseClassSet = new CaseClassSet
      caseMsg.caseClassSet.caseTypeCode = caseTypeCodeList.toArray
    }

    caseMsg.money = caseInfo.getText.getStrMoneyLost //金额（人民币元）
    caseMsg.caseOccurAdministrativeDivisionCode = caseInfo.getText.getStrCaseOccurPlaceCode //案事件发生地点_行政区划代码
    caseMsg.caseOccurAddress = caseInfo.getText.getStrCaseOccurPlace //案事件发生地点_地址名称
    caseMsg.briefCase = caseInfo.getText.getStrBriefCase //简要案情
    caseMsg.whetherKill = if (caseInfo.getText.getBPersonKilled) "1" else "0" //是否命案_判断标识

    latentCollectInfoMsg.fingerprintComparisonSysTypeDescript = fpt4code.GAIMG_CPRMETHOD_EGFS_CODE //指纹比对系统类型描述
    latentCollectInfoMsg.extractUnitCode = caseInfo.getText.getStrExtractUnitCode //提取单位_公安机关机构代码
    latentCollectInfoMsg.extractUnitName = caseInfo.getText.getStrExtractUnitName //提取单位_公安机关名称
    latentCollectInfoMsg.extractPerson = caseInfo.getText.getStrExtractor //提取人员_姓名
    latentCollectInfoMsg.extractPersonIdCard = caseInfo.getText.getStrExtractorIdCard //提取人员_公民身份号码
    latentCollectInfoMsg.extractPersonTel = caseInfo.getText.getStrExtractorTel //提取人员_联系电话
    latentCollectInfoMsg.extractDateTime = caseInfo.getText.getStrExtractDate //提取时间

    if(fingerCards != null){
      fingerCards.foreach(latentFingers += convertLPCards2LatentFingers(_))
    }
    if(palmCards != null){
      palmCards.foreach(latentPalms += convertPalmCards2LatentPalms(_))
    }
    latentPackage.caseMsg = caseMsg
    latentPackage.latentCollectInfoMsg = latentCollectInfoMsg
    latentPackage.latentFingers = latentFingers.toArray
    latentPackage.latentPalms = latentPalms.toArray
    latentPackage
  }

  def convertLatentPackage2Case(latentPackage: LatentPackage): Case = {
    val caseInfo = Case.newBuilder()
    val textBuilder = caseInfo.getTextBuilder
    caseInfo.setStrCaseID(dropHeadLetter(latentPackage.caseMsg.caseId,HallApiConstants.LPCARDNO_HEAD_LETTER))
    caseInfo.setStrJingZongCaseId(latentPackage.caseMsg.caseId) //警综案事件编号
    caseInfo.setStrSurveyId(latentPackage.caseMsg.latentSurveyId) //现场勘验编号
    if(null != latentPackage.latentFingers){
      caseInfo.setNCaseFingerCount(latentPackage.latentFingers.length) //现场指纹个数
      latentPackage.latentFingers.foreach(
        latentFingers => caseInfo.addStrFingerID(latentFingers.latentFingerImageMsg.originalSystemLatentFingerPalmId)
      )
    }
    if(null != latentPackage.caseMsg.caseClassSet){
      latentPackage.caseMsg.caseClassSet.caseTypeCode.size match{
        case 1  =>
          textBuilder.setStrCaseType1(latentPackage.caseMsg.caseClassSet.caseTypeCode(0).toString)
        case 2  =>
          textBuilder.setStrCaseType1(latentPackage.caseMsg.caseClassSet.caseTypeCode(0).toString)
          textBuilder.setStrCaseType2(latentPackage.caseMsg.caseClassSet.caseTypeCode(1).toString)
        case 3  =>
          textBuilder.setStrCaseType1(latentPackage.caseMsg.caseClassSet.caseTypeCode(0).toString) //案件类别
          textBuilder.setStrCaseType2(latentPackage.caseMsg.caseClassSet.caseTypeCode(1).toString)
          textBuilder.setStrCaseType3(latentPackage.caseMsg.caseClassSet.caseTypeCode(2).toString)
        case _  =>
      }
    }
    textBuilder.setStrMoneyLost(Option(latentPackage.caseMsg.money).getOrElse("")) //涉案金额
    textBuilder.setStrCaseOccurPlaceCode(latentPackage.caseMsg.caseOccurAdministrativeDivisionCode) //案发地点代码
    textBuilder.setStrCaseOccurPlace(latentPackage.caseMsg.caseOccurAddress) //案发地址详情
    textBuilder.setStrBriefCase(Option(latentPackage.caseMsg.briefCase).getOrElse("")) //简要案情
    textBuilder.setBPersonKilled("1".equals(latentPackage.caseMsg.whetherKill)) //命案标识
    textBuilder.setStrExtractUnitCode(latentPackage.latentCollectInfoMsg.extractUnitCode) //提取单位代码
    textBuilder.setStrExtractUnitName(latentPackage.latentCollectInfoMsg.extractUnitName) //提取单位
    textBuilder.setStrExtractor(latentPackage.latentCollectInfoMsg.extractPerson) //提取人
    textBuilder.setStrExtractorIdCard(latentPackage.latentCollectInfoMsg.extractPersonIdCard) //提取人
    if(null != latentPackage.latentCollectInfoMsg.extractPersonTel)textBuilder.setStrExtractorTel(latentPackage.latentCollectInfoMsg.extractPersonTel) //提取人
    textBuilder.setStrExtractDate(latentPackage.latentCollectInfoMsg.extractDateTime) //提取时间
    //6.2案件信息掌纹列表和指纹列表
    if(Option(latentPackage.latentFingers).nonEmpty){
      latentPackage.latentFingers.foreach{
        finger =>
          val fingerID = latentPackage.caseMsg.caseId + finger.latentFingerImageMsg.latentPhysicalId.substring(finger.latentFingerImageMsg.latentPhysicalId.length-2)
          caseInfo.addStrFingerID(dropHeadLetter(fingerID,HallApiConstants.LPCARDNO_HEAD_LETTER))
      }
    }
    if(Option(latentPackage.latentPalms).nonEmpty){
      latentPackage.latentPalms.foreach{
        palm =>
          val palmID = latentPackage.caseMsg.caseId + palm.latentPalmImageMsg.latentPalmPhysicalId.substring(palm.latentPalmImageMsg.latentPalmPhysicalId.length-2)
          caseInfo.addStrPalmID(dropHeadLetter(palmID,HallApiConstants.LPCARDNO_HEAD_LETTER))
      }
    }
    caseInfo.build()
  }

  def convertLatentPackage2LPCard(latentPackage: LatentPackage): Seq[LPCard] = {
    val lpCardList = new ArrayBuffer[LPCard]
    if (null != latentPackage.latentFingers) {
      latentPackage.latentFingers.foreach {
        t =>
          if (null != t.latentFingerImageMsg) {
            val lpCard = LPCard.newBuilder()
            val blobBuilder = lpCard.getBlobBuilder
            val xcwzbh = t.latentFingerImageMsg.latentPhysicalId
            lpCard.setStrCardID(dropHeadLetter(latentPackage.caseMsg.caseId,HallApiConstants.LPCARDNO_HEAD_LETTER) + xcwzbh.substring(xcwzbh.length-2))   //现场指掌纹编号 :案事件编号+现场物证编号后两位
            lpCard.setStrPhysicalId(t.latentFingerImageMsg.latentPhysicalId)
            val textBuilder = lpCard.getTextBuilder
            textBuilder.setStrCaseId(dropHeadLetter(latentPackage.caseMsg.caseId,HallApiConstants.LPCARDNO_HEAD_LETTER))
            textBuilder.setStrSeq(xcwzbh.substring(xcwzbh.length-2)) // 取最后两位
            textBuilder.setStrRemainPlace(t.latentFingerImageMsg.latentFingerLeftPosition) //遗留部位
            textBuilder.setStrRidgeColor(t.latentFingerImageMsg.latentFingerMastoidProcessLineColorCode) //乳突线颜色
            textBuilder.setBDeadBody("1".equals(t.latentFingerImageMsg.latentFingerCorpseJudgeIdentify)) //未知名尸体标识
            if (null != t.latentFingerImageMsg.latentFingerConnectFingerBeginPhysicalId) {
              textBuilder.setStrStart(t.latentFingerImageMsg.latentFingerConnectFingerBeginPhysicalId) //联指开始序号
            }
            if (null != t.latentFingerImageMsg.latentFingerConnectFingerEndPhysicalId) {
              textBuilder.setStrEnd(Option(t.latentFingerImageMsg.latentFingerConnectFingerEndPhysicalId).getOrElse("")) //联指结束序号
            }

            if (null != t.latentFingerImageMsg.latentFingerImageData && t.latentFingerImageMsg.latentFingerImageData.length > 0) {
              blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
              blobBuilder.setStImageBytes(ByteString.copyFrom(FPT5ImageConverter.convertLPFingerImageData2GafisImage(t.latentFingerImageMsg).toByteArray()))
            }
            textBuilder.setNBiDuiState(t.latentFingerImageMsg.latentFingerComparisonStatusCode.toInt)

            if(null != t.latentFingerFeatureMsg) {
              t.latentFingerFeatureMsg.foreach {
                f =>
                  lpCard.setStrCardID(f.originalSystemLatentFingerPalmId)
                  lpCard.setStrPhysicalId(f.latentPhysicalId)
                  val gafisMnt = FPT5MntConverter.convertFingerLDataMnt2GafisMnt(t.latentFingerImageMsg, f)
                  blobBuilder.setStMntBytes(ByteString.copyFrom(gafisMnt.toByteArray()))
                  textBuilder.setStrFeatureGroupIdentifier(f.latentFeatureGroupIdentifier)
                  textBuilder.setStrFeatureGroupDscriptInfo(Option(f.latentFeatureGroupDscriptInfo).getOrElse(""))
              }
            }
            lpCardList += lpCard.build
          }
      }
    }
    if (null != latentPackage.latentPalms) {
      latentPackage.latentPalms.foreach {
        t =>
          if (null != t.latentPalmImageMsg) {
            val lpCard = LPCard.newBuilder()
            val textBuilder = lpCard.getTextBuilder
            val blobBuilder = lpCard.getBlobBuilder
            val xcwzbh = t.latentPalmImageMsg.latentPalmPhysicalId
            lpCard.setStrCardID(dropHeadLetter(latentPackage.caseMsg.caseId,HallApiConstants.LPCARDNO_HEAD_LETTER) + xcwzbh.substring(xcwzbh.length-2))  //现场指掌纹编号 :案事件编号+现场物证编号后两位
            lpCard.setStrPhysicalId(t.latentPalmImageMsg.latentPalmPhysicalId)
            textBuilder.setStrCaseId(dropHeadLetter(latentPackage.caseMsg.caseId,HallApiConstants.LPCARDNO_HEAD_LETTER))
            textBuilder.setStrSeq(xcwzbh.substring(xcwzbh.length-2))
            textBuilder.setStrRemainPlace(t.latentPalmImageMsg.latentPalmLeftPostion) //遗留部位
            textBuilder.setStrRidgeColor(t.latentPalmImageMsg.latentPalmMastoidProcessLineColorCode) //乳突线颜色
            textBuilder.setBDeadBody("1".equals(t.latentPalmImageMsg.latentPalmCorpseJudgeIdentify)) //未知名尸体标识

            if (null != t.latentPalmImageMsg.latentPalmImageData &&
              t.latentPalmImageMsg.latentPalmImageData.length > 0) {
              blobBuilder.setType(ImageType.IMAGETYPE_PALM)
              blobBuilder.setStImageBytes(ByteString.copyFrom(FPT5ImageConverter.convertLPPalmImageData2GafisImage(t.latentPalmImageMsg).toByteArray()))
            }
            textBuilder.setNBiDuiState(t.latentPalmImageMsg.latentPalmComparisonStatusCode.toInt)

            if(null != t.latentPalmFeatureMsg){
              t.latentPalmFeatureMsg.foreach {
                f =>
                  lpCard.setStrPhysicalId(f.latentPalmPhysicalId)
                  val gafisMnt = FPT5MntConverter.convertPalmLDataMnt2GafisMnt(t.latentPalmImageMsg, f)
                  blobBuilder.setStMntBytes(ByteString.copyFrom(gafisMnt.toByteArray()))
                  textBuilder.setStrFeatureGroupIdentifier(f.latentPalmFeatureGroupIdentifier)
                  textBuilder.setStrFeatureGroupDscriptInfo(f.latentPalmFeatureDscriptInfo)
              }
            }
            lpCardList += lpCard.build
          }
      }
    }
    lpCardList
  }



  /**
    * 去掉首字母
    * @param caseId
    * @return
    */
  private def dropHeadLetter(caseId: String,letter:String): String = {
    var str = ""
    if (caseId.toUpperCase.startsWith(letter))
      str = caseId.toUpperCase.drop(1)
    else str = caseId.toUpperCase
    str
  }

  /**
    * 拼接首字母
    * @param caseId
    * @return
    */
  private def appendHeadLetter(caseId: String,letter:String): String = {
    var str = ""
    if (!caseId.toUpperCase.startsWith(letter))
      str = letter.concat(caseId.toUpperCase)
    else
      str = caseId.toUpperCase

    str
  }

  /**
    * 更新6.2的案件信息的现场指纹列表和掌纹列表
    *
    * @param newCaseInfo
    * @param oldCaseInfo
    * @return
    */
  def updateCaseFingerorPalmIDList(newCaseInfo: Case, oldCaseInfo: Case): Case = {
    val caseBuilder = newCaseInfo.toBuilder
    oldCaseInfo.getStrPalmIDList.foreach {
      palmID =>
        if (!caseBuilder.getStrPalmIDList.contains(palmID))
          caseBuilder.addStrPalmID(palmID)
    }
    oldCaseInfo.getStrFingerIDList.foreach {
      fingerID =>
        if (!caseBuilder.getStrFingerIDList.contains(fingerID))
          caseBuilder.addStrFingerID(fingerID)
    }
    caseBuilder.build()
  }


  /**
    * 获取单枚指纹节点信息
    * @param blob
    * @return
    */
  private def getFingerMsg(blob:TPCardBlob): FingerMsg ={
    //图像转换
    val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
    //gafis图像转换为FPT标准上要求的图像格式
    val fingerMsg = FPT5ImageConverter.convertGAFISIMAGESTRUCT2FingerMsg(gafisImage)

    val gafisMnt = new GAFISIMAGESTRUCT().fromByteArray(blob.getStMntBytes.toByteArray())
    //gafis特征转换成FPT特征
    FPT5MntConverter.convertGafisMnt2FingerMsg(gafisMnt, fingerMsg)
    fingerMsg.fingerCustomInfo = new Array[Byte](255)
    fingerMsg.adactylismCauseCode = fpt5util.FINGER_LOST_NORMAL
    fingerMsg
  }

  /**
    * 获取单枚掌纹节点信息
    * @param blob
    * @return
    */
  private def getPalmMsg(blob:TPCardBlob):PalmMsg = {
    //图像转换
    val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
    val palmMsg = FPT5ImageConverter.convertGAFISIMAGESTRUCT2PalmMsg(gafisImage)
    //特征转换
    val gafisMnt = new GAFISIMAGESTRUCT().fromByteArray(blob.getStMntBytes.toByteArray)
    FPT5MntConverter.convertGafisMnt2PalmMsg(gafisMnt, palmMsg)

    palmMsg.palmPostionCode = palmFgpPares2String(blob.getPalmfgp)
    palmMsg.lackPalmCauseCode = fpt5util.FINGER_LOST_NORMAL
    palmMsg
  }

  /**
    * 获取四连指节点信息
    * @param blob
    * @return
    */
  private def getPalmFourPrintMsg(blob:TPCardBlob):FourprintMsg = {
    val fourprintMsg = new FourprintMsg
    val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
    FPT5ImageConverter.convertGAFISIMAGESTRUCT2fourPrintMsg(gafisImage)
  }

  /**
    * 获取单个指节纹节点信息
    * @param blob
    * @return
    */
  private def getKnuckleprintMsg(blob:TPCardBlob): KnuckleprintMsg ={
    val knuckleprintMsg = new KnuckleprintMsg
    val gafisMnt = new GAFISIMAGESTRUCT
    gafisMnt.fromStreamReader(blob.getStMntBytes.newInput())
    val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
    FPT5ImageConverter.convertGAFISIMAGESTRUCT2KnuckleprintMsg(gafisMnt)
  }

  /**
    * 获取单个全掌纹节点信息
    * @param blob
    * @return
    */
  private def getFullPalmMsg(blob:TPCardBlob):FullpalmMsg = {
    val fullpalmMsg = new FullpalmMsg
    val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
    FPT5ImageConverter.convertGAFISIMAGESTRUCT2fullPalmMsg(gafisImage)
  }

  /**
    * 获取单个人像节点信息
    * @param blob
    * @return
    */
  private def getFaceImage(blob:TPCardBlob):FaceImage = {
    val faceImage = new FaceImage
    val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
    faceImage.personPictureTypeCode = fgpPares(blob.getFacefgp)
    faceImage.personPictureFileLayout = "jpeg"//gafisImage.stHead.nCompressMethod = GAIMG_CPRMETHOD_JPG
    faceImage.personPictureImageData = gafisImage.bnData
    faceImage
  }



  /**
    * 人像转换
    *
    * @param fgp
    * @return
    */
  private def fgpPares(fgp:FPTProto.FaceFgp): String ={
    fgp match {
      case FaceFgp.FACE_FRONT =>
        "1"
      case FaceFgp.FACE_RIGHT =>
        "2"
      case FaceFgp.FACE_LEFT =>
        "3"
      case _ =>
        "0"
    }
  }

  /**
    * 将ProtoBuffer的掌纹类型转换成系统中的枚举类型,
    */
  private def palmFgpPares2String(fgp:PalmFgp): String = {
    fgp match {
      case PalmFgp.PALM_RIGHT => "31"
      case PalmFgp.PALM_LEFT => "32"
      case PalmFgp.PALM_RIGTH_SIDE => "33"
      case PalmFgp.PALM_LEFT_SIDE => "34"
      case PalmFgp.PALM_FULL_PALM_RIGHT => "35"
      case PalmFgp.PALM_FULL_PALM_LEFT => "36"
      case PalmFgp.PALM_UNKNOWN => "39"
    }
  }

  /**
    * 将系统中的枚举类型转换成ProtoBuffer的掌纹类型,
    * @param palmPostionCode
    * @return
    */
  private def string2PalmFgpPares(palmPostionCode:String):PalmFgp = {
    palmPostionCode match {
      case "31" => PalmFgp.PALM_RIGHT
      case "32" => PalmFgp.PALM_LEFT
      case "33" => PalmFgp.PALM_RIGTH_SIDE
      case "34" => PalmFgp.PALM_LEFT_SIDE
      case "35" => PalmFgp.PALM_FULL_PALM_RIGHT
      case "36" => PalmFgp.PALM_FULL_PALM_LEFT
      case "39" => PalmFgp.PALM_UNKNOWN
    }
  }


  /**
    * 转换为fpt5LatentFingers
    * @param lpcard 现场卡片信息
    * @return
    */
  private def convertLPCards2LatentFingers(lpcard: LPCard): LatentFingers ={
    val latentFingers = new LatentFingers
    var latentImageMsg:LatentFingerImageMsg = null
    var latentFingerFeatureMsg = new ArrayBuffer[LatentFingerFeatureMsg]
    latentImageMsg = new LatentFingerImageMsg
    latentImageMsg.originalSystemLatentFingerPalmId = lpcard.getStrCardID //原始系统_现场指掌纹编号
    if (StringUtils.isNotEmpty(lpcard.getStrPhysicalId) && StringUtils.isNotBlank(lpcard.getStrPhysicalId)) {
      latentImageMsg.latentPhysicalId = lpcard.getStrPhysicalId //现场物证编号
    } else {
      latentImageMsg.latentPhysicalId = fpt5util.gerenateLatentPhysicalIdTake(fpt5util.PHYSICAL_TYPE_CODE_FINGER) //TODO:添加一个现场物证编号的三位顺序号的序列生成器
    }
    latentImageMsg.latentFingerLeftPosition = lpcard.getText.getStrRemainPlace //现场指纹_现场指掌纹遗留部位
    latentImageMsg.latentFingerCorpseJudgeIdentify = if (lpcard.getText.getBDeadBody) "1" else "0" //现场指纹_尸体指掌纹_判断标识
    latentImageMsg.latentFingerMastoidProcessLineColorCode = lpcard.getText.getStrRidgeColor //现场指纹_乳突线颜色代码
    latentImageMsg.latentFingerConnectFingerBeginPhysicalId = lpcard.getText.getStrStart //现场指纹_连指开始_现场物证编号
    latentImageMsg.latentFingerConnectFingerEndPhysicalId = lpcard.getText.getStrEnd //现场指纹_连指结束_现场物证编号
    latentImageMsg.latentFingerComparisonStatusCode = lpcard.getText.getNBiDuiState.toString //现场指纹_指纹比对状态代码
    latentImageMsg.latentFingerCustomInfo = lpcard.getText.getStrComment.getBytes() //现场指纹_自定义信息

    val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(lpcard.getBlob.getStImageBytes.toByteArray)
    FPT5ImageConverter.convertGAFISIMAGESTRUCT2LatentFingerImageMsg(gafisImage, latentImageMsg)
    if (lpcard.getBlob.getStMnt.nonEmpty) {
      //判断是否有特征
      val latentFeatureMsg = new LatentFingerFeatureMsg
      val gafisMnt = new GAFISIMAGESTRUCT().fromByteArray(lpcard.getBlob.getStMntBytes.toByteArray)
      FPT5MntConverter.convertGafisMnt2LatentFingerFeatureMsg(gafisMnt, latentFeatureMsg)
      latentFeatureMsg.originalSystemLatentFingerPalmId = lpcard.getStrCardID
      if (StringUtils.isNotEmpty(lpcard.getStrPhysicalId) && StringUtils.isNotBlank(lpcard.getStrPhysicalId)) {
        latentFeatureMsg.latentPhysicalId = lpcard.getStrPhysicalId //现场物证编号
      } else {
        latentFeatureMsg.latentPhysicalId = fpt5util.gerenateLatentPhysicalIdTake(fpt5util.PHYSICAL_TYPE_CODE_FINGER) //TODO:添加一个现场物证编号的三位顺序号的序列生成器
      }
      latentFeatureMsg.latentFeatureGroupIdentifier = lpcard.getText.getStrFeatureGroupIdentifier
      latentFeatureMsg.latentFeatureGroupDscriptInfo = lpcard.getText.getStrFeatureGroupDscriptInfo
      latentFeatureMsg.fingerAnalysisPostionBrief = convertFingerFgp2FPT5(lpcard.getBlob.getFgpList)
      latentFeatureMsg.fingerPatternAnalysisBrief = convertPatternType2FPT5(lpcard.getBlob.getRpList)
      //指位和纹型转换
      latentImageMsg.latentFingerAnalysisPostionBrief = convertFingerFgp2FPT5(lpcard.getBlob.getFgpList)
      latentImageMsg.latentFingerPatternAnalysisBrief = convertPatternType2FPT5(lpcard.getBlob.getRpList)

      latentFingerFeatureMsg += latentFeatureMsg
    }

    latentFingers.latentFingerImageMsg = latentImageMsg
    latentFingers.latentFingerFeatureMsg = latentFingerFeatureMsg.toArray
    latentFingers
  }

  /**
    * 分析指位转换为fpt5格式
    * @param fgpList 分析指位列表proto
    * @return 从左至右，每字符依次对应指位01-10，如果对应位为1表示有可能是这个指位，为0表示不可能是这个指位
    */
  private def convertFingerFgp2FPT5(fgpList: Seq[FingerFgp]): String={
    var str = ""
    (1 to 10).foreach{i=>
      if(fgpList.exists(p=> p.getNumber == i)){
        str = "1"+str
      }else{
        str = "0"+str
      }
    }
    str
  }

  /**
    * 候选纹型转为fpt5格式
    * @param patternType 分析纹型列表proto, 目前只有4种
    * @return 从左至右每字符对应指纹纹型分类代码1到7，每字符有效值为1或0，1表示可能的分类，0表示不可能的分类
    */
  private def convertPatternType2FPT5(patternType: Seq[PatternType]): String={
    var str = ""
    (1 to 7).foreach{i=>
      if(patternType.exists(p=> p.getNumber == i)){
        str = "1"+str
      }else{
        str = "0"+str
      }
    }
    str
  }

  /**
    * 分析掌位转换为fpt5格式
    * @param fgpList 分析掌位proto
    * @return 从左至右，每字符依次对应指位左右掌，如果对应位为1表示有可能是这个掌位，为0表示不可能是这个掌位
    */
  private def convertPalmFgp2FPT5(fgpList: PalmFgp): String={
    var str = ""
    if(fgpList.getNumber == 1){
      str = "10"  //右掌
    }else{
      str = "01"  //左掌
    }
    str
  }

  /**
    * 现场掌纹LPCard转换为LatentPalms
    * @param palm
    * @return
    */
  private def convertPalmCards2LatentPalms(palm: LPCard): LatentPalms ={
    val latentPalms = new LatentPalms
    var latentPalmFeatureMsgList = new ArrayBuffer[LatentPalmFeatureMsg]
    var latentPalmImageMsg:LatentPalmImageMsg = null
    latentPalmImageMsg = new LatentPalmImageMsg
    val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(palm.getBlob.getStImageBytes.toByteArray)
    latentPalmImageMsg.latentPalmId = palm.getStrCardID  //现场掌纹_现场指掌纹编号
    latentPalmImageMsg.latentPalmPhysicalId = palm.getStrPhysicalId  //现场掌纹_现场物证编号
    if(StringUtils.isNotEmpty(palm.getStrPhysicalId) && StringUtils.isNotBlank(palm.getStrPhysicalId)){
      latentPalmImageMsg.latentPalmPhysicalId = palm.getStrPhysicalId //现场物证编号
    }else{
      latentPalmImageMsg.latentPalmPhysicalId = fpt5util.gerenateLatentPhysicalIdTake(fpt5util.PHYSICAL_TYPE_CODE_PALM) //TODO:添加一个现场物证编号的三位顺序号的序列生成器
    }
    latentPalmImageMsg.latentPalmLeftPostion = palm.getText.getStrRemainPlace  //现场掌纹_现场指掌纹遗留部位
    latentPalmImageMsg.latentPalmCorpseJudgeIdentify = if(palm.getText.getBDeadBody) "1" else "0"  //现场掌纹_尸体指掌纹_判断标识
    latentPalmImageMsg.latentPalmMastoidProcessLineColorCode = palm.getText.getStrRidgeColor //现场掌纹_乳突线颜色代码
    latentPalmImageMsg.latentPalmComparisonStatusCode = palm.getText.getNBiDuiState.toString  //现场掌纹_掌纹比对状态代码
    latentPalmImageMsg.latentPalmCustomInfo = palm.getText.getStrComment.getBytes()  //现场掌纹_自定义信息
    latentPalmImageMsg.latentPalmPostionAnalysisBriefly = convertPalmFgp2FPT5(palm.getBlob.getPalmFgp)
    FPT5ImageConverter.convertGAFISIMAGESTRUCT2LatentPalmImageMsg(gafisImage, latentPalmImageMsg)
    if(palm.getBlob.getStMnt.nonEmpty){//判断是否有特征
    val latentPalmFeatureMsg = new LatentPalmFeatureMsg
      latentPalmFeatureMsg.latentPalmDeltaSet = new LatentPalmDeltaSet
      val gafisMnt = new GAFISIMAGESTRUCT().fromByteArray(palm.getBlob.getStMntBytes.toByteArray)
      FPT5MntConverter.convertGafisMnt2LatentPalmFeatureMsg(gafisMnt, latentPalmFeatureMsg)
      latentPalmFeatureMsg.latentPalmNo = palm.getStrCardID //现场掌纹_现场指掌纹编号
      if(StringUtils.isNotEmpty(palm.getStrPhysicalId) && StringUtils.isNotBlank(palm.getStrPhysicalId)){
        latentPalmFeatureMsg.latentPalmPhysicalId = palm.getStrPhysicalId //现场物证编号
      }else{
        latentPalmFeatureMsg.latentPalmPhysicalId = fpt5util.gerenateLatentPhysicalIdTake(fpt5util.PHYSICAL_TYPE_CODE_PALM) //TODO:添加一个现场物证编号的三位顺序号的序列生成器
      }
      latentPalmFeatureMsg.latentPalmFeatureGroupIdentifier = palm.getText.getStrFeatureGroupIdentifier
      latentPalmFeatureMsg.latentPalmFeatureDscriptInfo = palm.getText.getStrFeatureGroupDscriptInfo
      latentPalmFeatureMsg.latentPalmComparisonStatusCode = palm.getText.getNBiDuiState.toString //现场掌纹_指掌纹比对状态代码
      latentPalmFeatureMsg.latentPalmAnalysisBrief = convertPalmFgp2FPT5(palm.getBlob.getPalmFgp)
      //TODO 掌纹折返点, 掌位分析, 现场掌纹_特征组合描述, 特征组标识符
      latentPalmFeatureMsgList += latentPalmFeatureMsg
    }
    latentPalms.latentPalmImageMsg = latentPalmImageMsg
    latentPalms.latentPalmFeatureMsg = latentPalmFeatureMsgList.toArray
    latentPalms
  }
}

