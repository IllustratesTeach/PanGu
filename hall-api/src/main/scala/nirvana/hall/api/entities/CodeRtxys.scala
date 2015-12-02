package nirvana.hall.api.entities

import scalikejdbc._

case class CodeRtxys(
  code: String,
  name: Option[String] = None,
  deleteFlag: Option[String] = None,
  ord: Option[Long] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = CodeRtxys.autoSession): CodeRtxys = CodeRtxys.save(this)(session)

  def destroy()(implicit session: DBSession = CodeRtxys.autoSession): Unit = CodeRtxys.destroy(this)(session)

}


object CodeRtxys extends SQLSyntaxSupport[CodeRtxys] {

  override val tableName = "CODE_RTXYS"

  override val columns = Seq("CODE", "NAME", "DELETE_FLAG", "ORD", "REMARK")

  def apply(cr: SyntaxProvider[CodeRtxys])(rs: WrappedResultSet): CodeRtxys = apply(cr.resultName)(rs)
  def apply(cr: ResultName[CodeRtxys])(rs: WrappedResultSet): CodeRtxys = new CodeRtxys(
    code = rs.get(cr.code),
    name = rs.get(cr.name),
    deleteFlag = rs.get(cr.deleteFlag),
    ord = rs.get(cr.ord),
    remark = rs.get(cr.remark)
  )

  val cr = CodeRtxys.syntax("cr")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String)(implicit session: DBSession = autoSession): Option[CodeRtxys] = {
    withSQL {
      select.from(CodeRtxys as cr).where.eq(cr.code, code)
    }.map(CodeRtxys(cr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodeRtxys] = {
    withSQL(select.from(CodeRtxys as cr)).map(CodeRtxys(cr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodeRtxys as cr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodeRtxys] = {
    withSQL {
      select.from(CodeRtxys as cr).where.append(where)
    }.map(CodeRtxys(cr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodeRtxys] = {
    withSQL {
      select.from(CodeRtxys as cr).where.append(where)
    }.map(CodeRtxys(cr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodeRtxys as cr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    deleteFlag: Option[String] = None,
    ord: Option[Long] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): CodeRtxys = {
    withSQL {
      insert.into(CodeRtxys).columns(
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

    CodeRtxys(
      code = code,
      name = name,
      deleteFlag = deleteFlag,
      ord = ord,
      remark = remark)
  }

  def save(entity: CodeRtxys)(implicit session: DBSession = autoSession): CodeRtxys = {
    withSQL {
      update(CodeRtxys).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.deleteFlag -> entity.deleteFlag,
        column.ord -> entity.ord,
        column.remark -> entity.remark
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: CodeRtxys)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodeRtxys).where.eq(column.code, entity.code) }.update.apply()
  }

}
