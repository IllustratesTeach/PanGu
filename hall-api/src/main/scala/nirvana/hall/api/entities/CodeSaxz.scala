package nirvana.hall.api.entities

import scalikejdbc._

case class CodeSaxz(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeSaxz.autoSession): CodeSaxz = CodeSaxz.save(this)(session)

  def destroy()(implicit session: DBSession = CodeSaxz.autoSession): Unit = CodeSaxz.destroy(this)(session)

}


object CodeSaxz extends SQLSyntaxSupport[CodeSaxz] {

  override val tableName = "CODE_SAXZ"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cs: SyntaxProvider[CodeSaxz])(rs: WrappedResultSet): CodeSaxz = apply(cs.resultName)(rs)
  def apply(cs: ResultName[CodeSaxz])(rs: WrappedResultSet): CodeSaxz = new CodeSaxz(
    code = rs.get(cs.code),
    name = rs.get(cs.name),
    deleteFlag = rs.get(cs.deleteFlag),
    ord = rs.get(cs.ord),
    remark = rs.get(cs.remark)
  )

  val cs = CodeSaxz.syntax("cs")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeSaxz] = {
    withSQL {
      select.from(CodeSaxz as cs).where.eq(cs.code, code).and.eq(cs.name, name).and.eq(cs.deleteFlag, deleteFlag).and.eq(cs.ord, ord).and.eq(cs.remark, remark)
    }.map(CodeSaxz(cs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeSaxz] = {
    withSQL(select.from(CodeSaxz as cs)).map(CodeSaxz(cs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeSaxz as cs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeSaxz] = {
    withSQL {
      select.from(CodeSaxz as cs).where.append(where)
    }.map(CodeSaxz(cs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeSaxz] = {
    withSQL {
      select.from(CodeSaxz as cs).where.append(where)
    }.map(CodeSaxz(cs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeSaxz as cs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeSaxz = {
    withSQL {
      insert.into(CodeSaxz).columns(
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

    CodeSaxz(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeSaxz)(implicit session: DBSession = autoSession): CodeSaxz = {
    withSQL {
      update(CodeSaxz).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeSaxz)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeSaxz).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
