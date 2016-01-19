package nirvana.hall.stream.config

import javax.xml.bind.annotation.{XmlElement, XmlType, XmlRootElement}

import monad.core.config.LogFileSupport

/**
 * hall stream configuration object
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-15
 */
@XmlRootElement(name = "hall_stream")
@XmlType(name = "NirvanaHallStreamConfig")
class NirvanaHallStreamConfig extends HallStreamConfigSupport with LogFileSupport

@XmlType(name = "HallStreamConfigSupport")
trait HallStreamConfigSupport{
  @XmlElement(name="stream")
  var stream:HallStreamConfig = new HallStreamConfig
}
@XmlType(name = "HallStreamConfig")
class HallStreamConfig {
  /** decompress thread **/
  @XmlElement(name="decompress_thread_num")
  var decompressThread:Int = 1
  /** extract thread **/
  @XmlElement(name="extract_thread_num")
  var extractThread:Int = 2
  @XmlElement(name="save_feature_thread_num")
  var saveFeatureThread:Int = 2
  @XmlElement(name="is_new_feature")
  var isNewFeature:Boolean = false
}
