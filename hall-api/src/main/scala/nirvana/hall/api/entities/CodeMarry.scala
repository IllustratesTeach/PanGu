package nirvana.hall.api.entities

import scalikejdbc._

case class CodeMarry(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeMarry.autoSession): CodeMarry = CodeMarry.save(this)(session)

  def destroy()(implicit session: DBSession = CodeMarry.autoSession): Unit = CodeMarry.destroy(this)(session)

}


object CodeMarry extends SQLSyntaxSupport[CodeMarry] {

  override val tableName = "CODE_MARRY"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cm: SyntaxProvider[CodeMarry])(rs: WrappedResultSet): CodeMarry = apply(cm.resultName)(rs)
  def apply(cm: ResultName[CodeMarry])(rs: WrappedResultSet): CodeMarry = new CodeMarry(
    code = rs.get(cm.code),
    name = rs.get(cm.name),
    deleteFlag = rs.get(cm.deleteFlag),
    ord = rs.get(cm.ord),
    remark = rs.get(cm.remark)
  )

  val cm = CodeMarry.syntax("cm")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String)(implicit session: DBSession = autoSession): Option[CodeMarry] = {
    withSQL {
      select.from(CodeMarry as cm).where.eq(cm.code, code)
    }.map(CodeMarry(cm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeMarry] = {
    withSQL(select.from(CodeMarry as cm)).map(CodeMarry(cm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeMarry as cm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeMarry] = {
    withSQL {
      select.from(CodeMarry as cm).where.append(where)
    }.map(CodeMarry(cm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeMarry] = {
    withSQL {
      select.from(CodeMarry as cm).where.append(where)
    }.map(CodeMarry(cm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeMarry as cm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeMarry = {
    withSQL {
      insert.into(CodeMarry).columns(
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

    CodeMarry(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeMarry)(implicit session: DBSession = autoSession): CodeMarry = {
    withSQL {
      update(CodeMarry).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeMarry)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeMarry).where.eq(column.code, entity.code) }.update.apply()
  }

}
