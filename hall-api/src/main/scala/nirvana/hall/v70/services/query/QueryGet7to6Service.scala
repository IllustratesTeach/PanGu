package nirvana.hall.v70.services.query

import nirvana.hall.v70.jpa.GafisNormalqueryQueryque
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 15/12/30.
 */
trait QueryGet7to6Service {

  /**
   * 获取正在比对的6.2查询任务
   * @return
   */
  def getGafisNormalqueryQueryqueMatching(): Option[GafisNormalqueryQueryque]

  /**
   * 从6.2获取查询结果，保存候选列表信息
   * @param gafisNormalqueryQueryque
   * @return
   */
  @Transactional
  def getQueryAndSaveMatchResult(gafisNormalqueryQueryque: GafisNormalqueryQueryque)

  /**
   * 定时任务, 读取正在比对的任务，获取远程比对结果
   */
  @Transactional
  def doWork()
}
