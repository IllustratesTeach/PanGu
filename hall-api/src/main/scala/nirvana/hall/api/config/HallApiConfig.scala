package nirvana.hall.api.config

import javax.xml.bind.annotation._

import monad.core.config.{LocalStoreConfigSupport, LogFileSupport}
import monad.support.services.WebServerConfigSupport

/**
 * hall api configuration
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-04-02
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HallApiServerConfig")
@XmlRootElement(name = "hall_api")
class HallApiConfig
    extends LogFileSupport
    with LocalStoreConfigSupport
    with WebServerConfigSupport {

}


