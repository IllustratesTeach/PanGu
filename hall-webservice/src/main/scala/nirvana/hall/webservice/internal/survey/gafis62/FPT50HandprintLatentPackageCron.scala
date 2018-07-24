package nirvana.hall.webservice.internal.survey.gafis62

import java.util.Date

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gloclib.survey
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.webservice.config.{HallWebserviceConfig}
import nirvana.hall.webservice.internal.survey.{SurveyConstant, SurveyFatal}
import nirvana.hall.webservice.services.survey.{SurveyConfigService, SurveyHitResultRecordService, SurveyRecordService}
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

    if(hallWebserviceConfig.handprintService.getLatentPackageCron!= null){
      //根据现勘列表获取现场指纹FPT5数据
      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.handprintService.getLatentPackageCron, StartAtDelay), "survey-cron-getLatentPackage", new Runnable {
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
    var phyEvidenceNo = Array.emptyByteArray
    if (recordList.nonEmpty) {
      recordList.foreach { record =>
        try {
          phyEvidenceNo = record.szPhyEvidenceNo.getBytes()
          val latentPackageOp = fPT50HandprintServiceClient.getLatentPackage(record.szPhyEvidenceNo)
          if (latentPackageOp.nonEmpty) {
            val resultType = FPT50HandprintServiceConstants.RESULT_TYPE_ADD
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
            //实际办案过程中会出现现场信息已经录入现勘系统也进入指纹系统中了，但是并没有真正的立案，这样的话FPT包中CaseMsg部分的asjbh应该没有值；
            //因此需要做一下区分，如果该项在FPT包中不为空且校验通过，那么把PoliceIncidentExist状态置为1，表示已存在。
            //默认为0表示不存在，将会通过另外的定时任务，定时调用现勘接口进行获取，获取后更新到指纹系统CASEINFO中。
            if(!Option(latentPackageOp.get.caseMsg.caseId).isEmpty
              && !Option(latentPackageOp.get.caseMsg.caseId).get.trim.isEmpty){
              record.PoliceIncidentExist = survey.POLICE_INCIDENT_Exist
            }

            surveyRecordService.updateSurveyRecord(record)
            fPT50HandprintServiceClient.sendFBUseCondition(record.szPhyEvidenceNo, resultType)
          }
        }catch {
          case ex: Throwable =>
            if(SurveyFatal.unapply(ex)){
              fPT50HandprintServiceClient.sendFBUseCondition(record.szPhyEvidenceNo, FPT50HandprintServiceConstants.RESULT_TYPE_ERROR)
            }
            error("fPT50HandprintServiceClient-getLatentPackage:{},PhyEvidenceNo:{},currentTime:{}",ExceptionUtil.getStackTraceInfo(ex)
              ,new String(phyEvidenceNo)
              ,DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT))
            record.nState = survey.SURVEY_STATE_FAIL
            surveyRecordService.updateSurveyRecord(record)
        }
      }
      getLatentPackage
    }
  }


}
