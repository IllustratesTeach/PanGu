package nirvana.hall.api.entities

import scalikejdbc._

case class CodeLx(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None,
  path: Option[String] = None) {

  def save()(implicit session: DBSession = CodeLx.autoSession): CodeLx = CodeLx.save(this)(session)

  def destroy()(implicit session: DBSession = CodeLx.autoSession): Unit = CodeLx.destroy(this)(session)

}


object CodeLx extends SQLSyntaxSupport[CodeLx] {

  override val tableName = "CODE_LX"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK", "PATH")

  def apply(cl: SyntaxProvider[CodeLx])(rs: WrappedResultSet): CodeLx = apply(cl.resultName)(rs)
  def apply(cl: ResultName[CodeLx])(rs: WrappedResultSet): CodeLx = new CodeLx(
    code = rs.get(cl.code),
    name = rs.get(cl.name),
    deleteFlag = rs.get(cl.deleteFlag),
    ord = rs.get(cl.ord),
    remark = rs.get(cl.remark),
    path = rs.get(cl.path)
  )

  val cl = CodeLx.syntax("cl")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String], path: Option[String])(implicit session: DBSession = autoSession): Option[CodeLx] = {
    withSQL {
      select.from(CodeLx as cl).where.eq(cl.code, code).and.eq(cl.name, name).and.eq(cl.deleteFlag, deleteFlag).and.eq(cl.ord, ord).and.eq(cl.remark, remark).and.eq(cl.path, path)
    }.map(CodeLx(cl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeLx] = {
    withSQL(select.from(CodeLx as cl)).map(CodeLx(cl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeLx as cl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeLx] = {
    withSQL {
      select.from(CodeLx as cl).where.append(where)
    }.map(CodeLx(cl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeLx] = {
    withSQL {
      select.from(CodeLx as cl).where.append(where)
    }.map(CodeLx(cl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeLx as cl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None,
    path: Option[String] = None)(implicit session: DBSession = autoSession): CodeLx = {
    withSQL {
      insert.into(CodeLx).columns(
        column.code,
        column.name,
        column.deleteFlag,
        column.ord,
        column.remark,
        column.path
      ).values(
        code,
        name,
        deleteFlag,
        ord,
        remark,
        path
      )
    }.update.apply()

    CodeLx(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark,
      path = path)
  }

  def save(entity: CodeLx)(implicit session: DBSession = autoSession): CodeLx = {
    withSQL {
      update(CodeLx).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark,
        column.path -> entity.path
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark).and.eq(column.path, entity.path)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeLx)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeLx).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark).and.eq(column.path, entity.path) }.update.apply()
  }

}
