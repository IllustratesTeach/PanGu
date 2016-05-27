package nirvana.hall.v70.internal.query

/**
 * Created by songpeng on 16/5/10.
 */
object QueryConstants {
  val STATUS_WAIT: java.lang.Short = 0.toShort//任务状态，正在比对
  val STATUS_MATCHING: java.lang.Short = 1.toShort//任务状态，正在比对
  val STATUS_SUCCESS: java.lang.Short = 2.toShort//完成比对
  val STATUS_FAIL: java.lang.Short = 3.toShort//任务状态，失败
}
