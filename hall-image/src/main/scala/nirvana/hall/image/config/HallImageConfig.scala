package nirvana.hall.image.config

import java.util
import javax.xml.bind.annotation.{XmlElementWrapper, XmlElement, XmlRootElement, XmlType}

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
  extends ImageConfigSupport
  with RpcBindSupport
  with WebServerConfigSupport
  with LocalStoreConfigSupport
  with LogFileSupport
  with ZkClientConfigSupport
  with HeartbeatConfigSupport

trait ImageConfigSupport{
  @XmlElement(name="image")
  var image=new ImageConfig
}
@XmlRootElement(name = "image_config")
@XmlType(name = "ImageConfig")
class ImageConfig{
  @XmlElementWrapper(name = "dll_properties")
  @XmlElement(name = "dll")
  var dllConcurrent = new util.ArrayList[DllConfig]()
}
@XmlRootElement(name="dll")
class DllConfig{
  @XmlElement(name="name")
  var name:String = _
  @XmlElement(name="is_concurrent")
  var isConcurrent:Boolean = _
}
