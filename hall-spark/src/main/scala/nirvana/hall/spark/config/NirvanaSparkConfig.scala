package nirvana.hall.spark.config

import java.util
import javax.xml.bind.annotation._


/**
 * nirvana spark configuration
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-02-17
 */
@XmlType(name = "NirvanaSparkConfig")
@XmlRootElement(name = "nirvana_spark")
class NirvanaSparkConfig extends Serializable{
  @XmlElement(name="host")
  var host:String = "127.0.0.1"
  @XmlElement(name="app_name")
  var appName:String = _
  @XmlElement(name="master_server")
  var masterServer:String = "local[0]"
  @XmlElement(name="kafka_server")
  var kafkaServer:String = _
  @XmlElement(name="kafka_topic_name")
  var kafkaTopicName:String = _
  @XmlElement(name="image_file_server")
  var imageFileServer:String = _
  @XmlElement(name="decompress_image_server")
  var decompressImageServer:String = _
  @XmlElement(name="extractor_server")
  var extractorServer:String = _
  @XmlElement(name="hdfs_server")
  var hdfsServer:String = "None"
  @XmlElement(name="is_new_feature")
  var isNewFeature:Boolean = false
  @XmlElement(name="is_extractor_bin")
  var isExtractorBin:Boolean = false
  @XmlElement(name="partitions_num")
  var partitionsNum:Int = 4
  @XmlElement(name = "database")
  var db: DatabaseConfig = new DatabaseConfig()
  /** JPA相关配置 **/
  @XmlElementWrapper(name = "spark")
  @XmlElement(name = "property")
  var sparkProperties  = new util.ArrayList[SparkConfigProperty]
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatabaseConfig")
class DatabaseConfig extends Serializable{
  @XmlElement(name = "driver")
  var driver: String = _
  @XmlElement(name = "user")
  var user: String = _
  @XmlElement(name = "password")
  var password: String = _
  @XmlElement(name = "url")
  var url: String = _
}
/**
 * JPA属性配置
 */
@XmlRootElement(name = "property")
class SparkConfigProperty {
  @XmlAttribute(name = "name")
  var name: String = null
  @XmlAttribute(name = "value")
  var value: String = null
}
