package nirvana.hall.v70.internal.sync

import nirvana.hall.v70.internal.BaseV70TestCase
import nirvana.hall.v70.services.sync.Sync6to7Service
import org.junit.Test

/**
 * Created by songpeng on 16/6/19.
 */
class Sync6to7ServiceImplTest extends BaseV70TestCase{

  @Test
  def test_sync(): Unit ={
    val service = getService[Sync6to7Service]

    service.doWork()
  }
}
