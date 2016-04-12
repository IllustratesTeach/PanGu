package nirvana.hall.matcher

import nirvana.hall.matcher.internal.adapter.daku.{PutMatchResultServiceImpl, GetMatchTaskServiceImpl, SyncDataServiceImpl}
import nirvana.hall.matcher.service.{PutMatchResultService, GetMatchTaskService, SyncDataService}
import org.apache.tapestry5.ioc.ServiceBinder

/**
 * Created by songpeng on 16/3/25.
 */
object HallMatcherDakuServiceModule {

  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[SyncDataService], classOf[SyncDataServiceImpl])
    binder.bind(classOf[GetMatchTaskService], classOf[GetMatchTaskServiceImpl])
    binder.bind(classOf[PutMatchResultService], classOf[PutMatchResultServiceImpl])
  }
}
