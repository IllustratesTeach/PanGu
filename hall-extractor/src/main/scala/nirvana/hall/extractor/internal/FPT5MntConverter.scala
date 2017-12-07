package nirvana.hall.extractor.internal

import java.nio.ByteOrder

import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gfpt5lib
import nirvana.hall.c.services.gfpt5lib.fpt5util._
import nirvana.hall.c.services.gfpt5lib.{LatentFingerFeatureMsg, LatentPalmFeatureMsg, PalmMsg, _}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.mnt_checker_def.{AFISCOREDELTASTRUCT, AFISMNTPOINTSTRUCT, MNTDISPSTRUCT}
import nirvana.hall.extractor.jni.NativeExtractor

import scala.collection.mutable.ArrayBuffer


object FPT5MntConverter {


  /**
    * fpt现场指纹特征转换为6.2xgw特征
    * @param fingerImageMsg 单枚现场指纹的图像信息
    * @param fingerFeatureMsg 单枚现场指纹的特征信息
    * @return
    */
  def convertFingerLDataMnt2GafisMnt(fingerImageMsg:LatentFingerImageMsg,fingerFeatureMsg: LatentFingerFeatureMsg): GAFISIMAGESTRUCT={
    val mntDisp = convertFingerLDataMnt2MntDisp(fingerImageMsg,fingerFeatureMsg)
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
    * @return
    */
  def convertPalmLDataMnt2GafisMnt(latentPalmImageMsg:LatentPalmImageMsg,latentPalmFeatureMsg: LatentPalmFeatureMsg):GAFISIMAGESTRUCT={
    val mntDisp = convertPalmLDataMnt2MntDisp(latentPalmImageMsg,latentPalmFeatureMsg)
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
    * @return
    */
  private def convertFingerLDataMnt2MntDisp(fingerImageMsg:LatentFingerImageMsg,fingerFeatureMsg: LatentFingerFeatureMsg): MNTDISPSTRUCT={
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

    var AFISMNTPOINTSTRUCTList = new ArrayBuffer[AFISMNTPOINTSTRUCT]
    var pstmnt:AFISMNTPOINTSTRUCT = null
    fingerFeatureMsg.LatentMinutiaSet.latentMinutia.foreach{
      t =>
        pstmnt = new AFISMNTPOINTSTRUCT
        UTIL_Minutia_OneFPT2MntDisp(t.fingerFeaturePointXCoordinate.toShort
          ,t.fingerFeaturePointYCoordinate.toShort
          ,t.fingerFeaturePointDirection
          ,t.fingerFeaturePointQuality.toShort,pstmnt)
        AFISMNTPOINTSTRUCTList += pstmnt
    }
    mntDisp.stCm.nMntCnt = AFISMNTPOINTSTRUCTList.size.toShort
    mntDisp.stCm.mnt = AFISMNTPOINTSTRUCTList.toArray

    mntDisp
  }



  /**
    * 现场掌纹fpt特征转换MNTDISPSTRUCT
    * 现场掌纹包括：折返点（目前金指没有）；三角点、特征点
    * @param latentPalmImageMsg 单个掌纹信息
    * @return
    */
  private def convertPalmLDataMnt2MntDisp(latentPalmImageMsg:LatentPalmImageMsg,latentPalmFeatureMsg: LatentPalmFeatureMsg): MNTDISPSTRUCT={
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
        scoreDeltaStruts.z = UTIL_Angle_FPT2MntDisp(d.latentPalmTrianglePointFeatureRange.toShort)
        d.latentPalmDeltaDirection.foreach{
          t =>
            scoreDeltaStruts.nRadius = t.latentPalmTrianglePointFeatureDirection.toByte
            scoreDeltaStruts.nzVarRange = t.latentPalmTrianglePointFeatureDirectionRange.toByte
        }
        scoreDeltaStruts.nClass = d.palmTrianglePostionTypeCode.toByte
        scoreDeltaStruts.bEdited = 1
        scoreDeltaStruts.bIsExist = 1
        scoreDeltaStrutsArray += scoreDeltaStruts
    }
    mntDisp.stPm.nPatternDeltaCnt = scoreDeltaStrutsArray.size.toByte
    mntDisp.stPm.PatternDelta = scoreDeltaStrutsArray.toArray

    var AFISMNTPOINTSTRUCTList = new ArrayBuffer[AFISMNTPOINTSTRUCT]
    var pstmnt:AFISMNTPOINTSTRUCT = null
    latentPalmFeatureMsg.latentPalmMinutiaSet.latentPalmMinutia.foreach{
      t =>
        pstmnt = new AFISMNTPOINTSTRUCT
        UTIL_Minutia_OneFPT2MntDisp(t.fingerFeaturePointXCoordinate.toShort
          ,t.fingerFeaturePointYCoordinate.toShort
          ,t.fingerFeaturePointDirection
          ,t.fingerFeaturePointQuality.toShort,pstmnt)
        pstmnt.nFlag = 1
        AFISMNTPOINTSTRUCTList += pstmnt
    }
    mntDisp.stCm.nMntCnt = AFISMNTPOINTSTRUCTList.size.toShort
    mntDisp.stCm.mnt = AFISMNTPOINTSTRUCTList.toArray


    mntDisp
  }

  /**
    * gafis特征转FPT5特征(捺印指纹)
    */
  def convertGafisMnt2FingerMsg(gafisMnt: GAFISIMAGESTRUCT, fingerMsg: FingerMsg = new FingerMsg): FingerMsg ={
    convertTFingerMntDisp2FPTMnt(gafisMnt2DisMnt(gafisMnt), fingerMsg)
  }

  /**
    * gafis特征转FPT5特征(捺印掌纹)
    */
  def convertGafisMnt2PalmMsg(gafisMnt: GAFISIMAGESTRUCT, palmMsg: PalmMsg = new PalmMsg): PalmMsg ={
    convertTPalmMntDisp2FPTMnt(gafisMnt2DisMnt(gafisMnt), palmMsg)
  }

  /**
    * gafis特征转FPT5特征(现场指纹)
    */
  def convertGafisMnt2LatentFingerFeatureMsg(gafisMnt: GAFISIMAGESTRUCT, latentFeatureMsg: LatentFingerFeatureMsg = new LatentFingerFeatureMsg): LatentFingerFeatureMsg ={
    convertLFingerMntDisp2FPTMnt(gafisMnt2DisMnt(gafisMnt), latentFeatureMsg)
  }

  /**
    * gafis特征转FPT5特征(现场掌纹)
    */
  def convertGafisMnt2LatentPalmFeatureMsg(gafisMnt: GAFISIMAGESTRUCT, latentPalmFeatureMsg: LatentPalmFeatureMsg = new LatentPalmFeatureMsg): LatentPalmFeatureMsg ={
    convertLPalmMntDisp2FPTMnt(gafisMnt2DisMnt(gafisMnt), latentPalmFeatureMsg)
  }

  /**
    * 捺印指纹MNTDISPSTRUCT转换FPT特征
    * @param mntDisp
    * @return
    */
  private def convertTFingerMntDisp2FPTMnt(mntDisp: MNTDISPSTRUCT, fingerMsg: FingerMsg): FingerMsg={
    fingerMsg.fingerPatternMasterCode = mntDisp.stFg.rp.toString
    fingerMsg.fingerPatternSlaveCode = mntDisp.stFg.vrp.toString

    val util_featureDir = UTIL_Direction_MntDisp2FPT(mntDisp)
    fingerMsg.fingerFeatureDirection = util_featureDir.direction
    fingerMsg.fingerFeatureDirectionRange = util_featureDir.range
    if(mntDisp.stFg.upcore.bIsExist > 0) {
      //中心点
      val centerPointInfo = UTIL_CoreDelta_MntDisp2FPT(mntDisp.stFg.upcore, UTIL_COREDELTA_TYPE_UPCORE)
      fingerMsg.fingerCenterPointFeatureXCoordinate = centerPointInfo.x
      fingerMsg.fingerCenterPointFeatureYCoordinate = centerPointInfo.y
      fingerMsg.fingerCenterPointFeatureCoordinateRange = centerPointInfo.nRadius
      fingerMsg.fingerCenterPointFeatureDirection = centerPointInfo.szSP3.toInt
      fingerMsg.fingerCenterPointFeatureDirectionRange = centerPointInfo.szSP2.toInt
      fingerMsg.fingerCenterPointFeatureReliabilityLevel = centerPointInfo.nReliability
    }
    if(mntDisp.stFg.lowcore.bIsExist > 0){
      //副中心
      val slaveCenterPointInfo = UTIL_CoreDelta_MntDisp2FPT(mntDisp.stFg.lowcore, UTIL_COREDELTA_TYPE_VICECORE)
      fingerMsg.fingerSlaveCenterFeatureXCoordinate = slaveCenterPointInfo.x
      fingerMsg.fingerSlaveCenterFeatureYCoordinate = slaveCenterPointInfo.y
      fingerMsg.fingerSlaveCenterFeatureCoordinateRange = slaveCenterPointInfo.nRadius
      fingerMsg.fingerSlaveCenterFeatureDirection = slaveCenterPointInfo.szSP3.toInt
      fingerMsg.fingerSlaveCenterFeatureDirectionRange = slaveCenterPointInfo.szSP2.toInt
      fingerMsg.fingerSlaveCenterFeatureReliabilityLevel = slaveCenterPointInfo.nReliability
    }
      if(mntDisp.stFg.ldelta.bIsExist > 0){
        //左三角
        val LeftTriangleInfo = UTIL_CoreDelta_MntDisp2FPT(mntDisp.stFg.ldelta,UTIL_COREDELTA_TYPE_LDELTA)
        fingerMsg.fingerLeftTriangleFeatureXCoordinate = LeftTriangleInfo.x
        fingerMsg.fingerLeftTriangleFeatureYCoordinate = LeftTriangleInfo.y
        fingerMsg.fingerLeftTriangleFeatureCoordinateRange = LeftTriangleInfo.nRadius
        fingerMsg.fingerLeftTriangleFeatureReliabilityLevel = LeftTriangleInfo.nReliability
      }
      if(mntDisp.stFg.rdelta.bIsExist > 0){
        //右三角
        val rightTriangleInfo = UTIL_CoreDelta_MntDisp2FPT(mntDisp.stFg.ldelta,UTIL_COREDELTA_TYPE_RDELTA)
        fingerMsg.fingerRightTriangleFeatureXCoordinate = rightTriangleInfo.x
        fingerMsg.fingerRightTriangleFeatureYCoordinate = rightTriangleInfo.y
        fingerMsg.fingerRightTriangleFeatureCoordinateRange = rightTriangleInfo.nRadius
        fingerMsg.fingerRightTriangleFeatureReliabilityLevel = rightTriangleInfo.nReliability
      }

    val nMaxMnt:Int = 1800/9
    var ntemp:Int = mntDisp.stCm.nMntCnt
    if ( ntemp > nMaxMnt ) ntemp = nMaxMnt
    if ( ntemp > 0 ) {
      var minutiaArray = new ArrayBuffer[gfpt5lib.Minutia]
      var minutia:gfpt5lib.Minutia = null
      for (i <- 0 until ntemp){
        minutia = new gfpt5lib.Minutia
        minutia.fingerFeaturePointXCoordinate = mntDisp.stCm.mnt(i).x
        minutia.fingerFeaturePointYCoordinate = mntDisp.stCm.mnt(i).y
        minutia.fingerFeatureDirection = UTIL_Angle_MntDisp2FPT(mntDisp.stCm.mnt(i).z)
        minutia.fingerFeatureQuality = mntDisp.stCm.mnt(i).nFlag.toInt
        minutiaArray += minutia
      }
      fingerMsg.fingerMinutiaSet.minutia = minutiaArray.toArray
    }
    fingerMsg.fingerFeatureExtractionMethodCode = fpt4code.EXTRACT_METHOD_A
    fingerMsg
  }


  /**
    * 捺印掌纹MNTDISPSTRUCT转换FPT特征
    * @param mntDisp
    * @return
    */
  private def convertTPalmMntDisp2FPTMnt(mntDisp: MNTDISPSTRUCT, palmMsg: PalmMsg): PalmMsg={
    if(mntDisp.stPm.nPatternDeltaCnt.toInt >0){
      var deltaArray = new ArrayBuffer[Delta]
      var delta:Delta = null
      var deltaDirection:DeltaDirection = null
      val deltaSet = new DeltaSet
      for(i <- 0 until mntDisp.stPm.nPatternDeltaCnt){
        val t = mntDisp.stPm.PatternDelta(i)
        var deltaDirectionArray  = new ArrayBuffer[DeltaDirection]
        delta = new Delta
        delta.palmTrianglePointFeatureXCoordinate = t.x
        delta.palmTrianglePointFeatureYCoordinate = t.y
        delta.palmTrianglePointFeatureCoodinateRange = t.z
        deltaDirection = new DeltaDirection
        deltaDirection.palmTrianglePointFeatureDirection = t.nRadius
        deltaDirection.palmTrianglePointFeatureDirectionRange = t.nzVarRange
        deltaDirectionArray += deltaDirection
        delta.deltaDirection = deltaDirectionArray.toArray
        deltaArray += delta
      }
      deltaSet.delta = deltaArray.toArray
      palmMsg.deltaSet = deltaSet
    }

    val palmMinutiaSet = new PalmMinutiaSet
    val nMaxMnt:Int = 1800/9
    var ntemp:Int = mntDisp.stCm.nMntCnt
    if ( ntemp > nMaxMnt ) ntemp = nMaxMnt
    if (ntemp > 0) {
      var minutiaArray = new ArrayBuffer[gfpt5lib.PalmMinutia]
      var minutia: gfpt5lib.PalmMinutia = null
      for (i <- 0 until ntemp) {
        minutia = new gfpt5lib.PalmMinutia
        minutia.fingerFeaturePointXCoordinate = mntDisp.stCm.mnt(i).x
        minutia.fingerFeaturePointYCoordinate = mntDisp.stCm.mnt(i).y
        minutia.fingerFeatureDirection = UTIL_Angle_MntDisp2FPT(mntDisp.stCm.mnt(i).z)
        minutia.fingerFeatureQuality = mntDisp.stCm.mnt(i).nFlag.toInt
        minutiaArray += minutia
      }
      palmMinutiaSet.palmMinutia = minutiaArray.toArray
      palmMsg.palmMinutiaSet = palmMinutiaSet
    }
    palmMsg
  }

  /**
    * 现场指纹MNTDISPSTRUCT转换FPT特征
    * @param mntDisp
    * @return
    */
  private def convertLFingerMntDisp2FPTMnt(mntDisp: MNTDISPSTRUCT, latentFingerFeatureMsg: LatentFingerFeatureMsg):LatentFingerFeatureMsg = {
    val util_Direction = UTIL_Direction_MntDisp2FPT(mntDisp)
    latentFingerFeatureMsg.fingerFeatureDirection = util_Direction.direction
    latentFingerFeatureMsg.fingerFeatureDirectionRange = util_Direction.range
    if(mntDisp.stFg.upcore.bIsExist > 0) {
      //中心点
      val centerPointInfo = UTIL_CoreDelta_MntDisp2FPT(mntDisp.stFg.upcore, UTIL_COREDELTA_TYPE_UPCORE)
      latentFingerFeatureMsg.fingerCenterPointFeatureXCoordinate = centerPointInfo.x
      latentFingerFeatureMsg.fingerCenterPointFeatureYCoordinate = centerPointInfo.y
      latentFingerFeatureMsg.fingerCenterPointFeatureCoordinateRange = centerPointInfo.nRadius
      latentFingerFeatureMsg.fingerCenterPointFeatureDirection = centerPointInfo.szSP3.toInt
      latentFingerFeatureMsg.fingerCenterPointFeatureDirectionRange = centerPointInfo.szSP2.toInt
      latentFingerFeatureMsg.fingerCenterPointFeatureReliabilityLevel = centerPointInfo.nReliability
    }
    if(mntDisp.stFg.lowcore.bIsExist > 0){
      //副中心
      val slaveCenterPointInfo = UTIL_CoreDelta_MntDisp2FPT(mntDisp.stFg.lowcore, UTIL_COREDELTA_TYPE_VICECORE)
      latentFingerFeatureMsg.fingerSlaveCenterFeatureXCoordinate = slaveCenterPointInfo.x
      latentFingerFeatureMsg.fingerSlaveCenterFeatureYCoordinate = slaveCenterPointInfo.y
      latentFingerFeatureMsg.fingerSlaveCenterFeatureCoordinateRange = slaveCenterPointInfo.nRadius
      latentFingerFeatureMsg.fingerSlaveCenterFeatureDirection = slaveCenterPointInfo.szSP3.toInt
      latentFingerFeatureMsg.fingerSlaveCenterFeatureDirectionRange = slaveCenterPointInfo.szSP2.toInt
      latentFingerFeatureMsg.fingerSlaveCenterFeatureReliabilityLevel = slaveCenterPointInfo.nReliability
    }
    if(mntDisp.stFg.ldelta.bIsExist > 0){
      //左三角
      val LeftTriangleInfo = UTIL_CoreDelta_MntDisp2FPT(mntDisp.stFg.ldelta,UTIL_COREDELTA_TYPE_LDELTA)
      latentFingerFeatureMsg.fingerLeftTriangleFeatureXCoordinate = LeftTriangleInfo.x
      latentFingerFeatureMsg.fingerLeftTriangleFeatureYCoordinate = LeftTriangleInfo.y
      latentFingerFeatureMsg.fingerLeftTriangleFeatureCoordinateRange = LeftTriangleInfo.nRadius
      latentFingerFeatureMsg.fingerLeftTriangleFeatureReliabilityLevel = LeftTriangleInfo.nReliability
    }
    if(mntDisp.stFg.rdelta.bIsExist > 0){
      //右三角
      val rightTriangleInfo = UTIL_CoreDelta_MntDisp2FPT(mntDisp.stFg.ldelta,UTIL_COREDELTA_TYPE_RDELTA)
      latentFingerFeatureMsg.fingerRightTriangleFeatureXCoordinate = rightTriangleInfo.x
      latentFingerFeatureMsg.fingerRightTriangleFeatureYCoordinate = rightTriangleInfo.y
      latentFingerFeatureMsg.fingerRightTriangleFeatureCoordinateRange = rightTriangleInfo.nRadius
      latentFingerFeatureMsg.fingerRightTriangleFeatureReliabilityLevel = rightTriangleInfo.nReliability
    }

    val nMaxMnt:Int = 1800/9
    var ntemp:Int = mntDisp.stCm.nMntCnt
    if ( ntemp > nMaxMnt ) ntemp = nMaxMnt
    if ( ntemp > 0 ) {
      var minutiaArray = new ArrayBuffer[gfpt5lib.LatentMinutia]
      var minutia:gfpt5lib.LatentMinutia = null
      for (i <- 0 until ntemp){
        minutia = new gfpt5lib.LatentMinutia
        minutia.fingerFeaturePointXCoordinate = mntDisp.stCm.mnt(i).x
        minutia.fingerFeaturePointYCoordinate = mntDisp.stCm.mnt(i).y
        minutia.fingerFeaturePointDirection = UTIL_Angle_MntDisp2FPT(mntDisp.stCm.mnt(i).z)
        minutia.fingerFeaturePointQuality = mntDisp.stCm.mnt(i).nFlag.toInt
        minutiaArray += minutia
      }
      latentFingerFeatureMsg.LatentMinutiaSet.latentMinutia = minutiaArray.toArray
    }
    latentFingerFeatureMsg.latentFeatureExtractMethodCode = fpt4code.EXTRACT_METHOD_M
    latentFingerFeatureMsg
  }


  /**
    * 现场掌纹MNTDISPSTRUCT转换FPT特征
    * @param mntDisp
    * @return
    */
  private def convertLPalmMntDisp2FPTMnt(mntDisp:MNTDISPSTRUCT, latentPalmFeatureMsg: LatentPalmFeatureMsg):LatentPalmFeatureMsg = {

    if(mntDisp.stPm.nPatternDeltaCnt.toInt >0){
      var deltaArray = new ArrayBuffer[LatentPalmDelta]
      var delta:LatentPalmDelta = null
      var deltaDirectionArray  = new ArrayBuffer[LatentPalmDeltaDirection]
      var deltaDirection:LatentPalmDeltaDirection = null
      for(i <- 0 until mntDisp.stPm.nPatternDeltaCnt){
        delta = new LatentPalmDelta
        delta.latentPalmTrianglePointFeatureXCoordinate = mntDisp.stPm.PatternDelta(i).x
        delta.latentPalmTrianglePointFeatureYCoordinate = mntDisp.stPm.PatternDelta(i).y
        delta.latentPalmTrianglePointFeatureRange = UTIL_Angle_MntDisp2FPT(mntDisp.stPm.PatternDelta(i).z)
        deltaDirection = new LatentPalmDeltaDirection
        deltaDirection.latentPalmTrianglePointFeatureDirection = mntDisp.stPm.PatternDelta(i).nRadius
        deltaDirection.latentPalmTrianglePointFeatureDirectionRange = mntDisp.stPm.PatternDelta(i).nzVarRange
        deltaDirectionArray += deltaDirection
        delta.latentPalmDeltaDirection = deltaDirectionArray.toArray
        deltaArray += delta
      }
      latentPalmFeatureMsg.latentPalmDeltaSet.latentPalmDelta = deltaArray.toArray
    }

    val latentPalmMinutiaSet = new LatentPalmMinutiaSet
    val nMaxMnt:Int = 1800/9
    var ntemp:Int = mntDisp.stCm.nMntCnt
    if ( ntemp > nMaxMnt ) ntemp = nMaxMnt
    if (ntemp > 0) {
      var minutiaArray = new ArrayBuffer[gfpt5lib.LatentPalmMinutia]
      var minutia: gfpt5lib.LatentPalmMinutia = null
      for (i <- 0 until ntemp) {
        minutia = new gfpt5lib.LatentPalmMinutia
        minutia.fingerFeaturePointXCoordinate = mntDisp.stCm.mnt(i).x
        minutia.fingerFeaturePointYCoordinate = mntDisp.stCm.mnt(i).y
        minutia.fingerFeaturePointDirection = UTIL_Angle_MntDisp2FPT(mntDisp.stCm.mnt(i).z)
        minutia.fingerFeaturePointQuality = mntDisp.stCm.mnt(i).nFlag.toInt
        minutiaArray += minutia
      }
      latentPalmMinutiaSet.latentPalmMinutia = minutiaArray.toArray
      latentPalmFeatureMsg.latentPalmMinutiaSet = latentPalmMinutiaSet
    }
    latentPalmFeatureMsg.latentPalmFeatureExtractMethodCode = fpt4code.EXTRACT_METHOD_M
    latentPalmFeatureMsg
  }


  /**
    * gafis特征转换为显示特征
    * @return
    */
  private def gafisMnt2DisMnt(gafisMnt: GAFISIMAGESTRUCT):MNTDISPSTRUCT = {
    val mntDispBytes = (new MNTDISPSTRUCT).toByteArray(byteOrder=ByteOrder.LITTLE_ENDIAN)
    NativeExtractor.GAFIS_MntStdToMntDisp(gafisMnt.bnData, mntDispBytes, 1)//1???

    val mntDisp = new MNTDISPSTRUCT
    mntDisp.fromByteArray(mntDispBytes, byteOrder=ByteOrder.LITTLE_ENDIAN)
  }
}
