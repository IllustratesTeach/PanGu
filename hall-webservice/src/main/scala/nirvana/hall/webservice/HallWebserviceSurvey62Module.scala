package nirvana.hall.webservice

import nirvana.hall.webservice.internal.survey.gafis62.{FPT50HandprintServiceCron, SurveyConfigServiceImpl, SurveyHitResultRecordServiceImpl, SurveyRecordServiceImpl}
import nirvana.hall.webservice.services.survey.{SurveyConfigService, SurveyHitResultRecordService, SurveyRecordService}
import org.apache.tapestry5.ioc.ServiceBinder

/**
  * Created by songpeng on 2018/1/19.
  */
object HallWebserviceSurvey62Module {

  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[FPT50HandprintServiceCron], classOf[FPT50HandprintServiceCron]).eagerLoad()
    binder.bind(classOf[SurveyConfigService], classOf[SurveyConfigServiceImpl])
    binder.bind(classOf[SurveyRecordService], classOf[SurveyRecordServiceImpl])
    binder.bind(classOf[SurveyHitResultRecordService], classOf[SurveyHitResultRecordServiceImpl])
  }
}