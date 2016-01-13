package nirvana.hall.api.services.stamp

import nirvana.hall.api.jpa.GafisGatherPortrait
import nirvana.hall.orm.services.Relation
import org.springframework.transaction.annotation.Transactional

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
  def queryGatherPortrait(person : String)  : Relation[GafisGatherPortrait]


  /**
   * 解析人像
   * @param personId
   * @param gatherData
   * @return
   */
  @Transactional
  def analysisGatherPortrait(personId : String,gatherData : String)  : String


  /**
   * 人像信息新增
   * @param gafisGatherPortrait
   * @return
   */
  @Transactional
  def addGatherPortrait(gafisGatherPortrait: GafisGatherPortrait)  : Boolean


  /**
   * 覆盖删除人像
   * @param personid
   * @return
   */
  @Transactional
  def deleteGatherPortrait(personid : String)  : Boolean

}
