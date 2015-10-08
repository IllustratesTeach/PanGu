package nirvana.hall.api.entities

import scalikejdbc._

case class CodeDhlx(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeDhlx.autoSession): CodeDhlx = CodeDhlx.save(this)(session)

  def destroy()(implicit session: DBSession = CodeDhlx.autoSession): Unit = CodeDhlx.destroy(this)(session)

}


object CodeDhlx extends SQLSyntaxSupport[CodeDhlx] {

  override val tableName = "CODE_DHLX"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cd: SyntaxProvider[CodeDhlx])(rs: WrappedResultSet): CodeDhlx = apply(cd.resultName)(rs)
  def apply(cd: ResultName[CodeDhlx])(rs: WrappedResultSet): CodeDhlx = new CodeDhlx(
    code = rs.get(cd.code),
    name = rs.get(cd.name),
    deleteFlag = rs.get(cd.deleteFlag),
    ord = rs.get(cd.ord),
    remark = rs.get(cd.remark)
  )

  val cd = CodeDhlx.syntax("cd")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeDhlx] = {
    withSQL {
      select.from(CodeDhlx as cd).where.eq(cd.code, code).and.eq(cd.name, name).and.eq(cd.deleteFlag, deleteFlag).and.eq(cd.ord, ord).and.eq(cd.remark, remark)
    }.map(CodeDhlx(cd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeDhlx] = {
    withSQL(select.from(CodeDhlx as cd)).map(CodeDhlx(cd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeDhlx as cd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeDhlx] = {
    withSQL {
      select.from(CodeDhlx as cd).where.append(where)
    }.map(CodeDhlx(cd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeDhlx] = {
    withSQL {
      select.from(CodeDhlx as cd).where.append(where)
    }.map(CodeDhlx(cd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeDhlx as cd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeDhlx = {
    withSQL {
      insert.into(CodeDhlx).columns(
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

    CodeDhlx(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeDhlx)(implicit session: DBSession = autoSession): CodeDhlx = {
    withSQL {
      update(CodeDhlx).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeDhlx)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeDhlx).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
