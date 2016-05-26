package nirvana.hall.v62.internal.c.gloclib

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.grmtlib.grmtpkg
import org.apache.commons.io.IOUtils
import org.junit.{Assert, Test}

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-24
  */
class galocpkgTest {

  @Test
  def test_OP_TPLIB_ADD2: Unit ={
    val itemPkg = new GBASE_ITEMPKG_OPSTRUCT
    val bytes = IOUtils.toByteArray(getClass.getResourceAsStream("/OP_TPLIB_ADD_2.dat"))
    itemPkg.fromByteArray(bytes)
    Assert.assertEquals(bytes.length,itemPkg.getDataSize)
    Assert.assertEquals(bytes.length,itemPkg.head.nDataLen)

    val galocpkg = new galocpkg with gitempkg with galoctp with grmtpkg
    val items = galocpkg.GAFIS_PKG_GetTpCard(itemPkg)
    val request = galocpkg.GAFIS_PKG_GetRmtRequest(itemPkg).getOrElse(throw new IllegalStateException("Missing Request Item"))
    Assert.assertEquals(4,items.length)
  }
  @Test
  def test_OP_TPLIB_ADD: Unit ={
    val itemPkg = new GBASE_ITEMPKG_OPSTRUCT
    val bytes = IOUtils.toByteArray(getClass.getResourceAsStream("/OP_TPLIB_ADD.dat"))
    itemPkg.fromByteArray(bytes)
    Assert.assertEquals(bytes.length,itemPkg.getDataSize)

    val galocpkg = new galocpkg with gitempkg with galoctp with grmtpkg
    val items = galocpkg.GAFIS_PKG_GetTpCard(itemPkg)
    val request = galocpkg.GAFIS_PKG_GetRmtRequest(itemPkg).getOrElse(throw new IllegalStateException("Missing Request Item"))
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
