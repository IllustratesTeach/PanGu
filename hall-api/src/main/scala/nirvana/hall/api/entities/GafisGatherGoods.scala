package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherGoods(
  wpbh: String,
  sawpdm: Option[String] = None,
  sawpdmbcms: Option[String] = None,
  wpxlh: Option[String] = None,
  wpysdm: Option[String] = None,
  wpysdmbcms: Option[String] = None,
  wpjzrmby: Option[Int] = None,
  wpsl: Option[Int] = None,
  wpzl: Option[Int] = None,
  jldw: Option[String] = None,
  wptzms: Option[String] = None,
  wpzwbs: Option[String] = None,
  wpgzsjRqsj: Option[DateTime] = None,
  objectType: Option[Long] = None) {

  def save()(implicit session: DBSession = GafisGatherGoods.autoSession): GafisGatherGoods = GafisGatherGoods.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherGoods.autoSession): Unit = GafisGatherGoods.destroy(this)(session)

}


object GafisGatherGoods extends SQLSyntaxSupport[GafisGatherGoods] {

  override val tableName = "GAFIS_GATHER_GOODS"

  override val columns = Seq("WPBH", "SAWPDM", "SAWPDMBCMS", "WPXLH", "WPYSDM", "WPYSDMBCMS", "WPJZRMBY", "WPSL", "WPZL", "JLDW", "WPTZMS", "WPZWBS", "WPGZSJ_RQSJ", "OBJECT_TYPE")

  def apply(ggg: SyntaxProvider[GafisGatherGoods])(rs: WrappedResultSet): GafisGatherGoods = apply(ggg.resultName)(rs)
  def apply(ggg: ResultName[GafisGatherGoods])(rs: WrappedResultSet): GafisGatherGoods = new GafisGatherGoods(
    wpbh = rs.get(ggg.wpbh),
    sawpdm = rs.get(ggg.sawpdm),
    sawpdmbcms = rs.get(ggg.sawpdmbcms),
    wpxlh = rs.get(ggg.wpxlh),
    wpysdm = rs.get(ggg.wpysdm),
    wpysdmbcms = rs.get(ggg.wpysdmbcms),
    wpjzrmby = rs.get(ggg.wpjzrmby),
    wpsl = rs.get(ggg.wpsl),
    wpzl = rs.get(ggg.wpzl),
    jldw = rs.get(ggg.jldw),
    wptzms = rs.get(ggg.wptzms),
    wpzwbs = rs.get(ggg.wpzwbs),
    wpgzsjRqsj = rs.get(ggg.wpgzsjRqsj),
    objectType = rs.get(ggg.objectType)
  )

  val ggg = GafisGatherGoods.syntax("ggg")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(wpbh: String, sawpdm: Option[String], sawpdmbcms: Option[String], wpxlh: Option[String], wpysdm: Option[String], wpysdmbcms: Option[String], wpjzrmby: Option[Int], wpsl: Option[Int], wpzl: Option[Int], jldw: Option[String], wptzms: Option[String], wpzwbs: Option[String], wpgzsjRqsj: Option[DateTime], objectType: Option[Long])(implicit session: DBSession = autoSession): Option[GafisGatherGoods] = {
    withSQL {
      select.from(GafisGatherGoods as ggg).where.eq(ggg.wpbh, wpbh).and.eq(ggg.sawpdm, sawpdm).and.eq(ggg.sawpdmbcms, sawpdmbcms).and.eq(ggg.wpxlh, wpxlh).and.eq(ggg.wpysdm, wpysdm).and.eq(ggg.wpysdmbcms, wpysdmbcms).and.eq(ggg.wpjzrmby, wpjzrmby).and.eq(ggg.wpsl, wpsl).and.eq(ggg.wpzl, wpzl).and.eq(ggg.jldw, jldw).and.eq(ggg.wptzms, wptzms).and.eq(ggg.wpzwbs, wpzwbs).and.eq(ggg.wpgzsjRqsj, wpgzsjRqsj).and.eq(ggg.objectType, objectType)
    }.map(GafisGatherGoods(ggg.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherGoods] = {
    withSQL(select.from(GafisGatherGoods as ggg)).map(GafisGatherGoods(ggg.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherGoods as ggg)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherGoods] = {
    withSQL {
      select.from(GafisGatherGoods as ggg).where.append(where)
    }.map(GafisGatherGoods(ggg.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherGoods] = {
    withSQL {
      select.from(GafisGatherGoods as ggg).where.append(where)
    }.map(GafisGatherGoods(ggg.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherGoods as ggg).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    wpbh: String,
    sawpdm: Option[String] = None,
    sawpdmbcms: Option[String] = None,
    wpxlh: Option[String] = None,
    wpysdm: Option[String] = None,
    wpysdmbcms: Option[String] = None,
    wpjzrmby: Option[Int] = None,
    wpsl: Option[Int] = None,
    wpzl: Option[Int] = None,
    jldw: Option[String] = None,
    wptzms: Option[String] = None,
    wpzwbs: Option[String] = None,
    wpgzsjRqsj: Option[DateTime] = None,
    objectType: Option[Long] = None)(implicit session: DBSession = autoSession): GafisGatherGoods = {
    withSQL {
      insert.into(GafisGatherGoods).columns(
        column.wpbh,
        column.sawpdm,
        column.sawpdmbcms,
        column.wpxlh,
        column.wpysdm,
        column.wpysdmbcms,
        column.wpjzrmby,
        column.wpsl,
        column.wpzl,
        column.jldw,
        column.wptzms,
        column.wpzwbs,
        column.wpgzsjRqsj,
        column.objectType
      ).values(
        wpbh,
        sawpdm,
        sawpdmbcms,
        wpxlh,
        wpysdm,
        wpysdmbcms,
        wpjzrmby,
        wpsl,
        wpzl,
        jldw,
        wptzms,
        wpzwbs,
        wpgzsjRqsj,
        objectType
      )
    }.update.apply()

    GafisGatherGoods(
      wpbh = wpbh,
      sawpdm = sawpdm,
      sawpdmbcms = sawpdmbcms,
      wpxlh = wpxlh,
      wpysdm = wpysdm,
      wpysdmbcms = wpysdmbcms,
      wpjzrmby = wpjzrmby,
      wpsl = wpsl,
      wpzl = wpzl,
      jldw = jldw,
      wptzms = wptzms,
      wpzwbs = wpzwbs,
      wpgzsjRqsj = wpgzsjRqsj,
      objectType = objectType)
  }

  def save(entity: GafisGatherGoods)(implicit session: DBSession = autoSession): GafisGatherGoods = {
    withSQL {
      update(GafisGatherGoods).set(
        column.wpbh -> entity.wpbh,
        column.sawpdm -> entity.sawpdm,
        column.sawpdmbcms -> entity.sawpdmbcms,
        column.wpxlh -> entity.wpxlh,
        column.wpysdm -> entity.wpysdm,
        column.wpysdmbcms -> entity.wpysdmbcms,
        column.wpjzrmby -> entity.wpjzrmby,
        column.wpsl -> entity.wpsl,
        column.wpzl -> entity.wpzl,
        column.jldw -> entity.jldw,
        column.wptzms -> entity.wptzms,
        column.wpzwbs -> entity.wpzwbs,
        column.wpgzsjRqsj -> entity.wpgzsjRqsj,
        column.objectType -> entity.objectType
      ).where.eq(column.wpbh, entity.wpbh).and.eq(column.sawpdm, entity.sawpdm).and.eq(column.sawpdmbcms, entity.sawpdmbcms).and.eq(column.wpxlh, entity.wpxlh).and.eq(column.wpysdm, entity.wpysdm).and.eq(column.wpysdmbcms, entity.wpysdmbcms).and.eq(column.wpjzrmby, entity.wpjzrmby).and.eq(column.wpsl, entity.wpsl).and.eq(column.wpzl, entity.wpzl).and.eq(column.jldw, entity.jldw).and.eq(column.wptzms, entity.wptzms).and.eq(column.wpzwbs, entity.wpzwbs).and.eq(column.wpgzsjRqsj, entity.wpgzsjRqsj).and.eq(column.objectType, entity.objectType)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherGoods)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherGoods).where.eq(column.wpbh, entity.wpbh).and.eq(column.sawpdm, entity.sawpdm).and.eq(column.sawpdmbcms, entity.sawpdmbcms).and.eq(column.wpxlh, entity.wpxlh).and.eq(column.wpysdm, entity.wpysdm).and.eq(column.wpysdmbcms, entity.wpysdmbcms).and.eq(column.wpjzrmby, entity.wpjzrmby).and.eq(column.wpsl, entity.wpsl).and.eq(column.wpzl, entity.wpzl).and.eq(column.jldw, entity.jldw).and.eq(column.wptzms, entity.wptzms).and.eq(column.wpzwbs, entity.wpzwbs).and.eq(column.wpgzsjRqsj, entity.wpgzsjRqsj).and.eq(column.objectType, entity.objectType) }.update.apply()
  }

}
