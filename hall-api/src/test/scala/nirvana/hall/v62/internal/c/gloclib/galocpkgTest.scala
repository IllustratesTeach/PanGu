package nirvana.hall.v62.internal.c.gloclib

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import org.junit.{Assert, Test}

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-24
  */
class galocpkgTest {

  @Test
  def test_OP_TPLIB_ADD: Unit ={
    val itemPkg = new GBASE_ITEMPKG_OPSTRUCT
    itemPkg.fromStreamReader(getClass.getResourceAsStream("/OP_TPLIB_ADD.dat"))
    val galocpkg = new galocpkg with gitempkg with galoctp
    val items = galocpkg.GAFIS_PKG_GetTpCard(itemPkg)
    Assert.assertEquals(4,items.length)
  }
  @Test
  def test_OP_RMTLIB_QUERY_ADD: Unit ={
    val itemPkg = new GBASE_ITEMPKG_OPSTRUCT
    itemPkg.fromStreamReader(getClass.getResourceAsStream("/OP_RMTLIB_QUERY_ADD.dat"))
    val galocpkg = new galocpkg with gitempkg with galoctp
    val items = galocpkg.GAFIS_PKG_GetQuery(itemPkg)
    Assert.assertEquals(1,items.length)
  }
}
