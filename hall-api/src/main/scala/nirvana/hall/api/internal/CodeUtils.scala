package nirvana.hall.api.internal

object CodeUtils {

  /* 协查级别 A级 */
  final val XCJB_AJ_7 = "1"
  /* 协查级别 B级紧急 */
  final val XCJB_BJJJ_7 = "2"
  /* 协查级别 B级普通 */
  final val XCJB_BJPT_7 = "3"
  /* 协查级别 C级 */
  final val XCJB_CJ_7 = "4"
  /* 协查级别 其他 */
  final val XCJB_QT_7 = "9"

  /* 协查级别 A级 */
  final val XCJB_AJ_6 = "1"
  /* 协查级别 B级普通 */
  final val XCJB_BJPT_6 = "2"
  /* 协查级别 B级紧急 */
  final val XCJB_BJJJ_6 = "4"
  /* 协查级别 C级 */
  final val XCJB_CJ_6 = "3"
  /* 协查级别 未知 */
  final val XCJB_WZ_6 = "0"


  /**
    * 获取协查 6 to 7 转换码值
    */
  def convertCode6To7(code :String): String = {
    code match {
      case XCJB_AJ_6 => XCJB_AJ_7
      case XCJB_BJPT_6 => XCJB_BJPT_7
      case XCJB_BJJJ_6 => XCJB_BJJJ_7
      case XCJB_CJ_6 => XCJB_CJ_7
      case XCJB_WZ_6 => XCJB_QT_7
      case _ => ""
    }
  }

  /**
    * 获取协查 7 to 6 转换码值
    */
  def convertCode7To6(code :String): String = {
    code match {
      case XCJB_AJ_7 => XCJB_AJ_6
      case XCJB_BJPT_7 => XCJB_BJPT_6
      case XCJB_BJJJ_7 => XCJB_BJJJ_6
      case XCJB_CJ_7 => XCJB_CJ_6
      case XCJB_QT_7 => XCJB_WZ_6
      case _ => ""
    }
  }

}