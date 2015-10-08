package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherPhone(
  pkId: String,
  personid: Option[String] = None,
  phoneTypeCode: Option[String] = None,
  phoneNumber: Option[String] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  deletag: Option[String] = None,
  areanumber: Option[String] = None,
  useme: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherPhone.autoSession): GafisGatherPhone = GafisGatherPhone.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherPhone.autoSession): Unit = GafisGatherPhone.destroy(this)(session)

}


object GafisGatherPhone extends SQLSyntaxSupport[GafisGatherPhone] {

  override val tableName = "GAFIS_GATHER_PHONE"

  override val columns = Seq("PK_ID", "PERSONID", "PHONE_TYPE_CODE", "PHONE_NUMBER", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "DELETAG", "AREANUMBER", "USEME")

  def apply(ggp: SyntaxProvider[GafisGatherPhone])(rs: WrappedResultSet): GafisGatherPhone = apply(ggp.resultName)(rs)
  def apply(ggp: ResultName[GafisGatherPhone])(rs: WrappedResultSet): GafisGatherPhone = new GafisGatherPhone(
    pkId = rs.get(ggp.pkId),
    personid = rs.get(ggp.personid),
    phoneTypeCode = rs.get(ggp.phoneTypeCode),
    phoneNumber = rs.get(ggp.phoneNumber),
    inputpsn = rs.get(ggp.inputpsn),
    inputtime = rs.get(ggp.inputtime),
    modifiedpsn = rs.get(ggp.modifiedpsn),
    modifiedtime = rs.get(ggp.modifiedtime),
    deletag = rs.get(ggp.deletag),
    areanumber = rs.get(ggp.areanumber),
    useme = rs.get(ggp.useme)
  )

  val ggp = GafisGatherPhone.syntax("ggp")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], phoneTypeCode: Option[String], phoneNumber: Option[String], inputpsn: Option[String], inputtime: Option[DateTime], modifiedpsn: Option[String], modifiedtime: Option[DateTime], deletag: Option[String], areanumber: Option[String], useme: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherPhone] = {
    withSQL {
      select.from(GafisGatherPhone as ggp).where.eq(ggp.pkId, pkId).and.eq(ggp.personid, personid).and.eq(ggp.phoneTypeCode, phoneTypeCode).and.eq(ggp.phoneNumber, phoneNumber).and.eq(ggp.inputpsn, inputpsn).and.eq(ggp.inputtime, inputtime).and.eq(ggp.modifiedpsn, modifiedpsn).and.eq(ggp.modifiedtime, modifiedtime).and.eq(ggp.deletag, deletag).and.eq(ggp.areanumber, areanumber).and.eq(ggp.useme, useme)
    }.map(GafisGatherPhone(ggp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherPhone] = {
    withSQL(select.from(GafisGatherPhone as ggp)).map(GafisGatherPhone(ggp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherPhone as ggp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherPhone] = {
    withSQL {
      select.from(GafisGatherPhone as ggp).where.append(where)
    }.map(GafisGatherPhone(ggp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherPhone] = {
    withSQL {
      select.from(GafisGatherPhone as ggp).where.append(where)
    }.map(GafisGatherPhone(ggp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherPhone as ggp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    phoneTypeCode: Option[String] = None,
    phoneNumber: Option[String] = None,
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    deletag: Option[String] = None,
    areanumber: Option[String] = None,
    useme: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherPhone = {
    withSQL {
      insert.into(GafisGatherPhone).columns(
        column.pkId,
        column.personid,
        column.phoneTypeCode,
        column.phoneNumber,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.deletag,
        column.areanumber,
        column.useme
      ).values(
        pkId,
        personid,
        phoneTypeCode,
        phoneNumber,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        deletag,
        areanumber,
        useme
      )
    }.update.apply()

    GafisGatherPhone(
      pkId = pkId,
      personid = personid,
      phoneTypeCode = phoneTypeCode,
      phoneNumber = phoneNumber,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      deletag = deletag,
      areanumber = areanumber,
      useme = useme)
  }

  def save(entity: GafisGatherPhone)(implicit session: DBSession = autoSession): GafisGatherPhone = {
    withSQL {
      update(GafisGatherPhone).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.phoneTypeCode -> entity.phoneTypeCode,
        column.phoneNumber -> entity.phoneNumber,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.deletag -> entity.deletag,
        column.areanumber -> entity.areanumber,
        column.useme -> entity.useme
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.phoneTypeCode, entity.phoneTypeCode).and.eq(column.phoneNumber, entity.phoneNumber).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag).and.eq(column.areanumber, entity.areanumber).and.eq(column.useme, entity.useme)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherPhone)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherPhone).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.phoneTypeCode, entity.phoneTypeCode).and.eq(column.phoneNumber, entity.phoneNumber).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag).and.eq(column.areanumber, entity.areanumber).and.eq(column.useme, entity.useme) }.update.apply()
  }

}
