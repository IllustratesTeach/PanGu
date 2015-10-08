package nirvana.hall.api.entities

import scalikejdbc._

case class CodeZajtgjly(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeZajtgjly.autoSession): CodeZajtgjly = CodeZajtgjly.save(this)(session)

  def destroy()(implicit session: DBSession = CodeZajtgjly.autoSession): Unit = CodeZajtgjly.destroy(this)(session)

}


object CodeZajtgjly extends SQLSyntaxSupport[CodeZajtgjly] {

  override val tableName = "CODE_ZAJTGJLY"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cz: SyntaxProvider[CodeZajtgjly])(rs: WrappedResultSet): CodeZajtgjly = apply(cz.resultName)(rs)
  def apply(cz: ResultName[CodeZajtgjly])(rs: WrappedResultSet): CodeZajtgjly = new CodeZajtgjly(
    code = rs.get(cz.code),
    name = rs.get(cz.name),
    deleteFlag = rs.get(cz.deleteFlag),
    ord = rs.get(cz.ord),
    remark = rs.get(cz.remark)
  )

  val cz = CodeZajtgjly.syntax("cz")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeZajtgjly] = {
    withSQL {
      select.from(CodeZajtgjly as cz).where.eq(cz.code, code).and.eq(cz.name, name).and.eq(cz.deleteFlag, deleteFlag).and.eq(cz.ord, ord).and.eq(cz.remark, remark)
    }.map(CodeZajtgjly(cz.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeZajtgjly] = {
    withSQL(select.from(CodeZajtgjly as cz)).map(CodeZajtgjly(cz.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeZajtgjly as cz)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeZajtgjly] = {
    withSQL {
      select.from(CodeZajtgjly as cz).where.append(where)
    }.map(CodeZajtgjly(cz.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeZajtgjly] = {
    withSQL {
      select.from(CodeZajtgjly as cz).where.append(where)
    }.map(CodeZajtgjly(cz.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeZajtgjly as cz).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeZajtgjly = {
    withSQL {
      insert.into(CodeZajtgjly).columns(
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

    CodeZajtgjly(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeZajtgjly)(implicit session: DBSession = autoSession): CodeZajtgjly = {
    withSQL {
      update(CodeZajtgjly).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeZajtgjly)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeZajtgjly).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
