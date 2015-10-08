package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class StatementReceiving(
  receivingNoteDetailId: Int,
  statementId: Int) {

  def save()(implicit session: DBSession = StatementReceiving.autoSession): StatementReceiving = StatementReceiving.save(this)(session)

  def destroy()(implicit session: DBSession = StatementReceiving.autoSession): Unit = StatementReceiving.destroy(this)(session)

}


object StatementReceiving extends SQLSyntaxSupport[StatementReceiving] {

  override val tableName = "STATEMENT_RECEIVING"

  override val columns = Seq("RECEIVING_NOTE_DETAIL_ID", "STATEMENT_ID")

  def apply(sr: SyntaxProvider[StatementReceiving])(rs: WrappedResultSet): StatementReceiving = apply(sr.resultName)(rs)
  def apply(sr: ResultName[StatementReceiving])(rs: WrappedResultSet): StatementReceiving = new StatementReceiving(
    receivingNoteDetailId = rs.get(sr.receivingNoteDetailId),
    statementId = rs.get(sr.statementId)
  )

  val sr = StatementReceiving.syntax("sr")

 override def autoSession = AutoSpringDataSourceSession()

  def find(receivingNoteDetailId: Int, statementId: Int)(implicit session: DBSession = autoSession): Option[StatementReceiving] = {
    withSQL {
      select.from(StatementReceiving as sr).where.eq(sr.receivingNoteDetailId, receivingNoteDetailId).and.eq(sr.statementId, statementId)
    }.map(StatementReceiving(sr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[StatementReceiving] = {
    withSQL(select.from(StatementReceiving as sr)).map(StatementReceiving(sr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(StatementReceiving as sr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[StatementReceiving] = {
    withSQL {
      select.from(StatementReceiving as sr).where.append(where)
    }.map(StatementReceiving(sr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[StatementReceiving] = {
    withSQL {
      select.from(StatementReceiving as sr).where.append(where)
    }.map(StatementReceiving(sr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(StatementReceiving as sr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    receivingNoteDetailId: Int,
    statementId: Int)(implicit session: DBSession = autoSession): StatementReceiving = {
    withSQL {
      insert.into(StatementReceiving).columns(
        column.receivingNoteDetailId,
        column.statementId
      ).values(
        receivingNoteDetailId,
        statementId
      )
    }.update.apply()

    StatementReceiving(
      receivingNoteDetailId = receivingNoteDetailId,
      statementId = statementId)
  }

  def save(entity: StatementReceiving)(implicit session: DBSession = autoSession): StatementReceiving = {
    withSQL {
      update(StatementReceiving).set(
        column.receivingNoteDetailId -> entity.receivingNoteDetailId,
        column.statementId -> entity.statementId
      ).where.eq(column.receivingNoteDetailId, entity.receivingNoteDetailId).and.eq(column.statementId, entity.statementId)
    }.update.apply()
    entity
  }

  def destroy(entity: StatementReceiving)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(StatementReceiving).where.eq(column.receivingNoteDetailId, entity.receivingNoteDetailId).and.eq(column.statementId, entity.statementId) }.update.apply()
  }

}
