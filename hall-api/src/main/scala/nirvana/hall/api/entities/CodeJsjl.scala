package nirvana.hall.api.entities

import scalikejdbc._

case class CodeJsjl(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeJsjl.autoSession): CodeJsjl = CodeJsjl.save(this)(session)

  def destroy()(implicit session: DBSession = CodeJsjl.autoSession): Unit = CodeJsjl.destroy(this)(session)

}


object CodeJsjl extends SQLSyntaxSupport[CodeJsjl] {

  override val tableName = "CODE_JSJL"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cj: SyntaxProvider[CodeJsjl])(rs: WrappedResultSet): CodeJsjl = apply(cj.resultName)(rs)
  def apply(cj: ResultName[CodeJsjl])(rs: WrappedResultSet): CodeJsjl = new CodeJsjl(
    code = rs.get(cj.code),
    name = rs.get(cj.name),
    deleteFlag = rs.get(cj.deleteFlag),
    ord = rs.get(cj.ord),
    remark = rs.get(cj.remark)
  )

  val cj = CodeJsjl.syntax("cj")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeJsjl] = {
    withSQL {
      select.from(CodeJsjl as cj).where.eq(cj.code, code).and.eq(cj.name, name).and.eq(cj.deleteFlag, deleteFlag).and.eq(cj.ord, ord).and.eq(cj.remark, remark)
    }.map(CodeJsjl(cj.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeJsjl] = {
    withSQL(select.from(CodeJsjl as cj)).map(CodeJsjl(cj.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeJsjl as cj)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeJsjl] = {
    withSQL {
      select.from(CodeJsjl as cj).where.append(where)
    }.map(CodeJsjl(cj.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeJsjl] = {
    withSQL {
      select.from(CodeJsjl as cj).where.append(where)
    }.map(CodeJsjl(cj.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeJsjl as cj).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeJsjl = {
    withSQL {
      insert.into(CodeJsjl).columns(
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

    CodeJsjl(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeJsjl)(implicit session: DBSession = autoSession): CodeJsjl = {
    withSQL {
      update(CodeJsjl).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeJsjl)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeJsjl).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
