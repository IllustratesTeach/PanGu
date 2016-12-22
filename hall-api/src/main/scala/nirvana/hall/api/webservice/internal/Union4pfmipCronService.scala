package nirvana.hall.api.webservice.internal


import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date

import monad.support.services.LoggerSupport
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.services.sync.FetchQueryService
import nirvana.hall.api.services.{CaseInfoService, LPCardService, QueryService, TPCardService}
import nirvana.hall.api.webservice.services.union4pfmip
import nirvana.hall.api.webservice.util.FPTConvertToProtoBuffer
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.protocol.api.FPTProto._
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import stark.webservice.services.StarkWebServiceClient

/**
  * 互查系统定时任务
  */
class Union4pfmipCronService(hallApiConfig: HallApiConfig,
                             tPCardService: TPCardService,
                             lPCardService: LPCardService,
                             caseInfoService: CaseInfoService,
                             queryService: QueryService,
                             fetchQueryService: FetchQueryService) extends LoggerSupport{

  /**
    * 定时器，获取比对任务
    *
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallApiConfig.webservice.union4pfmip.cron != null)
      periodicExecutor.addJob(new CronSchedule(hallApiConfig.webservice.union4pfmip.cron), "union4pfmip-cron", new Runnable {
        override def run(): Unit = {
          //发送请求获取任务
          val url = hallApiConfig.webservice.union4pfmip.url
          val targetNamespace = hallApiConfig.webservice.union4pfmip.targetNamespace
          val userid = hallApiConfig.webservice.union4pfmip.user
          val password = hallApiConfig.webservice.union4pfmip.password
          var taskControlID = ""
          val searchTaskService = StarkWebServiceClient.createClient(classOf[union4pfmip], url, targetNamespace)
          val taskDataHandler = searchTaskService.getSearchTask(userid, password)

            try{
              val taskFpt = FPTFile.parseFromInputStream(taskDataHandler.getInputStream)
              taskControlID = taskFpt.right.get.sid
              //保存debug Fpt文件
              saveFpt(taskFpt.right.get,taskControlID)
              info("fun:Union4pfmipCronService,taskControlID:{};time:{}",taskControlID,new Date)
              taskFpt match {
                case Left(fpt3) => throw new Exception("Not Support FPT-V3.0")
                case Right(fpt4) =>
                  if(fpt4.logic02Recs.length>0){
                    handlerTPcardData(fpt4)
                    info("success:Union4pfmipCronService--logic02Recs,taskControlID:{};outtime:{}",taskControlID,new Date)
                  }else if(fpt4.logic03Recs.length>0){
                    handlerLPCardData(fpt4)
                    info("success:Union4pfmipCronService--logic03Recs,taskControlID:{};outtime:{}",taskControlID,new Date)
                  }else{
                    throw new Exception("接收FPT逻辑描述记录类型不正确")
                  }
              }
              try{
                val flag:Int = searchTaskService.setSearchTaskStatus(userid, password, taskControlID, true)
                if(flag!=1){
                  error("call setSearchTaskStatus faild!inputParam:"+ true + " returnVal:" + flag)
                }
              }catch{
                case e:Exception => error("setSearchTaskStatus-error:" + e.getMessage)
              }
          }catch{
              case e:Exception=> error("Union4pfmipCronService-error:" + e.getMessage)
                try{
                  val flag:Int = searchTaskService.setSearchTaskStatus(userid, password, taskControlID, false)
                  if(flag!=1){
                    error("call setSearchTaskStatus faild!inputParam:"+ false + " returnVal:" + flag)
                  }
                }catch{
                  case e:Exception=> error("setSearchTaskStatus-error:" + e.getMessage)
                }
          }
        }
      })
  }


  /**
    * 处理TPCard数据
    *
    * @param fpt4
    */
  def handlerTPcardData(fpt4:FPT4File): Unit ={
    var tPCard:TPCard = null
    fpt4.logic02Recs.foreach( sLogic02Rec =>
      tPCard = FPTConvertToProtoBuffer.TPFPT2ProtoBuffer(sLogic02Rec,fpt4)
    )
    tPCardService.addTPCard(tPCard)
    val matchTask = FPTConvertToProtoBuffer.FPT2MatchTaskProtoBuffer(fpt4)
    queryService.sendQuery(matchTask)
  }

  /**
    * 处理LPCard数据
    *
    * @param fpt4
    */
  def handlerLPCardData(fpt4:FPT4File): Unit ={
    val lPCard = FPTConvertToProtoBuffer.FPT2LPProtoBuffer(fpt4)
    val caseInfo = FPTConvertToProtoBuffer.FPT2CaseProtoBuffer(fpt4)
    val matchTask = FPTConvertToProtoBuffer.FPT2MatchTaskCaseProtoBuffer(fpt4)
    lPCardService.addLPCard(lPCard)
    caseInfoService.addCaseInfo(caseInfo)
    queryService.sendQuery(matchTask)
  }



  /**
    * 保存debug fpt
    * @param fpt4
    */
  def saveFpt(fpt4:FPT4File,taskId: String): Unit = {
    val dirPath = "E:/debugTaskFpt"
    val now = new Date()
    val sdf:SimpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS")
    val nowStr = sdf.format(now)
    var dir = new java.io.File(dirPath)
    if(!dir.exists()){
      dir.mkdirs()
    }
    try{
      var out = new FileOutputStream(dir+"/"+taskId+"_"+nowStr+".fpt")
      val fpt4ByteArray :Array[Byte] = fpt4.toByteArray(AncientConstants.GBK_ENCODING)
      out.write(fpt4ByteArray)
      out.flush()
      out.close()
    } catch {
      case e:Exception=> error("saveFpt-error:" + e.getMessage)
        e.printStackTrace()
    }
  }

}
