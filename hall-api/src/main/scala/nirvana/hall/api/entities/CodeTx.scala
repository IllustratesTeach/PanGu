package nirvana.hall.api.entities

import scalikejdbc._

case class CodeTx(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeTx.autoSession): CodeTx = CodeTx.save(this)(session)

  def destroy()(implicit session: DBSession = CodeTx.autoSession): Unit = CodeTx.destroy(this)(session)

}


object CodeTx extends SQLSyntaxSupport[CodeTx] {

  override val tableName = "CODE_TX"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(ct: SyntaxProvider[CodeTx])(rs: WrappedResultSet): CodeTx = apply(ct.resultName)(rs)
  def apply(ct: ResultName[CodeTx])(rs: WrappedResultSet): CodeTx = new CodeTx(
    code = rs.get(ct.code),
    name = rs.get(ct.name),
    deleteFlag = rs.get(ct.deleteFlag),
    ord = rs.get(ct.ord),
    remark = rs.get(ct.remark)
  )

  val ct = CodeTx.syntax("ct")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeTx] = {
    withSQL {
      select.from(CodeTx as ct).where.eq(ct.code, code).and.eq(ct.name, name).and.eq(ct.deleteFlag, deleteFlag).and.eq(ct.ord, ord).and.eq(ct.remark, remark)
    }.map(CodeTx(ct.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeTx] = {
    withSQL(select.from(CodeTx as ct)).map(CodeTx(ct.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeTx as ct)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeTx] = {
    withSQL {
      select.from(CodeTx as ct).where.append(where)
    }.map(CodeTx(ct.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeTx] = {
    withSQL {
      select.from(CodeTx as ct).where.append(where)
    }.map(CodeTx(ct.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeTx as ct).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeTx = {
    withSQL {
      insert.into(CodeTx).columns(
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

    CodeTx(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeTx)(implicit session: DBSession = autoSession): CodeTx = {
    withSQL {
      update(CodeTx).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeTx)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeTx).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
