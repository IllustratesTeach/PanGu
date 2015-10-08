package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherPhonelist(
  pkId: String,
  personid: Option[String] = None,
  ownertel: Option[String] = None,
  contactno: Option[String] = None,
  conname: Option[String] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  deletag: Option[String] = None,
  iccid: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherPhonelist.autoSession): GafisGatherPhonelist = GafisGatherPhonelist.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherPhonelist.autoSession): Unit = GafisGatherPhonelist.destroy(this)(session)

}


object GafisGatherPhonelist extends SQLSyntaxSupport[GafisGatherPhonelist] {

  override val tableName = "GAFIS_GATHER_PHONELIST"

  override val columns = Seq("PK_ID", "PERSONID", "OWNERTEL", "CONTACTNO", "CONNAME", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "DELETAG", "ICCID")

  def apply(ggp: SyntaxProvider[GafisGatherPhonelist])(rs: WrappedResultSet): GafisGatherPhonelist = apply(ggp.resultName)(rs)
  def apply(ggp: ResultName[GafisGatherPhonelist])(rs: WrappedResultSet): GafisGatherPhonelist = new GafisGatherPhonelist(
    pkId = rs.get(ggp.pkId),
    personid = rs.get(ggp.personid),
    ownertel = rs.get(ggp.ownertel),
    contactno = rs.get(ggp.contactno),
    conname = rs.get(ggp.conname),
    inputpsn = rs.get(ggp.inputpsn),
    inputtime = rs.get(ggp.inputtime),
    modifiedpsn = rs.get(ggp.modifiedpsn),
    modifiedtime = rs.get(ggp.modifiedtime),
    deletag = rs.get(ggp.deletag),
    iccid = rs.get(ggp.iccid)
  )

  val ggp = GafisGatherPhonelist.syntax("ggp")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], ownertel: Option[String], contactno: Option[String], conname: Option[String], inputpsn: Option[String], inputtime: Option[DateTime], modifiedpsn: Option[String], modifiedtime: Option[DateTime], deletag: Option[String], iccid: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherPhonelist] = {
    withSQL {
      select.from(GafisGatherPhonelist as ggp).where.eq(ggp.pkId, pkId).and.eq(ggp.personid, personid).and.eq(ggp.ownertel, ownertel).and.eq(ggp.contactno, contactno).and.eq(ggp.conname, conname).and.eq(ggp.inputpsn, inputpsn).and.eq(ggp.inputtime, inputtime).and.eq(ggp.modifiedpsn, modifiedpsn).and.eq(ggp.modifiedtime, modifiedtime).and.eq(ggp.deletag, deletag).and.eq(ggp.iccid, iccid)
    }.map(GafisGatherPhonelist(ggp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherPhonelist] = {
    withSQL(select.from(GafisGatherPhonelist as ggp)).map(GafisGatherPhonelist(ggp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherPhonelist as ggp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherPhonelist] = {
    withSQL {
      select.from(GafisGatherPhonelist as ggp).where.append(where)
    }.map(GafisGatherPhonelist(ggp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherPhonelist] = {
    withSQL {
      select.from(GafisGatherPhonelist as ggp).where.append(where)
    }.map(GafisGatherPhonelist(ggp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherPhonelist as ggp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    ownertel: Option[String] = None,
    contactno: Option[String] = None,
    conname: Option[String] = None,
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    deletag: Option[String] = None,
    iccid: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherPhonelist = {
    withSQL {
      insert.into(GafisGatherPhonelist).columns(
        column.pkId,
        column.personid,
        column.ownertel,
        column.contactno,
        column.conname,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.deletag,
        column.iccid
      ).values(
        pkId,
        personid,
        ownertel,
        contactno,
        conname,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        deletag,
        iccid
      )
    }.update.apply()

    GafisGatherPhonelist(
      pkId = pkId,
      personid = personid,
      ownertel = ownertel,
      contactno = contactno,
      conname = conname,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      deletag = deletag,
      iccid = iccid)
  }

  def save(entity: GafisGatherPhonelist)(implicit session: DBSession = autoSession): GafisGatherPhonelist = {
    withSQL {
      update(GafisGatherPhonelist).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.ownertel -> entity.ownertel,
        column.contactno -> entity.contactno,
        column.conname -> entity.conname,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.deletag -> entity.deletag,
        column.iccid -> entity.iccid
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.ownertel, entity.ownertel).and.eq(column.contactno, entity.contactno).and.eq(column.conname, entity.conname).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag).and.eq(column.iccid, entity.iccid)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherPhonelist)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherPhonelist).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.ownertel, entity.ownertel).and.eq(column.contactno, entity.contactno).and.eq(column.conname, entity.conname).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag).and.eq(column.iccid, entity.iccid) }.update.apply()
  }

}
