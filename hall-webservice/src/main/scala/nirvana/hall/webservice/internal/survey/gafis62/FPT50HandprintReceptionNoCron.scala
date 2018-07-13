package nirvana.hall.webservice.internal.survey.gafis62

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gloclib.survey
import nirvana.hall.c.services.gloclib.survey.{SURVEYCONFIG, SURVEYRECORD}
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.webservice.config.{HallWebserviceConfig, SurveyConfig}
import nirvana.hall.webservice.internal.survey.SurveyConstant
import nirvana.hall.webservice.services.survey.{SurveyConfigService, SurveyHitResultRecordService, SurveyRecordService}
import org.apache.commons.lang.StringUtils
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

/**
  * Created by songpeng on 2017/12/24.
  * 现场勘验系统接口定时服务
  */
class FPT50HandprintReceptionNoCron(hallWebserviceConfig: HallWebserviceConfig,
                                    surveyConfigService: SurveyConfigService,
                                    surveyRecordService: SurveyRecordService,
                                    surveyHitResultRecordService: SurveyHitResultRecordService,
                                    matchRelationService: MatchRelationService,
                                    fPT5Service: FPT5Service) extends LoggerSupport{

  val fPT50HandprintServiceClient = new FPT50HandprintServiceClient(hallWebserviceConfig.handprintService)
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {

    if(hallWebserviceConfig.handprintService.cron!= null){
      //获取接警编号
      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.handprintService.cron, StartAtDelay), "survey-cron-getReceptionNo", new Runnable {
        override def run(): Unit = {
          try {
            info("begin getReceptionNo")
            if(hallWebserviceConfig.handprintService.surveyV62ServiceConfig != null){
              hallWebserviceConfig.handprintService.surveyV62ServiceConfig.foreach{surveyV62ServiceConfig=>
                V62Facade.withConfigurationServer(surveyV62ServiceConfig.v62ServerConfig){
                  getReceptionNo
                }
              }
            }
            info("end getReceptionNo")
          } catch {
            case ex: Exception =>
              error("getReceptionNo-error:{},currentTime:{}"
                ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
              )
          }
        }
      })
    }
  }

  /**
    * 获取接警编号
    */
  def getReceptionNo: Unit ={
    //读取SURVEY_RECORD表没有接警编号的数据,获取接警编号并保存
    val recordList = surveyRecordService.getSurveyRecordListByJieJingState(survey.SURVEY_STATE_DEFAULT, 10)
    if(recordList.nonEmpty){
      recordList.foreach{record=>
        fPT50HandprintServiceClient.getReceptionNo(record.szKNo)
        record.nJieJingState = survey.SURVEY_STATE_SUCCESS
        surveyRecordService.updateSurveyRecord(record)
      }
      getReceptionNo
    }
  }
}
