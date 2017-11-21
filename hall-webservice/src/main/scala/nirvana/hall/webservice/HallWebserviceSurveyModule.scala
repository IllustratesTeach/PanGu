package nirvana.hall.webservice

import nirvana.hall.webservice.internal.survey.gz.recordmod.SurveyRecordImpl
import nirvana.hall.webservice.internal.survey.gz.{GetDateServiceCronService, HandprintServiceCronService}
import nirvana.hall.webservice.services.survey.gz.SurveyRecordService
import org.apache.tapestry5.ioc.ServiceBinder

/**
  * Created by ssj on 2017/9/27.
  */
object HallWebserviceSurveyModule {


  def bind(binder: ServiceBinder) {

    binder.bind(classOf[SurveyRecordService], classOf[SurveyRecordImpl]).withSimpleId()
    binder.bind(classOf[HandprintServiceCronService],classOf[HandprintServiceCronService]).eagerLoad
    binder.bind(classOf[GetDateServiceCronService],classOf[GetDateServiceCronService]).eagerLoad
  }
}
