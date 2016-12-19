package nirvana.hall.v62.webservice

import nirvana.hall.api.webservice.services.WsFingerService
import nirvana.hall.v62.BaseV62TestCase
import org.junit.Test

/**
  * Created by songpeng on 2016/12/5.
  */
class WsFingerServiceImplTest extends BaseV62TestCase{

  @Test
  def test_getTenprintFinger: Unit ={
    val service = getService[WsFingerService]
    val obj = service.getTenprintFinger("", "", "1234567890")
    println(obj)
  }

  @Test
  def test_getLatent: Unit ={
    val service = getService[WsFingerService]
    val dataHandler = service.getLatent("", "", "123456", null, null, null, null, null, null, null)
    println(dataHandler)
  }
}
