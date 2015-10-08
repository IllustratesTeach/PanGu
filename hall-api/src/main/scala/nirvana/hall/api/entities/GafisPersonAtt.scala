package nirvana.hall.api.entities

import scalikejdbc._
import java.sql.{Blob}

case class GafisPersonAtt(
  pkId: String,
  img: Option[Blob] = None,
  `type`: Option[String] = None,
  personid: Option[String] = None,
  gatherdatanosqlid: Option[String] = None) {

  def save()(implicit session: DBSession = GafisPersonAtt.autoSession): GafisPersonAtt = GafisPersonAtt.save(this)(session)

  def destroy()(implicit session: DBSession = GafisPersonAtt.autoSession): Unit = GafisPersonAtt.destroy(this)(session)

}


object GafisPersonAtt extends SQLSyntaxSupport[GafisPersonAtt] {

  override val tableName = "GAFIS_PERSON_ATT"

  override val columns = Seq("PK_ID", "IMG", "TYPE", "PERSONID", "GATHERDATANOSQLID")

  def apply(gpa: SyntaxProvider[GafisPersonAtt])(rs: WrappedResultSet): GafisPersonAtt = apply(gpa.resultName)(rs)
  def apply(gpa: ResultName[GafisPersonAtt])(rs: WrappedResultSet): GafisPersonAtt = new GafisPersonAtt(
    pkId = rs.get(gpa.pkId),
    img = rs.get(gpa.img),
    `type` = rs.get(gpa.`type`),
    personid = rs.get(gpa.personid),
    gatherdatanosqlid = rs.get(gpa.gatherdatanosqlid)
  )

  val gpa = GafisPersonAtt.syntax("gpa")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, img: Option[Blob], `type`: Option[String], personid: Option[String], gatherdatanosqlid: Option[String])(implicit session: DBSession = autoSession): Option[GafisPersonAtt] = {
    withSQL {
      select.from(GafisPersonAtt as gpa).where.eq(gpa.pkId, pkId).and.eq(gpa.img, img).and.eq(gpa.`type`, `type`).and.eq(gpa.personid, personid).and.eq(gpa.gatherdatanosqlid, gatherdatanosqlid)
    }.map(GafisPersonAtt(gpa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisPersonAtt] = {
    withSQL(select.from(GafisPersonAtt as gpa)).map(GafisPersonAtt(gpa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisPersonAtt as gpa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisPersonAtt] = {
    withSQL {
      select.from(GafisPersonAtt as gpa).where.append(where)
    }.map(GafisPersonAtt(gpa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisPersonAtt] = {
    withSQL {
      select.from(GafisPersonAtt as gpa).where.append(where)
    }.map(GafisPersonAtt(gpa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisPersonAtt as gpa).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    img: Option[Blob] = None,
    `type`: Option[String] = None,
    personid: Option[String] = None,
    gatherdatanosqlid: Option[String] = None)(implicit session: DBSession = autoSession): GafisPersonAtt = {
    withSQL {
      insert.into(GafisPersonAtt).columns(
        column.pkId,
        column.img,
        column.`type`,
        column.personid,
        column.gatherdatanosqlid
      ).values(
        pkId,
        img,
        `type`,
        personid,
        gatherdatanosqlid
      )
    }.update.apply()

    GafisPersonAtt(
      pkId = pkId,
      img = img,
      `type` = `type`,
      personid = personid,
      gatherdatanosqlid = gatherdatanosqlid)
  }

  def save(entity: GafisPersonAtt)(implicit session: DBSession = autoSession): GafisPersonAtt = {
    withSQL {
      update(GafisPersonAtt).set(
        column.pkId -> entity.pkId,
        column.img -> entity.img,
        column.`type` -> entity.`type`,
        column.personid -> entity.personid,
        column.gatherdatanosqlid -> entity.gatherdatanosqlid
      ).where.eq(column.pkId, entity.pkId).and.eq(column.img, entity.img).and.eq(column.`type`, entity.`type`).and.eq(column.personid, entity.personid).and.eq(column.gatherdatanosqlid, entity.gatherdatanosqlid)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisPersonAtt)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisPersonAtt).where.eq(column.pkId, entity.pkId).and.eq(column.img, entity.img).and.eq(column.`type`, entity.`type`).and.eq(column.personid, entity.personid).and.eq(column.gatherdatanosqlid, entity.gatherdatanosqlid) }.update.apply()
  }

}
