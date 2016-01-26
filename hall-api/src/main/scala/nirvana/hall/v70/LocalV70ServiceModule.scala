package nirvana.hall.v70

import nirvana.hall.v70.internal.query.{Query7to6ServiceImpl, QueryGet7to6ServiceImpl}
import nirvana.hall.v70.internal.sync.{Sync7to6ServiceImpl, SyncDictServiceImpl}
import nirvana.hall.v70.services.query.{Query7to6Service, QueryGet7to6Service}
import nirvana.hall.v70.services.sync.{Sync7to6Service, SyncDictService}
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
