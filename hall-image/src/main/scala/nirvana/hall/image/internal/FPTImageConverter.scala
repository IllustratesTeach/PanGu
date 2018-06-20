package nirvana.hall.image.internal

import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT

/**
  * Created by songpeng on 2017/1/26.
  */
object FPTImageConverter {

  type FPTFingerData ={
    var imgCompressMethod:String
    var imgData:Array[Byte]
    var imgHorizontalLength:String
    var imgVerticalLength :String
    var dpi:String
    var fgp:String
  }

  def convert2GafisImage(fingerData: FPTFingerData, isLatent: Boolean = false): GAFISIMAGESTRUCT={
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.nCompressMethod = fpt4code.fptCprMethodToGafisCode(fingerData.imgCompressMethod).toByte
    if (gafisImg.stHead.nCompressMethod == 0) //no compress
      gafisImg.stHead.bIsCompressed = 0
    else
      gafisImg.stHead.bIsCompressed = 1

    gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
    gafisImg.stHead.nWidth = fingerData.imgHorizontalLength.toShort
    gafisImg.stHead.nHeight = fingerData.imgVerticalLength.toShort
    gafisImg.stHead.nBits = 8
    gafisImg.stHead.nResolution = fingerData.dpi.toShort
    gafisImg.bnData = fingerData.imgData
    gafisImg.stHead.nImgSize = gafisImg.bnData.length
    if(!isLatent){//捺印指位
      val positionInt = fingerData.fgp.toInt
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
    gafisImg.stHead.nWidth = 2304
    gafisImg.stHead.nHeight = 2304
    gafisImg.stHead.nBits = 8
    gafisImg.stHead.nResolution = 500
    gafisImg.bnData = palmData
    gafisImg.stHead.nFingerIndex = fgp.toByte
    gafisImg.stHead.nImgSize = gafisImg.bnData.length
    gafisImg
  }

}
