package nirvana.hall.v62.services

import nirvana.hall.v62.internal.c.gbaselib.gafiserr.GAFISERRDATSTRUCT
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-07
 */
class GafisExceptionTest {


  @Test
  def test_exception: Unit ={
    val gafisError = new GAFISERRDATSTRUCT
    gafisError.nAFISErrno = 2606

    val exception = new GafisException(gafisError)
    Assert.assertEquals(2606,exception.code)
    Assert.assertTrue(exception.getMessage.indexOf("记录已经存在") == 0)
    exception.printStackTrace()
  }

}
