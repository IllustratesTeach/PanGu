package nirvana.hall.api.entities

import scalikejdbc._

case class DzdaCodeGzbx(
  lxbh: Option[String] = None,
  lxmc: Option[String] = None,
  mc: Option[String] = None,
  mcjp: Option[String] = None,
  xh: String,
  bz: Option[String] = None,
  sjxh: Option[String] = None) {

  def save()(implicit session: DBSession = DzdaCodeGzbx.autoSession): DzdaCodeGzbx = DzdaCodeGzbx.save(this)(session)

  def destroy()(implicit session: DBSession = DzdaCodeGzbx.autoSession): Unit = DzdaCodeGzbx.destroy(this)(session)

}


object DzdaCodeGzbx extends SQLSyntaxSupport[DzdaCodeGzbx] {

  override val tableName = "DZDA_CODE_GZBX"

  override val columns = Seq("LXBH", "LXMC", "MC", "MCJP", "XH", "BZ", "SJXH")

  def apply(dcg: SyntaxProvider[DzdaCodeGzbx])(rs: WrappedResultSet): DzdaCodeGzbx = apply(dcg.resultName)(rs)
  def apply(dcg: ResultName[DzdaCodeGzbx])(rs: WrappedResultSet): DzdaCodeGzbx = new DzdaCodeGzbx(
    lxbh = rs.get(dcg.lxbh),
    lxmc = rs.get(dcg.lxmc),
    mc = rs.get(dcg.mc),
    mcjp = rs.get(dcg.mcjp),
    xh = rs.get(dcg.xh),
    bz = rs.get(dcg.bz),
    sjxh = rs.get(dcg.sjxh)
  )

  val dcg = DzdaCodeGzbx.syntax("dcg")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(lxbh: Option[String], lxmc: Option[String], mc: Option[String], mcjp: Option[String], xh: String, bz: Option[String], sjxh: Option[String])(implicit session: DBSession = autoSession): Option[DzdaCodeGzbx] = {
    withSQL {
      select.from(DzdaCodeGzbx as dcg).where.eq(dcg.lxbh, lxbh).and.eq(dcg.lxmc, lxmc).and.eq(dcg.mc, mc).and.eq(dcg.mcjp, mcjp).and.eq(dcg.xh, xh).and.eq(dcg.bz, bz).and.eq(dcg.sjxh, sjxh)
    }.map(DzdaCodeGzbx(dcg.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DzdaCodeGzbx] = {
    withSQL(select.from(DzdaCodeGzbx as dcg)).map(DzdaCodeGzbx(dcg.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DzdaCodeGzbx as dcg)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DzdaCodeGzbx] = {
    withSQL {
      select.from(DzdaCodeGzbx as dcg).where.append(where)
    }.map(DzdaCodeGzbx(dcg.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DzdaCodeGzbx] = {
    withSQL {
      select.from(DzdaCodeGzbx as dcg).where.append(where)
    }.map(DzdaCodeGzbx(dcg.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DzdaCodeGzbx as dcg).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    lxbh: Option[String] = None,
    lxmc: Option[String] = None,
    mc: Option[String] = None,
    mcjp: Option[String] = None,
    xh: String,
    bz: Option[String] = None,
    sjxh: Option[String] = None)(implicit session: DBSession = autoSession): DzdaCodeGzbx = {
    withSQL {
      insert.into(DzdaCodeGzbx).columns(
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

    DzdaCodeGzbx(
      lxbh = lxbh,
      lxmc = lxmc,
      mc = mc,
      mcjp = mcjp,
      xh = xh,
      bz = bz,
      sjxh = sjxh)
  }

  def save(entity: DzdaCodeGzbx)(implicit session: DBSession = autoSession): DzdaCodeGzbx = {
    withSQL {
      update(DzdaCodeGzbx).set(
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

  def destroy(entity: DzdaCodeGzbx)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(DzdaCodeGzbx).where.eq(column.lxbh, entity.lxbh).and.eq(column.lxmc, entity.lxmc).and.eq(column.mc, entity.mc).and.eq(column.mcjp, entity.mcjp).and.eq(column.xh, entity.xh).and.eq(column.bz, entity.bz).and.eq(column.sjxh, entity.sjxh) }.update.apply()
  }

}
