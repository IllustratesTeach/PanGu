package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysControl(
  pkId: String,
  version: Option[String] = None,
  uploadPerson: Option[String] = None,
  uploadTime: Option[DateTime] = None,
  clientType: Option[String] = None,
  filePath: Option[String] = None,
  enableFlag: Option[String] = None,
  sysType: Option[String] = None) {

  def save()(implicit session: DBSession = SysControl.autoSession): SysControl = SysControl.save(this)(session)

  def destroy()(implicit session: DBSession = SysControl.autoSession): Unit = SysControl.destroy(this)(session)

}


object SysControl extends SQLSyntaxSupport[SysControl] {

  override val tableName = "SYS_CONTROL"

  override val columns = Seq("PK_ID", "VERSION", "UPLOAD_PERSON", "UPLOAD_TIME", "CLIENT_TYPE", "FILE_PATH", "ENABLE_FLAG", "SYS_TYPE")

  def apply(sc: SyntaxProvider[SysControl])(rs: WrappedResultSet): SysControl = apply(sc.resultName)(rs)
  def apply(sc: ResultName[SysControl])(rs: WrappedResultSet): SysControl = new SysControl(
    pkId = rs.get(sc.pkId),
    version = rs.get(sc.version),
    uploadPerson = rs.get(sc.uploadPerson),
    uploadTime = rs.get(sc.uploadTime),
    clientType = rs.get(sc.clientType),
    filePath = rs.get(sc.filePath),
    enableFlag = rs.get(sc.enableFlag),
    sysType = rs.get(sc.sysType)
  )

  val sc = SysControl.syntax("sc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, version: Option[String], uploadPerson: Option[String], uploadTime: Option[DateTime], clientType: Option[String], filePath: Option[String], enableFlag: Option[String], sysType: Option[String])(implicit session: DBSession = autoSession): Option[SysControl] = {
    withSQL {
      select.from(SysControl as sc).where.eq(sc.pkId, pkId).and.eq(sc.version, version).and.eq(sc.uploadPerson, uploadPerson).and.eq(sc.uploadTime, uploadTime).and.eq(sc.clientType, clientType).and.eq(sc.filePath, filePath).and.eq(sc.enableFlag, enableFlag).and.eq(sc.sysType, sysType)
    }.map(SysControl(sc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysControl] = {
    withSQL(select.from(SysControl as sc)).map(SysControl(sc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysControl as sc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysControl] = {
    withSQL {
      select.from(SysControl as sc).where.append(where)
    }.map(SysControl(sc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysControl] = {
    withSQL {
      select.from(SysControl as sc).where.append(where)
    }.map(SysControl(sc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysControl as sc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    version: Option[String] = None,
    uploadPerson: Option[String] = None,
    uploadTime: Option[DateTime] = None,
    clientType: Option[String] = None,
    filePath: Option[String] = None,
    enableFlag: Option[String] = None,
    sysType: Option[String] = None)(implicit session: DBSession = autoSession): SysControl = {
    withSQL {
      insert.into(SysControl).columns(
        column.pkId,
        column.version,
        column.uploadPerson,
        column.uploadTime,
        column.clientType,
        column.filePath,
        column.enableFlag,
        column.sysType
      ).values(
        pkId,
        version,
        uploadPerson,
        uploadTime,
        clientType,
        filePath,
        enableFlag,
        sysType
      )
    }.update.apply()

    SysControl(
      pkId = pkId,
      version = version,
      uploadPerson = uploadPerson,
      uploadTime = uploadTime,
      clientType = clientType,
      filePath = filePath,
      enableFlag = enableFlag,
      sysType = sysType)
  }

  def save(entity: SysControl)(implicit session: DBSession = autoSession): SysControl = {
    withSQL {
      update(SysControl).set(
        column.pkId -> entity.pkId,
        column.version -> entity.version,
        column.uploadPerson -> entity.uploadPerson,
        column.uploadTime -> entity.uploadTime,
        column.clientType -> entity.clientType,
        column.filePath -> entity.filePath,
        column.enableFlag -> entity.enableFlag,
        column.sysType -> entity.sysType
      ).where.eq(column.pkId, entity.pkId).and.eq(column.version, entity.version).and.eq(column.uploadPerson, entity.uploadPerson).and.eq(column.uploadTime, entity.uploadTime).and.eq(column.clientType, entity.clientType).and.eq(column.filePath, entity.filePath).and.eq(column.enableFlag, entity.enableFlag).and.eq(column.sysType, entity.sysType)
    }.update.apply()
    entity
  }

  def destroy(entity: SysControl)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysControl).where.eq(column.pkId, entity.pkId).and.eq(column.version, entity.version).and.eq(column.uploadPerson, entity.uploadPerson).and.eq(column.uploadTime, entity.uploadTime).and.eq(column.clientType, entity.clientType).and.eq(column.filePath, entity.filePath).and.eq(column.enableFlag, entity.enableFlag).and.eq(column.sysType, entity.sysType) }.update.apply()
  }

}
