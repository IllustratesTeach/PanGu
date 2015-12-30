package nirvana.hall.api.entities

import scalikejdbc._

case class GafisQuery7to6(
  oraSid: Long,
  queryId: Long) {

  def save()(implicit session: DBSession = GafisQuery7to6.autoSession): GafisQuery7to6 = GafisQuery7to6.save(this)(session)

  def destroy()(implicit session: DBSession = GafisQuery7to6.autoSession): Unit = GafisQuery7to6.destroy(this)(session)

}


object GafisQuery7to6 extends SQLSyntaxSupport[GafisQuery7to6] {

  override val tableName = "GAFIS_QUERY_7TO6"

  override val columns = Seq("ORA_SID", "QUERY_ID")

  def apply(gq: SyntaxProvider[GafisQuery7to6])(rs: WrappedResultSet): GafisQuery7to6 = apply(gq.resultName)(rs)
  def apply(gq: ResultName[GafisQuery7to6])(rs: WrappedResultSet): GafisQuery7to6 = new GafisQuery7to6(
    oraSid = rs.get(gq.oraSid),
    queryId = rs.get(gq.queryId)
  )

  val gq = GafisQuery7to6.syntax("gq")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(oraSid: Long)(implicit session: DBSession = autoSession): Option[GafisQuery7to6] = {
    withSQL {
      select.from(GafisQuery7to6 as gq).where.eq(gq.oraSid, oraSid)
    }.map(GafisQuery7to6(gq.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisQuery7to6] = {
    withSQL(select.from(GafisQuery7to6 as gq)).map(GafisQuery7to6(gq.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisQuery7to6 as gq)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisQuery7to6] = {
    withSQL {
      select.from(GafisQuery7to6 as gq).where.append(where)
    }.map(GafisQuery7to6(gq.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisQuery7to6] = {
    withSQL {
      select.from(GafisQuery7to6 as gq).where.append(where)
    }.map(GafisQuery7to6(gq.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisQuery7to6 as gq).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    oraSid: Long,
    queryId: Long)(implicit session: DBSession = autoSession): GafisQuery7to6 = {
    withSQL {
      insert.into(GafisQuery7to6).columns(
        column.oraSid,
        column.queryId
      ).values(
        oraSid,
        queryId
      )
    }.update.apply()

    GafisQuery7to6(
      oraSid = oraSid,
      queryId = queryId)
  }

  def save(entity: GafisQuery7to6)(implicit session: DBSession = autoSession): GafisQuery7to6 = {
    withSQL {
      update(GafisQuery7to6).set(
        column.oraSid -> entity.oraSid,
        column.queryId -> entity.queryId
      ).where.eq(column.oraSid, entity.oraSid)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisQuery7to6)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisQuery7to6).where.eq(column.oraSid, entity.oraSid) }.update.apply()
  }

}
