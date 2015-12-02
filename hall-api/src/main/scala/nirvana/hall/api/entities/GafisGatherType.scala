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
  ruleId: Option[String] = None,
  cardRuleId: Option[String] = None,
  cardRuleType: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherType.autoSession): GafisGatherType = GafisGatherType.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherType.autoSession): Unit = GafisGatherType.destroy(this)(session)

}


object GafisGatherType extends SQLSyntaxSupport[GafisGatherType] {

  override val tableName = "GAFIS_GATHER_TYPE"

  override val columns = Seq("PK_ID", "TYPE_NAME", "DELETE_FLAG", "CREATE_USER_ID", "CREATE_DATETIME", "UPDATE_USER_ID", "UPDATE_DATETIME", "MENU_ID", "PERSON_CATEGORY", "GATHER_CATEGORY", "PARENT_ID", "ISCHILDREN", "RULE_ID", "CARD_RULE_ID", "CARD_RULE_TYPE")

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
    ruleId = rs.get(ggt.ruleId),
    cardRuleId = rs.get(ggt.cardRuleId),
    cardRuleType = rs.get(ggt.cardRuleType)
  )

  val ggt = GafisGatherType.syntax("ggt")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[GafisGatherType] = {
    withSQL {
      select.from(GafisGatherType as ggt).where.eq(ggt.pkId, pkId)
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
    ruleId: Option[String] = None,
    cardRuleId: Option[String] = None,
    cardRuleType: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherType = {
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
        column.ruleId,
        column.cardRuleId,
        column.cardRuleType
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
        ruleId,
        cardRuleId,
        cardRuleType
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
      ruleId = ruleId,
      cardRuleId = cardRuleId,
      cardRuleType = cardRuleType)
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
        column.ruleId -> entity.ruleId,
        column.cardRuleId -> entity.cardRuleId,
        column.cardRuleType -> entity.cardRuleType
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherType)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherType).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
