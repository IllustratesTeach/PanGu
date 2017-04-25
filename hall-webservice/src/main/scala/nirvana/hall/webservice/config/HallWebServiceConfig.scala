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

  @XmlElement(name = "union4pfmip")
  var union4pfmip: Union4pfmipConfig = new Union4pfmipConfig
  @XmlElement(name = "hall_image_url")
  var hallImageUrl: String = _
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Union4pfmip")
class Union4pfmipConfig{
  @XmlElement(name = "cron")
  var cron: String = _
  @XmlElement(name = "url")
  var url: String = _
  @XmlElement(name = "target_namespace")
  var targetNamespace: String = _
  @XmlElement(name = "user")
  var user: String = _
  @XmlElement(name = "password")
  var password: String = _
  @XmlElement(name = "taskPath")
  var taskPath: String = _
  @XmlElement(name = "dateLimit")
  var dateLimit: String = _
  @XmlElement(name = "TenPrintPrex")
  var TenPrintPrex: String = _
  @XmlElement(name = "LatentPrex")
  var LatentPrex: String = _
}