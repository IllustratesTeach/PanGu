package nirvana.hall.api.entities

import scalikejdbc._

case class CodeIf(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeIf.autoSession): CodeIf = CodeIf.save(this)(session)

  def destroy()(implicit session: DBSession = CodeIf.autoSession): Unit = CodeIf.destroy(this)(session)

}


object CodeIf extends SQLSyntaxSupport[CodeIf] {

  override val tableName = "CODE_IF"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(ci: SyntaxProvider[CodeIf])(rs: WrappedResultSet): CodeIf = apply(ci.resultName)(rs)
  def apply(ci: ResultName[CodeIf])(rs: WrappedResultSet): CodeIf = new CodeIf(
    code = rs.get(ci.code),
    name = rs.get(ci.name),
    deleteFlag = rs.get(ci.deleteFlag),
    ord = rs.get(ci.ord),
    remark = rs.get(ci.remark)
  )

  val ci = CodeIf.syntax("ci")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeIf] = {
    withSQL {
      select.from(CodeIf as ci).where.eq(ci.code, code).and.eq(ci.name, name).and.eq(ci.deleteFlag, deleteFlag).and.eq(ci.ord, ord).and.eq(ci.remark, remark)
    }.map(CodeIf(ci.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeIf] = {
    withSQL(select.from(CodeIf as ci)).map(CodeIf(ci.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeIf as ci)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeIf] = {
    withSQL {
      select.from(CodeIf as ci).where.append(where)
    }.map(CodeIf(ci.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeIf] = {
    withSQL {
      select.from(CodeIf as ci).where.append(where)
    }.map(CodeIf(ci.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeIf as ci).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeIf = {
    withSQL {
      insert.into(CodeIf).columns(
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

    CodeIf(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeIf)(implicit session: DBSession = autoSession): CodeIf = {
    withSQL {
      update(CodeIf).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeIf)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeIf).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
