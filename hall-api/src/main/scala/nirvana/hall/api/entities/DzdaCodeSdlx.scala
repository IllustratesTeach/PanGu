package nirvana.hall.api.entities

import scalikejdbc._

case class DzdaCodeSdlx(
  lxbh: Option[String] = None,
  lxmc: Option[String] = None,
  mc: Option[String] = None,
  mcjp: Option[String] = None,
  xh: String,
  bz: Option[String] = None,
  sjxh: Option[String] = None) {

  def save()(implicit session: DBSession = DzdaCodeSdlx.autoSession): DzdaCodeSdlx = DzdaCodeSdlx.save(this)(session)

  def destroy()(implicit session: DBSession = DzdaCodeSdlx.autoSession): Unit = DzdaCodeSdlx.destroy(this)(session)

}


object DzdaCodeSdlx extends SQLSyntaxSupport[DzdaCodeSdlx] {

  override val tableName = "DZDA_CODE_SDLX"

  override val columns = Seq("LXBH", "LXMC", "MC", "MCJP", "XH", "BZ", "SJXH")

  def apply(dcs: SyntaxProvider[DzdaCodeSdlx])(rs: WrappedResultSet): DzdaCodeSdlx = apply(dcs.resultName)(rs)
  def apply(dcs: ResultName[DzdaCodeSdlx])(rs: WrappedResultSet): DzdaCodeSdlx = new DzdaCodeSdlx(
    lxbh = rs.get(dcs.lxbh),
    lxmc = rs.get(dcs.lxmc),
    mc = rs.get(dcs.mc),
    mcjp = rs.get(dcs.mcjp),
    xh = rs.get(dcs.xh),
    bz = rs.get(dcs.bz),
    sjxh = rs.get(dcs.sjxh)
  )

  val dcs = DzdaCodeSdlx.syntax("dcs")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(lxbh: Option[String], lxmc: Option[String], mc: Option[String], mcjp: Option[String], xh: String, bz: Option[String], sjxh: Option[String])(implicit session: DBSession = autoSession): Option[DzdaCodeSdlx] = {
    withSQL {
      select.from(DzdaCodeSdlx as dcs).where.eq(dcs.lxbh, lxbh).and.eq(dcs.lxmc, lxmc).and.eq(dcs.mc, mc).and.eq(dcs.mcjp, mcjp).and.eq(dcs.xh, xh).and.eq(dcs.bz, bz).and.eq(dcs.sjxh, sjxh)
    }.map(DzdaCodeSdlx(dcs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DzdaCodeSdlx] = {
    withSQL(select.from(DzdaCodeSdlx as dcs)).map(DzdaCodeSdlx(dcs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DzdaCodeSdlx as dcs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DzdaCodeSdlx] = {
    withSQL {
      select.from(DzdaCodeSdlx as dcs).where.append(where)
    }.map(DzdaCodeSdlx(dcs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DzdaCodeSdlx] = {
    withSQL {
      select.from(DzdaCodeSdlx as dcs).where.append(where)
    }.map(DzdaCodeSdlx(dcs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DzdaCodeSdlx as dcs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    lxbh: Option[String] = None,
    lxmc: Option[String] = None,
    mc: Option[String] = None,
    mcjp: Option[String] = None,
    xh: String,
    bz: Option[String] = None,
    sjxh: Option[String] = None)(implicit session: DBSession = autoSession): DzdaCodeSdlx = {
    withSQL {
      insert.into(DzdaCodeSdlx).columns(
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

    DzdaCodeSdlx(
      lxbh = lxbh,
      lxmc = lxmc,
      mc = mc,
      mcjp = mcjp,
      xh = xh,
      bz = bz,
      sjxh = sjxh)
  }

  def save(entity: DzdaCodeSdlx)(implicit session: DBSession = autoSession): DzdaCodeSdlx = {
    withSQL {
      update(DzdaCodeSdlx).set(
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

  def destroy(entity: DzdaCodeSdlx)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(DzdaCodeSdlx).where.eq(column.lxbh, entity.lxbh).and.eq(column.lxmc, entity.lxmc).and.eq(column.mc, entity.mc).and.eq(column.mcjp, entity.mcjp).and.eq(column.xh, entity.xh).and.eq(column.bz, entity.bz).and.eq(column.sjxh, entity.sjxh) }.update.apply()
  }

}
