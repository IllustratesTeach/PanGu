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
  @XmlElement(name = "mnt")
  var mnt = new MntSizeConfig
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MntSizeConfig")
class MntSizeConfig {
  @XmlElement(name = "header_size")
  var headerSize = 64
  @XmlElement(name = "finger_template_size")
  var fingerTemplateSize = 640
  @XmlElement(name = "finger_latent_size")
  var fingerLatentSize = 640
  @XmlElement(name = "palm_template_size")
  var palmTemplateSize = 8192
  @XmlElement(name = "palm_latent_size")
  var palmLatentSize = 5120
}

