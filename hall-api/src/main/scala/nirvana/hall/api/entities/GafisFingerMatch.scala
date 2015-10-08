package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisFingerMatch(
  pkId: String,
  personId: String,
  matchType: Option[String] = None,
  matchCard: Option[String] = None,
  matchDate: Option[DateTime] = None,
  matchFgp: Option[Short] = None,
  matchScore: Option[Int] = None) {

  def save()(implicit session: DBSession = GafisFingerMatch.autoSession): GafisFingerMatch = GafisFingerMatch.save(this)(session)

  def destroy()(implicit session: DBSession = GafisFingerMatch.autoSession): Unit = GafisFingerMatch.destroy(this)(session)

}


object GafisFingerMatch extends SQLSyntaxSupport[GafisFingerMatch] {

  override val tableName = "GAFIS_FINGER_MATCH"

  override val columns = Seq("PK_ID", "PERSON_ID", "MATCH_TYPE", "MATCH_CARD", "MATCH_DATE", "MATCH_FGP", "MATCH_SCORE")

  def apply(gfm: SyntaxProvider[GafisFingerMatch])(rs: WrappedResultSet): GafisFingerMatch = apply(gfm.resultName)(rs)
  def apply(gfm: ResultName[GafisFingerMatch])(rs: WrappedResultSet): GafisFingerMatch = new GafisFingerMatch(
    pkId = rs.get(gfm.pkId),
    personId = rs.get(gfm.personId),
    matchType = rs.get(gfm.matchType),
    matchCard = rs.get(gfm.matchCard),
    matchDate = rs.get(gfm.matchDate),
    matchFgp = rs.get(gfm.matchFgp),
    matchScore = rs.get(gfm.matchScore)
  )

  val gfm = GafisFingerMatch.syntax("gfm")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personId: String, matchType: Option[String], matchCard: Option[String], matchDate: Option[DateTime], matchFgp: Option[Short], matchScore: Option[Int])(implicit session: DBSession = autoSession): Option[GafisFingerMatch] = {
    withSQL {
      select.from(GafisFingerMatch as gfm).where.eq(gfm.pkId, pkId).and.eq(gfm.personId, personId).and.eq(gfm.matchType, matchType).and.eq(gfm.matchCard, matchCard).and.eq(gfm.matchDate, matchDate).and.eq(gfm.matchFgp, matchFgp).and.eq(gfm.matchScore, matchScore)
    }.map(GafisFingerMatch(gfm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisFingerMatch] = {
    withSQL(select.from(GafisFingerMatch as gfm)).map(GafisFingerMatch(gfm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisFingerMatch as gfm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisFingerMatch] = {
    withSQL {
      select.from(GafisFingerMatch as gfm).where.append(where)
    }.map(GafisFingerMatch(gfm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisFingerMatch] = {
    withSQL {
      select.from(GafisFingerMatch as gfm).where.append(where)
    }.map(GafisFingerMatch(gfm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisFingerMatch as gfm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personId: String,
    matchType: Option[String] = None,
    matchCard: Option[String] = None,
    matchDate: Option[DateTime] = None,
    matchFgp: Option[Short] = None,
    matchScore: Option[Int] = None)(implicit session: DBSession = autoSession): GafisFingerMatch = {
    withSQL {
      insert.into(GafisFingerMatch).columns(
        column.pkId,
        column.personId,
        column.matchType,
        column.matchCard,
        column.matchDate,
        column.matchFgp,
        column.matchScore
      ).values(
        pkId,
        personId,
        matchType,
        matchCard,
        matchDate,
        matchFgp,
        matchScore
      )
    }.update.apply()

    GafisFingerMatch(
      pkId = pkId,
      personId = personId,
      matchType = matchType,
      matchCard = matchCard,
      matchDate = matchDate,
      matchFgp = matchFgp,
      matchScore = matchScore)
  }

  def save(entity: GafisFingerMatch)(implicit session: DBSession = autoSession): GafisFingerMatch = {
    withSQL {
      update(GafisFingerMatch).set(
        column.pkId -> entity.pkId,
        column.personId -> entity.personId,
        column.matchType -> entity.matchType,
        column.matchCard -> entity.matchCard,
        column.matchDate -> entity.matchDate,
        column.matchFgp -> entity.matchFgp,
        column.matchScore -> entity.matchScore
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.matchType, entity.matchType).and.eq(column.matchCard, entity.matchCard).and.eq(column.matchDate, entity.matchDate).and.eq(column.matchFgp, entity.matchFgp).and.eq(column.matchScore, entity.matchScore)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisFingerMatch)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisFingerMatch).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.matchType, entity.matchType).and.eq(column.matchCard, entity.matchCard).and.eq(column.matchDate, entity.matchDate).and.eq(column.matchFgp, entity.matchFgp).and.eq(column.matchScore, entity.matchScore) }.update.apply()
  }

}
