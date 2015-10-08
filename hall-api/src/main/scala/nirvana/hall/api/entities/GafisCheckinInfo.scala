package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisCheckinInfo(
  pkId: String,
  code: Option[String] = None,
  tcode: Option[String] = None,
  querytype: Option[Short] = None,
  registerTime: Option[DateTime] = None,
  registerUser: Option[String] = None,
  registerOrg: Option[String] = None,
  hitpossibility: Option[Short] = None,
  priority: Option[Short] = None,
  reviewStatus: Option[Short] = None,
  reviewBout: Option[Short] = None,
  appealStatus: Option[Short] = None,
  confirmStatus: Option[Short] = None,
  confirmUser: Option[String] = None,
  confirmTime: Option[DateTime] = None,
  queryUuid: Option[String] = None,
  reviewOrg: Option[String] = None,
  rank: Option[Int] = None,
  fraction: Option[Int] = None,
  fgp: Option[String] = None,
  confirmOpinion: Option[String] = None,
  cardType1: Option[Long] = None,
  cardType2: Option[Long] = None,
  lastHandleDate: Option[DateTime] = None,
  operatetype: Option[Long] = None,
  ckSource: Option[String] = None) {

  def save()(implicit session: DBSession = GafisCheckinInfo.autoSession): GafisCheckinInfo = GafisCheckinInfo.save(this)(session)

  def destroy()(implicit session: DBSession = GafisCheckinInfo.autoSession): Unit = GafisCheckinInfo.destroy(this)(session)

}


object GafisCheckinInfo extends SQLSyntaxSupport[GafisCheckinInfo] {

  override val tableName = "GAFIS_CHECKIN_INFO"

  override val columns = Seq("PK_ID", "CODE", "TCODE", "QUERYTYPE", "REGISTER_TIME", "REGISTER_USER", "REGISTER_ORG", "HITPOSSIBILITY", "PRIORITY", "REVIEW_STATUS", "REVIEW_BOUT", "APPEAL_STATUS", "CONFIRM_STATUS", "CONFIRM_USER", "CONFIRM_TIME", "QUERY_UUID", "REVIEW_ORG", "RANK", "FRACTION", "FGP", "CONFIRM_OPINION", "CARD_TYPE1", "CARD_TYPE2", "LAST_HANDLE_DATE", "OPERATETYPE", "CK_SOURCE")

  def apply(gci: SyntaxProvider[GafisCheckinInfo])(rs: WrappedResultSet): GafisCheckinInfo = apply(gci.resultName)(rs)
  def apply(gci: ResultName[GafisCheckinInfo])(rs: WrappedResultSet): GafisCheckinInfo = new GafisCheckinInfo(
    pkId = rs.get(gci.pkId),
    code = rs.get(gci.code),
    tcode = rs.get(gci.tcode),
    querytype = rs.get(gci.querytype),
    registerTime = rs.get(gci.registerTime),
    registerUser = rs.get(gci.registerUser),
    registerOrg = rs.get(gci.registerOrg),
    hitpossibility = rs.get(gci.hitpossibility),
    priority = rs.get(gci.priority),
    reviewStatus = rs.get(gci.reviewStatus),
    reviewBout = rs.get(gci.reviewBout),
    appealStatus = rs.get(gci.appealStatus),
    confirmStatus = rs.get(gci.confirmStatus),
    confirmUser = rs.get(gci.confirmUser),
    confirmTime = rs.get(gci.confirmTime),
    queryUuid = rs.get(gci.queryUuid),
    reviewOrg = rs.get(gci.reviewOrg),
    rank = rs.get(gci.rank),
    fraction = rs.get(gci.fraction),
    fgp = rs.get(gci.fgp),
    confirmOpinion = rs.get(gci.confirmOpinion),
    cardType1 = rs.get(gci.cardType1),
    cardType2 = rs.get(gci.cardType2),
    lastHandleDate = rs.get(gci.lastHandleDate),
    operatetype = rs.get(gci.operatetype),
    ckSource = rs.get(gci.ckSource)
  )

  val gci = GafisCheckinInfo.syntax("gci")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, code: Option[String], tcode: Option[String], querytype: Option[Short], registerTime: Option[DateTime], registerUser: Option[String], registerOrg: Option[String], hitpossibility: Option[Short], priority: Option[Short], reviewStatus: Option[Short], reviewBout: Option[Short], appealStatus: Option[Short], confirmStatus: Option[Short], confirmUser: Option[String], confirmTime: Option[DateTime], queryUuid: Option[String], reviewOrg: Option[String], rank: Option[Int], fraction: Option[Int], fgp: Option[String], confirmOpinion: Option[String], cardType1: Option[Long], cardType2: Option[Long], lastHandleDate: Option[DateTime], operatetype: Option[Long], ckSource: Option[String])(implicit session: DBSession = autoSession): Option[GafisCheckinInfo] = {
    withSQL {
      select.from(GafisCheckinInfo as gci).where.eq(gci.pkId, pkId).and.eq(gci.code, code).and.eq(gci.tcode, tcode).and.eq(gci.querytype, querytype).and.eq(gci.registerTime, registerTime).and.eq(gci.registerUser, registerUser).and.eq(gci.registerOrg, registerOrg).and.eq(gci.hitpossibility, hitpossibility).and.eq(gci.priority, priority).and.eq(gci.reviewStatus, reviewStatus).and.eq(gci.reviewBout, reviewBout).and.eq(gci.appealStatus, appealStatus).and.eq(gci.confirmStatus, confirmStatus).and.eq(gci.confirmUser, confirmUser).and.eq(gci.confirmTime, confirmTime).and.eq(gci.queryUuid, queryUuid).and.eq(gci.reviewOrg, reviewOrg).and.eq(gci.rank, rank).and.eq(gci.fraction, fraction).and.eq(gci.fgp, fgp).and.eq(gci.confirmOpinion, confirmOpinion).and.eq(gci.cardType1, cardType1).and.eq(gci.cardType2, cardType2).and.eq(gci.lastHandleDate, lastHandleDate).and.eq(gci.operatetype, operatetype).and.eq(gci.ckSource, ckSource)
    }.map(GafisCheckinInfo(gci.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisCheckinInfo] = {
    withSQL(select.from(GafisCheckinInfo as gci)).map(GafisCheckinInfo(gci.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisCheckinInfo as gci)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisCheckinInfo] = {
    withSQL {
      select.from(GafisCheckinInfo as gci).where.append(where)
    }.map(GafisCheckinInfo(gci.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisCheckinInfo] = {
    withSQL {
      select.from(GafisCheckinInfo as gci).where.append(where)
    }.map(GafisCheckinInfo(gci.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisCheckinInfo as gci).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    code: Option[String] = None,
    tcode: Option[String] = None,
    querytype: Option[Short] = None,
    registerTime: Option[DateTime] = None,
    registerUser: Option[String] = None,
    registerOrg: Option[String] = None,
    hitpossibility: Option[Short] = None,
    priority: Option[Short] = None,
    reviewStatus: Option[Short] = None,
    reviewBout: Option[Short] = None,
    appealStatus: Option[Short] = None,
    confirmStatus: Option[Short] = None,
    confirmUser: Option[String] = None,
    confirmTime: Option[DateTime] = None,
    queryUuid: Option[String] = None,
    reviewOrg: Option[String] = None,
    rank: Option[Int] = None,
    fraction: Option[Int] = None,
    fgp: Option[String] = None,
    confirmOpinion: Option[String] = None,
    cardType1: Option[Long] = None,
    cardType2: Option[Long] = None,
    lastHandleDate: Option[DateTime] = None,
    operatetype: Option[Long] = None,
    ckSource: Option[String] = None)(implicit session: DBSession = autoSession): GafisCheckinInfo = {
    withSQL {
      insert.into(GafisCheckinInfo).columns(
        column.pkId,
        column.code,
        column.tcode,
        column.querytype,
        column.registerTime,
        column.registerUser,
        column.registerOrg,
        column.hitpossibility,
        column.priority,
        column.reviewStatus,
        column.reviewBout,
        column.appealStatus,
        column.confirmStatus,
        column.confirmUser,
        column.confirmTime,
        column.queryUuid,
        column.reviewOrg,
        column.rank,
        column.fraction,
        column.fgp,
        column.confirmOpinion,
        column.cardType1,
        column.cardType2,
        column.lastHandleDate,
        column.operatetype,
        column.ckSource
      ).values(
        pkId,
        code,
        tcode,
        querytype,
        registerTime,
        registerUser,
        registerOrg,
        hitpossibility,
        priority,
        reviewStatus,
        reviewBout,
        appealStatus,
        confirmStatus,
        confirmUser,
        confirmTime,
        queryUuid,
        reviewOrg,
        rank,
        fraction,
        fgp,
        confirmOpinion,
        cardType1,
        cardType2,
        lastHandleDate,
        operatetype,
        ckSource
      )
    }.update.apply()

    new GafisCheckinInfo(
      pkId = pkId,
      code = code,
      tcode = tcode,
      querytype = querytype,
      registerTime = registerTime,
      registerUser = registerUser,
      registerOrg = registerOrg,
      hitpossibility = hitpossibility,
      priority = priority,
      reviewStatus = reviewStatus,
      reviewBout = reviewBout,
      appealStatus = appealStatus,
      confirmStatus = confirmStatus,
      confirmUser = confirmUser,
      confirmTime = confirmTime,
      queryUuid = queryUuid,
      reviewOrg = reviewOrg,
      rank = rank,
      fraction = fraction,
      fgp = fgp,
      confirmOpinion = confirmOpinion,
      cardType1 = cardType1,
      cardType2 = cardType2,
      lastHandleDate = lastHandleDate,
      operatetype = operatetype,
      ckSource = ckSource)
  }

  def save(entity: GafisCheckinInfo)(implicit session: DBSession = autoSession): GafisCheckinInfo = {
    withSQL {
      update(GafisCheckinInfo).set(
        column.pkId -> entity.pkId,
        column.code -> entity.code,
        column.tcode -> entity.tcode,
        column.querytype -> entity.querytype,
        column.registerTime -> entity.registerTime,
        column.registerUser -> entity.registerUser,
        column.registerOrg -> entity.registerOrg,
        column.hitpossibility -> entity.hitpossibility,
        column.priority -> entity.priority,
        column.reviewStatus -> entity.reviewStatus,
        column.reviewBout -> entity.reviewBout,
        column.appealStatus -> entity.appealStatus,
        column.confirmStatus -> entity.confirmStatus,
        column.confirmUser -> entity.confirmUser,
        column.confirmTime -> entity.confirmTime,
        column.queryUuid -> entity.queryUuid,
        column.reviewOrg -> entity.reviewOrg,
        column.rank -> entity.rank,
        column.fraction -> entity.fraction,
        column.fgp -> entity.fgp,
        column.confirmOpinion -> entity.confirmOpinion,
        column.cardType1 -> entity.cardType1,
        column.cardType2 -> entity.cardType2,
        column.lastHandleDate -> entity.lastHandleDate,
        column.operatetype -> entity.operatetype,
        column.ckSource -> entity.ckSource
      ).where.eq(column.pkId, entity.pkId).and.eq(column.code, entity.code).and.eq(column.tcode, entity.tcode).and.eq(column.querytype, entity.querytype).and.eq(column.registerTime, entity.registerTime).and.eq(column.registerUser, entity.registerUser).and.eq(column.registerOrg, entity.registerOrg).and.eq(column.hitpossibility, entity.hitpossibility).and.eq(column.priority, entity.priority).and.eq(column.reviewStatus, entity.reviewStatus).and.eq(column.reviewBout, entity.reviewBout).and.eq(column.appealStatus, entity.appealStatus).and.eq(column.confirmStatus, entity.confirmStatus).and.eq(column.confirmUser, entity.confirmUser).and.eq(column.confirmTime, entity.confirmTime).and.eq(column.queryUuid, entity.queryUuid).and.eq(column.reviewOrg, entity.reviewOrg).and.eq(column.rank, entity.rank).and.eq(column.fraction, entity.fraction).and.eq(column.fgp, entity.fgp).and.eq(column.confirmOpinion, entity.confirmOpinion).and.eq(column.cardType1, entity.cardType1).and.eq(column.cardType2, entity.cardType2).and.eq(column.lastHandleDate, entity.lastHandleDate).and.eq(column.operatetype, entity.operatetype).and.eq(column.ckSource, entity.ckSource)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisCheckinInfo)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisCheckinInfo).where.eq(column.pkId, entity.pkId).and.eq(column.code, entity.code).and.eq(column.tcode, entity.tcode).and.eq(column.querytype, entity.querytype).and.eq(column.registerTime, entity.registerTime).and.eq(column.registerUser, entity.registerUser).and.eq(column.registerOrg, entity.registerOrg).and.eq(column.hitpossibility, entity.hitpossibility).and.eq(column.priority, entity.priority).and.eq(column.reviewStatus, entity.reviewStatus).and.eq(column.reviewBout, entity.reviewBout).and.eq(column.appealStatus, entity.appealStatus).and.eq(column.confirmStatus, entity.confirmStatus).and.eq(column.confirmUser, entity.confirmUser).and.eq(column.confirmTime, entity.confirmTime).and.eq(column.queryUuid, entity.queryUuid).and.eq(column.reviewOrg, entity.reviewOrg).and.eq(column.rank, entity.rank).and.eq(column.fraction, entity.fraction).and.eq(column.fgp, entity.fgp).and.eq(column.confirmOpinion, entity.confirmOpinion).and.eq(column.cardType1, entity.cardType1).and.eq(column.cardType2, entity.cardType2).and.eq(column.lastHandleDate, entity.lastHandleDate).and.eq(column.operatetype, entity.operatetype).and.eq(column.ckSource, entity.ckSource) }.update.apply()
  }

}
