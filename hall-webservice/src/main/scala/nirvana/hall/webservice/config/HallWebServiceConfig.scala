package nirvana.hall.webservice.config

import javax.xml.bind.annotation._

import monad.core.config.{HeartbeatConfigSupport, LocalStoreConfigSupport, LogFileSupport}
import monad.support.services.WebServerConfigSupport
import nirvana.hall.api.config.HallImageRemoteConfigSupport

/**
  * Created by songpeng on 2017/4/24.
  */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HallWebserviceConfig")
@XmlRootElement(name = "hall_webservice")
class HallWebserviceConfig
  extends LogFileSupport
  with HallImageRemoteConfigSupport
  with WebServerConfigSupport
  with LocalStoreConfigSupport
  with HeartbeatConfigSupport{

  @XmlElement(name = "union4pfmip")
  var union4pfmip: Union4pfmipConfig = new Union4pfmipConfig

  @XmlElement(name = "AutoQuerySetting")
  var autoQuerySetting: AutoQuerySetting = new AutoQuerySetting
  @XmlElement(name = "local_tenprint_path")
  var localTenprintPath:String = _
  @XmlElement(name = "local_latent_path")
  var localLatentPath:String = _
  @XmlElement(name = "local_hit_result_path")
  var localHitResultPath:String = _
  @XmlElement(name = "xc_hit_result_path")
  var xcHitResultPath:String = _
  @XmlElement(name = "hall_image_url")
  var hall_image_url: String = _
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
  @XmlElement(name = "dateLimit")
  var dateLimit: String = _
  @XmlElement(name = "TenPrintPrex")
  var TenPrintPrex: String = _
  @XmlElement(name = "LatentPrex")
  var LatentPrex: String = _
  @XmlElement(name = "send_checkin_cron")
  var sendCheckinCron: String = _
  @XmlElement(name = "upload_checkin_cron")
  var uploadCheckinCron: String = _
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AutoQuerySetting")
class AutoQuerySetting{
  @XmlElement(name = "isAutoQuery")
  var isAutoQuery: String = _
}