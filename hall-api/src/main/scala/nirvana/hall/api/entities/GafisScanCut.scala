package nirvana.hall.api.entities

import scalikejdbc._

case class GafisScanCut(
  pkId: String,
  keyScanCut: String,
  reamk: Option[String] = None) {

  def save()(implicit session: DBSession = GafisScanCut.autoSession): GafisScanCut = GafisScanCut.save(this)(session)

  def destroy()(implicit session: DBSession = GafisScanCut.autoSession): Unit = GafisScanCut.destroy(this)(session)

}


object GafisScanCut extends SQLSyntaxSupport[GafisScanCut] {

  override val tableName = "GAFIS_SCAN_CUT"

  override val columns = Seq("PK_ID", "KEY_SCAN_CUT", "REAMK")

  def apply(gsc: SyntaxProvider[GafisScanCut])(rs: WrappedResultSet): GafisScanCut = apply(gsc.resultName)(rs)
  def apply(gsc: ResultName[GafisScanCut])(rs: WrappedResultSet): GafisScanCut = new GafisScanCut(
    pkId = rs.get(gsc.pkId),
    keyScanCut = rs.get(gsc.keyScanCut),
    reamk = rs.get(gsc.reamk)
  )

  val gsc = GafisScanCut.syntax("gsc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, keyScanCut: String, reamk: Option[String])(implicit session: DBSession = autoSession): Option[GafisScanCut] = {
    withSQL {
      select.from(GafisScanCut as gsc).where.eq(gsc.pkId, pkId).and.eq(gsc.keyScanCut, keyScanCut).and.eq(gsc.reamk, reamk)
    }.map(GafisScanCut(gsc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisScanCut] = {
    withSQL(select.from(GafisScanCut as gsc)).map(GafisScanCut(gsc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisScanCut as gsc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisScanCut] = {
    withSQL {
      select.from(GafisScanCut as gsc).where.append(where)
    }.map(GafisScanCut(gsc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisScanCut] = {
    withSQL {
      select.from(GafisScanCut as gsc).where.append(where)
    }.map(GafisScanCut(gsc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisScanCut as gsc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    keyScanCut: String,
    reamk: Option[String] = None)(implicit session: DBSession = autoSession): GafisScanCut = {
    withSQL {
      insert.into(GafisScanCut).columns(
        column.pkId,
        column.keyScanCut,
        column.reamk
      ).values(
        pkId,
        keyScanCut,
        reamk
      )
    }.update.apply()

    GafisScanCut(
      pkId = pkId,
      keyScanCut = keyScanCut,
      reamk = reamk)
  }

  def save(entity: GafisScanCut)(implicit session: DBSession = autoSession): GafisScanCut = {
    withSQL {
      update(GafisScanCut).set(
        column.pkId -> entity.pkId,
        column.keyScanCut -> entity.keyScanCut,
        column.reamk -> entity.reamk
      ).where.eq(column.pkId, entity.pkId).and.eq(column.keyScanCut, entity.keyScanCut).and.eq(column.reamk, entity.reamk)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisScanCut)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisScanCut).where.eq(column.pkId, entity.pkId).and.eq(column.keyScanCut, entity.keyScanCut).and.eq(column.reamk, entity.reamk) }.update.apply()
  }

}
