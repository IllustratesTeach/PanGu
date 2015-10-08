package nirvana.hall.api.entities

import scalikejdbc._

case class GafisCaseFingerUpdateIdss(
  caseId: Option[String] = None,
  seqNo: Option[String] = None,
  fingerId: String,
  newSeqno: Option[String] = None,
  newFingerId: Option[String] = None) {

  def save()(implicit session: DBSession = GafisCaseFingerUpdateIdss.autoSession): GafisCaseFingerUpdateIdss = GafisCaseFingerUpdateIdss.save(this)(session)

  def destroy()(implicit session: DBSession = GafisCaseFingerUpdateIdss.autoSession): Unit = GafisCaseFingerUpdateIdss.destroy(this)(session)

}


object GafisCaseFingerUpdateIdss extends SQLSyntaxSupport[GafisCaseFingerUpdateIdss] {

  override val tableName = "GAFIS_CASE_FINGER_UPDATE_IDSS"

  override val columns = Seq("CASE_ID", "SEQ_NO", "FINGER_ID", "NEW_SEQNO", "NEW_FINGER_ID")

  def apply(gcfui: SyntaxProvider[GafisCaseFingerUpdateIdss])(rs: WrappedResultSet): GafisCaseFingerUpdateIdss = apply(gcfui.resultName)(rs)
  def apply(gcfui: ResultName[GafisCaseFingerUpdateIdss])(rs: WrappedResultSet): GafisCaseFingerUpdateIdss = new GafisCaseFingerUpdateIdss(
    caseId = rs.get(gcfui.caseId),
    seqNo = rs.get(gcfui.seqNo),
    fingerId = rs.get(gcfui.fingerId),
    newSeqno = rs.get(gcfui.newSeqno),
    newFingerId = rs.get(gcfui.newFingerId)
  )

  val gcfui = GafisCaseFingerUpdateIdss.syntax("gcfui")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(caseId: Option[String], seqNo: Option[String], fingerId: String, newSeqno: Option[String], newFingerId: Option[String])(implicit session: DBSession = autoSession): Option[GafisCaseFingerUpdateIdss] = {
    withSQL {
      select.from(GafisCaseFingerUpdateIdss as gcfui).where.eq(gcfui.caseId, caseId).and.eq(gcfui.seqNo, seqNo).and.eq(gcfui.fingerId, fingerId).and.eq(gcfui.newSeqno, newSeqno).and.eq(gcfui.newFingerId, newFingerId)
    }.map(GafisCaseFingerUpdateIdss(gcfui.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisCaseFingerUpdateIdss] = {
    withSQL(select.from(GafisCaseFingerUpdateIdss as gcfui)).map(GafisCaseFingerUpdateIdss(gcfui.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisCaseFingerUpdateIdss as gcfui)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisCaseFingerUpdateIdss] = {
    withSQL {
      select.from(GafisCaseFingerUpdateIdss as gcfui).where.append(where)
    }.map(GafisCaseFingerUpdateIdss(gcfui.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisCaseFingerUpdateIdss] = {
    withSQL {
      select.from(GafisCaseFingerUpdateIdss as gcfui).where.append(where)
    }.map(GafisCaseFingerUpdateIdss(gcfui.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisCaseFingerUpdateIdss as gcfui).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    caseId: Option[String] = None,
    seqNo: Option[String] = None,
    fingerId: String,
    newSeqno: Option[String] = None,
    newFingerId: Option[String] = None)(implicit session: DBSession = autoSession): GafisCaseFingerUpdateIdss = {
    withSQL {
      insert.into(GafisCaseFingerUpdateIdss).columns(
        column.caseId,
        column.seqNo,
        column.fingerId,
        column.newSeqno,
        column.newFingerId
      ).values(
        caseId,
        seqNo,
        fingerId,
        newSeqno,
        newFingerId
      )
    }.update.apply()

    GafisCaseFingerUpdateIdss(
      caseId = caseId,
      seqNo = seqNo,
      fingerId = fingerId,
      newSeqno = newSeqno,
      newFingerId = newFingerId)
  }

  def save(entity: GafisCaseFingerUpdateIdss)(implicit session: DBSession = autoSession): GafisCaseFingerUpdateIdss = {
    withSQL {
      update(GafisCaseFingerUpdateIdss).set(
        column.caseId -> entity.caseId,
        column.seqNo -> entity.seqNo,
        column.fingerId -> entity.fingerId,
        column.newSeqno -> entity.newSeqno,
        column.newFingerId -> entity.newFingerId
      ).where.eq(column.caseId, entity.caseId).and.eq(column.seqNo, entity.seqNo).and.eq(column.fingerId, entity.fingerId).and.eq(column.newSeqno, entity.newSeqno).and.eq(column.newFingerId, entity.newFingerId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisCaseFingerUpdateIdss)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisCaseFingerUpdateIdss).where.eq(column.caseId, entity.caseId).and.eq(column.seqNo, entity.seqNo).and.eq(column.fingerId, entity.fingerId).and.eq(column.newSeqno, entity.newSeqno).and.eq(column.newFingerId, entity.newFingerId) }.update.apply()
  }

}
