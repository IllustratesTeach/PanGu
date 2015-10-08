package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisRptLog(
  id: Option[Long] = None,
  procName: Option[String] = None,
  startdate: Option[DateTime] = None,
  description: Option[String] = None) {

  def save()(implicit session: DBSession = GafisRptLog.autoSession): GafisRptLog = GafisRptLog.save(this)(session)

  def destroy()(implicit session: DBSession = GafisRptLog.autoSession): Unit = GafisRptLog.destroy(this)(session)

}


object GafisRptLog extends SQLSyntaxSupport[GafisRptLog] {

  override val tableName = "GAFIS_RPT_LOG"

  override val columns = Seq("ID", "PROC_NAME", "STARTDATE", "DESCRIPTION")

  def apply(grl: SyntaxProvider[GafisRptLog])(rs: WrappedResultSet): GafisRptLog = apply(grl.resultName)(rs)
  def apply(grl: ResultName[GafisRptLog])(rs: WrappedResultSet): GafisRptLog = new GafisRptLog(
    id = rs.get(grl.id),
    procName = rs.get(grl.procName),
    startdate = rs.get(grl.startdate),
    description = rs.get(grl.description)
  )

  val grl = GafisRptLog.syntax("grl")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(id: Option[Long], procName: Option[String], startdate: Option[DateTime], description: Option[String])(implicit session: DBSession = autoSession): Option[GafisRptLog] = {
    withSQL {
      select.from(GafisRptLog as grl).where.eq(grl.id, id).and.eq(grl.procName, procName).and.eq(grl.startdate, startdate).and.eq(grl.description, description)
    }.map(GafisRptLog(grl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisRptLog] = {
    withSQL(select.from(GafisRptLog as grl)).map(GafisRptLog(grl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisRptLog as grl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisRptLog] = {
    withSQL {
      select.from(GafisRptLog as grl).where.append(where)
    }.map(GafisRptLog(grl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisRptLog] = {
    withSQL {
      select.from(GafisRptLog as grl).where.append(where)
    }.map(GafisRptLog(grl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisRptLog as grl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: Option[Long] = None,
    procName: Option[String] = None,
    startdate: Option[DateTime] = None,
    description: Option[String] = None)(implicit session: DBSession = autoSession): GafisRptLog = {
    withSQL {
      insert.into(GafisRptLog).columns(
        column.id,
        column.procName,
        column.startdate,
        column.description
      ).values(
        id,
        procName,
        startdate,
        description
      )
    }.update.apply()

    GafisRptLog(
      id = id,
      procName = procName,
      startdate = startdate,
      description = description)
  }

  def save(entity: GafisRptLog)(implicit session: DBSession = autoSession): GafisRptLog = {
    withSQL {
      update(GafisRptLog).set(
        column.id -> entity.id,
        column.procName -> entity.procName,
        column.startdate -> entity.startdate,
        column.description -> entity.description
      ).where.eq(column.id, entity.id).and.eq(column.procName, entity.procName).and.eq(column.startdate, entity.startdate).and.eq(column.description, entity.description)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisRptLog)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisRptLog).where.eq(column.id, entity.id).and.eq(column.procName, entity.procName).and.eq(column.startdate, entity.startdate).and.eq(column.description, entity.description) }.update.apply()
  }

}
