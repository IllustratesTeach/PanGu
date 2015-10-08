package nirvana.hall.api.entities

import scalikejdbc._

case class GafisGatherGCurrency(
  wpbh: String,
  hbzldm: Option[String] = None,
  hbmz: Option[Short] = None,
  jldw: Option[String] = None,
  wpjzrmby: Option[Int] = None,
  wptzms: Option[String] = None,
  wpzwbs: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherGCurrency.autoSession): GafisGatherGCurrency = GafisGatherGCurrency.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherGCurrency.autoSession): Unit = GafisGatherGCurrency.destroy(this)(session)

}


object GafisGatherGCurrency extends SQLSyntaxSupport[GafisGatherGCurrency] {

  override val tableName = "GAFIS_GATHER_G_CURRENCY"

  override val columns = Seq("WPBH", "HBZLDM", "HBMZ", "JLDW", "WPJZRMBY", "WPTZMS", "WPZWBS")

  def apply(gggc: SyntaxProvider[GafisGatherGCurrency])(rs: WrappedResultSet): GafisGatherGCurrency = apply(gggc.resultName)(rs)
  def apply(gggc: ResultName[GafisGatherGCurrency])(rs: WrappedResultSet): GafisGatherGCurrency = new GafisGatherGCurrency(
    wpbh = rs.get(gggc.wpbh),
    hbzldm = rs.get(gggc.hbzldm),
    hbmz = rs.get(gggc.hbmz),
    jldw = rs.get(gggc.jldw),
    wpjzrmby = rs.get(gggc.wpjzrmby),
    wptzms = rs.get(gggc.wptzms),
    wpzwbs = rs.get(gggc.wpzwbs)
  )

  val gggc = GafisGatherGCurrency.syntax("gggc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(wpbh: String, hbzldm: Option[String], hbmz: Option[Short], jldw: Option[String], wpjzrmby: Option[Int], wptzms: Option[String], wpzwbs: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherGCurrency] = {
    withSQL {
      select.from(GafisGatherGCurrency as gggc).where.eq(gggc.wpbh, wpbh).and.eq(gggc.hbzldm, hbzldm).and.eq(gggc.hbmz, hbmz).and.eq(gggc.jldw, jldw).and.eq(gggc.wpjzrmby, wpjzrmby).and.eq(gggc.wptzms, wptzms).and.eq(gggc.wpzwbs, wpzwbs)
    }.map(GafisGatherGCurrency(gggc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherGCurrency] = {
    withSQL(select.from(GafisGatherGCurrency as gggc)).map(GafisGatherGCurrency(gggc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherGCurrency as gggc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherGCurrency] = {
    withSQL {
      select.from(GafisGatherGCurrency as gggc).where.append(where)
    }.map(GafisGatherGCurrency(gggc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherGCurrency] = {
    withSQL {
      select.from(GafisGatherGCurrency as gggc).where.append(where)
    }.map(GafisGatherGCurrency(gggc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherGCurrency as gggc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    wpbh: String,
    hbzldm: Option[String] = None,
    hbmz: Option[Short] = None,
    jldw: Option[String] = None,
    wpjzrmby: Option[Int] = None,
    wptzms: Option[String] = None,
    wpzwbs: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherGCurrency = {
    withSQL {
      insert.into(GafisGatherGCurrency).columns(
        column.wpbh,
        column.hbzldm,
        column.hbmz,
        column.jldw,
        column.wpjzrmby,
        column.wptzms,
        column.wpzwbs
      ).values(
        wpbh,
        hbzldm,
        hbmz,
        jldw,
        wpjzrmby,
        wptzms,
        wpzwbs
      )
    }.update.apply()

    GafisGatherGCurrency(
      wpbh = wpbh,
      hbzldm = hbzldm,
      hbmz = hbmz,
      jldw = jldw,
      wpjzrmby = wpjzrmby,
      wptzms = wptzms,
      wpzwbs = wpzwbs)
  }

  def save(entity: GafisGatherGCurrency)(implicit session: DBSession = autoSession): GafisGatherGCurrency = {
    withSQL {
      update(GafisGatherGCurrency).set(
        column.wpbh -> entity.wpbh,
        column.hbzldm -> entity.hbzldm,
        column.hbmz -> entity.hbmz,
        column.jldw -> entity.jldw,
        column.wpjzrmby -> entity.wpjzrmby,
        column.wptzms -> entity.wptzms,
        column.wpzwbs -> entity.wpzwbs
      ).where.eq(column.wpbh, entity.wpbh).and.eq(column.hbzldm, entity.hbzldm).and.eq(column.hbmz, entity.hbmz).and.eq(column.jldw, entity.jldw).and.eq(column.wpjzrmby, entity.wpjzrmby).and.eq(column.wptzms, entity.wptzms).and.eq(column.wpzwbs, entity.wpzwbs)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherGCurrency)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherGCurrency).where.eq(column.wpbh, entity.wpbh).and.eq(column.hbzldm, entity.hbzldm).and.eq(column.hbmz, entity.hbmz).and.eq(column.jldw, entity.jldw).and.eq(column.wpjzrmby, entity.wpjzrmby).and.eq(column.wptzms, entity.wptzms).and.eq(column.wpzwbs, entity.wpzwbs) }.update.apply()
  }

}
