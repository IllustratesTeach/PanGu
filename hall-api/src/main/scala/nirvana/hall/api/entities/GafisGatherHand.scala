package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisGatherHand(
  pkId: String,
  personid: Option[String] = None,
  fgp: String,
  gatherData: Blob,
  inputpsn: Option[String] = None,
  inputtime: DateTime,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  deletag: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherHand.autoSession): GafisGatherHand = GafisGatherHand.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherHand.autoSession): Unit = GafisGatherHand.destroy(this)(session)

}


object GafisGatherHand extends SQLSyntaxSupport[GafisGatherHand] {

  override val tableName = "GAFIS_GATHER_HAND"

  override val columns = Seq("PK_ID", "PERSONID", "FGP", "GATHER_DATA", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "DELETAG")

  def apply(ggh: SyntaxProvider[GafisGatherHand])(rs: WrappedResultSet): GafisGatherHand = apply(ggh.resultName)(rs)
  def apply(ggh: ResultName[GafisGatherHand])(rs: WrappedResultSet): GafisGatherHand = new GafisGatherHand(
    pkId = rs.get(ggh.pkId),
    personid = rs.get(ggh.personid),
    fgp = rs.get(ggh.fgp),
    gatherData = rs.get(ggh.gatherData),
    inputpsn = rs.get(ggh.inputpsn),
    inputtime = rs.get(ggh.inputtime),
    modifiedpsn = rs.get(ggh.modifiedpsn),
    modifiedtime = rs.get(ggh.modifiedtime),
    deletag = rs.get(ggh.deletag)
  )

  val ggh = GafisGatherHand.syntax("ggh")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], fgp: String, gatherData: Blob, inputpsn: Option[String], inputtime: DateTime, modifiedpsn: Option[String], modifiedtime: Option[DateTime], deletag: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherHand] = {
    withSQL {
      select.from(GafisGatherHand as ggh).where.eq(ggh.pkId, pkId).and.eq(ggh.personid, personid).and.eq(ggh.fgp, fgp).and.eq(ggh.gatherData, gatherData).and.eq(ggh.inputpsn, inputpsn).and.eq(ggh.inputtime, inputtime).and.eq(ggh.modifiedpsn, modifiedpsn).and.eq(ggh.modifiedtime, modifiedtime).and.eq(ggh.deletag, deletag)
    }.map(GafisGatherHand(ggh.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherHand] = {
    withSQL(select.from(GafisGatherHand as ggh)).map(GafisGatherHand(ggh.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherHand as ggh)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherHand] = {
    withSQL {
      select.from(GafisGatherHand as ggh).where.append(where)
    }.map(GafisGatherHand(ggh.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherHand] = {
    withSQL {
      select.from(GafisGatherHand as ggh).where.append(where)
    }.map(GafisGatherHand(ggh.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherHand as ggh).where.append(where)
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
    deletag: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherHand = {
    withSQL {
      insert.into(GafisGatherHand).columns(
        column.pkId,
        column.personid,
        column.fgp,
        column.gatherData,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.deletag
      ).values(
        pkId,
        personid,
        fgp,
        gatherData,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        deletag
      )
    }.update.apply()

    GafisGatherHand(
      pkId = pkId,
      personid = personid,
      fgp = fgp,
      gatherData = gatherData,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      deletag = deletag)
  }

  def save(entity: GafisGatherHand)(implicit session: DBSession = autoSession): GafisGatherHand = {
    withSQL {
      update(GafisGatherHand).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.fgp -> entity.fgp,
        column.gatherData -> entity.gatherData,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.deletag -> entity.deletag
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.fgp, entity.fgp).and.eq(column.gatherData, entity.gatherData).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherHand)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherHand).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.fgp, entity.fgp).and.eq(column.gatherData, entity.gatherData).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag) }.update.apply()
  }

}
