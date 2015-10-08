package nirvana.hall.api.entities

import scalikejdbc._

case class SysDict(
  dictCode: String,
  dictName: Option[String] = None,
  typeId: Option[String] = None,
  openFlag: Option[Long] = None,
  remark: Option[String] = None,
  ord: Option[Long] = None,
  limitto: Option[String] = None,
  dictValue: Option[String] = None) {

  def save()(implicit session: DBSession = SysDict.autoSession): SysDict = SysDict.save(this)(session)

  def destroy()(implicit session: DBSession = SysDict.autoSession): Unit = SysDict.destroy(this)(session)

}


object SysDict extends SQLSyntaxSupport[SysDict] {

  override val tableName = "SYS_DICT"

  override val columns = Seq("DICT_CODE", "DICT_NAME", "TYPE_ID", "OPEN_FLAG", "REMARK", "ORD", "LIMITTO", "DICT_VALUE")

  def apply(sd: SyntaxProvider[SysDict])(rs: WrappedResultSet): SysDict = apply(sd.resultName)(rs)
  def apply(sd: ResultName[SysDict])(rs: WrappedResultSet): SysDict = new SysDict(
    dictCode = rs.get(sd.dictCode),
    dictName = rs.get(sd.dictName),
    typeId = rs.get(sd.typeId),
    openFlag = rs.get(sd.openFlag),
    remark = rs.get(sd.remark),
    ord = rs.get(sd.ord),
    limitto = rs.get(sd.limitto),
    dictValue = rs.get(sd.dictValue)
  )

  val sd = SysDict.syntax("sd")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(dictCode: String, dictName: Option[String], typeId: Option[String], openFlag: Option[Long], remark: Option[String], ord: Option[Long], limitto: Option[String], dictValue: Option[String])(implicit session: DBSession = autoSession): Option[SysDict] = {
    withSQL {
      select.from(SysDict as sd).where.eq(sd.dictCode, dictCode).and.eq(sd.dictName, dictName).and.eq(sd.typeId, typeId).and.eq(sd.openFlag, openFlag).and.eq(sd.remark, remark).and.eq(sd.ord, ord).and.eq(sd.limitto, limitto).and.eq(sd.dictValue, dictValue)
    }.map(SysDict(sd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysDict] = {
    withSQL(select.from(SysDict as sd)).map(SysDict(sd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysDict as sd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysDict] = {
    withSQL {
      select.from(SysDict as sd).where.append(where)
    }.map(SysDict(sd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysDict] = {
    withSQL {
      select.from(SysDict as sd).where.append(where)
    }.map(SysDict(sd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysDict as sd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dictCode: String,
    dictName: Option[String] = None,
    typeId: Option[String] = None,
    openFlag: Option[Long] = None,
    remark: Option[String] = None,
    ord: Option[Long] = None,
    limitto: Option[String] = None,
    dictValue: Option[String] = None)(implicit session: DBSession = autoSession): SysDict = {
    withSQL {
      insert.into(SysDict).columns(
        column.dictCode,
        column.dictName,
        column.typeId,
        column.openFlag,
        column.remark,
        column.ord,
        column.limitto,
        column.dictValue
      ).values(
        dictCode,
        dictName,
        typeId,
        openFlag,
        remark,
        ord,
        limitto,
        dictValue
      )
    }.update.apply()

    SysDict(
      dictCode = dictCode,
      dictName = dictName,
      typeId = typeId,
      openFlag = openFlag,
      remark = remark,
      ord = ord,
      limitto = limitto,
      dictValue = dictValue)
  }

  def save(entity: SysDict)(implicit session: DBSession = autoSession): SysDict = {
    withSQL {
      update(SysDict).set(
        column.dictCode -> entity.dictCode,
        column.dictName -> entity.dictName,
        column.typeId -> entity.typeId,
        column.openFlag -> entity.openFlag,
        column.remark -> entity.remark,
        column.ord -> entity.ord,
        column.limitto -> entity.limitto,
        column.dictValue -> entity.dictValue
      ).where.eq(column.dictCode, entity.dictCode).and.eq(column.dictName, entity.dictName).and.eq(column.typeId, entity.typeId).and.eq(column.openFlag, entity.openFlag).and.eq(column.remark, entity.remark).and.eq(column.ord, entity.ord).and.eq(column.limitto, entity.limitto).and.eq(column.dictValue, entity.dictValue)
    }.update.apply()
    entity
  }

  def destroy(entity: SysDict)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysDict).where.eq(column.dictCode, entity.dictCode).and.eq(column.dictName, entity.dictName).and.eq(column.typeId, entity.typeId).and.eq(column.openFlag, entity.openFlag).and.eq(column.remark, entity.remark).and.eq(column.ord, entity.ord).and.eq(column.limitto, entity.limitto).and.eq(column.dictValue, entity.dictValue) }.update.apply()
  }

}
