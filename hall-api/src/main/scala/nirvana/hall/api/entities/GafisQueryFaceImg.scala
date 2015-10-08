package nirvana.hall.api.entities

import scalikejdbc._
import java.sql.{Blob}

case class GafisQueryFaceImg(
  pkId: String,
  card: Option[String] = None,
  fgp: Short,
  imgData: Blob) {

  def save()(implicit session: DBSession = GafisQueryFaceImg.autoSession): GafisQueryFaceImg = GafisQueryFaceImg.save(this)(session)

  def destroy()(implicit session: DBSession = GafisQueryFaceImg.autoSession): Unit = GafisQueryFaceImg.destroy(this)(session)

}


object GafisQueryFaceImg extends SQLSyntaxSupport[GafisQueryFaceImg] {

  override val tableName = "GAFIS_QUERY_FACE_IMG"

  override val columns = Seq("PK_ID", "CARD", "FGP", "IMG_DATA")

  def apply(gqfi: SyntaxProvider[GafisQueryFaceImg])(rs: WrappedResultSet): GafisQueryFaceImg = apply(gqfi.resultName)(rs)
  def apply(gqfi: ResultName[GafisQueryFaceImg])(rs: WrappedResultSet): GafisQueryFaceImg = new GafisQueryFaceImg(
    pkId = rs.get(gqfi.pkId),
    card = rs.get(gqfi.card),
    fgp = rs.get(gqfi.fgp),
    imgData = rs.get(gqfi.imgData)
  )

  val gqfi = GafisQueryFaceImg.syntax("gqfi")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, card: Option[String], fgp: Short, imgData: Blob)(implicit session: DBSession = autoSession): Option[GafisQueryFaceImg] = {
    withSQL {
      select.from(GafisQueryFaceImg as gqfi).where.eq(gqfi.pkId, pkId).and.eq(gqfi.card, card).and.eq(gqfi.fgp, fgp).and.eq(gqfi.imgData, imgData)
    }.map(GafisQueryFaceImg(gqfi.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisQueryFaceImg] = {
    withSQL(select.from(GafisQueryFaceImg as gqfi)).map(GafisQueryFaceImg(gqfi.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisQueryFaceImg as gqfi)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisQueryFaceImg] = {
    withSQL {
      select.from(GafisQueryFaceImg as gqfi).where.append(where)
    }.map(GafisQueryFaceImg(gqfi.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisQueryFaceImg] = {
    withSQL {
      select.from(GafisQueryFaceImg as gqfi).where.append(where)
    }.map(GafisQueryFaceImg(gqfi.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisQueryFaceImg as gqfi).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    card: Option[String] = None,
    fgp: Short,
    imgData: Blob)(implicit session: DBSession = autoSession): GafisQueryFaceImg = {
    withSQL {
      insert.into(GafisQueryFaceImg).columns(
        column.pkId,
        column.card,
        column.fgp,
        column.imgData
      ).values(
        pkId,
        card,
        fgp,
        imgData
      )
    }.update.apply()

    GafisQueryFaceImg(
      pkId = pkId,
      card = card,
      fgp = fgp,
      imgData = imgData)
  }

  def save(entity: GafisQueryFaceImg)(implicit session: DBSession = autoSession): GafisQueryFaceImg = {
    withSQL {
      update(GafisQueryFaceImg).set(
        column.pkId -> entity.pkId,
        column.card -> entity.card,
        column.fgp -> entity.fgp,
        column.imgData -> entity.imgData
      ).where.eq(column.pkId, entity.pkId).and.eq(column.card, entity.card).and.eq(column.fgp, entity.fgp).and.eq(column.imgData, entity.imgData)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisQueryFaceImg)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisQueryFaceImg).where.eq(column.pkId, entity.pkId).and.eq(column.card, entity.card).and.eq(column.fgp, entity.fgp).and.eq(column.imgData, entity.imgData) }.update.apply()
  }

}
