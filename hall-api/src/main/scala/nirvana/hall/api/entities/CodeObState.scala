package nirvana.hall.api.entities

import scalikejdbc._

case class CodeObState(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeObState.autoSession): CodeObState = CodeObState.save(this)(session)

  def destroy()(implicit session: DBSession = CodeObState.autoSession): Unit = CodeObState.destroy(this)(session)

}


object CodeObState extends SQLSyntaxSupport[CodeObState] {

  override val tableName = "CODE_OB_STATE"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cos: SyntaxProvider[CodeObState])(rs: WrappedResultSet): CodeObState = apply(cos.resultName)(rs)
  def apply(cos: ResultName[CodeObState])(rs: WrappedResultSet): CodeObState = new CodeObState(
    code = rs.get(cos.code),
    name = rs.get(cos.name),
    deleteFlag = rs.get(cos.deleteFlag),
    ord = rs.get(cos.ord),
    remark = rs.get(cos.remark)
  )

  val cos = CodeObState.syntax("cos")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeObState] = {
    withSQL {
      select.from(CodeObState as cos).where.eq(cos.code, code).and.eq(cos.name, name).and.eq(cos.deleteFlag, deleteFlag).and.eq(cos.ord, ord).and.eq(cos.remark, remark)
    }.map(CodeObState(cos.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeObState] = {
    withSQL(select.from(CodeObState as cos)).map(CodeObState(cos.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeObState as cos)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeObState] = {
    withSQL {
      select.from(CodeObState as cos).where.append(where)
    }.map(CodeObState(cos.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeObState] = {
    withSQL {
      select.from(CodeObState as cos).where.append(where)
    }.map(CodeObState(cos.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeObState as cos).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeObState = {
    withSQL {
      insert.into(CodeObState).columns(
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

    CodeObState(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeObState)(implicit session: DBSession = autoSession): CodeObState = {
    withSQL {
      update(CodeObState).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeObState)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeObState).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
