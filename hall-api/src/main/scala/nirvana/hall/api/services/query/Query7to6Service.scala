package nirvana.hall.api.services.query

import nirvana.hall.api.services.AutoSpringDataSourceSession
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import org.springframework.transaction.annotation.Transactional
import scalikejdbc.DBSession

/**
 * 发送查询到6.2服务
 * Created by songpeng on 15/12/9.
 */
trait Query7to6Service {

  /**
   * 发送比对任务
   * @param matchTask
   * @param session
   * @return
   */
  @Transactional
  def sendQuery(matchTask: MatchTask)(implicit session: DBSession = AutoSpringDataSourceSession.apply())

  /**
   * 获取比对任务，一条
   * @param session
   */
  @Transactional
  def getMatchTask(implicit session: DBSession = AutoSpringDataSourceSession.apply()): Option[MatchTask]
}
