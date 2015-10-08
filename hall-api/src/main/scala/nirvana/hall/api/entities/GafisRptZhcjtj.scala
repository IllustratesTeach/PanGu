package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisRptZhcjtj(
  gatherorgcode: Option[String] = None,
  gatherdate: Option[DateTime] = None,
  total: Option[Int] = None,
  requiregathernumber: Option[Int] = None,
  shouldgathernumber: Option[Int] = None,
  maygathernumber: Option[Int] = None,
  fingergathernumber: Option[Int] = None,
  portraitgathernumber: Option[Int] = None,
  dnagathernumber: Option[Int] = None,
  phonegathernumber: Option[Int] = None,
  goodsgathernumber: Option[Int] = None,
  handwritinggathernumber: Option[Int] = None,
  palmgathernumber: Option[Int] = None,
  urinegathernumber: Option[Int] = None) {

  def save()(implicit session: DBSession = GafisRptZhcjtj.autoSession): GafisRptZhcjtj = GafisRptZhcjtj.save(this)(session)

  def destroy()(implicit session: DBSession = GafisRptZhcjtj.autoSession): Unit = GafisRptZhcjtj.destroy(this)(session)

}


object GafisRptZhcjtj extends SQLSyntaxSupport[GafisRptZhcjtj] {

  override val tableName = "GAFIS_RPT_ZHCJTJ"

  override val columns = Seq("GATHERORGCODE", "GATHERDATE", "TOTAL", "REQUIREGATHERNUMBER", "SHOULDGATHERNUMBER", "MAYGATHERNUMBER", "FINGERGATHERNUMBER", "PORTRAITGATHERNUMBER", "DNAGATHERNUMBER", "PHONEGATHERNUMBER", "GOODSGATHERNUMBER", "HANDWRITINGGATHERNUMBER", "PALMGATHERNUMBER", "URINEGATHERNUMBER")

  def apply(grz: SyntaxProvider[GafisRptZhcjtj])(rs: WrappedResultSet): GafisRptZhcjtj = apply(grz.resultName)(rs)
  def apply(grz: ResultName[GafisRptZhcjtj])(rs: WrappedResultSet): GafisRptZhcjtj = new GafisRptZhcjtj(
    gatherorgcode = rs.get(grz.gatherorgcode),
    gatherdate = rs.get(grz.gatherdate),
    total = rs.get(grz.total),
    requiregathernumber = rs.get(grz.requiregathernumber),
    shouldgathernumber = rs.get(grz.shouldgathernumber),
    maygathernumber = rs.get(grz.maygathernumber),
    fingergathernumber = rs.get(grz.fingergathernumber),
    portraitgathernumber = rs.get(grz.portraitgathernumber),
    dnagathernumber = rs.get(grz.dnagathernumber),
    phonegathernumber = rs.get(grz.phonegathernumber),
    goodsgathernumber = rs.get(grz.goodsgathernumber),
    handwritinggathernumber = rs.get(grz.handwritinggathernumber),
    palmgathernumber = rs.get(grz.palmgathernumber),
    urinegathernumber = rs.get(grz.urinegathernumber)
  )

  val grz = GafisRptZhcjtj.syntax("grz")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(gatherorgcode: Option[String], gatherdate: Option[DateTime], total: Option[Int], requiregathernumber: Option[Int], shouldgathernumber: Option[Int], maygathernumber: Option[Int], fingergathernumber: Option[Int], portraitgathernumber: Option[Int], dnagathernumber: Option[Int], phonegathernumber: Option[Int], goodsgathernumber: Option[Int], handwritinggathernumber: Option[Int], palmgathernumber: Option[Int], urinegathernumber: Option[Int])(implicit session: DBSession = autoSession): Option[GafisRptZhcjtj] = {
    withSQL {
      select.from(GafisRptZhcjtj as grz).where.eq(grz.gatherorgcode, gatherorgcode).and.eq(grz.gatherdate, gatherdate).and.eq(grz.total, total).and.eq(grz.requiregathernumber, requiregathernumber).and.eq(grz.shouldgathernumber, shouldgathernumber).and.eq(grz.maygathernumber, maygathernumber).and.eq(grz.fingergathernumber, fingergathernumber).and.eq(grz.portraitgathernumber, portraitgathernumber).and.eq(grz.dnagathernumber, dnagathernumber).and.eq(grz.phonegathernumber, phonegathernumber).and.eq(grz.goodsgathernumber, goodsgathernumber).and.eq(grz.handwritinggathernumber, handwritinggathernumber).and.eq(grz.palmgathernumber, palmgathernumber).and.eq(grz.urinegathernumber, urinegathernumber)
    }.map(GafisRptZhcjtj(grz.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisRptZhcjtj] = {
    withSQL(select.from(GafisRptZhcjtj as grz)).map(GafisRptZhcjtj(grz.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisRptZhcjtj as grz)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisRptZhcjtj] = {
    withSQL {
      select.from(GafisRptZhcjtj as grz).where.append(where)
    }.map(GafisRptZhcjtj(grz.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisRptZhcjtj] = {
    withSQL {
      select.from(GafisRptZhcjtj as grz).where.append(where)
    }.map(GafisRptZhcjtj(grz.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisRptZhcjtj as grz).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    gatherorgcode: Option[String] = None,
    gatherdate: Option[DateTime] = None,
    total: Option[Int] = None,
    requiregathernumber: Option[Int] = None,
    shouldgathernumber: Option[Int] = None,
    maygathernumber: Option[Int] = None,
    fingergathernumber: Option[Int] = None,
    portraitgathernumber: Option[Int] = None,
    dnagathernumber: Option[Int] = None,
    phonegathernumber: Option[Int] = None,
    goodsgathernumber: Option[Int] = None,
    handwritinggathernumber: Option[Int] = None,
    palmgathernumber: Option[Int] = None,
    urinegathernumber: Option[Int] = None)(implicit session: DBSession = autoSession): GafisRptZhcjtj = {
    withSQL {
      insert.into(GafisRptZhcjtj).columns(
        column.gatherorgcode,
        column.gatherdate,
        column.total,
        column.requiregathernumber,
        column.shouldgathernumber,
        column.maygathernumber,
        column.fingergathernumber,
        column.portraitgathernumber,
        column.dnagathernumber,
        column.phonegathernumber,
        column.goodsgathernumber,
        column.handwritinggathernumber,
        column.palmgathernumber,
        column.urinegathernumber
      ).values(
        gatherorgcode,
        gatherdate,
        total,
        requiregathernumber,
        shouldgathernumber,
        maygathernumber,
        fingergathernumber,
        portraitgathernumber,
        dnagathernumber,
        phonegathernumber,
        goodsgathernumber,
        handwritinggathernumber,
        palmgathernumber,
        urinegathernumber
      )
    }.update.apply()

    GafisRptZhcjtj(
      gatherorgcode = gatherorgcode,
      gatherdate = gatherdate,
      total = total,
      requiregathernumber = requiregathernumber,
      shouldgathernumber = shouldgathernumber,
      maygathernumber = maygathernumber,
      fingergathernumber = fingergathernumber,
      portraitgathernumber = portraitgathernumber,
      dnagathernumber = dnagathernumber,
      phonegathernumber = phonegathernumber,
      goodsgathernumber = goodsgathernumber,
      handwritinggathernumber = handwritinggathernumber,
      palmgathernumber = palmgathernumber,
      urinegathernumber = urinegathernumber)
  }

  def save(entity: GafisRptZhcjtj)(implicit session: DBSession = autoSession): GafisRptZhcjtj = {
    withSQL {
      update(GafisRptZhcjtj).set(
        column.gatherorgcode -> entity.gatherorgcode,
        column.gatherdate -> entity.gatherdate,
        column.total -> entity.total,
        column.requiregathernumber -> entity.requiregathernumber,
        column.shouldgathernumber -> entity.shouldgathernumber,
        column.maygathernumber -> entity.maygathernumber,
        column.fingergathernumber -> entity.fingergathernumber,
        column.portraitgathernumber -> entity.portraitgathernumber,
        column.dnagathernumber -> entity.dnagathernumber,
        column.phonegathernumber -> entity.phonegathernumber,
        column.goodsgathernumber -> entity.goodsgathernumber,
        column.handwritinggathernumber -> entity.handwritinggathernumber,
        column.palmgathernumber -> entity.palmgathernumber,
        column.urinegathernumber -> entity.urinegathernumber
      ).where.eq(column.gatherorgcode, entity.gatherorgcode).and.eq(column.gatherdate, entity.gatherdate).and.eq(column.total, entity.total).and.eq(column.requiregathernumber, entity.requiregathernumber).and.eq(column.shouldgathernumber, entity.shouldgathernumber).and.eq(column.maygathernumber, entity.maygathernumber).and.eq(column.fingergathernumber, entity.fingergathernumber).and.eq(column.portraitgathernumber, entity.portraitgathernumber).and.eq(column.dnagathernumber, entity.dnagathernumber).and.eq(column.phonegathernumber, entity.phonegathernumber).and.eq(column.goodsgathernumber, entity.goodsgathernumber).and.eq(column.handwritinggathernumber, entity.handwritinggathernumber).and.eq(column.palmgathernumber, entity.palmgathernumber).and.eq(column.urinegathernumber, entity.urinegathernumber)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisRptZhcjtj)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisRptZhcjtj).where.eq(column.gatherorgcode, entity.gatherorgcode).and.eq(column.gatherdate, entity.gatherdate).and.eq(column.total, entity.total).and.eq(column.requiregathernumber, entity.requiregathernumber).and.eq(column.shouldgathernumber, entity.shouldgathernumber).and.eq(column.maygathernumber, entity.maygathernumber).and.eq(column.fingergathernumber, entity.fingergathernumber).and.eq(column.portraitgathernumber, entity.portraitgathernumber).and.eq(column.dnagathernumber, entity.dnagathernumber).and.eq(column.phonegathernumber, entity.phonegathernumber).and.eq(column.goodsgathernumber, entity.goodsgathernumber).and.eq(column.handwritinggathernumber, entity.handwritinggathernumber).and.eq(column.palmgathernumber, entity.palmgathernumber).and.eq(column.urinegathernumber, entity.urinegathernumber) }.update.apply()
  }

}
