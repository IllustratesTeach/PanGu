package nirvana.hall.extractor.internal

import java.nio.ByteOrder
import nirvana.hall.c.services.gfpt5lib.fpt5util._
import nirvana.hall.c.services.gfpt5lib._
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.mnt_checker_def.{AFISCOREDELTASTRUCT, AFISMNTPOINTSTRUCT, MNTDISPSTRUCT}
import nirvana.hall.extractor.jni.NativeExtractor

import scala.collection.mutable.ArrayBuffer


object FPT5MntConverter {


  /**
    * 特征集合的通用类型
    */
  type MinutiaSet = {
    var minutiaList:Array[fpt5util.Minutia]
  }


  /**
    * fpt捺印指纹特征转6.2xgw特征
    * @param fingerMsg 单枚指纹信息
    * @param minutiaSet 单枚指纹特征点集合
    * @return
    */
  def convertFingerTDataMnt2GafisMnt(fingerMsg:FingerMsg,minutiaSet:MinutiaSet): GAFISIMAGESTRUCT={
    if(minutiaSet.minutiaList.isEmpty)
      return null
    val mntDisp = convertFingerTDataMnt2MntDisp(fingerMsg,minutiaSet)
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
    gafisHead.nWidth = fingerMsg.fingerImageHorizontalDirectionLength.toShort
    gafisHead.nHeight = fingerMsg.fingerImageVerticalDirectionLength.toShort
    gafisHead.nBits = 8
    gafisHead.nResolution = fingerMsg.fingerImageRatio.toShort
    gafisHead.szName = "TFingerMnt"

    gafisImg.bnData = bytes
    gafisHead.nImgSize = bytes.length

    gafisImg
  }

  /**
    * fpt捺印掌纹特征转6.2xgw特征
    * @param palmMsg 单枚掌纹信息
    * @param palmTDataMnt 单枚掌纹特征点信息
    * @return
    */
  def convertPalmTDataMnt2GafisMnt(palmMsg:PalmMsg,palmTDataMnt:MinutiaSet):GAFISIMAGESTRUCT={
    if(palmTDataMnt.minutiaList.isEmpty)
      return null
    val mntDisp = convertPalmTDataMnt2MntDisp(palmMsg,palmTDataMnt)
    //此处结构传入到JNI层需要采用低字节序
    val dispBytes = mntDisp.toByteArray(byteOrder=ByteOrder.LITTLE_ENDIAN)
    val bytes: Array[Byte] = new Array[Byte](640) //捺印掌纹大小是多少？？？
    //现场和捺印调用不同的jni
    NativeExtractor.GAFIS_MntDispToMntStd(dispBytes,bytes)

    //构造GAFISIMAGESTRUCT
    val gafisImg = new GAFISIMAGESTRUCT
    //构造头信息
    val gafisHead = gafisImg.stHead
    gafisHead.bIsCompressed = 0
    gafisHead.nImageType = glocdef.GAIMG_IMAGETYPE_PALM.toByte
    gafisHead.nWidth = palmMsg.palmImageHorizontalDirectionLength.toShort
    gafisHead.nHeight = palmMsg.palmImageVerticalDirectionLength.toShort
    gafisHead.nBits = 8
    gafisHead.nResolution = palmMsg.palmImageRatio.toShort
    gafisHead.szName = "TPalmMnt"

    gafisImg.bnData = bytes
    gafisHead.nImgSize = bytes.length

    gafisImg
  }


  /**
    * fpt现场指纹特征转换为6.2xgw特征
    * @param fingerImageMsg 单枚现场指纹的图像信息
    * @param fingerFeatureMsg 单枚现场指纹的特征信息
    * @param minutiaSet 单枚现场指纹的特征点集合
    * @return
    */
  def convertFingerLDataMnt2GafisMnt(fingerImageMsg:LatentFingerImageMsg,fingerFeatureMsg: LatentFingerFeatureMsg,minutiaSet: MinutiaSet): GAFISIMAGESTRUCT={
    val mntDisp = convertFingerLDataMnt2MntDisp(fingerImageMsg,fingerFeatureMsg,minutiaSet)
    //此处结构传入到JNI层需要采用低字节序
    val dispBytes = mntDisp.toByteArray(byteOrder=ByteOrder.LITTLE_ENDIAN)
    val bytes: Array[Byte] = new Array[Byte](640)
    //现场和捺印调用不同的jni
    NativeExtractor.GAFIS_MntDispToMntStd(dispBytes,bytes)

    //构造GAFISIMAGESTRUCT
    val gafisImg = new GAFISIMAGESTRUCT
    //构造头信息
    val gafisHead = gafisImg.stHead
    gafisHead.bIsCompressed = 0
    gafisHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
    gafisHead.nWidth = fingerImageMsg.latentFingerImageHorizontalDirectionLength.toShort
    gafisHead.nHeight = fingerImageMsg.latentFingerImageVerticalDirectionLength.toShort
    gafisHead.nBits = 8
    gafisHead.nResolution = fingerImageMsg.latentFingerImageRatio.toShort
    gafisHead.szName = "LFingerMnt"

    gafisImg.bnData = bytes
    gafisHead.nImgSize = bytes.length

    gafisImg
  }

  /**
    * fpt现场掌纹特征转6.2xgw特征
    * @param latentPalmImageMsg 单枚现场掌纹的图像信息
    * @param latentPalmFeatureMsg 单枚现场掌纹的特征信息
    * @param palmDataMnt  单枚现场掌纹的特征点集合
    * @return
    */
  def convertPalmLDataMnt2GafisMnt(latentPalmImageMsg:LatentPalmImageMsg,latentPalmFeatureMsg: LatentPalmFeatureMsg,palmDataMnt:MinutiaSet):GAFISIMAGESTRUCT={
    if(palmDataMnt.minutiaList.isEmpty)
      return null
    val mntDisp = convertPalmLDataMnt2MntDisp(latentPalmImageMsg,latentPalmFeatureMsg,palmDataMnt)
    //此处结构传入到JNI层需要采用低字节序
    val dispBytes = mntDisp.toByteArray(byteOrder=ByteOrder.LITTLE_ENDIAN)
    val bytes: Array[Byte] = new Array[Byte](640) //捺印掌纹大小是多少？？？
    //现场和捺印调用不同的jni
    NativeExtractor.GAFIS_MntDispToMntStd(dispBytes,bytes)

    //构造GAFISIMAGESTRUCT
    val gafisImg = new GAFISIMAGESTRUCT
    //构造头信息
    val gafisHead = gafisImg.stHead
    gafisHead.bIsCompressed = 0
    gafisHead.nImageType = glocdef.GAIMG_IMAGETYPE_PALM.toByte
    gafisHead.nWidth = latentPalmImageMsg.latentPalmImageHorizontalDirectionLength.toShort
    gafisHead.nHeight = latentPalmImageMsg.latentPalmImageVerticalDirectionLength.toShort
    gafisHead.nBits = 8
    gafisHead.nResolution = latentPalmImageMsg.latentPalmImageRatio.toShort
    gafisHead.szName = "LPalmMnt"

    gafisImg.bnData = bytes
    gafisHead.nImgSize = bytes.length

    gafisImg
  }




  /**
    * fpt现场指纹特征转换为MNTDISPSTRUCT
    * @param fingerImageMsg 现场指纹信息
    * @param fingerFeatureMsg 现场指纹特征信息
    * @param minutiaSet 单枚现场掌纹特征点集合
    * @return
    */
  private def convertFingerLDataMnt2MntDisp(fingerImageMsg:LatentFingerImageMsg,fingerFeatureMsg: LatentFingerFeatureMsg,minutiaSet: MinutiaSet): MNTDISPSTRUCT={
    val mntDisp = new MNTDISPSTRUCT
    mntDisp.bIsLatent = 1
    mntDisp.bIsPalm = 0
    mntDisp.nResolution = 500
    mntDisp.nHeight = fingerImageMsg.latentFingerImageVerticalDirectionLength.toShort
    mntDisp.nWidth = fingerImageMsg.latentFingerImageHorizontalDirectionLength.toShort

    GfAlg_MntDispMntInitial(mntDisp)

    //现场的分析指位和纹型
    mntDisp.stFg.FingerCode = fingerImageMsg.latentFingerAnalysisPostionBrief
    mntDisp.stFg.RpCode = UTIL_LatPattern_FPT2MntDisp(fingerImageMsg.latentFingerPatternAnalysisBrief)

    val fca=UTIL_Direction_FPT2MntDisp(fingerImageMsg.latentFingerFeatureDirection.toString)
    mntDisp.stCm.fca = fca._1
    mntDisp.stCm.D_fca = fca._2
    UTIL_CoreDelta_FPT2MntDisp(fingerFeatureMsg.fingerCenterPointFeatureXCoordinate
      , fingerFeatureMsg.fingerCenterPointFeatureYCoordinate
      ,fingerFeatureMsg.fingerCenterPointFeatureCoordinateRange
      ,fingerFeatureMsg.fingerCenterPointFeatureDirection
      ,fingerFeatureMsg.fingerCenterPointFeatureDirectionRange
      ,fingerFeatureMsg.fingerCenterPointFeatureReliabilityLevel,mntDisp.stFg.upcore,UTIL_COREDELTA_TYPE_UPCORE)
    UTIL_CoreDelta_FPT2MntDisp(fingerFeatureMsg.fingerSlaveCenterFeatureXCoordinate
      , fingerFeatureMsg.fingerSlaveCenterFeatureYCoordinate
      ,fingerFeatureMsg.fingerSlaveCenterFeatureCoordinateRange
      ,fingerFeatureMsg.fingerSlaveCenterFeatureDirection
      ,fingerFeatureMsg.fingerSlaveCenterFeatureDirectionRange
      ,fingerFeatureMsg.fingerSlaveCenterFeatureReliabilityLevel,mntDisp.stFg.lowcore,UTIL_COREDELTA_TYPE_VICECORE)
    UTIL_CoreDelta_FPT2MntDisp(fingerFeatureMsg.fingerLeftTriangleFeatureXCoordinate
      , fingerFeatureMsg.fingerLeftTriangleFeatureYCoordinate
      ,fingerFeatureMsg.fingerLeftTriangleFeatureCoordinateRange
      ,fingerFeatureMsg.fingerLeftTriangleFeatureDirection
      ,fingerFeatureMsg.fingerLeftTriangleFeatureDirectionRange
      ,fingerFeatureMsg.fingerLeftTriangleFeatureReliabilityLevel,mntDisp.stFg.ldelta,UTIL_COREDELTA_TYPE_LDELTA)
    UTIL_CoreDelta_FPT2MntDisp(fingerFeatureMsg.fingerRightTriangleFeatureXCoordinate
      , fingerFeatureMsg.fingerRightTriangleFeatureYCoordinate
      ,fingerFeatureMsg.fingerRightTriangleFeatureCoordinateRange
      ,fingerFeatureMsg.fingerRightTriangleFeatureDirection
      ,fingerFeatureMsg.fingerRightTriangleFeatureDirectionRange
      ,fingerFeatureMsg.fingerRightTriangleFeatureReliabilityLevel,mntDisp.stFg.rdelta,UTIL_COREDELTA_TYPE_RDELTA)

    mntDisp.stCm.mnt = new Array[AFISMNTPOINTSTRUCT](mntDisp.stCm.nMntCnt)
    UTIL_Minutia_FPT2MntDisp(minutiaSet.minutiaList,mntDisp.stCm.mnt, mntDisp.stCm.nMntCnt)

    mntDisp
  }

  /**
    * 捺印fpt特征转换MNTDISPSTRUCT
    * @param fingerMsg 单枚指纹信息
    * @param minutiaSet 单枚指纹特征点集合
    * @return
    */
  private def convertFingerTDataMnt2MntDisp(fingerMsg:FingerMsg,minutiaSet:MinutiaSet): MNTDISPSTRUCT={
    val mntDisp = new MNTDISPSTRUCT
    mntDisp.bIsLatent = 0
    mntDisp.bIsPalm = 0
    mntDisp.nResolution = fingerMsg.fingerImageRatio.toShort
    mntDisp.nHeight = fingerMsg.fingerImageVerticalDirectionLength.toShort
    mntDisp.nWidth = fingerMsg.fingerImageHorizontalDirectionLength.toShort

    GfAlg_MntDispMntInitial(mntDisp)

    //捺印指位和纹型
    mntDisp.stFg.FingerIdx = fingerMsg.fingerPositionCode.toInt.toByte
    mntDisp.stFg.rp = fingerMsg.fingerPatternMasterCode.toInt.toByte
    mntDisp.stFg.vrp= fingerMsg.fingerPatternSlaveCode.toInt.toByte

    val fca=UTIL_Direction_FPT2MntDisp(fingerMsg.fingerFeatureDirection.toString)
    mntDisp.stCm.fca = fca._1
    mntDisp.stCm.D_fca = fca._2
    UTIL_CoreDelta_FPT2MntDisp(fingerMsg.fingerCenterPointFeatureXCoordinate
                              ,fingerMsg.fingerCenterPointFeatureYCoordinate
                              ,fingerMsg.fingerCenterPointFeatureCoordinateRange
                              ,fingerMsg.fingerCenterPointFeatureDirection
                              ,fingerMsg.fingerCenterPointFeatureDirectionRange
                              ,fingerMsg.fingerCenterPointFeatureReliabilityLevel
                              ,mntDisp.stFg.upcore,UTIL_COREDELTA_TYPE_UPCORE)
    UTIL_CoreDelta_FPT2MntDisp(fingerMsg.fingerSlaveCenterFeatureXCoordinate
                              ,fingerMsg.fingerSlaveCenterFeatureYCoordinate
                              ,fingerMsg.fingerSlaveCenterFeatureCoordinateRange
                              ,fingerMsg.fingerSlaveCenterFeatureDirection
                              ,fingerMsg.fingerSlaveCenterFeatureDirectionRange
                              ,fingerMsg.fingerSlaveCenterFeatureReliabilityLevel
                              ,mntDisp.stFg.lowcore,UTIL_COREDELTA_TYPE_UPCORE)
    UTIL_CoreDelta_FPT2MntDisp(fingerMsg.fingerLeftTriangleFeatureXCoordinate
                              ,fingerMsg.fingerLeftTriangleFeatureYCoordinate
                              ,fingerMsg.fingerLeftTriangleFeatureCoordinateRange
                              ,fingerMsg.fingerLeftTriangleFeatureDirection
                              ,fingerMsg.fingerLeftTriangleFeatureDirectionRange
                              ,fingerMsg.fingerLeftTriangleFeatureReliabilityLevel
                              ,mntDisp.stFg.ldelta
                              ,UTIL_COREDELTA_TYPE_UPCORE)
    UTIL_CoreDelta_FPT2MntDisp(fingerMsg.fingerRightTriangleFeatureXCoordinate
                              ,fingerMsg.fingerRightTriangleFeatureYCoordinate
                              ,fingerMsg.fingerRightTriangleFeatureCoordinateRange
                              ,fingerMsg.fingerRightTriangleFeatureDirection
                              ,fingerMsg.fingerRightTriangleFeatureDirectionRange
                              ,fingerMsg.fingerRightTriangleFeatureReliabilityLevel
                              ,mntDisp.stFg.rdelta,UTIL_COREDELTA_TYPE_UPCORE)

    mntDisp.stCm.mnt = new Array[AFISMNTPOINTSTRUCT](mntDisp.stCm.nMntCnt)
    UTIL_Minutia_FPT2MntDisp(minutiaSet.minutiaList,mntDisp.stCm.mnt, mntDisp.stCm.nMntCnt)

    mntDisp
  }

  /**
    * 捺印掌纹fpt特征转换MNTDISPSTRUCT
    * 捺印掌纹包括：折返点（目前金指没有）；三角点、特征点
    * @param palmMsg 单个掌纹信息
    * @param palmDataMnt 单个掌纹特征点集合
    * @return
    */
  private def convertPalmTDataMnt2MntDisp(palmMsg:PalmMsg,palmDataMnt:MinutiaSet): MNTDISPSTRUCT={
    val mntDisp = new MNTDISPSTRUCT
    mntDisp.bIsLatent = 0
    mntDisp.bIsPalm = 1
    mntDisp.nResolution = palmMsg.palmImageRatio.toShort
    mntDisp.nHeight = palmMsg.palmImageVerticalDirectionLength.toShort
    mntDisp.nWidth = palmMsg.palmImageHorizontalDirectionLength.toShort

    GfAlg_MntDispMntInitial(mntDisp)

    mntDisp.stPm.nPalmIndex = palmMsg.palmPostionCode.toInt.toByte
    var scoreDeltaStruts:AFISCOREDELTASTRUCT = null
    var scoreDeltaStrutsArray = new ArrayBuffer[AFISCOREDELTASTRUCT]
    palmMsg.deltaSet.delta.foreach{
      d =>
        scoreDeltaStruts = new AFISCOREDELTASTRUCT
      scoreDeltaStruts.x = d.palmTrianglePointFeatureXCoordinate.toShort
      scoreDeltaStruts.y = d.palmTrianglePointFeatureYCoordinate.toShort
      scoreDeltaStruts.z = d.palmTrianglePointFeatureCoodinateRange.toShort
        d.deltaDirection.foreach{
          t =>
            scoreDeltaStruts.nRadius = t.palmTrianglePointFeatureDirection.toByte
            scoreDeltaStruts.nzVarRange = t.palmTrianglePointFeatureDirectionRange.toByte
      }
        scoreDeltaStruts.nClass = d.deltaPostionClassCode.toByte
        scoreDeltaStrutsArray += scoreDeltaStruts
        mntDisp.stPm.PatternDelta = scoreDeltaStrutsArray.toArray
    }

    mntDisp.stCm.mnt = new Array[AFISMNTPOINTSTRUCT](mntDisp.stCm.nMntCnt)
    UTIL_Minutia_FPT2MntDisp(palmDataMnt.minutiaList,mntDisp.stCm.mnt, mntDisp.stCm.nMntCnt)

    mntDisp
  }


  /**
    * 现场掌纹fpt特征转换MNTDISPSTRUCT
    * 现场掌纹包括：折返点（目前金指没有）；三角点、特征点
    * @param latentPalmImageMsg 单个掌纹信息
    * @param palmDataMnt 单个掌纹特征点集合
    * @return
    */
  private def convertPalmLDataMnt2MntDisp(latentPalmImageMsg:LatentPalmImageMsg,latentPalmFeatureMsg: LatentPalmFeatureMsg,palmDataMnt:MinutiaSet): MNTDISPSTRUCT={
    val mntDisp = new MNTDISPSTRUCT
    mntDisp.bIsLatent = 1
    mntDisp.bIsPalm = 1
    mntDisp.nResolution = latentPalmImageMsg.latentPalmImageRatio.toShort
    mntDisp.nHeight = latentPalmImageMsg.latentPalmImageVerticalDirectionLength.toShort
    mntDisp.nWidth = latentPalmImageMsg.latentPalmImageVerticalDirectionLength.toShort

    GfAlg_MntDispMntInitial(mntDisp)
    var scoreDeltaStruts:AFISCOREDELTASTRUCT = null
    var scoreDeltaStrutsArray = new ArrayBuffer[AFISCOREDELTASTRUCT]
    latentPalmFeatureMsg.latentPalmDeltaSet.latentPalmDelta.foreach{
      d =>
        scoreDeltaStruts = new AFISCOREDELTASTRUCT
        scoreDeltaStruts.x = d.latentPalmTrianglePointFeatureXCoordinate.toShort
        scoreDeltaStruts.y = d.latentPalmTrianglePointFeatureYCoordinate.toShort
        scoreDeltaStruts.z = d.latentPalmTrianglePointFeatureRange.toShort
        d.latentPalmDeltaDirection.foreach{
          t =>
            scoreDeltaStruts.nRadius = t.latentPalmTrianglePointFeatureDirection.toByte
            scoreDeltaStruts.nzVarRange = t.latentPalmTrianglePointFeatureDirectionRange.toByte
        }
        scoreDeltaStruts.nClass = d.palmTrianglePostionTypeCode.toByte
        scoreDeltaStrutsArray += scoreDeltaStruts
        mntDisp.stPm.PatternDelta = scoreDeltaStrutsArray.toArray
    }

    mntDisp.stCm.mnt = new Array[AFISMNTPOINTSTRUCT](mntDisp.stCm.nMntCnt)
    UTIL_Minutia_FPT2MntDisp(palmDataMnt.minutiaList,mntDisp.stCm.mnt, mntDisp.stCm.nMntCnt)

    mntDisp
  }


}
