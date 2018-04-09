package nirvana.hall.v70.internal.adapter.nj.query

/**
 * Created by songpeng on 16/5/10.
 */
object QueryConstants {
  val STATUS_WAIT: java.lang.Short = 0.toShort//任务状态，等待比对
  val STATUS_MATCHING: java.lang.Short = 1.toShort//任务状态，正在比对
  val STATUS_SUCCESS: java.lang.Short = 2.toShort//完成比对
  val STATUS_FAIL: java.lang.Short = 3.toShort//任务状态，失败

  //提交比对的系统
  val SUBMIT_SYS_FINGER = "1" //指纹系统
  val SUBMIT_SYS_GATHER = "2" //标采
  val SUBMIT_SYS_AFIS = "3" //afis6.2系统

  val QUERY_TYPE_TT = 0 //查重
  val QUERY_TYPE_TL = 1 //倒查
  val QUERY_TYPE_LT = 2 //正查
  val QUERY_TYPE_LL = 3 //串查

  val FLAG_PALM: Short = 2 //掌纹查询
  val FLAG_PALM_TEXT: Short = 22 //掌纹带文本
  val FLAG_FINGER: Short = 1 //指纹查询, 暂时不区分平面，滚动和文本

  val FETCH_CONFIG_TPDB = "TPDB"
  val FETCH_CONFIG_LPDB = "LPDB"

  val CONFIRM_STATUS_UNKNOWN: java.lang.Short = (-1).toShort //初始值
  val CONFIRM_STATUS_WAIT: java.lang.Short = 0.toShort //待认定
  val CONFIRM_STATUS_YES: java.lang.Short = 98.toShort //认定
  val CONFIRM_STATUS_NO: java.lang.Short = 99.toShort //否定

  val REVIEW_STATUS_WAIT: java.lang.Short = 0.toShort //待复核
  val REVIEW_STATUS_YES: java.lang.Short = 1.toShort //复核成功
  val REVIEW_STATUS_NO: java.lang.Short = 2.toShort //符合失败
  val REVIEW_STATUS_NOT: java.lang.Short = 3.toShort //确认符合失败
  val REVIEW_STATUS_CANCEL : java.lang.Short = 4.toShort //撤销

}
