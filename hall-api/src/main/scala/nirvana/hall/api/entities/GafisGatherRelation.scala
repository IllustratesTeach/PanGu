package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherRelation(
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  relationCode: Option[String] = None,
  relationid: Option[String] = None,
  annex: Option[String] = None,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  deletag: Option[String] = None,
  pkId: String,
  personid: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherRelation.autoSession): GafisGatherRelation = GafisGatherRelation.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherRelation.autoSession): Unit = GafisGatherRelation.destroy(this)(session)

}


object GafisGatherRelation extends SQLSyntaxSupport[GafisGatherRelation] {

  override val tableName = "GAFIS_GATHER_RELATION"

  override val columns = Seq("INPUTPSN", "INPUTTIME", "RELATION_CODE", "RELATIONID", "ANNEX", "MODIFIEDPSN", "MODIFIEDTIME", "DELETAG", "PK_ID", "PERSONID")

  def apply(ggr: SyntaxProvider[GafisGatherRelation])(rs: WrappedResultSet): GafisGatherRelation = apply(ggr.resultName)(rs)
  def apply(ggr: ResultName[GafisGatherRelation])(rs: WrappedResultSet): GafisGatherRelation = new GafisGatherRelation(
    inputpsn = rs.get(ggr.inputpsn),
    inputtime = rs.get(ggr.inputtime),
    relationCode = rs.get(ggr.relationCode),
    relationid = rs.get(ggr.relationid),
    annex = rs.get(ggr.annex),
    modifiedpsn = rs.get(ggr.modifiedpsn),
    modifiedtime = rs.get(ggr.modifiedtime),
    deletag = rs.get(ggr.deletag),
    pkId = rs.get(ggr.pkId),
    personid = rs.get(ggr.personid)
  )

  val ggr = GafisGatherRelation.syntax("ggr")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(inputpsn: Option[String], inputtime: Option[DateTime], relationCode: Option[String], relationid: Option[String], annex: Option[String], modifiedpsn: Option[String], modifiedtime: Option[DateTime], deletag: Option[String], pkId: String, personid: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherRelation] = {
    withSQL {
      select.from(GafisGatherRelation as ggr).where.eq(ggr.inputpsn, inputpsn).and.eq(ggr.inputtime, inputtime).and.eq(ggr.relationCode, relationCode).and.eq(ggr.relationid, relationid).and.eq(ggr.annex, annex).and.eq(ggr.modifiedpsn, modifiedpsn).and.eq(ggr.modifiedtime, modifiedtime).and.eq(ggr.deletag, deletag).and.eq(ggr.pkId, pkId).and.eq(ggr.personid, personid)
    }.map(GafisGatherRelation(ggr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherRelation] = {
    withSQL(select.from(GafisGatherRelation as ggr)).map(GafisGatherRelation(ggr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherRelation as ggr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherRelation] = {
    withSQL {
      select.from(GafisGatherRelation as ggr).where.append(where)
    }.map(GafisGatherRelation(ggr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherRelation] = {
    withSQL {
      select.from(GafisGatherRelation as ggr).where.append(where)
    }.map(GafisGatherRelation(ggr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherRelation as ggr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    relationCode: Option[String] = None,
    relationid: Option[String] = None,
    annex: Option[String] = None,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    deletag: Option[String] = None,
    pkId: String,
    personid: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherRelation = {
    withSQL {
      insert.into(GafisGatherRelation).columns(
        column.inputpsn,
        column.inputtime,
        column.relationCode,
        column.relationid,
        column.annex,
        column.modifiedpsn,
        column.modifiedtime,
        column.deletag,
        column.pkId,
        column.personid
      ).values(
        inputpsn,
        inputtime,
        relationCode,
        relationid,
        annex,
        modifiedpsn,
        modifiedtime,
        deletag,
        pkId,
        personid
      )
    }.update.apply()

    GafisGatherRelation(
      inputpsn = inputpsn,
      inputtime = inputtime,
      relationCode = relationCode,
      relationid = relationid,
      annex = annex,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      deletag = deletag,
      pkId = pkId,
      personid = personid)
  }

  def save(entity: GafisGatherRelation)(implicit session: DBSession = autoSession): GafisGatherRelation = {
    withSQL {
      update(GafisGatherRelation).set(
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.relationCode -> entity.relationCode,
        column.relationid -> entity.relationid,
        column.annex -> entity.annex,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.deletag -> entity.deletag,
        column.pkId -> entity.pkId,
        column.personid -> entity.personid
      ).where.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.relationCode, entity.relationCode).and.eq(column.relationid, entity.relationid).and.eq(column.annex, entity.annex).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag).and.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherRelation)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherRelation).where.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.relationCode, entity.relationCode).and.eq(column.relationid, entity.relationid).and.eq(column.annex, entity.annex).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag).and.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid) }.update.apply()
  }

}
