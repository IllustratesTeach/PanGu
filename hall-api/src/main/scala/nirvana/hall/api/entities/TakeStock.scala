package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class TakeStock(
  id: Int,
  goodsId: Int,
  quantityBefore: Int,
  quantityAfter: Int) {

  def save()(implicit session: DBSession = TakeStock.autoSession): TakeStock = TakeStock.save(this)(session)

  def destroy()(implicit session: DBSession = TakeStock.autoSession): Unit = TakeStock.destroy(this)(session)

}


object TakeStock extends SQLSyntaxSupport[TakeStock] {

  override val tableName = "TAKE_STOCK"

  override val columns = Seq("ID", "GOODS_ID", "QUANTITY_BEFORE", "QUANTITY_AFTER")

  def apply(ts: SyntaxProvider[TakeStock])(rs: WrappedResultSet): TakeStock = apply(ts.resultName)(rs)
  def apply(ts: ResultName[TakeStock])(rs: WrappedResultSet): TakeStock = new TakeStock(
    id = rs.get(ts.id),
    goodsId = rs.get(ts.goodsId),
    quantityBefore = rs.get(ts.quantityBefore),
    quantityAfter = rs.get(ts.quantityAfter)
  )

  val ts = TakeStock.syntax("ts")

 override def autoSession = AutoSpringDataSourceSession()

  def find(id: Int)(implicit session: DBSession = autoSession): Option[TakeStock] = {
    withSQL {
      select.from(TakeStock as ts).where.eq(ts.id, id)
    }.map(TakeStock(ts.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TakeStock] = {
    withSQL(select.from(TakeStock as ts)).map(TakeStock(ts.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TakeStock as ts)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TakeStock] = {
    withSQL {
      select.from(TakeStock as ts).where.append(where)
    }.map(TakeStock(ts.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TakeStock] = {
    withSQL {
      select.from(TakeStock as ts).where.append(where)
    }.map(TakeStock(ts.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TakeStock as ts).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    goodsId: Int,
    quantityBefore: Int,
    quantityAfter: Int)(implicit session: DBSession = autoSession): TakeStock = {
    val generatedKey = withSQL {
      insert.into(TakeStock).columns(
        column.goodsId,
        column.quantityBefore,
        column.quantityAfter
      ).values(
        goodsId,
        quantityBefore,
        quantityAfter
      )
    }.updateAndReturnGeneratedKey.apply()

    TakeStock(
      id = generatedKey.toInt,
      goodsId = goodsId,
      quantityBefore = quantityBefore,
      quantityAfter = quantityAfter)
  }

  def save(entity: TakeStock)(implicit session: DBSession = autoSession): TakeStock = {
    withSQL {
      update(TakeStock).set(
        column.id -> entity.id,
        column.goodsId -> entity.goodsId,
        column.quantityBefore -> entity.quantityBefore,
        column.quantityAfter -> entity.quantityAfter
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: TakeStock)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(TakeStock).where.eq(column.id, entity.id) }.update.apply()
  }

}
