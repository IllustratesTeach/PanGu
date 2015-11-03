package nirvana.hall.v62.internal

import monad.support.services.LoggerSupport
import nirvana.hall.v62.services.AncientClient

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-29
 */
object AncientClient extends LoggerSupport{
  def connect(host:String,port:Int): AncientClient={
    new XSocketAncientClient(host,port)
  }
}

