package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class DeliveryNote(
  id: Int,
  userId: Int,
  createdTime: Option[Int] = None,
  updatedTime: Option[Int] = None,
  partnerId: Int,
  noteType: Int,
  amount: Option[Int] = None,
  time: Option[Int] = None) {

  def save()(implicit session: DBSession = DeliveryNote.autoSession): DeliveryNote = DeliveryNote.save(this)(session)

  def destroy()(implicit session: DBSession = DeliveryNote.autoSession): Unit = DeliveryNote.destroy(this)(session)

}


object DeliveryNote extends SQLSyntaxSupport[DeliveryNote] {

  override val tableName = "DELIVERY_NOTE"

  override val columns = Seq("ID", "USER_ID", "CREATED_TIME", "UPDATED_TIME", "PARTNER_ID", "NOTE_TYPE", "AMOUNT", "TIME")

  def apply(dn: SyntaxProvider[DeliveryNote])(rs: WrappedResultSet): DeliveryNote = apply(dn.resultName)(rs)
  def apply(dn: ResultName[DeliveryNote])(rs: WrappedResultSet): DeliveryNote = new DeliveryNote(
    id = rs.get(dn.id),
    userId = rs.get(dn.userId),
    createdTime = rs.get(dn.createdTime),
    updatedTime = rs.get(dn.updatedTime),
    partnerId = rs.get(dn.partnerId),
    noteType = rs.get(dn.noteType),
    amount = rs.get(dn.amount),
    time = rs.get(dn.time)
  )

  val dn = DeliveryNote.syntax("dn")

 override def autoSession = AutoSpringDataSourceSession()

  def find(id: Int)(implicit session: DBSession = autoSession): Option[DeliveryNote] = {
    withSQL {
      select.from(DeliveryNote as dn).where.eq(dn.id, id)
    }.map(DeliveryNote(dn.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DeliveryNote] = {
    withSQL(select.from(DeliveryNote as dn)).map(DeliveryNote(dn.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DeliveryNote as dn)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DeliveryNote] = {
    withSQL {
      select.from(DeliveryNote as dn).where.append(where)
    }.map(DeliveryNote(dn.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DeliveryNote] = {
    withSQL {
      select.from(DeliveryNote as dn).where.append(where)
    }.map(DeliveryNote(dn.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DeliveryNote as dn).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userId: Int,
    createdTime: Option[Int] = None,
    updatedTime: Option[Int] = None,
    partnerId: Int,
    noteType: Int,
    amount: Option[Int] = None,
    time: Option[Int] = None)(implicit session: DBSession = autoSession): DeliveryNote = {
    val generatedKey = withSQL {
      insert.into(DeliveryNote).columns(
        column.userId,
        column.createdTime,
        column.updatedTime,
        column.partnerId,
        column.noteType,
        column.amount,
        column.time
      ).values(
        userId,
        createdTime,
        updatedTime,
        partnerId,
        noteType,
        amount,
        time
      )
    }.updateAndReturnGeneratedKey.apply()

    DeliveryNote(
      id = generatedKey.toInt,
      userId = userId,
      createdTime = createdTime,
      updatedTime = updatedTime,
      partnerId = partnerId,
      noteType = noteType,
      amount = amount,
      time = time)
  }

  def save(entity: DeliveryNote)(implicit session: DBSession = autoSession): DeliveryNote = {
    withSQL {
      update(DeliveryNote).set(
        column.id -> entity.id,
        column.userId -> entity.userId,
        column.createdTime -> entity.createdTime,
        column.updatedTime -> entity.updatedTime,
        column.partnerId -> entity.partnerId,
        column.noteType -> entity.noteType,
        column.amount -> entity.amount,
        column.time -> entity.time
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: DeliveryNote)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(DeliveryNote).where.eq(column.id, entity.id) }.update.apply()
  }

}
