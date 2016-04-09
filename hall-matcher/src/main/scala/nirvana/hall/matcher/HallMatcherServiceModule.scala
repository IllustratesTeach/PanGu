package nirvana.hall.matcher

import nirvana.hall.matcher.internal.adapter.daku.{GetMatchTaskServiceImpl, SyncDataServiceImpl}
import nirvana.hall.matcher.service.{GetMatchTaskService, SyncDataService}
import org.apache.tapestry5.ioc.ServiceBinder

/**
 * Created by songpeng on 16/3/25.
 */
object HallMatcherServiceModule {

  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[SyncDataService], classOf[SyncDataServiceImpl])
    binder.bind(classOf[GetMatchTaskService], classOf[GetMatchTaskServiceImpl])
  }
}
