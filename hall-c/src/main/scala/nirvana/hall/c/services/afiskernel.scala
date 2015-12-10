package nirvana.hall.c.services

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
object afiskernel {

  final val KERROR_UNKNOWN = -1		//	未知错误
  final val KERROR_PARAMETER = -2		//	参数错误
  final val KERROR_DATA = -3		//	数据错误
  final val KERROR_MEMORY = -4		//	内存申请错误
  final val KERROR_LICENSE = -5		//	使用权限错误

  final val LIVESCAN_QUALITY_PASS = 1	//	活体质量通过
  final val LIVESCAN_QUALITY_OTHERERR = -1	//	其他错误
  final val LIVESCAN_QUALITY_WARNNING = -2	//	质量警告,允许强行通过
  final val LIVESCAN_QUALITY_UNPASS = -3	//	质量不通过,必须重新采集

  final val PALM_LVQLEV_AREA = -11		//	捺印有效面积不够大
  final val PALM_LVQLEV_SIZE = -12		//	宽度或高度不够
  final val PALM_LVQLEV_ROOT = -13		//	指根区域捺印不完整
  final val PALM_LVQLEV_WRIST = -14		//	腕部区域捺印不完整
  final val PALM_LVQLEV_HOLE = -15		//	掌心区域捺印不完整
  final val PALM_LVQLEV_LEAVE = -16		//	发现残留掌纹
  final val PALM_LVQLEV_DIRTY = -17		//	采集窗口脏
  final val PALM_LVQLEV_WHITE = -18		//	图像偏白
  final val PALM_LVQLEV_DARK = -19		//	图像偏黑
  final val PALM_LVQLEV_LOWCONTRAST = -20		//	图像反差小




  //图像增强方法
  final val IMGENHANCE_METHOD_LOCAL = 0				//	局部增强
  final val IMGENHANCE_METHOD_EQUAL = 1				//	均衡化
  final val IMGENHANCE_METHOD_HIST = 2				//	直方图拉伸
  final val IMGENHANCE_METHOD_GABOR1 = 3				//	GABOR1变换，允许在部分区域上人工描线辅助下进行图像增强
  final val IMGENHANCE_METHOD_GABOR2 = 4				//	GABOR2变换，按照人工给定的纹线方向进行图像增强

  //图像描述信息
  final val IMGENHANCE_AREA_MASK = 0				//	非增强区域
  final val IMGENHANCE_AREA_WORK = 1				//	需增强的工作区域，自动计算增强图像
  final val IMGENHANCE_AREA_RIDGE = 2				//	人工描线区域，用于描述所在点（包括周围）的纹线方向来辅助进行图像增强，该区域在工作区域内,   在IMGENHANCE_METHOD_GABOR1使用


  /****************************  特征转换函数  (gafistools.dll)       ********************************/
  final val MNTVALID_OK = 1
  final val MNTVALID_WARNING = -1
  final val MNTVALID_SUGGEST = -2


  //现场错误信息代码
  final val MNTERRNO_NONAMEERR = -1
  final val MNTERRNO_MNTCOUNT = -2
  final val MNTERRNO_POSITION = -3
  final val MNTERRNO_RPANDFEATURE = -4
  final val MNTERRNO_RPMATCH = -5
  final val MNTERRNO_MISSFEATURE = -6
  final val MNTERRNO_EXACTAREA = -7
  final val MNTERRNO_NORP = -8

  final val MNTERRNO_FEATURERANGE1 = -20
  final val MNTERRNO_FEATURERANGE2 = -21
  final val MNTERRNO_MNTPOSITION = -22
  final val MNTERRNO_MNTDISPERSE = -23



  final val EXTRACTMODE_NEW = 0				//	重新提取全部特征
  final val EXTRACTMODE_UPDATE = 1				//	在原有特征基础上更新特征
  final val EXTRACTMODE_CHECK = 2				//	检查原有特征的错误

  final val EXTRACTMNT_AUTO = 0x00000001		//	自动更新特征（在现场处理EXTRACTMODE_UPDATE模式下，目前建议用这个选项）
  final val EXTRACTMNT_MINUTIA_NEW = 0x00000002		//	使用新的细节特征
  final val EXTRACTMNT_MINUTIA_ADD = 0x00000004		//	补充新的细节特征
  final val EXTRACTMNT_MORPHO_NEW = 0x00000008		//	使用新的形态特征（指纹：纹型、中心、副中心、三角、指纹方向，掌纹：第一屈肌褶纹、腕部三角、掌纹方向、花纹中心、花纹三角）
  final val EXTRACTMNT_MORPHO_ADD = 0x00000010		//	补充新的形态特征
  final val EXTRACTMNT_RIDGE_NEW = 0x00000020		//	使用新的纹线信息
  final val EXTRACTMNT_RIDGE_ADD = 0x00000040		//	补充新的纹线信息（暂时不用）
  final val EXTRACTMNT_AREAINFO_NEW = 0x00000080		//	使用新的区域信息（模糊区、清晰区域、区域方向等）
  final val EXTRACTMNT_AREAINFO_ADD = 0x00000100		//	补充新的区域信息（暂时不用）

  final val MNTCHK_MINU_SHIFT = 0x00000001		//	细节特征漂移
  final val MNTCHK_MINU_OMISSION = 0x00000002		//	细节特征遗漏多
  final val MNTCHK_MINU_FAKE = 0x00000004		//	细节特征伪特征多
  final val MNTCHK_CORE_MISS = 0x00000008		//	指纹中心遗漏
  final val MNTCHK_CORE_POSITION = 0x00000010		//	指纹中心位置不准确
  final val MNTCHK_CORE_DIRECTON = 0x00000020		//	指纹中心方向不准确
  final val MNTCHK_VCORE_POSITION = 0x00000040		//	指纹副中心位置不准确
  final val MNTCHK_DELTA_POSITION = 0x00000080		//	指纹三角位置不准确
  final val MNTCHK_DATA_ERROR = 0x00000100		//	特征数据不规范
  final val MNTCHK_MORPHO_RELERR = 0x00000200		//	指纹形态特征相对位置关系不正确



  class XGWMNTEXTRACTSTR extends AncientData
  {
    var pImage_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pImage_Data:Array[GAFISIMAGESTRUCT] = _ // for pImage pointer ,struct:GAFISIMAGESTRUCT;			//	待处理的图像数据
  var pMnt_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pMnt_Data:Array[Byte] = _ // for pMnt pointer ,struct:void;				//	输入输出的特征信息
  var pBin_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pBin_Data:Array[GAFISIMAGESTRUCT] = _ // for pBin pointer ,struct:GAFISIMAGESTRUCT;				//	输入输出的BIN数据
  var pExtPara_Ptr:Int = _ //using 4 byte as pointer
  var ExtractMode:Byte = _;		//	处理模式，EXTRACTMODE_XXX
  var belatent:Byte=_;			//	是否为现场数据  1...是， 0...不是
    @Length(6)
  var nRes1:Array[Byte]= _ 			//	保留字节
  var MntBeUpdated:Int = _;		//	处理的特征：EXTRACTMNT_XXX  按位记录
  var MntCheckErr:Int = _;		//	特征检查返回的错误信息， MNTCHK_XXX按位记录
    @Length(40)
  var nRes2:Array[Byte]= _ ;			//	保留字节
  } //	XGWMNTEXTRACTSTR;


  /*********************		手纹（指纹和掌纹）特征提取(包括重新提取和特征更新）  (texfdll.dll)	****************************/
  //	函数返回值
  //		1	......	成功
  //		<0	......	失败    KERROR-XXX


  class LPMNTSTATICS extends AncientData
  {
    var beExist:Byte = _ ;			//	特征信息是否存在		1...存在，0...空数据
  var CoreON:Byte = _ ;				//	是否有中心				1...有， 0..没有
  var VCoreON:Byte = _ ;			//	是否有副中心			1...有， 0..没有
  var LDeltaON:Byte = _ ;			//	是否有左三角			1...有， 0..没有
  var RDeltaON:Byte = _ ;			//	是否右三角				1...有， 0..没有
  var Err_Rp_Morpho:Byte = _ ;		//	纹型和形态特征是否匹配	1...有错，0...无误
  var Err_Morpho_Rel:Byte = _ ;		//	形态特征相对关系是否正确	1...有错，0...无误
  var beZeroDegreeCore:Byte = _ ;	//	是否方向向下的中心		1...是， 0..否
  var nMntCnt :Short = _;			//	细节特征个数
  @Length(14)
  var nResp:String = _ ;
  } // LPMNTSTATICS;

  final val GSCH_DESTMNTCNT = 22

  final val DATAERR_OK = 1
  final val DATAERR_BASEPARAM = -1
  final val DATAERR_INDEX = -2
  final val DATAERR_MORPHO = -3
  final val DATAERR_MINUTIA = -4
  final val DATAERR_LINCOUNT = -5
  final val DATAERR_EXACTAREA = -6
  final val DATAERR_MNTPOS = -7
  final val DATAERR_BIN = -8

  //	函数返回值......1		正常数据
  //					<0		错误代码DATAERR_XXXX
  //备注：mnt,bin可以为空，表示不检查该项内容

  /******************************  活体指纹拼接函数 ********************************************/

  final val MOSAIC_ERR_PARAMETER = -1	//参数错误。给定函数的参数有错误。
  final val MOSAIC_ERR_MEMORY = -2	//内存分配失败。没有分配到足够的内存。
  final val MOSAIC_ERR_FUNCTION = -3	//功能未实现。调用函数的功能没有实现。
  final val MOSAIC_ERR_RESERVE1 = -4	//保留
  final val MOSAIC_ERR_RESERVE2 = -5	//保留
  final val MOSAIC_ERR_ERRNUMBER = -6	//非法的错误号。
  final val MOSAIC_ERR_UNAUTHOR = -7	//没有授权
  final val MOSAIC_ERR_NONEINIT = -8	//拼接未初始化。
  final val MOSAIC_ERR_TOOQUICK = -9  //滚动速度太快
  final val MOSAIC_ERR_ROLLBACK = -10 //大幅度回滚
  final val MOSAIC_ERR_DISLOCATION = -11 //由于回滚或者捺印变形导致纹线错位

  //下面部分是GAFIS_ExtractMNT_LV_EX函数的返回的错误代码

  final val MOSAIC_ERR_BADQLEV = -20			//指纹捺印质量差
  final val MOSAIC_ERR_COMPLETE = -21			//指纹捺印不完整
  final val MOSAIC_ERR_SMALLAREA = -22			//指纹捺印面积太小（捺印高度或宽度不够）
  final val MOSAIC_ERR_LEFT = -23			//指纹捺印太靠左边
  final val MOSAIC_ERR_RIGHT = -24			//指纹捺印太靠右边
  final val MOSAIC_ERR_ANGLE = -25			//指纹捺印倾斜角太大



  class MOSAICOPTION extends AncientData
  {
    var QlevCtrl:Byte = _ ;		//	活体拼接过程质量控制标准 0...10,
  //	0...缺省值，严格控制质量， 1...9,质量标准逐步降低
  //	10... 不使用质量控制
  @Length(23)
  var nres:String = _ ;
  } // MOSAICOPTION;




}
