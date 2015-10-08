package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherSuspiciousAct(
  pkId: String,
  personid: Option[String] = None,
  kyxwCode: Option[String] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  deletag: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherSuspiciousAct.autoSession): GafisGatherSuspiciousAct = GafisGatherSuspiciousAct.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherSuspiciousAct.autoSession): Unit = GafisGatherSuspiciousAct.destroy(this)(session)

}


object GafisGatherSuspiciousAct extends SQLSyntaxSupport[GafisGatherSuspiciousAct] {

  override val tableName = "GAFIS_GATHER_SUSPICIOUS_ACT"

  override val columns = Seq("PK_ID", "PERSONID", "KYXW_CODE", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "DELETAG")

  def apply(ggsa: SyntaxProvider[GafisGatherSuspiciousAct])(rs: WrappedResultSet): GafisGatherSuspiciousAct = apply(ggsa.resultName)(rs)
  def apply(ggsa: ResultName[GafisGatherSuspiciousAct])(rs: WrappedResultSet): GafisGatherSuspiciousAct = new GafisGatherSuspiciousAct(
    pkId = rs.get(ggsa.pkId),
    personid = rs.get(ggsa.personid),
    kyxwCode = rs.get(ggsa.kyxwCode),
    inputpsn = rs.get(ggsa.inputpsn),
    inputtime = rs.get(ggsa.inputtime),
    modifiedpsn = rs.get(ggsa.modifiedpsn),
    modifiedtime = rs.get(ggsa.modifiedtime),
    deletag = rs.get(ggsa.deletag)
  )

  val ggsa = GafisGatherSuspiciousAct.syntax("ggsa")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], kyxwCode: Option[String], inputpsn: Option[String], inputtime: Option[DateTime], modifiedpsn: Option[String], modifiedtime: Option[DateTime], deletag: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherSuspiciousAct] = {
    withSQL {
      select.from(GafisGatherSuspiciousAct as ggsa).where.eq(ggsa.pkId, pkId).and.eq(ggsa.personid, personid).and.eq(ggsa.kyxwCode, kyxwCode).and.eq(ggsa.inputpsn, inputpsn).and.eq(ggsa.inputtime, inputtime).and.eq(ggsa.modifiedpsn, modifiedpsn).and.eq(ggsa.modifiedtime, modifiedtime).and.eq(ggsa.deletag, deletag)
    }.map(GafisGatherSuspiciousAct(ggsa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherSuspiciousAct] = {
    withSQL(select.from(GafisGatherSuspiciousAct as ggsa)).map(GafisGatherSuspiciousAct(ggsa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherSuspiciousAct as ggsa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherSuspiciousAct] = {
    withSQL {
      select.from(GafisGatherSuspiciousAct as ggsa).where.append(where)
    }.map(GafisGatherSuspiciousAct(ggsa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherSuspiciousAct] = {
    withSQL {
      select.from(GafisGatherSuspiciousAct as ggsa).where.append(where)
    }.map(GafisGatherSuspiciousAct(ggsa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherSuspiciousAct as ggsa).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    kyxwCode: Option[String] = None,
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    deletag: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherSuspiciousAct = {
    withSQL {
      insert.into(GafisGatherSuspiciousAct).columns(
        column.pkId,
        column.personid,
        column.kyxwCode,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.deletag
      ).values(
        pkId,
        personid,
        kyxwCode,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        deletag
      )
    }.update.apply()

    GafisGatherSuspiciousAct(
      pkId = pkId,
      personid = personid,
      kyxwCode = kyxwCode,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      deletag = deletag)
  }

  def save(entity: GafisGatherSuspiciousAct)(implicit session: DBSession = autoSession): GafisGatherSuspiciousAct = {
    withSQL {
      update(GafisGatherSuspiciousAct).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.kyxwCode -> entity.kyxwCode,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.deletag -> entity.deletag
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.kyxwCode, entity.kyxwCode).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherSuspiciousAct)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherSuspiciousAct).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.kyxwCode, entity.kyxwCode).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag) }.update.apply()
  }

}
