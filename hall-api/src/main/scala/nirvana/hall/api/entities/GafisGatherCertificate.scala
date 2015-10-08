package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherCertificate(
  pkId: String,
  personid: Option[String] = None,
  certificatetype: Option[String] = None,
  certificateid: Option[String] = None,
  issueunit: Option[String] = None,
  signdate: Option[DateTime] = None,
  validitybegin: Option[DateTime] = None,
  validityend: Option[DateTime] = None,
  iftrue: Option[String] = None,
  holder: Option[String] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  deletag: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherCertificate.autoSession): GafisGatherCertificate = GafisGatherCertificate.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherCertificate.autoSession): Unit = GafisGatherCertificate.destroy(this)(session)

}


object GafisGatherCertificate extends SQLSyntaxSupport[GafisGatherCertificate] {

  override val tableName = "GAFIS_GATHER_CERTIFICATE"

  override val columns = Seq("PK_ID", "PERSONID", "CERTIFICATETYPE", "CERTIFICATEID", "ISSUEUNIT", "SIGNDATE", "VALIDITYBEGIN", "VALIDITYEND", "IFTRUE", "HOLDER", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "DELETAG")

  def apply(ggc: SyntaxProvider[GafisGatherCertificate])(rs: WrappedResultSet): GafisGatherCertificate = apply(ggc.resultName)(rs)
  def apply(ggc: ResultName[GafisGatherCertificate])(rs: WrappedResultSet): GafisGatherCertificate = new GafisGatherCertificate(
    pkId = rs.get(ggc.pkId),
    personid = rs.get(ggc.personid),
    certificatetype = rs.get(ggc.certificatetype),
    certificateid = rs.get(ggc.certificateid),
    issueunit = rs.get(ggc.issueunit),
    signdate = rs.get(ggc.signdate),
    validitybegin = rs.get(ggc.validitybegin),
    validityend = rs.get(ggc.validityend),
    iftrue = rs.get(ggc.iftrue),
    holder = rs.get(ggc.holder),
    inputpsn = rs.get(ggc.inputpsn),
    inputtime = rs.get(ggc.inputtime),
    modifiedpsn = rs.get(ggc.modifiedpsn),
    modifiedtime = rs.get(ggc.modifiedtime),
    deletag = rs.get(ggc.deletag)
  )

  val ggc = GafisGatherCertificate.syntax("ggc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], certificatetype: Option[String], certificateid: Option[String], issueunit: Option[String], signdate: Option[DateTime], validitybegin: Option[DateTime], validityend: Option[DateTime], iftrue: Option[String], holder: Option[String], inputpsn: Option[String], inputtime: Option[DateTime], modifiedpsn: Option[String], modifiedtime: Option[DateTime], deletag: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherCertificate] = {
    withSQL {
      select.from(GafisGatherCertificate as ggc).where.eq(ggc.pkId, pkId).and.eq(ggc.personid, personid).and.eq(ggc.certificatetype, certificatetype).and.eq(ggc.certificateid, certificateid).and.eq(ggc.issueunit, issueunit).and.eq(ggc.signdate, signdate).and.eq(ggc.validitybegin, validitybegin).and.eq(ggc.validityend, validityend).and.eq(ggc.iftrue, iftrue).and.eq(ggc.holder, holder).and.eq(ggc.inputpsn, inputpsn).and.eq(ggc.inputtime, inputtime).and.eq(ggc.modifiedpsn, modifiedpsn).and.eq(ggc.modifiedtime, modifiedtime).and.eq(ggc.deletag, deletag)
    }.map(GafisGatherCertificate(ggc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherCertificate] = {
    withSQL(select.from(GafisGatherCertificate as ggc)).map(GafisGatherCertificate(ggc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherCertificate as ggc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherCertificate] = {
    withSQL {
      select.from(GafisGatherCertificate as ggc).where.append(where)
    }.map(GafisGatherCertificate(ggc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherCertificate] = {
    withSQL {
      select.from(GafisGatherCertificate as ggc).where.append(where)
    }.map(GafisGatherCertificate(ggc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherCertificate as ggc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    certificatetype: Option[String] = None,
    certificateid: Option[String] = None,
    issueunit: Option[String] = None,
    signdate: Option[DateTime] = None,
    validitybegin: Option[DateTime] = None,
    validityend: Option[DateTime] = None,
    iftrue: Option[String] = None,
    holder: Option[String] = None,
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    deletag: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherCertificate = {
    withSQL {
      insert.into(GafisGatherCertificate).columns(
        column.pkId,
        column.personid,
        column.certificatetype,
        column.certificateid,
        column.issueunit,
        column.signdate,
        column.validitybegin,
        column.validityend,
        column.iftrue,
        column.holder,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.deletag
      ).values(
        pkId,
        personid,
        certificatetype,
        certificateid,
        issueunit,
        signdate,
        validitybegin,
        validityend,
        iftrue,
        holder,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        deletag
      )
    }.update.apply()

    GafisGatherCertificate(
      pkId = pkId,
      personid = personid,
      certificatetype = certificatetype,
      certificateid = certificateid,
      issueunit = issueunit,
      signdate = signdate,
      validitybegin = validitybegin,
      validityend = validityend,
      iftrue = iftrue,
      holder = holder,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      deletag = deletag)
  }

  def save(entity: GafisGatherCertificate)(implicit session: DBSession = autoSession): GafisGatherCertificate = {
    withSQL {
      update(GafisGatherCertificate).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.certificatetype -> entity.certificatetype,
        column.certificateid -> entity.certificateid,
        column.issueunit -> entity.issueunit,
        column.signdate -> entity.signdate,
        column.validitybegin -> entity.validitybegin,
        column.validityend -> entity.validityend,
        column.iftrue -> entity.iftrue,
        column.holder -> entity.holder,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.deletag -> entity.deletag
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.certificatetype, entity.certificatetype).and.eq(column.certificateid, entity.certificateid).and.eq(column.issueunit, entity.issueunit).and.eq(column.signdate, entity.signdate).and.eq(column.validitybegin, entity.validitybegin).and.eq(column.validityend, entity.validityend).and.eq(column.iftrue, entity.iftrue).and.eq(column.holder, entity.holder).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherCertificate)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherCertificate).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.certificatetype, entity.certificatetype).and.eq(column.certificateid, entity.certificateid).and.eq(column.issueunit, entity.issueunit).and.eq(column.signdate, entity.signdate).and.eq(column.validitybegin, entity.validitybegin).and.eq(column.validityend, entity.validityend).and.eq(column.iftrue, entity.iftrue).and.eq(column.holder, entity.holder).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag) }.update.apply()
  }

}
