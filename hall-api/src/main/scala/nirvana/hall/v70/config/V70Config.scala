package nirvana.hall.v70.config

import javax.xml.bind.annotation._

import monad.core.config.DatabaseConfig
import stark.activerecord.config.ActiveRecordConfigSupport

/**
 * Created by songpeng on 16/1/25.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HallV70Config")
@XmlRootElement(name = "hall_v70")
class HallV70Config extends ActiveRecordConfigSupport {
  @XmlElement(name = "database")
  var db: DatabaseConfig = new DatabaseConfig()
  @XmlElement(name = "cron")
  var cron: CronConfig = new CronConfig
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CronConfig")
class CronConfig {
  @XmlElement(name = "sync_dict_cron")
  var syncDictCron: String = _
  @XmlElement(name = "sync_7to6_cron")
  var sync7to6Cron: String = _
  @XmlElement(name = "query_7to6_cron")
  val query7to6Cron: String = null
}