package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisFingerMatchTt(
  pkId: String,
  personId: String,
  resultCode: Option[String] = None,
  resultValue: Option[Long] = None,
  explain: Option[String] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisFingerMatchTt.autoSession): GafisFingerMatchTt = GafisFingerMatchTt.save(this)(session)

  def destroy()(implicit session: DBSession = GafisFingerMatchTt.autoSession): Unit = GafisFingerMatchTt.destroy(this)(session)

}


object GafisFingerMatchTt extends SQLSyntaxSupport[GafisFingerMatchTt] {

  override val tableName = "GAFIS_FINGER_MATCH_TT"

  override val columns = Seq("PK_ID", "PERSON_ID", "RESULT_CODE", "RESULT_VALUE", "EXPLAIN", "INPUTPSN", "INPUTTIME")

  def apply(gfmt: SyntaxProvider[GafisFingerMatchTt])(rs: WrappedResultSet): GafisFingerMatchTt = apply(gfmt.resultName)(rs)
  def apply(gfmt: ResultName[GafisFingerMatchTt])(rs: WrappedResultSet): GafisFingerMatchTt = new GafisFingerMatchTt(
    pkId = rs.get(gfmt.pkId),
    personId = rs.get(gfmt.personId),
    resultCode = rs.get(gfmt.resultCode),
    resultValue = rs.get(gfmt.resultValue),
    explain = rs.get(gfmt.explain),
    inputpsn = rs.get(gfmt.inputpsn),
    inputtime = rs.get(gfmt.inputtime)
  )

  val gfmt = GafisFingerMatchTt.syntax("gfmt")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personId: String, resultCode: Option[String], resultValue: Option[Long], explain: Option[String], inputpsn: Option[String], inputtime: Option[DateTime])(implicit session: DBSession = autoSession): Option[GafisFingerMatchTt] = {
    withSQL {
      select.from(GafisFingerMatchTt as gfmt).where.eq(gfmt.pkId, pkId).and.eq(gfmt.personId, personId).and.eq(gfmt.resultCode, resultCode).and.eq(gfmt.resultValue, resultValue).and.eq(gfmt.explain, explain).and.eq(gfmt.inputpsn, inputpsn).and.eq(gfmt.inputtime, inputtime)
    }.map(GafisFingerMatchTt(gfmt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisFingerMatchTt] = {
    withSQL(select.from(GafisFingerMatchTt as gfmt)).map(GafisFingerMatchTt(gfmt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisFingerMatchTt as gfmt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisFingerMatchTt] = {
    withSQL {
      select.from(GafisFingerMatchTt as gfmt).where.append(where)
    }.map(GafisFingerMatchTt(gfmt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisFingerMatchTt] = {
    withSQL {
      select.from(GafisFingerMatchTt as gfmt).where.append(where)
    }.map(GafisFingerMatchTt(gfmt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisFingerMatchTt as gfmt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personId: String,
    resultCode: Option[String] = None,
    resultValue: Option[Long] = None,
    explain: Option[String] = None,
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisFingerMatchTt = {
    withSQL {
      insert.into(GafisFingerMatchTt).columns(
        column.pkId,
        column.personId,
        column.resultCode,
        column.resultValue,
        column.explain,
        column.inputpsn,
        column.inputtime
      ).values(
        pkId,
        personId,
        resultCode,
        resultValue,
        explain,
        inputpsn,
        inputtime
      )
    }.update.apply()

    GafisFingerMatchTt(
      pkId = pkId,
      personId = personId,
      resultCode = resultCode,
      resultValue = resultValue,
      explain = explain,
      inputpsn = inputpsn,
      inputtime = inputtime)
  }

  def save(entity: GafisFingerMatchTt)(implicit session: DBSession = autoSession): GafisFingerMatchTt = {
    withSQL {
      update(GafisFingerMatchTt).set(
        column.pkId -> entity.pkId,
        column.personId -> entity.personId,
        column.resultCode -> entity.resultCode,
        column.resultValue -> entity.resultValue,
        column.explain -> entity.explain,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.resultCode, entity.resultCode).and.eq(column.resultValue, entity.resultValue).and.eq(column.explain, entity.explain).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisFingerMatchTt)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisFingerMatchTt).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.resultCode, entity.resultCode).and.eq(column.resultValue, entity.resultValue).and.eq(column.explain, entity.explain).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime) }.update.apply()
  }

}
