package nirvana.hall.v70.internal.sync

import nirvana.hall.v70.internal.BaseV70TestCase
import nirvana.hall.v70.jpa.SyncQueue
import nirvana.hall.v70.services.sync.Sync7to6Service
import org.junit.Test

/**
 * Created by songpeng on 15/12/4.
 */
class Sync7to6ServiceImplTest extends BaseV70TestCase{
  @Test
  def test_sync(): Unit ={
    val syncService = getService[Sync7to6Service]
    val syncQueue = SyncQueue.find("11")
    syncService.doTaskOfSyncQueue(syncQueue)

  }

}
