package nirvana.hall.spark.services

import java.io.{File, InputStream}

import nirvana.hall.spark.config.NirvanaSparkConfig
import org.apache.commons.io.IOUtils
import org.apache.commons.net.ftp.{FTP, FTPClient}

/**
 * Created by wangjue on 2016/6/5.
 */
object SparkFTPClient {
  private lazy val ftpPost = SysProperties.getPropertyOption("ftp.post").get
  private lazy val ftpPort = SysProperties.getPropertyOption("ftp.port").get
  private lazy val ftpUserName = SysProperties.getPropertyOption("ftp.username").get
  private lazy val ftpPassword = SysProperties.getPropertyOption("fpt.password").get
  def downloadFPTFromFTPServer(parameter:NirvanaSparkConfig,filePath:String): Array[Byte] = {
    val client = new FTPClient
    var fptFile : InputStream = null
    try {
      client.setDataTimeout(60000)
      client.setConnectTimeout(60000)
      client.enterLocalActiveMode()
      client.connect(ftpPost,ftpPort.toInt)
      client.login(ftpUserName,ftpPassword)
      client.setFileType(FTP.BINARY_FILE_TYPE)
      fptFile = client.retrieveFileStream(filePath)
      val data = IOUtils.toByteArray(fptFile)
      if (fptFile != null)
        fptFile.close()
      client.logout()
      data
    } catch {
      case e:RuntimeException =>
        if (client.isConnected)
          client.disconnect()
        throw e
    } finally  {
        if (client.isConnected)
          client.disconnect()
    }
  }

}
