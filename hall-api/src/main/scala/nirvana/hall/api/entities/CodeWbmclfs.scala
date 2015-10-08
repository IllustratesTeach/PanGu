package nirvana.hall.api.entities

import scalikejdbc._

case class CodeWbmclfs(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeWbmclfs.autoSession): CodeWbmclfs = CodeWbmclfs.save(this)(session)

  def destroy()(implicit session: DBSession = CodeWbmclfs.autoSession): Unit = CodeWbmclfs.destroy(this)(session)

}


object CodeWbmclfs extends SQLSyntaxSupport[CodeWbmclfs] {

  override val tableName = "CODE_WBMCLFS"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cw: SyntaxProvider[CodeWbmclfs])(rs: WrappedResultSet): CodeWbmclfs = apply(cw.resultName)(rs)
  def apply(cw: ResultName[CodeWbmclfs])(rs: WrappedResultSet): CodeWbmclfs = new CodeWbmclfs(
    code = rs.get(cw.code),
    name = rs.get(cw.name),
    deleteFlag = rs.get(cw.deleteFlag),
    ord = rs.get(cw.ord),
    remark = rs.get(cw.remark)
  )

  val cw = CodeWbmclfs.syntax("cw")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeWbmclfs] = {
    withSQL {
      select.from(CodeWbmclfs as cw).where.eq(cw.code, code).and.eq(cw.name, name).and.eq(cw.deleteFlag, deleteFlag).and.eq(cw.ord, ord).and.eq(cw.remark, remark)
    }.map(CodeWbmclfs(cw.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeWbmclfs] = {
    withSQL(select.from(CodeWbmclfs as cw)).map(CodeWbmclfs(cw.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeWbmclfs as cw)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeWbmclfs] = {
    withSQL {
      select.from(CodeWbmclfs as cw).where.append(where)
    }.map(CodeWbmclfs(cw.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeWbmclfs] = {
    withSQL {
      select.from(CodeWbmclfs as cw).where.append(where)
    }.map(CodeWbmclfs(cw.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeWbmclfs as cw).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeWbmclfs = {
    withSQL {
      insert.into(CodeWbmclfs).columns(
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

    CodeWbmclfs(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeWbmclfs)(implicit session: DBSession = autoSession): CodeWbmclfs = {
    withSQL {
      update(CodeWbmclfs).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeWbmclfs)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeWbmclfs).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
