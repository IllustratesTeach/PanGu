package nirvana.hall.api

import nirvana.hall.api.internal.query.{QueryGet7to6ServiceImpl, Query7to6ServiceImpl}
import nirvana.hall.api.internal.sync._
import nirvana.hall.api.services.query.{QueryGet7to6Service, Query7to6Service}
import nirvana.hall.api.services.sync._
import org.apache.tapestry5.ioc.ServiceBinder

/**
 * api sync schedule module
 * Created by songpeng on 15/12/7.
 */
object LocalApiSyncModule {
  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[SyncDictService], classOf[SyncDictServiceImpl]).eagerLoad()
    binder.bind(classOf[Sync62Service], classOf[Sync62ServiceImpl]).eagerLoad()
    binder.bind(classOf[Query7to6Service], classOf[Query7to6ServiceImpl]).eagerLoad()
    binder.bind(classOf[QueryGet7to6Service], classOf[QueryGet7to6ServiceImpl]).eagerLoad()

  }
}
