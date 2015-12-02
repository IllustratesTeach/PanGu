package nirvana.hall.api.entities

import scalikejdbc._

case class CodeAjly(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeAjly.autoSession): CodeAjly = CodeAjly.save(this)(session)

  def destroy()(implicit session: DBSession = CodeAjly.autoSession): Unit = CodeAjly.destroy(this)(session)

}


object CodeAjly extends SQLSyntaxSupport[CodeAjly] {

  override val tableName = "CODE_AJLY"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(ca: SyntaxProvider[CodeAjly])(rs: WrappedResultSet): CodeAjly = apply(ca.resultName)(rs)
  def apply(ca: ResultName[CodeAjly])(rs: WrappedResultSet): CodeAjly = new CodeAjly(
    code = rs.get(ca.code),
    name = rs.get(ca.name),
    deleteFlag = rs.get(ca.deleteFlag),
    ord = rs.get(ca.ord),
    remark = rs.get(ca.remark)
  )

  val ca = CodeAjly.syntax("ca")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String)(implicit session: DBSession = autoSession): Option[CodeAjly] = {
    withSQL {
      select.from(CodeAjly as ca).where.eq(ca.code, code)
    }.map(CodeAjly(ca.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeAjly] = {
    withSQL(select.from(CodeAjly as ca)).map(CodeAjly(ca.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeAjly as ca)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeAjly] = {
    withSQL {
      select.from(CodeAjly as ca).where.append(where)
    }.map(CodeAjly(ca.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeAjly] = {
    withSQL {
      select.from(CodeAjly as ca).where.append(where)
    }.map(CodeAjly(ca.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeAjly as ca).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeAjly = {
    withSQL {
      insert.into(CodeAjly).columns(
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

    CodeAjly(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeAjly)(implicit session: DBSession = autoSession): CodeAjly = {
    withSQL {
      update(CodeAjly).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeAjly)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeAjly).where.eq(column.code, entity.code) }.update.apply()
  }

}
