package nirvana.hall.api.entities

import scalikejdbc._

case class CodeYhsf(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeYhsf.autoSession): CodeYhsf = CodeYhsf.save(this)(session)

  def destroy()(implicit session: DBSession = CodeYhsf.autoSession): Unit = CodeYhsf.destroy(this)(session)

}


object CodeYhsf extends SQLSyntaxSupport[CodeYhsf] {

  override val tableName = "CODE_YHSF"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cy: SyntaxProvider[CodeYhsf])(rs: WrappedResultSet): CodeYhsf = apply(cy.resultName)(rs)
  def apply(cy: ResultName[CodeYhsf])(rs: WrappedResultSet): CodeYhsf = new CodeYhsf(
    code = rs.get(cy.code),
    name = rs.get(cy.name),
    deleteFlag = rs.get(cy.deleteFlag),
    ord = rs.get(cy.ord),
    remark = rs.get(cy.remark)
  )

  val cy = CodeYhsf.syntax("cy")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeYhsf] = {
    withSQL {
      select.from(CodeYhsf as cy).where.eq(cy.code, code).and.eq(cy.name, name).and.eq(cy.deleteFlag, deleteFlag).and.eq(cy.ord, ord).and.eq(cy.remark, remark)
    }.map(CodeYhsf(cy.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeYhsf] = {
    withSQL(select.from(CodeYhsf as cy)).map(CodeYhsf(cy.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeYhsf as cy)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeYhsf] = {
    withSQL {
      select.from(CodeYhsf as cy).where.append(where)
    }.map(CodeYhsf(cy.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeYhsf] = {
    withSQL {
      select.from(CodeYhsf as cy).where.append(where)
    }.map(CodeYhsf(cy.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeYhsf as cy).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeYhsf = {
    withSQL {
      insert.into(CodeYhsf).columns(
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

    CodeYhsf(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeYhsf)(implicit session: DBSession = autoSession): CodeYhsf = {
    withSQL {
      update(CodeYhsf).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeYhsf)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeYhsf).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
