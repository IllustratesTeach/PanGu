package nirvana.hall.webservice.v70

import java.io.File
import javax.activation.{DataHandler, FileDataSource}

import nirvana.hall.webservice.services.haixin.WsHaiXinFingerService
import org.junit.{Assert, Test}

/**
  * Created by yuchen on 2017/9/26.
  */
class HaixinWsFingerServiceImpl70Test extends BaseTestCase{
  @Test
  def test_setFinger: Unit ={
    val dataHandler = new DataHandler(new FileDataSource(new File("D://R2100000000002017090199.FPT")))
    val service = getService[WsHaiXinFingerService]
    val result = service.setFinger("1701","0101","3701","R2100000000002017090199",dataHandler)
    Assert.assertEquals(1,result)
  }
}
