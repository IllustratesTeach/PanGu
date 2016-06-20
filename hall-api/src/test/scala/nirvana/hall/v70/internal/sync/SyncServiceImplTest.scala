package nirvana.hall.v70.internal.sync

import nirvana.hall.api.services.sync.SyncService
import nirvana.hall.v70.internal.BaseV70TestCase
import org.junit.Test

/**
 * Created by songpeng on 16/6/19.
 */
class SyncServiceImplTest extends BaseV70TestCase{

  @Test
  def test_sync(): Unit ={
    val service = getService[SyncService]
    service.doWork()
  }
}
