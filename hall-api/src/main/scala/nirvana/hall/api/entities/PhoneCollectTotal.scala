package nirvana.hall.api.entities

import scalikejdbc._

case class PhoneCollectTotal(
  personid: Option[String] = None,
  collecttargetId: Option[String] = None,
  total: Option[Long] = None,
  target: Option[String] = None,
  pkId: String) {

  def save()(implicit session: DBSession = PhoneCollectTotal.autoSession): PhoneCollectTotal = PhoneCollectTotal.save(this)(session)

  def destroy()(implicit session: DBSession = PhoneCollectTotal.autoSession): Unit = PhoneCollectTotal.destroy(this)(session)

}


object PhoneCollectTotal extends SQLSyntaxSupport[PhoneCollectTotal] {

  override val tableName = "PHONE_COLLECT_TOTAL"

  override val columns = Seq("PERSONID", "COLLECTTARGET_ID", "TOTAL", "TARGET", "PK_ID")

  def apply(pct: SyntaxProvider[PhoneCollectTotal])(rs: WrappedResultSet): PhoneCollectTotal = apply(pct.resultName)(rs)
  def apply(pct: ResultName[PhoneCollectTotal])(rs: WrappedResultSet): PhoneCollectTotal = new PhoneCollectTotal(
    personid = rs.get(pct.personid),
    collecttargetId = rs.get(pct.collecttargetId),
    total = rs.get(pct.total),
    target = rs.get(pct.target),
    pkId = rs.get(pct.pkId)
  )

  val pct = PhoneCollectTotal.syntax("pct")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(personid: Option[String], collecttargetId: Option[String], total: Option[Long], target: Option[String], pkId: String)(implicit session: DBSession = autoSession): Option[PhoneCollectTotal] = {
    withSQL {
      select.from(PhoneCollectTotal as pct).where.eq(pct.personid, personid).and.eq(pct.collecttargetId, collecttargetId).and.eq(pct.total, total).and.eq(pct.target, target).and.eq(pct.pkId, pkId)
    }.map(PhoneCollectTotal(pct.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PhoneCollectTotal] = {
    withSQL(select.from(PhoneCollectTotal as pct)).map(PhoneCollectTotal(pct.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PhoneCollectTotal as pct)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PhoneCollectTotal] = {
    withSQL {
      select.from(PhoneCollectTotal as pct).where.append(where)
    }.map(PhoneCollectTotal(pct.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PhoneCollectTotal] = {
    withSQL {
      select.from(PhoneCollectTotal as pct).where.append(where)
    }.map(PhoneCollectTotal(pct.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PhoneCollectTotal as pct).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    personid: Option[String] = None,
    collecttargetId: Option[String] = None,
    total: Option[Long] = None,
    target: Option[String] = None,
    pkId: String)(implicit session: DBSession = autoSession): PhoneCollectTotal = {
    withSQL {
      insert.into(PhoneCollectTotal).columns(
        column.personid,
        column.collecttargetId,
        column.total,
        column.target,
        column.pkId
      ).values(
        personid,
        collecttargetId,
        total,
        target,
        pkId
      )
    }.update.apply()

    PhoneCollectTotal(
      personid = personid,
      collecttargetId = collecttargetId,
      total = total,
      target = target,
      pkId = pkId)
  }

  def save(entity: PhoneCollectTotal)(implicit session: DBSession = autoSession): PhoneCollectTotal = {
    withSQL {
      update(PhoneCollectTotal).set(
        column.personid -> entity.personid,
        column.collecttargetId -> entity.collecttargetId,
        column.total -> entity.total,
        column.target -> entity.target,
        column.pkId -> entity.pkId
      ).where.eq(column.personid, entity.personid).and.eq(column.collecttargetId, entity.collecttargetId).and.eq(column.total, entity.total).and.eq(column.target, entity.target).and.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: PhoneCollectTotal)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(PhoneCollectTotal).where.eq(column.personid, entity.personid).and.eq(column.collecttargetId, entity.collecttargetId).and.eq(column.total, entity.total).and.eq(column.target, entity.target).and.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
