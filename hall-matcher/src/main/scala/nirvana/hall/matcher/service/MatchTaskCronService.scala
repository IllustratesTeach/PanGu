package nirvana.hall.matcher.service

/**
 * 比对任务处理定时任务
 */
trait MatchTaskCronService {

  /**
   * 当比对任务超过1小时没有返回结果信息，设置比对状态为0，重发比对
   */
  def updateMatchStatusWaitingByMatchTaskTimeout(minute: Int)

}
