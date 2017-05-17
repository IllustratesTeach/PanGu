package nirvana.hall.webservice.internal.xingzhuan

import javax.activation.DataHandler
import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.services.{AssistCheckRecordService, ExceptRelationService}
import nirvana.hall.webservice.HallWebserviceConstants
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.xingzhuan.{AssistcheckService}
import nirvana.hall.webservice.util.xingzhuan.FPTUtil
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

/**
  * 更新协查比对关系
  */
class AssistcheckServiceImpl(config: HallWebserviceConfig,
                             assistCheckRecordService: AssistCheckRecordService,
                             exceptRelationService: ExceptRelationService,
                             implicit val dataSource: DataSource) extends AssistcheckService with LoggerSupport{

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(config.union4pfmip.cron != null)
      periodicExecutor.addJob(new CronSchedule(config.union4pfmip.cron), "assistcheck-cron", new Runnable {
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
    //2. 获取比对关系fpt，保存本地
    //3. 保存fpt路径，更新FPT状态为“FPT已生成”
    info("run AssistcheckService")
    val isDeubg =  System.getProperty("isDebug")
    val isProxy =  System.getProperty("isProxy")
    if(isProxy != null && isProxy == "true") {
      System.setProperty("http.proxySet", "true")
      System.setProperty("http.proxyHost", "127.0.0.1")
      System.setProperty("http.proxyPort", "8888")
    }
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
            var dataHandler:DataHandler = exceptRelationService.exportMatchRelation(queryId,oraSid)
            if(dataHandler != null) {
              //保存fpt更新状态
              status = 1
              val fptPath:String = FPTUtil.saveFPTAndUpdateStatus(HallWebserviceConstants.XCHitResult, dataHandler.getInputStream,id, status,config,dataSource)
              assistCheckRecordService.updateAssistcheck(status, id, fptPath)
            } else {
              //比对完成无比对关系
              status = 8
              assistCheckRecordService.updateAssistcheckStatus(status, id)
            }
          } catch {
            case e:Exception=>
              error("assistcheckCron-error: queryId: " + queryId + " oraSid:" + oraSid + " keyId:"+ keyId + " queryType:" + queryType + " errorInfo:" + ExceptionUtil.getStackTraceInfo(e))
          }
        }
      }
    }catch{
      case e:Exception=>
        error("assistcheckCron-error:" + ExceptionUtil.getStackTraceInfo(e))
    }
  }

}