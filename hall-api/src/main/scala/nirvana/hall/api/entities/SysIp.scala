package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysIp(
  pkId: String,
  addreIp: String,
  deleteFlag: Option[Long] = None,
  createUsername: Option[String] = None,
  createUserid: Option[String] = None,
  createDatetime: Option[DateTime] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = SysIp.autoSession): SysIp = SysIp.save(this)(session)

  def destroy()(implicit session: DBSession = SysIp.autoSession): Unit = SysIp.destroy(this)(session)

}


object SysIp extends SQLSyntaxSupport[SysIp] {

  override val tableName = "SYS_IP"

  override val columns = Seq("PK_ID", "ADDRE_IP", "DELETE_FLAG", "CREATE_USERNAME", "CREATE_USERID", "CREATE_DATETIME", "REMARK")

  def apply(si: SyntaxProvider[SysIp])(rs: WrappedResultSet): SysIp = apply(si.resultName)(rs)
  def apply(si: ResultName[SysIp])(rs: WrappedResultSet): SysIp = new SysIp(
    pkId = rs.get(si.pkId),
    addreIp = rs.get(si.addreIp),
    deleteFlag = rs.get(si.deleteFlag),
    createUsername = rs.get(si.createUsername),
    createUserid = rs.get(si.createUserid),
    createDatetime = rs.get(si.createDatetime),
    remark = rs.get(si.remark)
  )

  val si = SysIp.syntax("si")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[SysIp] = {
    withSQL {
      select.from(SysIp as si).where.eq(si.pkId, pkId)
    }.map(SysIp(si.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysIp] = {
    withSQL(select.from(SysIp as si)).map(SysIp(si.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysIp as si)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysIp] = {
    withSQL {
      select.from(SysIp as si).where.append(where)
    }.map(SysIp(si.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysIp] = {
    withSQL {
      select.from(SysIp as si).where.append(where)
    }.map(SysIp(si.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysIp as si).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    addreIp: String,
    deleteFlag: Option[Long] = None,
    createUsername: Option[String] = None,
    createUserid: Option[String] = None,
    createDatetime: Option[DateTime] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): SysIp = {
    withSQL {
      insert.into(SysIp).columns(
        column.pkId,
        column.addreIp,
        column.deleteFlag,
        column.createUsername,
        column.createUserid,
        column.createDatetime,
        column.remark
      ).values(
        pkId,
        addreIp,
        deleteFlag,
        createUsername,
        createUserid,
        createDatetime,
        remark
      )
    }.update.apply()

    SysIp(
      pkId = pkId,
      addreIp = addreIp,
      deleteFlag = deleteFlag,
      createUsername = createUsername,
      createUserid = createUserid,
      createDatetime = createDatetime,
      remark = remark)
  }

  def save(entity: SysIp)(implicit session: DBSession = autoSession): SysIp = {
    withSQL {
      update(SysIp).set(
        column.pkId -> entity.pkId,
        column.addreIp -> entity.addreIp,
        column.deleteFlag -> entity.deleteFlag,
        column.createUsername -> entity.createUsername,
        column.createUserid -> entity.createUserid,
        column.createDatetime -> entity.createDatetime,
        column.remark -> entity.remark
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: SysIp)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysIp).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
