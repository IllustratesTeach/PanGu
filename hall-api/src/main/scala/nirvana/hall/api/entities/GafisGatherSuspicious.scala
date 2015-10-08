package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherSuspicious(
  pkId: String,
  personid: Option[String] = None,
  kyjxCode: Option[String] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  deletag: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherSuspicious.autoSession): GafisGatherSuspicious = GafisGatherSuspicious.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherSuspicious.autoSession): Unit = GafisGatherSuspicious.destroy(this)(session)

}


object GafisGatherSuspicious extends SQLSyntaxSupport[GafisGatherSuspicious] {

  override val tableName = "GAFIS_GATHER_SUSPICIOUS"

  override val columns = Seq("PK_ID", "PERSONID", "KYJX_CODE", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "DELETAG")

  def apply(ggs: SyntaxProvider[GafisGatherSuspicious])(rs: WrappedResultSet): GafisGatherSuspicious = apply(ggs.resultName)(rs)
  def apply(ggs: ResultName[GafisGatherSuspicious])(rs: WrappedResultSet): GafisGatherSuspicious = new GafisGatherSuspicious(
    pkId = rs.get(ggs.pkId),
    personid = rs.get(ggs.personid),
    kyjxCode = rs.get(ggs.kyjxCode),
    inputpsn = rs.get(ggs.inputpsn),
    inputtime = rs.get(ggs.inputtime),
    modifiedpsn = rs.get(ggs.modifiedpsn),
    modifiedtime = rs.get(ggs.modifiedtime),
    deletag = rs.get(ggs.deletag)
  )

  val ggs = GafisGatherSuspicious.syntax("ggs")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], kyjxCode: Option[String], inputpsn: Option[String], inputtime: Option[DateTime], modifiedpsn: Option[String], modifiedtime: Option[DateTime], deletag: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherSuspicious] = {
    withSQL {
      select.from(GafisGatherSuspicious as ggs).where.eq(ggs.pkId, pkId).and.eq(ggs.personid, personid).and.eq(ggs.kyjxCode, kyjxCode).and.eq(ggs.inputpsn, inputpsn).and.eq(ggs.inputtime, inputtime).and.eq(ggs.modifiedpsn, modifiedpsn).and.eq(ggs.modifiedtime, modifiedtime).and.eq(ggs.deletag, deletag)
    }.map(GafisGatherSuspicious(ggs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherSuspicious] = {
    withSQL(select.from(GafisGatherSuspicious as ggs)).map(GafisGatherSuspicious(ggs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherSuspicious as ggs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherSuspicious] = {
    withSQL {
      select.from(GafisGatherSuspicious as ggs).where.append(where)
    }.map(GafisGatherSuspicious(ggs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherSuspicious] = {
    withSQL {
      select.from(GafisGatherSuspicious as ggs).where.append(where)
    }.map(GafisGatherSuspicious(ggs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherSuspicious as ggs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    kyjxCode: Option[String] = None,
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    deletag: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherSuspicious = {
    withSQL {
      insert.into(GafisGatherSuspicious).columns(
        column.pkId,
        column.personid,
        column.kyjxCode,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.deletag
      ).values(
        pkId,
        personid,
        kyjxCode,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        deletag
      )
    }.update.apply()

    GafisGatherSuspicious(
      pkId = pkId,
      personid = personid,
      kyjxCode = kyjxCode,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      deletag = deletag)
  }

  def save(entity: GafisGatherSuspicious)(implicit session: DBSession = autoSession): GafisGatherSuspicious = {
    withSQL {
      update(GafisGatherSuspicious).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.kyjxCode -> entity.kyjxCode,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.deletag -> entity.deletag
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.kyjxCode, entity.kyjxCode).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherSuspicious)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherSuspicious).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.kyjxCode, entity.kyjxCode).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag) }.update.apply()
  }

}
