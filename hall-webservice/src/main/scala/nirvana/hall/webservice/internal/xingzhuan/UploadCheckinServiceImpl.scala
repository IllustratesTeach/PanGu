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
import nirvana.hall.webservice.services.xingzhuan.UploadCheckinService
import nirvana.hall.webservice.util.WebServicesClient_AssistCheck
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

/**
  * 上报协查比对关系
  */
class UploadCheckinServiceImpl(config: HallWebserviceConfig,
                               assistCheckRecordService: AssistCheckRecordService,
                               exceptRelationService: ExceptRelationService,
                               implicit val dataSource: DataSource) extends UploadCheckinService with LoggerSupport{

  /**
    * 上报比对关系
    *
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(config.union4pfmip.uploadCheckinCron != null)
      periodicExecutor.addJob(new CronSchedule(config.union4pfmip.uploadCheckinCron), "uploadCheckin-cron", new Runnable {
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
    //3. 记录传输结果
    info("run UploadCheckin")
    val isDeubg =  System.getProperty("isDebug")
    val isProxy =  System.getProperty("isProxy")
    if(isProxy != null && isProxy == "true") {
      System.setProperty("http.proxySet", "true")
      System.setProperty("http.proxyHost", "127.0.0.1")
      System.setProperty("http.proxyPort", "8888")
    }
    val url = config.union4pfmip.url
    val targetNamespace = config.union4pfmip.targetNamespace
    val username = config.union4pfmip.user
    val password = config.union4pfmip.password
    val uploadTime = config.union4pfmip.dateLimit
    val size = "20"
    try{
      var resultList = assistCheckRecordService.findUploadCheckin(uploadTime, size)
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
            if(dataHandler != null) {
              //debug保存 fpt
              if(isDeubg != null && isDeubg == "true") {
                saveFpt(dataHandler.getInputStream,queryId)
              }
              val methodArgs: Array[AnyRef] = new Array[AnyRef](3)
              methodArgs(0) = username
              methodArgs(1) = password
              methodArgs(2) = dataHandler
              val resultCode:Int = WebServicesClient_AssistCheck.callHallWebServiceTypeOfInt(url, targetNamespace, methodArgs, "setLocalHitResult")
              //入库成功
              if (resultCode == 1) {
                status = 1
                info("call setLocalHitResult  success! queryId: " + queryId + " oraSid:" + oraSid + " keyId:"+ keyId + " queryType:" + queryType + " resultCode:" + resultCode )
                assistCheckRecordService.saveXcReport(oraUuid,typ,status,null)
                info("uploadCheckin success! queryId: " + queryId + " oraSid:" + oraSid + " keyId:"+ keyId + " queryType:" + queryType + " status:" + status )
                //重复数据
              } else if (resultCode == 0) {
                status = 0
                error("call setLocalHitResult  success! queryId: " + queryId + " oraSid:" + oraSid + " keyId:"+ keyId + " queryType:" + queryType + " resultCode:" + resultCode)
                assistCheckRecordService.saveXcReport(oraUuid,typ,status,null)
                info("uploadCheckin success! queryId: " + queryId + " oraSid:" + oraSid + " keyId:"+ keyId + " queryType:" + queryType + " status:" + status )
              } else {
                status = -1
                info("uploadCheckin fail! queryId: " + queryId + " oraSid:" + oraSid + " keyId:"+ keyId + " queryType:" + queryType + " status:" + status )
              }
            } else {
              //未比中
              status = 3
              assistCheckRecordService.saveXcReport(oraUuid,typ,status,null)
              info("uploadCheckin success! queryId: " + queryId + " oraSid:" + oraSid + " keyId:"+ keyId + " queryType:" + queryType + " status:" + status )
            }

          } catch {
            case e:Exception=> error("uploadCheckin-error: queryId: " + queryId + " oraSid:" + oraSid + " keyId:"+ keyId + " queryType:" + queryType + " errorInfo:" + ExceptionUtil.getStackTraceInfo(e))
              e.printStackTrace()
          }
        }
      }
    }catch{
      case e:Exception=> error("uploadCheckin-error:" + ExceptionUtil.getStackTraceInfo(e))
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

  def saveFpt(in: InputStream,taskId: String): Unit = {
    val dirPath = "C:/fpt_sy"
    val now = new Date()
    val sdf:SimpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS")
    val nowStr = sdf.format(now)
    var dir = new java.io.File(dirPath)
    if(!dir.exists()){
      dir.mkdirs()
    }
    try{
      var out = new FileOutputStream(dir+"/"+taskId+"_"+nowStr+"_return.fpt")
      var byteArray = new Array[Byte](1024)
      var count:Int = -1
      count = in.read(byteArray)
      while(count != -1){
        out.write(byteArray,0,count)
        count = in.read(byteArray)
      }
      out.flush()
      out.close()
    } catch {
      case e:Exception=> error("saveFpt-error:" + e.getMessage)
        e.printStackTrace()
    }
  }

}