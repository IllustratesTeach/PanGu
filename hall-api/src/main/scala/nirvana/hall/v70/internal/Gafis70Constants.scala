package nirvana.hall.v70.internal

/**
 * v70 常量
 * Created by songpeng on 16/2/17.
 */
object Gafis70Constants {

  //接口保存默认的创建人ID
  val INPUTPSN :String = "1"
  //删除标记
  val DELETAG_USE :String = "1"
  val DELETAG_DEL :String = "0"
  //指纹显示，捺印人员列表可查询到
  val FINGER_SHOW_STATUS = "1"
  //数据来源，gafis6.2
  val DATA_SOURCE_GAFIS6= 4.toShort
  val DATA_SOURCE_CAPTURE=1.toString
  //FPT导入 
  val DATA_SOURCE_FPT= "5"
  //数据来源，海鑫综采
  val DATA_SOURCE_HXZC= 7.toShort
  //数据来源，现勘对接
  val DATA_SOURCE_SURVEY= 8.toShort
  //默认采集类型
  val GATHER_TYPE_ID_DEFAULT = "1"
  //主特征
  val IS_MAIN_MNT = "1"

  //特征
  val GROUP_ID_MNT = 0: Short
  //压缩图
  val GROUP_ID_CPR = 1: Short
  //JPG
  val GROUP_ID_JPG = 3: Short
  //纹线
  val GROUP_ID_BIN = 4: Short
  //数据
  val LOBTYPE_DATA = 1: Short
  //特征
  val LOBTYPE_MNT = 2: Short

  val FGP_CASE_PLAIN = 1: Short //平面
  val FGP_CASE_ROLL = 0: Short  //滚动

  val FGP_CASE_KNUCKLE_PRINTS = "2" //指节纹  
  val FGP_CASE_FOUR_PRINT = "1" //四连指 
  val FGP_CASE_FULL_PALM = "2" //全掌

  val PALM_RIGHT        = 11: Short //右掌
  val PALM_LEFT         = 12: Short //左掌
  val PALM_FINGER_R     = 3: Short  //右手指尖
  val PALM_FINGER_L     = 4: Short  //左手指尖
  val PALM_THUMB_R_LOW  = 5: Short  //右掌拇指下部区域
  val PALM_THUMB_R_UP   = 6: Short  //右掌拇指上部区域
  val PALM_THUMB_L_LOW  = 7: Short  //左掌拇指下部区域
  val PALM_THUMB_L_UP   = 8: Short  //左掌拇指上部区域
  val PALM_RIGHT_SIDE   = 17: Short //右侧掌纹
  val PALM_LEFT_SIDE    = 18: Short //左侧掌纹

  val PALM_FOUR_PRINT_RIGHT = 13:Short //四连指右 
  val PALM_FOUR_PRINT_LEFT  =14:Short //四连指左  
  val PALM_FULL_RIGHT = 15:Short //全掌右 
  val PALM_FULL_LEFT= 16:Short //全掌左

  val FACE_FRONT = "1"  //人像正面
  val FACE_RIGHT = "2"  //右侧
  val FACE_LEFT = "3"   //左侧
}
