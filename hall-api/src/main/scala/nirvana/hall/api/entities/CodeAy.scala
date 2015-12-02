package nirvana.hall.api.entities

import scalikejdbc._

case class CodeAy(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None,
  firstLetter: Option[String] = None) {

  def save()(implicit session: DBSession = CodeAy.autoSession): CodeAy = CodeAy.save(this)(session)

  def destroy()(implicit session: DBSession = CodeAy.autoSession): Unit = CodeAy.destroy(this)(session)

}


object CodeAy extends SQLSyntaxSupport[CodeAy] {

  override val tableName = "CODE_AY"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK", "FIRST_LETTER")

  def apply(ca: SyntaxProvider[CodeAy])(rs: WrappedResultSet): CodeAy = apply(ca.resultName)(rs)
  def apply(ca: ResultName[CodeAy])(rs: WrappedResultSet): CodeAy = new CodeAy(
    code = rs.get(ca.code),
    name = rs.get(ca.name),
    deleteFlag = rs.get(ca.deleteFlag),
    ord = rs.get(ca.ord),
    remark = rs.get(ca.remark),
    firstLetter = rs.get(ca.firstLetter)
  )

  val ca = CodeAy.syntax("ca")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String)(implicit session: DBSession = autoSession): Option[CodeAy] = {
    withSQL {
      select.from(CodeAy as ca).where.eq(ca.code, code)
    }.map(CodeAy(ca.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeAy] = {
    withSQL(select.from(CodeAy as ca)).map(CodeAy(ca.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeAy as ca)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeAy] = {
    withSQL {
      select.from(CodeAy as ca).where.append(where)
    }.map(CodeAy(ca.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeAy] = {
    withSQL {
      select.from(CodeAy as ca).where.append(where)
    }.map(CodeAy(ca.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeAy as ca).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None,
    firstLetter: Option[String] = None)(implicit session: DBSession = autoSession): CodeAy = {
    withSQL {
      insert.into(CodeAy).columns(
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

    CodeAy(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark,
      firstLetter = firstLetter)
  }

  def save(entity: CodeAy)(implicit session: DBSession = autoSession): CodeAy = {
    withSQL {
      update(CodeAy).set(
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

  def destroy(entity: CodeAy)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeAy).where.eq(column.code, entity.code) }.update.apply()
  }

}
