package nirvana.hall.webservice

import java.io.File
import javax.activation.{DataHandler, FileDataSource}

import nirvana.hall.webservice.services.haixin.WsHaiXinFingerService
import org.junit.Test
import stark.webservice.services.StarkWebServiceClient

/**
  * Created by yuchen on 2017/4/8.
  */
class WebServicesClient {


  @Test
  def test_HaiXin_setFinger(): Unit = {
    try{
      val dataHandler = new DataHandler(new FileDataSource(new File("/Users/yuchen/fpt/A1200000000002006060707.fpt")))
      val client = StarkWebServiceClient.createClient(classOf[WsHaiXinFingerService],"http://localhost:8080" + "/ws/WsHaiXinFingerService?wsdl","http://www.gafis.com/")
      val result = client.setFinger("01","01","001","0001",dataHandler)
      assert(null != result ,result)
    }catch{
      case e:Exception => println("ERROR:" + e.getMessage)
    }
  }
}
