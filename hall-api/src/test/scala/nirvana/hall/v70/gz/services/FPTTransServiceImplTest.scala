package nirvana.hall.v70.gz.services


import java.io.File

import monad.support.services.XmlLoader
import nirvana.hall.api.services.FPTTransService
import nirvana.hall.c.services.gfpt5lib.FPT5File
import nirvana.hall.protocol.api.FPTTrans.ExportType
import nirvana.hall.v70.gz.sys.BaseV70TestCase
import org.apache.commons.io.FileUtils
import org.junit.{Assert, Test}

/**
  * Created by yuchen on 2017/11/10.
  */
class FPTTransServiceImplTest extends BaseV70TestCase{

  @Test
  def test_fPTExport(): Unit ={
    try{
      val service = getService[FPTTransService]
      val fpt5 = service.fPTExport("P5200000000002017099992",ExportType.TP)
      println(FileUtils.writeStringToFile(new File("D:\\FPT5.fptx"),new String(fpt5.getBytes)))
      val obj = XmlLoader.parseXML[FPT5File](new String(fpt5.getBytes),xsd = Some(getClass.getResourceAsStream("/fpt5.xsd")))
      //Assert.assertTrue(obj.fingerprintPackage.get(0).descriptMsg.name.equals("张松"))
    }catch{
      case ex:Exception =>
       Assert.fail(ex.getMessage)
    }
  }

}
