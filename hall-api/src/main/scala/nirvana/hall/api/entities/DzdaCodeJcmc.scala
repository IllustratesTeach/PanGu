package nirvana.hall.api.entities

import scalikejdbc._

case class DzdaCodeJcmc(
  lxbh: Option[String] = None,
  lxmc: Option[String] = None,
  mc: Option[String] = None,
  mcjp: Option[String] = None,
  xh: String,
  bz: Option[String] = None,
  sjxh: Option[String] = None) {

  def save()(implicit session: DBSession = DzdaCodeJcmc.autoSession): DzdaCodeJcmc = DzdaCodeJcmc.save(this)(session)

  def destroy()(implicit session: DBSession = DzdaCodeJcmc.autoSession): Unit = DzdaCodeJcmc.destroy(this)(session)

}


object DzdaCodeJcmc extends SQLSyntaxSupport[DzdaCodeJcmc] {

  override val tableName = "DZDA_CODE_JCMC"

  override val columns = Seq("LXBH", "LXMC", "MC", "MCJP", "XH", "BZ", "SJXH")

  def apply(dcj: SyntaxProvider[DzdaCodeJcmc])(rs: WrappedResultSet): DzdaCodeJcmc = apply(dcj.resultName)(rs)
  def apply(dcj: ResultName[DzdaCodeJcmc])(rs: WrappedResultSet): DzdaCodeJcmc = new DzdaCodeJcmc(
    lxbh = rs.get(dcj.lxbh),
    lxmc = rs.get(dcj.lxmc),
    mc = rs.get(dcj.mc),
    mcjp = rs.get(dcj.mcjp),
    xh = rs.get(dcj.xh),
    bz = rs.get(dcj.bz),
    sjxh = rs.get(dcj.sjxh)
  )

  val dcj = DzdaCodeJcmc.syntax("dcj")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(lxbh: Option[String], lxmc: Option[String], mc: Option[String], mcjp: Option[String], xh: String, bz: Option[String], sjxh: Option[String])(implicit session: DBSession = autoSession): Option[DzdaCodeJcmc] = {
    withSQL {
      select.from(DzdaCodeJcmc as dcj).where.eq(dcj.lxbh, lxbh).and.eq(dcj.lxmc, lxmc).and.eq(dcj.mc, mc).and.eq(dcj.mcjp, mcjp).and.eq(dcj.xh, xh).and.eq(dcj.bz, bz).and.eq(dcj.sjxh, sjxh)
    }.map(DzdaCodeJcmc(dcj.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DzdaCodeJcmc] = {
    withSQL(select.from(DzdaCodeJcmc as dcj)).map(DzdaCodeJcmc(dcj.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DzdaCodeJcmc as dcj)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DzdaCodeJcmc] = {
    withSQL {
      select.from(DzdaCodeJcmc as dcj).where.append(where)
    }.map(DzdaCodeJcmc(dcj.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DzdaCodeJcmc] = {
    withSQL {
      select.from(DzdaCodeJcmc as dcj).where.append(where)
    }.map(DzdaCodeJcmc(dcj.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DzdaCodeJcmc as dcj).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    lxbh: Option[String] = None,
    lxmc: Option[String] = None,
    mc: Option[String] = None,
    mcjp: Option[String] = None,
    xh: String,
    bz: Option[String] = None,
    sjxh: Option[String] = None)(implicit session: DBSession = autoSession): DzdaCodeJcmc = {
    withSQL {
      insert.into(DzdaCodeJcmc).columns(
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

    DzdaCodeJcmc(
      lxbh = lxbh,
      lxmc = lxmc,
      mc = mc,
      mcjp = mcjp,
      xh = xh,
      bz = bz,
      sjxh = sjxh)
  }

  def save(entity: DzdaCodeJcmc)(implicit session: DBSession = autoSession): DzdaCodeJcmc = {
    withSQL {
      update(DzdaCodeJcmc).set(
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

  def destroy(entity: DzdaCodeJcmc)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(DzdaCodeJcmc).where.eq(column.lxbh, entity.lxbh).and.eq(column.lxmc, entity.lxmc).and.eq(column.mc, entity.mc).and.eq(column.mcjp, entity.mcjp).and.eq(column.xh, entity.xh).and.eq(column.bz, entity.bz).and.eq(column.sjxh, entity.sjxh) }.update.apply()
  }

}
