package nirvana.hall.api.entities

import scalikejdbc._

case class CodeAjlb(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None,
  firstLetter: Option[String] = None) {

  def save()(implicit session: DBSession = CodeAjlb.autoSession): CodeAjlb = CodeAjlb.save(this)(session)

  def destroy()(implicit session: DBSession = CodeAjlb.autoSession): Unit = CodeAjlb.destroy(this)(session)

}


object CodeAjlb extends SQLSyntaxSupport[CodeAjlb] {

  override val tableName = "CODE_AJLB"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK", "FIRST_LETTER")

  def apply(ca: SyntaxProvider[CodeAjlb])(rs: WrappedResultSet): CodeAjlb = apply(ca.resultName)(rs)
  def apply(ca: ResultName[CodeAjlb])(rs: WrappedResultSet): CodeAjlb = new CodeAjlb(
    code = rs.get(ca.code),
    name = rs.get(ca.name),
    deleteFlag = rs.get(ca.deleteFlag),
    ord = rs.get(ca.ord),
    remark = rs.get(ca.remark),
    firstLetter = rs.get(ca.firstLetter)
  )

  val ca = CodeAjlb.syntax("ca")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String], firstLetter: Option[String])(implicit session: DBSession = autoSession): Option[CodeAjlb] = {
    withSQL {
      select.from(CodeAjlb as ca).where.eq(ca.code, code).and.eq(ca.name, name).and.eq(ca.deleteFlag, deleteFlag).and.eq(ca.ord, ord).and.eq(ca.remark, remark).and.eq(ca.firstLetter, firstLetter)
    }.map(CodeAjlb(ca.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeAjlb] = {
    withSQL(select.from(CodeAjlb as ca)).map(CodeAjlb(ca.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeAjlb as ca)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeAjlb] = {
    withSQL {
      select.from(CodeAjlb as ca).where.append(where)
    }.map(CodeAjlb(ca.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeAjlb] = {
    withSQL {
      select.from(CodeAjlb as ca).where.append(where)
    }.map(CodeAjlb(ca.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeAjlb as ca).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None,
    firstLetter: Option[String] = None)(implicit session: DBSession = autoSession): CodeAjlb = {
    withSQL {
      insert.into(CodeAjlb).columns(
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

    CodeAjlb(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark,
      firstLetter = firstLetter)
  }

  def save(entity: CodeAjlb)(implicit session: DBSession = autoSession): CodeAjlb = {
    withSQL {
      update(CodeAjlb).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark,
        column.firstLetter -> entity.firstLetter
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark).and.eq(column.firstLetter, entity.firstLetter)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeAjlb)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeAjlb).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark).and.eq(column.firstLetter, entity.firstLetter) }.update.apply()
  }

}
