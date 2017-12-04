package nirvana.hall.api.internal.fpt



import com.google.protobuf.ByteString
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.{FPT4File, fpt4code}
import nirvana.hall.c.services.gfpt5lib.{FaceImage, LatentPalmImageMsg, _}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.{FPT5MntConverter, FPTMntConverter}
import nirvana.hall.image.internal.{FPT5ImageConverter, FPTImageConverter}
import nirvana.hall.protocol.api.FPTProto
import nirvana.hall.protocol.api.FPTProto.TPCard.TPCardBlob
import nirvana.hall.protocol.api.FPTProto.{LPCard, _}
import org.apache.commons.lang.StringUtils

import scala.collection.mutable
import scala.collection.mutable._
import scala.collection.JavaConversions._


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
    fingerprintPackage.descriptiveMsg.sex = tpCard.getText.getNSex.toString  //性别代码
    fingerprintPackage.descriptiveMsg.birthday = tpCard.getText.getStrBirthDate //出生日期
    fingerprintPackage.descriptiveMsg.nationality =  tpCard.getText.getStrRace //国籍代码
    fingerprintPackage.descriptiveMsg.nation = tpCard.getText.getStrNation //民族代码
    fingerprintPackage.descriptiveMsg.credentialsCode = tpCard.getText.getStrCertifType  //常用证件代码
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
  def fgpPares(fgp:FPTProto.FaceFgp): String ={
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
  def palmFgpPares2String(fgp:PalmFgp): String = {
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
  def string2PalmFgpPares(palmPostionCode:String):PalmFgp = {
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
    * 将指纹包转换为tpcard protobuf对象
    * @param fingerprintPackage
    * @return
    */
  def convertFingerprintPackage2TPCard(fingerprintPackage:FingerprintPackage):TPCard = {
    val tpCard = TPCard.newBuilder()
    val textBuilder = tpCard.getTextBuilder
    tpCard.setStrCardID(fingerprintPackage.descriptiveMsg.fingerPalmCardId)
    tpCard.setStrMisPersonID(fingerprintPackage.descriptiveMsg.originalSystemCasePersonId)
    tpCard.setStrJingZongPersonId(fingerprintPackage.descriptiveMsg.jingZongPersonId)
    tpCard.setStrCasePersonID(fingerprintPackage.descriptiveMsg.casePersonid)
    textBuilder.setStrName(fingerprintPackage.descriptiveMsg.name)
    if(StringUtils.isNotEmpty(fingerprintPackage.descriptiveMsg.alias)
      && StringUtils.isNotBlank(fingerprintPackage.descriptiveMsg.alias)){
      textBuilder.setStrAliasName(fingerprintPackage.descriptiveMsg.alias)
    }
    textBuilder.setNSex(fingerprintPackage.descriptiveMsg.sex.toInt)
    textBuilder.setStrNation(fingerprintPackage.descriptiveMsg.nation)
    textBuilder.setStrRace(fingerprintPackage.descriptiveMsg.nationality)
    textBuilder.setStrCertifType(fingerprintPackage.descriptiveMsg.credentialsCode)
    textBuilder.setStrCertifID(fingerprintPackage.descriptiveMsg.credentialsNo)
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
    tpCard.setStrPrinterIdCardId(fingerprintPackage.collectInfoMsg.chopPersonIdCard)
    tpCard.setStrPrinterTel(fingerprintPackage.collectInfoMsg.chopPersonTel)
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

    caseMsg.originalSystemCaseId = caseInfo.getStrCaseID //原始系统_案事件编号
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
      val latentFinger = convertCaseInfoAndLPCards2LatentFingers(caseInfo,fingerCards)
      latentFingers += latentFinger
    }
    if(palmCards != null){
      val latentPalm = convertCaseInfoAndPalmCards2LatentPalms(caseInfo,palmCards)
      latentPalms += latentPalm
    }
    latentPackage.caseMsg = caseMsg
    latentPackage.latentCollectInfoMsg = latentCollectInfoMsg
    latentPackage.latentFingers = latentFingers.toArray
    latentPackage.latentPalms = latentPalms.toArray
    latentPackage
  }

  /**
    * 转换为fpt5LatentFingers
    * @param caseInfo 案件信息
    * @param lpCards 现场卡片信息
    * @return
    */
  def convertCaseInfoAndLPCards2LatentFingers(caseInfo: Case,lpCards: Seq[LPCard]): LatentFingers ={
    val latentFingers = new LatentFingers
    var latentImageMsg:LatentFingerImageMsg = null
    var latentFingerFeatureMsg = new ArrayBuffer[LatentFingerFeatureMsg]
    lpCards.foreach(lpcard => {
      latentImageMsg = new LatentFingerImageMsg
      latentImageMsg.originalSystemLatentFingerPalmId = lpcard.getStrCardID //原始系统_现场指掌纹编号
      if(StringUtils.isNotEmpty(lpcard.getStrPhysicalId) && StringUtils.isNotBlank(lpcard.getStrPhysicalId)){
        latentImageMsg.latentPhysicalId = lpcard.getStrPhysicalId //现场物证编号
      }else{
        latentImageMsg.latentPhysicalId = fpt5util.gerenateLatentPhysicalIdTake("") //TODO:添加一个现场物证编号的三位顺序号的序列生成器
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
      if (lpcard.getBlob.getStMnt.nonEmpty) {//判断是否有特征
      val latentFeatureMsg = new LatentFingerFeatureMsg
        val gafisMnt = new GAFISIMAGESTRUCT().fromByteArray(lpcard.getBlob.getStMntBytes.toByteArray)
        FPT5MntConverter.convertGafisMnt2LatentFingerFeatureMsg(gafisMnt, latentFeatureMsg)
        //TODO 指位和纹型转换, 指纹方向
        lpcard.getBlob.getFgpList.foreach{fgp=>
//          latentImageMsg.latentFingerAnalysisPostionBrief = fgp
        }
        lpcard.getBlob.getRpList.foreach{pattern=>
//          latentImageMsg.latentFingerPatternAnalysisBrief = //现场指纹_纹型分析_简要情况
        }
        latentFingerFeatureMsg += latentFeatureMsg
      }
    })

    latentFingers.latentFingerImageMsg = latentImageMsg
    latentFingers.latentFingerFeatureMsg = latentFingerFeatureMsg.toArray
    latentFingers
  }

  def convertCaseInfoAndPalmCards2LatentPalms(caseInfo: Case,palmCards: Seq[LPCard]): LatentPalms ={
    val latentPalms = new LatentPalms
    var latentPalmFeatureMsgList = new ArrayBuffer[LatentPalmFeatureMsg]
    var latentPalmImageMsg:LatentPalmImageMsg = null
    palmCards.foreach( palm =>{
      latentPalmImageMsg = new LatentPalmImageMsg
      val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(palm.getBlob.getStImageBytes.toByteArray)
      latentPalmImageMsg.latentPalmId = palm.getStrCardID  //现场掌纹_现场指掌纹编号
      latentPalmImageMsg.latentPalmPhysicalId = palm.getStrPhysicalId  //现场掌纹_现场物证编号
      if(StringUtils.isNotEmpty(palm.getStrPhysicalId) && StringUtils.isNotBlank(palm.getStrPhysicalId)){
        latentPalmImageMsg.latentPalmPhysicalId = palm.getStrPhysicalId //现场物证编号
      }else{
        latentPalmImageMsg.latentPalmPhysicalId = fpt5util.gerenateLatentPhysicalIdTake("") //TODO:添加一个现场物证编号的三位顺序号的序列生成器
      }
      latentPalmImageMsg.latentPalmLeftPostion = palm.getText.getStrRemainPlace  //现场掌纹_现场指掌纹遗留部位
      latentPalmImageMsg.latentPalmCorpseJudgeIdentify = if(palm.getText.getBDeadBody) "1" else "0"  //现场掌纹_尸体指掌纹_判断标识
      latentPalmImageMsg.latentPalmMastoidProcessLineColorCode = palm.getText.getStrRidgeColor //现场掌纹_乳突线颜色代码
      latentPalmImageMsg.latentPalmComparisonStatusCode = palm.getText.getNBiDuiState.toString  //现场掌纹_掌纹比对状态代码
      latentPalmImageMsg.latentPalmCustomInfo = palm.getText.getStrComment.getBytes()  //现场掌纹_自定义信息
      FPT5ImageConverter.convertGAFISIMAGESTRUCT2LatentPalmImageMsg(gafisImage, latentPalmImageMsg)
      if(palm.getBlob.getStMnt.nonEmpty){//判断是否有特征
        val latentPalmFeatureMsg = new LatentPalmFeatureMsg
        val gafisMnt = new GAFISIMAGESTRUCT().fromByteArray(palm.getBlob.getStMntBytes.toByteArray)
        FPT5MntConverter.convertGafisMnt2LatentPalmFeatureMsg(gafisMnt, latentPalmFeatureMsg)
        latentPalmFeatureMsg.latentPalmNo = palm.getStrCardID //现场掌纹_现场指掌纹编号
        if(StringUtils.isNotEmpty(palm.getStrPhysicalId) && StringUtils.isNotBlank(palm.getStrPhysicalId)){
          latentPalmFeatureMsg.latentPalmPhysicalId = palm.getStrPhysicalId //现场物证编号
        }else{
          latentPalmFeatureMsg.latentPalmPhysicalId = fpt5util.gerenateLatentPhysicalIdTake("") //TODO:添加一个现场物证编号的三位顺序号的序列生成器
        }
        latentPalmFeatureMsg.latentPalmFeatureDscriptInfo = "" //现场掌纹_特征组合描述信息
        latentPalmFeatureMsg.latentPalmComparisonStatusCode = palm.getText.getNBiDuiState.toString //现场掌纹_指掌纹比对状态代码
        //TODO 掌纹折返点, 掌位分析, 现场掌纹_特征组合描述, 特征组标识符
        latentPalmFeatureMsgList += latentPalmFeatureMsg
      }
    })
    latentPalms.latentPalmImageMsg = latentPalmImageMsg
    latentPalms.latentPalmFeatureMsg = latentPalmFeatureMsgList.toArray
    latentPalms
  }

  def convertLatentPackage2Case(latentPackage: LatentPackage): Case = {
    val caseInfo = Case.newBuilder()
    val textBuilder = caseInfo.getTextBuilder
    caseInfo.setStrCaseID(latentPackage.caseMsg.originalSystemCaseId)
    caseInfo.setStrJingZongCaseId(latentPackage.caseMsg.caseId) //警综案事件编号
    caseInfo.setStrSurveyId(latentPackage.caseMsg.latentSurveyId) //现场勘验编号
    caseInfo.setNCaseFingerCount(latentPackage.latentFingers.length) //现场指纹个数
    latentPackage.latentFingers.foreach(
      latentFingers => caseInfo.addStrFingerID(latentFingers.latentFingerImageMsg.originalSystemLatentFingerPalmId)
    )
    textBuilder.setStrCaseType1(latentPackage.caseMsg.caseClassSet.caseTypeCode.toString) //案件类别
    textBuilder.setStrCaseType2(latentPackage.caseMsg.caseClassSet.caseTypeCode.toString)
    textBuilder.setStrCaseType3(latentPackage.caseMsg.caseClassSet.caseTypeCode.toString)
    textBuilder.setStrMoneyLost(latentPackage.caseMsg.money) //涉案金额
    textBuilder.setStrCaseOccurPlaceCode(latentPackage.caseMsg.caseOccurAdministrativeDivisionCode) //案发地点代码
    textBuilder.setStrCaseOccurPlace(latentPackage.caseMsg.caseOccurAddress) //案发地址详情
    textBuilder.setStrBriefCase(latentPackage.caseMsg.briefCase) //简要案情
    textBuilder.setBPersonKilled("1".equals(latentPackage.caseMsg.whetherKill)) //命案标识
    textBuilder.setStrExtractUnitCode(latentPackage.latentCollectInfoMsg.extractUnitCode) //提取单位代码
    textBuilder.setStrExtractUnitName(latentPackage.latentCollectInfoMsg.extractUnitName) //提取单位
    textBuilder.setStrExtractor(latentPackage.latentCollectInfoMsg.extractPerson) //提取人
    textBuilder.setStrExtractorIdCard(latentPackage.latentCollectInfoMsg.extractPersonIdCard) //提取人
    textBuilder.setStrExtractorTel(latentPackage.latentCollectInfoMsg.extractPersonTel) //提取人
    textBuilder.setStrExtractDate(latentPackage.latentCollectInfoMsg.extractDateTime) //提取时间

    caseInfo.build()
  }

  def convertLatentPackage2LPCard(latentPackage: LatentPackage): Seq[LPCard] = {
    val lpCardList = new ArrayBuffer[LPCard]
    latentPackage.latentFingers.foreach{
      t =>

        if(t.latentFingerImageMsg !=null){
          val lpCard = LPCard.newBuilder()
          val blobBuilder = lpCard.getBlobBuilder
          lpCard.setStrCardID(t.latentFingerImageMsg.originalSystemLatentFingerPalmId)
          val textBuilder = lpCard.getTextBuilder
          textBuilder.setStrCaseId(latentPackage.caseMsg.originalSystemCaseId)
          textBuilder.setStrSeq(t.latentFingerImageMsg.originalSystemLatentFingerPalmId.substring(t.latentFingerImageMsg.originalSystemLatentFingerPalmId.length-2))// 取最后两位
          textBuilder.setStrRemainPlace(t.latentFingerImageMsg.latentFingerLeftPosition) //遗留部位
          textBuilder.setStrRidgeColor(t.latentFingerImageMsg.latentFingerMastoidProcessLineColorCode) //乳突线颜色
          textBuilder.setBDeadBody("1".equals(t.latentFingerImageMsg.latentFingerCorpseJudgeIdentify)) //未知名尸体标识
          textBuilder.setStrStart(t.latentFingerImageMsg.latentFingerConnectFingerBeginPhysicalId) //联指开始序号
          textBuilder.setStrEnd(t.latentFingerImageMsg.latentFingerConnectFingerEndPhysicalId) //联指结束序号

          if (t.latentFingerImageMsg.latentFingerImageData != null && t.latentFingerImageMsg.latentFingerImageData.length > 0) {
            blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
            blobBuilder.setStImageBytes(ByteString.copyFrom(FPT5ImageConverter.convertLPFingerImageData2GafisImage(t.latentFingerImageMsg).toByteArray()))
          }
          textBuilder.setNBiDuiState(t.latentFingerImageMsg.latentFingerComparisonStatusCode.toInt)

          t.latentFingerFeatureMsg.foreach{
            f =>
              lpCard.setStrCardID(f.originalSystemLatentFingerPalmId)
              lpCard.setStrPhysicalId(f.latentPhysicalId)
              val gafisMnt = FPT5MntConverter.convertFingerLDataMnt2GafisMnt(t.latentFingerImageMsg,f,f.LatentMinutiaSet)
              blobBuilder.setStMntBytes(ByteString.copyFrom(gafisMnt.toByteArray()))
          }
          lpCardList += lpCard.build
        }
    }

    latentPackage.latentPalms.foreach{
      t =>
        if(t.latentPalmImageMsg != null){
          val lpCard = LPCard.newBuilder()
          val textBuilder = lpCard.getTextBuilder
          val blobBuilder = lpCard.getBlobBuilder
          lpCard.setStrCardID(t.latentPalmImageMsg.latentPalmId)
          textBuilder.setStrCaseId(latentPackage.caseMsg.originalSystemCaseId)
          textBuilder.setStrSeq(t.latentPalmImageMsg.latentPalmId.substring(t.latentPalmImageMsg.latentPalmId.length-2))
          textBuilder.setStrRemainPlace(t.latentPalmImageMsg.latentPalmLeftPostion) //遗留部位
          textBuilder.setStrRidgeColor(t.latentPalmImageMsg.latentPalmMastoidProcessLineColorCode) //乳突线颜色
          textBuilder.setBDeadBody("1".equals(t.latentPalmImageMsg.latentPalmCorpseJudgeIdentify)) //未知名尸体标识

          if (t.latentPalmImageMsg.latentPalmImageData != null &&
            t.latentPalmImageMsg.latentPalmImageData.length > 0) {
            blobBuilder.setType(ImageType.IMAGETYPE_PALM)
            blobBuilder.setStImageBytes(ByteString.copyFrom(FPT5ImageConverter.convertLPPalmImageData2GafisImage(t.latentPalmImageMsg).toByteArray()))
          }
          textBuilder.setNBiDuiState(t.latentPalmImageMsg.latentPalmComparisonStatusCode.toInt)

          t.latentPalmFeatureMsg.foreach{
            f =>
              lpCard.setStrCardID(f.latentPalmNo)
              lpCard.setStrPhysicalId(f.latentPalmPhysicalId)
              val gafisMnt = FPT5MntConverter.convertPalmLDataMnt2GafisMnt(t.latentPalmImageMsg,f,f.latentPalmMinutiaSet)
              blobBuilder.setStMntBytes(ByteString.copyFrom(gafisMnt.toByteArray()))
          }
          lpCardList += lpCard.build
        }
    }
    lpCardList
  }
}

/**
  * 图像信息属性类
  */
class ImageProperty {
    var compressMethod: String = _
    var imageType: Byte = _
    var width: Short = _
    var height: Short = _
    var resolution: Short = _
    var bnData: Array[Byte] = _
    var positionCode:Int = _
  }

/**
  * 掌纹掌位代码
  */
object FPT5PalmFgp extends Enumeration{
  type FPT5PalmFgp = Value
  val PALMRIGHt = Value("31") //右手平面掌纹
  val PALMLEFT = Value("32")  //左手平面掌纹
  val PALMRIGHTSIDE = Value("33")  //右手侧面掌纹
  val PALMLEFTSIDE = Value("34")  //左手侧面掌纹
  val PALMFULLPALMRIGHT = Value("35") //右手平面全掌纹
  val PALMFULLPALMLEFT = Value("36")  //左手平面全掌纹
  val PALMUNKNOWN = Value("39")  //不确定掌纹
}

