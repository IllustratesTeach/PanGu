package nirvana.hall.webservice.xingzhuan

import javax.activation.DataHandler

import junit.framework.Assert
import nirvana.hall.webservice.services.xingzhuan.FingerPalmAppServer
import org.apache.axiom.attachments.ByteArrayDataSource
import org.junit.Test
import stark.webservice.services.StarkWebServiceClient

import scala.io.Source

/**
  * Created by songpeng on 2017/11/3.
  */
class FingerPalmAppServerTest {//extends BaseTestCase {
  @Test
  def test_sendFingerPrint(): Unit ={
    val service: FingerPalmAppServer = StarkWebServiceClient.createClient(classOf[FingerPalmAppServer], "http://localhost:12345/ws/FingerPalmAppServer", "http://www.egfit.com/")
    val xmlData = Source.fromInputStream(getClass.getResourceAsStream("/NYZZW-5224010107772013070452.ftpx")).mkString //TODO 构建xml数据
    val file = new DataHandler(new ByteArrayDataSource(xmlData.getBytes))
    val dh = service.sendFingerPrint("","", file)
    Assert.assertNotNull(dh)
  }

  @Test
  def test_sendLatent: Unit ={

  }
}
