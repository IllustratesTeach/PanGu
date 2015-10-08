package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class ReceivingNoteDetail(
  id: Int,
  receivingNoteId: Int,
  goodsId: Int,
  quantity: Int,
  unitPrice: Option[Int] = None,
  amount: Int) {

  def save()(implicit session: DBSession = ReceivingNoteDetail.autoSession): ReceivingNoteDetail = ReceivingNoteDetail.save(this)(session)

  def destroy()(implicit session: DBSession = ReceivingNoteDetail.autoSession): Unit = ReceivingNoteDetail.destroy(this)(session)

}


object ReceivingNoteDetail extends SQLSyntaxSupport[ReceivingNoteDetail] {

  override val tableName = "RECEIVING_NOTE_DETAIL"

  override val columns = Seq("ID", "RECEIVING_NOTE_ID", "GOODS_ID", "QUANTITY", "UNIT_PRICE", "AMOUNT")

  def apply(rnd: SyntaxProvider[ReceivingNoteDetail])(rs: WrappedResultSet): ReceivingNoteDetail = apply(rnd.resultName)(rs)
  def apply(rnd: ResultName[ReceivingNoteDetail])(rs: WrappedResultSet): ReceivingNoteDetail = new ReceivingNoteDetail(
    id = rs.get(rnd.id),
    receivingNoteId = rs.get(rnd.receivingNoteId),
    goodsId = rs.get(rnd.goodsId),
    quantity = rs.get(rnd.quantity),
    unitPrice = rs.get(rnd.unitPrice),
    amount = rs.get(rnd.amount)
  )

  val rnd = ReceivingNoteDetail.syntax("rnd")

 override def autoSession = AutoSpringDataSourceSession()

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ReceivingNoteDetail] = {
    withSQL {
      select.from(ReceivingNoteDetail as rnd).where.eq(rnd.id, id)
    }.map(ReceivingNoteDetail(rnd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ReceivingNoteDetail] = {
    withSQL(select.from(ReceivingNoteDetail as rnd)).map(ReceivingNoteDetail(rnd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ReceivingNoteDetail as rnd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ReceivingNoteDetail] = {
    withSQL {
      select.from(ReceivingNoteDetail as rnd).where.append(where)
    }.map(ReceivingNoteDetail(rnd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ReceivingNoteDetail] = {
    withSQL {
      select.from(ReceivingNoteDetail as rnd).where.append(where)
    }.map(ReceivingNoteDetail(rnd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ReceivingNoteDetail as rnd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    receivingNoteId: Int,
    goodsId: Int,
    quantity: Int,
    unitPrice: Option[Int] = None,
    amount: Int)(implicit session: DBSession = autoSession): ReceivingNoteDetail = {
    val generatedKey = withSQL {
      insert.into(ReceivingNoteDetail).columns(
        column.receivingNoteId,
        column.goodsId,
        column.quantity,
        column.unitPrice,
        column.amount
      ).values(
        receivingNoteId,
        goodsId,
        quantity,
        unitPrice,
        amount
      )
    }.updateAndReturnGeneratedKey.apply()

    ReceivingNoteDetail(
      id = generatedKey.toInt,
      receivingNoteId = receivingNoteId,
      goodsId = goodsId,
      quantity = quantity,
      unitPrice = unitPrice,
      amount = amount)
  }

  def save(entity: ReceivingNoteDetail)(implicit session: DBSession = autoSession): ReceivingNoteDetail = {
    withSQL {
      update(ReceivingNoteDetail).set(
        column.id -> entity.id,
        column.receivingNoteId -> entity.receivingNoteId,
        column.goodsId -> entity.goodsId,
        column.quantity -> entity.quantity,
        column.unitPrice -> entity.unitPrice,
        column.amount -> entity.amount
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ReceivingNoteDetail)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(ReceivingNoteDetail).where.eq(column.id, entity.id) }.update.apply()
  }

}
