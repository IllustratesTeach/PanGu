package nirvana.hall.api.entities

import scalikejdbc._

case class CodeZmcrshj(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeZmcrshj.autoSession): CodeZmcrshj = CodeZmcrshj.save(this)(session)

  def destroy()(implicit session: DBSession = CodeZmcrshj.autoSession): Unit = CodeZmcrshj.destroy(this)(session)

}


object CodeZmcrshj extends SQLSyntaxSupport[CodeZmcrshj] {

  override val tableName = "CODE_ZMCRSHJ"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cz: SyntaxProvider[CodeZmcrshj])(rs: WrappedResultSet): CodeZmcrshj = apply(cz.resultName)(rs)
  def apply(cz: ResultName[CodeZmcrshj])(rs: WrappedResultSet): CodeZmcrshj = new CodeZmcrshj(
    code = rs.get(cz.code),
    name = rs.get(cz.name),
    deleteFlag = rs.get(cz.deleteFlag),
    ord = rs.get(cz.ord),
    remark = rs.get(cz.remark)
  )

  val cz = CodeZmcrshj.syntax("cz")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeZmcrshj] = {
    withSQL {
      select.from(CodeZmcrshj as cz).where.eq(cz.code, code).and.eq(cz.name, name).and.eq(cz.deleteFlag, deleteFlag).and.eq(cz.ord, ord).and.eq(cz.remark, remark)
    }.map(CodeZmcrshj(cz.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeZmcrshj] = {
    withSQL(select.from(CodeZmcrshj as cz)).map(CodeZmcrshj(cz.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeZmcrshj as cz)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeZmcrshj] = {
    withSQL {
      select.from(CodeZmcrshj as cz).where.append(where)
    }.map(CodeZmcrshj(cz.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeZmcrshj] = {
    withSQL {
      select.from(CodeZmcrshj as cz).where.append(where)
    }.map(CodeZmcrshj(cz.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeZmcrshj as cz).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeZmcrshj = {
    withSQL {
      insert.into(CodeZmcrshj).columns(
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

    CodeZmcrshj(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeZmcrshj)(implicit session: DBSession = autoSession): CodeZmcrshj = {
    withSQL {
      update(CodeZmcrshj).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeZmcrshj)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeZmcrshj).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
