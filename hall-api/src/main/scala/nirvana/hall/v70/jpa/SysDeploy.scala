package nirvana.hall.v70.jpa

;
// Generated Jan 6, 2016 6:08:10 PM by hall orm generator 4.3.1.Final


import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
 * SysDeploy generated by hall orm 
 */
object SysDeploy extends ActiveRecordInstance[SysDeploy]

@Entity
@Table(name = "SYS_DEPLOY"
)
class SysDeploy extends ActiveRecord {


  @Id
  @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
  var pkId: java.lang.String = _
  @Column(name = "PROJECT_NAME", nullable = false, length = 300)
  var projectName: java.lang.String = _
  @Column(name = "CURRENT_FILE_PATH", length = 100)
  var currentFilePath: java.lang.String = _
  @Column(name = "UPLOAD_IP", length = 64)
  var uploadIp: java.lang.String = _
  @Column(name = "UPLOAD_PORT")
  var uploadPort: java.lang.Long = _
  @Column(name = "UPLOAD_PWD", length = 64)
  var uploadPwd: java.lang.String = _
  @Column(name = "SYS_VERSION", length = 32)
  var sysVersion: java.lang.String = _
  @Column(name = "LOCAL_IP", length = 64)
  var localIp: java.lang.String = _
  @Column(name = "SERVER_NO", length = 32)
  var serverNo: java.lang.String = _
  @Column(name = "LOCAL_PORT")
  var localPort: java.lang.Long = _
  @Column(name = "AUTO_UPLOAD")
  var autoUpload: java.lang.Long = _
  @Column(name = "PERSON_NO_PR", length = 10)
  var personNoPr: java.lang.String = _
  @Column(name = "WEB_SER_URL", length = 100)
  var webSerUrl: java.lang.String = _
  @Column(name = "WEB_SER_USERNAME", length = 20)
  var webSerUsername: java.lang.String = _
  @Column(name = "WEB_SER_PASSWORD", length = 20)
  var webSerPassword: java.lang.String = _
  @Column(name = "WEBSERVICE_URL", length = 100)
  var webserviceUrl: java.lang.String = _


  def this(pkId: java.lang.String, projectName: java.lang.String) {
    this()
    this.pkId = pkId
    this.projectName = projectName
  }

  def this(pkId: java.lang.String, projectName: java.lang.String, currentFilePath: java.lang.String, uploadIp: java.lang.String, uploadPort: java.lang.Long, uploadPwd: java.lang.String, sysVersion: java.lang.String, localIp: java.lang.String, serverNo: java.lang.String, localPort: java.lang.Long, autoUpload: java.lang.Long, personNoPr: java.lang.String, webSerUrl: java.lang.String, webSerUsername: java.lang.String, webSerPassword: java.lang.String, webserviceUrl: java.lang.String) {
    this()
    this.pkId = pkId
    this.projectName = projectName
    this.currentFilePath = currentFilePath
    this.uploadIp = uploadIp
    this.uploadPort = uploadPort
    this.uploadPwd = uploadPwd
    this.sysVersion = sysVersion
    this.localIp = localIp
    this.serverNo = serverNo
    this.localPort = localPort
    this.autoUpload = autoUpload
    this.personNoPr = personNoPr
    this.webSerUrl = webSerUrl
    this.webSerUsername = webSerUsername
    this.webSerPassword = webSerPassword
    this.webserviceUrl = webserviceUrl
  }


}


