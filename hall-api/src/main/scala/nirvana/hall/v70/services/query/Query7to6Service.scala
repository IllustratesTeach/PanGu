package nirvana.hall.v70.services.query

import nirvana.hall.v70.jpa.GafisNormalqueryQueryque
import org.springframework.transaction.annotation.Transactional

/**
 * 发送查询到6.2服务
 * Created by songpeng on 15/12/9.
 */
trait Query7to6Service {

  /**
   * 发送比对任务
   * @param gafisQuery
   * @return
   */
  def sendQuery(gafisQuery: GafisNormalqueryQueryque)

  /**
   * 获取比对任务，一条
   */
  @Transactional
  def getGafisNormalqueryQueryque: Option[GafisNormalqueryQueryque]
}
