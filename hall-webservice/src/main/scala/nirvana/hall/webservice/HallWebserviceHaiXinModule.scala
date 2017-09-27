package nirvana.hall.webservice



import nirvana.hall.webservice.internal.haixin.{StrategyServiceImpl, SyncCronSendQueryServiceImpl, WsHaiXinFingerServiceImpl}
import nirvana.hall.webservice.services.haixin.{StrategyService, SyncCronSendQueryService, WsHaiXinFingerService}
import org.apache.tapestry5.ioc.ServiceBinder

/**
  * Created by yuchen on 2017/9/27.
  */
object HallWebserviceHaiXinModule {

  def bind(binder: ServiceBinder) {

    binder.bind(classOf[StrategyService], classOf[StrategyServiceImpl])
    binder.bind(classOf[WsHaiXinFingerService],classOf[WsHaiXinFingerServiceImpl]).withSimpleId
    binder.bind(classOf[SyncCronSendQueryService],classOf[SyncCronSendQueryServiceImpl]).eagerLoad
  }
}
