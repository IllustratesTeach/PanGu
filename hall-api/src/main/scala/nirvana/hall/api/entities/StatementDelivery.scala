package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class StatementDelivery(
  deliveryNoteDetailId: Int,
  statementId: Int) {

  def save()(implicit session: DBSession = StatementDelivery.autoSession): StatementDelivery = StatementDelivery.save(this)(session)

  def destroy()(implicit session: DBSession = StatementDelivery.autoSession): Unit = StatementDelivery.destroy(this)(session)

}


object StatementDelivery extends SQLSyntaxSupport[StatementDelivery] {

  override val tableName = "STATEMENT_DELIVERY"

  override val columns = Seq("DELIVERY_NOTE_DETAIL_ID", "STATEMENT_ID")

  def apply(sd: SyntaxProvider[StatementDelivery])(rs: WrappedResultSet): StatementDelivery = apply(sd.resultName)(rs)
  def apply(sd: ResultName[StatementDelivery])(rs: WrappedResultSet): StatementDelivery = new StatementDelivery(
    deliveryNoteDetailId = rs.get(sd.deliveryNoteDetailId),
    statementId = rs.get(sd.statementId)
  )

  val sd = StatementDelivery.syntax("sd")

 override def autoSession = AutoSpringDataSourceSession()

  def find(deliveryNoteDetailId: Int, statementId: Int)(implicit session: DBSession = autoSession): Option[StatementDelivery] = {
    withSQL {
      select.from(StatementDelivery as sd).where.eq(sd.deliveryNoteDetailId, deliveryNoteDetailId).and.eq(sd.statementId, statementId)
    }.map(StatementDelivery(sd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[StatementDelivery] = {
    withSQL(select.from(StatementDelivery as sd)).map(StatementDelivery(sd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(StatementDelivery as sd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[StatementDelivery] = {
    withSQL {
      select.from(StatementDelivery as sd).where.append(where)
    }.map(StatementDelivery(sd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[StatementDelivery] = {
    withSQL {
      select.from(StatementDelivery as sd).where.append(where)
    }.map(StatementDelivery(sd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(StatementDelivery as sd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    deliveryNoteDetailId: Int,
    statementId: Int)(implicit session: DBSession = autoSession): StatementDelivery = {
    withSQL {
      insert.into(StatementDelivery).columns(
        column.deliveryNoteDetailId,
        column.statementId
      ).values(
        deliveryNoteDetailId,
        statementId
      )
    }.update.apply()

    StatementDelivery(
      deliveryNoteDetailId = deliveryNoteDetailId,
      statementId = statementId)
  }

  def save(entity: StatementDelivery)(implicit session: DBSession = autoSession): StatementDelivery = {
    withSQL {
      update(StatementDelivery).set(
        column.deliveryNoteDetailId -> entity.deliveryNoteDetailId,
        column.statementId -> entity.statementId
      ).where.eq(column.deliveryNoteDetailId, entity.deliveryNoteDetailId).and.eq(column.statementId, entity.statementId)
    }.update.apply()
    entity
  }

  def destroy(entity: StatementDelivery)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(StatementDelivery).where.eq(column.deliveryNoteDetailId, entity.deliveryNoteDetailId).and.eq(column.statementId, entity.statementId) }.update.apply()
  }

}
