package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisGatherGPhoto(
  xxbh: String,
  wpbh: Option[String] = None,
  dzwjmc: Option[String] = None,
  dzwjzy: Option[String] = None,
  dzwjgs: Option[String] = None,
  dzwjnr: Option[Blob] = None,
  dzwjdx: Option[String] = None,
  dzwjwz: Option[String] = None,
  dzwjcjsjRqsj: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisGatherGPhoto.autoSession): GafisGatherGPhoto = GafisGatherGPhoto.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherGPhoto.autoSession): Unit = GafisGatherGPhoto.destroy(this)(session)

}


object GafisGatherGPhoto extends SQLSyntaxSupport[GafisGatherGPhoto] {

  override val tableName = "GAFIS_GATHER_G_PHOTO"

  override val columns = Seq("XXBH", "WPBH", "DZWJMC", "DZWJZY", "DZWJGS", "DZWJNR", "DZWJDX", "DZWJWZ", "DZWJCJSJ_RQSJ")

  def apply(gggp: SyntaxProvider[GafisGatherGPhoto])(rs: WrappedResultSet): GafisGatherGPhoto = apply(gggp.resultName)(rs)
  def apply(gggp: ResultName[GafisGatherGPhoto])(rs: WrappedResultSet): GafisGatherGPhoto = new GafisGatherGPhoto(
    xxbh = rs.get(gggp.xxbh),
    wpbh = rs.get(gggp.wpbh),
    dzwjmc = rs.get(gggp.dzwjmc),
    dzwjzy = rs.get(gggp.dzwjzy),
    dzwjgs = rs.get(gggp.dzwjgs),
    dzwjnr = rs.get(gggp.dzwjnr),
    dzwjdx = rs.get(gggp.dzwjdx),
    dzwjwz = rs.get(gggp.dzwjwz),
    dzwjcjsjRqsj = rs.get(gggp.dzwjcjsjRqsj)
  )

  val gggp = GafisGatherGPhoto.syntax("gggp")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(xxbh: String, wpbh: Option[String], dzwjmc: Option[String], dzwjzy: Option[String], dzwjgs: Option[String], dzwjnr: Option[Blob], dzwjdx: Option[String], dzwjwz: Option[String], dzwjcjsjRqsj: Option[DateTime])(implicit session: DBSession = autoSession): Option[GafisGatherGPhoto] = {
    withSQL {
      select.from(GafisGatherGPhoto as gggp).where.eq(gggp.xxbh, xxbh).and.eq(gggp.wpbh, wpbh).and.eq(gggp.dzwjmc, dzwjmc).and.eq(gggp.dzwjzy, dzwjzy).and.eq(gggp.dzwjgs, dzwjgs).and.eq(gggp.dzwjnr, dzwjnr).and.eq(gggp.dzwjdx, dzwjdx).and.eq(gggp.dzwjwz, dzwjwz).and.eq(gggp.dzwjcjsjRqsj, dzwjcjsjRqsj)
    }.map(GafisGatherGPhoto(gggp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherGPhoto] = {
    withSQL(select.from(GafisGatherGPhoto as gggp)).map(GafisGatherGPhoto(gggp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherGPhoto as gggp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherGPhoto] = {
    withSQL {
      select.from(GafisGatherGPhoto as gggp).where.append(where)
    }.map(GafisGatherGPhoto(gggp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherGPhoto] = {
    withSQL {
      select.from(GafisGatherGPhoto as gggp).where.append(where)
    }.map(GafisGatherGPhoto(gggp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherGPhoto as gggp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    xxbh: String,
    wpbh: Option[String] = None,
    dzwjmc: Option[String] = None,
    dzwjzy: Option[String] = None,
    dzwjgs: Option[String] = None,
    dzwjnr: Option[Blob] = None,
    dzwjdx: Option[String] = None,
    dzwjwz: Option[String] = None,
    dzwjcjsjRqsj: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisGatherGPhoto = {
    withSQL {
      insert.into(GafisGatherGPhoto).columns(
        column.xxbh,
        column.wpbh,
        column.dzwjmc,
        column.dzwjzy,
        column.dzwjgs,
        column.dzwjnr,
        column.dzwjdx,
        column.dzwjwz,
        column.dzwjcjsjRqsj
      ).values(
        xxbh,
        wpbh,
        dzwjmc,
        dzwjzy,
        dzwjgs,
        dzwjnr,
        dzwjdx,
        dzwjwz,
        dzwjcjsjRqsj
      )
    }.update.apply()

    GafisGatherGPhoto(
      xxbh = xxbh,
      wpbh = wpbh,
      dzwjmc = dzwjmc,
      dzwjzy = dzwjzy,
      dzwjgs = dzwjgs,
      dzwjnr = dzwjnr,
      dzwjdx = dzwjdx,
      dzwjwz = dzwjwz,
      dzwjcjsjRqsj = dzwjcjsjRqsj)
  }

  def save(entity: GafisGatherGPhoto)(implicit session: DBSession = autoSession): GafisGatherGPhoto = {
    withSQL {
      update(GafisGatherGPhoto).set(
        column.xxbh -> entity.xxbh,
        column.wpbh -> entity.wpbh,
        column.dzwjmc -> entity.dzwjmc,
        column.dzwjzy -> entity.dzwjzy,
        column.dzwjgs -> entity.dzwjgs,
        column.dzwjnr -> entity.dzwjnr,
        column.dzwjdx -> entity.dzwjdx,
        column.dzwjwz -> entity.dzwjwz,
        column.dzwjcjsjRqsj -> entity.dzwjcjsjRqsj
      ).where.eq(column.xxbh, entity.xxbh).and.eq(column.wpbh, entity.wpbh).and.eq(column.dzwjmc, entity.dzwjmc).and.eq(column.dzwjzy, entity.dzwjzy).and.eq(column.dzwjgs, entity.dzwjgs).and.eq(column.dzwjnr, entity.dzwjnr).and.eq(column.dzwjdx, entity.dzwjdx).and.eq(column.dzwjwz, entity.dzwjwz).and.eq(column.dzwjcjsjRqsj, entity.dzwjcjsjRqsj)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherGPhoto)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherGPhoto).where.eq(column.xxbh, entity.xxbh).and.eq(column.wpbh, entity.wpbh).and.eq(column.dzwjmc, entity.dzwjmc).and.eq(column.dzwjzy, entity.dzwjzy).and.eq(column.dzwjgs, entity.dzwjgs).and.eq(column.dzwjnr, entity.dzwjnr).and.eq(column.dzwjdx, entity.dzwjdx).and.eq(column.dzwjwz, entity.dzwjwz).and.eq(column.dzwjcjsjRqsj, entity.dzwjcjsjRqsj) }.update.apply()
  }

}
