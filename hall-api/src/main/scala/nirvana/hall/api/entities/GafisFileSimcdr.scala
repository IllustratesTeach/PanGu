package nirvana.hall.api.entities

import scalikejdbc._

case class GafisFileSimcdr(
  pkId: String,
  personId: Option[String] = None,
  phoneNum: Option[String] = None,
  fileAttach: Option[String] = None,
  deletag: Option[String] = None) {

  def save()(implicit session: DBSession = GafisFileSimcdr.autoSession): GafisFileSimcdr = GafisFileSimcdr.save(this)(session)

  def destroy()(implicit session: DBSession = GafisFileSimcdr.autoSession): Unit = GafisFileSimcdr.destroy(this)(session)

}


object GafisFileSimcdr extends SQLSyntaxSupport[GafisFileSimcdr] {

  override val tableName = "GAFIS_FILE_SIMCDR"

  override val columns = Seq("PK_ID", "PERSON_ID", "PHONE_NUM", "FILE_ATTACH", "DELETAG")

  def apply(gfs: SyntaxProvider[GafisFileSimcdr])(rs: WrappedResultSet): GafisFileSimcdr = apply(gfs.resultName)(rs)
  def apply(gfs: ResultName[GafisFileSimcdr])(rs: WrappedResultSet): GafisFileSimcdr = new GafisFileSimcdr(
    pkId = rs.get(gfs.pkId),
    personId = rs.get(gfs.personId),
    phoneNum = rs.get(gfs.phoneNum),
    fileAttach = rs.get(gfs.fileAttach),
    deletag = rs.get(gfs.deletag)
  )

  val gfs = GafisFileSimcdr.syntax("gfs")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personId: Option[String], phoneNum: Option[String], fileAttach: Option[String], deletag: Option[String])(implicit session: DBSession = autoSession): Option[GafisFileSimcdr] = {
    withSQL {
      select.from(GafisFileSimcdr as gfs).where.eq(gfs.pkId, pkId).and.eq(gfs.personId, personId).and.eq(gfs.phoneNum, phoneNum).and.eq(gfs.fileAttach, fileAttach).and.eq(gfs.deletag, deletag)
    }.map(GafisFileSimcdr(gfs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisFileSimcdr] = {
    withSQL(select.from(GafisFileSimcdr as gfs)).map(GafisFileSimcdr(gfs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisFileSimcdr as gfs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisFileSimcdr] = {
    withSQL {
      select.from(GafisFileSimcdr as gfs).where.append(where)
    }.map(GafisFileSimcdr(gfs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisFileSimcdr] = {
    withSQL {
      select.from(GafisFileSimcdr as gfs).where.append(where)
    }.map(GafisFileSimcdr(gfs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisFileSimcdr as gfs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personId: Option[String] = None,
    phoneNum: Option[String] = None,
    fileAttach: Option[String] = None,
    deletag: Option[String] = None)(implicit session: DBSession = autoSession): GafisFileSimcdr = {
    withSQL {
      insert.into(GafisFileSimcdr).columns(
        column.pkId,
        column.personId,
        column.phoneNum,
        column.fileAttach,
        column.deletag
      ).values(
        pkId,
        personId,
        phoneNum,
        fileAttach,
        deletag
      )
    }.update.apply()

    GafisFileSimcdr(
      pkId = pkId,
      personId = personId,
      phoneNum = phoneNum,
      fileAttach = fileAttach,
      deletag = deletag)
  }

  def save(entity: GafisFileSimcdr)(implicit session: DBSession = autoSession): GafisFileSimcdr = {
    withSQL {
      update(GafisFileSimcdr).set(
        column.pkId -> entity.pkId,
        column.personId -> entity.personId,
        column.phoneNum -> entity.phoneNum,
        column.fileAttach -> entity.fileAttach,
        column.deletag -> entity.deletag
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.phoneNum, entity.phoneNum).and.eq(column.fileAttach, entity.fileAttach).and.eq(column.deletag, entity.deletag)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisFileSimcdr)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisFileSimcdr).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.phoneNum, entity.phoneNum).and.eq(column.fileAttach, entity.fileAttach).and.eq(column.deletag, entity.deletag) }.update.apply()
  }

}
