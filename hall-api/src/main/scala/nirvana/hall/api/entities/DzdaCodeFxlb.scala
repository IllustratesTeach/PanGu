package nirvana.hall.api.entities

import scalikejdbc._

case class DzdaCodeFxlb(
  lxbh: Option[String] = None,
  lxmc: Option[String] = None,
  mc: Option[String] = None,
  mcjp: Option[String] = None,
  xh: String,
  bz: Option[String] = None,
  sjxh: Option[String] = None) {

  def save()(implicit session: DBSession = DzdaCodeFxlb.autoSession): DzdaCodeFxlb = DzdaCodeFxlb.save(this)(session)

  def destroy()(implicit session: DBSession = DzdaCodeFxlb.autoSession): Unit = DzdaCodeFxlb.destroy(this)(session)

}


object DzdaCodeFxlb extends SQLSyntaxSupport[DzdaCodeFxlb] {

  override val tableName = "DZDA_CODE_FXLB"

  override val columns = Seq("LXBH", "LXMC", "MC", "MCJP", "XH", "BZ", "SJXH")

  def apply(dcf: SyntaxProvider[DzdaCodeFxlb])(rs: WrappedResultSet): DzdaCodeFxlb = apply(dcf.resultName)(rs)
  def apply(dcf: ResultName[DzdaCodeFxlb])(rs: WrappedResultSet): DzdaCodeFxlb = new DzdaCodeFxlb(
    lxbh = rs.get(dcf.lxbh),
    lxmc = rs.get(dcf.lxmc),
    mc = rs.get(dcf.mc),
    mcjp = rs.get(dcf.mcjp),
    xh = rs.get(dcf.xh),
    bz = rs.get(dcf.bz),
    sjxh = rs.get(dcf.sjxh)
  )

  val dcf = DzdaCodeFxlb.syntax("dcf")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(lxbh: Option[String], lxmc: Option[String], mc: Option[String], mcjp: Option[String], xh: String, bz: Option[String], sjxh: Option[String])(implicit session: DBSession = autoSession): Option[DzdaCodeFxlb] = {
    withSQL {
      select.from(DzdaCodeFxlb as dcf).where.eq(dcf.lxbh, lxbh).and.eq(dcf.lxmc, lxmc).and.eq(dcf.mc, mc).and.eq(dcf.mcjp, mcjp).and.eq(dcf.xh, xh).and.eq(dcf.bz, bz).and.eq(dcf.sjxh, sjxh)
    }.map(DzdaCodeFxlb(dcf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DzdaCodeFxlb] = {
    withSQL(select.from(DzdaCodeFxlb as dcf)).map(DzdaCodeFxlb(dcf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DzdaCodeFxlb as dcf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DzdaCodeFxlb] = {
    withSQL {
      select.from(DzdaCodeFxlb as dcf).where.append(where)
    }.map(DzdaCodeFxlb(dcf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DzdaCodeFxlb] = {
    withSQL {
      select.from(DzdaCodeFxlb as dcf).where.append(where)
    }.map(DzdaCodeFxlb(dcf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DzdaCodeFxlb as dcf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    lxbh: Option[String] = None,
    lxmc: Option[String] = None,
    mc: Option[String] = None,
    mcjp: Option[String] = None,
    xh: String,
    bz: Option[String] = None,
    sjxh: Option[String] = None)(implicit session: DBSession = autoSession): DzdaCodeFxlb = {
    withSQL {
      insert.into(DzdaCodeFxlb).columns(
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

    DzdaCodeFxlb(
      lxbh = lxbh,
      lxmc = lxmc,
      mc = mc,
      mcjp = mcjp,
      xh = xh,
      bz = bz,
      sjxh = sjxh)
  }

  def save(entity: DzdaCodeFxlb)(implicit session: DBSession = autoSession): DzdaCodeFxlb = {
    withSQL {
      update(DzdaCodeFxlb).set(
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

  def destroy(entity: DzdaCodeFxlb)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(DzdaCodeFxlb).where.eq(column.lxbh, entity.lxbh).and.eq(column.lxmc, entity.lxmc).and.eq(column.mc, entity.mc).and.eq(column.mcjp, entity.mcjp).and.eq(column.xh, entity.xh).and.eq(column.bz, entity.bz).and.eq(column.sjxh, entity.sjxh) }.update.apply()
  }

}
