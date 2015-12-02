package nirvana.hall.api.entities

import scalikejdbc._

case class CodeJjly(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeJjly.autoSession): CodeJjly = CodeJjly.save(this)(session)

  def destroy()(implicit session: DBSession = CodeJjly.autoSession): Unit = CodeJjly.destroy(this)(session)

}


object CodeJjly extends SQLSyntaxSupport[CodeJjly] {

  override val tableName = "CODE_JJLY"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cj: SyntaxProvider[CodeJjly])(rs: WrappedResultSet): CodeJjly = apply(cj.resultName)(rs)
  def apply(cj: ResultName[CodeJjly])(rs: WrappedResultSet): CodeJjly = new CodeJjly(
    code = rs.get(cj.code),
    name = rs.get(cj.name),
    deleteFlag = rs.get(cj.deleteFlag),
    ord = rs.get(cj.ord),
    remark = rs.get(cj.remark)
  )

  val cj = CodeJjly.syntax("cj")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String)(implicit session: DBSession = autoSession): Option[CodeJjly] = {
    withSQL {
      select.from(CodeJjly as cj).where.eq(cj.code, code)
    }.map(CodeJjly(cj.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeJjly] = {
    withSQL(select.from(CodeJjly as cj)).map(CodeJjly(cj.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeJjly as cj)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeJjly] = {
    withSQL {
      select.from(CodeJjly as cj).where.append(where)
    }.map(CodeJjly(cj.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeJjly] = {
    withSQL {
      select.from(CodeJjly as cj).where.append(where)
    }.map(CodeJjly(cj.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeJjly as cj).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeJjly = {
    withSQL {
      insert.into(CodeJjly).columns(
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

    CodeJjly(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeJjly)(implicit session: DBSession = autoSession): CodeJjly = {
    withSQL {
      update(CodeJjly).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeJjly)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeJjly).where.eq(column.code, entity.code) }.update.apply()
  }

}
