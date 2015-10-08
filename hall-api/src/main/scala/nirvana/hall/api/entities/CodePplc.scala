package nirvana.hall.api.entities

import scalikejdbc._

case class CodePplc(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodePplc.autoSession): CodePplc = CodePplc.save(this)(session)

  def destroy()(implicit session: DBSession = CodePplc.autoSession): Unit = CodePplc.destroy(this)(session)

}


object CodePplc extends SQLSyntaxSupport[CodePplc] {

  override val tableName = "CODE_PPLC"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cp: SyntaxProvider[CodePplc])(rs: WrappedResultSet): CodePplc = apply(cp.resultName)(rs)
  def apply(cp: ResultName[CodePplc])(rs: WrappedResultSet): CodePplc = new CodePplc(
    code = rs.get(cp.code),
    name = rs.get(cp.name),
    deleteFlag = rs.get(cp.deleteFlag),
    ord = rs.get(cp.ord),
    remark = rs.get(cp.remark)
  )

  val cp = CodePplc.syntax("cp")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodePplc] = {
    withSQL {
      select.from(CodePplc as cp).where.eq(cp.code, code).and.eq(cp.name, name).and.eq(cp.deleteFlag, deleteFlag).and.eq(cp.ord, ord).and.eq(cp.remark, remark)
    }.map(CodePplc(cp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodePplc] = {
    withSQL(select.from(CodePplc as cp)).map(CodePplc(cp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodePplc as cp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodePplc] = {
    withSQL {
      select.from(CodePplc as cp).where.append(where)
    }.map(CodePplc(cp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodePplc] = {
    withSQL {
      select.from(CodePplc as cp).where.append(where)
    }.map(CodePplc(cp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodePplc as cp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodePplc = {
    withSQL {
      insert.into(CodePplc).columns(
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

    CodePplc(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodePplc)(implicit session: DBSession = autoSession): CodePplc = {
    withSQL {
      update(CodePplc).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodePplc)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodePplc).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
