package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherSpecialty(
  pkId: String,
  personid: Option[String] = None,
  specialtyCode: Option[String] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  deletag: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherSpecialty.autoSession): GafisGatherSpecialty = GafisGatherSpecialty.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherSpecialty.autoSession): Unit = GafisGatherSpecialty.destroy(this)(session)

}


object GafisGatherSpecialty extends SQLSyntaxSupport[GafisGatherSpecialty] {

  override val tableName = "GAFIS_GATHER_SPECIALTY"

  override val columns = Seq("PK_ID", "PERSONID", "SPECIALTY_CODE", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "DELETAG")

  def apply(ggs: SyntaxProvider[GafisGatherSpecialty])(rs: WrappedResultSet): GafisGatherSpecialty = apply(ggs.resultName)(rs)
  def apply(ggs: ResultName[GafisGatherSpecialty])(rs: WrappedResultSet): GafisGatherSpecialty = new GafisGatherSpecialty(
    pkId = rs.get(ggs.pkId),
    personid = rs.get(ggs.personid),
    specialtyCode = rs.get(ggs.specialtyCode),
    inputpsn = rs.get(ggs.inputpsn),
    inputtime = rs.get(ggs.inputtime),
    modifiedpsn = rs.get(ggs.modifiedpsn),
    modifiedtime = rs.get(ggs.modifiedtime),
    deletag = rs.get(ggs.deletag)
  )

  val ggs = GafisGatherSpecialty.syntax("ggs")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], specialtyCode: Option[String], inputpsn: Option[String], inputtime: Option[DateTime], modifiedpsn: Option[String], modifiedtime: Option[DateTime], deletag: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherSpecialty] = {
    withSQL {
      select.from(GafisGatherSpecialty as ggs).where.eq(ggs.pkId, pkId).and.eq(ggs.personid, personid).and.eq(ggs.specialtyCode, specialtyCode).and.eq(ggs.inputpsn, inputpsn).and.eq(ggs.inputtime, inputtime).and.eq(ggs.modifiedpsn, modifiedpsn).and.eq(ggs.modifiedtime, modifiedtime).and.eq(ggs.deletag, deletag)
    }.map(GafisGatherSpecialty(ggs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherSpecialty] = {
    withSQL(select.from(GafisGatherSpecialty as ggs)).map(GafisGatherSpecialty(ggs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherSpecialty as ggs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherSpecialty] = {
    withSQL {
      select.from(GafisGatherSpecialty as ggs).where.append(where)
    }.map(GafisGatherSpecialty(ggs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherSpecialty] = {
    withSQL {
      select.from(GafisGatherSpecialty as ggs).where.append(where)
    }.map(GafisGatherSpecialty(ggs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherSpecialty as ggs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    specialtyCode: Option[String] = None,
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    deletag: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherSpecialty = {
    withSQL {
      insert.into(GafisGatherSpecialty).columns(
        column.pkId,
        column.personid,
        column.specialtyCode,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.deletag
      ).values(
        pkId,
        personid,
        specialtyCode,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        deletag
      )
    }.update.apply()

    GafisGatherSpecialty(
      pkId = pkId,
      personid = personid,
      specialtyCode = specialtyCode,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      deletag = deletag)
  }

  def save(entity: GafisGatherSpecialty)(implicit session: DBSession = autoSession): GafisGatherSpecialty = {
    withSQL {
      update(GafisGatherSpecialty).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.specialtyCode -> entity.specialtyCode,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.deletag -> entity.deletag
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.specialtyCode, entity.specialtyCode).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherSpecialty)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherSpecialty).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.specialtyCode, entity.specialtyCode).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag) }.update.apply()
  }

}
