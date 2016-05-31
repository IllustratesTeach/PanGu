package nirvana.hall.spark.config

import java.util
import javax.xml.bind.annotation._

import nirvana.hall.spark.internal.{GafisPartitionRecordsSaver, GafisDatabaseImageProvider}


/**
 * nirvana spark configuration
  *
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

  @XmlElement(name="image_provider_class")
  var imageProviderClass:String = classOf[GafisDatabaseImageProvider].getName
  @XmlElement(name="data_saver_class")
  var dataSaverClass:String = classOf[GafisPartitionRecordsSaver].getName

  @XmlElement(name="hdfs_server")
  var hdfsServer:String = "None"
  @XmlElement(name="is_new_feature")
  var isNewFeature:Boolean = false
  @XmlElement(name="is_extractor_bin")
  var isExtractorBin:Boolean = false
  @XmlElement(name="partitions_num")
  var partitionsNum:Int = 4

  @XmlElementWrapper(name = "database_list")
  @XmlElement(name = "database")
  var database = new util.ArrayList[DatabaseConfig]()

  @XmlElementWrapper(name = "properties")
  @XmlElement(name = "property")
  var properties = new util.ArrayList[SparkConfigProperty]
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatabaseConfig")
class DatabaseConfig extends Serializable{
  @XmlAttribute(name = "name")
  var poolName: String = _
  @XmlElement(name = "driver")
  var driver: String = _
  @XmlElement(name = "user")
  var user: String = _
  @XmlElement(name = "password")
  var password: String = _
  @XmlElement(name = "url")
  var url: String = _
  @XmlElement(name = "url")
  var max: Int= 20
}
/**
 * JPA属性配置
 */
@XmlRootElement(name = "property")
class SparkConfigProperty extends Serializable{
  @XmlAttribute(name = "key")
  var name: String = null
  @XmlAttribute(name = "value")
  var value: String = null
}
