package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysMeg(
  pkId: String,
  personId: String,
  personName: Option[String] = None,
  gatherUserId: Option[String] = None,
  megType: Option[Long] = None,
  message: Option[String] = None,
  lookStatus: Option[Long] = None,
  inputtime: Option[DateTime] = None,
  gatherDepartCode: Option[String] = None) {

  def save()(implicit session: DBSession = SysMeg.autoSession): SysMeg = SysMeg.save(this)(session)

  def destroy()(implicit session: DBSession = SysMeg.autoSession): Unit = SysMeg.destroy(this)(session)

}


object SysMeg extends SQLSyntaxSupport[SysMeg] {

  override val tableName = "SYS_MEG"

  override val columns = Seq("PK_ID", "PERSON_ID", "PERSON_NAME", "GATHER_USER_ID", "MEG_TYPE", "MESSAGE", "LOOK_STATUS", "INPUTTIME", "GATHER_DEPART_CODE")

  def apply(sm: SyntaxProvider[SysMeg])(rs: WrappedResultSet): SysMeg = apply(sm.resultName)(rs)
  def apply(sm: ResultName[SysMeg])(rs: WrappedResultSet): SysMeg = new SysMeg(
    pkId = rs.get(sm.pkId),
    personId = rs.get(sm.personId),
    personName = rs.get(sm.personName),
    gatherUserId = rs.get(sm.gatherUserId),
    megType = rs.get(sm.megType),
    message = rs.get(sm.message),
    lookStatus = rs.get(sm.lookStatus),
    inputtime = rs.get(sm.inputtime),
    gatherDepartCode = rs.get(sm.gatherDepartCode)
  )

  val sm = SysMeg.syntax("sm")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[SysMeg] = {
    withSQL {
      select.from(SysMeg as sm).where.eq(sm.pkId, pkId)
    }.map(SysMeg(sm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysMeg] = {
    withSQL(select.from(SysMeg as sm)).map(SysMeg(sm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysMeg as sm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysMeg] = {
    withSQL {
      select.from(SysMeg as sm).where.append(where)
    }.map(SysMeg(sm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysMeg] = {
    withSQL {
      select.from(SysMeg as sm).where.append(where)
    }.map(SysMeg(sm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysMeg as sm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personId: String,
    personName: Option[String] = None,
    gatherUserId: Option[String] = None,
    megType: Option[Long] = None,
    message: Option[String] = None,
    lookStatus: Option[Long] = None,
    inputtime: Option[DateTime] = None,
    gatherDepartCode: Option[String] = None)(implicit session: DBSession = autoSession): SysMeg = {
    withSQL {
      insert.into(SysMeg).columns(
        column.pkId,
        column.personId,
        column.personName,
        column.gatherUserId,
        column.megType,
        column.message,
        column.lookStatus,
        column.inputtime,
        column.gatherDepartCode
      ).values(
        pkId,
        personId,
        personName,
        gatherUserId,
        megType,
        message,
        lookStatus,
        inputtime,
        gatherDepartCode
      )
    }.update.apply()

    SysMeg(
      pkId = pkId,
      personId = personId,
      personName = personName,
      gatherUserId = gatherUserId,
      megType = megType,
      message = message,
      lookStatus = lookStatus,
      inputtime = inputtime,
      gatherDepartCode = gatherDepartCode)
  }

  def save(entity: SysMeg)(implicit session: DBSession = autoSession): SysMeg = {
    withSQL {
      update(SysMeg).set(
        column.pkId -> entity.pkId,
        column.personId -> entity.personId,
        column.personName -> entity.personName,
        column.gatherUserId -> entity.gatherUserId,
        column.megType -> entity.megType,
        column.message -> entity.message,
        column.lookStatus -> entity.lookStatus,
        column.inputtime -> entity.inputtime,
        column.gatherDepartCode -> entity.gatherDepartCode
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: SysMeg)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysMeg).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
