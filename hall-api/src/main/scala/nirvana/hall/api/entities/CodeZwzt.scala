package nirvana.hall.api.entities

import scalikejdbc._

case class CodeZwzt(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeZwzt.autoSession): CodeZwzt = CodeZwzt.save(this)(session)

  def destroy()(implicit session: DBSession = CodeZwzt.autoSession): Unit = CodeZwzt.destroy(this)(session)

}


object CodeZwzt extends SQLSyntaxSupport[CodeZwzt] {

  override val tableName = "CODE_ZWZT"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cz: SyntaxProvider[CodeZwzt])(rs: WrappedResultSet): CodeZwzt = apply(cz.resultName)(rs)
  def apply(cz: ResultName[CodeZwzt])(rs: WrappedResultSet): CodeZwzt = new CodeZwzt(
    code = rs.get(cz.code),
    name = rs.get(cz.name),
    deleteFlag = rs.get(cz.deleteFlag),
    ord = rs.get(cz.ord),
    remark = rs.get(cz.remark)
  )

  val cz = CodeZwzt.syntax("cz")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeZwzt] = {
    withSQL {
      select.from(CodeZwzt as cz).where.eq(cz.code, code).and.eq(cz.name, name).and.eq(cz.deleteFlag, deleteFlag).and.eq(cz.ord, ord).and.eq(cz.remark, remark)
    }.map(CodeZwzt(cz.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeZwzt] = {
    withSQL(select.from(CodeZwzt as cz)).map(CodeZwzt(cz.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeZwzt as cz)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeZwzt] = {
    withSQL {
      select.from(CodeZwzt as cz).where.append(where)
    }.map(CodeZwzt(cz.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeZwzt] = {
    withSQL {
      select.from(CodeZwzt as cz).where.append(where)
    }.map(CodeZwzt(cz.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeZwzt as cz).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeZwzt = {
    withSQL {
      insert.into(CodeZwzt).columns(
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

    CodeZwzt(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeZwzt)(implicit session: DBSession = autoSession): CodeZwzt = {
    withSQL {
      update(CodeZwzt).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeZwzt)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeZwzt).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
