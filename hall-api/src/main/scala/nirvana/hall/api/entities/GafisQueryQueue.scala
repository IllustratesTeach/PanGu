package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisQueryQueue(
  pkId: String,
  queryId: Option[String] = None,
  personId: Option[String] = None,
  createDatetime: Option[DateTime] = None,
  completeDate: Option[DateTime] = None,
  createUser: Option[String] = None,
  status: Option[Long] = None,
  message: Option[String] = None,
  queryType: Option[String] = None,
  gatherOrgCode: Option[String] = None,
  obtainResultTimes: Option[Long] = None) {

  def save()(implicit session: DBSession = GafisQueryQueue.autoSession): GafisQueryQueue = GafisQueryQueue.save(this)(session)

  def destroy()(implicit session: DBSession = GafisQueryQueue.autoSession): Unit = GafisQueryQueue.destroy(this)(session)

}


object GafisQueryQueue extends SQLSyntaxSupport[GafisQueryQueue] {

  override val tableName = "GAFIS_QUERY_QUEUE"

  override val columns = Seq("PK_ID", "QUERY_ID", "PERSON_ID", "CREATE_DATETIME", "COMPLETE_DATE", "CREATE_USER", "STATUS", "MESSAGE", "QUERY_TYPE", "GATHER_ORG_CODE", "OBTAIN_RESULT_TIMES")

  def apply(gqq: SyntaxProvider[GafisQueryQueue])(rs: WrappedResultSet): GafisQueryQueue = apply(gqq.resultName)(rs)
  def apply(gqq: ResultName[GafisQueryQueue])(rs: WrappedResultSet): GafisQueryQueue = new GafisQueryQueue(
    pkId = rs.get(gqq.pkId),
    queryId = rs.get(gqq.queryId),
    personId = rs.get(gqq.personId),
    createDatetime = rs.get(gqq.createDatetime),
    completeDate = rs.get(gqq.completeDate),
    createUser = rs.get(gqq.createUser),
    status = rs.get(gqq.status),
    message = rs.get(gqq.message),
    queryType = rs.get(gqq.queryType),
    gatherOrgCode = rs.get(gqq.gatherOrgCode),
    obtainResultTimes = rs.get(gqq.obtainResultTimes)
  )

  val gqq = GafisQueryQueue.syntax("gqq")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, queryId: Option[String], personId: Option[String], createDatetime: Option[DateTime], completeDate: Option[DateTime], createUser: Option[String], status: Option[Long], message: Option[String], queryType: Option[String], gatherOrgCode: Option[String], obtainResultTimes: Option[Long])(implicit session: DBSession = autoSession): Option[GafisQueryQueue] = {
    withSQL {
      select.from(GafisQueryQueue as gqq).where.eq(gqq.pkId, pkId).and.eq(gqq.queryId, queryId).and.eq(gqq.personId, personId).and.eq(gqq.createDatetime, createDatetime).and.eq(gqq.completeDate, completeDate).and.eq(gqq.createUser, createUser).and.eq(gqq.status, status).and.eq(gqq.message, message).and.eq(gqq.queryType, queryType).and.eq(gqq.gatherOrgCode, gatherOrgCode).and.eq(gqq.obtainResultTimes, obtainResultTimes)
    }.map(GafisQueryQueue(gqq.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisQueryQueue] = {
    withSQL(select.from(GafisQueryQueue as gqq)).map(GafisQueryQueue(gqq.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisQueryQueue as gqq)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisQueryQueue] = {
    withSQL {
      select.from(GafisQueryQueue as gqq).where.append(where)
    }.map(GafisQueryQueue(gqq.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisQueryQueue] = {
    withSQL {
      select.from(GafisQueryQueue as gqq).where.append(where)
    }.map(GafisQueryQueue(gqq.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisQueryQueue as gqq).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    queryId: Option[String] = None,
    personId: Option[String] = None,
    createDatetime: Option[DateTime] = None,
    completeDate: Option[DateTime] = None,
    createUser: Option[String] = None,
    status: Option[Long] = None,
    message: Option[String] = None,
    queryType: Option[String] = None,
    gatherOrgCode: Option[String] = None,
    obtainResultTimes: Option[Long] = None)(implicit session: DBSession = autoSession): GafisQueryQueue = {
    withSQL {
      insert.into(GafisQueryQueue).columns(
        column.pkId,
        column.queryId,
        column.personId,
        column.createDatetime,
        column.completeDate,
        column.createUser,
        column.status,
        column.message,
        column.queryType,
        column.gatherOrgCode,
        column.obtainResultTimes
      ).values(
        pkId,
        queryId,
        personId,
        createDatetime,
        completeDate,
        createUser,
        status,
        message,
        queryType,
        gatherOrgCode,
        obtainResultTimes
      )
    }.update.apply()

    GafisQueryQueue(
      pkId = pkId,
      queryId = queryId,
      personId = personId,
      createDatetime = createDatetime,
      completeDate = completeDate,
      createUser = createUser,
      status = status,
      message = message,
      queryType = queryType,
      gatherOrgCode = gatherOrgCode,
      obtainResultTimes = obtainResultTimes)
  }

  def save(entity: GafisQueryQueue)(implicit session: DBSession = autoSession): GafisQueryQueue = {
    withSQL {
      update(GafisQueryQueue).set(
        column.pkId -> entity.pkId,
        column.queryId -> entity.queryId,
        column.personId -> entity.personId,
        column.createDatetime -> entity.createDatetime,
        column.completeDate -> entity.completeDate,
        column.createUser -> entity.createUser,
        column.status -> entity.status,
        column.message -> entity.message,
        column.queryType -> entity.queryType,
        column.gatherOrgCode -> entity.gatherOrgCode,
        column.obtainResultTimes -> entity.obtainResultTimes
      ).where.eq(column.pkId, entity.pkId).and.eq(column.queryId, entity.queryId).and.eq(column.personId, entity.personId).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.completeDate, entity.completeDate).and.eq(column.createUser, entity.createUser).and.eq(column.status, entity.status).and.eq(column.message, entity.message).and.eq(column.queryType, entity.queryType).and.eq(column.gatherOrgCode, entity.gatherOrgCode).and.eq(column.obtainResultTimes, entity.obtainResultTimes)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisQueryQueue)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisQueryQueue).where.eq(column.pkId, entity.pkId).and.eq(column.queryId, entity.queryId).and.eq(column.personId, entity.personId).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.completeDate, entity.completeDate).and.eq(column.createUser, entity.createUser).and.eq(column.status, entity.status).and.eq(column.message, entity.message).and.eq(column.queryType, entity.queryType).and.eq(column.gatherOrgCode, entity.gatherOrgCode).and.eq(column.obtainResultTimes, entity.obtainResultTimes) }.update.apply()
  }

}
