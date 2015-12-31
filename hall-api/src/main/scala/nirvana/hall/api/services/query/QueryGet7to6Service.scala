package nirvana.hall.api.services.query

import nirvana.hall.api.entities.GafisNormalqueryQueryque
import nirvana.hall.api.services.AutoSpringDataSourceSession
import org.springframework.transaction.annotation.Transactional
import scalikejdbc.DBSession

/**
 * Created by songpeng on 15/12/30.
 */
trait QueryGet7to6Service {

  /**
   * 获取正在比对的6.2查询任务
   * @return
   */
  def getGafisNormalqueryQueryqueMatching(implicit session: DBSession = AutoSpringDataSourceSession.apply()): Option[GafisNormalqueryQueryque]

  /**
   * 从6.2获取查询结果，保存候选列表信息
   * @param gafisNormalqueryQueryque
   * @param session
   * @return
   */
  @Transactional
  def getQueryAndSaveMatchResult(gafisNormalqueryQueryque: GafisNormalqueryQueryque)(implicit session:DBSession = AutoSpringDataSourceSession.apply())
}
