package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.gbaselib.gbasedef.GAKEYSTRUCT
import nirvana.hall.c.services.gloclib.galoctp._
import nirvana.hall.c.services.gloclib.galoctp.GPERSONINFOSTRUCT
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.gcolnames._
import org.junit.Test

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-07-04
  */
class gnettpTest {
  @Test
  def test_getCardListByGroupId(): Unit ={
    val config = new HallV62Config
    config.appServer.host = "192.168.1.253"
    config.appServer.port = 6798
    config.appServer.user = "afisadmin"
    config.appServer.password="helloafis"
    V62Facade.withConfigurationServer(config.appServer) {

      val facade = new V62Facade(config)
      val m_stPersonInfo = new GPERSONINFOSTRUCT
      m_stPersonInfo.szPersonID = "2016062216210000000000"
      m_stPersonInfo.nItemFlag = GPIS_ITEMFLAG_CARDCOUNT | GPIS_ITEMFLAG_CARDID | GPIS_ITEMFLAG_TEXT
      m_stPersonInfo.nItemFlag2 = GPIS_ITEMFLAG2_LPGROUPDBID | GPIS_ITEMFLAG2_LPGROUPTID | GPIS_ITEMFLAG2_FLAG

      val dbId: Short = 1
      val tableId: Short = 3

      facade.NET_GAFIS_PERSON_Get(dbId, tableId, m_stPersonInfo)
      m_stPersonInfo.pstID_Data.foreach(x=>println(x.szCardID))
    }

  }

  @Test
  def test_getCardIdList: Unit ={
    val config = new HallV62Config
    config.appServer.host = "127.0.0.1"
    config.appServer.port = 6798
    config.appServer.user = "afisadmin"
    config.appServer.password="helloafis"
    val facade = new V62Facade(config)
    val mapper = Map(
      g_stCN.stTcID.pszName -> "szKey"
    )
    val statementOpt = Option("(1=1) AND (CardID='1234567890') AND (shenfenid = '123456')")
    val cardId = facade.queryV62Table[GAKEYSTRUCT](1, 2, mapper, statementOpt, 10)
    println(cardId.size)
  }
}
