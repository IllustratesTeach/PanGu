package nirvana.hall.v62.internal.c

import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-24
 */
class BaseV62TestCase {

  def createFacade:V62Facade ={
    val config = new HallV62Config
    config.host = "10.1.6.119"
    config.port = 6798
    config.user = "afisadmin"
    config.password=""

    new V62Facade(config)
  }
}
