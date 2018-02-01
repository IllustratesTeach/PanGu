package nirvana.hall.webservice.internal.survey.gafis62

import java.util.Date

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.c.services.gloclib.survey
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.survey.SurveyConstant
import nirvana.hall.webservice.services.survey.{SurveyHitResultRecordService, SurveyRecordService}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

/**
  * Created by T430 on 2/1/2018.
  */
class FPT50HandprintSendHitResultCron (hallWebserviceConfig: HallWebserviceConfig,
                                       fPT50HandprintServiceClient: FPT50HandprintServiceClient,
                                       surveyRecordService: SurveyRecordService,
                                       surveyHitResultRecordService: SurveyHitResultRecordService) extends LoggerSupport{
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    //推送比对任务
    periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.handprintService.cron, StartAtDelay), "sync-cron-sendHitResult", new Runnable {
      override def run(): Unit = {
        try {
          info("begin sendHitResult")
          sendHitResult
          info("end sendHitResult")
        } catch {
          case ex: Exception =>
            error("sendHitResult-error:{},currentTime:{}"
              ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
            )
        }
      }
    })
  }

  /**
    * 推送比对任务
    */
  def sendHitResult: Unit ={
    val hitResultList = surveyHitResultRecordService.getSurveyHitResultRecordList(survey.SURVEY_STATE_DEFAULT, 10)
    if(hitResultList.nonEmpty){
      hitResultList.foreach{hitResult=>
        val xckybh = surveyRecordService.getPhyEvidenceNoByFingerId(hitResult.szFingerID)
        if (xckybh.nonEmpty) {
          val hitResultDhOp = surveyHitResultRecordService.getDataHandlerOfLtOrLlHitResultPackage(hitResult)
          if(hitResultDhOp.nonEmpty){
            fPT50HandprintServiceClient.sendHitResult(xckybh.get, hitResult.nQueryType, hitResultDhOp.get)

            hitResult.nState = survey.SURVEY_STATE_SUCCESS
          }else{
            hitResult.nState = survey.SURVEY_STATE_FAIL
          }
          surveyHitResultRecordService.updateSurveyHitResultRecord(hitResult)
        }
      }
      sendHitResult
    }
  }




}
