package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisGatherPersonPhoto(
  pkId: String,
  personid: Option[String] = None,
  photoCode: String,
  photoData: Blob,
  photoOrg: Option[Long] = None,
  inputpsn: Option[String] = None,
  inputtime: DateTime,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  comments: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherPersonPhoto.autoSession): GafisGatherPersonPhoto = GafisGatherPersonPhoto.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherPersonPhoto.autoSession): Unit = GafisGatherPersonPhoto.destroy(this)(session)

}


object GafisGatherPersonPhoto extends SQLSyntaxSupport[GafisGatherPersonPhoto] {

  override val tableName = "GAFIS_GATHER_PERSON_PHOTO"

  override val columns = Seq("PK_ID", "PERSONID", "PHOTO_CODE", "PHOTO_DATA", "PHOTO_ORG", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "COMMENTS")

  def apply(ggpp: SyntaxProvider[GafisGatherPersonPhoto])(rs: WrappedResultSet): GafisGatherPersonPhoto = apply(ggpp.resultName)(rs)
  def apply(ggpp: ResultName[GafisGatherPersonPhoto])(rs: WrappedResultSet): GafisGatherPersonPhoto = new GafisGatherPersonPhoto(
    pkId = rs.get(ggpp.pkId),
    personid = rs.get(ggpp.personid),
    photoCode = rs.get(ggpp.photoCode),
    photoData = rs.get(ggpp.photoData),
    photoOrg = rs.get(ggpp.photoOrg),
    inputpsn = rs.get(ggpp.inputpsn),
    inputtime = rs.get(ggpp.inputtime),
    modifiedpsn = rs.get(ggpp.modifiedpsn),
    modifiedtime = rs.get(ggpp.modifiedtime),
    comments = rs.get(ggpp.comments)
  )

  val ggpp = GafisGatherPersonPhoto.syntax("ggpp")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[GafisGatherPersonPhoto] = {
    withSQL {
      select.from(GafisGatherPersonPhoto as ggpp).where.eq(ggpp.pkId, pkId)
    }.map(GafisGatherPersonPhoto(ggpp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherPersonPhoto] = {
    withSQL(select.from(GafisGatherPersonPhoto as ggpp)).map(GafisGatherPersonPhoto(ggpp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherPersonPhoto as ggpp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherPersonPhoto] = {
    withSQL {
      select.from(GafisGatherPersonPhoto as ggpp).where.append(where)
    }.map(GafisGatherPersonPhoto(ggpp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherPersonPhoto] = {
    withSQL {
      select.from(GafisGatherPersonPhoto as ggpp).where.append(where)
    }.map(GafisGatherPersonPhoto(ggpp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherPersonPhoto as ggpp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    photoCode: String,
    photoData: Blob,
    photoOrg: Option[Long] = None,
    inputpsn: Option[String] = None,
    inputtime: DateTime,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    comments: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherPersonPhoto = {
    withSQL {
      insert.into(GafisGatherPersonPhoto).columns(
        column.pkId,
        column.personid,
        column.photoCode,
        column.photoData,
        column.photoOrg,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.comments
      ).values(
        pkId,
        personid,
        photoCode,
        photoData,
        photoOrg,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        comments
      )
    }.update.apply()

    GafisGatherPersonPhoto(
      pkId = pkId,
      personid = personid,
      photoCode = photoCode,
      photoData = photoData,
      photoOrg = photoOrg,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      comments = comments)
  }

  def save(entity: GafisGatherPersonPhoto)(implicit session: DBSession = autoSession): GafisGatherPersonPhoto = {
    withSQL {
      update(GafisGatherPersonPhoto).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.photoCode -> entity.photoCode,
        column.photoData -> entity.photoData,
        column.photoOrg -> entity.photoOrg,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.comments -> entity.comments
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherPersonPhoto)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherPersonPhoto).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
