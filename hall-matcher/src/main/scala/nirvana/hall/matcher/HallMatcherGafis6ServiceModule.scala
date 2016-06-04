package nirvana.hall.matcher

import nirvana.hall.matcher.internal.adapter.gafis6.{GetMatchTaskServiceImpl, PutMatchProgressServiceImpl, PutMatchResultServiceImpl, SyncDataServiceImpl}
import nirvana.hall.matcher.service.{GetMatchTaskService, PutMatchProgressService, PutMatchResultService, SyncDataService}
import org.apache.tapestry5.ioc.ServiceBinder

/**
 * Created by songpeng on 16/3/25.
 */
object HallMatcherGafis6ServiceModule {

  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[SyncDataService], classOf[SyncDataServiceImpl])
    binder.bind(classOf[GetMatchTaskService], classOf[GetMatchTaskServiceImpl])
    binder.bind(classOf[PutMatchResultService], classOf[PutMatchResultServiceImpl])
    binder.bind(classOf[PutMatchProgressService], classOf[PutMatchProgressServiceImpl])
  }
}
