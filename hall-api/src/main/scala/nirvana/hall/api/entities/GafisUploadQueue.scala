package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisUploadQueue(
  pkId: String,
  personId: Option[String] = None,
  createDatetime: Option[DateTime] = None,
  completeDate: Option[DateTime] = None,
  createUser: Option[String] = None,
  status: Option[Long] = None,
  message: Option[String] = None,
  ttStatus: Option[Long] = None,
  tlStatus: Option[Long] = None,
  gatherOrgCode: Option[String] = None,
  queryType: Option[Long] = None) {

  def save()(implicit session: DBSession = GafisUploadQueue.autoSession): GafisUploadQueue = GafisUploadQueue.save(this)(session)

  def destroy()(implicit session: DBSession = GafisUploadQueue.autoSession): Unit = GafisUploadQueue.destroy(this)(session)

}


object GafisUploadQueue extends SQLSyntaxSupport[GafisUploadQueue] {

  override val tableName = "GAFIS_UPLOAD_QUEUE"

  override val columns = Seq("PK_ID", "PERSON_ID", "CREATE_DATETIME", "COMPLETE_DATE", "CREATE_USER", "STATUS", "MESSAGE", "TT_STATUS", "TL_STATUS", "GATHER_ORG_CODE", "QUERY_TYPE")

  def apply(guq: SyntaxProvider[GafisUploadQueue])(rs: WrappedResultSet): GafisUploadQueue = apply(guq.resultName)(rs)
  def apply(guq: ResultName[GafisUploadQueue])(rs: WrappedResultSet): GafisUploadQueue = new GafisUploadQueue(
    pkId = rs.get(guq.pkId),
    personId = rs.get(guq.personId),
    createDatetime = rs.get(guq.createDatetime),
    completeDate = rs.get(guq.completeDate),
    createUser = rs.get(guq.createUser),
    status = rs.get(guq.status),
    message = rs.get(guq.message),
    ttStatus = rs.get(guq.ttStatus),
    tlStatus = rs.get(guq.tlStatus),
    gatherOrgCode = rs.get(guq.gatherOrgCode),
    queryType = rs.get(guq.queryType)
  )

  val guq = GafisUploadQueue.syntax("guq")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personId: Option[String], createDatetime: Option[DateTime], completeDate: Option[DateTime], createUser: Option[String], status: Option[Long], message: Option[String], ttStatus: Option[Long], tlStatus: Option[Long], gatherOrgCode: Option[String], queryType: Option[Long])(implicit session: DBSession = autoSession): Option[GafisUploadQueue] = {
    withSQL {
      select.from(GafisUploadQueue as guq).where.eq(guq.pkId, pkId).and.eq(guq.personId, personId).and.eq(guq.createDatetime, createDatetime).and.eq(guq.completeDate, completeDate).and.eq(guq.createUser, createUser).and.eq(guq.status, status).and.eq(guq.message, message).and.eq(guq.ttStatus, ttStatus).and.eq(guq.tlStatus, tlStatus).and.eq(guq.gatherOrgCode, gatherOrgCode).and.eq(guq.queryType, queryType)
    }.map(GafisUploadQueue(guq.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisUploadQueue] = {
    withSQL(select.from(GafisUploadQueue as guq)).map(GafisUploadQueue(guq.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisUploadQueue as guq)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisUploadQueue] = {
    withSQL {
      select.from(GafisUploadQueue as guq).where.append(where)
    }.map(GafisUploadQueue(guq.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisUploadQueue] = {
    withSQL {
      select.from(GafisUploadQueue as guq).where.append(where)
    }.map(GafisUploadQueue(guq.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisUploadQueue as guq).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personId: Option[String] = None,
    createDatetime: Option[DateTime] = None,
    completeDate: Option[DateTime] = None,
    createUser: Option[String] = None,
    status: Option[Long] = None,
    message: Option[String] = None,
    ttStatus: Option[Long] = None,
    tlStatus: Option[Long] = None,
    gatherOrgCode: Option[String] = None,
    queryType: Option[Long] = None)(implicit session: DBSession = autoSession): GafisUploadQueue = {
    withSQL {
      insert.into(GafisUploadQueue).columns(
        column.pkId,
        column.personId,
        column.createDatetime,
        column.completeDate,
        column.createUser,
        column.status,
        column.message,
        column.ttStatus,
        column.tlStatus,
        column.gatherOrgCode,
        column.queryType
      ).values(
        pkId,
        personId,
        createDatetime,
        completeDate,
        createUser,
        status,
        message,
        ttStatus,
        tlStatus,
        gatherOrgCode,
        queryType
      )
    }.update.apply()

    GafisUploadQueue(
      pkId = pkId,
      personId = personId,
      createDatetime = createDatetime,
      completeDate = completeDate,
      createUser = createUser,
      status = status,
      message = message,
      ttStatus = ttStatus,
      tlStatus = tlStatus,
      gatherOrgCode = gatherOrgCode,
      queryType = queryType)
  }

  def save(entity: GafisUploadQueue)(implicit session: DBSession = autoSession): GafisUploadQueue = {
    withSQL {
      update(GafisUploadQueue).set(
        column.pkId -> entity.pkId,
        column.personId -> entity.personId,
        column.createDatetime -> entity.createDatetime,
        column.completeDate -> entity.completeDate,
        column.createUser -> entity.createUser,
        column.status -> entity.status,
        column.message -> entity.message,
        column.ttStatus -> entity.ttStatus,
        column.tlStatus -> entity.tlStatus,
        column.gatherOrgCode -> entity.gatherOrgCode,
        column.queryType -> entity.queryType
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.completeDate, entity.completeDate).and.eq(column.createUser, entity.createUser).and.eq(column.status, entity.status).and.eq(column.message, entity.message).and.eq(column.ttStatus, entity.ttStatus).and.eq(column.tlStatus, entity.tlStatus).and.eq(column.gatherOrgCode, entity.gatherOrgCode).and.eq(column.queryType, entity.queryType)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisUploadQueue)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisUploadQueue).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.completeDate, entity.completeDate).and.eq(column.createUser, entity.createUser).and.eq(column.status, entity.status).and.eq(column.message, entity.message).and.eq(column.ttStatus, entity.ttStatus).and.eq(column.tlStatus, entity.tlStatus).and.eq(column.gatherOrgCode, entity.gatherOrgCode).and.eq(column.queryType, entity.queryType) }.update.apply()
  }

}
