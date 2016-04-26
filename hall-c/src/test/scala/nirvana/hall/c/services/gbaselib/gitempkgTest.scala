package nirvana.hall.c.services.gbaselib

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_ITEMSTRUCT
import org.junit.{Assert, Test}

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-26
  */
class gitempkgTest {
  @Test
  def test_length: Unit ={
    val item = new GBASE_ITEMPKG_ITEMSTRUCT
    item.stHead.nItemLen = 100
    Assert.assertEquals(item.toByteArray().length,item.stHead.getDataSize+100)
  }

}
