package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class StatementSummary(
  id: Int,
  userId: Int,
  createdTime: Option[Int] = None,
  updatedTime: Option[Int] = None,
  partnerId: Int,
  amountCaled: Option[Int] = None,
  amountFixed: Option[Int] = None) {

  def save()(implicit session: DBSession = StatementSummary.autoSession): StatementSummary = StatementSummary.save(this)(session)

  def destroy()(implicit session: DBSession = StatementSummary.autoSession): Unit = StatementSummary.destroy(this)(session)

}


object StatementSummary extends SQLSyntaxSupport[StatementSummary] {

  override val tableName = "STATEMENT_SUMMARY"

  override val columns = Seq("ID", "USER_ID", "CREATED_TIME", "UPDATED_TIME", "PARTNER_ID", "AMOUNT_CALED", "AMOUNT_FIXED")

  def apply(ss: SyntaxProvider[StatementSummary])(rs: WrappedResultSet): StatementSummary = apply(ss.resultName)(rs)
  def apply(ss: ResultName[StatementSummary])(rs: WrappedResultSet): StatementSummary = new StatementSummary(
    id = rs.get(ss.id),
    userId = rs.get(ss.userId),
    createdTime = rs.get(ss.createdTime),
    updatedTime = rs.get(ss.updatedTime),
    partnerId = rs.get(ss.partnerId),
    amountCaled = rs.get(ss.amountCaled),
    amountFixed = rs.get(ss.amountFixed)
  )

  val ss = StatementSummary.syntax("ss")

 override def autoSession = AutoSpringDataSourceSession()

  def find(id: Int)(implicit session: DBSession = autoSession): Option[StatementSummary] = {
    withSQL {
      select.from(StatementSummary as ss).where.eq(ss.id, id)
    }.map(StatementSummary(ss.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[StatementSummary] = {
    withSQL(select.from(StatementSummary as ss)).map(StatementSummary(ss.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(StatementSummary as ss)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[StatementSummary] = {
    withSQL {
      select.from(StatementSummary as ss).where.append(where)
    }.map(StatementSummary(ss.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[StatementSummary] = {
    withSQL {
      select.from(StatementSummary as ss).where.append(where)
    }.map(StatementSummary(ss.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(StatementSummary as ss).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userId: Int,
    createdTime: Option[Int] = None,
    updatedTime: Option[Int] = None,
    partnerId: Int,
    amountCaled: Option[Int] = None,
    amountFixed: Option[Int] = None)(implicit session: DBSession = autoSession): StatementSummary = {
    val generatedKey = withSQL {
      insert.into(StatementSummary).columns(
        column.userId,
        column.createdTime,
        column.updatedTime,
        column.partnerId,
        column.amountCaled,
        column.amountFixed
      ).values(
        userId,
        createdTime,
        updatedTime,
        partnerId,
        amountCaled,
        amountFixed
      )
    }.updateAndReturnGeneratedKey.apply()

    StatementSummary(
      id = generatedKey.toInt,
      userId = userId,
      createdTime = createdTime,
      updatedTime = updatedTime,
      partnerId = partnerId,
      amountCaled = amountCaled,
      amountFixed = amountFixed)
  }

  def save(entity: StatementSummary)(implicit session: DBSession = autoSession): StatementSummary = {
    withSQL {
      update(StatementSummary).set(
        column.id -> entity.id,
        column.userId -> entity.userId,
        column.createdTime -> entity.createdTime,
        column.updatedTime -> entity.updatedTime,
        column.partnerId -> entity.partnerId,
        column.amountCaled -> entity.amountCaled,
        column.amountFixed -> entity.amountFixed
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: StatementSummary)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(StatementSummary).where.eq(column.id, entity.id) }.update.apply()
  }

}
