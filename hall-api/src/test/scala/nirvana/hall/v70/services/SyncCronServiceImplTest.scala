package nirvana.hall.v70.services

import nirvana.hall.api.services.sync.SyncCronService
import nirvana.hall.v70.internal.BaseV70TestCase
import org.junit.Test

/**
 * Created by songpeng on 16/6/19.
 */
class SyncCronServiceImplTest extends BaseV70TestCase{

  @Test
  def test_syncCron(): Unit ={
    val service = getService[SyncCronService]
    service.doWork()
  }
}
