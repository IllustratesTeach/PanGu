package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisFingerBbs(
  pkId: String,
  title: Option[String] = None,
  contentss: Option[String] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  modifiedtime: Option[DateTime] = None,
  status: Option[String] = None,
  deletag: Option[String] = None,
  depart: Option[String] = None) {

  def save()(implicit session: DBSession = GafisFingerBbs.autoSession): GafisFingerBbs = GafisFingerBbs.save(this)(session)

  def destroy()(implicit session: DBSession = GafisFingerBbs.autoSession): Unit = GafisFingerBbs.destroy(this)(session)

}


object GafisFingerBbs extends SQLSyntaxSupport[GafisFingerBbs] {

  override val tableName = "GAFIS_FINGER_BBS"

  override val columns = Seq("PK_ID", "TITLE", "CONTENTSS", "INPUTPSN", "INPUTTIME", "MODIFIEDTIME", "STATUS", "DELETAG", "DEPART")

  def apply(gfb: SyntaxProvider[GafisFingerBbs])(rs: WrappedResultSet): GafisFingerBbs = apply(gfb.resultName)(rs)
  def apply(gfb: ResultName[GafisFingerBbs])(rs: WrappedResultSet): GafisFingerBbs = new GafisFingerBbs(
    pkId = rs.get(gfb.pkId),
    title = rs.get(gfb.title),
    contentss = rs.get(gfb.contentss),
    inputpsn = rs.get(gfb.inputpsn),
    inputtime = rs.get(gfb.inputtime),
    modifiedtime = rs.get(gfb.modifiedtime),
    status = rs.get(gfb.status),
    deletag = rs.get(gfb.deletag),
    depart = rs.get(gfb.depart)
  )

  val gfb = GafisFingerBbs.syntax("gfb")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, title: Option[String], contentss: Option[String], inputpsn: Option[String], inputtime: Option[DateTime], modifiedtime: Option[DateTime], status: Option[String], deletag: Option[String], depart: Option[String])(implicit session: DBSession = autoSession): Option[GafisFingerBbs] = {
    withSQL {
      select.from(GafisFingerBbs as gfb).where.eq(gfb.pkId, pkId).and.eq(gfb.title, title).and.eq(gfb.contentss, contentss).and.eq(gfb.inputpsn, inputpsn).and.eq(gfb.inputtime, inputtime).and.eq(gfb.modifiedtime, modifiedtime).and.eq(gfb.status, status).and.eq(gfb.deletag, deletag).and.eq(gfb.depart, depart)
    }.map(GafisFingerBbs(gfb.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisFingerBbs] = {
    withSQL(select.from(GafisFingerBbs as gfb)).map(GafisFingerBbs(gfb.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisFingerBbs as gfb)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisFingerBbs] = {
    withSQL {
      select.from(GafisFingerBbs as gfb).where.append(where)
    }.map(GafisFingerBbs(gfb.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisFingerBbs] = {
    withSQL {
      select.from(GafisFingerBbs as gfb).where.append(where)
    }.map(GafisFingerBbs(gfb.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisFingerBbs as gfb).where.append(where)
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
    depart: Option[String] = None)(implicit session: DBSession = autoSession): GafisFingerBbs = {
    withSQL {
      insert.into(GafisFingerBbs).columns(
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

    GafisFingerBbs(
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

  def save(entity: GafisFingerBbs)(implicit session: DBSession = autoSession): GafisFingerBbs = {
    withSQL {
      update(GafisFingerBbs).set(
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

  def destroy(entity: GafisFingerBbs)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisFingerBbs).where.eq(column.pkId, entity.pkId).and.eq(column.title, entity.title).and.eq(column.contentss, entity.contentss).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.status, entity.status).and.eq(column.deletag, entity.deletag).and.eq(column.depart, entity.depart) }.update.apply()
  }

}
