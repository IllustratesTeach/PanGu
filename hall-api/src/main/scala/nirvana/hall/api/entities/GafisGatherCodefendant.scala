package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherCodefendant(
  pkId: String,
  personId: Option[String] = None,
  name: Option[String] = None,
  idcard: Option[String] = None,
  createUserId: Option[String] = None,
  createDatetime: Option[DateTime] = None,
  updateUserId: Option[String] = None,
  updateDatetime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisGatherCodefendant.autoSession): GafisGatherCodefendant = GafisGatherCodefendant.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherCodefendant.autoSession): Unit = GafisGatherCodefendant.destroy(this)(session)

}


object GafisGatherCodefendant extends SQLSyntaxSupport[GafisGatherCodefendant] {

  override val tableName = "GAFIS_GATHER_CODEFENDANT"

  override val columns = Seq("PK_ID", "PERSON_ID", "NAME", "IDCARD", "CREATE_USER_ID", "CREATE_DATETIME", "UPDATE_USER_ID", "UPDATE_DATETIME")

  def apply(ggc: SyntaxProvider[GafisGatherCodefendant])(rs: WrappedResultSet): GafisGatherCodefendant = apply(ggc.resultName)(rs)
  def apply(ggc: ResultName[GafisGatherCodefendant])(rs: WrappedResultSet): GafisGatherCodefendant = new GafisGatherCodefendant(
    pkId = rs.get(ggc.pkId),
    personId = rs.get(ggc.personId),
    name = rs.get(ggc.name),
    idcard = rs.get(ggc.idcard),
    createUserId = rs.get(ggc.createUserId),
    createDatetime = rs.get(ggc.createDatetime),
    updateUserId = rs.get(ggc.updateUserId),
    updateDatetime = rs.get(ggc.updateDatetime)
  )

  val ggc = GafisGatherCodefendant.syntax("ggc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personId: Option[String], name: Option[String], idcard: Option[String], createUserId: Option[String], createDatetime: Option[DateTime], updateUserId: Option[String], updateDatetime: Option[DateTime])(implicit session: DBSession = autoSession): Option[GafisGatherCodefendant] = {
    withSQL {
      select.from(GafisGatherCodefendant as ggc).where.eq(ggc.pkId, pkId).and.eq(ggc.personId, personId).and.eq(ggc.name, name).and.eq(ggc.idcard, idcard).and.eq(ggc.createUserId, createUserId).and.eq(ggc.createDatetime, createDatetime).and.eq(ggc.updateUserId, updateUserId).and.eq(ggc.updateDatetime, updateDatetime)
    }.map(GafisGatherCodefendant(ggc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherCodefendant] = {
    withSQL(select.from(GafisGatherCodefendant as ggc)).map(GafisGatherCodefendant(ggc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherCodefendant as ggc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherCodefendant] = {
    withSQL {
      select.from(GafisGatherCodefendant as ggc).where.append(where)
    }.map(GafisGatherCodefendant(ggc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherCodefendant] = {
    withSQL {
      select.from(GafisGatherCodefendant as ggc).where.append(where)
    }.map(GafisGatherCodefendant(ggc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherCodefendant as ggc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personId: Option[String] = None,
    name: Option[String] = None,
    idcard: Option[String] = None,
    createUserId: Option[String] = None,
    createDatetime: Option[DateTime] = None,
    updateUserId: Option[String] = None,
    updateDatetime: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisGatherCodefendant = {
    withSQL {
      insert.into(GafisGatherCodefendant).columns(
        column.pkId,
        column.personId,
        column.name,
        column.idcard,
        column.createUserId,
        column.createDatetime,
        column.updateUserId,
        column.updateDatetime
      ).values(
        pkId,
        personId,
        name,
        idcard,
        createUserId,
        createDatetime,
        updateUserId,
        updateDatetime
      )
    }.update.apply()

    GafisGatherCodefendant(
      pkId = pkId,
      personId = personId,
      name = name,
      idcard = idcard,
      createUserId = createUserId,
      createDatetime = createDatetime,
      updateUserId = updateUserId,
      updateDatetime = updateDatetime)
  }

  def save(entity: GafisGatherCodefendant)(implicit session: DBSession = autoSession): GafisGatherCodefendant = {
    withSQL {
      update(GafisGatherCodefendant).set(
        column.pkId -> entity.pkId,
        column.personId -> entity.personId,
        column.name -> entity.name,
        column.idcard -> entity.idcard,
        column.createUserId -> entity.createUserId,
        column.createDatetime -> entity.createDatetime,
        column.updateUserId -> entity.updateUserId,
        column.updateDatetime -> entity.updateDatetime
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.name, entity.name).and.eq(column.idcard, entity.idcard).and.eq(column.createUserId, entity.createUserId).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.updateUserId, entity.updateUserId).and.eq(column.updateDatetime, entity.updateDatetime)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherCodefendant)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherCodefendant).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.name, entity.name).and.eq(column.idcard, entity.idcard).and.eq(column.createUserId, entity.createUserId).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.updateUserId, entity.updateUserId).and.eq(column.updateDatetime, entity.updateDatetime) }.update.apply()
  }

}
