package nirvana.hall.api.entities

import scalikejdbc._

case class CodeFmtl(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeFmtl.autoSession): CodeFmtl = CodeFmtl.save(this)(session)

  def destroy()(implicit session: DBSession = CodeFmtl.autoSession): Unit = CodeFmtl.destroy(this)(session)

}


object CodeFmtl extends SQLSyntaxSupport[CodeFmtl] {

  override val tableName = "CODE_FMTL"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cf: SyntaxProvider[CodeFmtl])(rs: WrappedResultSet): CodeFmtl = apply(cf.resultName)(rs)
  def apply(cf: ResultName[CodeFmtl])(rs: WrappedResultSet): CodeFmtl = new CodeFmtl(
    code = rs.get(cf.code),
    name = rs.get(cf.name),
    deleteFlag = rs.get(cf.deleteFlag),
    ord = rs.get(cf.ord),
    remark = rs.get(cf.remark)
  )

  val cf = CodeFmtl.syntax("cf")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeFmtl] = {
    withSQL {
      select.from(CodeFmtl as cf).where.eq(cf.code, code).and.eq(cf.name, name).and.eq(cf.deleteFlag, deleteFlag).and.eq(cf.ord, ord).and.eq(cf.remark, remark)
    }.map(CodeFmtl(cf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeFmtl] = {
    withSQL(select.from(CodeFmtl as cf)).map(CodeFmtl(cf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeFmtl as cf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeFmtl] = {
    withSQL {
      select.from(CodeFmtl as cf).where.append(where)
    }.map(CodeFmtl(cf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeFmtl] = {
    withSQL {
      select.from(CodeFmtl as cf).where.append(where)
    }.map(CodeFmtl(cf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeFmtl as cf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeFmtl = {
    withSQL {
      insert.into(CodeFmtl).columns(
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

    CodeFmtl(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeFmtl)(implicit session: DBSession = autoSession): CodeFmtl = {
    withSQL {
      update(CodeFmtl).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeFmtl)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeFmtl).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
