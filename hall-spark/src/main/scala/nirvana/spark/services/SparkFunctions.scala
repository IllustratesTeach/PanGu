package nirvana.spark.services

import java.util.Properties
import java.util.concurrent.atomic.AtomicBoolean

import kafka.javaapi.producer.Producer
import kafka.producer.{KeyedMessage, ProducerConfig}
import nirvana.hall.extractor.jni.JniLoader
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.spark.config.NirvanaSparkConfig

/**
 * utility function
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-31
 */
object SparkFunctions{
  lazy val httpClient= SparkRpcClient
  private var errorKafkaServers:String = _
  private lazy val producer:Producer[String,String] = createErrorProducer
  private lazy val imageInit = new AtomicBoolean(false)
  @volatile
  private var imageDllLoaded = false
  def loadImageJNI():Unit ={
    if(imageInit.compareAndSet(false,true)) {
      nirvana.hall.image.jni.JniLoader.loadJniLibrary(".",null)
      imageDllLoaded = true
    }
    if(!imageDllLoaded)
      loadImageJNI()
  }
  private lazy val extractorInit = new AtomicBoolean(false)
  @volatile
  private var extractorDllLoaded = false
  def loadExtractorJNI():Unit ={
    if(extractorInit.compareAndSet(false,true)) {
      JniLoader.loadJniLibrary(".",null)
      extractorDllLoaded = true
    }
    if(!extractorDllLoaded)
      loadExtractorJNI()

  }


  def createErrorProducer:Producer[String,String]= {
    val props = new Properties()
    props.put("metadata.broker.list", errorKafkaServers)
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    val config = new ProducerConfig(props)
    new Producer[String, String](config)
  }
  abstract class StreamError(val event:StreamEvent) extends Serializable {
    def getMessage:String
  }
  /*case class StreamEvent(personId:String,path:String,featureType: FeatureType,position: FingerPosition) extends Serializable{
    var data:Array[Any] = _
  }*/

  case class StreamEvent(path:String,personId:String,featureType: FeatureType,position: FingerPosition,caseId:String,sendNo: String,cardId: String) extends Serializable{
    var data:Array[Any] = _
  }
  def reportError(parameter:NirvanaSparkConfig,streamError: StreamError): Unit = {
    errorKafkaServers = parameter.kafkaServer
    val event = streamError.event
    var message: KeyedMessage[String, String] = null
    if (event.path.indexOf("脱密案件") != -1) {//latent
      message = new KeyedMessage("ERROR", event.path,
        "%s|%s|%s|%s|%s".format(event.path, event.caseId, event.cardId, streamError.getMessage,"latent"))

    } else {
      message = new KeyedMessage("ERROR", event.path,
        "%s|%s|%s|%s|%s".format(event.path, event.personId, event.position, streamError.getMessage,"template"))
    }

    producer.send(message)
  }

}
