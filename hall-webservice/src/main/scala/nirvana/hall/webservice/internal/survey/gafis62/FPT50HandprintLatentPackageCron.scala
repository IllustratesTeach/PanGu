package nirvana.hall.webservice.internal.survey.gafis62

import java.util.Date

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gloclib.survey
import nirvana.hall.c.services.gloclib.survey.{SURVEYCONFIG, SURVEYRECORD}
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.webservice.config.{HallWebserviceConfig, SurveyConfig}
import nirvana.hall.webservice.internal.survey.{SurveyConstant, SurveyFatal}
import nirvana.hall.webservice.services.survey.{SurveyConfigService, SurveyHitResultRecordService, SurveyRecordService}
import org.apache.commons.lang.StringUtils
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

/**
  * Created by songpeng on 2017/12/24.
  * 现场勘验系统接口定时服务
  */
class FPT50HandprintLatentPackageCron(hallWebserviceConfig: HallWebserviceConfig,
                                      surveyConfigService: SurveyConfigService,
                                      surveyRecordService: SurveyRecordService,
                                      surveyHitResultRecordService: SurveyHitResultRecordService,
                                      matchRelationService: MatchRelationService,
                                      fPT5Service: FPT5Service) extends LoggerSupport{

  val fPT50HandprintServiceClient = new FPT50HandprintServiceClient(hallWebserviceConfig.handprintService)
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {

    if(hallWebserviceConfig.handprintService.cron!= null){
      //根据现勘列表获取现场指纹FPT5数据
      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.handprintService.cron, StartAtDelay), "survey-cron-getLatentPackage", new Runnable {
        override def run(): Unit = {
          try {
            info("begin getLatentPackage")
            if(hallWebserviceConfig.handprintService.surveyV62ServiceConfig != null){
              hallWebserviceConfig.handprintService.surveyV62ServiceConfig.foreach{surveyV62ServiceConfig=>
                V62Facade.withConfigurationServer(surveyV62ServiceConfig.v62ServerConfig){
                  getLatentPackage
                }
              }
            }
            info("end  getLatentPackage")
          } catch {
            case ex: Exception =>
              error("getLatentPackage-error:{},currentTime:{}"
                ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
              )
          }
        }
      })
    }
  }

  /**
    * 读取没有获取fpt数据的现堪记录，根据物证编号获取数据并保存
    */
  def getLatentPackage: Unit = {
    val recordList = surveyRecordService.getSurveyRecordListByState(survey.SURVEY_STATE_DEFAULT, 10)
    info("获取recordlist数量:{}",recordList.size)
    if (recordList.nonEmpty) {
      recordList.foreach { record =>
        try {
          val latentPackageOp = fPT50HandprintServiceClient.getLatentPackage(record.szPhyEvidenceNo)
          if (latentPackageOp.nonEmpty) {
            var resultType = FPT50HandprintServiceConstants.RESULT_TYPE_ADD
            //根据客户要求去掉按事件编号判断
            //            if (null == latentPackageOp.get.caseMsg.caseId) {
            //              //更新状态
            //              warn("该现场物证编号{} 无案事件编号", record.szPhyEvidenceNo)
            //              record.nState = survey.SURVEY_STATE_FAIL
            //              resultType = FPT50HandprintServiceConstants.RESULT_TYPE_ERROR
            //            } else {
            //保存数据
            fPT5Service.addLatentPackage(latentPackageOp.get)
            info("入库成功，现场物证编号：" + record.szPhyEvidenceNo)

            if(Option(latentPackageOp.get.latentFingers).nonEmpty){
              latentPackageOp.get.latentFingers.foreach { finger =>
                if (record.szPhyEvidenceNo.equals(finger.latentFingerImageMsg.latentPhysicalId)) {
                  record.szFingerid = finger.latentFingerImageMsg.latentPhysicalId
                  //更新状态
                  record.nState = survey.SURVEY_STATE_SUCCESS
                }
              }
            }
            if (Option(latentPackageOp.get.latentPalms).nonEmpty) {
              latentPackageOp.get.latentPalms.foreach { palm =>
                if (record.szPhyEvidenceNo.equals(palm.latentPalmImageMsg.latentPalmPhysicalId)) {
                  record.szFingerid = palm.latentPalmImageMsg.latentPalmPhysicalId
                  //更新状态
                  record.nState = survey.SURVEY_STATE_SUCCESS
                }
              }
            }
            //}
            surveyRecordService.updateSurveyRecord(record)
            fPT50HandprintServiceClient.sendFBUseCondition(record.szPhyEvidenceNo, resultType)
          }
        }catch {
          case ex: Throwable =>
            if(SurveyFatal.unapply(ex)){
              fPT50HandprintServiceClient.sendFBUseCondition(record.szPhyEvidenceNo, FPT50HandprintServiceConstants.RESULT_TYPE_ERROR)
            }
            error("fPT50HandprintServiceClient-getLatentPackage:{},currentTime:{}",ExceptionUtil.getStackTraceInfoByThrowAble(ex)
              ,DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT))
            record.nState = survey.SURVEY_STATE_FAIL
            surveyRecordService.updateSurveyRecord(record)
        }
      }
      getLatentPackage
    }
  }


}
