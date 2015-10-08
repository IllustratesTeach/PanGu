package nirvana.hall.api.entities

import scalikejdbc._

case class CodeQjhtlxcff(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeQjhtlxcff.autoSession): CodeQjhtlxcff = CodeQjhtlxcff.save(this)(session)

  def destroy()(implicit session: DBSession = CodeQjhtlxcff.autoSession): Unit = CodeQjhtlxcff.destroy(this)(session)

}


object CodeQjhtlxcff extends SQLSyntaxSupport[CodeQjhtlxcff] {

  override val tableName = "CODE_QJHTLXCFF"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cq: SyntaxProvider[CodeQjhtlxcff])(rs: WrappedResultSet): CodeQjhtlxcff = apply(cq.resultName)(rs)
  def apply(cq: ResultName[CodeQjhtlxcff])(rs: WrappedResultSet): CodeQjhtlxcff = new CodeQjhtlxcff(
    code = rs.get(cq.code),
    name = rs.get(cq.name),
    deleteFlag = rs.get(cq.deleteFlag),
    ord = rs.get(cq.ord),
    remark = rs.get(cq.remark)
  )

  val cq = CodeQjhtlxcff.syntax("cq")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeQjhtlxcff] = {
    withSQL {
      select.from(CodeQjhtlxcff as cq).where.eq(cq.code, code).and.eq(cq.name, name).and.eq(cq.deleteFlag, deleteFlag).and.eq(cq.ord, ord).and.eq(cq.remark, remark)
    }.map(CodeQjhtlxcff(cq.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeQjhtlxcff] = {
    withSQL(select.from(CodeQjhtlxcff as cq)).map(CodeQjhtlxcff(cq.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeQjhtlxcff as cq)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeQjhtlxcff] = {
    withSQL {
      select.from(CodeQjhtlxcff as cq).where.append(where)
    }.map(CodeQjhtlxcff(cq.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeQjhtlxcff] = {
    withSQL {
      select.from(CodeQjhtlxcff as cq).where.append(where)
    }.map(CodeQjhtlxcff(cq.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeQjhtlxcff as cq).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeQjhtlxcff = {
    withSQL {
      insert.into(CodeQjhtlxcff).columns(
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

    CodeQjhtlxcff(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeQjhtlxcff)(implicit session: DBSession = autoSession): CodeQjhtlxcff = {
    withSQL {
      update(CodeQjhtlxcff).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeQjhtlxcff)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeQjhtlxcff).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
