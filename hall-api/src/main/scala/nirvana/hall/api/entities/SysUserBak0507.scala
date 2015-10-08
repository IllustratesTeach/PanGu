package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysUserBak0507(
  pkId: String,
  loginName: String,
  password: Option[String] = None,
  email: Option[String] = None,
  trueName: Option[String] = None,
  deleteFlag: Option[String] = None,
  remark: Option[String] = None,
  createDatetime: Option[DateTime] = None,
  updateDatetime: Option[DateTime] = None,
  createUserId: Option[String] = None,
  updateUserId: Option[String] = None,
  departCode: Option[String] = None,
  idcard: Option[String] = None,
  policeNumber: Option[String] = None,
  genderCode: Option[String] = None,
  userType: Option[String] = None,
  phone: Option[String] = None,
  departStartDate: Option[DateTime] = None,
  userStatus: Option[String] = None,
  lastLoginDate: Option[DateTime] = None) {

  def save()(implicit session: DBSession = SysUserBak0507.autoSession): SysUserBak0507 = SysUserBak0507.save(this)(session)

  def destroy()(implicit session: DBSession = SysUserBak0507.autoSession): Unit = SysUserBak0507.destroy(this)(session)

}


object SysUserBak0507 extends SQLSyntaxSupport[SysUserBak0507] {

  override val tableName = "SYS_USER_BAK0507"

  override val columns = Seq("PK_ID", "LOGIN_NAME", "PASSWORD", "EMAIL", "TRUE_NAME", "DELETE_FLAG", "REMARK", "CREATE_DATETIME", "UPDATE_DATETIME", "CREATE_USER_ID", "UPDATE_USER_ID", "DEPART_CODE", "IDCARD", "POLICE_NUMBER", "GENDER_CODE", "USER_TYPE", "PHONE", "DEPART_START_DATE", "USER_STATUS", "LAST_LOGIN_DATE")

  def apply(sub: SyntaxProvider[SysUserBak0507])(rs: WrappedResultSet): SysUserBak0507 = apply(sub.resultName)(rs)
  def apply(sub: ResultName[SysUserBak0507])(rs: WrappedResultSet): SysUserBak0507 = new SysUserBak0507(
    pkId = rs.get(sub.pkId),
    loginName = rs.get(sub.loginName),
    password = rs.get(sub.password),
    email = rs.get(sub.email),
    trueName = rs.get(sub.trueName),
    deleteFlag = rs.get(sub.deleteFlag),
    remark = rs.get(sub.remark),
    createDatetime = rs.get(sub.createDatetime),
    updateDatetime = rs.get(sub.updateDatetime),
    createUserId = rs.get(sub.createUserId),
    updateUserId = rs.get(sub.updateUserId),
    departCode = rs.get(sub.departCode),
    idcard = rs.get(sub.idcard),
    policeNumber = rs.get(sub.policeNumber),
    genderCode = rs.get(sub.genderCode),
    userType = rs.get(sub.userType),
    phone = rs.get(sub.phone),
    departStartDate = rs.get(sub.departStartDate),
    userStatus = rs.get(sub.userStatus),
    lastLoginDate = rs.get(sub.lastLoginDate)
  )

  val sub = SysUserBak0507.syntax("sub")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, loginName: String, password: Option[String], email: Option[String], trueName: Option[String], deleteFlag: Option[String], remark: Option[String], createDatetime: Option[DateTime], updateDatetime: Option[DateTime], createUserId: Option[String], updateUserId: Option[String], departCode: Option[String], idcard: Option[String], policeNumber: Option[String], genderCode: Option[String], userType: Option[String], phone: Option[String], departStartDate: Option[DateTime], userStatus: Option[String], lastLoginDate: Option[DateTime])(implicit session: DBSession = autoSession): Option[SysUserBak0507] = {
    withSQL {
      select.from(SysUserBak0507 as sub).where.eq(sub.pkId, pkId).and.eq(sub.loginName, loginName).and.eq(sub.password, password).and.eq(sub.email, email).and.eq(sub.trueName, trueName).and.eq(sub.deleteFlag, deleteFlag).and.eq(sub.remark, remark).and.eq(sub.createDatetime, createDatetime).and.eq(sub.updateDatetime, updateDatetime).and.eq(sub.createUserId, createUserId).and.eq(sub.updateUserId, updateUserId).and.eq(sub.departCode, departCode).and.eq(sub.idcard, idcard).and.eq(sub.policeNumber, policeNumber).and.eq(sub.genderCode, genderCode).and.eq(sub.userType, userType).and.eq(sub.phone, phone).and.eq(sub.departStartDate, departStartDate).and.eq(sub.userStatus, userStatus).and.eq(sub.lastLoginDate, lastLoginDate)
    }.map(SysUserBak0507(sub.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysUserBak0507] = {
    withSQL(select.from(SysUserBak0507 as sub)).map(SysUserBak0507(sub.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysUserBak0507 as sub)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysUserBak0507] = {
    withSQL {
      select.from(SysUserBak0507 as sub).where.append(where)
    }.map(SysUserBak0507(sub.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysUserBak0507] = {
    withSQL {
      select.from(SysUserBak0507 as sub).where.append(where)
    }.map(SysUserBak0507(sub.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysUserBak0507 as sub).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    loginName: String,
    password: Option[String] = None,
    email: Option[String] = None,
    trueName: Option[String] = None,
    deleteFlag: Option[String] = None,
    remark: Option[String] = None,
    createDatetime: Option[DateTime] = None,
    updateDatetime: Option[DateTime] = None,
    createUserId: Option[String] = None,
    updateUserId: Option[String] = None,
    departCode: Option[String] = None,
    idcard: Option[String] = None,
    policeNumber: Option[String] = None,
    genderCode: Option[String] = None,
    userType: Option[String] = None,
    phone: Option[String] = None,
    departStartDate: Option[DateTime] = None,
    userStatus: Option[String] = None,
    lastLoginDate: Option[DateTime] = None)(implicit session: DBSession = autoSession): SysUserBak0507 = {
    withSQL {
      insert.into(SysUserBak0507).columns(
        column.pkId,
        column.loginName,
        column.password,
        column.email,
        column.trueName,
        column.deleteFlag,
        column.remark,
        column.createDatetime,
        column.updateDatetime,
        column.createUserId,
        column.updateUserId,
        column.departCode,
        column.idcard,
        column.policeNumber,
        column.genderCode,
        column.userType,
        column.phone,
        column.departStartDate,
        column.userStatus,
        column.lastLoginDate
      ).values(
        pkId,
        loginName,
        password,
        email,
        trueName,
        deleteFlag,
        remark,
        createDatetime,
        updateDatetime,
        createUserId,
        updateUserId,
        departCode,
        idcard,
        policeNumber,
        genderCode,
        userType,
        phone,
        departStartDate,
        userStatus,
        lastLoginDate
      )
    }.update.apply()

    SysUserBak0507(
      pkId = pkId,
      loginName = loginName,
      password = password,
      email = email,
      trueName = trueName,
      deleteFlag = deleteFlag,
      remark = remark,
      createDatetime = createDatetime,
      updateDatetime = updateDatetime,
      createUserId = createUserId,
      updateUserId = updateUserId,
      departCode = departCode,
      idcard = idcard,
      policeNumber = policeNumber,
      genderCode = genderCode,
      userType = userType,
      phone = phone,
      departStartDate = departStartDate,
      userStatus = userStatus,
      lastLoginDate = lastLoginDate)
  }

  def save(entity: SysUserBak0507)(implicit session: DBSession = autoSession): SysUserBak0507 = {
    withSQL {
      update(SysUserBak0507).set(
        column.pkId -> entity.pkId,
        column.loginName -> entity.loginName,
        column.password -> entity.password,
        column.email -> entity.email,
        column.trueName -> entity.trueName,
        column.deleteFlag -> entity.deleteFlag,
        column.remark -> entity.remark,
        column.createDatetime -> entity.createDatetime,
        column.updateDatetime -> entity.updateDatetime,
        column.createUserId -> entity.createUserId,
        column.updateUserId -> entity.updateUserId,
        column.departCode -> entity.departCode,
        column.idcard -> entity.idcard,
        column.policeNumber -> entity.policeNumber,
        column.genderCode -> entity.genderCode,
        column.userType -> entity.userType,
        column.phone -> entity.phone,
        column.departStartDate -> entity.departStartDate,
        column.userStatus -> entity.userStatus,
        column.lastLoginDate -> entity.lastLoginDate
      ).where.eq(column.pkId, entity.pkId).and.eq(column.loginName, entity.loginName).and.eq(column.password, entity.password).and.eq(column.email, entity.email).and.eq(column.trueName, entity.trueName).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.remark, entity.remark).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.updateDatetime, entity.updateDatetime).and.eq(column.createUserId, entity.createUserId).and.eq(column.updateUserId, entity.updateUserId).and.eq(column.departCode, entity.departCode).and.eq(column.idcard, entity.idcard).and.eq(column.policeNumber, entity.policeNumber).and.eq(column.genderCode, entity.genderCode).and.eq(column.userType, entity.userType).and.eq(column.phone, entity.phone).and.eq(column.departStartDate, entity.departStartDate).and.eq(column.userStatus, entity.userStatus).and.eq(column.lastLoginDate, entity.lastLoginDate)
    }.update.apply()
    entity
  }

  def destroy(entity: SysUserBak0507)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysUserBak0507).where.eq(column.pkId, entity.pkId).and.eq(column.loginName, entity.loginName).and.eq(column.password, entity.password).and.eq(column.email, entity.email).and.eq(column.trueName, entity.trueName).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.remark, entity.remark).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.updateDatetime, entity.updateDatetime).and.eq(column.createUserId, entity.createUserId).and.eq(column.updateUserId, entity.updateUserId).and.eq(column.departCode, entity.departCode).and.eq(column.idcard, entity.idcard).and.eq(column.policeNumber, entity.policeNumber).and.eq(column.genderCode, entity.genderCode).and.eq(column.userType, entity.userType).and.eq(column.phone, entity.phone).and.eq(column.departStartDate, entity.departStartDate).and.eq(column.userStatus, entity.userStatus).and.eq(column.lastLoginDate, entity.lastLoginDate) }.update.apply()
  }

}
