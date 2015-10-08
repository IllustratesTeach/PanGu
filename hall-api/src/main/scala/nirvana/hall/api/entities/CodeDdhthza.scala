package nirvana.hall.api.entities

import scalikejdbc._

case class CodeDdhthza(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeDdhthza.autoSession): CodeDdhthza = CodeDdhthza.save(this)(session)

  def destroy()(implicit session: DBSession = CodeDdhthza.autoSession): Unit = CodeDdhthza.destroy(this)(session)

}


object CodeDdhthza extends SQLSyntaxSupport[CodeDdhthza] {

  override val tableName = "CODE_DDHTHZA"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cd: SyntaxProvider[CodeDdhthza])(rs: WrappedResultSet): CodeDdhthza = apply(cd.resultName)(rs)
  def apply(cd: ResultName[CodeDdhthza])(rs: WrappedResultSet): CodeDdhthza = new CodeDdhthza(
    code = rs.get(cd.code),
    name = rs.get(cd.name),
    deleteFlag = rs.get(cd.deleteFlag),
    ord = rs.get(cd.ord),
    remark = rs.get(cd.remark)
  )

  val cd = CodeDdhthza.syntax("cd")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeDdhthza] = {
    withSQL {
      select.from(CodeDdhthza as cd).where.eq(cd.code, code).and.eq(cd.name, name).and.eq(cd.deleteFlag, deleteFlag).and.eq(cd.ord, ord).and.eq(cd.remark, remark)
    }.map(CodeDdhthza(cd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeDdhthza] = {
    withSQL(select.from(CodeDdhthza as cd)).map(CodeDdhthza(cd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeDdhthza as cd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeDdhthza] = {
    withSQL {
      select.from(CodeDdhthza as cd).where.append(where)
    }.map(CodeDdhthza(cd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeDdhthza] = {
    withSQL {
      select.from(CodeDdhthza as cd).where.append(where)
    }.map(CodeDdhthza(cd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeDdhthza as cd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeDdhthza = {
    withSQL {
      insert.into(CodeDdhthza).columns(
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

    CodeDdhthza(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeDdhthza)(implicit session: DBSession = autoSession): CodeDdhthza = {
    withSQL {
      update(CodeDdhthza).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeDdhthza)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeDdhthza).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
