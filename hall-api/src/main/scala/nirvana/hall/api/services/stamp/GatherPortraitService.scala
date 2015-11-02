package nirvana.hall.api.services.stamp

import nirvana.hall.api.entities.{SysUser, GafisGatherPortrait}
import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc.DBSession

/**
 * 人像
 * Created by wangjue on 2015/10/26.
 */
trait GatherPortraitService {

  /**
   * 人像信息查询
   * @param person
   * @return
   */
  def queryGatherPortrait(person : String) (implicit session: DBSession = AutoSpringDataSourceSession.apply())  : List[GafisGatherPortrait]


  /**
   * 人像信息新增
   * @param gafisGatherPortrait
   * @return
   */
  def addGatherPortrait(gafisGatherPortrait: GafisGatherPortrait,login : SysUser) (implicit session: DBSession = AutoSpringDataSourceSession.apply())  : Boolean


  /**
   * 人像信息修改
   * @param gafisGatherPortrait
   * @return
   */
  def updateGatherPortrait(gafisGatherPortrait: GafisGatherPortrait) (implicit session: DBSession = AutoSpringDataSourceSession.apply())  : Boolean

}
