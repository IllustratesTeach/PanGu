package nirvana.hall.webservice.internal.survey.gafis62

import java.util.Date

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.c.services.gloclib.survey
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.survey.SurveyConstant
import nirvana.hall.webservice.services.survey.SurveyRecordService
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

import scala.util.control.NonFatal

/**
  * Created by yuchen on 2018/7/13.
  * 向现勘系统获取警综案事件编号的定时任务
  */
class FPT50HandPrintGetCaseNoCron(hallWebserviceConfig: HallWebserviceConfig
                                 ,surveyRecordService: SurveyRecordService
                                 ,caseInfoService:CaseInfoService) extends LoggerSupport{
  val fPT50HandPrintServiceClient = new FPT50HandprintServiceClient(hallWebserviceConfig.handprintService)
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {

    if(hallWebserviceConfig.handprintService.getCaseNoCron!= null){

      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.handprintService.getCaseNoCron, StartAtDelay), "survey-cron-getCaseNo", new Runnable {
        override def run(): Unit = {
          try {
            info("begin FPT50HandPrintGetCaseNoCron")
            if(hallWebserviceConfig.handprintService.surveyV62ServiceConfig != null){
              hallWebserviceConfig.handprintService.surveyV62ServiceConfig.foreach{surveyV62ServiceConfig=>
                V62Facade.withConfigurationServer(surveyV62ServiceConfig.v62ServerConfig){
                  getCaseNo
                }
              }
            }
            info("end  FPT50HandPrintGetCaseNoCron")
          } catch {
            case ex: Exception =>
              error("FPT50HandPrintGetCaseNoCron-error:{},currentTime:{}"
                ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
              )
          }
        }
      })
    }
  }

  private def getCaseNo: Unit ={
    try{
      surveyRecordService.getSurveyRecordWithPoliceIncidentIsNotExist.foreach{
            info("call v62-1")
        surveyRecord =>
          if(null ==surveyRecord.szKNo){
            info("surveyRecord.szKNo是NULL===================null")
          }
          val kNo = surveyRecord.szKNo
          val caseNo = fPT50HandPrintServiceClient.getCaseNo(kNo)
          if(null == caseNo){
            info("现勘获得的编号是NULL===================null")
          }
          if(null != caseNo){
            if(caseNo.matches(SurveyConstant.REGEX_ASJBH)){
              info("call surveyServer getCaseNo success and pass regex,caseNo:{}",caseNo)
              val caseId = surveyRecordService.getCaseIdByKNo(kNo).getOrElse(throw new Exception("get caseId fail,kNo is " + kNo))
              info("call v62-2")
              caseInfoService.updateCaseInfo(caseInfoService.getCaseInfo(caseId).toBuilder.setStrJingZongCaseId(caseNo).build)
              info("call v62-3")
              surveyRecord.PoliceIncidentExist = survey.POLICE_INCIDENT_Exist
              surveyRecordService.updateSurveyRecord(surveyRecord)
              info("call v62-4")
            }else{throw new Exception("call surveyServer getCaseNo success,but not pass regex,caseNo:" + caseNo)}
          }
      }
    }catch{
      case NonFatal(e) => error("getCaseNo error:{},Error:{}",e.toString,ExceptionUtil.getStackTraceInfoByThrowAble(e))
    }
  }
}
