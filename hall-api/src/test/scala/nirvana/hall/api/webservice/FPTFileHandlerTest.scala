package nirvana.hall.api.webservice


import java.io.FileInputStream

import nirvana.hall.api.webservice.util.FPTFileHandler
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import org.junit.Test

/**
  * Created by yuchen on 2016/12/7.
  */
class FPTFileHandlerTest {
  @Test
  def test_getTenprintFinger: Unit ={  //A1200002018881996110060.fpt  //44200481222242352823638.fpt
    val fptFile = FPTFileHandler.FPTFileParse(new FileInputStream("E:\\A1200002018881996110060.fpt"))
    val imageStruts = FPTFileHandler.getImage(fptFile)

    var personId = ""

    fptFile match {
      case Left(fpt3) => throw new Exception("Not Support FPT-V3.0")
      case Right(fpt4) =>
        val featureType = ParseFeatureTypeEnum(fpt4.tpCount.toInt,fpt4.lpCount.toInt)
        fpt4.logic02Recs.foreach { tp =>
          val personId = tp.personId

          tp.fingers.foreach { tData =>
            if (tData.imgData != null && tData.imgData.length > 0)
              if (tData.imgData != null && tData.imgData.length > 0) {
                val tBuffer = FPTFileHandler.fingerDataToGafisImage(tData)
                //解压
                val s = FPTFileHandler.callHallImageDecompressionImage(tBuffer)
                //提特征
                val mnt = FPTFileHandler.extractorFeature(s,fgpParesEnum(tData.fgp),featureType)
              }
          }
        }
    }


    assert(imageStruts.seq.iterator.hasNext,"获得图像错误")
  }

  /**
    * 将解析出的指位翻译成系统中的枚举类型
    */
  def fgpParesEnum(fgp:String): FingerPosition ={
    fgp match {
      case "01" =>
        FingerPosition.FINGER_R_THUMB
      case "02" =>
        FingerPosition.FINGER_R_INDEX
      case "03" =>
        FingerPosition.FINGER_R_MIDDLE
      case "04" =>
        FingerPosition.FINGER_R_RING
      case "05" =>
        FingerPosition.FINGER_R_LITTLE
      case "06" =>
        FingerPosition.FINGER_L_THUMB
      case "07" =>
        FingerPosition.FINGER_L_INDEX
      case "08" =>
        FingerPosition.FINGER_L_MIDDLE
      case "09" =>
        FingerPosition.FINGER_L_RING
      case "10" =>
        FingerPosition.FINGER_L_LITTLE
      case other =>
        FingerPosition.FINGER_UNDET
    }
  }

  /**
    *
    * @param tpCount
    * @param lpCount
    * @return
    */
  def ParseFeatureTypeEnum(tpCount:Integer,lpCount:Integer): FeatureType ={
    var featureType:FeatureType = null
    if(tpCount>0){
      featureType = FeatureType.FingerTemplate
    }else if(lpCount>0){
      featureType = FeatureType.FingerLatent
    }
    featureType
  }
}
