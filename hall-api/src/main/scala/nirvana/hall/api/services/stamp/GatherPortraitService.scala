package nirvana.hall.api.services.stamp

import nirvana.hall.api.entities.{SysUser, GafisGatherPortrait}
import nirvana.hall.api.services.AutoSpringDataSourceSession
import org.springframework.transaction.annotation.Transactional
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
   * 解析人像
   * @param personId
   * @param gatherData
   * @param session
   * @return
   */
  @Transactional
  def analysisGatherPortrait(personId : String,gatherData : String) (implicit session: DBSession = AutoSpringDataSourceSession.apply())  : String


  /**
   * 人像信息新增
   * @param gafisGatherPortrait
   * @return
   */
  @Transactional
  def addGatherPortrait(gafisGatherPortrait: GafisGatherPortrait) (implicit session: DBSession = AutoSpringDataSourceSession.apply())  : Boolean


  /**
   * 覆盖删除人像
   * @param personid
   * @param session
   * @return
   */
  @Transactional
  def deleteGatherPortrait(personid : String) (implicit session: DBSession = AutoSpringDataSourceSession.apply())  : Boolean

}
