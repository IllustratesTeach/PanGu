package nirvana.hall.api.entities

import scalikejdbc._

case class CodeCjlb(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeCjlb.autoSession): CodeCjlb = CodeCjlb.save(this)(session)

  def destroy()(implicit session: DBSession = CodeCjlb.autoSession): Unit = CodeCjlb.destroy(this)(session)

}


object CodeCjlb extends SQLSyntaxSupport[CodeCjlb] {

  override val tableName = "CODE_CJLB"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cc: SyntaxProvider[CodeCjlb])(rs: WrappedResultSet): CodeCjlb = apply(cc.resultName)(rs)
  def apply(cc: ResultName[CodeCjlb])(rs: WrappedResultSet): CodeCjlb = new CodeCjlb(
    code = rs.get(cc.code),
    name = rs.get(cc.name),
    deleteFlag = rs.get(cc.deleteFlag),
    ord = rs.get(cc.ord),
    remark = rs.get(cc.remark)
  )

  val cc = CodeCjlb.syntax("cc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String)(implicit session: DBSession = autoSession): Option[CodeCjlb] = {
    withSQL {
      select.from(CodeCjlb as cc).where.eq(cc.code, code)
    }.map(CodeCjlb(cc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeCjlb] = {
    withSQL(select.from(CodeCjlb as cc)).map(CodeCjlb(cc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeCjlb as cc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeCjlb] = {
    withSQL {
      select.from(CodeCjlb as cc).where.append(where)
    }.map(CodeCjlb(cc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeCjlb] = {
    withSQL {
      select.from(CodeCjlb as cc).where.append(where)
    }.map(CodeCjlb(cc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeCjlb as cc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeCjlb = {
    withSQL {
      insert.into(CodeCjlb).columns(
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

    CodeCjlb(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeCjlb)(implicit session: DBSession = autoSession): CodeCjlb = {
    withSQL {
      update(CodeCjlb).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeCjlb)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeCjlb).where.eq(column.code, entity.code) }.update.apply()
  }

}
