package nirvana.hall.api.entities

import scalikejdbc._

case class GafisPersonNoRule(
  pkId: String,
  rule: Option[String] = None) {

  def save()(implicit session: DBSession = GafisPersonNoRule.autoSession): GafisPersonNoRule = GafisPersonNoRule.save(this)(session)

  def destroy()(implicit session: DBSession = GafisPersonNoRule.autoSession): Unit = GafisPersonNoRule.destroy(this)(session)

}


object GafisPersonNoRule extends SQLSyntaxSupport[GafisPersonNoRule] {

  override val tableName = "GAFIS_PERSON_NO_RULE"

  override val columns = Seq("PK_ID", "RULE")

  def apply(gpnr: SyntaxProvider[GafisPersonNoRule])(rs: WrappedResultSet): GafisPersonNoRule = apply(gpnr.resultName)(rs)
  def apply(gpnr: ResultName[GafisPersonNoRule])(rs: WrappedResultSet): GafisPersonNoRule = new GafisPersonNoRule(
    pkId = rs.get(gpnr.pkId),
    rule = rs.get(gpnr.rule)
  )

  val gpnr = GafisPersonNoRule.syntax("gpnr")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, rule: Option[String])(implicit session: DBSession = autoSession): Option[GafisPersonNoRule] = {
    withSQL {
      select.from(GafisPersonNoRule as gpnr).where.eq(gpnr.pkId, pkId).and.eq(gpnr.rule, rule)
    }.map(GafisPersonNoRule(gpnr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisPersonNoRule] = {
    withSQL(select.from(GafisPersonNoRule as gpnr)).map(GafisPersonNoRule(gpnr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisPersonNoRule as gpnr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisPersonNoRule] = {
    withSQL {
      select.from(GafisPersonNoRule as gpnr).where.append(where)
    }.map(GafisPersonNoRule(gpnr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisPersonNoRule] = {
    withSQL {
      select.from(GafisPersonNoRule as gpnr).where.append(where)
    }.map(GafisPersonNoRule(gpnr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisPersonNoRule as gpnr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    rule: Option[String] = None)(implicit session: DBSession = autoSession): GafisPersonNoRule = {
    withSQL {
      insert.into(GafisPersonNoRule).columns(
        column.pkId,
        column.rule
      ).values(
        pkId,
        rule
      )
    }.update.apply()

    GafisPersonNoRule(
      pkId = pkId,
      rule = rule)
  }

  def save(entity: GafisPersonNoRule)(implicit session: DBSession = autoSession): GafisPersonNoRule = {
    withSQL {
      update(GafisPersonNoRule).set(
        column.pkId -> entity.pkId,
        column.rule -> entity.rule
      ).where.eq(column.pkId, entity.pkId).and.eq(column.rule, entity.rule)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisPersonNoRule)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisPersonNoRule).where.eq(column.pkId, entity.pkId).and.eq(column.rule, entity.rule) }.update.apply()
  }

}
