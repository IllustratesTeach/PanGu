package nirvana.hall.api.entities

import scalikejdbc._

case class DzdaCodeRztd(
  lxbh: Option[String] = None,
  lxmc: Option[String] = None,
  mc: Option[String] = None,
  mcjp: Option[String] = None,
  xh: String,
  bz: Option[String] = None,
  sjxh: Option[String] = None) {

  def save()(implicit session: DBSession = DzdaCodeRztd.autoSession): DzdaCodeRztd = DzdaCodeRztd.save(this)(session)

  def destroy()(implicit session: DBSession = DzdaCodeRztd.autoSession): Unit = DzdaCodeRztd.destroy(this)(session)

}


object DzdaCodeRztd extends SQLSyntaxSupport[DzdaCodeRztd] {

  override val tableName = "DZDA_CODE_RZTD"

  override val columns = Seq("LXBH", "LXMC", "MC", "MCJP", "XH", "BZ", "SJXH")

  def apply(dcr: SyntaxProvider[DzdaCodeRztd])(rs: WrappedResultSet): DzdaCodeRztd = apply(dcr.resultName)(rs)
  def apply(dcr: ResultName[DzdaCodeRztd])(rs: WrappedResultSet): DzdaCodeRztd = new DzdaCodeRztd(
    lxbh = rs.get(dcr.lxbh),
    lxmc = rs.get(dcr.lxmc),
    mc = rs.get(dcr.mc),
    mcjp = rs.get(dcr.mcjp),
    xh = rs.get(dcr.xh),
    bz = rs.get(dcr.bz),
    sjxh = rs.get(dcr.sjxh)
  )

  val dcr = DzdaCodeRztd.syntax("dcr")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(lxbh: Option[String], lxmc: Option[String], mc: Option[String], mcjp: Option[String], xh: String, bz: Option[String], sjxh: Option[String])(implicit session: DBSession = autoSession): Option[DzdaCodeRztd] = {
    withSQL {
      select.from(DzdaCodeRztd as dcr).where.eq(dcr.lxbh, lxbh).and.eq(dcr.lxmc, lxmc).and.eq(dcr.mc, mc).and.eq(dcr.mcjp, mcjp).and.eq(dcr.xh, xh).and.eq(dcr.bz, bz).and.eq(dcr.sjxh, sjxh)
    }.map(DzdaCodeRztd(dcr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DzdaCodeRztd] = {
    withSQL(select.from(DzdaCodeRztd as dcr)).map(DzdaCodeRztd(dcr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DzdaCodeRztd as dcr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DzdaCodeRztd] = {
    withSQL {
      select.from(DzdaCodeRztd as dcr).where.append(where)
    }.map(DzdaCodeRztd(dcr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DzdaCodeRztd] = {
    withSQL {
      select.from(DzdaCodeRztd as dcr).where.append(where)
    }.map(DzdaCodeRztd(dcr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DzdaCodeRztd as dcr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    lxbh: Option[String] = None,
    lxmc: Option[String] = None,
    mc: Option[String] = None,
    mcjp: Option[String] = None,
    xh: String,
    bz: Option[String] = None,
    sjxh: Option[String] = None)(implicit session: DBSession = autoSession): DzdaCodeRztd = {
    withSQL {
      insert.into(DzdaCodeRztd).columns(
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

    DzdaCodeRztd(
      lxbh = lxbh,
      lxmc = lxmc,
      mc = mc,
      mcjp = mcjp,
      xh = xh,
      bz = bz,
      sjxh = sjxh)
  }

  def save(entity: DzdaCodeRztd)(implicit session: DBSession = autoSession): DzdaCodeRztd = {
    withSQL {
      update(DzdaCodeRztd).set(
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

  def destroy(entity: DzdaCodeRztd)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(DzdaCodeRztd).where.eq(column.lxbh, entity.lxbh).and.eq(column.lxmc, entity.lxmc).and.eq(column.mc, entity.mc).and.eq(column.mcjp, entity.mcjp).and.eq(column.xh, entity.xh).and.eq(column.bz, entity.bz).and.eq(column.sjxh, entity.sjxh) }.update.apply()
  }

}
