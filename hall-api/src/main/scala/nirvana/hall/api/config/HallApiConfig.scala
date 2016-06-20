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
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SyncConfig")
class SyncConfig {
    @XmlElement(name = "sync_cron")
    var syncCron: String = _
}
