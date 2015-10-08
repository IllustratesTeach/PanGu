package nirvana.hall.api.entities

import scalikejdbc._

case class CodeXctqfs(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeXctqfs.autoSession): CodeXctqfs = CodeXctqfs.save(this)(session)

  def destroy()(implicit session: DBSession = CodeXctqfs.autoSession): Unit = CodeXctqfs.destroy(this)(session)

}


object CodeXctqfs extends SQLSyntaxSupport[CodeXctqfs] {

  override val tableName = "CODE_XCTQFS"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cx: SyntaxProvider[CodeXctqfs])(rs: WrappedResultSet): CodeXctqfs = apply(cx.resultName)(rs)
  def apply(cx: ResultName[CodeXctqfs])(rs: WrappedResultSet): CodeXctqfs = new CodeXctqfs(
    code = rs.get(cx.code),
    name = rs.get(cx.name),
    deleteFlag = rs.get(cx.deleteFlag),
    ord = rs.get(cx.ord),
    remark = rs.get(cx.remark)
  )

  val cx = CodeXctqfs.syntax("cx")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeXctqfs] = {
    withSQL {
      select.from(CodeXctqfs as cx).where.eq(cx.code, code).and.eq(cx.name, name).and.eq(cx.deleteFlag, deleteFlag).and.eq(cx.ord, ord).and.eq(cx.remark, remark)
    }.map(CodeXctqfs(cx.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeXctqfs] = {
    withSQL(select.from(CodeXctqfs as cx)).map(CodeXctqfs(cx.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeXctqfs as cx)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeXctqfs] = {
    withSQL {
      select.from(CodeXctqfs as cx).where.append(where)
    }.map(CodeXctqfs(cx.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeXctqfs] = {
    withSQL {
      select.from(CodeXctqfs as cx).where.append(where)
    }.map(CodeXctqfs(cx.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeXctqfs as cx).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeXctqfs = {
    withSQL {
      insert.into(CodeXctqfs).columns(
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

    CodeXctqfs(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeXctqfs)(implicit session: DBSession = autoSession): CodeXctqfs = {
    withSQL {
      update(CodeXctqfs).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeXctqfs)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeXctqfs).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
