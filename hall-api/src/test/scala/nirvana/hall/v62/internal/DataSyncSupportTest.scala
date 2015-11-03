package nirvana.hall.v62.internal

import monad.support.services.LoggerSupport
import nirvana.hall.v62.services.{DatabaseTable, AncientClient}
import org.junit.Test

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-03
 */
class DataSyncSupportTest {
  @Test
  def test_send(): Unit ={
    val sync = createSender()
    //sync.sendData(DatabaseTable(2,2))
    sync.sendData(DatabaseTable(1,2))
  }
  private def createSender():DataSyncSupport={
    new DataSyncSupport with AncientClientSupport with LoggerSupport{
      /**
       * obtain AncientClient instance
       * @return AncientClient instance
       */
      override def createAncientClient: AncientClient = {
        AncientAppClient.connect("10.1.6.119",6898)
      }
    }
  }
}
