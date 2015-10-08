package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class DeliveryNoteDetailHistory(
  id: Int,
  deliveryNoteDetailId: Int,
  quantityBefore: Int,
  quantityAfter: Int,
  amountBefore: Int,
  amountAfter: Int,
  time: Option[Int] = None) {

  def save()(implicit session: DBSession = DeliveryNoteDetailHistory.autoSession): DeliveryNoteDetailHistory = DeliveryNoteDetailHistory.save(this)(session)

  def destroy()(implicit session: DBSession = DeliveryNoteDetailHistory.autoSession): Unit = DeliveryNoteDetailHistory.destroy(this)(session)

}


object DeliveryNoteDetailHistory extends SQLSyntaxSupport[DeliveryNoteDetailHistory] {

  override val tableName = "DELIVERY_NOTE_DETAIL_HISTORY"

  override val columns = Seq("ID", "DELIVERY_NOTE_DETAIL_ID", "QUANTITY_BEFORE", "QUANTITY_AFTER", "AMOUNT_BEFORE", "AMOUNT_AFTER", "TIME")

  def apply(dndh: SyntaxProvider[DeliveryNoteDetailHistory])(rs: WrappedResultSet): DeliveryNoteDetailHistory = apply(dndh.resultName)(rs)
  def apply(dndh: ResultName[DeliveryNoteDetailHistory])(rs: WrappedResultSet): DeliveryNoteDetailHistory = new DeliveryNoteDetailHistory(
    id = rs.get(dndh.id),
    deliveryNoteDetailId = rs.get(dndh.deliveryNoteDetailId),
    quantityBefore = rs.get(dndh.quantityBefore),
    quantityAfter = rs.get(dndh.quantityAfter),
    amountBefore = rs.get(dndh.amountBefore),
    amountAfter = rs.get(dndh.amountAfter),
    time = rs.get(dndh.time)
  )

  val dndh = DeliveryNoteDetailHistory.syntax("dndh")

 override def autoSession = AutoSpringDataSourceSession()

  def find(id: Int)(implicit session: DBSession = autoSession): Option[DeliveryNoteDetailHistory] = {
    withSQL {
      select.from(DeliveryNoteDetailHistory as dndh).where.eq(dndh.id, id)
    }.map(DeliveryNoteDetailHistory(dndh.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DeliveryNoteDetailHistory] = {
    withSQL(select.from(DeliveryNoteDetailHistory as dndh)).map(DeliveryNoteDetailHistory(dndh.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DeliveryNoteDetailHistory as dndh)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DeliveryNoteDetailHistory] = {
    withSQL {
      select.from(DeliveryNoteDetailHistory as dndh).where.append(where)
    }.map(DeliveryNoteDetailHistory(dndh.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DeliveryNoteDetailHistory] = {
    withSQL {
      select.from(DeliveryNoteDetailHistory as dndh).where.append(where)
    }.map(DeliveryNoteDetailHistory(dndh.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DeliveryNoteDetailHistory as dndh).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    deliveryNoteDetailId: Int,
    quantityBefore: Int,
    quantityAfter: Int,
    amountBefore: Int,
    amountAfter: Int,
    time: Option[Int] = None)(implicit session: DBSession = autoSession): DeliveryNoteDetailHistory = {
    val generatedKey = withSQL {
      insert.into(DeliveryNoteDetailHistory).columns(
        column.deliveryNoteDetailId,
        column.quantityBefore,
        column.quantityAfter,
        column.amountBefore,
        column.amountAfter,
        column.time
      ).values(
        deliveryNoteDetailId,
        quantityBefore,
        quantityAfter,
        amountBefore,
        amountAfter,
        time
      )
    }.updateAndReturnGeneratedKey.apply()

    DeliveryNoteDetailHistory(
      id = generatedKey.toInt,
      deliveryNoteDetailId = deliveryNoteDetailId,
      quantityBefore = quantityBefore,
      quantityAfter = quantityAfter,
      amountBefore = amountBefore,
      amountAfter = amountAfter,
      time = time)
  }

  def save(entity: DeliveryNoteDetailHistory)(implicit session: DBSession = autoSession): DeliveryNoteDetailHistory = {
    withSQL {
      update(DeliveryNoteDetailHistory).set(
        column.id -> entity.id,
        column.deliveryNoteDetailId -> entity.deliveryNoteDetailId,
        column.quantityBefore -> entity.quantityBefore,
        column.quantityAfter -> entity.quantityAfter,
        column.amountBefore -> entity.amountBefore,
        column.amountAfter -> entity.amountAfter,
        column.time -> entity.time
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: DeliveryNoteDetailHistory)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(DeliveryNoteDetailHistory).where.eq(column.id, entity.id) }.update.apply()
  }

}
