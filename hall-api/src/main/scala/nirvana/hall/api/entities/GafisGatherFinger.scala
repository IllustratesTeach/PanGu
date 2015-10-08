package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisGatherFinger(
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

  def save()(implicit session: DBSession = GafisGatherFinger.autoSession): GafisGatherFinger = GafisGatherFinger.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherFinger.autoSession): Unit = GafisGatherFinger.destroy(this)(session)

}


object GafisGatherFinger extends SQLSyntaxSupport[GafisGatherFinger] {

  override val tableName = "GAFIS_GATHER_FINGER"

  override val columns = Seq("PK_ID", "PERSON_ID", "FGP", "GROUP_ID", "LOBTYPE", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "FGP_CASE", "AUDIT_STATUS", "DESCRIPTION", "MAIN_PATTERN", "VICE_PATTERN", "GATHER_DATA", "SEQ", "FINGER_DATA_NOSQL_ID", "PARTITION_CITYCODE")

  def apply(ggf: SyntaxProvider[GafisGatherFinger])(rs: WrappedResultSet): GafisGatherFinger = apply(ggf.resultName)(rs)
  def apply(ggf: ResultName[GafisGatherFinger])(rs: WrappedResultSet): GafisGatherFinger = new GafisGatherFinger(
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

  val ggf = GafisGatherFinger.syntax("ggf")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[GafisGatherFinger] = {
    withSQL {
      select.from(GafisGatherFinger as ggf).where.eq(ggf.pkId, pkId)
    }.map(GafisGatherFinger(ggf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherFinger] = {
    withSQL(select.from(GafisGatherFinger as ggf)).map(GafisGatherFinger(ggf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherFinger as ggf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherFinger] = {
    withSQL {
      select.from(GafisGatherFinger as ggf).where.append(where)
    }.map(GafisGatherFinger(ggf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherFinger] = {
    withSQL {
      select.from(GafisGatherFinger as ggf).where.append(where)
    }.map(GafisGatherFinger(ggf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherFinger as ggf).where.append(where)
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
    partitionCitycode: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherFinger = {
    withSQL {
      insert.into(GafisGatherFinger).columns(
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

    GafisGatherFinger(
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

  def save(entity: GafisGatherFinger)(implicit session: DBSession = autoSession): GafisGatherFinger = {
    withSQL {
      update(GafisGatherFinger).set(
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
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherFinger)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherFinger).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
