package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherGoodsPerson(
  pkId: String,
  personId: Option[String] = None,
  deletag: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  inputtime: DateTime,
  inputpsn: Option[String] = None,
  wpbh: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherGoodsPerson.autoSession): GafisGatherGoodsPerson = GafisGatherGoodsPerson.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherGoodsPerson.autoSession): Unit = GafisGatherGoodsPerson.destroy(this)(session)

}


object GafisGatherGoodsPerson extends SQLSyntaxSupport[GafisGatherGoodsPerson] {

  override val tableName = "GAFIS_GATHER_GOODS_PERSON"

  override val columns = Seq("PK_ID", "PERSON_ID", "DELETAG", "MODIFIEDTIME", "MODIFIEDPSN", "INPUTTIME", "INPUTPSN", "WPBH")

  def apply(gggp: SyntaxProvider[GafisGatherGoodsPerson])(rs: WrappedResultSet): GafisGatherGoodsPerson = apply(gggp.resultName)(rs)
  def apply(gggp: ResultName[GafisGatherGoodsPerson])(rs: WrappedResultSet): GafisGatherGoodsPerson = new GafisGatherGoodsPerson(
    pkId = rs.get(gggp.pkId),
    personId = rs.get(gggp.personId),
    deletag = rs.get(gggp.deletag),
    modifiedtime = rs.get(gggp.modifiedtime),
    modifiedpsn = rs.get(gggp.modifiedpsn),
    inputtime = rs.get(gggp.inputtime),
    inputpsn = rs.get(gggp.inputpsn),
    wpbh = rs.get(gggp.wpbh)
  )

  val gggp = GafisGatherGoodsPerson.syntax("gggp")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personId: Option[String], deletag: Option[String], modifiedtime: Option[DateTime], modifiedpsn: Option[String], inputtime: DateTime, inputpsn: Option[String], wpbh: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherGoodsPerson] = {
    withSQL {
      select.from(GafisGatherGoodsPerson as gggp).where.eq(gggp.pkId, pkId).and.eq(gggp.personId, personId).and.eq(gggp.deletag, deletag).and.eq(gggp.modifiedtime, modifiedtime).and.eq(gggp.modifiedpsn, modifiedpsn).and.eq(gggp.inputtime, inputtime).and.eq(gggp.inputpsn, inputpsn).and.eq(gggp.wpbh, wpbh)
    }.map(GafisGatherGoodsPerson(gggp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherGoodsPerson] = {
    withSQL(select.from(GafisGatherGoodsPerson as gggp)).map(GafisGatherGoodsPerson(gggp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherGoodsPerson as gggp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherGoodsPerson] = {
    withSQL {
      select.from(GafisGatherGoodsPerson as gggp).where.append(where)
    }.map(GafisGatherGoodsPerson(gggp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherGoodsPerson] = {
    withSQL {
      select.from(GafisGatherGoodsPerson as gggp).where.append(where)
    }.map(GafisGatherGoodsPerson(gggp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherGoodsPerson as gggp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personId: Option[String] = None,
    deletag: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    inputtime: DateTime,
    inputpsn: Option[String] = None,
    wpbh: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherGoodsPerson = {
    withSQL {
      insert.into(GafisGatherGoodsPerson).columns(
        column.pkId,
        column.personId,
        column.deletag,
        column.modifiedtime,
        column.modifiedpsn,
        column.inputtime,
        column.inputpsn,
        column.wpbh
      ).values(
        pkId,
        personId,
        deletag,
        modifiedtime,
        modifiedpsn,
        inputtime,
        inputpsn,
        wpbh
      )
    }.update.apply()

    GafisGatherGoodsPerson(
      pkId = pkId,
      personId = personId,
      deletag = deletag,
      modifiedtime = modifiedtime,
      modifiedpsn = modifiedpsn,
      inputtime = inputtime,
      inputpsn = inputpsn,
      wpbh = wpbh)
  }

  def save(entity: GafisGatherGoodsPerson)(implicit session: DBSession = autoSession): GafisGatherGoodsPerson = {
    withSQL {
      update(GafisGatherGoodsPerson).set(
        column.pkId -> entity.pkId,
        column.personId -> entity.personId,
        column.deletag -> entity.deletag,
        column.modifiedtime -> entity.modifiedtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.inputtime -> entity.inputtime,
        column.inputpsn -> entity.inputpsn,
        column.wpbh -> entity.wpbh
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.deletag, entity.deletag).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.wpbh, entity.wpbh)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherGoodsPerson)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherGoodsPerson).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.deletag, entity.deletag).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.wpbh, entity.wpbh) }.update.apply()
  }

}
