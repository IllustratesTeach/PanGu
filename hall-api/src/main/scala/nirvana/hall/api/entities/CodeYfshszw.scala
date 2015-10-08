package nirvana.hall.api.entities

import scalikejdbc._

case class CodeYfshszw(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeYfshszw.autoSession): CodeYfshszw = CodeYfshszw.save(this)(session)

  def destroy()(implicit session: DBSession = CodeYfshszw.autoSession): Unit = CodeYfshszw.destroy(this)(session)

}


object CodeYfshszw extends SQLSyntaxSupport[CodeYfshszw] {

  override val tableName = "CODE_YFSHSZW"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cy: SyntaxProvider[CodeYfshszw])(rs: WrappedResultSet): CodeYfshszw = apply(cy.resultName)(rs)
  def apply(cy: ResultName[CodeYfshszw])(rs: WrappedResultSet): CodeYfshszw = new CodeYfshszw(
    code = rs.get(cy.code),
    name = rs.get(cy.name),
    deleteFlag = rs.get(cy.deleteFlag),
    ord = rs.get(cy.ord),
    remark = rs.get(cy.remark)
  )

  val cy = CodeYfshszw.syntax("cy")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeYfshszw] = {
    withSQL {
      select.from(CodeYfshszw as cy).where.eq(cy.code, code).and.eq(cy.name, name).and.eq(cy.deleteFlag, deleteFlag).and.eq(cy.ord, ord).and.eq(cy.remark, remark)
    }.map(CodeYfshszw(cy.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeYfshszw] = {
    withSQL(select.from(CodeYfshszw as cy)).map(CodeYfshszw(cy.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeYfshszw as cy)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeYfshszw] = {
    withSQL {
      select.from(CodeYfshszw as cy).where.append(where)
    }.map(CodeYfshszw(cy.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeYfshszw] = {
    withSQL {
      select.from(CodeYfshszw as cy).where.append(where)
    }.map(CodeYfshszw(cy.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeYfshszw as cy).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeYfshszw = {
    withSQL {
      insert.into(CodeYfshszw).columns(
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

    CodeYfshszw(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeYfshszw)(implicit session: DBSession = autoSession): CodeYfshszw = {
    withSQL {
      update(CodeYfshszw).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeYfshszw)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeYfshszw).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
