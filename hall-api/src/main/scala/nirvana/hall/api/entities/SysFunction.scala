package nirvana.hall.api.entities

import scalikejdbc._

case class SysFunction(
  functionId: String,
  name: Option[String] = None,
  remark: Option[String] = None) {

  def save()(implicit session: DBSession = SysFunction.autoSession): SysFunction = SysFunction.save(this)(session)

  def destroy()(implicit session: DBSession = SysFunction.autoSession): Unit = SysFunction.destroy(this)(session)

}


object SysFunction extends SQLSyntaxSupport[SysFunction] {

  override val tableName = "SYS_FUNCTION"

  override val columns = Seq("FUNCTION_ID", "NAME", "REMARK")

  def apply(sf: SyntaxProvider[SysFunction])(rs: WrappedResultSet): SysFunction = apply(sf.resultName)(rs)
  def apply(sf: ResultName[SysFunction])(rs: WrappedResultSet): SysFunction = new SysFunction(
    functionId = rs.get(sf.functionId),
    name = rs.get(sf.name),
    remark = rs.get(sf.remark)
  )

  val sf = SysFunction.syntax("sf")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(functionId: String, name: Option[String], remark: Option[String])(implicit session: DBSession = autoSession): Option[SysFunction] = {
    withSQL {
      select.from(SysFunction as sf).where.eq(sf.functionId, functionId).and.eq(sf.name, name).and.eq(sf.remark, remark)
    }.map(SysFunction(sf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysFunction] = {
    withSQL(select.from(SysFunction as sf)).map(SysFunction(sf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysFunction as sf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysFunction] = {
    withSQL {
      select.from(SysFunction as sf).where.append(where)
    }.map(SysFunction(sf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysFunction] = {
    withSQL {
      select.from(SysFunction as sf).where.append(where)
    }.map(SysFunction(sf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysFunction as sf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    functionId: String,
    name: Option[String] = None,
    remark: Option[String] = None)(implicit session: DBSession = autoSession): SysFunction = {
    withSQL {
      insert.into(SysFunction).columns(
        column.functionId,
        column.name,
        column.remark
      ).values(
        functionId,
        name,
        remark
      )
    }.update.apply()

    SysFunction(
      functionId = functionId,
      name = name,
      remark = remark)
  }

  def save(entity: SysFunction)(implicit session: DBSession = autoSession): SysFunction = {
    withSQL {
      update(SysFunction).set(
        column.functionId -> entity.functionId,
        column.name -> entity.name,
        column.remark -> entity.remark
      ).where.eq(column.functionId, entity.functionId).and.eq(column.name, entity.name).and.eq(column.remark, entity.remark)
    }.update.apply()
    entity
  }

  def destroy(entity: SysFunction)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysFunction).where.eq(column.functionId, entity.functionId).and.eq(column.name, entity.name).and.eq(column.remark, entity.remark) }.update.apply()
  }

}
