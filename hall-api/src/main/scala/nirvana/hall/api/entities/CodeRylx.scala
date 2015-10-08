package nirvana.hall.api.entities

import scalikejdbc._

case class CodeRylx(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None,
  strtype: Option[String] = None) {

  def save()(implicit session: DBSession = CodeRylx.autoSession): CodeRylx = CodeRylx.save(this)(session)

  def destroy()(implicit session: DBSession = CodeRylx.autoSession): Unit = CodeRylx.destroy(this)(session)

}


object CodeRylx extends SQLSyntaxSupport[CodeRylx] {

  override val tableName = "CODE_RYLX"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK", "STRTYPE")

  def apply(cr: SyntaxProvider[CodeRylx])(rs: WrappedResultSet): CodeRylx = apply(cr.resultName)(rs)
  def apply(cr: ResultName[CodeRylx])(rs: WrappedResultSet): CodeRylx = new CodeRylx(
    code = rs.get(cr.code),
    name = rs.get(cr.name),
    deleteFlag = rs.get(cr.deleteFlag),
    ord = rs.get(cr.ord),
    remark = rs.get(cr.remark),
    strtype = rs.get(cr.strtype)
  )

  val cr = CodeRylx.syntax("cr")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String], strtype: Option[String])(implicit session: DBSession = autoSession): Option[CodeRylx] = {
    withSQL {
      select.from(CodeRylx as cr).where.eq(cr.code, code).and.eq(cr.name, name).and.eq(cr.deleteFlag, deleteFlag).and.eq(cr.ord, ord).and.eq(cr.remark, remark).and.eq(cr.strtype, strtype)
    }.map(CodeRylx(cr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeRylx] = {
    withSQL(select.from(CodeRylx as cr)).map(CodeRylx(cr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeRylx as cr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeRylx] = {
    withSQL {
      select.from(CodeRylx as cr).where.append(where)
    }.map(CodeRylx(cr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeRylx] = {
    withSQL {
      select.from(CodeRylx as cr).where.append(where)
    }.map(CodeRylx(cr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeRylx as cr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None,
    strtype: Option[String] = None)(implicit session: DBSession = autoSession): CodeRylx = {
    withSQL {
      insert.into(CodeRylx).columns(
        column.code,
        column.name,
        column.deleteFlag,
        column.ord,
        column.remark,
        column.strtype
      ).values(
        code,
        name,
        deleteFlag,
        ord,
        remark,
        strtype
      )
    }.update.apply()

    CodeRylx(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark,
      strtype = strtype)
  }

  def save(entity: CodeRylx)(implicit session: DBSession = autoSession): CodeRylx = {
    withSQL {
      update(CodeRylx).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark,
        column.strtype -> entity.strtype
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark).and.eq(column.strtype, entity.strtype)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeRylx)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeRylx).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark).and.eq(column.strtype, entity.strtype) }.update.apply()
  }

}
