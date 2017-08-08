package nirvana.hall.webservice.internal.xingzhuan

import java.io.{FileOutputStream, InputStream}
import java.text.SimpleDateFormat
import java.util.Date
import javax.activation.DataHandler
import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.services.{AssistCheckRecordService, ExceptRelationService}
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.xingzhuan.LocalCheckinService
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

import scala.collection.mutable.ArrayBuffer

/**
  * 上报协查比对关系
  */
class LocalCheckinServiceImpl(config: HallWebserviceConfig,
                              assistCheckRecordService: AssistCheckRecordService,
                              exceptRelationService: ExceptRelationService,
                              implicit val dataSource: DataSource) extends LocalCheckinService with LoggerSupport{

  /**
    * 上报比对关系
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(config.union4pfmip.cron != null)
      periodicExecutor.addJob(new CronSchedule(config.union4pfmip.cron), "localCheckin-cron", new Runnable {
        override def run(): Unit = {
          doWork
        }
      })
  }

  /**
    * 定时任务调用方法
    */
  override def doWork(): Unit = {
    //1. 查询未上报比对关系
    //2. 获取比对关系fpt，传入 setXCHitResult
    //3. 保存比对fpt结果
    info("run localCheckin")
    val isDeubg =  System.getProperty("isDebug")
    val isProxy =  System.getProperty("isProxy")
    if(isProxy != null && isProxy == "true") {
      System.setProperty("http.proxySet", "true")
      System.setProperty("http.proxyHost", "127.0.0.1")
      System.setProperty("http.proxyPort", "8888")
    }
    val uploadTime = config.union4pfmip.dateLimit
    val size = "20"
    try{
      val resultList = assistCheckRecordService.findUploadCheckin(uploadTime, size)
      if(resultList.size > 0){
        resultList.foreach{ resultMap =>
          var queryId:String = ""
          var oraSid:String = ""
          var queryType:Long = -1
          var keyId:String = ""
          var oraUuid:String = ""
          try {
            queryId = resultMap("queryid").asInstanceOf[String]
            oraSid = resultMap("oraSid").asInstanceOf[String]
            queryType = resultMap("querytype").asInstanceOf[Long]
            keyId = resultMap("keyid").asInstanceOf[String]
            oraUuid = resultMap("oraUuid").asInstanceOf[String]
            info("queryId: " + queryId + " oraSid:" + oraSid + " keyId:" + keyId + " queryType:" + queryType)
            var status:Long = 0
            val typ:String = getType(queryType)
            val dataHandler:DataHandler = exceptRelationService.exportMatchRelation(queryId,oraSid)
            status = 1
            val fptPath:String = saveFpt(dataHandler.getInputStream,keyId,config.localHitResultPath)
            assistCheckRecordService.saveXcReport(oraUuid,typ,status, fptPath)
          } catch {
            case e:Exception=> error("localCheckin-error: queryId: " + queryId + " oraSid:" + oraSid + " keyId:"+ keyId + " queryType:" + queryType + " errorInfo:" + ExceptionUtil.getStackTraceInfo(e))
              val status = 8
              assistCheckRecordService.saveErrorReport(oraUuid,getType(queryType),status, ExceptionUtil.getStackTraceInfo(e))
          }
        }
      }
    }catch{
      case e:Exception=> error("localCheckin-error:" + ExceptionUtil.getStackTraceInfo(e))
    }
  }

  def getType(queryType:Long): String ={
    var typ = ""
    queryType match {
      case 0 => typ = "3"
      case 1 => typ = "4"
      case 2 => typ = "5"
      case 3 => typ = "6"
      case _ =>
    }
    typ
  }

  private def saveFpt(in: InputStream,id: String, dirPath:String): String = {
    val now = new Date()
    val sdf:SimpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS")
    val nowStr = sdf.format(now)
    var dir = new java.io.File(dirPath)
    val finalPath = dir+"\\"+id+"_"+nowStr+".fpt"
    var out:FileOutputStream = null
    if(!dir.exists()){
      dir.mkdirs()
    }
    try{
      out = new FileOutputStream(finalPath)
      var byteArray = new Array[Byte](1024)
      var count:Int = -1
      count = in.read(byteArray)
      while(count != -1){
        out.write(byteArray,0,count)
        count = in.read(byteArray)
      }
    } finally {
      out.flush()
      out.close()
    }
    finalPath
  }

}