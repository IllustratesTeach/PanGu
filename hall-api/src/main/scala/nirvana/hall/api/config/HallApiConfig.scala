package nirvana.hall.api.config

import javax.xml.bind.annotation._

import monad.core.config.{ DatabaseConfigSupport, LocalStoreConfigSupport, LogFileSupport }
import monad.support.services.WebServerConfigSupport
import nirvana.hall.orm.config.HallOrmConfigSupport

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
    with HallOrmConfigSupport
    with WebServerConfigSupport {

  @XmlElement(name = "api")
  var api: ApiServerConfig = new ApiServerConfig
  @XmlElement(name = "sync_dict")
  var syncDict: SyncDictConfig = null
  @XmlElement(name = "sync62_cron")
  var sync62Cron: String = "0 0 * * * ? *"
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApiServerConfig")
class ApiServerConfig extends DatabaseConfigSupport {
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SyncDictConfig")
class SyncDictConfig {
  @XmlElement(name = "url")
  var url: String = _
  @XmlElement(name = "cron")
  var cron: String = "0 0 0 * * ? *"
}

