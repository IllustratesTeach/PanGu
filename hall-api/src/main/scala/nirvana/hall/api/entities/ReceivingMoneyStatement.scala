package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class ReceivingMoneyStatement(
  statementId: Int,
  receivingMoneyId: Int,
  amount: Int) {

  def save()(implicit session: DBSession = ReceivingMoneyStatement.autoSession): ReceivingMoneyStatement = ReceivingMoneyStatement.save(this)(session)

  def destroy()(implicit session: DBSession = ReceivingMoneyStatement.autoSession): Unit = ReceivingMoneyStatement.destroy(this)(session)

}


object ReceivingMoneyStatement extends SQLSyntaxSupport[ReceivingMoneyStatement] {

  override val tableName = "RECEIVING_MONEY_STATEMENT"

  override val columns = Seq("STATEMENT_ID", "RECEIVING_MONEY_ID", "AMOUNT")

  def apply(rms: SyntaxProvider[ReceivingMoneyStatement])(rs: WrappedResultSet): ReceivingMoneyStatement = apply(rms.resultName)(rs)
  def apply(rms: ResultName[ReceivingMoneyStatement])(rs: WrappedResultSet): ReceivingMoneyStatement = new ReceivingMoneyStatement(
    statementId = rs.get(rms.statementId),
    receivingMoneyId = rs.get(rms.receivingMoneyId),
    amount = rs.get(rms.amount)
  )

  val rms = ReceivingMoneyStatement.syntax("rms")

 override def autoSession = AutoSpringDataSourceSession()

  def find(statementId: Int, receivingMoneyId: Int, amount: Int)(implicit session: DBSession = autoSession): Option[ReceivingMoneyStatement] = {
    withSQL {
      select.from(ReceivingMoneyStatement as rms).where.eq(rms.statementId, statementId).and.eq(rms.receivingMoneyId, receivingMoneyId).and.eq(rms.amount, amount)
    }.map(ReceivingMoneyStatement(rms.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ReceivingMoneyStatement] = {
    withSQL(select.from(ReceivingMoneyStatement as rms)).map(ReceivingMoneyStatement(rms.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ReceivingMoneyStatement as rms)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ReceivingMoneyStatement] = {
    withSQL {
      select.from(ReceivingMoneyStatement as rms).where.append(where)
    }.map(ReceivingMoneyStatement(rms.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ReceivingMoneyStatement] = {
    withSQL {
      select.from(ReceivingMoneyStatement as rms).where.append(where)
    }.map(ReceivingMoneyStatement(rms.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ReceivingMoneyStatement as rms).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    statementId: Int,
    receivingMoneyId: Int,
    amount: Int)(implicit session: DBSession = autoSession): ReceivingMoneyStatement = {
    withSQL {
      insert.into(ReceivingMoneyStatement).columns(
        column.statementId,
        column.receivingMoneyId,
        column.amount
      ).values(
        statementId,
        receivingMoneyId,
        amount
      )
    }.update.apply()

    ReceivingMoneyStatement(
      statementId = statementId,
      receivingMoneyId = receivingMoneyId,
      amount = amount)
  }

  def save(entity: ReceivingMoneyStatement)(implicit session: DBSession = autoSession): ReceivingMoneyStatement = {
    withSQL {
      update(ReceivingMoneyStatement).set(
        column.statementId -> entity.statementId,
        column.receivingMoneyId -> entity.receivingMoneyId,
        column.amount -> entity.amount
      ).where.eq(column.statementId, entity.statementId).and.eq(column.receivingMoneyId, entity.receivingMoneyId).and.eq(column.amount, entity.amount)
    }.update.apply()
    entity
  }

  def destroy(entity: ReceivingMoneyStatement)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(ReceivingMoneyStatement).where.eq(column.statementId, entity.statementId).and.eq(column.receivingMoneyId, entity.receivingMoneyId).and.eq(column.amount, entity.amount) }.update.apply()
  }

}
