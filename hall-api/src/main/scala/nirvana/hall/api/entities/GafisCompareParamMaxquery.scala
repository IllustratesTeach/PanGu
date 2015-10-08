package nirvana.hall.api.entities

import scalikejdbc._

case class GafisCompareParamMaxquery(
  pkId: String,
  roleId: Option[String] = None,
  roleName: Option[String] = None,
  queryType: Option[String] = None,
  queryLevel: Option[String] = None,
  maxQuery: Option[Int] = None) {

  def save()(implicit session: DBSession = GafisCompareParamMaxquery.autoSession): GafisCompareParamMaxquery = GafisCompareParamMaxquery.save(this)(session)

  def destroy()(implicit session: DBSession = GafisCompareParamMaxquery.autoSession): Unit = GafisCompareParamMaxquery.destroy(this)(session)

}


object GafisCompareParamMaxquery extends SQLSyntaxSupport[GafisCompareParamMaxquery] {

  override val tableName = "GAFIS_COMPARE_PARAM_MAXQUERY"

  override val columns = Seq("PK_ID", "ROLE_ID", "ROLE_NAME", "QUERY_TYPE", "QUERY_LEVEL", "MAX_QUERY")

  def apply(gcpm: SyntaxProvider[GafisCompareParamMaxquery])(rs: WrappedResultSet): GafisCompareParamMaxquery = apply(gcpm.resultName)(rs)
  def apply(gcpm: ResultName[GafisCompareParamMaxquery])(rs: WrappedResultSet): GafisCompareParamMaxquery = new GafisCompareParamMaxquery(
    pkId = rs.get(gcpm.pkId),
    roleId = rs.get(gcpm.roleId),
    roleName = rs.get(gcpm.roleName),
    queryType = rs.get(gcpm.queryType),
    queryLevel = rs.get(gcpm.queryLevel),
    maxQuery = rs.get(gcpm.maxQuery)
  )

  val gcpm = GafisCompareParamMaxquery.syntax("gcpm")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, roleId: Option[String], roleName: Option[String], queryType: Option[String], queryLevel: Option[String], maxQuery: Option[Int])(implicit session: DBSession = autoSession): Option[GafisCompareParamMaxquery] = {
    withSQL {
      select.from(GafisCompareParamMaxquery as gcpm).where.eq(gcpm.pkId, pkId).and.eq(gcpm.roleId, roleId).and.eq(gcpm.roleName, roleName).and.eq(gcpm.queryType, queryType).and.eq(gcpm.queryLevel, queryLevel).and.eq(gcpm.maxQuery, maxQuery)
    }.map(GafisCompareParamMaxquery(gcpm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisCompareParamMaxquery] = {
    withSQL(select.from(GafisCompareParamMaxquery as gcpm)).map(GafisCompareParamMaxquery(gcpm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisCompareParamMaxquery as gcpm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisCompareParamMaxquery] = {
    withSQL {
      select.from(GafisCompareParamMaxquery as gcpm).where.append(where)
    }.map(GafisCompareParamMaxquery(gcpm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisCompareParamMaxquery] = {
    withSQL {
      select.from(GafisCompareParamMaxquery as gcpm).where.append(where)
    }.map(GafisCompareParamMaxquery(gcpm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisCompareParamMaxquery as gcpm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    roleId: Option[String] = None,
    roleName: Option[String] = None,
    queryType: Option[String] = None,
    queryLevel: Option[String] = None,
    maxQuery: Option[Int] = None)(implicit session: DBSession = autoSession): GafisCompareParamMaxquery = {
    withSQL {
      insert.into(GafisCompareParamMaxquery).columns(
        column.pkId,
        column.roleId,
        column.roleName,
        column.queryType,
        column.queryLevel,
        column.maxQuery
      ).values(
        pkId,
        roleId,
        roleName,
        queryType,
        queryLevel,
        maxQuery
      )
    }.update.apply()

    GafisCompareParamMaxquery(
      pkId = pkId,
      roleId = roleId,
      roleName = roleName,
      queryType = queryType,
      queryLevel = queryLevel,
      maxQuery = maxQuery)
  }

  def save(entity: GafisCompareParamMaxquery)(implicit session: DBSession = autoSession): GafisCompareParamMaxquery = {
    withSQL {
      update(GafisCompareParamMaxquery).set(
        column.pkId -> entity.pkId,
        column.roleId -> entity.roleId,
        column.roleName -> entity.roleName,
        column.queryType -> entity.queryType,
        column.queryLevel -> entity.queryLevel,
        column.maxQuery -> entity.maxQuery
      ).where.eq(column.pkId, entity.pkId).and.eq(column.roleId, entity.roleId).and.eq(column.roleName, entity.roleName).and.eq(column.queryType, entity.queryType).and.eq(column.queryLevel, entity.queryLevel).and.eq(column.maxQuery, entity.maxQuery)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisCompareParamMaxquery)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisCompareParamMaxquery).where.eq(column.pkId, entity.pkId).and.eq(column.roleId, entity.roleId).and.eq(column.roleName, entity.roleName).and.eq(column.queryType, entity.queryType).and.eq(column.queryLevel, entity.queryLevel).and.eq(column.maxQuery, entity.maxQuery) }.update.apply()
  }

}
