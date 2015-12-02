package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherFlow(
  pkId: String,
  personId: Option[String] = None,
  gatherStatus: Option[Long] = None,
  gatherDate: Option[DateTime] = None,
  gathererId: Option[String] = None,
  ord: Option[Long] = None,
  gatherNodeId: Option[String] = None,
  isSkip: Option[Long] = None,
  skipReason: Option[String] = None,
  skipOperator: Option[String] = None,
  skipTime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisGatherFlow.autoSession): GafisGatherFlow = GafisGatherFlow.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherFlow.autoSession): Unit = GafisGatherFlow.destroy(this)(session)

}


object GafisGatherFlow extends SQLSyntaxSupport[GafisGatherFlow] {

  override val tableName = "GAFIS_GATHER_FLOW"

  override val columns = Seq("PK_ID", "PERSON_ID", "GATHER_STATUS", "GATHER_DATE", "GATHERER_ID", "ORD", "GATHER_NODE_ID", "IS_SKIP", "SKIP_REASON", "SKIP_OPERATOR", "SKIP_TIME")

  def apply(ggf: SyntaxProvider[GafisGatherFlow])(rs: WrappedResultSet): GafisGatherFlow = apply(ggf.resultName)(rs)
  def apply(ggf: ResultName[GafisGatherFlow])(rs: WrappedResultSet): GafisGatherFlow = new GafisGatherFlow(
    pkId = rs.get(ggf.pkId),
    personId = rs.get(ggf.personId),
    gatherStatus = rs.get(ggf.gatherStatus),
    gatherDate = rs.get(ggf.gatherDate),
    gathererId = rs.get(ggf.gathererId),
    ord = rs.get(ggf.ord),
    gatherNodeId = rs.get(ggf.gatherNodeId),
    isSkip = rs.get(ggf.isSkip),
    skipReason = rs.get(ggf.skipReason),
    skipOperator = rs.get(ggf.skipOperator),
    skipTime = rs.get(ggf.skipTime)
  )

  val ggf = GafisGatherFlow.syntax("ggf")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[GafisGatherFlow] = {
    withSQL {
      select.from(GafisGatherFlow as ggf).where.eq(ggf.pkId, pkId)
    }.map(GafisGatherFlow(ggf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherFlow] = {
    withSQL(select.from(GafisGatherFlow as ggf)).map(GafisGatherFlow(ggf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherFlow as ggf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherFlow] = {
    withSQL {
      select.from(GafisGatherFlow as ggf).where.append(where)
    }.map(GafisGatherFlow(ggf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherFlow] = {
    withSQL {
      select.from(GafisGatherFlow as ggf).where.append(where)
    }.map(GafisGatherFlow(ggf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherFlow as ggf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personId: Option[String] = None,
    gatherStatus: Option[Long] = None,
    gatherDate: Option[DateTime] = None,
    gathererId: Option[String] = None,
    ord: Option[Long] = None,
    gatherNodeId: Option[String] = None,
    isSkip: Option[Long] = None,
    skipReason: Option[String] = None,
    skipOperator: Option[String] = None,
    skipTime: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisGatherFlow = {
    withSQL {
      insert.into(GafisGatherFlow).columns(
        column.pkId,
        column.personId,
        column.gatherStatus,
        column.gatherDate,
        column.gathererId,
        column.ord,
        column.gatherNodeId,
        column.isSkip,
        column.skipReason,
        column.skipOperator,
        column.skipTime
      ).values(
        pkId,
        personId,
        gatherStatus,
        gatherDate,
        gathererId,
        ord,
        gatherNodeId,
        isSkip,
        skipReason,
        skipOperator,
        skipTime
      )
    }.update.apply()

    GafisGatherFlow(
      pkId = pkId,
      personId = personId,
      gatherStatus = gatherStatus,
      gatherDate = gatherDate,
      gathererId = gathererId,
      ord = ord,
      gatherNodeId = gatherNodeId,
      isSkip = isSkip,
      skipReason = skipReason,
      skipOperator = skipOperator,
      skipTime = skipTime)
  }

  def save(entity: GafisGatherFlow)(implicit session: DBSession = autoSession): GafisGatherFlow = {
    withSQL {
      update(GafisGatherFlow).set(
        column.pkId -> entity.pkId,
        column.personId -> entity.personId,
        column.gatherStatus -> entity.gatherStatus,
        column.gatherDate -> entity.gatherDate,
        column.gathererId -> entity.gathererId,
        column.ord -> entity.ord,
        column.gatherNodeId -> entity.gatherNodeId,
        column.isSkip -> entity.isSkip,
        column.skipReason -> entity.skipReason,
        column.skipOperator -> entity.skipOperator,
        column.skipTime -> entity.skipTime
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherFlow)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherFlow).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
