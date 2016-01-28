package nirvana.hall.v70.internal.sync

import nirvana.hall.v70.services.sync.Sync7to6Service
import nirvana.hall.v70.jpa.SyncQueue
import org.apache.tapestry5.ioc.RegistryBuilder
import org.junit.Test

/**
 * Created by songpeng on 15/12/4.
 */
class Sync7to6ServiceImplTest {
  val modules = Seq[String](
    "nirvana.hall.api.internal.JpaTestModule",
    "nirvana.hall.api.LocalProtobufModule",
    "nirvana.hall.v70.LocalDataSourceModule",
    "nirvana.hall.orm.HallOrmModule",
    "nirvana.hall.v70.LocalV70ServiceModule"
    ).map(Class.forName)
  val registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)
  @Test
  def test_sync(): Unit ={
    val syncService = registry.getService(classOf[Sync7to6Service])
    val syncQueue = SyncQueue.find("11")
    syncService.doTaskOfSyncQueue(syncQueue)

  }

}
