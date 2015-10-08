package nirvana.hall.api.entities

import scalikejdbc._

case class GafisCompareParamPriority(
  pkId: String,
  prioritySt: Option[String] = None,
  prioritySz: Option[String] = None,
  priorityXj: Option[String] = None,
  defaultPrioritySt: Option[String] = None,
  defaultPrioritySz: Option[String] = None,
  defaultPriorityXj: Option[String] = None,
  ttDefaultLevel: Option[String] = None,
  tlDefaultLevel: Option[String] = None) {

  def save()(implicit session: DBSession = GafisCompareParamPriority.autoSession): GafisCompareParamPriority = GafisCompareParamPriority.save(this)(session)

  def destroy()(implicit session: DBSession = GafisCompareParamPriority.autoSession): Unit = GafisCompareParamPriority.destroy(this)(session)

}


object GafisCompareParamPriority extends SQLSyntaxSupport[GafisCompareParamPriority] {

  override val tableName = "GAFIS_COMPARE_PARAM_PRIORITY"

  override val columns = Seq("PK_ID", "PRIORITY_ST", "PRIORITY_SZ", "PRIORITY_XJ", "DEFAULT_PRIORITY_ST", "DEFAULT_PRIORITY_SZ", "DEFAULT_PRIORITY_XJ", "TT_DEFAULT_LEVEL", "TL_DEFAULT_LEVEL")

  def apply(gcpp: SyntaxProvider[GafisCompareParamPriority])(rs: WrappedResultSet): GafisCompareParamPriority = apply(gcpp.resultName)(rs)
  def apply(gcpp: ResultName[GafisCompareParamPriority])(rs: WrappedResultSet): GafisCompareParamPriority = new GafisCompareParamPriority(
    pkId = rs.get(gcpp.pkId),
    prioritySt = rs.get(gcpp.prioritySt),
    prioritySz = rs.get(gcpp.prioritySz),
    priorityXj = rs.get(gcpp.priorityXj),
    defaultPrioritySt = rs.get(gcpp.defaultPrioritySt),
    defaultPrioritySz = rs.get(gcpp.defaultPrioritySz),
    defaultPriorityXj = rs.get(gcpp.defaultPriorityXj),
    ttDefaultLevel = rs.get(gcpp.ttDefaultLevel),
    tlDefaultLevel = rs.get(gcpp.tlDefaultLevel)
  )

  val gcpp = GafisCompareParamPriority.syntax("gcpp")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, prioritySt: Option[String], prioritySz: Option[String], priorityXj: Option[String], defaultPrioritySt: Option[String], defaultPrioritySz: Option[String], defaultPriorityXj: Option[String], ttDefaultLevel: Option[String], tlDefaultLevel: Option[String])(implicit session: DBSession = autoSession): Option[GafisCompareParamPriority] = {
    withSQL {
      select.from(GafisCompareParamPriority as gcpp).where.eq(gcpp.pkId, pkId).and.eq(gcpp.prioritySt, prioritySt).and.eq(gcpp.prioritySz, prioritySz).and.eq(gcpp.priorityXj, priorityXj).and.eq(gcpp.defaultPrioritySt, defaultPrioritySt).and.eq(gcpp.defaultPrioritySz, defaultPrioritySz).and.eq(gcpp.defaultPriorityXj, defaultPriorityXj).and.eq(gcpp.ttDefaultLevel, ttDefaultLevel).and.eq(gcpp.tlDefaultLevel, tlDefaultLevel)
    }.map(GafisCompareParamPriority(gcpp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisCompareParamPriority] = {
    withSQL(select.from(GafisCompareParamPriority as gcpp)).map(GafisCompareParamPriority(gcpp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisCompareParamPriority as gcpp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisCompareParamPriority] = {
    withSQL {
      select.from(GafisCompareParamPriority as gcpp).where.append(where)
    }.map(GafisCompareParamPriority(gcpp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisCompareParamPriority] = {
    withSQL {
      select.from(GafisCompareParamPriority as gcpp).where.append(where)
    }.map(GafisCompareParamPriority(gcpp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisCompareParamPriority as gcpp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    prioritySt: Option[String] = None,
    prioritySz: Option[String] = None,
    priorityXj: Option[String] = None,
    defaultPrioritySt: Option[String] = None,
    defaultPrioritySz: Option[String] = None,
    defaultPriorityXj: Option[String] = None,
    ttDefaultLevel: Option[String] = None,
    tlDefaultLevel: Option[String] = None)(implicit session: DBSession = autoSession): GafisCompareParamPriority = {
    withSQL {
      insert.into(GafisCompareParamPriority).columns(
        column.pkId,
        column.prioritySt,
        column.prioritySz,
        column.priorityXj,
        column.defaultPrioritySt,
        column.defaultPrioritySz,
        column.defaultPriorityXj,
        column.ttDefaultLevel,
        column.tlDefaultLevel
      ).values(
        pkId,
        prioritySt,
        prioritySz,
        priorityXj,
        defaultPrioritySt,
        defaultPrioritySz,
        defaultPriorityXj,
        ttDefaultLevel,
        tlDefaultLevel
      )
    }.update.apply()

    GafisCompareParamPriority(
      pkId = pkId,
      prioritySt = prioritySt,
      prioritySz = prioritySz,
      priorityXj = priorityXj,
      defaultPrioritySt = defaultPrioritySt,
      defaultPrioritySz = defaultPrioritySz,
      defaultPriorityXj = defaultPriorityXj,
      ttDefaultLevel = ttDefaultLevel,
      tlDefaultLevel = tlDefaultLevel)
  }

  def save(entity: GafisCompareParamPriority)(implicit session: DBSession = autoSession): GafisCompareParamPriority = {
    withSQL {
      update(GafisCompareParamPriority).set(
        column.pkId -> entity.pkId,
        column.prioritySt -> entity.prioritySt,
        column.prioritySz -> entity.prioritySz,
        column.priorityXj -> entity.priorityXj,
        column.defaultPrioritySt -> entity.defaultPrioritySt,
        column.defaultPrioritySz -> entity.defaultPrioritySz,
        column.defaultPriorityXj -> entity.defaultPriorityXj,
        column.ttDefaultLevel -> entity.ttDefaultLevel,
        column.tlDefaultLevel -> entity.tlDefaultLevel
      ).where.eq(column.pkId, entity.pkId).and.eq(column.prioritySt, entity.prioritySt).and.eq(column.prioritySz, entity.prioritySz).and.eq(column.priorityXj, entity.priorityXj).and.eq(column.defaultPrioritySt, entity.defaultPrioritySt).and.eq(column.defaultPrioritySz, entity.defaultPrioritySz).and.eq(column.defaultPriorityXj, entity.defaultPriorityXj).and.eq(column.ttDefaultLevel, entity.ttDefaultLevel).and.eq(column.tlDefaultLevel, entity.tlDefaultLevel)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisCompareParamPriority)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisCompareParamPriority).where.eq(column.pkId, entity.pkId).and.eq(column.prioritySt, entity.prioritySt).and.eq(column.prioritySz, entity.prioritySz).and.eq(column.priorityXj, entity.priorityXj).and.eq(column.defaultPrioritySt, entity.defaultPrioritySt).and.eq(column.defaultPrioritySz, entity.defaultPrioritySz).and.eq(column.defaultPriorityXj, entity.defaultPriorityXj).and.eq(column.ttDefaultLevel, entity.ttDefaultLevel).and.eq(column.tlDefaultLevel, entity.tlDefaultLevel) }.update.apply()
  }

}
