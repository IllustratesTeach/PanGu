package nirvana.hall.webservice.fpt

import javax.activation.DataHandler

import nirvana.hall.webservice.services.fpt.FingerPalmAppServer
import org.apache.axiom.attachments.ByteArrayDataSource
import org.junit.Test
import stark.webservice.services.StarkWebServiceClient

/**
  * Created by songpeng on 2017/11/3.
  */
class FingerPalmAppServerTest {

  @Test
  def test_sendFingerPrint(): Unit ={
    val service: FingerPalmAppServer = StarkWebServiceClient.createClient(classOf[FingerPalmAppServer], "", "")
    val xmlData = "" //TODO 构建xml数据
    val file = new DataHandler(new ByteArrayDataSource(xmlData.getBytes))
    service.sendFingerPrint("","", file)
  }
}
