package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SyncQueue(
  pkId: String,
  uploadKeyid: Option[String] = None,
  uploadFlag: Option[String] = None,
  uploadType: Option[String] = None,
  uploadStatus: Option[String] = None,
  uplaodTimes: Option[Short] = None,
  hasPalm: Option[String] = None,
  targetIp: Option[String] = None,
  targetPort: Option[String] = None,
  targetUsername: Option[String] = None,
  targetSid: Option[String] = None,
  createdate: Option[DateTime] = None,
  finishdate: Option[DateTime] = None,
  opration: Option[String] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = SyncQueue.autoSession): SyncQueue = SyncQueue.save(this)(session)

  def destroy()(implicit session: DBSession = SyncQueue.autoSession): Unit = SyncQueue.destroy(this)(session)

}


object SyncQueue extends SQLSyntaxSupport[SyncQueue] {

  override val tableName = "SYNC_QUEUE"

  override val columns = Seq("PK_ID", "UPLOAD_KEYID", "UPLOAD_FLAG", "UPLOAD_TYPE", "UPLOAD_STATUS", "UPLAOD_TIMES", "HAS_PALM", "TARGET_IP", "TARGET_PORT", "TARGET_USERNAME", "TARGET_SID", "CREATEDATE", "FINISHDATE", "OPRATION", "REMARK")

  def apply(sq: SyntaxProvider[SyncQueue])(rs: WrappedResultSet): SyncQueue = apply(sq.resultName)(rs)
  def apply(sq: ResultName[SyncQueue])(rs: WrappedResultSet): SyncQueue = new SyncQueue(
    pkId = rs.get(sq.pkId),
    uploadKeyid = rs.get(sq.uploadKeyid),
    uploadFlag = rs.get(sq.uploadFlag),
    uploadType = rs.get(sq.uploadType),
    uploadStatus = rs.get(sq.uploadStatus),
    uplaodTimes = rs.get(sq.uplaodTimes),
    hasPalm = rs.get(sq.hasPalm),
    targetIp = rs.get(sq.targetIp),
    targetPort = rs.get(sq.targetPort),
    targetUsername = rs.get(sq.targetUsername),
    targetSid = rs.get(sq.targetSid),
    createdate = rs.get(sq.createdate),
    finishdate = rs.get(sq.finishdate),
    opration = rs.get(sq.opration),
    remark = rs.get(sq.remark)
  )

  val sq = SyncQueue.syntax("sq")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[SyncQueue] = {
    withSQL {
      select.from(SyncQueue as sq).where.eq(sq.pkId, pkId)
    }.map(SyncQueue(sq.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SyncQueue] = {
    withSQL(select.from(SyncQueue as sq)).map(SyncQueue(sq.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SyncQueue as sq)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SyncQueue] = {
    withSQL {
      select.from(SyncQueue as sq).where.append(where)
    }.map(SyncQueue(sq.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SyncQueue] = {
    withSQL {
      select.from(SyncQueue as sq).where.append(where)
    }.map(SyncQueue(sq.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SyncQueue as sq).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    uploadKeyid: Option[String] = None,
    uploadFlag: Option[String] = None,
    uploadType: Option[String] = None,
    uploadStatus: Option[String] = None,
    uplaodTimes: Option[Short] = None,
    hasPalm: Option[String] = None,
    targetIp: Option[String] = None,
    targetPort: Option[String] = None,
    targetUsername: Option[String] = None,
    targetSid: Option[String] = None,
    createdate: Option[DateTime] = None,
    finishdate: Option[DateTime] = None,
    opration: Option[String] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): SyncQueue = {
    withSQL {
      insert.into(SyncQueue).columns(
        column.pkId,
        column.uploadKeyid,
        column.uploadFlag,
        column.uploadType,
        column.uploadStatus,
        column.uplaodTimes,
        column.hasPalm,
        column.targetIp,
        column.targetPort,
        column.targetUsername,
        column.targetSid,
        column.createdate,
        column.finishdate,
        column.opration,
        column.remark
      ).values(
        pkId,
        uploadKeyid,
        uploadFlag,
        uploadType,
        uploadStatus,
        uplaodTimes,
        hasPalm,
        targetIp,
        targetPort,
        targetUsername,
        targetSid,
        createdate,
        finishdate,
        opration,
        remark
      )
    }.update.apply()

    SyncQueue(
      pkId = pkId,
      uploadKeyid = uploadKeyid,
      uploadFlag = uploadFlag,
      uploadType = uploadType,
      uploadStatus = uploadStatus,
      uplaodTimes = uplaodTimes,
      hasPalm = hasPalm,
      targetIp = targetIp,
      targetPort = targetPort,
      targetUsername = targetUsername,
      targetSid = targetSid,
      createdate = createdate,
      finishdate = finishdate,
      opration = opration,
      remark = remark)
  }

  def save(entity: SyncQueue)(implicit session: DBSession = autoSession): SyncQueue = {
    withSQL {
      update(SyncQueue).set(
        column.pkId -> entity.pkId,
        column.uploadKeyid -> entity.uploadKeyid,
        column.uploadFlag -> entity.uploadFlag,
        column.uploadType -> entity.uploadType,
        column.uploadStatus -> entity.uploadStatus,
        column.uplaodTimes -> entity.uplaodTimes,
        column.hasPalm -> entity.hasPalm,
        column.targetIp -> entity.targetIp,
        column.targetPort -> entity.targetPort,
        column.targetUsername -> entity.targetUsername,
        column.targetSid -> entity.targetSid,
        column.createdate -> entity.createdate,
        column.finishdate -> entity.finishdate,
        column.opration -> entity.opration,
        column.remark -> entity.remark
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: SyncQueue)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SyncQueue).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
