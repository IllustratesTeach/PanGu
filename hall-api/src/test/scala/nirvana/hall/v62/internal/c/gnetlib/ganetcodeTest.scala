package nirvana.hall.v62.internal.c.gnetlib




import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.gacodetb.GAFIS_CODE_ENTRYSTRUCT
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import org.junit.{Assert, Test}

/**
  * Created by yuchen on 2018/1/26.
  */
class ganetcodeTest {

  @Test
  def test_NET_GAFIS_CODETB_ENTRY_Op(): Unit ={
    val config = new HallV62Config
    config.appServer.host = "192.168.1.151"
    config.appServer.port = 6798
    config.appServer.user = "afisadmin"
    config.appServer.password="helloafis"
    V62Facade.withConfigurationServer(config.appServer) {

      val facade = new V62Facade(config)
      val codeEntryStruct = new GAFIS_CODE_ENTRYSTRUCT
      codeEntryStruct.szCode = "140000"
      val dbId: Short = 21
      val tableId: Short = 209

      facade.NET_GAFIS_CODETB_INFO(dbId, tableId, "Code_UnitTable".getBytes,codeEntryStruct
        ,gnopcode.OP_CODETABLE_GET)


      Assert.assertEquals("山西",new String(codeEntryStruct.szName,"GBK").trim)
    }
  }

}
