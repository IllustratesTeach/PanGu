package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherGMobile(
  wpbh: String,
  iccid: Option[String] = None,
  imei: Option[String] = None,
  imsi: Option[String] = None,
  dhhm: Option[String] = None,
  wpjzrmby: Option[Int] = None,
  wptzms: Option[String] = None,
  wpgzsjRqsj: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisGatherGMobile.autoSession): GafisGatherGMobile = GafisGatherGMobile.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherGMobile.autoSession): Unit = GafisGatherGMobile.destroy(this)(session)

}


object GafisGatherGMobile extends SQLSyntaxSupport[GafisGatherGMobile] {

  override val tableName = "GAFIS_GATHER_G_MOBILE"

  override val columns = Seq("WPBH", "ICCID", "IMEI", "IMSI", "DHHM", "WPJZRMBY", "WPTZMS", "WPGZSJ_RQSJ")

  def apply(gggm: SyntaxProvider[GafisGatherGMobile])(rs: WrappedResultSet): GafisGatherGMobile = apply(gggm.resultName)(rs)
  def apply(gggm: ResultName[GafisGatherGMobile])(rs: WrappedResultSet): GafisGatherGMobile = new GafisGatherGMobile(
    wpbh = rs.get(gggm.wpbh),
    iccid = rs.get(gggm.iccid),
    imei = rs.get(gggm.imei),
    imsi = rs.get(gggm.imsi),
    dhhm = rs.get(gggm.dhhm),
    wpjzrmby = rs.get(gggm.wpjzrmby),
    wptzms = rs.get(gggm.wptzms),
    wpgzsjRqsj = rs.get(gggm.wpgzsjRqsj)
  )

  val gggm = GafisGatherGMobile.syntax("gggm")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(wpbh: String, iccid: Option[String], imei: Option[String], imsi: Option[String], dhhm: Option[String], wpjzrmby: Option[Int], wptzms: Option[String], wpgzsjRqsj: Option[DateTime])(implicit session: DBSession = autoSession): Option[GafisGatherGMobile] = {
    withSQL {
      select.from(GafisGatherGMobile as gggm).where.eq(gggm.wpbh, wpbh).and.eq(gggm.iccid, iccid).and.eq(gggm.imei, imei).and.eq(gggm.imsi, imsi).and.eq(gggm.dhhm, dhhm).and.eq(gggm.wpjzrmby, wpjzrmby).and.eq(gggm.wptzms, wptzms).and.eq(gggm.wpgzsjRqsj, wpgzsjRqsj)
    }.map(GafisGatherGMobile(gggm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherGMobile] = {
    withSQL(select.from(GafisGatherGMobile as gggm)).map(GafisGatherGMobile(gggm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherGMobile as gggm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherGMobile] = {
    withSQL {
      select.from(GafisGatherGMobile as gggm).where.append(where)
    }.map(GafisGatherGMobile(gggm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherGMobile] = {
    withSQL {
      select.from(GafisGatherGMobile as gggm).where.append(where)
    }.map(GafisGatherGMobile(gggm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherGMobile as gggm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    wpbh: String,
    iccid: Option[String] = None,
    imei: Option[String] = None,
    imsi: Option[String] = None,
    dhhm: Option[String] = None,
    wpjzrmby: Option[Int] = None,
    wptzms: Option[String] = None,
    wpgzsjRqsj: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisGatherGMobile = {
    withSQL {
      insert.into(GafisGatherGMobile).columns(
        column.wpbh,
        column.iccid,
        column.imei,
        column.imsi,
        column.dhhm,
        column.wpjzrmby,
        column.wptzms,
        column.wpgzsjRqsj
      ).values(
        wpbh,
        iccid,
        imei,
        imsi,
        dhhm,
        wpjzrmby,
        wptzms,
        wpgzsjRqsj
      )
    }.update.apply()

    GafisGatherGMobile(
      wpbh = wpbh,
      iccid = iccid,
      imei = imei,
      imsi = imsi,
      dhhm = dhhm,
      wpjzrmby = wpjzrmby,
      wptzms = wptzms,
      wpgzsjRqsj = wpgzsjRqsj)
  }

  def save(entity: GafisGatherGMobile)(implicit session: DBSession = autoSession): GafisGatherGMobile = {
    withSQL {
      update(GafisGatherGMobile).set(
        column.wpbh -> entity.wpbh,
        column.iccid -> entity.iccid,
        column.imei -> entity.imei,
        column.imsi -> entity.imsi,
        column.dhhm -> entity.dhhm,
        column.wpjzrmby -> entity.wpjzrmby,
        column.wptzms -> entity.wptzms,
        column.wpgzsjRqsj -> entity.wpgzsjRqsj
      ).where.eq(column.wpbh, entity.wpbh).and.eq(column.iccid, entity.iccid).and.eq(column.imei, entity.imei).and.eq(column.imsi, entity.imsi).and.eq(column.dhhm, entity.dhhm).and.eq(column.wpjzrmby, entity.wpjzrmby).and.eq(column.wptzms, entity.wptzms).and.eq(column.wpgzsjRqsj, entity.wpgzsjRqsj)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherGMobile)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherGMobile).where.eq(column.wpbh, entity.wpbh).and.eq(column.iccid, entity.iccid).and.eq(column.imei, entity.imei).and.eq(column.imsi, entity.imsi).and.eq(column.dhhm, entity.dhhm).and.eq(column.wpjzrmby, entity.wpjzrmby).and.eq(column.wptzms, entity.wptzms).and.eq(column.wpgzsjRqsj, entity.wpgzsjRqsj) }.update.apply()
  }

}
