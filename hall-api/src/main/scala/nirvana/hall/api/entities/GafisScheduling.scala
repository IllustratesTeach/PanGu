package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisScheduling(
  pkId: String,
  departCode: Option[String] = None,
  startTime: Option[DateTime] = None,
  endTime: Option[DateTime] = None,
  dutyDepartName: Option[String] = None,
  dutyDepartCode: Option[String] = None,
  createUserId: Option[String] = None,
  createDatetime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisScheduling.autoSession): GafisScheduling = GafisScheduling.save(this)(session)

  def destroy()(implicit session: DBSession = GafisScheduling.autoSession): Unit = GafisScheduling.destroy(this)(session)

}


object GafisScheduling extends SQLSyntaxSupport[GafisScheduling] {

  override val tableName = "GAFIS_SCHEDULING"

  override val columns = Seq("PK_ID", "DEPART_CODE", "START_TIME", "END_TIME", "DUTY_DEPART_NAME", "DUTY_DEPART_CODE", "CREATE_USER_ID", "CREATE_DATETIME")

  def apply(gs: SyntaxProvider[GafisScheduling])(rs: WrappedResultSet): GafisScheduling = apply(gs.resultName)(rs)
  def apply(gs: ResultName[GafisScheduling])(rs: WrappedResultSet): GafisScheduling = new GafisScheduling(
    pkId = rs.get(gs.pkId),
    departCode = rs.get(gs.departCode),
    startTime = rs.get(gs.startTime),
    endTime = rs.get(gs.endTime),
    dutyDepartName = rs.get(gs.dutyDepartName),
    dutyDepartCode = rs.get(gs.dutyDepartCode),
    createUserId = rs.get(gs.createUserId),
    createDatetime = rs.get(gs.createDatetime)
  )

  val gs = GafisScheduling.syntax("gs")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, departCode: Option[String], startTime: Option[DateTime], endTime: Option[DateTime], dutyDepartName: Option[String], dutyDepartCode: Option[String], createUserId: Option[String], createDatetime: Option[DateTime])(implicit session: DBSession = autoSession): Option[GafisScheduling] = {
    withSQL {
      select.from(GafisScheduling as gs).where.eq(gs.pkId, pkId).and.eq(gs.departCode, departCode).and.eq(gs.startTime, startTime).and.eq(gs.endTime, endTime).and.eq(gs.dutyDepartName, dutyDepartName).and.eq(gs.dutyDepartCode, dutyDepartCode).and.eq(gs.createUserId, createUserId).and.eq(gs.createDatetime, createDatetime)
    }.map(GafisScheduling(gs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisScheduling] = {
    withSQL(select.from(GafisScheduling as gs)).map(GafisScheduling(gs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisScheduling as gs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisScheduling] = {
    withSQL {
      select.from(GafisScheduling as gs).where.append(where)
    }.map(GafisScheduling(gs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisScheduling] = {
    withSQL {
      select.from(GafisScheduling as gs).where.append(where)
    }.map(GafisScheduling(gs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisScheduling as gs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    departCode: Option[String] = None,
    startTime: Option[DateTime] = None,
    endTime: Option[DateTime] = None,
    dutyDepartName: Option[String] = None,
    dutyDepartCode: Option[String] = None,
    createUserId: Option[String] = None,
    createDatetime: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisScheduling = {
    withSQL {
      insert.into(GafisScheduling).columns(
        column.pkId,
        column.departCode,
        column.startTime,
        column.endTime,
        column.dutyDepartName,
        column.dutyDepartCode,
        column.createUserId,
        column.createDatetime
      ).values(
        pkId,
        departCode,
        startTime,
        endTime,
        dutyDepartName,
        dutyDepartCode,
        createUserId,
        createDatetime
      )
    }.update.apply()

    GafisScheduling(
      pkId = pkId,
      departCode = departCode,
      startTime = startTime,
      endTime = endTime,
      dutyDepartName = dutyDepartName,
      dutyDepartCode = dutyDepartCode,
      createUserId = createUserId,
      createDatetime = createDatetime)
  }

  def save(entity: GafisScheduling)(implicit session: DBSession = autoSession): GafisScheduling = {
    withSQL {
      update(GafisScheduling).set(
        column.pkId -> entity.pkId,
        column.departCode -> entity.departCode,
        column.startTime -> entity.startTime,
        column.endTime -> entity.endTime,
        column.dutyDepartName -> entity.dutyDepartName,
        column.dutyDepartCode -> entity.dutyDepartCode,
        column.createUserId -> entity.createUserId,
        column.createDatetime -> entity.createDatetime
      ).where.eq(column.pkId, entity.pkId).and.eq(column.departCode, entity.departCode).and.eq(column.startTime, entity.startTime).and.eq(column.endTime, entity.endTime).and.eq(column.dutyDepartName, entity.dutyDepartName).and.eq(column.dutyDepartCode, entity.dutyDepartCode).and.eq(column.createUserId, entity.createUserId).and.eq(column.createDatetime, entity.createDatetime)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisScheduling)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisScheduling).where.eq(column.pkId, entity.pkId).and.eq(column.departCode, entity.departCode).and.eq(column.startTime, entity.startTime).and.eq(column.endTime, entity.endTime).and.eq(column.dutyDepartName, entity.dutyDepartName).and.eq(column.dutyDepartCode, entity.dutyDepartCode).and.eq(column.createUserId, entity.createUserId).and.eq(column.createDatetime, entity.createDatetime) }.update.apply()
  }

}
