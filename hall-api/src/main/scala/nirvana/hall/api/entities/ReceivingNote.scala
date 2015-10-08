package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class ReceivingNote(
  id: Int,
  userId: Int,
  createdTime: Option[Int] = None,
  updatedTime: Option[Int] = None,
  partnerId: Int,
  noteType: Int,
  amount: Option[Int] = None,
  status: Option[Int] = None,
  time: Int,
  voucherUrl: Option[String] = None) {

  def save()(implicit session: DBSession = ReceivingNote.autoSession): ReceivingNote = ReceivingNote.save(this)(session)

  def destroy()(implicit session: DBSession = ReceivingNote.autoSession): Unit = ReceivingNote.destroy(this)(session)

}


object ReceivingNote extends SQLSyntaxSupport[ReceivingNote] {

  override val tableName = "RECEIVING_NOTE"

  override val columns = Seq("ID", "USER_ID", "CREATED_TIME", "UPDATED_TIME", "PARTNER_ID", "NOTE_TYPE", "AMOUNT", "STATUS", "TIME", "VOUCHER_URL")

  def apply(rn: SyntaxProvider[ReceivingNote])(rs: WrappedResultSet): ReceivingNote = apply(rn.resultName)(rs)
  def apply(rn: ResultName[ReceivingNote])(rs: WrappedResultSet): ReceivingNote = new ReceivingNote(
    id = rs.get(rn.id),
    userId = rs.get(rn.userId),
    createdTime = rs.get(rn.createdTime),
    updatedTime = rs.get(rn.updatedTime),
    partnerId = rs.get(rn.partnerId),
    noteType = rs.get(rn.noteType),
    amount = rs.get(rn.amount),
    status = rs.get(rn.status),
    time = rs.get(rn.time),
    voucherUrl = rs.get(rn.voucherUrl)
  )

  val rn = ReceivingNote.syntax("rn")

 override def autoSession = AutoSpringDataSourceSession()

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ReceivingNote] = {
    withSQL {
      select.from(ReceivingNote as rn).where.eq(rn.id, id)
    }.map(ReceivingNote(rn.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ReceivingNote] = {
    withSQL(select.from(ReceivingNote as rn)).map(ReceivingNote(rn.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ReceivingNote as rn)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ReceivingNote] = {
    withSQL {
      select.from(ReceivingNote as rn).where.append(where)
    }.map(ReceivingNote(rn.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ReceivingNote] = {
    withSQL {
      select.from(ReceivingNote as rn).where.append(where)
    }.map(ReceivingNote(rn.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ReceivingNote as rn).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userId: Int,
    createdTime: Option[Int] = None,
    updatedTime: Option[Int] = None,
    partnerId: Int,
    noteType: Int,
    amount: Option[Int] = None,
    status: Option[Int] = None,
    time: Int,
    voucherUrl: Option[String] = None)(implicit session: DBSession = autoSession): ReceivingNote = {
    val generatedKey = withSQL {
      insert.into(ReceivingNote).columns(
        column.userId,
        column.createdTime,
        column.updatedTime,
        column.partnerId,
        column.noteType,
        column.amount,
        column.status,
        column.time,
        column.voucherUrl
      ).values(
        userId,
        createdTime,
        updatedTime,
        partnerId,
        noteType,
        amount,
        status,
        time,
        voucherUrl
      )
    }.updateAndReturnGeneratedKey.apply()

    ReceivingNote(
      id = generatedKey.toInt,
      userId = userId,
      createdTime = createdTime,
      updatedTime = updatedTime,
      partnerId = partnerId,
      noteType = noteType,
      amount = amount,
      status = status,
      time = time,
      voucherUrl = voucherUrl)
  }

  def save(entity: ReceivingNote)(implicit session: DBSession = autoSession): ReceivingNote = {
    withSQL {
      update(ReceivingNote).set(
        column.id -> entity.id,
        column.userId -> entity.userId,
        column.createdTime -> entity.createdTime,
        column.updatedTime -> entity.updatedTime,
        column.partnerId -> entity.partnerId,
        column.noteType -> entity.noteType,
        column.amount -> entity.amount,
        column.status -> entity.status,
        column.time -> entity.time,
        column.voucherUrl -> entity.voucherUrl
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ReceivingNote)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(ReceivingNote).where.eq(column.id, entity.id) }.update.apply()
  }

}
