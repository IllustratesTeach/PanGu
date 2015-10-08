package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisGatherFinger5201(
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

  def save()(implicit session: DBSession = GafisGatherFinger5201.autoSession): GafisGatherFinger5201 = GafisGatherFinger5201.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherFinger5201.autoSession): Unit = GafisGatherFinger5201.destroy(this)(session)

}


object GafisGatherFinger5201 extends SQLSyntaxSupport[GafisGatherFinger5201] {

  override val tableName = "GAFIS_GATHER_FINGER_5201"

  override val columns = Seq("PK_ID", "PERSON_ID", "FGP", "GROUP_ID", "LOBTYPE", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "FGP_CASE", "AUDIT_STATUS", "DESCRIPTION", "MAIN_PATTERN", "VICE_PATTERN", "GATHER_DATA", "SEQ", "FINGER_DATA_NOSQL_ID", "PARTITION_CITYCODE")

  def apply(ggf: SyntaxProvider[GafisGatherFinger5201])(rs: WrappedResultSet): GafisGatherFinger5201 = apply(ggf.resultName)(rs)
  def apply(ggf: ResultName[GafisGatherFinger5201])(rs: WrappedResultSet): GafisGatherFinger5201 = new GafisGatherFinger5201(
    pkId = rs.get(ggf.pkId),
    personId = rs.get(ggf.personId),
    fgp = rs.get(ggf.fgp),
    groupId = rs.get(ggf.groupId),
    lobtype = rs.get(ggf.lobtype),
    inputpsn = rs.get(ggf.inputpsn),
    inputtime = rs.get(ggf.inputtime),
    modifiedpsn = rs.get(ggf.modifiedpsn),
    modifiedtime = rs.get(ggf.modifiedtime),
    fgpCase = rs.get(ggf.fgpCase),
    auditStatus = rs.get(ggf.auditStatus),
    description = rs.get(ggf.description),
    mainPattern = rs.get(ggf.mainPattern),
    vicePattern = rs.get(ggf.vicePattern),
    gatherData = rs.get(ggf.gatherData),
    seq = rs.get(ggf.seq),
    fingerDataNosqlId = rs.get(ggf.fingerDataNosqlId),
    partitionCitycode = rs.get(ggf.partitionCitycode)
  )

  val ggf = GafisGatherFinger5201.syntax("ggf")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personId: Option[String], fgp: Short, groupId: Option[Short], lobtype: Short, inputpsn: Option[String], inputtime: DateTime, modifiedpsn: Option[String], modifiedtime: Option[DateTime], fgpCase: Option[String], auditStatus: Option[Short], description: Option[String], mainPattern: Option[Short], vicePattern: Option[Short], gatherData: Blob, seq: Option[Long], fingerDataNosqlId: Option[String], partitionCitycode: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherFinger5201] = {
    withSQL {
      select.from(GafisGatherFinger5201 as ggf).where.eq(ggf.pkId, pkId).and.eq(ggf.personId, personId).and.eq(ggf.fgp, fgp).and.eq(ggf.groupId, groupId).and.eq(ggf.lobtype, lobtype).and.eq(ggf.inputpsn, inputpsn).and.eq(ggf.inputtime, inputtime).and.eq(ggf.modifiedpsn, modifiedpsn).and.eq(ggf.modifiedtime, modifiedtime).and.eq(ggf.fgpCase, fgpCase).and.eq(ggf.auditStatus, auditStatus).and.eq(ggf.description, description).and.eq(ggf.mainPattern, mainPattern).and.eq(ggf.vicePattern, vicePattern).and.eq(ggf.gatherData, gatherData).and.eq(ggf.seq, seq).and.eq(ggf.fingerDataNosqlId, fingerDataNosqlId).and.eq(ggf.partitionCitycode, partitionCitycode)
    }.map(GafisGatherFinger5201(ggf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherFinger5201] = {
    withSQL(select.from(GafisGatherFinger5201 as ggf)).map(GafisGatherFinger5201(ggf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherFinger5201 as ggf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherFinger5201] = {
    withSQL {
      select.from(GafisGatherFinger5201 as ggf).where.append(where)
    }.map(GafisGatherFinger5201(ggf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherFinger5201] = {
    withSQL {
      select.from(GafisGatherFinger5201 as ggf).where.append(where)
    }.map(GafisGatherFinger5201(ggf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherFinger5201 as ggf).where.append(where)
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
    partitionCitycode: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherFinger5201 = {
    withSQL {
      insert.into(GafisGatherFinger5201).columns(
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

    GafisGatherFinger5201(
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

  def save(entity: GafisGatherFinger5201)(implicit session: DBSession = autoSession): GafisGatherFinger5201 = {
    withSQL {
      update(GafisGatherFinger5201).set(
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

  def destroy(entity: GafisGatherFinger5201)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherFinger5201).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.fgp, entity.fgp).and.eq(column.groupId, entity.groupId).and.eq(column.lobtype, entity.lobtype).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.fgpCase, entity.fgpCase).and.eq(column.auditStatus, entity.auditStatus).and.eq(column.description, entity.description).and.eq(column.mainPattern, entity.mainPattern).and.eq(column.vicePattern, entity.vicePattern).and.eq(column.gatherData, entity.gatherData).and.eq(column.seq, entity.seq).and.eq(column.fingerDataNosqlId, entity.fingerDataNosqlId).and.eq(column.partitionCitycode, entity.partitionCitycode) }.update.apply()
  }

}
