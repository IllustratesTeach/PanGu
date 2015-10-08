package nirvana.hall.api.entities

import scalikejdbc._

case class CodeBj(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeBj.autoSession): CodeBj = CodeBj.save(this)(session)

  def destroy()(implicit session: DBSession = CodeBj.autoSession): Unit = CodeBj.destroy(this)(session)

}


object CodeBj extends SQLSyntaxSupport[CodeBj] {

  override val tableName = "CODE_BJ"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cb: SyntaxProvider[CodeBj])(rs: WrappedResultSet): CodeBj = apply(cb.resultName)(rs)
  def apply(cb: ResultName[CodeBj])(rs: WrappedResultSet): CodeBj = new CodeBj(
    code = rs.get(cb.code),
    name = rs.get(cb.name),
    deleteFlag = rs.get(cb.deleteFlag),
    ord = rs.get(cb.ord),
    remark = rs.get(cb.remark)
  )

  val cb = CodeBj.syntax("cb")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeBj] = {
    withSQL {
      select.from(CodeBj as cb).where.eq(cb.code, code).and.eq(cb.name, name).and.eq(cb.deleteFlag, deleteFlag).and.eq(cb.ord, ord).and.eq(cb.remark, remark)
    }.map(CodeBj(cb.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeBj] = {
    withSQL(select.from(CodeBj as cb)).map(CodeBj(cb.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeBj as cb)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeBj] = {
    withSQL {
      select.from(CodeBj as cb).where.append(where)
    }.map(CodeBj(cb.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeBj] = {
    withSQL {
      select.from(CodeBj as cb).where.append(where)
    }.map(CodeBj(cb.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeBj as cb).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeBj = {
    withSQL {
      insert.into(CodeBj).columns(
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

    CodeBj(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeBj)(implicit session: DBSession = autoSession): CodeBj = {
    withSQL {
      update(CodeBj).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeBj)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeBj).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
