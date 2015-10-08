package nirvana.hall.api.entities

import scalikejdbc._

case class GafisBbsAttachment(
  pkId: String,
  bbsid: Option[String] = None,
  filename: Option[String] = None,
  fileurl: Option[String] = None,
  filesize: Option[String] = None,
  filetype: Option[String] = None,
  deletag: Option[String] = None) {

  def save()(implicit session: DBSession = GafisBbsAttachment.autoSession): GafisBbsAttachment = GafisBbsAttachment.save(this)(session)

  def destroy()(implicit session: DBSession = GafisBbsAttachment.autoSession): Unit = GafisBbsAttachment.destroy(this)(session)

}


object GafisBbsAttachment extends SQLSyntaxSupport[GafisBbsAttachment] {

  override val tableName = "GAFIS_BBS_ATTACHMENT"

  override val columns = Seq("PK_ID", "BBSID", "FILENAME", "FILEURL", "FILESIZE", "FILETYPE", "DELETAG")

  def apply(gba: SyntaxProvider[GafisBbsAttachment])(rs: WrappedResultSet): GafisBbsAttachment = apply(gba.resultName)(rs)
  def apply(gba: ResultName[GafisBbsAttachment])(rs: WrappedResultSet): GafisBbsAttachment = new GafisBbsAttachment(
    pkId = rs.get(gba.pkId),
    bbsid = rs.get(gba.bbsid),
    filename = rs.get(gba.filename),
    fileurl = rs.get(gba.fileurl),
    filesize = rs.get(gba.filesize),
    filetype = rs.get(gba.filetype),
    deletag = rs.get(gba.deletag)
  )

  val gba = GafisBbsAttachment.syntax("gba")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, bbsid: Option[String], filename: Option[String], fileurl: Option[String], filesize: Option[String], filetype: Option[String], deletag: Option[String])(implicit session: DBSession = autoSession): Option[GafisBbsAttachment] = {
    withSQL {
      select.from(GafisBbsAttachment as gba).where.eq(gba.pkId, pkId).and.eq(gba.bbsid, bbsid).and.eq(gba.filename, filename).and.eq(gba.fileurl, fileurl).and.eq(gba.filesize, filesize).and.eq(gba.filetype, filetype).and.eq(gba.deletag, deletag)
    }.map(GafisBbsAttachment(gba.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisBbsAttachment] = {
    withSQL(select.from(GafisBbsAttachment as gba)).map(GafisBbsAttachment(gba.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisBbsAttachment as gba)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisBbsAttachment] = {
    withSQL {
      select.from(GafisBbsAttachment as gba).where.append(where)
    }.map(GafisBbsAttachment(gba.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisBbsAttachment] = {
    withSQL {
      select.from(GafisBbsAttachment as gba).where.append(where)
    }.map(GafisBbsAttachment(gba.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisBbsAttachment as gba).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    bbsid: Option[String] = None,
    filename: Option[String] = None,
    fileurl: Option[String] = None,
    filesize: Option[String] = None,
    filetype: Option[String] = None,
    deletag: Option[String] = None)(implicit session: DBSession = autoSession): GafisBbsAttachment = {
    withSQL {
      insert.into(GafisBbsAttachment).columns(
        column.pkId,
        column.bbsid,
        column.filename,
        column.fileurl,
        column.filesize,
        column.filetype,
        column.deletag
      ).values(
        pkId,
        bbsid,
        filename,
        fileurl,
        filesize,
        filetype,
        deletag
      )
    }.update.apply()

    GafisBbsAttachment(
      pkId = pkId,
      bbsid = bbsid,
      filename = filename,
      fileurl = fileurl,
      filesize = filesize,
      filetype = filetype,
      deletag = deletag)
  }

  def save(entity: GafisBbsAttachment)(implicit session: DBSession = autoSession): GafisBbsAttachment = {
    withSQL {
      update(GafisBbsAttachment).set(
        column.pkId -> entity.pkId,
        column.bbsid -> entity.bbsid,
        column.filename -> entity.filename,
        column.fileurl -> entity.fileurl,
        column.filesize -> entity.filesize,
        column.filetype -> entity.filetype,
        column.deletag -> entity.deletag
      ).where.eq(column.pkId, entity.pkId).and.eq(column.bbsid, entity.bbsid).and.eq(column.filename, entity.filename).and.eq(column.fileurl, entity.fileurl).and.eq(column.filesize, entity.filesize).and.eq(column.filetype, entity.filetype).and.eq(column.deletag, entity.deletag)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisBbsAttachment)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisBbsAttachment).where.eq(column.pkId, entity.pkId).and.eq(column.bbsid, entity.bbsid).and.eq(column.filename, entity.filename).and.eq(column.fileurl, entity.fileurl).and.eq(column.filesize, entity.filesize).and.eq(column.filetype, entity.filetype).and.eq(column.deletag, entity.deletag) }.update.apply()
  }

}
