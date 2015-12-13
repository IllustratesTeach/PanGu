package nirvana.hall.image.config

import javax.xml.bind.annotation.{XmlRootElement, XmlType}

import monad.core.config.{LocalStoreConfigSupport, ZkClientConfigSupport, HeartbeatConfigSupport, LogFileSupport}
import monad.rpc.config.RpcBindSupport
import monad.support.services.WebServerConfigSupport

/**
 * hall image configuration
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
@XmlRootElement(name = "hall_image")
@XmlType(name = "HallImageConfig")
class HallImageConfig
  extends RpcBindSupport
  with WebServerConfigSupport
  with LocalStoreConfigSupport
  with LogFileSupport
  with ZkClientConfigSupport
  with HeartbeatConfigSupport
