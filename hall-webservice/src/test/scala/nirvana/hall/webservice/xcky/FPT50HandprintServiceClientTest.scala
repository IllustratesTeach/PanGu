package nirvana.hall.webservice.xcky

import monad.support.services.XmlLoader
import nirvana.hall.webservice.internal.xcky.FingerPrintListResponse
import org.junit.{Assert, Test}

/**
  * Created by songpeng on 2017/12/24.
  */
class FPT50HandprintServiceClientTest {

  @Test
  def test_parseXML_FingerPrintListResponse: Unit ={
    val xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<list>\n<k>\n<ajmc>案件名称</ajmc>\n<xckybh>现场勘验编号</xckybh>\n<xcwzbh>现场物证编号</xcwzbh>\n</k>\n</list>"
    val fingerPrintListResponse = XmlLoader.parseXML[FingerPrintListResponse](xml)
    Assert.assertEquals(fingerPrintListResponse.list(0).ajmc, "案件名称")
  }
}
