package nirvana.hall.api.entities

import scalikejdbc._

case class CodeWbdjqk(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeWbdjqk.autoSession): CodeWbdjqk = CodeWbdjqk.save(this)(session)

  def destroy()(implicit session: DBSession = CodeWbdjqk.autoSession): Unit = CodeWbdjqk.destroy(this)(session)

}


object CodeWbdjqk extends SQLSyntaxSupport[CodeWbdjqk] {

  override val tableName = "CODE_WBDJQK"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cw: SyntaxProvider[CodeWbdjqk])(rs: WrappedResultSet): CodeWbdjqk = apply(cw.resultName)(rs)
  def apply(cw: ResultName[CodeWbdjqk])(rs: WrappedResultSet): CodeWbdjqk = new CodeWbdjqk(
    code = rs.get(cw.code),
    name = rs.get(cw.name),
    deleteFlag = rs.get(cw.deleteFlag),
    ord = rs.get(cw.ord),
    remark = rs.get(cw.remark)
  )

  val cw = CodeWbdjqk.syntax("cw")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeWbdjqk] = {
    withSQL {
      select.from(CodeWbdjqk as cw).where.eq(cw.code, code).and.eq(cw.name, name).and.eq(cw.deleteFlag, deleteFlag).and.eq(cw.ord, ord).and.eq(cw.remark, remark)
    }.map(CodeWbdjqk(cw.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeWbdjqk] = {
    withSQL(select.from(CodeWbdjqk as cw)).map(CodeWbdjqk(cw.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeWbdjqk as cw)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeWbdjqk] = {
    withSQL {
      select.from(CodeWbdjqk as cw).where.append(where)
    }.map(CodeWbdjqk(cw.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeWbdjqk] = {
    withSQL {
      select.from(CodeWbdjqk as cw).where.append(where)
    }.map(CodeWbdjqk(cw.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeWbdjqk as cw).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeWbdjqk = {
    withSQL {
      insert.into(CodeWbdjqk).columns(
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

    CodeWbdjqk(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeWbdjqk)(implicit session: DBSession = autoSession): CodeWbdjqk = {
    withSQL {
      update(CodeWbdjqk).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeWbdjqk)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeWbdjqk).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
