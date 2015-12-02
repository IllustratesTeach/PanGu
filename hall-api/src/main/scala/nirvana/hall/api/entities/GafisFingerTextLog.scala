package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisFingerTextLog(
  pkId: String,
  userId: Option[String] = None,
  userName: Option[String] = None,
  unitCode: Option[String] = None,
  unitName: Option[String] = None,
  ip: Option[String] = None,
  objectId: Option[String] = None,
  tableName: Option[String] = None,
  oldInfo: Option[String] = None,
  newInfo: Option[String] = None,
  createDatetime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisFingerTextLog.autoSession): GafisFingerTextLog = GafisFingerTextLog.save(this)(session)

  def destroy()(implicit session: DBSession = GafisFingerTextLog.autoSession): Unit = GafisFingerTextLog.destroy(this)(session)

}


object GafisFingerTextLog extends SQLSyntaxSupport[GafisFingerTextLog] {

  override val tableName = "GAFIS_FINGER_TEXT_LOG"

  override val columns = Seq("PK_ID", "USER_ID", "USER_NAME", "UNIT_CODE", "UNIT_NAME", "IP", "OBJECT_ID", "TABLE_NAME", "OLD_INFO", "NEW_INFO", "CREATE_DATETIME")

  def apply(gftl: SyntaxProvider[GafisFingerTextLog])(rs: WrappedResultSet): GafisFingerTextLog = apply(gftl.resultName)(rs)
  def apply(gftl: ResultName[GafisFingerTextLog])(rs: WrappedResultSet): GafisFingerTextLog = new GafisFingerTextLog(
    pkId = rs.get(gftl.pkId),
    userId = rs.get(gftl.userId),
    userName = rs.get(gftl.userName),
    unitCode = rs.get(gftl.unitCode),
    unitName = rs.get(gftl.unitName),
    ip = rs.get(gftl.ip),
    objectId = rs.get(gftl.objectId),
    tableName = rs.get(gftl.tableName),
    oldInfo = rs.get(gftl.oldInfo),
    newInfo = rs.get(gftl.newInfo),
    createDatetime = rs.get(gftl.createDatetime)
  )

  val gftl = GafisFingerTextLog.syntax("gftl")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[GafisFingerTextLog] = {
    withSQL {
      select.from(GafisFingerTextLog as gftl).where.eq(gftl.pkId, pkId)
    }.map(GafisFingerTextLog(gftl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisFingerTextLog] = {
    withSQL(select.from(GafisFingerTextLog as gftl)).map(GafisFingerTextLog(gftl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisFingerTextLog as gftl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisFingerTextLog] = {
    withSQL {
      select.from(GafisFingerTextLog as gftl).where.append(where)
    }.map(GafisFingerTextLog(gftl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisFingerTextLog] = {
    withSQL {
      select.from(GafisFingerTextLog as gftl).where.append(where)
    }.map(GafisFingerTextLog(gftl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisFingerTextLog as gftl).where.append(where)
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
    oldInfo: Option[String] = None,
    newInfo: Option[String] = None,
    createDatetime: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisFingerTextLog = {
    withSQL {
      insert.into(GafisFingerTextLog).columns(
        column.pkId,
        column.userId,
        column.userName,
        column.unitCode,
        column.unitName,
        column.ip,
        column.objectId,
        column.tableName,
        column.oldInfo,
        column.newInfo,
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
        oldInfo,
        newInfo,
        createDatetime
      )
    }.update.apply()

    GafisFingerTextLog(
      pkId = pkId,
      userId = userId,
      userName = userName,
      unitCode = unitCode,
      unitName = unitName,
      ip = ip,
      objectId = objectId,
      tableName = tableName,
      oldInfo = oldInfo,
      newInfo = newInfo,
      createDatetime = createDatetime)
  }

  def save(entity: GafisFingerTextLog)(implicit session: DBSession = autoSession): GafisFingerTextLog = {
    withSQL {
      update(GafisFingerTextLog).set(
        column.pkId -> entity.pkId,
        column.userId -> entity.userId,
        column.userName -> entity.userName,
        column.unitCode -> entity.unitCode,
        column.unitName -> entity.unitName,
        column.ip -> entity.ip,
        column.objectId -> entity.objectId,
        column.tableName -> entity.tableName,
        column.oldInfo -> entity.oldInfo,
        column.newInfo -> entity.newInfo,
        column.createDatetime -> entity.createDatetime
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisFingerTextLog)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisFingerTextLog).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
