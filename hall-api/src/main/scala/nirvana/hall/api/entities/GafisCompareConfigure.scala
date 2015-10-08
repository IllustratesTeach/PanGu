package nirvana.hall.api.entities

import scalikejdbc._

case class GafisCompareConfigure(
  pkId: String,
  configureTt: Option[Long] = None,
  configureTl: Option[Long] = None,
  configureLt: Option[Long] = None,
  configureLl: Option[Long] = None,
  roleId: Option[String] = None,
  mark: Option[String] = None) {

  def save()(implicit session: DBSession = GafisCompareConfigure.autoSession): GafisCompareConfigure = GafisCompareConfigure.save(this)(session)

  def destroy()(implicit session: DBSession = GafisCompareConfigure.autoSession): Unit = GafisCompareConfigure.destroy(this)(session)

}


object GafisCompareConfigure extends SQLSyntaxSupport[GafisCompareConfigure] {

  override val tableName = "GAFIS_COMPARE_CONFIGURE"

  override val columns = Seq("PK_ID", "CONFIGURE_TT", "CONFIGURE_TL", "CONFIGURE_LT", "CONFIGURE_LL", "ROLE_ID", "MARK")

  def apply(gcc: SyntaxProvider[GafisCompareConfigure])(rs: WrappedResultSet): GafisCompareConfigure = apply(gcc.resultName)(rs)
  def apply(gcc: ResultName[GafisCompareConfigure])(rs: WrappedResultSet): GafisCompareConfigure = new GafisCompareConfigure(
    pkId = rs.get(gcc.pkId),
    configureTt = rs.get(gcc.configureTt),
    configureTl = rs.get(gcc.configureTl),
    configureLt = rs.get(gcc.configureLt),
    configureLl = rs.get(gcc.configureLl),
    roleId = rs.get(gcc.roleId),
    mark = rs.get(gcc.mark)
  )

  val gcc = GafisCompareConfigure.syntax("gcc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, configureTt: Option[Long], configureTl: Option[Long], configureLt: Option[Long], configureLl: Option[Long], roleId: Option[String], mark: Option[String])(implicit session: DBSession = autoSession): Option[GafisCompareConfigure] = {
    withSQL {
      select.from(GafisCompareConfigure as gcc).where.eq(gcc.pkId, pkId).and.eq(gcc.configureTt, configureTt).and.eq(gcc.configureTl, configureTl).and.eq(gcc.configureLt, configureLt).and.eq(gcc.configureLl, configureLl).and.eq(gcc.roleId, roleId).and.eq(gcc.mark, mark)
    }.map(GafisCompareConfigure(gcc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisCompareConfigure] = {
    withSQL(select.from(GafisCompareConfigure as gcc)).map(GafisCompareConfigure(gcc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisCompareConfigure as gcc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisCompareConfigure] = {
    withSQL {
      select.from(GafisCompareConfigure as gcc).where.append(where)
    }.map(GafisCompareConfigure(gcc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisCompareConfigure] = {
    withSQL {
      select.from(GafisCompareConfigure as gcc).where.append(where)
    }.map(GafisCompareConfigure(gcc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisCompareConfigure as gcc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    configureTt: Option[Long] = None,
    configureTl: Option[Long] = None,
    configureLt: Option[Long] = None,
    configureLl: Option[Long] = None,
    roleId: Option[String] = None,
    mark: Option[String] = None)(implicit session: DBSession = autoSession): GafisCompareConfigure = {
    withSQL {
      insert.into(GafisCompareConfigure).columns(
        column.pkId,
        column.configureTt,
        column.configureTl,
        column.configureLt,
        column.configureLl,
        column.roleId,
        column.mark
      ).values(
        pkId,
        configureTt,
        configureTl,
        configureLt,
        configureLl,
        roleId,
        mark
      )
    }.update.apply()

    GafisCompareConfigure(
      pkId = pkId,
      configureTt = configureTt,
      configureTl = configureTl,
      configureLt = configureLt,
      configureLl = configureLl,
      roleId = roleId,
      mark = mark)
  }

  def save(entity: GafisCompareConfigure)(implicit session: DBSession = autoSession): GafisCompareConfigure = {
    withSQL {
      update(GafisCompareConfigure).set(
        column.pkId -> entity.pkId,
        column.configureTt -> entity.configureTt,
        column.configureTl -> entity.configureTl,
        column.configureLt -> entity.configureLt,
        column.configureLl -> entity.configureLl,
        column.roleId -> entity.roleId,
        column.mark -> entity.mark
      ).where.eq(column.pkId, entity.pkId).and.eq(column.configureTt, entity.configureTt).and.eq(column.configureTl, entity.configureTl).and.eq(column.configureLt, entity.configureLt).and.eq(column.configureLl, entity.configureLl).and.eq(column.roleId, entity.roleId).and.eq(column.mark, entity.mark)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisCompareConfigure)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisCompareConfigure).where.eq(column.pkId, entity.pkId).and.eq(column.configureTt, entity.configureTt).and.eq(column.configureTl, entity.configureTl).and.eq(column.configureLt, entity.configureLt).and.eq(column.configureLl, entity.configureLl).and.eq(column.roleId, entity.roleId).and.eq(column.mark, entity.mark) }.update.apply()
  }

}
