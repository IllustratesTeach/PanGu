package nirvana.hall.v70

import nirvana.hall.api.internal.query.{QueryGet7to6ServiceImpl, Query7to6ServiceImpl}
import nirvana.hall.api.internal.sync.{Sync7to6ServiceImpl, SyncDictServiceImpl}
import nirvana.hall.api.services.query.{QueryGet7to6Service, Query7to6Service}
import nirvana.hall.api.services.sync.{Sync7to6Service, SyncDictService}
import org.apache.tapestry5.ioc.ServiceBinder

/**
 * Created by songpeng on 16/1/25.
 */
object LocalV70ServiceModule {
  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[SyncDictService], classOf[SyncDictServiceImpl]).eagerLoad()
    binder.bind(classOf[Sync7to6Service], classOf[Sync7to6ServiceImpl]).eagerLoad()
    binder.bind(classOf[Query7to6Service], classOf[Query7to6ServiceImpl]).eagerLoad()
    binder.bind(classOf[QueryGet7to6Service], classOf[QueryGet7to6ServiceImpl]).eagerLoad()

  }

}
