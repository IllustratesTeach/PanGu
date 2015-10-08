package nirvana.hall.api.entities

import scalikejdbc._

case class CodeDdqjddgj(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeDdqjddgj.autoSession): CodeDdqjddgj = CodeDdqjddgj.save(this)(session)

  def destroy()(implicit session: DBSession = CodeDdqjddgj.autoSession): Unit = CodeDdqjddgj.destroy(this)(session)

}


object CodeDdqjddgj extends SQLSyntaxSupport[CodeDdqjddgj] {

  override val tableName = "CODE_DDQJDDGJ"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cd: SyntaxProvider[CodeDdqjddgj])(rs: WrappedResultSet): CodeDdqjddgj = apply(cd.resultName)(rs)
  def apply(cd: ResultName[CodeDdqjddgj])(rs: WrappedResultSet): CodeDdqjddgj = new CodeDdqjddgj(
    code = rs.get(cd.code),
    name = rs.get(cd.name),
    deleteFlag = rs.get(cd.deleteFlag),
    ord = rs.get(cd.ord),
    remark = rs.get(cd.remark)
  )

  val cd = CodeDdqjddgj.syntax("cd")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeDdqjddgj] = {
    withSQL {
      select.from(CodeDdqjddgj as cd).where.eq(cd.code, code).and.eq(cd.name, name).and.eq(cd.deleteFlag, deleteFlag).and.eq(cd.ord, ord).and.eq(cd.remark, remark)
    }.map(CodeDdqjddgj(cd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeDdqjddgj] = {
    withSQL(select.from(CodeDdqjddgj as cd)).map(CodeDdqjddgj(cd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeDdqjddgj as cd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeDdqjddgj] = {
    withSQL {
      select.from(CodeDdqjddgj as cd).where.append(where)
    }.map(CodeDdqjddgj(cd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeDdqjddgj] = {
    withSQL {
      select.from(CodeDdqjddgj as cd).where.append(where)
    }.map(CodeDdqjddgj(cd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeDdqjddgj as cd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeDdqjddgj = {
    withSQL {
      insert.into(CodeDdqjddgj).columns(
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

    CodeDdqjddgj(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeDdqjddgj)(implicit session: DBSession = autoSession): CodeDdqjddgj = {
    withSQL {
      update(CodeDdqjddgj).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeDdqjddgj)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeDdqjddgj).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
