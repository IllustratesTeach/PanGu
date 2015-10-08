package nirvana.hall.api.entities

import scalikejdbc._

case class CodeSfshlx(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeSfshlx.autoSession): CodeSfshlx = CodeSfshlx.save(this)(session)

  def destroy()(implicit session: DBSession = CodeSfshlx.autoSession): Unit = CodeSfshlx.destroy(this)(session)

}


object CodeSfshlx extends SQLSyntaxSupport[CodeSfshlx] {

  override val tableName = "CODE_SFSHLX"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cs: SyntaxProvider[CodeSfshlx])(rs: WrappedResultSet): CodeSfshlx = apply(cs.resultName)(rs)
  def apply(cs: ResultName[CodeSfshlx])(rs: WrappedResultSet): CodeSfshlx = new CodeSfshlx(
    code = rs.get(cs.code),
    name = rs.get(cs.name),
    deleteFlag = rs.get(cs.deleteFlag),
    ord = rs.get(cs.ord),
    remark = rs.get(cs.remark)
  )

  val cs = CodeSfshlx.syntax("cs")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeSfshlx] = {
    withSQL {
      select.from(CodeSfshlx as cs).where.eq(cs.code, code).and.eq(cs.name, name).and.eq(cs.deleteFlag, deleteFlag).and.eq(cs.ord, ord).and.eq(cs.remark, remark)
    }.map(CodeSfshlx(cs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeSfshlx] = {
    withSQL(select.from(CodeSfshlx as cs)).map(CodeSfshlx(cs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeSfshlx as cs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeSfshlx] = {
    withSQL {
      select.from(CodeSfshlx as cs).where.append(where)
    }.map(CodeSfshlx(cs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeSfshlx] = {
    withSQL {
      select.from(CodeSfshlx as cs).where.append(where)
    }.map(CodeSfshlx(cs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeSfshlx as cs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeSfshlx = {
    withSQL {
      insert.into(CodeSfshlx).columns(
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

    CodeSfshlx(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeSfshlx)(implicit session: DBSession = autoSession): CodeSfshlx = {
    withSQL {
      update(CodeSfshlx).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeSfshlx)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeSfshlx).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
