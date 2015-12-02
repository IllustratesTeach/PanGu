package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysRole(
  pkId: String,
  roleName: String,
  roleDescription: Option[String] = None,
  deleteFlag: Option[String] = None,
  createDatetime: DateTime,
  updateDatetime: Option[DateTime] = None,
  createUserId: Option[String] = None,
  updateUserId: Option[String] = None,
  roleLevel: Option[String] = None,
  sysCode: Option[String] = None,
  roleSign: Option[String] = None) {

  def save()(implicit session: DBSession = SysRole.autoSession): SysRole = SysRole.save(this)(session)

  def destroy()(implicit session: DBSession = SysRole.autoSession): Unit = SysRole.destroy(this)(session)

}


object SysRole extends SQLSyntaxSupport[SysRole] {

  override val tableName = "SYS_ROLE"

  override val columns = Seq("PK_ID", "ROLE_NAME", "ROLE_DESCRIPTION", "DELETE_FLAG", "CREATE_DATETIME", "UPDATE_DATETIME", "CREATE_USER_ID", "UPDATE_USER_ID", "ROLE_LEVEL", "SYS_CODE", "ROLE_SIGN")

  def apply(sr: SyntaxProvider[SysRole])(rs: WrappedResultSet): SysRole = apply(sr.resultName)(rs)
  def apply(sr: ResultName[SysRole])(rs: WrappedResultSet): SysRole = new SysRole(
    pkId = rs.get(sr.pkId),
    roleName = rs.get(sr.roleName),
    roleDescription = rs.get(sr.roleDescription),
    deleteFlag = rs.get(sr.deleteFlag),
    createDatetime = rs.get(sr.createDatetime),
    updateDatetime = rs.get(sr.updateDatetime),
    createUserId = rs.get(sr.createUserId),
    updateUserId = rs.get(sr.updateUserId),
    roleLevel = rs.get(sr.roleLevel),
    sysCode = rs.get(sr.sysCode),
    roleSign = rs.get(sr.roleSign)
  )

  val sr = SysRole.syntax("sr")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[SysRole] = {
    withSQL {
      select.from(SysRole as sr).where.eq(sr.pkId, pkId)
    }.map(SysRole(sr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysRole] = {
    withSQL(select.from(SysRole as sr)).map(SysRole(sr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysRole as sr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysRole] = {
    withSQL {
      select.from(SysRole as sr).where.append(where)
    }.map(SysRole(sr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysRole] = {
    withSQL {
      select.from(SysRole as sr).where.append(where)
    }.map(SysRole(sr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysRole as sr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    roleName: String,
    roleDescription: Option[String] = None,
    deleteFlag: Option[String] = None,
    createDatetime: DateTime,
    updateDatetime: Option[DateTime] = None,
    createUserId: Option[String] = None,
    updateUserId: Option[String] = None,
    roleLevel: Option[String] = None,
    sysCode: Option[String] = None,
    roleSign: Option[String] = None)(implicit session: DBSession = autoSession): SysRole = {
    withSQL {
      insert.into(SysRole).columns(
        column.pkId,
        column.roleName,
        column.roleDescription,
        column.deleteFlag,
        column.createDatetime,
        column.updateDatetime,
        column.createUserId,
        column.updateUserId,
        column.roleLevel,
        column.sysCode,
        column.roleSign
      ).values(
        pkId,
        roleName,
        roleDescription,
        deleteFlag,
        createDatetime,
        updateDatetime,
        createUserId,
        updateUserId,
        roleLevel,
        sysCode,
        roleSign
      )
    }.update.apply()

    SysRole(
      pkId = pkId,
      roleName = roleName,
      roleDescription = roleDescription,
      deleteFlag = deleteFlag,
      createDatetime = createDatetime,
      updateDatetime = updateDatetime,
      createUserId = createUserId,
      updateUserId = updateUserId,
      roleLevel = roleLevel,
      sysCode = sysCode,
      roleSign = roleSign)
  }

  def save(entity: SysRole)(implicit session: DBSession = autoSession): SysRole = {
    withSQL {
      update(SysRole).set(
        column.pkId -> entity.pkId,
        column.roleName -> entity.roleName,
        column.roleDescription -> entity.roleDescription,
        column.deleteFlag -> entity.deleteFlag,
        column.createDatetime -> entity.createDatetime,
        column.updateDatetime -> entity.updateDatetime,
        column.createUserId -> entity.createUserId,
        column.updateUserId -> entity.updateUserId,
        column.roleLevel -> entity.roleLevel,
        column.sysCode -> entity.sysCode,
        column.roleSign -> entity.roleSign
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: SysRole)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysRole).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
