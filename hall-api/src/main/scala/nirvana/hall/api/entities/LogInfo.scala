package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class LogInfo(
  numId: Long,
  userId: Option[String] = None,
  organization: Option[String] = None,
  userName: Option[String] = None,
  terminalId: Option[String] = None,
  operateType: Option[Short] = None,
  operateTime: Option[DateTime] = None,
  operateCondition: Option[String] = None,
  operateResult: Option[String] = None,
  operateObject: Option[String] = None,
  departname: Option[String] = None,
  updateBefore: Option[String] = None,
  updateAfter: Option[String] = None,
  time: Option[DateTime] = None) {

  def save()(implicit session: DBSession = LogInfo.autoSession): LogInfo = LogInfo.save(this)(session)

  def destroy()(implicit session: DBSession = LogInfo.autoSession): Unit = LogInfo.destroy(this)(session)

}


object LogInfo extends SQLSyntaxSupport[LogInfo] {

  override val tableName = "LOG_INFO"

  override val columns = Seq("NUM_ID", "USER_ID", "ORGANIZATION", "USER_NAME", "TERMINAL_ID", "OPERATE_TYPE", "OPERATE_TIME", "OPERATE_CONDITION", "OPERATE_RESULT", "OPERATE_OBJECT", "DEPARTNAME", "UPDATE_BEFORE", "UPDATE_AFTER", "TIME")

  def apply(li: SyntaxProvider[LogInfo])(rs: WrappedResultSet): LogInfo = apply(li.resultName)(rs)
  def apply(li: ResultName[LogInfo])(rs: WrappedResultSet): LogInfo = new LogInfo(
    numId = rs.get(li.numId),
    userId = rs.get(li.userId),
    organization = rs.get(li.organization),
    userName = rs.get(li.userName),
    terminalId = rs.get(li.terminalId),
    operateType = rs.get(li.operateType),
    operateTime = rs.get(li.operateTime),
    operateCondition = rs.get(li.operateCondition),
    operateResult = rs.get(li.operateResult),
    operateObject = rs.get(li.operateObject),
    departname = rs.get(li.departname),
    updateBefore = rs.get(li.updateBefore),
    updateAfter = rs.get(li.updateAfter),
    time = rs.get(li.time)
  )

  val li = LogInfo.syntax("li")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(numId: Long)(implicit session: DBSession = autoSession): Option[LogInfo] = {
    withSQL {
      select.from(LogInfo as li).where.eq(li.numId, numId)
    }.map(LogInfo(li.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[LogInfo] = {
    withSQL(select.from(LogInfo as li)).map(LogInfo(li.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(LogInfo as li)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[LogInfo] = {
    withSQL {
      select.from(LogInfo as li).where.append(where)
    }.map(LogInfo(li.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[LogInfo] = {
    withSQL {
      select.from(LogInfo as li).where.append(where)
    }.map(LogInfo(li.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(LogInfo as li).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    numId: Long,
    userId: Option[String] = None,
    organization: Option[String] = None,
    userName: Option[String] = None,
    terminalId: Option[String] = None,
    operateType: Option[Short] = None,
    operateTime: Option[DateTime] = None,
    operateCondition: Option[String] = None,
    operateResult: Option[String] = None,
    operateObject: Option[String] = None,
    departname: Option[String] = None,
    updateBefore: Option[String] = None,
    updateAfter: Option[String] = None,
    time: Option[DateTime] = None)(implicit session: DBSession = autoSession): LogInfo = {
    withSQL {
      insert.into(LogInfo).columns(
        column.numId,
        column.userId,
        column.organization,
        column.userName,
        column.terminalId,
        column.operateType,
        column.operateTime,
        column.operateCondition,
        column.operateResult,
        column.operateObject,
        column.departname,
        column.updateBefore,
        column.updateAfter,
        column.time
      ).values(
        numId,
        userId,
        organization,
        userName,
        terminalId,
        operateType,
        operateTime,
        operateCondition,
        operateResult,
        operateObject,
        departname,
        updateBefore,
        updateAfter,
        time
      )
    }.update.apply()

    LogInfo(
      numId = numId,
      userId = userId,
      organization = organization,
      userName = userName,
      terminalId = terminalId,
      operateType = operateType,
      operateTime = operateTime,
      operateCondition = operateCondition,
      operateResult = operateResult,
      operateObject = operateObject,
      departname = departname,
      updateBefore = updateBefore,
      updateAfter = updateAfter,
      time = time)
  }

  def save(entity: LogInfo)(implicit session: DBSession = autoSession): LogInfo = {
    withSQL {
      update(LogInfo).set(
        column.numId -> entity.numId,
        column.userId -> entity.userId,
        column.organization -> entity.organization,
        column.userName -> entity.userName,
        column.terminalId -> entity.terminalId,
        column.operateType -> entity.operateType,
        column.operateTime -> entity.operateTime,
        column.operateCondition -> entity.operateCondition,
        column.operateResult -> entity.operateResult,
        column.operateObject -> entity.operateObject,
        column.departname -> entity.departname,
        column.updateBefore -> entity.updateBefore,
        column.updateAfter -> entity.updateAfter,
        column.time -> entity.time
      ).where.eq(column.numId, entity.numId)
    }.update.apply()
    entity
  }

  def destroy(entity: LogInfo)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(LogInfo).where.eq(column.numId, entity.numId) }.update.apply()
  }

}
