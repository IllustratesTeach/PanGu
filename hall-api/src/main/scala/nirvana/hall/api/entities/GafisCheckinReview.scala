package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisCheckinReview(
  pkId: String,
  checkinId: String,
  result: Option[Short] = None,
  reviewOrg: Option[String] = None,
  reviewUser: Option[String] = None,
  reason: Option[String] = None,
  reviewTime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisCheckinReview.autoSession): GafisCheckinReview = GafisCheckinReview.save(this)(session)

  def destroy()(implicit session: DBSession = GafisCheckinReview.autoSession): Unit = GafisCheckinReview.destroy(this)(session)

}


object GafisCheckinReview extends SQLSyntaxSupport[GafisCheckinReview] {

  override val tableName = "GAFIS_CHECKIN_REVIEW"

  override val columns = Seq("PK_ID", "CHECKIN_ID", "RESULT", "REVIEW_ORG", "REVIEW_USER", "REASON", "REVIEW_TIME")

  def apply(gcr: SyntaxProvider[GafisCheckinReview])(rs: WrappedResultSet): GafisCheckinReview = apply(gcr.resultName)(rs)
  def apply(gcr: ResultName[GafisCheckinReview])(rs: WrappedResultSet): GafisCheckinReview = new GafisCheckinReview(
    pkId = rs.get(gcr.pkId),
    checkinId = rs.get(gcr.checkinId),
    result = rs.get(gcr.result),
    reviewOrg = rs.get(gcr.reviewOrg),
    reviewUser = rs.get(gcr.reviewUser),
    reason = rs.get(gcr.reason),
    reviewTime = rs.get(gcr.reviewTime)
  )

  val gcr = GafisCheckinReview.syntax("gcr")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, checkinId: String, result: Option[Short], reviewOrg: Option[String], reviewUser: Option[String], reason: Option[String], reviewTime: Option[DateTime])(implicit session: DBSession = autoSession): Option[GafisCheckinReview] = {
    withSQL {
      select.from(GafisCheckinReview as gcr).where.eq(gcr.pkId, pkId).and.eq(gcr.checkinId, checkinId).and.eq(gcr.result, result).and.eq(gcr.reviewOrg, reviewOrg).and.eq(gcr.reviewUser, reviewUser).and.eq(gcr.reason, reason).and.eq(gcr.reviewTime, reviewTime)
    }.map(GafisCheckinReview(gcr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisCheckinReview] = {
    withSQL(select.from(GafisCheckinReview as gcr)).map(GafisCheckinReview(gcr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisCheckinReview as gcr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisCheckinReview] = {
    withSQL {
      select.from(GafisCheckinReview as gcr).where.append(where)
    }.map(GafisCheckinReview(gcr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisCheckinReview] = {
    withSQL {
      select.from(GafisCheckinReview as gcr).where.append(where)
    }.map(GafisCheckinReview(gcr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisCheckinReview as gcr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    checkinId: String,
    result: Option[Short] = None,
    reviewOrg: Option[String] = None,
    reviewUser: Option[String] = None,
    reason: Option[String] = None,
    reviewTime: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisCheckinReview = {
    withSQL {
      insert.into(GafisCheckinReview).columns(
        column.pkId,
        column.checkinId,
        column.result,
        column.reviewOrg,
        column.reviewUser,
        column.reason,
        column.reviewTime
      ).values(
        pkId,
        checkinId,
        result,
        reviewOrg,
        reviewUser,
        reason,
        reviewTime
      )
    }.update.apply()

    GafisCheckinReview(
      pkId = pkId,
      checkinId = checkinId,
      result = result,
      reviewOrg = reviewOrg,
      reviewUser = reviewUser,
      reason = reason,
      reviewTime = reviewTime)
  }

  def save(entity: GafisCheckinReview)(implicit session: DBSession = autoSession): GafisCheckinReview = {
    withSQL {
      update(GafisCheckinReview).set(
        column.pkId -> entity.pkId,
        column.checkinId -> entity.checkinId,
        column.result -> entity.result,
        column.reviewOrg -> entity.reviewOrg,
        column.reviewUser -> entity.reviewUser,
        column.reason -> entity.reason,
        column.reviewTime -> entity.reviewTime
      ).where.eq(column.pkId, entity.pkId).and.eq(column.checkinId, entity.checkinId).and.eq(column.result, entity.result).and.eq(column.reviewOrg, entity.reviewOrg).and.eq(column.reviewUser, entity.reviewUser).and.eq(column.reason, entity.reason).and.eq(column.reviewTime, entity.reviewTime)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisCheckinReview)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisCheckinReview).where.eq(column.pkId, entity.pkId).and.eq(column.checkinId, entity.checkinId).and.eq(column.result, entity.result).and.eq(column.reviewOrg, entity.reviewOrg).and.eq(column.reviewUser, entity.reviewUser).and.eq(column.reason, entity.reason).and.eq(column.reviewTime, entity.reviewTime) }.update.apply()
  }

}
