package nirvana.hall.api.entities

import scalikejdbc._

case class SyncTarget(
  pkId: String,
  targetSid: Option[String] = None,
  targetName: Option[String] = None,
  targetIp: Option[String] = None,
  targetPort: Option[String] = None,
  targetUsername: Option[String] = None,
  deltag: Option[String] = None) {

  def save()(implicit session: DBSession = SyncTarget.autoSession): SyncTarget = SyncTarget.save(this)(session)

  def destroy()(implicit session: DBSession = SyncTarget.autoSession): Unit = SyncTarget.destroy(this)(session)

}


object SyncTarget extends SQLSyntaxSupport[SyncTarget] {

  override val tableName = "SYNC_TARGET"

  override val columns = Seq("PK_ID", "TARGET_SID", "TARGET_NAME", "TARGET_IP", "TARGET_PORT", "TARGET_USERNAME", "DELTAG")

  def apply(st: SyntaxProvider[SyncTarget])(rs: WrappedResultSet): SyncTarget = apply(st.resultName)(rs)
  def apply(st: ResultName[SyncTarget])(rs: WrappedResultSet): SyncTarget = new SyncTarget(
    pkId = rs.get(st.pkId),
    targetSid = rs.get(st.targetSid),
    targetName = rs.get(st.targetName),
    targetIp = rs.get(st.targetIp),
    targetPort = rs.get(st.targetPort),
    targetUsername = rs.get(st.targetUsername),
    deltag = rs.get(st.deltag)
  )

  val st = SyncTarget.syntax("st")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[SyncTarget] = {
    withSQL {
      select.from(SyncTarget as st).where.eq(st.pkId, pkId)
    }.map(SyncTarget(st.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SyncTarget] = {
    withSQL(select.from(SyncTarget as st)).map(SyncTarget(st.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SyncTarget as st)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SyncTarget] = {
    withSQL {
      select.from(SyncTarget as st).where.append(where)
    }.map(SyncTarget(st.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SyncTarget] = {
    withSQL {
      select.from(SyncTarget as st).where.append(where)
    }.map(SyncTarget(st.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SyncTarget as st).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    targetSid: Option[String] = None,
    targetName: Option[String] = None,
    targetIp: Option[String] = None,
    targetPort: Option[String] = None,
    targetUsername: Option[String] = None,
    deltag: Option[String] = None)(implicit session: DBSession = autoSession): SyncTarget = {
    withSQL {
      insert.into(SyncTarget).columns(
        column.pkId,
        column.targetSid,
        column.targetName,
        column.targetIp,
        column.targetPort,
        column.targetUsername,
        column.deltag
      ).values(
        pkId,
        targetSid,
        targetName,
        targetIp,
        targetPort,
        targetUsername,
        deltag
      )
    }.update.apply()

    SyncTarget(
      pkId = pkId,
      targetSid = targetSid,
      targetName = targetName,
      targetIp = targetIp,
      targetPort = targetPort,
      targetUsername = targetUsername,
      deltag = deltag)
  }

  def save(entity: SyncTarget)(implicit session: DBSession = autoSession): SyncTarget = {
    withSQL {
      update(SyncTarget).set(
        column.pkId -> entity.pkId,
        column.targetSid -> entity.targetSid,
        column.targetName -> entity.targetName,
        column.targetIp -> entity.targetIp,
        column.targetPort -> entity.targetPort,
        column.targetUsername -> entity.targetUsername,
        column.deltag -> entity.deltag
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: SyncTarget)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SyncTarget).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
