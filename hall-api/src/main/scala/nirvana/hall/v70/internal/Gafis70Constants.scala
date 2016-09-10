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

}
