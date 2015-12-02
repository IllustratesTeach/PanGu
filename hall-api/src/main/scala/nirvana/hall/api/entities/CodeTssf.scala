package nirvana.hall.api.entities

import scalikejdbc._

case class CodeTssf(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeTssf.autoSession): CodeTssf = CodeTssf.save(this)(session)

  def destroy()(implicit session: DBSession = CodeTssf.autoSession): Unit = CodeTssf.destroy(this)(session)

}


object CodeTssf extends SQLSyntaxSupport[CodeTssf] {

  override val tableName = "CODE_TSSF"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(ct: SyntaxProvider[CodeTssf])(rs: WrappedResultSet): CodeTssf = apply(ct.resultName)(rs)
  def apply(ct: ResultName[CodeTssf])(rs: WrappedResultSet): CodeTssf = new CodeTssf(
    code = rs.get(ct.code),
    name = rs.get(ct.name),
    deleteFlag = rs.get(ct.deleteFlag),
    ord = rs.get(ct.ord),
    remark = rs.get(ct.remark)
  )

  val ct = CodeTssf.syntax("ct")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String)(implicit session: DBSession = autoSession): Option[CodeTssf] = {
    withSQL {
      select.from(CodeTssf as ct).where.eq(ct.code, code)
    }.map(CodeTssf(ct.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeTssf] = {
    withSQL(select.from(CodeTssf as ct)).map(CodeTssf(ct.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeTssf as ct)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeTssf] = {
    withSQL {
      select.from(CodeTssf as ct).where.append(where)
    }.map(CodeTssf(ct.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeTssf] = {
    withSQL {
      select.from(CodeTssf as ct).where.append(where)
    }.map(CodeTssf(ct.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeTssf as ct).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeTssf = {
    withSQL {
      insert.into(CodeTssf).columns(
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

    CodeTssf(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeTssf)(implicit session: DBSession = autoSession): CodeTssf = {
    withSQL {
      update(CodeTssf).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeTssf)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeTssf).where.eq(column.code, entity.code) }.update.apply()
  }

}
