package nirvana.hall.v62.internal.c.gloclib

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
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
    val card = galocpkg.GAFIS_PKG_GetTpCard(itemPkg).get
    val request = galocpkg.GAFIS_PKG_GetRmtRequest(itemPkg).getOrElse(throw new IllegalStateException("Missing Request Item"))
  }
  @Test
  def test_OP_TPLIB_ADD: Unit ={
    val itemPkg = new GBASE_ITEMPKG_OPSTRUCT
    val bytes = IOUtils.toByteArray(getClass.getResourceAsStream("/OP_TPLIB_ADD.dat"))
    itemPkg.fromByteArray(bytes)
    Assert.assertEquals(bytes.length,itemPkg.getDataSize)

    val galocpkg = new galocpkg with gitempkg with galoctp with grmtpkg
    val card = galocpkg.GAFIS_PKG_GetTpCard(itemPkg).get
    val request = galocpkg.GAFIS_PKG_GetRmtRequest(itemPkg).getOrElse(throw new IllegalStateException("Missing Request Item"))
  }
  @Test
  def test_OP_RMTLIB_QUERY_ADD: Unit ={
    val itemPkg = new GBASE_ITEMPKG_OPSTRUCT
    itemPkg.fromStreamReader(getClass.getResourceAsStream("/OP_RMTLIB_QUERY_ADD.dat"))
    val galocpkg = new galocpkg with gitempkg with galoctp
    val items = galocpkg.GAFIS_PKG_GetQuery(itemPkg)
    Assert.assertEquals(1,items.length)
  }
  @Test
  def test_OP_RMTLIB_GETINT1VALUEBYCOLNAME: Unit ={
    val pReq = new GNETREQUESTHEADOBJECT
    pReq.fromStreamReader(getClass.getResourceAsStream("/OP_RMTLIB_GETINT1VALUEBYCOLNAME.dat"))
    val sidArr = new Array[Byte](6)
    System.arraycopy(pReq.bnData, 40, sidArr, 0, 6)
    val colArr = new Array[Byte](32)
    System.arraycopy(pReq.bnData, 0, colArr, 0, 32)
    println(new String(colArr, "gbk").trim)
    val oraSid = gaqryqueConverter.convertSixByteArrayToLong(sidArr)
    println(oraSid)
  }
}
