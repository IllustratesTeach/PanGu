package nirvana.hall.api.entities

import scalikejdbc._

case class CodeAjxz(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None,
  firstLetter: Option[String] = None) {

  def save()(implicit session: DBSession = CodeAjxz.autoSession): CodeAjxz = CodeAjxz.save(this)(session)

  def destroy()(implicit session: DBSession = CodeAjxz.autoSession): Unit = CodeAjxz.destroy(this)(session)

}


object CodeAjxz extends SQLSyntaxSupport[CodeAjxz] {

  override val tableName = "CODE_AJXZ"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK", "FIRST_LETTER")

  def apply(ca: SyntaxProvider[CodeAjxz])(rs: WrappedResultSet): CodeAjxz = apply(ca.resultName)(rs)
  def apply(ca: ResultName[CodeAjxz])(rs: WrappedResultSet): CodeAjxz = new CodeAjxz(
    code = rs.get(ca.code),
    name = rs.get(ca.name),
    deleteFlag = rs.get(ca.deleteFlag),
    ord = rs.get(ca.ord),
    remark = rs.get(ca.remark),
    firstLetter = rs.get(ca.firstLetter)
  )

  val ca = CodeAjxz.syntax("ca")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String)(implicit session: DBSession = autoSession): Option[CodeAjxz] = {
    withSQL {
      select.from(CodeAjxz as ca).where.eq(ca.code, code)
    }.map(CodeAjxz(ca.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeAjxz] = {
    withSQL(select.from(CodeAjxz as ca)).map(CodeAjxz(ca.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeAjxz as ca)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeAjxz] = {
    withSQL {
      select.from(CodeAjxz as ca).where.append(where)
    }.map(CodeAjxz(ca.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeAjxz] = {
    withSQL {
      select.from(CodeAjxz as ca).where.append(where)
    }.map(CodeAjxz(ca.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeAjxz as ca).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None,
    firstLetter: Option[String] = None)(implicit session: DBSession = autoSession): CodeAjxz = {
    withSQL {
      insert.into(CodeAjxz).columns(
        column.code,
        column.name,
        column.deleteFlag,
        column.ord,
        column.remark,
        column.firstLetter
      ).values(
        code,
        name,
        deleteFlag,
        ord,
        remark,
        firstLetter
      )
    }.update.apply()

    CodeAjxz(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark,
      firstLetter = firstLetter)
  }

  def save(entity: CodeAjxz)(implicit session: DBSession = autoSession): CodeAjxz = {
    withSQL {
      update(CodeAjxz).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark,
        column.firstLetter -> entity.firstLetter
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeAjxz)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeAjxz).where.eq(column.code, entity.code) }.update.apply()
  }

}
