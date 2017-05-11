package nirvana.hall.webservice.internal.bjwcsy

import java.io.{FileOutputStream, InputStream}
import java.text.SimpleDateFormat
import java.util.Date
import javax.activation.DataHandler
import javax.sql.DataSource
import javax.xml.namespace.QName

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.fpt.FPTConvertToProtoBuffer
import nirvana.hall.api.services.{CaseInfoService, LPCardService, QueryService, TPCardService}
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.webservice.config.HallWebserviceConfig
import org.apache.axis2.addressing.EndpointReference
import org.apache.axis2.client.Options
import org.apache.axis2.rpc.client.RPCServiceClient
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

/**
  * 互查系统定时任务
  */
class Union4pfmipCronService(hallWebserviceConfig: HallWebserviceConfig,
                             tPCardService: TPCardService,
                             lPCardService: LPCardService,
                             caseInfoService: CaseInfoService,
                             queryService: QueryService,
                             implicit val dataSource: DataSource) extends LoggerSupport{

  /**
    * 定时器，获取比对任务
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallWebserviceConfig.union4pfmip.cron != null)
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.union4pfmip.cron), "union4pfmip-cron", new Runnable {
        override def run(): Unit = {
          //发送请求获取任务
          val url = hallWebserviceConfig.union4pfmip.url
          val targetNamespace = hallWebserviceConfig.union4pfmip.targetNamespace
          val userid = hallWebserviceConfig.union4pfmip.user
          val password = hallWebserviceConfig.union4pfmip.password
          var taskControlID = ""
//          StarkWebServiceClient.createClient(classOf[union4pfmip], url, targetNamespace)
          val taskDataHandler = callGetSearchTask(userid,password,url,targetNamespace)
          try{
            if(null!= taskDataHandler){
              //保存debug Fpt文件
              saveFpt(taskDataHandler.getInputStream)
              val taskFpt = FPTFile.parseFromInputStream(taskDataHandler.getInputStream)
              taskControlID = taskFpt.right.get.sid
              //保存debug Fpt文件
              saveFpt(taskFpt.right.get,taskControlID)
              info("fun:Union4pfmipCronService,taskControlID:{};time:{}",taskControlID,new Date)
              var orgSid:Long = -1
              taskFpt match {
                case Left(fpt3) => throw new Exception("Not Support FPT-V3.0")
                case Right(fpt4) =>
                  if(fpt4.logic02Recs.length>0){
                    orgSid = handlerTPcardData(fpt4,hallWebserviceConfig.hallImageUrl)
		                updateMatchResultStatus(orgSid, 0)
                    info("success:Union4pfmipCronService--logic02Recs,taskControlID:{};outtime:{}",taskControlID,new Date)
                  }else if(fpt4.logic03Recs.length>0){
                    orgSid = handlerLPCardData(fpt4)
                    updateMatchResultStatus(orgSid, 0)
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
    * @param fpt4
    * @param imageDecompressUrl 用于解压返回原图的hall-image的服务的地址
    */
  def handlerTPcardData(fpt4:FPT4File,imageDecompressUrl:String): Long ={
    fpt4.logic02Recs.foreach{ sLogic02Rec =>
      val tpCard = FPTConvertToProtoBuffer.TPFPT2ProtoBuffer(sLogic02Rec, imageDecompressUrl)
      tPCardService.addTPCard(tpCard)
    }
    //TODO 发查询的代码剥离出来
    val matchTask = FPTConvertToProtoBuffer.FPT2MatchTaskProtoBuffer(fpt4)
    queryService.sendQuery(matchTask)
  }

  /**
    * 处理LPCard数据
    *
    * @param fpt4
    */
  def handlerLPCardData(fpt4:FPT4File): Long ={
    val lPCard = FPTConvertToProtoBuffer.FPT2LPProtoBuffer(fpt4)
    val caseInfo = FPTConvertToProtoBuffer.FPT2CaseProtoBuffer(fpt4)
    val matchTask = FPTConvertToProtoBuffer.FPT2MatchTaskCaseProtoBuffer(fpt4)
    lPCardService.addLPCard(lPCard)
    caseInfoService.addCaseInfo(caseInfo)
    queryService.sendQuery(matchTask)
  }

  /**
    * 更新比对任务状态
    * @param oraSid
    */
  def updateMatchResultStatus(oraSid:Long, seq:Long): Unit = {
    val sql = "update normalquery_queryque set seq = '"+seq+"' where status >= '2' and ora_sid = ?"
    JdbcDatabase.update(sql) { ps =>
      ps.setLong(1, seq)
      ps.setLong(2, oraSid)
    }
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
