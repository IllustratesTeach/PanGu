package nirvana.hall.matcher.internal.adapter.common

import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.service.MatchTaskCronService
import nirvana.hall.support.services.JdbcDatabase
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

/**
 * Created by songpeng on 16/9/7.
 */
class MatchTaskCronServiceImpl(hallMatcherConfig: HallMatcherConfig, implicit val dataSource: DataSource) extends MatchTaskCronService with LoggerSupport{

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, matchTaskCronService: MatchTaskCronService): Unit = {
    if(hallMatcherConfig.matchTimeout != null){
      periodicExecutor.addJob(new CronSchedule(hallMatcherConfig.matchTimeout.cron), "match_timeout-cron", new Runnable {
        override def run(): Unit = {
          info("begin match task cron, update status waiting when timeout. ")
          matchTaskCronService.updateMatchStatusWaitingByMatchTaskTimeout(hallMatcherConfig.matchTimeout.timeout)
          info("end match task cron")
        }
      })
    }
  }
  /**
   * 当比对任务超时,设置比对状态为0，比对进度为0
   * @param minute 分钟
   */
  override def updateMatchStatusWaitingByMatchTaskTimeout(minute: Int): Unit = {
    val sql = "update GAFIS_NORMALQUERY_QUERYQUE t set t.status=0, t.match_progress='' where t.status=1 and t.begintime < (sysdate - ?/1440) "
    JdbcDatabase.update(sql){ps=> ps.setInt(1, minute)}
  }
}
