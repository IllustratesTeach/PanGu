package nirvana.hall.matcher.internal.adapter.common

import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.matcher.service.MatchTaskCronService
import nirvana.hall.support.services.JdbcDatabase
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

/**
 * Created by songpeng on 16/9/7.
 */
class MatchTaskCronServiceImpl(implicit dataSource: DataSource) extends MatchTaskCronService with LoggerSupport{
  val cron = "0 0/5 * * * ? *"

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, matchTaskCronService: MatchTaskCronService): Unit = {
    periodicExecutor.addJob(new CronSchedule(cron), "sync-cron", new Runnable {
      override def run(): Unit = {
        info("begin match task cron, update status waiting when timeout 1 hour no result")
        matchTaskCronService.updateMatchStatusWaitingByMatchTaskTimeout()
        info("end match task cron")
      }
    })
  }
  /**
   * 当比对任务超过1小时没有返回结果信息，设置比对状态为0，重发比对
   */
  override def updateMatchStatusWaitingByMatchTaskTimeout(): Unit = {
    val sql = "update GAFIS_NORMALQUERY_QUERYQUE t set t.status=0 where t.status=1 and t.begintime < (sysdate - 1/24) "
    JdbcDatabase.update(sql){ps=>}
  }
}
