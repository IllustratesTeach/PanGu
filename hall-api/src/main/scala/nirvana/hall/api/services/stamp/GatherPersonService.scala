package nirvana.hall.api.services.stamp

import nirvana.hall.api.entities.{GafisGatherTypeNodeField, GafisGatherType, GafisPerson}
import nirvana.hall.api.services.AutoSpringDataSourceSession
import org.springframework.transaction.annotation.Transactional
import scalikejdbc.DBSession

/**
 * 人员信息
 * Created by wangjue on 2015/10/20.
 */
trait GatherPersonService {


  /**
   *捺印人员列表
   * @param start:查询开始条数
   * @param limit:查询条数
   */
  def queryGatherPersonList(start : Integer,limit : Integer) (implicit session: DBSession = AutoSpringDataSourceSession.apply()) : List[GafisPerson]


  /**
   * 捺印人员查询
   * @param gatherDateStart 采集开始时间
   * @param gatherDateEnd 采集结束时间
   * @param name  姓名
   * @param idCard  身份证号
   */
  def queryGatherPersonBy(gatherDateStart: String,gatherDateEnd:String,name:String,idCard:String,start: Integer, limit: Integer) (implicit session: DBSession = AutoSpringDataSourceSession.apply()) : List[GafisPerson]

  /**
   * 捺印人员高级查询
   */
  def queryGatherPersonSeniorBy() (implicit session: DBSession = AutoSpringDataSourceSession.apply()) : List[GafisPerson]


  /**
   * 上报
   * @param personid
   * @param uplaodStatus(0:等待上报;1:正在上报;2:完成上报)
   */
  @Transactional
  def uploadGatherPerson(personid : String,uplaodStatus : Integer) (implicit session: DBSession = AutoSpringDataSourceSession.apply()) : Boolean


  /**
   * 人员采集类型查询
   * @return
   */
  def queryGatherType() (implicit session: DBSession = AutoSpringDataSourceSession.apply()) : List[GafisGatherType]


  /**
   * 通过人员类型获取不同的采集字段和必填项
   * @param gatherTypeId
   * @return
   */
  def queryGatherTypeNodeFieldBy(gatherTypeId : String) (implicit session: DBSession = AutoSpringDataSourceSession.apply()) : List[GafisGatherTypeNodeField]














}
