package nirvana.hall.api.entities

import scalikejdbc._

case class CodeBdzt(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeBdzt.autoSession): CodeBdzt = CodeBdzt.save(this)(session)

  def destroy()(implicit session: DBSession = CodeBdzt.autoSession): Unit = CodeBdzt.destroy(this)(session)

}


object CodeBdzt extends SQLSyntaxSupport[CodeBdzt] {

  override val tableName = "CODE_BDZT"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cb: SyntaxProvider[CodeBdzt])(rs: WrappedResultSet): CodeBdzt = apply(cb.resultName)(rs)
  def apply(cb: ResultName[CodeBdzt])(rs: WrappedResultSet): CodeBdzt = new CodeBdzt(
    code = rs.get(cb.code),
    name = rs.get(cb.name),
    deleteFlag = rs.get(cb.deleteFlag),
    ord = rs.get(cb.ord),
    remark = rs.get(cb.remark)
  )

  val cb = CodeBdzt.syntax("cb")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeBdzt] = {
    withSQL {
      select.from(CodeBdzt as cb).where.eq(cb.code, code).and.eq(cb.name, name).and.eq(cb.deleteFlag, deleteFlag).and.eq(cb.ord, ord).and.eq(cb.remark, remark)
    }.map(CodeBdzt(cb.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeBdzt] = {
    withSQL(select.from(CodeBdzt as cb)).map(CodeBdzt(cb.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeBdzt as cb)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeBdzt] = {
    withSQL {
      select.from(CodeBdzt as cb).where.append(where)
    }.map(CodeBdzt(cb.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeBdzt] = {
    withSQL {
      select.from(CodeBdzt as cb).where.append(where)
    }.map(CodeBdzt(cb.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeBdzt as cb).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeBdzt = {
    withSQL {
      insert.into(CodeBdzt).columns(
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

    CodeBdzt(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeBdzt)(implicit session: DBSession = autoSession): CodeBdzt = {
    withSQL {
      update(CodeBdzt).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeBdzt)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeBdzt).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
