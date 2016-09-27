package nirvana.hall.v70.internal.query

/**
 * Created by songpeng on 16/5/10.
 */
object QueryConstants {
  val STATUS_WAIT: java.lang.Short = 0.toShort//任务状态，正在比对
  val STATUS_MATCHING: java.lang.Short = 1.toShort//任务状态，正在比对
  val STATUS_SUCCESS: java.lang.Short = 2.toShort//完成比对
  val STATUS_FAIL: java.lang.Short = 3.toShort//任务状态，失败

  //提交比对的系统
  val SUBMIT_SYS_FINGER = "1" //指纹系统
  val SUBMIT_SYS_GATHER = "2" //标采

  val QUERY_TYPE_TT = 0 //查重
  val QUERY_TYPE_TL = 1 //倒查
  val QUERY_TYPE_LT = 2 //正查
  val QUERY_TYPE_LL = 3 //串查

  val FETCH_CONFIG_TPDB = "TPDB"
  val FETCH_CONFIG_LPDB = "LPDB"
}
