package nirvana.hall.v62.config

import javax.xml.bind.annotation._

import monad.rpc.config.RpcBind
import org.apache.tapestry5.ioc.annotations.Marker
import org.apache.tapestry5.services.Core

/**
 * v62 configuration
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-04
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HallV62Config")
@XmlRootElement(name = "hall_v62")
@Marker(Array(classOf[Core]))
class HallV62Config extends V62ProxyBindSupport{
  @XmlElement(name="app_server")
  var appServer:V62ServerConfig = new V62ServerConfig()

  @XmlElement(name="template_finger_table")
  var templateTable:DatabaseTable = _
  @XmlElement(name="latent_finger_table")
  var latentTable:DatabaseTable = _
  @XmlElement(name="case_table")
  var caseTable:DatabaseTable = _
  @XmlElement(name="query_table")
  var queryTable:DatabaseTable = _
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HallV62DatabaseTable")
class DatabaseTable{
  @XmlElement(name="database_id")
  var dbId:Int = _
  @XmlElement(name="table_id")
  var tableId:Int = _
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "V62ProxyBindSupport")
trait V62ProxyBindSupport {
  @XmlElement(name = "proxy")
  var proxy:ProxyServerConfig = _
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "V62Server")
class ProxyServerConfig{
  @XmlElement(name = "server")
  var server: RpcBind = _
  @XmlElement(name="tx_server")
  var backend:V62ServerConfig = _
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "V62ServerConfig")
class V62ServerConfig{
  @XmlElement(name="host")
  var host:String = _
  @XmlElement(name="port")
  var port:Int = _
  @XmlElement(name="connection_timeout_secs")
  var connectionTimeoutSecs:Int = 30
  @XmlElement(name="read_timeout_secs")
  var readTimeoutSecs:Int = 30
  @XmlElement(name="user")
  var user:String= _
  @XmlElement(name="password")
  var password:String= _
}

