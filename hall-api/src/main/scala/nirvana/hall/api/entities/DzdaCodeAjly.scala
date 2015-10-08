package nirvana.hall.api.entities

import scalikejdbc._

case class DzdaCodeAjly(
  lxbh: Option[String] = None,
  lxmc: Option[String] = None,
  mc: Option[String] = None,
  mcjp: Option[String] = None,
  xh: String,
  bz: Option[String] = None,
  sjxh: Option[String] = None) {

  def save()(implicit session: DBSession = DzdaCodeAjly.autoSession): DzdaCodeAjly = DzdaCodeAjly.save(this)(session)

  def destroy()(implicit session: DBSession = DzdaCodeAjly.autoSession): Unit = DzdaCodeAjly.destroy(this)(session)

}


object DzdaCodeAjly extends SQLSyntaxSupport[DzdaCodeAjly] {

  override val tableName = "DZDA_CODE_AJLY"

  override val columns = Seq("LXBH", "LXMC", "MC", "MCJP", "XH", "BZ", "SJXH")

  def apply(dca: SyntaxProvider[DzdaCodeAjly])(rs: WrappedResultSet): DzdaCodeAjly = apply(dca.resultName)(rs)
  def apply(dca: ResultName[DzdaCodeAjly])(rs: WrappedResultSet): DzdaCodeAjly = new DzdaCodeAjly(
    lxbh = rs.get(dca.lxbh),
    lxmc = rs.get(dca.lxmc),
    mc = rs.get(dca.mc),
    mcjp = rs.get(dca.mcjp),
    xh = rs.get(dca.xh),
    bz = rs.get(dca.bz),
    sjxh = rs.get(dca.sjxh)
  )

  val dca = DzdaCodeAjly.syntax("dca")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(lxbh: Option[String], lxmc: Option[String], mc: Option[String], mcjp: Option[String], xh: String, bz: Option[String], sjxh: Option[String])(implicit session: DBSession = autoSession): Option[DzdaCodeAjly] = {
    withSQL {
      select.from(DzdaCodeAjly as dca).where.eq(dca.lxbh, lxbh).and.eq(dca.lxmc, lxmc).and.eq(dca.mc, mc).and.eq(dca.mcjp, mcjp).and.eq(dca.xh, xh).and.eq(dca.bz, bz).and.eq(dca.sjxh, sjxh)
    }.map(DzdaCodeAjly(dca.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DzdaCodeAjly] = {
    withSQL(select.from(DzdaCodeAjly as dca)).map(DzdaCodeAjly(dca.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DzdaCodeAjly as dca)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DzdaCodeAjly] = {
    withSQL {
      select.from(DzdaCodeAjly as dca).where.append(where)
    }.map(DzdaCodeAjly(dca.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DzdaCodeAjly] = {
    withSQL {
      select.from(DzdaCodeAjly as dca).where.append(where)
    }.map(DzdaCodeAjly(dca.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DzdaCodeAjly as dca).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    lxbh: Option[String] = None,
    lxmc: Option[String] = None,
    mc: Option[String] = None,
    mcjp: Option[String] = None,
    xh: String,
    bz: Option[String] = None,
    sjxh: Option[String] = None)(implicit session: DBSession = autoSession): DzdaCodeAjly = {
    withSQL {
      insert.into(DzdaCodeAjly).columns(
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

    DzdaCodeAjly(
      lxbh = lxbh,
      lxmc = lxmc,
      mc = mc,
      mcjp = mcjp,
      xh = xh,
      bz = bz,
      sjxh = sjxh)
  }

  def save(entity: DzdaCodeAjly)(implicit session: DBSession = autoSession): DzdaCodeAjly = {
    withSQL {
      update(DzdaCodeAjly).set(
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

  def destroy(entity: DzdaCodeAjly)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(DzdaCodeAjly).where.eq(column.lxbh, entity.lxbh).and.eq(column.lxmc, entity.lxmc).and.eq(column.mc, entity.mc).and.eq(column.mcjp, entity.mcjp).and.eq(column.xh, entity.xh).and.eq(column.bz, entity.bz).and.eq(column.sjxh, entity.sjxh) }.update.apply()
  }

}
