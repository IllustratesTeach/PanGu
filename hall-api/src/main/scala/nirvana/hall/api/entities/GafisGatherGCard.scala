package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherGCard(
  wpbh: String,
  sawpdm: Option[String] = None,
  sawpdmbcms: Option[String] = None,
  kh: Option[String] = None,
  wpzwbs: Option[String] = None,
  fkdwGjhdqdm: Option[String] = None,
  fkdwDwmc: Option[String] = None,
  fksjRqsj: Option[DateTime] = None,
  yxqKssj: Option[DateTime] = None,
  yxqJssj: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisGatherGCard.autoSession): GafisGatherGCard = GafisGatherGCard.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherGCard.autoSession): Unit = GafisGatherGCard.destroy(this)(session)

}


object GafisGatherGCard extends SQLSyntaxSupport[GafisGatherGCard] {

  override val tableName = "GAFIS_GATHER_G_CARD"

  override val columns = Seq("WPBH", "SAWPDM", "SAWPDMBCMS", "KH", "WPZWBS", "FKDW_GJHDQDM", "FKDW_DWMC", "FKSJ_RQSJ", "YXQ_KSSJ", "YXQ_JSSJ")

  def apply(gggc: SyntaxProvider[GafisGatherGCard])(rs: WrappedResultSet): GafisGatherGCard = apply(gggc.resultName)(rs)
  def apply(gggc: ResultName[GafisGatherGCard])(rs: WrappedResultSet): GafisGatherGCard = new GafisGatherGCard(
    wpbh = rs.get(gggc.wpbh),
    sawpdm = rs.get(gggc.sawpdm),
    sawpdmbcms = rs.get(gggc.sawpdmbcms),
    kh = rs.get(gggc.kh),
    wpzwbs = rs.get(gggc.wpzwbs),
    fkdwGjhdqdm = rs.get(gggc.fkdwGjhdqdm),
    fkdwDwmc = rs.get(gggc.fkdwDwmc),
    fksjRqsj = rs.get(gggc.fksjRqsj),
    yxqKssj = rs.get(gggc.yxqKssj),
    yxqJssj = rs.get(gggc.yxqJssj)
  )

  val gggc = GafisGatherGCard.syntax("gggc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(wpbh: String, sawpdm: Option[String], sawpdmbcms: Option[String], kh: Option[String], wpzwbs: Option[String], fkdwGjhdqdm: Option[String], fkdwDwmc: Option[String], fksjRqsj: Option[DateTime], yxqKssj: Option[DateTime], yxqJssj: Option[DateTime])(implicit session: DBSession = autoSession): Option[GafisGatherGCard] = {
    withSQL {
      select.from(GafisGatherGCard as gggc).where.eq(gggc.wpbh, wpbh).and.eq(gggc.sawpdm, sawpdm).and.eq(gggc.sawpdmbcms, sawpdmbcms).and.eq(gggc.kh, kh).and.eq(gggc.wpzwbs, wpzwbs).and.eq(gggc.fkdwGjhdqdm, fkdwGjhdqdm).and.eq(gggc.fkdwDwmc, fkdwDwmc).and.eq(gggc.fksjRqsj, fksjRqsj).and.eq(gggc.yxqKssj, yxqKssj).and.eq(gggc.yxqJssj, yxqJssj)
    }.map(GafisGatherGCard(gggc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherGCard] = {
    withSQL(select.from(GafisGatherGCard as gggc)).map(GafisGatherGCard(gggc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherGCard as gggc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherGCard] = {
    withSQL {
      select.from(GafisGatherGCard as gggc).where.append(where)
    }.map(GafisGatherGCard(gggc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherGCard] = {
    withSQL {
      select.from(GafisGatherGCard as gggc).where.append(where)
    }.map(GafisGatherGCard(gggc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherGCard as gggc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    wpbh: String,
    sawpdm: Option[String] = None,
    sawpdmbcms: Option[String] = None,
    kh: Option[String] = None,
    wpzwbs: Option[String] = None,
    fkdwGjhdqdm: Option[String] = None,
    fkdwDwmc: Option[String] = None,
    fksjRqsj: Option[DateTime] = None,
    yxqKssj: Option[DateTime] = None,
    yxqJssj: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisGatherGCard = {
    withSQL {
      insert.into(GafisGatherGCard).columns(
        column.wpbh,
        column.sawpdm,
        column.sawpdmbcms,
        column.kh,
        column.wpzwbs,
        column.fkdwGjhdqdm,
        column.fkdwDwmc,
        column.fksjRqsj,
        column.yxqKssj,
        column.yxqJssj
      ).values(
        wpbh,
        sawpdm,
        sawpdmbcms,
        kh,
        wpzwbs,
        fkdwGjhdqdm,
        fkdwDwmc,
        fksjRqsj,
        yxqKssj,
        yxqJssj
      )
    }.update.apply()

    GafisGatherGCard(
      wpbh = wpbh,
      sawpdm = sawpdm,
      sawpdmbcms = sawpdmbcms,
      kh = kh,
      wpzwbs = wpzwbs,
      fkdwGjhdqdm = fkdwGjhdqdm,
      fkdwDwmc = fkdwDwmc,
      fksjRqsj = fksjRqsj,
      yxqKssj = yxqKssj,
      yxqJssj = yxqJssj)
  }

  def save(entity: GafisGatherGCard)(implicit session: DBSession = autoSession): GafisGatherGCard = {
    withSQL {
      update(GafisGatherGCard).set(
        column.wpbh -> entity.wpbh,
        column.sawpdm -> entity.sawpdm,
        column.sawpdmbcms -> entity.sawpdmbcms,
        column.kh -> entity.kh,
        column.wpzwbs -> entity.wpzwbs,
        column.fkdwGjhdqdm -> entity.fkdwGjhdqdm,
        column.fkdwDwmc -> entity.fkdwDwmc,
        column.fksjRqsj -> entity.fksjRqsj,
        column.yxqKssj -> entity.yxqKssj,
        column.yxqJssj -> entity.yxqJssj
      ).where.eq(column.wpbh, entity.wpbh).and.eq(column.sawpdm, entity.sawpdm).and.eq(column.sawpdmbcms, entity.sawpdmbcms).and.eq(column.kh, entity.kh).and.eq(column.wpzwbs, entity.wpzwbs).and.eq(column.fkdwGjhdqdm, entity.fkdwGjhdqdm).and.eq(column.fkdwDwmc, entity.fkdwDwmc).and.eq(column.fksjRqsj, entity.fksjRqsj).and.eq(column.yxqKssj, entity.yxqKssj).and.eq(column.yxqJssj, entity.yxqJssj)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherGCard)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherGCard).where.eq(column.wpbh, entity.wpbh).and.eq(column.sawpdm, entity.sawpdm).and.eq(column.sawpdmbcms, entity.sawpdmbcms).and.eq(column.kh, entity.kh).and.eq(column.wpzwbs, entity.wpzwbs).and.eq(column.fkdwGjhdqdm, entity.fkdwGjhdqdm).and.eq(column.fkdwDwmc, entity.fkdwDwmc).and.eq(column.fksjRqsj, entity.fksjRqsj).and.eq(column.yxqKssj, entity.yxqKssj).and.eq(column.yxqJssj, entity.yxqJssj) }.update.apply()
  }

}
