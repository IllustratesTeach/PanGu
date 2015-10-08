package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysTransfer(
  pkId: String,
  userid: Option[String] = None,
  lastDepartId: Option[String] = None,
  newDepartId: Option[String] = None,
  transferDate: Option[DateTime] = None,
  transferReason: Option[String] = None) {

  def save()(implicit session: DBSession = SysTransfer.autoSession): SysTransfer = SysTransfer.save(this)(session)

  def destroy()(implicit session: DBSession = SysTransfer.autoSession): Unit = SysTransfer.destroy(this)(session)

}


object SysTransfer extends SQLSyntaxSupport[SysTransfer] {

  override val tableName = "SYS_TRANSFER"

  override val columns = Seq("PK_ID", "USERID", "LAST_DEPART_ID", "NEW_DEPART_ID", "TRANSFER_DATE", "TRANSFER_REASON")

  def apply(st: SyntaxProvider[SysTransfer])(rs: WrappedResultSet): SysTransfer = apply(st.resultName)(rs)
  def apply(st: ResultName[SysTransfer])(rs: WrappedResultSet): SysTransfer = new SysTransfer(
    pkId = rs.get(st.pkId),
    userid = rs.get(st.userid),
    lastDepartId = rs.get(st.lastDepartId),
    newDepartId = rs.get(st.newDepartId),
    transferDate = rs.get(st.transferDate),
    transferReason = rs.get(st.transferReason)
  )

  val st = SysTransfer.syntax("st")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, userid: Option[String], lastDepartId: Option[String], newDepartId: Option[String], transferDate: Option[DateTime], transferReason: Option[String])(implicit session: DBSession = autoSession): Option[SysTransfer] = {
    withSQL {
      select.from(SysTransfer as st).where.eq(st.pkId, pkId).and.eq(st.userid, userid).and.eq(st.lastDepartId, lastDepartId).and.eq(st.newDepartId, newDepartId).and.eq(st.transferDate, transferDate).and.eq(st.transferReason, transferReason)
    }.map(SysTransfer(st.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysTransfer] = {
    withSQL(select.from(SysTransfer as st)).map(SysTransfer(st.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysTransfer as st)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysTransfer] = {
    withSQL {
      select.from(SysTransfer as st).where.append(where)
    }.map(SysTransfer(st.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysTransfer] = {
    withSQL {
      select.from(SysTransfer as st).where.append(where)
    }.map(SysTransfer(st.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysTransfer as st).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    userid: Option[String] = None,
    lastDepartId: Option[String] = None,
    newDepartId: Option[String] = None,
    transferDate: Option[DateTime] = None,
    transferReason: Option[String] = None)(implicit session: DBSession = autoSession): SysTransfer = {
    withSQL {
      insert.into(SysTransfer).columns(
        column.pkId,
        column.userid,
        column.lastDepartId,
        column.newDepartId,
        column.transferDate,
        column.transferReason
      ).values(
        pkId,
        userid,
        lastDepartId,
        newDepartId,
        transferDate,
        transferReason
      )
    }.update.apply()

    SysTransfer(
      pkId = pkId,
      userid = userid,
      lastDepartId = lastDepartId,
      newDepartId = newDepartId,
      transferDate = transferDate,
      transferReason = transferReason)
  }

  def save(entity: SysTransfer)(implicit session: DBSession = autoSession): SysTransfer = {
    withSQL {
      update(SysTransfer).set(
        column.pkId -> entity.pkId,
        column.userid -> entity.userid,
        column.lastDepartId -> entity.lastDepartId,
        column.newDepartId -> entity.newDepartId,
        column.transferDate -> entity.transferDate,
        column.transferReason -> entity.transferReason
      ).where.eq(column.pkId, entity.pkId).and.eq(column.userid, entity.userid).and.eq(column.lastDepartId, entity.lastDepartId).and.eq(column.newDepartId, entity.newDepartId).and.eq(column.transferDate, entity.transferDate).and.eq(column.transferReason, entity.transferReason)
    }.update.apply()
    entity
  }

  def destroy(entity: SysTransfer)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysTransfer).where.eq(column.pkId, entity.pkId).and.eq(column.userid, entity.userid).and.eq(column.lastDepartId, entity.lastDepartId).and.eq(column.newDepartId, entity.newDepartId).and.eq(column.transferDate, entity.transferDate).and.eq(column.transferReason, entity.transferReason) }.update.apply()
  }

}
