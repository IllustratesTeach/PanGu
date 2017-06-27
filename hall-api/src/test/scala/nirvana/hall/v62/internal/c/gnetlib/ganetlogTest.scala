package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import org.junit.Test

/**
  * Created by songpeng on 2017/6/21.
  */
class ganetlogTest {

  val config = new HallV62Config
  config.appServer.host = "127.0.0.1"
  config.appServer.port = 6898
  config.appServer.user = "afisadmin"
  config.appServer.password="helloafis"
  val facade = new V62Facade(config)

  @Test
  def test_NET_GAFIS_VERIFYLOG_Get: Unit ={
    val verifyLog = facade.NET_GAFIS_VERIFYLOG_Get(1)
    println(new String(verifyLog.pbnData_Data, "gbk").trim)

  }
  @Test
  def test_NET_GAFIS_VERIFYLOG_Add: Unit ={

    val verifyLog = facade.NET_GAFIS_VERIFYLOG_Get(1)
    verifyLog.szSubmitUserUnitCode = "37020000"
    verifyLog.szSubmitUserName = "hall"
    verifyLog.szBreakID = "hall370200000001" //主键

    facade.NET_GAFIS_VERIFYLOG_Add(verifyLog)

  }
}
