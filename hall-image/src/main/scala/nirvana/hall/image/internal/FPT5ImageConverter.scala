package nirvana.hall.image.internal

import nirvana.hall.c.services.gfpt4lib.fpt4code
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
