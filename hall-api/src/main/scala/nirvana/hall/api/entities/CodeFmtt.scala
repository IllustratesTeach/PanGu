package nirvana.hall.api.entities

import scalikejdbc._

case class CodeFmtt(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeFmtt.autoSession): CodeFmtt = CodeFmtt.save(this)(session)

  def destroy()(implicit session: DBSession = CodeFmtt.autoSession): Unit = CodeFmtt.destroy(this)(session)

}


object CodeFmtt extends SQLSyntaxSupport[CodeFmtt] {

  override val tableName = "CODE_FMTT"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cf: SyntaxProvider[CodeFmtt])(rs: WrappedResultSet): CodeFmtt = apply(cf.resultName)(rs)
  def apply(cf: ResultName[CodeFmtt])(rs: WrappedResultSet): CodeFmtt = new CodeFmtt(
    code = rs.get(cf.code),
    name = rs.get(cf.name),
    deleteFlag = rs.get(cf.deleteFlag),
    ord = rs.get(cf.ord),
    remark = rs.get(cf.remark)
  )

  val cf = CodeFmtt.syntax("cf")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeFmtt] = {
    withSQL {
      select.from(CodeFmtt as cf).where.eq(cf.code, code).and.eq(cf.name, name).and.eq(cf.deleteFlag, deleteFlag).and.eq(cf.ord, ord).and.eq(cf.remark, remark)
    }.map(CodeFmtt(cf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeFmtt] = {
    withSQL(select.from(CodeFmtt as cf)).map(CodeFmtt(cf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeFmtt as cf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeFmtt] = {
    withSQL {
      select.from(CodeFmtt as cf).where.append(where)
    }.map(CodeFmtt(cf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeFmtt] = {
    withSQL {
      select.from(CodeFmtt as cf).where.append(where)
    }.map(CodeFmtt(cf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeFmtt as cf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeFmtt = {
    withSQL {
      insert.into(CodeFmtt).columns(
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

    CodeFmtt(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeFmtt)(implicit session: DBSession = autoSession): CodeFmtt = {
    withSQL {
      update(CodeFmtt).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeFmtt)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeFmtt).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
