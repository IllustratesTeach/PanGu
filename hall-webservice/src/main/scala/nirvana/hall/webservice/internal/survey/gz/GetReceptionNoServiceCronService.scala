package nirvana.hall.webservice.internal.survey.gz


import monad.support.services.LoggerSupport
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.survey.{HandprintService, SurveyRecord}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import stark.webservice.services.StarkWebServiceClient

/**
  * Created by ssj on 2017/11/14.
  */
class GetReceptionNoServiceCronService (hallWebserviceConfig: HallWebserviceConfig,surveyRecord: SurveyRecord) extends LoggerSupport{
  val url = hallWebserviceConfig.union4pfmip.url
  val targetNamespace = hallWebserviceConfig.union4pfmip.targetNamespace
  val userId = hallWebserviceConfig.union4pfmip.user
  val password = hallWebserviceConfig.union4pfmip.password
  val client = StarkWebServiceClient.createClient(classOf[HandprintService], url, targetNamespace)

  /**
    * 定时器，调用海鑫现勘接口
    * 定时获取接警编号为空的失败的现勘号的 接警编号
    *
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallWebserviceConfig.union4pfmip.cron!= null){
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.union4pfmip.cron), "sync-cron", new Runnable {
        override def run(): Unit = {
          info("begin GetReceptionNoServiceCronService")
          try {
            val receptionNolist = surveyRecord.getXkcodebyState(-2)
            if (receptionNolist.size <= 0) {
              info("没有需要获取的接警编号...")
            } else {
              for (i <- 0 until receptionNolist.size) {
                val kno = receptionNolist(i)
                val map = new scala.collection.mutable.HashMap[String,Any]
                map += ("a" -> userId)
                map += ("b" -> password)
                map += ("c" -> kno)
                //获取现勘记录表中没有获取到接警编号的现勘号
                val receptionid = client.getReceptionNo(userId, password, kno)
                val requestmsgs = surveyRecord.mapToSting("getReceptionNo",map)
                surveyRecord.saveSurveyLogRecord("getReceptionNo","","",requestmsgs,receptionid,"")
                info("hx  getReceptionNo -- 接警编号：" + receptionid)
                if (receptionid.equals("") || receptionid == null) {
                  return
                } else {
                  //TODO 接警编号入库操作
                  surveyRecord.updateCasePeception(receptionid,kno)
                  //更新已经获取到接警编号的现勘号的状态
                  surveyRecord.updateXkcodeState(Constant.xkcodeajbhsuccess,kno)
                }
              }

            }
            info("end GetReceptionNoServiceCronService")
          } catch {
            case e: Exception =>
              error("GetReceptionNoServiceCronService-error:" + e.getMessage)
              surveyRecord.saveSurveyLogRecord("","","","","",e.getMessage)
          }
        }
      })
    }
  }
}
