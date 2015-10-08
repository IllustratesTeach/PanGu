package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisBbs(
  pkId: String,
  title: Option[String] = None,
  contentss: Option[String] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  modifiedtime: Option[DateTime] = None,
  status: Option[String] = None,
  deletag: Option[String] = None,
  depart: Option[String] = None) {

  def save()(implicit session: DBSession = GafisBbs.autoSession): GafisBbs = GafisBbs.save(this)(session)

  def destroy()(implicit session: DBSession = GafisBbs.autoSession): Unit = GafisBbs.destroy(this)(session)

}


object GafisBbs extends SQLSyntaxSupport[GafisBbs] {

  override val tableName = "GAFIS_BBS"

  override val columns = Seq("PK_ID", "TITLE", "CONTENTSS", "INPUTPSN", "INPUTTIME", "MODIFIEDTIME", "STATUS", "DELETAG", "DEPART")

  def apply(gb: SyntaxProvider[GafisBbs])(rs: WrappedResultSet): GafisBbs = apply(gb.resultName)(rs)
  def apply(gb: ResultName[GafisBbs])(rs: WrappedResultSet): GafisBbs = new GafisBbs(
    pkId = rs.get(gb.pkId),
    title = rs.get(gb.title),
    contentss = rs.get(gb.contentss),
    inputpsn = rs.get(gb.inputpsn),
    inputtime = rs.get(gb.inputtime),
    modifiedtime = rs.get(gb.modifiedtime),
    status = rs.get(gb.status),
    deletag = rs.get(gb.deletag),
    depart = rs.get(gb.depart)
  )

  val gb = GafisBbs.syntax("gb")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, title: Option[String], contentss: Option[String], inputpsn: Option[String], inputtime: Option[DateTime], modifiedtime: Option[DateTime], status: Option[String], deletag: Option[String], depart: Option[String])(implicit session: DBSession = autoSession): Option[GafisBbs] = {
    withSQL {
      select.from(GafisBbs as gb).where.eq(gb.pkId, pkId).and.eq(gb.title, title).and.eq(gb.contentss, contentss).and.eq(gb.inputpsn, inputpsn).and.eq(gb.inputtime, inputtime).and.eq(gb.modifiedtime, modifiedtime).and.eq(gb.status, status).and.eq(gb.deletag, deletag).and.eq(gb.depart, depart)
    }.map(GafisBbs(gb.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisBbs] = {
    withSQL(select.from(GafisBbs as gb)).map(GafisBbs(gb.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisBbs as gb)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisBbs] = {
    withSQL {
      select.from(GafisBbs as gb).where.append(where)
    }.map(GafisBbs(gb.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisBbs] = {
    withSQL {
      select.from(GafisBbs as gb).where.append(where)
    }.map(GafisBbs(gb.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisBbs as gb).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    title: Option[String] = None,
    contentss: Option[String] = None,
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    modifiedtime: Option[DateTime] = None,
    status: Option[String] = None,
    deletag: Option[String] = None,
    depart: Option[String] = None)(implicit session: DBSession = autoSession): GafisBbs = {
    withSQL {
      insert.into(GafisBbs).columns(
        column.pkId,
        column.title,
        column.contentss,
        column.inputpsn,
        column.inputtime,
        column.modifiedtime,
        column.status,
        column.deletag,
        column.depart
      ).values(
        pkId,
        title,
        contentss,
        inputpsn,
        inputtime,
        modifiedtime,
        status,
        deletag,
        depart
      )
    }.update.apply()

    GafisBbs(
      pkId = pkId,
      title = title,
      contentss = contentss,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedtime = modifiedtime,
      status = status,
      deletag = deletag,
      depart = depart)
  }

  def save(entity: GafisBbs)(implicit session: DBSession = autoSession): GafisBbs = {
    withSQL {
      update(GafisBbs).set(
        column.pkId -> entity.pkId,
        column.title -> entity.title,
        column.contentss -> entity.contentss,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedtime -> entity.modifiedtime,
        column.status -> entity.status,
        column.deletag -> entity.deletag,
        column.depart -> entity.depart
      ).where.eq(column.pkId, entity.pkId).and.eq(column.title, entity.title).and.eq(column.contentss, entity.contentss).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.status, entity.status).and.eq(column.deletag, entity.deletag).and.eq(column.depart, entity.depart)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisBbs)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisBbs).where.eq(column.pkId, entity.pkId).and.eq(column.title, entity.title).and.eq(column.contentss, entity.contentss).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.status, entity.status).and.eq(column.deletag, entity.deletag).and.eq(column.depart, entity.depart) }.update.apply()
  }

}
