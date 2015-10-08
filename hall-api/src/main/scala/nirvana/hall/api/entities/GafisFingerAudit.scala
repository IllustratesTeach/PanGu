package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisFingerAudit(
  pkId: String,
  personId: String,
  fgp: Option[String] = None,
  fgpCase: Option[String] = None,
  num: Option[Long] = None,
  status: Option[String] = None,
  description: Option[String] = None,
  auditor: Option[String] = None,
  auditTime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisFingerAudit.autoSession): GafisFingerAudit = GafisFingerAudit.save(this)(session)

  def destroy()(implicit session: DBSession = GafisFingerAudit.autoSession): Unit = GafisFingerAudit.destroy(this)(session)

}


object GafisFingerAudit extends SQLSyntaxSupport[GafisFingerAudit] {

  override val tableName = "GAFIS_FINGER_AUDIT"

  override val columns = Seq("PK_ID", "PERSON_ID", "FGP", "FGP_CASE", "NUM", "STATUS", "DESCRIPTION", "AUDITOR", "AUDIT_TIME")

  def apply(gfa: SyntaxProvider[GafisFingerAudit])(rs: WrappedResultSet): GafisFingerAudit = apply(gfa.resultName)(rs)
  def apply(gfa: ResultName[GafisFingerAudit])(rs: WrappedResultSet): GafisFingerAudit = new GafisFingerAudit(
    pkId = rs.get(gfa.pkId),
    personId = rs.get(gfa.personId),
    fgp = rs.get(gfa.fgp),
    fgpCase = rs.get(gfa.fgpCase),
    num = rs.get(gfa.num),
    status = rs.get(gfa.status),
    description = rs.get(gfa.description),
    auditor = rs.get(gfa.auditor),
    auditTime = rs.get(gfa.auditTime)
  )

  val gfa = GafisFingerAudit.syntax("gfa")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personId: String, fgp: Option[String], fgpCase: Option[String], num: Option[Long], status: Option[String], description: Option[String], auditor: Option[String], auditTime: Option[DateTime])(implicit session: DBSession = autoSession): Option[GafisFingerAudit] = {
    withSQL {
      select.from(GafisFingerAudit as gfa).where.eq(gfa.pkId, pkId).and.eq(gfa.personId, personId).and.eq(gfa.fgp, fgp).and.eq(gfa.fgpCase, fgpCase).and.eq(gfa.num, num).and.eq(gfa.status, status).and.eq(gfa.description, description).and.eq(gfa.auditor, auditor).and.eq(gfa.auditTime, auditTime)
    }.map(GafisFingerAudit(gfa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisFingerAudit] = {
    withSQL(select.from(GafisFingerAudit as gfa)).map(GafisFingerAudit(gfa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisFingerAudit as gfa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisFingerAudit] = {
    withSQL {
      select.from(GafisFingerAudit as gfa).where.append(where)
    }.map(GafisFingerAudit(gfa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisFingerAudit] = {
    withSQL {
      select.from(GafisFingerAudit as gfa).where.append(where)
    }.map(GafisFingerAudit(gfa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisFingerAudit as gfa).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personId: String,
    fgp: Option[String] = None,
    fgpCase: Option[String] = None,
    num: Option[Long] = None,
    status: Option[String] = None,
    description: Option[String] = None,
    auditor: Option[String] = None,
    auditTime: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisFingerAudit = {
    withSQL {
      insert.into(GafisFingerAudit).columns(
        column.pkId,
        column.personId,
        column.fgp,
        column.fgpCase,
        column.num,
        column.status,
        column.description,
        column.auditor,
        column.auditTime
      ).values(
        pkId,
        personId,
        fgp,
        fgpCase,
        num,
        status,
        description,
        auditor,
        auditTime
      )
    }.update.apply()

    GafisFingerAudit(
      pkId = pkId,
      personId = personId,
      fgp = fgp,
      fgpCase = fgpCase,
      num = num,
      status = status,
      description = description,
      auditor = auditor,
      auditTime = auditTime)
  }

  def save(entity: GafisFingerAudit)(implicit session: DBSession = autoSession): GafisFingerAudit = {
    withSQL {
      update(GafisFingerAudit).set(
        column.pkId -> entity.pkId,
        column.personId -> entity.personId,
        column.fgp -> entity.fgp,
        column.fgpCase -> entity.fgpCase,
        column.num -> entity.num,
        column.status -> entity.status,
        column.description -> entity.description,
        column.auditor -> entity.auditor,
        column.auditTime -> entity.auditTime
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.fgp, entity.fgp).and.eq(column.fgpCase, entity.fgpCase).and.eq(column.num, entity.num).and.eq(column.status, entity.status).and.eq(column.description, entity.description).and.eq(column.auditor, entity.auditor).and.eq(column.auditTime, entity.auditTime)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisFingerAudit)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisFingerAudit).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.fgp, entity.fgp).and.eq(column.fgpCase, entity.fgpCase).and.eq(column.num, entity.num).and.eq(column.status, entity.status).and.eq(column.description, entity.description).and.eq(column.auditor, entity.auditor).and.eq(column.auditTime, entity.auditTime) }.update.apply()
  }

}
