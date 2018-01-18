package nirvana.hall.spark.services

import java.io._
import java.net.{URI, URL}
import java.util.Properties
import java.util.concurrent.atomic.AtomicBoolean
import java.util.zip.ZipInputStream

import kafka.javaapi.producer.Producer
import kafka.producer.{KeyedMessage, ProducerConfig}
import nirvana.hall.extractor.jni.JniLoader
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.spark.config.NirvanaSparkConfig
import org.apache.commons.io.FileUtils
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.slf4j.LoggerFactory

import scala.util.control.NonFatal

/**
 * utility function
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-31
 */
object SparkFunctions{
  val logger = LoggerFactory getLogger getClass
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
    if (null != event.caseId) {//latent
      message = new KeyedMessage("ERROR", event.path,
        "%s|%s|%s|%s|%s".format(event.path, event.caseId, event.cardId, streamError.getMessage,"LATENT"))

    } else if (null != event.personId) {
      message = new KeyedMessage("ERROR", event.path,
        "%s|%s|%s|%s|%s".format(event.path, event.personId, event.position, streamError.getMessage,"TEMPLATE"))
    } else {
      message = new KeyedMessage("ERROR", event.path,
        "%s|%s|%s|%s|%s".format(event.path, "", "", streamError.getMessage,"UNKNOWN"))
    }
    if ("None".equals(errorKafkaServers)) logger.error(message.message)
    else producer.send(message)
  }

  def unzipByArray(zipFile : Array[Byte]) : Array[Byte] = {
    var in : ZipInputStream = null
    val out = new ByteArrayOutputStream()
    try {
      in = new ZipInputStream(new ByteArrayInputStream(zipFile))
      in.getNextEntry
      var n = 0
      while (n != -1) {
        if (n != 0) out.write(n)
        n = in.read()
      }
      out.toByteArray
    } catch {
      case NonFatal(e) => throw e
    } finally {
      if (null != in) in.close()
      if (null != out) in
    }
  }

  def unzipByURL(path : String) : Array[Byte] = {
    val url : URL = new URL(path)
    var in : ZipInputStream = null
    val out = new ByteArrayOutputStream()
    try {
      in = new ZipInputStream(url.openStream())
      in.getNextEntry
      var n = 0
      while (n != -1) {
        if (n != 0) out.write(n)
        n = in.read()
      }
      out.toByteArray
    } catch {
      case NonFatal(e) => throw e
    } finally {
      if (null != in) in.close()
      if (null != out) in
    }
  }



  /**
    * for test
    * @param parameter
    * @param streamError
    */
  def recordErrorToHDFS(parameter:NirvanaSparkConfig,streamError: StreamError) : Unit = {
    val hadoopConf = new Configuration()
    val hdfs = FileSystem.get(new URI(parameter.hdfsServer),hadoopConf)
    try {
      hdfs.exists(new Path(parameter.hdfsServer+"/error_file.log"))
    } catch {
      case e:IllegalArgumentException =>  hdfs.create(new Path(parameter.hdfsServer+"/error_file.log"))
    }
    var error_file = hdfs.open(new Path(parameter.hdfsServer+"/error_file.log"))
  }

}
