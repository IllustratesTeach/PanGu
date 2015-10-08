package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class ReceivingMoney(
  id: Int,
  userId: Int,
  createdTime: Option[Int] = None,
  updatedTime: Option[Int] = None,
  partnerId: Int,
  receivingType: Option[Short] = None,
  amount: Option[Int] = None) {

  def save()(implicit session: DBSession = ReceivingMoney.autoSession): ReceivingMoney = ReceivingMoney.save(this)(session)

  def destroy()(implicit session: DBSession = ReceivingMoney.autoSession): Unit = ReceivingMoney.destroy(this)(session)

}


object ReceivingMoney extends SQLSyntaxSupport[ReceivingMoney] {

  override val tableName = "RECEIVING_MONEY"

  override val columns = Seq("ID", "USER_ID", "CREATED_TIME", "UPDATED_TIME", "PARTNER_ID", "RECEIVING_TYPE", "AMOUNT")

  def apply(rm: SyntaxProvider[ReceivingMoney])(rs: WrappedResultSet): ReceivingMoney = apply(rm.resultName)(rs)
  def apply(rm: ResultName[ReceivingMoney])(rs: WrappedResultSet): ReceivingMoney = new ReceivingMoney(
    id = rs.get(rm.id),
    userId = rs.get(rm.userId),
    createdTime = rs.get(rm.createdTime),
    updatedTime = rs.get(rm.updatedTime),
    partnerId = rs.get(rm.partnerId),
    receivingType = rs.get(rm.receivingType),
    amount = rs.get(rm.amount)
  )

  val rm = ReceivingMoney.syntax("rm")

 override def autoSession = AutoSpringDataSourceSession()

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ReceivingMoney] = {
    withSQL {
      select.from(ReceivingMoney as rm).where.eq(rm.id, id)
    }.map(ReceivingMoney(rm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ReceivingMoney] = {
    withSQL(select.from(ReceivingMoney as rm)).map(ReceivingMoney(rm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ReceivingMoney as rm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ReceivingMoney] = {
    withSQL {
      select.from(ReceivingMoney as rm).where.append(where)
    }.map(ReceivingMoney(rm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ReceivingMoney] = {
    withSQL {
      select.from(ReceivingMoney as rm).where.append(where)
    }.map(ReceivingMoney(rm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ReceivingMoney as rm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userId: Int,
    createdTime: Option[Int] = None,
    updatedTime: Option[Int] = None,
    partnerId: Int,
    receivingType: Option[Short] = None,
    amount: Option[Int] = None)(implicit session: DBSession = autoSession): ReceivingMoney = {
    val generatedKey = withSQL {
      insert.into(ReceivingMoney).columns(
        column.userId,
        column.createdTime,
        column.updatedTime,
        column.partnerId,
        column.receivingType,
        column.amount
      ).values(
        userId,
        createdTime,
        updatedTime,
        partnerId,
        receivingType,
        amount
      )
    }.updateAndReturnGeneratedKey.apply()

    ReceivingMoney(
      id = generatedKey.toInt,
      userId = userId,
      createdTime = createdTime,
      updatedTime = updatedTime,
      partnerId = partnerId,
      receivingType = receivingType,
      amount = amount)
  }

  def save(entity: ReceivingMoney)(implicit session: DBSession = autoSession): ReceivingMoney = {
    withSQL {
      update(ReceivingMoney).set(
        column.id -> entity.id,
        column.userId -> entity.userId,
        column.createdTime -> entity.createdTime,
        column.updatedTime -> entity.updatedTime,
        column.partnerId -> entity.partnerId,
        column.receivingType -> entity.receivingType,
        column.amount -> entity.amount
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ReceivingMoney)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(ReceivingMoney).where.eq(column.id, entity.id) }.update.apply()
  }

}
