package nirvana.hall.api.entities

import scalikejdbc._

case class CodeTbtsbj(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeTbtsbj.autoSession): CodeTbtsbj = CodeTbtsbj.save(this)(session)

  def destroy()(implicit session: DBSession = CodeTbtsbj.autoSession): Unit = CodeTbtsbj.destroy(this)(session)

}


object CodeTbtsbj extends SQLSyntaxSupport[CodeTbtsbj] {

  override val tableName = "CODE_TBTSBJ"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(ct: SyntaxProvider[CodeTbtsbj])(rs: WrappedResultSet): CodeTbtsbj = apply(ct.resultName)(rs)
  def apply(ct: ResultName[CodeTbtsbj])(rs: WrappedResultSet): CodeTbtsbj = new CodeTbtsbj(
    code = rs.get(ct.code),
    name = rs.get(ct.name),
    deleteFlag = rs.get(ct.deleteFlag),
    ord = rs.get(ct.ord),
    remark = rs.get(ct.remark)
  )

  val ct = CodeTbtsbj.syntax("ct")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeTbtsbj] = {
    withSQL {
      select.from(CodeTbtsbj as ct).where.eq(ct.code, code).and.eq(ct.name, name).and.eq(ct.deleteFlag, deleteFlag).and.eq(ct.ord, ord).and.eq(ct.remark, remark)
    }.map(CodeTbtsbj(ct.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeTbtsbj] = {
    withSQL(select.from(CodeTbtsbj as ct)).map(CodeTbtsbj(ct.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeTbtsbj as ct)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeTbtsbj] = {
    withSQL {
      select.from(CodeTbtsbj as ct).where.append(where)
    }.map(CodeTbtsbj(ct.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeTbtsbj] = {
    withSQL {
      select.from(CodeTbtsbj as ct).where.append(where)
    }.map(CodeTbtsbj(ct.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeTbtsbj as ct).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeTbtsbj = {
    withSQL {
      insert.into(CodeTbtsbj).columns(
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

    CodeTbtsbj(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeTbtsbj)(implicit session: DBSession = autoSession): CodeTbtsbj = {
    withSQL {
      update(CodeTbtsbj).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeTbtsbj)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeTbtsbj).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
