package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysUserRole(
  pkId: String,
  userId: String,
  roleId: String,
  remark: Option[String] = None,
  createUser: String,
  createDatetime: Option[DateTime] = None,
  updateUser: Option[String] = None,
  updateDatetime: Option[DateTime] = None,
  createUserId: Option[String] = None,
  updateUserId: Option[String] = None,
  departCode: Option[String] = None) {

  def save()(implicit session: DBSession = SysUserRole.autoSession): SysUserRole = SysUserRole.save(this)(session)

  def destroy()(implicit session: DBSession = SysUserRole.autoSession): Unit = SysUserRole.destroy(this)(session)

}


object SysUserRole extends SQLSyntaxSupport[SysUserRole] {

  override val tableName = "SYS_USER_ROLE"

  override val columns = Seq("PK_ID", "USER_ID", "ROLE_ID", "REMARK", "CREATE_USER", "CREATE_DATETIME", "UPDATE_USER", "UPDATE_DATETIME", "CREATE_USER_ID", "UPDATE_USER_ID", "DEPART_CODE")

  def apply(sur: SyntaxProvider[SysUserRole])(rs: WrappedResultSet): SysUserRole = apply(sur.resultName)(rs)
  def apply(sur: ResultName[SysUserRole])(rs: WrappedResultSet): SysUserRole = new SysUserRole(
    pkId = rs.get(sur.pkId),
    userId = rs.get(sur.userId),
    roleId = rs.get(sur.roleId),
    remark = rs.get(sur.remark),
    createUser = rs.get(sur.createUser),
    createDatetime = rs.get(sur.createDatetime),
    updateUser = rs.get(sur.updateUser),
    updateDatetime = rs.get(sur.updateDatetime),
    createUserId = rs.get(sur.createUserId),
    updateUserId = rs.get(sur.updateUserId),
    departCode = rs.get(sur.departCode)
  )

  val sur = SysUserRole.syntax("sur")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[SysUserRole] = {
    withSQL {
      select.from(SysUserRole as sur).where.eq(sur.pkId, pkId)
    }.map(SysUserRole(sur.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysUserRole] = {
    withSQL(select.from(SysUserRole as sur)).map(SysUserRole(sur.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysUserRole as sur)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysUserRole] = {
    withSQL {
      select.from(SysUserRole as sur).where.append(where)
    }.map(SysUserRole(sur.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysUserRole] = {
    withSQL {
      select.from(SysUserRole as sur).where.append(where)
    }.map(SysUserRole(sur.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysUserRole as sur).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    userId: String,
    roleId: String,
    remark: Option[String] = None,
    createUser: String,
    createDatetime: Option[DateTime] = None,
    updateUser: Option[String] = None,
    updateDatetime: Option[DateTime] = None,
    createUserId: Option[String] = None,
    updateUserId: Option[String] = None,
    departCode: Option[String] = None)(implicit session: DBSession = autoSession): SysUserRole = {
    withSQL {
      insert.into(SysUserRole).columns(
        column.pkId,
        column.userId,
        column.roleId,
        column.remark,
        column.createUser,
        column.createDatetime,
        column.updateUser,
        column.updateDatetime,
        column.createUserId,
        column.updateUserId,
        column.departCode
      ).values(
        pkId,
        userId,
        roleId,
        remark,
        createUser,
        createDatetime,
        updateUser,
        updateDatetime,
        createUserId,
        updateUserId,
        departCode
      )
    }.update.apply()

    SysUserRole(
      pkId = pkId,
      userId = userId,
      roleId = roleId,
      remark = remark,
      createUser = createUser,
      createDatetime = createDatetime,
      updateUser = updateUser,
      updateDatetime = updateDatetime,
      createUserId = createUserId,
      updateUserId = updateUserId,
      departCode = departCode)
  }

  def save(entity: SysUserRole)(implicit session: DBSession = autoSession): SysUserRole = {
    withSQL {
      update(SysUserRole).set(
        column.pkId -> entity.pkId,
        column.userId -> entity.userId,
        column.roleId -> entity.roleId,
        column.remark -> entity.remark,
        column.createUser -> entity.createUser,
        column.createDatetime -> entity.createDatetime,
        column.updateUser -> entity.updateUser,
        column.updateDatetime -> entity.updateDatetime,
        column.createUserId -> entity.createUserId,
        column.updateUserId -> entity.updateUserId,
        column.departCode -> entity.departCode
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: SysUserRole)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysUserRole).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
