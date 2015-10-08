package nirvana.hall.api.entities

import scalikejdbc._

case class GafisLogicDbCase(
  pkId: String,
  logicDbPkid: Option[String] = None,
  casePkid: Option[String] = None) {

  def save()(implicit session: DBSession = GafisLogicDbCase.autoSession): GafisLogicDbCase = GafisLogicDbCase.save(this)(session)

  def destroy()(implicit session: DBSession = GafisLogicDbCase.autoSession): Unit = GafisLogicDbCase.destroy(this)(session)

}


object GafisLogicDbCase extends SQLSyntaxSupport[GafisLogicDbCase] {

  override val tableName = "GAFIS_LOGIC_DB_CASE"

  override val columns = Seq("PK_ID", "LOGIC_DB_PKID", "CASE_PKID")

  def apply(gldc: SyntaxProvider[GafisLogicDbCase])(rs: WrappedResultSet): GafisLogicDbCase = apply(gldc.resultName)(rs)
  def apply(gldc: ResultName[GafisLogicDbCase])(rs: WrappedResultSet): GafisLogicDbCase = new GafisLogicDbCase(
    pkId = rs.get(gldc.pkId),
    logicDbPkid = rs.get(gldc.logicDbPkid),
    casePkid = rs.get(gldc.casePkid)
  )

  val gldc = GafisLogicDbCase.syntax("gldc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, logicDbPkid: Option[String], casePkid: Option[String])(implicit session: DBSession = autoSession): Option[GafisLogicDbCase] = {
    withSQL {
      select.from(GafisLogicDbCase as gldc).where.eq(gldc.pkId, pkId).and.eq(gldc.logicDbPkid, logicDbPkid).and.eq(gldc.casePkid, casePkid)
    }.map(GafisLogicDbCase(gldc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisLogicDbCase] = {
    withSQL(select.from(GafisLogicDbCase as gldc)).map(GafisLogicDbCase(gldc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisLogicDbCase as gldc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisLogicDbCase] = {
    withSQL {
      select.from(GafisLogicDbCase as gldc).where.append(where)
    }.map(GafisLogicDbCase(gldc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisLogicDbCase] = {
    withSQL {
      select.from(GafisLogicDbCase as gldc).where.append(where)
    }.map(GafisLogicDbCase(gldc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisLogicDbCase as gldc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    logicDbPkid: Option[String] = None,
    casePkid: Option[String] = None)(implicit session: DBSession = autoSession): GafisLogicDbCase = {
    withSQL {
      insert.into(GafisLogicDbCase).columns(
        column.pkId,
        column.logicDbPkid,
        column.casePkid
      ).values(
        pkId,
        logicDbPkid,
        casePkid
      )
    }.update.apply()

    GafisLogicDbCase(
      pkId = pkId,
      logicDbPkid = logicDbPkid,
      casePkid = casePkid)
  }

  def save(entity: GafisLogicDbCase)(implicit session: DBSession = autoSession): GafisLogicDbCase = {
    withSQL {
      update(GafisLogicDbCase).set(
        column.pkId -> entity.pkId,
        column.logicDbPkid -> entity.logicDbPkid,
        column.casePkid -> entity.casePkid
      ).where.eq(column.pkId, entity.pkId).and.eq(column.logicDbPkid, entity.logicDbPkid).and.eq(column.casePkid, entity.casePkid)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisLogicDbCase)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisLogicDbCase).where.eq(column.pkId, entity.pkId).and.eq(column.logicDbPkid, entity.logicDbPkid).and.eq(column.casePkid, entity.casePkid) }.update.apply()
  }

}
