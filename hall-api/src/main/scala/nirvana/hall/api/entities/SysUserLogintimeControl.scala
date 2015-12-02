package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysUserLogintimeControl(
  pkId: String,
  userId: Option[String] = None,
  controlId: Option[String] = None,
  createUser: Option[String] = None,
  createTime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = SysUserLogintimeControl.autoSession): SysUserLogintimeControl = SysUserLogintimeControl.save(this)(session)

  def destroy()(implicit session: DBSession = SysUserLogintimeControl.autoSession): Unit = SysUserLogintimeControl.destroy(this)(session)

}


object SysUserLogintimeControl extends SQLSyntaxSupport[SysUserLogintimeControl] {

  override val tableName = "SYS_USER_LOGINTIME_CONTROL"

  override val columns = Seq("PK_ID", "USER_ID", "CONTROL_ID", "CREATE_USER", "CREATE_TIME")

  def apply(sulc: SyntaxProvider[SysUserLogintimeControl])(rs: WrappedResultSet): SysUserLogintimeControl = apply(sulc.resultName)(rs)
  def apply(sulc: ResultName[SysUserLogintimeControl])(rs: WrappedResultSet): SysUserLogintimeControl = new SysUserLogintimeControl(
    pkId = rs.get(sulc.pkId),
    userId = rs.get(sulc.userId),
    controlId = rs.get(sulc.controlId),
    createUser = rs.get(sulc.createUser),
    createTime = rs.get(sulc.createTime)
  )

  val sulc = SysUserLogintimeControl.syntax("sulc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[SysUserLogintimeControl] = {
    withSQL {
      select.from(SysUserLogintimeControl as sulc).where.eq(sulc.pkId, pkId)
    }.map(SysUserLogintimeControl(sulc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysUserLogintimeControl] = {
    withSQL(select.from(SysUserLogintimeControl as sulc)).map(SysUserLogintimeControl(sulc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysUserLogintimeControl as sulc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysUserLogintimeControl] = {
    withSQL {
      select.from(SysUserLogintimeControl as sulc).where.append(where)
    }.map(SysUserLogintimeControl(sulc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysUserLogintimeControl] = {
    withSQL {
      select.from(SysUserLogintimeControl as sulc).where.append(where)
    }.map(SysUserLogintimeControl(sulc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysUserLogintimeControl as sulc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    userId: Option[String] = None,
    controlId: Option[String] = None,
    createUser: Option[String] = None,
    createTime: Option[DateTime] = None)(implicit session: DBSession = autoSession): SysUserLogintimeControl = {
    withSQL {
      insert.into(SysUserLogintimeControl).columns(
        column.pkId,
        column.userId,
        column.controlId,
        column.createUser,
        column.createTime
      ).values(
        pkId,
        userId,
        controlId,
        createUser,
        createTime
      )
    }.update.apply()

    SysUserLogintimeControl(
      pkId = pkId,
      userId = userId,
      controlId = controlId,
      createUser = createUser,
      createTime = createTime)
  }

  def save(entity: SysUserLogintimeControl)(implicit session: DBSession = autoSession): SysUserLogintimeControl = {
    withSQL {
      update(SysUserLogintimeControl).set(
        column.pkId -> entity.pkId,
        column.userId -> entity.userId,
        column.controlId -> entity.controlId,
        column.createUser -> entity.createUser,
        column.createTime -> entity.createTime
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: SysUserLogintimeControl)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysUserLogintimeControl).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
