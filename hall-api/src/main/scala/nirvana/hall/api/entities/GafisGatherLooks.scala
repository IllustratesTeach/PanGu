package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherLooks(
  pkId: String,
  personid: Option[String] = None,
  characterCode: Option[String] = None,
  annex: Option[String] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  deletag: Option[String] = None,
  othersDesc: Option[String] = None,
  aspectDesc: Option[String] = None,
  surfaceDesc: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherLooks.autoSession): GafisGatherLooks = GafisGatherLooks.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherLooks.autoSession): Unit = GafisGatherLooks.destroy(this)(session)

}


object GafisGatherLooks extends SQLSyntaxSupport[GafisGatherLooks] {

  override val tableName = "GAFIS_GATHER_LOOKS"

  override val columns = Seq("PK_ID", "PERSONID", "CHARACTER_CODE", "ANNEX", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "DELETAG", "OTHERS_DESC", "ASPECT_DESC", "SURFACE_DESC")

  def apply(ggl: SyntaxProvider[GafisGatherLooks])(rs: WrappedResultSet): GafisGatherLooks = apply(ggl.resultName)(rs)
  def apply(ggl: ResultName[GafisGatherLooks])(rs: WrappedResultSet): GafisGatherLooks = new GafisGatherLooks(
    pkId = rs.get(ggl.pkId),
    personid = rs.get(ggl.personid),
    characterCode = rs.get(ggl.characterCode),
    annex = rs.get(ggl.annex),
    inputpsn = rs.get(ggl.inputpsn),
    inputtime = rs.get(ggl.inputtime),
    modifiedpsn = rs.get(ggl.modifiedpsn),
    modifiedtime = rs.get(ggl.modifiedtime),
    deletag = rs.get(ggl.deletag),
    othersDesc = rs.get(ggl.othersDesc),
    aspectDesc = rs.get(ggl.aspectDesc),
    surfaceDesc = rs.get(ggl.surfaceDesc)
  )

  val ggl = GafisGatherLooks.syntax("ggl")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], characterCode: Option[String], annex: Option[String], inputpsn: Option[String], inputtime: Option[DateTime], modifiedpsn: Option[String], modifiedtime: Option[DateTime], deletag: Option[String], othersDesc: Option[String], aspectDesc: Option[String], surfaceDesc: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherLooks] = {
    withSQL {
      select.from(GafisGatherLooks as ggl).where.eq(ggl.pkId, pkId).and.eq(ggl.personid, personid).and.eq(ggl.characterCode, characterCode).and.eq(ggl.annex, annex).and.eq(ggl.inputpsn, inputpsn).and.eq(ggl.inputtime, inputtime).and.eq(ggl.modifiedpsn, modifiedpsn).and.eq(ggl.modifiedtime, modifiedtime).and.eq(ggl.deletag, deletag).and.eq(ggl.othersDesc, othersDesc).and.eq(ggl.aspectDesc, aspectDesc).and.eq(ggl.surfaceDesc, surfaceDesc)
    }.map(GafisGatherLooks(ggl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherLooks] = {
    withSQL(select.from(GafisGatherLooks as ggl)).map(GafisGatherLooks(ggl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherLooks as ggl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherLooks] = {
    withSQL {
      select.from(GafisGatherLooks as ggl).where.append(where)
    }.map(GafisGatherLooks(ggl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherLooks] = {
    withSQL {
      select.from(GafisGatherLooks as ggl).where.append(where)
    }.map(GafisGatherLooks(ggl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherLooks as ggl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    characterCode: Option[String] = None,
    annex: Option[String] = None,
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    deletag: Option[String] = None,
    othersDesc: Option[String] = None,
    aspectDesc: Option[String] = None,
    surfaceDesc: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherLooks = {
    withSQL {
      insert.into(GafisGatherLooks).columns(
        column.pkId,
        column.personid,
        column.characterCode,
        column.annex,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.deletag,
        column.othersDesc,
        column.aspectDesc,
        column.surfaceDesc
      ).values(
        pkId,
        personid,
        characterCode,
        annex,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        deletag,
        othersDesc,
        aspectDesc,
        surfaceDesc
      )
    }.update.apply()

    GafisGatherLooks(
      pkId = pkId,
      personid = personid,
      characterCode = characterCode,
      annex = annex,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      deletag = deletag,
      othersDesc = othersDesc,
      aspectDesc = aspectDesc,
      surfaceDesc = surfaceDesc)
  }

  def save(entity: GafisGatherLooks)(implicit session: DBSession = autoSession): GafisGatherLooks = {
    withSQL {
      update(GafisGatherLooks).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.characterCode -> entity.characterCode,
        column.annex -> entity.annex,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.deletag -> entity.deletag,
        column.othersDesc -> entity.othersDesc,
        column.aspectDesc -> entity.aspectDesc,
        column.surfaceDesc -> entity.surfaceDesc
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.characterCode, entity.characterCode).and.eq(column.annex, entity.annex).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag).and.eq(column.othersDesc, entity.othersDesc).and.eq(column.aspectDesc, entity.aspectDesc).and.eq(column.surfaceDesc, entity.surfaceDesc)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherLooks)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherLooks).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.characterCode, entity.characterCode).and.eq(column.annex, entity.annex).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag).and.eq(column.othersDesc, entity.othersDesc).and.eq(column.aspectDesc, entity.aspectDesc).and.eq(column.surfaceDesc, entity.surfaceDesc) }.update.apply()
  }

}
