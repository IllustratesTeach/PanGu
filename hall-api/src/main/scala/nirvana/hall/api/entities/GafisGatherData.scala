package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisGatherData(
  pkId: String,
  personId: Option[String] = None,
  bty: String,
  fgp: String,
  dataNum: Short,
  lobtype: String,
  gatherData: Option[Blob] = None,
  createUserId: Option[String] = None,
  createUser: String,
  createDatetime: Option[DateTime] = None,
  updateUserId: Option[String] = None,
  updateUser: Option[String] = None,
  updateDatetime: Option[DateTime] = None,
  fgpCase: Option[Short] = None,
  groupId: Option[Short] = None,
  oldPkId: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherData.autoSession): GafisGatherData = GafisGatherData.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherData.autoSession): Unit = GafisGatherData.destroy(this)(session)

}


object GafisGatherData extends SQLSyntaxSupport[GafisGatherData] {

  override val tableName = "GAFIS_GATHER_DATA"

  override val columns = Seq("PK_ID", "PERSON_ID", "BTY", "FGP", "DATA_NUM", "LOBTYPE", "GATHER_DATA", "CREATE_USER_ID", "CREATE_USER", "CREATE_DATETIME", "UPDATE_USER_ID", "UPDATE_USER", "UPDATE_DATETIME", "FGP_CASE", "GROUP_ID", "OLD_PK_ID")

  def apply(ggd: SyntaxProvider[GafisGatherData])(rs: WrappedResultSet): GafisGatherData = apply(ggd.resultName)(rs)
  def apply(ggd: ResultName[GafisGatherData])(rs: WrappedResultSet): GafisGatherData = new GafisGatherData(
    pkId = rs.get(ggd.pkId),
    personId = rs.get(ggd.personId),
    bty = rs.get(ggd.bty),
    fgp = rs.get(ggd.fgp),
    dataNum = rs.get(ggd.dataNum),
    lobtype = rs.get(ggd.lobtype),
    gatherData = rs.get(ggd.gatherData),
    createUserId = rs.get(ggd.createUserId),
    createUser = rs.get(ggd.createUser),
    createDatetime = rs.get(ggd.createDatetime),
    updateUserId = rs.get(ggd.updateUserId),
    updateUser = rs.get(ggd.updateUser),
    updateDatetime = rs.get(ggd.updateDatetime),
    fgpCase = rs.get(ggd.fgpCase),
    groupId = rs.get(ggd.groupId),
    oldPkId = rs.get(ggd.oldPkId)
  )

  val ggd = GafisGatherData.syntax("ggd")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personId: Option[String], bty: String, fgp: String, dataNum: Short, lobtype: String, gatherData: Option[Blob], createUserId: Option[String], createUser: String, createDatetime: Option[DateTime], updateUserId: Option[String], updateUser: Option[String], updateDatetime: Option[DateTime], fgpCase: Option[Short], groupId: Option[Short], oldPkId: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherData] = {
    withSQL {
      select.from(GafisGatherData as ggd).where.eq(ggd.pkId, pkId).and.eq(ggd.personId, personId).and.eq(ggd.bty, bty).and.eq(ggd.fgp, fgp).and.eq(ggd.dataNum, dataNum).and.eq(ggd.lobtype, lobtype).and.eq(ggd.gatherData, gatherData).and.eq(ggd.createUserId, createUserId).and.eq(ggd.createUser, createUser).and.eq(ggd.createDatetime, createDatetime).and.eq(ggd.updateUserId, updateUserId).and.eq(ggd.updateUser, updateUser).and.eq(ggd.updateDatetime, updateDatetime).and.eq(ggd.fgpCase, fgpCase).and.eq(ggd.groupId, groupId).and.eq(ggd.oldPkId, oldPkId)
    }.map(GafisGatherData(ggd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherData] = {
    withSQL(select.from(GafisGatherData as ggd)).map(GafisGatherData(ggd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherData as ggd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherData] = {
    withSQL {
      select.from(GafisGatherData as ggd).where.append(where)
    }.map(GafisGatherData(ggd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherData] = {
    withSQL {
      select.from(GafisGatherData as ggd).where.append(where)
    }.map(GafisGatherData(ggd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherData as ggd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personId: Option[String] = None,
    bty: String,
    fgp: String,
    dataNum: Short,
    lobtype: String,
    gatherData: Option[Blob] = None,
    createUserId: Option[String] = None,
    createUser: String,
    createDatetime: Option[DateTime] = None,
    updateUserId: Option[String] = None,
    updateUser: Option[String] = None,
    updateDatetime: Option[DateTime] = None,
    fgpCase: Option[Short] = None,
    groupId: Option[Short] = None,
    oldPkId: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherData = {
    withSQL {
      insert.into(GafisGatherData).columns(
        column.pkId,
        column.personId,
        column.bty,
        column.fgp,
        column.dataNum,
        column.lobtype,
        column.gatherData,
        column.createUserId,
        column.createUser,
        column.createDatetime,
        column.updateUserId,
        column.updateUser,
        column.updateDatetime,
        column.fgpCase,
        column.groupId,
        column.oldPkId
      ).values(
        pkId,
        personId,
        bty,
        fgp,
        dataNum,
        lobtype,
        gatherData,
        createUserId,
        createUser,
        createDatetime,
        updateUserId,
        updateUser,
        updateDatetime,
        fgpCase,
        groupId,
        oldPkId
      )
    }.update.apply()

    GafisGatherData(
      pkId = pkId,
      personId = personId,
      bty = bty,
      fgp = fgp,
      dataNum = dataNum,
      lobtype = lobtype,
      gatherData = gatherData,
      createUserId = createUserId,
      createUser = createUser,
      createDatetime = createDatetime,
      updateUserId = updateUserId,
      updateUser = updateUser,
      updateDatetime = updateDatetime,
      fgpCase = fgpCase,
      groupId = groupId,
      oldPkId = oldPkId)
  }

  def save(entity: GafisGatherData)(implicit session: DBSession = autoSession): GafisGatherData = {
    withSQL {
      update(GafisGatherData).set(
        column.pkId -> entity.pkId,
        column.personId -> entity.personId,
        column.bty -> entity.bty,
        column.fgp -> entity.fgp,
        column.dataNum -> entity.dataNum,
        column.lobtype -> entity.lobtype,
        column.gatherData -> entity.gatherData,
        column.createUserId -> entity.createUserId,
        column.createUser -> entity.createUser,
        column.createDatetime -> entity.createDatetime,
        column.updateUserId -> entity.updateUserId,
        column.updateUser -> entity.updateUser,
        column.updateDatetime -> entity.updateDatetime,
        column.fgpCase -> entity.fgpCase,
        column.groupId -> entity.groupId,
        column.oldPkId -> entity.oldPkId
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.bty, entity.bty).and.eq(column.fgp, entity.fgp).and.eq(column.dataNum, entity.dataNum).and.eq(column.lobtype, entity.lobtype).and.eq(column.gatherData, entity.gatherData).and.eq(column.createUserId, entity.createUserId).and.eq(column.createUser, entity.createUser).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.updateUserId, entity.updateUserId).and.eq(column.updateUser, entity.updateUser).and.eq(column.updateDatetime, entity.updateDatetime).and.eq(column.fgpCase, entity.fgpCase).and.eq(column.groupId, entity.groupId).and.eq(column.oldPkId, entity.oldPkId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherData)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherData).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.bty, entity.bty).and.eq(column.fgp, entity.fgp).and.eq(column.dataNum, entity.dataNum).and.eq(column.lobtype, entity.lobtype).and.eq(column.gatherData, entity.gatherData).and.eq(column.createUserId, entity.createUserId).and.eq(column.createUser, entity.createUser).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.updateUserId, entity.updateUserId).and.eq(column.updateUser, entity.updateUser).and.eq(column.updateDatetime, entity.updateDatetime).and.eq(column.fgpCase, entity.fgpCase).and.eq(column.groupId, entity.groupId).and.eq(column.oldPkId, entity.oldPkId) }.update.apply()
  }

}
