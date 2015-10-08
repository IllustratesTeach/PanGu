package nirvana.hall.api.entities

import scalikejdbc._

case class CodeRtbw(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeRtbw.autoSession): CodeRtbw = CodeRtbw.save(this)(session)

  def destroy()(implicit session: DBSession = CodeRtbw.autoSession): Unit = CodeRtbw.destroy(this)(session)

}


object CodeRtbw extends SQLSyntaxSupport[CodeRtbw] {

  override val tableName = "CODE_RTBW"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cr: SyntaxProvider[CodeRtbw])(rs: WrappedResultSet): CodeRtbw = apply(cr.resultName)(rs)
  def apply(cr: ResultName[CodeRtbw])(rs: WrappedResultSet): CodeRtbw = new CodeRtbw(
    code = rs.get(cr.code),
    name = rs.get(cr.name),
    deleteFlag = rs.get(cr.deleteFlag),
    ord = rs.get(cr.ord),
    remark = rs.get(cr.remark)
  )

  val cr = CodeRtbw.syntax("cr")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeRtbw] = {
    withSQL {
      select.from(CodeRtbw as cr).where.eq(cr.code, code).and.eq(cr.name, name).and.eq(cr.deleteFlag, deleteFlag).and.eq(cr.ord, ord).and.eq(cr.remark, remark)
    }.map(CodeRtbw(cr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeRtbw] = {
    withSQL(select.from(CodeRtbw as cr)).map(CodeRtbw(cr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeRtbw as cr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeRtbw] = {
    withSQL {
      select.from(CodeRtbw as cr).where.append(where)
    }.map(CodeRtbw(cr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeRtbw] = {
    withSQL {
      select.from(CodeRtbw as cr).where.append(where)
    }.map(CodeRtbw(cr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeRtbw as cr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeRtbw = {
    withSQL {
      insert.into(CodeRtbw).columns(
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

    CodeRtbw(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeRtbw)(implicit session: DBSession = autoSession): CodeRtbw = {
    withSQL {
      update(CodeRtbw).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeRtbw)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeRtbw).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
