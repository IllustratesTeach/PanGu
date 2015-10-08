package nirvana.hall.api.entities

import scalikejdbc._

case class GafisGatherFingerPersonId(
  personId: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherFingerPersonId.autoSession): GafisGatherFingerPersonId = GafisGatherFingerPersonId.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherFingerPersonId.autoSession): Unit = GafisGatherFingerPersonId.destroy(this)(session)

}


object GafisGatherFingerPersonId extends SQLSyntaxSupport[GafisGatherFingerPersonId] {

  override val tableName = "GAFIS_GATHER_FINGER_PERSON_ID"

  override val columns = Seq("PERSON_ID")

  def apply(ggfpi: SyntaxProvider[GafisGatherFingerPersonId])(rs: WrappedResultSet): GafisGatherFingerPersonId = apply(ggfpi.resultName)(rs)
  def apply(ggfpi: ResultName[GafisGatherFingerPersonId])(rs: WrappedResultSet): GafisGatherFingerPersonId = new GafisGatherFingerPersonId(
    personId = rs.get(ggfpi.personId)
  )

  val ggfpi = GafisGatherFingerPersonId.syntax("ggfpi")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(personId: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherFingerPersonId] = {
    withSQL {
      select.from(GafisGatherFingerPersonId as ggfpi).where.eq(ggfpi.personId, personId)
    }.map(GafisGatherFingerPersonId(ggfpi.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherFingerPersonId] = {
    withSQL(select.from(GafisGatherFingerPersonId as ggfpi)).map(GafisGatherFingerPersonId(ggfpi.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherFingerPersonId as ggfpi)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherFingerPersonId] = {
    withSQL {
      select.from(GafisGatherFingerPersonId as ggfpi).where.append(where)
    }.map(GafisGatherFingerPersonId(ggfpi.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherFingerPersonId] = {
    withSQL {
      select.from(GafisGatherFingerPersonId as ggfpi).where.append(where)
    }.map(GafisGatherFingerPersonId(ggfpi.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherFingerPersonId as ggfpi).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    personId: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherFingerPersonId = {
    withSQL {
      insert.into(GafisGatherFingerPersonId).columns(
        column.personId
      ).values(
        personId
      )
    }.update.apply()

    GafisGatherFingerPersonId(
      personId = personId)
  }

  def save(entity: GafisGatherFingerPersonId)(implicit session: DBSession = autoSession): GafisGatherFingerPersonId = {
    withSQL {
      update(GafisGatherFingerPersonId).set(
        column.personId -> entity.personId
      ).where.eq(column.personId, entity.personId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherFingerPersonId)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherFingerPersonId).where.eq(column.personId, entity.personId) }.update.apply()
  }

}
