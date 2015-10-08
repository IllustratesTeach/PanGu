package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class Goods(
  id: Int,
  userId: Int,
  createdTime: Option[Int] = None,
  updatedTime: Option[Int] = None,
  name: String,
  code: Option[String] = None,
  pinyin: Option[String] = None,
  lastPrice: Option[Int] = None,
  quantity: Int) {

  def save()(implicit session: DBSession = Goods.autoSession): Goods = Goods.save(this)(session)

  def destroy()(implicit session: DBSession = Goods.autoSession): Unit = Goods.destroy(this)(session)

}


object Goods extends SQLSyntaxSupport[Goods] {

  override val tableName = "GOODS"

  override val columns = Seq("ID", "USER_ID", "CREATED_TIME", "UPDATED_TIME", "NAME", "CODE", "PINYIN", "LAST_PRICE", "QUANTITY")

  def apply(g: SyntaxProvider[Goods])(rs: WrappedResultSet): Goods = apply(g.resultName)(rs)
  def apply(g: ResultName[Goods])(rs: WrappedResultSet): Goods = new Goods(
    id = rs.get(g.id),
    userId = rs.get(g.userId),
    createdTime = rs.get(g.createdTime),
    updatedTime = rs.get(g.updatedTime),
    name = rs.get(g.name),
    code = rs.get(g.code),
    pinyin = rs.get(g.pinyin),
    lastPrice = rs.get(g.lastPrice),
    quantity = rs.get(g.quantity)
  )

  val g = Goods.syntax("g")

 override def autoSession = AutoSpringDataSourceSession()

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Goods] = {
    withSQL {
      select.from(Goods as g).where.eq(g.id, id)
    }.map(Goods(g.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Goods] = {
    withSQL(select.from(Goods as g)).map(Goods(g.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Goods as g)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Goods] = {
    withSQL {
      select.from(Goods as g).where.append(where)
    }.map(Goods(g.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Goods] = {
    withSQL {
      select.from(Goods as g).where.append(where)
    }.map(Goods(g.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Goods as g).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userId: Int,
    createdTime: Option[Int] = None,
    updatedTime: Option[Int] = None,
    name: String,
    code: Option[String] = None,
    pinyin: Option[String] = None,
    lastPrice: Option[Int] = None,
    quantity: Int)(implicit session: DBSession = autoSession): Goods = {
    val generatedKey = withSQL {
      insert.into(Goods).columns(
        column.userId,
        column.createdTime,
        column.updatedTime,
        column.name,
        column.code,
        column.pinyin,
        column.lastPrice,
        column.quantity
      ).values(
        userId,
        createdTime,
        updatedTime,
        name,
        code,
        pinyin,
        lastPrice,
        quantity
      )
    }.updateAndReturnGeneratedKey.apply()

    Goods(
      id = generatedKey.toInt,
      userId = userId,
      createdTime = createdTime,
      updatedTime = updatedTime,
      name = name,
      code = code,
      pinyin = pinyin,
      lastPrice = lastPrice,
      quantity = quantity)
  }

  def save(entity: Goods)(implicit session: DBSession = autoSession): Goods = {
    withSQL {
      update(Goods).set(
        column.id -> entity.id,
        column.userId -> entity.userId,
        column.createdTime -> entity.createdTime,
        column.updatedTime -> entity.updatedTime,
        column.name -> entity.name,
        column.code -> entity.code,
        column.pinyin -> entity.pinyin,
        column.lastPrice -> entity.lastPrice,
        column.quantity -> entity.quantity
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Goods)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(Goods).where.eq(column.id, entity.id) }.update.apply()
  }

}
