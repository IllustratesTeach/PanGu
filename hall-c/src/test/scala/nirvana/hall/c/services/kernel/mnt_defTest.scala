package nirvana.hall.c.services.kernel

import nirvana.hall.c.services.kernel.mnt_def.{FINGERLATMNTSTRUCT, FINGERMNTSTRUCT}
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-11
 */
class mnt_defTest {
  @Test
  def test_mnt: Unit ={
    val mnt = new FINGERMNTSTRUCT
    Assert.assertEquals(640,mnt.getDataSize)

    val mnt2 = new FINGERLATMNTSTRUCT
    Assert.assertEquals(640,mnt2.getDataSize)
  }

}
