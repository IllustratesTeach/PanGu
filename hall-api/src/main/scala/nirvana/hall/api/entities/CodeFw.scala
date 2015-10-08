package nirvana.hall.api.entities

import scalikejdbc._

case class CodeFw(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeFw.autoSession): CodeFw = CodeFw.save(this)(session)

  def destroy()(implicit session: DBSession = CodeFw.autoSession): Unit = CodeFw.destroy(this)(session)

}


object CodeFw extends SQLSyntaxSupport[CodeFw] {

  override val tableName = "CODE_FW"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cf: SyntaxProvider[CodeFw])(rs: WrappedResultSet): CodeFw = apply(cf.resultName)(rs)
  def apply(cf: ResultName[CodeFw])(rs: WrappedResultSet): CodeFw = new CodeFw(
    code = rs.get(cf.code),
    name = rs.get(cf.name),
    deleteFlag = rs.get(cf.deleteFlag),
    ord = rs.get(cf.ord),
    remark = rs.get(cf.remark)
  )

  val cf = CodeFw.syntax("cf")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeFw] = {
    withSQL {
      select.from(CodeFw as cf).where.eq(cf.code, code).and.eq(cf.name, name).and.eq(cf.deleteFlag, deleteFlag).and.eq(cf.ord, ord).and.eq(cf.remark, remark)
    }.map(CodeFw(cf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeFw] = {
    withSQL(select.from(CodeFw as cf)).map(CodeFw(cf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeFw as cf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeFw] = {
    withSQL {
      select.from(CodeFw as cf).where.append(where)
    }.map(CodeFw(cf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeFw] = {
    withSQL {
      select.from(CodeFw as cf).where.append(where)
    }.map(CodeFw(cf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeFw as cf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeFw = {
    withSQL {
      insert.into(CodeFw).columns(
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

    CodeFw(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeFw)(implicit session: DBSession = autoSession): CodeFw = {
    withSQL {
      update(CodeFw).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeFw)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeFw).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
