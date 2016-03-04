package nirvana.hall.c.services.gfpt4lib

import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-17
 */
object fpt4code {

  final val UTIL_FPT4LIB_AFISSYSTEM_CODE = "1900"

  // 不压缩
  final val GAIMG_CPRMETHOD_NOCPR_CODE = "0000"
  // 公安部10压缩
  final val GAIMG_CPRMETHOD_GA10_CODE = "0010"
  // 东方金指
  final val GAIMG_CPRMETHOD_EGFS_CODE = "1900"
  //东方金指WSQ3.1压缩
  final val GAIMG_CPRMETHOD_EGFS_WSQ_CODE = "0131"
  // 北大高科
  final val GAIMG_CPRMETHOD_PKU_CODE = "1300"
  //WSQ压缩方法
  final val GAIMG_CPRMETHOD_WSQ_CODE = "1400"
  // 北京海鑫
  final val GAIMG_CPRMETHOD_COGENT_CODE = "1700"
  // 小日本NEC
  final val GAIMG_CPRMETHOD_NEC_CODE = "1800"
  // 北京邮电大学
  final val GAIMG_CPRMETHOD_BUPT_CODE = "1200"
  // 北京刑科所
  final val GAIMG_CPRMETHOD_TSINGHUA_CODE = "1100"
  //魔佛
  final val GAIMG_CPRMETHOD_MORPHO_CODE = "2000" // 广东测试提供的数据，SAGEM MORPHO
  //汉林信通
  final val GAIMG_CPRMETHOD_HLXT_CODE = "2900" // 广东测试提供的数据，SAGEM MORPHO

  def gafisCprCodeToFPTCode(code:Int): String = {
    code match {
      case glocdef.GAIMG_CPRMETHOD_DEFAULT => // by xgw supplied method
        throw new UnsupportedOperationException("%s compress not supported".format(code))
      case glocdef.GAIMG_CPRMETHOD_XGW => // by xgw supplied method.
        throw new UnsupportedOperationException("%s compress not supported".format(code))
      case glocdef.GAIMG_CPRMETHOD_XGW_EZW => // 许公望的EZW压缩方法：适合低倍率高保真的压缩.
        throw new UnsupportedOperationException("%s compress not supported".format(code))
      case glocdef.GAIMG_CPRMETHOD_GA10 => // 公安部10倍压缩方法
        fpt4code.GAIMG_CPRMETHOD_GA10_CODE
      case glocdef.GAIMG_CPRMETHOD_GFS => // golden
        fpt4code.GAIMG_CPRMETHOD_EGFS_CODE
      case glocdef.GAIMG_CPRMETHOD_PKU => // call pku's compress method
        fpt4code.GAIMG_CPRMETHOD_PKU_CODE
      case glocdef.GAIMG_CPRMETHOD_COGENT => // cogent compress method
        fpt4code.GAIMG_CPRMETHOD_COGENT_CODE
      case glocdef.GAIMG_CPRMETHOD_WSQ => // was method
        fpt4code.GAIMG_CPRMETHOD_WSQ_CODE
      case glocdef.GAIMG_CPRMETHOD_NEC => // nec compress method
        fpt4code.GAIMG_CPRMETHOD_NEC_CODE
      case glocdef.GAIMG_CPRMETHOD_TSINGHUA => // tsing hua
        fpt4code.GAIMG_CPRMETHOD_TSINGHUA_CODE
      case glocdef.GAIMG_CPRMETHOD_BUPT => // beijing university of posts and telecommunications
        fpt4code.GAIMG_CPRMETHOD_BUPT_CODE
      case glocdef.GAIMG_CPRMETHOD_MORPHO =>
        fpt4code.GAIMG_CPRMETHOD_MORPHO_CODE
      case glocdef.GAIMG_CPRMETHOD_HLXT =>
        fpt4code.GAIMG_CPRMETHOD_HLXT_CODE
        /*
      case glocdef.GAIMG_CPRMETHOD_RMTZIP => // compressmethod provide by communication server(GAFIS)
        throw new UnsupportedOperationException("%s compress not supported".format(code))
      case glocdef.GAIMG_CPRMETHOD_LCW => // compress method provide by lucas wang.
        throw new UnsupportedOperationException("%s compress not supported".format(code))
      case glocdef.GAIMG_CPRMETHOD_JPG => // jpeg method.
        throw new UnsupportedOperationException("%s compress not supported".format(code))
      case glocdef.GAIMG_CPRMETHOD_MORPHO => //!< 广东测试时提供的压缩算法，MORPHO(SAGEM)
        throw new UnsupportedOperationException("%s compress not supported".format(code))
      case glocdef.GAIMG_CPRMETHOD_MAXVALUE =>
        throw new UnsupportedOperationException("%s compress not supported".format(code))
      */
      case other =>
        throw new UnsupportedOperationException("%s compress not supported".format(code))
    }
  }
  private final val GA10_PATTERN="00([0-9][0-9])".r
  private final val EGFS_CODE_PATTERN="19([0-9][0-9])".r
  private final val PKU_CODE_PATTERN = "13([0-9][0-9])".r
  private final val WSQ_CODE_PATTERN = "14([0-9][0-9])".r
  private final val COGENT_CODE_PATTERN = "17([0-9][0-9])".r
  private final val NEC_CODE_PATTERN = "18([0-9][0-9])".r
  private final val BUPT_CODE_PATTERN = "12([0-9][0-9])".r
  private final val TSINGHUA_CODE_PATTERN = "11([0-9][0-9])".r
  private final val MORPHO_CODE_PATTERN = "20([0-9][0-9])".r
  private final val HLXT_CODE_PATTERN = "29([0-9][0-9])".r
  //fpt4code to glocdef
  def fptCprMethodToGafisCode(codeFromFpt: String): Int={
    codeFromFpt match{
      case fpt4code.GAIMG_CPRMETHOD_NOCPR_CODE=>
        0
      case GA10_PATTERN(subCode) => 	// 公安部10倍压缩方法
        glocdef.GAIMG_CPRMETHOD_GA10
      case EGFS_CODE_PATTERN(subCode)  => 	// golden
        glocdef.GAIMG_CPRMETHOD_GFS
      case PKU_CODE_PATTERN(subCode)  => 	// call pku's compress method
        glocdef.GAIMG_CPRMETHOD_PKU
      case COGENT_CODE_PATTERN(subCode)  => 	// cogent compress method
        glocdef.GAIMG_CPRMETHOD_COGENT
      case WSQ_CODE_PATTERN(code) => 	// was method
        //case WSQ_CODE => 	// was method
        glocdef.GAIMG_CPRMETHOD_WSQ
      case NEC_CODE_PATTERN(subCode)  => 	// nec compress method
        glocdef.GAIMG_CPRMETHOD_NEC
      case TSINGHUA_CODE_PATTERN(subCode)  => 	// tsing hua
        glocdef.GAIMG_CPRMETHOD_TSINGHUA
      //        throw new UnsupportedOperationException("%s compress not supported".format(codeFromFpt))
      case BUPT_CODE_PATTERN(subCode)  => 	// beijing university of posts and telecommunications
        glocdef.GAIMG_CPRMETHOD_BUPT
      case MORPHO_CODE_PATTERN(subCode)  =>
        glocdef.GAIMG_CPRMETHOD_MORPHO
      case HLXT_CODE_PATTERN(subCode)  =>
        glocdef.GAIMG_CPRMETHOD_HLXT
      case other=>
        throw new UnsupportedOperationException("%s compress not supported".format(codeFromFpt))
    }
  }
  type FPTFingerData ={
    var imgCompressMethod:String
    var imgData:Array[Byte]
    var imgHorizontalLength:String
    var imgVerticalLength :String
    var dpi:String
    var fgp:String
  }
  def FPTFingerDataToGafisImage(personId: String, tData: FPTFingerData): GAFISIMAGESTRUCT = {
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.bIsCompressed = 1
    gafisImg.stHead.nCompressMethod = fptCprMethodToGafisCode(tData.imgCompressMethod).toByte
    if (gafisImg.stHead.nCompressMethod == 0) //no compress
      gafisImg.stHead.bIsCompressed = 0

    gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
    gafisImg.stHead.nWidth = tData.imgHorizontalLength.toShort
    gafisImg.stHead.nHeight = tData.imgVerticalLength.toShort
    gafisImg.stHead.nBits = 8
    gafisImg.stHead.nResolution = tData.dpi.toShort
    gafisImg.bnData = tData.imgData
    gafisImg.stHead.nImgSize = gafisImg.bnData.length
    val positionInt = tData.fgp.toInt
    if (positionInt > 10)
      gafisImg.stHead.bIsPlain = 1

    gafisImg
  }
}


