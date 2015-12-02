package nirvana.hall.api.entities

import scalikejdbc._

case class GafisLogicDbFingerprint(
  pkId: String,
  logicDbPkid: Option[String] = None,
  fingerprintPkid: Option[String] = None) {

  def save()(implicit session: DBSession = GafisLogicDbFingerprint.autoSession): GafisLogicDbFingerprint = GafisLogicDbFingerprint.save(this)(session)

  def destroy()(implicit session: DBSession = GafisLogicDbFingerprint.autoSession): Unit = GafisLogicDbFingerprint.destroy(this)(session)

}


object GafisLogicDbFingerprint extends SQLSyntaxSupport[GafisLogicDbFingerprint] {

  override val tableName = "GAFIS_LOGIC_DB_FINGERPRINT"

  override val columns = Seq("PK_ID", "LOGIC_DB_PKID", "FINGERPRINT_PKID")

  def apply(gldf: SyntaxProvider[GafisLogicDbFingerprint])(rs: WrappedResultSet): GafisLogicDbFingerprint = apply(gldf.resultName)(rs)
  def apply(gldf: ResultName[GafisLogicDbFingerprint])(rs: WrappedResultSet): GafisLogicDbFingerprint = new GafisLogicDbFingerprint(
    pkId = rs.get(gldf.pkId),
    logicDbPkid = rs.get(gldf.logicDbPkid),
    fingerprintPkid = rs.get(gldf.fingerprintPkid)
  )

  val gldf = GafisLogicDbFingerprint.syntax("gldf")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[GafisLogicDbFingerprint] = {
    withSQL {
      select.from(GafisLogicDbFingerprint as gldf).where.eq(gldf.pkId, pkId)
    }.map(GafisLogicDbFingerprint(gldf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisLogicDbFingerprint] = {
    withSQL(select.from(GafisLogicDbFingerprint as gldf)).map(GafisLogicDbFingerprint(gldf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisLogicDbFingerprint as gldf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisLogicDbFingerprint] = {
    withSQL {
      select.from(GafisLogicDbFingerprint as gldf).where.append(where)
    }.map(GafisLogicDbFingerprint(gldf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisLogicDbFingerprint] = {
    withSQL {
      select.from(GafisLogicDbFingerprint as gldf).where.append(where)
    }.map(GafisLogicDbFingerprint(gldf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisLogicDbFingerprint as gldf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    logicDbPkid: Option[String] = None,
    fingerprintPkid: Option[String] = None)(implicit session: DBSession = autoSession): GafisLogicDbFingerprint = {
    withSQL {
      insert.into(GafisLogicDbFingerprint).columns(
        column.pkId,
        column.logicDbPkid,
        column.fingerprintPkid
      ).values(
        pkId,
        logicDbPkid,
        fingerprintPkid
      )
    }.update.apply()

    GafisLogicDbFingerprint(
      pkId = pkId,
      logicDbPkid = logicDbPkid,
      fingerprintPkid = fingerprintPkid)
  }

  def save(entity: GafisLogicDbFingerprint)(implicit session: DBSession = autoSession): GafisLogicDbFingerprint = {
    withSQL {
      update(GafisLogicDbFingerprint).set(
        column.pkId -> entity.pkId,
        column.logicDbPkid -> entity.logicDbPkid,
        column.fingerprintPkid -> entity.fingerprintPkid
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisLogicDbFingerprint)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisLogicDbFingerprint).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
