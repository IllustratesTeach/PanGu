package nirvana.hall.api.entities

import scalikejdbc._

case class SysArea(
  areaCode: String,
  areaName: Option[String] = None,
  areaAlias: Option[String] = None,
  parentId: Option[String] = None,
  deleteFlag: Option[String] = None,
  remark: Option[String] = None,
  isLeaf: Option[String] = None,
  areanumber: Option[String] = None,
  areaFirstLetter: Option[String] = None) {

  def save()(implicit session: DBSession = SysArea.autoSession): SysArea = SysArea.save(this)(session)

  def destroy()(implicit session: DBSession = SysArea.autoSession): Unit = SysArea.destroy(this)(session)

}


object SysArea extends SQLSyntaxSupport[SysArea] {

  override val tableName = "SYS_AREA"

  override val columns = Seq("AREA_CODE", "AREA_NAME", "AREA_ALIAS", "PARENT_ID", "DELETE_FLAG", "REMARK", "IS_LEAF", "AREANUMBER", "AREA_FIRST_LETTER")

  def apply(sa: SyntaxProvider[SysArea])(rs: WrappedResultSet): SysArea = apply(sa.resultName)(rs)
  def apply(sa: ResultName[SysArea])(rs: WrappedResultSet): SysArea = new SysArea(
    areaCode = rs.get(sa.areaCode),
    areaName = rs.get(sa.areaName),
    areaAlias = rs.get(sa.areaAlias),
    parentId = rs.get(sa.parentId),
    deleteFlag = rs.get(sa.deleteFlag),
    remark = rs.get(sa.remark),
    isLeaf = rs.get(sa.isLeaf),
    areanumber = rs.get(sa.areanumber),
    areaFirstLetter = rs.get(sa.areaFirstLetter)
  )

  val sa = SysArea.syntax("sa")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(areaCode: String)(implicit session: DBSession = autoSession): Option[SysArea] = {
    withSQL {
      select.from(SysArea as sa).where.eq(sa.areaCode, areaCode)
    }.map(SysArea(sa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysArea] = {
    withSQL(select.from(SysArea as sa)).map(SysArea(sa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysArea as sa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysArea] = {
    withSQL {
      select.from(SysArea as sa).where.append(where)
    }.map(SysArea(sa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysArea] = {
    withSQL {
      select.from(SysArea as sa).where.append(where)
    }.map(SysArea(sa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysArea as sa).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    areaCode: String,
    areaName: Option[String] = None,
    areaAlias: Option[String] = None,
    parentId: Option[String] = None,
    deleteFlag: Option[String] = None,
    remark: Option[String] = None,
    isLeaf: Option[String] = None,
    areanumber: Option[String] = None,
    areaFirstLetter: Option[String] = None)(implicit session: DBSession = autoSession): SysArea = {
    withSQL {
      insert.into(SysArea).columns(
        column.areaCode,
        column.areaName,
        column.areaAlias,
        column.parentId,
        column.deleteFlag,
        column.remark,
        column.isLeaf,
        column.areanumber,
        column.areaFirstLetter
      ).values(
        areaCode,
        areaName,
        areaAlias,
        parentId,
        deleteFlag,
        remark,
        isLeaf,
        areanumber,
        areaFirstLetter
      )
    }.update.apply()

    SysArea(
      areaCode = areaCode,
      areaName = areaName,
      areaAlias = areaAlias,
      parentId = parentId,
      deleteFlag = deleteFlag,
      remark = remark,
      isLeaf = isLeaf,
      areanumber = areanumber,
      areaFirstLetter = areaFirstLetter)
  }

  def save(entity: SysArea)(implicit session: DBSession = autoSession): SysArea = {
    withSQL {
      update(SysArea).set(
        column.areaCode -> entity.areaCode,
        column.areaName -> entity.areaName,
        column.areaAlias -> entity.areaAlias,
        column.parentId -> entity.parentId,
        column.deleteFlag -> entity.deleteFlag,
        column.remark -> entity.remark,
        column.isLeaf -> entity.isLeaf,
        column.areanumber -> entity.areanumber,
        column.areaFirstLetter -> entity.areaFirstLetter
      ).where.eq(column.areaCode, entity.areaCode)
    }.update.apply()
    entity
  }

  def destroy(entity: SysArea)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysArea).where.eq(column.areaCode, entity.areaCode) }.update.apply()
  }

}
