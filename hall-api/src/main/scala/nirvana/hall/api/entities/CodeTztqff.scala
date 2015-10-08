package nirvana.hall.api.entities

import scalikejdbc._

case class CodeTztqff(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeTztqff.autoSession): CodeTztqff = CodeTztqff.save(this)(session)

  def destroy()(implicit session: DBSession = CodeTztqff.autoSession): Unit = CodeTztqff.destroy(this)(session)

}


object CodeTztqff extends SQLSyntaxSupport[CodeTztqff] {

  override val tableName = "CODE_TZTQFF"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(ct: SyntaxProvider[CodeTztqff])(rs: WrappedResultSet): CodeTztqff = apply(ct.resultName)(rs)
  def apply(ct: ResultName[CodeTztqff])(rs: WrappedResultSet): CodeTztqff = new CodeTztqff(
    code = rs.get(ct.code),
    name = rs.get(ct.name),
    deleteFlag = rs.get(ct.deleteFlag),
    ord = rs.get(ct.ord),
    remark = rs.get(ct.remark)
  )

  val ct = CodeTztqff.syntax("ct")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeTztqff] = {
    withSQL {
      select.from(CodeTztqff as ct).where.eq(ct.code, code).and.eq(ct.name, name).and.eq(ct.deleteFlag, deleteFlag).and.eq(ct.ord, ord).and.eq(ct.remark, remark)
    }.map(CodeTztqff(ct.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeTztqff] = {
    withSQL(select.from(CodeTztqff as ct)).map(CodeTztqff(ct.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeTztqff as ct)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeTztqff] = {
    withSQL {
      select.from(CodeTztqff as ct).where.append(where)
    }.map(CodeTztqff(ct.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeTztqff] = {
    withSQL {
      select.from(CodeTztqff as ct).where.append(where)
    }.map(CodeTztqff(ct.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeTztqff as ct).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeTztqff = {
    withSQL {
      insert.into(CodeTztqff).columns(
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

    CodeTztqff(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeTztqff)(implicit session: DBSession = autoSession): CodeTztqff = {
    withSQL {
      update(CodeTztqff).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeTztqff)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeTztqff).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
