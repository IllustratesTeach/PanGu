package nirvana.hall.webservice.internal.survey.gafis62

import java.net.{ConnectException, HttpURLConnection, URL}
import java.util.Date

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.webservice.CronExpParser
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.survey.{PlatformOperatorInfoProvider, PlatformOperatorInfoProviderLoader, SurveyConstant}
import nirvana.hall.webservice.jpa.survey.LogHaiXinservicestatus
import nirvana.hall.webservice.services.survey.{SurveyConfigService, SurveyHitResultRecordService, SurveyRecordService}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

/**
  * Created by songpeng on 2017/12/24.
  * 现场勘验系统接口定时服务
  */
class FPT50HandprintHaiXinServiceCron(hallWebserviceConfig: HallWebserviceConfig) extends LoggerSupport{

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallWebserviceConfig.handprintService.checkHaiXinServiceCron!= null){
      println(hallWebserviceConfig.handprintService.checkHaiXinServiceCron)
      println(CronExpParser.translate(hallWebserviceConfig.handprintService.checkHaiXinServiceCron))
      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.handprintService.checkHaiXinServiceCron, StartAtDelay), "survey-cron-checkHaiXinService", new Runnable {
        override def run(): Unit = {
          try {
            info("begin checkHaiXinService")
            LogHaiXinservicestatus.all.foreach{
              haixinService =>
                var addr = ""
                var xzqh = 0
                try{
                  if(haixinService.interfaceaddr.nonEmpty){
                    addr = haixinService.interfaceaddr
                    xzqh = haixinService.asjfsddXzqhdm
                    info("检查海鑫服务地址{},行政区划{}",addr,xzqh)
                    val connection = new URL(haixinService.interfaceaddr).openConnection().asInstanceOf[HttpURLConnection]
                    if(HttpURLConnection.HTTP_OK==connection.getResponseCode){
                      info("正常更新海鑫服务状态{},行政区划{}",haixinService.interfaceaddr,haixinService.asjfsddXzqhdm)
                      LogHaiXinservicestatus.update.set(status = SurveyConstant.HAIXIN_SERVICE_NORMAL_STATUS, calltime = new Date()).where(LogHaiXinservicestatus.pkId === haixinService.pkId).execute
                    }else{
                      info("异常更新海鑫服务状态{},行政区划{}",haixinService.interfaceaddr,haixinService.asjfsddXzqhdm)
                      LogHaiXinservicestatus.update.set(status = SurveyConstant.HAIXIN_SERVICE_EXCEPTION_STATUS, calltime = new Date()).where(LogHaiXinservicestatus.pkId === haixinService.pkId).execute
                    }
                  }
                }catch {
                  case ex:ConnectException =>
                    info("异常更新海鑫服务状态{},行政区划{}",addr,xzqh)
                    LogHaiXinservicestatus.update.set(status = SurveyConstant.HAIXIN_SERVICE_EXCEPTION_STATUS, calltime = new Date()).where(LogHaiXinservicestatus.asjfsddXzqhdm === xzqh).execute
                }
            }
            info("end  checkHaiXinService")
          } catch {
            case ex: Exception =>
              error("checkHaiXinService-error:{},currentTime:{}"
                ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
              )
          }
        }
      })
    }
  }
}
