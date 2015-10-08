package nirvana.hall.api.entities

import scalikejdbc._

case class CodeKsgjqk(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeKsgjqk.autoSession): CodeKsgjqk = CodeKsgjqk.save(this)(session)

  def destroy()(implicit session: DBSession = CodeKsgjqk.autoSession): Unit = CodeKsgjqk.destroy(this)(session)

}


object CodeKsgjqk extends SQLSyntaxSupport[CodeKsgjqk] {

  override val tableName = "CODE_KSGJQK"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(ck: SyntaxProvider[CodeKsgjqk])(rs: WrappedResultSet): CodeKsgjqk = apply(ck.resultName)(rs)
  def apply(ck: ResultName[CodeKsgjqk])(rs: WrappedResultSet): CodeKsgjqk = new CodeKsgjqk(
    code = rs.get(ck.code),
    name = rs.get(ck.name),
    deleteFlag = rs.get(ck.deleteFlag),
    ord = rs.get(ck.ord),
    remark = rs.get(ck.remark)
  )

  val ck = CodeKsgjqk.syntax("ck")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeKsgjqk] = {
    withSQL {
      select.from(CodeKsgjqk as ck).where.eq(ck.code, code).and.eq(ck.name, name).and.eq(ck.deleteFlag, deleteFlag).and.eq(ck.ord, ord).and.eq(ck.remark, remark)
    }.map(CodeKsgjqk(ck.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeKsgjqk] = {
    withSQL(select.from(CodeKsgjqk as ck)).map(CodeKsgjqk(ck.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeKsgjqk as ck)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeKsgjqk] = {
    withSQL {
      select.from(CodeKsgjqk as ck).where.append(where)
    }.map(CodeKsgjqk(ck.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeKsgjqk] = {
    withSQL {
      select.from(CodeKsgjqk as ck).where.append(where)
    }.map(CodeKsgjqk(ck.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeKsgjqk as ck).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeKsgjqk = {
    withSQL {
      insert.into(CodeKsgjqk).columns(
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

    CodeKsgjqk(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeKsgjqk)(implicit session: DBSession = autoSession): CodeKsgjqk = {
    withSQL {
      update(CodeKsgjqk).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeKsgjqk)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeKsgjqk).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
