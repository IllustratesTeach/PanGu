package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherOcxVersion(
  pkId: String,
  name: Option[String] = None,
  `type`: Option[String] = None,
  version: Option[String] = None,
  remark: Option[String] = None,
  createDatetime: Option[DateTime] = None,
  deletag: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherOcxVersion.autoSession): GafisGatherOcxVersion = GafisGatherOcxVersion.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherOcxVersion.autoSession): Unit = GafisGatherOcxVersion.destroy(this)(session)

}


object GafisGatherOcxVersion extends SQLSyntaxSupport[GafisGatherOcxVersion] {

  override val tableName = "GAFIS_GATHER_OCX_VERSION"

  override val columns = Seq("PK_ID", "NAME", "TYPE", "VERSION", "REMARK", "CREATE_DATETIME", "DELETAG")

  def apply(ggov: SyntaxProvider[GafisGatherOcxVersion])(rs: WrappedResultSet): GafisGatherOcxVersion = apply(ggov.resultName)(rs)
  def apply(ggov: ResultName[GafisGatherOcxVersion])(rs: WrappedResultSet): GafisGatherOcxVersion = new GafisGatherOcxVersion(
    pkId = rs.get(ggov.pkId),
    name = rs.get(ggov.name),
    `type` = rs.get(ggov.`type`),
    version = rs.get(ggov.version),
    remark = rs.get(ggov.remark),
    createDatetime = rs.get(ggov.createDatetime),
    deletag = rs.get(ggov.deletag)
  )

  val ggov = GafisGatherOcxVersion.syntax("ggov")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[GafisGatherOcxVersion] = {
    withSQL {
      select.from(GafisGatherOcxVersion as ggov).where.eq(ggov.pkId, pkId)
    }.map(GafisGatherOcxVersion(ggov.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherOcxVersion] = {
    withSQL(select.from(GafisGatherOcxVersion as ggov)).map(GafisGatherOcxVersion(ggov.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherOcxVersion as ggov)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherOcxVersion] = {
    withSQL {
      select.from(GafisGatherOcxVersion as ggov).where.append(where)
    }.map(GafisGatherOcxVersion(ggov.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherOcxVersion] = {
    withSQL {
      select.from(GafisGatherOcxVersion as ggov).where.append(where)
    }.map(GafisGatherOcxVersion(ggov.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherOcxVersion as ggov).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    name: Option[String] = None,
    `type`: Option[String] = None,
    version: Option[String] = None,
    remark: Option[String] = None,
    createDatetime: Option[DateTime] = None,
    deletag: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherOcxVersion = {
    withSQL {
      insert.into(GafisGatherOcxVersion).columns(
        column.pkId,
        column.name,
        column.`type`,
        column.version,
        column.remark,
        column.createDatetime,
        column.deletag
      ).values(
        pkId,
        name,
        `type`,
        version,
        remark,
        createDatetime,
        deletag
      )
    }.update.apply()

    GafisGatherOcxVersion(
      pkId = pkId,
      name = name,
      `type` = `type`,
      version = version,
      remark = remark,
      createDatetime = createDatetime,
      deletag = deletag)
  }

  def save(entity: GafisGatherOcxVersion)(implicit session: DBSession = autoSession): GafisGatherOcxVersion = {
    withSQL {
      update(GafisGatherOcxVersion).set(
        column.pkId -> entity.pkId,
        column.name -> entity.name,
        column.`type` -> entity.`type`,
        column.version -> entity.version,
        column.remark -> entity.remark,
        column.createDatetime -> entity.createDatetime,
        column.deletag -> entity.deletag
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherOcxVersion)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherOcxVersion).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
