package nirvana.hall.v62.config

import javax.xml.bind.annotation._

/**
 * v62 configuration
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-04
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HallV62Config")
@XmlRootElement(name = "hall_v62")
class HallV62Config {
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
