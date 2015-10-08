package nirvana.hall.api.entities

import scalikejdbc._

case class CodeHpzl(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeHpzl.autoSession): CodeHpzl = CodeHpzl.save(this)(session)

  def destroy()(implicit session: DBSession = CodeHpzl.autoSession): Unit = CodeHpzl.destroy(this)(session)

}


object CodeHpzl extends SQLSyntaxSupport[CodeHpzl] {

  override val tableName = "CODE_HPZL"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(ch: SyntaxProvider[CodeHpzl])(rs: WrappedResultSet): CodeHpzl = apply(ch.resultName)(rs)
  def apply(ch: ResultName[CodeHpzl])(rs: WrappedResultSet): CodeHpzl = new CodeHpzl(
    code = rs.get(ch.code),
    name = rs.get(ch.name),
    deleteFlag = rs.get(ch.deleteFlag),
    ord = rs.get(ch.ord),
    remark = rs.get(ch.remark)
  )

  val ch = CodeHpzl.syntax("ch")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String, name: Option[String], deleteFlag: Option[String], ord: Option[Long], remark: Option[String])(implicit session: DBSession = autoSession): Option[CodeHpzl] = {
    withSQL {
      select.from(CodeHpzl as ch).where.eq(ch.code, code).and.eq(ch.name, name).and.eq(ch.deleteFlag, deleteFlag).and.eq(ch.ord, ord).and.eq(ch.remark, remark)
    }.map(CodeHpzl(ch.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeHpzl] = {
    withSQL(select.from(CodeHpzl as ch)).map(CodeHpzl(ch.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeHpzl as ch)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeHpzl] = {
    withSQL {
      select.from(CodeHpzl as ch).where.append(where)
    }.map(CodeHpzl(ch.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeHpzl] = {
    withSQL {
      select.from(CodeHpzl as ch).where.append(where)
    }.map(CodeHpzl(ch.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeHpzl as ch).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeHpzl = {
    withSQL {
      insert.into(CodeHpzl).columns(
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

    CodeHpzl(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeHpzl)(implicit session: DBSession = autoSession): CodeHpzl = {
    withSQL {
      update(CodeHpzl).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeHpzl)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeHpzl).where.eq(column.code, entity.code).and.eq(column.name, entity.name).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.ord, entity.ord).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
