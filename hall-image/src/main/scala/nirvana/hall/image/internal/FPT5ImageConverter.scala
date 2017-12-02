package nirvana.hall.image.internal

import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gfpt5lib.{FingerMsg, LatentFingerImageMsg, LatentPalmImageMsg, PalmMsg}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT

/**
  * Created by songpeng on 2017/1/26.
  */
object FPT5ImageConverter {

  type FPTFingerData ={
    var latentFingerImageCompressMethodDescript:String
    var latentFingerImageData:Array[Byte]
    var latentFingerImageHorizontalDirectionLength:String
    var latentFingerImageVerticalDirectionLength :String
    var latentFingerImageRatio:String
    var fingerPositionCode:String
  }

  /**
    * gafis指纹图像转为fpt5 FingerMsg
    * @param gafisImage GAFISIMAGESTRUCT
    * @param fingerMsg FingerMsg
    * @return
    */
  def convertGAFISIMAGESTRUCT2FingerMsg(gafisImage: GAFISIMAGESTRUCT, fingerMsg: FingerMsg = new FingerMsg): FingerMsg={
    fingerMsg.fingerPositionCode = gafisImage.stHead.nFingerIndex.toString //指位
    fingerMsg.fingerImageHorizontalDirectionLength = gafisImage.stHead.nWidth //指纹图像水平方向长度
    fingerMsg.fingerImageVerticalDirectionLength = gafisImage.stHead.nHeight //指纹图像垂直方向长度
    fingerMsg.fingerImageRatio = gafisImage.stHead.nResolution //指纹图像分辨率
    fingerMsg.fingerImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod) //指纹图像压缩方法描述
    fingerMsg.fingerImageData = gafisImage.bnData

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
    palmMsg.palmImageHorizontalDirectionLength = gafisImage.stHead.nWidth //指纹图像水平方向长度
    palmMsg.palmImageVerticalDirectionLength = gafisImage.stHead.nHeight //指纹图像垂直方向长度
    palmMsg.palmImageRatio = gafisImage.stHead.nResolution //指纹图像分辨率
    palmMsg.palmImageCompressMethodDescript = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod) //指纹图像压缩方法描述
    palmMsg.palmImageData = gafisImage.bnData

    palmMsg
  }

  def convertGAFISIMAGESTRUCT2LatentFingerImageMsg(gafisImage: GAFISIMAGESTRUCT, latentImageMsg: LatentFingerImageMsg = new LatentFingerImageMsg): LatentFingerImageMsg={
    latentImageMsg.latentFingerImageHorizontalDirectionLength = gafisImage.stHead.nWidth //现场指纹_图像水平方向长度
    latentImageMsg.latentFingerImageVerticalDirectionLength = gafisImage.stHead.nHeight //现场指纹_图像垂直方向长度
    latentImageMsg.latentFingerImageRatio = gafisImage.stHead.nBits //现场指纹_图像分辨率
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

  def convert2GafisImage(latentFingerImageMsg: FPTFingerData, isLatent: Boolean = false,isPalm:Boolean = false): GAFISIMAGESTRUCT={
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.nCompressMethod = fpt4code.fptCprMethodToGafisCode(latentFingerImageMsg.latentFingerImageCompressMethodDescript).toByte
    if (gafisImg.stHead.nCompressMethod == 0) //no compress
      gafisImg.stHead.bIsCompressed = 0
    else
      gafisImg.stHead.bIsCompressed = 1

    gafisImg.stHead.nImageType = if(!isPalm) glocdef.GAIMG_IMAGETYPE_FINGER.toByte else glocdef.GAIMG_IMAGETYPE_PALM.toByte
    gafisImg.stHead.nWidth = latentFingerImageMsg.latentFingerImageHorizontalDirectionLength.toShort
    gafisImg.stHead.nHeight = latentFingerImageMsg.latentFingerImageVerticalDirectionLength.toShort
    gafisImg.stHead.nBits = 8
    gafisImg.stHead.nResolution = latentFingerImageMsg.latentFingerImageRatio.toShort
    gafisImg.bnData = latentFingerImageMsg.latentFingerImageData
    gafisImg.stHead.nImgSize = gafisImg.bnData.length

    if(!isLatent){//捺印指位
    val positionInt = latentFingerImageMsg.fingerPositionCode.toInt
      if (positionInt > 10) {
        //捺印平面指位[11,20]
        gafisImg.stHead.bIsPlain = 1
        gafisImg.stHead.nFingerIndex = (positionInt - 10).toByte
      }else{
        gafisImg.stHead.nFingerIndex = positionInt.toByte
      }
    }
    gafisImg
  }


  def convert2GafisPalmImage(palmData: Array[Byte],fgp:Int): GAFISIMAGESTRUCT={
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.nCompressMethod = 102
    gafisImg.stHead.bIsCompressed = 1

    gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_PALM.toByte
    gafisImg.stHead.nWidth = 640
    gafisImg.stHead.nHeight = 640
    gafisImg.stHead.nBits = 8
    gafisImg.stHead.nResolution = 500
    gafisImg.bnData = palmData
    gafisImg.stHead.nFingerIndex = fgp.toByte
    gafisImg.stHead.nImgSize = gafisImg.bnData.length
    gafisImg
  }

}
