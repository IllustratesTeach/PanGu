package nirvana.hall.api.entities

import scalikejdbc._

case class SysDeploy(
  pkId: String,
  projectName: String,
  currentFilePath: Option[String] = None,
  uploadIp: Option[String] = None,
  uploadPort: Option[Long] = None,
  uploadPwd: Option[String] = None,
  sysVersion: Option[String] = None,
  localIp: Option[String] = None,
  serverNo: Option[String] = None,
  localPort: Option[Long] = None,
  autoUpload: Option[Long] = None,
  personNoPr: Option[String] = None,
  webSerUrl: Option[String] = None,
  webSerUsername: Option[String] = None,
  webSerPassword: Option[String] = None,
  webserviceUrl: Option[String] = None) {

  def save()(implicit session: DBSession = SysDeploy.autoSession): SysDeploy = SysDeploy.save(this)(session)

  def destroy()(implicit session: DBSession = SysDeploy.autoSession): Unit = SysDeploy.destroy(this)(session)

}


object SysDeploy extends SQLSyntaxSupport[SysDeploy] {

  override val tableName = "SYS_DEPLOY"

  override val columns = Seq("PK_ID", "PROJECT_NAME", "CURRENT_FILE_PATH", "UPLOAD_IP", "UPLOAD_PORT", "UPLOAD_PWD", "SYS_VERSION", "LOCAL_IP", "SERVER_NO", "LOCAL_PORT", "AUTO_UPLOAD", "PERSON_NO_PR", "WEB_SER_URL", "WEB_SER_USERNAME", "WEB_SER_PASSWORD", "WEBSERVICE_URL")

  def apply(sd: SyntaxProvider[SysDeploy])(rs: WrappedResultSet): SysDeploy = apply(sd.resultName)(rs)
  def apply(sd: ResultName[SysDeploy])(rs: WrappedResultSet): SysDeploy = new SysDeploy(
    pkId = rs.get(sd.pkId),
    projectName = rs.get(sd.projectName),
    currentFilePath = rs.get(sd.currentFilePath),
    uploadIp = rs.get(sd.uploadIp),
    uploadPort = rs.get(sd.uploadPort),
    uploadPwd = rs.get(sd.uploadPwd),
    sysVersion = rs.get(sd.sysVersion),
    localIp = rs.get(sd.localIp),
    serverNo = rs.get(sd.serverNo),
    localPort = rs.get(sd.localPort),
    autoUpload = rs.get(sd.autoUpload),
    personNoPr = rs.get(sd.personNoPr),
    webSerUrl = rs.get(sd.webSerUrl),
    webSerUsername = rs.get(sd.webSerUsername),
    webSerPassword = rs.get(sd.webSerPassword),
    webserviceUrl = rs.get(sd.webserviceUrl)
  )

  val sd = SysDeploy.syntax("sd")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, projectName: String, currentFilePath: Option[String], uploadIp: Option[String], uploadPort: Option[Long], uploadPwd: Option[String], sysVersion: Option[String], localIp: Option[String], serverNo: Option[String], localPort: Option[Long], autoUpload: Option[Long], personNoPr: Option[String], webSerUrl: Option[String], webSerUsername: Option[String], webSerPassword: Option[String], webserviceUrl: Option[String])(implicit session: DBSession = autoSession): Option[SysDeploy] = {
    withSQL {
      select.from(SysDeploy as sd).where.eq(sd.pkId, pkId).and.eq(sd.projectName, projectName).and.eq(sd.currentFilePath, currentFilePath).and.eq(sd.uploadIp, uploadIp).and.eq(sd.uploadPort, uploadPort).and.eq(sd.uploadPwd, uploadPwd).and.eq(sd.sysVersion, sysVersion).and.eq(sd.localIp, localIp).and.eq(sd.serverNo, serverNo).and.eq(sd.localPort, localPort).and.eq(sd.autoUpload, autoUpload).and.eq(sd.personNoPr, personNoPr).and.eq(sd.webSerUrl, webSerUrl).and.eq(sd.webSerUsername, webSerUsername).and.eq(sd.webSerPassword, webSerPassword).and.eq(sd.webserviceUrl, webserviceUrl)
    }.map(SysDeploy(sd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysDeploy] = {
    withSQL(select.from(SysDeploy as sd)).map(SysDeploy(sd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysDeploy as sd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysDeploy] = {
    withSQL {
      select.from(SysDeploy as sd).where.append(where)
    }.map(SysDeploy(sd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysDeploy] = {
    withSQL {
      select.from(SysDeploy as sd).where.append(where)
    }.map(SysDeploy(sd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysDeploy as sd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    projectName: String,
    currentFilePath: Option[String] = None,
    uploadIp: Option[String] = None,
    uploadPort: Option[Long] = None,
    uploadPwd: Option[String] = None,
    sysVersion: Option[String] = None,
    localIp: Option[String] = None,
    serverNo: Option[String] = None,
    localPort: Option[Long] = None,
    autoUpload: Option[Long] = None,
    personNoPr: Option[String] = None,
    webSerUrl: Option[String] = None,
    webSerUsername: Option[String] = None,
    webSerPassword: Option[String] = None,
    webserviceUrl: Option[String] = None)(implicit session: DBSession = autoSession): SysDeploy = {
    withSQL {
      insert.into(SysDeploy).columns(
        column.pkId,
        column.projectName,
        column.currentFilePath,
        column.uploadIp,
        column.uploadPort,
        column.uploadPwd,
        column.sysVersion,
        column.localIp,
        column.serverNo,
        column.localPort,
        column.autoUpload,
        column.personNoPr,
        column.webSerUrl,
        column.webSerUsername,
        column.webSerPassword,
        column.webserviceUrl
      ).values(
        pkId,
        projectName,
        currentFilePath,
        uploadIp,
        uploadPort,
        uploadPwd,
        sysVersion,
        localIp,
        serverNo,
        localPort,
        autoUpload,
        personNoPr,
        webSerUrl,
        webSerUsername,
        webSerPassword,
        webserviceUrl
      )
    }.update.apply()

    SysDeploy(
      pkId = pkId,
      projectName = projectName,
      currentFilePath = currentFilePath,
      uploadIp = uploadIp,
      uploadPort = uploadPort,
      uploadPwd = uploadPwd,
      sysVersion = sysVersion,
      localIp = localIp,
      serverNo = serverNo,
      localPort = localPort,
      autoUpload = autoUpload,
      personNoPr = personNoPr,
      webSerUrl = webSerUrl,
      webSerUsername = webSerUsername,
      webSerPassword = webSerPassword,
      webserviceUrl = webserviceUrl)
  }

  def save(entity: SysDeploy)(implicit session: DBSession = autoSession): SysDeploy = {
    withSQL {
      update(SysDeploy).set(
        column.pkId -> entity.pkId,
        column.projectName -> entity.projectName,
        column.currentFilePath -> entity.currentFilePath,
        column.uploadIp -> entity.uploadIp,
        column.uploadPort -> entity.uploadPort,
        column.uploadPwd -> entity.uploadPwd,
        column.sysVersion -> entity.sysVersion,
        column.localIp -> entity.localIp,
        column.serverNo -> entity.serverNo,
        column.localPort -> entity.localPort,
        column.autoUpload -> entity.autoUpload,
        column.personNoPr -> entity.personNoPr,
        column.webSerUrl -> entity.webSerUrl,
        column.webSerUsername -> entity.webSerUsername,
        column.webSerPassword -> entity.webSerPassword,
        column.webserviceUrl -> entity.webserviceUrl
      ).where.eq(column.pkId, entity.pkId).and.eq(column.projectName, entity.projectName).and.eq(column.currentFilePath, entity.currentFilePath).and.eq(column.uploadIp, entity.uploadIp).and.eq(column.uploadPort, entity.uploadPort).and.eq(column.uploadPwd, entity.uploadPwd).and.eq(column.sysVersion, entity.sysVersion).and.eq(column.localIp, entity.localIp).and.eq(column.serverNo, entity.serverNo).and.eq(column.localPort, entity.localPort).and.eq(column.autoUpload, entity.autoUpload).and.eq(column.personNoPr, entity.personNoPr).and.eq(column.webSerUrl, entity.webSerUrl).and.eq(column.webSerUsername, entity.webSerUsername).and.eq(column.webSerPassword, entity.webSerPassword).and.eq(column.webserviceUrl, entity.webserviceUrl)
    }.update.apply()
    entity
  }

  def destroy(entity: SysDeploy)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysDeploy).where.eq(column.pkId, entity.pkId).and.eq(column.projectName, entity.projectName).and.eq(column.currentFilePath, entity.currentFilePath).and.eq(column.uploadIp, entity.uploadIp).and.eq(column.uploadPort, entity.uploadPort).and.eq(column.uploadPwd, entity.uploadPwd).and.eq(column.sysVersion, entity.sysVersion).and.eq(column.localIp, entity.localIp).and.eq(column.serverNo, entity.serverNo).and.eq(column.localPort, entity.localPort).and.eq(column.autoUpload, entity.autoUpload).and.eq(column.personNoPr, entity.personNoPr).and.eq(column.webSerUrl, entity.webSerUrl).and.eq(column.webSerUsername, entity.webSerUsername).and.eq(column.webSerPassword, entity.webSerPassword).and.eq(column.webserviceUrl, entity.webserviceUrl) }.update.apply()
  }

}
