package nirvana.hall.api.entities

import scalikejdbc._

case class SysRoleSign(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  remark: Option[String] = None,
  ord: Option[Long] = None) {

  def save()(implicit session: DBSession = SysRoleSign.autoSession): SysRoleSign = SysRoleSign.save(this)(session)

  def destroy()(implicit session: DBSession = SysRoleSign.autoSession): Unit = SysRoleSign.destroy(this)(session)

}


object SysRoleSign extends SQLSyntaxSupport[SysRoleSign] {

  override val tableName = "SYS_ROLE_SIGN"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "REMARK", "ORD")

  def apply(srs: SyntaxProvider[SysRoleSign])(rs: WrappedResultSet): SysRoleSign = apply(srs.resultName)(rs)
  def apply(srs: ResultName[SysRoleSign])(rs: WrappedResultSet): SysRoleSign = new SysRoleSign(
    code = rs.get(srs.code),
    name = rs.get(srs.name),
    deleteFlag = rs.get(srs.deleteFlag),
    remark = rs.get(srs.remark),
    ord = rs.get(srs.ord)
  )

  val srs = SysRoleSign.syntax("srs")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String)(implicit session: DBSession = autoSession): Option[SysRoleSign] = {
    withSQL {
      select.from(SysRoleSign as srs).where.eq(srs.code, code)
    }.map(SysRoleSign(srs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysRoleSign] = {
    withSQL(select.from(SysRoleSign as srs)).map(SysRoleSign(srs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysRoleSign as srs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysRoleSign] = {
    withSQL {
      select.from(SysRoleSign as srs).where.append(where)
    }.map(SysRoleSign(srs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysRoleSign] = {
    withSQL {
      select.from(SysRoleSign as srs).where.append(where)
    }.map(SysRoleSign(srs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysRoleSign as srs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    remark: Option[String] = None,
    ord: Option[Long] = None)(implicit session: DBSession = autoSession): SysRoleSign = {
    withSQL {
      insert.into(SysRoleSign).columns(
        column.code,
        column.name,
        column.deleteFlag,
        column.remark,
        column.ord
      ).values(
        code,
        name,
        deleteFlag,
        remark,
        ord
      )
    }.update.apply()

    SysRoleSign(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      remark = remark,
      ord = ord)
  }

  def save(entity: SysRoleSign)(implicit session: DBSession = autoSession): SysRoleSign = {
    withSQL {
      update(SysRoleSign).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.remark -> entity.remark,
        column.ord -> entity.ord
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: SysRoleSign)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysRoleSign).where.eq(column.code, entity.code) }.update.apply()
  }

}
