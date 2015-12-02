package nirvana.hall.api.entities

import scalikejdbc._

case class CodeXcjb(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeXcjb.autoSession): CodeXcjb = CodeXcjb.save(this)(session)

  def destroy()(implicit session: DBSession = CodeXcjb.autoSession): Unit = CodeXcjb.destroy(this)(session)

}


object CodeXcjb extends SQLSyntaxSupport[CodeXcjb] {

  override val tableName = "CODE_XCJB"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cx: SyntaxProvider[CodeXcjb])(rs: WrappedResultSet): CodeXcjb = apply(cx.resultName)(rs)
  def apply(cx: ResultName[CodeXcjb])(rs: WrappedResultSet): CodeXcjb = new CodeXcjb(
    code = rs.get(cx.code),
    name = rs.get(cx.name),
    deleteFlag = rs.get(cx.deleteFlag),
    ord = rs.get(cx.ord),
    remark = rs.get(cx.remark)
  )

  val cx = CodeXcjb.syntax("cx")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String)(implicit session: DBSession = autoSession): Option[CodeXcjb] = {
    withSQL {
      select.from(CodeXcjb as cx).where.eq(cx.code, code)
    }.map(CodeXcjb(cx.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeXcjb] = {
    withSQL(select.from(CodeXcjb as cx)).map(CodeXcjb(cx.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeXcjb as cx)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeXcjb] = {
    withSQL {
      select.from(CodeXcjb as cx).where.append(where)
    }.map(CodeXcjb(cx.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeXcjb] = {
    withSQL {
      select.from(CodeXcjb as cx).where.append(where)
    }.map(CodeXcjb(cx.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeXcjb as cx).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeXcjb = {
    withSQL {
      insert.into(CodeXcjb).columns(
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

    CodeXcjb(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeXcjb)(implicit session: DBSession = autoSession): CodeXcjb = {
    withSQL {
      update(CodeXcjb).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeXcjb)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeXcjb).where.eq(column.code, entity.code) }.update.apply()
  }

}
