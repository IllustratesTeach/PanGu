package nirvana.hall.webservice.internal.bjwcsy

import java.util.Date
import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.{CaseInfoService, LPCardService, QueryService, TPCardService}
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.bjwcsy.union4pfmip
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import stark.webservice.services.StarkWebServiceClient

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

  val url = hallWebserviceConfig.union4pfmip.url
  val targetNamespace = hallWebserviceConfig.union4pfmip.targetNamespace
  val userId = hallWebserviceConfig.union4pfmip.user
  val password = hallWebserviceConfig.union4pfmip.password
  val client = StarkWebServiceClient.createClient(classOf[union4pfmip], url, targetNamespace)
  /**
    * 定时器，获取比对任务
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallWebserviceConfig.union4pfmip.cron != null)
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.union4pfmip.cron), "union4pfmip-cron", new Runnable {
        override def run(): Unit = {
          var taskControlID = ""
          val taskDataHandler = client.getSearchTask(userId,password)
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
                      val queryId = queryService.sendQueryByCardIdAndMatchType(logic02Rec.cardId, MatchType.FINGER_TT)//发送TT查询
                      updateMatchResultStatus(queryId, 0)//更新状态
                      info("addLogic02Res:{} and sendQuery TT", logic02Rec.cardId)
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
              client.setSearchTaskStatus(userId,password,taskControlID,true)
            }

          }catch{
            case e:Exception=> error("Union4pfmipCronService-error:" + e.getMessage)
              client.setSearchTaskStatus(userId,password,taskControlID,false)
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

}
