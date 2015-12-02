package nirvana.hall.api.entities

import scalikejdbc._

case class CodeGj(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeGj.autoSession): CodeGj = CodeGj.save(this)(session)

  def destroy()(implicit session: DBSession = CodeGj.autoSession): Unit = CodeGj.destroy(this)(session)

}


object CodeGj extends SQLSyntaxSupport[CodeGj] {

  override val tableName = "CODE_GJ"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cg: SyntaxProvider[CodeGj])(rs: WrappedResultSet): CodeGj = apply(cg.resultName)(rs)
  def apply(cg: ResultName[CodeGj])(rs: WrappedResultSet): CodeGj = new CodeGj(
    code = rs.get(cg.code),
    name = rs.get(cg.name),
    deleteFlag = rs.get(cg.deleteFlag),
    ord = rs.get(cg.ord),
    remark = rs.get(cg.remark)
  )

  val cg = CodeGj.syntax("cg")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String)(implicit session: DBSession = autoSession): Option[CodeGj] = {
    withSQL {
      select.from(CodeGj as cg).where.eq(cg.code, code)
    }.map(CodeGj(cg.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeGj] = {
    withSQL(select.from(CodeGj as cg)).map(CodeGj(cg.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeGj as cg)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeGj] = {
    withSQL {
      select.from(CodeGj as cg).where.append(where)
    }.map(CodeGj(cg.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeGj] = {
    withSQL {
      select.from(CodeGj as cg).where.append(where)
    }.map(CodeGj(cg.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeGj as cg).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeGj = {
    withSQL {
      insert.into(CodeGj).columns(
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

    CodeGj(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeGj)(implicit session: DBSession = autoSession): CodeGj = {
    withSQL {
      update(CodeGj).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeGj)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeGj).where.eq(column.code, entity.code) }.update.apply()
  }

}
