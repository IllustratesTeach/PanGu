package nirvana.hall.api.entities

import scalikejdbc._

case class CodeRzlgdc(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeRzlgdc.autoSession): CodeRzlgdc = CodeRzlgdc.save(this)(session)

  def destroy()(implicit session: DBSession = CodeRzlgdc.autoSession): Unit = CodeRzlgdc.destroy(this)(session)

}


object CodeRzlgdc extends SQLSyntaxSupport[CodeRzlgdc] {

  override val tableName = "CODE_RZLGDC"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cr: SyntaxProvider[CodeRzlgdc])(rs: WrappedResultSet): CodeRzlgdc = apply(cr.resultName)(rs)
  def apply(cr: ResultName[CodeRzlgdc])(rs: WrappedResultSet): CodeRzlgdc = new CodeRzlgdc(
    code = rs.get(cr.code),
    name = rs.get(cr.name),
    deleteFlag = rs.get(cr.deleteFlag),
    ord = rs.get(cr.ord),
    remark = rs.get(cr.remark)
  )

  val cr = CodeRzlgdc.syntax("cr")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeRzlgdc] = {
    withSQL {
      select.from(CodeRzlgdc as cr).where.eq(cr.code, code).and.eq(cr.name, name).and.eq(cr.deleteFlag, deleteFlag).and.eq(cr.ord, ord).and.eq(cr.remark, remark)
    }.map(CodeRzlgdc(cr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeRzlgdc] = {
    withSQL(select.from(CodeRzlgdc as cr)).map(CodeRzlgdc(cr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeRzlgdc as cr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeRzlgdc] = {
    withSQL {
      select.from(CodeRzlgdc as cr).where.append(where)
    }.map(CodeRzlgdc(cr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeRzlgdc] = {
    withSQL {
      select.from(CodeRzlgdc as cr).where.append(where)
    }.map(CodeRzlgdc(cr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeRzlgdc as cr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeRzlgdc = {
    withSQL {
      insert.into(CodeRzlgdc).columns(
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

    CodeRzlgdc(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeRzlgdc)(implicit session: DBSession = autoSession): CodeRzlgdc = {
    withSQL {
      update(CodeRzlgdc).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeRzlgdc)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeRzlgdc).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
