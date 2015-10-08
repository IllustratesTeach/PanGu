package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisGatherPortrait(
  pkId: String,
  personid: Option[String] = None,
  fgp: String,
  gatherData: Blob,
  inputpsn: Option[String] = None,
  inputtime: DateTime,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  deletag: Option[String] = None,
  gatherdatanosqlid: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherPortrait.autoSession): GafisGatherPortrait = GafisGatherPortrait.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherPortrait.autoSession): Unit = GafisGatherPortrait.destroy(this)(session)

}


object GafisGatherPortrait extends SQLSyntaxSupport[GafisGatherPortrait] {

  override val tableName = "GAFIS_GATHER_PORTRAIT"

  override val columns = Seq("PK_ID", "PERSONID", "FGP", "GATHER_DATA", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "DELETAG", "GATHERDATANOSQLID")

  def apply(ggp: SyntaxProvider[GafisGatherPortrait])(rs: WrappedResultSet): GafisGatherPortrait = apply(ggp.resultName)(rs)
  def apply(ggp: ResultName[GafisGatherPortrait])(rs: WrappedResultSet): GafisGatherPortrait = new GafisGatherPortrait(
    pkId = rs.get(ggp.pkId),
    personid = rs.get(ggp.personid),
    fgp = rs.get(ggp.fgp),
    gatherData = rs.get(ggp.gatherData),
    inputpsn = rs.get(ggp.inputpsn),
    inputtime = rs.get(ggp.inputtime),
    modifiedpsn = rs.get(ggp.modifiedpsn),
    modifiedtime = rs.get(ggp.modifiedtime),
    deletag = rs.get(ggp.deletag),
    gatherdatanosqlid = rs.get(ggp.gatherdatanosqlid)
  )

  val ggp = GafisGatherPortrait.syntax("ggp")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], fgp: String, gatherData: Blob, inputpsn: Option[String], inputtime: DateTime, modifiedpsn: Option[String], modifiedtime: Option[DateTime], deletag: Option[String], gatherdatanosqlid: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherPortrait] = {
    withSQL {
      select.from(GafisGatherPortrait as ggp).where.eq(ggp.pkId, pkId).and.eq(ggp.personid, personid).and.eq(ggp.fgp, fgp).and.eq(ggp.gatherData, gatherData).and.eq(ggp.inputpsn, inputpsn).and.eq(ggp.inputtime, inputtime).and.eq(ggp.modifiedpsn, modifiedpsn).and.eq(ggp.modifiedtime, modifiedtime).and.eq(ggp.deletag, deletag).and.eq(ggp.gatherdatanosqlid, gatherdatanosqlid)
    }.map(GafisGatherPortrait(ggp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherPortrait] = {
    withSQL(select.from(GafisGatherPortrait as ggp)).map(GafisGatherPortrait(ggp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherPortrait as ggp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherPortrait] = {
    withSQL {
      select.from(GafisGatherPortrait as ggp).where.append(where)
    }.map(GafisGatherPortrait(ggp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherPortrait] = {
    withSQL {
      select.from(GafisGatherPortrait as ggp).where.append(where)
    }.map(GafisGatherPortrait(ggp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherPortrait as ggp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    fgp: String,
    gatherData: Blob,
    inputpsn: Option[String] = None,
    inputtime: DateTime,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    deletag: Option[String] = None,
    gatherdatanosqlid: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherPortrait = {
    withSQL {
      insert.into(GafisGatherPortrait).columns(
        column.pkId,
        column.personid,
        column.fgp,
        column.gatherData,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.deletag,
        column.gatherdatanosqlid
      ).values(
        pkId,
        personid,
        fgp,
        gatherData,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        deletag,
        gatherdatanosqlid
      )
    }.update.apply()

    GafisGatherPortrait(
      pkId = pkId,
      personid = personid,
      fgp = fgp,
      gatherData = gatherData,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      deletag = deletag,
      gatherdatanosqlid = gatherdatanosqlid)
  }

  def save(entity: GafisGatherPortrait)(implicit session: DBSession = autoSession): GafisGatherPortrait = {
    withSQL {
      update(GafisGatherPortrait).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.fgp -> entity.fgp,
        column.gatherData -> entity.gatherData,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.deletag -> entity.deletag,
        column.gatherdatanosqlid -> entity.gatherdatanosqlid
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.fgp, entity.fgp).and.eq(column.gatherData, entity.gatherData).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag).and.eq(column.gatherdatanosqlid, entity.gatherdatanosqlid)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherPortrait)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherPortrait).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.fgp, entity.fgp).and.eq(column.gatherData, entity.gatherData).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag).and.eq(column.gatherdatanosqlid, entity.gatherdatanosqlid) }.update.apply()
  }

}
