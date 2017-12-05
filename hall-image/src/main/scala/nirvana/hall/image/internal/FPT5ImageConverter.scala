package nirvana.hall.image.internal

import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gfpt5lib.{FourprintMsg, _}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT

/**
  * Created by songpeng on 2017/1/26.
  */
object FPT5ImageConverter {

  /**
    * gafis指纹图像转为fpt5 FingerMsg
    * @param gafisImage GAFISIMAGESTRUCT
    * @param fingerMsg FingerMsg
    * @return
    */
  def convertGAFISIMAGESTRUCT2FingerMsg(gafisImage: GAFISIMAGESTRUCT, fingerMsg: FingerMsg = new FingerMsg): FingerMsg={
    fingerMsg.fingerPositionCode = gafisImage.stHead.nFingerIndex.toString //指位
    fingerMsg.fingerFeatureExtractionMethodCode = fpt4code.EXTRACT_METHOD_A //提取方式代码
    fingerMsg.fingerImageHorizontalDirectionLength = gafisImage.stHead.nWidth //指纹图像水平方向长度
    fingerMsg.fingerImageVerticalDirectionLength = gafisImage.stHead.nHeight //指纹图像垂直方向长度
    fingerMsg.fingerImageRatio = gafisImage.stHead.nResolution //指纹图像分辨率
    fingerMsg.fingerImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod) //指纹图像压缩方法描述
    fingerMsg.fingerImageData = gafisImage.bnData
    fingerMsg.fingerImageQuality = gafisImage.stHead.nQualDesc.toInt

    fingerMsg
  }
  /**
    * gafis掌纹图像转为fpt5 PalmMsg
    * @param gafisImage GAFISIMAGESTRUCT
    * @param palmMsg PalmMsg
    * @return
    */
  def convertGAFISIMAGESTRUCT2PalmMsg(gafisImage: GAFISIMAGESTRUCT, palmMsg: PalmMsg = new PalmMsg): PalmMsg={
    palmMsg.palmPostionCode = gafisImage.stHead.nFingerIndex.toString
    palmMsg.palmFeatureExtractionMethodCode = fpt4code.EXTRACT_METHOD_A //提取方式代码
    palmMsg.palmImageHorizontalDirectionLength = gafisImage.stHead.nWidth //指纹图像水平方向长度
    palmMsg.palmImageVerticalDirectionLength = gafisImage.stHead.nHeight //指纹图像垂直方向长度
    palmMsg.palmImageRatio = gafisImage.stHead.nResolution //指纹图像分辨率
    palmMsg.palmImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod) //指纹图像压缩方法描述
    palmMsg.palmImageData = gafisImage.bnData
    palmMsg.palmImageQuality = gafisImage.stHead.nQualDesc.toInt

    palmMsg
  }

  def convertGAFISIMAGESTRUCT2LatentFingerImageMsg(gafisImage: GAFISIMAGESTRUCT, latentImageMsg: LatentFingerImageMsg = new LatentFingerImageMsg): LatentFingerImageMsg={
    latentImageMsg.latentFingerImageHorizontalDirectionLength = gafisImage.stHead.nWidth //现场指纹_图像水平方向长度
    latentImageMsg.latentFingerImageVerticalDirectionLength = gafisImage.stHead.nHeight //现场指纹_图像垂直方向长度
    latentImageMsg.latentFingerImageRatio = gafisImage.stHead.nResolution //现场指纹_图像分辨率
    latentImageMsg.latentFingerImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)//现场指纹_图像压缩方法描述
    latentImageMsg.latentFingerImageData = gafisImage.bnData //现场指纹_图像数据

    latentImageMsg
  }
  def convertGAFISIMAGESTRUCT2LatentPalmImageMsg(gafisImage: GAFISIMAGESTRUCT, palmMsg: LatentPalmImageMsg = new LatentPalmImageMsg): LatentPalmImageMsg={
    palmMsg.latentPalmImageHorizontalDirectionLength = gafisImage.stHead.nWidth //指纹图像水平方向长度
    palmMsg.latentPalmImageVerticalDirectionLength = gafisImage.stHead.nHeight //指纹图像垂直方向长度
    palmMsg.latentPalmImageRatio = gafisImage.stHead.nResolution //指纹图像分辨率
    palmMsg.latentPalmImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod) //指纹图像压缩方法描述
    palmMsg.latentPalmImageData = gafisImage.bnData

    palmMsg
  }

  /**
    * 捺印FPT指纹图像数据转成Gafis图像结构
    * @param fingerMsg
    * @return
    */
  def convertTPFingerImageData2GafisImage(fingerMsg: FingerMsg): GAFISIMAGESTRUCT={
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.nCompressMethod = fpt4code.fptCprMethodToGafisCode(fingerMsg.fingerImageCompressMethodDescript).toByte
    if (gafisImg.stHead.nCompressMethod == 0) //no compress
      gafisImg.stHead.bIsCompressed = 0
    else
      gafisImg.stHead.bIsCompressed = 1

    gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
    gafisImg.stHead.nWidth = fingerMsg.fingerImageHorizontalDirectionLength.toShort
    gafisImg.stHead.nHeight = fingerMsg.fingerImageVerticalDirectionLength.toShort
    gafisImg.stHead.nBits = 8
    gafisImg.stHead.nResolution = fingerMsg.fingerImageRatio.toShort
    gafisImg.bnData = fingerMsg.fingerImageData
    gafisImg.stHead.nImgSize = gafisImg.bnData.length
    gafisImg.stHead.nQualDesc = fingerMsg.fingerImageQuality.toByte

    val positionInt = fingerMsg.fingerPositionCode.toInt
    if (positionInt > 10) {
      //捺印平面指位[11,20]
      gafisImg.stHead.bIsPlain = 1
      gafisImg.stHead.nFingerIndex = (positionInt - 10).toByte
    }else{
      gafisImg.stHead.nFingerIndex = positionInt.toByte
    }
    gafisImg
  }


  /**
    * 捺印FPT掌纹图像数据转成Gafis图像结构
    * @param palmMsg
    * @return
    */
  def convertTPPalmImageData2GafisImage(palmMsg: PalmMsg): GAFISIMAGESTRUCT={
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.nCompressMethod = fpt4code.fptCprMethodToGafisCode(palmMsg.palmImageCompressMethodDescript).toByte
    if (gafisImg.stHead.nCompressMethod == 0) //no compress
      gafisImg.stHead.bIsCompressed = 0
    else
      gafisImg.stHead.bIsCompressed = 1

    gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_PALM.toByte
    gafisImg.stHead.nWidth = palmMsg.palmImageHorizontalDirectionLength.toShort
    gafisImg.stHead.nHeight = palmMsg.palmImageVerticalDirectionLength.toShort
    gafisImg.stHead.nBits = 8
    gafisImg.stHead.nResolution = palmMsg.palmImageRatio.toShort
    gafisImg.bnData = palmMsg.palmImageData
    gafisImg.stHead.nQualDesc = palmMsg.palmImageQuality.toByte
    gafisImg.stHead.nImgSize = gafisImg.bnData.length
    gafisImg
  }


  /**
    * 捺印FPT四联指图像数据转成Gafis图像结构
    * @param fourprintMsg
    * @return
    */
  def convertTPFourPrintImageData2GafisImage(fourprintMsg: FourprintMsg): GAFISIMAGESTRUCT={
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.nCompressMethod = fpt4code.fptCprMethodToGafisCode(fourprintMsg.fourPrintImageCompressMethodDescript).toByte
    if (gafisImg.stHead.nCompressMethod == 0) //no compress
      gafisImg.stHead.bIsCompressed = 0
    else
      gafisImg.stHead.bIsCompressed = 1

    //gafisImg.stHead.nImageType = //glocdef..toByte
    gafisImg.stHead.nWidth = fourprintMsg.fourPrintImageHorizontalDirectionLength.toShort
    gafisImg.stHead.nHeight = fourprintMsg.fourPrintImageVerticalDirectionLength.toShort
    gafisImg.stHead.nBits = 8
    gafisImg.stHead.nResolution = fourprintMsg.fourPrintImageRatio.toShort
    gafisImg.bnData = fourprintMsg.fourPrintImageData
    gafisImg.stHead.nQualDesc = fourprintMsg.fourPrintImageQuality.toByte
    gafisImg.stHead.nImgSize = gafisImg.bnData.length
    gafisImg
  }



  /**
    * 捺印FPT指节纹图像数据转成Gafis图像结构
    * @param knuckleprintMsg
    * @return
    */
  def convertTPKnuckleprintMsgImageData2GafisImage(knuckleprintMsg: KnuckleprintMsg): GAFISIMAGESTRUCT={
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.nCompressMethod = fpt4code.fptCprMethodToGafisCode(knuckleprintMsg.knucklePrintImageCompressMethodDescript).toByte
    if (gafisImg.stHead.nCompressMethod == 0) //no compress
      gafisImg.stHead.bIsCompressed = 0
    else
      gafisImg.stHead.bIsCompressed = 1

    //gafisImg.stHead.nImageType = //glocdef..toByte
    gafisImg.stHead.nWidth = knuckleprintMsg.knucklePrintImageHorizontalDirectionLength.toShort
    gafisImg.stHead.nHeight = knuckleprintMsg.knucklePrintImageVerticalDirectionLength.toShort
    gafisImg.stHead.nBits = 8
    gafisImg.stHead.nResolution = knuckleprintMsg.knucklePrintImageRatio.toShort
    gafisImg.bnData = knuckleprintMsg.knucklePrintImageData
    gafisImg.stHead.nQualDesc = knuckleprintMsg.knucklePrintImageQuality.toByte
    gafisImg.stHead.nImgSize = gafisImg.bnData.length
    gafisImg
  }


  /**
    * 捺印FPT全掌图像数据转成Gafis图像结构
    * @param fullpalmMsg
    * @return
    */
  def convertTPFullPalmMsgImageData2GafisImage(fullpalmMsg: FullpalmMsg): GAFISIMAGESTRUCT={
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.nCompressMethod = fpt4code.fptCprMethodToGafisCode(fullpalmMsg.fullPalmImageCompressMethodDescript).toByte
    if (gafisImg.stHead.nCompressMethod == 0) //no compress
      gafisImg.stHead.bIsCompressed = 0
    else
      gafisImg.stHead.bIsCompressed = 1

    //gafisImg.stHead.nImageType = //glocdef..toByte
    gafisImg.stHead.nWidth = fullpalmMsg.fullPalmImageHorizontalDirectionLength.toShort
    gafisImg.stHead.nHeight = fullpalmMsg.fullPalmImageVerticalDirectionLength.toShort
    gafisImg.stHead.nBits = 8
    gafisImg.stHead.nResolution = fullpalmMsg.fullPalmImageRatio.toShort
    gafisImg.bnData = fullpalmMsg.fullPalmImageData
    gafisImg.stHead.nQualDesc = fullpalmMsg.fullPalmImageQuality.toByte
    gafisImg.stHead.nImgSize = gafisImg.bnData.length
    gafisImg
  }



  /**
    * 现场FPT指纹图像数据转成Gafis图像结构
    * @param latentFingerImageMsg
    * @return
    */
  def convertLPFingerImageData2GafisImage(latentFingerImageMsg: LatentFingerImageMsg): GAFISIMAGESTRUCT={
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.nCompressMethod = fpt4code.fptCprMethodToGafisCode(latentFingerImageMsg.latentFingerImageCompressMethodDescript).toByte
    if (gafisImg.stHead.nCompressMethod == 0) //no compress
      gafisImg.stHead.bIsCompressed = 0
    else
      gafisImg.stHead.bIsCompressed = 1

    gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
    gafisImg.stHead.nWidth = latentFingerImageMsg.latentFingerImageHorizontalDirectionLength.toShort
    gafisImg.stHead.nHeight = latentFingerImageMsg.latentFingerImageVerticalDirectionLength.toShort
    gafisImg.stHead.nBits = 8
    gafisImg.stHead.nResolution = latentFingerImageMsg.latentFingerImageRatio.toShort
    gafisImg.bnData = latentFingerImageMsg.latentFingerImageData
    gafisImg.stHead.nImgSize = gafisImg.bnData.length
    gafisImg
  }


  /**
    * 现场FPT掌纹图像数据转成Gafis图像结构
    * @param latentPalmImageMsg
    * @return
    */
  def convertLPPalmImageData2GafisImage(latentPalmImageMsg: LatentPalmImageMsg): GAFISIMAGESTRUCT={
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.nCompressMethod = fpt4code.fptCprMethodToGafisCode(latentPalmImageMsg.latentPalmImageCompressMethodDescript).toByte
    if (gafisImg.stHead.nCompressMethod == 0) //no compress
      gafisImg.stHead.bIsCompressed = 0
    else
      gafisImg.stHead.bIsCompressed = 1

    gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_PALM.toByte
    gafisImg.stHead.nWidth = latentPalmImageMsg.latentPalmImageHorizontalDirectionLength.toShort
    gafisImg.stHead.nHeight = latentPalmImageMsg.latentPalmImageVerticalDirectionLength.toShort
    gafisImg.stHead.nBits = 8
    gafisImg.stHead.nResolution = latentPalmImageMsg.latentPalmImageRatio.toShort
    gafisImg.bnData = latentPalmImageMsg.latentPalmImageData
    gafisImg.stHead.nImgSize = gafisImg.bnData.length
    gafisImg
  }




  /**
    * gafis指节纹图像转为fpt5 KnuckleprintMsg
    * @param gafisImage GAFISIMAGESTRUCT
    * @param knuckleprintMsg KnuckleprintMsg
    * @return
    */
  def convertGAFISIMAGESTRUCT2KnuckleprintMsg(gafisImage: GAFISIMAGESTRUCT, knuckleprintMsg: KnuckleprintMsg = new KnuckleprintMsg): KnuckleprintMsg={
    knuckleprintMsg.knucklePrintPostionCode = gafisImage.stHead.nFingerIndex.toString //指位
    knuckleprintMsg.knucklePrintLackFingerCauseCode = "99"
    knuckleprintMsg.knucklePrintImageHorizontalDirectionLength = gafisImage.stHead.nWidth //指纹图像水平方向长度
    knuckleprintMsg.knucklePrintImageVerticalDirectionLength = gafisImage.stHead.nHeight //指纹图像垂直方向长度
    knuckleprintMsg.knucklePrintImageRatio = gafisImage.stHead.nResolution //指纹图像分辨率
    knuckleprintMsg.knucklePrintImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod) //指纹图像压缩方法描述
    knuckleprintMsg.knucklePrintImageData = gafisImage.bnData
    knuckleprintMsg.knucklePrintImageQuality = gafisImage.stHead.nQualDesc.toInt

    knuckleprintMsg
  }


  /**
    * gafis四联指图像转为fpt5 FourPrintMsg
    * @param gafisImage GAFISIMAGESTRUCT
    * @param fourPrintMsg fourPrintMsg
    * @return
    */
  def convertGAFISIMAGESTRUCT2fourPrintMsg(gafisImage: GAFISIMAGESTRUCT, fourPrintMsg: FourprintMsg = new FourprintMsg): FourprintMsg={
    fourPrintMsg.fourPrintPostionCode = gafisImage.stHead.nFingerIndex.toString //指位
    fourPrintMsg.fourPrintLackFingerCauseCode = "99"
    fourPrintMsg.fourPrintImageHorizontalDirectionLength = gafisImage.stHead.nWidth //指纹图像水平方向长度
    fourPrintMsg.fourPrintImageVerticalDirectionLength = gafisImage.stHead.nHeight //指纹图像垂直方向长度
    fourPrintMsg.fourPrintImageRatio = gafisImage.stHead.nResolution //指纹图像分辨率
    fourPrintMsg.fourPrintImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod) //指纹图像压缩方法描述
    fourPrintMsg.fourPrintImageData = gafisImage.bnData
    fourPrintMsg.fourPrintImageQuality = gafisImage.stHead.nQualDesc.toInt

    fourPrintMsg
  }

  /**
    * gafis全掌图像转为fpt5 fullPalmMsg
    * @param gafisImage GAFISIMAGESTRUCT
    * @param fullPalmMsg fullPalmMsg
    * @return
    */
  def convertGAFISIMAGESTRUCT2fullPalmMsg(gafisImage: GAFISIMAGESTRUCT, fullPalmMsg: FullpalmMsg = new FullpalmMsg): FullpalmMsg={
    fullPalmMsg.fullPalmPostionCode = gafisImage.stHead.nFingerIndex.toString //指位
    fullPalmMsg.fullPalmLackPalmCauseCode = "99"
    fullPalmMsg.fullPalmImageHorizontalDirectionLength = gafisImage.stHead.nWidth //指纹图像水平方向长度
    fullPalmMsg.fullPalmImageVerticalDirectionLength = gafisImage.stHead.nHeight //指纹图像垂直方向长度
    fullPalmMsg.fullPalmImageRatio = gafisImage.stHead.nResolution //指纹图像分辨率
    fullPalmMsg.fullPalmImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod) //指纹图像压缩方法描述
    fullPalmMsg.fullPalmImageData = gafisImage.bnData
    fullPalmMsg.fullPalmImageQuality = gafisImage.stHead.nQualDesc.toInt

    fullPalmMsg
  }

}
