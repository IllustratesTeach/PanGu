package nirvana.hall.webservice.internal.haixin



import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.services.QueryService
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.haixin.{StrategyService, SyncCronSendQueryService}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

import scala.collection.mutable

/**
  * Created by yuchen on 2017/8/2.
  */
class SyncCronSendQueryServiceImpl(strategyService:StrategyService
                                   ,queryService: QueryService
                                  ,hallWebserviceConfig: HallWebserviceConfig) extends SyncCronSendQueryService with LoggerSupport{

  val BATCH_SIZE = 10

  /**
    * 定时发查询
    * @param periodicExecutor
    * @param syncCronSendQueryService
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, syncCronSendQueryService: SyncCronSendQueryService): Unit = {
    if(hallWebserviceConfig.union4pfmip.cron!= null){
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.union4pfmip.cron), "sync-cron", new Runnable {
        override def run(): Unit = {
          info("begin cron sendQuery IA")
          try{
            syncCronSendQueryService.doWork()
          }catch{
            case e:Exception =>
              error(ExceptionUtil.getStackTraceInfo(e))
          }
          info("end sync-cron sendQuery IA")
        }
      })
    }
  }
  /**
    * 定时任务调用方法
    */
  override def doWork(): Unit = {

    val listBuffer = strategyService.getPersonIdUseBySendQuery(BATCH_SIZE)

    listBuffer match{
      case Some(m) => m.foreach(t => sendQuery(t))
      case _ =>
    }
  }

  private def sendQuery(map: mutable.HashMap[String,Any]): Unit ={

    val ia_finger_pkid = map.get("uuid").get.toString
    try{
      val ora_sid = queryService.sendQueryByCardIdAndMatchType(
        if(map.get("personid").get.toString.toUpperCase.startsWith("R"))
           map.get("personid").get.toString.toUpperCase.drop(1)
        else
          map.get("personid").get.toString.toUpperCase
        , MatchType.FINGER_TT)//发送TT查询
      strategyService.recordAutoSendQueryOraSidAndQueryIdWithTT(map.get("uuid").get.toString
        ,ora_sid,map.get("queryid").get.toString,MatchType.FINGER_TT.getNumber)
      strategyService.setSendQueryStatus(ia_finger_pkid,IAConstant.SEND_QUERY_SUCCESS)
    }catch {
      case e:Exception =>
        strategyService.sendQueryExceptionHandler(ia_finger_pkid,e.getMessage)
        strategyService.setSendQueryStatus(ia_finger_pkid,IAConstant.SEND_QUERY_FAIL)
    }
  }
}
