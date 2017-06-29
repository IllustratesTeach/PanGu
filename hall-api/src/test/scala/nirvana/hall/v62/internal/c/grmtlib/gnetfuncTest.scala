package nirvana.hall.v62.internal.c.grmtlib

import nirvana.hall.c.services.grmtlib.gserver.RMTSERVERSTRUCT
import nirvana.hall.v62.BaseV62TestCase
import org.junit.Test

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-24
 */
class gnetfuncTest extends BaseV62TestCase{


  @Test
  def test_add: Unit ={
    val facade = createFacade
    val pServer = new RMTSERVERSTRUCT
    pServer.szServerIP ="127.0.0.1"
    pServer.nServerID = 123
    pServer.nServerPort = 6789

    pServer.szServerName="local"
    pServer.szUnitCode="2100000"
    pServer.szEnUserName = "asdf"
    pServer.szEnUserPass = "fdsa"

    facade.NET_GAFIS_RMTLIB_SERVER_Add(21,100,pServer)
  }
}
