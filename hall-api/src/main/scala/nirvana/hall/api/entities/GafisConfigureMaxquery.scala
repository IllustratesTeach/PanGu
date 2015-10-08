package nirvana.hall.api.entities

import scalikejdbc._

case class GafisConfigureMaxquery(
  pkId: String,
  maxTt: Option[Long] = None,
  maxTl: Option[Long] = None,
  maxLt: Option[Long] = None,
  maxLl: Option[Long] = None,
  roleId: Option[String] = None,
  priority: Option[Long] = None) {

  def save()(implicit session: DBSession = GafisConfigureMaxquery.autoSession): GafisConfigureMaxquery = GafisConfigureMaxquery.save(this)(session)

  def destroy()(implicit session: DBSession = GafisConfigureMaxquery.autoSession): Unit = GafisConfigureMaxquery.destroy(this)(session)

}


object GafisConfigureMaxquery extends SQLSyntaxSupport[GafisConfigureMaxquery] {

  override val tableName = "GAFIS_CONFIGURE_MAXQUERY"

  override val columns = Seq("PK_ID", "MAX_TT", "MAX_TL", "MAX_LT", "MAX_LL", "ROLE_ID", "PRIORITY")

  def apply(gcm: SyntaxProvider[GafisConfigureMaxquery])(rs: WrappedResultSet): GafisConfigureMaxquery = apply(gcm.resultName)(rs)
  def apply(gcm: ResultName[GafisConfigureMaxquery])(rs: WrappedResultSet): GafisConfigureMaxquery = new GafisConfigureMaxquery(
    pkId = rs.get(gcm.pkId),
    maxTt = rs.get(gcm.maxTt),
    maxTl = rs.get(gcm.maxTl),
    maxLt = rs.get(gcm.maxLt),
    maxLl = rs.get(gcm.maxLl),
    roleId = rs.get(gcm.roleId),
    priority = rs.get(gcm.priority)
  )

  val gcm = GafisConfigureMaxquery.syntax("gcm")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, maxTt: Option[Long], maxTl: Option[Long], maxLt: Option[Long], maxLl: Option[Long], roleId: Option[String], priority: Option[Long])(implicit session: DBSession = autoSession): Option[GafisConfigureMaxquery] = {
    withSQL {
      select.from(GafisConfigureMaxquery as gcm).where.eq(gcm.pkId, pkId).and.eq(gcm.maxTt, maxTt).and.eq(gcm.maxTl, maxTl).and.eq(gcm.maxLt, maxLt).and.eq(gcm.maxLl, maxLl).and.eq(gcm.roleId, roleId).and.eq(gcm.priority, priority)
    }.map(GafisConfigureMaxquery(gcm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisConfigureMaxquery] = {
    withSQL(select.from(GafisConfigureMaxquery as gcm)).map(GafisConfigureMaxquery(gcm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisConfigureMaxquery as gcm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisConfigureMaxquery] = {
    withSQL {
      select.from(GafisConfigureMaxquery as gcm).where.append(where)
    }.map(GafisConfigureMaxquery(gcm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisConfigureMaxquery] = {
    withSQL {
      select.from(GafisConfigureMaxquery as gcm).where.append(where)
    }.map(GafisConfigureMaxquery(gcm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisConfigureMaxquery as gcm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    maxTt: Option[Long] = None,
    maxTl: Option[Long] = None,
    maxLt: Option[Long] = None,
    maxLl: Option[Long] = None,
    roleId: Option[String] = None,
    priority: Option[Long] = None)(implicit session: DBSession = autoSession): GafisConfigureMaxquery = {
    withSQL {
      insert.into(GafisConfigureMaxquery).columns(
        column.pkId,
        column.maxTt,
        column.maxTl,
        column.maxLt,
        column.maxLl,
        column.roleId,
        column.priority
      ).values(
        pkId,
        maxTt,
        maxTl,
        maxLt,
        maxLl,
        roleId,
        priority
      )
    }.update.apply()

    GafisConfigureMaxquery(
      pkId = pkId,
      maxTt = maxTt,
      maxTl = maxTl,
      maxLt = maxLt,
      maxLl = maxLl,
      roleId = roleId,
      priority = priority)
  }

  def save(entity: GafisConfigureMaxquery)(implicit session: DBSession = autoSession): GafisConfigureMaxquery = {
    withSQL {
      update(GafisConfigureMaxquery).set(
        column.pkId -> entity.pkId,
        column.maxTt -> entity.maxTt,
        column.maxTl -> entity.maxTl,
        column.maxLt -> entity.maxLt,
        column.maxLl -> entity.maxLl,
        column.roleId -> entity.roleId,
        column.priority -> entity.priority
      ).where.eq(column.pkId, entity.pkId).and.eq(column.maxTt, entity.maxTt).and.eq(column.maxTl, entity.maxTl).and.eq(column.maxLt, entity.maxLt).and.eq(column.maxLl, entity.maxLl).and.eq(column.roleId, entity.roleId).and.eq(column.priority, entity.priority)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisConfigureMaxquery)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisConfigureMaxquery).where.eq(column.pkId, entity.pkId).and.eq(column.maxTt, entity.maxTt).and.eq(column.maxTl, entity.maxTl).and.eq(column.maxLt, entity.maxLt).and.eq(column.maxLl, entity.maxLl).and.eq(column.roleId, entity.roleId).and.eq(column.priority, entity.priority) }.update.apply()
  }

}
