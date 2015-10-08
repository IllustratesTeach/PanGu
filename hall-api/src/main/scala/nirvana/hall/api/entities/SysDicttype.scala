package nirvana.hall.api.entities

import scalikejdbc._

case class SysDicttype(
  typeCode: String,
  isCommon: Option[Long] = None,
  typeName: Option[String] = None,
  deleteFlag: Option[String] = None,
  remark: Option[String] = None,
  typeTableName: Option[String] = None) {

  def save()(implicit session: DBSession = SysDicttype.autoSession): SysDicttype = SysDicttype.save(this)(session)

  def destroy()(implicit session: DBSession = SysDicttype.autoSession): Unit = SysDicttype.destroy(this)(session)

}


object SysDicttype extends SQLSyntaxSupport[SysDicttype] {

  override val tableName = "SYS_DICTTYPE"

  override val columns = Seq("TYPE_CODE", "IS_COMMON", "TYPE_NAME", "DELETE_FLAG", "REMARK", "TYPE_TABLE_NAME")

  def apply(sd: SyntaxProvider[SysDicttype])(rs: WrappedResultSet): SysDicttype = apply(sd.resultName)(rs)
  def apply(sd: ResultName[SysDicttype])(rs: WrappedResultSet): SysDicttype = new SysDicttype(
    typeCode = rs.get(sd.typeCode),
    isCommon = rs.get(sd.isCommon),
    typeName = rs.get(sd.typeName),
    deleteFlag = rs.get(sd.deleteFlag),
    remark = rs.get(sd.remark),
    typeTableName = rs.get(sd.typeTableName)
  )

  val sd = SysDicttype.syntax("sd")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(typeCode: String, isCommon: Option[Long], typeName: Option[String], deleteFlag: Option[String], remark: Option[String], typeTableName: Option[String])(implicit session: DBSession = autoSession): Option[SysDicttype] = {
    withSQL {
      select.from(SysDicttype as sd).where.eq(sd.typeCode, typeCode).and.eq(sd.isCommon, isCommon).and.eq(sd.typeName, typeName).and.eq(sd.deleteFlag, deleteFlag).and.eq(sd.remark, remark).and.eq(sd.typeTableName, typeTableName)
    }.map(SysDicttype(sd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysDicttype] = {
    withSQL(select.from(SysDicttype as sd)).map(SysDicttype(sd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysDicttype as sd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysDicttype] = {
    withSQL {
      select.from(SysDicttype as sd).where.append(where)
    }.map(SysDicttype(sd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysDicttype] = {
    withSQL {
      select.from(SysDicttype as sd).where.append(where)
    }.map(SysDicttype(sd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysDicttype as sd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    typeCode: String,
    isCommon: Option[Long] = None,
    typeName: Option[String] = None,
    deleteFlag: Option[String] = None,
    remark: Option[String] = None,
    typeTableName: Option[String] = None)(implicit session: DBSession = autoSession): SysDicttype = {
    withSQL {
      insert.into(SysDicttype).columns(
        column.typeCode,
        column.isCommon,
        column.typeName,
        column.deleteFlag,
        column.remark,
        column.typeTableName
      ).values(
        typeCode,
        isCommon,
        typeName,
        deleteFlag,
        remark,
        typeTableName
      )
    }.update.apply()

    SysDicttype(
      typeCode = typeCode,
      isCommon = isCommon,
      typeName = typeName,
      deleteFlag = deleteFlag,
      remark = remark,
      typeTableName = typeTableName)
  }

  def save(entity: SysDicttype)(implicit session: DBSession = autoSession): SysDicttype = {
    withSQL {
      update(SysDicttype).set(
        column.typeCode -> entity.typeCode,
        column.isCommon -> entity.isCommon,
        column.typeName -> entity.typeName,
        column.deleteFlag -> entity.deleteFlag,
        column.remark -> entity.remark,
        column.typeTableName -> entity.typeTableName
      ).where.eq(column.typeCode, entity.typeCode).and.eq(column.isCommon, entity.isCommon).and.eq(column.typeName, entity.typeName).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.remark, entity.remark).and.eq(column.typeTableName, entity.typeTableName)
    }.update.apply()
    entity
  }

  def destroy(entity: SysDicttype)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysDicttype).where.eq(column.typeCode, entity.typeCode).and.eq(column.isCommon, entity.isCommon).and.eq(column.typeName, entity.typeName).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.remark, entity.remark).and.eq(column.typeTableName, entity.typeTableName) }.update.apply()
  }

}
