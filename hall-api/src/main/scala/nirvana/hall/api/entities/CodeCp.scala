package nirvana.hall.api.entities

import scalikejdbc._

case class CodeCp(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeCp.autoSession): CodeCp = CodeCp.save(this)(session)

  def destroy()(implicit session: DBSession = CodeCp.autoSession): Unit = CodeCp.destroy(this)(session)

}


object CodeCp extends SQLSyntaxSupport[CodeCp] {

  override val tableName = "CODE_CP"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cc: SyntaxProvider[CodeCp])(rs: WrappedResultSet): CodeCp = apply(cc.resultName)(rs)
  def apply(cc: ResultName[CodeCp])(rs: WrappedResultSet): CodeCp = new CodeCp(
    code = rs.get(cc.code),
    name = rs.get(cc.name),
    deleteFlag = rs.get(cc.deleteFlag),
    ord = rs.get(cc.ord),
    remark = rs.get(cc.remark)
  )

  val cc = CodeCp.syntax("cc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeCp] = {
    withSQL {
      select.from(CodeCp as cc).where.eq(cc.code, code).and.eq(cc.name, name).and.eq(cc.deleteFlag, deleteFlag).and.eq(cc.ord, ord).and.eq(cc.remark, remark)
    }.map(CodeCp(cc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeCp] = {
    withSQL(select.from(CodeCp as cc)).map(CodeCp(cc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeCp as cc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeCp] = {
    withSQL {
      select.from(CodeCp as cc).where.append(where)
    }.map(CodeCp(cc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeCp] = {
    withSQL {
      select.from(CodeCp as cc).where.append(where)
    }.map(CodeCp(cc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeCp as cc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeCp = {
    withSQL {
      insert.into(CodeCp).columns(
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

    CodeCp(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeCp)(implicit session: DBSession = autoSession): CodeCp = {
    withSQL {
      update(CodeCp).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeCp)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeCp).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
