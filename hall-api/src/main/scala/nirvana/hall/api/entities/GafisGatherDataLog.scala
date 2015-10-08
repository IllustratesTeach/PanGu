package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisGatherDataLog(
  pkId: String,
  userId: Option[String] = None,
  userName: Option[String] = None,
  unitCode: Option[String] = None,
  unitName: Option[String] = None,
  ip: Option[String] = None,
  objectId: Option[String] = None,
  tableName: Option[String] = None,
  fgp: Option[String] = None,
  oldData: Option[Blob] = None,
  newData: Option[Blob] = None,
  lobtype: Option[Short] = None,
  createDatetime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisGatherDataLog.autoSession): GafisGatherDataLog = GafisGatherDataLog.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherDataLog.autoSession): Unit = GafisGatherDataLog.destroy(this)(session)

}


object GafisGatherDataLog extends SQLSyntaxSupport[GafisGatherDataLog] {

  override val tableName = "GAFIS_GATHER_DATA_LOG"

  override val columns = Seq("PK_ID", "USER_ID", "USER_NAME", "UNIT_CODE", "UNIT_NAME", "IP", "OBJECT_ID", "TABLE_NAME", "FGP", "OLD_DATA", "NEW_DATA", "LOBTYPE", "CREATE_DATETIME")

  def apply(ggdl: SyntaxProvider[GafisGatherDataLog])(rs: WrappedResultSet): GafisGatherDataLog = apply(ggdl.resultName)(rs)
  def apply(ggdl: ResultName[GafisGatherDataLog])(rs: WrappedResultSet): GafisGatherDataLog = new GafisGatherDataLog(
    pkId = rs.get(ggdl.pkId),
    userId = rs.get(ggdl.userId),
    userName = rs.get(ggdl.userName),
    unitCode = rs.get(ggdl.unitCode),
    unitName = rs.get(ggdl.unitName),
    ip = rs.get(ggdl.ip),
    objectId = rs.get(ggdl.objectId),
    tableName = rs.get(ggdl.tableName),
    fgp = rs.get(ggdl.fgp),
    oldData = rs.get(ggdl.oldData),
    newData = rs.get(ggdl.newData),
    lobtype = rs.get(ggdl.lobtype),
    createDatetime = rs.get(ggdl.createDatetime)
  )

  val ggdl = GafisGatherDataLog.syntax("ggdl")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, userId: Option[String], userName: Option[String], unitCode: Option[String], unitName: Option[String], ip: Option[String], objectId: Option[String], tableName: Option[String], fgp: Option[String], oldData: Option[Blob], newData: Option[Blob], lobtype: Option[Short], createDatetime: Option[DateTime])(implicit session: DBSession = autoSession): Option[GafisGatherDataLog] = {
    withSQL {
      select.from(GafisGatherDataLog as ggdl).where.eq(ggdl.pkId, pkId).and.eq(ggdl.userId, userId).and.eq(ggdl.userName, userName).and.eq(ggdl.unitCode, unitCode).and.eq(ggdl.unitName, unitName).and.eq(ggdl.ip, ip).and.eq(ggdl.objectId, objectId).and.eq(ggdl.tableName, tableName).and.eq(ggdl.fgp, fgp).and.eq(ggdl.oldData, oldData).and.eq(ggdl.newData, newData).and.eq(ggdl.lobtype, lobtype).and.eq(ggdl.createDatetime, createDatetime)
    }.map(GafisGatherDataLog(ggdl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherDataLog] = {
    withSQL(select.from(GafisGatherDataLog as ggdl)).map(GafisGatherDataLog(ggdl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherDataLog as ggdl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherDataLog] = {
    withSQL {
      select.from(GafisGatherDataLog as ggdl).where.append(where)
    }.map(GafisGatherDataLog(ggdl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherDataLog] = {
    withSQL {
      select.from(GafisGatherDataLog as ggdl).where.append(where)
    }.map(GafisGatherDataLog(ggdl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherDataLog as ggdl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    userId: Option[String] = None,
    userName: Option[String] = None,
    unitCode: Option[String] = None,
    unitName: Option[String] = None,
    ip: Option[String] = None,
    objectId: Option[String] = None,
    tableName: Option[String] = None,
    fgp: Option[String] = None,
    oldData: Option[Blob] = None,
    newData: Option[Blob] = None,
    lobtype: Option[Short] = None,
    createDatetime: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisGatherDataLog = {
    withSQL {
      insert.into(GafisGatherDataLog).columns(
        column.pkId,
        column.userId,
        column.userName,
        column.unitCode,
        column.unitName,
        column.ip,
        column.objectId,
        column.tableName,
        column.fgp,
        column.oldData,
        column.newData,
        column.lobtype,
        column.createDatetime
      ).values(
        pkId,
        userId,
        userName,
        unitCode,
        unitName,
        ip,
        objectId,
        tableName,
        fgp,
        oldData,
        newData,
        lobtype,
        createDatetime
      )
    }.update.apply()

    GafisGatherDataLog(
      pkId = pkId,
      userId = userId,
      userName = userName,
      unitCode = unitCode,
      unitName = unitName,
      ip = ip,
      objectId = objectId,
      tableName = tableName,
      fgp = fgp,
      oldData = oldData,
      newData = newData,
      lobtype = lobtype,
      createDatetime = createDatetime)
  }

  def save(entity: GafisGatherDataLog)(implicit session: DBSession = autoSession): GafisGatherDataLog = {
    withSQL {
      update(GafisGatherDataLog).set(
        column.pkId -> entity.pkId,
        column.userId -> entity.userId,
        column.userName -> entity.userName,
        column.unitCode -> entity.unitCode,
        column.unitName -> entity.unitName,
        column.ip -> entity.ip,
        column.objectId -> entity.objectId,
        column.tableName -> entity.tableName,
        column.fgp -> entity.fgp,
        column.oldData -> entity.oldData,
        column.newData -> entity.newData,
        column.lobtype -> entity.lobtype,
        column.createDatetime -> entity.createDatetime
      ).where.eq(column.pkId, entity.pkId).and.eq(column.userId, entity.userId).and.eq(column.userName, entity.userName).and.eq(column.unitCode, entity.unitCode).and.eq(column.unitName, entity.unitName).and.eq(column.ip, entity.ip).and.eq(column.objectId, entity.objectId).and.eq(column.tableName, entity.tableName).and.eq(column.fgp, entity.fgp).and.eq(column.oldData, entity.oldData).and.eq(column.newData, entity.newData).and.eq(column.lobtype, entity.lobtype).and.eq(column.createDatetime, entity.createDatetime)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherDataLog)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherDataLog).where.eq(column.pkId, entity.pkId).and.eq(column.userId, entity.userId).and.eq(column.userName, entity.userName).and.eq(column.unitCode, entity.unitCode).and.eq(column.unitName, entity.unitName).and.eq(column.ip, entity.ip).and.eq(column.objectId, entity.objectId).and.eq(column.tableName, entity.tableName).and.eq(column.fgp, entity.fgp).and.eq(column.oldData, entity.oldData).and.eq(column.newData, entity.newData).and.eq(column.lobtype, entity.lobtype).and.eq(column.createDatetime, entity.createDatetime) }.update.apply()
  }

}
