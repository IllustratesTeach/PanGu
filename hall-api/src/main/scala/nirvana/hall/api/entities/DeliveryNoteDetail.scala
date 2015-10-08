package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class DeliveryNoteDetail(
  id: Int,
  deliveryNoteId: Int,
  goodsId: Int,
  quantity: Int,
  unitPrice: Option[Int] = None,
  amount: Int) {

  def save()(implicit session: DBSession = DeliveryNoteDetail.autoSession): DeliveryNoteDetail = DeliveryNoteDetail.save(this)(session)

  def destroy()(implicit session: DBSession = DeliveryNoteDetail.autoSession): Unit = DeliveryNoteDetail.destroy(this)(session)

}


object DeliveryNoteDetail extends SQLSyntaxSupport[DeliveryNoteDetail] {

  override val tableName = "DELIVERY_NOTE_DETAIL"

  override val columns = Seq("ID", "DELIVERY_NOTE_ID", "GOODS_ID", "QUANTITY", "UNIT_PRICE", "AMOUNT")

  def apply(dnd: SyntaxProvider[DeliveryNoteDetail])(rs: WrappedResultSet): DeliveryNoteDetail = apply(dnd.resultName)(rs)
  def apply(dnd: ResultName[DeliveryNoteDetail])(rs: WrappedResultSet): DeliveryNoteDetail = new DeliveryNoteDetail(
    id = rs.get(dnd.id),
    deliveryNoteId = rs.get(dnd.deliveryNoteId),
    goodsId = rs.get(dnd.goodsId),
    quantity = rs.get(dnd.quantity),
    unitPrice = rs.get(dnd.unitPrice),
    amount = rs.get(dnd.amount)
  )

  val dnd = DeliveryNoteDetail.syntax("dnd")

 override def autoSession = AutoSpringDataSourceSession()

  def find(id: Int)(implicit session: DBSession = autoSession): Option[DeliveryNoteDetail] = {
    withSQL {
      select.from(DeliveryNoteDetail as dnd).where.eq(dnd.id, id)
    }.map(DeliveryNoteDetail(dnd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DeliveryNoteDetail] = {
    withSQL(select.from(DeliveryNoteDetail as dnd)).map(DeliveryNoteDetail(dnd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DeliveryNoteDetail as dnd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DeliveryNoteDetail] = {
    withSQL {
      select.from(DeliveryNoteDetail as dnd).where.append(where)
    }.map(DeliveryNoteDetail(dnd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DeliveryNoteDetail] = {
    withSQL {
      select.from(DeliveryNoteDetail as dnd).where.append(where)
    }.map(DeliveryNoteDetail(dnd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DeliveryNoteDetail as dnd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    deliveryNoteId: Int,
    goodsId: Int,
    quantity: Int,
    unitPrice: Option[Int] = None,
    amount: Int)(implicit session: DBSession = autoSession): DeliveryNoteDetail = {
    val generatedKey = withSQL {
      insert.into(DeliveryNoteDetail).columns(
        column.deliveryNoteId,
        column.goodsId,
        column.quantity,
        column.unitPrice,
        column.amount
      ).values(
        deliveryNoteId,
        goodsId,
        quantity,
        unitPrice,
        amount
      )
    }.updateAndReturnGeneratedKey.apply()

    DeliveryNoteDetail(
      id = generatedKey.toInt,
      deliveryNoteId = deliveryNoteId,
      goodsId = goodsId,
      quantity = quantity,
      unitPrice = unitPrice,
      amount = amount)
  }

  def save(entity: DeliveryNoteDetail)(implicit session: DBSession = autoSession): DeliveryNoteDetail = {
    withSQL {
      update(DeliveryNoteDetail).set(
        column.id -> entity.id,
        column.deliveryNoteId -> entity.deliveryNoteId,
        column.goodsId -> entity.goodsId,
        column.quantity -> entity.quantity,
        column.unitPrice -> entity.unitPrice,
        column.amount -> entity.amount
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: DeliveryNoteDetail)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(DeliveryNoteDetail).where.eq(column.id, entity.id) }.update.apply()
  }

}
