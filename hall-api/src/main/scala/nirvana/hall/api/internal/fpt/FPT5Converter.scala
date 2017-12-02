package nirvana.hall.api.internal.fpt


import java.util

import com.google.protobuf.ByteString
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.FingerLData
import nirvana.hall.c.services.gfpt4lib.{FPT4File, fpt4code}
import nirvana.hall.c.services.gfpt5lib.{FaceImage, LatentPalmImageMsg, _}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.{FPT5MntConverter, FPTMntConverter}
import nirvana.hall.image.internal.{FPT5ImageConverter, FPTImageConverter}
import nirvana.hall.protocol.api.FPTProto
import nirvana.hall.protocol.api.FPTProto.TPCard.TPCardBlob
import nirvana.hall.protocol.api.FPTProto.{LPCard, _}
import nirvana.hall.v70.internal.Gafis70Constants

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
    //采集信息原因代码集合
    fingerprintPackage.descriptiveMsg = new DescriptMsg
    fingerprintPackage.descriptiveMsg.originalSystemCasePersonId = tpCard.getStrCardID  //原始系统_案事件相关人员编号
    fingerprintPackage.descriptiveMsg.jingZongPersonId = tpCard.getStrJingZongPersonId  //由警综平台标准化采集系统发号
    fingerprintPackage.descriptiveMsg.casePersonid = tpCard.getStrCasePersonID  //案事件相关人员编号 由刑专系统发号，新数据必须提供
    fingerprintPackage.descriptiveMsg.fingerPalmCardId = tpCard.getStrCardID   //指掌纹卡编号 系统自用
    val captureInfoReasonCode = new mutable.ArrayBuffer[String]()
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
    //捺印信息节点
    fingerprintPackage.collectInfoMsg = new CollectInfoMsg
    fingerprintPackage.collectInfoMsg.fingerprintComparisonSysTypeDescript = fpt4code.GAIMG_CPRMETHOD_EGFS_CODE  //指纹比对系统描述
    fingerprintPackage.collectInfoMsg.chopUnitCode = tpCard.getText.getStrPrintUnitCode //捺印单位公安机关机构代码
    fingerprintPackage.collectInfoMsg.chopUnitName =  tpCard.getText.getStrPrintUnitName //捺印单位公安机关机构名称
    fingerprintPackage.collectInfoMsg.chopPersonName = tpCard.getText.getStrPrinter //捺印人员_姓名
    fingerprintPackage.collectInfoMsg.chopPersonIdCard = tpCard.getText.getStrPrinterIdCardNo //捺印人员_公民身份号码
    fingerprintPackage.collectInfoMsg.chopPersonTel = tpCard.getText.getStrPrinterPhone  //捺印人员_联系电话
    fingerprintPackage.collectInfoMsg.chopDateTime =  tpCard.getText.getStrPrintDate //捺印时间
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
            fingerMsgs += getFingerMsg(blob)
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
    val fingerMsg = FPT5ImageConverter.convertGAFISIMAGESTRUCT2FingerMsg(gafisImage)
    //特征转换
    val gafisMnt = new GAFISIMAGESTRUCT().fromByteArray(blob.getStMntBytes.toByteArray())
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
    caseMsg.caseId = caseInfo.getStrCaseID //案事件编号 --警综案事件编号，新数据必须提供
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
      val latentfinger = convertCaseInfoAndLPCards2LatentFingers(caseInfo,fingerCards)
      latentFingers += latentfinger
    }
    if(palmCards != null){
      val latentpalm = convertCaseInfoAndPalmCards2LatentPalms(caseInfo,palmCards)
      latentPalms += latentpalm
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
    val latentFingerImageMsg = new ArrayBuffer[LatentFingerImageMsg]
    var latentFingerFeatureMsg = new ArrayBuffer[LatentFingerFeatureMsg]
    lpCards.foreach(lpcard => {
      val latentImageMsg = new LatentFingerImageMsg
      latentImageMsg.originalSystemLatentFingerPalmId = lpcard.getStrCardID //原始系统_现场指掌纹编号
      latentImageMsg.latentPhysicalId = lpcard.getStrPhysicalId //现场物证编号
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
      latentFingerImageMsg += latentImageMsg
    })

    latentFingers.latentFingerImageMsg = latentFingerImageMsg.toArray
    latentFingers.latentFingerFeatureMsg = latentFingerFeatureMsg.toArray
    latentFingers
  }

  def convertCaseInfoAndPalmCards2LatentPalms(caseInfo: Case,palmCards: Seq[LPCard]): LatentPalms ={
    val latentPalms = new LatentPalms
    val latentPalmImageMsgList = new ArrayBuffer[LatentPalmImageMsg]
    var latentPalmFeatureMsgList = new ArrayBuffer[LatentPalmFeatureMsg]
    palmCards.foreach( palm =>{
      val latentPalmImageMsg = new LatentPalmImageMsg
      val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(palm.getBlob.getStImageBytes.toByteArray)
      latentPalmImageMsg.latentPalmId = palm.getStrCardID  //现场掌纹_现场指掌纹编号
      latentPalmImageMsg.latentPalmPhysicalId = palm.getStrPhysicalId  //现场掌纹_现场物证编号
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
        latentPalmFeatureMsg.latentPalmPhysicalId = palm.getStrPhysicalId //现场掌纹_现场物证编号
        latentPalmFeatureMsg.latentPalmFeatureDscriptInfo = "" //现场掌纹_特征组合描述信息
        latentPalmFeatureMsg.latentPalmComparisonStatusCode = palm.getText.getNBiDuiState.toString //现场掌纹_指掌纹比对状态代码
        //TODO 掌纹折返点, 掌位分析, 现场掌纹_特征组合描述, 特征组标识符
        latentPalmFeatureMsgList += latentPalmFeatureMsg
      }
      latentPalmImageMsgList += latentPalmImageMsg
    })
    latentPalms.latentPalmImageMsg = latentPalmImageMsgList.toArray
    latentPalms.latentPalmFeatureMsg = latentPalmFeatureMsgList.toArray
    latentPalms
  }

  def convertFeature2MinutiaSet(feature:String,featureCount:Int):Array[LatentMinutia]={
    var latentminutias = new ArrayBuffer[LatentMinutia]
    var mnts:String = ""
    var index:Int = 0
    for(i<-0 until featureCount){
      val Latentminutia = new LatentMinutia
      index = i
      mnts = feature.substring(index,index+9)
      Latentminutia.fingerFeaturePointXCoordinate = if(FPT5Utils.isNull(mnts.substring(0,3)))0 else mnts.substring(0,3).toInt
      Latentminutia.fingerFeaturePointYCoordinate = if(FPT5Utils.isNull(mnts.substring(3,6)))0 else mnts.substring(3,6).toInt
      Latentminutia.fingerFeaturePointDirection = if(FPT5Utils.isNull(mnts.substring(6,9)))0 else mnts.substring(6,9).toInt
      latentminutias += Latentminutia
    }
    latentminutias.toArray
  }

  def convertFeature2PalmMinutiaSet(feature:String,featureCount:Int):Array[LatentPalmMinutia]={
    var palmlatentminutias  = new ArrayBuffer[LatentPalmMinutia]
    var palmmnts:String = null
    var index:Int = 0
    for(i<-0 until featureCount){
      val palmLatentminutia = new LatentPalmMinutia
      index = i
      palmmnts = feature.substring(index,index+9)
      palmLatentminutia.fingerFeaturePointXCoordinate = palmmnts.substring(index,index+3).toInt
      palmLatentminutia.fingerFeaturePointYCoordinate = palmmnts.substring(index+3,index+6).toInt
      palmLatentminutia.fingerFeaturePointDirection = palmmnts.substring(index+6,index+9).toInt
      palmlatentminutias += palmLatentminutia
    }
    palmlatentminutias.toArray
  }

  def convertLatentPackage2Case(latentPackage: LatentPackage): Case = {
    val caseInfo = Case.newBuilder()
    val textBuilder = caseInfo.getTextBuilder
    caseInfo.setStrCaseID(latentPackage.caseMsg.caseId) //案事件编号
    caseInfo.setStrSurveyId(latentPackage.caseMsg.latentSurveyId) //现场勘验编号
    caseInfo.setNCaseFingerCount(latentPackage.latentFingers.length) //现场指纹个数
    latentPackage.latentFingers.foreach(
      latentFingers =>
        caseInfo.addStrFingerID(latentFingers.latentFingerImageMsg(0).originalSystemLatentFingerPalmId)  //原始系统_现场指掌纹编号
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

    textBuilder.setStrSuspArea1Code("") //可疑地区行政区划
    textBuilder.setStrSuspArea2Code("")
    textBuilder.setStrSuspArea3Code("")
    textBuilder.setStrCaseOccurDate("") //案发日期
    textBuilder.setStrPremium("") //协查奖金
    textBuilder.setStrXieChaDate("") //协查日期
    textBuilder.setStrXieChaRequestUnitName("") //协查单位名称
    textBuilder.setStrXieChaRequestUnitCode("") //协查单位代码
    //隐式转换，字符串转数字
    implicit def string2Int(str: String): Int = {
      if (str != null && str.matches("[0-9]+"))
        Integer.parseInt(str)
      else 0
    }
    textBuilder.setNSuperviseLevel("") //协查级别
    textBuilder.setNCaseState("") //案件状态
    textBuilder.setNCaseState("") //协查状态
    textBuilder.setNCancelFlag("") //撤销标识

    caseInfo.build()
  }

  def convertLatentPackage2LPCard(latentPackage: LatentPackage): Seq[LPCard] = {
    val lpCardList = new ArrayBuffer[LPCard]()
    val finger = new FPT4File.FingerLData
    val mnt = new FPT4File.FingerLData
    if(latentPackage.latentFingers != null){
      latentPackage.latentFingers.foreach(
        latentFingers=>{
          val lpCard = LPCard.newBuilder()
          val blobBuilder = lpCard.getBlobBuilder
          if(latentFingers.latentFingerImageMsg != null){
            latentFingers.latentFingerImageMsg.foreach(
              latentFingerImageMsg => {
                lpCard.setStrCardID(latentFingerImageMsg.originalSystemLatentFingerPalmId)
                val textBuilder = lpCard.getTextBuilder
                textBuilder.setStrCaseId(latentPackage.caseMsg.caseId)
                textBuilder.setStrSeq(latentFingerImageMsg.originalSystemLatentFingerPalmId.substring(23))
                textBuilder.setStrRemainPlace(latentFingerImageMsg.latentFingerLeftPosition) //遗留部位
                textBuilder.setStrRidgeColor(latentFingerImageMsg.latentFingerMastoidProcessLineColorCode) //乳突线颜色
                textBuilder.setBDeadBody("1".equals(latentFingerImageMsg.latentFingerCorpseJudgeIdentify)) //未知名尸体标识
                textBuilder.setStrDeadPersonNo("") //未知名尸体编号
                textBuilder.setStrStart(latentFingerImageMsg.latentFingerConnectFingerBeginPhysicalId) //联指开始序号
                textBuilder.setStrEnd(latentFingerImageMsg.latentFingerConnectFingerEndPhysicalId) //联指结束序号

                if (latentFingerImageMsg.latentFingerImageData != null &&
                  latentFingerImageMsg.latentFingerImageData.length > 0) {
                  blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
                  if (latentFingerImageMsg.originalSystemLatentFingerPalmId.substring(23) != null &&
                    latentFingerImageMsg.originalSystemLatentFingerPalmId.substring(23).length > 0) {
                    0.until(latentFingerImageMsg.originalSystemLatentFingerPalmId.substring(23).length)
                      .filter("1" == latentFingerImageMsg.originalSystemLatentFingerPalmId.substring(23).charAt(_))
                      .foreach(i => blobBuilder.addFgp(FingerFgp.valueOf(i + 1)))
                  }
                  finger.imgCompressMethod = "0000"
                  finger.imgData = latentFingerImageMsg.latentFingerImageData
                  finger.imgHorizontalLength = "512"
                  finger.imgVerticalLength = "512"
                  finger.dpi = "500"
                  finger.fgp = latentFingerImageMsg.originalSystemLatentFingerPalmId.substring(23)

                  val gafisImage = FPTImageConverter.convert2GafisImage(finger, true)
                  blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray()))
                }
                //隐式转换，字符串转数字
                implicit def string2Int(str: String): Int = {
                  if (str != null && str.matches("[0-9]+"))
                    Integer.parseInt(str)
                  else 0
                }
                textBuilder.setNXieChaState("")
                textBuilder.setNBiDuiState(latentFingerImageMsg.latentFingerComparisonStatusCode)

              })
          }
          if(latentFingers.latentFingerFeatureMsg != null) {
            latentFingers.latentFingerFeatureMsg.foreach(
              latentFingerFeatureMsg => {
                lpCard.setStrCardID(latentFingerFeatureMsg.originalSystemLatentFingerPalmId)

                mnt.fgp = finger.fgp
                mnt.pattern = latentFingerFeatureMsg.fingerPatternAnalysisBrief
                mnt.fingerDirection = latentFingerFeatureMsg.fingerFeatureDirection.toString +
                  latentFingerFeatureMsg.fingerFeatureDirectionRange.toString
                mnt.centerPoint = latentFingerFeatureMsg.fingerCenterPointFeatureXCoordinate.toString +
                  latentFingerFeatureMsg.fingerCenterPointFeatureYCoordinate.toString +
                  latentFingerFeatureMsg.fingerCenterPointFeatureCoordinateRange.toString +
                  latentFingerFeatureMsg.fingerCenterPointFeatureDirection.toString +
                  latentFingerFeatureMsg.fingerCenterPointFeatureDirectionRange.toString +
                  latentFingerFeatureMsg.fingerCenterPointFeatureReliabilityLevel.toString
                mnt.subCenterPoint = latentFingerFeatureMsg.fingerSlaveCenterFeatureXCoordinate.toString +
                  latentFingerFeatureMsg.fingerSlaveCenterFeatureYCoordinate.toString +
                  latentFingerFeatureMsg.fingerSlaveCenterFeatureCoordinateRange.toString +
                  latentFingerFeatureMsg.fingerSlaveCenterFeatureDirection.toString +
                  latentFingerFeatureMsg.fingerSlaveCenterFeatureDirectionRange.toString +
                  latentFingerFeatureMsg.fingerSlaveCenterFeatureReliabilityLevel.toString
                mnt.leftTriangle = latentFingerFeatureMsg.fingerLeftTriangleFeatureXCoordinate.toString +
                  latentFingerFeatureMsg.fingerLeftTriangleFeatureYCoordinate.toString +
                  latentFingerFeatureMsg.fingerLeftTriangleFeatureCoordinateRange.toString +
                  latentFingerFeatureMsg.fingerLeftTriangleFeatureDirection.toString +
                  latentFingerFeatureMsg.fingerLeftTriangleFeatureDirectionRange.toString +
                  latentFingerFeatureMsg.fingerLeftTriangleFeatureReliabilityLevel.toString
                mnt.rightTriangle = latentFingerFeatureMsg.fingerRightTriangleFeatureXCoordinate.toString +
                  latentFingerFeatureMsg.fingerRightTriangleFeatureYCoordinate.toString +
                  latentFingerFeatureMsg.fingerRightTriangleFeatureCoordinateRange.toString +
                  latentFingerFeatureMsg.fingerRightTriangleFeatureDirection.toString +
                  latentFingerFeatureMsg.fingerRightTriangleFeatureDirectionRange.toString +
                  latentFingerFeatureMsg.fingerRightTriangleFeatureReliabilityLevel.toString
                mnt.featureCount = latentFingerFeatureMsg.LatentMinutiaSet.latentMinutia.length.toString

                var features = ""
                latentFingerFeatureMsg.LatentMinutiaSet.latentMinutia.foreach(
                  latentMinutia => {
                    features +=
                      "%03d%03d%03d".format(latentMinutia.fingerFeaturePointXCoordinate,
                        latentMinutia.fingerFeaturePointYCoordinate,
                        latentMinutia.fingerFeaturePointDirection)
                  }
                )
                mnt.feature = features
                mnt.imgHorizontalLength = "512"
                mnt.imgVerticalLength = "512"
                mnt.dpi = "500"
                val gafisMnt = FPTMntConverter.convertFingerLData2GafisMnt(mnt)
                blobBuilder.setStMntBytes(ByteString.copyFrom(gafisMnt.toByteArray()))
              })
          }
          lpCardList += lpCard.build()
        })
    }
    if(latentPackage.latentPalms != null){
      latentPackage.latentPalms.foreach(
        latentPalms => {
          val lpCard = LPCard.newBuilder()
          val textBuilder = lpCard.getTextBuilder
          val blobBuilder = lpCard.getBlobBuilder
          if(latentPalms.latentPalmImageMsg != null){
            latentPalms.latentPalmImageMsg.foreach(
              latentPalmImageMsg => {
                lpCard.setStrCardID(latentPalmImageMsg.latentPalmId)
                textBuilder.setStrCaseId(latentPackage.caseMsg.caseId)
                textBuilder.setStrSeq(latentPalmImageMsg.latentPalmId.substring(23))
                textBuilder.setStrRemainPlace(latentPalmImageMsg.latentPalmLeftPostion) //遗留部位
                textBuilder.setStrRidgeColor(latentPalmImageMsg.latentPalmMastoidProcessLineColorCode) //乳突线颜色
                textBuilder.setBDeadBody("1".equals(latentPalmImageMsg.latentPalmCorpseJudgeIdentify)) //未知名尸体标识
                textBuilder.setStrDeadPersonNo("") //未知名尸体编号
                textBuilder.setStrStart("") //联指开始序号
                textBuilder.setStrEnd("") //联指结束序号

                if (latentPalmImageMsg.latentPalmImageData != null &&
                  latentPalmImageMsg.latentPalmImageData.length > 0) {
                  blobBuilder.setType(ImageType.IMAGETYPE_PALM)
                  if (latentPalmImageMsg.latentPalmId.substring(23) != null &&
                    latentPalmImageMsg.latentPalmId.substring(23).length > 0) {
                    0.until(latentPalmImageMsg.latentPalmId.substring(23).length)
                      .filter("1" == latentPalmImageMsg.latentPalmId.substring(23).charAt(_))
                      .foreach(i => blobBuilder.setPalmFgp(PalmFgp.valueOf(i + 10)))
                  }
                  finger.imgCompressMethod = "0000"
                  finger.imgData = latentPalmImageMsg.latentPalmImageData
                  finger.imgHorizontalLength = "512"
                  finger.imgVerticalLength = "512"
                  finger.dpi = "500"
                  finger.fgp = latentPalmImageMsg.latentPalmId.substring(23)

                  val gafisImage = FPTImageConverter.convert2GafisImage(finger, true)
                  blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray()))

                  //隐式转换，字符串转数字
                  implicit def string2Int(str: String): Int = {
                    if (str != null && str.matches("[0-9]+"))
                      Integer.parseInt(str)
                    else 0
                  }
                  textBuilder.setNXieChaState("")
                  textBuilder.setNBiDuiState(latentPalmImageMsg.latentPalmComparisonStatusCode)
                }
              })
          }
          if(latentPalms.latentPalmFeatureMsg != null){
            latentPalms.latentPalmFeatureMsg.foreach(
              LatentPalmFeatureMsg => {
                mnt.fgp = finger.fgp
                mnt.pattern = ""
                mnt.fingerDirection = ""
                mnt.centerPoint = ""
                mnt.subCenterPoint = ""
                mnt.featureCount = LatentPalmFeatureMsg.latentPalmMinutiaSet.latentPalmMinutia.length.toString
                var features = ""
                LatentPalmFeatureMsg.latentPalmMinutiaSet.latentPalmMinutia.foreach(
                  latentPalmMinutia => {
                    features +=
                      "%03d%03d%03d".format(latentPalmMinutia.fingerFeaturePointXCoordinate,
                        latentPalmMinutia.fingerFeaturePointYCoordinate,
                        latentPalmMinutia.fingerFeaturePointDirection)
                  })
                mnt.feature = features
                mnt.imgHorizontalLength = "512"
                mnt.imgVerticalLength = "512"
                mnt.dpi = "500"

                val gafisMnt = FPTMntConverter.convertFingerLData2GafisMnt(mnt)
                blobBuilder.setStMntBytes(ByteString.copyFrom(gafisMnt.toByteArray()))

              })
          }
          lpCardList += lpCard.build()
        })
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

