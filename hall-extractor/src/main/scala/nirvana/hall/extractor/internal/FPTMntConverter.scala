package nirvana.hall.extractor.internal

import java.nio.ByteOrder

import nirvana.hall.c.services.gfpt4lib.{FPT3File, FPT4File}
import nirvana.hall.c.services.gfpt4lib.fpt4util._
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.mnt_checker_def.{AFISMNTPOINTSTRUCT, MNTDISPSTRUCT}
import nirvana.hall.extractor.jni.NativeExtractor

/**
  * Created by songpeng on 2016/12/31.
  */
object FPTMntConverter {

  /**
    * 定义FPT捺印特征结构，使用type同时支持fpt3和fpt4的ingerTData
    */
  type FingerTDataMnt ={
    var pattern1: String   //指纹纹型分类,主纹型
    var pattern2: String   //指纹纹型分类,副纹型 ???
    var fgp: String   //指位
    var fingerDirection: String   //指纹方向
    var centerPoint: String   //中心点
    var subCenterPoint: String    //副中心
    var leftTriangle: String    //左三角
    var rightTriangle: String   //右三角
    var featureCount: String    //特征点个数
    var feature: String   //特征点
    var imgHorizontalLength: String   //图像水平方向长度
    var imgVerticalLength: String   //图像垂直方向长度
    var dpi: String   //图像分辨率
  }

  /**
    * 定义FPT现场特征结构，使用type同时支持fpt3和fpt4的ingerTData
    */
  type FingerLDataMnt ={
    var pattern: String   //指纹纹型分类
    var fgp: String   //指位
    var fingerDirection: String   //指纹方向
    var centerPoint: String   //中心点
    var subCenterPoint: String    //副中心
    var leftTriangle: String    //左三角
    var rightTriangle: String   //右三角
    var featureCount: String    //特征点个数
    var feature: String   //特征点
    var imgHorizontalLength: String   //图像水平方向长度
    var imgVerticalLength: String   //图像垂直方向长度
    var dpi: String   //图像分辨率
  }

  /**
    * fpt4捺印特征转换，定义一下方法是为了调用时不提示警告
    * @param fptMnt
    * @return
    */
  def convertFingerTData2GafisMnt(fptMnt: FPT4File.FingerTData): GAFISIMAGESTRUCT={
    convertFingerTDataMnt2GafisMnt(fptMnt)
  }
  def convertFingerTData2GafisMnt(fptMnt: FPT3File.FingerTData): GAFISIMAGESTRUCT={
    convertFingerTDataMnt2GafisMnt(fptMnt)
  }
  def convertFingerLData2GafisMnt(fptMnt: FPT4File.FingerLData): GAFISIMAGESTRUCT={
    convertFingerLDataMnt2GafisMnt(fptMnt)
  }
  def convertFingerLData2GafisMnt(fptMnt: FPT3File.FingerLData): GAFISIMAGESTRUCT={
    convertFingerLDataMnt2GafisMnt(fptMnt)
  }

  /**
    * fpt现场特征转换为6.2xgw特征
    * @param fptMnt
    * @return
    */
  def convertFingerLDataMnt2GafisMnt(fptMnt: FingerLDataMnt): GAFISIMAGESTRUCT={
    val mntDisp = convertFingerLDataMnt2MntDisp(fptMnt)
    //此处结构传入到JNI层需要采用低字节序
    val dispBytes = mntDisp.toByteArray(byteOrder=ByteOrder.LITTLE_ENDIAN)
    val bytes: Array[Byte] = new Array[Byte](640) //捺印现场特征长度都是640 等同于new FINGERMNTSTRUCT().toByteArray()
    //现场和捺印调用不同的jni
    NativeExtractor.ConvertFPTLatentMNT2Std(dispBytes,bytes)

    //构造GAFISIMAGESTRUCT
    val gafisImg = new GAFISIMAGESTRUCT
    //构造头信息
    val gafisHead = gafisImg.stHead
    gafisHead.bIsCompressed = 0
    gafisHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
    gafisHead.nWidth = fptMnt.imgHorizontalLength.toShort
    gafisHead.nHeight = fptMnt.imgVerticalLength.toShort
    gafisHead.nBits = 8
    gafisHead.nResolution = fptMnt.dpi.toShort
    gafisHead.szName = "FingerMnt"

    gafisImg.bnData = bytes
    gafisHead.nImgSize = bytes.length

    gafisImg
  }

  /**
    * fpt捺印特征转6.2xgw特征
    * @param fptMnt
    * @return
    */
  def convertFingerTDataMnt2GafisMnt(fptMnt: FingerTDataMnt): GAFISIMAGESTRUCT={
    if(fptMnt.feature.isEmpty)
      return null
    val mntDisp = convertFingerTDataMnt2MntDisp(fptMnt)
    //此处结构传入到JNI层需要采用低字节序
    val dispBytes = mntDisp.toByteArray(byteOrder=ByteOrder.LITTLE_ENDIAN)
    val bytes: Array[Byte] = new Array[Byte](640) //捺印现场特征长度都是640 等同于new FINGERMNTSTRUCT().toByteArray()
    //现场和捺印调用不同的jni
    NativeExtractor.GAFIS_MntDispToMntStd(dispBytes,bytes)

    //构造GAFISIMAGESTRUCT
    val gafisImg = new GAFISIMAGESTRUCT
    //构造头信息
    val gafisHead = gafisImg.stHead
    gafisHead.bIsCompressed = 0
    gafisHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
    gafisHead.nWidth = fptMnt.imgHorizontalLength.toShort
    gafisHead.nHeight = fptMnt.imgVerticalLength.toShort
    gafisHead.nBits = 8
    gafisHead.nResolution = fptMnt.dpi.toShort
    gafisHead.szName = "FingerMnt" //??? 是否要根据指位设置不同的名称

    gafisImg.bnData = bytes
    gafisHead.nImgSize = bytes.length

    gafisImg
  }


  /**
    * fpt现场特征转换为MNTDISPSTRUCT
    * @param fptMnt
    * @return
    */
  def convertFingerLDataMnt2MntDisp(fptMnt: FingerLDataMnt): MNTDISPSTRUCT={
    val mntDisp = new MNTDISPSTRUCT
    mntDisp.bIsLatent = 1
    mntDisp.bIsPalm = 0
    mntDisp.nResolution = fptMnt.dpi.toShort
    mntDisp.nHeight = fptMnt.imgVerticalLength.toShort
    mntDisp.nWidth = fptMnt.imgHorizontalLength.toShort

    GfAlg_MntDispMntInitial(mntDisp)

    //现场的指位和纹型
    mntDisp.stFg.FingerCode = fptMnt.fgp
    mntDisp.stFg.RpCode = UTIL_LatPattern_FPT2MntDisp(fptMnt.pattern)

    val fca=UTIL_Direction_FPT2MntDisp(fptMnt.fingerDirection)
    mntDisp.stCm.fca = fca._1
    mntDisp.stCm.D_fca = fca._2
    UTIL_CoreDelta_FPT2MntDisp(fptMnt.centerPoint,mntDisp.stFg.upcore,UTIL_COREDELTA_TYPE_UPCORE)
    UTIL_CoreDelta_FPT2MntDisp(fptMnt.subCenterPoint,mntDisp.stFg.lowcore,UTIL_COREDELTA_TYPE_UPCORE)
    UTIL_CoreDelta_FPT2MntDisp(fptMnt.leftTriangle,mntDisp.stFg.ldelta,UTIL_COREDELTA_TYPE_UPCORE)
    UTIL_CoreDelta_FPT2MntDisp(fptMnt.rightTriangle,mntDisp.stFg.rdelta,UTIL_COREDELTA_TYPE_UPCORE)

    mntDisp.stCm.nMntCnt = fptMnt.featureCount.toShort

    mntDisp.stCm.mnt = new Array[AFISMNTPOINTSTRUCT](mntDisp.stCm.nMntCnt)
    UTIL_Minutia_FPT2MntDisp(fptMnt.feature,mntDisp.stCm.mnt, mntDisp.stCm.nMntCnt)

    mntDisp
  }

  /**
    * 捺印fpt特征转换MNTDISPSTRUCT
    * @param fptMnt
    * @return
    */
  def convertFingerTDataMnt2MntDisp(fptMnt: FingerTDataMnt): MNTDISPSTRUCT={
    val mntDisp = new MNTDISPSTRUCT
    mntDisp.bIsLatent = 0
    mntDisp.bIsPalm = 0
    mntDisp.nResolution = fptMnt.dpi.toShort
    mntDisp.nHeight = fptMnt.imgVerticalLength.toShort
    mntDisp.nWidth = fptMnt.imgHorizontalLength.toShort

    GfAlg_MntDispMntInitial(mntDisp)

    //捺印指位和纹型
    mntDisp.stFg.FingerIdx = fptMnt.fgp.toInt.toByte
    mntDisp.stFg.rp = fptMnt.pattern1.toInt.toByte
    mntDisp.stFg.vrp= fptMnt.pattern2.toInt.toByte

    val fca=UTIL_Direction_FPT2MntDisp(fptMnt.fingerDirection)
    mntDisp.stCm.fca = fca._1
    mntDisp.stCm.D_fca = fca._2
    UTIL_CoreDelta_FPT2MntDisp(fptMnt.centerPoint,mntDisp.stFg.upcore,UTIL_COREDELTA_TYPE_UPCORE)
    UTIL_CoreDelta_FPT2MntDisp(fptMnt.subCenterPoint,mntDisp.stFg.lowcore,UTIL_COREDELTA_TYPE_UPCORE)
    UTIL_CoreDelta_FPT2MntDisp(fptMnt.leftTriangle,mntDisp.stFg.ldelta,UTIL_COREDELTA_TYPE_UPCORE)
    UTIL_CoreDelta_FPT2MntDisp(fptMnt.rightTriangle,mntDisp.stFg.rdelta,UTIL_COREDELTA_TYPE_UPCORE)

    mntDisp.stCm.nMntCnt = fptMnt.featureCount.toShort

    mntDisp.stCm.mnt = new Array[AFISMNTPOINTSTRUCT](mntDisp.stCm.nMntCnt)
    UTIL_Minutia_FPT2MntDisp(fptMnt.feature,mntDisp.stCm.mnt, mntDisp.stCm.nMntCnt)

    mntDisp
  }

}
