package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherType(
  pkId: String,
  typeName: String,
  deleteFlag: Option[Long] = None,
  createUserId: String,
  createDatetime: DateTime,
  updateUserId: Option[String] = None,
  updateDatetime: Option[DateTime] = None,
  menuId: Option[String] = None,
  personCategory: Option[String] = None,
  gatherCategory: Option[String] = None,
  parentId: Option[String] = None,
  ischildren: Option[String] = None,
  ruleId: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherType.autoSession): GafisGatherType = GafisGatherType.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherType.autoSession): Unit = GafisGatherType.destroy(this)(session)

}


object GafisGatherType extends SQLSyntaxSupport[GafisGatherType] {

  override val tableName = "GAFIS_GATHER_TYPE"

  override val columns = Seq("PK_ID", "TYPE_NAME", "DELETE_FLAG", "CREATE_USER_ID", "CREATE_DATETIME", "UPDATE_USER_ID", "UPDATE_DATETIME", "MENU_ID", "PERSON_CATEGORY", "GATHER_CATEGORY", "PARENT_ID", "ISCHILDREN", "RULE_ID")

  def apply(ggt: SyntaxProvider[GafisGatherType])(rs: WrappedResultSet): GafisGatherType = apply(ggt.resultName)(rs)
  def apply(ggt: ResultName[GafisGatherType])(rs: WrappedResultSet): GafisGatherType = new GafisGatherType(
    pkId = rs.get(ggt.pkId),
    typeName = rs.get(ggt.typeName),
    deleteFlag = rs.get(ggt.deleteFlag),
    createUserId = rs.get(ggt.createUserId),
    createDatetime = rs.get(ggt.createDatetime),
    updateUserId = rs.get(ggt.updateUserId),
    updateDatetime = rs.get(ggt.updateDatetime),
    menuId = rs.get(ggt.menuId),
    personCategory = rs.get(ggt.personCategory),
    gatherCategory = rs.get(ggt.gatherCategory),
    parentId = rs.get(ggt.parentId),
    ischildren = rs.get(ggt.ischildren),
    ruleId = rs.get(ggt.ruleId)
  )

  val ggt = GafisGatherType.syntax("ggt")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, typeName: String, deleteFlag: Option[Long], createUserId: String, createDatetime: DateTime, updateUserId: Option[String], updateDatetime: Option[DateTime], menuId: Option[String], personCategory: Option[String], gatherCategory: Option[String], parentId: Option[String], ischildren: Option[String], ruleId: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherType] = {
    withSQL {
      select.from(GafisGatherType as ggt).where.eq(ggt.pkId, pkId).and.eq(ggt.typeName, typeName).and.eq(ggt.deleteFlag, deleteFlag).and.eq(ggt.createUserId, createUserId).and.eq(ggt.createDatetime, createDatetime).and.eq(ggt.updateUserId, updateUserId).and.eq(ggt.updateDatetime, updateDatetime).and.eq(ggt.menuId, menuId).and.eq(ggt.personCategory, personCategory).and.eq(ggt.gatherCategory, gatherCategory).and.eq(ggt.parentId, parentId).and.eq(ggt.ischildren, ischildren).and.eq(ggt.ruleId, ruleId)
    }.map(GafisGatherType(ggt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherType] = {
    withSQL(select.from(GafisGatherType as ggt)).map(GafisGatherType(ggt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherType as ggt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherType] = {
    withSQL {
      select.from(GafisGatherType as ggt).where.append(where)
    }.map(GafisGatherType(ggt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherType] = {
    withSQL {
      select.from(GafisGatherType as ggt).where.append(where)
    }.map(GafisGatherType(ggt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherType as ggt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    typeName: String,
    deleteFlag: Option[Long] = None,
    createUserId: String,
    createDatetime: DateTime,
    updateUserId: Option[String] = None,
    updateDatetime: Option[DateTime] = None,
    menuId: Option[String] = None,
    personCategory: Option[String] = None,
    gatherCategory: Option[String] = None,
    parentId: Option[String] = None,
    ischildren: Option[String] = None,
    ruleId: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherType = {
    withSQL {
      insert.into(GafisGatherType).columns(
        column.pkId,
        column.typeName,
        column.deleteFlag,
        column.createUserId,
        column.createDatetime,
        column.updateUserId,
        column.updateDatetime,
        column.menuId,
        column.personCategory,
        column.gatherCategory,
        column.parentId,
        column.ischildren,
        column.ruleId
      ).values(
        pkId,
        typeName,
        deleteFlag,
        createUserId,
        createDatetime,
        updateUserId,
        updateDatetime,
        menuId,
        personCategory,
        gatherCategory,
        parentId,
        ischildren,
        ruleId
      )
    }.update.apply()

    GafisGatherType(
      pkId = pkId,
      typeName = typeName,
      deleteFlag = deleteFlag,
      createUserId = createUserId,
      createDatetime = createDatetime,
      updateUserId = updateUserId,
      updateDatetime = updateDatetime,
      menuId = menuId,
      personCategory = personCategory,
      gatherCategory = gatherCategory,
      parentId = parentId,
      ischildren = ischildren,
      ruleId = ruleId)
  }

  def save(entity: GafisGatherType)(implicit session: DBSession = autoSession): GafisGatherType = {
    withSQL {
      update(GafisGatherType).set(
        column.pkId -> entity.pkId,
        column.typeName -> entity.typeName,
        column.deleteFlag -> entity.deleteFlag,
        column.createUserId -> entity.createUserId,
        column.createDatetime -> entity.createDatetime,
        column.updateUserId -> entity.updateUserId,
        column.updateDatetime -> entity.updateDatetime,
        column.menuId -> entity.menuId,
        column.personCategory -> entity.personCategory,
        column.gatherCategory -> entity.gatherCategory,
        column.parentId -> entity.parentId,
        column.ischildren -> entity.ischildren,
        column.ruleId -> entity.ruleId
      ).where.eq(column.pkId, entity.pkId).and.eq(column.typeName, entity.typeName).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.createUserId, entity.createUserId).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.updateUserId, entity.updateUserId).and.eq(column.updateDatetime, entity.updateDatetime).and.eq(column.menuId, entity.menuId).and.eq(column.personCategory, entity.personCategory).and.eq(column.gatherCategory, entity.gatherCategory).and.eq(column.parentId, entity.parentId).and.eq(column.ischildren, entity.ischildren).and.eq(column.ruleId, entity.ruleId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherType)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherType).where.eq(column.pkId, entity.pkId).and.eq(column.typeName, entity.typeName).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.createUserId, entity.createUserId).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.updateUserId, entity.updateUserId).and.eq(column.updateDatetime, entity.updateDatetime).and.eq(column.menuId, entity.menuId).and.eq(column.personCategory, entity.personCategory).and.eq(column.gatherCategory, entity.gatherCategory).and.eq(column.parentId, entity.parentId).and.eq(column.ischildren, entity.ischildren).and.eq(column.ruleId, entity.ruleId) }.update.apply()
  }

}
