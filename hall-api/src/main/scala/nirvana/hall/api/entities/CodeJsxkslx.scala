package nirvana.hall.api.entities

import scalikejdbc._

case class CodeJsxkslx(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeJsxkslx.autoSession): CodeJsxkslx = CodeJsxkslx.save(this)(session)

  def destroy()(implicit session: DBSession = CodeJsxkslx.autoSession): Unit = CodeJsxkslx.destroy(this)(session)

}


object CodeJsxkslx extends SQLSyntaxSupport[CodeJsxkslx] {

  override val tableName = "CODE_JSXKSLX"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cj: SyntaxProvider[CodeJsxkslx])(rs: WrappedResultSet): CodeJsxkslx = apply(cj.resultName)(rs)
  def apply(cj: ResultName[CodeJsxkslx])(rs: WrappedResultSet): CodeJsxkslx = new CodeJsxkslx(
    code = rs.get(cj.code),
    name = rs.get(cj.name),
    deleteFlag = rs.get(cj.deleteFlag),
    ord = rs.get(cj.ord),
    remark = rs.get(cj.remark)
  )

  val cj = CodeJsxkslx.syntax("cj")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeJsxkslx] = {
    withSQL {
      select.from(CodeJsxkslx as cj).where.eq(cj.code, code).and.eq(cj.name, name).and.eq(cj.deleteFlag, deleteFlag).and.eq(cj.ord, ord).and.eq(cj.remark, remark)
    }.map(CodeJsxkslx(cj.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeJsxkslx] = {
    withSQL(select.from(CodeJsxkslx as cj)).map(CodeJsxkslx(cj.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeJsxkslx as cj)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeJsxkslx] = {
    withSQL {
      select.from(CodeJsxkslx as cj).where.append(where)
    }.map(CodeJsxkslx(cj.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeJsxkslx] = {
    withSQL {
      select.from(CodeJsxkslx as cj).where.append(where)
    }.map(CodeJsxkslx(cj.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeJsxkslx as cj).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeJsxkslx = {
    withSQL {
      insert.into(CodeJsxkslx).columns(
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

    CodeJsxkslx(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeJsxkslx)(implicit session: DBSession = autoSession): CodeJsxkslx = {
    withSQL {
      update(CodeJsxkslx).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeJsxkslx)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeJsxkslx).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
