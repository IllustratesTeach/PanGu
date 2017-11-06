package nirvana.hall.api.internal.fpt

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.FingerLData
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gfpt5lib._
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FPTMntConverter
import nirvana.hall.protocol.api.FPTProto
import nirvana.hall.protocol.api.FPTProto.{LPCard, _}

/**
  * FPT5数据转换类
  * Created by songpeng on 2017/11/3.
  */
object FPT5Converter {

  /**
    * 将捺印proto信息转为FingerprintPackage
    * @param tpCard 捺印信息
    * @return
    */
  def convertTPCard2FingerprintPackage(tpCard: TPCard): FingerprintPackage ={
    val fingerprintPackage = new FingerprintPackage

    fingerprintPackage.fingerprintMsg.fingerprintComparisonSysTypeDescript = "1900"  //指纹比对系统描述
    fingerprintPackage.descriptMsg.originalSystemCasePersonId = tpCard.getStrCardID  //原始系统_案事件相关人员编号
    fingerprintPackage.descriptMsg.jingZongPersonId = ""  //警综人员编号
    fingerprintPackage.descriptMsg.casePersonid = tpCard.getStrCardID  //案事件相关人员编号
    fingerprintPackage.descriptMsg.fingerPalmCardId = tpCard.getStrCardID   //指掌纹卡编号
    fingerprintPackage.descriptMsg.chopedCauseCode = "999"  //被捺印指纹原因代码
    fingerprintPackage.descriptMsg.name = tpCard.getText.getStrName   //姓名
    fingerprintPackage.descriptMsg.alias = tpCard.getText.getStrAliasName    //别名/绰号
    fingerprintPackage.descriptMsg.sex = tpCard.getText.getNSex.toString  //性别代码
    fingerprintPackage.descriptMsg.birthday = tpCard.getText.getStrBirthDate  //出生日期
    fingerprintPackage.descriptMsg.nationality = tpCard.getText.getStrNation //国籍代码
    fingerprintPackage.descriptMsg.nation = tpCard.getText.getStrRace  //民族代码
    fingerprintPackage.descriptMsg.credentialsCode = "001"  //常用证件代码
    fingerprintPackage.descriptMsg.credentialsNo = tpCard.getText.getStrIdentityNum  //证件号码
    fingerprintPackage.descriptMsg.houkouAdministrativeDivisionCode = tpCard.getText.getStrHuKouPlaceCode  //户籍地址_行政区划代码
    fingerprintPackage.descriptMsg.houkouAddress = tpCard.getText.getStrHuKouPlaceTail  //户籍地址_地址名称
    fingerprintPackage.descriptMsg.houseAdministrativeDivisionCode = tpCard.getText.getStrAddrCode  //现住址_行政区划代码
    fingerprintPackage.descriptMsg.houseAddress = tpCard.getText.getStrAddr  //现住址_地址名称

    fingerprintPackage.fingerprintMsg.chopUnitCode = tpCard.getText.getStrPrintUnitCode  //"111111111111"  //捺印单位_公安机关机构代码
    fingerprintPackage.fingerprintMsg.chopUnitName = tpCard.getText.getStrPrintUnitName  //"234324"  //捺印单位_公安机关名称
    fingerprintPackage.fingerprintMsg.chopPersonName = tpCard.getText.getStrPrinter  //"55555"  //捺印人员_姓名
    fingerprintPackage.fingerprintMsg.chopPersonIdCard = "000000000000000"  //"120101198601031538"  //捺印人员_公民身份号码
    fingerprintPackage.fingerprintMsg.chopPersonTel = ""  //"123213123"  //捺印人员_联系电话
    fingerprintPackage.fingerprintMsg.chopDateTime =  "2017-10-11T10:00:01"   //捺印时间  tpCard.getText.getStrPrintDate
    fingerprintPackage.fingerprintMsg.memo = tpCard.getText.getStrComment//"123213213213123123123123123213"  //备注
    fingerprintPackage.fingerprintMsg.fingerNum = 0
    fingerprintPackage.fingerprintMsg.palmNum = 0
    fingerprintPackage.fingerprintMsg.fourFingerNum = 0
    fingerprintPackage.fingerprintMsg.knuckleFingerNum = 0
    fingerprintPackage.fingerprintMsg.fullPalmNum = 0
    fingerprintPackage.fingerprintMsg.personPictureNum = 0


    val iter = tpCard.getBlobList.iterator()
    while (iter.hasNext) {
      val blob = iter.next()
      blob.getType match {
        //指纹
        case ImageType.IMAGETYPE_FINGER =>
          fingerprintPackage.fingerprintMsg.fingerNum = fingerprintPackage.fingerprintMsg.fingerNum + 1
          if(!blob.getBPlain){
            val rollingMsg = new FingerMsg
            val gafisMnt = new GAFISIMAGESTRUCT
            gafisMnt.fromStreamReader(blob.getStMntBytes.newInput())
            val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
            val fingerTData = FPTMntConverter.convertGafisMnt2FingerTData(gafisMnt)
            rollingMsg.fingerPositionCode = if(fingerTData.fgp.equals("10")) (fingerTData.fgp) else ("0"+fingerTData.fgp)
            rollingMsg.fingerFeatureExtractionMethodCode = fingerTData.extractMethod
            rollingMsg.adactylismCauseCode = "3"
            rollingMsg.fingerPatternMasterCode = fingerTData.pattern1
            rollingMsg.fingerPatternSlaveCode = fingerTData.pattern2
            rollingMsg.fingerDirectionDescript = fingerTData.fingerDirection
            rollingMsg.fingerCenterPoint = fingerTData.centerPoint
            rollingMsg.fingerSlaveCenter = if(FPT5Utils.isNull(fingerTData.subCenterPoint) || FPT5Utils.replaceLength(fingerTData.subCenterPoint,14))"" else fingerTData.subCenterPoint
            rollingMsg.fingerLeftTriangle = if(FPT5Utils.isNull(fingerTData.leftTriangle) || FPT5Utils.replaceLength(fingerTData.leftTriangle,14))"" else fingerTData.leftTriangle
            rollingMsg.fingerRightTriangle = if(FPT5Utils.isNull(fingerTData.rightTriangle) || FPT5Utils.replaceLength(fingerTData.rightTriangle,14))"" else fingerTData.rightTriangle
            rollingMsg.fingerExtractionNum = fingerTData.featureCount.toInt
            rollingMsg.fingerExtractionInfo = fingerTData.feature
            rollingMsg.fingerCustomInfo = "".getBytes()
            rollingMsg.fingerImageHorizontalDirectionLength = 640
            rollingMsg.fingerImageVerticalDirectionLength = 640
            rollingMsg.fingerImageRatio = 500
            rollingMsg.fingerImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
            if (gafisImage.stHead.nCompressMethod == glocdef.GAIMG_CPRMETHOD_GFS) {
              gafisImage.transformForFPT()
              rollingMsg.fingerImageData = gafisImage.toByteArray(AncientConstants.GBK_ENCODING)
            } else {
              rollingMsg.fingerImageData = gafisImage.bnData
            }
            fingerprintPackage.fingers.rolling.fingerMsg.add(rollingMsg)
          }else{
            val planarMsg = new FingerMsg
            val gafisMnt = new GAFISIMAGESTRUCT
            gafisMnt.fromStreamReader(blob.getStMntBytes.newInput())
            val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
            val fingerTData = FPTMntConverter.convertGafisMnt2FingerTData(gafisMnt)
            planarMsg.fingerPositionCode = if(fingerTData.fgp.equals("10")) (fingerTData.fgp) else ("0"+fingerTData.fgp)
            planarMsg.fingerFeatureExtractionMethodCode = fingerTData.extractMethod
            planarMsg.adactylismCauseCode = "3"
            planarMsg.fingerPatternMasterCode = fingerTData.pattern1
            planarMsg.fingerPatternSlaveCode = fingerTData.pattern2
            planarMsg.fingerDirectionDescript = fingerTData.fingerDirection
            planarMsg.fingerCenterPoint = fingerTData.centerPoint
            planarMsg.fingerSlaveCenter = if(FPT5Utils.isNull(fingerTData.subCenterPoint) || FPT5Utils.replaceLength(fingerTData.subCenterPoint,14))"" else fingerTData.subCenterPoint
            planarMsg.fingerLeftTriangle = if(FPT5Utils.isNull(fingerTData.leftTriangle) || FPT5Utils.replaceLength(fingerTData.leftTriangle,14))"" else fingerTData.leftTriangle
            planarMsg.fingerRightTriangle = if(FPT5Utils.isNull(fingerTData.rightTriangle) || FPT5Utils.replaceLength(fingerTData.rightTriangle,14))"" else fingerTData.rightTriangle
            planarMsg.fingerExtractionNum = fingerTData.featureCount.toInt
            planarMsg.fingerExtractionInfo = fingerTData.feature
            planarMsg.fingerCustomInfo = "".getBytes()
            planarMsg.fingerImageHorizontalDirectionLength = 640
            planarMsg.fingerImageVerticalDirectionLength = 640
            planarMsg.fingerImageRatio = 500
            planarMsg.fingerImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
            if (gafisImage.stHead.nCompressMethod == glocdef.GAIMG_CPRMETHOD_GFS) {
              gafisImage.transformForFPT()
              planarMsg.fingerImageData = gafisImage.toByteArray(AncientConstants.GBK_ENCODING)
            } else {
              planarMsg.fingerImageData = gafisImage.bnData
            }
            fingerprintPackage.fingers.planar.fingerMsg.add(planarMsg)
          }
        //掌纹
        case ImageType.IMAGETYPE_PALM =>
          val palmMsg = new PalmMsg
          fingerprintPackage.fingerprintMsg.palmNum = fingerprintPackage.fingerprintMsg.palmNum + 1
          val gafisMnt = new GAFISIMAGESTRUCT
          gafisMnt.fromStreamReader(blob.getStMntBytes.newInput())
          val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
          val fingerTData = FPTMntConverter.convertGafisMnt2FingerTData(gafisMnt)
          palmMsg.palmPostionCode = if(fgpParesString(blob.getPalmfgp).equals("10")) fgpParesString(blob.getPalmfgp) else ("0" + fgpParesString(blob.getPalmfgp))
          palmMsg.lackPalmCauseCode = "3"
          palmMsg.palmFeatureExtractionMethodCode = ""
          palmMsg.palmRetracingPoint = 0
          palmMsg.palmRetracingPointInfo = ""
          palmMsg.palmTrianglePointNum = 0
          palmMsg.palmTrianglePointInfo = ""
          palmMsg.palmFeaturePointNum = fingerTData.featureCount.toInt
          palmMsg.palmFeaturePointInfo = if(FPT5Utils.isNull(fingerTData.feature)) "" else fingerTData.feature
          palmMsg.palmCustomInfo = "".getBytes()
          palmMsg.palmImageHorizontalDirectionLength = fingerTData.imgHorizontalLength.toInt
          palmMsg.palmImageVerticalDirectionLength = fingerTData.imgVerticalLength.toInt
          palmMsg.palmImageRatio = fingerTData.dpi.toInt
          palmMsg.palmImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
          if (gafisImage.stHead.nCompressMethod == glocdef.GAIMG_CPRMETHOD_GFS) {
            gafisImage.transformForFPT()
            palmMsg.palmImageData = gafisImage.toByteArray(AncientConstants.GBK_ENCODING)
          } else {
            palmMsg.palmImageData = gafisImage.bnData
          }
          fingerprintPackage.palms.palmMsg.add(palmMsg)
        //人像
        case ImageType.IMAGETYPE_FACE =>
          val portraitsMsg = new PortraitsMsg
          fingerprintPackage.fingerprintMsg.personPictureNum = fingerprintPackage.fingerprintMsg.personPictureNum + 1
          val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
          portraitsMsg.personPictureTypeCode = fgpPares(blob.getFacefgp)
          portraitsMsg.personPictureImageData = gafisImage.bnData
          fingerprintPackage.portraits.portraitsMsg.add(portraitsMsg)
        case _ =>
      }
    }
    fingerprintPackage
  }

  /**
    * 人像转换
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
    * 将解析出的指位翻译成系统中的枚举类型,ProtoBuffer
    */
  def fgpParesString(fgp:PalmFgp): String ={
    fgp match {
      case PalmFgp.PALM_RIGHT =>
        "1"
      case PalmFgp.PALM_LEFT =>
        "2"
      case PalmFgp.PALM_FINGER_R =>
        "3"
      case PalmFgp.PALM_FINGER_L =>
        "4"
      case PalmFgp.PALM_THUMB_R_LOW =>
        "5"
      case PalmFgp.PALM_THUMB_R_UP =>
        "6"
      case PalmFgp.PALM_THUMB_L_LOW =>
        "7"
      case PalmFgp.PALM_THUMB_L_UP =>
        "8"
      case PalmFgp.PALM_RIGTH_SIDE =>
        "9"
      case PalmFgp.PALM_LEFT_SIDE =>
        "10"
      case PalmFgp.PALM_UNKNOWN =>
        "99"
    }
  }

  /**
    * 将现场proto信息转为LatentPackage
    * @param caseInfo 案件信息
    * @param lpCards 现场指纹信息
    * @return
    */
  def convertCaseInfoAndLPCard2LatentPackage(caseInfo: Case, lpCards: Seq[LPCard], palmCards: Seq[LPCard]): LatentPackage = {
    val latentPackage = new LatentPackage
    latentPackage.caseMsg.latentFingerprintComparisonSysTypeDescript = "1900"  //指纹比对系统描述
    latentPackage.caseMsg.originalSystemCaseId = caseInfo.getStrCaseID  //原始系统_案事件编号
    latentPackage.caseMsg.caseId = caseInfo.getStrCaseID  //案事件编号
    latentPackage.caseMsg.latentSurveyId = caseInfo.getStrCaseID  //现场勘验编号
    latentPackage.caseMsg.latentCardId = ""  //现场指掌纹卡编号
    latentPackage.caseMsg.caseTypeCode = caseInfo.getText.getStrCaseType1  //案件类别代码
    latentPackage.caseMsg.money = caseInfo.getText.getStrMoneyLost  //金额（人民币元）
    latentPackage.caseMsg.caseOccurAdministrativeDivisionCode = caseInfo.getText.getStrCaseOccurPlaceCode  //案事件发生地点_行政区划代码
    latentPackage.caseMsg.caseOccurAddress = caseInfo.getText.getStrCaseOccurPlace  //案事件发生地点_地址名称
    latentPackage.caseMsg.briefCase = caseInfo.getText.getStrComment  //简要案情
    latentPackage.caseMsg.whetherKill = if (caseInfo.getText.getBPersonKilled) "1" else "0"  //是否命案_判断标识
    latentPackage.caseMsg.extractUnitCode = caseInfo.getText.getStrExtractUnitCode  //提取单位_公安机关机构代码
    latentPackage.caseMsg.extractUnitName = caseInfo.getText.getStrExtractUnitName  //提取单位_公安机关名称
    latentPackage.caseMsg.extractPerson = caseInfo.getText.getStrExtractor  //提取人员_姓名
    latentPackage.caseMsg.extractPersonIdCard = "000000000000000"  //提取人员_公民身份号码
    latentPackage.caseMsg.extractPersonTel = ""  //提取人员_联系电话
    latentPackage.caseMsg.extractDateTime = "2017-10-11T10:00:01"   //提取时间  caseInfo.getText.getStrExtractDate
    latentPackage.caseMsg.latentFingerNum = lpCards.size
    latentPackage.caseMsg.latentPalmNum = palmCards.size

    lpCards.foreach( lpcard =>{

      val latentfingerMsg = new LatentFingerMsg
      var fingerLData = new FingerLData
      val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(lpcard.getBlob.getStImageBytes.toByteArray)

      latentfingerMsg.originalSystemLatentFingerPalmId = lpcard.getStrCardID  //原始系统_现场指掌纹编号
      latentfingerMsg.latentPhysicalId = "R12345678901234567890123456789"  //现场物证编号
      latentfingerMsg.latentFingerLeftPosition = ""  //现场指纹_现场指掌纹遗留部位
      latentfingerMsg.latentFingerFeatureExtractMethodCode = "M"  //现场指纹_指掌纹特征提取方式代码
      latentfingerMsg.latentFingerCorpseJudgeIdentify = if(lpcard.getText.getBDeadBody) "1" else "0"  //现场指纹_尸体指掌纹_判断标识
      latentfingerMsg.latentFingerMastoidProcessLineColorCode = if(FPT5Utils.isNull(lpcard.getText.getStrRidgeColor)) "" else lpcard.getText.getStrRidgeColor  //现场指纹_乳突线颜色代码
      latentfingerMsg.latentFingerConnectFingerBeginPhysicalId = lpcard.getText.getStrStart  //现场指纹_连指开始_现场物证编号
      latentfingerMsg.latentFingerConnectFingerEndPhysicalId = lpcard.getText.getStrEnd  //现场指纹_连指结束_现场物证编号
      latentfingerMsg.latentFingerComparisonStatusCode = lpcard.getText.getNBiDuiState.toString  //现场指纹_指纹比对状态代码
      latentfingerMsg.latentFingerFeatureGroupNum = 0  //现场指纹_特征组合数量

      val latentFingerFeatureGroup = new Xczwtzz  //现场指纹特征组相关
      if(lpcard.getBlob.getStMnt.nonEmpty){//判断是否有特征

        latentfingerMsg.latentFingerFeatureGroupNum = latentfingerMsg.latentFingerFeatureGroupNum + 1
        val gafisMnt =  new GAFISIMAGESTRUCT
        gafisMnt.fromStreamReader(lpcard.getBlob.getStMntBytes.newInput())
        fingerLData = FPTMntConverter.convertGafisMnt2FingerLData(gafisMnt)

        latentFingerFeatureGroup.latentFingerFeatureGroupIdentify = "123451234512345"  //现场指纹_特征组合标识符
        latentFingerFeatureGroup.latentFingerFeatureGroupDescript = ""  //现场指纹_特征组合描述
        latentFingerFeatureGroup.latentFingerAnalysisPostionBrief = "1234567890"  //现场指纹_分析指位_简要情况
        latentFingerFeatureGroup.latentFingerPatternCode = "1234567"  //fingerLData.pattern  //现场指纹_指纹纹型代码
        latentFingerFeatureGroup.latentFingerDirectionDescript = fingerLData.fingerDirection  //现场指纹_指纹方向描述
        latentFingerFeatureGroup.latentFingerCenterPoint = fingerLData.centerPoint  //现场指纹_指纹中心点
        latentFingerFeatureGroup.latentFingerSlaveCenter = if(FPT5Utils.isNull(fingerLData.subCenterPoint) || FPT5Utils.replaceLength(fingerLData.subCenterPoint,14))"" else fingerLData.subCenterPoint  //现场指纹_指纹副中心
        latentFingerFeatureGroup.latentFingerLeftTriangle = if(FPT5Utils.isNull(fingerLData.leftTriangle) || FPT5Utils.replaceLength(fingerLData.leftTriangle,14))"" else fingerLData.leftTriangle   //现场指纹_指纹左三角
        latentFingerFeatureGroup.latentFingerRightTriangle = if(FPT5Utils.isNull(fingerLData.rightTriangle) || FPT5Utils.replaceLength(fingerLData.rightTriangle,14))"" else fingerLData.rightTriangle  //现场指纹_指纹右三角
        latentFingerFeatureGroup.latentFingerFeatureNum = fingerLData.featureCount.toInt  //现场指纹_指纹特征点_数量
        latentFingerFeatureGroup.latentFingerFeatureInfo = fingerLData.feature  //现场指纹_指纹特征点信息
        latentFingerFeatureGroup.latentFingerCustomInfo = lpcard.getText.getStrComment.getBytes() //现场指纹_自定义信息
        latentFingerFeatureGroup.latentFingerImageHorizontalDirectionLength = 512  //现场指纹_图像水平方向长度
        latentFingerFeatureGroup.latentFingerImageVerticalDirectionLength = 512  //现场指纹_图像垂直方向长度
        latentFingerFeatureGroup.latentFingerImageRatio = 500  //现场指纹_图像分辨率
        latentFingerFeatureGroup.latentFingerImageCompressMethodDescript = fpt4code.GAIMG_CPRMETHOD_NOCPR_CODE  //现场指纹_图像压缩方法描述
        latentFingerFeatureGroup.latentFingerImageData = gafisImage.bnData  //现场指纹_图像数据
      }
      latentfingerMsg.latentFingerFeatureGroup.add(latentFingerFeatureGroup)

      latentPackage.latentFingers.latentfingerMsg.add(latentfingerMsg)
    })

    palmCards.foreach( palm =>{

      val latentpalmMsg = new LatentpalmMsg
      var fingerLData = new FingerLData
      val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(palm.getBlob.getStImageBytes.toByteArray)

      latentpalmMsg.latentPalmId = palm.getStrCardID  //现场掌纹_现场指掌纹编号
      latentpalmMsg.latentPalmPhysicalId = "R12345678901234567890123456789"  //现场掌纹_现场物证编号
      latentpalmMsg.latentPalmLeftPostion = ""  //现场掌纹_现场指掌纹遗留部位
      latentpalmMsg.latentPalmFeatureExtractMethodCode = "M"  //现场掌纹_指掌纹特征提取方式代码
      latentpalmMsg.latentPalmCorpseJudgeIdentify = if(palm.getText.getBDeadBody) "1" else "0"  //现场掌纹_尸体指掌纹_判断标识
      latentpalmMsg.latentPalmMastoidProcessLineColorCode = if(FPT5Utils.isNull(palm.getText.getStrRidgeColor)) "" else palm.getText.getStrRidgeColor //现场掌纹_乳突线颜色代码
      latentpalmMsg.latentPalmComparisonStatusCode = palm.getText.getNBiDuiState.toString  //现场掌纹_掌纹比对状态代码
      latentpalmMsg.latentPalmFeatureGroupNum = 0  //现场掌纹_特征组合数量

      val latentPalmFeatureGroup = new Xczhwtzz  //现场掌纹特征组相关
      if(palm.getBlob.getStMnt.nonEmpty){//判断是否有特征

        latentpalmMsg.latentPalmFeatureGroupNum = latentpalmMsg.latentPalmFeatureGroupNum + 1
        val gafisMnt =  new GAFISIMAGESTRUCT
        gafisMnt.fromStreamReader(palm.getBlob.getStMntBytes.newInput())
        fingerLData = FPTMntConverter.convertGafisMnt2FingerLData(gafisMnt)

        latentPalmFeatureGroup.latentPalmFeatureGroupIdentify = "123451234512345"  //现场掌纹_特征组合标识符
        latentPalmFeatureGroup.latentPalmFeatureGroupDescript = ""  //现场掌纹_特征组合描述
        latentPalmFeatureGroup.latentPalmAnalysisPostionBrief = "1234"  //现场掌纹_分析掌位_简要情况
        latentPalmFeatureGroup.latentPalmRetracingPointNum = 0  //fingerLData.pattern  //现场掌纹_掌纹折返点数量
        latentPalmFeatureGroup.latentPalmRetracingPointInfo = ""  //现场掌纹_掌纹折返点信息
        latentPalmFeatureGroup.latentPalmTrianglePointNum = 0  //现场掌纹_掌纹三角点_数量
        latentPalmFeatureGroup.latentPalmTrianglePointInfo = ""  //现场掌纹_掌纹三角点信息
        latentPalmFeatureGroup.latentPalmFeaturePointNum = fingerLData.featureCount.toInt   //现场掌纹_掌纹特征点_数量
        latentPalmFeatureGroup.latentPalmFeaturePointInfo = fingerLData.feature  //现场掌纹_掌纹特征点信息
        latentPalmFeatureGroup.latentPalmCustomInfo = palm.getText.getStrComment.getBytes() //现场掌纹_自定义信息
        latentPalmFeatureGroup.latentPalmImageHorizontalDirectionLength = fingerLData.imgHorizontalLength.toInt  //现场掌纹_图像水平方向长度
        latentPalmFeatureGroup.latentPalmImageVerticalDirectionLength = fingerLData.imgVerticalLength.toInt  //现场掌纹_图像垂直方向长度
        latentPalmFeatureGroup.latentPalmImageRatio = fingerLData.dpi.toInt  //现场掌纹_图像分辨率
        latentPalmFeatureGroup.latentPalmImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)  //现场掌纹_图像压缩方法描述
        latentPalmFeatureGroup.latentPalmImageData = gafisImage.bnData  //现场掌纹_图像数据
      }
      latentpalmMsg.latentPalmFeatureGroup.add(latentPalmFeatureGroup)

      latentPackage.latentPalms.latentpalmMsg.add(latentpalmMsg)
    })

    latentPackage
  }
}
