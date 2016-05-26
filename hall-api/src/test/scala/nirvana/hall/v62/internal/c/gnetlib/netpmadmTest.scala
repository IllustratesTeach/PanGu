package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.gbaselib.paramadm
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import org.junit.Test

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-24
 */
class netpmadmTest {
  @Test
  def test_select: Unit ={

    val config = new HallV62Config
    config.host = "127.0.0.1"
    config.port = 7777
    config.user = "afisadmin"
    config.password=""
    V62Facade.withConfigurationServer(config) {

      val facade = new V62Facade


      println(facade.NET_GAFIS_PMADM_GetStr(paramadm.g_stParamName.pszUnitCode))
    }
  }
}
