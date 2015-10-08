package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherResume(
  pkId: String,
  personid: Option[String] = None,
  begindate: Option[DateTime] = None,
  enddate: Option[DateTime] = None,
  describe: Option[String] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  deletag: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherResume.autoSession): GafisGatherResume = GafisGatherResume.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherResume.autoSession): Unit = GafisGatherResume.destroy(this)(session)

}


object GafisGatherResume extends SQLSyntaxSupport[GafisGatherResume] {

  override val tableName = "GAFIS_GATHER_RESUME"

  override val columns = Seq("PK_ID", "PERSONID", "BEGINDATE", "ENDDATE", "DESCRIBE", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "DELETAG")

  def apply(ggr: SyntaxProvider[GafisGatherResume])(rs: WrappedResultSet): GafisGatherResume = apply(ggr.resultName)(rs)
  def apply(ggr: ResultName[GafisGatherResume])(rs: WrappedResultSet): GafisGatherResume = new GafisGatherResume(
    pkId = rs.get(ggr.pkId),
    personid = rs.get(ggr.personid),
    begindate = rs.get(ggr.begindate),
    enddate = rs.get(ggr.enddate),
    describe = rs.get(ggr.describe),
    inputpsn = rs.get(ggr.inputpsn),
    inputtime = rs.get(ggr.inputtime),
    modifiedpsn = rs.get(ggr.modifiedpsn),
    modifiedtime = rs.get(ggr.modifiedtime),
    deletag = rs.get(ggr.deletag)
  )

  val ggr = GafisGatherResume.syntax("ggr")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], begindate: Option[DateTime], enddate: Option[DateTime], describe: Option[String], inputpsn: Option[String], inputtime: Option[DateTime], modifiedpsn: Option[String], modifiedtime: Option[DateTime], deletag: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherResume] = {
    withSQL {
      select.from(GafisGatherResume as ggr).where.eq(ggr.pkId, pkId).and.eq(ggr.personid, personid).and.eq(ggr.begindate, begindate).and.eq(ggr.enddate, enddate).and.eq(ggr.describe, describe).and.eq(ggr.inputpsn, inputpsn).and.eq(ggr.inputtime, inputtime).and.eq(ggr.modifiedpsn, modifiedpsn).and.eq(ggr.modifiedtime, modifiedtime).and.eq(ggr.deletag, deletag)
    }.map(GafisGatherResume(ggr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherResume] = {
    withSQL(select.from(GafisGatherResume as ggr)).map(GafisGatherResume(ggr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherResume as ggr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherResume] = {
    withSQL {
      select.from(GafisGatherResume as ggr).where.append(where)
    }.map(GafisGatherResume(ggr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherResume] = {
    withSQL {
      select.from(GafisGatherResume as ggr).where.append(where)
    }.map(GafisGatherResume(ggr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherResume as ggr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    begindate: Option[DateTime] = None,
    enddate: Option[DateTime] = None,
    describe: Option[String] = None,
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    deletag: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherResume = {
    withSQL {
      insert.into(GafisGatherResume).columns(
        column.pkId,
        column.personid,
        column.begindate,
        column.enddate,
        column.describe,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.deletag
      ).values(
        pkId,
        personid,
        begindate,
        enddate,
        describe,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        deletag
      )
    }.update.apply()

    GafisGatherResume(
      pkId = pkId,
      personid = personid,
      begindate = begindate,
      enddate = enddate,
      describe = describe,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      deletag = deletag)
  }

  def save(entity: GafisGatherResume)(implicit session: DBSession = autoSession): GafisGatherResume = {
    withSQL {
      update(GafisGatherResume).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.begindate -> entity.begindate,
        column.enddate -> entity.enddate,
        column.describe -> entity.describe,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.deletag -> entity.deletag
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.begindate, entity.begindate).and.eq(column.enddate, entity.enddate).and.eq(column.describe, entity.describe).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherResume)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherResume).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.begindate, entity.begindate).and.eq(column.enddate, entity.enddate).and.eq(column.describe, entity.describe).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag) }.update.apply()
  }

}
