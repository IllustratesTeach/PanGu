package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysReportPersonOperation(
  dcode: Option[String] = None,
  dname: Option[String] = None,
  parentid: Option[String] = None,
  isleaf: Option[String] = None,
  inputtime: Option[DateTime] = None,
  bccount: Option[Long] = None,
  xccount: Option[Long] = None,
  zwcount: Option[Long] = None,
  fptcount: Option[Long] = None) {

  def save()(implicit session: DBSession = SysReportPersonOperation.autoSession): SysReportPersonOperation = SysReportPersonOperation.save(this)(session)

  def destroy()(implicit session: DBSession = SysReportPersonOperation.autoSession): Unit = SysReportPersonOperation.destroy(this)(session)

}


object SysReportPersonOperation extends SQLSyntaxSupport[SysReportPersonOperation] {

  override val tableName = "SYS_REPORT_PERSON_OPERATION"

  override val columns = Seq("DCODE", "DNAME", "PARENTID", "ISLEAF", "INPUTTIME", "BCCOUNT", "XCCOUNT", "ZWCOUNT", "FPTCOUNT")

  def apply(srpo: SyntaxProvider[SysReportPersonOperation])(rs: WrappedResultSet): SysReportPersonOperation = apply(srpo.resultName)(rs)
  def apply(srpo: ResultName[SysReportPersonOperation])(rs: WrappedResultSet): SysReportPersonOperation = new SysReportPersonOperation(
    dcode = rs.get(srpo.dcode),
    dname = rs.get(srpo.dname),
    parentid = rs.get(srpo.parentid),
    isleaf = rs.get(srpo.isleaf),
    inputtime = rs.get(srpo.inputtime),
    bccount = rs.get(srpo.bccount),
    xccount = rs.get(srpo.xccount),
    zwcount = rs.get(srpo.zwcount),
    fptcount = rs.get(srpo.fptcount)
  )

  val srpo = SysReportPersonOperation.syntax("srpo")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(dcode: Option[String], dname: Option[String], parentid: Option[String], isleaf: Option[String], inputtime: Option[DateTime], bccount: Option[Long], xccount: Option[Long], zwcount: Option[Long], fptcount: Option[Long])(implicit session: DBSession = autoSession): Option[SysReportPersonOperation] = {
    withSQL {
      select.from(SysReportPersonOperation as srpo).where.eq(srpo.dcode, dcode).and.eq(srpo.dname, dname).and.eq(srpo.parentid, parentid).and.eq(srpo.isleaf, isleaf).and.eq(srpo.inputtime, inputtime).and.eq(srpo.bccount, bccount).and.eq(srpo.xccount, xccount).and.eq(srpo.zwcount, zwcount).and.eq(srpo.fptcount, fptcount)
    }.map(SysReportPersonOperation(srpo.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysReportPersonOperation] = {
    withSQL(select.from(SysReportPersonOperation as srpo)).map(SysReportPersonOperation(srpo.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysReportPersonOperation as srpo)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysReportPersonOperation] = {
    withSQL {
      select.from(SysReportPersonOperation as srpo).where.append(where)
    }.map(SysReportPersonOperation(srpo.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysReportPersonOperation] = {
    withSQL {
      select.from(SysReportPersonOperation as srpo).where.append(where)
    }.map(SysReportPersonOperation(srpo.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysReportPersonOperation as srpo).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dcode: Option[String] = None,
    dname: Option[String] = None,
    parentid: Option[String] = None,
    isleaf: Option[String] = None,
    inputtime: Option[DateTime] = None,
    bccount: Option[Long] = None,
    xccount: Option[Long] = None,
    zwcount: Option[Long] = None,
    fptcount: Option[Long] = None)(implicit session: DBSession = autoSession): SysReportPersonOperation = {
    withSQL {
      insert.into(SysReportPersonOperation).columns(
        column.dcode,
        column.dname,
        column.parentid,
        column.isleaf,
        column.inputtime,
        column.bccount,
        column.xccount,
        column.zwcount,
        column.fptcount
      ).values(
        dcode,
        dname,
        parentid,
        isleaf,
        inputtime,
        bccount,
        xccount,
        zwcount,
        fptcount
      )
    }.update.apply()

    SysReportPersonOperation(
      dcode = dcode,
      dname = dname,
      parentid = parentid,
      isleaf = isleaf,
      inputtime = inputtime,
      bccount = bccount,
      xccount = xccount,
      zwcount = zwcount,
      fptcount = fptcount)
  }

  def save(entity: SysReportPersonOperation)(implicit session: DBSession = autoSession): SysReportPersonOperation = {
    withSQL {
      update(SysReportPersonOperation).set(
        column.dcode -> entity.dcode,
        column.dname -> entity.dname,
        column.parentid -> entity.parentid,
        column.isleaf -> entity.isleaf,
        column.inputtime -> entity.inputtime,
        column.bccount -> entity.bccount,
        column.xccount -> entity.xccount,
        column.zwcount -> entity.zwcount,
        column.fptcount -> entity.fptcount
      ).where.eq(column.dcode, entity.dcode).and.eq(column.dname, entity.dname).and.eq(column.parentid, entity.parentid).and.eq(column.isleaf, entity.isleaf).and.eq(column.inputtime, entity.inputtime).and.eq(column.bccount, entity.bccount).and.eq(column.xccount, entity.xccount).and.eq(column.zwcount, entity.zwcount).and.eq(column.fptcount, entity.fptcount)
    }.update.apply()
    entity
  }

  def destroy(entity: SysReportPersonOperation)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysReportPersonOperation).where.eq(column.dcode, entity.dcode).and.eq(column.dname, entity.dname).and.eq(column.parentid, entity.parentid).and.eq(column.isleaf, entity.isleaf).and.eq(column.inputtime, entity.inputtime).and.eq(column.bccount, entity.bccount).and.eq(column.xccount, entity.xccount).and.eq(column.zwcount, entity.zwcount).and.eq(column.fptcount, entity.fptcount) }.update.apply()
  }

}
