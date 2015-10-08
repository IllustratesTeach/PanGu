package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisGatherSign(
  pkId: String,
  personid: Option[String] = None,
  signplaceCode: Option[String] = None,
  signdirectionCode: Option[String] = None,
  photo: Option[Blob] = None,
  inputpsn: Option[String] = None,
  inputtime: DateTime,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  signamountCode: Option[String] = None,
  signsize: Option[String] = None,
  signcolorCode: Option[String] = None,
  signnameCode: Option[String] = None,
  annex: Option[String] = None,
  deletag: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherSign.autoSession): GafisGatherSign = GafisGatherSign.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherSign.autoSession): Unit = GafisGatherSign.destroy(this)(session)

}


object GafisGatherSign extends SQLSyntaxSupport[GafisGatherSign] {

  override val tableName = "GAFIS_GATHER_SIGN"

  override val columns = Seq("PK_ID", "PERSONID", "SIGNPLACE_CODE", "SIGNDIRECTION_CODE", "PHOTO", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "SIGNAMOUNT_CODE", "SIGNSIZE", "SIGNCOLOR_CODE", "SIGNNAME_CODE", "ANNEX", "DELETAG")

  def apply(ggs: SyntaxProvider[GafisGatherSign])(rs: WrappedResultSet): GafisGatherSign = apply(ggs.resultName)(rs)
  def apply(ggs: ResultName[GafisGatherSign])(rs: WrappedResultSet): GafisGatherSign = new GafisGatherSign(
    pkId = rs.get(ggs.pkId),
    personid = rs.get(ggs.personid),
    signplaceCode = rs.get(ggs.signplaceCode),
    signdirectionCode = rs.get(ggs.signdirectionCode),
    photo = rs.get(ggs.photo),
    inputpsn = rs.get(ggs.inputpsn),
    inputtime = rs.get(ggs.inputtime),
    modifiedpsn = rs.get(ggs.modifiedpsn),
    modifiedtime = rs.get(ggs.modifiedtime),
    signamountCode = rs.get(ggs.signamountCode),
    signsize = rs.get(ggs.signsize),
    signcolorCode = rs.get(ggs.signcolorCode),
    signnameCode = rs.get(ggs.signnameCode),
    annex = rs.get(ggs.annex),
    deletag = rs.get(ggs.deletag)
  )

  val ggs = GafisGatherSign.syntax("ggs")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], signplaceCode: Option[String], signdirectionCode: Option[String], photo: Option[Blob], inputpsn: Option[String], inputtime: DateTime, modifiedpsn: Option[String], modifiedtime: Option[DateTime], signamountCode: Option[String], signsize: Option[String], signcolorCode: Option[String], signnameCode: Option[String], annex: Option[String], deletag: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherSign] = {
    withSQL {
      select.from(GafisGatherSign as ggs).where.eq(ggs.pkId, pkId).and.eq(ggs.personid, personid).and.eq(ggs.signplaceCode, signplaceCode).and.eq(ggs.signdirectionCode, signdirectionCode).and.eq(ggs.photo, photo).and.eq(ggs.inputpsn, inputpsn).and.eq(ggs.inputtime, inputtime).and.eq(ggs.modifiedpsn, modifiedpsn).and.eq(ggs.modifiedtime, modifiedtime).and.eq(ggs.signamountCode, signamountCode).and.eq(ggs.signsize, signsize).and.eq(ggs.signcolorCode, signcolorCode).and.eq(ggs.signnameCode, signnameCode).and.eq(ggs.annex, annex).and.eq(ggs.deletag, deletag)
    }.map(GafisGatherSign(ggs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherSign] = {
    withSQL(select.from(GafisGatherSign as ggs)).map(GafisGatherSign(ggs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherSign as ggs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherSign] = {
    withSQL {
      select.from(GafisGatherSign as ggs).where.append(where)
    }.map(GafisGatherSign(ggs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherSign] = {
    withSQL {
      select.from(GafisGatherSign as ggs).where.append(where)
    }.map(GafisGatherSign(ggs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherSign as ggs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    signplaceCode: Option[String] = None,
    signdirectionCode: Option[String] = None,
    photo: Option[Blob] = None,
    inputpsn: Option[String] = None,
    inputtime: DateTime,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    signamountCode: Option[String] = None,
    signsize: Option[String] = None,
    signcolorCode: Option[String] = None,
    signnameCode: Option[String] = None,
    annex: Option[String] = None,
    deletag: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherSign = {
    withSQL {
      insert.into(GafisGatherSign).columns(
        column.pkId,
        column.personid,
        column.signplaceCode,
        column.signdirectionCode,
        column.photo,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.signamountCode,
        column.signsize,
        column.signcolorCode,
        column.signnameCode,
        column.annex,
        column.deletag
      ).values(
        pkId,
        personid,
        signplaceCode,
        signdirectionCode,
        photo,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        signamountCode,
        signsize,
        signcolorCode,
        signnameCode,
        annex,
        deletag
      )
    }.update.apply()

    GafisGatherSign(
      pkId = pkId,
      personid = personid,
      signplaceCode = signplaceCode,
      signdirectionCode = signdirectionCode,
      photo = photo,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      signamountCode = signamountCode,
      signsize = signsize,
      signcolorCode = signcolorCode,
      signnameCode = signnameCode,
      annex = annex,
      deletag = deletag)
  }

  def save(entity: GafisGatherSign)(implicit session: DBSession = autoSession): GafisGatherSign = {
    withSQL {
      update(GafisGatherSign).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.signplaceCode -> entity.signplaceCode,
        column.signdirectionCode -> entity.signdirectionCode,
        column.photo -> entity.photo,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.signamountCode -> entity.signamountCode,
        column.signsize -> entity.signsize,
        column.signcolorCode -> entity.signcolorCode,
        column.signnameCode -> entity.signnameCode,
        column.annex -> entity.annex,
        column.deletag -> entity.deletag
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.signplaceCode, entity.signplaceCode).and.eq(column.signdirectionCode, entity.signdirectionCode).and.eq(column.photo, entity.photo).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.signamountCode, entity.signamountCode).and.eq(column.signsize, entity.signsize).and.eq(column.signcolorCode, entity.signcolorCode).and.eq(column.signnameCode, entity.signnameCode).and.eq(column.annex, entity.annex).and.eq(column.deletag, entity.deletag)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherSign)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherSign).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.signplaceCode, entity.signplaceCode).and.eq(column.signdirectionCode, entity.signdirectionCode).and.eq(column.photo, entity.photo).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.signamountCode, entity.signamountCode).and.eq(column.signsize, entity.signsize).and.eq(column.signcolorCode, entity.signcolorCode).and.eq(column.signnameCode, entity.signnameCode).and.eq(column.annex, entity.annex).and.eq(column.deletag, entity.deletag) }.update.apply()
  }

}
