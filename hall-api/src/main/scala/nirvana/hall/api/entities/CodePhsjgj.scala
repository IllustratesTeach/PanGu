package nirvana.hall.api.entities

import scalikejdbc._

case class CodePhsjgj(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodePhsjgj.autoSession): CodePhsjgj = CodePhsjgj.save(this)(session)

  def destroy()(implicit session: DBSession = CodePhsjgj.autoSession): Unit = CodePhsjgj.destroy(this)(session)

}


object CodePhsjgj extends SQLSyntaxSupport[CodePhsjgj] {

  override val tableName = "CODE_PHSJGJ"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cp: SyntaxProvider[CodePhsjgj])(rs: WrappedResultSet): CodePhsjgj = apply(cp.resultName)(rs)
  def apply(cp: ResultName[CodePhsjgj])(rs: WrappedResultSet): CodePhsjgj = new CodePhsjgj(
    code = rs.get(cp.code),
    name = rs.get(cp.name),
    deleteFlag = rs.get(cp.deleteFlag),
    ord = rs.get(cp.ord),
    remark = rs.get(cp.remark)
  )

  val cp = CodePhsjgj.syntax("cp")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodePhsjgj] = {
    withSQL {
      select.from(CodePhsjgj as cp).where.eq(cp.code, code).and.eq(cp.name, name).and.eq(cp.deleteFlag, deleteFlag).and.eq(cp.ord, ord).and.eq(cp.remark, remark)
    }.map(CodePhsjgj(cp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodePhsjgj] = {
    withSQL(select.from(CodePhsjgj as cp)).map(CodePhsjgj(cp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodePhsjgj as cp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodePhsjgj] = {
    withSQL {
      select.from(CodePhsjgj as cp).where.append(where)
    }.map(CodePhsjgj(cp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodePhsjgj] = {
    withSQL {
      select.from(CodePhsjgj as cp).where.append(where)
    }.map(CodePhsjgj(cp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodePhsjgj as cp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodePhsjgj = {
    withSQL {
      insert.into(CodePhsjgj).columns(
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

    CodePhsjgj(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodePhsjgj)(implicit session: DBSession = autoSession): CodePhsjgj = {
    withSQL {
      update(CodePhsjgj).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodePhsjgj)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodePhsjgj).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
