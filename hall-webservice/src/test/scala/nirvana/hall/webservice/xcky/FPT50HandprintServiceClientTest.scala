package nirvana.hall.webservice.xcky

import monad.support.services.XmlLoader
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.survey.gafis62.{FPT50HandprintServiceClient, FPT50HandprintServiceConstants, FingerPrintListResponse}
import org.junit.{Assert, Test}

import scala.io.Source

/**
  * Created by songpeng on 2017/12/24.
  */
class FPT50HandprintServiceClientTest {

  val client = new FPT50HandprintServiceClient(buildHallWebserviceConfig.handprintService)
  def buildHallWebserviceConfig = {
    val content = Source.fromInputStream(getClass.getResourceAsStream("/test-webservice.xml"),"utf8").mkString
    XmlLoader.parseXML[HallWebserviceConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/webservice/webservice.xsd")))
  }

  @Test
  def test_parseXML_FingerPrintListResponse: Unit ={
    val xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<list>\n<k>\n<ajmc>案件名称</ajmc>\n<xckybh>现场勘验编号</xckybh>\n<xcwzbh>现场物证编号</xcwzbh>\n</k>\n</list>"
    val fingerPrintListResponse = XmlLoader.parseXML[FingerPrintListResponse](xml)
    Assert.assertEquals(fingerPrintListResponse.list(0).ajmc, "案件名称")
  }
  @Test
  def test_getSystemDateTime: Unit ={
    val time = client.getSystemDateTime()
    Assert.assertNotNull(time)
  }
  @Test
  def test_getLatentCount: Unit ={
    val count = client.getLatentCount("130100", FPT50HandprintServiceConstants.ZZHWLX_ALL, "","","")
    Assert.assertTrue(count > 0)
  }
  @Test
  def test_getLatentList: Unit ={
    val list = client.getLatentList("130100", FPT50HandprintServiceConstants.ZZHWLX_ALL, "","", "", 0, 10)
    Assert.assertTrue(list.nonEmpty)
  }
  @Test
  def test_getLatentPackage: Unit ={
    val latent = client.getLatentPackage("110000")
    Assert.assertTrue(latent != null)
  }
}
