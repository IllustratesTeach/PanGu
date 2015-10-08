package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisControrcapturePerson(
  pkid: String,
  personid: Option[String] = None,
  approvalType: Option[String] = None,
  approvalPerson: Option[String] = None,
  approvalDate: Option[DateTime] = None,
  approvalRemark: Option[String] = None,
  approvalResult: Option[String] = None) {

  def save()(implicit session: DBSession = GafisControrcapturePerson.autoSession): GafisControrcapturePerson = GafisControrcapturePerson.save(this)(session)

  def destroy()(implicit session: DBSession = GafisControrcapturePerson.autoSession): Unit = GafisControrcapturePerson.destroy(this)(session)

}


object GafisControrcapturePerson extends SQLSyntaxSupport[GafisControrcapturePerson] {

  override val tableName = "GAFIS_CONTRORCAPTURE_PERSON"

  override val columns = Seq("PKID", "PERSONID", "APPROVAL_TYPE", "APPROVAL_PERSON", "APPROVAL_DATE", "APPROVAL_REMARK", "APPROVAL_RESULT")

  def apply(gcp: SyntaxProvider[GafisControrcapturePerson])(rs: WrappedResultSet): GafisControrcapturePerson = apply(gcp.resultName)(rs)
  def apply(gcp: ResultName[GafisControrcapturePerson])(rs: WrappedResultSet): GafisControrcapturePerson = new GafisControrcapturePerson(
    pkid = rs.get(gcp.pkid),
    personid = rs.get(gcp.personid),
    approvalType = rs.get(gcp.approvalType),
    approvalPerson = rs.get(gcp.approvalPerson),
    approvalDate = rs.get(gcp.approvalDate),
    approvalRemark = rs.get(gcp.approvalRemark),
    approvalResult = rs.get(gcp.approvalResult)
  )

  val gcp = GafisControrcapturePerson.syntax("gcp")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkid: String, personid: Option[String], approvalType: Option[String], approvalPerson: Option[String], approvalDate: Option[DateTime], approvalRemark: Option[String], approvalResult: Option[String])(implicit session: DBSession = autoSession): Option[GafisControrcapturePerson] = {
    withSQL {
      select.from(GafisControrcapturePerson as gcp).where.eq(gcp.pkid, pkid).and.eq(gcp.personid, personid).and.eq(gcp.approvalType, approvalType).and.eq(gcp.approvalPerson, approvalPerson).and.eq(gcp.approvalDate, approvalDate).and.eq(gcp.approvalRemark, approvalRemark).and.eq(gcp.approvalResult, approvalResult)
    }.map(GafisControrcapturePerson(gcp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisControrcapturePerson] = {
    withSQL(select.from(GafisControrcapturePerson as gcp)).map(GafisControrcapturePerson(gcp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisControrcapturePerson as gcp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisControrcapturePerson] = {
    withSQL {
      select.from(GafisControrcapturePerson as gcp).where.append(where)
    }.map(GafisControrcapturePerson(gcp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisControrcapturePerson] = {
    withSQL {
      select.from(GafisControrcapturePerson as gcp).where.append(where)
    }.map(GafisControrcapturePerson(gcp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisControrcapturePerson as gcp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkid: String,
    personid: Option[String] = None,
    approvalType: Option[String] = None,
    approvalPerson: Option[String] = None,
    approvalDate: Option[DateTime] = None,
    approvalRemark: Option[String] = None,
    approvalResult: Option[String] = None)(implicit session: DBSession = autoSession): GafisControrcapturePerson = {
    withSQL {
      insert.into(GafisControrcapturePerson).columns(
        column.pkid,
        column.personid,
        column.approvalType,
        column.approvalPerson,
        column.approvalDate,
        column.approvalRemark,
        column.approvalResult
      ).values(
        pkid,
        personid,
        approvalType,
        approvalPerson,
        approvalDate,
        approvalRemark,
        approvalResult
      )
    }.update.apply()

    GafisControrcapturePerson(
      pkid = pkid,
      personid = personid,
      approvalType = approvalType,
      approvalPerson = approvalPerson,
      approvalDate = approvalDate,
      approvalRemark = approvalRemark,
      approvalResult = approvalResult)
  }

  def save(entity: GafisControrcapturePerson)(implicit session: DBSession = autoSession): GafisControrcapturePerson = {
    withSQL {
      update(GafisControrcapturePerson).set(
        column.pkid -> entity.pkid,
        column.personid -> entity.personid,
        column.approvalType -> entity.approvalType,
        column.approvalPerson -> entity.approvalPerson,
        column.approvalDate -> entity.approvalDate,
        column.approvalRemark -> entity.approvalRemark,
        column.approvalResult -> entity.approvalResult
      ).where.eq(column.pkid, entity.pkid).and.eq(column.personid, entity.personid).and.eq(column.approvalType, entity.approvalType).and.eq(column.approvalPerson, entity.approvalPerson).and.eq(column.approvalDate, entity.approvalDate).and.eq(column.approvalRemark, entity.approvalRemark).and.eq(column.approvalResult, entity.approvalResult)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisControrcapturePerson)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisControrcapturePerson).where.eq(column.pkid, entity.pkid).and.eq(column.personid, entity.personid).and.eq(column.approvalType, entity.approvalType).and.eq(column.approvalPerson, entity.approvalPerson).and.eq(column.approvalDate, entity.approvalDate).and.eq(column.approvalRemark, entity.approvalRemark).and.eq(column.approvalResult, entity.approvalResult) }.update.apply()
  }

}
