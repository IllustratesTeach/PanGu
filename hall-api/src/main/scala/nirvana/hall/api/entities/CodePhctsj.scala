package nirvana.hall.api.entities

import scalikejdbc._

case class CodePhctsj(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodePhctsj.autoSession): CodePhctsj = CodePhctsj.save(this)(session)

  def destroy()(implicit session: DBSession = CodePhctsj.autoSession): Unit = CodePhctsj.destroy(this)(session)

}


object CodePhctsj extends SQLSyntaxSupport[CodePhctsj] {

  override val tableName = "CODE_PHCTSJ"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cp: SyntaxProvider[CodePhctsj])(rs: WrappedResultSet): CodePhctsj = apply(cp.resultName)(rs)
  def apply(cp: ResultName[CodePhctsj])(rs: WrappedResultSet): CodePhctsj = new CodePhctsj(
    code = rs.get(cp.code),
    name = rs.get(cp.name),
    deleteFlag = rs.get(cp.deleteFlag),
    ord = rs.get(cp.ord),
    remark = rs.get(cp.remark)
  )

  val cp = CodePhctsj.syntax("cp")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodePhctsj] = {
    withSQL {
      select.from(CodePhctsj as cp).where.eq(cp.code, code).and.eq(cp.name, name).and.eq(cp.deleteFlag, deleteFlag).and.eq(cp.ord, ord).and.eq(cp.remark, remark)
    }.map(CodePhctsj(cp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodePhctsj] = {
    withSQL(select.from(CodePhctsj as cp)).map(CodePhctsj(cp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodePhctsj as cp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodePhctsj] = {
    withSQL {
      select.from(CodePhctsj as cp).where.append(where)
    }.map(CodePhctsj(cp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodePhctsj] = {
    withSQL {
      select.from(CodePhctsj as cp).where.append(where)
    }.map(CodePhctsj(cp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodePhctsj as cp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodePhctsj = {
    withSQL {
      insert.into(CodePhctsj).columns(
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

    CodePhctsj(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodePhctsj)(implicit session: DBSession = autoSession): CodePhctsj = {
    withSQL {
      update(CodePhctsj).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodePhctsj)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodePhctsj).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
