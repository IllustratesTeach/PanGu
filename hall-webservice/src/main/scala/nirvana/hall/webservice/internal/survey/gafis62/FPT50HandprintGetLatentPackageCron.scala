package nirvana.hall.webservice.internal.survey.gafis62

import java.util.Date

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gloclib.survey
import nirvana.hall.c.services.gloclib.survey.SURVEYCONFIG
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.survey.SurveyConstant
import nirvana.hall.webservice.services.survey.{SurveyConfigService, SurveyRecordService}
import org.apache.commons.lang.StringUtils
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

/**
  * Created by T430 on 2/1/2018.
  */
class FPT50HandprintGetLatentPackageCron (hallWebserviceConfig: HallWebserviceConfig,
                                          fPT50HandprintServiceClient: FPT50HandprintServiceClient,
                                          surveyConfigService: SurveyConfigService,
                                          surveyRecordService: SurveyRecordService,
                                          fPT5Service: FPT5Service,
                                          hallV62Config: HallV62Config) extends LoggerSupport {
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if (hallWebserviceConfig.handprintService.cron != null) {
      //根据现勘列表获取现场指纹FPT5数据
      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.handprintService.cron, StartAtDelay), "survey-cron-getLatentPackage", new Runnable {
        override def run(): Unit = {
          try {
            info("begin getLatentPackage")
            getLatentPackage
            info("end  getLatentPackage")
          } catch {
            case e: Exception =>
          }
        }
      })
    }
  }
  /**
    * 根据读取到的配置信息获取现场指纹包
    */
  def getLatentPackage: Unit ={
    try{
      //获取配置信息列表，并循环获取数据
      surveyConfigService.getSurveyConfigList().filter(_.nFlages == 1).foreach(getLatentPackageBySurveyConfig)
    }catch {
      case ex:Exception =>
        error("getLatentPackage-error:{},currentTime:{}"
          ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
        )
    }
  }

  /**
    * 读取没有获取fpt数据的现堪记录，根据物证编号获取数据并保存
    */
  def getLatentPackageBySurveyConfig(surveyConfig: SURVEYCONFIG): Unit = {
    FPT50HandprintUtils.modifyHallV62ConfigAppSvr(surveyConfig.szConfig,hallV62Config)
    //val recordList = surveyRecordService.getSurveyRecordListByState(survey.SURVEY_STATE_DEFAULT, 10)
    val recordList = surveyRecordService.getSurveyRecordListByState(2, 10)
    info("获取recordlist数量{}",recordList.length)
    if (recordList.nonEmpty) {
      recordList.foreach { record =>
        val latentPackageOp = fPT50HandprintServiceClient.getLatentPackage(record.szPhyEvidenceNo)
        if (latentPackageOp.nonEmpty) {
          var resultType = FPT50HandprintServiceConstants.RESULT_TYPE_ADD
//          if (StringUtils.isEmpty(latentPackageOp.get.caseMsg.caseId) && StringUtils.isBlank(latentPackageOp.get.caseMsg.caseId)) {
//            //更新状态
//            warn("该现场物证编号{} 无案事件编号", record.szPhyEvidenceNo)
//            record.nState = survey.SURVEY_STATE_FAIL
//            resultType = FPT50HandprintServiceConstants.RESULT_TYPE_ERROR
//          } else {
            //保存数据
          fPT5Service.addLatentPackage(latentPackageOp.get)
          if (null != latentPackageOp.get.latentFingers) {
            latentPackageOp.get.latentFingers.foreach { finger =>
              if (record.szPhyEvidenceNo.equals(finger.latentFingerImageMsg.latentPhysicalId)) {
                record.szFingerid = finger.latentFingerImageMsg.originalSystemLatentFingerPalmId
                //更新状态
                record.nState = survey.SURVEY_STATE_SUCCESS
              }
            }
          }
          if(null != latentPackageOp.get.latentPalms){
            latentPackageOp.get.latentPalms.foreach { palm  =>
              if (record.szPhyEvidenceNo.equals(palm.latentPalmImageMsg.latentPalmPhysicalId)) {
                record.szFingerid = palm.latentPalmImageMsg.latentPalmId
                //更新状态
                record.nState = survey.SURVEY_STATE_SUCCESS
              }
            }
          }
          //}
          surveyRecordService.updateSurveyRecord(record)
          fPT50HandprintServiceClient.sendFBUseCondition(record.szPhyEvidenceNo, resultType)
        }else{
          fPT50HandprintServiceClient.sendFBUseCondition(record.szPhyEvidenceNo, FPT50HandprintServiceConstants.RESULT_TYPE_ERROR)
        }
      }
      getLatentPackage
    }
  }
}
