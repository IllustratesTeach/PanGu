package nirvana.hall.webservice

import nirvana.hall.webservice.internal.bjwcsy.{SendMatchResultService, Union4pfmipCronService, WsFingerServiceImpl}
import nirvana.hall.webservice.internal.survey.gz.recordmod.SurveyRecordImpl
import nirvana.hall.webservice.internal.survey.gz.{GetDateServiceCronService, HandprintServiceCronService}
import nirvana.hall.webservice.services.bjwcsy.WsFingerService
import nirvana.hall.webservice.services.survey.SurveyRecord
import org.apache.tapestry5.ioc.ServiceBinder

/**
  * Created by ssj on 2017/9/27.
  */
object HallWebserviceSurveyModule {


  def bind(binder: ServiceBinder) {

    binder.bind(classOf[SurveyRecord], classOf[SurveyRecordImpl]).withSimpleId()
    binder.bind(classOf[HandprintServiceCronService],classOf[HandprintServiceCronService])//.eagerLoad()
    binder.bind(classOf[GetDateServiceCronService],classOf[GetDateServiceCronService]).eagerLoad()
  }
}
