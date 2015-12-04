package nirvana.hall.api.entities

import scalikejdbc._

case class SyncStrategy(
  pkId: String,
  strategyName: Option[String] = None,
  strategyQuery: Option[String] = None,
  `type`: Option[String] = None,
  targets: Option[String] = None,
  deltag: Option[String] = None,
  flag: Option[String] = None,
  hasPalm: Option[String] = None) {

  def save()(implicit session: DBSession = SyncStrategy.autoSession): SyncStrategy = SyncStrategy.save(this)(session)

  def destroy()(implicit session: DBSession = SyncStrategy.autoSession): Unit = SyncStrategy.destroy(this)(session)

}


object SyncStrategy extends SQLSyntaxSupport[SyncStrategy] {

  override val tableName = "SYNC_STRATEGY"

  override val columns = Seq("PK_ID", "STRATEGY_NAME", "STRATEGY_QUERY", "TYPE", "TARGETS", "DELTAG", "FLAG", "HAS_PALM")

  def apply(ss: SyntaxProvider[SyncStrategy])(rs: WrappedResultSet): SyncStrategy = apply(ss.resultName)(rs)
  def apply(ss: ResultName[SyncStrategy])(rs: WrappedResultSet): SyncStrategy = new SyncStrategy(
    pkId = rs.get(ss.pkId),
    strategyName = rs.get(ss.strategyName),
    strategyQuery = rs.get(ss.strategyQuery),
    `type` = rs.get(ss.`type`),
    targets = rs.get(ss.targets),
    deltag = rs.get(ss.deltag),
    flag = rs.get(ss.flag),
    hasPalm = rs.get(ss.hasPalm)
  )

  val ss = SyncStrategy.syntax("ss")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[SyncStrategy] = {
    withSQL {
      select.from(SyncStrategy as ss).where.eq(ss.pkId, pkId)
    }.map(SyncStrategy(ss.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SyncStrategy] = {
    withSQL(select.from(SyncStrategy as ss)).map(SyncStrategy(ss.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SyncStrategy as ss)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SyncStrategy] = {
    withSQL {
      select.from(SyncStrategy as ss).where.append(where)
    }.map(SyncStrategy(ss.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SyncStrategy] = {
    withSQL {
      select.from(SyncStrategy as ss).where.append(where)
    }.map(SyncStrategy(ss.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SyncStrategy as ss).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    strategyName: Option[String] = None,
    strategyQuery: Option[String] = None,
    `type`: Option[String] = None,
    targets: Option[String] = None,
    deltag: Option[String] = None,
    flag: Option[String] = None,
    hasPalm: Option[String] = None)(implicit session: DBSession = autoSession): SyncStrategy = {
    withSQL {
      insert.into(SyncStrategy).columns(
        column.pkId,
        column.strategyName,
        column.strategyQuery,
        column.`type`,
        column.targets,
        column.deltag,
        column.flag,
        column.hasPalm
      ).values(
        pkId,
        strategyName,
        strategyQuery,
        `type`,
        targets,
        deltag,
        flag,
        hasPalm
      )
    }.update.apply()

    SyncStrategy(
      pkId = pkId,
      strategyName = strategyName,
      strategyQuery = strategyQuery,
      `type` = `type`,
      targets = targets,
      deltag = deltag,
      flag = flag,
      hasPalm = hasPalm)
  }

  def save(entity: SyncStrategy)(implicit session: DBSession = autoSession): SyncStrategy = {
    withSQL {
      update(SyncStrategy).set(
        column.pkId -> entity.pkId,
        column.strategyName -> entity.strategyName,
        column.strategyQuery -> entity.strategyQuery,
        column.`type` -> entity.`type`,
        column.targets -> entity.targets,
        column.deltag -> entity.deltag,
        column.flag -> entity.flag,
        column.hasPalm -> entity.hasPalm
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: SyncStrategy)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SyncStrategy).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
