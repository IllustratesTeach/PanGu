package nirvana.hall.api.services.query

import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import org.springframework.transaction.annotation.Transactional

/**
 * 发送查询到6.2服务
 * Created by songpeng on 15/12/9.
 */
trait Query7to6Service {

  /**
   * 发送比对任务
   * @param matchTask
   * @return
   */
  def sendQuery(matchTask: MatchTask)

  /**
   * 获取比对任务，一条
   */
  @Transactional
  def getMatchTask: Option[MatchTask]
}
