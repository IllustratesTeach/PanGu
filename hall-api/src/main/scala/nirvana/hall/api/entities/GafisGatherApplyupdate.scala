package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherApplyupdate(
  pkId: String,
  personid: Option[String] = None,
  updateBody: Option[String] = None,
  applyPersonid: Option[String] = None,
  applyDepartid: Option[String] = None,
  applyDate: Option[DateTime] = None,
  applyResult: Option[String] = None,
  approvalsAdvice: Option[String] = None,
  approvalsPersonid: Option[String] = None,
  approvalsDate: Option[DateTime] = None,
  approvalsDepartid: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherApplyupdate.autoSession): GafisGatherApplyupdate = GafisGatherApplyupdate.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherApplyupdate.autoSession): Unit = GafisGatherApplyupdate.destroy(this)(session)

}


object GafisGatherApplyupdate extends SQLSyntaxSupport[GafisGatherApplyupdate] {

  override val tableName = "GAFIS_GATHER_APPLYUPDATE"

  override val columns = Seq("PK_ID", "PERSONID", "UPDATE_BODY", "APPLY_PERSONID", "APPLY_DEPARTID", "APPLY_DATE", "APPLY_RESULT", "APPROVALS_ADVICE", "APPROVALS_PERSONID", "APPROVALS_DATE", "APPROVALS_DEPARTID")

  def apply(gga: SyntaxProvider[GafisGatherApplyupdate])(rs: WrappedResultSet): GafisGatherApplyupdate = apply(gga.resultName)(rs)
  def apply(gga: ResultName[GafisGatherApplyupdate])(rs: WrappedResultSet): GafisGatherApplyupdate = new GafisGatherApplyupdate(
    pkId = rs.get(gga.pkId),
    personid = rs.get(gga.personid),
    updateBody = rs.get(gga.updateBody),
    applyPersonid = rs.get(gga.applyPersonid),
    applyDepartid = rs.get(gga.applyDepartid),
    applyDate = rs.get(gga.applyDate),
    applyResult = rs.get(gga.applyResult),
    approvalsAdvice = rs.get(gga.approvalsAdvice),
    approvalsPersonid = rs.get(gga.approvalsPersonid),
    approvalsDate = rs.get(gga.approvalsDate),
    approvalsDepartid = rs.get(gga.approvalsDepartid)
  )

  val gga = GafisGatherApplyupdate.syntax("gga")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], updateBody: Option[String], applyPersonid: Option[String], applyDepartid: Option[String], applyDate: Option[DateTime], applyResult: Option[String], approvalsAdvice: Option[String], approvalsPersonid: Option[String], approvalsDate: Option[DateTime], approvalsDepartid: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherApplyupdate] = {
    withSQL {
      select.from(GafisGatherApplyupdate as gga).where.eq(gga.pkId, pkId).and.eq(gga.personid, personid).and.eq(gga.updateBody, updateBody).and.eq(gga.applyPersonid, applyPersonid).and.eq(gga.applyDepartid, applyDepartid).and.eq(gga.applyDate, applyDate).and.eq(gga.applyResult, applyResult).and.eq(gga.approvalsAdvice, approvalsAdvice).and.eq(gga.approvalsPersonid, approvalsPersonid).and.eq(gga.approvalsDate, approvalsDate).and.eq(gga.approvalsDepartid, approvalsDepartid)
    }.map(GafisGatherApplyupdate(gga.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherApplyupdate] = {
    withSQL(select.from(GafisGatherApplyupdate as gga)).map(GafisGatherApplyupdate(gga.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherApplyupdate as gga)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherApplyupdate] = {
    withSQL {
      select.from(GafisGatherApplyupdate as gga).where.append(where)
    }.map(GafisGatherApplyupdate(gga.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherApplyupdate] = {
    withSQL {
      select.from(GafisGatherApplyupdate as gga).where.append(where)
    }.map(GafisGatherApplyupdate(gga.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherApplyupdate as gga).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    updateBody: Option[String] = None,
    applyPersonid: Option[String] = None,
    applyDepartid: Option[String] = None,
    applyDate: Option[DateTime] = None,
    applyResult: Option[String] = None,
    approvalsAdvice: Option[String] = None,
    approvalsPersonid: Option[String] = None,
    approvalsDate: Option[DateTime] = None,
    approvalsDepartid: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherApplyupdate = {
    withSQL {
      insert.into(GafisGatherApplyupdate).columns(
        column.pkId,
        column.personid,
        column.updateBody,
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
        updateBody,
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

    GafisGatherApplyupdate(
      pkId = pkId,
      personid = personid,
      updateBody = updateBody,
      applyPersonid = applyPersonid,
      applyDepartid = applyDepartid,
      applyDate = applyDate,
      applyResult = applyResult,
      approvalsAdvice = approvalsAdvice,
      approvalsPersonid = approvalsPersonid,
      approvalsDate = approvalsDate,
      approvalsDepartid = approvalsDepartid)
  }

  def save(entity: GafisGatherApplyupdate)(implicit session: DBSession = autoSession): GafisGatherApplyupdate = {
    withSQL {
      update(GafisGatherApplyupdate).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.updateBody -> entity.updateBody,
        column.applyPersonid -> entity.applyPersonid,
        column.applyDepartid -> entity.applyDepartid,
        column.applyDate -> entity.applyDate,
        column.applyResult -> entity.applyResult,
        column.approvalsAdvice -> entity.approvalsAdvice,
        column.approvalsPersonid -> entity.approvalsPersonid,
        column.approvalsDate -> entity.approvalsDate,
        column.approvalsDepartid -> entity.approvalsDepartid
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.updateBody, entity.updateBody).and.eq(column.applyPersonid, entity.applyPersonid).and.eq(column.applyDepartid, entity.applyDepartid).and.eq(column.applyDate, entity.applyDate).and.eq(column.applyResult, entity.applyResult).and.eq(column.approvalsAdvice, entity.approvalsAdvice).and.eq(column.approvalsPersonid, entity.approvalsPersonid).and.eq(column.approvalsDate, entity.approvalsDate).and.eq(column.approvalsDepartid, entity.approvalsDepartid)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherApplyupdate)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherApplyupdate).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.updateBody, entity.updateBody).and.eq(column.applyPersonid, entity.applyPersonid).and.eq(column.applyDepartid, entity.applyDepartid).and.eq(column.applyDate, entity.applyDate).and.eq(column.applyResult, entity.applyResult).and.eq(column.approvalsAdvice, entity.approvalsAdvice).and.eq(column.approvalsPersonid, entity.approvalsPersonid).and.eq(column.approvalsDate, entity.approvalsDate).and.eq(column.approvalsDepartid, entity.approvalsDepartid) }.update.apply()
  }

}
