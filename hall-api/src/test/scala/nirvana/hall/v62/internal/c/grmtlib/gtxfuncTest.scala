package nirvana.hall.v62.internal.c.grmtlib

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYSTRUCT
import nirvana.hall.c.services.grmtlib.grmtdef
import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.gloclib.{galocpkg, galoctp}
import nirvana.hall.v62.internal.c.gnetlib.{gnetcsr, reqansop}
import nirvana.hall.v62.services.V62ServerAddress
import org.junit.Test

class gtxfuncTest {

  @Test
  def test_NET_GAFIS_TXSVR_QUERY_Add: Unit ={
    val gaQuery = new GAQUERYSTRUCT().fromStreamReader(getClass.getResourceAsStream("/GAQUERYSTRUCT.dat"))
    new gtxfunc with AncientClientSupport with gitempkg with grmtpkg with galocpkg with galoctp with grmtcsr with reqansop with gnetcsr with LoggerSupport{
      def serverAddress = new V62ServerAddress("127.0.0.1",6811,30,30, "hall" )
    }.NET_GAFIS_TXSVR_QUERY_Add(20, 2, gaQuery, 0, grmtdef.RMTOPT_DEST_DEFAULT)
  }
}
