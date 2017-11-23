package nirvana.hall.webservice.internal.survey.gz

import java.io.File
import java.util.Date
import javax.activation.DataHandler

import org.apache.commons.io.{FileUtils, IOUtils}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.services.ExceptRelationService
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.survey.gz.SurveyRecordService
import nirvana.hall.webservice.services.survey.HandprintService
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import stark.webservice.services.StarkWebServiceClient


/**
  * Created by ssj on 2017/11/21.
  */
class SendHitServiceCronService(hallWebserviceConfig: HallWebserviceConfig, surveyRecordService: SurveyRecordService, exceptRelationService: ExceptRelationService) extends LoggerSupport{
  val url = hallWebserviceConfig.handprintService.url
  val targetNamespace = hallWebserviceConfig.handprintService.targetNamespace
  val userId = hallWebserviceConfig.handprintService.user
  val password = hallWebserviceConfig.handprintService.password
  val unitCode = hallWebserviceConfig.handprintService.unitCode
  val client = StarkWebServiceClient.createClient(classOf[HandprintService], url, targetNamespace)

  final var BATCH_SIZE = 10

  /**
    * 定时器，调用海鑫现勘接口
    * 定时发送比中上报，导出和上报功能（4.0上报现勘  5.0上报刑专）
    *
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallWebserviceConfig.handprintService.cron != null){
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.handprintService.cron), "sync-cron", new Runnable {
        override def run(): Unit = {
          try {
            info("begin SendHitServiceCronService Cron")
            doWork
            info("end SendHitServiceCronService Cron")
          } catch {
            case e: Exception =>
              error("SendHitServiceCronService-error:{},currentTime:{}"
                , ExceptionUtil.getStackTraceInfo(e), DateConverter.convertDate2String(new Date, Constant.DATETIME_FORMAT)
              )
              surveyRecordService.saveSurveyLogRecord("","","","","",ExceptionUtil.getStackTraceInfo(e))
          }
        }
      })
    }
  }
  def doWork {
    var hitfpt: DataHandler = null
    var path: String = null
    surveyRecordService.getSurveyHit(BATCH_SIZE).foreach {
      hitResult =>
        if(surveyRecordService.isKno(hitResult.get("kno").toString)){
          surveyRecordService.updateSurveyHitState(Constant.IsNotCode,hitResult.get("uuid").toString)
        }else{
          hitfpt = exceptRelationService.exportMatchRelation(Constant.EMPTY,hitResult.get("orasid").toString)
          path = hallWebserviceConfig.localTenprintPath + hitResult.get("kno").toString + hitResult.get("sno").toString + ".FPT"
          if(hallWebserviceConfig.saveFPTFlag.equals("1")){
            FileUtils.writeByteArrayToFile(new File(path), IOUtils.toByteArray(hitfpt.getInputStream))
          }

          val matchcode = client.FBMatchCondition(userId
            ,password
            ,IOUtils.toByteArray(hitfpt.getInputStream))
          info("FBMatchCondition 反馈返回值： " + matchcode)

          matchcode match {
            case Constant.SendHitError =>
              surveyRecordService.updateSurveyHitState(Constant.SendHitError,hitResult.get("uuid").toString)
            case Constant.SendHitSuccess =>
              surveyRecordService.updateSurveyHitState(Constant.SendHitSuccess,hitResult.get("uuid").toString)
            case _ =>
          }
          surveyRecordService.saveSurveyLogRecord(Constant.FBMatchCondition
            ,Constant.EMPTY
            ,Constant.EMPTY
            ,CommonUtil.appendParam("userId:"+userId
              ,"password:"+password
              ,"unitCode:"+unitCode
              ,"hitpath:"+ path)
            ,matchcode
            ,Constant.EMPTY)
        }
    }
  }
}
