package nirvana.hall.api.entities

import scalikejdbc._

case class CodeShyy(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeShyy.autoSession): CodeShyy = CodeShyy.save(this)(session)

  def destroy()(implicit session: DBSession = CodeShyy.autoSession): Unit = CodeShyy.destroy(this)(session)

}


object CodeShyy extends SQLSyntaxSupport[CodeShyy] {

  override val tableName = "CODE_SHYY"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cs: SyntaxProvider[CodeShyy])(rs: WrappedResultSet): CodeShyy = apply(cs.resultName)(rs)
  def apply(cs: ResultName[CodeShyy])(rs: WrappedResultSet): CodeShyy = new CodeShyy(
    code = rs.get(cs.code),
    name = rs.get(cs.name),
    deleteFlag = rs.get(cs.deleteFlag),
    ord = rs.get(cs.ord),
    remark = rs.get(cs.remark)
  )

  val cs = CodeShyy.syntax("cs")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeShyy] = {
    withSQL {
      select.from(CodeShyy as cs).where.eq(cs.code, code).and.eq(cs.name, name).and.eq(cs.deleteFlag, deleteFlag).and.eq(cs.ord, ord).and.eq(cs.remark, remark)
    }.map(CodeShyy(cs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeShyy] = {
    withSQL(select.from(CodeShyy as cs)).map(CodeShyy(cs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeShyy as cs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeShyy] = {
    withSQL {
      select.from(CodeShyy as cs).where.append(where)
    }.map(CodeShyy(cs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeShyy] = {
    withSQL {
      select.from(CodeShyy as cs).where.append(where)
    }.map(CodeShyy(cs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeShyy as cs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeShyy = {
    withSQL {
      insert.into(CodeShyy).columns(
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

    CodeShyy(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeShyy)(implicit session: DBSession = autoSession): CodeShyy = {
    withSQL {
      update(CodeShyy).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeShyy)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeShyy).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
