package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisGatherCityCodeDef(
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

  def save()(implicit session: DBSession = GafisGatherCityCodeDef.autoSession): GafisGatherCityCodeDef = GafisGatherCityCodeDef.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherCityCodeDef.autoSession): Unit = GafisGatherCityCodeDef.destroy(this)(session)

}


object GafisGatherCityCodeDef extends SQLSyntaxSupport[GafisGatherCityCodeDef] {

  override val tableName = "GAFIS_GATHER__CITY_CODE_DEF"

  override val columns = Seq("PK_ID", "PERSON_ID", "FGP", "GROUP_ID", "LOBTYPE", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "FGP_CASE", "AUDIT_STATUS", "DESCRIPTION", "MAIN_PATTERN", "VICE_PATTERN", "GATHER_DATA", "SEQ", "FINGER_DATA_NOSQL_ID", "PARTITION_CITYCODE")

  def apply(ggccd: SyntaxProvider[GafisGatherCityCodeDef])(rs: WrappedResultSet): GafisGatherCityCodeDef = apply(ggccd.resultName)(rs)
  def apply(ggccd: ResultName[GafisGatherCityCodeDef])(rs: WrappedResultSet): GafisGatherCityCodeDef = new GafisGatherCityCodeDef(
    pkId = rs.get(ggccd.pkId),
    personId = rs.get(ggccd.personId),
    fgp = rs.get(ggccd.fgp),
    groupId = rs.get(ggccd.groupId),
    lobtype = rs.get(ggccd.lobtype),
    inputpsn = rs.get(ggccd.inputpsn),
    inputtime = rs.get(ggccd.inputtime),
    modifiedpsn = rs.get(ggccd.modifiedpsn),
    modifiedtime = rs.get(ggccd.modifiedtime),
    fgpCase = rs.get(ggccd.fgpCase),
    auditStatus = rs.get(ggccd.auditStatus),
    description = rs.get(ggccd.description),
    mainPattern = rs.get(ggccd.mainPattern),
    vicePattern = rs.get(ggccd.vicePattern),
    gatherData = rs.get(ggccd.gatherData),
    seq = rs.get(ggccd.seq),
    fingerDataNosqlId = rs.get(ggccd.fingerDataNosqlId),
    partitionCitycode = rs.get(ggccd.partitionCitycode)
  )

  val ggccd = GafisGatherCityCodeDef.syntax("ggccd")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personId: Option[String], fgp: Short, groupId: Option[Short], lobtype: Short, inputpsn: Option[String], inputtime: DateTime, modifiedpsn: Option[String], modifiedtime: Option[DateTime], fgpCase: Option[String], auditStatus: Option[Short], description: Option[String], mainPattern: Option[Short], vicePattern: Option[Short], gatherData: Blob, seq: Option[Long], fingerDataNosqlId: Option[String], partitionCitycode: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherCityCodeDef] = {
    withSQL {
      select.from(GafisGatherCityCodeDef as ggccd).where.eq(ggccd.pkId, pkId).and.eq(ggccd.personId, personId).and.eq(ggccd.fgp, fgp).and.eq(ggccd.groupId, groupId).and.eq(ggccd.lobtype, lobtype).and.eq(ggccd.inputpsn, inputpsn).and.eq(ggccd.inputtime, inputtime).and.eq(ggccd.modifiedpsn, modifiedpsn).and.eq(ggccd.modifiedtime, modifiedtime).and.eq(ggccd.fgpCase, fgpCase).and.eq(ggccd.auditStatus, auditStatus).and.eq(ggccd.description, description).and.eq(ggccd.mainPattern, mainPattern).and.eq(ggccd.vicePattern, vicePattern).and.eq(ggccd.gatherData, gatherData).and.eq(ggccd.seq, seq).and.eq(ggccd.fingerDataNosqlId, fingerDataNosqlId).and.eq(ggccd.partitionCitycode, partitionCitycode)
    }.map(GafisGatherCityCodeDef(ggccd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherCityCodeDef] = {
    withSQL(select.from(GafisGatherCityCodeDef as ggccd)).map(GafisGatherCityCodeDef(ggccd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherCityCodeDef as ggccd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherCityCodeDef] = {
    withSQL {
      select.from(GafisGatherCityCodeDef as ggccd).where.append(where)
    }.map(GafisGatherCityCodeDef(ggccd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherCityCodeDef] = {
    withSQL {
      select.from(GafisGatherCityCodeDef as ggccd).where.append(where)
    }.map(GafisGatherCityCodeDef(ggccd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherCityCodeDef as ggccd).where.append(where)
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
    partitionCitycode: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherCityCodeDef = {
    withSQL {
      insert.into(GafisGatherCityCodeDef).columns(
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

    GafisGatherCityCodeDef(
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

  def save(entity: GafisGatherCityCodeDef)(implicit session: DBSession = autoSession): GafisGatherCityCodeDef = {
    withSQL {
      update(GafisGatherCityCodeDef).set(
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

  def destroy(entity: GafisGatherCityCodeDef)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherCityCodeDef).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.fgp, entity.fgp).and.eq(column.groupId, entity.groupId).and.eq(column.lobtype, entity.lobtype).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.fgpCase, entity.fgpCase).and.eq(column.auditStatus, entity.auditStatus).and.eq(column.description, entity.description).and.eq(column.mainPattern, entity.mainPattern).and.eq(column.vicePattern, entity.vicePattern).and.eq(column.gatherData, entity.gatherData).and.eq(column.seq, entity.seq).and.eq(column.fingerDataNosqlId, entity.fingerDataNosqlId).and.eq(column.partitionCitycode, entity.partitionCitycode) }.update.apply()
  }

}
