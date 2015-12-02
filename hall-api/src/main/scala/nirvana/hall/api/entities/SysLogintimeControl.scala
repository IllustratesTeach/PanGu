package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysLogintimeControl(
  pkId: String,
  controlName: Option[String] = None,
  controlRule: Option[String] = None,
  startTime: Option[String] = None,
  endTime: Option[String] = None,
  createUser: Option[String] = None,
  createTime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = SysLogintimeControl.autoSession): SysLogintimeControl = SysLogintimeControl.save(this)(session)

  def destroy()(implicit session: DBSession = SysLogintimeControl.autoSession): Unit = SysLogintimeControl.destroy(this)(session)

}


object SysLogintimeControl extends SQLSyntaxSupport[SysLogintimeControl] {

  override val tableName = "SYS_LOGINTIME_CONTROL"

  override val columns = Seq("PK_ID", "CONTROL_NAME", "CONTROL_RULE", "START_TIME", "END_TIME", "CREATE_USER", "CREATE_TIME")

  def apply(slc: SyntaxProvider[SysLogintimeControl])(rs: WrappedResultSet): SysLogintimeControl = apply(slc.resultName)(rs)
  def apply(slc: ResultName[SysLogintimeControl])(rs: WrappedResultSet): SysLogintimeControl = new SysLogintimeControl(
    pkId = rs.get(slc.pkId),
    controlName = rs.get(slc.controlName),
    controlRule = rs.get(slc.controlRule),
    startTime = rs.get(slc.startTime),
    endTime = rs.get(slc.endTime),
    createUser = rs.get(slc.createUser),
    createTime = rs.get(slc.createTime)
  )

  val slc = SysLogintimeControl.syntax("slc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[SysLogintimeControl] = {
    withSQL {
      select.from(SysLogintimeControl as slc).where.eq(slc.pkId, pkId)
    }.map(SysLogintimeControl(slc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysLogintimeControl] = {
    withSQL(select.from(SysLogintimeControl as slc)).map(SysLogintimeControl(slc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysLogintimeControl as slc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysLogintimeControl] = {
    withSQL {
      select.from(SysLogintimeControl as slc).where.append(where)
    }.map(SysLogintimeControl(slc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysLogintimeControl] = {
    withSQL {
      select.from(SysLogintimeControl as slc).where.append(where)
    }.map(SysLogintimeControl(slc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysLogintimeControl as slc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    controlName: Option[String] = None,
    controlRule: Option[String] = None,
    startTime: Option[String] = None,
    endTime: Option[String] = None,
    createUser: Option[String] = None,
    createTime: Option[DateTime] = None)(implicit session: DBSession = autoSession): SysLogintimeControl = {
    withSQL {
      insert.into(SysLogintimeControl).columns(
        column.pkId,
        column.controlName,
        column.controlRule,
        column.startTime,
        column.endTime,
        column.createUser,
        column.createTime
      ).values(
        pkId,
        controlName,
        controlRule,
        startTime,
        endTime,
        createUser,
        createTime
      )
    }.update.apply()

    SysLogintimeControl(
      pkId = pkId,
      controlName = controlName,
      controlRule = controlRule,
      startTime = startTime,
      endTime = endTime,
      createUser = createUser,
      createTime = createTime)
  }

  def save(entity: SysLogintimeControl)(implicit session: DBSession = autoSession): SysLogintimeControl = {
    withSQL {
      update(SysLogintimeControl).set(
        column.pkId -> entity.pkId,
        column.controlName -> entity.controlName,
        column.controlRule -> entity.controlRule,
        column.startTime -> entity.startTime,
        column.endTime -> entity.endTime,
        column.createUser -> entity.createUser,
        column.createTime -> entity.createTime
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: SysLogintimeControl)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysLogintimeControl).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
