package nirvana.hall.api.entities

import scalikejdbc._

case class CodeAjlx(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeAjlx.autoSession): CodeAjlx = CodeAjlx.save(this)(session)

  def destroy()(implicit session: DBSession = CodeAjlx.autoSession): Unit = CodeAjlx.destroy(this)(session)

}


object CodeAjlx extends SQLSyntaxSupport[CodeAjlx] {

  override val tableName = "CODE_AJLX"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(ca: SyntaxProvider[CodeAjlx])(rs: WrappedResultSet): CodeAjlx = apply(ca.resultName)(rs)
  def apply(ca: ResultName[CodeAjlx])(rs: WrappedResultSet): CodeAjlx = new CodeAjlx(
    code = rs.get(ca.code),
    name = rs.get(ca.name),
    deleteFlag = rs.get(ca.deleteFlag),
    ord = rs.get(ca.ord),
    remark = rs.get(ca.remark)
  )

  val ca = CodeAjlx.syntax("ca")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String)(implicit session: DBSession = autoSession): Option[CodeAjlx] = {
    withSQL {
      select.from(CodeAjlx as ca).where.eq(ca.code, code)
    }.map(CodeAjlx(ca.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeAjlx] = {
    withSQL(select.from(CodeAjlx as ca)).map(CodeAjlx(ca.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeAjlx as ca)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeAjlx] = {
    withSQL {
      select.from(CodeAjlx as ca).where.append(where)
    }.map(CodeAjlx(ca.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeAjlx] = {
    withSQL {
      select.from(CodeAjlx as ca).where.append(where)
    }.map(CodeAjlx(ca.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeAjlx as ca).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeAjlx = {
    withSQL {
      insert.into(CodeAjlx).columns(
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

    CodeAjlx(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeAjlx)(implicit session: DBSession = autoSession): CodeAjlx = {
    withSQL {
      update(CodeAjlx).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeAjlx)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeAjlx).where.eq(column.code, entity.code) }.update.apply()
  }

}
