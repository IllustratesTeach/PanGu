package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisFingerStatisticsReport(
  pkId: String,
  departCode: Option[String] = None,
  departName: Option[String] = None,
  casecount: Option[Int] = None,
  fingercount: Option[Int] = None,
  casepastcount: Option[Int] = None,
  fingerpastcount: Option[Int] = None,
  casecurrentcount: Option[Int] = None,
  fingercurrentcount: Option[Int] = None,
  tlcount: Option[Int] = None,
  ltcount: Option[Int] = None,
  llcount: Option[Int] = None,
  manualmatchcount: Option[Int] = None,
  tlcountassist: Option[Int] = None,
  ltcountassist: Option[Int] = None,
  llcountassist: Option[Int] = None,
  manualmatchcountassist: Option[Int] = None,
  assista: Option[Int] = None,
  assistb: Option[Int] = None,
  assistb2: Option[Int] = None,
  assistc: Option[Int] = None,
  inputtime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisFingerStatisticsReport.autoSession): GafisFingerStatisticsReport = GafisFingerStatisticsReport.save(this)(session)

  def destroy()(implicit session: DBSession = GafisFingerStatisticsReport.autoSession): Unit = GafisFingerStatisticsReport.destroy(this)(session)

}


object GafisFingerStatisticsReport extends SQLSyntaxSupport[GafisFingerStatisticsReport] {

  override val tableName = "GAFIS_FINGER_STATISTICS_REPORT"

  override val columns = Seq("PK_ID", "DEPART_CODE", "DEPART_NAME", "CASECOUNT", "FINGERCOUNT", "CASEPASTCOUNT", "FINGERPASTCOUNT", "CASECURRENTCOUNT", "FINGERCURRENTCOUNT", "TLCOUNT", "LTCOUNT", "LLCOUNT", "MANUALMATCHCOUNT", "TLCOUNTASSIST", "LTCOUNTASSIST", "LLCOUNTASSIST", "MANUALMATCHCOUNTASSIST", "ASSISTA", "ASSISTB", "ASSISTB2", "ASSISTC", "INPUTTIME")

  def apply(gfsr: SyntaxProvider[GafisFingerStatisticsReport])(rs: WrappedResultSet): GafisFingerStatisticsReport = apply(gfsr.resultName)(rs)
  def apply(gfsr: ResultName[GafisFingerStatisticsReport])(rs: WrappedResultSet): GafisFingerStatisticsReport = new GafisFingerStatisticsReport(
    pkId = rs.get(gfsr.pkId),
    departCode = rs.get(gfsr.departCode),
    departName = rs.get(gfsr.departName),
    casecount = rs.get(gfsr.casecount),
    fingercount = rs.get(gfsr.fingercount),
    casepastcount = rs.get(gfsr.casepastcount),
    fingerpastcount = rs.get(gfsr.fingerpastcount),
    casecurrentcount = rs.get(gfsr.casecurrentcount),
    fingercurrentcount = rs.get(gfsr.fingercurrentcount),
    tlcount = rs.get(gfsr.tlcount),
    ltcount = rs.get(gfsr.ltcount),
    llcount = rs.get(gfsr.llcount),
    manualmatchcount = rs.get(gfsr.manualmatchcount),
    tlcountassist = rs.get(gfsr.tlcountassist),
    ltcountassist = rs.get(gfsr.ltcountassist),
    llcountassist = rs.get(gfsr.llcountassist),
    manualmatchcountassist = rs.get(gfsr.manualmatchcountassist),
    assista = rs.get(gfsr.assista),
    assistb = rs.get(gfsr.assistb),
    assistb2 = rs.get(gfsr.assistb2),
    assistc = rs.get(gfsr.assistc),
    inputtime = rs.get(gfsr.inputtime)
  )

  val gfsr = GafisFingerStatisticsReport.syntax("gfsr")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, departCode: Option[String], departName: Option[String], casecount: Option[Int], fingercount: Option[Int], casepastcount: Option[Int], fingerpastcount: Option[Int], casecurrentcount: Option[Int], fingercurrentcount: Option[Int], tlcount: Option[Int], ltcount: Option[Int], llcount: Option[Int], manualmatchcount: Option[Int], tlcountassist: Option[Int], ltcountassist: Option[Int], llcountassist: Option[Int], manualmatchcountassist: Option[Int], assista: Option[Int], assistb: Option[Int], assistb2: Option[Int], assistc: Option[Int], inputtime: Option[DateTime])(implicit session: DBSession = autoSession): Option[GafisFingerStatisticsReport] = {
    withSQL {
      select.from(GafisFingerStatisticsReport as gfsr).where.eq(gfsr.pkId, pkId).and.eq(gfsr.departCode, departCode).and.eq(gfsr.departName, departName).and.eq(gfsr.casecount, casecount).and.eq(gfsr.fingercount, fingercount).and.eq(gfsr.casepastcount, casepastcount).and.eq(gfsr.fingerpastcount, fingerpastcount).and.eq(gfsr.casecurrentcount, casecurrentcount).and.eq(gfsr.fingercurrentcount, fingercurrentcount).and.eq(gfsr.tlcount, tlcount).and.eq(gfsr.ltcount, ltcount).and.eq(gfsr.llcount, llcount).and.eq(gfsr.manualmatchcount, manualmatchcount).and.eq(gfsr.tlcountassist, tlcountassist).and.eq(gfsr.ltcountassist, ltcountassist).and.eq(gfsr.llcountassist, llcountassist).and.eq(gfsr.manualmatchcountassist, manualmatchcountassist).and.eq(gfsr.assista, assista).and.eq(gfsr.assistb, assistb).and.eq(gfsr.assistb2, assistb2).and.eq(gfsr.assistc, assistc).and.eq(gfsr.inputtime, inputtime)
    }.map(GafisFingerStatisticsReport(gfsr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisFingerStatisticsReport] = {
    withSQL(select.from(GafisFingerStatisticsReport as gfsr)).map(GafisFingerStatisticsReport(gfsr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisFingerStatisticsReport as gfsr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisFingerStatisticsReport] = {
    withSQL {
      select.from(GafisFingerStatisticsReport as gfsr).where.append(where)
    }.map(GafisFingerStatisticsReport(gfsr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisFingerStatisticsReport] = {
    withSQL {
      select.from(GafisFingerStatisticsReport as gfsr).where.append(where)
    }.map(GafisFingerStatisticsReport(gfsr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisFingerStatisticsReport as gfsr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    departCode: Option[String] = None,
    departName: Option[String] = None,
    casecount: Option[Int] = None,
    fingercount: Option[Int] = None,
    casepastcount: Option[Int] = None,
    fingerpastcount: Option[Int] = None,
    casecurrentcount: Option[Int] = None,
    fingercurrentcount: Option[Int] = None,
    tlcount: Option[Int] = None,
    ltcount: Option[Int] = None,
    llcount: Option[Int] = None,
    manualmatchcount: Option[Int] = None,
    tlcountassist: Option[Int] = None,
    ltcountassist: Option[Int] = None,
    llcountassist: Option[Int] = None,
    manualmatchcountassist: Option[Int] = None,
    assista: Option[Int] = None,
    assistb: Option[Int] = None,
    assistb2: Option[Int] = None,
    assistc: Option[Int] = None,
    inputtime: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisFingerStatisticsReport = {
    withSQL {
      insert.into(GafisFingerStatisticsReport).columns(
        column.pkId,
        column.departCode,
        column.departName,
        column.casecount,
        column.fingercount,
        column.casepastcount,
        column.fingerpastcount,
        column.casecurrentcount,
        column.fingercurrentcount,
        column.tlcount,
        column.ltcount,
        column.llcount,
        column.manualmatchcount,
        column.tlcountassist,
        column.ltcountassist,
        column.llcountassist,
        column.manualmatchcountassist,
        column.assista,
        column.assistb,
        column.assistb2,
        column.assistc,
        column.inputtime
      ).values(
        pkId,
        departCode,
        departName,
        casecount,
        fingercount,
        casepastcount,
        fingerpastcount,
        casecurrentcount,
        fingercurrentcount,
        tlcount,
        ltcount,
        llcount,
        manualmatchcount,
        tlcountassist,
        ltcountassist,
        llcountassist,
        manualmatchcountassist,
        assista,
        assistb,
        assistb2,
        assistc,
        inputtime
      )
    }.update.apply()

    GafisFingerStatisticsReport(
      pkId = pkId,
      departCode = departCode,
      departName = departName,
      casecount = casecount,
      fingercount = fingercount,
      casepastcount = casepastcount,
      fingerpastcount = fingerpastcount,
      casecurrentcount = casecurrentcount,
      fingercurrentcount = fingercurrentcount,
      tlcount = tlcount,
      ltcount = ltcount,
      llcount = llcount,
      manualmatchcount = manualmatchcount,
      tlcountassist = tlcountassist,
      ltcountassist = ltcountassist,
      llcountassist = llcountassist,
      manualmatchcountassist = manualmatchcountassist,
      assista = assista,
      assistb = assistb,
      assistb2 = assistb2,
      assistc = assistc,
      inputtime = inputtime)
  }

  def save(entity: GafisFingerStatisticsReport)(implicit session: DBSession = autoSession): GafisFingerStatisticsReport = {
    withSQL {
      update(GafisFingerStatisticsReport).set(
        column.pkId -> entity.pkId,
        column.departCode -> entity.departCode,
        column.departName -> entity.departName,
        column.casecount -> entity.casecount,
        column.fingercount -> entity.fingercount,
        column.casepastcount -> entity.casepastcount,
        column.fingerpastcount -> entity.fingerpastcount,
        column.casecurrentcount -> entity.casecurrentcount,
        column.fingercurrentcount -> entity.fingercurrentcount,
        column.tlcount -> entity.tlcount,
        column.ltcount -> entity.ltcount,
        column.llcount -> entity.llcount,
        column.manualmatchcount -> entity.manualmatchcount,
        column.tlcountassist -> entity.tlcountassist,
        column.ltcountassist -> entity.ltcountassist,
        column.llcountassist -> entity.llcountassist,
        column.manualmatchcountassist -> entity.manualmatchcountassist,
        column.assista -> entity.assista,
        column.assistb -> entity.assistb,
        column.assistb2 -> entity.assistb2,
        column.assistc -> entity.assistc,
        column.inputtime -> entity.inputtime
      ).where.eq(column.pkId, entity.pkId).and.eq(column.departCode, entity.departCode).and.eq(column.departName, entity.departName).and.eq(column.casecount, entity.casecount).and.eq(column.fingercount, entity.fingercount).and.eq(column.casepastcount, entity.casepastcount).and.eq(column.fingerpastcount, entity.fingerpastcount).and.eq(column.casecurrentcount, entity.casecurrentcount).and.eq(column.fingercurrentcount, entity.fingercurrentcount).and.eq(column.tlcount, entity.tlcount).and.eq(column.ltcount, entity.ltcount).and.eq(column.llcount, entity.llcount).and.eq(column.manualmatchcount, entity.manualmatchcount).and.eq(column.tlcountassist, entity.tlcountassist).and.eq(column.ltcountassist, entity.ltcountassist).and.eq(column.llcountassist, entity.llcountassist).and.eq(column.manualmatchcountassist, entity.manualmatchcountassist).and.eq(column.assista, entity.assista).and.eq(column.assistb, entity.assistb).and.eq(column.assistb2, entity.assistb2).and.eq(column.assistc, entity.assistc).and.eq(column.inputtime, entity.inputtime)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisFingerStatisticsReport)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisFingerStatisticsReport).where.eq(column.pkId, entity.pkId).and.eq(column.departCode, entity.departCode).and.eq(column.departName, entity.departName).and.eq(column.casecount, entity.casecount).and.eq(column.fingercount, entity.fingercount).and.eq(column.casepastcount, entity.casepastcount).and.eq(column.fingerpastcount, entity.fingerpastcount).and.eq(column.casecurrentcount, entity.casecurrentcount).and.eq(column.fingercurrentcount, entity.fingercurrentcount).and.eq(column.tlcount, entity.tlcount).and.eq(column.ltcount, entity.ltcount).and.eq(column.llcount, entity.llcount).and.eq(column.manualmatchcount, entity.manualmatchcount).and.eq(column.tlcountassist, entity.tlcountassist).and.eq(column.ltcountassist, entity.ltcountassist).and.eq(column.llcountassist, entity.llcountassist).and.eq(column.manualmatchcountassist, entity.manualmatchcountassist).and.eq(column.assista, entity.assista).and.eq(column.assistb, entity.assistb).and.eq(column.assistb2, entity.assistb2).and.eq(column.assistc, entity.assistc).and.eq(column.inputtime, entity.inputtime) }.update.apply()
  }

}
