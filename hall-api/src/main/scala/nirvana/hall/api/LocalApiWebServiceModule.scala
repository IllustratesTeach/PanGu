package nirvana.hall.api

import nirvana.hall.api.webservice.internal.Union4pfmipCronService
import nirvana.hall.api.webservice.services.WsFingerService
import nirvana.hall.api.webservice.services.internal.WsFingerServiceImpl
import org.apache.tapestry5.ioc.ServiceBinder

/**
  * Created by songpeng on 16/11/28.
  */
object LocalApiWebServiceModule {
  def bind(binder: ServiceBinder) {
    binder.bind(classOf[WsFingerService], classOf[WsFingerServiceImpl]).withSimpleId()
    binder.bind(classOf[Union4pfmipCronService], classOf[Union4pfmipCronService]).eagerLoad()
  }
}
