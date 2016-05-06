package nirvana.hall.matcher

import nirvana.hall.matcher.internal.adapter.sh.{PutMatchProgressServiceImpl, PutMatchResultServiceImpl, GetMatchTaskServiceImpl, SyncDataServiceImpl}
import nirvana.hall.matcher.service.{PutMatchProgressService, PutMatchResultService, GetMatchTaskService, SyncDataService}
import org.apache.tapestry5.ioc.ServiceBinder

/**
 * Created by songpeng on 16/5/6.
 */
object HallMatcherShServiceModule {
  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[SyncDataService], classOf[SyncDataServiceImpl])
    binder.bind(classOf[GetMatchTaskService], classOf[GetMatchTaskServiceImpl])
    binder.bind(classOf[PutMatchResultService], classOf[PutMatchResultServiceImpl])
    binder.bind(classOf[PutMatchProgressService], classOf[PutMatchProgressServiceImpl])
  }
}
