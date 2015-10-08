package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherSms(
  pkId: String,
  personid: Option[String] = None,
  ownertel: Option[String] = None,
  sendtel: Option[String] = None,
  smstime: Option[DateTime] = None,
  smscontent: Option[String] = None,
  inputtime: Option[DateTime] = None,
  inputpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  deletag: Option[String] = None,
  iccid: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherSms.autoSession): GafisGatherSms = GafisGatherSms.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherSms.autoSession): Unit = GafisGatherSms.destroy(this)(session)

}


object GafisGatherSms extends SQLSyntaxSupport[GafisGatherSms] {

  override val tableName = "GAFIS_GATHER_SMS"

  override val columns = Seq("PK_ID", "PERSONID", "OWNERTEL", "SENDTEL", "SMSTIME", "SMSCONTENT", "INPUTTIME", "INPUTPSN", "MODIFIEDTIME", "MODIFIEDPSN", "DELETAG", "ICCID")

  def apply(ggs: SyntaxProvider[GafisGatherSms])(rs: WrappedResultSet): GafisGatherSms = apply(ggs.resultName)(rs)
  def apply(ggs: ResultName[GafisGatherSms])(rs: WrappedResultSet): GafisGatherSms = new GafisGatherSms(
    pkId = rs.get(ggs.pkId),
    personid = rs.get(ggs.personid),
    ownertel = rs.get(ggs.ownertel),
    sendtel = rs.get(ggs.sendtel),
    smstime = rs.get(ggs.smstime),
    smscontent = rs.get(ggs.smscontent),
    inputtime = rs.get(ggs.inputtime),
    inputpsn = rs.get(ggs.inputpsn),
    modifiedtime = rs.get(ggs.modifiedtime),
    modifiedpsn = rs.get(ggs.modifiedpsn),
    deletag = rs.get(ggs.deletag),
    iccid = rs.get(ggs.iccid)
  )

  val ggs = GafisGatherSms.syntax("ggs")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], ownertel: Option[String], sendtel: Option[String], smstime: Option[DateTime], smscontent: Option[String], inputtime: Option[DateTime], inputpsn: Option[String], modifiedtime: Option[DateTime], modifiedpsn: Option[String], deletag: Option[String], iccid: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherSms] = {
    withSQL {
      select.from(GafisGatherSms as ggs).where.eq(ggs.pkId, pkId).and.eq(ggs.personid, personid).and.eq(ggs.ownertel, ownertel).and.eq(ggs.sendtel, sendtel).and.eq(ggs.smstime, smstime).and.eq(ggs.smscontent, smscontent).and.eq(ggs.inputtime, inputtime).and.eq(ggs.inputpsn, inputpsn).and.eq(ggs.modifiedtime, modifiedtime).and.eq(ggs.modifiedpsn, modifiedpsn).and.eq(ggs.deletag, deletag).and.eq(ggs.iccid, iccid)
    }.map(GafisGatherSms(ggs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherSms] = {
    withSQL(select.from(GafisGatherSms as ggs)).map(GafisGatherSms(ggs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherSms as ggs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherSms] = {
    withSQL {
      select.from(GafisGatherSms as ggs).where.append(where)
    }.map(GafisGatherSms(ggs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherSms] = {
    withSQL {
      select.from(GafisGatherSms as ggs).where.append(where)
    }.map(GafisGatherSms(ggs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherSms as ggs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    ownertel: Option[String] = None,
    sendtel: Option[String] = None,
    smstime: Option[DateTime] = None,
    smscontent: Option[String] = None,
    inputtime: Option[DateTime] = None,
    inputpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    deletag: Option[String] = None,
    iccid: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherSms = {
    withSQL {
      insert.into(GafisGatherSms).columns(
        column.pkId,
        column.personid,
        column.ownertel,
        column.sendtel,
        column.smstime,
        column.smscontent,
        column.inputtime,
        column.inputpsn,
        column.modifiedtime,
        column.modifiedpsn,
        column.deletag,
        column.iccid
      ).values(
        pkId,
        personid,
        ownertel,
        sendtel,
        smstime,
        smscontent,
        inputtime,
        inputpsn,
        modifiedtime,
        modifiedpsn,
        deletag,
        iccid
      )
    }.update.apply()

    GafisGatherSms(
      pkId = pkId,
      personid = personid,
      ownertel = ownertel,
      sendtel = sendtel,
      smstime = smstime,
      smscontent = smscontent,
      inputtime = inputtime,
      inputpsn = inputpsn,
      modifiedtime = modifiedtime,
      modifiedpsn = modifiedpsn,
      deletag = deletag,
      iccid = iccid)
  }

  def save(entity: GafisGatherSms)(implicit session: DBSession = autoSession): GafisGatherSms = {
    withSQL {
      update(GafisGatherSms).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.ownertel -> entity.ownertel,
        column.sendtel -> entity.sendtel,
        column.smstime -> entity.smstime,
        column.smscontent -> entity.smscontent,
        column.inputtime -> entity.inputtime,
        column.inputpsn -> entity.inputpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.deletag -> entity.deletag,
        column.iccid -> entity.iccid
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.ownertel, entity.ownertel).and.eq(column.sendtel, entity.sendtel).and.eq(column.smstime, entity.smstime).and.eq(column.smscontent, entity.smscontent).and.eq(column.inputtime, entity.inputtime).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.deletag, entity.deletag).and.eq(column.iccid, entity.iccid)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherSms)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherSms).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.ownertel, entity.ownertel).and.eq(column.sendtel, entity.sendtel).and.eq(column.smstime, entity.smstime).and.eq(column.smscontent, entity.smscontent).and.eq(column.inputtime, entity.inputtime).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.deletag, entity.deletag).and.eq(column.iccid, entity.iccid) }.update.apply()
  }

}
