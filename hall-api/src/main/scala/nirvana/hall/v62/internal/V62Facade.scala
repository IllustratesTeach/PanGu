package nirvana.hall.v62.internal

import monad.support.services.LoggerSupport
import nirvana.hall.v62.services.AncientClient

/**
 * v62 facade
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-04
 */
class V62Facade extends SendMatchTaskSupport
with DataSyncSupport
with AncientClientSupport
with LoggerSupport{
  /**
   * obtain AncientClient instance
   * @return AncientClient instance
   */
  override def createAncientClient(host: String, port: Int): AncientClient = {
    new XSocketAncientClient(host,port)
  }
}
