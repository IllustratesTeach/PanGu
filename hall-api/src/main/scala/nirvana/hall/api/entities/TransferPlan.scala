package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class TransferPlan(
  id: Int,
  userId: Int,
  createdTime: Option[Int] = None,
  updatedTime: Option[Int] = None,
  partnerId: Int,
  statementSummaryId: Int,
  transferMethodId: Int,
  amount: Int,
  planTime: Option[Int] = None,
  actualTime: Option[Int] = None,
  transferType: Option[Int] = None) {

  def save()(implicit session: DBSession = TransferPlan.autoSession): TransferPlan = TransferPlan.save(this)(session)

  def destroy()(implicit session: DBSession = TransferPlan.autoSession): Unit = TransferPlan.destroy(this)(session)

}


object TransferPlan extends SQLSyntaxSupport[TransferPlan] {

  override val tableName = "TRANSFER_PLAN"

  override val columns = Seq("ID", "USER_ID", "CREATED_TIME", "UPDATED_TIME", "PARTNER_ID", "STATEMENT_SUMMARY_ID", "TRANSFER_METHOD_ID", "AMOUNT", "PLAN_TIME", "ACTUAL_TIME", "TRANSFER_TYPE")

  def apply(tp: SyntaxProvider[TransferPlan])(rs: WrappedResultSet): TransferPlan = apply(tp.resultName)(rs)
  def apply(tp: ResultName[TransferPlan])(rs: WrappedResultSet): TransferPlan = new TransferPlan(
    id = rs.get(tp.id),
    userId = rs.get(tp.userId),
    createdTime = rs.get(tp.createdTime),
    updatedTime = rs.get(tp.updatedTime),
    partnerId = rs.get(tp.partnerId),
    statementSummaryId = rs.get(tp.statementSummaryId),
    transferMethodId = rs.get(tp.transferMethodId),
    amount = rs.get(tp.amount),
    planTime = rs.get(tp.planTime),
    actualTime = rs.get(tp.actualTime),
    transferType = rs.get(tp.transferType)
  )

  val tp = TransferPlan.syntax("tp")

 override def autoSession = AutoSpringDataSourceSession()

  def find(id: Int)(implicit session: DBSession = autoSession): Option[TransferPlan] = {
    withSQL {
      select.from(TransferPlan as tp).where.eq(tp.id, id)
    }.map(TransferPlan(tp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TransferPlan] = {
    withSQL(select.from(TransferPlan as tp)).map(TransferPlan(tp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TransferPlan as tp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TransferPlan] = {
    withSQL {
      select.from(TransferPlan as tp).where.append(where)
    }.map(TransferPlan(tp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TransferPlan] = {
    withSQL {
      select.from(TransferPlan as tp).where.append(where)
    }.map(TransferPlan(tp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TransferPlan as tp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userId: Int,
    createdTime: Option[Int] = None,
    updatedTime: Option[Int] = None,
    partnerId: Int,
    statementSummaryId: Int,
    transferMethodId: Int,
    amount: Int,
    planTime: Option[Int] = None,
    actualTime: Option[Int] = None,
    transferType: Option[Int] = None)(implicit session: DBSession = autoSession): TransferPlan = {
    val generatedKey = withSQL {
      insert.into(TransferPlan).columns(
        column.userId,
        column.createdTime,
        column.updatedTime,
        column.partnerId,
        column.statementSummaryId,
        column.transferMethodId,
        column.amount,
        column.planTime,
        column.actualTime,
        column.transferType
      ).values(
        userId,
        createdTime,
        updatedTime,
        partnerId,
        statementSummaryId,
        transferMethodId,
        amount,
        planTime,
        actualTime,
        transferType
      )
    }.updateAndReturnGeneratedKey.apply()

    TransferPlan(
      id = generatedKey.toInt,
      userId = userId,
      createdTime = createdTime,
      updatedTime = updatedTime,
      partnerId = partnerId,
      statementSummaryId = statementSummaryId,
      transferMethodId = transferMethodId,
      amount = amount,
      planTime = planTime,
      actualTime = actualTime,
      transferType = transferType)
  }

  def save(entity: TransferPlan)(implicit session: DBSession = autoSession): TransferPlan = {
    withSQL {
      update(TransferPlan).set(
        column.id -> entity.id,
        column.userId -> entity.userId,
        column.createdTime -> entity.createdTime,
        column.updatedTime -> entity.updatedTime,
        column.partnerId -> entity.partnerId,
        column.statementSummaryId -> entity.statementSummaryId,
        column.transferMethodId -> entity.transferMethodId,
        column.amount -> entity.amount,
        column.planTime -> entity.planTime,
        column.actualTime -> entity.actualTime,
        column.transferType -> entity.transferType
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: TransferPlan)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(TransferPlan).where.eq(column.id, entity.id) }.update.apply()
  }

}
