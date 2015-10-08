package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysDictList(
  pkId: String,
  dictName: Option[String] = None,
  dictTableName: Option[String] = None,
  createDatetime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = SysDictList.autoSession): SysDictList = SysDictList.save(this)(session)

  def destroy()(implicit session: DBSession = SysDictList.autoSession): Unit = SysDictList.destroy(this)(session)

}


object SysDictList extends SQLSyntaxSupport[SysDictList] {

  override val tableName = "SYS_DICT_LIST"

  override val columns = Seq("PK_ID", "DICT_NAME", "DICT_TABLE_NAME", "CREATE_DATETIME")

  def apply(sdl: SyntaxProvider[SysDictList])(rs: WrappedResultSet): SysDictList = apply(sdl.resultName)(rs)
  def apply(sdl: ResultName[SysDictList])(rs: WrappedResultSet): SysDictList = new SysDictList(
    pkId = rs.get(sdl.pkId),
    dictName = rs.get(sdl.dictName),
    dictTableName = rs.get(sdl.dictTableName),
    createDatetime = rs.get(sdl.createDatetime)
  )

  val sdl = SysDictList.syntax("sdl")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, dictName: Option[String], dictTableName: Option[String], createDatetime: Option[DateTime])(implicit session: DBSession = autoSession): Option[SysDictList] = {
    withSQL {
      select.from(SysDictList as sdl).where.eq(sdl.pkId, pkId).and.eq(sdl.dictName, dictName).and.eq(sdl.dictTableName, dictTableName).and.eq(sdl.createDatetime, createDatetime)
    }.map(SysDictList(sdl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysDictList] = {
    withSQL(select.from(SysDictList as sdl)).map(SysDictList(sdl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysDictList as sdl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysDictList] = {
    withSQL {
      select.from(SysDictList as sdl).where.append(where)
    }.map(SysDictList(sdl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysDictList] = {
    withSQL {
      select.from(SysDictList as sdl).where.append(where)
    }.map(SysDictList(sdl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysDictList as sdl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    dictName: Option[String] = None,
    dictTableName: Option[String] = None,
    createDatetime: Option[DateTime] = None)(implicit session: DBSession = autoSession): SysDictList = {
    withSQL {
      insert.into(SysDictList).columns(
        column.pkId,
        column.dictName,
        column.dictTableName,
        column.createDatetime
      ).values(
        pkId,
        dictName,
        dictTableName,
        createDatetime
      )
    }.update.apply()

    SysDictList(
      pkId = pkId,
      dictName = dictName,
      dictTableName = dictTableName,
      createDatetime = createDatetime)
  }

  def save(entity: SysDictList)(implicit session: DBSession = autoSession): SysDictList = {
    withSQL {
      update(SysDictList).set(
        column.pkId -> entity.pkId,
        column.dictName -> entity.dictName,
        column.dictTableName -> entity.dictTableName,
        column.createDatetime -> entity.createDatetime
      ).where.eq(column.pkId, entity.pkId).and.eq(column.dictName, entity.dictName).and.eq(column.dictTableName, entity.dictTableName).and.eq(column.createDatetime, entity.createDatetime)
    }.update.apply()
    entity
  }

  def destroy(entity: SysDictList)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysDictList).where.eq(column.pkId, entity.pkId).and.eq(column.dictName, entity.dictName).and.eq(column.dictTableName, entity.dictTableName).and.eq(column.createDatetime, entity.createDatetime) }.update.apply()
  }

}
