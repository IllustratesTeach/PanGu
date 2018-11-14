package nirvana.hall.matcher.internal.adapter.gafis6

import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.service.MatchTaskCronService
import nirvana.hall.support.services.JdbcDatabase
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

class MatchTaskCronServiceImpl(hallMatcherConfig: HallMatcherConfig, implicit val dataSource: DataSource) extends MatchTaskCronService with LoggerSupport{

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallMatcherConfig.matchTimeout != null){
      info("begin match task cron, update status waiting when timeout. ")
      updateMatchStatusWaitingByMatchTaskTimeout(hallMatcherConfig.matchTimeout.timeout)
      info("end match task cron")
    }
  }
  /**
    * 重启之后设置比对状态为0，重发比对
    */
  override def updateMatchStatusWaitingByMatchTaskTimeout(minute: Int): Unit = {
    val sql = "update NORMALQUERY_QUERYQUE t set t.status=0 where t.status=1 and t.rmtflag in (0,2) "
    JdbcDatabase.update(sql){ps=>}
  }
}
