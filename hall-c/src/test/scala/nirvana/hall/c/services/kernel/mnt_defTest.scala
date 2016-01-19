package nirvana.hall.c.services.kernel

import nirvana.hall.c.services.kernel.mnt_def._
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
    val mnt_newtt = new FINGERMNTSTRUCT_NEWTT
    Assert.assertEquals(3960,mnt_newtt.getDataSize)

    val mnt2 = new FINGERLATMNTSTRUCT
    Assert.assertEquals(640,mnt2.getDataSize)

    val mnt3 = new PALMMNTSTRUCT
    Assert.assertEquals(8192,mnt3.getDataSize)

    val mnt4 = new PALMLATMNTSTRUCT
    Assert.assertEquals(5120,mnt4.getDataSize)
  }

}
