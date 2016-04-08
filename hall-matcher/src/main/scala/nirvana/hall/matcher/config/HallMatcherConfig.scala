package nirvana.hall.matcher.config

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlRootElement, XmlType}

import monad.core.config.{LocalStoreConfigSupport, LogFileSupport}
import monad.support.services.WebServerConfigSupport

/**
 * Created by songpeng on 16/3/25.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HallMatcherServerConfig")
@XmlRootElement(name = "hall_matcher")
class HallMatcherConfig extends LogFileSupport with LocalStoreConfigSupport with WebServerConfigSupport {

}
