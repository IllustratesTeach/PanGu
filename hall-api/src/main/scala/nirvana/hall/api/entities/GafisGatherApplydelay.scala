package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherApplydelay(
  pkId: String,
  personid: Option[String] = None,
  delayreason: Option[String] = None,
  delayDeadline: Option[DateTime] = None,
  applyPersonid: Option[String] = None,
  applyDepartid: Option[String] = None,
  applyDate: Option[DateTime] = None,
  applyResult: Option[String] = None,
  approvalsAdvice: Option[String] = None,
  approvalsPersonid: Option[String] = None,
  approvalsDate: Option[DateTime] = None,
  approvalsDepartid: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherApplydelay.autoSession): GafisGatherApplydelay = GafisGatherApplydelay.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherApplydelay.autoSession): Unit = GafisGatherApplydelay.destroy(this)(session)

}


object GafisGatherApplydelay extends SQLSyntaxSupport[GafisGatherApplydelay] {

  override val tableName = "GAFIS_GATHER_APPLYDELAY"

  override val columns = Seq("PK_ID", "PERSONID", "DELAYREASON", "DELAY_DEADLINE", "APPLY_PERSONID", "APPLY_DEPARTID", "APPLY_DATE", "APPLY_RESULT", "APPROVALS_ADVICE", "APPROVALS_PERSONID", "APPROVALS_DATE", "APPROVALS_DEPARTID")

  def apply(gga: SyntaxProvider[GafisGatherApplydelay])(rs: WrappedResultSet): GafisGatherApplydelay = apply(gga.resultName)(rs)
  def apply(gga: ResultName[GafisGatherApplydelay])(rs: WrappedResultSet): GafisGatherApplydelay = new GafisGatherApplydelay(
    pkId = rs.get(gga.pkId),
    personid = rs.get(gga.personid),
    delayreason = rs.get(gga.delayreason),
    delayDeadline = rs.get(gga.delayDeadline),
    applyPersonid = rs.get(gga.applyPersonid),
    applyDepartid = rs.get(gga.applyDepartid),
    applyDate = rs.get(gga.applyDate),
    applyResult = rs.get(gga.applyResult),
    approvalsAdvice = rs.get(gga.approvalsAdvice),
    approvalsPersonid = rs.get(gga.approvalsPersonid),
    approvalsDate = rs.get(gga.approvalsDate),
    approvalsDepartid = rs.get(gga.approvalsDepartid)
  )

  val gga = GafisGatherApplydelay.syntax("gga")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], delayreason: Option[String], delayDeadline: Option[DateTime], applyPersonid: Option[String], applyDepartid: Option[String], applyDate: Option[DateTime], applyResult: Option[String], approvalsAdvice: Option[String], approvalsPersonid: Option[String], approvalsDate: Option[DateTime], approvalsDepartid: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherApplydelay] = {
    withSQL {
      select.from(GafisGatherApplydelay as gga).where.eq(gga.pkId, pkId).and.eq(gga.personid, personid).and.eq(gga.delayreason, delayreason).and.eq(gga.delayDeadline, delayDeadline).and.eq(gga.applyPersonid, applyPersonid).and.eq(gga.applyDepartid, applyDepartid).and.eq(gga.applyDate, applyDate).and.eq(gga.applyResult, applyResult).and.eq(gga.approvalsAdvice, approvalsAdvice).and.eq(gga.approvalsPersonid, approvalsPersonid).and.eq(gga.approvalsDate, approvalsDate).and.eq(gga.approvalsDepartid, approvalsDepartid)
    }.map(GafisGatherApplydelay(gga.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherApplydelay] = {
    withSQL(select.from(GafisGatherApplydelay as gga)).map(GafisGatherApplydelay(gga.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherApplydelay as gga)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherApplydelay] = {
    withSQL {
      select.from(GafisGatherApplydelay as gga).where.append(where)
    }.map(GafisGatherApplydelay(gga.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherApplydelay] = {
    withSQL {
      select.from(GafisGatherApplydelay as gga).where.append(where)
    }.map(GafisGatherApplydelay(gga.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherApplydelay as gga).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    delayreason: Option[String] = None,
    delayDeadline: Option[DateTime] = None,
    applyPersonid: Option[String] = None,
    applyDepartid: Option[String] = None,
    applyDate: Option[DateTime] = None,
    applyResult: Option[String] = None,
    approvalsAdvice: Option[String] = None,
    approvalsPersonid: Option[String] = None,
    approvalsDate: Option[DateTime] = None,
    approvalsDepartid: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherApplydelay = {
    withSQL {
      insert.into(GafisGatherApplydelay).columns(
        column.pkId,
        column.personid,
        column.delayreason,
        column.delayDeadline,
        column.applyPersonid,
        column.applyDepartid,
        column.applyDate,
        column.applyResult,
        column.approvalsAdvice,
        column.approvalsPersonid,
        column.approvalsDate,
        column.approvalsDepartid
      ).values(
        pkId,
        personid,
        delayreason,
        delayDeadline,
        applyPersonid,
        applyDepartid,
        applyDate,
        applyResult,
        approvalsAdvice,
        approvalsPersonid,
        approvalsDate,
        approvalsDepartid
      )
    }.update.apply()

    GafisGatherApplydelay(
      pkId = pkId,
      personid = personid,
      delayreason = delayreason,
      delayDeadline = delayDeadline,
      applyPersonid = applyPersonid,
      applyDepartid = applyDepartid,
      applyDate = applyDate,
      applyResult = applyResult,
      approvalsAdvice = approvalsAdvice,
      approvalsPersonid = approvalsPersonid,
      approvalsDate = approvalsDate,
      approvalsDepartid = approvalsDepartid)
  }

  def save(entity: GafisGatherApplydelay)(implicit session: DBSession = autoSession): GafisGatherApplydelay = {
    withSQL {
      update(GafisGatherApplydelay).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.delayreason -> entity.delayreason,
        column.delayDeadline -> entity.delayDeadline,
        column.applyPersonid -> entity.applyPersonid,
        column.applyDepartid -> entity.applyDepartid,
        column.applyDate -> entity.applyDate,
        column.applyResult -> entity.applyResult,
        column.approvalsAdvice -> entity.approvalsAdvice,
        column.approvalsPersonid -> entity.approvalsPersonid,
        column.approvalsDate -> entity.approvalsDate,
        column.approvalsDepartid -> entity.approvalsDepartid
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.delayreason, entity.delayreason).and.eq(column.delayDeadline, entity.delayDeadline).and.eq(column.applyPersonid, entity.applyPersonid).and.eq(column.applyDepartid, entity.applyDepartid).and.eq(column.applyDate, entity.applyDate).and.eq(column.applyResult, entity.applyResult).and.eq(column.approvalsAdvice, entity.approvalsAdvice).and.eq(column.approvalsPersonid, entity.approvalsPersonid).and.eq(column.approvalsDate, entity.approvalsDate).and.eq(column.approvalsDepartid, entity.approvalsDepartid)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherApplydelay)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherApplydelay).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.delayreason, entity.delayreason).and.eq(column.delayDeadline, entity.delayDeadline).and.eq(column.applyPersonid, entity.applyPersonid).and.eq(column.applyDepartid, entity.applyDepartid).and.eq(column.applyDate, entity.applyDate).and.eq(column.applyResult, entity.applyResult).and.eq(column.approvalsAdvice, entity.approvalsAdvice).and.eq(column.approvalsPersonid, entity.approvalsPersonid).and.eq(column.approvalsDate, entity.approvalsDate).and.eq(column.approvalsDepartid, entity.approvalsDepartid) }.update.apply()
  }

}
