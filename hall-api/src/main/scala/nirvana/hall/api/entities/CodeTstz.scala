package nirvana.hall.api.entities

import scalikejdbc._

case class CodeTstz(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeTstz.autoSession): CodeTstz = CodeTstz.save(this)(session)

  def destroy()(implicit session: DBSession = CodeTstz.autoSession): Unit = CodeTstz.destroy(this)(session)

}


object CodeTstz extends SQLSyntaxSupport[CodeTstz] {

  override val tableName = "CODE_TSTZ"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(ct: SyntaxProvider[CodeTstz])(rs: WrappedResultSet): CodeTstz = apply(ct.resultName)(rs)
  def apply(ct: ResultName[CodeTstz])(rs: WrappedResultSet): CodeTstz = new CodeTstz(
    code = rs.get(ct.code),
    name = rs.get(ct.name),
    deleteFlag = rs.get(ct.deleteFlag),
    ord = rs.get(ct.ord),
    remark = rs.get(ct.remark)
  )

  val ct = CodeTstz.syntax("ct")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeTstz] = {
    withSQL {
      select.from(CodeTstz as ct).where.eq(ct.code, code).and.eq(ct.name, name).and.eq(ct.deleteFlag, deleteFlag).and.eq(ct.ord, ord).and.eq(ct.remark, remark)
    }.map(CodeTstz(ct.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeTstz] = {
    withSQL(select.from(CodeTstz as ct)).map(CodeTstz(ct.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeTstz as ct)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeTstz] = {
    withSQL {
      select.from(CodeTstz as ct).where.append(where)
    }.map(CodeTstz(ct.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeTstz] = {
    withSQL {
      select.from(CodeTstz as ct).where.append(where)
    }.map(CodeTstz(ct.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeTstz as ct).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeTstz = {
    withSQL {
      insert.into(CodeTstz).columns(
        column.code,
        column.name,
        column.deleteFlag,
        column.ord,
        column.remark
      ).values(
        code,
        name,
        deleteFlag,
        ord,
        remark
      )
    }.update.apply()

    CodeTstz(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeTstz)(implicit session: DBSession = autoSession): CodeTstz = {
    withSQL {
      update(CodeTstz).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeTstz)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeTstz).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
