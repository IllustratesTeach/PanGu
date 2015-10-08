package nirvana.hall.api.entities

import scalikejdbc._

case class TempImgid(
  pkId: Option[String] = None,
  personid: Option[String] = None) {

  def save()(implicit session: DBSession = TempImgid.autoSession): TempImgid = TempImgid.save(this)(session)

  def destroy()(implicit session: DBSession = TempImgid.autoSession): Unit = TempImgid.destroy(this)(session)

}


object TempImgid extends SQLSyntaxSupport[TempImgid] {

  override val tableName = "TEMP_IMGID"

  override val columns = Seq("PK_ID", "PERSONID")

  def apply(ti: SyntaxProvider[TempImgid])(rs: WrappedResultSet): TempImgid = apply(ti.resultName)(rs)
  def apply(ti: ResultName[TempImgid])(rs: WrappedResultSet): TempImgid = new TempImgid(
    pkId = rs.get(ti.pkId),
    personid = rs.get(ti.personid)
  )

  val ti = TempImgid.syntax("ti")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: Option[String], personid: Option[String])(implicit session: DBSession = autoSession): Option[TempImgid] = {
    withSQL {
      select.from(TempImgid as ti).where.eq(ti.pkId, pkId).and.eq(ti.personid, personid)
    }.map(TempImgid(ti.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TempImgid] = {
    withSQL(select.from(TempImgid as ti)).map(TempImgid(ti.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TempImgid as ti)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TempImgid] = {
    withSQL {
      select.from(TempImgid as ti).where.append(where)
    }.map(TempImgid(ti.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TempImgid] = {
    withSQL {
      select.from(TempImgid as ti).where.append(where)
    }.map(TempImgid(ti.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TempImgid as ti).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: Option[String] = None,
    personid: Option[String] = None)(implicit session: DBSession = autoSession): TempImgid = {
    withSQL {
      insert.into(TempImgid).columns(
        column.pkId,
        column.personid
      ).values(
        pkId,
        personid
      )
    }.update.apply()

    TempImgid(
      pkId = pkId,
      personid = personid)
  }

  def save(entity: TempImgid)(implicit session: DBSession = autoSession): TempImgid = {
    withSQL {
      update(TempImgid).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid)
    }.update.apply()
    entity
  }

  def destroy(entity: TempImgid)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(TempImgid).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid) }.update.apply()
  }

}
