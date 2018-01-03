package nirvana.hall.spark.services

import java.io.File

import kafka.serializer.StringDecoder
import monad.support.MonadSupportConstants
import monad.support.services.XmlLoader
import nirvana.hall.spark.config.NirvanaSparkConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils

import scala.io.Source

/**
  * Created by wangjue on 2017/12/14.
  */
object BigDataForBjsjStream {


  def createStreamContext(checkpointDirectory:Option[String],parameter:NirvanaSparkConfig): StreamingContext ={
    val conf = new SparkConf().setAppName(parameter.appName)
      .setMaster(parameter.masterServer)
    conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    conf.set("spark.driver.host",parameter.host)
    val ssc =  new StreamingContext(conf, Minutes(parameter.streamingContextStartTime))
    checkpointDirectory.foreach(ssc.checkpoint)
    val kafkaParams = Map[String, String]("metadata.broker.list" -> parameter.kafkaServer,"auto.offset.reset"->"smallest")
    val kafkaTopicName = parameter.kafkaTopicName

    //设置系统属性
    SysProperties.setConfig(parameter)
    KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams,Set(kafkaTopicName))
      .repartition(parameter.partitionsNum) //reset partition
      .map(_._2) //only use message content
      .flatMap{x=>
      SysProperties.setConfig(parameter)
      SparkFunctions.loadExtractorJNI()
      SparkFunctions.loadImageJNI()
      ImageProviderService.requestRemoteFileByFID(parameter,x)
    } //fetch files
      .foreachRDD{rdd=>
      //save records for partition
      rdd.foreachPartition{x=>
        SysProperties.setConfig(parameter)
        PartitionRecordsSaverService.savePartitionRecords(parameter)(x)
      }
    }
    ssc
  }
  def main(args:Array[String]): Unit ={
    if(args.length != 1){
      println("please add parameter: <configuration file path>")
      return
    }
    val content = Source.fromFile(new File(args(0)),MonadSupportConstants.UTF8_ENCODING).mkString
    val config = XmlLoader.parseXML[NirvanaSparkConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/spark/nirvana-spark.xsd")))
    val checkpointDirectory = config.hdfsServer
    val ssc =
      if(checkpointDirectory == "None"){
        createStreamContext(None,config)
      }else {
        StreamingContext.getOrCreate(checkpointDirectory, () => {
          createStreamContext(Some(checkpointDirectory), config)
        })
      }

    ssc.start()
    ssc.awaitTermination()
  }

}
