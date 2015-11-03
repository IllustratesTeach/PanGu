package nirvana.hall.v62.internal

import nirvana.hall.v62.services.AncientClient

/**
 * provide AncientClient instance
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-02
 */
trait AncientClientSupport {
  /**
   * obtain AncientClient instance
   * @return AncientClient instance
   */
  def createAncientClient:AncientClient
}
