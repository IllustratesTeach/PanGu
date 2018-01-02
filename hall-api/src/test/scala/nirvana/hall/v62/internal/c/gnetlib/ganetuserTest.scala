package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.gloclib.gafisusr.GAFIS_USERSTRUCT
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import org.junit.Test

/**
  * Created by yuchen on 2017/12/30.
  */
class ganetuserTest {

  @Test
  def a(): Unit ={
    val config = new HallV62Config
    config.appServer.host = "192.168.0.102"
    config.appServer.port = 6898
    config.appServer.user = "afisadmin"
    config.appServer.password=""
    V62Facade.withConfigurationServer(config.appServer) {

      val facade = new V62Facade(config)
      val userStruct = new GAFIS_USERSTRUCT
      userStruct.stInfo.szName = "zss"

      val dbId: Short = 21
      val tableId: Short = 2

      facade.NET_GAFIS_USER_GetUserInfo(dbId, tableId, userStruct)
      println(userStruct.stInfo.szFullName)
    }
  }

}
