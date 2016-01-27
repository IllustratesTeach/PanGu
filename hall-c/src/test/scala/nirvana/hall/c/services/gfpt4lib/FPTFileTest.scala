package nirvana.hall.c.services.gfpt4lib

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
    FPTObject.parseOfInputStream(getClass.getResourceAsStream("/8-1.fpt"))
    val fpt3 = new FPT3File
    fpt3.fromByteArray(bytes)
    Assert.assertEquals(690,new Logic1Rec().getDataSize)
  }
}
