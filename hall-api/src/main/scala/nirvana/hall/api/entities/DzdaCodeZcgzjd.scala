package nirvana.hall.api.entities

import scalikejdbc._

case class DzdaCodeZcgzjd(
  lxbh: Option[String] = None,
  lxmc: Option[String] = None,
  mc: Option[String] = None,
  mcjp: Option[String] = None,
  xh: String,
  bz: Option[String] = None,
  sjxh: Option[String] = None) {

  def save()(implicit session: DBSession = DzdaCodeZcgzjd.autoSession): DzdaCodeZcgzjd = DzdaCodeZcgzjd.save(this)(session)

  def destroy()(implicit session: DBSession = DzdaCodeZcgzjd.autoSession): Unit = DzdaCodeZcgzjd.destroy(this)(session)

}


object DzdaCodeZcgzjd extends SQLSyntaxSupport[DzdaCodeZcgzjd] {

  override val tableName = "DZDA_CODE_ZCGZJD"

  override val columns = Seq("LXBH", "LXMC", "MC", "MCJP", "XH", "BZ", "SJXH")

  def apply(dcz: SyntaxProvider[DzdaCodeZcgzjd])(rs: WrappedResultSet): DzdaCodeZcgzjd = apply(dcz.resultName)(rs)
  def apply(dcz: ResultName[DzdaCodeZcgzjd])(rs: WrappedResultSet): DzdaCodeZcgzjd = new DzdaCodeZcgzjd(
    lxbh = rs.get(dcz.lxbh),
    lxmc = rs.get(dcz.lxmc),
    mc = rs.get(dcz.mc),
    mcjp = rs.get(dcz.mcjp),
    xh = rs.get(dcz.xh),
    bz = rs.get(dcz.bz),
    sjxh = rs.get(dcz.sjxh)
  )

  val dcz = DzdaCodeZcgzjd.syntax("dcz")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(lxbh: Option[String], lxmc: Option[String], mc: Option[String], mcjp: Option[String], xh: String, bz: Option[String], sjxh: Option[String])(implicit session: DBSession = autoSession): Option[DzdaCodeZcgzjd] = {
    withSQL {
      select.from(DzdaCodeZcgzjd as dcz).where.eq(dcz.lxbh, lxbh).and.eq(dcz.lxmc, lxmc).and.eq(dcz.mc, mc).and.eq(dcz.mcjp, mcjp).and.eq(dcz.xh, xh).and.eq(dcz.bz, bz).and.eq(dcz.sjxh, sjxh)
    }.map(DzdaCodeZcgzjd(dcz.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DzdaCodeZcgzjd] = {
    withSQL(select.from(DzdaCodeZcgzjd as dcz)).map(DzdaCodeZcgzjd(dcz.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DzdaCodeZcgzjd as dcz)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DzdaCodeZcgzjd] = {
    withSQL {
      select.from(DzdaCodeZcgzjd as dcz).where.append(where)
    }.map(DzdaCodeZcgzjd(dcz.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DzdaCodeZcgzjd] = {
    withSQL {
      select.from(DzdaCodeZcgzjd as dcz).where.append(where)
    }.map(DzdaCodeZcgzjd(dcz.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DzdaCodeZcgzjd as dcz).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    lxbh: Option[String] = None,
    lxmc: Option[String] = None,
    mc: Option[String] = None,
    mcjp: Option[String] = None,
    xh: String,
    bz: Option[String] = None,
    sjxh: Option[String] = None)(implicit session: DBSession = autoSession): DzdaCodeZcgzjd = {
    withSQL {
      insert.into(DzdaCodeZcgzjd).columns(
        column.lxbh,
        column.lxmc,
        column.mc,
        column.mcjp,
        column.xh,
        column.bz,
        column.sjxh
      ).values(
        lxbh,
        lxmc,
        mc,
        mcjp,
        xh,
        bz,
        sjxh
      )
    }.update.apply()

    DzdaCodeZcgzjd(
      lxbh = lxbh,
      lxmc = lxmc,
      mc = mc,
      mcjp = mcjp,
      xh = xh,
      bz = bz,
      sjxh = sjxh)
  }

  def save(entity: DzdaCodeZcgzjd)(implicit session: DBSession = autoSession): DzdaCodeZcgzjd = {
    withSQL {
      update(DzdaCodeZcgzjd).set(
        column.lxbh -> entity.lxbh,
        column.lxmc -> entity.lxmc,
        column.mc -> entity.mc,
        column.mcjp -> entity.mcjp,
        column.xh -> entity.xh,
        column.bz -> entity.bz,
        column.sjxh -> entity.sjxh
      ).where.eq(column.lxbh, entity.lxbh).and.eq(column.lxmc, entity.lxmc).and.eq(column.mc, entity.mc).and.eq(column.mcjp, entity.mcjp).and.eq(column.xh, entity.xh).and.eq(column.bz, entity.bz).and.eq(column.sjxh, entity.sjxh)
    }.update.apply()
    entity
  }

  def destroy(entity: DzdaCodeZcgzjd)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(DzdaCodeZcgzjd).where.eq(column.lxbh, entity.lxbh).and.eq(column.lxmc, entity.lxmc).and.eq(column.mc, entity.mc).and.eq(column.mcjp, entity.mcjp).and.eq(column.xh, entity.xh).and.eq(column.bz, entity.bz).and.eq(column.sjxh, entity.sjxh) }.update.apply()
  }

}
