package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisQueryqueCount(
  pkId: String,
  userId: Option[String] = None,
  usedQueryCount: Option[Short] = None,
  queryType: Option[Short] = None,
  queryDate: Option[DateTime] = None,
  remark: Option[String] = None,
  queryPriority: Option[Short] = None) {

  def save()(implicit session: DBSession = GafisQueryqueCount.autoSession): GafisQueryqueCount = GafisQueryqueCount.save(this)(session)

  def destroy()(implicit session: DBSession = GafisQueryqueCount.autoSession): Unit = GafisQueryqueCount.destroy(this)(session)

}


object GafisQueryqueCount extends SQLSyntaxSupport[GafisQueryqueCount] {

  override val tableName = "GAFIS_QUERYQUE_COUNT"

  override val columns = Seq("PK_ID", "USER_ID", "USED_QUERY_COUNT", "QUERY_TYPE", "QUERY_DATE", "REMARK", "QUERY_PRIORITY")

  def apply(gqc: SyntaxProvider[GafisQueryqueCount])(rs: WrappedResultSet): GafisQueryqueCount = apply(gqc.resultName)(rs)
  def apply(gqc: ResultName[GafisQueryqueCount])(rs: WrappedResultSet): GafisQueryqueCount = new GafisQueryqueCount(
    pkId = rs.get(gqc.pkId),
    userId = rs.get(gqc.userId),
    usedQueryCount = rs.get(gqc.usedQueryCount),
    queryType = rs.get(gqc.queryType),
    queryDate = rs.get(gqc.queryDate),
    remark = rs.get(gqc.remark),
    queryPriority = rs.get(gqc.queryPriority)
  )

  val gqc = GafisQueryqueCount.syntax("gqc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, userId: Option[String], usedQueryCount: Option[Short], queryType: Option[Short], queryDate: Option[DateTime], remark: Option[String], queryPriority: Option[Short])(implicit session: DBSession = autoSession): Option[GafisQueryqueCount] = {
    withSQL {
      select.from(GafisQueryqueCount as gqc).where.eq(gqc.pkId, pkId).and.eq(gqc.userId, userId).and.eq(gqc.usedQueryCount, usedQueryCount).and.eq(gqc.queryType, queryType).and.eq(gqc.queryDate, queryDate).and.eq(gqc.remark, remark).and.eq(gqc.queryPriority, queryPriority)
    }.map(GafisQueryqueCount(gqc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisQueryqueCount] = {
    withSQL(select.from(GafisQueryqueCount as gqc)).map(GafisQueryqueCount(gqc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisQueryqueCount as gqc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisQueryqueCount] = {
    withSQL {
      select.from(GafisQueryqueCount as gqc).where.append(where)
    }.map(GafisQueryqueCount(gqc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisQueryqueCount] = {
    withSQL {
      select.from(GafisQueryqueCount as gqc).where.append(where)
    }.map(GafisQueryqueCount(gqc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisQueryqueCount as gqc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    userId: Option[String] = None,
    usedQueryCount: Option[Short] = None,
    queryType: Option[Short] = None,
    queryDate: Option[DateTime] = None,
    remark: Option[String] = None,
    queryPriority: Option[Short] = None)(implicit session: DBSession = autoSession): GafisQueryqueCount = {
    withSQL {
      insert.into(GafisQueryqueCount).columns(
        column.pkId,
        column.userId,
        column.usedQueryCount,
        column.queryType,
        column.queryDate,
        column.remark,
        column.queryPriority
      ).values(
        pkId,
        userId,
        usedQueryCount,
        queryType,
        queryDate,
        remark,
        queryPriority
      )
    }.update.apply()

    GafisQueryqueCount(
      pkId = pkId,
      userId = userId,
      usedQueryCount = usedQueryCount,
      queryType = queryType,
      queryDate = queryDate,
      remark = remark,
      queryPriority = queryPriority)
  }

  def save(entity: GafisQueryqueCount)(implicit session: DBSession = autoSession): GafisQueryqueCount = {
    withSQL {
      update(GafisQueryqueCount).set(
        column.pkId -> entity.pkId,
        column.userId -> entity.userId,
        column.usedQueryCount -> entity.usedQueryCount,
        column.queryType -> entity.queryType,
        column.queryDate -> entity.queryDate,
        column.remark -> entity.remark,
        column.queryPriority -> entity.queryPriority
      ).where.eq(column.pkId, entity.pkId).and.eq(column.userId, entity.userId).and.eq(column.usedQueryCount, entity.usedQueryCount).and.eq(column.queryType, entity.queryType).and.eq(column.queryDate, entity.queryDate).and.eq(column.remark, entity.remark).and.eq(column.queryPriority, entity.queryPriority)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisQueryqueCount)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisQueryqueCount).where.eq(column.pkId, entity.pkId).and.eq(column.userId, entity.userId).and.eq(column.usedQueryCount, entity.usedQueryCount).and.eq(column.queryType, entity.queryType).and.eq(column.queryDate, entity.queryDate).and.eq(column.remark, entity.remark).and.eq(column.queryPriority, entity.queryPriority) }.update.apply()
  }

}
