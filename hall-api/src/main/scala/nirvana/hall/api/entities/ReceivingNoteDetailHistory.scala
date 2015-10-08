package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class ReceivingNoteDetailHistory(
  id: Int,
  receivingNoteDetailId: Int,
  quantityBefore: Int,
  quantityAfter: Int,
  amountBefore: Int,
  amountAfter: Int,
  time: Option[Int] = None) {

  def save()(implicit session: DBSession = ReceivingNoteDetailHistory.autoSession): ReceivingNoteDetailHistory = ReceivingNoteDetailHistory.save(this)(session)

  def destroy()(implicit session: DBSession = ReceivingNoteDetailHistory.autoSession): Unit = ReceivingNoteDetailHistory.destroy(this)(session)

}


object ReceivingNoteDetailHistory extends SQLSyntaxSupport[ReceivingNoteDetailHistory] {

  override val tableName = "RECEIVING_NOTE_DETAIL_HISTORY"

  override val columns = Seq("ID", "RECEIVING_NOTE_DETAIL_ID", "QUANTITY_BEFORE", "QUANTITY_AFTER", "AMOUNT_BEFORE", "AMOUNT_AFTER", "TIME")

  def apply(rndh: SyntaxProvider[ReceivingNoteDetailHistory])(rs: WrappedResultSet): ReceivingNoteDetailHistory = apply(rndh.resultName)(rs)
  def apply(rndh: ResultName[ReceivingNoteDetailHistory])(rs: WrappedResultSet): ReceivingNoteDetailHistory = new ReceivingNoteDetailHistory(
    id = rs.get(rndh.id),
    receivingNoteDetailId = rs.get(rndh.receivingNoteDetailId),
    quantityBefore = rs.get(rndh.quantityBefore),
    quantityAfter = rs.get(rndh.quantityAfter),
    amountBefore = rs.get(rndh.amountBefore),
    amountAfter = rs.get(rndh.amountAfter),
    time = rs.get(rndh.time)
  )

  val rndh = ReceivingNoteDetailHistory.syntax("rndh")

 override def autoSession = AutoSpringDataSourceSession()

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ReceivingNoteDetailHistory] = {
    withSQL {
      select.from(ReceivingNoteDetailHistory as rndh).where.eq(rndh.id, id)
    }.map(ReceivingNoteDetailHistory(rndh.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ReceivingNoteDetailHistory] = {
    withSQL(select.from(ReceivingNoteDetailHistory as rndh)).map(ReceivingNoteDetailHistory(rndh.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ReceivingNoteDetailHistory as rndh)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ReceivingNoteDetailHistory] = {
    withSQL {
      select.from(ReceivingNoteDetailHistory as rndh).where.append(where)
    }.map(ReceivingNoteDetailHistory(rndh.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ReceivingNoteDetailHistory] = {
    withSQL {
      select.from(ReceivingNoteDetailHistory as rndh).where.append(where)
    }.map(ReceivingNoteDetailHistory(rndh.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ReceivingNoteDetailHistory as rndh).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    receivingNoteDetailId: Int,
    quantityBefore: Int,
    quantityAfter: Int,
    amountBefore: Int,
    amountAfter: Int,
    time: Option[Int] = None)(implicit session: DBSession = autoSession): ReceivingNoteDetailHistory = {
    val generatedKey = withSQL {
      insert.into(ReceivingNoteDetailHistory).columns(
        column.receivingNoteDetailId,
        column.quantityBefore,
        column.quantityAfter,
        column.amountBefore,
        column.amountAfter,
        column.time
      ).values(
        receivingNoteDetailId,
        quantityBefore,
        quantityAfter,
        amountBefore,
        amountAfter,
        time
      )
    }.updateAndReturnGeneratedKey.apply()

    ReceivingNoteDetailHistory(
      id = generatedKey.toInt,
      receivingNoteDetailId = receivingNoteDetailId,
      quantityBefore = quantityBefore,
      quantityAfter = quantityAfter,
      amountBefore = amountBefore,
      amountAfter = amountAfter,
      time = time)
  }

  def save(entity: ReceivingNoteDetailHistory)(implicit session: DBSession = autoSession): ReceivingNoteDetailHistory = {
    withSQL {
      update(ReceivingNoteDetailHistory).set(
        column.id -> entity.id,
        column.receivingNoteDetailId -> entity.receivingNoteDetailId,
        column.quantityBefore -> entity.quantityBefore,
        column.quantityAfter -> entity.quantityAfter,
        column.amountBefore -> entity.amountBefore,
        column.amountAfter -> entity.amountAfter,
        column.time -> entity.time
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ReceivingNoteDetailHistory)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(ReceivingNoteDetailHistory).where.eq(column.id, entity.id) }.update.apply()
  }

}
