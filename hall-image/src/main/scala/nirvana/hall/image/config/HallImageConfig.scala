package nirvana.hall.image.config

import javax.xml.bind.annotation.{XmlRootElement, XmlType}

import monad.core.config.{ZkClientConfigSupport, HeartbeatConfigSupport, LogFileSupport}
import monad.rpc.config.RpcBindSupport

/**
 * hall image configuration
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
@XmlRootElement(name = "hall_image")
@XmlType(name = "HallImageConfig")
class HallImageConfig
  extends RpcBindSupport
  with LogFileSupport
  with ZkClientConfigSupport
  with HeartbeatConfigSupport
