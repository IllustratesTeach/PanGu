package nirvana.hall.api

import nirvana.hall.api.internal.sync.{SyncCronFilterFPTServiceImpl, SyncCronServiceImpl}
import nirvana.hall.api.services.sync.{SyncCronFilterFPTService, SyncCronService}
import org.apache.tapestry5.ioc.ServiceBinder

/**
  * API定时任务module,绑定所有定时任务service
  * Created by songpeng on 2017/12/1.
  */
object LocalApiCronServiceModule {
  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[SyncCronService], classOf[SyncCronServiceImpl]).eagerLoad()
    binder.bind(classOf[SyncCronFilterFPTService],classOf[SyncCronFilterFPTServiceImpl]).eagerLoad
  }
}
