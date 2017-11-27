package nirvana.hall.api.internal.fpt


import com.google.protobuf.ByteString
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.FingerLData
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gfpt5lib._
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FPTMntConverter
import nirvana.hall.protocol.api.FPTProto
import nirvana.hall.protocol.api.FPTProto.TPCard.TPCardBlob
import nirvana.hall.protocol.api.FPTProto.{LPCard, _}
import nirvana.hall.v70.internal.Gafis70Constants

import scala.collection.mutable.ListBuffer

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
//  def convertTPCard2FingerprintPackage(tpCard: TPCard): FingerprintPackage ={
//    val fingerprintPackage = new FingerprintPackage
//
////    fingerprintPackage.fingerprintMsg.fingerprintComparisonSysTypeDescript = "1900"  //指纹比对系统描述
////    fingerprintPackage.descriptMsg.originalSystemCasePersonId = tpCard.getStrCardID  //原始系统_案事件相关人员编号
////    fingerprintPackage.descriptMsg.jingZongPersonId = ""  //由警综平台标准化采集系统发号
////    fingerprintPackage.descriptMsg.casePersonid = ""  //案事件相关人员编号 由刑专系统发号，新数据必须提供
////    fingerprintPackage.descriptMsg.fingerPalmCardId = tpCard.getStrCardID   //指掌纹卡编号 系统自用
////    fingerprintPackage.descriptMsg.chopedCauseCode = "999"  //被捺印指纹原因代码
////    fingerprintPackage.descriptMsg.name = tpCard.getText.getStrName   //姓名
////    fingerprintPackage.descriptMsg.alias = tpCard.getText.getStrAliasName    //别名/绰号
////    fingerprintPackage.descriptMsg.sex = tpCard.getText.getNSex.toString  //性别代码
////    fingerprintPackage.descriptMsg.birthday = tpCard.getText.getStrBirthDate  //出生日期
////    fingerprintPackage.descriptMsg.nationality =  tpCard.getText.getStrRace//国籍代码
////    fingerprintPackage.descriptMsg.nation = tpCard.getText.getStrNation  //民族代码
////    fingerprintPackage.descriptMsg.credentialsCode = "001"  //常用证件代码
////    fingerprintPackage.descriptMsg.credentialsNo = tpCard.getText.getStrIdentityNum  //证件号码
////    fingerprintPackage.descriptMsg.houkouAdministrativeDivisionCode = tpCard.getText.getStrHuKouPlaceCode  //户籍地址_行政区划代码
////    fingerprintPackage.descriptMsg.houkouAddress = tpCard.getText.getStrHuKouPlaceTail  //户籍地址_地址名称
////    fingerprintPackage.descriptMsg.houseAdministrativeDivisionCode = tpCard.getText.getStrAddrCode  //现住址_行政区划代码
////    fingerprintPackage.descriptMsg.houseAddress = tpCard.getText.getStrAddr  //现住址_地址名称
////
////
////    fingerprintPackage.fingerprintMsg.chopUnitCode = tpCard.getText.getStrPrintUnitCode  //"111111111111"  //捺印单位_公安机关机构代码
////    fingerprintPackage.fingerprintMsg.chopUnitName = tpCard.getText.getStrPrintUnitName  //"234324"  //捺印单位_公安机关名称
////    fingerprintPackage.fingerprintMsg.chopPersonName = tpCard.getText.getStrPrinter  //"55555"  //捺印人员_姓名
////    fingerprintPackage.fingerprintMsg.chopPersonIdCard = "000000000000000"  //"120101198601031538"  //捺印人员_公民身份号码
////    fingerprintPackage.fingerprintMsg.chopPersonTel = ""  //"123213123"  //捺印人员_联系电话
////    fingerprintPackage.fingerprintMsg.chopDateTime =  "2017-10-11T10:00:01"   //捺印时间  tpCard.getText.getStrPrintDate
////    fingerprintPackage.fingerprintMsg.memo = tpCard.getText.getStrComment//"123213213213123123123123123213"  //备注
////    fingerprintPackage.fingerprintMsg.fingerNum = 0
////    fingerprintPackage.fingerprintMsg.palmNum = 0
////    fingerprintPackage.fingerprintMsg.fourFingerNum = 0
////    fingerprintPackage.fingerprintMsg.knuckleFingerNum = 0
////    fingerprintPackage.fingerprintMsg.fullPalmNum = 0
////    fingerprintPackage.fingerprintMsg.personPictureNum = 0
//
//
//    val iter = tpCard.getBlobList.iterator
//    while (iter.hasNext) {
//      val blob = iter.next()
//      blob.getType match {
////        //指纹
////        case ImageType.IMAGETYPE_FINGER =>
////          fingerprintPackage.fingerprintMsg.fingerNum = fingerprintPackage.fingerprintMsg.fingerNum + 1
////          if(!blob.getBPlain){
////            fingerprintPackage.fingers.rolling.fingerMsg.add(getFingerMsg(blob))
////          }else{
////            fingerprintPackage.fingers.planar.fingerMsg.add(getFingerMsg(blob))
////          }
////        //指节纹
////        case ImageType.IMAGETYPE_KNUCKLEPRINTS =>
////          fingerprintPackage.fingerprintMsg.knuckleFingerNum = fingerprintPackage.fingerprintMsg.knuckleFingerNum + 1
////          fingerprintPackage.knuckleprints.knuckleprintMsg.add(getKnuckleprintMsg(blob))
////        //掌纹
////        case ImageType.IMAGETYPE_PALM =>
////          fingerprintPackage.fingerprintMsg.palmNum = fingerprintPackage.fingerprintMsg.palmNum + 1
////          fingerprintPackage.palms.palmMsg.add(getPalmMsg(blob))
////        //四联指
////        case ImageType.IMAGETYPE_FOURPRINT =>
////          fingerprintPackage.fingerprintMsg.fourFingerNum = fingerprintPackage.fingerprintMsg.fourFingerNum + 1
////          fingerprintPackage.fourprints.fourprintMsg.add(getPalmFourPrintMsg(blob))
////        //全掌
////        case ImageType.IMAGETYPE_FULLPALM =>
////          fingerprintPackage.fingerprintMsg.fullPalmNum = fingerprintPackage.fingerprintMsg.fullPalmNum + 1
////          fingerprintPackage.fullpalms.fullpalmMsg.add(getFullPalmMsg(blob))
////        //人像
////        case ImageType.IMAGETYPE_FACE =>
////          val portraitsMsg = new PortraitsMsg
////          fingerprintPackage.fingerprintMsg.personPictureNum = fingerprintPackage.fingerprintMsg.personPictureNum + 1
////          val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
////          portraitsMsg.personPictureTypeCode = fgpPares(blob.getFacefgp)
////          portraitsMsg.personPictureImageData = gafisImage.bnData
////          fingerprintPackage.portraits.portraitsMsg.add(portraitsMsg)
////        case _ =>
//      }
//    }
//    fingerprintPackage
//  }
//
//
//
//  private def getFingerMsg(blob:TPCardBlob): FingerMsg ={
//    val fingerMsg = new FingerMsg
//    val gafisMnt = new GAFISIMAGESTRUCT
//    gafisMnt.fromStreamReader(blob.getStMntBytes.newInput())
//    val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
//    val fingerTData = FPTMntConverter.convertGafisMnt2FingerTData(gafisMnt)
//    fingerMsg.fingerPositionCode = if(fingerTData.fgp.matches("^([1-9])$")) ("0"+fingerTData.fgp) else (fingerTData.fgp)
//    fingerMsg.fingerFeatureExtractionMethodCode = fingerTData.extractMethod
//    fingerMsg.adactylismCauseCode = "3"
//    fingerMsg.fingerPatternMasterCode = fingerTData.pattern1
//    fingerMsg.fingerPatternSlaveCode = fingerTData.pattern2
////    fingerMsg.fingerDirectionDescript = fingerTData.fingerDirection
////    fingerMsg.fingerCenterPoint = fingerTData.centerPoint
////    fingerMsg.fingerSlaveCenter = if(FPT5Utils.isNull(fingerTData.subCenterPoint) || FPT5Utils.replaceLength(fingerTData.subCenterPoint,14))"" else fingerTData.subCenterPoint
////    fingerMsg.fingerLeftTriangle = if(FPT5Utils.isNull(fingerTData.leftTriangle) || FPT5Utils.replaceLength(fingerTData.leftTriangle,14))"" else fingerTData.leftTriangle
////    fingerMsg.fingerRightTriangle = if(FPT5Utils.isNull(fingerTData.rightTriangle) || FPT5Utils.replaceLength(fingerTData.rightTriangle,14))"" else fingerTData.rightTriangle
////    fingerMsg.fingerExtractionNum = fingerTData.featureCount.toInt
////    fingerMsg.fingerExtractionInfo = fingerTData.feature
//    fingerMsg.fingerCustomInfo = "".getBytes()
//    fingerMsg.fingerImageHorizontalDirectionLength = 640
//    fingerMsg.fingerImageVerticalDirectionLength = 640
//    fingerMsg.fingerImageRatio = 500
//    fingerMsg.fingerImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
//    fingerMsg.fingerImageData = gafisImage.bnData
//    fingerMsg
//  }
//
//  private def getKnuckleprintMsg(blob:TPCardBlob): KnuckleprintMsg ={
//    val knuckleprintMsg = new KnuckleprintMsg
//    val gafisMnt = new GAFISIMAGESTRUCT
//    gafisMnt.fromStreamReader(blob.getStMntBytes.newInput())
//    val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
//    val fingerTData = FPTMntConverter.convertGafisMnt2FingerTData(gafisMnt)
//    knuckleprintMsg.knucklePrintPostionCode = if(fingerTData.fgp.matches("^([1-9])$")) ("0"+fingerTData.fgp) else (fingerTData.fgp)
//    knuckleprintMsg.knucklePrintLackFingerCauseCode = "3"
//    knuckleprintMsg.knucklePrintCustomInfo = "".getBytes
//    knuckleprintMsg.knucklePrintImageHorizontalDirectionLength = 640
//    knuckleprintMsg.knucklePrintImageVerticalDirectionLength = 640
//    knuckleprintMsg.knucklePrintImageRatio = 500
//    knuckleprintMsg.knucklePrintImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
//    knuckleprintMsg.knucklePrintImageData = gafisImage.bnData
//    knuckleprintMsg
//  }
//
//  private def getPalmMsg(blob:TPCardBlob):PalmMsg = {
//    val palmMsg = new PalmMsg
//    val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
//    val fingerTData = FPTMntConverter.convertGafisMnt2FingerTData(gafisImage)
//    palmMsg.palmPostionCode = blob.getPalmfgp.getNumber.toString
//    palmMsg.lackPalmCauseCode = "3"
//    palmMsg.palmFeatureExtractionMethodCode = fingerTData.extractMethod
////    palmMsg.palmRetracingPoint = 0
////    palmMsg.palmRetracingPointInfo = ""
////    palmMsg.palmTrianglePointNum = 0
////    palmMsg.palmTrianglePointInfo = ""
////    palmMsg.palmFeaturePointNum = fingerTData.featureCount.toInt
////    palmMsg.palmFeaturePointInfo = if(FPT5Utils.isNull(fingerTData.feature)) "" else fingerTData.feature
//    palmMsg.palmCustomInfo = "".getBytes()
//    palmMsg.palmImageHorizontalDirectionLength = fingerTData.imgHorizontalLength.toInt
//    palmMsg.palmImageVerticalDirectionLength = fingerTData.imgVerticalLength.toInt
//    palmMsg.palmImageRatio = fingerTData.dpi.toInt
//    palmMsg.palmImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
//    palmMsg.palmImageData = gafisImage.bnData
//    palmMsg
//  }
//
//  private def getPalmFourPrintMsg(blob:TPCardBlob):FourprintMsg = {
//    val fourprintMsg = new FourprintMsg
//    val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
//    val fingerTData = FPTMntConverter.convertGafisMnt2FingerTData(gafisImage)
//    fourprintMsg.fourPrintPostionCode = blob.getPalmfgp.getNumber.toString
//    fourprintMsg.fourPrintLackFingerCauseCode = "3"
//    fourprintMsg.fourPrintCustomInfo = "".getBytes()
//    fourprintMsg.fourPrintImageHorizontalDirectionLength = fingerTData.imgHorizontalLength.toInt
//    fourprintMsg.fourPrintImageVerticalDirectionLength = fingerTData.imgVerticalLength.toInt
//    fourprintMsg.fourPrintImageRatio = fingerTData.dpi.toInt
//    fourprintMsg.fourPrintImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
//    fourprintMsg.fourPrintImageData = gafisImage.bnData
//    fourprintMsg
//  }
//
//  private def getFullPalmMsg(blob:TPCardBlob):FullpalmMsg = {
//    val fullpalmMsg = new FullpalmMsg
//    val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
//    val fingerTData = FPTMntConverter.convertGafisMnt2FingerTData(gafisImage)
//    fullpalmMsg.fullPalmPostionCode = blob.getPalmfgp.getNumber.toString
//    fullpalmMsg.fullPalmLackPalmCauseCode = "3"
//    fullpalmMsg.fullPalmCustomInfo = "".getBytes()
//    fullpalmMsg.fullPalmImageHorizontalDirectionLength = fingerTData.imgHorizontalLength.toInt
//    fullpalmMsg.fullPalmImageVerticalDirectionLength = fingerTData.imgVerticalLength.toInt
//    fullpalmMsg.fullPalmImageRatio = fingerTData.dpi.toInt
//    fullpalmMsg.fullPalmImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
//    fullpalmMsg.fullPalmImageData = gafisImage.bnData
//    fullpalmMsg
//  }
//
//
//  /**
//    * 人像转换
//    * @param fgp
//    * @return
//    */
//  def fgpPares(fgp:FPTProto.FaceFgp): String ={
//    fgp match {
//      case FaceFgp.FACE_FRONT =>
//        "1"
//      case FaceFgp.FACE_RIGHT =>
//        "2"
//      case FaceFgp.FACE_LEFT =>
//        "3"
//      case _ =>
//        "0"
//    }
//  }
//
//  /**
//    * 将解析出的指位翻译成系统中的枚举类型,ProtoBuffer
//    */
//  def fgpParesString(fgp:PalmFgp): String ={
//    fgp match {
//      case PalmFgp.PALM_RIGHT =>
//        "1"
//      case PalmFgp.PALM_LEFT =>
//        "2"
//      case PalmFgp.PALM_FINGER_R =>
//        "3"
//      case PalmFgp.PALM_FINGER_L =>
//        "4"
//      case PalmFgp.PALM_THUMB_R_LOW =>
//        "5"
//      case PalmFgp.PALM_THUMB_R_UP =>
//        "6"
//      case PalmFgp.PALM_THUMB_L_LOW =>
//        "7"
//      case PalmFgp.PALM_THUMB_L_UP =>
//        "8"
//      case PalmFgp.PALM_RIGTH_SIDE =>
//        "9"
//      case PalmFgp.PALM_LEFT_SIDE =>
//        "10"
//      case PalmFgp.PALM_UNKNOWN =>
//        "0"
//    }
//  }
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
//  /**
//    * 将指纹包转换为tpcard protobuf对象
//    * @param fingerprintPackage
//    * @return
//    */
//  def convertFingerprintPackage2TPCard(fingerprintPackage:FingerprintPackage):TPCard = {
//    val tpCard = TPCard.newBuilder()
//    val textBuilder = tpCard.getTextBuilder
////    tpCard.setStrCardID(fingerprintPackage.descriptMsg.fingerPalmCardId)
////    tpCard.setStrMisPersonID(fingerprintPackage.descriptMsg.casePersonid)
////    textBuilder.setStrName(fingerprintPackage.descriptMsg.name)
////    textBuilder.setStrAliasName(fingerprintPackage.descriptMsg.alias)
////    if (fingerprintPackage.descriptMsg.sex != null && fingerprintPackage.descriptMsg.sex.length > 0) {
////      textBuilder.setNSex(fingerprintPackage.descriptMsg.sex.toInt)
////    }
////    textBuilder.setStrNation(fingerprintPackage.descriptMsg.nation)
////    textBuilder.setStrRace(fingerprintPackage.descriptMsg.nationality)
////    textBuilder.setStrCertifType(fingerprintPackage.descriptMsg.credentialsCode)
////    textBuilder.setStrCertifID(fingerprintPackage.descriptMsg.credentialsNo)
////    textBuilder.setStrBirthDate(fingerprintPackage.descriptMsg.birthday)
////    textBuilder.setStrIdentityNum(fingerprintPackage.descriptMsg.credentialsNo)
////    textBuilder.setStrHuKouPlaceCode(fingerprintPackage.descriptMsg.houkouAdministrativeDivisionCode)
////    textBuilder.setStrHuKouPlaceTail(fingerprintPackage.descriptMsg.houkouAddress)
////    textBuilder.setStrAddrCode(fingerprintPackage.descriptMsg.houseAdministrativeDivisionCode)
////    textBuilder.setStrAddr(fingerprintPackage.descriptMsg.houseAddress)
////    textBuilder.setStrPrintUnitCode(fingerprintPackage.fingerprintMsg.chopUnitCode)
////    textBuilder.setStrPrintUnitName(fingerprintPackage.fingerprintMsg.chopUnitName)
////    textBuilder.setStrPrinter(fingerprintPackage.fingerprintMsg.chopPersonName)
////    textBuilder.setStrPrintDate(fingerprintPackage.fingerprintMsg.chopDateTime)
////    tpCard.setStrPrinterIdCardId(fingerprintPackage.fingerprintMsg.chopPersonIdCard)
////    tpCard.setStrPrinterTel(fingerprintPackage.fingerprintMsg.chopPersonTel)
////    textBuilder.setStrComment(fingerprintPackage.fingerprintMsg.memo)
//    tpCard.setStrDataSource(Gafis70Constants.DATA_SOURCE_FPT)
//
//    //val planar = fingerprintPackage.fingers.planar.fingerMsg.iterator
//    var planarImg:FingerMsg = null
//    val planarImgProperty = new ImageProperty
//    //while(planar.hasNext){
//      //planarImg = planar.next
//      if(planarImg.fingerImageData !=null && planarImg.fingerImageData.length > 0){
//        val blobBuilder = tpCard.addBlobBuilder()
//            blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
//            if(planarImg.fingerPositionCode.toInt > 10){
//              blobBuilder.setFgp(FingerFgp.valueOf(planarImg.fingerPositionCode.toInt - 10))
//            }else{
//              blobBuilder.setFgp(FingerFgp.valueOf(planarImg.fingerPositionCode.toInt))
//            }
//            blobBuilder.setBPlain(true)
//        planarImgProperty.compressMethod  = planarImg.fingerImageCompressMethodDescript
//        planarImgProperty.imageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
//        planarImgProperty.width = planarImg.fingerImageHorizontalDirectionLength.toShort
//        planarImgProperty.height = planarImg.fingerImageVerticalDirectionLength.toShort
//        planarImgProperty.resolution = planarImg.fingerImageRatio.toShort
//        planarImgProperty.positionCode = planarImg.fingerPositionCode.toShort
//        planarImgProperty.bnData = planarImg.fingerImageData
////            val gafisImage = convert2GafisImage(planarImgProperty)
////            blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray(AncientConstants.GBK_ENCODING)))
//      }
//    }
//
//    val rolling = fingerprintPackage.fingers.rolling.fingerMsg.iterator
//    var rollingImg:FingerMsg = null
//    val rollingImgProperty = new ImageProperty
//    while(rolling.hasNext){
//      rollingImg = rolling.next
//      if(rollingImg.fingerImageData !=null && rollingImg.fingerImageData.length > 0){
//        val blobBuilder = tpCard.addBlobBuilder()
//        blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
//        if(planarImg.fingerPositionCode.toInt > 10){
//          blobBuilder.setFgp(FingerFgp.valueOf(rollingImg.fingerPositionCode.toInt - 10))
//        }else{
//          blobBuilder.setFgp(FingerFgp.valueOf(rollingImg.fingerPositionCode.toInt))
//        }
//        blobBuilder.setBPlain(false)
//        rollingImgProperty.compressMethod  = rollingImg.fingerImageCompressMethodDescript
//        rollingImgProperty.imageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
//        rollingImgProperty.width = rollingImg.fingerImageHorizontalDirectionLength.toShort
//        rollingImgProperty.height = rollingImg.fingerImageVerticalDirectionLength.toShort
//        rollingImgProperty.resolution = rollingImg.fingerImageRatio.toShort
//        rollingImgProperty.positionCode = rollingImg.fingerPositionCode.toShort
//        rollingImgProperty.bnData = rollingImg.fingerImageData
//        val gafisImage = convert2GafisImage(rollingImgProperty)
//        blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray(AncientConstants.GBK_ENCODING)))
//      }
//    }
//
//    val knucklePrints = fingerprintPackage.knuckleprints.knuckleprintMsg.iterator
//    var knucklePrintImg:KnuckleprintMsg = null
//    val knucklePrintImgProperty = new ImageProperty
//    while(knucklePrints.hasNext){
//      knucklePrintImg = knucklePrints.next
//      if(knucklePrintImg.knucklePrintImageData !=null && knucklePrintImg.knucklePrintImageData.length > 0){
//        val blobBuilder = tpCard.addBlobBuilder()
//        blobBuilder.setType(ImageType.IMAGETYPE_KNUCKLEPRINTS)
//        blobBuilder.setFgp(FingerFgp.valueOf(knucklePrintImg.knucklePrintPostionCode.toInt))//指节纹的位置 待定？？？
//        knucklePrintImgProperty.compressMethod = knucklePrintImg.knucklePrintImageCompressMethodDescript
//        knucklePrintImgProperty.imageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
//        knucklePrintImgProperty.width = knucklePrintImg.knucklePrintImageHorizontalDirectionLength.toShort
//        knucklePrintImgProperty.height = knucklePrintImg.knucklePrintImageVerticalDirectionLength.toShort
//        knucklePrintImgProperty.resolution = knucklePrintImg.knucklePrintImageRatio.toShort
//        knucklePrintImgProperty.positionCode = knucklePrintImg.knucklePrintPostionCode.toShort
//        knucklePrintImgProperty.bnData = knucklePrintImg.knucklePrintImageData
//        val gafisImage = convert2GafisImage(knucklePrintImgProperty)
//        blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray(AncientConstants.GBK_ENCODING)))
//      }
//    }
//
//    val palmMsg = fingerprintPackage.palms.palmMsg.iterator
//    var palmMsgImg:PalmMsg = null
//    val palmMsgImgProperty = new ImageProperty
//    while(palmMsg.hasNext){
//      palmMsgImg = palmMsg.next
//      if(palmMsgImg.palmImageData !=null && palmMsgImg.palmImageData.length > 0){
//        val blobBuilder = tpCard.addBlobBuilder()
//        blobBuilder.setType(ImageType.IMAGETYPE_PALM)
//        blobBuilder.setPalmfgp(PalmFgp.valueOf(palmMsgImg.palmPostionCode.toInt))
//        palmMsgImgProperty.compressMethod = palmMsgImg.palmImageCompressMethodDescript
//        palmMsgImgProperty.imageType = glocdef.GAIMG_IMAGETYPE_PALM.toByte
//        palmMsgImgProperty.width = palmMsgImg.palmImageHorizontalDirectionLength.toShort
//        palmMsgImgProperty.height = palmMsgImg.palmImageVerticalDirectionLength.toShort
//        palmMsgImgProperty.resolution = palmMsgImg.palmImageRatio.toShort
//        palmMsgImgProperty.positionCode = palmMsgImg.palmPostionCode.toShort
//        palmMsgImgProperty.bnData = palmMsgImg.palmImageData
//        val gafisImage = convert2GafisImage(palmMsgImgProperty)
//        blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray(AncientConstants.GBK_ENCODING)))
//      }
//    }
//
//    val fourPrint = fingerprintPackage.fourprints.fourprintMsg.iterator
//    var fourPrintImg:FourprintMsg = null
//    val fourPrintImgProperty = new ImageProperty
//    while(fourPrint.hasNext){
//      fourPrintImg = fourPrint.next
//      if(fourPrintImg.fourPrintImageData !=null && fourPrintImg.fourPrintImageData.length > 0){
//        val blobBuilder = tpCard.addBlobBuilder()
//        blobBuilder.setType(ImageType.IMAGETYPE_FOURPRINT)
//        blobBuilder.setPalmfgp(PalmFgp.valueOf(fourPrintImg.fourPrintPostionCode.toInt))//四联指 是分左右吗？
//        fourPrintImgProperty.compressMethod = fourPrintImg.fourPrintImageCompressMethodDescript
//        fourPrintImgProperty.imageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
//        fourPrintImgProperty.width = fourPrintImg.fourPrintImageHorizontalDirectionLength.toShort
//        fourPrintImgProperty.height = fourPrintImg.fourPrintImageVerticalDirectionLength.toShort
//        fourPrintImgProperty.resolution = fourPrintImg.fourPrintImageRatio.toShort
//        fourPrintImgProperty.positionCode = fourPrintImg.fourPrintPostionCode.toShort
//        fourPrintImgProperty.bnData = fourPrintImg.fourPrintImageData
//        val gafisImage = convert2GafisImage(fourPrintImgProperty)
//        blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray(AncientConstants.GBK_ENCODING)))
//      }
//    }
//
//
//    val fullPalmMsgs = fingerprintPackage.fullpalms.fullpalmMsg.iterator
//    var fullPalmMsg:FullpalmMsg = null
//    val fullPalmMsgImgProperty = new ImageProperty
//    while(fullPalmMsgs.hasNext){
//      fullPalmMsg = fullPalmMsgs.next
//      if(fullPalmMsg.fullPalmImageData !=null && fullPalmMsg.fullPalmImageData.length > 0){
//        val blobBuilder = tpCard.addBlobBuilder()
//        blobBuilder.setType(ImageType.IMAGETYPE_FULLPALM)
//        blobBuilder.setPalmfgp(PalmFgp.valueOf(fullPalmMsg.fullPalmPostionCode.toInt))//全掌的掌位，待定？？？
//        fullPalmMsgImgProperty.compressMethod = fullPalmMsg.fullPalmImageCompressMethodDescript
//        fullPalmMsgImgProperty.imageType  = glocdef.GAIMG_IMAGETYPE_PALM.toByte
//        fullPalmMsgImgProperty.width = fullPalmMsg.fullPalmImageHorizontalDirectionLength.toShort
//        fullPalmMsgImgProperty.height = fullPalmMsg.fullPalmImageVerticalDirectionLength.toShort
//        fullPalmMsgImgProperty.resolution = fullPalmMsg.fullPalmImageRatio.toShort
//        fullPalmMsgImgProperty.positionCode = fullPalmMsg.fullPalmPostionCode.toShort
//        fullPalmMsgImgProperty.bnData = fullPalmMsg.fullPalmImageData
//        val gafisImage = convert2GafisImage(fullPalmMsgImgProperty)
//        blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray(AncientConstants.GBK_ENCODING)))
//      }
//    }
//
//
//    val portraitsMsgs = fingerprintPackage.portraits.portraitsMsg.iterator
//    var portraitsMsg:PortraitsMsg = null
//    while(portraitsMsgs.hasNext){
//      portraitsMsg = portraitsMsgs.next
//      if(portraitsMsg.personPictureImageData !=null && portraitsMsg.personPictureImageData.length > 0){
//        val blobBuilder = tpCard.addBlobBuilder()
//        blobBuilder.setType(ImageType.IMAGETYPE_FACE)
//        blobBuilder.setFacefgp(FaceFgp.valueOf(portraitsMsg.personPictureTypeCode.toInt))
//        blobBuilder.setStImageBytes(ByteString.copyFrom(portraitsMsg.personPictureImageData))
//      }
//    }
//    tpCard.build()
//  }
//
//  private def convert2GafisImage(imageProperty: ImageProperty, isLatent: Boolean = false): GAFISIMAGESTRUCT={
//    val gafisImg = new GAFISIMAGESTRUCT
//    gafisImg.stHead.nCompressMethod = fpt4code.fptCprMethodToGafisCode(imageProperty.compressMethod).toByte
//    if (gafisImg.stHead.nCompressMethod == 0) //no compress
//      gafisImg.stHead.bIsCompressed = 0
//    else
//      gafisImg.stHead.bIsCompressed = 1
//
//    gafisImg.stHead.nImageType = imageProperty.imageType
//    gafisImg.stHead.nWidth = imageProperty.width
//    gafisImg.stHead.nHeight = imageProperty.height
//    gafisImg.stHead.nBits = 8
//    gafisImg.stHead.nResolution = imageProperty.resolution
//    gafisImg.bnData = imageProperty.bnData
//    gafisImg.stHead.nImgSize = gafisImg.bnData.length
//    if(!isLatent){//捺印指位
//    val positionInt = imageProperty.positionCode
//      if (positionInt > 10) {
//        //捺印平面指位[11,20]
//        gafisImg.stHead.bIsPlain = 1
//        gafisImg.stHead.nFingerIndex = (positionInt - 10).toByte
//      }else{
//        gafisImg.stHead.nFingerIndex = positionInt.toByte
//      }
//    }
//
//    gafisImg
//  }
//
//  class ImageProperty{
//    var compressMethod:String = _
//    var imageType:Byte = _
//    var width:Short = _
//    var height:Short = _
//    var resolution:Short = _
//    var bnData:Array[Byte] = _
//    var positionCode:Int = _
//  }
}
