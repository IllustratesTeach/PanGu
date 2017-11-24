package nirvana.hall.webservice.internal.survey.gz


import java.util.Date

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.survey.HandprintService
import nirvana.hall.webservice.services.survey.gz.SurveyRecordService
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import stark.webservice.services.StarkWebServiceClient

/**
  * Created by ssj on 2017/11/14.
  */
class GetReceptionNoServiceCronService (hallWebserviceConfig: HallWebserviceConfig,surveyRecordService: SurveyRecordService) extends LoggerSupport{
  val url = hallWebserviceConfig.handprintService.url
  val targetNamespace = hallWebserviceConfig.handprintService.targetNamespace
  val userId = hallWebserviceConfig.handprintService.user
  val password = hallWebserviceConfig.handprintService.password
  val unitCode = hallWebserviceConfig.handprintService.unitCode
  val client = StarkWebServiceClient.createClient(classOf[HandprintService],
    url,
    targetNamespace,
    classOf[HandprintService].getSimpleName,
    classOf[HandprintService].getSimpleName + "HttpPort")

  val BATCH_SIZE=10
  /**
    * 定时器，调用海鑫现勘接口
    * 定时获取接警编号为空的失败的现勘号的 接警编号
    *
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallWebserviceConfig.handprintService.cron != null){
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.handprintService.cron), "sync-cron", new Runnable {
        override def run(): Unit = {
          try{
            info("begin GetReceptionNoServiceCronService Cron")
            doWork
            info("end GetReceptionNoServiceCronService Cron")
          }catch{
            case ex:Exception =>
              error("GetReceptionNoServiceCronService-error:{},currentTime:{}"
                ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,Constant.DATETIME_FORMAT)
              )
          }
        }
      })
    }
  }
  def doWork {
    surveyRecordService.getXkcodebyState(Constant.SURVEY_CODE_CASEID_ERROR, BATCH_SIZE).foreach {
      kNo =>
        val receptionid = client.getReceptionNo(userId, password, kNo)
        info("getReceptionNo -- 接警编号：" + receptionid)
        surveyRecordService.saveSurveyLogRecord (Constant.GET_RECEPTION_NO
          , Constant.EMPTY
          , Constant.EMPTY
          , CommonUtil.appendParam ("userId:" + userId, "password:" + password, "kNo:" + kNo)
          , receptionid
          , Constant.EMPTY)
        if (! CommonUtil.isNullOrEmpty (receptionid) ) {
          surveyRecordService.updateCasePeception(receptionid,kNo)
          surveyRecordService.updateXkcodeState(Constant.SURVEY_CODE_CASEID_SUCCESS,kNo)
        }
    }
  }
}
