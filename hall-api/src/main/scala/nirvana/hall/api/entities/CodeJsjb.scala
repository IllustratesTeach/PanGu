package nirvana.hall.api.entities

import scalikejdbc._

case class CodeJsjb(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeJsjb.autoSession): CodeJsjb = CodeJsjb.save(this)(session)

  def destroy()(implicit session: DBSession = CodeJsjb.autoSession): Unit = CodeJsjb.destroy(this)(session)

}


object CodeJsjb extends SQLSyntaxSupport[CodeJsjb] {

  override val tableName = "CODE_JSJB"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cj: SyntaxProvider[CodeJsjb])(rs: WrappedResultSet): CodeJsjb = apply(cj.resultName)(rs)
  def apply(cj: ResultName[CodeJsjb])(rs: WrappedResultSet): CodeJsjb = new CodeJsjb(
    code = rs.get(cj.code),
    name = rs.get(cj.name),
    deleteFlag = rs.get(cj.deleteFlag),
    ord = rs.get(cj.ord),
    remark = rs.get(cj.remark)
  )

  val cj = CodeJsjb.syntax("cj")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeJsjb] = {
    withSQL {
      select.from(CodeJsjb as cj).where.eq(cj.code, code).and.eq(cj.name, name).and.eq(cj.deleteFlag, deleteFlag).and.eq(cj.ord, ord).and.eq(cj.remark, remark)
    }.map(CodeJsjb(cj.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeJsjb] = {
    withSQL(select.from(CodeJsjb as cj)).map(CodeJsjb(cj.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeJsjb as cj)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeJsjb] = {
    withSQL {
      select.from(CodeJsjb as cj).where.append(where)
    }.map(CodeJsjb(cj.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeJsjb] = {
    withSQL {
      select.from(CodeJsjb as cj).where.append(where)
    }.map(CodeJsjb(cj.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeJsjb as cj).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeJsjb = {
    withSQL {
      insert.into(CodeJsjb).columns(
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

    CodeJsjb(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeJsjb)(implicit session: DBSession = autoSession): CodeJsjb = {
    withSQL {
      update(CodeJsjb).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeJsjb)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeJsjb).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
