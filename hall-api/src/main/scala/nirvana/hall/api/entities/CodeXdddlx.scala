package nirvana.hall.api.entities

import scalikejdbc._

case class CodeXdddlx(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeXdddlx.autoSession): CodeXdddlx = CodeXdddlx.save(this)(session)

  def destroy()(implicit session: DBSession = CodeXdddlx.autoSession): Unit = CodeXdddlx.destroy(this)(session)

}


object CodeXdddlx extends SQLSyntaxSupport[CodeXdddlx] {

  override val tableName = "CODE_XDDDLX"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cx: SyntaxProvider[CodeXdddlx])(rs: WrappedResultSet): CodeXdddlx = apply(cx.resultName)(rs)
  def apply(cx: ResultName[CodeXdddlx])(rs: WrappedResultSet): CodeXdddlx = new CodeXdddlx(
    code = rs.get(cx.code),
    name = rs.get(cx.name),
    deleteFlag = rs.get(cx.deleteFlag),
    ord = rs.get(cx.ord),
    remark = rs.get(cx.remark)
  )

  val cx = CodeXdddlx.syntax("cx")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeXdddlx] = {
    withSQL {
      select.from(CodeXdddlx as cx).where.eq(cx.code, code).and.eq(cx.name, name).and.eq(cx.deleteFlag, deleteFlag).and.eq(cx.ord, ord).and.eq(cx.remark, remark)
    }.map(CodeXdddlx(cx.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeXdddlx] = {
    withSQL(select.from(CodeXdddlx as cx)).map(CodeXdddlx(cx.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeXdddlx as cx)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeXdddlx] = {
    withSQL {
      select.from(CodeXdddlx as cx).where.append(where)
    }.map(CodeXdddlx(cx.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeXdddlx] = {
    withSQL {
      select.from(CodeXdddlx as cx).where.append(where)
    }.map(CodeXdddlx(cx.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeXdddlx as cx).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeXdddlx = {
    withSQL {
      insert.into(CodeXdddlx).columns(
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

    CodeXdddlx(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeXdddlx)(implicit session: DBSession = autoSession): CodeXdddlx = {
    withSQL {
      update(CodeXdddlx).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeXdddlx)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeXdddlx).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
