package nirvana.hall.api.entities

import scalikejdbc._

case class GafisGoodsType(
  code: String,
  parentid: Option[String] = None,
  ord: Option[Long] = None,
  name: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGoodsType.autoSession): GafisGoodsType = GafisGoodsType.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGoodsType.autoSession): Unit = GafisGoodsType.destroy(this)(session)

}


object GafisGoodsType extends SQLSyntaxSupport[GafisGoodsType] {

  override val tableName = "GAFIS_GOODS_TYPE"

  override val columns = Seq("CODE", "PARENTID", "ORD", "NAME")

  def apply(ggt: SyntaxProvider[GafisGoodsType])(rs: WrappedResultSet): GafisGoodsType = apply(ggt.resultName)(rs)
  def apply(ggt: ResultName[GafisGoodsType])(rs: WrappedResultSet): GafisGoodsType = new GafisGoodsType(
    code = rs.get(ggt.code),
    parentid = rs.get(ggt.parentid),
    ord = rs.get(ggt.ord),
    name = rs.get(ggt.name)
  )

  val ggt = GafisGoodsType.syntax("ggt")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, parentid: Option[String], ord: Option[Long], name: Option[String])(implicit session: DBSession = autoSession): Option[GafisGoodsType] = {
    withSQL {
      select.from(GafisGoodsType as ggt).where.eq(ggt.code, code).and.eq(ggt.parentid, parentid).and.eq(ggt.ord, ord).and.eq(ggt.name, name)
    }.map(GafisGoodsType(ggt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGoodsType] = {
    withSQL(select.from(GafisGoodsType as ggt)).map(GafisGoodsType(ggt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGoodsType as ggt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGoodsType] = {
    withSQL {
      select.from(GafisGoodsType as ggt).where.append(where)
    }.map(GafisGoodsType(ggt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGoodsType] = {
    withSQL {
      select.from(GafisGoodsType as ggt).where.append(where)
    }.map(GafisGoodsType(ggt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGoodsType as ggt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    parentid: Option[String] = None,
    ord: Option[Long] = None,
    name: Option[String] = None)(implicit session: DBSession = autoSession): GafisGoodsType = {
    withSQL {
      insert.into(GafisGoodsType).columns(
        column.code,
        column.parentid,
        column.ord,
        column.name
      ).values(
        code,
        parentid,
        ord,
        name
      )
    }.update.apply()

    GafisGoodsType(
      code = code,
      parentid = parentid,
      ord = ord,
      name = name)
  }

  def save(entity: GafisGoodsType)(implicit session: DBSession = autoSession): GafisGoodsType = {
    withSQL {
      update(GafisGoodsType).set(
        column.code -> entity.code,
        column.parentid -> entity.parentid,
        column.ord -> entity.ord,
        column.name -> entity.name
      ).where.eq(column.code, entity.code).and.eq(column.parentid, entity.parentid).and.eq(column.ord, entity.ord).and.eq(column.name, entity.name)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGoodsType)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGoodsType).where.eq(column.code, entity.code).and.eq(column.parentid, entity.parentid).and.eq(column.ord, entity.ord).and.eq(column.name, entity.name) }.update.apply()
  }

}
