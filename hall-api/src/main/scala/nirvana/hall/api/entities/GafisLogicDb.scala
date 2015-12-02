package nirvana.hall.api.entities

import scalikejdbc._

case class GafisLogicDb(
  pkId: String,
  logicCode: Option[String] = None,
  logicName: Option[String] = None,
  logicCategory: Option[String] = None,
  logicDeltag: Option[String] = None,
  logicRemark: Option[String] = None) {

  def save()(implicit session: DBSession = GafisLogicDb.autoSession): GafisLogicDb = GafisLogicDb.save(this)(session)

  def destroy()(implicit session: DBSession = GafisLogicDb.autoSession): Unit = GafisLogicDb.destroy(this)(session)

}


object GafisLogicDb extends SQLSyntaxSupport[GafisLogicDb] {

  override val tableName = "GAFIS_LOGIC_DB"

  override val columns = Seq("PK_ID", "LOGIC_CODE", "LOGIC_NAME", "LOGIC_CATEGORY", "LOGIC_DELTAG", "LOGIC_REMARK")

  def apply(gld: SyntaxProvider[GafisLogicDb])(rs: WrappedResultSet): GafisLogicDb = apply(gld.resultName)(rs)
  def apply(gld: ResultName[GafisLogicDb])(rs: WrappedResultSet): GafisLogicDb = new GafisLogicDb(
    pkId = rs.get(gld.pkId),
    logicCode = rs.get(gld.logicCode),
    logicName = rs.get(gld.logicName),
    logicCategory = rs.get(gld.logicCategory),
    logicDeltag = rs.get(gld.logicDeltag),
    logicRemark = rs.get(gld.logicRemark)
  )

  val gld = GafisLogicDb.syntax("gld")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[GafisLogicDb] = {
    withSQL {
      select.from(GafisLogicDb as gld).where.eq(gld.pkId, pkId)
    }.map(GafisLogicDb(gld.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisLogicDb] = {
    withSQL(select.from(GafisLogicDb as gld)).map(GafisLogicDb(gld.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisLogicDb as gld)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisLogicDb] = {
    withSQL {
      select.from(GafisLogicDb as gld).where.append(where)
    }.map(GafisLogicDb(gld.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisLogicDb] = {
    withSQL {
      select.from(GafisLogicDb as gld).where.append(where)
    }.map(GafisLogicDb(gld.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisLogicDb as gld).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    logicCode: Option[String] = None,
    logicName: Option[String] = None,
    logicCategory: Option[String] = None,
    logicDeltag: Option[String] = None,
    logicRemark: Option[String] = None)(implicit session: DBSession = autoSession): GafisLogicDb = {
    withSQL {
      insert.into(GafisLogicDb).columns(
        column.pkId,
        column.logicCode,
        column.logicName,
        column.logicCategory,
        column.logicDeltag,
        column.logicRemark
      ).values(
        pkId,
        logicCode,
        logicName,
        logicCategory,
        logicDeltag,
        logicRemark
      )
    }.update.apply()

    GafisLogicDb(
      pkId = pkId,
      logicCode = logicCode,
      logicName = logicName,
      logicCategory = logicCategory,
      logicDeltag = logicDeltag,
      logicRemark = logicRemark)
  }

  def save(entity: GafisLogicDb)(implicit session: DBSession = autoSession): GafisLogicDb = {
    withSQL {
      update(GafisLogicDb).set(
        column.pkId -> entity.pkId,
        column.logicCode -> entity.logicCode,
        column.logicName -> entity.logicName,
        column.logicCategory -> entity.logicCategory,
        column.logicDeltag -> entity.logicDeltag,
        column.logicRemark -> entity.logicRemark
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisLogicDb)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisLogicDb).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
