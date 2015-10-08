package nirvana.hall.api.entities

import scalikejdbc._

case class CodeLgrzdjqk(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeLgrzdjqk.autoSession): CodeLgrzdjqk = CodeLgrzdjqk.save(this)(session)

  def destroy()(implicit session: DBSession = CodeLgrzdjqk.autoSession): Unit = CodeLgrzdjqk.destroy(this)(session)

}


object CodeLgrzdjqk extends SQLSyntaxSupport[CodeLgrzdjqk] {

  override val tableName = "CODE_LGRZDJQK"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cl: SyntaxProvider[CodeLgrzdjqk])(rs: WrappedResultSet): CodeLgrzdjqk = apply(cl.resultName)(rs)
  def apply(cl: ResultName[CodeLgrzdjqk])(rs: WrappedResultSet): CodeLgrzdjqk = new CodeLgrzdjqk(
    code = rs.get(cl.code),
    name = rs.get(cl.name),
    deleteFlag = rs.get(cl.deleteFlag),
    ord = rs.get(cl.ord),
    remark = rs.get(cl.remark)
  )

  val cl = CodeLgrzdjqk.syntax("cl")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeLgrzdjqk] = {
    withSQL {
      select.from(CodeLgrzdjqk as cl).where.eq(cl.code, code).and.eq(cl.name, name).and.eq(cl.deleteFlag, deleteFlag).and.eq(cl.ord, ord).and.eq(cl.remark, remark)
    }.map(CodeLgrzdjqk(cl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeLgrzdjqk] = {
    withSQL(select.from(CodeLgrzdjqk as cl)).map(CodeLgrzdjqk(cl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeLgrzdjqk as cl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeLgrzdjqk] = {
    withSQL {
      select.from(CodeLgrzdjqk as cl).where.append(where)
    }.map(CodeLgrzdjqk(cl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeLgrzdjqk] = {
    withSQL {
      select.from(CodeLgrzdjqk as cl).where.append(where)
    }.map(CodeLgrzdjqk(cl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeLgrzdjqk as cl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeLgrzdjqk = {
    withSQL {
      insert.into(CodeLgrzdjqk).columns(
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

    CodeLgrzdjqk(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeLgrzdjqk)(implicit session: DBSession = autoSession): CodeLgrzdjqk = {
    withSQL {
      update(CodeLgrzdjqk).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeLgrzdjqk)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeLgrzdjqk).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
