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

  def find(pkId: String, userId: String, roleId: String, remark: Option[String], createUser: String, createDatetime: Option[DateTime], updateUser: Option[String], updateDatetime: Option[DateTime], createUserId: Option[String], updateUserId: Option[String], departCode: Option[String])(implicit session: DBSession = autoSession): Option[SysUserRole] = {
    withSQL {
      select.from(SysUserRole as sur).where.eq(sur.pkId, pkId).and.eq(sur.userId, userId).and.eq(sur.roleId, roleId).and.eq(sur.remark, remark).and.eq(sur.createUser, createUser).and.eq(sur.createDatetime, createDatetime).and.eq(sur.updateUser, updateUser).and.eq(sur.updateDatetime, updateDatetime).and.eq(sur.createUserId, createUserId).and.eq(sur.updateUserId, updateUserId).and.eq(sur.departCode, departCode)
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
      ).where.eq(column.pkId, entity.pkId).and.eq(column.userId, entity.userId).and.eq(column.roleId, entity.roleId).and.eq(column.remark, entity.remark).and.eq(column.createUser, entity.createUser).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.updateUser, entity.updateUser).and.eq(column.updateDatetime, entity.updateDatetime).and.eq(column.createUserId, entity.createUserId).and.eq(column.updateUserId, entity.updateUserId).and.eq(column.departCode, entity.departCode)
    }.update.apply()
    entity
  }

  def destroy(entity: SysUserRole)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysUserRole).where.eq(column.pkId, entity.pkId).and.eq(column.userId, entity.userId).and.eq(column.roleId, entity.roleId).and.eq(column.remark, entity.remark).and.eq(column.createUser, entity.createUser).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.updateUser, entity.updateUser).and.eq(column.updateDatetime, entity.updateDatetime).and.eq(column.createUserId, entity.createUserId).and.eq(column.updateUserId, entity.updateUserId).and.eq(column.departCode, entity.departCode) }.update.apply()
  }

}
