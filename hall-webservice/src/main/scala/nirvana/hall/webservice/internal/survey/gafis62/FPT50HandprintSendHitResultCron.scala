package nirvana.hall.webservice.internal.survey.gafis62

import java.util.{Date, UUID}

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gloclib.survey
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.webservice.CronExpParser
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.survey.{PlatformOperatorInfoProvider, PlatformOperatorInfoProviderLoader, SurveyConstant}
import nirvana.hall.webservice.jpa.{LogGetfingerdetail, LogInterfacestatus, LogPuthitresult}
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
  var provider:Option[PlatformOperatorInfoProvider] = None
  if(hallWebserviceConfig.handprintService.platformOperatorInfoProviderClass != null){
    provider = Option(PlatformOperatorInfoProviderLoader.createProvider(hallWebserviceConfig.handprintService.platformOperatorInfoProviderClass))
  }

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {

    if(hallWebserviceConfig.handprintService.sendHitCron!= null){
      //推送比对任务
      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.handprintService.sendHitCron, StartAtDelay), "sync-cron-sendHitResult", new Runnable {
        override def run(): Unit = {
          try {
            info("begin sendHitResult")
            //检查金指推送比中关系服务
            checkJinZhiSendHitResultService
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
    val hitResultList = surveyHitResultRecordService.getSurveyHitResultRecordList(survey.SURVEY_STATE_DEFAULT, 1)
    val logPutHitResult = new LogPuthitresult()
    val logInterfaceStatus = new LogInterfacestatus()
    if(hitResultList.nonEmpty){
      hitResultList.foreach{hitResult=>
        try{
          info("hitResultList,szFingerID{}",hitResult.szFingerID)
          val xckybh = surveyRecordService.getKNoByFingerId(hitResult.szFingerID)
          info("导出比中关系,现场勘验编号{}",xckybh.getOrElse(""))
          if (xckybh.nonEmpty) {
            info("获取比中关系包")
            val hitResultDhOp = surveyHitResultRecordService.getDataHandlerOfLtOrLlHitResultPackage(hitResult,logPutHitResult)
            info("获取hitResultDhOp对象{}",hitResultDhOp.getOrElse(""))
            if(hitResultDhOp.nonEmpty){
              info("发送比中关系包")
              fPT50HandprintServiceClient.sendHitResult(xckybh.get, hitResult.nQueryType, hitResultDhOp.get, logPutHitResult)

              hitResult.nState = survey.SURVEY_STATE_SUCCESS
            }else{
              logPutHitResult.calltime = new Date()
              logPutHitResult.asjfsddXzqhdm = if(hitResult.szFingerID.startsWith("F")) hitResult.szFingerID.drop(1).take(6).toLong else hitResult.szFingerID.take(6).toLong
              logPutHitResult.xczwYsxtXczzhwbh = hitResult.szFingerID
              logPutHitResult.nyzwZzhwkbh = hitResult.szHitFingerID
              logPutHitResult.nyzwZzhwdm = if(hitResult.nHitFgp.toInt < 10) "0" + hitResult.nHitFgp.toString else hitResult.nHitFgp.toString
              logPutHitResult.errormsg = "未获取到比中关系数据包。请查验比中关系数据信息是否异常"
              logPutHitResult.returnstatus = SurveyConstant.GET_HITRESULT_PACKAGE_EXCEPTION
              hitResult.nState = survey.SURVEY_STATE_FAIL
            }
            surveyHitResultRecordService.updateSurveyHitResultRecord(hitResult)
          }else{
            logPutHitResult.calltime = new Date()
            logPutHitResult.asjfsddXzqhdm = if(hitResult.szFingerID.startsWith("F")) hitResult.szFingerID.drop(1).take(6).toLong else hitResult.szFingerID.take(6).toLong
            logPutHitResult.xczwYsxtXczzhwbh = hitResult.szFingerID
            logPutHitResult.nyzwZzhwkbh = hitResult.szHitFingerID
            logPutHitResult.nyzwZzhwdm = if(hitResult.nHitFgp.toInt < 10) "0" + hitResult.nHitFgp.toString else hitResult.nHitFgp.toString
            logPutHitResult.errormsg = "未获取到现场勘验编号。1.现场指纹数据是否为现勘系统获取来的 2.请查验现场指纹数据信息是否异常"
            logPutHitResult.returnstatus = SurveyConstant.GET_HITRESULT_PACKAGE_EXCEPTION
            hitResult.nState = survey.SURVEY_STATE_FAIL
            surveyHitResultRecordService.updateSurveyHitResultRecord(hitResult)
          }
        }catch {
          case ex:Exception =>
            error("fPT50HandprintServiceClient-sendHitResult:{},currentTime:{}"
              ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
            )
            logPutHitResult.calltime = new Date()
            logPutHitResult.asjfsddXzqhdm = if(hitResult.szFingerID.startsWith("F")) hitResult.szFingerID.drop(1).take(6).toLong else hitResult.szFingerID.take(6).toLong
            logPutHitResult.xczwYsxtXczzhwbh = hitResult.szFingerID
            logPutHitResult.nyzwZzhwkbh = hitResult.szHitFingerID
            logPutHitResult.nyzwZzhwdm = if(hitResult.nHitFgp.toInt < 10) "0" + hitResult.nHitFgp.toString else hitResult.nHitFgp.toString
            logPutHitResult.errormsg = ExceptionUtil.getStackTraceInfo(ex)
            hitResult.nState = survey.SURVEY_STATE_FAIL
            surveyHitResultRecordService.updateSurveyHitResultRecord(hitResult)
        }
        if(provider.nonEmpty){
          logPutHitResult.pkId = UUID.randomUUID().toString.replace("-","")
          logPutHitResult.username = hallWebserviceConfig.handprintService.user
          logPutHitResult.returntime = new Date()
          provider.get.addHitResultInfo(logPutHitResult)
          }
        }
      }
    }

  def checkJinZhiSendHitResultService():Unit = {
    info("start checkJinZhiSendHitResultService")
    if(hallWebserviceConfig.handprintService.area != null){
      if(LogInterfacestatus.find_by_asjfsddXzqhdm_and_interfacename(hallWebserviceConfig.handprintService.area.toInt,"sendHitResult").nonEmpty){
        info("update-logInterfaceStatus,行政区划{},接口名称{}",hallWebserviceConfig.handprintService.area,"sendHitResult")
        LogInterfacestatus.update.set(calltime = new Date()).where(LogInterfacestatus.asjfsddXzqhdm === hallWebserviceConfig.handprintService.area.toInt and LogInterfacestatus.interfacename === "sendHitResult").execute
      }else{
        val logInterfaceStatus = new LogInterfacestatus()
        info("insert-logInterfaceStatus,行政区划{},接口名称{}",hallWebserviceConfig.handprintService.area,"sendHitResult")
        logInterfaceStatus.pkId = UUID.randomUUID().toString.replace("-","")
        logInterfaceStatus.interfacename = "sendHitResult"
        logInterfaceStatus.schedule = CronExpParser.translate(hallWebserviceConfig.handprintService.sendHitCron)
        logInterfaceStatus.asjfsddXzqhdm = hallWebserviceConfig.handprintService.area.toInt
        logInterfaceStatus.calltime = new Date()
        logInterfaceStatus.save()
      }
    }
    info("end checkJinZhiSendHitResultService")
  }
}
