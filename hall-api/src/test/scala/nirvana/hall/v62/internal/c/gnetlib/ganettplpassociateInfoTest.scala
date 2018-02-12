package nirvana.hall.v62.internal.c.gnetlib



import nirvana.hall.c.services.gloclib.gatplpassociate.GAFIS_TPLP_ASSOCIATE
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.V62SqlHelper
import nirvana.hall.v62.internal.c.gloclib.gaqryqueConverter
import nirvana.hall.v62.internal.c.gloclib.gcolnames.g_stCN
import org.junit.{Assert, Test}

/**
  * Created by yuchen on 2018/2/5.
  */
class ganettplpassociateInfoTest {

  /**
    * struct size test
    */
  @Test
  def test_GAFIS_TPLP_ASSOCIATE_Length(): Unit ={
    val length = new GAFIS_TPLP_ASSOCIATE().toByteArray().length
    Assert.assertEquals(256,length)
  }

  @Test
  def test_getAssociateSid: Unit ={
    val config = new HallV62Config
    config.appServer.host = "192.168.1.119"
    config.appServer.port = 6898
    config.appServer.user = "afisadmin"
    config.appServer.password="helloafis"
    val facade = new V62Facade(config)
    val mapper = Map(
      g_stCN.stNuminaCol.pszSID -> "nSID"
    )
    var statement = "(1=1)"
    statement += V62SqlHelper.andSQL("LPGROUPID", "510124360000201105012301")
    statement += V62SqlHelper.andSQL("TPPERSONID","1101083009992012102025")

    val tpLpAssocaite = facade.queryV62Table[GAFIS_TPLP_ASSOCIATE](21, 340, mapper, Option(statement), 10)
    Assert.assertTrue(tpLpAssocaite.nonEmpty)
  }

  @Test
  def test_NET_GAFIS_TPLP_ASSOCIATE_Add(): Unit ={
    val config = new HallV62Config
    config.appServer.host = "192.168.1.151"
    config.appServer.port = 6798
    config.appServer.user = "afisadmin"
    config.appServer.password="helloafis"
    val facade = new V62Facade(config)
    val tplpAssociate = new GAFIS_TPLP_ASSOCIATE
    tplpAssociate.szTPPersonID = "1101083009992012102025"
    tplpAssociate.szLPGroupID = "510124360000201105012301"
    facade.NET_GAFIS_TPLP_ASSOCIATE_ADD(tplpAssociate,0)
  }

  //"510124360000201105012301"," 1101083009992012102025"
  @Test
  def test_NET_GAFIS_TPLP_ASSOCIATE_Get(): Unit ={
    val config = new HallV62Config
    config.appServer.host = "192.168.1.119"
    config.appServer.port = 6898
    config.appServer.user = "afisadmin"
    config.appServer.password="helloafis"
    val facade = new V62Facade(config)
    val tPLP_ASSOCIATE = new GAFIS_TPLP_ASSOCIATE
    tPLP_ASSOCIATE.nSID = gaqryqueConverter.convertLongAsSixByteArray(1)
    facade.NET_GAFIS_TPLP_ASSOCIATE_GET(tPLP_ASSOCIATE, 0)
    Assert.assertEquals(tPLP_ASSOCIATE.szTPPersonID,"1101083009992012102025")
  }


}
