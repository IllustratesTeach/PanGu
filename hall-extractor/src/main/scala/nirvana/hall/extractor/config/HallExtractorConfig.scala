package nirvana.hall.extractor.config

import javax.xml.bind.annotation.{XmlRootElement, XmlType}

import monad.core.config.{LocalStoreConfigSupport, ZkClientConfigSupport, HeartbeatConfigSupport, LogFileSupport}
import monad.rpc.config.RpcBindSupport
import monad.support.services.WebServerConfigSupport

/**
 * hall extractor configuration
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
@XmlRootElement(name = "hall_extractor")
@XmlType(name = "HallExtractorConfig")
class HallExtractorConfig
  extends RpcBindSupport
  with WebServerConfigSupport
  with LocalStoreConfigSupport
  with LogFileSupport
  with ZkClientConfigSupport
  with HeartbeatConfigSupport
