package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisGatherCityCode5203(
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

  def save()(implicit session: DBSession = GafisGatherCityCode5203.autoSession): GafisGatherCityCode5203 = GafisGatherCityCode5203.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherCityCode5203.autoSession): Unit = GafisGatherCityCode5203.destroy(this)(session)

}


object GafisGatherCityCode5203 extends SQLSyntaxSupport[GafisGatherCityCode5203] {

  override val tableName = "GAFIS_GATHER__CITY_CODE_520_3"

  override val columns = Seq("PK_ID", "PERSON_ID", "FGP", "GROUP_ID", "LOBTYPE", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "FGP_CASE", "AUDIT_STATUS", "DESCRIPTION", "MAIN_PATTERN", "VICE_PATTERN", "GATHER_DATA", "SEQ", "FINGER_DATA_NOSQL_ID", "PARTITION_CITYCODE")

  def apply(ggcc: SyntaxProvider[GafisGatherCityCode5203])(rs: WrappedResultSet): GafisGatherCityCode5203 = apply(ggcc.resultName)(rs)
  def apply(ggcc: ResultName[GafisGatherCityCode5203])(rs: WrappedResultSet): GafisGatherCityCode5203 = new GafisGatherCityCode5203(
    pkId = rs.get(ggcc.pkId),
    personId = rs.get(ggcc.personId),
    fgp = rs.get(ggcc.fgp),
    groupId = rs.get(ggcc.groupId),
    lobtype = rs.get(ggcc.lobtype),
    inputpsn = rs.get(ggcc.inputpsn),
    inputtime = rs.get(ggcc.inputtime),
    modifiedpsn = rs.get(ggcc.modifiedpsn),
    modifiedtime = rs.get(ggcc.modifiedtime),
    fgpCase = rs.get(ggcc.fgpCase),
    auditStatus = rs.get(ggcc.auditStatus),
    description = rs.get(ggcc.description),
    mainPattern = rs.get(ggcc.mainPattern),
    vicePattern = rs.get(ggcc.vicePattern),
    gatherData = rs.get(ggcc.gatherData),
    seq = rs.get(ggcc.seq),
    fingerDataNosqlId = rs.get(ggcc.fingerDataNosqlId),
    partitionCitycode = rs.get(ggcc.partitionCitycode)
  )

  val ggcc = GafisGatherCityCode5203.syntax("ggcc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personId: Option[String], fgp: Short, groupId: Option[Short], lobtype: Short, inputpsn: Option[String], inputtime: DateTime, modifiedpsn: Option[String], modifiedtime: Option[DateTime], fgpCase: Option[String], auditStatus: Option[Short], description: Option[String], mainPattern: Option[Short], vicePattern: Option[Short], gatherData: Blob, seq: Option[Long], fingerDataNosqlId: Option[String], partitionCitycode: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherCityCode5203] = {
    withSQL {
      select.from(GafisGatherCityCode5203 as ggcc).where.eq(ggcc.pkId, pkId).and.eq(ggcc.personId, personId).and.eq(ggcc.fgp, fgp).and.eq(ggcc.groupId, groupId).and.eq(ggcc.lobtype, lobtype).and.eq(ggcc.inputpsn, inputpsn).and.eq(ggcc.inputtime, inputtime).and.eq(ggcc.modifiedpsn, modifiedpsn).and.eq(ggcc.modifiedtime, modifiedtime).and.eq(ggcc.fgpCase, fgpCase).and.eq(ggcc.auditStatus, auditStatus).and.eq(ggcc.description, description).and.eq(ggcc.mainPattern, mainPattern).and.eq(ggcc.vicePattern, vicePattern).and.eq(ggcc.gatherData, gatherData).and.eq(ggcc.seq, seq).and.eq(ggcc.fingerDataNosqlId, fingerDataNosqlId).and.eq(ggcc.partitionCitycode, partitionCitycode)
    }.map(GafisGatherCityCode5203(ggcc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherCityCode5203] = {
    withSQL(select.from(GafisGatherCityCode5203 as ggcc)).map(GafisGatherCityCode5203(ggcc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherCityCode5203 as ggcc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherCityCode5203] = {
    withSQL {
      select.from(GafisGatherCityCode5203 as ggcc).where.append(where)
    }.map(GafisGatherCityCode5203(ggcc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherCityCode5203] = {
    withSQL {
      select.from(GafisGatherCityCode5203 as ggcc).where.append(where)
    }.map(GafisGatherCityCode5203(ggcc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherCityCode5203 as ggcc).where.append(where)
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
    partitionCitycode: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherCityCode5203 = {
    withSQL {
      insert.into(GafisGatherCityCode5203).columns(
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

    GafisGatherCityCode5203(
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

  def save(entity: GafisGatherCityCode5203)(implicit session: DBSession = autoSession): GafisGatherCityCode5203 = {
    withSQL {
      update(GafisGatherCityCode5203).set(
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

  def destroy(entity: GafisGatherCityCode5203)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherCityCode5203).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.fgp, entity.fgp).and.eq(column.groupId, entity.groupId).and.eq(column.lobtype, entity.lobtype).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.fgpCase, entity.fgpCase).and.eq(column.auditStatus, entity.auditStatus).and.eq(column.description, entity.description).and.eq(column.mainPattern, entity.mainPattern).and.eq(column.vicePattern, entity.vicePattern).and.eq(column.gatherData, entity.gatherData).and.eq(column.seq, entity.seq).and.eq(column.fingerDataNosqlId, entity.fingerDataNosqlId).and.eq(column.partitionCitycode, entity.partitionCitycode) }.update.apply()
  }

}
