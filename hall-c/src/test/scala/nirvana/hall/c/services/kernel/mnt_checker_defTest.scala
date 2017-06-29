package nirvana.hall.c.services.kernel

import nirvana.hall.c.services.kernel.mnt_checker_def.MNTDISPSTRUCT
import org.junit.{Assert, Test}

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-03-17
  */
class mnt_checker_defTest {

  @Test
  def test_len: Unit ={
    val checker = new MNTDISPSTRUCT
    Assert.assertEquals(28 * 1024 ,checker.getDataSize)
  }
}
