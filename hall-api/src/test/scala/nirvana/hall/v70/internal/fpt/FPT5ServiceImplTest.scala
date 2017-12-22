package nirvana.hall.v70.internal.fpt

import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.v70.internal.BaseV70TestCase
import org.junit.{Assert, Test}

/**
  * Created by songpeng on 2017/12/5.
  */
class FPT5ServiceImplTest extends BaseV70TestCase{

  @Test
  def test_getLatentPackage : Unit ={
    val service = getService[FPT5Service]
    val latentPackege = service.getLatentPackage("A5200001111111111222222")
    Assert.assertNotNull(latentPackege)

  }
}
