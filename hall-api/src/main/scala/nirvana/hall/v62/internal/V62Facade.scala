package nirvana.hall.v62.internal

import monad.support.services.LoggerSupport
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gnetlib.{ganetdbp, gnetflib, gnetcsr, ganetqry}
import nirvana.hall.v62.services.V62ServerAddress

/**
 * v62 facade
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-04
 */
class V62Facade(config:HallV62Config)
  extends gnetcsr
  with DataSyncSupport
  with ganetqry
  with ganetdbp
  with gnetflib
  with AncientClientSupport
  with LoggerSupport{
  private val address = V62ServerAddress(config.host,config.port,config.user,Option(config.password))
  override def serverAddress: V62ServerAddress = address
}
