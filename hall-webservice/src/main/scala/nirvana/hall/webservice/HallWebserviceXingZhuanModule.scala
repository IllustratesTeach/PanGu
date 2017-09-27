package nirvana.hall.webservice


import nirvana.hall.webservice.internal.{TaskHandlerServiceImpl, TenPrinterExportServiceImpl}
import nirvana.hall.webservice.internal.xingzhuan.{AssistcheckServiceImpl, LocalCheckinServiceImpl, SyncSyCronServiceImpl, _}
import nirvana.hall.webservice.services.{TaskHandlerService, TenPrinterExportService}
import nirvana.hall.webservice.services.xingzhuan.{AssistcheckService, LocalCheckinService, SyncSyCronService, _}
import org.apache.tapestry5.ioc.ServiceBinder

/**
  * Created by yuchen on 2017/9/27.
  */
object HallWebserviceXingZhuanModule {

  def bind(binder: ServiceBinder) {

    binder.bind(classOf[WsSendQueryService], classOf[WsSendQueryServiceImpl]) //.withSimpleId()
    binder.bind(classOf[TenPrinterExportService], classOf[TenPrinterExportServiceImpl])
    binder.bind(classOf[SendQueryService], classOf[SendQueryServiceImpl])
    binder.bind(classOf[TaskHandlerService], classOf[TaskHandlerServiceImpl])
    binder.bind(classOf[FetchFPTService], classOf[FetchFPTServiceImpl])
    binder.bind(classOf[HallDatasourceService], classOf[HallDatasourceServiceImpl])

    binder.bind(classOf[FetchLPCardExportService], classOf[FetchLPCardExportServiceImpl])
    binder.bind(classOf[xingzhuanTaskCronService], classOf[xingzhuanTaskCronServiceImpl])
    binder.bind(classOf[SendCheckinService], classOf[SendCheckinServiceImpl]) //.eagerLoad()
    binder.bind(classOf[UploadCheckinService], classOf[UploadCheckinServiceImpl]) //.eagerLoad()
    //============================================================================//
    binder.bind(classOf[TenPrinterCronService], classOf[TenPrinterCronServiceImpl]) //.eagerLoad()
    binder.bind(classOf[LatentCronService], classOf[LatentCronServiceImpl]) //.eagerLoad()
    binder.bind(classOf[AssistcheckService], classOf[AssistcheckServiceImpl]) //.eagerLoad()
    binder.bind(classOf[LocalCheckinService], classOf[LocalCheckinServiceImpl]) //.eagerLoad()
    binder.bind(classOf[SyncSyCronService], classOf[SyncSyCronServiceImpl]) //.eagerLoad()
  }
}
