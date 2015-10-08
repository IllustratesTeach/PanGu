package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysRoleFunction(
  pkId: String,
  roleId: Option[String] = None,
  functionId: Option[String] = None,
  createUserId: Option[String] = None,
  createDatetime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = SysRoleFunction.autoSession): SysRoleFunction = SysRoleFunction.save(this)(session)

  def destroy()(implicit session: DBSession = SysRoleFunction.autoSession): Unit = SysRoleFunction.destroy(this)(session)

}


object SysRoleFunction extends SQLSyntaxSupport[SysRoleFunction] {

  override val tableName = "SYS_ROLE_FUNCTION"

  override val columns = Seq("PK_ID", "ROLE_ID", "FUNCTION_ID", "CREATE_USER_ID", "CREATE_DATETIME")

  def apply(srf: SyntaxProvider[SysRoleFunction])(rs: WrappedResultSet): SysRoleFunction = apply(srf.resultName)(rs)
  def apply(srf: ResultName[SysRoleFunction])(rs: WrappedResultSet): SysRoleFunction = new SysRoleFunction(
    pkId = rs.get(srf.pkId),
    roleId = rs.get(srf.roleId),
    functionId = rs.get(srf.functionId),
    createUserId = rs.get(srf.createUserId),
    createDatetime = rs.get(srf.createDatetime)
  )

  val srf = SysRoleFunction.syntax("srf")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, roleId: Option[String], functionId: Option[String], createUserId: Option[String], createDatetime: Option[DateTime])(implicit session: DBSession = autoSession): Option[SysRoleFunction] = {
    withSQL {
      select.from(SysRoleFunction as srf).where.eq(srf.pkId, pkId).and.eq(srf.roleId, roleId).and.eq(srf.functionId, functionId).and.eq(srf.createUserId, createUserId).and.eq(srf.createDatetime, createDatetime)
    }.map(SysRoleFunction(srf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysRoleFunction] = {
    withSQL(select.from(SysRoleFunction as srf)).map(SysRoleFunction(srf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysRoleFunction as srf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysRoleFunction] = {
    withSQL {
      select.from(SysRoleFunction as srf).where.append(where)
    }.map(SysRoleFunction(srf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysRoleFunction] = {
    withSQL {
      select.from(SysRoleFunction as srf).where.append(where)
    }.map(SysRoleFunction(srf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysRoleFunction as srf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    roleId: Option[String] = None,
    functionId: Option[String] = None,
    createUserId: Option[String] = None,
    createDatetime: Option[DateTime] = None)(implicit session: DBSession = autoSession): SysRoleFunction = {
    withSQL {
      insert.into(SysRoleFunction).columns(
        column.pkId,
        column.roleId,
        column.functionId,
        column.createUserId,
        column.createDatetime
      ).values(
        pkId,
        roleId,
        functionId,
        createUserId,
        createDatetime
      )
    }.update.apply()

    SysRoleFunction(
      pkId = pkId,
      roleId = roleId,
      functionId = functionId,
      createUserId = createUserId,
      createDatetime = createDatetime)
  }

  def save(entity: SysRoleFunction)(implicit session: DBSession = autoSession): SysRoleFunction = {
    withSQL {
      update(SysRoleFunction).set(
        column.pkId -> entity.pkId,
        column.roleId -> entity.roleId,
        column.functionId -> entity.functionId,
        column.createUserId -> entity.createUserId,
        column.createDatetime -> entity.createDatetime
      ).where.eq(column.pkId, entity.pkId).and.eq(column.roleId, entity.roleId).and.eq(column.functionId, entity.functionId).and.eq(column.createUserId, entity.createUserId).and.eq(column.createDatetime, entity.createDatetime)
    }.update.apply()
    entity
  }

  def destroy(entity: SysRoleFunction)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysRoleFunction).where.eq(column.pkId, entity.pkId).and.eq(column.roleId, entity.roleId).and.eq(column.functionId, entity.functionId).and.eq(column.createUserId, entity.createUserId).and.eq(column.createDatetime, entity.createDatetime) }.update.apply()
  }

}
