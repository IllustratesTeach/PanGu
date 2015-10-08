package nirvana.hall.api.entities

import scalikejdbc._

case class CodeAjzt(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeAjzt.autoSession): CodeAjzt = CodeAjzt.save(this)(session)

  def destroy()(implicit session: DBSession = CodeAjzt.autoSession): Unit = CodeAjzt.destroy(this)(session)

}


object CodeAjzt extends SQLSyntaxSupport[CodeAjzt] {

  override val tableName = "CODE_AJZT"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(ca: SyntaxProvider[CodeAjzt])(rs: WrappedResultSet): CodeAjzt = apply(ca.resultName)(rs)
  def apply(ca: ResultName[CodeAjzt])(rs: WrappedResultSet): CodeAjzt = new CodeAjzt(
    code = rs.get(ca.code),
    name = rs.get(ca.name),
    deleteFlag = rs.get(ca.deleteFlag),
    ord = rs.get(ca.ord),
    remark = rs.get(ca.remark)
  )

  val ca = CodeAjzt.syntax("ca")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeAjzt] = {
    withSQL {
      select.from(CodeAjzt as ca).where.eq(ca.code, code).and.eq(ca.name, name).and.eq(ca.deleteFlag, deleteFlag).and.eq(ca.ord, ord).and.eq(ca.remark, remark)
    }.map(CodeAjzt(ca.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeAjzt] = {
    withSQL(select.from(CodeAjzt as ca)).map(CodeAjzt(ca.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeAjzt as ca)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeAjzt] = {
    withSQL {
      select.from(CodeAjzt as ca).where.append(where)
    }.map(CodeAjzt(ca.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeAjzt] = {
    withSQL {
      select.from(CodeAjzt as ca).where.append(where)
    }.map(CodeAjzt(ca.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeAjzt as ca).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeAjzt = {
    withSQL {
      insert.into(CodeAjzt).columns(
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

    CodeAjzt(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeAjzt)(implicit session: DBSession = autoSession): CodeAjzt = {
    withSQL {
      update(CodeAjzt).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeAjzt)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeAjzt).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
