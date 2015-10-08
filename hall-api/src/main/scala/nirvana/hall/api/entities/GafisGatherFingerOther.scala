package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisGatherFingerOther(
  pkId: String,
  personId: Option[String] = None,
  fgp: Short,
  groupId: Option[Short] = None,
  lobtype: Short,
  inputpsn: Option[String] = None,
  inputtime: DateTime,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  fgpCase: Option[String] = None,
  auditStatus: Option[Short] = None,
  description: Option[String] = None,
  mainPattern: Option[Short] = None,
  vicePattern: Option[Short] = None,
  gatherData: Blob,
  seq: Option[Long] = None,
  fingerDataNosqlId: Option[String] = None,
  partitionCitycode: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherFingerOther.autoSession): GafisGatherFingerOther = GafisGatherFingerOther.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherFingerOther.autoSession): Unit = GafisGatherFingerOther.destroy(this)(session)

}


object GafisGatherFingerOther extends SQLSyntaxSupport[GafisGatherFingerOther] {

  override val tableName = "GAFIS_GATHER_FINGER_OTHER"

  override val columns = Seq("PK_ID", "PERSON_ID", "FGP", "GROUP_ID", "LOBTYPE", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "FGP_CASE", "AUDIT_STATUS", "DESCRIPTION", "MAIN_PATTERN", "VICE_PATTERN", "GATHER_DATA", "SEQ", "FINGER_DATA_NOSQL_ID", "PARTITION_CITYCODE")

  def apply(ggfo: SyntaxProvider[GafisGatherFingerOther])(rs: WrappedResultSet): GafisGatherFingerOther = apply(ggfo.resultName)(rs)
  def apply(ggfo: ResultName[GafisGatherFingerOther])(rs: WrappedResultSet): GafisGatherFingerOther = new GafisGatherFingerOther(
    pkId = rs.get(ggfo.pkId),
    personId = rs.get(ggfo.personId),
    fgp = rs.get(ggfo.fgp),
    groupId = rs.get(ggfo.groupId),
    lobtype = rs.get(ggfo.lobtype),
    inputpsn = rs.get(ggfo.inputpsn),
    inputtime = rs.get(ggfo.inputtime),
    modifiedpsn = rs.get(ggfo.modifiedpsn),
    modifiedtime = rs.get(ggfo.modifiedtime),
    fgpCase = rs.get(ggfo.fgpCase),
    auditStatus = rs.get(ggfo.auditStatus),
    description = rs.get(ggfo.description),
    mainPattern = rs.get(ggfo.mainPattern),
    vicePattern = rs.get(ggfo.vicePattern),
    gatherData = rs.get(ggfo.gatherData),
    seq = rs.get(ggfo.seq),
    fingerDataNosqlId = rs.get(ggfo.fingerDataNosqlId),
    partitionCitycode = rs.get(ggfo.partitionCitycode)
  )

  val ggfo = GafisGatherFingerOther.syntax("ggfo")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personId: Option[String], fgp: Short, groupId: Option[Short], lobtype: Short, inputpsn: Option[String], inputtime: DateTime, modifiedpsn: Option[String], modifiedtime: Option[DateTime], fgpCase: Option[String], auditStatus: Option[Short], description: Option[String], mainPattern: Option[Short], vicePattern: Option[Short], gatherData: Blob, seq: Option[Long], fingerDataNosqlId: Option[String], partitionCitycode: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherFingerOther] = {
    withSQL {
      select.from(GafisGatherFingerOther as ggfo).where.eq(ggfo.pkId, pkId).and.eq(ggfo.personId, personId).and.eq(ggfo.fgp, fgp).and.eq(ggfo.groupId, groupId).and.eq(ggfo.lobtype, lobtype).and.eq(ggfo.inputpsn, inputpsn).and.eq(ggfo.inputtime, inputtime).and.eq(ggfo.modifiedpsn, modifiedpsn).and.eq(ggfo.modifiedtime, modifiedtime).and.eq(ggfo.fgpCase, fgpCase).and.eq(ggfo.auditStatus, auditStatus).and.eq(ggfo.description, description).and.eq(ggfo.mainPattern, mainPattern).and.eq(ggfo.vicePattern, vicePattern).and.eq(ggfo.gatherData, gatherData).and.eq(ggfo.seq, seq).and.eq(ggfo.fingerDataNosqlId, fingerDataNosqlId).and.eq(ggfo.partitionCitycode, partitionCitycode)
    }.map(GafisGatherFingerOther(ggfo.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherFingerOther] = {
    withSQL(select.from(GafisGatherFingerOther as ggfo)).map(GafisGatherFingerOther(ggfo.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherFingerOther as ggfo)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherFingerOther] = {
    withSQL {
      select.from(GafisGatherFingerOther as ggfo).where.append(where)
    }.map(GafisGatherFingerOther(ggfo.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherFingerOther] = {
    withSQL {
      select.from(GafisGatherFingerOther as ggfo).where.append(where)
    }.map(GafisGatherFingerOther(ggfo.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherFingerOther as ggfo).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personId: Option[String] = None,
    fgp: Short,
    groupId: Option[Short] = None,
    lobtype: Short,
    inputpsn: Option[String] = None,
    inputtime: DateTime,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    fgpCase: Option[String] = None,
    auditStatus: Option[Short] = None,
    description: Option[String] = None,
    mainPattern: Option[Short] = None,
    vicePattern: Option[Short] = None,
    gatherData: Blob,
    seq: Option[Long] = None,
    fingerDataNosqlId: Option[String] = None,
    partitionCitycode: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherFingerOther = {
    withSQL {
      insert.into(GafisGatherFingerOther).columns(
        column.pkId,
        column.personId,
        column.fgp,
        column.groupId,
        column.lobtype,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.fgpCase,
        column.auditStatus,
        column.description,
        column.mainPattern,
        column.vicePattern,
        column.gatherData,
        column.seq,
        column.fingerDataNosqlId,
        column.partitionCitycode
      ).values(
        pkId,
        personId,
        fgp,
        groupId,
        lobtype,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        fgpCase,
        auditStatus,
        description,
        mainPattern,
        vicePattern,
        gatherData,
        seq,
        fingerDataNosqlId,
        partitionCitycode
      )
    }.update.apply()

    GafisGatherFingerOther(
      pkId = pkId,
      personId = personId,
      fgp = fgp,
      groupId = groupId,
      lobtype = lobtype,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      fgpCase = fgpCase,
      auditStatus = auditStatus,
      description = description,
      mainPattern = mainPattern,
      vicePattern = vicePattern,
      gatherData = gatherData,
      seq = seq,
      fingerDataNosqlId = fingerDataNosqlId,
      partitionCitycode = partitionCitycode)
  }

  def save(entity: GafisGatherFingerOther)(implicit session: DBSession = autoSession): GafisGatherFingerOther = {
    withSQL {
      update(GafisGatherFingerOther).set(
        column.pkId -> entity.pkId,
        column.personId -> entity.personId,
        column.fgp -> entity.fgp,
        column.groupId -> entity.groupId,
        column.lobtype -> entity.lobtype,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.fgpCase -> entity.fgpCase,
        column.auditStatus -> entity.auditStatus,
        column.description -> entity.description,
        column.mainPattern -> entity.mainPattern,
        column.vicePattern -> entity.vicePattern,
        column.gatherData -> entity.gatherData,
        column.seq -> entity.seq,
        column.fingerDataNosqlId -> entity.fingerDataNosqlId,
        column.partitionCitycode -> entity.partitionCitycode
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.fgp, entity.fgp).and.eq(column.groupId, entity.groupId).and.eq(column.lobtype, entity.lobtype).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.fgpCase, entity.fgpCase).and.eq(column.auditStatus, entity.auditStatus).and.eq(column.description, entity.description).and.eq(column.mainPattern, entity.mainPattern).and.eq(column.vicePattern, entity.vicePattern).and.eq(column.gatherData, entity.gatherData).and.eq(column.seq, entity.seq).and.eq(column.fingerDataNosqlId, entity.fingerDataNosqlId).and.eq(column.partitionCitycode, entity.partitionCitycode)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherFingerOther)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherFingerOther).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.fgp, entity.fgp).and.eq(column.groupId, entity.groupId).and.eq(column.lobtype, entity.lobtype).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.fgpCase, entity.fgpCase).and.eq(column.auditStatus, entity.auditStatus).and.eq(column.description, entity.description).and.eq(column.mainPattern, entity.mainPattern).and.eq(column.vicePattern, entity.vicePattern).and.eq(column.gatherData, entity.gatherData).and.eq(column.seq, entity.seq).and.eq(column.fingerDataNosqlId, entity.fingerDataNosqlId).and.eq(column.partitionCitycode, entity.partitionCitycode) }.update.apply()
  }

}
