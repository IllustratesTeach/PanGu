package nirvana.hall.api.internal.stamp

import nirvana.hall.api.entities.{GafisGatherTypeNodeField, GafisGatherType, GafisPerson}
import nirvana.hall.api.services.stamp.GatherPersonService
import scalikejdbc._

/**
 * Created by wangjue on 2015/10/27.
 */
class GatherPersonServiceImpl extends GatherPersonService{
  val gp = GafisPerson.syntax("gp")
  /**
   * 捺印人员列表
   * @param start:查询开始条数
   * @param limit:查询条数
   */
  override def queryGatherPersonList(start: Integer, limit: Integer) (implicit session: DBSession = GafisPerson.autoSession) : List[GafisPerson] = {
    withSQL {
      select.from(GafisPerson as gp).limit(limit).offset(start)
    }.map(GafisPerson(gp)).list.apply()
  }

  /**
   * 上报
   * @param personid
   * @param uplaodStatus(0:等待上报;1:正在上报;2:完成上报)
   */
  override def uploadGatherPerson(personid: String, uplaodStatus: Integer) (implicit session: DBSession = GafisPerson.autoSession): Boolean = {
    try {
      withSQL {update(GafisPerson).set(GafisPerson.column.status -> uplaodStatus).
        where.eq(GafisPerson.column.personid,personid)}.
          update.apply()
      true
    } catch {
      case exception: Exception => false
    }
  }

  /**
   * 捺印人员查询
   * @param gatherDateStart 采集开始时间
   * @param gatherDateEnd 采集结束时间
   * @param name  姓名
   * @param idCard  身份证号
   * @param start:查询开始条数
   * @param limit:查询条数
   */
  override def queryGatherPersonBy(gatherDateStart: String, gatherDateEnd: String, name: String, idCard: String,start: Integer, limit: Integer) (implicit session: DBSession = GafisPerson.autoSession): List[GafisPerson] = {
    withSQL {
      select.from(GafisPerson as gp).where.eq(GafisPerson.column.name,name).and.
        eq(GafisPerson.column.idcardno,idCard).and.between(GafisPerson.column.gatherDate,gatherDateStart,gatherDateEnd).
          orderBy(GafisPerson.column.gatherDate).desc.
            limit(limit).
              offset(start)
    }.map(GafisPerson(gp.resultName)).list.apply()
  }

  /**
   * 捺印人员高级查询
   */
  override def queryGatherPersonSeniorBy() (implicit session: DBSession = GafisPerson.autoSession): List[GafisPerson] = ???


  /**
   * 人员采集类型查询
   * @return
   */
  def queryGatherType() (implicit session: DBSession = GafisGatherType.autoSession) : List[GafisGatherType] = {
    GafisGatherType.findAll()
  }



  /**
   * 通过人员类型获取不同的采集字段和必填项
   * @param gatherTypeId
   * @return
   */
  def queryGatherTypeNodeFieldBy(gatherTypeId : String) (implicit session: DBSession = GafisGatherTypeNodeField.autoSession) : List[GafisGatherTypeNodeField] = {
    GafisGatherTypeNodeField.findAllBy(sqls.eq(GafisGatherTypeNodeField.column.typeId,gatherTypeId))
  }





}
