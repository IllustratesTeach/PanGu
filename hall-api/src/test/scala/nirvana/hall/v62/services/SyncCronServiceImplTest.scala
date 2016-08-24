package nirvana.hall.v62.services

import nirvana.hall.api.services.sync.SyncCronService
import nirvana.hall.v62.BaseV62TestCase
import org.junit.Test

/**
 * Created by songpeng on 16/8/19.
 */
class SyncCronServiceImplTest extends BaseV62TestCase{

  @Test
  def test_syncCron(): Unit ={
    val service = getService[SyncCronService]
    service.doWork()
  }
}
