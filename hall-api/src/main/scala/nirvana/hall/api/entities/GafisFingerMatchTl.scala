package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisFingerMatchTl(
  pkId: String,
  personId: String,
  resultCode: Option[String] = None,
  explain: Option[String] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisFingerMatchTl.autoSession): GafisFingerMatchTl = GafisFingerMatchTl.save(this)(session)

  def destroy()(implicit session: DBSession = GafisFingerMatchTl.autoSession): Unit = GafisFingerMatchTl.destroy(this)(session)

}


object GafisFingerMatchTl extends SQLSyntaxSupport[GafisFingerMatchTl] {

  override val tableName = "GAFIS_FINGER_MATCH_TL"

  override val columns = Seq("PK_ID", "PERSON_ID", "RESULT_CODE", "EXPLAIN", "INPUTPSN", "INPUTTIME")

  def apply(gfmt: SyntaxProvider[GafisFingerMatchTl])(rs: WrappedResultSet): GafisFingerMatchTl = apply(gfmt.resultName)(rs)
  def apply(gfmt: ResultName[GafisFingerMatchTl])(rs: WrappedResultSet): GafisFingerMatchTl = new GafisFingerMatchTl(
    pkId = rs.get(gfmt.pkId),
    personId = rs.get(gfmt.personId),
    resultCode = rs.get(gfmt.resultCode),
    explain = rs.get(gfmt.explain),
    inputpsn = rs.get(gfmt.inputpsn),
    inputtime = rs.get(gfmt.inputtime)
  )

  val gfmt = GafisFingerMatchTl.syntax("gfmt")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personId: String, resultCode: Option[String], explain: Option[String], inputpsn: Option[String], inputtime: Option[DateTime])(implicit session: DBSession = autoSession): Option[GafisFingerMatchTl] = {
    withSQL {
      select.from(GafisFingerMatchTl as gfmt).where.eq(gfmt.pkId, pkId).and.eq(gfmt.personId, personId).and.eq(gfmt.resultCode, resultCode).and.eq(gfmt.explain, explain).and.eq(gfmt.inputpsn, inputpsn).and.eq(gfmt.inputtime, inputtime)
    }.map(GafisFingerMatchTl(gfmt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisFingerMatchTl] = {
    withSQL(select.from(GafisFingerMatchTl as gfmt)).map(GafisFingerMatchTl(gfmt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisFingerMatchTl as gfmt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisFingerMatchTl] = {
    withSQL {
      select.from(GafisFingerMatchTl as gfmt).where.append(where)
    }.map(GafisFingerMatchTl(gfmt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisFingerMatchTl] = {
    withSQL {
      select.from(GafisFingerMatchTl as gfmt).where.append(where)
    }.map(GafisFingerMatchTl(gfmt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisFingerMatchTl as gfmt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personId: String,
    resultCode: Option[String] = None,
    explain: Option[String] = None,
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisFingerMatchTl = {
    withSQL {
      insert.into(GafisFingerMatchTl).columns(
        column.pkId,
        column.personId,
        column.resultCode,
        column.explain,
        column.inputpsn,
        column.inputtime
      ).values(
        pkId,
        personId,
        resultCode,
        explain,
        inputpsn,
        inputtime
      )
    }.update.apply()

    GafisFingerMatchTl(
      pkId = pkId,
      personId = personId,
      resultCode = resultCode,
      explain = explain,
      inputpsn = inputpsn,
      inputtime = inputtime)
  }

  def save(entity: GafisFingerMatchTl)(implicit session: DBSession = autoSession): GafisFingerMatchTl = {
    withSQL {
      update(GafisFingerMatchTl).set(
        column.pkId -> entity.pkId,
        column.personId -> entity.personId,
        column.resultCode -> entity.resultCode,
        column.explain -> entity.explain,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.resultCode, entity.resultCode).and.eq(column.explain, entity.explain).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisFingerMatchTl)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisFingerMatchTl).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.resultCode, entity.resultCode).and.eq(column.explain, entity.explain).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime) }.update.apply()
  }

}
