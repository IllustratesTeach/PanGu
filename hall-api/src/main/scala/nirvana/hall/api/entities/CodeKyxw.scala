package nirvana.hall.api.entities

import scalikejdbc._

case class CodeKyxw(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeKyxw.autoSession): CodeKyxw = CodeKyxw.save(this)(session)

  def destroy()(implicit session: DBSession = CodeKyxw.autoSession): Unit = CodeKyxw.destroy(this)(session)

}


object CodeKyxw extends SQLSyntaxSupport[CodeKyxw] {

  override val tableName = "CODE_KYXW"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(ck: SyntaxProvider[CodeKyxw])(rs: WrappedResultSet): CodeKyxw = apply(ck.resultName)(rs)
  def apply(ck: ResultName[CodeKyxw])(rs: WrappedResultSet): CodeKyxw = new CodeKyxw(
    code = rs.get(ck.code),
    name = rs.get(ck.name),
    deleteFlag = rs.get(ck.deleteFlag),
    ord = rs.get(ck.ord),
    remark = rs.get(ck.remark)
  )

  val ck = CodeKyxw.syntax("ck")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeKyxw] = {
    withSQL {
      select.from(CodeKyxw as ck).where.eq(ck.code, code).and.eq(ck.name, name).and.eq(ck.deleteFlag, deleteFlag).and.eq(ck.ord, ord).and.eq(ck.remark, remark)
    }.map(CodeKyxw(ck.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeKyxw] = {
    withSQL(select.from(CodeKyxw as ck)).map(CodeKyxw(ck.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeKyxw as ck)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeKyxw] = {
    withSQL {
      select.from(CodeKyxw as ck).where.append(where)
    }.map(CodeKyxw(ck.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeKyxw] = {
    withSQL {
      select.from(CodeKyxw as ck).where.append(where)
    }.map(CodeKyxw(ck.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeKyxw as ck).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeKyxw = {
    withSQL {
      insert.into(CodeKyxw).columns(
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

    CodeKyxw(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeKyxw)(implicit session: DBSession = autoSession): CodeKyxw = {
    withSQL {
      update(CodeKyxw).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeKyxw)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeKyxw).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
