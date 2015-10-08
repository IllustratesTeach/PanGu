package nirvana.hall.api.entities

import scalikejdbc._

case class GafisAutoConfigure(
  pkId: String,
  autoTt: Option[Long] = None,
  autoTl: Option[Long] = None,
  autoLt: Option[Long] = None,
  autoLl: Option[Long] = None,
  mark: Option[String] = None) {

  def save()(implicit session: DBSession = GafisAutoConfigure.autoSession): GafisAutoConfigure = GafisAutoConfigure.save(this)(session)

  def destroy()(implicit session: DBSession = GafisAutoConfigure.autoSession): Unit = GafisAutoConfigure.destroy(this)(session)

}


object GafisAutoConfigure extends SQLSyntaxSupport[GafisAutoConfigure] {

  override val tableName = "GAFIS_AUTO_CONFIGURE"

  override val columns = Seq("PK_ID", "AUTO_TT", "AUTO_TL", "AUTO_LT", "AUTO_LL", "MARK")

  def apply(gac: SyntaxProvider[GafisAutoConfigure])(rs: WrappedResultSet): GafisAutoConfigure = apply(gac.resultName)(rs)
  def apply(gac: ResultName[GafisAutoConfigure])(rs: WrappedResultSet): GafisAutoConfigure = new GafisAutoConfigure(
    pkId = rs.get(gac.pkId),
    autoTt = rs.get(gac.autoTt),
    autoTl = rs.get(gac.autoTl),
    autoLt = rs.get(gac.autoLt),
    autoLl = rs.get(gac.autoLl),
    mark = rs.get(gac.mark)
  )

  val gac = GafisAutoConfigure.syntax("gac")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, autoTt: Option[Long], autoTl: Option[Long], autoLt: Option[Long], autoLl: Option[Long], mark: Option[String])(implicit session: DBSession = autoSession): Option[GafisAutoConfigure] = {
    withSQL {
      select.from(GafisAutoConfigure as gac).where.eq(gac.pkId, pkId).and.eq(gac.autoTt, autoTt).and.eq(gac.autoTl, autoTl).and.eq(gac.autoLt, autoLt).and.eq(gac.autoLl, autoLl).and.eq(gac.mark, mark)
    }.map(GafisAutoConfigure(gac.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisAutoConfigure] = {
    withSQL(select.from(GafisAutoConfigure as gac)).map(GafisAutoConfigure(gac.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisAutoConfigure as gac)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisAutoConfigure] = {
    withSQL {
      select.from(GafisAutoConfigure as gac).where.append(where)
    }.map(GafisAutoConfigure(gac.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisAutoConfigure] = {
    withSQL {
      select.from(GafisAutoConfigure as gac).where.append(where)
    }.map(GafisAutoConfigure(gac.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisAutoConfigure as gac).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    autoTt: Option[Long] = None,
    autoTl: Option[Long] = None,
    autoLt: Option[Long] = None,
    autoLl: Option[Long] = None,
    mark: Option[String] = None)(implicit session: DBSession = autoSession): GafisAutoConfigure = {
    withSQL {
      insert.into(GafisAutoConfigure).columns(
        column.pkId,
        column.autoTt,
        column.autoTl,
        column.autoLt,
        column.autoLl,
        column.mark
      ).values(
        pkId,
        autoTt,
        autoTl,
        autoLt,
        autoLl,
        mark
      )
    }.update.apply()

    GafisAutoConfigure(
      pkId = pkId,
      autoTt = autoTt,
      autoTl = autoTl,
      autoLt = autoLt,
      autoLl = autoLl,
      mark = mark)
  }

  def save(entity: GafisAutoConfigure)(implicit session: DBSession = autoSession): GafisAutoConfigure = {
    withSQL {
      update(GafisAutoConfigure).set(
        column.pkId -> entity.pkId,
        column.autoTt -> entity.autoTt,
        column.autoTl -> entity.autoTl,
        column.autoLt -> entity.autoLt,
        column.autoLl -> entity.autoLl,
        column.mark -> entity.mark
      ).where.eq(column.pkId, entity.pkId).and.eq(column.autoTt, entity.autoTt).and.eq(column.autoTl, entity.autoTl).and.eq(column.autoLt, entity.autoLt).and.eq(column.autoLl, entity.autoLl).and.eq(column.mark, entity.mark)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisAutoConfigure)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisAutoConfigure).where.eq(column.pkId, entity.pkId).and.eq(column.autoTt, entity.autoTt).and.eq(column.autoTl, entity.autoTl).and.eq(column.autoLt, entity.autoLt).and.eq(column.autoLl, entity.autoLl).and.eq(column.mark, entity.mark) }.update.apply()
  }

}
