package nirvana.hall.api.entities

import scalikejdbc._

case class GafisGatherUrinetest(
  pkId: String,
  uGoods: Option[String] = None,
  uMethod: Option[String] = None,
  uResult: Option[String] = None,
  uRemark: Option[String] = None,
  personId: Option[String] = None,
  ljtpbh: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherUrinetest.autoSession): GafisGatherUrinetest = GafisGatherUrinetest.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherUrinetest.autoSession): Unit = GafisGatherUrinetest.destroy(this)(session)

}


object GafisGatherUrinetest extends SQLSyntaxSupport[GafisGatherUrinetest] {

  override val tableName = "GAFIS_GATHER_URINETEST"

  override val columns = Seq("PK_ID", "U_GOODS", "U_METHOD", "U_RESULT", "U_REMARK", "PERSON_ID", "LJTPBH")

  def apply(ggu: SyntaxProvider[GafisGatherUrinetest])(rs: WrappedResultSet): GafisGatherUrinetest = apply(ggu.resultName)(rs)
  def apply(ggu: ResultName[GafisGatherUrinetest])(rs: WrappedResultSet): GafisGatherUrinetest = new GafisGatherUrinetest(
    pkId = rs.get(ggu.pkId),
    uGoods = rs.get(ggu.uGoods),
    uMethod = rs.get(ggu.uMethod),
    uResult = rs.get(ggu.uResult),
    uRemark = rs.get(ggu.uRemark),
    personId = rs.get(ggu.personId),
    ljtpbh = rs.get(ggu.ljtpbh)
  )

  val ggu = GafisGatherUrinetest.syntax("ggu")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, uGoods: Option[String], uMethod: Option[String], uResult: Option[String], uRemark: Option[String], personId: Option[String], ljtpbh: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherUrinetest] = {
    withSQL {
      select.from(GafisGatherUrinetest as ggu).where.eq(ggu.pkId, pkId).and.eq(ggu.uGoods, uGoods).and.eq(ggu.uMethod, uMethod).and.eq(ggu.uResult, uResult).and.eq(ggu.uRemark, uRemark).and.eq(ggu.personId, personId).and.eq(ggu.ljtpbh, ljtpbh)
    }.map(GafisGatherUrinetest(ggu.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherUrinetest] = {
    withSQL(select.from(GafisGatherUrinetest as ggu)).map(GafisGatherUrinetest(ggu.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherUrinetest as ggu)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherUrinetest] = {
    withSQL {
      select.from(GafisGatherUrinetest as ggu).where.append(where)
    }.map(GafisGatherUrinetest(ggu.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherUrinetest] = {
    withSQL {
      select.from(GafisGatherUrinetest as ggu).where.append(where)
    }.map(GafisGatherUrinetest(ggu.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherUrinetest as ggu).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    uGoods: Option[String] = None,
    uMethod: Option[String] = None,
    uResult: Option[String] = None,
    uRemark: Option[String] = None,
    personId: Option[String] = None,
    ljtpbh: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherUrinetest = {
    withSQL {
      insert.into(GafisGatherUrinetest).columns(
        column.pkId,
        column.uGoods,
        column.uMethod,
        column.uResult,
        column.uRemark,
        column.personId,
        column.ljtpbh
      ).values(
        pkId,
        uGoods,
        uMethod,
        uResult,
        uRemark,
        personId,
        ljtpbh
      )
    }.update.apply()

    GafisGatherUrinetest(
      pkId = pkId,
      uGoods = uGoods,
      uMethod = uMethod,
      uResult = uResult,
      uRemark = uRemark,
      personId = personId,
      ljtpbh = ljtpbh)
  }

  def save(entity: GafisGatherUrinetest)(implicit session: DBSession = autoSession): GafisGatherUrinetest = {
    withSQL {
      update(GafisGatherUrinetest).set(
        column.pkId -> entity.pkId,
        column.uGoods -> entity.uGoods,
        column.uMethod -> entity.uMethod,
        column.uResult -> entity.uResult,
        column.uRemark -> entity.uRemark,
        column.personId -> entity.personId,
        column.ljtpbh -> entity.ljtpbh
      ).where.eq(column.pkId, entity.pkId).and.eq(column.uGoods, entity.uGoods).and.eq(column.uMethod, entity.uMethod).and.eq(column.uResult, entity.uResult).and.eq(column.uRemark, entity.uRemark).and.eq(column.personId, entity.personId).and.eq(column.ljtpbh, entity.ljtpbh)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherUrinetest)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherUrinetest).where.eq(column.pkId, entity.pkId).and.eq(column.uGoods, entity.uGoods).and.eq(column.uMethod, entity.uMethod).and.eq(column.uResult, entity.uResult).and.eq(column.uRemark, entity.uRemark).and.eq(column.personId, entity.personId).and.eq(column.ljtpbh, entity.ljtpbh) }.update.apply()
  }

}
