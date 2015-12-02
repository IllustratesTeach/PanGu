package nirvana.hall.api.entities

import scalikejdbc._

case class CodeXb(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeXb.autoSession): CodeXb = CodeXb.save(this)(session)

  def destroy()(implicit session: DBSession = CodeXb.autoSession): Unit = CodeXb.destroy(this)(session)

}


object CodeXb extends SQLSyntaxSupport[CodeXb] {

  override val tableName = "CODE_XB"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cx: SyntaxProvider[CodeXb])(rs: WrappedResultSet): CodeXb = apply(cx.resultName)(rs)
  def apply(cx: ResultName[CodeXb])(rs: WrappedResultSet): CodeXb = new CodeXb(
    code = rs.get(cx.code),
    name = rs.get(cx.name),
    deleteFlag = rs.get(cx.deleteFlag),
    ord = rs.get(cx.ord),
    remark = rs.get(cx.remark)
  )

  val cx = CodeXb.syntax("cx")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String)(implicit session: DBSession = autoSession): Option[CodeXb] = {
    withSQL {
      select.from(CodeXb as cx).where.eq(cx.code, code)
    }.map(CodeXb(cx.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeXb] = {
    withSQL(select.from(CodeXb as cx)).map(CodeXb(cx.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeXb as cx)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeXb] = {
    withSQL {
      select.from(CodeXb as cx).where.append(where)
    }.map(CodeXb(cx.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeXb] = {
    withSQL {
      select.from(CodeXb as cx).where.append(where)
    }.map(CodeXb(cx.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeXb as cx).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeXb = {
    withSQL {
      insert.into(CodeXb).columns(
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

    CodeXb(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeXb)(implicit session: DBSession = autoSession): CodeXb = {
    withSQL {
      update(CodeXb).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeXb)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeXb).where.eq(column.code, entity.code) }.update.apply()
  }

}
