package nirvana.hall.webservice.internal.bjwcsy

import java.util.Date
import javax.activation.DataHandler
import javax.sql.DataSource
import javax.xml.namespace.QName

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.{CaseInfoService, LPCardService, QueryService, TPCardService}
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
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
                             fptService: FPTService,
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
              val taskFpt = FPTFile.parseFromInputStream(taskDataHandler.getInputStream)
              taskControlID = taskFpt.right.get.sid
              info("fun:Union4pfmipCronService,taskControlID:{};time:{}",taskControlID,new Date)
              taskFpt match {
                case Left(fpt3) => throw new Exception("Not Support FPT-V3.0")
                case Right(fpt4) =>
                  if(fpt4.logic02Recs.length>0){
                    fpt4.logic02Recs.foreach{ logic02Rec =>
                      fptService.addLogic02Res(logic02Rec)//保存数据
                      logic02Rec.fingers.foreach { finger =>
                        val queryId = queryService.sendQueryByCardIdAndMatchType(logic02Rec.cardId, MatchType.FINGER_TT) //发送TT查询
                        updateMatchResultStatus(queryId, 0) //更新状态
                        info("addLogic02Res:{} and sendQuery TT", logic02Rec.cardId)
                      }
                    }
                    info("success:Union4pfmipCronService--logic02Recs,taskControlID:{};outtime:{}",taskControlID,new Date)
                  }else if(fpt4.logic03Recs.length>0){
                    fpt4.logic03Recs.foreach{ logic03Res =>
                      fptService.addLogic03Res(_)
                      logic03Res.fingers.foreach{ finger=>
                        val queryId = queryService.sendQueryByCardIdAndMatchType(finger.fingerId, MatchType.FINGER_LT)//发送LT查询
                        updateMatchResultStatus(queryId, 0)//更新状态
                        info("addLogic03Res:fingerId:{} and sendQuery LT", finger.fingerId)
                      }
                    }
                    info("success:Union4pfmipCronService--logic03Recs,taskControlID:{};outtime:{}",taskControlID,new Date)
                  }else{
                    info("success:Union4pfmipCronService:返回空FPT文件")
                    return
                  }
              }
              (userid,password,taskControlID,true,url,targetNamespace)
            }

          }catch{
            case e:Exception=> error("Union4pfmipCronService-error:" + e.getMessage)
              setSearchTaskStatus(userid,password,taskControlID,false,url,targetNamespace)
          }
        }
      })
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
