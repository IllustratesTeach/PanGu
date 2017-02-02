package nirvana.hall.v62.services

import nirvana.hall.api.internal.BaseJniLoader
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.v62.BaseV62TestCase
import org.junit.{Assert, Test}

/**
  * Created by songpeng on 2017/1/24.
  */
class FPTServiceImplTest extends BaseV62TestCase with BaseJniLoader{

  @Test
  def test_getLogic02Rec: Unit ={
    val service = getService[FPTService]
    val logic02Rec = service.getLogic02Rec("1234567890")
    Assert.assertNotNull(logic02Rec)
  }
}
