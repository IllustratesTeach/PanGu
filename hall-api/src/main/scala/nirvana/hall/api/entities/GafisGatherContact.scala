package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherContact(
  pkId: String,
  personId: Option[String] = None,
  contactKey: Option[String] = None,
  contactKeyVal: Option[String] = None,
  contactValue: Option[String] = None,
  createUserId: Option[String] = None,
  createDatetime: Option[DateTime] = None,
  updateUserId: Option[String] = None,
  updateDatetime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisGatherContact.autoSession): GafisGatherContact = GafisGatherContact.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherContact.autoSession): Unit = GafisGatherContact.destroy(this)(session)

}


object GafisGatherContact extends SQLSyntaxSupport[GafisGatherContact] {

  override val tableName = "GAFIS_GATHER_CONTACT"

  override val columns = Seq("PK_ID", "PERSON_ID", "CONTACT_KEY", "CONTACT_KEY_VAL", "CONTACT_VALUE", "CREATE_USER_ID", "CREATE_DATETIME", "UPDATE_USER_ID", "UPDATE_DATETIME")

  def apply(ggc: SyntaxProvider[GafisGatherContact])(rs: WrappedResultSet): GafisGatherContact = apply(ggc.resultName)(rs)
  def apply(ggc: ResultName[GafisGatherContact])(rs: WrappedResultSet): GafisGatherContact = new GafisGatherContact(
    pkId = rs.get(ggc.pkId),
    personId = rs.get(ggc.personId),
    contactKey = rs.get(ggc.contactKey),
    contactKeyVal = rs.get(ggc.contactKeyVal),
    contactValue = rs.get(ggc.contactValue),
    createUserId = rs.get(ggc.createUserId),
    createDatetime = rs.get(ggc.createDatetime),
    updateUserId = rs.get(ggc.updateUserId),
    updateDatetime = rs.get(ggc.updateDatetime)
  )

  val ggc = GafisGatherContact.syntax("ggc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personId: Option[String], contactKey: Option[String], contactKeyVal: Option[String], contactValue: Option[String], createUserId: Option[String], createDatetime: Option[DateTime], updateUserId: Option[String], updateDatetime: Option[DateTime])(implicit session: DBSession = autoSession): Option[GafisGatherContact] = {
    withSQL {
      select.from(GafisGatherContact as ggc).where.eq(ggc.pkId, pkId).and.eq(ggc.personId, personId).and.eq(ggc.contactKey, contactKey).and.eq(ggc.contactKeyVal, contactKeyVal).and.eq(ggc.contactValue, contactValue).and.eq(ggc.createUserId, createUserId).and.eq(ggc.createDatetime, createDatetime).and.eq(ggc.updateUserId, updateUserId).and.eq(ggc.updateDatetime, updateDatetime)
    }.map(GafisGatherContact(ggc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherContact] = {
    withSQL(select.from(GafisGatherContact as ggc)).map(GafisGatherContact(ggc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherContact as ggc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherContact] = {
    withSQL {
      select.from(GafisGatherContact as ggc).where.append(where)
    }.map(GafisGatherContact(ggc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherContact] = {
    withSQL {
      select.from(GafisGatherContact as ggc).where.append(where)
    }.map(GafisGatherContact(ggc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherContact as ggc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personId: Option[String] = None,
    contactKey: Option[String] = None,
    contactKeyVal: Option[String] = None,
    contactValue: Option[String] = None,
    createUserId: Option[String] = None,
    createDatetime: Option[DateTime] = None,
    updateUserId: Option[String] = None,
    updateDatetime: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisGatherContact = {
    withSQL {
      insert.into(GafisGatherContact).columns(
        column.pkId,
        column.personId,
        column.contactKey,
        column.contactKeyVal,
        column.contactValue,
        column.createUserId,
        column.createDatetime,
        column.updateUserId,
        column.updateDatetime
      ).values(
        pkId,
        personId,
        contactKey,
        contactKeyVal,
        contactValue,
        createUserId,
        createDatetime,
        updateUserId,
        updateDatetime
      )
    }.update.apply()

    GafisGatherContact(
      pkId = pkId,
      personId = personId,
      contactKey = contactKey,
      contactKeyVal = contactKeyVal,
      contactValue = contactValue,
      createUserId = createUserId,
      createDatetime = createDatetime,
      updateUserId = updateUserId,
      updateDatetime = updateDatetime)
  }

  def save(entity: GafisGatherContact)(implicit session: DBSession = autoSession): GafisGatherContact = {
    withSQL {
      update(GafisGatherContact).set(
        column.pkId -> entity.pkId,
        column.personId -> entity.personId,
        column.contactKey -> entity.contactKey,
        column.contactKeyVal -> entity.contactKeyVal,
        column.contactValue -> entity.contactValue,
        column.createUserId -> entity.createUserId,
        column.createDatetime -> entity.createDatetime,
        column.updateUserId -> entity.updateUserId,
        column.updateDatetime -> entity.updateDatetime
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.contactKey, entity.contactKey).and.eq(column.contactKeyVal, entity.contactKeyVal).and.eq(column.contactValue, entity.contactValue).and.eq(column.createUserId, entity.createUserId).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.updateUserId, entity.updateUserId).and.eq(column.updateDatetime, entity.updateDatetime)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherContact)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherContact).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.contactKey, entity.contactKey).and.eq(column.contactKeyVal, entity.contactKeyVal).and.eq(column.contactValue, entity.contactValue).and.eq(column.createUserId, entity.createUserId).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.updateUserId, entity.updateUserId).and.eq(column.updateDatetime, entity.updateDatetime) }.update.apply()
  }

}
