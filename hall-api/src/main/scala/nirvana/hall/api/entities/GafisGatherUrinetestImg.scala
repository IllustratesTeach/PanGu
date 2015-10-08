package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisGatherUrinetestImg(
  pkId: String,
  dateimg: Option[Blob] = None,
  cjsj: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisGatherUrinetestImg.autoSession): GafisGatherUrinetestImg = GafisGatherUrinetestImg.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherUrinetestImg.autoSession): Unit = GafisGatherUrinetestImg.destroy(this)(session)

}


object GafisGatherUrinetestImg extends SQLSyntaxSupport[GafisGatherUrinetestImg] {

  override val tableName = "GAFIS_GATHER_URINETEST_IMG"

  override val columns = Seq("PK_ID", "DATEIMG", "CJSJ")

  def apply(ggui: SyntaxProvider[GafisGatherUrinetestImg])(rs: WrappedResultSet): GafisGatherUrinetestImg = apply(ggui.resultName)(rs)
  def apply(ggui: ResultName[GafisGatherUrinetestImg])(rs: WrappedResultSet): GafisGatherUrinetestImg = new GafisGatherUrinetestImg(
    pkId = rs.get(ggui.pkId),
    dateimg = rs.get(ggui.dateimg),
    cjsj = rs.get(ggui.cjsj)
  )

  val ggui = GafisGatherUrinetestImg.syntax("ggui")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, dateimg: Option[Blob], cjsj: Option[DateTime])(implicit session: DBSession = autoSession): Option[GafisGatherUrinetestImg] = {
    withSQL {
      select.from(GafisGatherUrinetestImg as ggui).where.eq(ggui.pkId, pkId).and.eq(ggui.dateimg, dateimg).and.eq(ggui.cjsj, cjsj)
    }.map(GafisGatherUrinetestImg(ggui.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherUrinetestImg] = {
    withSQL(select.from(GafisGatherUrinetestImg as ggui)).map(GafisGatherUrinetestImg(ggui.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherUrinetestImg as ggui)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherUrinetestImg] = {
    withSQL {
      select.from(GafisGatherUrinetestImg as ggui).where.append(where)
    }.map(GafisGatherUrinetestImg(ggui.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherUrinetestImg] = {
    withSQL {
      select.from(GafisGatherUrinetestImg as ggui).where.append(where)
    }.map(GafisGatherUrinetestImg(ggui.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherUrinetestImg as ggui).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    dateimg: Option[Blob] = None,
    cjsj: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisGatherUrinetestImg = {
    withSQL {
      insert.into(GafisGatherUrinetestImg).columns(
        column.pkId,
        column.dateimg,
        column.cjsj
      ).values(
        pkId,
        dateimg,
        cjsj
      )
    }.update.apply()

    GafisGatherUrinetestImg(
      pkId = pkId,
      dateimg = dateimg,
      cjsj = cjsj)
  }

  def save(entity: GafisGatherUrinetestImg)(implicit session: DBSession = autoSession): GafisGatherUrinetestImg = {
    withSQL {
      update(GafisGatherUrinetestImg).set(
        column.pkId -> entity.pkId,
        column.dateimg -> entity.dateimg,
        column.cjsj -> entity.cjsj
      ).where.eq(column.pkId, entity.pkId).and.eq(column.dateimg, entity.dateimg).and.eq(column.cjsj, entity.cjsj)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherUrinetestImg)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherUrinetestImg).where.eq(column.pkId, entity.pkId).and.eq(column.dateimg, entity.dateimg).and.eq(column.cjsj, entity.cjsj) }.update.apply()
  }

}
