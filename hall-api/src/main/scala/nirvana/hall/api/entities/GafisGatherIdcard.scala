package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisGatherIdcard(
  pkId: String,
  personid: Option[String] = None,
  fgpCode: Option[String] = None,
  gatherData: Option[Blob] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  deletag: Option[String] = None,
  gatherdatanosqlid: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherIdcard.autoSession): GafisGatherIdcard = GafisGatherIdcard.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherIdcard.autoSession): Unit = GafisGatherIdcard.destroy(this)(session)

}


object GafisGatherIdcard extends SQLSyntaxSupport[GafisGatherIdcard] {

  override val tableName = "GAFIS_GATHER_IDCARD"

  override val columns = Seq("PK_ID", "PERSONID", "FGP_CODE", "GATHER_DATA", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "DELETAG", "GATHERDATANOSQLID")

  def apply(ggi: SyntaxProvider[GafisGatherIdcard])(rs: WrappedResultSet): GafisGatherIdcard = apply(ggi.resultName)(rs)
  def apply(ggi: ResultName[GafisGatherIdcard])(rs: WrappedResultSet): GafisGatherIdcard = new GafisGatherIdcard(
    pkId = rs.get(ggi.pkId),
    personid = rs.get(ggi.personid),
    fgpCode = rs.get(ggi.fgpCode),
    gatherData = rs.get(ggi.gatherData),
    inputpsn = rs.get(ggi.inputpsn),
    inputtime = rs.get(ggi.inputtime),
    modifiedpsn = rs.get(ggi.modifiedpsn),
    modifiedtime = rs.get(ggi.modifiedtime),
    deletag = rs.get(ggi.deletag),
    gatherdatanosqlid = rs.get(ggi.gatherdatanosqlid)
  )

  val ggi = GafisGatherIdcard.syntax("ggi")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], fgpCode: Option[String], gatherData: Option[Blob], inputpsn: Option[String], inputtime: Option[DateTime], modifiedpsn: Option[String], modifiedtime: Option[DateTime], deletag: Option[String], gatherdatanosqlid: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherIdcard] = {
    withSQL {
      select.from(GafisGatherIdcard as ggi).where.eq(ggi.pkId, pkId).and.eq(ggi.personid, personid).and.eq(ggi.fgpCode, fgpCode).and.eq(ggi.gatherData, gatherData).and.eq(ggi.inputpsn, inputpsn).and.eq(ggi.inputtime, inputtime).and.eq(ggi.modifiedpsn, modifiedpsn).and.eq(ggi.modifiedtime, modifiedtime).and.eq(ggi.deletag, deletag).and.eq(ggi.gatherdatanosqlid, gatherdatanosqlid)
    }.map(GafisGatherIdcard(ggi.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherIdcard] = {
    withSQL(select.from(GafisGatherIdcard as ggi)).map(GafisGatherIdcard(ggi.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherIdcard as ggi)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherIdcard] = {
    withSQL {
      select.from(GafisGatherIdcard as ggi).where.append(where)
    }.map(GafisGatherIdcard(ggi.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherIdcard] = {
    withSQL {
      select.from(GafisGatherIdcard as ggi).where.append(where)
    }.map(GafisGatherIdcard(ggi.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherIdcard as ggi).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    fgpCode: Option[String] = None,
    gatherData: Option[Blob] = None,
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    deletag: Option[String] = None,
    gatherdatanosqlid: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherIdcard = {
    withSQL {
      insert.into(GafisGatherIdcard).columns(
        column.pkId,
        column.personid,
        column.fgpCode,
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
        fgpCode,
        gatherData,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        deletag,
        gatherdatanosqlid
      )
    }.update.apply()

    GafisGatherIdcard(
      pkId = pkId,
      personid = personid,
      fgpCode = fgpCode,
      gatherData = gatherData,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      deletag = deletag,
      gatherdatanosqlid = gatherdatanosqlid)
  }

  def save(entity: GafisGatherIdcard)(implicit session: DBSession = autoSession): GafisGatherIdcard = {
    withSQL {
      update(GafisGatherIdcard).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.fgpCode -> entity.fgpCode,
        column.gatherData -> entity.gatherData,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.deletag -> entity.deletag,
        column.gatherdatanosqlid -> entity.gatherdatanosqlid
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.fgpCode, entity.fgpCode).and.eq(column.gatherData, entity.gatherData).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag).and.eq(column.gatherdatanosqlid, entity.gatherdatanosqlid)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherIdcard)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherIdcard).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.fgpCode, entity.fgpCode).and.eq(column.gatherData, entity.gatherData).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag).and.eq(column.gatherdatanosqlid, entity.gatherdatanosqlid) }.update.apply()
  }

}
