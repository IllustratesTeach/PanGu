package nirvana.hall.api.entities

import scalikejdbc._

case class CodeZjzl(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeZjzl.autoSession): CodeZjzl = CodeZjzl.save(this)(session)

  def destroy()(implicit session: DBSession = CodeZjzl.autoSession): Unit = CodeZjzl.destroy(this)(session)

}


object CodeZjzl extends SQLSyntaxSupport[CodeZjzl] {

  override val tableName = "CODE_ZJZL"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cz: SyntaxProvider[CodeZjzl])(rs: WrappedResultSet): CodeZjzl = apply(cz.resultName)(rs)
  def apply(cz: ResultName[CodeZjzl])(rs: WrappedResultSet): CodeZjzl = new CodeZjzl(
    code = rs.get(cz.code),
    name = rs.get(cz.name),
    deleteFlag = rs.get(cz.deleteFlag),
    ord = rs.get(cz.ord),
    remark = rs.get(cz.remark)
  )

  val cz = CodeZjzl.syntax("cz")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String)(implicit session: DBSession = autoSession): Option[CodeZjzl] = {
    withSQL {
      select.from(CodeZjzl as cz).where.eq(cz.code, code)
    }.map(CodeZjzl(cz.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeZjzl] = {
    withSQL(select.from(CodeZjzl as cz)).map(CodeZjzl(cz.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeZjzl as cz)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeZjzl] = {
    withSQL {
      select.from(CodeZjzl as cz).where.append(where)
    }.map(CodeZjzl(cz.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeZjzl] = {
    withSQL {
      select.from(CodeZjzl as cz).where.append(where)
    }.map(CodeZjzl(cz.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeZjzl as cz).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeZjzl = {
    withSQL {
      insert.into(CodeZjzl).columns(
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

    CodeZjzl(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeZjzl)(implicit session: DBSession = autoSession): CodeZjzl = {
    withSQL {
      update(CodeZjzl).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeZjzl)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeZjzl).where.eq(column.code, entity.code) }.update.apply()
  }

}
