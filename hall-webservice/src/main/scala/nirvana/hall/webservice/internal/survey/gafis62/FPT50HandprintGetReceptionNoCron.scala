package nirvana.hall.webservice.internal.survey.gafis62

import java.util.Date

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.c.services.gloclib.survey
import nirvana.hall.c.services.gloclib.survey.SURVEYCONFIG
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.survey.SurveyConstant
import nirvana.hall.webservice.services.survey.{SurveyConfigService, SurveyRecordService}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

/**
  * Created by T430 on 2/1/2018.
  */
class FPT50HandprintGetReceptionNoCron(hallWebserviceConfig: HallWebserviceConfig,
                                       fPT50HandprintServiceClient: FPT50HandprintServiceClient,
                                       surveyConfigService: SurveyConfigService,
                                       surveyRecordService: SurveyRecordService,
                                       hallV62Config: HallV62Config) extends LoggerSupport{

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    //获取接警编号
    periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.handprintService.cron, StartAtDelay), "survey-cron-getReceptionNo", new Runnable {
      override def run(): Unit = {
        try {
          info("begin getReceptionNo")
          getReceptionNo
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

  /**
    * 获取现场指纹列表
    */
  def getReceptionNo: Unit ={
    //TODO 添加日志，异常判断和处理，完善业务逻辑,目前是完全理想状态下的代码逻辑
    try{
      //获取系统时间
      val systemDateTime = fPT50HandprintServiceClient.getSystemDateTime()
      info("系统时间{}",systemDateTime)
      //获取配置信息列表，并循环获取数据
      surveyConfigService.getSurveyConfigList().filter(_.nFlages == 1).foreach(getReceptionNoBySurveyConfig)
    }catch {
      case ex:Exception =>
        error("getLatentList-error:{},currentTime:{}"
          ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
        )
    }
  }
  /**
    * 获取接警编号
    */
  def getReceptionNoBySurveyConfig(surveyConfig: SURVEYCONFIG): Unit ={
    FPT50HandprintUtils.modifyHallV62ConfigAppSvr(surveyConfig.szConfig,hallV62Config)
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
