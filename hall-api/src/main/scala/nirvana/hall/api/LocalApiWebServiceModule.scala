package nirvana.hall.api

import nirvana.hall.api.internal.fpt.FPTServiceImpl
import nirvana.hall.api.internal.remote.HallImageRemoteServiceImpl
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.api.webservice.internal.{SendMatchResultService, Union4pfmipCronService}
import nirvana.hall.api.webservice.services.WsFingerService
import nirvana.hall.api.webservice.services.internal.WsFingerServiceImpl
import org.apache.tapestry5.ioc.ServiceBinder

/**
  * Created by songpeng on 16/11/28.
  */
object LocalApiWebServiceModule {
  def bind(binder: ServiceBinder) {
    binder.bind(classOf[FPTService], classOf[FPTServiceImpl])
    binder.bind(classOf[HallImageRemoteService], classOf[HallImageRemoteServiceImpl])
    binder.bind(classOf[WsFingerService], classOf[WsFingerServiceImpl]).withSimpleId()
    binder.bind(classOf[Union4pfmipCronService], classOf[Union4pfmipCronService]).eagerLoad()
    binder.bind(classOf[SendMatchResultService], classOf[SendMatchResultService]).eagerLoad()
  }
}
