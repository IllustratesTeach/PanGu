package nirvana.hall.webservice.internal.colligategather.sync

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.internal.fpt.FPT5Converter
import nirvana.hall.api.services.TPCardService
import nirvana.hall.c.services.gfpt5lib.FPT5File
import nirvana.hall.support.services.XmlLoader
import nirvana.hall.webservice.config.HallWebserviceConfig
import org.apache.commons.io.IOUtils
import org.apache.commons.net.ftp.{FTPClient, FTPFile, FTPFileFilter}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

/**
  * @author <a href="yuchen_1997_200486@126.com">yuchen</a>.
  * @since 2018/12/27
  */
class SyncCornGetTemplateFingerPackage(hallWebserviceConfig: HallWebserviceConfig
                                       ,tPCardService: TPCardService) extends LoggerSupport{

  object Control{

    def getFTPClient: FTPClient ={
      val fTPClient = new FTPClient
      fTPClient.connect(hallWebserviceConfig.colligateGatherService.ftpIp
        ,hallWebserviceConfig.colligateGatherService.port)
      fTPClient.login(hallWebserviceConfig.colligateGatherService.ftpUsername,hallWebserviceConfig.colligateGatherService.ftpPassword)
      fTPClient.changeWorkingDirectory(hallWebserviceConfig.colligateGatherService.ftpUrl)
      fTPClient
    }

    def closeFTPConnect(fTPClient:FTPClient): Unit = fTPClient.disconnect
  }

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallWebserviceConfig.colligateGatherService.cron != null){
      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.colligateGatherService.cron, StartAtDelay), "export-cron", new Runnable {
        override def run(): Unit = {
          info("begin get")
          try{
            doWork
          }catch{
            case e:Exception =>
              error(ExceptionUtil.getStackTraceInfo(e))
          }
          info("end get")
        }
      })
    }
  }

  /**
    *
    */
  private def doWork: Unit ={
    import Control._
    val fTPClient = getFTPClient
    val files = fTPClient.listFiles(hallWebserviceConfig.colligateGatherService.ftpUrl,new FTPFileFilter {
      override def accept(file: FTPFile): Boolean = {
        (file.isFile && file.getName.endsWith("fptx"))
      }
    })
    for(f <- files){
      var success = false
      val fTPClient_ = getFTPClient
      try{
        info("================正在处理:{}",f.getName)
        val fileInputStream = fTPClient_.retrieveFileStream(hallWebserviceConfig.colligateGatherService.ftpUrl + f.getName)
        val fptObject = XmlLoader.parseXML[FPT5File](new String(IOUtils.toByteArray(fileInputStream)))
        val tpCardBuilder = FPT5Converter.convertFingerprintPackage2TPCard(fptObject.fingerprintPackage.head).toBuilder
        tPCardService.addTPCard(tpCardBuilder.build())
        success = true
      }catch {
        case ex:Throwable =>
          error("error:{},处理失败:{}",f.getName,ExceptionUtil.getStackTraceInfo(ex))
      }finally {
        val ftpClientForReName = getFTPClient
        if(success){
          ftpClientForReName.rename(hallWebserviceConfig.colligateGatherService.ftpUrl + f.getName,hallWebserviceConfig.colligateGatherService.ftpUrl + f.getName + ".success")
          info("{},处理成功",f.getName)
        }else{
          ftpClientForReName.rename(hallWebserviceConfig.colligateGatherService.ftpUrl + f.getName,hallWebserviceConfig.colligateGatherService.ftpUrl + f.getName + ".failed")
        }
        closeFTPConnect(ftpClientForReName)
        closeFTPConnect(fTPClient_)
      }
    }
    closeFTPConnect(fTPClient)
  }
}
