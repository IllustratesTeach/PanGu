package nirvana.hall.api.entities

import scalikejdbc._

case class CodeFkqsyxqyy(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeFkqsyxqyy.autoSession): CodeFkqsyxqyy = CodeFkqsyxqyy.save(this)(session)

  def destroy()(implicit session: DBSession = CodeFkqsyxqyy.autoSession): Unit = CodeFkqsyxqyy.destroy(this)(session)

}


object CodeFkqsyxqyy extends SQLSyntaxSupport[CodeFkqsyxqyy] {

  override val tableName = "CODE_FKQSYXQYY"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cf: SyntaxProvider[CodeFkqsyxqyy])(rs: WrappedResultSet): CodeFkqsyxqyy = apply(cf.resultName)(rs)
  def apply(cf: ResultName[CodeFkqsyxqyy])(rs: WrappedResultSet): CodeFkqsyxqyy = new CodeFkqsyxqyy(
    code = rs.get(cf.code),
    name = rs.get(cf.name),
    deleteFlag = rs.get(cf.deleteFlag),
    ord = rs.get(cf.ord),
    remark = rs.get(cf.remark)
  )

  val cf = CodeFkqsyxqyy.syntax("cf")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeFkqsyxqyy] = {
    withSQL {
      select.from(CodeFkqsyxqyy as cf).where.eq(cf.code, code).and.eq(cf.name, name).and.eq(cf.deleteFlag, deleteFlag).and.eq(cf.ord, ord).and.eq(cf.remark, remark)
    }.map(CodeFkqsyxqyy(cf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeFkqsyxqyy] = {
    withSQL(select.from(CodeFkqsyxqyy as cf)).map(CodeFkqsyxqyy(cf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeFkqsyxqyy as cf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeFkqsyxqyy] = {
    withSQL {
      select.from(CodeFkqsyxqyy as cf).where.append(where)
    }.map(CodeFkqsyxqyy(cf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeFkqsyxqyy] = {
    withSQL {
      select.from(CodeFkqsyxqyy as cf).where.append(where)
    }.map(CodeFkqsyxqyy(cf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeFkqsyxqyy as cf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeFkqsyxqyy = {
    withSQL {
      insert.into(CodeFkqsyxqyy).columns(
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

    CodeFkqsyxqyy(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeFkqsyxqyy)(implicit session: DBSession = autoSession): CodeFkqsyxqyy = {
    withSQL {
      update(CodeFkqsyxqyy).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeFkqsyxqyy)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeFkqsyxqyy).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
