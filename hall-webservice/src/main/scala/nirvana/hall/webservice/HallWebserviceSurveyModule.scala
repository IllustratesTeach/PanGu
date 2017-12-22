package nirvana.hall.webservice

import nirvana.hall.webservice.internal.survey.gz.recordmod.SurveyRecordImpl
import nirvana.hall.webservice.internal.survey.gz.{GetDateServiceCronService, GetReceptionNoServiceCronService, HandprintServiceCronService, SendHitServiceCronService}
import nirvana.hall.webservice.services.survey.gz.SurveyRecordService
import org.apache.tapestry5.ioc.ServiceBinder

/**
  * Created by ssj on 2017/9/27.
  */
object HallWebserviceSurveyModule {


  def bind(binder: ServiceBinder) {

    binder.bind(classOf[SurveyRecordService], classOf[SurveyRecordImpl]).withSimpleId()
    binder.bind(classOf[HandprintServiceCronService]).eagerLoad
    binder.bind(classOf[GetDateServiceCronService])//.eagerLoad
    binder.bind(classOf[GetReceptionNoServiceCronService])//.eagerLoad
    binder.bind(classOf[SendHitServiceCronService])//.eagerLoad
  }
}
