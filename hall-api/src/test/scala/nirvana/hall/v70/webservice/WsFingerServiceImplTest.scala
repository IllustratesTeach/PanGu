package nirvana.hall.v70.webservice

import java.io.FileOutputStream

import nirvana.hall.api.internal.BaseJniLoader
import nirvana.hall.api.webservice.services.WsFingerService
import nirvana.hall.v70.internal.BaseV70TestCase
import org.junit.Test

/**
  * Created by songpeng on 2017/1/14.
  */
class WsFingerServiceImplTest extends BaseV70TestCase with BaseJniLoader{

  @Test
  def test_getTenprintFinger: Unit ={
    val service = getService[WsFingerService]
    val dataHandler = service.getTenprintFinger("", "", "1234567890")
    dataHandler.writeTo(new FileOutputStream("/Users/songpeng/win7共享/tenprintFinger.fpt"))
  }
}
