package nirvana.hall.api.entities

import scalikejdbc._

case class CodeFcqdzdwp(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeFcqdzdwp.autoSession): CodeFcqdzdwp = CodeFcqdzdwp.save(this)(session)

  def destroy()(implicit session: DBSession = CodeFcqdzdwp.autoSession): Unit = CodeFcqdzdwp.destroy(this)(session)

}


object CodeFcqdzdwp extends SQLSyntaxSupport[CodeFcqdzdwp] {

  override val tableName = "CODE_FCQDZDWP"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cf: SyntaxProvider[CodeFcqdzdwp])(rs: WrappedResultSet): CodeFcqdzdwp = apply(cf.resultName)(rs)
  def apply(cf: ResultName[CodeFcqdzdwp])(rs: WrappedResultSet): CodeFcqdzdwp = new CodeFcqdzdwp(
    code = rs.get(cf.code),
    name = rs.get(cf.name),
    deleteFlag = rs.get(cf.deleteFlag),
    ord = rs.get(cf.ord),
    remark = rs.get(cf.remark)
  )

  val cf = CodeFcqdzdwp.syntax("cf")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeFcqdzdwp] = {
    withSQL {
      select.from(CodeFcqdzdwp as cf).where.eq(cf.code, code).and.eq(cf.name, name).and.eq(cf.deleteFlag, deleteFlag).and.eq(cf.ord, ord).and.eq(cf.remark, remark)
    }.map(CodeFcqdzdwp(cf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeFcqdzdwp] = {
    withSQL(select.from(CodeFcqdzdwp as cf)).map(CodeFcqdzdwp(cf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeFcqdzdwp as cf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeFcqdzdwp] = {
    withSQL {
      select.from(CodeFcqdzdwp as cf).where.append(where)
    }.map(CodeFcqdzdwp(cf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeFcqdzdwp] = {
    withSQL {
      select.from(CodeFcqdzdwp as cf).where.append(where)
    }.map(CodeFcqdzdwp(cf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeFcqdzdwp as cf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeFcqdzdwp = {
    withSQL {
      insert.into(CodeFcqdzdwp).columns(
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

    CodeFcqdzdwp(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeFcqdzdwp)(implicit session: DBSession = autoSession): CodeFcqdzdwp = {
    withSQL {
      update(CodeFcqdzdwp).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeFcqdzdwp)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeFcqdzdwp).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
