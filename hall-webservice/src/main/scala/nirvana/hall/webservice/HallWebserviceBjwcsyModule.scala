package nirvana.hall.webservice


import nirvana.hall.webservice.internal.bjwcsy.{SendMatchResultService, Union4pfmipCronService, WsFingerServiceImpl}
import nirvana.hall.webservice.services.bjwcsy.WsFingerService
import org.apache.tapestry5.ioc.ServiceBinder

/**
  * Created by yuchen on 2017/9/27.
  */
object HallWebserviceBjwcsyModule {

  def bind(binder: ServiceBinder) {

    binder.bind(classOf[WsFingerService], classOf[WsFingerServiceImpl]).withSimpleId()
    binder.bind(classOf[Union4pfmipCronService], classOf[Union4pfmipCronService]).eagerLoad()
    binder.bind(classOf[SendMatchResultService], classOf[SendMatchResultService]).eagerLoad()
  }
}
