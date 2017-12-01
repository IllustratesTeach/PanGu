package nirvana.hall.api.internal.fpt


import java.util

import com.google.protobuf.ByteString
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.FingerLData
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gfpt5lib.{FaceImage, _}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FPTMntConverter
import nirvana.hall.protocol.api.FPTProto
import nirvana.hall.protocol.api.FPTProto.TPCard.TPCardBlob
import nirvana.hall.protocol.api.FPTProto.{LPCard, _}
import nirvana.hall.v70.internal.Gafis70Constants

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
    //采集信息原因代码集合
    fingerprintPackage.descriptiveMsg = new DescriptMsg
    fingerprintPackage.descriptiveMsg.originalSystemCasePersonId = tpCard.getStrCardID  //原始系统_案事件相关人员编号
    fingerprintPackage.descriptiveMsg.jingZongPersonId = "J5224010109992013070451"  //由警综平台标准化采集系统发号
    fingerprintPackage.descriptiveMsg.casePersonid = "A5224010109992013070451"  //案事件相关人员编号 由刑专系统发号，新数据必须提供
    fingerprintPackage.descriptiveMsg.fingerPalmCardId = tpCard.getStrCardID   //指掌纹卡编号 系统自用
    val captureInfoReasonCode = new mutable.ArrayBuffer[String]()
    captureInfoReasonCode += "01"
    fingerprintPackage.descriptiveMsg.collectingReasonSet.captureInfoReasonCode = captureInfoReasonCode.toArray // 采集信息原因代码
    fingerprintPackage.descriptiveMsg.name = tpCard.getText.getStrName   //姓名
    fingerprintPackage.descriptiveMsg.alias = tpCard.getText.getStrAliasName    //别名/绰号
    fingerprintPackage.descriptiveMsg.sex = tpCard.getText.getNSex.toString  //性别代码
    fingerprintPackage.descriptiveMsg.birthday = if(tpCard.getText.getStrBirthDate != "") tpCard.getText.getStrBirthDate else "00000000" //出生日期
    fingerprintPackage.descriptiveMsg.nationality =  if(tpCard.getText.getStrRace != "")tpCard.getText.getStrRace else "000"//国籍代码
    fingerprintPackage.descriptiveMsg.nation = if(tpCard.getText.getStrNation != "")tpCard.getText.getStrNation else "00"  //民族代码
    fingerprintPackage.descriptiveMsg.credentialsCode = "001"  //常用证件代码
    fingerprintPackage.descriptiveMsg.credentialsNo = if(tpCard.getText.getStrIdentityNum != "")tpCard.getText.getStrIdentityNum  else "000000000000000000000000000000"//证件号码
    fingerprintPackage.descriptiveMsg.houkouAdministrativeDivisionCode =if(tpCard.getText.getStrHuKouPlaceCode != "")tpCard.getText.getStrHuKouPlaceCode else "000000" //户籍地址行政区划代码
    fingerprintPackage.descriptiveMsg.houkouAddress = if(tpCard.getText.getStrHuKouPlaceTail != "")tpCard.getText.getStrHuKouPlaceTail else "  "//户籍地址地址名称
    fingerprintPackage.descriptiveMsg.houseAdministrativeDivisionCode = if(tpCard.getText.getStrAddrCode !="")tpCard.getText.getStrAddrCode else "000000" //现住址行政区划代码
    fingerprintPackage.descriptiveMsg.houseAddress = if(tpCard.getText.getStrAddr != "") tpCard.getText.getStrAddr else " "//现住址地址名称
    fingerprintPackage.descriptiveMsg.memo = tpCard.getText.getStrComment//"123213213213123123123123123213"  //备注
    //捺印信息节点
    fingerprintPackage.collectInfoMsg = new CollectInfoMsg
    fingerprintPackage.collectInfoMsg.fingerprintComparisonSysTypeDescript = "1900"  //指纹比对系统描述
    fingerprintPackage.collectInfoMsg.chopUnitCode = if(tpCard.getText.getStrPrintUnitCode != "") tpCard.getText.getStrPrintUnitCode else "111111111111" //捺印单位公安机关机构代码
    fingerprintPackage.collectInfoMsg.chopUnitName = if(tpCard.getText.getStrPrintUnitName != "") tpCard.getText.getStrPrintUnitName else "234324" //捺印单位公安机关机构名称
    fingerprintPackage.collectInfoMsg.chopPersonName = if(tpCard.getText.getStrPrinter != "") tpCard.getText.getStrPrinter else "55555"  //捺印人员_姓名
    fingerprintPackage.collectInfoMsg.chopPersonIdCard = if(tpCard.getText.getStrIdentityNum != "")  tpCard.getText.getStrIdentityNum else "000000000000000000"//"120101198601031538"  //捺印人员_公民身份号码
    fingerprintPackage.collectInfoMsg.chopPersonTel = ""  //"123213123"  //捺印人员_联系电话
    fingerprintPackage.collectInfoMsg.chopDateTime =  "2017-10-11T10:00:01"   //捺印时间  tpCard.getText.getStrPrintDate
    //先初始化捺印指纹包信息
    val fingerMsgs = new mutable.ArrayBuffer[FingerMsg]()
    val knuckleprintMsgs = new mutable.ArrayBuffer[KnuckleprintMsg]()
    val palmMsgs = new mutable.ArrayBuffer[PalmMsg]()
    val fourprintMsgs = new mutable.ArrayBuffer[FourprintMsg]()
    val fullpalmMsgs = new mutable.ArrayBuffer[FullpalmMsg]()
    val faceImageMsgs = new mutable.ArrayBuffer[FaceImage]()
    val iter = tpCard.getBlobList.iterator
    while (iter.hasNext) {
      val blob = iter.next()
      blob.getType match {
        //指纹
        case ImageType.IMAGETYPE_FINGER =>
          if(!blob.getBPlain){
            fingerMsgs += getFingerMsg(blob)
          }else{
            fingerMsgs += getFingerMsg(blob)
          }
        //指节纹
        case ImageType.IMAGETYPE_KNUCKLEPRINTS =>
          knuckleprintMsgs += getKnuckleprintMsg(blob)
        //掌纹
        case ImageType.IMAGETYPE_PALM =>
          palmMsgs += getPalmMsg(blob)
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
    if( fingerMsgs.size > 0 ) {
      val fingers = new Fingers()
      fingers.fingerMsg = fingerMsgs.toArray
      fingerprintPackage.fingers = fingers
    }
    if(knuckleprintMsgs.size > 0){
      val knuckleprints = new Knuckleprints()
      knuckleprints.knuckleprintMsg = knuckleprintMsgs.toArray
      fingerprintPackage.knuckleprints = knuckleprints
    }
    if(palmMsgs.size > 0){
      val palms = new Palms()
      palms.palmMsg = palmMsgs.toArray
      fingerprintPackage.palms = palms
    }
    if(fourprintMsgs.size > 0){
      val fourprints = new Fourprints()
      fourprints.fourprintMsg = fourprintMsgs.toArray
      fingerprintPackage.fourprints = fourprints
    }
    if(fullpalmMsgs.size > 0) {
      val fullpalms = new Fullpalms()
      fullpalms.fullpalmMsg = fullpalmMsgs.toArray
      fingerprintPackage.fullpalms = fullpalms
    }
    if(faceImageMsgs.size > 0 ){
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
    val fingerMsg = new FingerMsg
    val gafisMnt = new GAFISIMAGESTRUCT
    gafisMnt.fromStreamReader(blob.getStMntBytes.newInput())
    val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
    val fingerTData = FPTMntConverter.convertGafisMnt2FingerTData(gafisMnt)
    fingerMsg.fingerPositionCode = if(fingerTData.fgp.matches("^([1-9])$")) ("0"+fingerTData.fgp) else (fingerTData.fgp) //指纹指位代码
    fingerMsg.fingerFeatureExtractionMethodCode = fingerTData.extractMethod //指掌纹特征提取方式代码
    fingerMsg.adactylismCauseCode = "3" //指掌纹缺失情况代码
    fingerMsg.fingerPatternMasterCode = fingerTData.pattern1 //指纹纹型主分类_指纹纹型代码
    fingerMsg.fingerPatternSlaveCode = fingerTData.pattern2  //指纹纹型副分类_指纹纹型代码
    fingerMsg.fingerFeatureDirection = fingerTData.fingerDirection.substring(0,3).toInt //指纹方向-特征方向
    fingerMsg.fingerFeatureDirectionRange =  fingerTData.fingerDirection.substring(3,5).toInt//指纹方向-特征方向-范围
    val centerFeature = convertFpt4Feature2CenterFeature(fingerTData.centerPoint)//指纹中心点特征
    if(centerFeature != null){
      fingerMsg.fingerCenterPointFeatureXCoordinate = centerFeature.xPoint
      fingerMsg.fingerCenterPointFeatureYCoordinate = centerFeature.yPoint
      fingerMsg.fingerCenterPointFeatureCoordinateRange =centerFeature.range
      fingerMsg.fingerCenterPointFeatureDirection=  centerFeature.direction
      fingerMsg.fingerCenterPointFeatureDirectionRange = centerFeature.directionRange
      fingerMsg.fingerCenterPointFeatureReliabilityLevel = centerFeature.reliability
    }
    val subCenterFeature = convertFpt4Feature2CenterFeature(fingerTData.subCenterPoint) //指纹副中心特征
    if(subCenterFeature != null){
      fingerMsg.fingerSlaveCenterFeatureXCoordinate=   subCenterFeature.xPoint
      fingerMsg.fingerSlaveCenterFeatureYCoordinate =  subCenterFeature.yPoint
      fingerMsg.fingerSlaveCenterFeatureCoordinateRange =   subCenterFeature.range
      fingerMsg.fingerSlaveCenterFeatureDirection =   subCenterFeature.direction
      fingerMsg.fingerSlaveCenterFeatureDirectionRange =  subCenterFeature.directionRange
      fingerMsg.fingerSlaveCenterFeatureReliabilityLevel = subCenterFeature.reliability
    }
    val leftTriangleFeature = convertFpt4Feature2CenterFeature(fingerTData.leftTriangle) //指纹左三角特征
    if(leftTriangleFeature != null){
      fingerMsg.fingerLeftTriangleFeatureXCoordinate = leftTriangleFeature.xPoint
      fingerMsg.fingerLeftTriangleFeatureYCoordinate = leftTriangleFeature.yPoint
      fingerMsg.fingerLeftTriangleFeatureCoordinateRange = leftTriangleFeature.range
      fingerMsg.fingerLeftTriangleFeatureDirection =  leftTriangleFeature.direction
      fingerMsg.fingerLeftTriangleFeatureDirectionRange = leftTriangleFeature.directionRange
      fingerMsg.fingerLeftTriangleFeatureReliabilityLevel =  leftTriangleFeature.reliability
    }
    val rightTriangle = convertFpt4Feature2CenterFeature(fingerTData.rightTriangle) //指纹右三角特征
    if(rightTriangle != null){
      fingerMsg.fingerRightTriangleFeatureXCoordinate =  rightTriangle.xPoint
      fingerMsg.fingerRightTriangleFeatureYCoordinate =  rightTriangle.yPoint
      fingerMsg.fingerRightTriangleFeatureCoordinateRange =  rightTriangle.range
      fingerMsg.fingerRightTriangleFeatureDirection = rightTriangle.direction
      fingerMsg.fingerRightTriangleFeatureDirectionRange =  rightTriangle.directionRange
      fingerMsg.fingerRightTriangleFeatureReliabilityLevel =  rightTriangle.reliability
    }

    val minSet = new FingerMinutiaSet
    if(fingerTData.featureCount.toInt >= 0){
      minSet.minutia = convertFeature2FingerMinutiaSet(fingerTData.feature,fingerTData.featureCount.toInt)
    }
    fingerMsg.fingerMinutiaSet = minSet// 单个指纹特征点信息集合
    fingerMsg.fingerCustomInfo = "".getBytes() //指纹自定义信息
    fingerMsg.fingerImageHorizontalDirectionLength = 640 //指纹图像水平方向长度
    fingerMsg.fingerImageVerticalDirectionLength = 640 //指纹图像垂直方向长度
    fingerMsg.fingerImageRatio = 500 //指纹图像分辨率
    fingerMsg.fingerImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod) //指纹图像压缩方法描述
    fingerMsg.fingerImageData = gafisImage.bnData
    fingerMsg
  }

  /**
    * 获取单枚掌纹节点信息
    * @param blob
    * @return
    */
  private def getPalmMsg(blob:TPCardBlob):PalmMsg = {
    val palmMsg = new PalmMsg
    val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
    val fingerTData = FPTMntConverter.convertGafisMnt2FingerTData(gafisImage)
    palmMsg.palmPostionCode = blob.getPalmfgp.getNumber.toString //掌纹掌位代码
    palmMsg.lackPalmCauseCode = "0"
    palmMsg.palmFeatureExtractionMethodCode = fingerTData.extractMethod
    palmMsg.coreLikePatternSet = new CoreLikePatternSet()
    palmMsg.deltaSet = new DeltaSet()
    palmMsg.palmMinutiaSet = new PalmMinutiaSet()
    palmMsg.palmCustomInfo = "".getBytes()
    palmMsg.palmImageHorizontalDirectionLength = fingerTData.imgHorizontalLength.toInt
    palmMsg.palmImageVerticalDirectionLength = fingerTData.imgVerticalLength.toInt
    palmMsg.palmImageRatio = 500
    palmMsg.palmImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
    palmMsg.palmImageData = gafisImage.bnData
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
    val fingerTData = FPTMntConverter.convertGafisMnt2FingerTData(gafisImage)
    fourprintMsg.fourPrintPostionCode = blob.getPalmfgp.getNumber.toString
    fourprintMsg.fourPrintLackFingerCauseCode = "3"
    fourprintMsg.fourPrintCustomInfo = "".getBytes()
    fourprintMsg.fourPrintImageHorizontalDirectionLength = fingerTData.imgHorizontalLength.toInt
    fourprintMsg.fourPrintImageVerticalDirectionLength = fingerTData.imgVerticalLength.toInt
    fourprintMsg.fourPrintImageRatio = 500
    fourprintMsg.fourPrintImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
    fourprintMsg.fourPrintImageData = gafisImage.bnData
    fourprintMsg
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
    val fingerTData = FPTMntConverter.convertGafisMnt2FingerTData(gafisMnt)
    knuckleprintMsg.knucklePrintPostionCode = if(fingerTData.fgp.matches("^([1-9])$")) ("0"+fingerTData.fgp) else (fingerTData.fgp) //指节纹指纹指位代码
    knuckleprintMsg.knucklePrintLackFingerCauseCode = "3"
    knuckleprintMsg.knucklePrintCustomInfo = "".getBytes
    knuckleprintMsg.knucklePrintImageHorizontalDirectionLength = 640
    knuckleprintMsg.knucklePrintImageVerticalDirectionLength = 640
    knuckleprintMsg.knucklePrintImageRatio = 500
    knuckleprintMsg.knucklePrintImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
    knuckleprintMsg.knucklePrintImageData = gafisImage.bnData
    knuckleprintMsg
  }

  /**
    * 获取单个全掌纹节点信息
    * @param blob
    * @return
    */
  private def getFullPalmMsg(blob:TPCardBlob):FullpalmMsg = {
    val fullpalmMsg = new FullpalmMsg
    val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
    val fingerTData = FPTMntConverter.convertGafisMnt2FingerTData(gafisImage)
    fullpalmMsg.fullPalmPostionCode = blob.getPalmfgp.getNumber.toString
    fullpalmMsg.fullPalmLackPalmCauseCode = "3"
    fullpalmMsg.fullPalmCustomInfo = "".getBytes()
    fullpalmMsg.fullPalmImageHorizontalDirectionLength = fingerTData.imgHorizontalLength.toInt
    fullpalmMsg.fullPalmImageVerticalDirectionLength = fingerTData.imgVerticalLength.toInt
    fullpalmMsg.fullPalmImageRatio = 500
    fullpalmMsg.fullPalmImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
    fullpalmMsg.fullPalmImageData = gafisImage.bnData
    fullpalmMsg
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
    faceImage.personPictureFileLayout = " "
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
//
//  /**
//    * 将现场proto信息转为LatentPackage
//    * @param caseInfo 案件信息
//    * @param lpCards 现场指纹信息
//    * @return
//    */
//  def convertCaseInfoAndLPCard2LatentPackage(caseInfo: Case, lpCards: Seq[LPCard], palmCards: Seq[LPCard]): LatentPackage = {
//    val latentPackage = new LatentPackage
//    //latentPackage.caseMsg.latentFingerprintComparisonSysTypeDescript = "1900"  //指纹比对系统描述
//    latentPackage.caseMsg.originalSystemCaseId = caseInfo.getStrCaseID  //原始系统_案事件编号
//    latentPackage.caseMsg.caseId = caseInfo.getStrCaseID  //案事件编号 --警综案事件编号，新数据必须提供
//    latentPackage.caseMsg.latentSurveyId = caseInfo.getStrCaseID  //现场勘验编号
//    latentPackage.caseMsg.latentCardId = ""  //现场指掌纹卡编号
//    //latentPackage.caseMsg.caseTypeCode = caseInfo.getText.getStrCaseType1  //案件类别代码
//    latentPackage.caseMsg.money = caseInfo.getText.getStrMoneyLost  //金额（人民币元）
//    latentPackage.caseMsg.caseOccurAdministrativeDivisionCode = caseInfo.getText.getStrCaseOccurPlaceCode  //案事件发生地点_行政区划代码
//    latentPackage.caseMsg.caseOccurAddress = caseInfo.getText.getStrCaseOccurPlace  //案事件发生地点_地址名称
//    latentPackage.caseMsg.briefCase = caseInfo.getText.getStrComment  //简要案情
//    latentPackage.caseMsg.whetherKill = if (caseInfo.getText.getBPersonKilled) "1" else "0"  //是否命案_判断标识
////    latentPackage.caseMsg.extractUnitCode = caseInfo.getText.getStrExtractUnitCode  //提取单位_公安机关机构代码
////    latentPackage.caseMsg.extractUnitName = caseInfo.getText.getStrExtractUnitName  //提取单位_公安机关名称
////    latentPackage.caseMsg.extractPerson = caseInfo.getText.getStrExtractor  //提取人员_姓名
////    latentPackage.caseMsg.extractPersonIdCard = "000000000000000"  //提取人员_公民身份号码
////    latentPackage.caseMsg.extractPersonTel = ""  //提取人员_联系电话
////    latentPackage.caseMsg.extractDateTime = "2017-10-11T10:00:01"   //提取时间  caseInfo.getText.getStrExtractDate
////    latentPackage.caseMsg.latentFingerNum = lpCards.size
////    latentPackage.caseMsg.latentPalmNum = palmCards.size
//
//    lpCards.foreach( lpcard =>{
//
////      val latentfingerMsg = new LatentFingerMsg
//      var fingerLData = new FingerLData
//      val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(lpcard.getBlob.getStImageBytes.toByteArray)
//
////      latentfingerMsg.originalSystemLatentFingerPalmId = lpcard.getStrCardID  //原始系统_现场指掌纹编号
////      latentfingerMsg.latentPhysicalId = "R12345678901234567890123456789"  //现场物证编号
////      latentfingerMsg.latentFingerLeftPosition = ""  //现场指纹_现场指掌纹遗留部位
////      latentfingerMsg.latentFingerFeatureExtractMethodCode = "M"  //现场指纹_指掌纹特征提取方式代码
////      latentfingerMsg.latentFingerCorpseJudgeIdentify = if(lpcard.getText.getBDeadBody) "1" else "0"  //现场指纹_尸体指掌纹_判断标识
////      latentfingerMsg.latentFingerMastoidProcessLineColorCode = if(FPT5Utils.isNull(lpcard.getText.getStrRidgeColor)) "" else lpcard.getText.getStrRidgeColor  //现场指纹_乳突线颜色代码
////      latentfingerMsg.latentFingerConnectFingerBeginPhysicalId = lpcard.getText.getStrStart  //现场指纹_连指开始_现场物证编号
////      latentfingerMsg.latentFingerConnectFingerEndPhysicalId = lpcard.getText.getStrEnd  //现场指纹_连指结束_现场物证编号
////      latentfingerMsg.latentFingerComparisonStatusCode = lpcard.getText.getNBiDuiState.toString  //现场指纹_指纹比对状态代码
////      latentfingerMsg.latentFingerFeatureGroupNum = 0  //现场指纹_特征组合数量
//
//      //val latentFingerFeatureGroup = new Xczwtzz  //现场指纹特征组相关
//      if(lpcard.getBlob.getStMnt.nonEmpty){//判断是否有特征
//
//        //latentfingerMsg.latentFingerFeatureGroupNum = latentfingerMsg.latentFingerFeatureGroupNum + 1
//        val gafisMnt =  new GAFISIMAGESTRUCT
//        gafisMnt.fromStreamReader(lpcard.getBlob.getStMntBytes.newInput())
//        fingerLData = FPTMntConverter.convertGafisMnt2FingerLData(gafisMnt)
//
////        latentFingerFeatureGroup.latentFingerFeatureGroupIdentify = "123451234512345"  //现场指纹_特征组合标识符
////        latentFingerFeatureGroup.latentFingerFeatureGroupDescript = ""  //现场指纹_特征组合描述
////        latentFingerFeatureGroup.latentFingerAnalysisPostionBrief = "1234567890"  //现场指纹_分析指位_简要情况
////        latentFingerFeatureGroup.latentFingerPatternCode = "1234567"  //fingerLData.pattern  //现场指纹_指纹纹型代码
////        latentFingerFeatureGroup.latentFingerDirectionDescript = fingerLData.fingerDirection  //现场指纹_指纹方向描述
////        latentFingerFeatureGroup.latentFingerCenterPoint = fingerLData.centerPoint  //现场指纹_指纹中心点
////        latentFingerFeatureGroup.latentFingerSlaveCenter = if(FPT5Utils.isNull(fingerLData.subCenterPoint) || FPT5Utils.replaceLength(fingerLData.subCenterPoint,14))"" else fingerLData.subCenterPoint  //现场指纹_指纹副中心
////        latentFingerFeatureGroup.latentFingerLeftTriangle = if(FPT5Utils.isNull(fingerLData.leftTriangle) || FPT5Utils.replaceLength(fingerLData.leftTriangle,14))"" else fingerLData.leftTriangle   //现场指纹_指纹左三角
////        latentFingerFeatureGroup.latentFingerRightTriangle = if(FPT5Utils.isNull(fingerLData.rightTriangle) || FPT5Utils.replaceLength(fingerLData.rightTriangle,14))"" else fingerLData.rightTriangle  //现场指纹_指纹右三角
////        latentFingerFeatureGroup.latentFingerFeatureNum = fingerLData.featureCount.toInt  //现场指纹_指纹特征点_数量
////        latentFingerFeatureGroup.latentFingerFeatureInfo = fingerLData.feature  //现场指纹_指纹特征点信息
////        latentFingerFeatureGroup.latentFingerCustomInfo = lpcard.getText.getStrComment.getBytes() //现场指纹_自定义信息
////        latentFingerFeatureGroup.latentFingerImageHorizontalDirectionLength = 512  //现场指纹_图像水平方向长度
////        latentFingerFeatureGroup.latentFingerImageVerticalDirectionLength = 512  //现场指纹_图像垂直方向长度
////        latentFingerFeatureGroup.latentFingerImageRatio = 500  //现场指纹_图像分辨率
////        latentFingerFeatureGroup.latentFingerImageCompressMethodDescript = fpt4code.GAIMG_CPRMETHOD_NOCPR_CODE  //现场指纹_图像压缩方法描述
////        latentFingerFeatureGroup.latentFingerImageData = gafisImage.bnData  //现场指纹_图像数据
//      }
//      //latentfingerMsg.latentFingerFeatureGroup.add(latentFingerFeatureGroup)
//
//      //latentPackage.latentFingers.latentfingerMsg.add(latentfingerMsg)
//    })
//
//    palmCards.foreach( palm =>{
//
//      //val latentpalmMsg = new LatentpalmMsg
//      var fingerLData = new FingerLData
//      val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(palm.getBlob.getStImageBytes.toByteArray)
//
////      latentpalmMsg.latentPalmId = palm.getStrCardID  //现场掌纹_现场指掌纹编号
////      latentpalmMsg.latentPalmPhysicalId = "R12345678901234567890123456789"  //现场掌纹_现场物证编号
////      latentpalmMsg.latentPalmLeftPostion = ""  //现场掌纹_现场指掌纹遗留部位
////      latentpalmMsg.latentPalmFeatureExtractMethodCode = "M"  //现场掌纹_指掌纹特征提取方式代码
////      latentpalmMsg.latentPalmCorpseJudgeIdentify = if(palm.getText.getBDeadBody) "1" else "0"  //现场掌纹_尸体指掌纹_判断标识
////      latentpalmMsg.latentPalmMastoidProcessLineColorCode = if(FPT5Utils.isNull(palm.getText.getStrRidgeColor)) "" else palm.getText.getStrRidgeColor //现场掌纹_乳突线颜色代码
////      latentpalmMsg.latentPalmComparisonStatusCode = palm.getText.getNBiDuiState.toString  //现场掌纹_掌纹比对状态代码
////      latentpalmMsg.latentPalmFeatureGroupNum = 0  //现场掌纹_特征组合数量
//
//      //val latentPalmFeatureGroup = new Xczhwtzz  //现场掌纹特征组相关
//      if(palm.getBlob.getStMnt.nonEmpty){//判断是否有特征
//
//        //latentpalmMsg.latentPalmFeatureGroupNum = latentpalmMsg.latentPalmFeatureGroupNum + 1
//        val gafisMnt =  new GAFISIMAGESTRUCT
//        gafisMnt.fromStreamReader(palm.getBlob.getStMntBytes.newInput())
//        fingerLData = FPTMntConverter.convertGafisMnt2FingerLData(gafisMnt)
//
////        latentPalmFeatureGroup.latentPalmFeatureGroupIdentify = "123451234512345"  //现场掌纹_特征组合标识符
////        latentPalmFeatureGroup.latentPalmFeatureGroupDescript = ""  //现场掌纹_特征组合描述
////        latentPalmFeatureGroup.latentPalmAnalysisPostionBrief = "1234"  //现场掌纹_分析掌位_简要情况
////        latentPalmFeatureGroup.latentPalmRetracingPointNum = 0  //fingerLData.pattern  //现场掌纹_掌纹折返点数量
////        latentPalmFeatureGroup.latentPalmRetracingPointInfo = ""  //现场掌纹_掌纹折返点信息
////        latentPalmFeatureGroup.latentPalmTrianglePointNum = 0  //现场掌纹_掌纹三角点_数量
////        latentPalmFeatureGroup.latentPalmTrianglePointInfo = ""  //现场掌纹_掌纹三角点信息
////        latentPalmFeatureGroup.latentPalmFeaturePointNum = fingerLData.featureCount.toInt   //现场掌纹_掌纹特征点_数量
////        latentPalmFeatureGroup.latentPalmFeaturePointInfo = fingerLData.feature  //现场掌纹_掌纹特征点信息
////        latentPalmFeatureGroup.latentPalmCustomInfo = palm.getText.getStrComment.getBytes() //现场掌纹_自定义信息
////        latentPalmFeatureGroup.latentPalmImageHorizontalDirectionLength = fingerLData.imgHorizontalLength.toInt  //现场掌纹_图像水平方向长度
////        latentPalmFeatureGroup.latentPalmImageVerticalDirectionLength = fingerLData.imgVerticalLength.toInt  //现场掌纹_图像垂直方向长度
////        latentPalmFeatureGroup.latentPalmImageRatio = fingerLData.dpi.toInt  //现场掌纹_图像分辨率
////        latentPalmFeatureGroup.latentPalmImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)  //现场掌纹_图像压缩方法描述
////        latentPalmFeatureGroup.latentPalmImageData = gafisImage.bnData  //现场掌纹_图像数据
//      }
//      //latentpalmMsg.latentPalmFeatureGroup.add(latentPalmFeatureGroup)
//
//      //latentPackage.latentPalms.latentpalmMsg.add(latentpalmMsg)
//    })
//
//    latentPackage
//  }
//
  /**
    * 将指纹包转换为tpcard protobuf对象
    * @param fingerprintPackage
    * @return
    */
  def convertFingerprintPackage2TPCard(fingerprintPackage:FingerprintPackage):TPCard = {
    val tpCard = TPCard.newBuilder()
    val textBuilder = tpCard.getTextBuilder
    tpCard.setStrCardID(fingerprintPackage.descriptiveMsg.fingerPalmCardId)
    tpCard.setStrMisPersonID(fingerprintPackage.descriptiveMsg.casePersonid)
    textBuilder.setStrName(fingerprintPackage.descriptiveMsg.name)
    textBuilder.setStrAliasName(fingerprintPackage.descriptiveMsg.alias)
    if (fingerprintPackage.descriptiveMsg.sex != null && fingerprintPackage.descriptiveMsg.sex.length > 0) {
      textBuilder.setNSex(fingerprintPackage.descriptiveMsg.sex.toInt)
    }
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
//    textBuilder.setStrPrintUnitCode(fingerprintPackage.descriptiveMsg.chopUnitCode)
//    textBuilder.setStrPrintUnitName(fingerprintPackage.descriptiveMsg.chopUnitName)
//    textBuilder.setStrPrinter(fingerprintPackage.descriptiveMsg.chopPersonName)
//    textBuilder.setStrPrintDate(fingerprintPackage.descriptiveMsg.chopDateTime)
//    tpCard.setStrPrinterIdCardId(fingerprintPackage.descriptiveMsg.chopPersonIdCard)
//    tpCard.setStrPrinterTel(fingerprintPackage.descriptiveMsg.chopPersonTel)
    textBuilder.setStrComment(fingerprintPackage.descriptiveMsg.memo)
    tpCard.setStrDataSource(Gafis70Constants.DATA_SOURCE_FPT)
    if(fingerprintPackage.fingers != null){
      val planar = fingerprintPackage.fingers.fingerMsg.iterator
      var planarImg:FingerMsg = null
      val planarImgProperty = new ImageProperty
      while(planar.hasNext){
        planarImg = planar.next
        if(planarImg.fingerImageData !=null && planarImg.fingerImageData.length > 0){
          val blobBuilder = tpCard.addBlobBuilder()
          blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
          if(planarImg.fingerPositionCode.toInt > 10){
            blobBuilder.setFgp(FingerFgp.valueOf(planarImg.fingerPositionCode.toInt - 10))
            blobBuilder.setBPlain(true)
          }else{
            blobBuilder.setFgp(FingerFgp.valueOf(planarImg.fingerPositionCode.toInt))
          }
          planarImgProperty.compressMethod  = planarImg.fingerImageCompressMethodDescript
          planarImgProperty.imageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
          planarImgProperty.width = "640".toShort
          planarImgProperty.height = "640".toShort
          planarImgProperty.resolution = "500".toShort
          planarImgProperty.bnData = planarImg.fingerImageData
          planarImgProperty.positionCode = planarImg.fingerPositionCode.toInt
          val gafisImage = convert2GafisImage(planarImgProperty)
          blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray(AncientConstants.GBK_ENCODING)))
        }
      }
    }
    if(fingerprintPackage.palms != null){
      val palmMsg = fingerprintPackage.palms.palmMsg.iterator
      var palmMsgImg:PalmMsg = null
      val palmMsgImgProperty = new ImageProperty
      while(palmMsg.hasNext){
        palmMsgImg = palmMsg.next
        if(palmMsgImg.palmImageData !=null && palmMsgImg.palmImageData.length > 0){
          val blobBuilder = tpCard.addBlobBuilder()
          blobBuilder.setType(ImageType.IMAGETYPE_PALM)
          blobBuilder.setPalmfgp(string2PalmFgpPares(palmMsgImg.palmPostionCode)) //掌纹掌位代码
          palmMsgImgProperty.compressMethod = palmMsgImg.palmImageCompressMethodDescript //掌纹_指掌纹特征提取方式代码
          palmMsgImgProperty.imageType = glocdef.GAIMG_IMAGETYPE_PALM.toByte
          palmMsgImgProperty.width = palmMsgImg.palmImageHorizontalDirectionLength.toShort
          palmMsgImgProperty.height = palmMsgImg.palmImageVerticalDirectionLength.toShort
          palmMsgImgProperty.resolution = "500".toShort
          palmMsgImgProperty.bnData = palmMsgImg.palmImageData
          palmMsgImgProperty.positionCode = palmMsgImg.palmPostionCode.toInt
          val gafisImage = convert2GafisImage(palmMsgImgProperty)
          blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray(AncientConstants.GBK_ENCODING)))
        }
      }
    }
    if(fingerprintPackage.fourprints != null){
      val fourPrint = fingerprintPackage.fourprints.fourprintMsg.iterator
      var fourPrintImg:FourprintMsg = null
      val fourPrintImgProperty = new ImageProperty
      while(fourPrint.hasNext){
        fourPrintImg = fourPrint.next
        if(fourPrintImg.fourPrintImageData !=null && fourPrintImg.fourPrintImageData.length > 0) {
          val blobBuilder = tpCard.addBlobBuilder()
          blobBuilder.setType(ImageType.IMAGETYPE_FOURPRINT)
          fourPrintImg.fourPrintPostionCode match {
            case "21" => blobBuilder.setPalmfgp(PalmFgp.PALM_FOUR_PRINT_RIGHT)
            case "22" => blobBuilder.setPalmfgp(PalmFgp.PALM_FOUR_PRINT_LEFT)
          }
          fourPrintImgProperty.compressMethod = fourPrintImg.fourPrintImageCompressMethodDescript //掌纹_指掌纹特征提取方式代码
          fourPrintImgProperty.imageType = glocdef.GAIMG_IMAGETYPE_PALM.toByte
          fourPrintImgProperty.width = fourPrintImg.fourPrintImageHorizontalDirectionLength.toShort
          fourPrintImgProperty.height = fourPrintImg.fourPrintImageVerticalDirectionLength.toShort
          fourPrintImgProperty.resolution = "500".toShort
          fourPrintImgProperty.bnData = fourPrintImg.fourPrintImageData
          val gafisImage = convert2GafisImage(fourPrintImgProperty)
          blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray(AncientConstants.GBK_ENCODING)))
        }
      }
    }
    if(fingerprintPackage.knuckleprints != null){
      val knucklePrints = fingerprintPackage.knuckleprints.knuckleprintMsg.iterator
      var knucklePrintImg:KnuckleprintMsg = null
      val knucklePrintImgProperty = new ImageProperty
      while(knucklePrints.hasNext){
        knucklePrintImg = knucklePrints.next
        if(knucklePrintImg.knucklePrintImageData !=null && knucklePrintImg.knucklePrintImageData.length > 0){
          val blobBuilder = tpCard.addBlobBuilder()
          blobBuilder.setType(ImageType.IMAGETYPE_KNUCKLEPRINTS)
          blobBuilder.setFgp(FingerFgp.valueOf(knucklePrintImg.knucklePrintPostionCode.toInt))//指节纹的位置 待定？？？
          knucklePrintImgProperty.compressMethod = knucklePrintImg.knucklePrintImageCompressMethodDescript
          knucklePrintImgProperty.imageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
          knucklePrintImgProperty.width = knucklePrintImg.knucklePrintImageHorizontalDirectionLength.toShort
          knucklePrintImgProperty.height = knucklePrintImg.knucklePrintImageVerticalDirectionLength.toShort
          knucklePrintImgProperty.resolution = knucklePrintImg.knucklePrintImageRatio.toShort
          knucklePrintImgProperty.bnData = knucklePrintImg.knucklePrintImageData
          val gafisImage = convert2GafisImage(knucklePrintImgProperty)
          blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray(AncientConstants.GBK_ENCODING)))
        }
      }
    }
    if(fingerprintPackage.fullpalms != null){
      val fullPalmMsgs = fingerprintPackage.fullpalms.fullpalmMsg.iterator
      var fullPalmMsg:FullpalmMsg = null
      val fullPalmMsgImgProperty = new ImageProperty
      while(fullPalmMsgs.hasNext){
        fullPalmMsg = fullPalmMsgs.next
        if(fullPalmMsg.fullPalmImageData !=null && fullPalmMsg.fullPalmImageData.length > 0){
          val blobBuilder = tpCard.addBlobBuilder()
          blobBuilder.setType(ImageType.IMAGETYPE_FULLPALM)
          blobBuilder.setPalmfgp(PalmFgp.valueOf(fullPalmMsg.fullPalmPostionCode.toInt))//全掌的掌位，待定？？？
          fullPalmMsgImgProperty.compressMethod = fullPalmMsg.fullPalmImageCompressMethodDescript
          fullPalmMsgImgProperty.imageType  = glocdef.GAIMG_IMAGETYPE_PALM.toByte
          fullPalmMsgImgProperty.width = fullPalmMsg.fullPalmImageHorizontalDirectionLength.toShort
          fullPalmMsgImgProperty.height = fullPalmMsg.fullPalmImageVerticalDirectionLength.toShort
          fullPalmMsgImgProperty.resolution = fullPalmMsg.fullPalmImageRatio.toShort
          fullPalmMsgImgProperty.bnData = fullPalmMsg.fullPalmImageData
          val gafisImage = convert2GafisImage(fullPalmMsgImgProperty)
          blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray(AncientConstants.GBK_ENCODING)))
        }
      }
    }
    if(fingerprintPackage.faceImages != null){
      val faceImages = fingerprintPackage.faceImages.faceImage.iterator
      var portraitsMsg:FaceImage = null
      while(faceImages.hasNext){
        portraitsMsg = faceImages.next
        if(portraitsMsg.personPictureImageData !=null && portraitsMsg.personPictureImageData.length > 0){
          val blobBuilder = tpCard.addBlobBuilder()
          blobBuilder.setType(ImageType.IMAGETYPE_FACE)
          blobBuilder.setFacefgp(FaceFgp.valueOf(portraitsMsg.personPictureTypeCode.toInt))
          blobBuilder.setStImageBytes(ByteString.copyFrom(portraitsMsg.personPictureImageData))
        }
      }
    }
    tpCard.build()
  }

  private def convert2GafisImage(imageProperty: ImageProperty, isLatent: Boolean = false): GAFISIMAGESTRUCT={
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.nCompressMethod = fpt4code.fptCprMethodToGafisCode(imageProperty.compressMethod).toByte
    if (gafisImg.stHead.nCompressMethod == 0) //no compress
      gafisImg.stHead.bIsCompressed = 0
    else
      gafisImg.stHead.bIsCompressed = 1
    gafisImg.stHead.nImageType = imageProperty.imageType
    gafisImg.stHead.nWidth = imageProperty.width
    gafisImg.stHead.nHeight = imageProperty.height
    gafisImg.stHead.nBits = 8
    gafisImg.stHead.nResolution = imageProperty.resolution
    gafisImg.bnData = imageProperty.bnData
    gafisImg.stHead.nImgSize = gafisImg.bnData.length
    if(!isLatent){//捺印指位
    val positionInt = imageProperty.positionCode
      if (positionInt > 10 && positionInt < 21 ) {
        //捺印平面指位[11,20]
        gafisImg.stHead.bIsPlain = 1
        gafisImg.stHead.nFingerIndex = (positionInt - 10).toByte
      }else{
        gafisImg.stHead.nFingerIndex = positionInt.toByte
      }
    }
    gafisImg
  }

  /**
    *
    * @param feature
    * @param featureCount
    * @return
    */
  private def convertFeature2FingerMinutiaSet(feature: String, featureCount: Int): Array[Minutia] = {
    val buffer = new mutable.ArrayBuffer[Minutia]()
    for (i <- 0 until featureCount) {
      val index = i * 9
      val mnts = feature.substring(index, index + 9)
      val mnt = new Minutia
      mnt.fingerFeaturePointXCoordinate = if(FPT5Utils.isNull(mnts.substring(0, 3))) 0 else mnts.substring(0, 3).toInt
      mnt.fingerFeaturePointYCoordinate = if(FPT5Utils.isNull(mnts.substring(3, 6))) 0 else mnts.substring(3, 6).toInt
      mnt.fingerFeatureDirection = if(FPT5Utils.isNull(mnts.substring(6, 9))) 0 else mnts.substring(6, 9).toInt
      buffer += mnt
    }
    buffer.toArray
  }

  private def convertFingerMinutiaSet2Feature(minutias:util.ArrayList[Minutia]):String = {
    var feature = ""
    val count  = minutias.size()
    for (i <- 0 until count){
      val minutia = minutias.get(i)
      feature + minutia.fingerFeaturePointXCoordinate.toString + minutia.fingerFeaturePointYCoordinate.toString + minutia.fingerFeatureDirection.toString
    }
    feature.toString
  }

  /**
    *
    * @param xPoint 特征X坐标
    * @param yPoint 特征Y坐标
    * @param range
    * @param direction
    * @param directionRange
    * @param reliability
    */
  class CenterFeature(val xPoint: Int,val yPoint: Int,val range: Int,val direction: Int,val directionRange: Int,val reliability: Int)

  /**
    * 把FPT4特征点转换成中心点特征点信息
    * @param feature
    * @return
    */
  def convertFpt4Feature2CenterFeature(feature: String):CenterFeature={
    if(feature != null && feature.nonEmpty && feature.length >= 14){
      val xPoint = if(FPT5Utils.isNull(feature.substring(0, 3)) || feature.substring(0, 3).trim == "") 0 else feature.substring(0, 3).toInt //指纹中心点特征X坐标
      val yPoint = if(FPT5Utils.isNull(feature.substring(3, 6)) || feature.substring(3, 6).trim == "") 0 else feature.substring(3, 6).toInt //指纹中心点特征Y坐标
      val pointRange = if(FPT5Utils.isNull(feature.substring(6, 8)) || feature.substring(6, 8).trim == "") 0 else feature.substring(6, 8).toInt //指纹中心点特征坐标范围
      val pointDirection = if(FPT5Utils.isNull(feature.substring(8, 11)) || feature.substring(8, 11).trim == "") 0 else feature.substring(8, 11).toInt //指纹中心点特征特征方向
      val pointDirectionRange = if(FPT5Utils.isNull(feature.substring(11, 13)) || feature.substring(11, 13).trim == "") 0 else feature.substring(11, 13).toInt //指纹中心点特征特征方向范围
      val pointReliability = if(FPT5Utils.isNull(feature.substring(13)) || feature.substring(13).trim == "") 0 else feature.substring(13).toInt //指纹中心点可靠度
      new CenterFeature(xPoint,yPoint,pointRange,pointDirection,pointDirectionRange,pointReliability)
    }else{
      null
    }
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

