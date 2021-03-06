package nirvana.hall.webservice

import nirvana.hall.webservice.internal.survey.PlatformOperatorInfoProvider
import nirvana.hall.webservice.internal.survey.gafis62._
import nirvana.hall.webservice.services.survey.{SurveyConfigService, SurveyHitResultRecordService, SurveyRecordService}
import org.apache.tapestry5.ioc.{Configuration, ServiceBinder}

/**
  * Created by songpeng on 2018/1/19.
  */
object HallWebserviceSurvey62Module {

  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[PlatformOperatorInfoProvider],classOf[PlatformOperatorInfoProvider])
    binder.bind(classOf[FPT50HandprintServiceClient],classOf[FPT50HandprintServiceClient])
    binder.bind(classOf[FPT50HandprintServiceCron], classOf[FPT50HandprintServiceCron]).eagerLoad()
    binder.bind(classOf[FPT50HandprintLatentPackageCron], classOf[FPT50HandprintLatentPackageCron]).eagerLoad()
    binder.bind(classOf[FPT50HandprintSendHitResultCron], classOf[FPT50HandprintSendHitResultCron]).eagerLoad()
    binder.bind(classOf[FPT50HandPrintGetCaseNoCron], classOf[FPT50HandPrintGetCaseNoCron]).eagerLoad()
    binder.bind(classOf[FPT50HandprintHaiXinServiceCron],classOf[FPT50HandprintHaiXinServiceCron]).eagerLoad()
    binder.bind(classOf[SurveyConfigService], classOf[SurveyConfigServiceImpl])
    binder.bind(classOf[SurveyRecordService], classOf[SurveyRecordServiceImpl])
    binder.bind(classOf[SurveyHitResultRecordService], classOf[SurveyHitResultRecordServiceImpl])
    binder.bind(classOf[SurveyTableMaintenanceService],classOf[SurveyTableMaintenanceService])
  }

  def contributeEntityManagerFactory(configuration:Configuration[String]): Unit ={
    configuration.add("nirvana.hall.webservice.jpa")
  }
}
