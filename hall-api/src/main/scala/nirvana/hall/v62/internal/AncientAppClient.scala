package nirvana.hall.v62.internal

import monad.support.services.LoggerSupport
import nirvana.hall.v62.services.AncientClient

/**
 * ancient application server client
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-29
 */
object AncientAppClient extends LoggerSupport{
  def connect(host:String,port:Int): AncientClient={
    new XSocketAncientClient(host,port)
  }
}

