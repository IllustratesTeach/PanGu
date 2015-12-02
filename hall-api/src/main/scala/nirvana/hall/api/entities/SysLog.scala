package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysLog(
  pkId: String,
  addreIp: Option[String] = None,
  userName: Option[String] = None,
  userId: Option[String] = None,
  createDatetime: DateTime,
  deleteFlag: Long,
  remark: Option[String] = None,
  logType: Option[String] = None) {

  def save()(implicit session: DBSession = SysLog.autoSession): SysLog = SysLog.save(this)(session)

  def destroy()(implicit session: DBSession = SysLog.autoSession): Unit = SysLog.destroy(this)(session)

}


object SysLog extends SQLSyntaxSupport[SysLog] {

  override val tableName = "SYS_LOG"

  override val columns = Seq("PK_ID", "ADDRE_IP", "USER_NAME", "USER_ID", "CREATE_DATETIME", "DELETE_FLAG", "REMARK", "LOG_TYPE")

  def apply(sl: SyntaxProvider[SysLog])(rs: WrappedResultSet): SysLog = apply(sl.resultName)(rs)
  def apply(sl: ResultName[SysLog])(rs: WrappedResultSet): SysLog = new SysLog(
    pkId = rs.get(sl.pkId),
    addreIp = rs.get(sl.addreIp),
    userName = rs.get(sl.userName),
    userId = rs.get(sl.userId),
    createDatetime = rs.get(sl.createDatetime),
    deleteFlag = rs.get(sl.deleteFlag),
    remark = rs.get(sl.remark),
    logType = rs.get(sl.logType)
  )

  val sl = SysLog.syntax("sl")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[SysLog] = {
    withSQL {
      select.from(SysLog as sl).where.eq(sl.pkId, pkId)
    }.map(SysLog(sl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysLog] = {
    withSQL(select.from(SysLog as sl)).map(SysLog(sl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysLog as sl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysLog] = {
    withSQL {
      select.from(SysLog as sl).where.append(where)
    }.map(SysLog(sl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysLog] = {
    withSQL {
      select.from(SysLog as sl).where.append(where)
    }.map(SysLog(sl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysLog as sl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    addreIp: Option[String] = None,
    userName: Option[String] = None,
    userId: Option[String] = None,
    createDatetime: DateTime,
    deleteFlag: Long,
    remark: Option[String] = None,
    logType: Option[String] = None)(implicit session: DBSession = autoSession): SysLog = {
    withSQL {
      insert.into(SysLog).columns(
        column.pkId,
        column.addreIp,
        column.userName,
        column.userId,
        column.createDatetime,
        column.deleteFlag,
        column.remark,
        column.logType
      ).values(
        pkId,
        addreIp,
        userName,
        userId,
        createDatetime,
        deleteFlag,
        remark,
        logType
      )
    }.update.apply()

    SysLog(
      pkId = pkId,
      addreIp = addreIp,
      userName = userName,
      userId = userId,
      createDatetime = createDatetime,
      deleteFlag = deleteFlag,
      remark = remark,
      logType = logType)
  }

  def save(entity: SysLog)(implicit session: DBSession = autoSession): SysLog = {
    withSQL {
      update(SysLog).set(
        column.pkId -> entity.pkId,
        column.addreIp -> entity.addreIp,
        column.userName -> entity.userName,
        column.userId -> entity.userId,
        column.createDatetime -> entity.createDatetime,
        column.deleteFlag -> entity.deleteFlag,
        column.remark -> entity.remark,
        column.logType -> entity.logType
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: SysLog)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysLog).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
