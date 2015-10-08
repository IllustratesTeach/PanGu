package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisFileAttach(
  pkId: String,
  ext: Option[String] = None,
  filePath: Option[String] = None,
  fileName: Option[String] = None,
  note: Option[String] = None,
  deletag: Option[String] = None,
  inputtime: Option[DateTime] = None,
  modifiedtime: Option[DateTime] = None,
  inputpsn: Option[String] = None,
  modifiedpsn: Option[String] = None,
  filesize: Option[String] = None) {

  def save()(implicit session: DBSession = GafisFileAttach.autoSession): GafisFileAttach = GafisFileAttach.save(this)(session)

  def destroy()(implicit session: DBSession = GafisFileAttach.autoSession): Unit = GafisFileAttach.destroy(this)(session)

}


object GafisFileAttach extends SQLSyntaxSupport[GafisFileAttach] {

  override val tableName = "GAFIS_FILE_ATTACH"

  override val columns = Seq("PK_ID", "EXT", "FILE_PATH", "FILE_NAME", "NOTE", "DELETAG", "INPUTTIME", "MODIFIEDTIME", "INPUTPSN", "MODIFIEDPSN", "FILESIZE")

  def apply(gfa: SyntaxProvider[GafisFileAttach])(rs: WrappedResultSet): GafisFileAttach = apply(gfa.resultName)(rs)
  def apply(gfa: ResultName[GafisFileAttach])(rs: WrappedResultSet): GafisFileAttach = new GafisFileAttach(
    pkId = rs.get(gfa.pkId),
    ext = rs.get(gfa.ext),
    filePath = rs.get(gfa.filePath),
    fileName = rs.get(gfa.fileName),
    note = rs.get(gfa.note),
    deletag = rs.get(gfa.deletag),
    inputtime = rs.get(gfa.inputtime),
    modifiedtime = rs.get(gfa.modifiedtime),
    inputpsn = rs.get(gfa.inputpsn),
    modifiedpsn = rs.get(gfa.modifiedpsn),
    filesize = rs.get(gfa.filesize)
  )

  val gfa = GafisFileAttach.syntax("gfa")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, ext: Option[String], filePath: Option[String], fileName: Option[String], note: Option[String], deletag: Option[String], inputtime: Option[DateTime], modifiedtime: Option[DateTime], inputpsn: Option[String], modifiedpsn: Option[String], filesize: Option[String])(implicit session: DBSession = autoSession): Option[GafisFileAttach] = {
    withSQL {
      select.from(GafisFileAttach as gfa).where.eq(gfa.pkId, pkId).and.eq(gfa.ext, ext).and.eq(gfa.filePath, filePath).and.eq(gfa.fileName, fileName).and.eq(gfa.note, note).and.eq(gfa.deletag, deletag).and.eq(gfa.inputtime, inputtime).and.eq(gfa.modifiedtime, modifiedtime).and.eq(gfa.inputpsn, inputpsn).and.eq(gfa.modifiedpsn, modifiedpsn).and.eq(gfa.filesize, filesize)
    }.map(GafisFileAttach(gfa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisFileAttach] = {
    withSQL(select.from(GafisFileAttach as gfa)).map(GafisFileAttach(gfa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisFileAttach as gfa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisFileAttach] = {
    withSQL {
      select.from(GafisFileAttach as gfa).where.append(where)
    }.map(GafisFileAttach(gfa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisFileAttach] = {
    withSQL {
      select.from(GafisFileAttach as gfa).where.append(where)
    }.map(GafisFileAttach(gfa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisFileAttach as gfa).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    ext: Option[String] = None,
    filePath: Option[String] = None,
    fileName: Option[String] = None,
    note: Option[String] = None,
    deletag: Option[String] = None,
    inputtime: Option[DateTime] = None,
    modifiedtime: Option[DateTime] = None,
    inputpsn: Option[String] = None,
    modifiedpsn: Option[String] = None,
    filesize: Option[String] = None)(implicit session: DBSession = autoSession): GafisFileAttach = {
    withSQL {
      insert.into(GafisFileAttach).columns(
        column.pkId,
        column.ext,
        column.filePath,
        column.fileName,
        column.note,
        column.deletag,
        column.inputtime,
        column.modifiedtime,
        column.inputpsn,
        column.modifiedpsn,
        column.filesize
      ).values(
        pkId,
        ext,
        filePath,
        fileName,
        note,
        deletag,
        inputtime,
        modifiedtime,
        inputpsn,
        modifiedpsn,
        filesize
      )
    }.update.apply()

    GafisFileAttach(
      pkId = pkId,
      ext = ext,
      filePath = filePath,
      fileName = fileName,
      note = note,
      deletag = deletag,
      inputtime = inputtime,
      modifiedtime = modifiedtime,
      inputpsn = inputpsn,
      modifiedpsn = modifiedpsn,
      filesize = filesize)
  }

  def save(entity: GafisFileAttach)(implicit session: DBSession = autoSession): GafisFileAttach = {
    withSQL {
      update(GafisFileAttach).set(
        column.pkId -> entity.pkId,
        column.ext -> entity.ext,
        column.filePath -> entity.filePath,
        column.fileName -> entity.fileName,
        column.note -> entity.note,
        column.deletag -> entity.deletag,
        column.inputtime -> entity.inputtime,
        column.modifiedtime -> entity.modifiedtime,
        column.inputpsn -> entity.inputpsn,
        column.modifiedpsn -> entity.modifiedpsn,
        column.filesize -> entity.filesize
      ).where.eq(column.pkId, entity.pkId).and.eq(column.ext, entity.ext).and.eq(column.filePath, entity.filePath).and.eq(column.fileName, entity.fileName).and.eq(column.note, entity.note).and.eq(column.deletag, entity.deletag).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.filesize, entity.filesize)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisFileAttach)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisFileAttach).where.eq(column.pkId, entity.pkId).and.eq(column.ext, entity.ext).and.eq(column.filePath, entity.filePath).and.eq(column.fileName, entity.fileName).and.eq(column.note, entity.note).and.eq(column.deletag, entity.deletag).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.filesize, entity.filesize) }.update.apply()
  }

}
