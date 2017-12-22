package nirvana.hall.fpt5

import junit.framework.Assert
import nirvana.hall.c.services.gfpt5lib.FPT5File
import nirvana.hall.support.services.XmlLoader
import org.junit.Test

import scala.io.Source

/**
  * Created by songpeng on 2017/12/10.
  */
class FTP5FileTest {

  @Test
  def test_validFingerprintPackage: Unit ={
    val content = Source.fromInputStream(getClass.getResourceAsStream("/R5224010107772013070452.xml")).mkString
    val fpt5File = XmlLoader.parseXML[FPT5File](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/fingerprint.xsd")), basePath= "/nirvana/hall/fpt5/")
    Assert.assertNotNull(fpt5File)
  }
}
