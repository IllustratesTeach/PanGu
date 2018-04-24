package nirvana.hall.matcher.config

import javax.xml.bind.annotation._

import monad.core.config.{DatabaseConfig, LocalStoreConfigSupport, LogFileSupport}
import monad.support.services.WebServerConfigSupport

/**
 * Created by songpeng on 16/3/25.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HallMatcherServerConfig")
@XmlRootElement(name = "hall_matcher")
class HallMatcherConfig extends LogFileSupport with LocalStoreConfigSupport with WebServerConfigSupport {

  @XmlElement(name = "database")
  var db: DatabaseConfig = new DatabaseConfig
  @XmlElement(name = "fetch_batch_size")
  var fetchBatchSize: Int = 100
  @XmlElement(name = "module")
  var module: String = "gz"
  @XmlElement(name = "mnt")
  var mnt = new MntSizeConfig
  @XmlElement(name = "match_timeout")
  var matchTimeout: MatchTimeoutConfig = _
  @XmlElement(name = "auto_check")
  var autoCheck: AutoCheckConfig = _
  @XmlElement(name = "cand_key_filter")
  var candKeyFilters: Array[CandKeyFilterConfig] = _
  @XmlElement(name = "auto_extract_feature")
  var autoExtractFeature: AutoExtractFeatureConfig = _
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MntSizeConfig")
class MntSizeConfig {
  @XmlElement(name = "header_size")
  var headerSize = 64
  @XmlElement(name = "finger_template_size")
  var fingerTemplateSize = 640
  @XmlElement(name = "finger_latent_size")
  var fingerLatentSize = 640
  @XmlElement(name = "palm_template_size")
  var palmTemplateSize = 8192
  @XmlElement(name = "palm_latent_size")
  var palmLatentSize = 5120
  @XmlElement(name = "is_new_feature")
  var isNewFeature= false
  @XmlElement(name = "has_ridge")
  var hasRidge = true
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlElement(name = "MatchTimeoutConfig")
class MatchTimeoutConfig{
  @XmlElement(name = "cron")
  var cron:String = "0 0/5 * * * ? *"
  @XmlElement(name = "timeout")
  var timeout = 60
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlElement(name = "AutoCheckConfig")
class AutoCheckConfig{
  @XmlElement(name = "confirm_score")
  var confirmScore = 60
  @XmlElement(name = "deny_score")
  var denyScore = 30
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlElement(name = "CandKeyFilterConfig")
class CandKeyFilterConfig{
  @XmlAttribute(name = "query_type")
  var queryType: Int = _
  @XmlAttribute(name = "is_percent")
  var isPercent: Boolean = false
  @XmlElement(name = "item")
  var items:Array[CandKeyFilterConfigItem] = _
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlElement(name = "CandKeyFilterConfigItem")
class CandKeyFilterConfigItem{
  @XmlAttribute(name = "reverse")
  var reverse: Boolean = false
  @XmlAttribute(name = "count")
  var count: Int = 0
  @XmlElement(name = "key_wild")
  var keyWild: String = _
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlElement(name = "AutoExtractFeatureConfig")
class AutoExtractFeatureConfig{
  @XmlElement(name = "cron")
  var cron:String = "0 0 0 * * ? *"
  @XmlElement(name = "timeout")
  var sql: String = ""
}
