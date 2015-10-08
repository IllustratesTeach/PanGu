package nirvana.hall.api.entities

import scalikejdbc._

case class CodeJggj(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeJggj.autoSession): CodeJggj = CodeJggj.save(this)(session)

  def destroy()(implicit session: DBSession = CodeJggj.autoSession): Unit = CodeJggj.destroy(this)(session)

}


object CodeJggj extends SQLSyntaxSupport[CodeJggj] {

  override val tableName = "CODE_JGGJ"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cj: SyntaxProvider[CodeJggj])(rs: WrappedResultSet): CodeJggj = apply(cj.resultName)(rs)
  def apply(cj: ResultName[CodeJggj])(rs: WrappedResultSet): CodeJggj = new CodeJggj(
    code = rs.get(cj.code),
    name = rs.get(cj.name),
    deleteFlag = rs.get(cj.deleteFlag),
    ord = rs.get(cj.ord),
    remark = rs.get(cj.remark)
  )

  val cj = CodeJggj.syntax("cj")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeJggj] = {
    withSQL {
      select.from(CodeJggj as cj).where.eq(cj.code, code).and.eq(cj.name, name).and.eq(cj.deleteFlag, deleteFlag).and.eq(cj.ord, ord).and.eq(cj.remark, remark)
    }.map(CodeJggj(cj.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeJggj] = {
    withSQL(select.from(CodeJggj as cj)).map(CodeJggj(cj.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeJggj as cj)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeJggj] = {
    withSQL {
      select.from(CodeJggj as cj).where.append(where)
    }.map(CodeJggj(cj.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeJggj] = {
    withSQL {
      select.from(CodeJggj as cj).where.append(where)
    }.map(CodeJggj(cj.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeJggj as cj).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeJggj = {
    withSQL {
      insert.into(CodeJggj).columns(
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

    CodeJggj(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeJggj)(implicit session: DBSession = autoSession): CodeJggj = {
    withSQL {
      update(CodeJggj).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeJggj)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeJggj).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
