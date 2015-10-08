package nirvana.hall.api.entities

import scalikejdbc._

case class GafisAutoCompareConf(
  pkId: String,
  item: Option[String] = None,
  description: Option[String] = None,
  value: Option[Int] = None) {

  def save()(implicit session: DBSession = GafisAutoCompareConf.autoSession): GafisAutoCompareConf = GafisAutoCompareConf.save(this)(session)

  def destroy()(implicit session: DBSession = GafisAutoCompareConf.autoSession): Unit = GafisAutoCompareConf.destroy(this)(session)

}


object GafisAutoCompareConf extends SQLSyntaxSupport[GafisAutoCompareConf] {

  override val tableName = "GAFIS_AUTO_COMPARE_CONF"

  override val columns = Seq("PK_ID", "ITEM", "DESCRIPTION", "VALUE")

  def apply(gacc: SyntaxProvider[GafisAutoCompareConf])(rs: WrappedResultSet): GafisAutoCompareConf = apply(gacc.resultName)(rs)
  def apply(gacc: ResultName[GafisAutoCompareConf])(rs: WrappedResultSet): GafisAutoCompareConf = new GafisAutoCompareConf(
    pkId = rs.get(gacc.pkId),
    item = rs.get(gacc.item),
    description = rs.get(gacc.description),
    value = rs.get(gacc.value)
  )

  val gacc = GafisAutoCompareConf.syntax("gacc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, item: Option[String], description: Option[String], value: Option[Int])(implicit session: DBSession = autoSession): Option[GafisAutoCompareConf] = {
    withSQL {
      select.from(GafisAutoCompareConf as gacc).where.eq(gacc.pkId, pkId).and.eq(gacc.item, item).and.eq(gacc.description, description).and.eq(gacc.value, value)
    }.map(GafisAutoCompareConf(gacc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisAutoCompareConf] = {
    withSQL(select.from(GafisAutoCompareConf as gacc)).map(GafisAutoCompareConf(gacc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisAutoCompareConf as gacc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisAutoCompareConf] = {
    withSQL {
      select.from(GafisAutoCompareConf as gacc).where.append(where)
    }.map(GafisAutoCompareConf(gacc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisAutoCompareConf] = {
    withSQL {
      select.from(GafisAutoCompareConf as gacc).where.append(where)
    }.map(GafisAutoCompareConf(gacc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisAutoCompareConf as gacc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    item: Option[String] = None,
    description: Option[String] = None,
    value: Option[Int] = None)(implicit session: DBSession = autoSession): GafisAutoCompareConf = {
    withSQL {
      insert.into(GafisAutoCompareConf).columns(
        column.pkId,
        column.item,
        column.description,
        column.value
      ).values(
        pkId,
        item,
        description,
        value
      )
    }.update.apply()

    GafisAutoCompareConf(
      pkId = pkId,
      item = item,
      description = description,
      value = value)
  }

  def save(entity: GafisAutoCompareConf)(implicit session: DBSession = autoSession): GafisAutoCompareConf = {
    withSQL {
      update(GafisAutoCompareConf).set(
        column.pkId -> entity.pkId,
        column.item -> entity.item,
        column.description -> entity.description,
        column.value -> entity.value
      ).where.eq(column.pkId, entity.pkId).and.eq(column.item, entity.item).and.eq(column.description, entity.description).and.eq(column.value, entity.value)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisAutoCompareConf)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisAutoCompareConf).where.eq(column.pkId, entity.pkId).and.eq(column.item, entity.item).and.eq(column.description, entity.description).and.eq(column.value, entity.value) }.update.apply()
  }

}
