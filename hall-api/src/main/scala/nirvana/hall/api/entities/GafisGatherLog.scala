package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherLog(
  pkId: String,
  addreIp: Option[String] = None,
  userId: Option[String] = None,
  createDatetime: DateTime,
  deleteFlag: Long,
  remark: Option[String] = None,
  logType: Option[String] = None,
  personid: Option[String] = None,
  fieldName: Option[String] = None,
  modifyBefore: Option[String] = None,
  modifyAfter: Option[String] = None,
  userName: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherLog.autoSession): GafisGatherLog = GafisGatherLog.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherLog.autoSession): Unit = GafisGatherLog.destroy(this)(session)

}


object GafisGatherLog extends SQLSyntaxSupport[GafisGatherLog] {

  override val tableName = "GAFIS_GATHER_LOG"

  override val columns = Seq("PK_ID", "ADDRE_IP", "USER_ID", "CREATE_DATETIME", "DELETE_FLAG", "REMARK", "LOG_TYPE", "PERSONID", "FIELD_NAME", "MODIFY_BEFORE", "MODIFY_AFTER", "USER_NAME")

  def apply(ggl: SyntaxProvider[GafisGatherLog])(rs: WrappedResultSet): GafisGatherLog = apply(ggl.resultName)(rs)
  def apply(ggl: ResultName[GafisGatherLog])(rs: WrappedResultSet): GafisGatherLog = new GafisGatherLog(
    pkId = rs.get(ggl.pkId),
    addreIp = rs.get(ggl.addreIp),
    userId = rs.get(ggl.userId),
    createDatetime = rs.get(ggl.createDatetime),
    deleteFlag = rs.get(ggl.deleteFlag),
    remark = rs.get(ggl.remark),
    logType = rs.get(ggl.logType),
    personid = rs.get(ggl.personid),
    fieldName = rs.get(ggl.fieldName),
    modifyBefore = rs.get(ggl.modifyBefore),
    modifyAfter = rs.get(ggl.modifyAfter),
    userName = rs.get(ggl.userName)
  )

  val ggl = GafisGatherLog.syntax("ggl")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, addreIp: Option[String], userId: Option[String], createDatetime: DateTime, deleteFlag: Long, remark: Option[String], logType: Option[String], personid: Option[String], fieldName: Option[String], modifyBefore: Option[String], modifyAfter: Option[String], userName: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherLog] = {
    withSQL {
      select.from(GafisGatherLog as ggl).where.eq(ggl.pkId, pkId).and.eq(ggl.addreIp, addreIp).and.eq(ggl.userId, userId).and.eq(ggl.createDatetime, createDatetime).and.eq(ggl.deleteFlag, deleteFlag).and.eq(ggl.remark, remark).and.eq(ggl.logType, logType).and.eq(ggl.personid, personid).and.eq(ggl.fieldName, fieldName).and.eq(ggl.modifyBefore, modifyBefore).and.eq(ggl.modifyAfter, modifyAfter).and.eq(ggl.userName, userName)
    }.map(GafisGatherLog(ggl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherLog] = {
    withSQL(select.from(GafisGatherLog as ggl)).map(GafisGatherLog(ggl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherLog as ggl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherLog] = {
    withSQL {
      select.from(GafisGatherLog as ggl).where.append(where)
    }.map(GafisGatherLog(ggl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherLog] = {
    withSQL {
      select.from(GafisGatherLog as ggl).where.append(where)
    }.map(GafisGatherLog(ggl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherLog as ggl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    addreIp: Option[String] = None,
    userId: Option[String] = None,
    createDatetime: DateTime,
    deleteFlag: Long,
    remark: Option[String] = None,
    logType: Option[String] = None,
    personid: Option[String] = None,
    fieldName: Option[String] = None,
    modifyBefore: Option[String] = None,
    modifyAfter: Option[String] = None,
    userName: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherLog = {
    withSQL {
      insert.into(GafisGatherLog).columns(
        column.pkId,
        column.addreIp,
        column.userId,
        column.createDatetime,
        column.deleteFlag,
        column.remark,
        column.logType,
        column.personid,
        column.fieldName,
        column.modifyBefore,
        column.modifyAfter,
        column.userName
      ).values(
        pkId,
        addreIp,
        userId,
        createDatetime,
        deleteFlag,
        remark,
        logType,
        personid,
        fieldName,
        modifyBefore,
        modifyAfter,
        userName
      )
    }.update.apply()

    GafisGatherLog(
      pkId = pkId,
      addreIp = addreIp,
      userId = userId,
      createDatetime = createDatetime,
      deleteFlag = deleteFlag,
      remark = remark,
      logType = logType,
      personid = personid,
      fieldName = fieldName,
      modifyBefore = modifyBefore,
      modifyAfter = modifyAfter,
      userName = userName)
  }

  def save(entity: GafisGatherLog)(implicit session: DBSession = autoSession): GafisGatherLog = {
    withSQL {
      update(GafisGatherLog).set(
        column.pkId -> entity.pkId,
        column.addreIp -> entity.addreIp,
        column.userId -> entity.userId,
        column.createDatetime -> entity.createDatetime,
        column.deleteFlag -> entity.deleteFlag,
        column.remark -> entity.remark,
        column.logType -> entity.logType,
        column.personid -> entity.personid,
        column.fieldName -> entity.fieldName,
        column.modifyBefore -> entity.modifyBefore,
        column.modifyAfter -> entity.modifyAfter,
        column.userName -> entity.userName
      ).where.eq(column.pkId, entity.pkId).and.eq(column.addreIp, entity.addreIp).and.eq(column.userId, entity.userId).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.remark, entity.remark).and.eq(column.logType, entity.logType).and.eq(column.personid, entity.personid).and.eq(column.fieldName, entity.fieldName).and.eq(column.modifyBefore, entity.modifyBefore).and.eq(column.modifyAfter, entity.modifyAfter).and.eq(column.userName, entity.userName)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherLog)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherLog).where.eq(column.pkId, entity.pkId).and.eq(column.addreIp, entity.addreIp).and.eq(column.userId, entity.userId).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.remark, entity.remark).and.eq(column.logType, entity.logType).and.eq(column.personid, entity.personid).and.eq(column.fieldName, entity.fieldName).and.eq(column.modifyBefore, entity.modifyBefore).and.eq(column.modifyAfter, entity.modifyAfter).and.eq(column.userName, entity.userName) }.update.apply()
  }

}
