package nirvana.hall.webservice.internal.survey.gz

import java.util.Date

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.survey.gz.client.FPT50HandprintServiceService
import nirvana.hall.webservice.services.survey.gz.SurveyRecordService
import org.apache.commons.codec.digest.DigestUtils
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

/**
  * Created by ssj on 2017/11/14.
  */
class GetReceptionNoServiceCronService (hallWebserviceConfig: HallWebserviceConfig,
                                        surveyRecordService: SurveyRecordService) extends LoggerSupport{
  val url = hallWebserviceConfig.handprintService.url
  val targetNamespace = hallWebserviceConfig.handprintService.targetNamespace
  val userID = hallWebserviceConfig.handprintService.user
  val passwordStr = hallWebserviceConfig.handprintService.password
  val unitCode = hallWebserviceConfig.handprintService.unitCode
  val password = DigestUtils.md5Hex(passwordStr)

  val fpt50handprintServiceService = new FPT50HandprintServiceService
  val fpt50handprintServicePort = fpt50handprintServiceService.getFPT50HandprintServicePort

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
    surveyRecordService.getSurveyRecordbyState(Constant.SURVEY_CODE_CASEID_ERROR, BATCH_SIZE).foreach {
      kNo =>
        val receptionNO = fpt50handprintServicePort.getReceptionNo(userID,password,kNo)
        info("getReceptionNo -- 接警编号：" + receptionNO)
        surveyRecordService.saveSurveyLogRecord (Constant.GET_RECEPTION_NO
          , kNo
          , Constant.EMPTY
          , CommonUtil.appendParam ("userID:" + userID, "password:" + password, "kNo:" + kNo)
          , receptionNO
          , Constant.EMPTY)
        if (! CommonUtil.isNullOrEmpty (receptionNO) ) {
          surveyRecordService.updateCasePeception(receptionNO,kNo)
          surveyRecordService.updateRecordStateByKno(Constant.SURVEY_CODE_CASEID_SUCCESS,kNo)
        }
    }
  }
}
