package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherGoodsNo(
  pkId: String,
  datetime: Option[DateTime] = None,
  count: Option[Long] = None,
  deptNo: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherGoodsNo.autoSession): GafisGatherGoodsNo = GafisGatherGoodsNo.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherGoodsNo.autoSession): Unit = GafisGatherGoodsNo.destroy(this)(session)

}


object GafisGatherGoodsNo extends SQLSyntaxSupport[GafisGatherGoodsNo] {

  override val tableName = "GAFIS_GATHER_GOODS_NO"

  override val columns = Seq("PK_ID", "DATETIME", "COUNT", "DEPT_NO")

  def apply(gggn: SyntaxProvider[GafisGatherGoodsNo])(rs: WrappedResultSet): GafisGatherGoodsNo = apply(gggn.resultName)(rs)
  def apply(gggn: ResultName[GafisGatherGoodsNo])(rs: WrappedResultSet): GafisGatherGoodsNo = new GafisGatherGoodsNo(
    pkId = rs.get(gggn.pkId),
    datetime = rs.get(gggn.datetime),
    count = rs.get(gggn.count),
    deptNo = rs.get(gggn.deptNo)
  )

  val gggn = GafisGatherGoodsNo.syntax("gggn")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, datetime: Option[DateTime], count: Option[Long], deptNo: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherGoodsNo] = {
    withSQL {
      select.from(GafisGatherGoodsNo as gggn).where.eq(gggn.pkId, pkId).and.eq(gggn.datetime, datetime).and.eq(gggn.count, count).and.eq(gggn.deptNo, deptNo)
    }.map(GafisGatherGoodsNo(gggn.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherGoodsNo] = {
    withSQL(select.from(GafisGatherGoodsNo as gggn)).map(GafisGatherGoodsNo(gggn.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherGoodsNo as gggn)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherGoodsNo] = {
    withSQL {
      select.from(GafisGatherGoodsNo as gggn).where.append(where)
    }.map(GafisGatherGoodsNo(gggn.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherGoodsNo] = {
    withSQL {
      select.from(GafisGatherGoodsNo as gggn).where.append(where)
    }.map(GafisGatherGoodsNo(gggn.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherGoodsNo as gggn).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    datetime: Option[DateTime] = None,
    count: Option[Long] = None,
    deptNo: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherGoodsNo = {
    withSQL {
      insert.into(GafisGatherGoodsNo).columns(
        column.pkId,
        column.datetime,
        column.count,
        column.deptNo
      ).values(
        pkId,
        datetime,
        count,
        deptNo
      )
    }.update.apply()

    GafisGatherGoodsNo(
      pkId = pkId,
      datetime = datetime,
      count = count,
      deptNo = deptNo)
  }

  def save(entity: GafisGatherGoodsNo)(implicit session: DBSession = autoSession): GafisGatherGoodsNo = {
    withSQL {
      update(GafisGatherGoodsNo).set(
        column.pkId -> entity.pkId,
        column.datetime -> entity.datetime,
        column.count -> entity.count,
        column.deptNo -> entity.deptNo
      ).where.eq(column.pkId, entity.pkId).and.eq(column.datetime, entity.datetime).and.eq(column.count, entity.count).and.eq(column.deptNo, entity.deptNo)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherGoodsNo)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherGoodsNo).where.eq(column.pkId, entity.pkId).and.eq(column.datetime, entity.datetime).and.eq(column.count, entity.count).and.eq(column.deptNo, entity.deptNo) }.update.apply()
  }

}
