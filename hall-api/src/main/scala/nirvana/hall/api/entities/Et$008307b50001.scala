package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class Et$008307b50001(
  pkId: Option[String] = None,
  personId: Option[String] = None,
  fgp: Option[Short] = None,
  groupId: Option[Short] = None,
  lobtype: Option[Short] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  fgpCase: Option[String] = None,
  auditStatus: Option[Short] = None,
  description: Option[String] = None,
  mainPattern: Option[Short] = None,
  vicePattern: Option[Short] = None,
  seq: Option[Long] = None,
  fingerDataNosqlId: Option[String] = None,
  gatherData: Option[Blob] = None) {

  def save()(implicit session: DBSession = Et$008307b50001.autoSession): Et$008307b50001 = Et$008307b50001.save(this)(session)

  def destroy()(implicit session: DBSession = Et$008307b50001.autoSession): Unit = Et$008307b50001.destroy(this)(session)

}


object Et$008307b50001 extends SQLSyntaxSupport[Et$008307b50001] {

  override val tableName = "ET$008307B50001"

  override val columns = Seq("PK_ID", "PERSON_ID", "FGP", "GROUP_ID", "LOBTYPE", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "FGP_CASE", "AUDIT_STATUS", "DESCRIPTION", "MAIN_PATTERN", "VICE_PATTERN", "SEQ", "FINGER_DATA_NOSQL_ID", "GATHER_DATA")

  def apply(e: SyntaxProvider[Et$008307b50001])(rs: WrappedResultSet): Et$008307b50001 = apply(e.resultName)(rs)
  def apply(e: ResultName[Et$008307b50001])(rs: WrappedResultSet): Et$008307b50001 = new Et$008307b50001(
    pkId = rs.get(e.pkId),
    personId = rs.get(e.personId),
    fgp = rs.get(e.fgp),
    groupId = rs.get(e.groupId),
    lobtype = rs.get(e.lobtype),
    inputpsn = rs.get(e.inputpsn),
    inputtime = rs.get(e.inputtime),
    modifiedpsn = rs.get(e.modifiedpsn),
    modifiedtime = rs.get(e.modifiedtime),
    fgpCase = rs.get(e.fgpCase),
    auditStatus = rs.get(e.auditStatus),
    description = rs.get(e.description),
    mainPattern = rs.get(e.mainPattern),
    vicePattern = rs.get(e.vicePattern),
    seq = rs.get(e.seq),
    fingerDataNosqlId = rs.get(e.fingerDataNosqlId),
    gatherData = rs.get(e.gatherData)
  )

  val e = Et$008307b50001.syntax("e")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: Option[String], personId: Option[String], fgp: Option[Short], groupId: Option[Short], lobtype: Option[Short], inputpsn: Option[String], inputtime: Option[DateTime], modifiedpsn: Option[String], modifiedtime: Option[DateTime], fgpCase: Option[String], auditStatus: Option[Short], description: Option[String], mainPattern: Option[Short], vicePattern: Option[Short], seq: Option[Long], fingerDataNosqlId: Option[String], gatherData: Option[Blob])(implicit session: DBSession = autoSession): Option[Et$008307b50001] = {
    withSQL {
      select.from(Et$008307b50001 as e).where.eq(e.pkId, pkId).and.eq(e.personId, personId).and.eq(e.fgp, fgp).and.eq(e.groupId, groupId).and.eq(e.lobtype, lobtype).and.eq(e.inputpsn, inputpsn).and.eq(e.inputtime, inputtime).and.eq(e.modifiedpsn, modifiedpsn).and.eq(e.modifiedtime, modifiedtime).and.eq(e.fgpCase, fgpCase).and.eq(e.auditStatus, auditStatus).and.eq(e.description, description).and.eq(e.mainPattern, mainPattern).and.eq(e.vicePattern, vicePattern).and.eq(e.seq, seq).and.eq(e.fingerDataNosqlId, fingerDataNosqlId).and.eq(e.gatherData, gatherData)
    }.map(Et$008307b50001(e.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Et$008307b50001] = {
    withSQL(select.from(Et$008307b50001 as e)).map(Et$008307b50001(e.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Et$008307b50001 as e)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Et$008307b50001] = {
    withSQL {
      select.from(Et$008307b50001 as e).where.append(where)
    }.map(Et$008307b50001(e.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Et$008307b50001] = {
    withSQL {
      select.from(Et$008307b50001 as e).where.append(where)
    }.map(Et$008307b50001(e.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Et$008307b50001 as e).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: Option[String] = None,
    personId: Option[String] = None,
    fgp: Option[Short] = None,
    groupId: Option[Short] = None,
    lobtype: Option[Short] = None,
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    fgpCase: Option[String] = None,
    auditStatus: Option[Short] = None,
    description: Option[String] = None,
    mainPattern: Option[Short] = None,
    vicePattern: Option[Short] = None,
    seq: Option[Long] = None,
    fingerDataNosqlId: Option[String] = None,
    gatherData: Option[Blob] = None)(implicit session: DBSession = autoSession): Et$008307b50001 = {
    withSQL {
      insert.into(Et$008307b50001).columns(
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
        column.seq,
        column.fingerDataNosqlId,
        column.gatherData
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
        seq,
        fingerDataNosqlId,
        gatherData
      )
    }.update.apply()

    Et$008307b50001(
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
      seq = seq,
      fingerDataNosqlId = fingerDataNosqlId,
      gatherData = gatherData)
  }

  def save(entity: Et$008307b50001)(implicit session: DBSession = autoSession): Et$008307b50001 = {
    withSQL {
      update(Et$008307b50001).set(
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
        column.seq -> entity.seq,
        column.fingerDataNosqlId -> entity.fingerDataNosqlId,
        column.gatherData -> entity.gatherData
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.fgp, entity.fgp).and.eq(column.groupId, entity.groupId).and.eq(column.lobtype, entity.lobtype).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.fgpCase, entity.fgpCase).and.eq(column.auditStatus, entity.auditStatus).and.eq(column.description, entity.description).and.eq(column.mainPattern, entity.mainPattern).and.eq(column.vicePattern, entity.vicePattern).and.eq(column.seq, entity.seq).and.eq(column.fingerDataNosqlId, entity.fingerDataNosqlId).and.eq(column.gatherData, entity.gatherData)
    }.update.apply()
    entity
  }

  def destroy(entity: Et$008307b50001)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(Et$008307b50001).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.fgp, entity.fgp).and.eq(column.groupId, entity.groupId).and.eq(column.lobtype, entity.lobtype).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.fgpCase, entity.fgpCase).and.eq(column.auditStatus, entity.auditStatus).and.eq(column.description, entity.description).and.eq(column.mainPattern, entity.mainPattern).and.eq(column.vicePattern, entity.vicePattern).and.eq(column.seq, entity.seq).and.eq(column.fingerDataNosqlId, entity.fingerDataNosqlId).and.eq(column.gatherData, entity.gatherData) }.update.apply()
  }

}
