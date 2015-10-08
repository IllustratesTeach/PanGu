package nirvana.hall.api.entities

import scalikejdbc._

case class DzdaCodeBzlx(
  lxbh: Option[String] = None,
  lxmc: Option[String] = None,
  mc: Option[String] = None,
  mcjp: Option[String] = None,
  xh: String,
  bz: Option[String] = None,
  sjxh: Option[String] = None) {

  def save()(implicit session: DBSession = DzdaCodeBzlx.autoSession): DzdaCodeBzlx = DzdaCodeBzlx.save(this)(session)

  def destroy()(implicit session: DBSession = DzdaCodeBzlx.autoSession): Unit = DzdaCodeBzlx.destroy(this)(session)

}


object DzdaCodeBzlx extends SQLSyntaxSupport[DzdaCodeBzlx] {

  override val tableName = "DZDA_CODE_BZLX"

  override val columns = Seq("LXBH", "LXMC", "MC", "MCJP", "XH", "BZ", "SJXH")

  def apply(dcb: SyntaxProvider[DzdaCodeBzlx])(rs: WrappedResultSet): DzdaCodeBzlx = apply(dcb.resultName)(rs)
  def apply(dcb: ResultName[DzdaCodeBzlx])(rs: WrappedResultSet): DzdaCodeBzlx = new DzdaCodeBzlx(
    lxbh = rs.get(dcb.lxbh),
    lxmc = rs.get(dcb.lxmc),
    mc = rs.get(dcb.mc),
    mcjp = rs.get(dcb.mcjp),
    xh = rs.get(dcb.xh),
    bz = rs.get(dcb.bz),
    sjxh = rs.get(dcb.sjxh)
  )

  val dcb = DzdaCodeBzlx.syntax("dcb")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(lxbh: Option[String], lxmc: Option[String], mc: Option[String], mcjp: Option[String], xh: String, bz: Option[String], sjxh: Option[String])(implicit session: DBSession = autoSession): Option[DzdaCodeBzlx] = {
    withSQL {
      select.from(DzdaCodeBzlx as dcb).where.eq(dcb.lxbh, lxbh).and.eq(dcb.lxmc, lxmc).and.eq(dcb.mc, mc).and.eq(dcb.mcjp, mcjp).and.eq(dcb.xh, xh).and.eq(dcb.bz, bz).and.eq(dcb.sjxh, sjxh)
    }.map(DzdaCodeBzlx(dcb.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DzdaCodeBzlx] = {
    withSQL(select.from(DzdaCodeBzlx as dcb)).map(DzdaCodeBzlx(dcb.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DzdaCodeBzlx as dcb)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DzdaCodeBzlx] = {
    withSQL {
      select.from(DzdaCodeBzlx as dcb).where.append(where)
    }.map(DzdaCodeBzlx(dcb.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DzdaCodeBzlx] = {
    withSQL {
      select.from(DzdaCodeBzlx as dcb).where.append(where)
    }.map(DzdaCodeBzlx(dcb.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DzdaCodeBzlx as dcb).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    lxbh: Option[String] = None,
    lxmc: Option[String] = None,
    mc: Option[String] = None,
    mcjp: Option[String] = None,
    xh: String,
    bz: Option[String] = None,
    sjxh: Option[String] = None)(implicit session: DBSession = autoSession): DzdaCodeBzlx = {
    withSQL {
      insert.into(DzdaCodeBzlx).columns(
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

    DzdaCodeBzlx(
      lxbh = lxbh,
      lxmc = lxmc,
      mc = mc,
      mcjp = mcjp,
      xh = xh,
      bz = bz,
      sjxh = sjxh)
  }

  def save(entity: DzdaCodeBzlx)(implicit session: DBSession = autoSession): DzdaCodeBzlx = {
    withSQL {
      update(DzdaCodeBzlx).set(
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

  def destroy(entity: DzdaCodeBzlx)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(DzdaCodeBzlx).where.eq(column.lxbh, entity.lxbh).and.eq(column.lxmc, entity.lxmc).and.eq(column.mc, entity.mc).and.eq(column.mcjp, entity.mcjp).and.eq(column.xh, entity.xh).and.eq(column.bz, entity.bz).and.eq(column.sjxh, entity.sjxh) }.update.apply()
  }

}
