package nirvana.hall.c.services.gfpt5lib

import monad.support.services.XmlLoader
import nirvana.hall.c.services.gfpt4lib.fpt4util._
import nirvana.hall.c.services.kernel.mnt_checker_def._

/**
  * fpt5字典数据转换工具类
  */
object fpt5util {

  /**
    *	  yuchen 2017-11-30
    *	注：结合公安部FPT5.0标准，按照6。2的结构以及FPT4util中特征转换，进行了针对FPT5的重写，大体思路不变。
    */

  //指纹数据代码第4部分：被捺印指纹人员类别代码
  val PERSON_TYPE_CRIMINAL = "01" //经人民法院审判定罪的罪犯
  val PERSON_TYPE_REHABILITATE = "02" //依法被收容教养的人员
  val PERSON_TYPE_ADMINISTRATIVE_PENALTY = "03" //依法被行政拘留或者因实施违反治安管理或者出入境管理为被依法予以其他行政处罚的人员，但是被当场作出治安管理处罚的除外
  val PERSON_TYPE_WITHDRAWAL = "04" //依法被强制戒毒的人员
  val PERSON_TYPE_DETENTION_EDUCATION = "05" //依法被收容教育的人员
  val PERSON_TYPE_SUSPECT = "06" //依法被拘传、取保候审、监视居住、拘留或者逮捕的犯罪嫌疑人
  val PERSON_TYPE_INTERROGATE = "07" //依法被继续盘问的人员
  val PERSON_TYPE_ABOVE_COUNTY_RATIFY_GATHER = "08" //公安机关因办理案（事）件需要，经县级以上公安机关负责人批准采集指掌纹信息的人员
  val PERSON_TYPE_PUBLIC_SECURITY = "21" //保安员申请人
  val PERSON_TYPE_OTHER = "99" //其他

  //指纹数据代码 第1部分：指纹指位代码
  val FINGER_ROLL_R_THUMB	= "01";	//滚动右拇
  val FINGER_ROLL_R_INDEX	= "02";	//滚动右食
  val FINGER_ROLL_R_MIDDLE	= "03";	//滚动右中
  val FINGER_ROLL_R_RING	= "04";	//滚动右环
  val FINGER_ROLL_R_LITTLE	= "05";	//滚动右小
  val FINGER_ROLL_L_THUMB	= "06";	//滚动左姆
  val FINGER_ROLL_L_INDEX	= "07";	//滚动左食
  val FINGER_ROLL_L_MIDDLE	= "08";	//滚动左中
  val FINGER_ROLL_L_RING	= "09";	//滚动左环
  val FINGER_ROLL_L_LITTLE	= "10";	//滚动左小
  val FINGER_PLAIN_R_THUMB	= "11";	//平面右拇
  val FINGER_PLAIN_R_INDEX	= "12";	//平面右食
  val FINGER_PLAIN_R_MIDDLE	= "13";	//平面右中
  val FINGER_PLAIN_R_RING	= "14";	//平面右环
  val FINGER_PLAIN_R_LITTLE	= "15";	//平面右小
  val FINGER_PLAIN_L_THUMB	= "16";	//平面左姆
  val FINGER_PLAIN_L_INDEX	= "17";	//平面左食
  val FINGER_PLAIN_L_MIDDLE	= "18";	//平面左中
  val FINGER_PLAIN_L_RING	= "19";	//平面左环
  val FINGER_PLAIN_R_FOUR_BLOCK	= "20";	//平面右手四连指
  val FINGER_PLAIN_L_FOUR_BLOCK	= "20";	//平面左手四连指
  val FINGER_PLAIN_DOUBLE_THUMB	= "20";	//平面左右手拇指
  val FINGER_PLAIN_UNKNOWN = "20";	//不确定指位

  //指纹数据代码第8部分：指纹特征提取方式缩略规则
  val EXTRACT_METHOD_A = "A" //自动提取
  val EXTRACT_METHOD_U = "U" //自动提取且需要人工编辑
  val EXTRACT_METHOD_E = "E" //自动提取且已经人工编辑
  val EXTRACT_METHOD_M = "M" //人工抽取
  val EXTRACT_METHOD_O = "O" //其他

  //公安信息代码第XXX部分：指掌纹缺失情况代码
  val FINGER_LOST_NORMAL = "0"  //正常
  val FINGER_LOST_INCOMPLETE = "1"  //残缺
  val FINGER_LOST_SYS_NO_GATHER = "2"  //系统设置不采集
  val FINGER_LOST_INJURED = "3"  //受伤未采集
  val FINGER_LOST_OTHER = "9"  //其他缺失情况

  //指纹数据代码第2部分：指纹纹型代码
  val PATTERN_TYPE_ARCH = "1"		// 弓型纹
  val PATTERN_TYPE_LEFTLOOP = "2"		// 左箕型纹
  val PATTERN_TYPE_RIGHTLOOP = "3"		// 右箕型纹
  val PATTERN_TYPE_WHORL = "4"		// 斗型纹
  val PATTERN_TYPE_NOTEXIST = "5"		// 缺指
  val PATTERN_TYPE_UNKNOWN = "6"		// 未知
  val PATTERN_TYPE_OTHER = "9"		// 其它

  //指纹数据代码第9部分：掌纹掌位代码
  val PALM_R_PLAIN = "" //右手平面掌纹
  val PALM_L_PLAIN = "" //左手平面掌纹
  val PALM_R_SIDE = "" //右手侧面掌纹
  val PALM_L_SIDE = "" //左手侧面掌纹
  val PALM_R_FULL = "" //右手平面全掌纹
  val PALM_L_FULL = "" //左手平面全掌纹
  val PALM_UNKNOWN = "" //不确定掌纹

  //人像照片类型代码
  val FACE_FRONT = "1" //正面像
  val FACE_LEFT = "2" //左侧像
  val FACE_RIGHT = "3"  //右侧像
  val FACE_OTHER = "9"  //右侧像

  //指纹数据代码第3部分：乳突线颜色代码
  val RIDGE_COLOR_WHITE = "1" //白色
  val RIDGE_COLOR_BLACK = "2" //黑色
  val RIDGE_COLOR_OTHER = "9" //其他

  //掌纹三角位置类型代码
  val PALM_TRIANGLE_TYPE_UNKNOWN = "" //未知位置三角
  val PALM_TRIANGLE_TYPE_R_WRIST = "" //右手腕部三角
  val PALM_TRIANGLE_TYPE_R_INDEX_ROOT = "" //右手食指指根三角
  val PALM_TRIANGLE_TYPE_R_MIDDLE_ROOT = "" //右手中指指根三角
  val PALM_TRIANGLE_TYPE_R_RING_ROOT = "" //右手环指指根三角
  val PALM_TRIANGLE_TYPE_R_LITTLE_ROOT = "" //右手小指指根三角
  val PALM_TRIANGLE_TYPE_L_WRIST = "" //左手腕部三角
  val PALM_TRIANGLE_TYPE_L_INDEX_ROOT = "" //左手食指指根三角
  val PALM_TRIANGLE_TYPE_L_MIDDLE_ROOT = "" //左手中指指根三角
  val PALM_TRIANGLE_TYPE_L_RING_ROOT = "" //左手环指指根三角
  val PALM_TRIANGLE_TYPE_L_LITTLE_ROOT = "" //左手小指指根三角

  //指纹数据代码第7部分：指纹比对状态代码
  val QUERY_STATUS_WAIT = "1" //待查
  val QUERY_STATUS_CHANEL = "2" //撤销
  val QUERY_STATUS_MATCHED = "3" //比中
  val QUERY_STATUS_OTHER = "9" //其他

  //指掌纹比对任务类型代码
  val QUERY_TYPE_TT = "0" //查重
  val QUERY_TYPE_TL = "1" //正查
  val QUERY_TYPE_LT = "2" //倒查
  val QUERY_TYPE_LL = "3" //串查
  val QUERY_TYPE_UNKNOWN = "9" //其他

  //指掌纹查重比中类型代码
  val TT_MATCHED_NORMAL = "A" //十指正常比中，所有指位都是确认同一
  val TT_MATCHED_SAME_CARD = "B" //这两个次的数据是通过复制或者简单裁剪、旋转或者重新扫描等方式录入的，是同一张卡,是上一种情况的特例
  val TT_MATCHED_PART = "C" //A某些指位是明确比中了，但是其他指位因为质量问题等，不确定是否比中，各个指位比中情况有确认同一或者不确定是否同一两种
                            //B不同人捺印，某些指位出现确定不同一，并且不是这个人的其他指位指纹的重复，但是其他指位是确认同一或者不确定是否同一
  val TT_MATCHED_ECTOPIA = "D" //左右手弄反了，不同指位的指纹确认同一
  val TT_MATCHED_MIRROR = "E" //镜像比中

  //常用证件类型默认值
  val DEFAULT_CERTIFICATE_TYPE = "111" //身份证

  //物证分类与代码
  val PHYSICAL_TYPE_CODE_FINGER = "1101" //指纹
  val PHYSICAL_TYPE_CODE_KNUCKLEPRINT = "1102" //指节纹
  val PHYSICAL_TYPE_CODE_PALM = "1103" //掌纹
  val PHYSICAL_TYPE_CODE_GLOVE = "1104" //手套印
  val PHYSICAL_TYPE_CODE_OTHER = "1199" //其他手印痕迹

  /**
    * 中心，三角转换
    * 1-3位为x坐标，4-6位为y坐标，7-8位为坐标范围，
    * 9-11位为方向，12-13位为方向范围，
    * 14位表示可靠度（1-3可靠度依次递减）。
    * 无有效值的数据用ASCII码空格（SP）填写，采用GB/T 1988-1998
    */
  def convertCoreDelta2AFISCOREDELTASTRUCT(coreDelta: CoreDelta,stCoreDelta:AFISCOREDELTASTRUCT, nType:Int):Unit= {
    stCoreDelta.x = coreDelta.x.toShort
    stCoreDelta.y = coreDelta.y.toShort
    stCoreDelta.nRadius = coreDelta.nRadius.toByte
    // 方向
    stCoreDelta.z = UTIL_Angle_FPT2MntDisp(coreDelta.featureDirection)
    //方向范围
    stCoreDelta.nzVarRange = coreDelta.featureDirectionRange.toByte

    // 14位表示可靠度（1-3可靠度依次递减）
    stCoreDelta.nReliability = UTIL_Reliability_FPT2MntDisp(coreDelta.nRadius, nType).toByte

    stCoreDelta.bIsExist = 1

    nType match
    {
      case UTIL_COREDELTA_TYPE_UPCORE=>
        stCoreDelta.nClass = DELTACLASS_CORE.toByte
      case UTIL_COREDELTA_TYPE_VICECORE=>
        stCoreDelta.nClass = DELTACLASS_VICECORE.toByte
      case UTIL_COREDELTA_TYPE_LDELTA=>
        stCoreDelta.nClass = DELTACLASS_LEFT.toByte
      case UTIL_COREDELTA_TYPE_RDELTA=>
        stCoreDelta.nClass = DELTACLASS_RIGHT.toByte
      case other=>
        stCoreDelta.nClass = DELTACLASS_UNKNOWN.toByte
    }
  }

  /**
    * 特征方向转换
    * @param mntDisp 显示特征
    * @return （特征方向, 特征方向-范围）
    */
  def convertMntDisp2FeatureDirection(mntDisp: MNTDISPSTRUCT): (Int, Int)={
    val nFPTAngle = UTIL_Angle_MntDisp2FPT(mntDisp.stCm.fca)
    (nFPTAngle,mntDisp.stCm.D_fca)
  }

  /**
    *
    * @param x 指纹中心点_特征X坐标
    * @param y 指纹中心点_特征Y坐标
    * @param nRadius  指纹中心点_特征坐标范围
    * @param featureDirection 指纹中心点_特征方向
    * @param featureDirectionRange 指纹中心点_特征方向范围
    * @param nReliability 指纹中心点_特征可靠度（1-3可靠度依次递减）
    */
  case class CoreDelta(x:Int,y:Int,nRadius:Int,featureDirection: Int,featureDirectionRange: Int,nReliability:Int)

  /**
    * 中心点转换
    * @param stCoreDelta 6.2中心点结构AFISCOREDELTASTRUCT
    * @param nType 中心点类型
    * @return
    */
  def convertAFISCOREDELTASTRUCT2CoreDelta(stCoreDelta:AFISCOREDELTASTRUCT, nType:Int): CoreDelta={
    var featureDirection = 0
    var featureDirectionRange = 0
    if ( nType == UTIL_COREDELTA_TYPE_UPCORE || UTIL_COREDELTA_TYPE_VICECORE == nType){
      featureDirection = UTIL_Angle_MntDisp2FPT(stCoreDelta.z)
      featureDirectionRange = stCoreDelta.nzVarRange
    }
    val nReliability = UTIL_Reliability_MntDisp2FPT(stCoreDelta.nReliability, nType)
    var nRadius = stCoreDelta.nRadius
    //TODO 位置半径校验，暂时对不符合要求（两位整数）默认设置30
    if(nRadius > 100 || nRadius < 0)
      nRadius = 30
    CoreDelta(stCoreDelta.x, stCoreDelta.y, nRadius, featureDirection, featureDirectionRange, nReliability)
  }

  type Minutia = {
    var fingerFeaturePointXCoordinate: Int
    var fingerFeaturePointYCoordinate: Int
    var fingerFeaturePointDirection: Int
    var fingerFeaturePointQuality: Int
  }
  /**
    * 细节特征转换
    * @param mnt Minutia
    * @return
    */
  def convertMinutia2AFISMNTPOINTSTRUCT(mnt: Minutia): AFISMNTPOINTSTRUCT = {
    val stmnt = new AFISMNTPOINTSTRUCT
    stmnt.x = mnt.fingerFeaturePointXCoordinate.toShort
    stmnt.y = mnt.fingerFeaturePointYCoordinate.toShort
    stmnt.z = UTIL_Angle_FPT2MntDisp(mnt.fingerFeaturePointDirection)
    stmnt.nReliability = 1 // mnt.fingerFeaturePointQuality.toByte
    stmnt.nFlag = 1

    stmnt
  }

  /**
    * 生成现场物证编号
    * 规则是‘F+现勘号后22位+4位物证分类代码+3位顺序号',历史数据如无现勘编号，前23位用'F0000000000000000000000'占位
    * 三位顺序号在方法外生成
    * @return
    */
  def gerenateLatentPhysicalIdTake(physicalTypeCode:String):String
        ="F0000000000000000000000" + physicalTypeCode + "%03d".format(new scala.util.Random().nextInt(999))

  def parseFPT5(fptx : String) : FPT5File = XmlLoader.parseXML[FPT5File](fptx)
}
