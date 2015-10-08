package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisGatherPalm(
  pkId: String,
  personId: Option[String] = None,
  fgp: Short,
  groupId: Option[Short] = None,
  lobtype: Short,
  gatherData: Blob,
  inputpsn: Option[String] = None,
  inputtime: DateTime,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  seq: Option[Long] = None,
  palmDataNosqlId: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherPalm.autoSession): GafisGatherPalm = GafisGatherPalm.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherPalm.autoSession): Unit = GafisGatherPalm.destroy(this)(session)

}


object GafisGatherPalm extends SQLSyntaxSupport[GafisGatherPalm] {

  override val tableName = "GAFIS_GATHER_PALM"

  override val columns = Seq("PK_ID", "PERSON_ID", "FGP", "GROUP_ID", "LOBTYPE", "GATHER_DATA", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "SEQ", "PALM_DATA_NOSQL_ID")

  def apply(ggp: SyntaxProvider[GafisGatherPalm])(rs: WrappedResultSet): GafisGatherPalm = apply(ggp.resultName)(rs)
  def apply(ggp: ResultName[GafisGatherPalm])(rs: WrappedResultSet): GafisGatherPalm = new GafisGatherPalm(
    pkId = rs.get(ggp.pkId),
    personId = rs.get(ggp.personId),
    fgp = rs.get(ggp.fgp),
    groupId = rs.get(ggp.groupId),
    lobtype = rs.get(ggp.lobtype),
    gatherData = rs.get(ggp.gatherData),
    inputpsn = rs.get(ggp.inputpsn),
    inputtime = rs.get(ggp.inputtime),
    modifiedpsn = rs.get(ggp.modifiedpsn),
    modifiedtime = rs.get(ggp.modifiedtime),
    seq = rs.get(ggp.seq),
    palmDataNosqlId = rs.get(ggp.palmDataNosqlId)
  )

  val ggp = GafisGatherPalm.syntax("ggp")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personId: Option[String], fgp: Short, groupId: Option[Short], lobtype: Short, gatherData: Blob, inputpsn: Option[String], inputtime: DateTime, modifiedpsn: Option[String], modifiedtime: Option[DateTime], seq: Option[Long], palmDataNosqlId: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherPalm] = {
    withSQL {
      select.from(GafisGatherPalm as ggp).where.eq(ggp.pkId, pkId).and.eq(ggp.personId, personId).and.eq(ggp.fgp, fgp).and.eq(ggp.groupId, groupId).and.eq(ggp.lobtype, lobtype).and.eq(ggp.gatherData, gatherData).and.eq(ggp.inputpsn, inputpsn).and.eq(ggp.inputtime, inputtime).and.eq(ggp.modifiedpsn, modifiedpsn).and.eq(ggp.modifiedtime, modifiedtime).and.eq(ggp.seq, seq).and.eq(ggp.palmDataNosqlId, palmDataNosqlId)
    }.map(GafisGatherPalm(ggp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherPalm] = {
    withSQL(select.from(GafisGatherPalm as ggp)).map(GafisGatherPalm(ggp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherPalm as ggp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherPalm] = {
    withSQL {
      select.from(GafisGatherPalm as ggp).where.append(where)
    }.map(GafisGatherPalm(ggp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherPalm] = {
    withSQL {
      select.from(GafisGatherPalm as ggp).where.append(where)
    }.map(GafisGatherPalm(ggp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherPalm as ggp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personId: Option[String] = None,
    fgp: Short,
    groupId: Option[Short] = None,
    lobtype: Short,
    gatherData: Blob,
    inputpsn: Option[String] = None,
    inputtime: DateTime,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    seq: Option[Long] = None,
    palmDataNosqlId: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherPalm = {
    withSQL {
      insert.into(GafisGatherPalm).columns(
        column.pkId,
        column.personId,
        column.fgp,
        column.groupId,
        column.lobtype,
        column.gatherData,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.seq,
        column.palmDataNosqlId
      ).values(
        pkId,
        personId,
        fgp,
        groupId,
        lobtype,
        gatherData,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        seq,
        palmDataNosqlId
      )
    }.update.apply()

    GafisGatherPalm(
      pkId = pkId,
      personId = personId,
      fgp = fgp,
      groupId = groupId,
      lobtype = lobtype,
      gatherData = gatherData,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      seq = seq,
      palmDataNosqlId = palmDataNosqlId)
  }

  def save(entity: GafisGatherPalm)(implicit session: DBSession = autoSession): GafisGatherPalm = {
    withSQL {
      update(GafisGatherPalm).set(
        column.pkId -> entity.pkId,
        column.personId -> entity.personId,
        column.fgp -> entity.fgp,
        column.groupId -> entity.groupId,
        column.lobtype -> entity.lobtype,
        column.gatherData -> entity.gatherData,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.seq -> entity.seq,
        column.palmDataNosqlId -> entity.palmDataNosqlId
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.fgp, entity.fgp).and.eq(column.groupId, entity.groupId).and.eq(column.lobtype, entity.lobtype).and.eq(column.gatherData, entity.gatherData).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.seq, entity.seq).and.eq(column.palmDataNosqlId, entity.palmDataNosqlId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherPalm)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherPalm).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.fgp, entity.fgp).and.eq(column.groupId, entity.groupId).and.eq(column.lobtype, entity.lobtype).and.eq(column.gatherData, entity.gatherData).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.seq, entity.seq).and.eq(column.palmDataNosqlId, entity.palmDataNosqlId) }.update.apply()
  }

}
