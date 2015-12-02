package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisCaseFingerMnt(
  pkId: String,
  fingerId: Option[String] = None,
  captureMethod: Option[String] = None,
  fingerMnt: Option[Blob] = None,
  fingerRidge: Option[Blob] = None,
  isMainMnt: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  inputpsn: Option[String] = None,
  deletag: Option[String] = None,
  fingerMntNosqlId: Option[String] = None,
  fingerRidgeNosqlId: Option[String] = None) {

  def save()(implicit session: DBSession = GafisCaseFingerMnt.autoSession): GafisCaseFingerMnt = GafisCaseFingerMnt.save(this)(session)

  def destroy()(implicit session: DBSession = GafisCaseFingerMnt.autoSession): Unit = GafisCaseFingerMnt.destroy(this)(session)

}


object GafisCaseFingerMnt extends SQLSyntaxSupport[GafisCaseFingerMnt] {

  override val tableName = "GAFIS_CASE_FINGER_MNT"

  override val columns = Seq("PK_ID", "FINGER_ID", "CAPTURE_METHOD", "FINGER_MNT", "FINGER_RIDGE", "IS_MAIN_MNT", "MODIFIEDTIME", "MODIFIEDPSN", "INPUTTIME", "INPUTPSN", "DELETAG", "FINGER_MNT_NOSQL_ID", "FINGER_RIDGE_NOSQL_ID")

  def apply(gcfm: SyntaxProvider[GafisCaseFingerMnt])(rs: WrappedResultSet): GafisCaseFingerMnt = apply(gcfm.resultName)(rs)
  def apply(gcfm: ResultName[GafisCaseFingerMnt])(rs: WrappedResultSet): GafisCaseFingerMnt = new GafisCaseFingerMnt(
    pkId = rs.get(gcfm.pkId),
    fingerId = rs.get(gcfm.fingerId),
    captureMethod = rs.get(gcfm.captureMethod),
    fingerMnt = rs.get(gcfm.fingerMnt),
    fingerRidge = rs.get(gcfm.fingerRidge),
    isMainMnt = rs.get(gcfm.isMainMnt),
    modifiedtime = rs.get(gcfm.modifiedtime),
    modifiedpsn = rs.get(gcfm.modifiedpsn),
    inputtime = rs.get(gcfm.inputtime),
    inputpsn = rs.get(gcfm.inputpsn),
    deletag = rs.get(gcfm.deletag),
    fingerMntNosqlId = rs.get(gcfm.fingerMntNosqlId),
    fingerRidgeNosqlId = rs.get(gcfm.fingerRidgeNosqlId)
  )

  val gcfm = GafisCaseFingerMnt.syntax("gcfm")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[GafisCaseFingerMnt] = {
    withSQL {
      select.from(GafisCaseFingerMnt as gcfm).where.eq(gcfm.pkId, pkId)
    }.map(GafisCaseFingerMnt(gcfm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisCaseFingerMnt] = {
    withSQL(select.from(GafisCaseFingerMnt as gcfm)).map(GafisCaseFingerMnt(gcfm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisCaseFingerMnt as gcfm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisCaseFingerMnt] = {
    withSQL {
      select.from(GafisCaseFingerMnt as gcfm).where.append(where)
    }.map(GafisCaseFingerMnt(gcfm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisCaseFingerMnt] = {
    withSQL {
      select.from(GafisCaseFingerMnt as gcfm).where.append(where)
    }.map(GafisCaseFingerMnt(gcfm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisCaseFingerMnt as gcfm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    fingerId: Option[String] = None,
    captureMethod: Option[String] = None,
    fingerMnt: Option[Blob] = None,
    fingerRidge: Option[Blob] = None,
    isMainMnt: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    inputpsn: Option[String] = None,
    deletag: Option[String] = None,
    fingerMntNosqlId: Option[String] = None,
    fingerRidgeNosqlId: Option[String] = None)(implicit session: DBSession = autoSession): GafisCaseFingerMnt = {
    withSQL {
      insert.into(GafisCaseFingerMnt).columns(
        column.pkId,
        column.fingerId,
        column.captureMethod,
        column.fingerMnt,
        column.fingerRidge,
        column.isMainMnt,
        column.modifiedtime,
        column.modifiedpsn,
        column.inputtime,
        column.inputpsn,
        column.deletag,
        column.fingerMntNosqlId,
        column.fingerRidgeNosqlId
      ).values(
        pkId,
        fingerId,
        captureMethod,
        fingerMnt,
        fingerRidge,
        isMainMnt,
        modifiedtime,
        modifiedpsn,
        inputtime,
        inputpsn,
        deletag,
        fingerMntNosqlId,
        fingerRidgeNosqlId
      )
    }.update.apply()

    GafisCaseFingerMnt(
      pkId = pkId,
      fingerId = fingerId,
      captureMethod = captureMethod,
      fingerMnt = fingerMnt,
      fingerRidge = fingerRidge,
      isMainMnt = isMainMnt,
      modifiedtime = modifiedtime,
      modifiedpsn = modifiedpsn,
      inputtime = inputtime,
      inputpsn = inputpsn,
      deletag = deletag,
      fingerMntNosqlId = fingerMntNosqlId,
      fingerRidgeNosqlId = fingerRidgeNosqlId)
  }

  def save(entity: GafisCaseFingerMnt)(implicit session: DBSession = autoSession): GafisCaseFingerMnt = {
    withSQL {
      update(GafisCaseFingerMnt).set(
        column.pkId -> entity.pkId,
        column.fingerId -> entity.fingerId,
        column.captureMethod -> entity.captureMethod,
        column.fingerMnt -> entity.fingerMnt,
        column.fingerRidge -> entity.fingerRidge,
        column.isMainMnt -> entity.isMainMnt,
        column.modifiedtime -> entity.modifiedtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.inputtime -> entity.inputtime,
        column.inputpsn -> entity.inputpsn,
        column.deletag -> entity.deletag,
        column.fingerMntNosqlId -> entity.fingerMntNosqlId,
        column.fingerRidgeNosqlId -> entity.fingerRidgeNosqlId
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisCaseFingerMnt)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisCaseFingerMnt).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
