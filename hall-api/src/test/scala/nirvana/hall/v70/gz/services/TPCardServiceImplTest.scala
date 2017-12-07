package nirvana.hall.v70.gz.services

import nirvana.hall.api.services.TPCardService
import nirvana.hall.v70.internal.BaseV70TestCase
import org.junit.{Assert, Test}

/**
  * Created by songpeng on 2017/5/26.
  */
class TPCardServiceImplTest extends BaseV70TestCase{

  @Test
  def test_getTPCard: Unit ={
    val service = getService[TPCardService]
    val tpCard = service.getTPCard("1234567890")
    Assert.assertEquals(tpCard.getStrCardID, "1234567890")
  }
}
