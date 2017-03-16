package nirvana.hall.api.config

import javax.xml.bind.annotation._

import monad.core.config.{HeartbeatConfigSupport, ZkClientConfigSupport, LocalStoreConfigSupport, LogFileSupport}
import monad.rpc.config.RpcBindSupport
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
    with RpcBindSupport
    with WebServerConfigSupport
    with LocalStoreConfigSupport
    with ZkClientConfigSupport
    with HeartbeatConfigSupport{

    @XmlElement(name = "sync")
    var sync: SyncConfig = new SyncConfig
    @XmlElement(name = "webservice")
    var webservice = new WebserviceConfig
    @XmlElement(name = "hall_image_url")
    var hallImageUrl: String = _

}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SyncConfig")
class SyncConfig {
    @XmlElement(name = "batch_size")
    var batchSize: Int = 1
    @XmlElement(name = "sync_cron")
    var syncCron: String = _
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WebserviceConfig")
class WebserviceConfig{
    @XmlElement(name = "union4pfmip")
    var union4pfmip: Union4pfmipConfig = new Union4pfmipConfig
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
}
