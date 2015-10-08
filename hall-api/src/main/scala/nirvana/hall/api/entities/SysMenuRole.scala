package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysMenuRole(
  pkId: String,
  roleId: String,
  menuCode: Option[String] = None,
  remark: Option[String] = None,
  createUser: String,
  createDatetime: Option[DateTime] = None,
  updateUser: Option[String] = None,
  updateDatetime: Option[DateTime] = None,
  createUserId: Option[String] = None,
  updateUserId: Option[String] = None,
  onlyRead: Option[Long] = None) {

  def save()(implicit session: DBSession = SysMenuRole.autoSession): SysMenuRole = SysMenuRole.save(this)(session)

  def destroy()(implicit session: DBSession = SysMenuRole.autoSession): Unit = SysMenuRole.destroy(this)(session)

}


object SysMenuRole extends SQLSyntaxSupport[SysMenuRole] {

  override val tableName = "SYS_MENU_ROLE"

  override val columns = Seq("PK_ID", "ROLE_ID", "MENU_CODE", "REMARK", "CREATE_USER", "CREATE_DATETIME", "UPDATE_USER", "UPDATE_DATETIME", "CREATE_USER_ID", "UPDATE_USER_ID", "ONLY_READ")

  def apply(smr: SyntaxProvider[SysMenuRole])(rs: WrappedResultSet): SysMenuRole = apply(smr.resultName)(rs)
  def apply(smr: ResultName[SysMenuRole])(rs: WrappedResultSet): SysMenuRole = new SysMenuRole(
    pkId = rs.get(smr.pkId),
    roleId = rs.get(smr.roleId),
    menuCode = rs.get(smr.menuCode),
    remark = rs.get(smr.remark),
    createUser = rs.get(smr.createUser),
    createDatetime = rs.get(smr.createDatetime),
    updateUser = rs.get(smr.updateUser),
    updateDatetime = rs.get(smr.updateDatetime),
    createUserId = rs.get(smr.createUserId),
    updateUserId = rs.get(smr.updateUserId),
    onlyRead = rs.get(smr.onlyRead)
  )

  val smr = SysMenuRole.syntax("smr")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, roleId: String, menuCode: Option[String], remark: Option[String], createUser: String, createDatetime: Option[DateTime], updateUser: Option[String], updateDatetime: Option[DateTime], createUserId: Option[String], updateUserId: Option[String], onlyRead: Option[Long])(implicit session: DBSession = autoSession): Option[SysMenuRole] = {
    withSQL {
      select.from(SysMenuRole as smr).where.eq(smr.pkId, pkId).and.eq(smr.roleId, roleId).and.eq(smr.menuCode, menuCode).and.eq(smr.remark, remark).and.eq(smr.createUser, createUser).and.eq(smr.createDatetime, createDatetime).and.eq(smr.updateUser, updateUser).and.eq(smr.updateDatetime, updateDatetime).and.eq(smr.createUserId, createUserId).and.eq(smr.updateUserId, updateUserId).and.eq(smr.onlyRead, onlyRead)
    }.map(SysMenuRole(smr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysMenuRole] = {
    withSQL(select.from(SysMenuRole as smr)).map(SysMenuRole(smr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysMenuRole as smr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysMenuRole] = {
    withSQL {
      select.from(SysMenuRole as smr).where.append(where)
    }.map(SysMenuRole(smr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysMenuRole] = {
    withSQL {
      select.from(SysMenuRole as smr).where.append(where)
    }.map(SysMenuRole(smr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysMenuRole as smr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    roleId: String,
    menuCode: Option[String] = None,
    remark: Option[String] = None,
    createUser: String,
    createDatetime: Option[DateTime] = None,
    updateUser: Option[String] = None,
    updateDatetime: Option[DateTime] = None,
    createUserId: Option[String] = None,
    updateUserId: Option[String] = None,
    onlyRead: Option[Long] = None)(implicit session: DBSession = autoSession): SysMenuRole = {
    withSQL {
      insert.into(SysMenuRole).columns(
        column.pkId,
        column.roleId,
        column.menuCode,
        column.remark,
        column.createUser,
        column.createDatetime,
        column.updateUser,
        column.updateDatetime,
        column.createUserId,
        column.updateUserId,
        column.onlyRead
      ).values(
        pkId,
        roleId,
        menuCode,
        remark,
        createUser,
        createDatetime,
        updateUser,
        updateDatetime,
        createUserId,
        updateUserId,
        onlyRead
      )
    }.update.apply()

    SysMenuRole(
      pkId = pkId,
      roleId = roleId,
      menuCode = menuCode,
      remark = remark,
      createUser = createUser,
      createDatetime = createDatetime,
      updateUser = updateUser,
      updateDatetime = updateDatetime,
      createUserId = createUserId,
      updateUserId = updateUserId,
      onlyRead = onlyRead)
  }

  def save(entity: SysMenuRole)(implicit session: DBSession = autoSession): SysMenuRole = {
    withSQL {
      update(SysMenuRole).set(
        column.pkId -> entity.pkId,
        column.roleId -> entity.roleId,
        column.menuCode -> entity.menuCode,
        column.remark -> entity.remark,
        column.createUser -> entity.createUser,
        column.createDatetime -> entity.createDatetime,
        column.updateUser -> entity.updateUser,
        column.updateDatetime -> entity.updateDatetime,
        column.createUserId -> entity.createUserId,
        column.updateUserId -> entity.updateUserId,
        column.onlyRead -> entity.onlyRead
      ).where.eq(column.pkId, entity.pkId).and.eq(column.roleId, entity.roleId).and.eq(column.menuCode, entity.menuCode).and.eq(column.remark, entity.remark).and.eq(column.createUser, entity.createUser).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.updateUser, entity.updateUser).and.eq(column.updateDatetime, entity.updateDatetime).and.eq(column.createUserId, entity.createUserId).and.eq(column.updateUserId, entity.updateUserId).and.eq(column.onlyRead, entity.onlyRead)
    }.update.apply()
    entity
  }

  def destroy(entity: SysMenuRole)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysMenuRole).where.eq(column.pkId, entity.pkId).and.eq(column.roleId, entity.roleId).and.eq(column.menuCode, entity.menuCode).and.eq(column.remark, entity.remark).and.eq(column.createUser, entity.createUser).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.updateUser, entity.updateUser).and.eq(column.updateDatetime, entity.updateDatetime).and.eq(column.createUserId, entity.createUserId).and.eq(column.updateUserId, entity.updateUserId).and.eq(column.onlyRead, entity.onlyRead) }.update.apply()
  }

}
