package nirvana.hall.c.services.kernel

import java.nio.ByteBuffer

import nirvana.hall.c.services.gfpt4lib.FPT4File.FingerLData
import nirvana.hall.c.services.gfpt4lib.fpt4util._
import nirvana.hall.c.services.kernel.mnt_checker_def._
import nirvana.hall.c.services.kernel.mnt_def._

/**
  * convert fpt latent data to MNTDISPSTRUCT structure
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-03-17
  */
object FPTLDataToMNTDISP {
  def convertFPT03ToMNTDISP(fingerLData: FingerLData): MNTDISPSTRUCT ={
    val mntDisp = new MNTDISPSTRUCT
    mntDisp.bIsPalm = 0
    mntDisp.bIsLatent = 1
    mntDisp.nResolution = fingerLData.dpi.toShort
    mntDisp.nHeight = fingerLData.imgVerticalLength.toShort
    mntDisp.nWidth = fingerLData.imgHorizontalLength.toShort
    GAFIS_MntDispMnt_Initial(mntDisp)


    mntDisp.stFg.FingerCode = fingerLData.fgp
    mntDisp.stFg.RpCode = UTIL_LatPattern_FPT2MntDisp(fingerLData.pattern)
    val fca=UTIL_Direction_FPT2MntDisp(fingerLData.fingerDirection)
    mntDisp.stCm.fca = fca._1
    mntDisp.stCm.D_fca = fca._2
    UTIL_CoreDelta_FPT2MntDisp(fingerLData.centerPoint,mntDisp.stFg.upcore,UTIL_COREDELTA_TYPE_UPCORE)
    UTIL_CoreDelta_FPT2MntDisp(fingerLData.subCenterPoint,mntDisp.stFg.lowcore,UTIL_COREDELTA_TYPE_UPCORE)
    UTIL_CoreDelta_FPT2MntDisp(fingerLData.leftTriangle,mntDisp.stFg.ldelta,UTIL_COREDELTA_TYPE_UPCORE)
    UTIL_CoreDelta_FPT2MntDisp(fingerLData.rightTriangle,mntDisp.stFg.rdelta,UTIL_COREDELTA_TYPE_UPCORE)

    mntDisp.stCm.nMntCnt = fingerLData.featureCount.toShort

    UTIL_Minutia_FPT2MntDisp(fingerLData.feature,mntDisp.stCm.mnt, mntDisp.stCm.nMntCnt)

    mntDisp
  }
  def GAFIS_MntDispMnt_Initial(pmnt:MNTDISPSTRUCT) {

    if (pmnt.bIsPalm == 0) {
      if (pmnt.bIsLatent == 0) {
        pmnt.nMax2PtLC = LINECOUNTSIZE;
        pmnt.nMaxEA = BLKMNTSIZE;
        pmnt.nMaxMnt = FTPMNTSIZE;
        pmnt.nMaxMntRegion = MNTPOSSIZE;
      } else {
        pmnt.nMax2PtLC = LINECOUNTSIZE;
        pmnt.nMaxEA = BLKMNTSIZE;
        pmnt.nMaxMnt = FLPMNTSIZE;
        pmnt.nMaxMntRegion = MNTPOSSIZE;
      }
    } else {
      if (pmnt.bIsLatent == 0) {
        pmnt.nMax2PtLC = LINECOUNTSIZE;
        pmnt.nMaxEA = BLKMNTSIZE;
        pmnt.nMaxMnt = PTPMNTSIZE;
        pmnt.nMaxMntRegion = MNTPOSSIZE;
      } else {
        pmnt.nMax2PtLC = LINECOUNTSIZE;
        pmnt.nMaxEA = BLKMNTSIZE;
        pmnt.nMaxMnt = PLPMNTSIZE;
        pmnt.nMaxMntRegion = MNTPOSSIZE;
      }

    }
  }
  def UTIL_LatPattern_FPT2MntDisp(pattern:String):Int=
  {
    val bytes = pattern.map(ch=>(ch - '0').asInstanceOf[Byte]).toArray
    /*
    val RpCode= Array[Byte](4)
    RpCode(0) = pattern.charAt(0) - '0';	//!< 弓型纹
    RpCode(1) = pszValue[1] - '0';	//!< 左箕纹
    RpCode(2) = pszValue[2] - '0';	//!< 右箕纹
    RpCode(3) = pszValue[3] - '0';	//!< 斗型纹
    */
    ByteBuffer.wrap(bytes).getInt
  }
  def UTIL_Direction_FPT2MntDisp(direction:String):(Short,Short)=
  {

    val fca = UTIL_Angle_FPT2MntDisp(direction.take(3).trim().toInt)

    val D_fca = direction.drop(3).trim().toInt

    (fca.toShort,D_fca.toShort)
  }
  def UTIL_Angle_FPT2MntDisp(p_nFPTAngle:Int):Short= // [0, 360) --> [-90, 270)
  {
    var nFPTAngle = p_nFPTAngle
    nFPTAngle += 90;
    while ( nFPTAngle >= 270 ) nFPTAngle -= 360;
    while ( nFPTAngle < -90 ) nFPTAngle += 360;

    nFPTAngle.toShort
  }

  def UTIL_CoreDelta_FPT2MntDisp(CoreDelta:String, stCoreDelta:AFISCOREDELTASTRUCT , nType:Int)=
  {

    val szCoreDelta = UTIL_FPT4LIB_StrTrimSpace(CoreDelta, UTIL_FPT4LIB_STRTRIM_STYLE_LEFT)
    stCoreDelta.x = szCoreDelta.take(3).toShort

    stCoreDelta.y = szCoreDelta.substring(3,6).toShort

    stCoreDelta.nRadius = szCoreDelta.substring(6,8).toInt.toByte

    // 9-11位为方向
    stCoreDelta.z = UTIL_Angle_FPT2MntDisp(szCoreDelta.substring(8,11).toInt)

    // 12-13位为方向范围
    stCoreDelta.nzVarRange = szCoreDelta.substring(11,13).toInt.toByte

    // 14位表示可靠度（1-3可靠度依次递减）
    stCoreDelta.nReliability = UTIL_Reliability_FPT2MntDisp(szCoreDelta.substring(13,14).toInt, nType).toByte

    stCoreDelta.bIsExist = 1

    nType match
    {
      case UTIL_COREDELTA_TYPE_UPCORE=>
      stCoreDelta.nClass = DELTACLASS_CORE;
      case UTIL_COREDELTA_TYPE_VICECORE=>
      stCoreDelta.nClass = DELTACLASS_VICECORE;
      case UTIL_COREDELTA_TYPE_LDELTA=>
      stCoreDelta.nClass = DELTACLASS_LEFT;
      case UTIL_COREDELTA_TYPE_RDELTA=>
      stCoreDelta.nClass = DELTACLASS_RIGHT;
      case other=>
        stCoreDelta.nClass = mnt_checker_def.DELTACLASS_UNKNOWN;
    }
  }
  def UTIL_FPT4LIB_StrTrimSpace(pszString:String, nTrimStyle:Int):String=
  {
    var ret = pszString
    if ( (nTrimStyle & UTIL_FPT4LIB_STRTRIM_STYLE_LEFT) > 0)  {
      ret = ret.replaceAll("^\\s*", "");
    }
    if ( (nTrimStyle & UTIL_FPT4LIB_STRTRIM_STYLE_RIGHT) > 0) {
      ret = ret.replaceAll("\\s*$", "");
    }

    ret
  }
  def UTIL_Reliability_FPT2MntDisp(nFPT:Int, nType:Int):Int=
  {
    if ( nType != UTIL_COREDELTA_TYPE_MINUTIA )
    {
      if ( nFPT < 1 ) return 2;
      else if ( nFPT <= 2 ) return 0;
      else return 1;
    }
    else
    {
      if ( ( nFPT < 1 ) || ( nFPT > 2 ) ) return 0;
      else return 1;
    }
  }


  def UTIL_Minutia_FPT2MntDisp(FPTMnt:String, pstmnt:Array[AFISMNTPOINTSTRUCT], nmntCnt:Int)
  {
    if(FPTMnt == null || FPTMnt.length < (9*nmntCnt))
      throw new IllegalArgumentException("mnt length not equals:f(%s) < n(%s)".format(FPTMnt.length,9*nmntCnt))
    0 until nmntCnt foreach{i=>
      UTIL_Minutia_OneFPT2MntDisp(FPTMnt.substring(i*9 ,i*9+9), pstmnt(i));
    }
  }
  def UTIL_Minutia_OneFPT2MntDisp(pszFPTMnt:String, stmnt:AFISMNTPOINTSTRUCT)
  {
    stmnt.x = pszFPTMnt.substring(0,3).toShort
    stmnt.y = pszFPTMnt.substring(3,6).toShort
    stmnt.z = UTIL_Angle_FPT2MntDisp(pszFPTMnt.substring(6,9).toInt)
    stmnt.nReliability = 1
  }
}
