package nirvana.hall.c.services.kernel

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.AncientData

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-03-17
  */
object mnt_checker_def {
  /*
   * 特征文件展示相关结构定义的头文件.
   * @file mnt_checker_def.h
   */


  //#define GAIFS_SWEATPORE_USED

  final val KEYIDLEN = 32
  final val USERIDLEN = 16

  //typedef unsigned short int	uint2;
  //typedef unsigned char		UCHAR;
  //typedef short int			int2;
  //typedef unsigned  int		uint4;

  /* value for MNTDATSTRUCT.rp */
  final val MNTRP_UNDET = 0
  /* 不确定 */
  final val MNTRP_ARCH = 1
  /* 弓型  */
  final val MNTRP_LEFTLOOP = 2
  /* 左箕 */
  final val MNTRP_RIGHTLOOP = 3
  /* 右箕 */
  final val MNTRP_WHORL = 4 /* 斗  */

  /* value for whorl type */
  final val MNTWHORL_UNDET = 0
  final val MNTWHORL_RIGHT = 1
  final val MNTWHORL_LEFT = 2

  class AFISPOINTSTRUCT extends AncientData {
    var x: Short = _;
    var y: Short = _;
  }

  // AFISPOINTSTRUCT; // 4 bytes long.

  class AFISMNTPOINTSTRUCT extends AncientData {
    var x: Short = _;
    //	特征点横向坐标，范围从0到 nImgWidth-1
    var y: Short = _;
    //	特征点纵向坐标，范围从0到 nImgHeight-1
    var z: Short = _;
    //	细节特征方向, [-90, 270)
    var nFlag: Byte = _;
    //	MNTPTFLAG_XXX  使用特征质量标记
    var nReliability: Byte = _;
    //	特征可靠度，0...不可信，1...可信
    var nPosOnFinger: Byte = _;
    //	掌纹特征位置 按位计算，或的关系
    //	0x1.....指根区，0x2...内侧区 0x4...外侧区
    @Length(7)
    var nRes: Array[Byte] = _;
  }

  // AFISMNTPOINTSTRUCT; // 16 bytes

  final val MNTPTFLAG_USEREL = 0x1
  //	使用特征质量标记
  //	指纹
  final val DELTACLASS_CORE = 0
  final val DELTACLASS_VICECORE = 0

  final val DELTACLASS_LEFT = 0
  final val DELTACLASS_RIGHT = 1

  final val DELTACLASS_UNKNOWN = 0
  final val DELTACLASS_WRIST = 1
  final val DELTACLASS_FINGROOT = 2
  final val DELTACLASS_PATTERN = 3

  // pattern

  // structure for center
  class AFISCOREDELTASTRUCT extends AncientData {
    var x: Short = _;
    var y: Short = _;
    var z: Short = _;
    //	特征方向，范围从-90到 270，999表示未知方向
    var nRadius: Byte = _;
    //	位置半径
    var nzVarRange: Byte = _;
    //	方向范围
    var nReliability: Byte = _;
    //	可信度，1...估计，2...虚拟
    var nPosOnPalm: Byte = _;
    //	特征所在区域，0：未知区域，1：指根区 ,2：内侧区，3：外侧区，其他：跨区域用（掌纹）
    var nFingerIdx: Byte = _;
    //  指根三角索引号（掌纹用）
    var nClass: Byte = _;
    //	三角是 DELTACLASS_XXX, （副）中心是 CORECLASS_XXX
    var bIsExist: Byte = _;
    //	0...不存在，1...存在
    var bEdited: Byte = _;
    //	是否编辑过
    var nRes: Short = _;
  }

  // AFISCOREDELTASTRUCT; // 16 bytes

  // structure for circle

  class AFISCIRCLESTRUCT extends AncientData {
    var x: Short = _;
    var y: Short = _;
    var nRelibility: Byte = _;
    var nRadius: Byte = _;
    var nRes: Short = _;
  }

  // AFISCIRCLESTRUCT; // 8 bytes

  class AFISRECTSTRUCT extends AncientData {
    var l: Short = _;
    // left
    var t: Short = _;
    // top
    var r: Short = _;
    // right
    var b: Short = _;
    // bottom
    var nPos: Byte = _;
    // which part, MNTPART_XX
    @Length(3)
    var bnRes: Array[Byte] = _;
  }

  // AFISRECTSTRUCT; // size is 12 bytes

  //  MODIFY AND NEW MNTPART_XXX
  final val MNTPART_PALM_FP = 1
  final val MNTPART_PALM_IP = 2
  final val MNTPART_PALM_OP = 3

  //	指根区的小分区
  final val MNTPART_PALM_FP1 = 11
  final val MNTPART_PALM_FP2 = 12
  final val MNTPART_PALM_FP3 = 13
  final val MNTPART_PALM_FP4 = 14
  //	内侧区的小分区
  final val MNTPART_PALM_IPUP = 21
  final val MNTPART_PALM_IPMD = 22
  final val MNTPART_PALM_IPLW = 23
  //	外侧区的小分区
  final val MNTPART_PALM_OPUP = 31
  final val MNTPART_PALM_OPMD = 32
  final val MNTPART_PALM_OPLW = 33


  final val MNTPART_FINGER_UPPER = 1
  final val MNTPART_FINGER_LOWER = 2
  final val MNTPART_FINGER_LEFT = 3
  final val MNTPART_FINGER_RIGHT = 4

  class LINECNT2MNTSTRUCT extends AncientData {
    var nStartMntIdx: Short = _;
    // first mnt index, 2 bytes
    var nEndMntIdx: Short = _;
    // second mnt index	2 bytes
    var nLineCnt: Short = _;
    // 2 bytes
    var nRes: Short = _; // reserved 2 bytes
  }

  // LINECNT2MNTSTRUCT; // total 8 bytes

  class GAFIS_PALMSPECMNTSTRUCT extends AncientData {
    /////////////////		掌纹专有特征	////////////////////////////////
    var nPalmIndex: Byte = _;
    // MODIFY  掌位，0：未知，1：右掌，2：左掌
    @Length(3)
    var bnRes: Array[Byte] = _;
    // MODIFY reserved, to here is 4 bytes long.
    var nBaseLinePos: Short = _;
    //
    var wca: Short = _;
    //  NEW 第一曲肌褶纹方向（起点为外侧点）
    // to here is 8 bytes long.
    @Length(8)
    var bnRes0: Array[Byte] = _;
    var stWIPt = new AFISPOINTSTRUCT;
    // 	第一曲肌褶纹内侧点
    var stWOPt = new AFISPOINTSTRUCT;
    //  第一曲肌褶纹外侧点
    var nPatternDeltaCnt: Byte = _;
    //	花纹三角个数
    var nPatternCoreCnt: Byte = _;
    //	花纹中心个数
    @Length(6)
    var bnRes_1: Array[Byte] = _;
    // to here is 32 bytes
    var WristDelta = new AFISCOREDELTASTRUCT;
    //	腕部三角
    @Length(20)
    var PatternDelta: Array[AFISCOREDELTASTRUCT] = _;
    //	花纹三角
    @Length(20)
    var PatternCore: Array[AFISCOREDELTASTRUCT] = _;
    //	花纹中心
    @Length(336)
    var bnRes_3: Array[Byte] = _;
  }

  // GAFIS_PALMSPECMNTSTRUCT; // structure is 1024 bytes

  class GAFIS_FINGERSPECMNTSTRUCT extends AncientData {
    //////////////////		指纹专有特征   /////////////////////////////
    var rp: Byte = _;
    //	纹型 0，1，2，3，4
    var vrp: Byte = _;
    //  副纹型 0，1，2，3，4
    var FingerIdx: Byte = _;
    //	捺印指纹指位
    @Length(10)
    var FingerCode: String = _;
    //	现场候选指位
    var RpCode: Int = _;
    //	现场候选纹型
    @Length(10 * 4)
    var fgrp: Array[Byte] = _;
    //	现场连指纹型
    var bEditedRP: Byte = _;
    //	纹型是否修改过
    @Length(6)
    var bnRes: Array[Byte] = _;
    //  MODIFY to here is 64 bytes
    var upcore = new AFISCOREDELTASTRUCT;
    //	指纹中心特征
    var lowcore = new AFISCOREDELTASTRUCT;
    //	指纹副中心特征
    var ldelta = new AFISCOREDELTASTRUCT;
    //	指纹左三角特征
    var rdelta = new AFISCOREDELTASTRUCT; //	指纹右三角特征
  }

  // GAFIS_FINGERSPECMNTSTRUCT; // size of this structure is 64+64=128 bytes


  /**
    * Added by beagle on Dec. 12, 2008
    * 增加汗眼特征数据信息
    */

  /*
class GAFIS_PorePointData extends AncientData
     {
  var pstPore_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstPore_Data:Array[GAFIS_POREPOINT] = _ // for pstPore pointer ,struct:GAFIS_POREPOINT;
  var nPoreCount:Int = _;
  var bCanBeFree:Int = _;
} //GAFIS_POREPOINTDATA;
     */

  class GAFIS_PFCOMMONMNTSTRUCT extends AncientData {
    //////////////////		指纹掌纹共有特征   /////////////////////////////
    var MntVersion: Byte = _;
    //	特征提取版本
    var qlev: Byte = _;
    //	捺印指纹掌纹图象整体质量
    var fca: Short = _;
    //	指纹掌纹方向
    var D_fca: Short = _;
    //	指纹掌纹方向范围

    var Distore: Byte = _;
    //  变形参数，0...未知，1...变形小，2...变形大

    var bUseQlev: Byte = _;
    //	细节特征是否评判质量 0...无，1...有
    var nMntCnt: Short = _;
    //	细节特征数目
    var nExactAreaCnt: Short = _;
    //	现场稳定区域个数
    var n2ptlc: Short = _;
    //	现场特征数线个数
    var nMntRegionCnt: Short = _;
    //	NEW 现场特征位置信息框个数
    @Length(1500)
    var mnt: Array[AFISMNTPOINTSTRUCT] = _;
    //	细节特征数据
    @Length(32)
    var exactarea: Array[AFISCIRCLESTRUCT] = _;
    //	现场稳定区域数据
    @Length(32)
    var m_st2ptlc: Array[LINECNT2MNTSTRUCT] = _;
    //	现场特征数线信息
    @Length(8)
    var stMntRegion: Array[AFISRECTSTRUCT] = _;
    //  NEW 8 rects, a minutia can belong one or more of them
    //	GAFIS_POREPOINTDATA	stPorePoint;	// 汗眼数据信息
    @Length(976)
    var bnRes: Array[Byte] = _; // - sizeof(GAFIS_POREPOINTDATA)];
  }

  // GAFIS_PFCOMMONMNTSTRUCT; // size is 25KB=1024*25 bytes

  class GAFIS_MNTINNERDATA extends AncientData {
    var pData_Ptr: Int = _
    //using 4 byte as pointer
    @IgnoreTransfer
    var pData_Data: Array[Byte] = _
    // for pData pointer ,struct:void;
    var nMntDataLen: Int = _;
    var bCanBeFree: Int = _;
  }

  // GAFIS_MNTINNERDATA;

  //A类：RpQlev>80 ImgQlev>85，B类：RpQlev>70 ImgQlev>75，C类：ImgQlev 50分以上，D类：ImgQlev 50分以下

  class FINGERFEATQLEV extends AncientData {
    var bUseQlev: Byte = _;
    //	是否使用特征质量判断，	1...使用，	0...不使用
    var bIsPlain: Byte = _
    //	是否平面指纹,			1...是，	0...不是
    var ImgQlev: Byte = _
    //	指纹图像质量，主要反映纹线清晰程度，0－100
    var RpQlev: Byte = _
    //	形态特征总体质量， 建议80分以下要干预
    var CoreQlev: Byte = _
    //  中心可靠程度，0－100以及255，255代表主纹型下该特征不存在，0代表该特征未被提取（副中心，三角类似）
    var VCoreQlev: Byte = _
    //	副中心可靠程度
    var LDeltaQlev: Byte = _
    //	左三角可靠程度
    var RDeltaQlev: Byte = _ //  右三角可靠程度
  }

  // FINGERFEATQLEV;

  class MNTDISPSTRUCT extends AncientData {
    //////////////////		基本数据       /////////////////////////////
    var nSize: Short = _;
    //	结构长度  ？？ 是否需要
    var nWidth: Short = _;
    //	图象宽度
    var nHeight: Short = _;
    //	高度
    var nResolution: Short = _;
    //	分辨率
    var bIsPalm: Byte = _;
    //  0...指纹，1...掌纹
    var bIsLatent: Byte = _;
    //  0...捺印，1...现场
    var nSignature: Short = _;
    // signature of the minutiae.
    var bEditedMinutia: Byte = _;
    //	捺印细节特征是否修改正确,1...修改正确，0...未修改或未知
    var nMntFormat: Byte = _;
    //	特征格式
    @Length(2)
    var bnRes_1: Array[Byte] = _;
    //	以下字段是否需要修改
    @Length(KEYIDLEN)
    var Bar: String = _;
    //	条码号
    var Type: Byte = _;
    var LibType: Byte = _;
    @Length(6)
    var bnRes_2: Array[Byte] = _;
    var LibID: Int = _;
    var LastEditTime: Long = _;
    @Length(KEYIDLEN)
    var PersonID: String = _;
    @Length(KEYIDLEN)
    var MatchBar: String = _;
    var BreakTime: Long = _;
    @Length(USERIDLEN)
    var BreakUserID: String = _;
    @Length(KEYIDLEN)
    var CaseID: String = _;
    @Length(KEYIDLEN)
    var CrimeBarCode: String = _;
    //	查中捺印指纹条码号
    var CrimeTenLibID: Int = _;
    //	查中捺印指纹库序号
    var CrimeFingerNum: Byte = _;
    //	查中捺印指纹指位
    var Breakdbid: Byte = _;
    var BreakedFlag: Byte = _;
    @Length(5)
    var bnRes_3: Array[Byte] = _;
    var GroupNo: Int = _;
    //////////////////////////////////////
    var nMaxMnt: Short = _;
    // 最大特征数
    var nMaxEA: Short = _;
    // 最大稳定区域个数
    var nMax2PtLC: Short = _;
    // 最大特征数线个数
    var nMaxMntRegion: Short = _;
    // NEW 最大现场特征位置信息框个数
    @Length(8)
    var bnRes_4: Array[Byte] = _;
    // MODIFY

    var stPm = new GAFIS_PALMSPECMNTSTRUCT;
    var stFg = new GAFIS_FINGERSPECMNTSTRUCT;
    var stCm = new GAFIS_PFCOMMONMNTSTRUCT;
    // common mnt
    @Length(128)
    var stdReserve: String = _;
    // 内部数据，不允许更改
    var stInnerData = new GAFIS_MNTINNERDATA;

    var FeatQlev = new FINGERFEATQLEV;
    //	8字节

    @Length(1520)
    var bnRes_5: Array[Byte] = _; // reserved.
  } // MNTDISPSTRUCT; // total size is very large, 28KB=28*1024.

  final val MNTDIFF_FEAT_NOUSE = 0
  final val MNTDIFF_FEAT_RIGHT = 1
  final val MNTDIFF_FEAT_OMIT = 2
  final val MNTDIFF_FEAT_ERROR = 3
  final val MNTDIFF_FEAT_FAKE = 4

  //  错误类型
  //  0...不存在该类特征, 1...存在并且正确，2...该类特征遗漏，3...该类特征错误

  class MNTDIFF extends AncientData {
    var rp: Byte = _
    //	纹型	MNTDIFF_FEAT_***
    var upcore: Byte = _
    //	上中心	MNTDIFF_FEAT_***
    var lowcore: Byte = _
    //	副中心	MNTDIFF_FEAT_***
    var ldelta: Byte = _
    //	左三角	MNTDIFF_FEAT_***
    var rdelta: Byte = _
    //	右三角	MNTDIFF_FEAT_***
    @Length(11)
    var nres1: Array[Byte] = _;
    var cm: Short = _;
    //	标准特征的细节特征数目
    var omitmnt: Short = _;
    //	遗漏特征数目
    var errmmt: Short = _;
    //	错误(偏)特征数目
    var fakemmt: Short = _;
    //	伪特征数目
    @Length(8)
    var nres2: Array[Byte] = _;
  } // MNTDIFF;

  def FPT03RecToMntDisp: Unit ={

  }
}

