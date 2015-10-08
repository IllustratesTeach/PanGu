package nirvana.hall.api.entities

import scalikejdbc._

case class CodeQjdd(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeQjdd.autoSession): CodeQjdd = CodeQjdd.save(this)(session)

  def destroy()(implicit session: DBSession = CodeQjdd.autoSession): Unit = CodeQjdd.destroy(this)(session)

}


object CodeQjdd extends SQLSyntaxSupport[CodeQjdd] {

  override val tableName = "CODE_QJDD"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cq: SyntaxProvider[CodeQjdd])(rs: WrappedResultSet): CodeQjdd = apply(cq.resultName)(rs)
  def apply(cq: ResultName[CodeQjdd])(rs: WrappedResultSet): CodeQjdd = new CodeQjdd(
    code = rs.get(cq.code),
    name = rs.get(cq.name),
    deleteFlag = rs.get(cq.deleteFlag),
    ord = rs.get(cq.ord),
    remark = rs.get(cq.remark)
  )

  val cq = CodeQjdd.syntax("cq")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeQjdd] = {
    withSQL {
      select.from(CodeQjdd as cq).where.eq(cq.code, code).and.eq(cq.name, name).and.eq(cq.deleteFlag, deleteFlag).and.eq(cq.ord, ord).and.eq(cq.remark, remark)
    }.map(CodeQjdd(cq.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeQjdd] = {
    withSQL(select.from(CodeQjdd as cq)).map(CodeQjdd(cq.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeQjdd as cq)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeQjdd] = {
    withSQL {
      select.from(CodeQjdd as cq).where.append(where)
    }.map(CodeQjdd(cq.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeQjdd] = {
    withSQL {
      select.from(CodeQjdd as cq).where.append(where)
    }.map(CodeQjdd(cq.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeQjdd as cq).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeQjdd = {
    withSQL {
      insert.into(CodeQjdd).columns(
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

    CodeQjdd(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeQjdd)(implicit session: DBSession = autoSession): CodeQjdd = {
    withSQL {
      update(CodeQjdd).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeQjdd)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeQjdd).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
