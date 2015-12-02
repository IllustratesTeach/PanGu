package nirvana.hall.api.entities

import scalikejdbc._

case class CodeMz(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeMz.autoSession): CodeMz = CodeMz.save(this)(session)

  def destroy()(implicit session: DBSession = CodeMz.autoSession): Unit = CodeMz.destroy(this)(session)

}


object CodeMz extends SQLSyntaxSupport[CodeMz] {

  override val tableName = "CODE_MZ"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cm: SyntaxProvider[CodeMz])(rs: WrappedResultSet): CodeMz = apply(cm.resultName)(rs)
  def apply(cm: ResultName[CodeMz])(rs: WrappedResultSet): CodeMz = new CodeMz(
    code = rs.get(cm.code),
    name = rs.get(cm.name),
    deleteFlag = rs.get(cm.deleteFlag),
    ord = rs.get(cm.ord),
    remark = rs.get(cm.remark)
  )

  val cm = CodeMz.syntax("cm")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String)(implicit session: DBSession = autoSession): Option[CodeMz] = {
    withSQL {
      select.from(CodeMz as cm).where.eq(cm.code, code)
    }.map(CodeMz(cm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeMz] = {
    withSQL(select.from(CodeMz as cm)).map(CodeMz(cm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeMz as cm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeMz] = {
    withSQL {
      select.from(CodeMz as cm).where.append(where)
    }.map(CodeMz(cm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeMz] = {
    withSQL {
      select.from(CodeMz as cm).where.append(where)
    }.map(CodeMz(cm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeMz as cm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeMz = {
    withSQL {
      insert.into(CodeMz).columns(
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

    CodeMz(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeMz)(implicit session: DBSession = autoSession): CodeMz = {
    withSQL {
      update(CodeMz).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeMz)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeMz).where.eq(column.code, entity.code) }.update.apply()
  }

}
