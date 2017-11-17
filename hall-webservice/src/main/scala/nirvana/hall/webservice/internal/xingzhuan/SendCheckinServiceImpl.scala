package nirvana.hall.webservice.internal.xingzhuan

import java.io.{FileOutputStream, InputStream}
import java.text.SimpleDateFormat
import java.util.Date
import javax.activation.DataHandler
import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.services.{AssistCheckRecordService, ExceptRelationService}
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.xingzhuan.SendCheckinService
import nirvana.hall.webservice.util.WebServicesClient_AssistCheck
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

import scala.collection.mutable.ArrayBuffer

/**
  * 返回协查比对关系
  */
class SendCheckinServiceImpl(config: HallWebserviceConfig,
                             assistCheckRecordService: AssistCheckRecordService,
                             exceptRelationService: ExceptRelationService,
                             implicit val dataSource: DataSource) extends SendCheckinService with LoggerSupport{

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(config.union4pfmip.sendCheckinCron != null)
      periodicExecutor.addJob(new CronSchedule(config.union4pfmip.sendCheckinCron), "sendCheckin-cron", new Runnable {
        override def run(): Unit = {
          doWork
        }
      })
  }

  /**
    * 定时任务调用方法
    */
  override def doWork(): Unit = {
    //1. 查询 HALL_ASSISTCHECK 表获取已认定完成任务
    //2. 获取比对关系fpt，传入 setXCHitResult
    //3. 记录传输结果
    info("run SendCheckin")
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
    val size = "20"
    try{
      var resultList = assistCheckRecordService.findAssistcheck(size)
      if(resultList.size > 0){
        resultList.foreach{ resultMap =>
          var id:String = ""
          var queryId:String = ""
          var oraSid:String = ""
          var queryType:Long = -1
          var keyId:String = ""
          try {
            id = resultMap("id").asInstanceOf[String]
            queryId = resultMap("queryid").asInstanceOf[String]
            oraSid = resultMap("orasid").asInstanceOf[String]
            queryType = resultMap("querytype").asInstanceOf[Long]
            keyId = resultMap("keyid").asInstanceOf[String]
            info("queryId: " + queryId + " oraSid:" + oraSid + " keyId:" + keyId + " queryType:" + queryType)
            var status:Long = 0
            val dataHandler:DataHandler = exceptRelationService.exportMatchRelation(queryId,oraSid)
            if(dataHandler != null){
              //debug保存 fpt
              if(isDeubg != null && isDeubg == "true") {
                saveFpt(dataHandler.getInputStream,queryId)
              }
              val methodArgs: Array[AnyRef] = new Array[AnyRef](3)
              methodArgs(0) = username
              methodArgs(1) = password
              methodArgs(2) = dataHandler
              val resultCode:Int = WebServicesClient_AssistCheck.callHallWebServiceTypeOfInt(url, targetNamespace, methodArgs, "setXCHitResult")
              //入库成功
              if (resultCode == 1) {
                status = 1
                info("call setXCHitResult  success! queryId: " + queryId + " oraSid:" + oraSid + " keyId:"+ keyId + " queryType:" + queryType + " resultCode:" + resultCode )
                updateAssistcheckStatus(status, id)
                info("sendCheckinCron  success! queryId: " + queryId + " oraSid:" + oraSid + " keyId:"+ keyId + " queryType:" + queryType + " resultCode:" + resultCode)
                //重复数据
              } else if (resultCode == 0) {
                status = 2
                info("call setXCHitResult  success! queryId: " + queryId + " oraSid:" + oraSid + " keyId:"+ keyId + " queryType:" + queryType + " resultCode:" + resultCode)
                updateAssistcheckStatus(status, id)
                info("sendCheckinCron  success! queryId: " + queryId + " oraSid:" + oraSid + " keyId:"+ keyId + " queryType:" + queryType + " resultCode:" + resultCode)
              } else {
                status = -1
                error("call setXCHitResult  faild! queryId: " + queryId + " oraSid:" + oraSid + " keyId:"+ keyId + " queryType:" + queryType + " resultCode:" + resultCode)
              }
            } else {
              //比对完成无比对关系
              status = 3
              updateAssistcheckStatus(status, id)
            }
          } catch {
            case e:Exception=>
              error("sendCheckinCron-error: queryId: " + queryId + " oraSid:" + oraSid + " keyId:"+ keyId + " queryType:" + queryType + " errorInfo:" + ExceptionUtil.getStackTraceInfo(e))
          }
        }
      }
    }catch{
      case e:Exception=> error("sendCheckinCron-error:" + ExceptionUtil.getStackTraceInfo(e))
        e.printStackTrace()
    }
  }

  /**
    * 更新协查任务状态
    * @param status
    * @param id
    */
  def updateAssistcheckStatus(status:Long, id:String): Unit = {
    val sql = "update hall_assistcheck set status = ? where id = ? "
    JdbcDatabase.update(sql) { ps =>
      ps.setLong(1, status)
      ps.setString(2, id)
    }
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
      var out = new FileOutputStream(dir+"/"+taskId+"_"+nowStr+".fpt")
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