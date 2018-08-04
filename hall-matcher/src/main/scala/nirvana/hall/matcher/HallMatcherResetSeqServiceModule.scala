package nirvana.hall.matcher

import nirvana.hall.matcher.internal.adapter.gafis6.PutMatchProgressServiceImpl
import nirvana.hall.matcher.internal.adapter.reset.{GetMatchTaskServiceImpl, PutMatchResultServiceImpl, SyncDataServiceImpl}
import nirvana.hall.matcher.service.{GetMatchTaskService, PutMatchProgressService, PutMatchResultService, SyncDataService}
import org.apache.tapestry5.ioc.ServiceBinder

object HallMatcherResetSeqServiceModule {

  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[SyncDataService], classOf[SyncDataServiceImpl])
      binder.bind(classOf[GetMatchTaskService], classOf[GetMatchTaskServiceImpl])
      binder.bind(classOf[PutMatchResultService], classOf[PutMatchResultServiceImpl])
    binder.bind(classOf[PutMatchProgressService], classOf[PutMatchProgressServiceImpl])
  }
}
