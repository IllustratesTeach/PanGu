package nirvana.hall.api.entities

import scalikejdbc._

case class GafisFingerBbsAttachment(
  pkId: String,
  bbsid: Option[String] = None,
  filename: Option[String] = None,
  fileurl: Option[String] = None,
  filesize: Option[String] = None,
  filetype: Option[String] = None,
  deletag: Option[String] = None) {

  def save()(implicit session: DBSession = GafisFingerBbsAttachment.autoSession): GafisFingerBbsAttachment = GafisFingerBbsAttachment.save(this)(session)

  def destroy()(implicit session: DBSession = GafisFingerBbsAttachment.autoSession): Unit = GafisFingerBbsAttachment.destroy(this)(session)

}


object GafisFingerBbsAttachment extends SQLSyntaxSupport[GafisFingerBbsAttachment] {

  override val tableName = "GAFIS_FINGER_BBS_ATTACHMENT"

  override val columns = Seq("PK_ID", "BBSID", "FILENAME", "FILEURL", "FILESIZE", "FILETYPE", "DELETAG")

  def apply(gfba: SyntaxProvider[GafisFingerBbsAttachment])(rs: WrappedResultSet): GafisFingerBbsAttachment = apply(gfba.resultName)(rs)
  def apply(gfba: ResultName[GafisFingerBbsAttachment])(rs: WrappedResultSet): GafisFingerBbsAttachment = new GafisFingerBbsAttachment(
    pkId = rs.get(gfba.pkId),
    bbsid = rs.get(gfba.bbsid),
    filename = rs.get(gfba.filename),
    fileurl = rs.get(gfba.fileurl),
    filesize = rs.get(gfba.filesize),
    filetype = rs.get(gfba.filetype),
    deletag = rs.get(gfba.deletag)
  )

  val gfba = GafisFingerBbsAttachment.syntax("gfba")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, bbsid: Option[String], filename: Option[String], fileurl: Option[String], filesize: Option[String], filetype: Option[String], deletag: Option[String])(implicit session: DBSession = autoSession): Option[GafisFingerBbsAttachment] = {
    withSQL {
      select.from(GafisFingerBbsAttachment as gfba).where.eq(gfba.pkId, pkId).and.eq(gfba.bbsid, bbsid).and.eq(gfba.filename, filename).and.eq(gfba.fileurl, fileurl).and.eq(gfba.filesize, filesize).and.eq(gfba.filetype, filetype).and.eq(gfba.deletag, deletag)
    }.map(GafisFingerBbsAttachment(gfba.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisFingerBbsAttachment] = {
    withSQL(select.from(GafisFingerBbsAttachment as gfba)).map(GafisFingerBbsAttachment(gfba.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisFingerBbsAttachment as gfba)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisFingerBbsAttachment] = {
    withSQL {
      select.from(GafisFingerBbsAttachment as gfba).where.append(where)
    }.map(GafisFingerBbsAttachment(gfba.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisFingerBbsAttachment] = {
    withSQL {
      select.from(GafisFingerBbsAttachment as gfba).where.append(where)
    }.map(GafisFingerBbsAttachment(gfba.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisFingerBbsAttachment as gfba).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    bbsid: Option[String] = None,
    filename: Option[String] = None,
    fileurl: Option[String] = None,
    filesize: Option[String] = None,
    filetype: Option[String] = None,
    deletag: Option[String] = None)(implicit session: DBSession = autoSession): GafisFingerBbsAttachment = {
    withSQL {
      insert.into(GafisFingerBbsAttachment).columns(
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

    GafisFingerBbsAttachment(
      pkId = pkId,
      bbsid = bbsid,
      filename = filename,
      fileurl = fileurl,
      filesize = filesize,
      filetype = filetype,
      deletag = deletag)
  }

  def save(entity: GafisFingerBbsAttachment)(implicit session: DBSession = autoSession): GafisFingerBbsAttachment = {
    withSQL {
      update(GafisFingerBbsAttachment).set(
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

  def destroy(entity: GafisFingerBbsAttachment)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisFingerBbsAttachment).where.eq(column.pkId, entity.pkId).and.eq(column.bbsid, entity.bbsid).and.eq(column.filename, entity.filename).and.eq(column.fileurl, entity.fileurl).and.eq(column.filesize, entity.filesize).and.eq(column.filetype, entity.filetype).and.eq(column.deletag, entity.deletag) }.update.apply()
  }

}
