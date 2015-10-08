package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherGBicycle(
  wpbh: String,
  djh: Option[String] = None,
  cjh: Option[String] = None,
  clpzh: Option[String] = None,
  gyh: Option[String] = None,
  wpysdm: Option[String] = None,
  wpysdmbcms: Option[String] = None,
  wpjzrmby: Option[Int] = None,
  wptzms: Option[String] = None,
  wpgzsjRqsj: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisGatherGBicycle.autoSession): GafisGatherGBicycle = GafisGatherGBicycle.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherGBicycle.autoSession): Unit = GafisGatherGBicycle.destroy(this)(session)

}


object GafisGatherGBicycle extends SQLSyntaxSupport[GafisGatherGBicycle] {

  override val tableName = "GAFIS_GATHER_G_BICYCLE"

  override val columns = Seq("WPBH", "DJH", "CJH", "CLPZH", "GYH", "WPYSDM", "WPYSDMBCMS", "WPJZRMBY", "WPTZMS", "WPGZSJ_RQSJ")

  def apply(gggb: SyntaxProvider[GafisGatherGBicycle])(rs: WrappedResultSet): GafisGatherGBicycle = apply(gggb.resultName)(rs)
  def apply(gggb: ResultName[GafisGatherGBicycle])(rs: WrappedResultSet): GafisGatherGBicycle = new GafisGatherGBicycle(
    wpbh = rs.get(gggb.wpbh),
    djh = rs.get(gggb.djh),
    cjh = rs.get(gggb.cjh),
    clpzh = rs.get(gggb.clpzh),
    gyh = rs.get(gggb.gyh),
    wpysdm = rs.get(gggb.wpysdm),
    wpysdmbcms = rs.get(gggb.wpysdmbcms),
    wpjzrmby = rs.get(gggb.wpjzrmby),
    wptzms = rs.get(gggb.wptzms),
    wpgzsjRqsj = rs.get(gggb.wpgzsjRqsj)
  )

  val gggb = GafisGatherGBicycle.syntax("gggb")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(wpbh: String, djh: Option[String], cjh: Option[String], clpzh: Option[String], gyh: Option[String], wpysdm: Option[String], wpysdmbcms: Option[String], wpjzrmby: Option[Int], wptzms: Option[String], wpgzsjRqsj: Option[DateTime])(implicit session: DBSession = autoSession): Option[GafisGatherGBicycle] = {
    withSQL {
      select.from(GafisGatherGBicycle as gggb).where.eq(gggb.wpbh, wpbh).and.eq(gggb.djh, djh).and.eq(gggb.cjh, cjh).and.eq(gggb.clpzh, clpzh).and.eq(gggb.gyh, gyh).and.eq(gggb.wpysdm, wpysdm).and.eq(gggb.wpysdmbcms, wpysdmbcms).and.eq(gggb.wpjzrmby, wpjzrmby).and.eq(gggb.wptzms, wptzms).and.eq(gggb.wpgzsjRqsj, wpgzsjRqsj)
    }.map(GafisGatherGBicycle(gggb.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherGBicycle] = {
    withSQL(select.from(GafisGatherGBicycle as gggb)).map(GafisGatherGBicycle(gggb.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherGBicycle as gggb)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherGBicycle] = {
    withSQL {
      select.from(GafisGatherGBicycle as gggb).where.append(where)
    }.map(GafisGatherGBicycle(gggb.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherGBicycle] = {
    withSQL {
      select.from(GafisGatherGBicycle as gggb).where.append(where)
    }.map(GafisGatherGBicycle(gggb.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherGBicycle as gggb).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    wpbh: String,
    djh: Option[String] = None,
    cjh: Option[String] = None,
    clpzh: Option[String] = None,
    gyh: Option[String] = None,
    wpysdm: Option[String] = None,
    wpysdmbcms: Option[String] = None,
    wpjzrmby: Option[Int] = None,
    wptzms: Option[String] = None,
    wpgzsjRqsj: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisGatherGBicycle = {
    withSQL {
      insert.into(GafisGatherGBicycle).columns(
        column.wpbh,
        column.djh,
        column.cjh,
        column.clpzh,
        column.gyh,
        column.wpysdm,
        column.wpysdmbcms,
        column.wpjzrmby,
        column.wptzms,
        column.wpgzsjRqsj
      ).values(
        wpbh,
        djh,
        cjh,
        clpzh,
        gyh,
        wpysdm,
        wpysdmbcms,
        wpjzrmby,
        wptzms,
        wpgzsjRqsj
      )
    }.update.apply()

    GafisGatherGBicycle(
      wpbh = wpbh,
      djh = djh,
      cjh = cjh,
      clpzh = clpzh,
      gyh = gyh,
      wpysdm = wpysdm,
      wpysdmbcms = wpysdmbcms,
      wpjzrmby = wpjzrmby,
      wptzms = wptzms,
      wpgzsjRqsj = wpgzsjRqsj)
  }

  def save(entity: GafisGatherGBicycle)(implicit session: DBSession = autoSession): GafisGatherGBicycle = {
    withSQL {
      update(GafisGatherGBicycle).set(
        column.wpbh -> entity.wpbh,
        column.djh -> entity.djh,
        column.cjh -> entity.cjh,
        column.clpzh -> entity.clpzh,
        column.gyh -> entity.gyh,
        column.wpysdm -> entity.wpysdm,
        column.wpysdmbcms -> entity.wpysdmbcms,
        column.wpjzrmby -> entity.wpjzrmby,
        column.wptzms -> entity.wptzms,
        column.wpgzsjRqsj -> entity.wpgzsjRqsj
      ).where.eq(column.wpbh, entity.wpbh).and.eq(column.djh, entity.djh).and.eq(column.cjh, entity.cjh).and.eq(column.clpzh, entity.clpzh).and.eq(column.gyh, entity.gyh).and.eq(column.wpysdm, entity.wpysdm).and.eq(column.wpysdmbcms, entity.wpysdmbcms).and.eq(column.wpjzrmby, entity.wpjzrmby).and.eq(column.wptzms, entity.wptzms).and.eq(column.wpgzsjRqsj, entity.wpgzsjRqsj)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherGBicycle)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherGBicycle).where.eq(column.wpbh, entity.wpbh).and.eq(column.djh, entity.djh).and.eq(column.cjh, entity.cjh).and.eq(column.clpzh, entity.clpzh).and.eq(column.gyh, entity.gyh).and.eq(column.wpysdm, entity.wpysdm).and.eq(column.wpysdmbcms, entity.wpysdmbcms).and.eq(column.wpjzrmby, entity.wpjzrmby).and.eq(column.wptzms, entity.wptzms).and.eq(column.wpgzsjRqsj, entity.wpgzsjRqsj) }.update.apply()
  }

}
