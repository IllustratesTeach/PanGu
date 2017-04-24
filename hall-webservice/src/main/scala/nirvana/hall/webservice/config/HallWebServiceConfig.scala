package nirvana.hall.webservice.config

import javax.xml.bind.annotation._

import monad.core.config.{HeartbeatConfigSupport, LocalStoreConfigSupport, LogFileSupport}
import monad.support.services.WebServerConfigSupport

/**
  * Created by songpeng on 2017/4/24.
  */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HallWebserviceConfig")
@XmlRootElement(name = "hall_webservice")
class HallWebserviceConfig
  extends LogFileSupport
  with WebServerConfigSupport
  with LocalStoreConfigSupport
  with HeartbeatConfigSupport{

}
