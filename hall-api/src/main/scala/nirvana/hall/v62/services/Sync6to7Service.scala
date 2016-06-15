package nirvana.hall.v62.services

import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/6/12.
 */
trait Sync6to7Service {

  /**
   * 定时任务调用方法
   */
  @Transactional
  def doWork()
}
