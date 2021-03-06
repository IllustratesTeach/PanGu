package nirvana.hall.c.services.gfpt4lib

import nirvana.hall.c.services.kernel.mnt_checker_def
import nirvana.hall.c.services.kernel.mnt_checker_def.{AFISCOREDELTASTRUCT, AFISMNTPOINTSTRUCT, MNTDISPSTRUCT}

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-03-17
  */
object fpt4util {

  /**
    * GAFIS6.2系统中的协查级别使用的是FPT标准3（Version3）标准，其代码为：0 - 未知；1 - A级；2 - B级；3 - C级；4 - B级紧急
    * 而最新协查级别为公安部FTP标准4（Version4），其代码：1 - A级；2 - B级紧急；3 - B级；4 - C级；9 - 其他；
    * 为了与最新的标准一致，在fpt导入导出时，对协查级别进行转换，转换原则为：
    *	1、导出时，协查级别转换成Version4标准，并且在fpt文件的第一类逻辑记录的备注里面增
    *		加标记"<FPX_Level Version = "4" />"，除非m_bReserveFPX_Level = 1
    * 2、导入时，如果导入的是非东方金指生成的fpt文件，则其中的协查级别自动被认为是Version4，
    *		把其转换成Version3标准，如果是东方金指生成的fpt文件，则检查第一类逻辑记录的备注中有
    *		没有FPX_Level Version标记，如果有并且其值为4，则转换成Version3标准，如果没有或者其值
    *		为3，则认为就是Version3标准，不用转换
    *	3、不管是导入还是导出，如果m_bReserveFPX_Level=1，则不对协查级别进行转换
    */

  /**
    * 目的特征数据的表示方式
    */
  final val UTIL_FPT4MNT_TOUNKNOWN = 0
  final val UTIL_FPT4MNT_TOMNTDISP = 1

  /**
    * 纹型类型
    */
  final val UTIL_FPT4_PATTERNTYPE_ARCH = 1		// 弓型纹
  final val UTIL_FPT4_PATTERNTYPE_LEFTLOOP = 2		// 左箕型纹
  final val UTIL_FPT4_PATTERNTYPE_RIGHTLOOP = 3		// 右箕型纹
  final val UTIL_FPT4_PATTERNTYPE_WHORL = 4		// 斗型纹
  final val UTIL_FPT4_PATTERNTYPE_NOTEXIST = 5		// 缺指
  final val UTIL_FPT4_PATTERNTYPE_UNKNOWN = 6		// 未知
  final val UTIL_FPT4_PATTERNTYPE_OTHER = 9		// 其它



  /**
    * 中心点、三角
    */
  final val UTIL_COREDELTA_TYPE_UPCORE = 1
  final val UTIL_COREDELTA_TYPE_VICECORE = 2
  final val UTIL_COREDELTA_TYPE_LDELTA = 3
  final val UTIL_COREDELTA_TYPE_RDELTA = 4
  final val UTIL_COREDELTA_TYPE_MINUTIA = 5



  /**
    * 去掉字符串左侧（或）和右侧的空格
    */
  final val UTIL_FPT4LIB_STRTRIM_STYLE_LEFT = 0x1
  final val UTIL_FPT4LIB_STRTRIM_STYLE_RIGHT = 0x2
  final val UTIL_FPT4LIB_STRTRIM_STYLE_TWOSIDE = 0x3

  def UTIL_FPT4LIB_StrTrimSpace(pszString:String, nTrimStyle:Int):String= {
    var ret = pszString
    if ( (nTrimStyle & UTIL_FPT4LIB_STRTRIM_STYLE_LEFT) > 0)  {
      ret = ret.replaceAll("^\\s*", "");
    }
    if ( (nTrimStyle & UTIL_FPT4LIB_STRTRIM_STYLE_RIGHT) > 0) {
      ret = ret.replaceAll("\\s*$", "");
    }

    ret
  }

  /**
    * 初始化特征结构 xgw
    * @param pmnt
    */
  def GfAlg_MntDispMntInitial(pmnt:MNTDISPSTRUCT): Unit ={
    import nirvana.hall.c.services.kernel.mnt_def._
    if (pmnt.bIsPalm == 0) {
      if (pmnt.bIsLatent == 0) {
        pmnt.nMax2PtLC = LINECOUNTSIZE.toShort
        pmnt.nMaxEA = BLKMNTSIZE.toShort
        pmnt.nMaxMnt = FTPMNTSIZE.toShort
        pmnt.nMaxMntRegion = MNTPOSSIZE.toShort
      } else {
        pmnt.nMax2PtLC = LINECOUNTSIZE.toShort
        pmnt.nMaxEA = BLKMNTSIZE.toShort
        pmnt.nMaxMnt = FLPMNTSIZE.toShort
        pmnt.nMaxMntRegion = MNTPOSSIZE.toShort
      }
    } else {
      if (pmnt.bIsLatent == 0) {
        pmnt.nMax2PtLC = LINECOUNTSIZE.toShort
        pmnt.nMaxEA = BLKMNTSIZE.toShort
        pmnt.nMaxMnt = PTPMNTSIZE.toShort
        pmnt.nMaxMntRegion = MNTPOSSIZE.toShort
      } else {
        pmnt.nMax2PtLC = LINECOUNTSIZE.toShort
        pmnt.nMaxEA = BLKMNTSIZE.toShort
        pmnt.nMaxMnt = PLPMNTSIZE.toShort
        pmnt.nMaxMntRegion = MNTPOSSIZE.toShort
      }
    }
  }

  def convertStringAsInt(string:String): Int={
    if(string == null || string.trim.isEmpty) 0 else string.trim.toInt
  }

  /**
    * 现场纹型转换
    * @param pattern
    * @return
    */
  def UTIL_LatPattern_FPT2MntDisp(pattern:String)=
  {
    pattern.trim.take(4).map(ch=>(ch - '0').asInstanceOf[Byte]).toArray
    /*
    val RpCode= Array[Byte](4)
    RpCode(0) = pattern.charAt(0) - '0';	//!< 弓型纹
    RpCode(1) = pszValue[1] - '0';	//!< 左箕纹
    RpCode(2) = pszValue[2] - '0';	//!< 右箕纹
    RpCode(3) = pszValue[3] - '0';	//!< 斗型纹
    */
    //ByteBuffer.wrap(bytes).getInt
  }
  def UTIL_LatPattern_MntDisp2FPT(rpCode: Array[Byte]): String={
    var pattern = ""
    rpCode.foreach{nValue =>
      if(nValue == 1){
        pattern += "1"
      }else{
        pattern += "0"
      }
    }

    pattern
  }

  /**
    * 指纹方向转换
    * @param direction
    * @return
    */
  def UTIL_Direction_FPT2MntDisp(direction:String):(Short,Short)=
  {
    if(direction.length!=5)
      return (UTIL_Angle_FPT2MntDisp(0),0)

    val fca = UTIL_Angle_FPT2MntDisp(direction.take(3).trim().toInt)
    val D_fca = direction.drop(3).trim().toInt

    (fca.toShort,D_fca.toShort)
  }
  def UTIL_Direction_MntDisp2FPT(mntDisp: MNTDISPSTRUCT): String ={
    val nFPTAngle = UTIL_Angle_MntDisp2FPT(mntDisp.stCm.fca)

    "%03d%02d".format(nFPTAngle, mntDisp.stCm.D_fca)
  }

  /**
    * 角度转换
    * @param p_nFPTAngle
    * @return
    */
  def UTIL_Angle_FPT2MntDisp(p_nFPTAngle:Int):Short= // [0, 360) --> [-90, 270)
  {
    var nFPTAngle = p_nFPTAngle
    nFPTAngle += 90
    while ( nFPTAngle >= 270 ) nFPTAngle -= 360
    while ( nFPTAngle < -90 ) nFPTAngle += 360

    nFPTAngle.toShort
  }
  def UTIL_Angle_MntDisp2FPT(fca: Short): Int ={
    var nFPT = fca - 90
    while (nFPT < 0) nFPT += 360
    while (nFPT >= 360) nFPT -= 360

    nFPT
  }

  /**
    * 中心，三角转换
    * @param CoreDelta
    * @param stCoreDelta
    * @param nType
    */
  def UTIL_CoreDelta_FPT2MntDisp(CoreDelta:String, stCoreDelta:AFISCOREDELTASTRUCT, nType:Int):Unit=
  {
    if(CoreDelta.length != 14)
      return
    val szCoreDelta = UTIL_FPT4LIB_StrTrimSpace(CoreDelta, UTIL_FPT4LIB_STRTRIM_STYLE_LEFT)

    stCoreDelta.x = szCoreDelta.take(3).trim().toShort

    stCoreDelta.y = szCoreDelta.substring(3,6).trim().toShort

    stCoreDelta.nRadius = convertStringAsInt(szCoreDelta.substring(6,8)).toByte

    // 9-11位为方向
    stCoreDelta.z = UTIL_Angle_FPT2MntDisp(convertStringAsInt(szCoreDelta.substring(8,11)))

    // 12-13位为方向范围
    stCoreDelta.nzVarRange = convertStringAsInt(szCoreDelta.substring(11,13)).toByte

    // 14位表示可靠度（1-3可靠度依次递减）
    stCoreDelta.nReliability = UTIL_Reliability_FPT2MntDisp(szCoreDelta.substring(13,14).toInt, nType).toByte

    stCoreDelta.bIsExist = 1

    nType match
    {
      case UTIL_COREDELTA_TYPE_UPCORE=>
        stCoreDelta.nClass = mnt_checker_def.DELTACLASS_CORE.toByte
      case UTIL_COREDELTA_TYPE_VICECORE=>
        stCoreDelta.nClass = mnt_checker_def.DELTACLASS_VICECORE.toByte
      case UTIL_COREDELTA_TYPE_LDELTA=>
        stCoreDelta.nClass = mnt_checker_def.DELTACLASS_LEFT.toByte
      case UTIL_COREDELTA_TYPE_RDELTA=>
        stCoreDelta.nClass = mnt_checker_def.DELTACLASS_RIGHT.toByte
      case other=>
        stCoreDelta.nClass = mnt_checker_def.DELTACLASS_UNKNOWN.toByte
    }
  }
  def UTIL_CoreDelta_MntDisp2FPT(stCoreDelta:AFISCOREDELTASTRUCT, nType:Int): String={
    var szSP3 = "   " //3个空格SP
    var szSP2 = "  " //2个空格SP
    if ( nType == UTIL_COREDELTA_TYPE_UPCORE || UTIL_COREDELTA_TYPE_VICECORE == nType){
      val nDir = UTIL_Angle_MntDisp2FPT(stCoreDelta.z)
      val nScope = stCoreDelta.nzVarRange
      szSP3 = "%03d".format(nDir)
      szSP2 = "%02d".format(nScope)
    }
    val nReliability = UTIL_Reliability_MntDisp2FPT(stCoreDelta.nReliability, nType)
    var nRadius = stCoreDelta.nRadius
    //TODO 位置半径校验，暂时对不符合要求（两位整数）默认设置30
    if(nRadius > 100 || nRadius < 0)
      nRadius = 30

    val result = "%03d%03d%02d%s%s%d".format(stCoreDelta.x, stCoreDelta.y, nRadius, szSP3, szSP2, nReliability)
    //中心特征长度超长14，返回空字符串
    if(result.length > 14){
      return ""
    }else{
      return result
    }
  }

  /**
    * 副中心导出到FPT文件时特征方向和角度调整
    * @param stViceCore
    * @param nCore 中心的方向
    * @param nzVarRange 中心的方向范围
    */
  def UTIL_ViceCoreMntDispCheckBefore2FPT(stViceCore: AFISCOREDELTASTRUCT, nCore: Short, nzVarRange: Byte) {
    if(nCore == 999) {
      stViceCore.z = 180.toShort
      stViceCore.nzVarRange = 90
      return 1
    }
    stViceCore.z = (nCore - 180).toShort
    if(stViceCore.z < -90) {
      stViceCore.z = (stViceCore.z + 360).toShort
    }
    if(nzVarRange < 45) stViceCore.nzVarRange = 45
    else stViceCore.nzVarRange = nzVarRange

    return 1
  }

  /**
    * 可靠度转换
    * @param nFPT
    * @param nType
    * @return
    */
  def UTIL_Reliability_FPT2MntDisp(nFPT:Int, nType:Int):Int=
  {
    if ( nType != UTIL_COREDELTA_TYPE_MINUTIA )
    {
      if ( nFPT < 1 ) return 2
      else if ( nFPT <= 2 ) return 0
      else return 1
    }
    else
    {
      if ( ( nFPT < 1 ) || ( nFPT > 2 ) ) return 0
      else return 1
    }
  }
  def UTIL_Reliability_MntDisp2FPT(nDisp: Int, nType: Int): Int={
    if ( nType != UTIL_COREDELTA_TYPE_MINUTIA )
    {
      if ( nDisp == 0 ) return 1
      else if ( nDisp == 1 ) return 3
      else return 0
    }
    else
    {
      if ( nDisp == 0 ) return 3
      else return 1
    }
  }


  /**
    * 细节特征点转换
    * @param FPTMnt
    * @param pstmnt
    * @param nmntCnt
    */
  def UTIL_Minutia_FPT2MntDisp(FPTMnt:String, pstmnt:Array[AFISMNTPOINTSTRUCT], nmntCnt:Int)
  {
    if(FPTMnt == null || FPTMnt.length < (9*nmntCnt))
      throw new IllegalArgumentException("mnt length not equals:f(%s) < n(%s)".format(FPTMnt.length,9*nmntCnt))
    0 until nmntCnt foreach{i=>
      pstmnt(i)=new AFISMNTPOINTSTRUCT
      UTIL_Minutia_OneFPT2MntDisp(FPTMnt.substring(i*9 ,i*9+9), pstmnt(i));
    }
  }
  def UTIL_Minutia_OneFPT2MntDisp(pszFPTMnt:String, stmnt:AFISMNTPOINTSTRUCT)
  {
    if (pszFPTMnt.replace(" ","").length> 0) {
      stmnt.x = pszFPTMnt.substring(0, 3).replace(" ","").toShort
      stmnt.y = pszFPTMnt.substring(3, 6).replace(" ","").toShort
      val zString = pszFPTMnt.substring(6, 9)
      stmnt.z = UTIL_Angle_FPT2MntDisp(zString.trim.replace(" ","").toInt)
      stmnt.nReliability = 1
    }
  }

  /**
    * 每个特征点用9个字节表示，x坐标、y坐标和方向各3位
    * @param stmnt
    * @return
    */
  def UTIL_Minutia_OneMntDisp2FPT(stmnt:AFISMNTPOINTSTRUCT): String ={
    "%03d%03d%03d".format(stmnt.x, stmnt.y, UTIL_Angle_MntDisp2FPT(stmnt.z))
  }

  def UTIL_Minutia_MntDisp2FPT(pstMnt: Seq[AFISMNTPOINTSTRUCT], nmntCnt: Int): String={
    var pszFPTMnt = ""
    (0 until nmntCnt).foreach(i => pszFPTMnt += UTIL_Minutia_OneMntDisp2FPT(pstMnt(i)))
//    pstMnt.foreach{mnt =>
//      pszFPTMnt += UTIL_Minutia_OneMntDisp2FPT(mnt)
//    }

    //无有效值的数据用ASCII码空格（SP）填写, 总长度1800
    while (pszFPTMnt.length < 1800) pszFPTMnt += " "

    pszFPTMnt
  }
}
