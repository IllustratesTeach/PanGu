package nirvana.hall.api.entities

import scalikejdbc._

case class CodeCqylcs(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeCqylcs.autoSession): CodeCqylcs = CodeCqylcs.save(this)(session)

  def destroy()(implicit session: DBSession = CodeCqylcs.autoSession): Unit = CodeCqylcs.destroy(this)(session)

}


object CodeCqylcs extends SQLSyntaxSupport[CodeCqylcs] {

  override val tableName = "CODE_CQYLCS"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cc: SyntaxProvider[CodeCqylcs])(rs: WrappedResultSet): CodeCqylcs = apply(cc.resultName)(rs)
  def apply(cc: ResultName[CodeCqylcs])(rs: WrappedResultSet): CodeCqylcs = new CodeCqylcs(
    code = rs.get(cc.code),
    name = rs.get(cc.name),
    deleteFlag = rs.get(cc.deleteFlag),
    ord = rs.get(cc.ord),
    remark = rs.get(cc.remark)
  )

  val cc = CodeCqylcs.syntax("cc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeCqylcs] = {
    withSQL {
      select.from(CodeCqylcs as cc).where.eq(cc.code, code).and.eq(cc.name, name).and.eq(cc.deleteFlag, deleteFlag).and.eq(cc.ord, ord).and.eq(cc.remark, remark)
    }.map(CodeCqylcs(cc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeCqylcs] = {
    withSQL(select.from(CodeCqylcs as cc)).map(CodeCqylcs(cc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeCqylcs as cc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeCqylcs] = {
    withSQL {
      select.from(CodeCqylcs as cc).where.append(where)
    }.map(CodeCqylcs(cc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeCqylcs] = {
    withSQL {
      select.from(CodeCqylcs as cc).where.append(where)
    }.map(CodeCqylcs(cc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeCqylcs as cc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeCqylcs = {
    withSQL {
      insert.into(CodeCqylcs).columns(
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

    CodeCqylcs(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeCqylcs)(implicit session: DBSession = autoSession): CodeCqylcs = {
    withSQL {
      update(CodeCqylcs).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeCqylcs)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeCqylcs).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
