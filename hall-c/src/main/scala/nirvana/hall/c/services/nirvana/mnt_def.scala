package nirvana.hall.c.services.nirvana

import nirvana.hall.c.annotations.Length
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-11
 */
object mnt_def {

    final val FTPMNTSIZE = 180				//不能动
    final val FLPMNTSIZE = 80				//不能动

    final val PTPMNTSIZE = 1200
    final val PLPMNTSIZE = 512


    final val BLKMNTSIZE = 16

    final val LINECOUNTSIZE = 24
    final val MNTPOSSIZE = 8

    final val PALMDELTASIZE = 20
    final val PALMPATCORESIZE = 16

    final val MAXSGPOINTCNT_FINGER = 5
    final val MAXSGPOINTCNT_PALM = 20




    final val MAXMINUTIAQLEV = 100
    final val MAXMINUTIAQLEV2 = 50


    final val WINOFBLOCKDRT = 30			//	块窗口大小,被缩放系数2.5整除

    final val MAX_BLOCKDRT = 16			//	分块方向的最大块数

    final val MNTPART_FINGER_UPPER = 1
    final val MNTPART_FINGER_LOWER = 2
    final val MNTPART_FINGER_LEFT = 3
    final val MNTPART_FINGER_RIGHT = 4

    final val MNTPART_FINGER_UPPER_BITS = 1
    final val MNTPART_FINGER_LOWER_BITS = 2
    final val MNTPART_FINGER_LEFT_BITS = 4
    final val MNTPART_FINGER_RIGHT_BITS = 8

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


    final val MNTPART_PALM_FP_BITS = 0x10
    final val MNTPART_PALM_IP_BITS = 0x100
    final val MNTPART_PALM_OP_BITS = 0x1000

    //	指根区的小分区
    final val MNTPART_PALM_FP1_BITS = 0x1
    final val MNTPART_PALM_FP2_BITS = 0x2
    final val MNTPART_PALM_FP3_BITS = 0x4
    final val MNTPART_PALM_FP4_BITS = 0x8
    //	内侧区的小分区
    final val MNTPART_PALM_IPUP_BITS = 0x20
    final val MNTPART_PALM_IPMD_BITS = 0x40
    final val MNTPART_PALM_IPLW_BITS = 0x80
    //	外侧区的小分区
    final val MNTPART_PALM_OPUP_BITS = 0x200
    final val MNTPART_PALM_OPMD_BITS = 0x400
    final val MNTPART_PALM_OPLW_BITS = 0x800
    /*
    final val MNTPART_PALM_FP_BITS = 1
    final val MNTPART_PALM_IP_BITS = 2
    final val MNTPART_PALM_OP_BITS = 4

    final val MNTPART_PALM_SUBAREA1_BITS = 16
    final val MNTPART_PALM_SUBAREA2_BITS = 32
    final val MNTPART_PALM_SUBAREA3_BITS = 64
    final val MNTPART_PALM_SUBAREA4_BITS = 128
    */
    final val DELTACLASS_UNKNOWN = 0
    final val DELTACLASS_WRIST = 1
    final val DELTACLASS_FINGROOT = 2
    final val DELTACLASS_PATTERN = 3	// pattern

    //脊线类型
    final val RIDGE_NOUSE = 0	//	不使用细节特征类型
    final val RIDGE_UNKNOWN = 1	//	使用特征类型,脊线类型未知
    final val RIDGE_BLACK = 2	//	脊线为黑线
    final val RIDGE_WHITE = 3	//	脊线为白线
    //细节特征点类型
    final val MTYPE_UNKNOWN = 0	//	未知
    final val MTYPE_ENDPOINT = 1	//	端点
    final val MTYPE_BIFPOINT = 2	//	分叉点

    class PALMMORHOFEATURE extends AncientData {
    var pcx:Short = _ ;
    var pcy:Short = _ ;
    var pca:Byte = _ ; //	0...180,(cz+90)/2,cz为实际的方向(-90,270)
    var pcr:Byte = _ ; //	位置半径
    var pda:Byte = _ ; //	方向范围
    var res:Byte = _ ;
    } // PALMMORHOFEATURE;

    class FEATQLEV extends AncientData  {
    //A类：RpQlev>80 ImgQlev>85，B类：RpQlev>70 ImgQlev>75，C类：ImgQlev 50分以上，D类：ImgQlev 50分以下
    var bUseQlev:Byte = _ ; //	是否使用特征质量判断，	1...使用，	0...不使用
    var bIsPlain:Byte = _ ; //	是否平面指纹,			1...是，	0...不是
    var ImgQlev:Byte = _ ; //	指纹图像质量，主要反映纹线清晰程度，0－100
    var RpQlev:Byte = _ ; //	形态特征总体质量， 建议80分以下要干预
    var CoreQlev:Byte = _ ; //  中心可靠程度，0－100以及255，255代表主纹型下该特征不存在，0代表该特征未被提取（副中心，三角类似）
    var VCoreQlev:Byte = _ ; //	副中心可靠程度
    var LDeltaQlev:Byte = _ ; //	左三角可靠程度
    var RDeltaQlev:Byte = _ ; //  右三角可靠程度
    } // FEATQLEV; //同mnt2disp.h中FINGERFEATQLEV

    class SGPOINT extends AncientData  {
    var x:Byte = _ ;
    var y:Byte = _ ;
    var z:Byte = _ ; //	奇异点方向
    var TypeQlev:Byte = _ ; //	最高位代表类型，0...中心型，1...三角型,  低7位代表质量0－100
    } // SGPOINT;

    class BLOCKDRT extends AncientData  {
    var x:Byte = _ ;
    var y:Byte = _ ;
    var z:Byte = _ ; //	方向－90－90,高位为1表示质量可靠
    } // BLOCKDRT; //	32*32块的方向

    final val _TT_ACCELERATE = 1

    class DELTADRT extends AncientData  {
    var x:Byte = _ ;
    var y:Byte = _ ; //	三角的位置
    @Length(3)
    var z:Array[Byte] = _ ; //	三角三个方向，朝上的为第一个，左边第二，朝右第三，垂直向下为0度，逆时针方向，-90-269
    } // DELTADRT;

    final val MAXSIZEOFCSL = 9

    class CORESTREAMLINE extends AncientData  {
    var x:Byte = _ ;
    var y:Byte = _ ;
    var z:Byte = _ ;
    var dw:Byte = _ ;
    @Length(MAXSIZEOFCSL)
    var off:Array[Byte] = _ ; //	纹线方向与中心方向夹角为（i+1）*dw时的距离（沿中心方向的延长线），0表示结束
    } // CORESTREAMLINE;

    class FINGERMNTSTRUCT extends AncientData {
    var bePalm:Byte = _ ; //
    var beLatent:Byte = _ ; //
    var resolution:Short = _ ; //	采集密度，缺省值为500	//
    var nWidth:Short = _ ; //	图象宽度				//
    var nHeight:Short = _ ; //	图象高度				//
    var offsetX:Short = _ ; //	特征提取偏移位置（相对坐标原点,nres下的偏移量）
    var offsetY:Short = _ ;
    var nFingerIndex:Byte = _ ; //	指位1...10
    var nres:Short = _ ; //	存储密度，缺省值为200   SCALE=resolution/nres
    var MntVersion:Byte = _ ; //	特征提取方法(以前版本为0)
    var qlev:Byte = _ ; //	指纹质量：0 到 100
    var rp:Byte = _ ; /*  指纹纹型
										0.....残缺或不确定或有歧义
										1.....弓形
										2.....左箕
										3.....右箕
										4.....斗
									*/
    var vrp:Byte = _ ; //	副纹型，定义同纹型

    var fca:Byte = _ ; //	指纹方向，定义同中心方向
    var D_fca:Byte = _ ; //	指纹方向角度变化范围,定义同中心方向范围，实际值2*D_fca
    //	有指纹方向时，D_fca值在8和45之间
    //	当D_fca==0代表不用指纹方向

    var cx:Byte = _ ; //	cx : 中心实际位置为(SCALE*cx,SCALE*cy)
    var cy:Byte = _ ;
    var ca:Byte = _ ; //	ca : 中心实际方向为 2*ca-90, 在[-90,90]区间内
    //	垂直向下为 0，水平向右为 90，水平向左为-90
    var D_ca:Byte = _ ;
    var D_cr:Byte = _ ;

    var ex:Byte = _ ; //	下中心实际位置为(SCALE*ex,SCALE*ey)
    var ey:Byte = _ ;
    var D_er:Byte = _ ;
    var ldx:Byte = _ ; //	左三角实际位置为(SCALE*ldx,SCALE*ldy)
    var ldy:Byte = _ ;
    var D_lr:Byte = _ ;

    var rdx:Byte = _ ; //	右三角实际位置为(SCALE*rdx,SCALE*rdy)
    var rdy:Byte = _ ;
    var D_rr:Byte = _ ;

    var cm:Byte = _ ; //	细节特征数	0---180 */
    //以上36字节
    @Length(FTPMNTSIZE)
    var xx:Array[Byte] = _ ; //	细节特征实际位置为(SCALE*xx,SCALE*yy) */	//
    @Length(FTPMNTSIZE)
    var yy:Array[Byte] = _ ; //
    @Length(FTPMNTSIZE)
    var zz:Array[Byte] = _ ; //  细节特征实际方向为 2*zz-90, 在[-90,270)区间内//
    //	垂直向下为 0，水平向右为 90，水平向左为-90

    var mqlevON:Byte = _ ; //	使用细节特征评判 1...使用，0...不使用
  @Length(24)
  var mqlev:Array[Byte] = _ ; //	细节特征质量，按位设置，1代表可靠，0代表不可靠
  var mdate:Short = _ ; //	生成或变更时间，转换成短整型 YY*32*32+MM*32+DD
  var nSignature:Short = _ ; //	图象标签，用来判定图象特征一致性
  var bEditedMinutia:Byte = _ ; //	捺印细节特征是否修改正确
  var bEditedMorpho:Byte = _ ; //	捺印形态特征是否修改正确
  var FeatQlev = new FEATQLEV

      //@Length(640 - 67 - 3 * FTPMNTSIZE - sizeof (FEATQLEV))
      @Length(640 - 67 - 3 * FTPMNTSIZE - 8)
  var MntFill:Array[Byte] = _  //	剩余25字节
  } // FINGERMNTSTRUCT;

  class FINGERFEATURE extends AncientData {
  var bePalm:Byte = _ ; //
  var beLatent:Byte = _ ; //
  var resolution:Short = _ ; //	采集密度，缺省值为500	//
  var nWidth:Short = _ ; //	图象宽度				//
  var nHeight:Short = _ ; //	图象高度				//
  var offsetX:Short = _ ; //	特征提取偏移位置（相对坐标原点,nres下的偏移量）
  var offsetY:Short = _ ;
  var nFingerIndex:Byte = _ ; //	1...10
  var nres:Short = _ ; //	存储密度，缺省值为200   SCALE=resolution/nres
  var MntVersion:Byte = _ ; //	特征提取方法(以前版本为0)
  var qlev:Byte = _ ; //	指纹质量：0 到 100
  var rp:Byte = _ ; /*  指纹纹型
										0.....残缺或不确定或有歧义
										1.....弓形
										2.....左箕
										3.....右箕
										4.....斗
									*/
  var vrp:Byte = _ ; //	副纹型，定义同纹型

  var fca:Byte = _ ; //	指纹方向，定义同中心方向
  var D_fca:Byte = _ ; //	指纹方向角度变化范围,定义同中心方向范围，
  //	有指纹方向时，D_fca值在8和45之间
  //	当D_fca==0代表不用指纹方向

  var cx:Byte = _ ; //	cx : 中心实际位置为(SCALE*cx,SCALE*cy)
  var cy:Byte = _ ;
  var ca:Byte = _ ; //	ca : 中心实际方向为 2*ca-90, 在[-90,90]区间内
  //	垂直向下为 0，水平向右为 90，水平向左为-90
  var D_ca:Byte = _ ;
  var D_cr:Byte = _ ;

  var ex:Byte = _ ; //	下中心实际位置为(SCALE*ex,SCALE*ey)
  var ey:Byte = _ ;
  var D_er:Byte = _ ;
  var ldx:Byte = _ ; //	左三角实际位置为(SCALE*ldx,SCALE*ldy)
  var ldy:Byte = _ ;
  var D_lr:Byte = _ ;

  var rdx:Byte = _ ; //	右三角实际位置为(SCALE*rdx,SCALE*rdy)
  var rdy:Byte = _ ;
  var D_rr:Byte = _ ;
  var bEditedMinutia:Byte = _ ; //	捺印细节特征是否修改正确
  var bEditedMorpho:Byte = _ ; //	捺印形态特征是否修改正确(按位来表示,1..纹型,2..中心,4..副中心,8..左三角,16..右三角)
  var FeatQlev = new FEATQLEV;
    @Length(11 - 8)
  var MntFill:Array[Byte] = _
  } // FINGERFEATURE;

  class FINGERLATMNTSTRUCT extends AncientData {
  var bePalm:Byte = _ ; //
  var beLatent:Byte = _ ; //
  var resolution:Short = _ ; //	采集密度，缺省值为500
  var nWidth:Short = _ ; //	图象宽度
  var nHeight:Short = _ ; //	图象高度
  var offsetX:Short = _ ; //	特征提取偏移位置（相对坐标原点,nres下的偏移量）
  var offsetY:Short = _ ;
  var nres:Short = _ ; //	存储密度，缺省值为200   SCALE=dpi/resolution
  var MntVersion:Byte = _ ; //	特征提取方法(以前版本为0)

  var FingerCode:Short = _ ; //	候选指位（按位计算）
  var RpCode:Byte = _ ; //	候选纹型（按位计算）
  @Length(10)
  var fgrp:Array[Byte] = _ ; //	联指纹型（按位计算）
  var Distore:Byte = _ ; /*  变形参数
										0.......未知
										1.......变形小
										2.......变形大
									*/

  var fca:Byte = _ ; //指纹方向，定义同中心方向
  var D_fca:Byte = _ ; //指纹方向角度变化范围
  var cfl:Byte = _ ; /*	左三角置信度
										1...估计三角
										2...虚拟三角
										0...无三角
									*/
  var cfr:Byte = _ ; /*	右三角置信度
										1...估计三角
										2...虚拟三角
										0...无三角
									*/
  var cx:Byte = _ ;
  var cy:Byte = _ ;
  var ca:Byte = _ ;
  var D_ca:Byte = _ ; //　2*D_ca 为估计中心角度变化范围　
  var D_cr:Byte = _ ; //	SCALE*D_cr 为估计中心区域半径
  var ex:Byte = _ ;
  var ey:Byte = _ ;
  var D_er:Byte = _ ; //	SCALE*D_er 为估计下中心区域半径
  var ldx:Byte = _ ;
  var ldy:Byte = _ ;
  var D_lr:Byte = _ ; //	SCALE*D_lr 为估计左三角区域半径
  var rdx:Byte = _ ;
  var rdy:Byte = _ ;
  var D_rr:Byte = _ ; //	SCALE*D_rr 为估计右三角区域半径
  var cm:Byte = _ ; //
  @Length(FLPMNTSIZE)
  var xx:Array[Byte] = _ ; //
  @Length(FLPMNTSIZE)
  var yy:Array[Byte] = _ ; //
  @Length(FLPMNTSIZE)
  var zz:Array[Byte] = _ ;
  @Length(FLPMNTSIZE)
  var mpos:Array[Byte] = _ ; //	特征位置，按位设置
  //			mpos|0...上部，1...下部，2...左部，3...右部
  var mqlevON:Byte = _ ; //	使用细节特征评判 1...使用，0...不使用
    @Length(FLPMNTSIZE / 8)
  var mqlev:Array[Byte] = _ ; //	细节特征质量，按位设置，1代表可靠，0代表不可靠

  /*	确定区域个数(bm)
          确定区域定义为：指纹纹线清晰，标点特征可靠，
          没有遗漏特征和伪特征
          确定区域包含老版本中空白区，即无细节特征的确定区域
   */
  var bm:Byte = _ ;
  @Length(BLKMNTSIZE)
  var bx:Array[Byte] = _ ;
  @Length(BLKMNTSIZE)
  var by:Array[Byte] = _ ;
  @Length(BLKMNTSIZE)
  var br:Array[Byte] = _ ;
  /*　结束	*/
  var lm:Byte = _ ; //数线点对个数				//
  @Length(LINECOUNTSIZE)
  var lnum0:Array[Byte] = _ ; //数线点对的特征标号		//
  @Length(LINECOUNTSIZE)
  var lnum1:Array[Byte] = _ ; //数线点对的特征标号		//
  @Length(LINECOUNTSIZE)
  var lcount:Array[Byte] = _ ; //线数						//
  //  特征区域设置框信息
  var mpm:Byte = _ ;
  @Length(MNTPOSSIZE)
  var mptop:Array[Byte] = _ ;
  @Length(MNTPOSSIZE)
  var mpbottom:Array[Byte] = _ ;
  @Length(MNTPOSSIZE)
  var mpleft:Array[Byte] = _ ;
  @Length(MNTPOSSIZE)
  var mpright:Array[Byte] = _ ;
  @Length(MNTPOSSIZE)
  var mptype:Array[Byte] = _ ; //	设置框代表的区域编码
  //	0...上部，1...下部，2...左部，3...右部
  var mdate:Short = _ ; //	生成或变更时间，转换成短整型 YY*32*32+MM*32+DD
  var nSignature:Short = _ ; //	图象标签，用来判定图象特征一致性
  var ImgQlev:Byte = _ ; //	现场指纹质量
    @Length(640 - 67 - 3 * LINECOUNTSIZE - 4 * FLPMNTSIZE - 3 * BLKMNTSIZE - 5 * MNTPOSSIZE)
  var LatMntFill:Array[Byte] = _

  } // FINGERLATMNTSTRUCT;

  class PALMMNTSTRUCT extends AncientData {
  var bePalm:Byte = _ ; //
  var beLatent:Byte = _ ; //
  var resolution:Short = _ ; //	采集密度，缺省值为500	//
  var nWidth:Short = _ ; //	图象宽度				//
  var nHeight:Short = _ ; //	图象高度				//
  var offsetX:Short = _ ; //	特征提取偏移位置（相对原图坐标原点）
  var offsetY:Short = _ ;
  var nPalmIndex:Byte = _ ; //	掌位，0：未知，1：右掌，2：左掌
  var nres:Short = _ ; //	存储密度，缺省值为200   SCALE=resolution/nres
  var MntVersion:Byte = _ ; //	特征提取方法(以前版本为0)
  var qlev:Byte = _ ; //	掌纹质量：0 到 100
  var basepos:Short = _ ; //	基线位置
  var wiptx:Short = _ ; //	第一曲肌褶纹内侧位置
  var wipty:Short = _ ;
  var woptx:Short = _ ; // 	第一曲肌褶纹外侧位置
  var wopty:Short = _ ;

  var fca:Byte = _ ; //	掌纹方向，定义同中心方向
  var D_fca:Byte = _ ; //	掌纹方向角度变化范围,定义同中心方向范围，
  //	有掌纹方向时，D_fca值在8和45之间
  //	当D_fca==0代表不用掌纹方向
  /*
var FPTrp:Byte = _ ;			//	指根区纹型信息0：未知,1：存在,2：不存在
var IPTrp:Byte = _ ;			//	内侧区纹型信息0：未知,1：存在,2：不存在
var OPTrp:Byte = _ ;			//	外侧区纹型信息0：未知,1：存在,2：不存在   //30字节
   */
  @Length(3)
  var nres1:Array[Byte] = _ ; //	删掉分区纹型信息
  var nDeltaCnt:Byte = _ ; //	三角个数

  @Length(PALMDELTASIZE * 2)
  var dx:Array[Byte] = _ ;
  @Length(PALMDELTASIZE * 2)
  var dy:Array[Byte] = _ ;
  @Length(PALMDELTASIZE)
  var dz:Array[Byte] = _ ; //	0...180,(cz+90)/2,cz为实际的方向(-90,270)
  @Length(PALMDELTASIZE)
  var dr:Array[Byte] = _ ; //	位置半径
  @Length(PALMDELTASIZE)
  var D_dz:Array[Byte] = _ ; //	方向范围
  @Length(PALMDELTASIZE)
  var dtype:Array[Byte] = _ ; //	三角类型（腕部、指根或花纹）
  //	UCHAR	dfpt[PALMDELTASIZE];		//	指根三角（按位表示，从右到左0...食指，3...小指）
  @Length(PALMDELTASIZE)
  var nres2:Array[Byte] = _ ; //	删掉指根三角信息

  var nCoreCnt:Byte = _ ; //	花纹中心个数

  @Length(PALMPATCORESIZE * 2)
  var cx:Array[Byte] = _ ;
  @Length(PALMPATCORESIZE * 2)
  var cy:Array[Byte] = _ ;
  @Length(PALMPATCORESIZE)
  var cz:Array[Byte] = _ ; //	0...180,(cz+90)/2,cz为实际的方向(-90,270)
  @Length(PALMPATCORESIZE)
  var cr:Array[Byte] = _ ; //	位置半径
  @Length(PALMPATCORESIZE)
  var D_cz:Array[Byte] = _ ; //	方向范围

  var cm:Short = _ ; //	细节特征数	0---150 */						//
  @Length(PTPMNTSIZE * 2)
  var xx:Array[Byte]= _ ; //	细节特征实际位置为(SCALE*xx,SCALE*yy) */	//
  @Length(PTPMNTSIZE * 2)
  var yy:Array[Byte]= _ ; //
  @Length(PTPMNTSIZE)
  var zz:Array[Byte] = _ ; //  细节特征实际方向为 2*zz-90, 在[-90,270)区间内//
  //	垂直向下为 0，水平向右为 90，水平向左为-90
  var mqlevON:Byte = _ ; //	使用细节特征评判 1...使用，0...不使用
    @Length((PTPMNTSIZE + 4) / 8)
  var mqlev:Byte = _ ; //	细节特征质量，按位设置，1代表可靠，0代表不可靠
  var mdate:Short = _ ; //	生成或变更时间，转换成短整型 YY*32*32+MM*32+DD
  var nSignature:Short = _ ; //	图象标签，用来判定图象特征一致性
  var bEditedMinutia:Byte = _ ; //	捺印细节特征是否修改正确
  var bEditedMorpho:Byte = _ ; //	捺印形态特征是否修改正确
    @Length(8192 - 43 - 9 * PALMDELTASIZE - PALMPATCORESIZE * 7 - 5 * PTPMNTSIZE - (PTPMNTSIZE + 4) / 8)
  var MntFill:Array[Byte] = _
  } // PALMMNTSTRUCT;

  class PALMLATMNTSTRUCT extends AncientData {
  var bePalm:Byte = _ ; //
  var beLatent:Byte = _ ; //
  var resolution:Short = _ ; //	采集密度，缺省值为500	//
  var nWidth:Short = _ ; //	图象宽度				//
  var nHeight:Short = _ ; //	图象高度				//
  var offsetX:Short = _ ; //	特征提取偏移位置（相对原图坐标原点）
  var offsetY:Short = _ ;
  var nPalmIndex:Byte = _ ; //	掌位，0：未知，1：右掌，2：左掌
  var nres:Short = _ ; //	存储密度，缺省值为200   SCALE=resolution/nres
  var MntVersion:Byte = _ ; //	特征提取方法(以前版本为0)
  var Distore:Byte = _ ; //  变形参数

  var basepos:Short = _ ; //	基线位置
  var woptx:Short = _ ; // 	第一曲肌褶纹外侧位置
  var wopty:Short = _ ;
  var woptz:Byte = _ ; //	第一曲肌褶纹的方向（指向手心方向）

  var fca:Byte = _ ; //	掌纹方向，定义同中心方向
  var D_fca:Byte = _ ; //	掌纹方向角度变化范围,定义同中心方向范围，
  //	有掌纹方向时，D_fca值在8和45之间（实际值为15--90）
  //	当D_fca==0代表不用掌纹方向
  /*
var FPTrp:Byte = _ ;			//	指根区纹型信息0：未知,1：存在,2：不存在
var IPTrp:Byte = _ ;			//	内侧区纹型信息0：未知,1：存在,2：不存在
var OPTrp:Byte = _ ;			//	外侧区纹型信息0：未知,1：存在,2：不存在   //30字节
   */
  @Length(3)
  var nres1:Array[Byte] = _ ;
  var nDeltaCnt:Byte = _ ; //	三角个数

  @Length(PALMDELTASIZE * 2)
  var dx:Array[Byte] = _ ;
  @Length(PALMDELTASIZE * 2)
  var dy:Array[Byte] = _ ;
  @Length(PALMDELTASIZE)
  var dz:Array[Byte] = _ ; //	0...180,(cz+90)/2,cz为实际的方向(-90,270)
  @Length(PALMDELTASIZE)
  var dr:Array[Byte] = _ ; //	位置半径
  @Length(PALMDELTASIZE)
  var D_dz:Array[Byte] = _ ; //	方向范围
  @Length(PALMDELTASIZE)
  var dtype:Array[Byte] = _ ; //	三角类型（腕部、指根或花纹）
  //	UCHAR	dfpt[PALMDELTASIZE];		//	指根三角（按位表示，从右到左0...食指，3...小指）
  @Length(20)
  var nres2:Array[Byte] = _ ;

  var nCoreCnt:Byte = _ ; //	花纹中心个数

  @Length(PALMPATCORESIZE * 2)
  var cx:Array[Byte] = _ ;
  @Length(PALMPATCORESIZE * 2)
  var cy:Array[Byte] = _ ;
  @Length(PALMPATCORESIZE)
  var cz:Array[Byte] = _ ; //	0...180,(cz+90)/2,cz为实际的方向(-90,270)
  @Length(PALMPATCORESIZE)
  var cr:Array[Byte] = _ ; //	位置半径
  @Length(PALMPATCORESIZE)
  var D_cz:Array[Byte] = _ ; //	方向范围
  var cm:Short = _ ; //	细节特征数	0---150 */						//
  @Length(PLPMNTSIZE * 2)
  var xx:Array[Byte] = _ ; //	细节特征实际位置为(SCALE*xx,SCALE*yy) */	//
  @Length(PLPMNTSIZE * 2)
  var yy:Array[Byte] = _ ; //
  @Length(PLPMNTSIZE)
  var zz:Array[Byte] = _ ; //  细节特征实际方向为 2*zz-90, 在[-90,270)区间内//
  //	垂直向下为 0，水平向右为 90，水平向左为-90
  @Length(PLPMNTSIZE)
  var mpos:Array[Byte] = _ ; //	按位表示 指根区--128，内侧区--64 外侧区--32
  var mqlevON:Byte = _ ; //	使用细节特征评判 1...使用，0...不使用
    @Length((PLPMNTSIZE + 4) / 8)
  var mqlev:Array[Byte] = _ ; //	细节特征质量，按位设置，1代表可靠，0代表不可靠
  var bm:Byte = _ ;
  @Length(BLKMNTSIZE * 2)
  var bx:Array[Byte]= _ ;
  @Length(BLKMNTSIZE * 2)
  var by:Array[Byte]= _ ;
  @Length(BLKMNTSIZE)
  var br:Array[Byte]= _ ;
  /*　结束	*/
  var lm:Byte = _ ; //数线点对个数				//
  @Length(LINECOUNTSIZE * 2)
  var lnum0:Array[Byte]= _ ; //数线点对的特征标号		//
  @Length(LINECOUNTSIZE * 2)
  var lnum1:Array[Byte]= _ ; //数线点对的特征标号		//
  @Length(LINECOUNTSIZE)
  var lcount:Array[Byte]= _ ; //线数						//
  //  特征区域设置框信息
  var mpm:Byte = _ ;
  @Length(MNTPOSSIZE * 2)
  var mptop:Array[Byte] = _ ;
  @Length(MNTPOSSIZE * 2)
  var mpbottom:Array[Byte]= _ ;
  @Length(MNTPOSSIZE * 2)
  var mpleft:Array[Byte]= _ ;
  @Length(MNTPOSSIZE * 2)
  var mpright:Array[Byte]= _ ;
  @Length(MNTPOSSIZE)
  var mptype:Array[Byte]= _ ; //	设置框代表的区域编码
  //	0...上部，1...下部，2...左部，3...右部
  var mdate:Short = _ ; //	生成或变更时间，转换成短整型 YY*32*32+MM*32+DD
  var nSignature:Short = _ ; //	图象标签，用来判定图象特征一致性
    @Length(5120 - 41 - PALMDELTASIZE * 9 - PALMPATCORESIZE * 7 - 6 * PLPMNTSIZE - (PLPMNTSIZE + 4) / 8 - 5 * BLKMNTSIZE - 5 * LINECOUNTSIZE - 9 * MNTPOSSIZE)
  var MntFill:Byte = _
  } // PALMLATMNTSTRUCT;



  class FINGERMNTSTRUCT50 extends AncientData {
  var resolution:Short = _ ; //	采集密度，缺省值为500	//
  var nWidth:Short = _ ; //	图象宽度				//
  var nHeight:Short = _ ; //	图象高度				//
  var offsetX:Short = _ ; //	特征提取偏移位置（相对原图坐标原点）
  var offsetY:Short = _ ;
  var nFingerIndex:Byte = _ ; //	指位1...10
  var nres:Short = _ ; //	存储密度，缺省值为200   SCALE=resolution/nres
  var MntVersion:Byte = _ ; //	特征提取方法(以前版本为0)
  var qlev:Byte = _ ; //	指纹质量：0 到 100
  var rp:Byte = _ ; /*  指纹纹型
										0.....残缺或不确定或有歧义
										1.....弓形
										2.....左箕
										3.....右箕
										4.....斗
									*/
  var vrp:Byte = _ ; //	副纹型，定义同纹型

  var fca:Byte = _ ; //	指纹方向，定义同中心方向
  var D_fca:Byte = _ ; //	指纹方向角度变化范围,定义同中心方向范围，实际值2*D_fca
  //	有指纹方向时，D_fca值在8和45之间
  //	当D_fca==0代表不用指纹方向

  var cx:Byte = _ ; //	cx : 中心实际位置为(SCALE*cx,SCALE*cy)
  var cy:Byte = _ ;
  var ca:Byte = _ ; //	ca : 中心实际方向为 2*ca-90, 在[-90,90]区间内
  //	垂直向下为 0，水平向右为 90，水平向左为-90
  var D_ca:Byte = _ ;
  var D_cr:Byte = _ ;

  var ex:Byte = _ ; //	下中心实际位置为(SCALE*ex,SCALE*ey)
  var ey:Byte = _ ;
  var D_er:Byte = _ ;
  var ldx:Byte = _ ; //	左三角实际位置为(SCALE*ldx,SCALE*ldy)
  var ldy:Byte = _ ;
  var D_lr:Byte = _ ;

  var rdx:Byte = _ ; //	右三角实际位置为(SCALE*rdx,SCALE*rdy)
  var rdy:Byte = _ ;
  var D_rr:Byte = _ ;

  var cm:Byte = _ ; //	细节特征数	0---150 */						//
  @Length(FTPMNTSIZE)
  var xx:Array[Byte]= _ ; //	细节特征实际位置为(SCALE*xx,SCALE*yy) */	//
  @Length(FTPMNTSIZE)
  var yy:Array[Byte]= _ ; //
  @Length(FTPMNTSIZE)
  var zz:Array[Byte]= _ ; //  细节特征实际方向为 2*zz-90, 在[-90,270)区间内//
  //	垂直向下为 0，水平向右为 90，水平向左为-90

  var mqlevON:Byte = _ ; //	使用细节特征评判 1...使用，0...不使用
  @Length(24)
  var mqlev:Array[Byte] = _ ; //	细节特征质量，按位设置，1代表可靠，0代表不可靠
  var mdate:Short = _ ; //	生成或变更时间，转换成短整型 YY*32*32+MM*32+DD
  var nSignature:Short = _ ; //	图象标签，用来判定图象特征一致性
    @Length(640 - 63 - 3 * FTPMNTSIZE)
  var MntFill:Array[Byte] = _ ; //	59-->63
  } // FINGERMNTSTRUCT50;

  class FINGERFEATURE50 extends AncientData {
  var resolution:Short = _ ; //	采集密度，缺省值为500	//
  var nWidth:Short = _ ; //	图象宽度				//
  var nHeight:Short = _ ; //	图象高度				//
  var offsetX:Short = _ ; //	特征提取偏移位置（相对原图坐标原点）
  var offsetY:Short = _ ;
  var nFingerIndex:Byte = _ ; //	1...10
  var nres:Short = _ ; //	存储密度，缺省值为200   SCALE=resolution/nres
  var MntVersion:Byte = _ ; //	特征提取方法(以前版本为0)
  var qlev:Byte = _ ; //	指纹质量：0 到 100
  var rp:Byte = _ ; /*  指纹纹型
										0.....残缺或不确定或有歧义
										1.....弓形
										2.....左箕
										3.....右箕
										4.....斗
									*/
  var vrp:Byte = _ ; //	副纹型，定义同纹型

  var fca:Byte = _ ; //	指纹方向，定义同中心方向
  var D_fca:Byte = _ ; //	指纹方向角度变化范围,定义同中心方向范围，
  //	有指纹方向时，D_fca值在8和45之间
  //	当D_fca==0代表不用指纹方向

  var cx:Byte = _ ; //	cx : 中心实际位置为(SCALE*cx,SCALE*cy)
  var cy:Byte = _ ;
  var ca:Byte = _ ; //	ca : 中心实际方向为 2*ca-90, 在[-90,90]区间内
  //	垂直向下为 0，水平向右为 90，水平向左为-90
  var D_ca:Byte = _ ;
  var D_cr:Byte = _ ;

  var ex:Byte = _ ; //	下中心实际位置为(SCALE*ex,SCALE*ey)
  var ey:Byte = _ ;
  var D_er:Byte = _ ;
  var ldx:Byte = _ ; //	左三角实际位置为(SCALE*ldx,SCALE*ldy)
  var ldy:Byte = _ ;
  var D_lr:Byte = _ ;

  var rdx:Byte = _ ; //	右三角实际位置为(SCALE*rdx,SCALE*rdy)
  var rdy:Byte = _ ;
  var D_rr:Byte = _ ;

  @Length(15)
  var MntFill:Array[Byte]= _ ;
  } // FINGERFEATURE50;

  class FINGERLATMNTSTRUCT50 extends AncientData {
  var resolution:Short = _ ; //	采集密度，缺省值为500
  var nWidth:Short = _ ; //	图象宽度
  var nHeight:Short = _ ; //	图象高度
  var offsetX:Short = _ ; //	特征提取偏移位置（相对原图坐标原点）
  var offsetY:Short = _ ;
  var nres:Short = _ ; //	存储密度，缺省值为200   SCALE=dpi/resolution
  var MntVersion:Byte = _ ; //	特征提取方法(以前版本为0)

  var FingerCode:Short = _ ; //	候选指位（按位计算）
  var RpCode:Byte = _ ; //	候选纹型（按位计算）
  @Length(10)
  var fgrp:Array[Byte] = _ ; //	联指纹型（按位计算）
  var Distore:Byte = _ ; /*  变形参数
										0.......未知
										1.......变形小
										2.......变形大
									*/

  var fca:Byte = _ ; //指纹方向，定义同中心方向
  var D_fca:Byte = _ ; //指纹方向角度变化范围
  var cfl:Byte = _ ; /*	左三角置信度
										1...估计三角
										2...虚拟三角
										0...无三角
									*/
  var cfr:Byte = _ ; /*	右三角置信度
										1...估计三角
										2...虚拟三角
										0...无三角
									*/
  var cx:Byte = _ ;
  var cy:Byte = _ ;
  var ca:Byte = _ ;
  var D_ca:Byte = _ ; //　2*D_ca 为估计中心角度变化范围　
  var D_cr:Byte = _ ; //	SCALE*D_cr 为估计中心区域半径
  var ex:Byte = _ ;
  var ey:Byte = _ ;
  var D_er:Byte = _ ; //	SCALE*D_er 为估计下中心区域半径
  var ldx:Byte = _ ;
  var ldy:Byte = _ ;
  var D_lr:Byte = _ ; //	SCALE*D_lr 为估计左三角区域半径
  var rdx:Byte = _ ;
  var rdy:Byte = _ ;
  var D_rr:Byte = _ ; //	SCALE*D_rr 为估计右三角区域半径
  var cm:Byte = _ ; //
  @Length(FLPMNTSIZE)
  var xx:Array[Byte] = _ ; //
  @Length(FLPMNTSIZE)
  var yy:Array[Byte] = _ ; //
  @Length(FLPMNTSIZE)
  var zz:Array[Byte] = _ ;
  @Length(FLPMNTSIZE)
  var mpos:Array[Byte] = _ ; //	特征位置，按位设置
  //			mpos|0...上部，1...下部，2...左部，3...右部
  var mqlevON:Byte = _ ; //	使用细节特征评判 1...使用，0...不使用
    @Length(FLPMNTSIZE / 8)
  var mqlev:Array[Byte] = _ ; //	细节特征质量，按位设置，1代表可靠，0代表不可靠

  /*	确定区域个数(bm)
          确定区域定义为：指纹纹线清晰，标点特征可靠，
          没有遗漏特征和伪特征
          确定区域包含老版本中空白区，即无细节特征的确定区域
   */
  var bm:Byte = _ ;
  @Length(BLKMNTSIZE)
  var bx:Array[Byte]= _ ;
  @Length(BLKMNTSIZE)
  var by:Array[Byte]= _ ;
  @Length(BLKMNTSIZE)
  var br:Array[Byte]= _ ;
  /*　结束	*/
  var lm:Byte = _ ; //数线点对个数				//
  @Length(LINECOUNTSIZE)
  var lnum0:Array[Byte]= _ ; //数线点对的特征标号		//
  @Length(LINECOUNTSIZE)
  var lnum1:Array[Byte]= _ ; //数线点对的特征标号		//
  @Length(LINECOUNTSIZE)
  var lcount:Array[Byte]= _ ; //线数						//
  //  特征区域设置框信息
  var mpm:Byte = _ ;
  @Length(MNTPOSSIZE)
  var mptop:Array[Byte]= _ ;
  @Length(MNTPOSSIZE)
  var mpbottom:Array[Byte]= _ ;
  @Length(MNTPOSSIZE)
  var mpleft:Array[Byte]= _ ;
  @Length(MNTPOSSIZE)
  var mpright:Array[Byte] = _ ;
  @Length(MNTPOSSIZE)
  var mptype:Array[Byte] = _ ; //	设置框代表的区域编码
  //	0...上部，1...下部，2...左部，3...右部
  @Length(2048)
  var ridgemap:Array[Byte] = _ ;
  var mdate:Short = _ ; //	生成或变更时间，转换成短整型 YY*32*32+MM*32+DD
  var nSignature:Short = _ ; //	图象标签，用来判定图象特征一致性
    @Length(2880 - 2048 - 64 - 3 * LINECOUNTSIZE - 4 * FLPMNTSIZE - 3 * BLKMNTSIZE - 5 * MNTPOSSIZE)
  var LatMntFill:Array[Byte] = _
  } // FINGERLATMNTSTRUCT50;

  class PALMMNTSTRUCT50 extends AncientData {
  var resolution:Short = _ ; //	采集密度，缺省值为500	//
  var nWidth:Short = _ ; //	图象宽度				//
  var nHeight:Short = _ ; //	图象高度				//
  var offsetX:Short = _ ; //	特征提取偏移位置（相对原图坐标原点）
  var offsetY:Short = _ ;
  var nPalmIndex:Byte = _ ; //	掌位，0：未知，1：右掌，2：左掌
  var nres:Short = _ ; //	存储密度，缺省值为200   SCALE=resolution/nres
  var MntVersion:Byte = _ ; //	特征提取方法(以前版本为0)
  var qlev:Byte = _ ; //	掌纹质量：0 到 100
  var basepos:Short = _ ; //	基线位置
  var wiptx:Short = _ ; //	第一曲肌褶纹内侧位置
  var wipty:Short = _ ;
  var woptx:Short = _ ; // 	第一曲肌褶纹外侧位置
  var wopty:Short = _ ;

  var fca:Byte = _ ; //	掌纹方向，定义同中心方向
  var D_fca:Byte = _ ; //	掌纹方向角度变化范围,定义同中心方向范围，
  //	有掌纹方向时，D_fca值在8和45之间
  //	当D_fca==0代表不用掌纹方向
  var FPTrp:Byte = _ ; //	指根区纹型信息0：未知,1：存在,2：不存在
  var IPTrp:Byte = _ ; //	内侧区纹型信息0：未知,1：存在,2：不存在
  var OPTrp:Byte = _ ; //	外侧区纹型信息0：未知,1：存在,2：不存在   //30字节

  var nDeltaCnt:Byte = _ ; //	三角个数

  @Length(PALMDELTASIZE * 2)
  var dx:Array[Byte] = _ ;
  @Length(PALMDELTASIZE * 2)
  var dy:Array[Byte] = _ ;
  @Length(PALMDELTASIZE)
  var dz:Array[Byte] = _ ; //	0...180,(cz+90)/2,cz为实际的方向(-90,270)
  @Length(PALMDELTASIZE)
  var dr:Array[Byte] = _ ; //	位置半径
  @Length(PALMDELTASIZE)
  var D_dz:Array[Byte] = _ ; //	方向范围
  @Length(PALMDELTASIZE)
  var dtype:Array[Byte] = _ ; //	三角类型（腕部、指根或花纹）
  @Length(PALMDELTASIZE)
  var dfpt:Array[Byte] = _ ; //	指根三角（按位表示，从右到左0...食指，3...小指）

  var nCoreCnt:Byte = _ ; //	花纹中心个数

  @Length(PALMPATCORESIZE * 2)
  var cx:Array[Byte] = _ ;
  @Length(PALMPATCORESIZE * 2)
  var cy:Array[Byte] = _ ;
  @Length(PALMPATCORESIZE)
  var cz:Array[Byte] = _ ; //	0...180,(cz+90)/2,cz为实际的方向(-90,270)
  @Length(PALMPATCORESIZE)
  var cr:Array[Byte] = _ ; //	位置半径
  @Length(PALMPATCORESIZE)
  var D_cz:Array[Byte] = _ ; //	方向范围

  var cm:Short = _ ; //	细节特征数	0---150 */						//
  @Length(PTPMNTSIZE * 2)
  var xx:Array[Byte] = _ ; //	细节特征实际位置为(SCALE*xx,SCALE*yy) */	//
  @Length(PTPMNTSIZE * 2)
  var yy:Array[Byte] = _ ; //
  @Length(PTPMNTSIZE)
  var zz:Array[Byte] = _ ; //  细节特征实际方向为 2*zz-90, 在[-90,270)区间内//
  //	垂直向下为 0，水平向右为 90，水平向左为-90
  var mqlevON:Byte = _ ; //	使用细节特征评判 1...使用，0...不使用
    @Length((PTPMNTSIZE + 4) / 8)
  var mqlev:Array[Byte] = _ ; //	细节特征质量，按位设置，1代表可靠，0代表不可靠
  var mdate:Short = _ ; //	生成或变更时间，转换成短整型 YY*32*32+MM*32+DD
  var nSignature:Short = _ ; //	图象标签，用来判定图象特征一致性
    @Length(8192 - 39 - 9 * PALMDELTASIZE - PALMPATCORESIZE * 7 - 5 * PTPMNTSIZE - (PTPMNTSIZE + 4) / 8)
  var MntFill:Array[Byte] = _
  } // PALMMNTSTRUCT50;

  class PALMLATMNTSTRUCT50 extends AncientData {
  var resolution:Short = _ ; //	采集密度，缺省值为500	//
  var nWidth:Short = _ ; //	图象宽度				//
  var nHeight:Short = _ ; //	图象高度				//
  var offsetX:Short = _ ; //	特征提取偏移位置（相对原图坐标原点）
  var offsetY:Short = _ ;
  var nPalmIndex:Byte = _ ; //	掌位，0：未知，1：右掌，2：左掌
  var nres:Short = _ ; //	存储密度，缺省值为200   SCALE=resolution/nres
  var MntVersion:Byte = _ ; //	特征提取方法(以前版本为0)
  var Distore:Byte = _ ; //  变形参数

  var basepos:Short = _ ; //	基线位置
  var woptx:Short = _ ; // 	第一曲肌褶纹外侧位置
  var wopty:Short = _ ;
  var woptz:Byte = _ ; //	第一曲肌褶纹的方向（指向手心方向）

  var fca:Byte = _ ; //	掌纹方向，定义同中心方向
  var D_fca:Byte = _ ; //	掌纹方向角度变化范围,定义同中心方向范围，
  //	有掌纹方向时，D_fca值在8和45之间（实际值为15--90）
  //	当D_fca==0代表不用掌纹方向
  var FPTrp:Byte = _ ; //	指根区纹型信息0：未知,1：存在,2：不存在
  var IPTrp:Byte = _ ; //	内侧区纹型信息0：未知,1：存在,2：不存在
  var OPTrp:Byte = _ ; //	外侧区纹型信息0：未知,1：存在,2：不存在   //30字节

  var nDeltaCnt:Byte = _ ; //	三角个数

  @Length(PALMDELTASIZE * 2)
  var dx:Array[Byte] = _ ;
  @Length(PALMDELTASIZE * 2)
  var dy:Array[Byte] = _ ;
  @Length(PALMDELTASIZE)
  var dz:Array[Byte] = _ ; //	0...180,(cz+90)/2,cz为实际的方向(-90,270)
  @Length(PALMDELTASIZE)
  var dr:Array[Byte] = _ ; //	位置半径
  @Length(PALMDELTASIZE)
  var D_dz:Array[Byte] = _ ; //	方向范围
  @Length(PALMDELTASIZE)
  var dtype:Array[Byte] = _ ; //	三角类型（腕部、指根或花纹）
  @Length(PALMDELTASIZE)
  var dfpt:Array[Byte] = _ ; //	指根三角（按位表示，从右到左0...食指，3...小指）

  var nCoreCnt:Byte = _ ; //	花纹中心个数

  @Length(PALMPATCORESIZE * 2)
  var cx:Array[Byte] = _ ;
  @Length(PALMPATCORESIZE * 2)
  var cy:Array[Byte] = _ ;
  @Length(PALMPATCORESIZE)
  var cz:Array[Byte] = _ ; //	0...180,(cz+90)/2,cz为实际的方向(-90,270)
  @Length(PALMPATCORESIZE)
  var cr:Array[Byte] = _ ; //	位置半径
  @Length(PALMPATCORESIZE)
  var D_cz:Array[Byte] = _ ; //	方向范围
  var cm:Short = _ ; //	细节特征数	0---150 */						//
  @Length(PLPMNTSIZE * 2)
  var xx:Array[Byte] = _ ; //	细节特征实际位置为(SCALE*xx,SCALE*yy) */	//
  @Length(PLPMNTSIZE * 2)
  var yy:Array[Byte] = _ ; //
  @Length(PLPMNTSIZE)
  var zz:Array[Byte] = _ ; //  细节特征实际方向为 2*zz-90, 在[-90,270)区间内//
  //	垂直向下为 0，水平向右为 90，水平向左为-90
  @Length(PLPMNTSIZE)
  var mpos:Array[Byte] = _ ; //	按位表示 指根区--128，内侧区--64 外侧区--32
  var mqlevON:Byte = _ ; //	使用细节特征评判 1...使用，0...不使用
    @Length((PLPMNTSIZE + 4) / 8)
  var mqlev:Array[Byte] = _ ; //	细节特征质量，按位设置，1代表可靠，0代表不可靠
  var bm:Byte = _ ;
  @Length(BLKMNTSIZE*2)
  var bx:Array[Byte] = _ ;
  @Length(BLKMNTSIZE*2)
  var by:Array[Byte] = _ ;
  @Length(BLKMNTSIZE)
  var br:Array[Byte] = _ ;
  /*　结束	*/
  var lm:Byte = _ ; //数线点对个数				//
  @Length(LINECOUNTSIZE*2)
  var lnum0:Array[Byte] = _ ; //数线点对的特征标号		//
  @Length(LINECOUNTSIZE*2)
  var lnum1:Array[Byte] = _ ; //数线点对的特征标号		//
  @Length(LINECOUNTSIZE)
  var lcount:Array[Byte] = _ ; //线数						//
  //  特征区域设置框信息
  var mpm:Byte = _ ;
  @Length(MNTPOSSIZE * 2)
  var mptop:Array[Byte] = _ ;
  @Length(MNTPOSSIZE * 2)
  var mpbottom:Array[Byte] = _ ;
  @Length(MNTPOSSIZE * 2)
  var mpleft:Array[Byte] = _ ;
  @Length(MNTPOSSIZE * 2)
  var mpright:Array[Byte] = _ ;
  @Length(MNTPOSSIZE)
  var mptype:Array[Byte] = _ ; //	设置框代表的区域编码
  //	0...上部，1...下部，2...左部，3...右部
  @Length(15360)
  var ridgemap:Array[Byte] = _ ;
  var mdate:Short = _ ; //	生成或变更时间，转换成短整型 YY*32*32+MM*32+DD
  var nSignature:Short = _ ; //	图象标签，用来判定图象特征一致性
    @Length(20480 - 15360 - 39 - PALMDELTASIZE * 9 - PALMPATCORESIZE * 7 - 6 * PLPMNTSIZE - (PLPMNTSIZE + 4) / 8 - 5 * BLKMNTSIZE - 5 * LINECOUNTSIZE - 9 * MNTPOSSIZE)
  var MntFill:Array[Byte] = _
  } // PALMLATMNTSTRUCT50;


  final val MARKOFINNERMNT = 254	//	内部特征的标志
  final val INNERMNT_MNTCNT = 0	//	内部特征的总数
  final val INNERMNT_SGPOINT = 1	//	奇异点
  final val INNERMNT_MINUTIA1 = 2	//	可靠细节特征点，参与匹配
  final val INNERMNT_MINUTIA0 = 3	//	疑似特征点，影响匹配得分
  final val INNERMNT_CFAREA = 4	//	确定区域
  final val INNERMNT_MNTEVALUE = 5	//  特征评价信息
  final val INNERMNT_MINUTIAADJ = 6	//	需要调整方向的特征

  final val MNTCONF_UNSET = 0	// 未设置
  final val MNTCONF_TRUE = 1	// 确定特征
  final val MNTCONF_MAYBE = 2	// 疑似特征
  final val MNTCONF_FALSE = 3	// 错误特征


  final val MINUTIAERR_NORMAL = 0	// 细节特征正常
  final val MINUTIAERR_SHIFT = 1	// 细节特征平移
  final val MINUTIAERR_DIRECT = 2	// 细节特征方向错
  final val MINUTIAERR_FAKE = 4	// 细节特征伪
  final val MINUTIAERR_OMISSION = 8	// 细节特征遗漏

  class AFISCIRCLE extends AncientData  {
  var cx:Int = _;
  var cy:Int = _;
  var radius:Int = _;
  } // AFISCIRCLE;

  class MNTEVALUESTR extends AncientData  {
  var Conf_CorePos:Byte = _ ; // MNTCONF_XXX
  var Conf_CoreDrt:Byte = _ ; // MNTCONF_XXX
  var Conf_VCorePos:Byte = _ ; // MNTCONF_XXX
  var Conf_LDeltaPos:Byte = _ ; // MNTCONF_XXX
  var Conf_RDeltaPos:Byte = _ ; // MNTCONF_XXX
  var MinutiaErr:Byte = _ ; // MINUTIAERR_XXX位运算
  var ShiftX:Byte = _ ; // 竖直平移值（尺度变换过），原坐标值减这个值为实际值
  var ShiftY:Byte = _ ; // 横向平移值（尺度变换过），原坐标值减这个值为实际值
  } // MNTEVALUESTR;

  class LPINNERMNTSTR extends AncientData  {
  var nMntCnt_Add1:Byte = _ ; //	补充的可靠细节特征数
  var nMntCnt_Add0:Byte = _ ; //	补充的疑似细节特征数
  var nMntCnt_Adj:Byte = _ ; //	需调整方向的细节特征个数
  var SgCnt:Byte = _ ; //	奇异点个数
  var nCAreaCnt:Byte = _ ; //	确定区域个数
  @Length(3)
  var nRes:Array[Byte] = _ ;
  var stFgMntValue = new MNTEVALUESTR; //	特征自动评价信息
  @Length(FLPMNTSIZE)
  var xx1:Array[Byte] = _ ; //	补充的可靠细节特征信息
  @Length(FLPMNTSIZE)
  var yy1:Array[Byte] = _ ; //
  @Length(FLPMNTSIZE)
  var zz1:Array[Byte] = _ ; //
  @Length(FLPMNTSIZE)
  var xx0:Array[Byte] = _ ; //	补充的疑似细节特征信息
  @Length(FLPMNTSIZE)
  var yy0:Array[Byte] = _ ; //
  @Length(FLPMNTSIZE)
  var zz0:Array[Byte] = _ ; //
  @Length(FLPMNTSIZE)
  var Adj_Indx:Array[Byte] = _ ; //	需调整方向的细节特征下标
  @Length(FLPMNTSIZE)
  var Adj_zz:Array[Byte] = _ ; //	需调整方向的细节特征的方向
  @Length(BLKMNTSIZE)
  var CAreaArray:Array[AFISCIRCLE] = _; //	确定区域信息
  @Length(MAXSGPOINTCNT_FINGER)
  var SgPoint:Array[SGPOINT] = _; //	奇异点信息
  @Length(48)
  var nres:Array[Byte] = _ ;
  } // LPINNERMNTSTR;

  class LPMNTSTATICS extends AncientData  {
  var beExist:Byte = _ ; //	特征信息是否存在		1...存在，0...空特征
  var CoreON:Byte = _ ; //	是否有中心				1...有， 0..没有
  var VCoreON:Byte = _ ; //	是否有副中心			1...有， 0..没有
  var LDeltaON:Byte = _ ; //	是否有左三角			1...有， 0..没有
  var RDeltaON:Byte = _ ; //	是否右三角				1...有， 0..没有
  var Err_Rp_Morpho:Byte = _ ; //	纹型和形态特征是否匹配	1...有错，0...无误
  var Err_Morpho_Rel:Byte = _ ; //	形态特征相对关系是否正确	1...有错，0...无误
  var beZeroDegreeCore:Byte = _ ; //	是否方向向下的中心		1...是， 0..否
  var nMntCnt:Short = _; //	细节特征个数			1...有， 0..没有
  @Length(14)
  var nResp:Array[Byte] = _ ;
  } // LPMNTSTATICS;


}

