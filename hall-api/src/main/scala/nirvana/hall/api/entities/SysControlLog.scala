package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysControlLog(
  pkId: String,
  ip: Option[String] = None,
  mac: Option[String] = None,
  version: Option[String] = None,
  sysType: Option[String] = None,
  updateTime: Option[DateTime] = None,
  state: Option[String] = None) {

  def save()(implicit session: DBSession = SysControlLog.autoSession): SysControlLog = SysControlLog.save(this)(session)

  def destroy()(implicit session: DBSession = SysControlLog.autoSession): Unit = SysControlLog.destroy(this)(session)

}


object SysControlLog extends SQLSyntaxSupport[SysControlLog] {

  override val tableName = "SYS_CONTROL_LOG"

  override val columns = Seq("PK_ID", "IP", "MAC", "VERSION", "SYS_TYPE", "UPDATE_TIME", "STATE")

  def apply(scl: SyntaxProvider[SysControlLog])(rs: WrappedResultSet): SysControlLog = apply(scl.resultName)(rs)
  def apply(scl: ResultName[SysControlLog])(rs: WrappedResultSet): SysControlLog = new SysControlLog(
    pkId = rs.get(scl.pkId),
    ip = rs.get(scl.ip),
    mac = rs.get(scl.mac),
    version = rs.get(scl.version),
    sysType = rs.get(scl.sysType),
    updateTime = rs.get(scl.updateTime),
    state = rs.get(scl.state)
  )

  val scl = SysControlLog.syntax("scl")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[SysControlLog] = {
    withSQL {
      select.from(SysControlLog as scl).where.eq(scl.pkId, pkId)
    }.map(SysControlLog(scl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysControlLog] = {
    withSQL(select.from(SysControlLog as scl)).map(SysControlLog(scl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysControlLog as scl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysControlLog] = {
    withSQL {
      select.from(SysControlLog as scl).where.append(where)
    }.map(SysControlLog(scl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysControlLog] = {
    withSQL {
      select.from(SysControlLog as scl).where.append(where)
    }.map(SysControlLog(scl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysControlLog as scl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    ip: Option[String] = None,
    mac: Option[String] = None,
    version: Option[String] = None,
    sysType: Option[String] = None,
    updateTime: Option[DateTime] = None,
    state: Option[String] = None)(implicit session: DBSession = autoSession): SysControlLog = {
    withSQL {
      insert.into(SysControlLog).columns(
        column.pkId,
        column.ip,
        column.mac,
        column.version,
        column.sysType,
        column.updateTime,
        column.state
      ).values(
        pkId,
        ip,
        mac,
        version,
        sysType,
        updateTime,
        state
      )
    }.update.apply()

    SysControlLog(
      pkId = pkId,
      ip = ip,
      mac = mac,
      version = version,
      sysType = sysType,
      updateTime = updateTime,
      state = state)
  }

  def save(entity: SysControlLog)(implicit session: DBSession = autoSession): SysControlLog = {
    withSQL {
      update(SysControlLog).set(
        column.pkId -> entity.pkId,
        column.ip -> entity.ip,
        column.mac -> entity.mac,
        column.version -> entity.version,
        column.sysType -> entity.sysType,
        column.updateTime -> entity.updateTime,
        column.state -> entity.state
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: SysControlLog)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysControlLog).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
