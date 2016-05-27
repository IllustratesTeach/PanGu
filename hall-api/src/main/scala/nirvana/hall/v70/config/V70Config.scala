package nirvana.hall.v70.config

import javax.xml.bind.annotation._

import monad.core.config.DatabaseConfigSupport
import stark.activerecord.config.ActiveRecordConfigSupport

/**
 * Created by songpeng on 16/1/25.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HallV70Config")
@XmlRootElement(name = "hall_v70")
class HallV70Config extends ActiveRecordConfigSupport {

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
  var cron: String = "0 0 * * * ? *"
}