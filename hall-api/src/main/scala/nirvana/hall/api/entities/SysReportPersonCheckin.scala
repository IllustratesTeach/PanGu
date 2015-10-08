package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysReportPersonCheckin(
  dcode: Option[String] = None,
  dname: Option[String] = None,
  parentid: Option[String] = None,
  confirmtime: Option[DateTime] = None,
  isleaf: Option[String] = None,
  gathersystemcount: Option[Long] = None,
  fingersystemcount: Option[Long] = None,
  assista: Option[Long] = None,
  assistb2: Option[Long] = None,
  assistb: Option[Long] = None,
  assistc: Option[Long] = None,
  wsxzcount: Option[Long] = None) {

  def save()(implicit session: DBSession = SysReportPersonCheckin.autoSession): SysReportPersonCheckin = SysReportPersonCheckin.save(this)(session)

  def destroy()(implicit session: DBSession = SysReportPersonCheckin.autoSession): Unit = SysReportPersonCheckin.destroy(this)(session)

}


object SysReportPersonCheckin extends SQLSyntaxSupport[SysReportPersonCheckin] {

  override val tableName = "SYS_REPORT_PERSON_CHECKIN"

  override val columns = Seq("DCODE", "DNAME", "PARENTID", "CONFIRMTIME", "ISLEAF", "GATHERSYSTEMCOUNT", "FINGERSYSTEMCOUNT", "ASSISTA", "ASSISTB2", "ASSISTB", "ASSISTC", "WSXZCOUNT")

  def apply(srpc: SyntaxProvider[SysReportPersonCheckin])(rs: WrappedResultSet): SysReportPersonCheckin = apply(srpc.resultName)(rs)
  def apply(srpc: ResultName[SysReportPersonCheckin])(rs: WrappedResultSet): SysReportPersonCheckin = new SysReportPersonCheckin(
    dcode = rs.get(srpc.dcode),
    dname = rs.get(srpc.dname),
    parentid = rs.get(srpc.parentid),
    confirmtime = rs.get(srpc.confirmtime),
    isleaf = rs.get(srpc.isleaf),
    gathersystemcount = rs.get(srpc.gathersystemcount),
    fingersystemcount = rs.get(srpc.fingersystemcount),
    assista = rs.get(srpc.assista),
    assistb2 = rs.get(srpc.assistb2),
    assistb = rs.get(srpc.assistb),
    assistc = rs.get(srpc.assistc),
    wsxzcount = rs.get(srpc.wsxzcount)
  )

  val srpc = SysReportPersonCheckin.syntax("srpc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(dcode: Option[String], dname: Option[String], parentid: Option[String], confirmtime: Option[DateTime], isleaf: Option[String], gathersystemcount: Option[Long], fingersystemcount: Option[Long], assista: Option[Long], assistb2: Option[Long], assistb: Option[Long], assistc: Option[Long], wsxzcount: Option[Long])(implicit session: DBSession = autoSession): Option[SysReportPersonCheckin] = {
    withSQL {
      select.from(SysReportPersonCheckin as srpc).where.eq(srpc.dcode, dcode).and.eq(srpc.dname, dname).and.eq(srpc.parentid, parentid).and.eq(srpc.confirmtime, confirmtime).and.eq(srpc.isleaf, isleaf).and.eq(srpc.gathersystemcount, gathersystemcount).and.eq(srpc.fingersystemcount, fingersystemcount).and.eq(srpc.assista, assista).and.eq(srpc.assistb2, assistb2).and.eq(srpc.assistb, assistb).and.eq(srpc.assistc, assistc).and.eq(srpc.wsxzcount, wsxzcount)
    }.map(SysReportPersonCheckin(srpc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysReportPersonCheckin] = {
    withSQL(select.from(SysReportPersonCheckin as srpc)).map(SysReportPersonCheckin(srpc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysReportPersonCheckin as srpc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysReportPersonCheckin] = {
    withSQL {
      select.from(SysReportPersonCheckin as srpc).where.append(where)
    }.map(SysReportPersonCheckin(srpc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysReportPersonCheckin] = {
    withSQL {
      select.from(SysReportPersonCheckin as srpc).where.append(where)
    }.map(SysReportPersonCheckin(srpc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysReportPersonCheckin as srpc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dcode: Option[String] = None,
    dname: Option[String] = None,
    parentid: Option[String] = None,
    confirmtime: Option[DateTime] = None,
    isleaf: Option[String] = None,
    gathersystemcount: Option[Long] = None,
    fingersystemcount: Option[Long] = None,
    assista: Option[Long] = None,
    assistb2: Option[Long] = None,
    assistb: Option[Long] = None,
    assistc: Option[Long] = None,
    wsxzcount: Option[Long] = None)(implicit session: DBSession = autoSession): SysReportPersonCheckin = {
    withSQL {
      insert.into(SysReportPersonCheckin).columns(
        column.dcode,
        column.dname,
        column.parentid,
        column.confirmtime,
        column.isleaf,
        column.gathersystemcount,
        column.fingersystemcount,
        column.assista,
        column.assistb2,
        column.assistb,
        column.assistc,
        column.wsxzcount
      ).values(
        dcode,
        dname,
        parentid,
        confirmtime,
        isleaf,
        gathersystemcount,
        fingersystemcount,
        assista,
        assistb2,
        assistb,
        assistc,
        wsxzcount
      )
    }.update.apply()

    SysReportPersonCheckin(
      dcode = dcode,
      dname = dname,
      parentid = parentid,
      confirmtime = confirmtime,
      isleaf = isleaf,
      gathersystemcount = gathersystemcount,
      fingersystemcount = fingersystemcount,
      assista = assista,
      assistb2 = assistb2,
      assistb = assistb,
      assistc = assistc,
      wsxzcount = wsxzcount)
  }

  def save(entity: SysReportPersonCheckin)(implicit session: DBSession = autoSession): SysReportPersonCheckin = {
    withSQL {
      update(SysReportPersonCheckin).set(
        column.dcode -> entity.dcode,
        column.dname -> entity.dname,
        column.parentid -> entity.parentid,
        column.confirmtime -> entity.confirmtime,
        column.isleaf -> entity.isleaf,
        column.gathersystemcount -> entity.gathersystemcount,
        column.fingersystemcount -> entity.fingersystemcount,
        column.assista -> entity.assista,
        column.assistb2 -> entity.assistb2,
        column.assistb -> entity.assistb,
        column.assistc -> entity.assistc,
        column.wsxzcount -> entity.wsxzcount
      ).where.eq(column.dcode, entity.dcode).and.eq(column.dname, entity.dname).and.eq(column.parentid, entity.parentid).and.eq(column.confirmtime, entity.confirmtime).and.eq(column.isleaf, entity.isleaf).and.eq(column.gathersystemcount, entity.gathersystemcount).and.eq(column.fingersystemcount, entity.fingersystemcount).and.eq(column.assista, entity.assista).and.eq(column.assistb2, entity.assistb2).and.eq(column.assistb, entity.assistb).and.eq(column.assistc, entity.assistc).and.eq(column.wsxzcount, entity.wsxzcount)
    }.update.apply()
    entity
  }

  def destroy(entity: SysReportPersonCheckin)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysReportPersonCheckin).where.eq(column.dcode, entity.dcode).and.eq(column.dname, entity.dname).and.eq(column.parentid, entity.parentid).and.eq(column.confirmtime, entity.confirmtime).and.eq(column.isleaf, entity.isleaf).and.eq(column.gathersystemcount, entity.gathersystemcount).and.eq(column.fingersystemcount, entity.fingersystemcount).and.eq(column.assista, entity.assista).and.eq(column.assistb2, entity.assistb2).and.eq(column.assistb, entity.assistb).and.eq(column.assistc, entity.assistc).and.eq(column.wsxzcount, entity.wsxzcount) }.update.apply()
  }

}
