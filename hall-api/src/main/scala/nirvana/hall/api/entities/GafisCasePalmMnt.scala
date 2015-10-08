package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisCasePalmMnt(
  pkId: String,
  palmId: Option[String] = None,
  captureMethod: Option[String] = None,
  palmMnt: Option[Blob] = None,
  palmRidge: Option[Blob] = None,
  isMainMnt: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  inputpsn: Option[String] = None,
  palmMntNosqlId: Option[String] = None,
  palmRidgeNosqlId: Option[String] = None) {

  def save()(implicit session: DBSession = GafisCasePalmMnt.autoSession): GafisCasePalmMnt = GafisCasePalmMnt.save(this)(session)

  def destroy()(implicit session: DBSession = GafisCasePalmMnt.autoSession): Unit = GafisCasePalmMnt.destroy(this)(session)

}


object GafisCasePalmMnt extends SQLSyntaxSupport[GafisCasePalmMnt] {

  override val tableName = "GAFIS_CASE_PALM_MNT"

  override val columns = Seq("PK_ID", "PALM_ID", "CAPTURE_METHOD", "PALM_MNT", "PALM_RIDGE", "IS_MAIN_MNT", "MODIFIEDTIME", "MODIFIEDPSN", "INPUTTIME", "INPUTPSN", "PALM_MNT_NOSQL_ID", "PALM_RIDGE_NOSQL_ID")

  def apply(gcpm: SyntaxProvider[GafisCasePalmMnt])(rs: WrappedResultSet): GafisCasePalmMnt = apply(gcpm.resultName)(rs)
  def apply(gcpm: ResultName[GafisCasePalmMnt])(rs: WrappedResultSet): GafisCasePalmMnt = new GafisCasePalmMnt(
    pkId = rs.get(gcpm.pkId),
    palmId = rs.get(gcpm.palmId),
    captureMethod = rs.get(gcpm.captureMethod),
    palmMnt = rs.get(gcpm.palmMnt),
    palmRidge = rs.get(gcpm.palmRidge),
    isMainMnt = rs.get(gcpm.isMainMnt),
    modifiedtime = rs.get(gcpm.modifiedtime),
    modifiedpsn = rs.get(gcpm.modifiedpsn),
    inputtime = rs.get(gcpm.inputtime),
    inputpsn = rs.get(gcpm.inputpsn),
    palmMntNosqlId = rs.get(gcpm.palmMntNosqlId),
    palmRidgeNosqlId = rs.get(gcpm.palmRidgeNosqlId)
  )

  val gcpm = GafisCasePalmMnt.syntax("gcpm")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, palmId: Option[String], captureMethod: Option[String], palmMnt: Option[Blob], palmRidge: Option[Blob], isMainMnt: Option[String], modifiedtime: Option[DateTime], modifiedpsn: Option[String], inputtime: Option[DateTime], inputpsn: Option[String], palmMntNosqlId: Option[String], palmRidgeNosqlId: Option[String])(implicit session: DBSession = autoSession): Option[GafisCasePalmMnt] = {
    withSQL {
      select.from(GafisCasePalmMnt as gcpm).where.eq(gcpm.pkId, pkId).and.eq(gcpm.palmId, palmId).and.eq(gcpm.captureMethod, captureMethod).and.eq(gcpm.palmMnt, palmMnt).and.eq(gcpm.palmRidge, palmRidge).and.eq(gcpm.isMainMnt, isMainMnt).and.eq(gcpm.modifiedtime, modifiedtime).and.eq(gcpm.modifiedpsn, modifiedpsn).and.eq(gcpm.inputtime, inputtime).and.eq(gcpm.inputpsn, inputpsn).and.eq(gcpm.palmMntNosqlId, palmMntNosqlId).and.eq(gcpm.palmRidgeNosqlId, palmRidgeNosqlId)
    }.map(GafisCasePalmMnt(gcpm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisCasePalmMnt] = {
    withSQL(select.from(GafisCasePalmMnt as gcpm)).map(GafisCasePalmMnt(gcpm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisCasePalmMnt as gcpm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisCasePalmMnt] = {
    withSQL {
      select.from(GafisCasePalmMnt as gcpm).where.append(where)
    }.map(GafisCasePalmMnt(gcpm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisCasePalmMnt] = {
    withSQL {
      select.from(GafisCasePalmMnt as gcpm).where.append(where)
    }.map(GafisCasePalmMnt(gcpm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisCasePalmMnt as gcpm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    palmId: Option[String] = None,
    captureMethod: Option[String] = None,
    palmMnt: Option[Blob] = None,
    palmRidge: Option[Blob] = None,
    isMainMnt: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    inputpsn: Option[String] = None,
    palmMntNosqlId: Option[String] = None,
    palmRidgeNosqlId: Option[String] = None)(implicit session: DBSession = autoSession): GafisCasePalmMnt = {
    withSQL {
      insert.into(GafisCasePalmMnt).columns(
        column.pkId,
        column.palmId,
        column.captureMethod,
        column.palmMnt,
        column.palmRidge,
        column.isMainMnt,
        column.modifiedtime,
        column.modifiedpsn,
        column.inputtime,
        column.inputpsn,
        column.palmMntNosqlId,
        column.palmRidgeNosqlId
      ).values(
        pkId,
        palmId,
        captureMethod,
        palmMnt,
        palmRidge,
        isMainMnt,
        modifiedtime,
        modifiedpsn,
        inputtime,
        inputpsn,
        palmMntNosqlId,
        palmRidgeNosqlId
      )
    }.update.apply()

    GafisCasePalmMnt(
      pkId = pkId,
      palmId = palmId,
      captureMethod = captureMethod,
      palmMnt = palmMnt,
      palmRidge = palmRidge,
      isMainMnt = isMainMnt,
      modifiedtime = modifiedtime,
      modifiedpsn = modifiedpsn,
      inputtime = inputtime,
      inputpsn = inputpsn,
      palmMntNosqlId = palmMntNosqlId,
      palmRidgeNosqlId = palmRidgeNosqlId)
  }

  def save(entity: GafisCasePalmMnt)(implicit session: DBSession = autoSession): GafisCasePalmMnt = {
    withSQL {
      update(GafisCasePalmMnt).set(
        column.pkId -> entity.pkId,
        column.palmId -> entity.palmId,
        column.captureMethod -> entity.captureMethod,
        column.palmMnt -> entity.palmMnt,
        column.palmRidge -> entity.palmRidge,
        column.isMainMnt -> entity.isMainMnt,
        column.modifiedtime -> entity.modifiedtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.inputtime -> entity.inputtime,
        column.inputpsn -> entity.inputpsn,
        column.palmMntNosqlId -> entity.palmMntNosqlId,
        column.palmRidgeNosqlId -> entity.palmRidgeNosqlId
      ).where.eq(column.pkId, entity.pkId).and.eq(column.palmId, entity.palmId).and.eq(column.captureMethod, entity.captureMethod).and.eq(column.palmMnt, entity.palmMnt).and.eq(column.palmRidge, entity.palmRidge).and.eq(column.isMainMnt, entity.isMainMnt).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.palmMntNosqlId, entity.palmMntNosqlId).and.eq(column.palmRidgeNosqlId, entity.palmRidgeNosqlId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisCasePalmMnt)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisCasePalmMnt).where.eq(column.pkId, entity.pkId).and.eq(column.palmId, entity.palmId).and.eq(column.captureMethod, entity.captureMethod).and.eq(column.palmMnt, entity.palmMnt).and.eq(column.palmRidge, entity.palmRidge).and.eq(column.isMainMnt, entity.isMainMnt).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.palmMntNosqlId, entity.palmMntNosqlId).and.eq(column.palmRidgeNosqlId, entity.palmRidgeNosqlId) }.update.apply()
  }

}
