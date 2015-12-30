package nirvana.hall.api.services.query

import nirvana.hall.api.entities.GafisNormalqueryQueryque

/**
 * Created by songpeng on 15/12/30.
 */
trait QueryGet7to6Service {

  /**
   * 获取正在比对的6.2查询任务
   * @return
   */
  def getGafisNormalqueryQueryqueMatching(): Option[GafisNormalqueryQueryque]
}
