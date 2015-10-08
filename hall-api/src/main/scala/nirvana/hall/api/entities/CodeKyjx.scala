package nirvana.hall.api.entities

import scalikejdbc._

case class CodeKyjx(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeKyjx.autoSession): CodeKyjx = CodeKyjx.save(this)(session)

  def destroy()(implicit session: DBSession = CodeKyjx.autoSession): Unit = CodeKyjx.destroy(this)(session)

}


object CodeKyjx extends SQLSyntaxSupport[CodeKyjx] {

  override val tableName = "CODE_KYJX"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(ck: SyntaxProvider[CodeKyjx])(rs: WrappedResultSet): CodeKyjx = apply(ck.resultName)(rs)
  def apply(ck: ResultName[CodeKyjx])(rs: WrappedResultSet): CodeKyjx = new CodeKyjx(
    code = rs.get(ck.code),
    name = rs.get(ck.name),
    deleteFlag = rs.get(ck.deleteFlag),
    ord = rs.get(ck.ord),
    remark = rs.get(ck.remark)
  )

  val ck = CodeKyjx.syntax("ck")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeKyjx] = {
    withSQL {
      select.from(CodeKyjx as ck).where.eq(ck.code, code).and.eq(ck.name, name).and.eq(ck.deleteFlag, deleteFlag).and.eq(ck.ord, ord).and.eq(ck.remark, remark)
    }.map(CodeKyjx(ck.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeKyjx] = {
    withSQL(select.from(CodeKyjx as ck)).map(CodeKyjx(ck.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeKyjx as ck)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeKyjx] = {
    withSQL {
      select.from(CodeKyjx as ck).where.append(where)
    }.map(CodeKyjx(ck.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeKyjx] = {
    withSQL {
      select.from(CodeKyjx as ck).where.append(where)
    }.map(CodeKyjx(ck.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeKyjx as ck).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeKyjx = {
    withSQL {
      insert.into(CodeKyjx).columns(
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

    CodeKyjx(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeKyjx)(implicit session: DBSession = autoSession): CodeKyjx = {
    withSQL {
      update(CodeKyjx).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeKyjx)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeKyjx).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
