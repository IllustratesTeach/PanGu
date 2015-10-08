package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisCheckinAppeal(
  pkId: String,
  checkinId: String,
  appealUser: Option[String] = None,
  appealOrg: Option[String] = None,
  appealTime: Option[DateTime] = None,
  reason: Option[String] = None,
  status: Option[Short] = None,
  acceptUser: Option[String] = None,
  acceptOrg: Option[String] = None,
  acceptTime: Option[DateTime] = None,
  acceptReason: Option[String] = None) {

  def save()(implicit session: DBSession = GafisCheckinAppeal.autoSession): GafisCheckinAppeal = GafisCheckinAppeal.save(this)(session)

  def destroy()(implicit session: DBSession = GafisCheckinAppeal.autoSession): Unit = GafisCheckinAppeal.destroy(this)(session)

}


object GafisCheckinAppeal extends SQLSyntaxSupport[GafisCheckinAppeal] {

  override val tableName = "GAFIS_CHECKIN_APPEAL"

  override val columns = Seq("PK_ID", "CHECKIN_ID", "APPEAL_USER", "APPEAL_ORG", "APPEAL_TIME", "REASON", "STATUS", "ACCEPT_USER", "ACCEPT_ORG", "ACCEPT_TIME", "ACCEPT_REASON")

  def apply(gca: SyntaxProvider[GafisCheckinAppeal])(rs: WrappedResultSet): GafisCheckinAppeal = apply(gca.resultName)(rs)
  def apply(gca: ResultName[GafisCheckinAppeal])(rs: WrappedResultSet): GafisCheckinAppeal = new GafisCheckinAppeal(
    pkId = rs.get(gca.pkId),
    checkinId = rs.get(gca.checkinId),
    appealUser = rs.get(gca.appealUser),
    appealOrg = rs.get(gca.appealOrg),
    appealTime = rs.get(gca.appealTime),
    reason = rs.get(gca.reason),
    status = rs.get(gca.status),
    acceptUser = rs.get(gca.acceptUser),
    acceptOrg = rs.get(gca.acceptOrg),
    acceptTime = rs.get(gca.acceptTime),
    acceptReason = rs.get(gca.acceptReason)
  )

  val gca = GafisCheckinAppeal.syntax("gca")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, checkinId: String, appealUser: Option[String], appealOrg: Option[String], appealTime: Option[DateTime], reason: Option[String], status: Option[Short], acceptUser: Option[String], acceptOrg: Option[String], acceptTime: Option[DateTime], acceptReason: Option[String])(implicit session: DBSession = autoSession): Option[GafisCheckinAppeal] = {
    withSQL {
      select.from(GafisCheckinAppeal as gca).where.eq(gca.pkId, pkId).and.eq(gca.checkinId, checkinId).and.eq(gca.appealUser, appealUser).and.eq(gca.appealOrg, appealOrg).and.eq(gca.appealTime, appealTime).and.eq(gca.reason, reason).and.eq(gca.status, status).and.eq(gca.acceptUser, acceptUser).and.eq(gca.acceptOrg, acceptOrg).and.eq(gca.acceptTime, acceptTime).and.eq(gca.acceptReason, acceptReason)
    }.map(GafisCheckinAppeal(gca.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisCheckinAppeal] = {
    withSQL(select.from(GafisCheckinAppeal as gca)).map(GafisCheckinAppeal(gca.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisCheckinAppeal as gca)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisCheckinAppeal] = {
    withSQL {
      select.from(GafisCheckinAppeal as gca).where.append(where)
    }.map(GafisCheckinAppeal(gca.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisCheckinAppeal] = {
    withSQL {
      select.from(GafisCheckinAppeal as gca).where.append(where)
    }.map(GafisCheckinAppeal(gca.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisCheckinAppeal as gca).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    checkinId: String,
    appealUser: Option[String] = None,
    appealOrg: Option[String] = None,
    appealTime: Option[DateTime] = None,
    reason: Option[String] = None,
    status: Option[Short] = None,
    acceptUser: Option[String] = None,
    acceptOrg: Option[String] = None,
    acceptTime: Option[DateTime] = None,
    acceptReason: Option[String] = None)(implicit session: DBSession = autoSession): GafisCheckinAppeal = {
    withSQL {
      insert.into(GafisCheckinAppeal).columns(
        column.pkId,
        column.checkinId,
        column.appealUser,
        column.appealOrg,
        column.appealTime,
        column.reason,
        column.status,
        column.acceptUser,
        column.acceptOrg,
        column.acceptTime,
        column.acceptReason
      ).values(
        pkId,
        checkinId,
        appealUser,
        appealOrg,
        appealTime,
        reason,
        status,
        acceptUser,
        acceptOrg,
        acceptTime,
        acceptReason
      )
    }.update.apply()

    GafisCheckinAppeal(
      pkId = pkId,
      checkinId = checkinId,
      appealUser = appealUser,
      appealOrg = appealOrg,
      appealTime = appealTime,
      reason = reason,
      status = status,
      acceptUser = acceptUser,
      acceptOrg = acceptOrg,
      acceptTime = acceptTime,
      acceptReason = acceptReason)
  }

  def save(entity: GafisCheckinAppeal)(implicit session: DBSession = autoSession): GafisCheckinAppeal = {
    withSQL {
      update(GafisCheckinAppeal).set(
        column.pkId -> entity.pkId,
        column.checkinId -> entity.checkinId,
        column.appealUser -> entity.appealUser,
        column.appealOrg -> entity.appealOrg,
        column.appealTime -> entity.appealTime,
        column.reason -> entity.reason,
        column.status -> entity.status,
        column.acceptUser -> entity.acceptUser,
        column.acceptOrg -> entity.acceptOrg,
        column.acceptTime -> entity.acceptTime,
        column.acceptReason -> entity.acceptReason
      ).where.eq(column.pkId, entity.pkId).and.eq(column.checkinId, entity.checkinId).and.eq(column.appealUser, entity.appealUser).and.eq(column.appealOrg, entity.appealOrg).and.eq(column.appealTime, entity.appealTime).and.eq(column.reason, entity.reason).and.eq(column.status, entity.status).and.eq(column.acceptUser, entity.acceptUser).and.eq(column.acceptOrg, entity.acceptOrg).and.eq(column.acceptTime, entity.acceptTime).and.eq(column.acceptReason, entity.acceptReason)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisCheckinAppeal)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisCheckinAppeal).where.eq(column.pkId, entity.pkId).and.eq(column.checkinId, entity.checkinId).and.eq(column.appealUser, entity.appealUser).and.eq(column.appealOrg, entity.appealOrg).and.eq(column.appealTime, entity.appealTime).and.eq(column.reason, entity.reason).and.eq(column.status, entity.status).and.eq(column.acceptUser, entity.acceptUser).and.eq(column.acceptOrg, entity.acceptOrg).and.eq(column.acceptTime, entity.acceptTime).and.eq(column.acceptReason, entity.acceptReason) }.update.apply()
  }

}
