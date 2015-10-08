package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisPersonNo(
  pkId: String,
  datetime: Option[DateTime] = None,
  count: Option[Long] = None,
  deptNo: Option[String] = None,
  miss: Option[String] = None,
  ruleId: Option[String] = None) {

  def save()(implicit session: DBSession = GafisPersonNo.autoSession): GafisPersonNo = GafisPersonNo.save(this)(session)

  def destroy()(implicit session: DBSession = GafisPersonNo.autoSession): Unit = GafisPersonNo.destroy(this)(session)

}


object GafisPersonNo extends SQLSyntaxSupport[GafisPersonNo] {

  override val tableName = "GAFIS_PERSON_NO"

  override val columns = Seq("PK_ID", "DATETIME", "COUNT", "DEPT_NO", "MISS", "RULE_ID")

  def apply(gpn: SyntaxProvider[GafisPersonNo])(rs: WrappedResultSet): GafisPersonNo = apply(gpn.resultName)(rs)
  def apply(gpn: ResultName[GafisPersonNo])(rs: WrappedResultSet): GafisPersonNo = new GafisPersonNo(
    pkId = rs.get(gpn.pkId),
    datetime = rs.get(gpn.datetime),
    count = rs.get(gpn.count),
    deptNo = rs.get(gpn.deptNo),
    miss = rs.get(gpn.miss),
    ruleId = rs.get(gpn.ruleId)
  )

  val gpn = GafisPersonNo.syntax("gpn")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, datetime: Option[DateTime], count: Option[Long], deptNo: Option[String], miss: Option[String], ruleId: Option[String])(implicit session: DBSession = autoSession): Option[GafisPersonNo] = {
    withSQL {
      select.from(GafisPersonNo as gpn).where.eq(gpn.pkId, pkId).and.eq(gpn.datetime, datetime).and.eq(gpn.count, count).and.eq(gpn.deptNo, deptNo).and.eq(gpn.miss, miss).and.eq(gpn.ruleId, ruleId)
    }.map(GafisPersonNo(gpn.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisPersonNo] = {
    withSQL(select.from(GafisPersonNo as gpn)).map(GafisPersonNo(gpn.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisPersonNo as gpn)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisPersonNo] = {
    withSQL {
      select.from(GafisPersonNo as gpn).where.append(where)
    }.map(GafisPersonNo(gpn.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisPersonNo] = {
    withSQL {
      select.from(GafisPersonNo as gpn).where.append(where)
    }.map(GafisPersonNo(gpn.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisPersonNo as gpn).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    datetime: Option[DateTime] = None,
    count: Option[Long] = None,
    deptNo: Option[String] = None,
    miss: Option[String] = None,
    ruleId: Option[String] = None)(implicit session: DBSession = autoSession): GafisPersonNo = {
    withSQL {
      insert.into(GafisPersonNo).columns(
        column.pkId,
        column.datetime,
        column.count,
        column.deptNo,
        column.miss,
        column.ruleId
      ).values(
        pkId,
        datetime,
        count,
        deptNo,
        miss,
        ruleId
      )
    }.update.apply()

    GafisPersonNo(
      pkId = pkId,
      datetime = datetime,
      count = count,
      deptNo = deptNo,
      miss = miss,
      ruleId = ruleId)
  }

  def save(entity: GafisPersonNo)(implicit session: DBSession = autoSession): GafisPersonNo = {
    withSQL {
      update(GafisPersonNo).set(
        column.pkId -> entity.pkId,
        column.datetime -> entity.datetime,
        column.count -> entity.count,
        column.deptNo -> entity.deptNo,
        column.miss -> entity.miss,
        column.ruleId -> entity.ruleId
      ).where.eq(column.pkId, entity.pkId).and.eq(column.datetime, entity.datetime).and.eq(column.count, entity.count).and.eq(column.deptNo, entity.deptNo).and.eq(column.miss, entity.miss).and.eq(column.ruleId, entity.ruleId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisPersonNo)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisPersonNo).where.eq(column.pkId, entity.pkId).and.eq(column.datetime, entity.datetime).and.eq(column.count, entity.count).and.eq(column.deptNo, entity.deptNo).and.eq(column.miss, entity.miss).and.eq(column.ruleId, entity.ruleId) }.update.apply()
  }

}
