package nirvana.hall.api.internal.sync

import nirvana.hall.api.entities.SyncQueue
import nirvana.hall.api.services.sync.Sync7to6Service
import org.apache.tapestry5.ioc.RegistryBuilder
import org.junit.Test

/**
 * Created by songpeng on 15/12/4.
 */
class Sync62ServiceImplTest {
  val modules = Seq[String](
    "nirvana.hall.api.LocalDataSourceModule",
    "nirvana.hall.api.LocalProtobufModule",
    "nirvana.hall.api.LocalApiServiceModule",
    "nirvana.hall.api.LocalApiSyncModule",
    "nirvana.hall.v62.LocalV62ServiceModule",
    "nirvana.hall.v62.internal.filter.TestModule",
    "nirvana.hall.api.internal.TestModule").map(Class.forName)
  val registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)
  @Test
  def test_sync(): Unit ={
    val syncService = registry.getService(classOf[Sync7to6Service])
    val syncQueue = SyncQueue.find("8a8187e45165db66015165de33d10048").get
    syncService.doWork(syncQueue)

  }

}
