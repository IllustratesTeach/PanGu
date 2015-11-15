package nirvana.hall.v62.internal.c.gloclib

import nirvana.hall.v62.internal.c.gloclib.galoclp.GLPCARDINFOSTRUCT
import nirvana.hall.v62.internal.c.gloclib.galoctp.GTPCARDINFOSTRUCT
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-14
 */
class galoctpTest {
  @Test
  def test_length: Unit ={
    val galoctp = new GTPCARDINFOSTRUCT
    Assert.assertEquals(384,galoctp.getDataSize)
    val galoclp = new GLPCARDINFOSTRUCT
    Assert.assertEquals(384,galoclp.getDataSize)
  }

}
