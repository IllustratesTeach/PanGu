package nirvana.hall.c.services.gbaselib

import nirvana.hall.c.services.gbaselib.gafiserr.GAFISERRDATSTRUCT
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-02
 */
class gafiserrTest {

  @Test
  def test_length: Unit ={
    val error = new GAFISERRDATSTRUCT
    Assert.assertEquals(640,error.getDataSize)
  }
}
