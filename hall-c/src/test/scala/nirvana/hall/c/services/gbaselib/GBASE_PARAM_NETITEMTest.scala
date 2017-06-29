package nirvana.hall.c.services.gbaselib

import nirvana.hall.c.services.gbaselib.paramadm.GBASE_PARAM_NETITEM
import org.junit.{Assert, Test}

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-25
  */
class GBASE_PARAM_NETITEMTest {
  @Test
  def test_length: Unit ={

    val item = new GBASE_PARAM_NETITEM
    Assert.assertEquals(128,item.getDataSize)

  }
}
