package nirvana.hall.api.entities

import scalikejdbc._

case class CodeDqhtlxcff(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeDqhtlxcff.autoSession): CodeDqhtlxcff = CodeDqhtlxcff.save(this)(session)

  def destroy()(implicit session: DBSession = CodeDqhtlxcff.autoSession): Unit = CodeDqhtlxcff.destroy(this)(session)

}


object CodeDqhtlxcff extends SQLSyntaxSupport[CodeDqhtlxcff] {

  override val tableName = "CODE_DQHTLXCFF"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cd: SyntaxProvider[CodeDqhtlxcff])(rs: WrappedResultSet): CodeDqhtlxcff = apply(cd.resultName)(rs)
  def apply(cd: ResultName[CodeDqhtlxcff])(rs: WrappedResultSet): CodeDqhtlxcff = new CodeDqhtlxcff(
    code = rs.get(cd.code),
    name = rs.get(cd.name),
    deleteFlag = rs.get(cd.deleteFlag),
    ord = rs.get(cd.ord),
    remark = rs.get(cd.remark)
  )

  val cd = CodeDqhtlxcff.syntax("cd")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeDqhtlxcff] = {
    withSQL {
      select.from(CodeDqhtlxcff as cd).where.eq(cd.code, code).and.eq(cd.name, name).and.eq(cd.deleteFlag, deleteFlag).and.eq(cd.ord, ord).and.eq(cd.remark, remark)
    }.map(CodeDqhtlxcff(cd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeDqhtlxcff] = {
    withSQL(select.from(CodeDqhtlxcff as cd)).map(CodeDqhtlxcff(cd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeDqhtlxcff as cd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeDqhtlxcff] = {
    withSQL {
      select.from(CodeDqhtlxcff as cd).where.append(where)
    }.map(CodeDqhtlxcff(cd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeDqhtlxcff] = {
    withSQL {
      select.from(CodeDqhtlxcff as cd).where.append(where)
    }.map(CodeDqhtlxcff(cd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeDqhtlxcff as cd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeDqhtlxcff = {
    withSQL {
      insert.into(CodeDqhtlxcff).columns(
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

    CodeDqhtlxcff(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeDqhtlxcff)(implicit session: DBSession = autoSession): CodeDqhtlxcff = {
    withSQL {
      update(CodeDqhtlxcff).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeDqhtlxcff)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeDqhtlxcff).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
