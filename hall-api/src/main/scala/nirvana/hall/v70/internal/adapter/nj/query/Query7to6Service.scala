package nirvana.hall.v70.internal.adapter.nj.query

import nirvana.hall.v70.internal.adapter.nj.jpa.GafisNormalqueryQueryque
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
  @Transactional
  def sendQuery(gafisQuery: GafisNormalqueryQueryque)

  /**
   * 获取一条等待比对的任务
   */
  def getGafisNormalqueryQueryqueWait: Option[GafisNormalqueryQueryque]

  /**
   * 读取任务队列，处理发送查询任务
   */
  def doWork
}
