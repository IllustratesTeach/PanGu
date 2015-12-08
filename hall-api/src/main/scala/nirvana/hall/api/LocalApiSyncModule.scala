package nirvana.hall.api

import nirvana.hall.api.internal.sync._
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

    binder.bind(classOf[Sync62TPCardService], classOf[Sync62TPCardService])
    binder.bind(classOf[Sync62CaseService], classOf[Sync62CaseService])
    binder.bind(classOf[Sync62LPCardService], classOf[Sync62LPCardService])

  }
}
