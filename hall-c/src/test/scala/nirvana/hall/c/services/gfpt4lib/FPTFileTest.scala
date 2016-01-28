package nirvana.hall.c.services.gfpt4lib

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.AncientData._
import nirvana.hall.c.services.gfpt4lib.FPT3File.{FPT3File, Logic1Rec}
import org.apache.commons.io.IOUtils
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-27
 */
class FPTFileTest {
  @Test
  def test_head: Unit ={
    Assert.assertEquals(690,new Logic1Rec().getDataSize)
  }
  @Test
  def test_read_fpt3: Unit ={
    val bytes = IOUtils.toByteArray(getClass.getResourceAsStream("/8-1.fpt"))
    val fpt3 = new FPT3File
    fpt3.fromStreamReader(getClass.getResourceAsStream("/8-1.fpt"),AncientConstants.GBK_ENCODING)
    val fpt3_2= new FPT3File().fromByteArray(fpt3.toByteArray)
    Assert.assertEquals(fpt3.logic1Rec.fileLength.toInt,fpt3.getDataSize)
    Assert.assertEquals(690,new Logic1Rec().getDataSize)
  }
}
