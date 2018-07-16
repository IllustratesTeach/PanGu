package nirvana.hall.webservice.internal.survey.gafis62

import java.util.Date

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gloclib.survey
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.survey.SurveyConstant
import nirvana.hall.webservice.services.survey.{SurveyConfigService, SurveyHitResultRecordService, SurveyRecordService}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

/**
  * Created by songpeng on 2017/12/24.
  * 现场勘验系统接口定时服务
  */
class FPT50HandprintSendHitResultCron(hallWebserviceConfig: HallWebserviceConfig,
                                      surveyConfigService: SurveyConfigService,
                                      surveyRecordService: SurveyRecordService,
                                      surveyHitResultRecordService: SurveyHitResultRecordService,
                                      matchRelationService: MatchRelationService,
                                      fPT5Service: FPT5Service) extends LoggerSupport{

  val fPT50HandprintServiceClient = new FPT50HandprintServiceClient(hallWebserviceConfig.handprintService)
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {

    if(hallWebserviceConfig.handprintService.cron!= null){
      //推送比对任务
      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.handprintService.cron, StartAtDelay), "sync-cron-sendHitResult", new Runnable {
        override def run(): Unit = {
          try {
            info("begin sendHitResult")
            if(hallWebserviceConfig.handprintService.surveyV62ServiceConfig != null){
              hallWebserviceConfig.handprintService.surveyV62ServiceConfig.foreach{surveyV62ServiceConfig=>
                V62Facade.withConfigurationServer(surveyV62ServiceConfig.v62ServerConfig){
                  sendHitResult
                }
              }
            }
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
  }

  /**
    * 推送比对任务
    */
  def sendHitResult: Unit ={
    val hitResultList = surveyHitResultRecordService.getSurveyHitResultRecordList(survey.SURVEY_STATE_DEFAULT, 10)
    if(hitResultList.nonEmpty){
      hitResultList.foreach{hitResult=>
        try{
          info("hitResultList,szFingerID{}",hitResult.szFingerID)
          val xckybh = surveyRecordService.getKNoByFingerId(hitResult.szFingerID)
          //info("导出比中关系,现场勘验编号{}",xckybh.get)
          if (xckybh.nonEmpty) {
            info("获取比中关系包")
            val hitResultDhOp = surveyHitResultRecordService.getDataHandlerOfLtOrLlHitResultPackage(hitResult)
            info("获取hitResultDhOp对象{}",hitResultDhOp.get.getName)
            if(hitResultDhOp.nonEmpty){
              info("发送比中关系包")
              fPT50HandprintServiceClient.sendHitResult(xckybh.get, hitResult.nQueryType, hitResultDhOp.get)

              hitResult.nState = survey.SURVEY_STATE_SUCCESS
            }else{
              hitResult.nState = survey.SURVEY_STATE_FAIL
            }
            surveyHitResultRecordService.updateSurveyHitResultRecord(hitResult)
          }else{
            hitResult.nState = survey.SURVEY_STATE_FAIL
            surveyHitResultRecordService.updateSurveyHitResultRecord(hitResult)
          }
        }catch {
          case ex:Exception =>
            error("fPT50HandprintServiceClient-sendHitResult:{},currentTime:{}"
              ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
            )
            hitResult.nState = survey.SURVEY_STATE_FAIL
            surveyHitResultRecordService.updateSurveyHitResultRecord(hitResult)
        }
      }
      sendHitResult
    }
  }
}
