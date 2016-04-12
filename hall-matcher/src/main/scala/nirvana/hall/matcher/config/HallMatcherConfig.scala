package nirvana.hall.matcher.config

import javax.xml.bind.annotation._

import monad.core.config.{DatabaseConfig, LocalStoreConfigSupport, LogFileSupport}
import monad.support.services.WebServerConfigSupport

/**
 * Created by songpeng on 16/3/25.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HallMatcherServerConfig")
@XmlRootElement(name = "hall_matcher")
class HallMatcherConfig extends LogFileSupport with LocalStoreConfigSupport with WebServerConfigSupport {

  @XmlElement(name = "database")
  var db: DatabaseConfig = new DatabaseConfig
  @XmlElement(name = "module")
  var module: String = "gz"
}

