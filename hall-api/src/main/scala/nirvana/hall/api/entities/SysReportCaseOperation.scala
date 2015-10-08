package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysReportCaseOperation(
  dcode: Option[String] = None,
  dname: Option[String] = None,
  parentid: Option[String] = None,
  isleaf: Option[String] = None,
  occurdate: Option[DateTime] = None,
  casecount: Option[Long] = None,
  casecheckedcount: Option[Long] = None,
  caseuncheckedcount: Option[Long] = None,
  assista: Option[Long] = None,
  assistb2: Option[Long] = None,
  assistb: Option[Long] = None,
  assistc: Option[Long] = None,
  assistaquery: Option[Long] = None,
  assistb2query: Option[Long] = None,
  assistbquery: Option[Long] = None,
  assistcquery: Option[Long] = None) {

  def save()(implicit session: DBSession = SysReportCaseOperation.autoSession): SysReportCaseOperation = SysReportCaseOperation.save(this)(session)

  def destroy()(implicit session: DBSession = SysReportCaseOperation.autoSession): Unit = SysReportCaseOperation.destroy(this)(session)

}


object SysReportCaseOperation extends SQLSyntaxSupport[SysReportCaseOperation] {

  override val tableName = "SYS_REPORT_CASE_OPERATION"

  override val columns = Seq("DCODE", "DNAME", "PARENTID", "ISLEAF", "OCCURDATE", "CASECOUNT", "CASECHECKEDCOUNT", "CASEUNCHECKEDCOUNT", "ASSISTA", "ASSISTB2", "ASSISTB", "ASSISTC", "ASSISTAQUERY", "ASSISTB2QUERY", "ASSISTBQUERY", "ASSISTCQUERY")

  def apply(srco: SyntaxProvider[SysReportCaseOperation])(rs: WrappedResultSet): SysReportCaseOperation = apply(srco.resultName)(rs)
  def apply(srco: ResultName[SysReportCaseOperation])(rs: WrappedResultSet): SysReportCaseOperation = new SysReportCaseOperation(
    dcode = rs.get(srco.dcode),
    dname = rs.get(srco.dname),
    parentid = rs.get(srco.parentid),
    isleaf = rs.get(srco.isleaf),
    occurdate = rs.get(srco.occurdate),
    casecount = rs.get(srco.casecount),
    casecheckedcount = rs.get(srco.casecheckedcount),
    caseuncheckedcount = rs.get(srco.caseuncheckedcount),
    assista = rs.get(srco.assista),
    assistb2 = rs.get(srco.assistb2),
    assistb = rs.get(srco.assistb),
    assistc = rs.get(srco.assistc),
    assistaquery = rs.get(srco.assistaquery),
    assistb2query = rs.get(srco.assistb2query),
    assistbquery = rs.get(srco.assistbquery),
    assistcquery = rs.get(srco.assistcquery)
  )

  val srco = SysReportCaseOperation.syntax("srco")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(dcode: Option[String], dname: Option[String], parentid: Option[String], isleaf: Option[String], occurdate: Option[DateTime], casecount: Option[Long], casecheckedcount: Option[Long], caseuncheckedcount: Option[Long], assista: Option[Long], assistb2: Option[Long], assistb: Option[Long], assistc: Option[Long], assistaquery: Option[Long], assistb2query: Option[Long], assistbquery: Option[Long], assistcquery: Option[Long])(implicit session: DBSession = autoSession): Option[SysReportCaseOperation] = {
    withSQL {
      select.from(SysReportCaseOperation as srco).where.eq(srco.dcode, dcode).and.eq(srco.dname, dname).and.eq(srco.parentid, parentid).and.eq(srco.isleaf, isleaf).and.eq(srco.occurdate, occurdate).and.eq(srco.casecount, casecount).and.eq(srco.casecheckedcount, casecheckedcount).and.eq(srco.caseuncheckedcount, caseuncheckedcount).and.eq(srco.assista, assista).and.eq(srco.assistb2, assistb2).and.eq(srco.assistb, assistb).and.eq(srco.assistc, assistc).and.eq(srco.assistaquery, assistaquery).and.eq(srco.assistb2query, assistb2query).and.eq(srco.assistbquery, assistbquery).and.eq(srco.assistcquery, assistcquery)
    }.map(SysReportCaseOperation(srco.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysReportCaseOperation] = {
    withSQL(select.from(SysReportCaseOperation as srco)).map(SysReportCaseOperation(srco.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysReportCaseOperation as srco)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysReportCaseOperation] = {
    withSQL {
      select.from(SysReportCaseOperation as srco).where.append(where)
    }.map(SysReportCaseOperation(srco.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysReportCaseOperation] = {
    withSQL {
      select.from(SysReportCaseOperation as srco).where.append(where)
    }.map(SysReportCaseOperation(srco.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysReportCaseOperation as srco).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dcode: Option[String] = None,
    dname: Option[String] = None,
    parentid: Option[String] = None,
    isleaf: Option[String] = None,
    occurdate: Option[DateTime] = None,
    casecount: Option[Long] = None,
    casecheckedcount: Option[Long] = None,
    caseuncheckedcount: Option[Long] = None,
    assista: Option[Long] = None,
    assistb2: Option[Long] = None,
    assistb: Option[Long] = None,
    assistc: Option[Long] = None,
    assistaquery: Option[Long] = None,
    assistb2query: Option[Long] = None,
    assistbquery: Option[Long] = None,
    assistcquery: Option[Long] = None)(implicit session: DBSession = autoSession): SysReportCaseOperation = {
    withSQL {
      insert.into(SysReportCaseOperation).columns(
        column.dcode,
        column.dname,
        column.parentid,
        column.isleaf,
        column.occurdate,
        column.casecount,
        column.casecheckedcount,
        column.caseuncheckedcount,
        column.assista,
        column.assistb2,
        column.assistb,
        column.assistc,
        column.assistaquery,
        column.assistb2query,
        column.assistbquery,
        column.assistcquery
      ).values(
        dcode,
        dname,
        parentid,
        isleaf,
        occurdate,
        casecount,
        casecheckedcount,
        caseuncheckedcount,
        assista,
        assistb2,
        assistb,
        assistc,
        assistaquery,
        assistb2query,
        assistbquery,
        assistcquery
      )
    }.update.apply()

    SysReportCaseOperation(
      dcode = dcode,
      dname = dname,
      parentid = parentid,
      isleaf = isleaf,
      occurdate = occurdate,
      casecount = casecount,
      casecheckedcount = casecheckedcount,
      caseuncheckedcount = caseuncheckedcount,
      assista = assista,
      assistb2 = assistb2,
      assistb = assistb,
      assistc = assistc,
      assistaquery = assistaquery,
      assistb2query = assistb2query,
      assistbquery = assistbquery,
      assistcquery = assistcquery)
  }

  def save(entity: SysReportCaseOperation)(implicit session: DBSession = autoSession): SysReportCaseOperation = {
    withSQL {
      update(SysReportCaseOperation).set(
        column.dcode -> entity.dcode,
        column.dname -> entity.dname,
        column.parentid -> entity.parentid,
        column.isleaf -> entity.isleaf,
        column.occurdate -> entity.occurdate,
        column.casecount -> entity.casecount,
        column.casecheckedcount -> entity.casecheckedcount,
        column.caseuncheckedcount -> entity.caseuncheckedcount,
        column.assista -> entity.assista,
        column.assistb2 -> entity.assistb2,
        column.assistb -> entity.assistb,
        column.assistc -> entity.assistc,
        column.assistaquery -> entity.assistaquery,
        column.assistb2query -> entity.assistb2query,
        column.assistbquery -> entity.assistbquery,
        column.assistcquery -> entity.assistcquery
      ).where.eq(column.dcode, entity.dcode).and.eq(column.dname, entity.dname).and.eq(column.parentid, entity.parentid).and.eq(column.isleaf, entity.isleaf).and.eq(column.occurdate, entity.occurdate).and.eq(column.casecount, entity.casecount).and.eq(column.casecheckedcount, entity.casecheckedcount).and.eq(column.caseuncheckedcount, entity.caseuncheckedcount).and.eq(column.assista, entity.assista).and.eq(column.assistb2, entity.assistb2).and.eq(column.assistb, entity.assistb).and.eq(column.assistc, entity.assistc).and.eq(column.assistaquery, entity.assistaquery).and.eq(column.assistb2query, entity.assistb2query).and.eq(column.assistbquery, entity.assistbquery).and.eq(column.assistcquery, entity.assistcquery)
    }.update.apply()
    entity
  }

  def destroy(entity: SysReportCaseOperation)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysReportCaseOperation).where.eq(column.dcode, entity.dcode).and.eq(column.dname, entity.dname).and.eq(column.parentid, entity.parentid).and.eq(column.isleaf, entity.isleaf).and.eq(column.occurdate, entity.occurdate).and.eq(column.casecount, entity.casecount).and.eq(column.casecheckedcount, entity.casecheckedcount).and.eq(column.caseuncheckedcount, entity.caseuncheckedcount).and.eq(column.assista, entity.assista).and.eq(column.assistb2, entity.assistb2).and.eq(column.assistb, entity.assistb).and.eq(column.assistc, entity.assistc).and.eq(column.assistaquery, entity.assistaquery).and.eq(column.assistb2query, entity.assistb2query).and.eq(column.assistbquery, entity.assistbquery).and.eq(column.assistcquery, entity.assistcquery) }.update.apply()
  }

}
