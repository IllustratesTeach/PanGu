package nirvana.hall.api.entities

import scalikejdbc._

case class CodeWhcd(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeWhcd.autoSession): CodeWhcd = CodeWhcd.save(this)(session)

  def destroy()(implicit session: DBSession = CodeWhcd.autoSession): Unit = CodeWhcd.destroy(this)(session)

}


object CodeWhcd extends SQLSyntaxSupport[CodeWhcd] {

  override val tableName = "CODE_WHCD"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cw: SyntaxProvider[CodeWhcd])(rs: WrappedResultSet): CodeWhcd = apply(cw.resultName)(rs)
  def apply(cw: ResultName[CodeWhcd])(rs: WrappedResultSet): CodeWhcd = new CodeWhcd(
    code = rs.get(cw.code),
    name = rs.get(cw.name),
    deleteFlag = rs.get(cw.deleteFlag),
    ord = rs.get(cw.ord),
    remark = rs.get(cw.remark)
  )

  val cw = CodeWhcd.syntax("cw")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String)(implicit session: DBSession = autoSession): Option[CodeWhcd] = {
    withSQL {
      select.from(CodeWhcd as cw).where.eq(cw.code, code)
    }.map(CodeWhcd(cw.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeWhcd] = {
    withSQL(select.from(CodeWhcd as cw)).map(CodeWhcd(cw.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeWhcd as cw)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeWhcd] = {
    withSQL {
      select.from(CodeWhcd as cw).where.append(where)
    }.map(CodeWhcd(cw.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeWhcd] = {
    withSQL {
      select.from(CodeWhcd as cw).where.append(where)
    }.map(CodeWhcd(cw.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeWhcd as cw).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeWhcd = {
    withSQL {
      insert.into(CodeWhcd).columns(
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

    CodeWhcd(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeWhcd)(implicit session: DBSession = autoSession): CodeWhcd = {
    withSQL {
      update(CodeWhcd).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeWhcd)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeWhcd).where.eq(column.code, entity.code) }.update.apply()
  }

}
