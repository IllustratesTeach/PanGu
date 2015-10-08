package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherGVehicle(
  wpbh: String,
  sawpdm: Option[String] = None,
  jdchpzldm: Option[String] = None,
  clpzh: Option[String] = None,
  vin: Option[String] = None,
  fdjh: Option[String] = None,
  jdccsysdm: Option[String] = None,
  wpjzrmby: Option[Int] = None,
  wptzms: Option[String] = None,
  wpgzsjRqsj: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisGatherGVehicle.autoSession): GafisGatherGVehicle = GafisGatherGVehicle.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherGVehicle.autoSession): Unit = GafisGatherGVehicle.destroy(this)(session)

}


object GafisGatherGVehicle extends SQLSyntaxSupport[GafisGatherGVehicle] {

  override val tableName = "GAFIS_GATHER_G_VEHICLE"

  override val columns = Seq("WPBH", "SAWPDM", "JDCHPZLDM", "CLPZH", "VIN", "FDJH", "JDCCSYSDM", "WPJZRMBY", "WPTZMS", "WPGZSJ_RQSJ")

  def apply(gggv: SyntaxProvider[GafisGatherGVehicle])(rs: WrappedResultSet): GafisGatherGVehicle = apply(gggv.resultName)(rs)
  def apply(gggv: ResultName[GafisGatherGVehicle])(rs: WrappedResultSet): GafisGatherGVehicle = new GafisGatherGVehicle(
    wpbh = rs.get(gggv.wpbh),
    sawpdm = rs.get(gggv.sawpdm),
    jdchpzldm = rs.get(gggv.jdchpzldm),
    clpzh = rs.get(gggv.clpzh),
    vin = rs.get(gggv.vin),
    fdjh = rs.get(gggv.fdjh),
    jdccsysdm = rs.get(gggv.jdccsysdm),
    wpjzrmby = rs.get(gggv.wpjzrmby),
    wptzms = rs.get(gggv.wptzms),
    wpgzsjRqsj = rs.get(gggv.wpgzsjRqsj)
  )

  val gggv = GafisGatherGVehicle.syntax("gggv")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(wpbh: String, sawpdm: Option[String], jdchpzldm: Option[String], clpzh: Option[String], vin: Option[String], fdjh: Option[String], jdccsysdm: Option[String], wpjzrmby: Option[Int], wptzms: Option[String], wpgzsjRqsj: Option[DateTime])(implicit session: DBSession = autoSession): Option[GafisGatherGVehicle] = {
    withSQL {
      select.from(GafisGatherGVehicle as gggv).where.eq(gggv.wpbh, wpbh).and.eq(gggv.sawpdm, sawpdm).and.eq(gggv.jdchpzldm, jdchpzldm).and.eq(gggv.clpzh, clpzh).and.eq(gggv.vin, vin).and.eq(gggv.fdjh, fdjh).and.eq(gggv.jdccsysdm, jdccsysdm).and.eq(gggv.wpjzrmby, wpjzrmby).and.eq(gggv.wptzms, wptzms).and.eq(gggv.wpgzsjRqsj, wpgzsjRqsj)
    }.map(GafisGatherGVehicle(gggv.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherGVehicle] = {
    withSQL(select.from(GafisGatherGVehicle as gggv)).map(GafisGatherGVehicle(gggv.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherGVehicle as gggv)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherGVehicle] = {
    withSQL {
      select.from(GafisGatherGVehicle as gggv).where.append(where)
    }.map(GafisGatherGVehicle(gggv.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherGVehicle] = {
    withSQL {
      select.from(GafisGatherGVehicle as gggv).where.append(where)
    }.map(GafisGatherGVehicle(gggv.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherGVehicle as gggv).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    wpbh: String,
    sawpdm: Option[String] = None,
    jdchpzldm: Option[String] = None,
    clpzh: Option[String] = None,
    vin: Option[String] = None,
    fdjh: Option[String] = None,
    jdccsysdm: Option[String] = None,
    wpjzrmby: Option[Int] = None,
    wptzms: Option[String] = None,
    wpgzsjRqsj: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisGatherGVehicle = {
    withSQL {
      insert.into(GafisGatherGVehicle).columns(
        column.wpbh,
        column.sawpdm,
        column.jdchpzldm,
        column.clpzh,
        column.vin,
        column.fdjh,
        column.jdccsysdm,
        column.wpjzrmby,
        column.wptzms,
        column.wpgzsjRqsj
      ).values(
        wpbh,
        sawpdm,
        jdchpzldm,
        clpzh,
        vin,
        fdjh,
        jdccsysdm,
        wpjzrmby,
        wptzms,
        wpgzsjRqsj
      )
    }.update.apply()

    GafisGatherGVehicle(
      wpbh = wpbh,
      sawpdm = sawpdm,
      jdchpzldm = jdchpzldm,
      clpzh = clpzh,
      vin = vin,
      fdjh = fdjh,
      jdccsysdm = jdccsysdm,
      wpjzrmby = wpjzrmby,
      wptzms = wptzms,
      wpgzsjRqsj = wpgzsjRqsj)
  }

  def save(entity: GafisGatherGVehicle)(implicit session: DBSession = autoSession): GafisGatherGVehicle = {
    withSQL {
      update(GafisGatherGVehicle).set(
        column.wpbh -> entity.wpbh,
        column.sawpdm -> entity.sawpdm,
        column.jdchpzldm -> entity.jdchpzldm,
        column.clpzh -> entity.clpzh,
        column.vin -> entity.vin,
        column.fdjh -> entity.fdjh,
        column.jdccsysdm -> entity.jdccsysdm,
        column.wpjzrmby -> entity.wpjzrmby,
        column.wptzms -> entity.wptzms,
        column.wpgzsjRqsj -> entity.wpgzsjRqsj
      ).where.eq(column.wpbh, entity.wpbh).and.eq(column.sawpdm, entity.sawpdm).and.eq(column.jdchpzldm, entity.jdchpzldm).and.eq(column.clpzh, entity.clpzh).and.eq(column.vin, entity.vin).and.eq(column.fdjh, entity.fdjh).and.eq(column.jdccsysdm, entity.jdccsysdm).and.eq(column.wpjzrmby, entity.wpjzrmby).and.eq(column.wptzms, entity.wptzms).and.eq(column.wpgzsjRqsj, entity.wpgzsjRqsj)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherGVehicle)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherGVehicle).where.eq(column.wpbh, entity.wpbh).and.eq(column.sawpdm, entity.sawpdm).and.eq(column.jdchpzldm, entity.jdchpzldm).and.eq(column.clpzh, entity.clpzh).and.eq(column.vin, entity.vin).and.eq(column.fdjh, entity.fdjh).and.eq(column.jdccsysdm, entity.jdccsysdm).and.eq(column.wpjzrmby, entity.wpjzrmby).and.eq(column.wptzms, entity.wptzms).and.eq(column.wpgzsjRqsj, entity.wpgzsjRqsj) }.update.apply()
  }

}
