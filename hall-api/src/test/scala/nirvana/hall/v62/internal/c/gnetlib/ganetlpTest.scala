package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.gloclib.galoclp.GAFIS_LPGROUPSTRUCT
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import org.junit.Test

/**
  * Created by songpeng on 2017/11/15.
  */
class ganetlpTest {
  val config = new HallV62Config
  config.appServer.host = "127.0.0.1"
  config.appServer.port = 6898
  config.appServer.user = "afisadmin"
  config.appServer.password="helloafis"
  val facade = new V62Facade(config)

  @Test
  def test_NET_GAFIS_LPGROUP_Get: Unit ={
    val lpGroup = new GAFIS_LPGROUPSTRUCT
    lpGroup.szGroupID = "12345601"
    facade.NET_GAFIS_LPGROUP_Get(V62Facade.DBID_LP_DEFAULT, V62Facade.TID_LPGROUP, lpGroup)
    lpGroup.pstKeyList_Data.foreach(f=> println(f.szKey))
  }
}
