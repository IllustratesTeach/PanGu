package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class Statement(
  id: Int,
  userId: Int,
  createdTime: Option[Int] = None,
  updatedTime: Option[Int] = None,
  partnerId: Int,
  statementSummaryId: Option[Int] = None,
  name: Option[String] = None,
  myAmount: Int,
  hisAmount: Int,
  amountFixed: Int,
  time: Option[Int] = None) {

  def save()(implicit session: DBSession = Statement.autoSession): Statement = Statement.save(this)(session)

  def destroy()(implicit session: DBSession = Statement.autoSession): Unit = Statement.destroy(this)(session)

}


object Statement extends SQLSyntaxSupport[Statement] {

  override val tableName = "STATEMENT"

  override val columns = Seq("ID", "USER_ID", "CREATED_TIME", "UPDATED_TIME", "PARTNER_ID", "STATEMENT_SUMMARY_ID", "NAME", "MY_AMOUNT", "HIS_AMOUNT", "AMOUNT_FIXED", "TIME")

  def apply(s: SyntaxProvider[Statement])(rs: WrappedResultSet): Statement = apply(s.resultName)(rs)
  def apply(s: ResultName[Statement])(rs: WrappedResultSet): Statement = new Statement(
    id = rs.get(s.id),
    userId = rs.get(s.userId),
    createdTime = rs.get(s.createdTime),
    updatedTime = rs.get(s.updatedTime),
    partnerId = rs.get(s.partnerId),
    statementSummaryId = rs.get(s.statementSummaryId),
    name = rs.get(s.name),
    myAmount = rs.get(s.myAmount),
    hisAmount = rs.get(s.hisAmount),
    amountFixed = rs.get(s.amountFixed),
    time = rs.get(s.time)
  )

  val s = Statement.syntax("s")

 override def autoSession = AutoSpringDataSourceSession()

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Statement] = {
    withSQL {
      select.from(Statement as s).where.eq(s.id, id)
    }.map(Statement(s.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Statement] = {
    withSQL(select.from(Statement as s)).map(Statement(s.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Statement as s)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Statement] = {
    withSQL {
      select.from(Statement as s).where.append(where)
    }.map(Statement(s.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Statement] = {
    withSQL {
      select.from(Statement as s).where.append(where)
    }.map(Statement(s.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Statement as s).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userId: Int,
    createdTime: Option[Int] = None,
    updatedTime: Option[Int] = None,
    partnerId: Int,
    statementSummaryId: Option[Int] = None,
    name: Option[String] = None,
    myAmount: Int,
    hisAmount: Int,
    amountFixed: Int,
    time: Option[Int] = None)(implicit session: DBSession = autoSession): Statement = {
    val generatedKey = withSQL {
      insert.into(Statement).columns(
        column.userId,
        column.createdTime,
        column.updatedTime,
        column.partnerId,
        column.statementSummaryId,
        column.name,
        column.myAmount,
        column.hisAmount,
        column.amountFixed,
        column.time
      ).values(
        userId,
        createdTime,
        updatedTime,
        partnerId,
        statementSummaryId,
        name,
        myAmount,
        hisAmount,
        amountFixed,
        time
      )
    }.updateAndReturnGeneratedKey.apply()

    Statement(
      id = generatedKey.toInt,
      userId = userId,
      createdTime = createdTime,
      updatedTime = updatedTime,
      partnerId = partnerId,
      statementSummaryId = statementSummaryId,
      name = name,
      myAmount = myAmount,
      hisAmount = hisAmount,
      amountFixed = amountFixed,
      time = time)
  }

  def save(entity: Statement)(implicit session: DBSession = autoSession): Statement = {
    withSQL {
      update(Statement).set(
        column.id -> entity.id,
        column.userId -> entity.userId,
        column.createdTime -> entity.createdTime,
        column.updatedTime -> entity.updatedTime,
        column.partnerId -> entity.partnerId,
        column.statementSummaryId -> entity.statementSummaryId,
        column.name -> entity.name,
        column.myAmount -> entity.myAmount,
        column.hisAmount -> entity.hisAmount,
        column.amountFixed -> entity.amountFixed,
        column.time -> entity.time
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Statement)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(Statement).where.eq(column.id, entity.id) }.update.apply()
  }

}
