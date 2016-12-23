package nirvana.hall.api.webservice.internal


import java.io.{FileOutputStream, InputStream}
import java.text.SimpleDateFormat
import java.util.Date
import javax.activation.DataHandler
import javax.xml.namespace.QName

import monad.support.services.LoggerSupport
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.services.sync.FetchQueryService
import nirvana.hall.api.services.{CaseInfoService, LPCardService, QueryService, TPCardService}
import nirvana.hall.api.webservice.util.FPTConvertToProtoBuffer
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.protocol.api.FPTProto._
import org.apache.axis2.addressing.EndpointReference
import org.apache.axis2.client.Options
import org.apache.axis2.rpc.client.RPCServiceClient
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

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
          val taskDataHandler = callGetSearchTask(userid,password,url,targetNamespace)
          try{
            if(null!= taskDataHandler){
              //保存debug Fpt文件
              saveFpt(taskDataHandler.getInputStream)
              val taskFpt = FPTFile.parseFromInputStream(taskDataHandler.getInputStream)
              taskControlID = taskFpt.right.get.sid
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
                    info("success:Union4pfmipCronService:返回空FPT文件")
                    return
                  }
              }
              setSearchTaskStatus(userid,password,taskControlID,true,url,targetNamespace)
            }

          }catch{
            case e:Exception=> error("Union4pfmipCronService-error:" + e.getMessage)
              setSearchTaskStatus(userid,password,taskControlID,false,url,targetNamespace)
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
    */
  private def saveFpt(fpt4Stream: InputStream,taskId: String = ""): Unit = {
    val dirPath = "E:/debugTaskFpt"
    val now = new Date()
    val sdf:SimpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS")
    val nowStr = sdf.format(now)
    var dir = new java.io.File(dirPath)
    if(!dir.exists()){
      dir.mkdirs()
    }
    try{
      var in = fpt4Stream
      var out = new FileOutputStream(dir+"/"+taskId+"_"+nowStr+".txt")
      var temp = -1
      while((temp = in.read()) != -1 && temp != -1){
        out.write(temp)
      }
      out.flush()
      out.close()
      in.close()
    } catch {
      case e:Exception=> error("saveFpt-error:" + e.getMessage)
        e.printStackTrace()
    }
  }

  /**
    * 保存debug fpt
    * @param fpt4
    */
  private def saveFpt(fpt4:FPT4File,taskId: String): Unit = {
    val dirPath = "E:/debugTaskFpt"
    val now = new Date()
    val sdf:SimpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS")
    val nowStr = sdf.format(now)
    var dir = new java.io.File(dirPath)
    if(!dir.exists()){
      dir.mkdirs()
    }
    try{
      var out = new FileOutputStream(dir+"/"+taskId+"_"+nowStr+".txt")
      val fpt4ByteArray :Array[Byte] = fpt4.toByteArray(AncientConstants.GBK_ENCODING)
      out.write(fpt4ByteArray)
      out.flush()
      out.close()
    } catch {
      case e:Exception=> error("saveFpt-error:" + e.getMessage)
        e.printStackTrace()
    }
  }


  def setSearchTaskStatus(userid:String,password:String,taskControlID:String,bstr:Boolean,url:String,targetNamespace:String): Unit ={
    try{

      val flag:Int = callSetTaskStatus(userid,password,taskControlID,bstr,url,targetNamespace)
      if(flag!=1){
        error("call setSearchTaskStatus faild!inputParam:"+ bstr + " returnVal:" + flag)
      }
    }catch{
      case e:Exception=> error("setSearchTaskStatus-error:" + e.getMessage)
    }
  }

  def callGetSearchTask(userid:String,password:String,url:String,targetNamespace:String): DataHandler ={
    val opAddEntryArgs: Array[AnyRef] = new Array[AnyRef](2)
    opAddEntryArgs(0) = userid
    opAddEntryArgs(1) = password
    val classes: Array[Class[_]] = Array[Class[_]](classOf[DataHandler])
    val serviceClientAndOpAddEntry = createClient(url,targetNamespace,"getSearchTask")
    val taskDataHandler = serviceClientAndOpAddEntry.get._1.invokeBlocking(serviceClientAndOpAddEntry.get._2, opAddEntryArgs, classes)(0).asInstanceOf[DataHandler]
    taskDataHandler
  }

  def callSetTaskStatus(userid:String,password:String,taskControlID:String,bstr:Boolean,url:String,targetNamespace:String): Int ={
    val opAddEntryArgs: Array[AnyRef] = new Array[AnyRef](4)
    opAddEntryArgs(0) = userid
    opAddEntryArgs(1) = password
    opAddEntryArgs(2) = taskControlID
    opAddEntryArgs(3) = bstr.toString
    val classes: Array[Class[_]] = Array[Class[_]](classOf[Int])
    val serviceClientAndSetTaskStatus = createClient(url,targetNamespace,"setSearchTaskStatus")
    val flag = serviceClientAndSetTaskStatus.get._1.invokeBlocking(serviceClientAndSetTaskStatus.get._2, opAddEntryArgs, classes)(0).asInstanceOf[Int]
    flag
  }

  private def createClient(url:String,targetNamespace:String,functionName:String): Option[(RPCServiceClient,QName)] ={
    val serviceClient: RPCServiceClient = new RPCServiceClient
    val options: Options = serviceClient.getOptions
    val targetEPR: EndpointReference = new EndpointReference(url)
    options.setTo(targetEPR)
    val opAddEntry: QName = new QName(targetNamespace, functionName)
    Some(serviceClient,opAddEntry)
  }



}
