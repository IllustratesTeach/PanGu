package nirvana.hall.api.entities

import scalikejdbc._

case class CodeGjzacyy(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeGjzacyy.autoSession): CodeGjzacyy = CodeGjzacyy.save(this)(session)

  def destroy()(implicit session: DBSession = CodeGjzacyy.autoSession): Unit = CodeGjzacyy.destroy(this)(session)

}


object CodeGjzacyy extends SQLSyntaxSupport[CodeGjzacyy] {

  override val tableName = "CODE_GJZACYY"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cg: SyntaxProvider[CodeGjzacyy])(rs: WrappedResultSet): CodeGjzacyy = apply(cg.resultName)(rs)
  def apply(cg: ResultName[CodeGjzacyy])(rs: WrappedResultSet): CodeGjzacyy = new CodeGjzacyy(
    code = rs.get(cg.code),
    name = rs.get(cg.name),
    deleteFlag = rs.get(cg.deleteFlag),
    ord = rs.get(cg.ord),
    remark = rs.get(cg.remark)
  )

  val cg = CodeGjzacyy.syntax("cg")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeGjzacyy] = {
    withSQL {
      select.from(CodeGjzacyy as cg).where.eq(cg.code, code).and.eq(cg.name, name).and.eq(cg.deleteFlag, deleteFlag).and.eq(cg.ord, ord).and.eq(cg.remark, remark)
    }.map(CodeGjzacyy(cg.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeGjzacyy] = {
    withSQL(select.from(CodeGjzacyy as cg)).map(CodeGjzacyy(cg.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeGjzacyy as cg)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeGjzacyy] = {
    withSQL {
      select.from(CodeGjzacyy as cg).where.append(where)
    }.map(CodeGjzacyy(cg.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeGjzacyy] = {
    withSQL {
      select.from(CodeGjzacyy as cg).where.append(where)
    }.map(CodeGjzacyy(cg.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeGjzacyy as cg).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeGjzacyy = {
    withSQL {
      insert.into(CodeGjzacyy).columns(
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

    CodeGjzacyy(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeGjzacyy)(implicit session: DBSession = autoSession): CodeGjzacyy = {
    withSQL {
      update(CodeGjzacyy).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeGjzacyy)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeGjzacyy).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
