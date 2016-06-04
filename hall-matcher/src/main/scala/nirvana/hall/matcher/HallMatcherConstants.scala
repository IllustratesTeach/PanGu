package nirvana.hall.matcher

/**
 * Created by songpeng on 16/3/29.
 */
object HallMatcherConstants {
  /**
   * 数据头长度
   */
  val HEADER_LENGTH: Int = 64
  /**
   * 指纹特征数据长度
   */
  val FINGER_MNT_LENGTH: Int = 640
  /**
   * 捺印掌纹特征数据长度
   */
  val PALM_MNT_LENGTH: Int = 8192
  /**
   * 现场掌纹特征数据长度
   */
  val PALM_MNT_LENGTH_LATENT: Int = 5120
  /**
   * 每次查询的数量
   */
  val FETCH_BATCH_SIZE: Int = 100

  final val PROTOBUF_CONTEXT = "application/protobuf"
  //http://bigtext.org/?font=smslant&text=HALL
  final val NIRVANA_TEXT_LOGO = """ #
   __ _____   __   __
  / // / _ | / /  / /
 / _  / __ |/ /__/ /__ module : |@@|red %s|@#
/_//_/_/ |_/____/____/ version: |@@|yellow %s|@
                               """.replaceAll("#", "@|green ")


  //查询类型
  val QUERY_TYPE_TT = 0
  val QUERY_TYPE_TL = 1
  val QUERY_TYPE_LT = 2
  val QUERY_TYPE_LL = 3

  //比对状态
  val QUERY_STATUS_WAIT = 0
  val QUERY_STATUS_MATCHING = 1
  val QUERY_STATUS_SUCCESS = 2
  val QUERY_STATUS_FAIL = 3
}
