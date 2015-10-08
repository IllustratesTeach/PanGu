package nirvana.hall.api.entities

import scalikejdbc._

case class CodePphqmcqk(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodePphqmcqk.autoSession): CodePphqmcqk = CodePphqmcqk.save(this)(session)

  def destroy()(implicit session: DBSession = CodePphqmcqk.autoSession): Unit = CodePphqmcqk.destroy(this)(session)

}


object CodePphqmcqk extends SQLSyntaxSupport[CodePphqmcqk] {

  override val tableName = "CODE_PPHQMCQK"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cp: SyntaxProvider[CodePphqmcqk])(rs: WrappedResultSet): CodePphqmcqk = apply(cp.resultName)(rs)
  def apply(cp: ResultName[CodePphqmcqk])(rs: WrappedResultSet): CodePphqmcqk = new CodePphqmcqk(
    code = rs.get(cp.code),
    name = rs.get(cp.name),
    deleteFlag = rs.get(cp.deleteFlag),
    ord = rs.get(cp.ord),
    remark = rs.get(cp.remark)
  )

  val cp = CodePphqmcqk.syntax("cp")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodePphqmcqk] = {
    withSQL {
      select.from(CodePphqmcqk as cp).where.eq(cp.code, code).and.eq(cp.name, name).and.eq(cp.deleteFlag, deleteFlag).and.eq(cp.ord, ord).and.eq(cp.remark, remark)
    }.map(CodePphqmcqk(cp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodePphqmcqk] = {
    withSQL(select.from(CodePphqmcqk as cp)).map(CodePphqmcqk(cp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodePphqmcqk as cp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodePphqmcqk] = {
    withSQL {
      select.from(CodePphqmcqk as cp).where.append(where)
    }.map(CodePphqmcqk(cp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodePphqmcqk] = {
    withSQL {
      select.from(CodePphqmcqk as cp).where.append(where)
    }.map(CodePphqmcqk(cp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodePphqmcqk as cp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodePphqmcqk = {
    withSQL {
      insert.into(CodePphqmcqk).columns(
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

    CodePphqmcqk(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodePphqmcqk)(implicit session: DBSession = autoSession): CodePphqmcqk = {
    withSQL {
      update(CodePphqmcqk).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodePphqmcqk)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodePphqmcqk).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
