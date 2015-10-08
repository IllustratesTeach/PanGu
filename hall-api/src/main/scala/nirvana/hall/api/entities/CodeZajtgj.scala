package nirvana.hall.api.entities

import scalikejdbc._

case class CodeZajtgj(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeZajtgj.autoSession): CodeZajtgj = CodeZajtgj.save(this)(session)

  def destroy()(implicit session: DBSession = CodeZajtgj.autoSession): Unit = CodeZajtgj.destroy(this)(session)

}


object CodeZajtgj extends SQLSyntaxSupport[CodeZajtgj] {

  override val tableName = "CODE_ZAJTGJ"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cz: SyntaxProvider[CodeZajtgj])(rs: WrappedResultSet): CodeZajtgj = apply(cz.resultName)(rs)
  def apply(cz: ResultName[CodeZajtgj])(rs: WrappedResultSet): CodeZajtgj = new CodeZajtgj(
    code = rs.get(cz.code),
    name = rs.get(cz.name),
    deleteFlag = rs.get(cz.deleteFlag),
    ord = rs.get(cz.ord),
    remark = rs.get(cz.remark)
  )

  val cz = CodeZajtgj.syntax("cz")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeZajtgj] = {
    withSQL {
      select.from(CodeZajtgj as cz).where.eq(cz.code, code).and.eq(cz.name, name).and.eq(cz.deleteFlag, deleteFlag).and.eq(cz.ord, ord).and.eq(cz.remark, remark)
    }.map(CodeZajtgj(cz.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeZajtgj] = {
    withSQL(select.from(CodeZajtgj as cz)).map(CodeZajtgj(cz.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeZajtgj as cz)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeZajtgj] = {
    withSQL {
      select.from(CodeZajtgj as cz).where.append(where)
    }.map(CodeZajtgj(cz.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeZajtgj] = {
    withSQL {
      select.from(CodeZajtgj as cz).where.append(where)
    }.map(CodeZajtgj(cz.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeZajtgj as cz).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeZajtgj = {
    withSQL {
      insert.into(CodeZajtgj).columns(
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

    CodeZajtgj(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeZajtgj)(implicit session: DBSession = autoSession): CodeZajtgj = {
    withSQL {
      update(CodeZajtgj).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeZajtgj)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeZajtgj).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
